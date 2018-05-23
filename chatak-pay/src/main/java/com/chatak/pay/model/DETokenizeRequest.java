package com.chatak.pay.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.chatak.pay.controller.model.Request;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 20-Mar-2015 3:15:43 PM
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DETokenizeRequest extends Request{

  /**
   * 
   */
  private static final long serialVersionUID = -2656624152740694783L;

  private String versionNum;

  private String tokenRequestorID;

  private String paymentToken;

  private String tokenExpiryDate;

  public String getVersionNum() {
    return versionNum;
  }

  public void setVersionNum(String versionNum) {
    this.versionNum = versionNum;
  }

  public String getTokenRequestorID() {
    return tokenRequestorID;
  }

  public void setTokenRequestorID(String tokenRequestorID) {
    this.tokenRequestorID = tokenRequestorID;
  }

  public String getPaymentToken() {
    return paymentToken;
  }

  public void setPaymentToken(String paymentToken) {
    this.paymentToken = paymentToken;
  }

  public String getTokenExpiryDate() {
    return tokenExpiryDate;
  }

  public void setTokenExpiryDate(String tokenExpiryDate) {
    this.tokenExpiryDate = tokenExpiryDate;
  }

}
