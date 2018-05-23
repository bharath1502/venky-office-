package com.chatak.pg.bean;


public class ChangePasswordRequest extends SearchRequest{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8069049770609300792L;
	
	private String userName;
	
	private String currentPassword;
	
	private String password;
	
	private String confirmPassword;
	
	private String securityQuestion;
	
	private String securityAnswer;
	
	private String newPassword;
	
	private Long userId;
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public final String getCurrentPassword() {
		return currentPassword;
	}

	public final void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public final String getSecurityQuestion() {
		return securityQuestion;
	}

	public final void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public final String getSecurityAnswer() {
		return securityAnswer;
	}

	public final void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

}
