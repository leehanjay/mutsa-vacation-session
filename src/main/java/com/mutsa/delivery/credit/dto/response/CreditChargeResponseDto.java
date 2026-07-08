package com.mutsa.delivery.credit.dto.response;

import com.mutsa.delivery.credit.entity.CreditTransaction;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreditChargeResponseDto {
    private Long userId;
    private Long chargedAmount;
    private Long balance;

    public static CreditChargeResponseDto from(CreditTransaction creditTransaction) {
        return CreditChargeResponseDto.builder()
                .userId(creditTransaction.getUser().getUserId())
                .chargedAmount(creditTransaction.getAmount())
                .balance(creditTransaction.getBalanceAfter())
                .build();
    }
}
