package com.ewha.heydongdong.module.controller;

import com.ewha.heydongdong.infra.protocol.Request;
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

        return new ResponseEntity<>(userService.signUp(request.getPayload()), HttpStatus.OK);
    }

    @GetMapping(value = "/check-email-token/{email}/{emailCheckToken}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> checkEmailToken(@PathVariable(value = "email") String email,
                                             @PathVariable(value = "emailCheckToken") String emailCheckToken) {

        log.info("[Request] check-email-token");

        return new ResponseEntity<>(userService.checkEmailToken(email, emailCheckToken), HttpStatus.OK);
    }
}
