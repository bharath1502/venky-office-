package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;
import com.chatak.pg.enums.RoleLevel;

public class AdminForgotPasswordRequestDTO extends SearchRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String securityAnswer;

	private RoleLevel userType;

	/**
	 * @return the securityAnswer
	 */
	public String getSecurityAnswer() {
		return securityAnswer;
	}

	/**
	 * @return the userType
	 */
	public RoleLevel getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(RoleLevel userType) {
		this.userType = userType;
	}

	/**
	 * @param securityAnswer
	 *            the securityAnswer to set
	 */
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	
	
}
