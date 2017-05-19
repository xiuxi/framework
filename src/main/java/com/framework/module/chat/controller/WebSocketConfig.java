package com.framework.module.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebMvc
// 声明该类支持WebSocket
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
	@Autowired
	private SystemWebSocketHandler systemWebSocketHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// 用来注册WebSocket Server实现类，第二个参数是访问WebSocket的地址
		// setAllowedOrigins: 设置来自哪些域名的请求可访问(默认: localhost)
		registry.addHandler(systemWebSocketHandler, "/webSocketServer").setAllowedOrigins("*");
		// 允许客户端使用SockJS
		registry.addHandler(systemWebSocketHandler, "/webSocketServer/sockjs").setAllowedOrigins("*").withSockJS();
		// registry.addHandler(systemWebSocketHandler(),
		// "/sockjs/webSocketServer").setAllowedOrigins("*").withSockJS();

		// 添加拦截器
		// registry.addHandler(systemWebSocketHandler(), "/webSocketServer")
		// .addInterceptors(new WebSocketHandshakeInterceptor());

		// registry.addHandler(systemWebSocketHandler(),
		// "/webSocketServer/sockjs")
		// .addInterceptors(new WebSocketHandshakeInterceptor()).withSockJS();
	}

}
