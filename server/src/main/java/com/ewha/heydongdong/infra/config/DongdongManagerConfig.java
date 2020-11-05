package com.ewha.heydongdong.infra.config;

import com.ewha.heydongdong.infra.dongdong.WebSocketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.io.IOException;

@Profile("!stomp")
@Configuration
@EnableWebSocket
public class DongdongManagerConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketManager webSocketManager;

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public WebSocketManager MessageHandler() throws IOException {
        return new WebSocketManager();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketManager, "/order").setAllowedOrigins("*").withSockJS();
    }
}
