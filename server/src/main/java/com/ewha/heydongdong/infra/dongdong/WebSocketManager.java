package com.ewha.heydongdong.infra.dongdong;

import com.ewha.heydongdong.infra.protocol.Request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Slf4j
@Profile("!stomp")
@Component
public class WebSocketManager extends AbstractWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("WebSocket connection established with " + session);
    }

    @Async
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        String msgStr = objectMapper.writeValueAsString(message.getPayload());
        msgStr = msgStr.replaceAll("^\"|\"$|\\\\", "");

        Request request = objectMapper.readValue(msgStr, Request.class);
        request.validateHeader("OrderRequest");
        request.validatePayload();

        DongdongAdmin dongdongAdmin = new DongdongAdmin(session, request);
        dongdongAdmin.start();
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("transport error =" + session + ", exception = " + exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        log.info("Websocket connection closed with :" + session);
    }
}
