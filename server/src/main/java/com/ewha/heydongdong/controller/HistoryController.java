package com.ewha.heydongdong.controller;

import com.ewha.heydongdong.model.protocol.Request;
import com.ewha.heydongdong.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        String history = historyService.getUserHistory(request.getHeader().getUserId());
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @GetMapping(value = "/detail", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserHistoryDetail(@RequestBody Request request) {

        log.debug("[Request] get-user-history-detail");

        request.validateHeader("GetUserHistoryDetailRequest");
        request.validatePayload();

        String historyDetail = historyService.getUserHistoryDetail(
                request.getHeader().getUserId(),
                request.getPayload().get("orderId").asLong()
        );

        return new ResponseEntity<>(historyDetail, HttpStatus.OK);
    }
}