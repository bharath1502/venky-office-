/**
 * 
 */
package com.chatak.acquirer.admin.controller.model;

import java.io.Serializable;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 03-Jan-2015 10:49:43 AM
 * @version 1.0
 */
public class LoginDetails implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 2008207112284994849L;

  private String acqU;

  private String acqP;

  private String jSession;
  
  private String loginIpAddress;
  
  private String currentLoginTime;

  public String getCurrentLoginTime() {
    return currentLoginTime;
  }

  public void setCurrentLoginTime(String currentLoginTime) {
    this.currentLoginTime = currentLoginTime;
  }
  
  /**
   * @param acqU
   *          the acqU to set
   */
  public void setAcqU(String acqU) {
    this.acqU = acqU;
  }

  /**
   * @return the acqP
   */
  public String getAcqP() {
    return acqP;
  }

  /**
   * @return the acqU
   */
  public String getAcqU() {
    return acqU;
  }

  /**
   * @param acqP
   *          the acqP to set
   */
  public void setAcqP(String acqP) {
    this.acqP = acqP;
  }
  
  public String getLoginIpAddress() {
	return loginIpAddress;
  }
	
 public void setLoginIpAddress(String loginIpAddress) {
	this.loginIpAddress = loginIpAddress;
 }

  /**
   * @return the jSession
   */
  public String getjSession() {
    return jSession;
  }

  /**
   * @param jSession
   *          the jSession to set
   */
  public void setjSession(String jSession) {
    this.jSession = jSession;
  }

}
