package com.chatak.pay.controller.model;

public class PaymentResponse extends SaleResponse {

  /**
   * 
   */
  private static final long serialVersionUID = 1855466143534365750L;

  private String token;

  private String last4;
  
  private String cType;

  /**
   * @return the token
   */
  public String getToken() {
    return token;
  }

  /**
   * @param token
   *          the token to set
   */
  public void setToken(String token) {
    this.token = token;
  }

  /**
   * @return the last4
   */
  public String getLast4() {
    return last4;
  }

  /**
   * @param last4
   *          the last4 to set
   */
  public void setLast4(String last4) {
    this.last4 = last4;
  }

  /**
   * @return the cType
   */
  public String getcType() {
    return cType;
  }

  /**
   * @param cType the cType to set
   */
  public void setcType(String cType) {
    this.cType = cType;
  }

}
