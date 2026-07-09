package com.mutsa.delivery.global.apiPayload.exception;

import com.mutsa.delivery.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class ProjectException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public ProjectException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ProjectException(BaseErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
