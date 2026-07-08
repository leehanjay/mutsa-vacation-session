package com.mutsa.delivery.cart.service;

import com.mutsa.delivery.cart.dto.request.CartItemAddRequestDto;
import com.mutsa.delivery.cart.dto.request.CartItemUpdateRequestDto;
import com.mutsa.delivery.cart.dto.response.CartItemResponseDto;
import com.mutsa.delivery.cart.dto.response.CartResponseDto;
import com.mutsa.delivery.cart.entity.Cart;
import com.mutsa.delivery.cart.entity.CartItem;
import com.mutsa.delivery.cart.entity.CartItemOption;
import com.mutsa.delivery.cart.repository.CartItemOptionRepository;
import com.mutsa.delivery.cart.repository.CartItemRepository;
import com.mutsa.delivery.cart.repository.CartRepository;
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

    //장바구니 담기
    @Transactional
    public Long addCartItem(CartItemAddRequestDto requestDto) {
        Long dummyUserId = 1L;
        User user = userRepository.findById(dummyUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Cart cart = cartRepository.findByUserId(dummyUserId)
                .orElseGet(() -> {
                    Cart newCart = Cart.createNew(user);
                    return cartRepository.save(newCart);
                });

        Menu menu = menuRepository.findById(requestDto.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));

        CartItem cartItem = CartItem.createNew(cart, menu, requestDto.getItemQuantity());
        CartItem savedCartItem = cartItemRepository.save(cartItem);

        if (requestDto.getOptionIds() != null && !requestDto.getOptionIds().isEmpty()) {
            List<Option> options = optionRepository.findAllById(requestDto.getOptionIds());

            if (options.size() != requestDto.getOptionIds().size()) {
                throw new IllegalArgumentException("존재하지 않는 옵션이 포함되어 있습니다.");
            }

            for (Option option : options) {
                CartItemOption cartItemOption = CartItemOption.createNew(savedCartItem,option);
                cartItemOptionRepository.save(cartItemOption);
            }
        }

        return savedCartItem.getCartItemId();
    }
    @Transactional(readOnly = true)
    public CartResponseDto getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 비어 있습니다."));
        return CartResponseDto.from(cart);
    }

    @Transactional
    public CartItemResponseDto updateCartItemQuantity(Long userId, Long cartItemId, Long quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 상품이 존재하지 않습니다"));

        if (!cartItem.getCart().getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("해당 장바구니 상품에 대한 권한이 없습니다.");}

            cartItem.updateQuantity(quantity);
            return CartItemResponseDto.from(cartItem);
    }

    @Transactional
    public void deleteCartItem(Long userId, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(()-> new IllegalArgumentException("장바구니 상품이 존재하지 않습니다."));

        if (!cartItem.getCart().getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("해당 장바구니 상품에 대한 권한이 없습니다.");
        }
        cartItemRepository.delete(cartItem);
    }
}
