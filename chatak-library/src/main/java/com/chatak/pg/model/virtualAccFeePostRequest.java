package com.chatak.pg.model;

import java.io.Serializable;

public class virtualAccFeePostRequest implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = -1653341910488212174L;
  
  private Long totalTxnAmount;
  private Long merchantAmount;
  private Long merchantFee;
  private Long rapidFee;
  private String caTransactionId;
  private String agentId;
  private String partnerId;
  private String mode;
  private String programManagerId;
  private String specificAccountNumber;
  public Long getTotalTxnAmount() {
    return totalTxnAmount;
  }
  public void setTotalTxnAmount(Long totalTxnAmount) {
    this.totalTxnAmount = totalTxnAmount;
  }
  public Long getMerchantAmount() {
    return merchantAmount;
  }
  public void setMerchantAmount(Long merchantAmount) {
    this.merchantAmount = merchantAmount;
  }
  public Long getMerchantFee() {
    return merchantFee;
  }
  public void setMerchantFee(Long merchantFee) {
    this.merchantFee = merchantFee;
  }
  public Long getRapidFee() {
    return rapidFee;
  }
  public void setRapidFee(Long rapidFee) {
    this.rapidFee = rapidFee;
  }
  public String getCaTransactionId() {
    return caTransactionId;
  }
  public void setCaTransactionId(String caTransactionId) {
    this.caTransactionId = caTransactionId;
  }
  public String getAgentId() {
    return agentId;
  }
  public void setAgentId(String agentId) {
    this.agentId = agentId;
  }
  public String getPartnerId() {
    return partnerId;
  }
  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }
  public String getMode() {
    return mode;
  }
  public void setMode(String mode) {
    this.mode = mode;
  }
  public String getProgramManagerId() {
    return programManagerId;
  }
  public void setProgramManagerId(String programManagerId) {
    this.programManagerId = programManagerId;
  }
  /**
   * @return the specificAccountNumber
   */
  public String getSpecificAccountNumber() {
    return specificAccountNumber;
  }
  /**
   * @param specificAccountNumber the specificAccountNumber to set
   */
  public void setSpecificAccountNumber(String specificAccountNumber) {
    this.specificAccountNumber = specificAccountNumber;
  }
  
}
