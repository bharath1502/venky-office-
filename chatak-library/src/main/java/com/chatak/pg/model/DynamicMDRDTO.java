package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class DynamicMDRDTO extends SearchRequest {

  /**
   * 
   */
  private static final long serialVersionUID = 4940801604264852245L;
  
  private Long id;
  
  private Long binNumber;
  
  private String paymentSchemeName;
  
  private String productType;
  
  private String accountType;
  
  private String transactionType;
  
  private String bankName;
  
  private Integer paymentId;
  
  private Integer bankId;
  
  private String slab;
  

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public Long getBinNumber() {
    return binNumber;
  }

  public void setBinNumber(Long binNumber) {
    this.binNumber = binNumber;
  }

  public String getPaymentSchemeName() {
    return paymentSchemeName;
  }

  public void setPaymentSchemeName(String paymentSchemeName) {
    this.paymentSchemeName = paymentSchemeName;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public Integer getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(Integer paymentId) {
    this.paymentId = paymentId;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public String getSlab() {
    return slab;
  }

  public void setSlab(String slab) {
    this.slab = slab;
  }

  public Integer getBankId() {
    return bankId;
  }

  public void setBankId(Integer bankId) {
    this.bankId = bankId;
  }

}
