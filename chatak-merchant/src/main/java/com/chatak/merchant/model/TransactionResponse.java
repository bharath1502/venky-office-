/**
 * 
 */
package com.chatak.merchant.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @Author: Girmiti Software
 * @Date: Apr 23, 2015
 * @Time: 11:15:50 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionResponse extends Response{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String txnRefNumber;
  private String authId;
  private String cgRefNumber;
  private String merchantCode;
  private Double totalTxnAmount;
  /**
   * @return the txnRefNumber
   */
  public String getTxnRefNumber() {
    return txnRefNumber;
  }
  /**
   * @return the authId
   */
  public String getAuthId() {
    return authId;
  }
  /**
   * @param txnRefNumber the txnRefNumber to set
   */
  public void setTxnRefNumber(String txnRefNumber) {
    this.txnRefNumber = txnRefNumber;
  }
  /**
   * @param authId the authId to set
   */
  public void setAuthId(String authId) {
    this.authId = authId;
  }
  /**
   * @return the pgTxnRefNumber
   */
  public String getCgRefNumber() {
    return cgRefNumber;
  }
  /**
   * @param pgTxnRefNumber the pgTxnRefNumber to set
   */
  public void setCgRefNumber(String cgRefNumber) {
    this.cgRefNumber = cgRefNumber;
  }
public String getMerchantCode() {
	return merchantCode;
}
public void setMerchantCode(String merchantCode) {
	this.merchantCode = merchantCode;
}
public Double getTotalTxnAmount() {
	return totalTxnAmount;
}
public void setTotalTxnAmount(Double totalTxnAmount) {
	this.totalTxnAmount = totalTxnAmount;
}

}
