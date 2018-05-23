package com.chatak.pg.user.bean;

import com.chatak.pg.util.CommonUtil;

public class LoginUserRequest extends Request{
	

	private String user_name;
	private String password;
	
	public String validate(){
		String message = "";
		if(CommonUtil.isNullAndEmpty(user_name)){
			message="User Name is the Required field";
		} else if(CommonUtil.isNullAndEmpty(password)){
			message= "Password is the Required field";
		} 
		return message;
	}
	
	/**
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}
	
	/**
	 * @param user_name the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
