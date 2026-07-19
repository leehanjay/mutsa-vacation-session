package com.mutsa.delivery.global.apiPayload.exception;

import com.mutsa.delivery.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class ProjectException extends RuntimeException {

    private final BaseErrorCode errorCode;

    // 열거형에 있는 기본 메세지를 사용
    public ProjectException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    // 열거형의 기본 메세지 대신 상황에 맞는 커스텀 메세지를 줌
    public ProjectException(BaseErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
