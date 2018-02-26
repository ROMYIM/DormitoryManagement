package com.dormitory.util.converter;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import com.dormitory.util.DateUtil;

public class DateConverter implements Converter<String, Date> {

	public DateConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Date convert(String arg0) {
		// TODO Auto-generated method stub
		return DateUtil.stringToDate(arg0, "yyyy-MM-dd hh:mm:ss");
	}

}
