package com.mutsa.delivery.cart.dto.response;

import com.mutsa.delivery.cart.entity.CartItemOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemOptionResponseDto {
    private Long optionId;
    private String optionName;
    private Long extraPrice; // 옵션 추가 금액

    public static CartItemOptionResponseDto from(CartItemOption cartItemOption) {
        return CartItemOptionResponseDto.builder()
                .optionId(cartItemOption.getOption().getOptionId())
                .optionName(cartItemOption.getOption().getOptionName())
                .extraPrice(cartItemOption.getOption().getExtraPrice())
                .build();
    }
}