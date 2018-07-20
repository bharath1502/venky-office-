package com.chatak.acquirer.admin.controller.model;

import java.io.Serializable;

public class ChangePasswordRequest implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4422550967492972151L;
	
	private Long userId;
	
	private String currentPassword;
	
	private String newPassword;
	
	private String confirmPassword;
	
	private boolean isNewUser;

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @return the currentPassword
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}

	/**
	 * @param currentPassword the currentPassword to set
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	
	public void setIsNewUser(boolean isNewUser) {
		this.isNewUser = isNewUser;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public boolean getIsNewUser() {
		return isNewUser;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
}
