package com.dormitory.entity;

import java.io.Serializable;

import com.dormitory.constant.ResultType;

public class ResponseResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int code;
	private Object result;
	private String message;

	public ResponseResult() {
		// TODO Auto-generated constructor stub
	}
	
	public  ResponseResult(ResultType responseResult) {
		code = responseResult.getCode();
		result = responseResult.getResult();
		message = responseResult.getMessage();
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setResult(Object result) {
		this.result = result;
	}
	
	public Object getResult() {
		return result;
	}
	
	public ResponseResult setResult(ResultType responseResult) {
		if (responseResult == null) {
			this.result = null;
			return this;
		}
		code = responseResult.getCode();
		result = responseResult.getResult();
		message = responseResult.getMessage();
		return this;
	}

}
