/**
 * 
 */
package com.chatak.pg.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
  
  private String authId;
  private String txnRefNumber;
  private String cgRefNumber;
  private Double totalTxnAmount;
  private String merchantCode;
  
  /**
   * @return the txnRefNumber
   */
  public String getTxnRefNumber() {
    return txnRefNumber;
  }
  
  /**
   * @param pgTxnRefNumber the pgTxnRefNumber to set
   */
  public void setCgRefNumber(String cgRefNumber) {
    this.cgRefNumber = cgRefNumber;
  }
  
  /**
   * @return the authId
   */
  public String getAuthId() {
    return authId;
  }
  
  public String getMerchantCode() {
    return merchantCode;
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
  
  public Double getTotalTxnAmount() {
    return totalTxnAmount;
  }
  
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }
  
  public void setTotalTxnAmount(Double totalTxnAmount) {
    this.totalTxnAmount = totalTxnAmount;
  }
 

}
