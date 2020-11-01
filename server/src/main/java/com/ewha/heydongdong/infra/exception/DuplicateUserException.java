package com.ewha.heydongdong.infra.exception;

import lombok.Getter;

public class DuplicateUserException extends RuntimeException {

    @Getter
    private final int ERR_CODE;

    public DuplicateUserException(String message) {
        super(message);
        ERR_CODE = 900;
    }
}