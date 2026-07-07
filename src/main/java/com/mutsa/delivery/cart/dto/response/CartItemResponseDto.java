package com.mutsa.delivery.cart.dto.response;

import com.mutsa.delivery.cart.entity.CartItem;
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
public class CartItemResponseDto {
    private Long cartItemId;
    private Long menuId;
    private String menuName;
    private Long itemQuantity;
    private Long menuPrice;
    private List<CartItemOptionResponseDto> options; // 해당 상품에 선택된 옵션 리스트

    public static CartItemResponseDto from(CartItem cartItem) {
        return CartItemResponseDto.builder()
                .cartItemId(cartItem.getCartItemId())
                .menuId(cartItem.getMenu().getMenuId())
                .menuName(cartItem.getMenu().getMenuName())
                .itemQuantity(cartItem.getItemQuantity())
                .menuPrice(cartItem.getMenu().getMenuPrice())
                // 자식 옵션 엔티티 리스트를 DTO 리스트로 변환하여 주입
                .options(cartItem.getCartItemOptions().stream()
                        .map(CartItemOptionResponseDto::from)
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * 이 상품 항목의 총 금액을 계산하는 메서드 (메뉴가 + 옵션가 전체) * 수량
     */
    public Long calculateItemTotalPrice() {
        long optionsPrice = options.stream()
                .mapToLong(CartItemOptionResponseDto::getExtraPrice)
                .sum();
        return (menuPrice + optionsPrice) * itemQuantity;
    }
}