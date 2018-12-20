package com.chatak.pg.model;

public class TransactionHistoryRequest {

  private static final long serialVersionUID = 1L;

  private String transactionDate;

  private String merchantCode;

  private String userName;

  private String transactionId;

  public String getTransactionDate() {
    return transactionDate;
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public String getUserName() {
    return userName;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public void setTransactionDate(String transactionDate) {
    this.transactionDate = transactionDate;
  }

}
