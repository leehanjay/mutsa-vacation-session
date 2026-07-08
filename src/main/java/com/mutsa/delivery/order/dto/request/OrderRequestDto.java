package com.mutsa.delivery.order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {
    @NotNull(message = "사용할 크레딧 금액은 필수입니다.")
    @Min(value = 0, message = "사용할 크레딧은 0원 이상이어야 합니다.")
    private Long usedCredit;
}
