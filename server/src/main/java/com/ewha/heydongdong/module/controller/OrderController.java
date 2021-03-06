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

        String addNewOrderResult = orderService.addNewOrder(request.getPayload());
        return new ResponseEntity<>(addNewOrderResult, HttpStatus.OK);
    }

    @PostMapping(value = "/get-progress", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getOrderProgress(@RequestBody Request request) {

        log.info("[Request] get-order-progress");
        request.validateHeader("GetOrderProgressRequest");
        request.validatePayload();

        String orderProgress = orderService.getOrderProgress(request.getPayload().get("orderId").asLong());
        return new ResponseEntity<>(orderProgress, HttpStatus.OK);
    }

    @PostMapping(value = "/update-progress", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateOrderProgress(@RequestBody Request request) throws InterruptedException {

        log.info("[Request] update-order-progress");
        request.validateHeader("UpdateOrderProgressRequest");
        request.validatePayload();

        String updateOrderProgressResult = orderService.updateOrderProgress(request.getPayload());
        return new ResponseEntity<>(updateOrderProgressResult, HttpStatus.OK);
    }
}
