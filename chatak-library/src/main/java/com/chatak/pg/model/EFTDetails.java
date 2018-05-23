package com.chatak.pg.model;

import java.io.Serializable;

public class EFTDetails implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 8589010567272192331L;

  private String merchantCode;
  
  private Long tranferAmountDebit;
  
  private Long tranferAmountCredit;
  
  private String toDate;
  
  private String fromDate;
  
  private String currency;
  
  private String companyName;
  

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public Long getTranferAmountDebit() {
    return tranferAmountDebit;
  }

  public void setTranferAmountDebit(Long tranferAmountDebit) {
    this.tranferAmountDebit = tranferAmountDebit;
  }

  public Long getTranferAmountCredit() {
    return tranferAmountCredit;
  }

  public void setTranferAmountCredit(Long tranferAmountCredit) {
    this.tranferAmountCredit = tranferAmountCredit;
  }

  public String getToDate() {
    return toDate;
  }

  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

  public String getFromDate() {
    return fromDate;
  }

  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  /**
   * @return the companyName
   */
  public String getCompanyName() {
    return companyName;
  }

  /**
   * @param companyName the companyName to set
   */
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
  
  
}
