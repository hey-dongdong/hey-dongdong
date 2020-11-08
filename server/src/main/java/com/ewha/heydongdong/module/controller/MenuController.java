package com.ewha.heydongdong.module.controller;

import com.ewha.heydongdong.infra.protocol.Request;
import com.ewha.heydongdong.module.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("menu")
@ControllerAdvice
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping(value = "/get-all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserHistory(@RequestBody Request request) {

        log.info("[Request] get-all-menus");
        request.validateHeader("GetAllMenusRequest");

        String allMenus = menuService.getAllMenus(request.getPayload().get("storeId").asInt());
        return new ResponseEntity<>(allMenus, HttpStatus.OK);
    }
}
