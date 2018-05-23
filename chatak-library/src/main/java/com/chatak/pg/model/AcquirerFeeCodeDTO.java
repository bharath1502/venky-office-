package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class AcquirerFeeCodeDTO extends SearchRequest {

  /**
   * 
   */
  private static final long serialVersionUID = 1791111304905968069L;

  private Long acquirerFeeCodeId;

  private Long accountNumber;

  private Double flatFee;

  private String entityId;

  private Double percentageOfTxn;

  private String acquirerName;

  private Long partnerId;

  private String merchantCode;

  public Long getAcquirerFeeCodeId() {
    return acquirerFeeCodeId;
  }

  public void setAcquirerFeeCodeId(Long acquirerFeeCodeId) {
    this.acquirerFeeCodeId = acquirerFeeCodeId;
  }

  public Long getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public String getAcquirerName() {
    return acquirerName;
  }

  public void setAcquirerName(String acquirerName) {
    this.acquirerName = acquirerName;
  }

  public Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  public Double getFlatFee() {
    return flatFee;
  }

  public void setFlatFee(Double flatFee) {
    this.flatFee = flatFee;
  }

  public Double getPercentageOfTxn() {
    return percentageOfTxn;
  }

  public void setPercentageOfTxn(Double percentageOfTxn) {
    this.percentageOfTxn = percentageOfTxn;
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

}