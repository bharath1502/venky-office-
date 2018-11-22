/**
 * 
 */
package com.chatak.pg.user.bean;

import java.io.Serializable;

/**
 * @Author: Girmiti Software
 * @Date: Sep 20, 2017
 * @Time: 3:35:02 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class DailyFundingReport implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String date;
  
  private String batchId;
  
  private String parentMerchantCode;

  private String parentBusinessName;
  
  private String merchantCode;

  private String businessName;
  
  private String currency;
  
  private String bankAccountNumber;
  
  private String bankRoutingNumber;

  private Double totalAmount;

  private Double txnAmount;

  private Double feeAmount;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getParentMerchantCode() {
    return parentMerchantCode;
  }

  public void setParentMerchantCode(String parentMerchantCode) {
    this.parentMerchantCode = parentMerchantCode;
  }

  public String getParentBusinessName() {
    return parentBusinessName;
  }

  public void setParentBusinessName(String parentBusinessName) {
    this.parentBusinessName = parentBusinessName;
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public String getBusinessName() {
    return businessName;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public Double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Double getTxnAmount() {
    return txnAmount;
  }

  public void setTxnAmount(Double txnAmount) {
    this.txnAmount = txnAmount;
  }

  public Double getFeeAmount() {
    return feeAmount;
  }

  public void setFeeAmount(Double feeAmount) {
    this.feeAmount = feeAmount;
  }

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getBankAccountNumber() {
    return bankAccountNumber;
  }

  public void setBankAccountNumber(String bankAccountNumber) {
    this.bankAccountNumber = bankAccountNumber;
  }

  public String getBankRoutingNumber() {
    return bankRoutingNumber;
  }

  public void setBankRoutingNumber(String bankRoutingNumber) {
    this.bankRoutingNumber = bankRoutingNumber;
  }

}
