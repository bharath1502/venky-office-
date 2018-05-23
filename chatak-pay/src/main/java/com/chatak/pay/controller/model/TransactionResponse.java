/**
 * 
 */
package com.chatak.pay.controller.model;

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
public class TransactionResponse extends Response {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String txnRefNumber;

  private String authId;

  private PaymentDetails paymentDetails;

  private String cgRefNumber;

  private String merchantCode;

  private Long totalAmount;

  private Double totalTxnAmount;

  private String merchantName;

  private String customerBalance;

  private String currency;

  private String txnId;

  private String procTxnId;

  private String merchantId;

  private String terminalId;
  
  private String deviceLocalTxnTime;

  public String getTxnRefNumber() {
    return txnRefNumber;
  }

  public void setTxnRefNumber(String txnRefNumber) {
    this.txnRefNumber = txnRefNumber;
  }

  public String getAuthId() {
    return authId;
  }

  public void setAuthId(String authId) {
    this.authId = authId;
  }

  public PaymentDetails getPaymentDetails() {
    return paymentDetails;
  }

  public void setPaymentDetails(PaymentDetails paymentDetails) {
    this.paymentDetails = paymentDetails;
  }

  public String getCgRefNumber() {
    return cgRefNumber;
  }

  public void setCgRefNumber(String cgRefNumber) {
    this.cgRefNumber = cgRefNumber;
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public Long getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Long totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Double getTotalTxnAmount() {
    return totalTxnAmount;
  }

  public void setTotalTxnAmount(Double totalTxnAmount) {
    this.totalTxnAmount = totalTxnAmount;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public String getCustomerBalance() {
    return customerBalance;
  }

  public void setCustomerBalance(String customerBalance) {
    this.customerBalance = customerBalance;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getTxnId() {
    return txnId;
  }

  public void setTxnId(String txnId) {
    this.txnId = txnId;
  }

  public String getProcTxnId() {
    return procTxnId;
  }

  public void setProcTxnId(String procTxnId) {
    this.procTxnId = procTxnId;
  }

  public String getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  public String getTerminalId() {
    return terminalId;
  }

  public void setTerminalId(String terminalId) {
    this.terminalId = terminalId;
  }

  public String getDeviceLocalTxnTime() {
    return deviceLocalTxnTime;
  }

  public void setDeviceLocalTxnTime(String deviceLocalTxnTime) {
    this.deviceLocalTxnTime = deviceLocalTxnTime;
  }

}
