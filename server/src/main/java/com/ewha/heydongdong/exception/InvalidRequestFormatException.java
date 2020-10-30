package com.ewha.heydongdong.exception;

import lombok.Getter;

public class InvalidRequestFormatException extends RuntimeException {

    @Getter
    private final int ERR_CODE;

    public InvalidRequestFormatException(String message) {
        super(message);
        ERR_CODE = 600;
    }
}
