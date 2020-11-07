package com.ewha.heydongdong.module.controller;

import com.ewha.heydongdong.infra.protocol.Request;
import com.ewha.heydongdong.module.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private HistoryService historyService;

    @PostMapping(value = "/history/{store-id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserHistory(@PathVariable(value = "store-id") Integer storeId,
                                            @RequestBody Request request) {

        log.info("[Request] get-store-history");
        request.validateHeader("GetStoreHistoryRequest");

        String history = historyService.getStoreHistory(storeId);
        return new ResponseEntity<>(history, HttpStatus.OK);
    }
}
