package com.ewha.heydongdong.infra.exception;

import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    ObjectMapper objectMapper;

    private String buildResponseJson(CustomRuntimeException e, String msg) {
        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name(e.getNAME())
                        .message(msg)
                        .build())
                .build();
        return objectMapper.valueToTree(response).toPrettyString();
    }

    @ExceptionHandler({InvalidRequestFormatException.class})
    public ResponseEntity<?> handleInvalidRequestFormatException(final InvalidRequestFormatException e) {

        String msg = e.getNAME() + ": Invalid request format [" + e.getMessage() + "]";
        log.error(msg);
        return ResponseEntity.badRequest().body(buildResponseJson(e, msg));
    }

    @ExceptionHandler({InvalidRequestParameterException.class})
    public ResponseEntity<?> handleInvalidRequestParameterException(final InvalidRequestParameterException e) {

        String msg = e.getNAME() + ": Invalid request parameter [" + e.getMessage() + "]";
        log.error(msg);
        return ResponseEntity.badRequest().body(buildResponseJson(e, msg));
    }

    @ExceptionHandler({NoResultFromDBException.class})
    public ResponseEntity<?> handleNoResultFromDBException(final NoResultFromDBException e) {

        log.info(e.getNAME() + ": No result found on DB [" + e.getMessage() + "]");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({DuplicateUserException.class})
    public ResponseEntity<?> handleDuplicateUserException(final DuplicateUserException e) {

        String msg = e.getNAME() + ": Duplicate User [" + e.getMessage() + "]";
        log.error(msg);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({NoSuchUserException.class})
    public ResponseEntity<?> handleNoSuchUserException(final NoSuchUserException e) {

        String msg = e.getNAME() + ": No such user [" + e.getMessage() + "]";
        log.error(msg);
        return ResponseEntity.badRequest().body(buildResponseJson(e, msg));
    }

    @ExceptionHandler({WrongOriginalPwException.class})
    public ResponseEntity<?> handleWrongOriginalPwException(final WrongOriginalPwException e) {

        String msg = e.getNAME() + ": Wrong original password [userId=" + e.getMessage() + "]";
        log.error(msg);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
