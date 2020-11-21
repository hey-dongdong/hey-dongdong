package com.ewha.heydongdong.infra.exception;

import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        return handleInvalidRequestParameterException(new InvalidRequestParameterException(e.getMessage()));
    }

    @ExceptionHandler({WrongOriginalPwException.class})
    public ResponseEntity<?> handleWrongOriginalPwException(final WrongOriginalPwException e) {

        String msg = e.getNAME() + ": Wrong original password [userId=" + e.getMessage() + "]";
        log.error(msg);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({JsonProcessingException.class, JSONException.class})
    public ResponseEntity<?> handleJsonException(final Exception e) {

        String msg = "Json exception [" + e.getMessage() + "]";
        log.error(msg);
        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("JsonException")
                        .message(msg)
                        .build())
                .build();
        return ResponseEntity.badRequest().body(objectMapper.valueToTree(response).toPrettyString());
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<?> handleEmptyResultDataAccessException(final EmptyResultDataAccessException e) {

        String msg = "Empty result data access exception [" + e.getMessage() + "]";
        log.error(msg);
        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("EmptyResultDataAccessException")
                        .message(msg)
                        .build())
                .build();
        return ResponseEntity.badRequest().body(objectMapper.valueToTree(response).toPrettyString());
    }

    @ExceptionHandler({NotVerifiedUserException.class})
    public ResponseEntity<?> handleNotVerifiedUserException(final NotVerifiedUserException e) {

        String msg = "Email not verified [userId=" + e.getMessage() + "]";
        log.error(msg);
        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("NotVerifiedUserException")
                        .message(msg)
                        .build())
                .build();
        return new ResponseEntity<>(objectMapper.valueToTree(response).toPrettyString(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ExpiredJwtException.class, SignatureException.class, MalformedJwtException.class})
    public ResponseEntity<?> handleInvalidJwtException(final Exception e) {

        String msg = "Invalid JWT exception [" + e.getMessage() + "]";
        log.error(msg);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
