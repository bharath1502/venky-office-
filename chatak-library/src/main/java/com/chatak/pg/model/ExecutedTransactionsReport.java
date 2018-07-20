package com.chatak.pg.model;

import java.sql.Timestamp;

public class ExecutedTransactionsReport {
  private Timestamp dateTime;
  private String userName;
  private String companyName;
  private Long accountNumber;
  private String accountType;
  private String transactionId;
  private String cardType;
  private String amount;
  private String paymentMethod;
  private String description;
  private String currency;
  
  public Timestamp getDateTime() {
    return dateTime;
  }
  public void setDateTime(Timestamp dateTime) {
    this.dateTime = dateTime;
  }
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getCompanyName() {
    return companyName;
  }
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
  public String getAccountType() {
    return accountType;
  }
  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }
  public String getTransactionId() {
    return transactionId;
  }
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }
  public String getCardType() {
    return cardType;
  }
  public void setCardType(String cardType) {
    this.cardType = cardType;
  }
  public String getPaymentMethod() {
    return paymentMethod;
  }
  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getCurrency() {
    return currency;
  }
  public void setCurrency(String currency) {
    this.currency = currency;
  }
  public Long getAccountNumber() {
    return accountNumber;
  }
  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }
  public String getAmount() {
    return amount;
  }
  public void setAmount(String amount) {
    this.amount = amount;
  }

}
