package com.dormitory.constant;

public enum Gender {
	MALE("��"), FEMALE("Ů");
	
	private final String value;
	
	private Gender(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	
}
