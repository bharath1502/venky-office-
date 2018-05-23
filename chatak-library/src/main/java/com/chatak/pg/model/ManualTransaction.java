package com.chatak.pg.model;

import java.io.Serializable;

public class ManualTransaction implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 8783329526940931509L;
  
  private String accountNumber;
  private String transactionCode;
  private Long amount;
  private Long fee;
  private String description;
  private String userId;
  private String merchantCode;
  private Long currentBalance;
  private Long avaliableBalance;
  public String getAccountNumber() {
    return accountNumber;
  }
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }
  public String getTransactionCode() {
    return transactionCode;
  }
  public void setTransactionCode(String transactionCode) {
    this.transactionCode = transactionCode;
  }
  public Long getAmount() {
    return amount;
  }
  public void setAmount(Long amount) {
    this.amount = amount;
  }
  public Long getFee() {
    return fee;
  }
  public void setFee(Long fee) {
    this.fee = fee;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getMerchantCode() {
    return merchantCode;
  }
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }
  public Long getCurrentBalance() {
    return currentBalance;
  }
  public void setCurrentBalance(Long currentBalance) {
    this.currentBalance = currentBalance;
  }
  public Long getAvaliableBalance() {
    return avaliableBalance;
  }
  public void setAvaliableBalance(Long avaliableBalance) {
    this.avaliableBalance = avaliableBalance;
  }
  
}
