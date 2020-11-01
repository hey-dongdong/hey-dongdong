package com.ewha.heydongdong.infra.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({InvalidRequestFormatException.class})
    public ResponseEntity<?> handleInvalidRequestFormatException(final InvalidRequestFormatException e) {

        String msg = e.getERR_CODE() + " Invalid request format [" + e.getMessage() + "]";
        log.error(msg);
        return ResponseEntity.badRequest().body(msg);
    }

    @ExceptionHandler({InvalidRequestParameterException.class})
    public ResponseEntity<?> handleInvalidRequestParameterException(final InvalidRequestParameterException e) {

        String msg = e.getERR_CODE() + " Invalid request parameter [" + e.getMessage() + "]";
        log.error(msg);
        return ResponseEntity.badRequest().body(msg);
    }

    @ExceptionHandler({NoResultFromDBException.class})
    public ResponseEntity<?> handleNoResultFromDBException(final NoResultFromDBException e) {

        log.error(e.getERR_CODE() + " No result found on DB [" + e.getMessage() + "]");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({DuplicateUserException.class})
    public ResponseEntity<?> handleDuplicateUserException(final DuplicateUserException e) {

        String msg = e.getERR_CODE() + " Duplicate User [" + e.getMessage() + "]";
        log.error(msg);
        return ResponseEntity.badRequest().body(msg);
    }
}
