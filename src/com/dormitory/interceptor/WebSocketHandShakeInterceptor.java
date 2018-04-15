package com.dormitory.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class WebSocketHandShakeInterceptor implements HandshakeInterceptor {

	public WebSocketHandShakeInterceptor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
		// TODO Auto-generated method stub
		System.out.println("握手成功");
	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2,
			Map<String, Object> arg3) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("握手前");
		if (arg0 instanceof ServerHttpRequest) {
			ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) arg0;
			HttpServletRequest request = servletServerHttpRequest.getServletRequest();
			String userId = request.getParameter("userId");
			if (userId != null) {
				arg3.put("uid", userId);
				System.out.println("用户： " + userId + "被加入");
				if (arg3.values().isEmpty()) {
					System.out.println("map is null");
				}
				return true;
			}
		}
		return false;
	}

}
