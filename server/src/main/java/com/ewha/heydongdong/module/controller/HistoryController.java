package com.ewha.heydongdong.module.controller;

import com.ewha.heydongdong.infra.protocol.Request;
import com.ewha.heydongdong.module.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @PostMapping(value = "/get-all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserHistory(@RequestBody Request request) {

        log.info("[Request] get-user-history");

        request.validateHeader("GetUserHistoryRequest");
        String history = historyService.getUserHistory(request.getHeader().getUserId());
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @PostMapping(value = "/detail", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserHistoryDetail(@RequestBody Request request) {

        log.info("[Request] get-user-history-detail");

        request.validateHeader("GetUserHistoryDetailRequest");
        request.validatePayload();

        String historyDetail = historyService.getUserHistoryDetail(
                request.getHeader().getUserId(),
                request.getPayload().get("orderId").asLong()
        );
        return new ResponseEntity<>(historyDetail, HttpStatus.OK);
    }
}
