package com.ewha.heydongdong.infra.exception;

import lombok.Getter;

public class NoResultFromDBException extends RuntimeException implements CustomRuntimeException {

    @Getter
    private final String NAME;

    public NoResultFromDBException(String message) {
        super(message);
        NAME = "NoResultFromDBException";
    }
}
