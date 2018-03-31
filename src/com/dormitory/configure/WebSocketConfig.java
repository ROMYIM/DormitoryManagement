package com.dormitory.configure;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.dormitory.constant.MessageStatus;
import com.dormitory.entity.Message;
import com.dormitory.interceptor.WebSocketHandShakeInterceptor;
import com.dormitory.service.IMessageService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	@Autowired
	private MyWebSocketHandler webSocketHandler;
	
	@Resource(name = "messageService")
	private IMessageService messageService;

	public WebSocketConfig() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry arg0) {
		// TODO Auto-generated method stub
		arg0.addHandler(webSocketHandler, "/ws").addInterceptors(new WebSocketHandShakeInterceptor());
	}
	
	@Bean(name = "webSocketHandler")
	public MyWebSocketHandler createWebSockerHandler() {
		return new MyWebSocketHandler();
	}
	
	private class MyWebSocketHandler implements WebSocketHandler {
		
		private final Map<String, WebSocketSession> socketSessionMap = new ConcurrentHashMap<>();
		private Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-mm-dd HH:mm:ss").create();

		@Override
		public void afterConnectionClosed(WebSocketSession arg0, CloseStatus arg1) throws Exception {
			// TODO Auto-generated method stub
			Iterator<Map.Entry<String, WebSocketSession>> iterator = socketSessionMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, WebSocketSession> entry = iterator.next();
				if (entry.getValue().getAttributes().get("uid") == arg0.getAttributes().get("uid")) {
					socketSessionMap.remove(arg0.getAttributes().get("uid"));
				}
			}
		}

		@Override
		public void afterConnectionEstablished(WebSocketSession arg0) throws Exception {
			// TODO Auto-generated method stub
			String uid = (String) arg0.getAttributes().get("uid");
			if (socketSessionMap.get(uid) == null) {
				socketSessionMap.put(uid, arg0);
			}
		}

		@Override
		public void handleMessage(WebSocketSession arg0, WebSocketMessage<?> arg1) throws Exception {
			// TODO Auto-generated method stub
			if (arg1.getPayloadLength() == 0) {
				return;
			}
			// 构建消息对象
			Message message = gson.fromJson(arg1.getPayload().toString(), Message.class);
			message.setStatus(MessageStatus.NOT_SEND);
			message.setSendDate(new Date());
			//发送消息
			WebSocketSession session = socketSessionMap.get(message.getReceiverId());
			if (session != null && session.isOpen()) {
				message.setStatus(MessageStatus.SEND);
				session.sendMessage(new TextMessage(gson.toJson(message)));
			} 
			messageService.addMessage(message);
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

}
