package com.chatak.acquirer.admin.controller.model;

import java.io.Serializable;

public class ForgotPasswordRequest implements Serializable
{
  
private String userName;

private String email;

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


}
