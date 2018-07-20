package com.chatak.pg.user.bean;

import com.chatak.pg.util.CommonUtil;

public class AuthenticateUserRequest extends Request{

	private String token;
	
	public String validate(){
		String message = "";
		if(CommonUtil.isNullAndEmpty(token)){
			message="Invalid Token";
		}  
		return message;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
}
