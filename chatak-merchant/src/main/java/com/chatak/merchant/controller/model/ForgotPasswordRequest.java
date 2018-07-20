package com.chatak.merchant.controller.model;

import java.io.Serializable;

public class ForgotPasswordRequest implements Serializable
{
  
/**
   * 
   */
  private static final long serialVersionUID = 1L;

private String userName;

private String email;

private boolean isNewUser;

/**
 * @return the userName
 */
public String getUserName() {
	return userName;
}

/**
 * @param userName the userName to set
 */
public void setUserName(String userName) {
	this.userName = userName;
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

public boolean getIsNewUser() {
	return isNewUser;
}

public void setIsNewUser(boolean isNewUser) {
	this.isNewUser = isNewUser;
}

}
