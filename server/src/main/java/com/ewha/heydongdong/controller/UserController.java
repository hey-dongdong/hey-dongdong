package com.ewha.heydongdong.controller;

import com.ewha.heydongdong.model.protocol.Request;
import com.ewha.heydongdong.service.UserService;
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

        String result = userService.signUp(request.getPayload());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
