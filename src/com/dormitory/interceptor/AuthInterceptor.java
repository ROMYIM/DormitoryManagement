package com.dormitory.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dormitory.constant.ResultType;
import com.dormitory.entity.ResponseResult;
import com.dormitory.entity.User;
import com.google.gson.Gson;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	public AuthInterceptor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("AuthInterceptor");
		String path = request.getServletPath();
		if (path.indexOf("/") >= 0) {
			return true;
		}
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		switch (user.getAuthentication()) {
		case STUDENT:
			if (path.indexOf("student") >= 0) {
				return true;
			}
			break;
		case DORMITORY_ADMINISTRATOR:
			if (path.indexOf("dorAdmin") >= 0) {
				return true;
			}
			break;
		case ADMINISTRATOR:
			if (path.indexOf("admin") >= 0) {
				return true;
			}
			break;
		default:
			break;
		}
		response.setContentType("application/json;charset=utf-8");
		Gson gson = new Gson();
		PrintWriter writer = response.getWriter();
		writer.print(gson.toJson(new ResponseResult(ResultType.ERR_AUTH)));
		writer.flush();
		writer.close();
		return false;
	}

}
