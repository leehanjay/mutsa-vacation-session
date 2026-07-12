package com.mutsa.delivery.cart.service;

import com.mutsa.delivery.cart.dto.request.CartItemAddRequestDto;
import com.mutsa.delivery.cart.dto.request.CartItemUpdateRequestDto;
import com.mutsa.delivery.cart.dto.response.CartItemResponseDto;
import com.mutsa.delivery.cart.dto.response.CartResponseDto;
import com.mutsa.delivery.cart.entity.Cart;
import com.mutsa.delivery.cart.entity.CartItem;
import com.mutsa.delivery.cart.entity.CartItemOption;
import com.mutsa.delivery.cart.exception.CartErrorCode;
import com.mutsa.delivery.cart.repository.CartItemOptionRepository;
import com.mutsa.delivery.cart.repository.CartItemRepository;
import com.mutsa.delivery.cart.repository.CartRepository;
import com.mutsa.delivery.global.apiPayload.code.GeneralErrorCode;
import com.mutsa.delivery.global.apiPayload.exception.ProjectException;
import com.mutsa.delivery.menu.entity.Menu;
import com.mutsa.delivery.menu.entity.Option;
import com.mutsa.delivery.menu.repository.MenuRepository;
import com.mutsa.delivery.menu.repository.OptionRepository;
import com.mutsa.delivery.user.entity.User;
import com.mutsa.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemOptionRepository cartItemOptionRepository;
    private final MenuRepository menuRepository;
    private final OptionRepository optionRepository;
    private final UserRepository userRepository;

    // 장바구니 담기
    @Transactional
    public Long addCartItem(CartItemAddRequestDto requestDto) {
        Long dummyUserId = 1L;
        User user = userRepository.findById(dummyUserId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.USER_NOT_FOUND));

        Cart cart = cartRepository.findByUserId(dummyUserId)
                .orElseGet(() -> {
                    Cart newCart = Cart.createNew(user);
                    return cartRepository.save(newCart);
                });

        Menu menu = menuRepository.findById(requestDto.getMenuId())
                .orElseThrow(() -> new ProjectException(CartErrorCode.MENU_NOT_FOUND));

        // 🚨 [버그 픽스] 멀티스토어 메뉴 혼합 방지 검증 로직
        if (cart.getCartItems() != null && !cart.getCartItems().isEmpty()) {
            // 기존 장바구니에 있던 첫 번째 상품의 가게 ID 추출
            Long existingStoreId = cart.getCartItems().get(0).getMenu().getStore().getStoreId();
            // 새로 담으려는 메뉴의 가게 ID 추출
            Long newStoreId = menu.getStore().getStoreId();

            if (!existingStoreId.equals(newStoreId)) {
                throw new ProjectException(CartErrorCode.DIFFERENT_STORE_NOT_ALLOWED);
            }
        }

        CartItem cartItem = CartItem.createNew(cart, menu, requestDto.getItemQuantity());
        CartItem savedCartItem = cartItemRepository.save(cartItem);

        if (requestDto.getOptionIds() != null && !requestDto.getOptionIds().isEmpty()) {
            List<Option> options = optionRepository.findAllById(requestDto.getOptionIds());

            if (options.size() != requestDto.getOptionIds().size()) {
                throw new ProjectException(CartErrorCode.OPTION_NOT_FOUND);
            }

            for (Option option : options) {
                CartItemOption cartItemOption = CartItemOption.createNew(savedCartItem, option);
                cartItemOptionRepository.save(cartItemOption);
            }
        }

        return savedCartItem.getCartItemId();
    }

    @Transactional(readOnly = true)
    public CartResponseDto getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ProjectException(CartErrorCode.CART_NOT_FOUND)); // 변경
        return CartResponseDto.from(cart);
    }

    @Transactional
    public CartItemResponseDto updateCartItemQuantity(Long userId, Long cartItemId, Long quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ProjectException(CartErrorCode.CART_ITEM_NOT_FOUND)); // 변경

        if (!cartItem.getCart().getUser().getUserId().equals(userId)) {
            throw new ProjectException(CartErrorCode.CART_ITEM_ACCESS_DENIED); // 변경
        }

        cartItem.updateQuantity(quantity);
        return CartItemResponseDto.from(cartItem);
    }

    @Transactional
    public void deleteCartItem(Long userId, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ProjectException(CartErrorCode.CART_ITEM_NOT_FOUND)); // 변경

        if (!cartItem.getCart().getUser().getUserId().equals(userId)) {
            throw new ProjectException(CartErrorCode.CART_ITEM_ACCESS_DENIED); // 변경
        }
        cartItemRepository.delete(cartItem);
    }
}
