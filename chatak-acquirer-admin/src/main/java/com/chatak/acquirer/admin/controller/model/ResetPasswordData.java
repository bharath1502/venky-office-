package com.chatak.acquirer.admin.controller.model;

import java.io.Serializable;

public class ResetPasswordData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1748514965506147782L;
	

	private String newPassword;
	
	private String confirmPassword;

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	

}
