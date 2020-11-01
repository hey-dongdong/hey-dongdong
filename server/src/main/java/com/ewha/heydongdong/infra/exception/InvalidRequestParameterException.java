package com.ewha.heydongdong.infra.exception;

import lombok.Getter;

public class InvalidRequestParameterException extends RuntimeException {

    @Getter
    private final int ERR_CODE;

    public InvalidRequestParameterException(String message) {
        super(message);
        ERR_CODE = 700;
    }
}
