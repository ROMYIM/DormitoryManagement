package com.dormitory.util;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class MyWebSocketHandler implements WebSocketHandler {

	private static final Map<String, WebSocketSession> SOCKET_SESSION_MAP;
	
	static {
		SOCKET_SESSION_MAP = new ConcurrentHashMap<>();
	}
	
	public MyWebSocketHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void afterConnectionClosed(WebSocketSession arg0, CloseStatus arg1) throws Exception {
		// TODO Auto-generated method stub
		Iterator<Map.Entry<String, WebSocketSession>> iterator = SOCKET_SESSION_MAP.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, WebSocketSession> entry = iterator.next();
			if (entry.getValue().getAttributes().get("uid") == arg0.getAttributes().get("uid")) {
				SOCKET_SESSION_MAP.remove(arg0.getAttributes().get("uid"));
			}
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession arg0) throws Exception {
		// TODO Auto-generated method stub
		String uid = (String) arg0.getAttributes().get("uid");
		if (SOCKET_SESSION_MAP.get(uid) == null) {
			SOCKET_SESSION_MAP.put(uid, arg0);
		}
	}

	@Override
	public void handleMessage(WebSocketSession arg0, WebSocketMessage<?> arg1) throws Exception {
		// TODO Auto-generated method stub
		if (arg1.getPayloadLength() == 0) {
			return;
		}
		// 获取消息，存入数据库
		//发送消息
		WebSocketSession session = SOCKET_SESSION_MAP.get("接受信息用户id");
		if (session != null && session.isOpen()) {
			session.sendMessage(new TextMessage("消息对象"));
		}
	}

	@Override
	public void handleTransportError(WebSocketSession arg0, Throwable arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
