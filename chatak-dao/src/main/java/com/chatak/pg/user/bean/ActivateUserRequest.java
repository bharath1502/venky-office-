package com.chatak.pg.user.bean;

import com.chatak.pg.util.CommonUtil;

public class ActivateUserRequest extends Request{
	
	private Long profile_id;
	private String email;
	private String password;

	public String validate(){
		String message = "";
		if(CommonUtil.isNullAndEmpty(email)){
			message="email is the Required field";
		} else if(CommonUtil.isNullAndEmpty(password)){
			message="password is the Required field";
		} else if(profile_id == null){
			message= "profile_id is the Required field";
		} 
		return message;
	}

	/**
	 * @return the profile_id
	 */
	public Long getProfile_id() {
		return profile_id;
	}

	/**
	 * @param profile_id the profile_id to set
	 */
	public void setProfile_id(Long profile_id) {
		this.profile_id = profile_id;
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
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
