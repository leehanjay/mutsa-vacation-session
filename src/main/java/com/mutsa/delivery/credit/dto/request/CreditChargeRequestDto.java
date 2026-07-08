package com.mutsa.delivery.credit.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreditChargeRequestDto {
    @NotNull(message = "충전 금액은 필수입니다.")
    @Min(value = 1, message = "충전 금액은 1원 이상이어야 합니다.")
    @Max(value = 10_000_000, message = "충전 금액은 1회 최대 10,000,000원까지 가능합니다.")
    private Long amount;
}
