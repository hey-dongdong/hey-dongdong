package com.ewha.heydongdong.infra.dongdong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
public class DongdongAdmin extends Thread {
    WebSocketSession session;

    public DongdongAdmin(WebSocketSession session) {
        this.session = session;
    }

    public void run() {

    }
}
