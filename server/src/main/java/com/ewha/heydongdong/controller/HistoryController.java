package com.ewha.heydongdong.controller;

import com.ewha.heydongdong.protocol.Request;
import com.ewha.heydongdong.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping(value = "/get-all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserHistory(@RequestBody Request request) {

        log.debug("[Request] get-user-history");

        request.validateHeader("GetUserHistoryRequest");

        String userId = request.getHeader().getUserId();
        String history = historyService.getUserHistory(userId);

        return new ResponseEntity<>(history, HttpStatus.OK);
    }
}
