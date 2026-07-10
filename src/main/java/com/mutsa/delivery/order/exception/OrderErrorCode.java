package com.mutsa.delivery.order.exception;

import com.mutsa.delivery.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements BaseErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404", "존재하지 않는 유저입니다."),
    CART_EMPTY(HttpStatus.BAD_REQUEST, "ORDER400_1", "장바구니가 비어 있어 주문할 수 없습니다."),
    INSUFFICIENT_CREDIT(HttpStatus.BAD_REQUEST, "ORDER400_2", "보유하신 크레딧이 부족합니다."),
    INVALID_CREDIT_USAGE(HttpStatus.BAD_REQUEST, "ORDER400_3", "주문 금액보다 많은 크레딧을 사용할 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
