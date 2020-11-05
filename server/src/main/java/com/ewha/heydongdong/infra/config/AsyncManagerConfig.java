package com.ewha.heydongdong.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Profile("!stomp")
@Configuration
@EnableWebSocket
public class AsyncManagerConfig {
}
