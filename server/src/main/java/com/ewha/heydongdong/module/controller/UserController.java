package com.ewha.heydongdong.module.controller;

import com.ewha.heydongdong.infra.protocol.Request;
import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.ewha.heydongdong.module.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ControllerAdvice
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/sign-up", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> signUpSubmit(@RequestBody Request request) throws JsonProcessingException {

        log.info("[Request] user-sign-up");

        request.validateHeader("SignUpRequest");
        request.validatePayload();

        String message = userService.signUp(request.getPayload());

        return new ResponseEntity<>(
                Response.builder()
                        .header(ResponseHeader.builder().name("SignUpResponse").message(message).build())
                        .build(), HttpStatus.OK);
    }

    @GetMapping(value = "/check-email-token/{email}/{emailCheckToken}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> checkEmailToken(@PathVariable(value = "email") String email,
                                             @PathVariable(value = "emailCheckToken") String emailCheckToken) {

        log.info("[Request] check-email-token");

        String message = userService.checkEmailToken(email, emailCheckToken);

        return new ResponseEntity<>(
                Response.builder()
                        .header(ResponseHeader.builder().name("CheckEmailTokenResponse").message(message).build())
                        .build(), HttpStatus.OK);
    }

    @PostMapping(value = "/sign-in", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> signInSubmit(@RequestBody Request request) {

        log.info("[Request] user-sign-in");

        request.validateHeader("SignInRequest");
        request.validatePayload();

        String message = userService.signIn(request.getPayload());

        return new ResponseEntity<>(
                Response.builder()
                        .header(ResponseHeader.builder().name("SignInResponse").message(message).build())
                        .build(), HttpStatus.OK);
    }

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserNoShowCount(@RequestBody Request request) {

        log.info("[Request] get-user-no-show-count");

        request.validateHeader("GetNoShowCountRequest");
        String noShowCount = userService.getUserNoShowCount(request.getHeader().getUserId());
        return new ResponseEntity<>(noShowCount, HttpStatus.OK);
    }
}
