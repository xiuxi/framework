package com.framework.module.chat.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * 处理来之浏览器（客户端）的WebSocket请求
 * 
 * @author qq
 *
 */
@Component
public class SystemWebSocketHandler implements WebSocketHandler {
	private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();;

	/**
	 * 客户端连接成功的回调
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// users.add(session);
		System.out.println("ConnectionEstablished");
		// 发送消息给客户端
		session.sendMessage(new TextMessage("connect"));
	}

	/**
	 * 收到客户端消息的回调
	 */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		System.out.println("服务端收到新消息: " + message.toString());

		session.sendMessage(new TextMessage(message.getPayload().toString()));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		// users.remove(session);
		System.out.println("handleTransportError" + exception.getMessage());
	}

	/**
	 * 连接关闭的回调
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		// users.remove(session);
		System.out.println("afterConnectionClosed" + closeStatus.getReason());

	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 给所有在线用户发送消息
	 *
	 * @param message
	 */
	public void sendMessageToUsers(TextMessage message) {
		for (WebSocketSession user : users) {
			try {
				if (user.isOpen()) {
					user.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}