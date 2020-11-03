package com.ewha.heydongdong.module.controller;

import com.ewha.heydongdong.infra.protocol.Request;
import com.ewha.heydongdong.module.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping(value = "/{storeName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getStoreInfo(@PathVariable(value = "storeName") String storeName,
                                          @RequestBody Request request) {

        log.info("[Request] store-info");

        request.validateHeader("StoreInfoRequest");
        String storeInfo = storeService.getStoreInfo(storeName);

        return new ResponseEntity<>(storeInfo, HttpStatus.OK);
    }
}