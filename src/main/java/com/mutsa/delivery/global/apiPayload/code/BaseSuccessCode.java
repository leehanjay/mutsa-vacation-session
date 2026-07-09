package com.mutsa.delivery.global.apiPayload.code;

import org.springframework.http.HttpStatus;

public interface BaseSuccessCode {

    HttpStatus getStatus();

    String getCode();

    String getMessage();
}
