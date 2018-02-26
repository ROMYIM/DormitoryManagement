package com.dormitory.constant;

public enum ViolationType {
	VANDALISM(3), LATE_BACK(1), ABUSE(3), CLIMB_OVER(4);
	
	private int value;
	
	private ViolationType(int value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
