package com.chatak.pay.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 25-Mar-2015 2:47:34 PM
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TokenizeResponse extends Response {

  /**
   * 
   */

  private static final long serialVersionUID = -210694792449506999L;

  private String versionNum;

  private String paymentToken;

  private String tokenExpDate;

  private String tokenAssuranceLevel;

  /**
   * @return the versionNum
   */
  public String getVersionNum() {
    return versionNum;
  }

  /**
   * @param versionNum
   *          the versionNum to set
   */
  public void setVersionNum(String versionNum) {
    this.versionNum = versionNum;
  }

  /**
   * @return the paymentToken
   */
  public String getPaymentToken() {
    return paymentToken;
  }

  /**
   * @param paymentToken
   *          the paymentToken to set
   */
  public void setPaymentToken(String paymentToken) {
    this.paymentToken = paymentToken;
  }

  /**
   * @return the tokenExpDate
   */
  public String getTokenExpDate() {
    return tokenExpDate;
  }

  /**
   * @param tokenExpDate
   *          the tokenExpDate to set
   */
  public void setTokenExpDate(String tokenExpDate) {
    this.tokenExpDate = tokenExpDate;
  }

  /**
   * @return the tokenAssuranceLevel
   */
  public String getTokenAssuranceLevel() {
    return tokenAssuranceLevel;
  }

  /**
   * @param tokenAssuranceLevel
   *          the tokenAssuranceLevel to set
   */
  public void setTokenAssuranceLevel(String tokenAssuranceLevel) {
    this.tokenAssuranceLevel = tokenAssuranceLevel;
  }

}
