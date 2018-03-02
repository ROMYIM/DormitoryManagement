package com.dormitory.util;

import org.hibernate.LazyInitializationException;
import org.hibernate.hql.internal.ast.QuerySyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dormitory.constant.ResultType;
import com.dormitory.entity.ResponseResult;

@ControllerAdvice
@ResponseBody
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
	public ResponseResult queryExeception(NullPointerException exception, @ModelAttribute("result") ResponseResult result) {
		logger.error(exception.getMessage());
		System.out.println(exception.getClass().getSimpleName() + ":" + exception.getMessage());
		return result.setResult(ResultType.ERR_QUERY);
	}
	
	@ExceptionHandler(ClassCastException.class)
	public ResponseResult classCastException(ClassCastException exception, @ModelAttribute("result") ResponseResult result) {
		logger.error(exception.getMessage());
		System.out.println(exception.getClass().getSimpleName() + ":" + exception.getMessage());
		return result.setResult(ResultType.ERR_UNKONOWN);
	}
	
	@ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
	public ResponseResult httpSessionException(Exception exception, @ModelAttribute("result") ResponseResult result) {
		logger.error(exception.getMessage());
		System.out.println(exception.getClass().getSimpleName() + ":" + exception.getMessage());
		return result.setResult(ResultType.ERR_UNKONOWN);
	}
	
	@ExceptionHandler(value = {LazyInitializationException.class, QuerySyntaxException.class})
	public ResponseResult hibernateException(Exception exception, @ModelAttribute("result") ResponseResult result) {
		logger.error(exception.getMessage());
		System.out.println(exception.getClass().getSimpleName() + ":" + exception.getMessage());
		return result.setResult(ResultType.ERR_UNKONOWN);
	}
	
	
	
	
	
//	@ExceptionHandler(Exception.class)
//	public String unknownException(Exception exception) {
//		logger.error(exception.getMessage());
//		System.out.println(exception.getMessage());
//		return gson.toJson(result.setResult(ResultType.ERR_UNKONOWN));
//	}

}
