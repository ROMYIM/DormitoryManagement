package com.dormitory.interceptor;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.dormitory.entity.User;

public class WebSocketHandShakeInterceptor implements HandshakeInterceptor {

	public WebSocketHandShakeInterceptor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2,
			Map<String, Object> arg3) throws Exception {
		// TODO Auto-generated method stub
		if (arg0 instanceof ServerHttpRequest) {
			ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) arg0;
			HttpSession session = servletServerHttpRequest.getServletRequest().getSession(false);
			User user = (User) session.getAttribute("user");
			if (user != null) {
				arg3.put("uid", user.getId());
				System.out.println("用户： " + user.getId() + "被加入");
				return true;
			} else {
				System.out.println("用户不存在");
			}
		}
		return false;
	}

}
