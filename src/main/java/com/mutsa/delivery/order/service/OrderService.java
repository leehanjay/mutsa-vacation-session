package com.mutsa.delivery.order.service;

import com.mutsa.delivery.cart.entity.Cart;
import com.mutsa.delivery.cart.entity.CartItem;
import com.mutsa.delivery.cart.entity.CartItemOption;
import com.mutsa.delivery.cart.repository.CartItemRepository;
import com.mutsa.delivery.cart.repository.CartRepository;
import com.mutsa.delivery.credit.entity.CreditTransaction;
import com.mutsa.delivery.credit.repository.CreditTransactionRepository;
import com.mutsa.delivery.global.apiPayload.code.GeneralErrorCode;
import com.mutsa.delivery.global.apiPayload.exception.ProjectException;
import com.mutsa.delivery.order.dto.request.OrderRequestDto;
import com.mutsa.delivery.order.dto.response.OrderResponseDto;
import com.mutsa.delivery.order.entity.Order;
import com.mutsa.delivery.order.entity.OrderItem;
import com.mutsa.delivery.order.entity.OrderItemOption;
import com.mutsa.delivery.order.entity.OrderStore;
import com.mutsa.delivery.order.exception.OrderErrorCode;
import com.mutsa.delivery.order.repository.OrderItemOptionRepository;
import com.mutsa.delivery.order.repository.OrderItemRepository;
import com.mutsa.delivery.order.repository.OrderRepository;
import com.mutsa.delivery.order.repository.OrderStoreRepository;
import com.mutsa.delivery.store.entity.Store;
import com.mutsa.delivery.user.entity.User;
import com.mutsa.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderStoreRepository orderStoreRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemOptionRepository orderItemOptionRepository;
    private final CreditTransactionRepository creditTransactionRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderResponseDto createOrder(Long userId, OrderRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.USER_NOT_FOUND)); // 변경

        if (user.getCredit() < requestDto.getUsedCredit()) {
            throw new ProjectException(OrderErrorCode.INSUFFICIENT_CREDIT); // 변경
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ProjectException(OrderErrorCode.CART_EMPTY)); // 변경

        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems == null || cartItems.isEmpty()) {
            throw new ProjectException(OrderErrorCode.CART_EMPTY); // 변경
        }

        Long totalPrice = 0L;
        for (CartItem cartItem : cartItems) {
            Long menuPrice = cartItem.getMenu().getMenuPrice();
            Long optionsPrice = cartItem.getCartItemOptions().stream()
                    .mapToLong(cio -> cio.getOption().getExtraPrice())
                    .sum();

            totalPrice += (menuPrice + optionsPrice) * cartItem.getItemQuantity();
        }

        // 입력된 크레딧이 총 주문 금액을 초과하는지 검증
        if (totalPrice < requestDto.getUsedCredit()) {
            throw new ProjectException(OrderErrorCode.INVALID_CREDIT_USAGE);
        }

        Order order = Order.createNew(user, totalPrice, requestDto.getUsedCredit());
        Order savedOrder = orderRepository.save(order);

        Store store = cartItems.get(0).getMenu().getStore();
        OrderStore orderStore = OrderStore.createNew(savedOrder, store, store.getStoreName());
        OrderStore savedOrderStore = orderStoreRepository.save(orderStore);

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = OrderItem.fromCartItem(savedOrderStore, cartItem);
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);

            for (CartItemOption cartItemOption : cartItem.getCartItemOptions()) {
                OrderItemOption orderItemOption = OrderItemOption.fromCartItemOption(savedOrderItem, cartItemOption);
                orderItemOptionRepository.save(orderItemOption);
            }
        }

        Long balanceAfter = user.getCredit() - requestDto.getUsedCredit();
        user.updateCredit(balanceAfter);

        CreditTransaction creditTransaction = CreditTransaction.use(user, savedOrder, requestDto.getUsedCredit(), balanceAfter);
        creditTransactionRepository.save(creditTransaction);

        cartItemRepository.deleteAll(cartItems);

        return OrderResponseDto.from(savedOrder);
    }
}