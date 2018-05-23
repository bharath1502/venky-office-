package com.chatak.pg.bean;

import java.io.Serializable;

public class CardTokenData extends CardToken implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1625819755421904722L;
  
  
  private String userId;
  
  private String password;

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
  
  
}
