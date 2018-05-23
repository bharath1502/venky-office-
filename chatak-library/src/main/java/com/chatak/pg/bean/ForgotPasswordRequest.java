package com.chatak.pg.bean;

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
 * @return the email
 */
public String getEmail() {
  return email;
}

/**
 * @param userName the userName to set
 */
public void setUserName(String userName) {
	this.userName = userName;
}

/**
 * @param email the email to set
 */
public void setEmail(String email) {
	this.email = email;
}


}
