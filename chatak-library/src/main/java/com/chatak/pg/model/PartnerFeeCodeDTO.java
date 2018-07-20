package com.chatak.pg.model;

import java.util.List;

import com.chatak.pg.bean.SearchRequest;

public class PartnerFeeCodeDTO extends SearchRequest{

  /**
   * 
   */
  private static final long serialVersionUID = 1791111304905968069L;
  
  private Long accountNumber;
  private Long flatFee;
  private String merchantCode;
  private Long percentageOfTxn;
  private String partnerName;
  private String createdBy;
  private String updatedBy;
  private Long partnerId;
  private List<AcquirerFeeCodeDTO> acquirerFeeCodeList;
  
  public String getPartnerName() {
    return partnerName;
  }
  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }
  public Long getPercentageOfTxn() {
    return percentageOfTxn;
  }
  public void setPercentageOfTxn(Long percentageOfTxn) {
    this.percentageOfTxn = percentageOfTxn;
  }
  public Long getFlatFee() {
    return flatFee;
  }
  public void setFlatFee(Long flatFee) {
    this.flatFee = flatFee;
  }
  public String getMerchantCode() {
    return merchantCode;
  }
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }
  public Long getAccountNumber() {
    return accountNumber;
  }
  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }
  public String getCreatedBy() {
    return createdBy;
  }
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }
  public String getUpdatedBy() {
    return updatedBy;
  }
  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }
  public Long getPartnerId() {
    return partnerId;
  }
  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }
  public List<AcquirerFeeCodeDTO> getAcquirerFeeCodeList() {
    return acquirerFeeCodeList;
  }
  public void setAcquirerFeeCodeList(List<AcquirerFeeCodeDTO> acquirerFeeCodeList) {
    this.acquirerFeeCodeList = acquirerFeeCodeList;
  }
  
  

}
