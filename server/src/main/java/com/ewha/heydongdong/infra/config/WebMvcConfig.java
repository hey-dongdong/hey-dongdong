package com.ewha.heydongdong.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://heydongdong.ewha.com.s3-website.ap-northeast-2.amazonaws.com")
                .allowedMethods("GET", "POST", "OPTIONS", "PUT")
                .allowCredentials(false)
                .maxAge(3600);

    }
}