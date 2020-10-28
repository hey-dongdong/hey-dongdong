package com.ewha.heydongdong.exception;

import lombok.Getter;

public class InvalidRequestException extends RuntimeException {

    @Getter
    private final int ERR_CODE;

    public InvalidRequestException(String message) {
        super(message);
        ERR_CODE = 100;
    }
}
