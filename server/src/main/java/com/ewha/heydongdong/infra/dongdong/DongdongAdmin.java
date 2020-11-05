package com.ewha.heydongdong.infra.dongdong;

import com.ewha.heydongdong.infra.protocol.Request;
import com.ewha.heydongdong.module.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
public class DongdongAdmin extends Thread {

    @Autowired
    OrderService orderService;

    WebSocketSession session;
    Request request;
    ResponseEntity<?> responseEntity;

    public DongdongAdmin(WebSocketSession session, Request request) {
        this.session = session;
        this.request = request;
    }

    public void run() {

        log.info("[Request] order-request");

        String historyDetail = orderService.getUserHistoryDetail(
                request.getHeader().getUserId(),
                request.getPayload().get("orderId").asLong()
        );

        return new ResponseEntity<>(historyDetail, HttpStatus.OK);

    }
}
