package com.chatak.pg.model;


public class EFTRefTxnData {
  
  private String merchantCode;
  private String dateTime;
  private String txnAmount;
  private String maskedCardNumber;
  private String transactionId;
  public String getMerchantCode() {
    return merchantCode;
  }
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }
  public String getDateTime() {
    return dateTime;
  }
  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }
  public String getTxnAmount() {
    return txnAmount;
  }
  public void setTxnAmount(String txnAmount) {
    this.txnAmount = txnAmount;
  }
  public String getMaskedCardNumber() {
    return maskedCardNumber;
  }
  public void setMaskedCardNumber(String maskedCardNumber) {
    this.maskedCardNumber = maskedCardNumber;
  }
  public String getTransactionId() {
    return transactionId;
  }
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }
   

}
