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
@RestController
@RequestMapping("user")
@ControllerAdvice
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

        userService.checkEmailToken(email, emailCheckToken);
        return new ResponseEntity<>("이메일 인증 완료되었습니다.\n헤이동동 앱에서 로그인하세요.", HttpStatus.OK);
    }

    @PostMapping(value = "/sign-in", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> signInSubmit(@RequestBody Request request) {

        log.info("[Request] user-sign-in");
        request.validateHeader("SignInRequest");
        request.validatePayload();

        String signInResult = userService.signIn(request.getPayload());
        return new ResponseEntity<>(signInResult, HttpStatus.OK);
    }

    @PostMapping(value = "/sign-out", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> signOut(@RequestBody Request request) {

        log.info("[Request] user-sign-out");
        request.validateHeader("SignOutRequest");

        String signOutResult = userService.signOut(request.getHeader().getUserId());
        return new ResponseEntity<>(signOutResult, HttpStatus.OK);
    }

    @PostMapping(value = "/find-info/id", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> findUserId(@RequestBody Request request) {

        log.info("[Request] find-user-id");
        request.validateHeader("FindIdRequest");
        request.validatePayload();

        String userId = userService.findUserId(request.getPayload());
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @PostMapping(value = "/find-info/pw", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> findUserPw(@RequestBody Request request) {

        log.info("[Request] find-user-pw");
        request.validateHeader("FindPwRequest");
        request.validatePayload();

        String userPw = userService.findUserPw(request.getPayload());
        return new ResponseEntity<>(userPw, HttpStatus.OK);
    }

    @PostMapping(value = "/change-pw", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> changePw(@RequestBody Request request) {

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

    @PostMapping(value = "/refresh-tokens", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> refreshUserToken(@RequestBody Request request) {

        log.info("[Request] refresh-user-tokens");
        request.validateHeader("RefreshTokensRequest");

        String refreshTokensResult = userService.refreshUserTokens(request.getPayload());
        return new ResponseEntity<>(refreshTokensResult, HttpStatus.OK);
    }
}



