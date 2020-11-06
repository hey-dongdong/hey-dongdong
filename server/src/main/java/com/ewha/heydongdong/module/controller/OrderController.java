package com.ewha.heydongdong.module.controller;

import com.ewha.heydongdong.infra.protocol.Request;
import com.ewha.heydongdong.module.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("order")
@ControllerAdvice
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addNewOrder(@RequestBody Request request) throws JsonProcessingException {

        log.info("[Request] add-new-order");

        request.validateHeader("AddNewOrderRequest");
        request.validatePayload();

        String newOrder = orderService.addNewOrder(request.getPayload());

        return new ResponseEntity<>(newOrder, HttpStatus.OK);
    }

    @PostMapping(value = "/update-progress", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateOrderProgress(@RequestBody Request request) {

        log.info("[Request] update-order-progress");

        request.validateHeader("UpdateOrderProgressRequest");
        request.validatePayload();

        String order = orderService.updateOrderProgress(request.getPayload());

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
