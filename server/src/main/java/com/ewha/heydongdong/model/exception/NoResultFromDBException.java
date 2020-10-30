package com.ewha.heydongdong.model.exception;

import lombok.Getter;

public class NoResultFromDBException extends RuntimeException {

    @Getter
    private final int ERR_CODE;

    public NoResultFromDBException(String message) {
        super(message);
        ERR_CODE = 800;
    }
}
