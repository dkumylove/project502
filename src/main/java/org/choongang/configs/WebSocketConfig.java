package org.choongang.configs;

import lombok.RequiredArgsConstructor;
import org.choongang.chatting.configs.ChatHandler;
import org.choongang.upbit.configs.UpBitHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;
    private final UpBitHandler upBitHandler;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "chat").setAllowedOrigins("*");
        registry.addHandler(upBitHandler, "upbit").setAllowedOrigins("*");
    }
}