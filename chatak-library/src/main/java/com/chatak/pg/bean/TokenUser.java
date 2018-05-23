package com.chatak.pg.bean;

import java.io.Serializable;

public class TokenUser implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -3577489824287386929L;
  
  private String userId;
  private String password;
  private String email;
  private String oldPassword;
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getOldPassword() {
    return oldPassword;
  }
  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }
  
  

}
