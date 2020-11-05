package com.ewha.heydongdong.infra.dongdong;

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
public class DongdongManager extends AbstractWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("WebSocket connection established with " + session);
    }

    @Async
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {

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
