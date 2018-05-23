package com.chatak.pg.bean;

import java.io.Serializable;

public class GetCardTokensRequest implements Serializable{

  
  /**
   * 
   */
  private static final long serialVersionUID = -4027052298241775681L;

  private String userId;
  
  private String password;

  /**
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }

  /**
   * @param userId the userId to set
   */
  public void setUserId(String userId) {
    this.userId = userId;
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
