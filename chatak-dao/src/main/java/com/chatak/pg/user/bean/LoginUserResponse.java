/**
 * 
 */
package com.chatak.pg.user.bean;


public class LoginUserResponse extends Response {

	private static final long serialVersionUID = 1533691811684673764L;
	
	private UserInfo userInfo;
	
	private String token;

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

	/**
	 * @return the userInfo
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo the userInfo to set
	 */
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	
	
}
