package com.mutsa.delivery.cart.dto.response;

import com.mutsa.delivery.cart.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {
    private Long cartId;
    private List<CartItemResponseDto> items; // 장바구니에 담긴 상품 리스트
    private Long totalCartPrice;             // 장바구니 총 금액 (모든 상품 + 옵션가 합산)

    public static CartResponseDto from(Cart cart) {
        // 1. 하위 장바구니 상품 엔티티들을 DTO로 변환
        List<CartItemResponseDto> itemDtos = cart.getCartItems().stream()
                .map(CartItemResponseDto::from)
                .collect(Collectors.toList());

        // 2. 변환된 DTO들을 순회하며 장바구니 전체 총 금액 계산
        long totalCartPrice = itemDtos.stream()
                .mapToLong(CartItemResponseDto::calculateItemTotalPrice)
                .sum();

        return CartResponseDto.builder()
                .cartId(cart.getCartId())
                .items(itemDtos)
                .totalCartPrice(totalCartPrice)
                .build();
    }
}