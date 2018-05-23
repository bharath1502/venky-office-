package com.chatak.pg.bean;

import java.io.Serializable;

public class BalanceEnquiryResponse extends Response implements Serializable {

  private static final long serialVersionUID = 1L;

  private String authId;

  private String txnRefNum;

  private String balance;// field 54

  private String txnId;

  private String procTxnId;

  private String merchantId;

  private String terminalId;

  private String currency;

  public String getAuthId() {
    return authId;
  }
  
  public void setProcTxnId(String procTxnId) {
    this.procTxnId = procTxnId;
  }

  public void setAuthId(String authId) {
    this.authId = authId;
  }

  public String getTxnRefNum() {
    return txnRefNum;
  }

  public void setTxnRefNum(String txnRefNum) {
    this.txnRefNum = txnRefNum;
  }

  public String getBalance() {
    return balance;
  }
  
  public String getTxnId() {
    return txnId;
  }

  public void setBalance(String balance) {
    this.balance = balance;
  }

  public void setTxnId(String txnId) {
    this.txnId = txnId;
  }

  public String getProcTxnId() {
    return procTxnId;
  }

  public String getMerchantId() {
    return merchantId;
  }

  public void setTerminalId(String terminalId) {
    this.terminalId = terminalId;
  }

  public String getCurrency() {
    return currency;
  }
  
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }
  
  public String getTerminalId() {
    return terminalId;
  }

}
