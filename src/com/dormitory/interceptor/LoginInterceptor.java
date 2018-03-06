package com.dormitory.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dormitory.constant.ResultType;
import com.dormitory.entity.ResponseResult;
import com.google.gson.Gson;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	public LoginInterceptor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("LoginInterceptor");
		String path = request.getServletPath();
		if (path.equals("/")) {
			return true;
		}
		if (path.indexOf("login") >= 0) {
			return true;
		} 
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			return true;
		}
		if (path.indexOf("app") >= 0) {
			Gson gson = new Gson();
			response.setContentType("application/json;charset=utf-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.print(gson.toJson(new ResponseResult(ResultType.ERR_LOGIN)));
			printWriter.flush();
			printWriter.close();
		} else {
			response.sendRedirect("");
		}
		return false;
	}
	
	

}
