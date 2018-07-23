package com.chatak.pg.bean;

import java.io.Serializable;

public class CashBackResponse extends Response implements Serializable {

  private static final long serialVersionUID = 1L;

  private String authId;

  private String txnRefNum;

  private Long txnAmount;

  private Long feeAmount;

  private Long totalAmount;

  private String issuerTxnRefNum;

  /**
   * @return the authId
   */
  public String getAuthId() {
    return authId;
  }
  
  public void setIssuerTxnRefNum(String issuerTxnRefNum) {
    this.issuerTxnRefNum = issuerTxnRefNum;
  }
  
  /**
   * @return the txnRefNum
   */
  public String getTxnRefNum() {
    return txnRefNum;
  }
  
  public String getIssuerTxnRefNum() {
    return issuerTxnRefNum;
  }

  /**
   * @param authId
   *          the authId to set
   */
  public void setAuthId(String authId) {
    this.authId = authId;
  }
  
  /**
   * @return the totalAmount
   */
  public Long getTotalAmount() {
    return totalAmount;
  }

  /**
   * @return the txnAmount
   */
  public Long getTxnAmount() {
    return txnAmount;
  }

  /**
   * @param txnRefNum
   *          the txnRefNum to set
   */
  public void setTxnRefNum(String txnRefNum) {
    this.txnRefNum = txnRefNum;
  }
  
  /**
   * @return the feeAmount
   */
  public Long getFeeAmount() {
    return feeAmount;
  }

  /**
   * @param txnAmount
   *          the txnAmount to set
   */
  public void setTxnAmount(Long txnAmount) {
    this.txnAmount = txnAmount;
  }
  
  /**
   * @param feeAmount
   *          the feeAmount to set
   */
  public void setFeeAmount(Long feeAmount) {
    this.feeAmount = feeAmount;
  }

  /**
   * @param totalAmount
   *          the totalAmount to set
   */
  public void setTotalAmount(Long totalAmount) {
    this.totalAmount = totalAmount;
  }

 

  

}
