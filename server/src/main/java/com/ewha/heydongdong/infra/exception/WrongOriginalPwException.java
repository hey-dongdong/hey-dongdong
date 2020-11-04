package com.ewha.heydongdong.infra.exception;

import lombok.Getter;

public class WrongOriginalPwException extends RuntimeException implements CustomRuntimeException {

    @Getter
    private final String NAME;

    public WrongOriginalPwException(String message) {
        super(message);
        NAME = "WrongOriginalPwException";
    }
}
