package com.ewha.heydongdong.infra.exception;

import lombok.Getter;

public class NoSuchUserException extends RuntimeException implements CustomRuntimeException {

    @Getter
    private final String NAME;

    public NoSuchUserException(String message) {
        super(message);
        NAME = "NoSuchUserException";
    }
}
