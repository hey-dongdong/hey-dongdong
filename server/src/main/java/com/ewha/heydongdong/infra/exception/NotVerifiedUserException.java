package com.ewha.heydongdong.infra.exception;

import lombok.Getter;

public class NotVerifiedUserException extends RuntimeException implements CustomRuntimeException {

    @Getter
    private final String NAME;

    public NotVerifiedUserException(String message) {
        super(message);
        NAME = "NotVerifiedUserException";
    }
}
