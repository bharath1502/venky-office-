package com.chatak.pg.enums;

public enum ParameterType {
	
	MT("MASTER"),
	DT("DEVICE")
	;
	
	private String parameterName;

	private ParameterType(String name) {
		parameterName = name;
	}
	
	public String getParameterName() {
		return parameterName;
	}
 
}
