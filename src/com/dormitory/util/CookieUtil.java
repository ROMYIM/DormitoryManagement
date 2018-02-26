package com.dormitory.util;

import javax.servlet.http.Cookie;

public class CookieUtil {

	public CookieUtil() {
		// TODO Auto-generated constructor stub
	}

	public static Cookie setCookie(String key, String value, int age) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(age);
		return cookie;
	}
	
	public static Cookie setCookie(String key, String value, int age, String doMain, String path, int version) {
		Cookie cookie = new Cookie(key, value);
		cookie.setDomain(doMain);
		cookie.setMaxAge(age);
		cookie.setPath(path);
		cookie.setVersion(version);
		return cookie;
	}
	
}
