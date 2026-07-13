package com.mutsa.delivery.global.security.exception;

import com.mutsa.delivery.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {
    ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "AUTH400_1", "이미 가입된 이메일입니다."),
    ALREADY_EXIST_NICKNAME(HttpStatus.BAD_REQUEST, "AUTH400_2", "이미 사용 중인 닉네임입니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "AUTH401_3", "존재하지 않는 사용자이거나 비밀번호가 일치하지 않습니다."), // 👈 중복 방지를 위해 401_3으로 변경!
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH401_1", "만료된 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH401_2", "유효하지 않은 토큰입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
