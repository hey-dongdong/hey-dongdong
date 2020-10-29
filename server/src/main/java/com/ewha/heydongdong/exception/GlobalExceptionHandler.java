package com.ewha.heydongdong.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({InvalidRequestException.class})
    public ResponseEntity<?> handleInvalidRequestException(final InvalidRequestException e) {

        log.error(e.getERR_CODE() + " " + e.getMessage());
        return ResponseEntity.badRequest().body("600 : Valid request required");
    }
}
