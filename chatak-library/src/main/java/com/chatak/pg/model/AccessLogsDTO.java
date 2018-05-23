package com.chatak.pg.model;

import java.io.Serializable;

public class AccessLogsDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 2884424532567637252L;
  
  private String userName;
  private String ipAddress;
  private String dateTime;
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getIpAddress() {
    return ipAddress;
  }
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }
  public String getDateTime() {
    return dateTime;
  }
  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }
  
  

}
