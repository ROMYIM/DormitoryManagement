package com.dormitory.util;

import org.hibernate.LazyInitializationException;
import org.hibernate.hql.internal.ast.QuerySyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.dormitory.entity.ResponseResult;

@ControllerAdvice
public class ExceptionHandle {
	
	private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

	public ExceptionHandle() {
		// TODO Auto-generated constructor stub
	}
	
	@ModelAttribute("result")
	public ResponseResult getResult() {
		return new ResponseResult();
	}
	
	@ExceptionHandler(NullPointerException.class)
	public void queryExeception(NullPointerException exception, @ModelAttribute("result") ResponseResult result) {
		logger.error(exception.getMessage());
		System.out.println(exception.getClass().getSimpleName() + ":" + exception.getMessage());
	}
	
	@ExceptionHandler(ClassCastException.class)
	public void classCastException(ClassCastException exception, @ModelAttribute("result") ResponseResult result) {
		logger.error(exception.getMessage());
		System.out.println(exception.getClass().getSimpleName() + ":" + exception.getMessage());
	}
	
	@ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
	public void httpSessionException(Exception exception, @ModelAttribute("result") ResponseResult result) {
		logger.error(exception.getMessage());
		System.out.println(exception.getClass().getSimpleName() + ":" + exception.getMessage());
	}
	
	@ExceptionHandler(value = {LazyInitializationException.class, QuerySyntaxException.class})
	public void hibernateException(Exception exception, @ModelAttribute("result") ResponseResult result) {
		logger.error(exception.getMessage());
		System.out.println(exception.getClass().getSimpleName() + ":" + exception.getMessage());
	}
	
	
	
	
	
//	@ExceptionHandler(Exception.class)
//	public String unknownException(Exception exception) {
//		logger.error(exception.getMessage());
//		System.out.println(exception.getMessage());
//		return gson.toJson(result.setResult(ResultType.ERR_UNKONOWN));
//	}

}
