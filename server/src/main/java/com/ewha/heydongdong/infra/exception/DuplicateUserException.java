package com.ewha.heydongdong.infra.exception;

import lombok.Getter;

public class DuplicateUserException extends RuntimeException implements CustomRuntimeException {

    @Getter
    private final String NAME;

    public DuplicateUserException(String message) {
        super(message);
        NAME = "DuplicateUserException";
    }
}