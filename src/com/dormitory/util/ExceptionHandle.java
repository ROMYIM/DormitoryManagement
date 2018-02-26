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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@ControllerAdvice
@ResponseBody
public class ExceptionHandle {
	
	private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static ResponseResult result = new ResponseResult();

	public ExceptionHandle() {
		// TODO Auto-generated constructor stub
	}
	
	@ModelAttribute("gson")
	public Gson getGson() {
		return new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();
	}
	
	@ModelAttribute("result")
	public ResponseResult getResult() {
		return new ResponseResult();
	}
	
	@ExceptionHandler(NullPointerException.class)
	public String queryExeception(NullPointerException exception) {
		logger.error(exception.getMessage());
		System.out.println(exception.getClass().getSimpleName() + ":" + exception.getMessage());
		return gson.toJson(result.setResult(ResultType.ERR_QUERY));
	}
	
	@ExceptionHandler(ClassCastException.class)
	public String classCastException(ClassCastException exception) {
		logger.error(exception.getMessage());
		System.out.println(exception.getClass().getSimpleName() + ":" + exception.getMessage());
		return gson.toJson(result.setResult(ResultType.ERR_UNKONOWN));
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public String attributeException(IllegalArgumentException exception) {
		logger.error(exception.getMessage());
		System.out.println(exception.getClass().getSimpleName() + ":" + exception.getMessage());
		return gson.toJson(result.setResult(ResultType.ERR_UNKONOWN));
	}
	
	@ExceptionHandler(LazyInitializationException.class)
	public String hibernateLazyException(LazyInitializationException exception) {
		logger.error(exception.getMessage());
		System.out.println(exception.getClass().getSimpleName() + ":" + exception.getMessage());
		return gson.toJson(result.setResult(ResultType.ERR_UNKONOWN));
	}
	
	@ExceptionHandler(QuerySyntaxException.class)
	public String hqlException(QuerySyntaxException exception) {
		logger.error(exception.getMessage());
		System.out.println(exception.getClass().getSimpleName() + ":" + exception.getMessage());
		return gson.toJson(result.setResult(ResultType.ERR_UNKONOWN));
	}
	
	
//	@ExceptionHandler(Exception.class)
//	public String unknownException(Exception exception) {
//		logger.error(exception.getMessage());
//		System.out.println(exception.getMessage());
//		return gson.toJson(result.setResult(ResultType.ERR_UNKONOWN));
//	}

}
