/**
 * 
 */
package com.chatak.pg.user.bean;

/**
 * @Author: Girmiti Software
 * @Date: Aug 3, 2016
 * @Time: 9:06:59 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class UpdateResellerResponse extends Response {

	
	private static final long serialVersionUID = -5446823902051979352L;
	
	
	private boolean isUpdated;
	private String password;
	
	/**
	 * @return the isUpdated
	 */
	public boolean isUpdated() {
		return isUpdated;
	}
	/**
	 * @param isUpdated the isUpdated to set
	 */
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
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
