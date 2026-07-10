package com.mutsa.delivery.cart.exception;

import com.mutsa.delivery.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CartErrorCode implements BaseErrorCode {
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "CART404", "장바구니가 비어 있습니다."),
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CART404_1", "장바구니 상품이 존재하지 않습니다."),
    CART_ITEM_ACCESS_DENIED(HttpStatus.FORBIDDEN, "CART403", "해당 장바구니 상품에 대한 권한이 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404", "존재하지 않는 유저입니다."),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "MENU404", "존재하지 않는 메뉴입니다."),
    OPTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "OPTION400", "존재하지 않는 옵션이 포함되어 있습니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}