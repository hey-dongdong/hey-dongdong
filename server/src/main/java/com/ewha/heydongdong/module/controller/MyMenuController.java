package com.ewha.heydongdong.module.controller;

import com.ewha.heydongdong.infra.protocol.Request;
import com.ewha.heydongdong.module.service.MyMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("my-menu")
public class MyMenuController {

    @Autowired
    private MyMenuService myMenuService;

    @PostMapping(value = "/get-all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserMyMenu(@RequestBody Request request) {

        log.info("[Request] get-user-my-menu");

        request.validateHeader("GetMyMenusRequest");
        String myMenu = myMenuService.getUserMyMenu(request.getHeader().getUserId());

        return new ResponseEntity<>(myMenu, HttpStatus.OK);
    }

    @PostMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addUserMyMenu(@RequestBody Request request) {

        log.info("[Request] add-user-my-menu");

        request.validateHeader("AddMyMenuRequest");
        String myMenu = myMenuService.addUserMyMenu(
                request.getHeader().getUserId(),
                request.getPayload().get("menuInOrderId").asLong()
        );

        return new ResponseEntity<>(myMenu, HttpStatus.OK);
    }

    @PostMapping(value = "/remove", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> removeUserMyMenu(@RequestBody Request request) {

        log.info("[Request] remove-user-my-menu");

        request.validateHeader("RemoveMyMenuRequest");
        String myMenu = myMenuService.removeUserMyMenu(
                request.getHeader().getUserId(),
                request.getPayload().get("myMenuId").asLong()
        );

        return new ResponseEntity<>(myMenu, HttpStatus.OK);
    }
}
