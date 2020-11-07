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
    public ResponseEntity<?> signUp(@RequestBody Request request) throws JsonProcessingException {

        log.info("[Request] user-sign-up");
        request.validateHeader("SignUpRequest");
        request.validatePayload();

        String signUpResult = userService.signUp(request.getPayload());
        return new ResponseEntity<>(signUpResult, HttpStatus.OK);
    }

    @GetMapping(value = "/check-email-token/{email}/{emailCheckToken}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> checkEmailToken(@PathVariable(value = "email") String email,
                                             @PathVariable(value = "emailCheckToken") String emailCheckToken) {

        log.info("[Request] check-email-token");

        String checkEmailResult = userService.checkEmailToken(email, emailCheckToken);
        return new ResponseEntity<>(checkEmailResult, HttpStatus.OK);
    }

    @PostMapping(value = "/sign-in", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> signInSubmit(@RequestBody Request request) {

        log.info("[Request] user-sign-in");
        request.validateHeader("SignInRequest");
        request.validatePayload();

        String signInResult = userService.signIn(request.getPayload());
        return new ResponseEntity<>(signInResult, HttpStatus.OK);
    }

    @PostMapping(value = "/find-info/id", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> findUserId(@RequestBody Request request) throws JsonProcessingException {

        log.info("[Request] find-user-id");
        request.validateHeader("FindIdRequest");
        request.validatePayload();

        String userId = userService.findUserId(request.getPayload());
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @PostMapping(value = "/find-info/pw", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> findUserPw(@RequestBody Request request) throws JsonProcessingException {

        log.info("[Request] find-user-pw");
        request.validateHeader("FindPwRequest");
        request.validatePayload();

        String userPw = userService.findUserPw(request.getPayload());
        return new ResponseEntity<>(userPw, HttpStatus.OK);
    }

    @GetMapping(value = "/login-by-email/{email}/{emailCheckToken}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> loginByEmail(@PathVariable(value = "email") String email,
                                          @PathVariable(value = "emailCheckToken") String emailCheckToken) {

        log.info("[Request] login-by-email");

        String loginByEmailResult = userService.loginByEmail(email, emailCheckToken);
        return new ResponseEntity<>(loginByEmailResult, HttpStatus.OK);
    }

    @PostMapping(value = "/change-pw", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> changePw(@RequestBody Request request) throws JsonProcessingException {

        log.info("[Request] change-pw");

        String changePwResult = userService.changePw(request.getPayload());
        return new ResponseEntity<>(changePwResult, HttpStatus.OK);
    }

    @PostMapping(value = "/no-show-count", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserNoShowCount(@RequestBody Request request) {

        log.info("[Request] get-user-no-show-count");
        request.validateHeader("GetNoShowCountRequest");

        String noShowCount = userService.getUserNoShowCount(request.getHeader().getUserId());
        return new ResponseEntity<>(noShowCount, HttpStatus.OK);
    }
}
