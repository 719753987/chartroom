package com.chartroom.config;

import com.chartroom.websocket.ChartRoom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * Created by icinfo on 2017-09-19.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chartRoom(),"/chartroom")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .withSockJS();
    }

    @Bean
    public ChartRoom chartRoom(){
        return new ChartRoom();
    }

}
