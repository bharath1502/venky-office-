package com.chatak.pg.bean;

import java.util.List;

public class DCCMarkup extends SearchRequest{

  /**
   * 
   */
  private static final long serialVersionUID = -5927388457547463099L;
  
  private Long id;

  private String merchantName;
  
  private String merchantCode;
  
  private String currencyCode;
  
  private String baseCurrency;
  
  private String transactionCurrency;
  
  private Double markUpFee;
  
  private Integer status;
  
  private List<DCCCurrency> dccCurrency;
  
  
  /**
   * @return the id
   */
  @Override
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  @Override
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the merchantName
   */
  public String getMerchantName() {
    return merchantName;
  }

  /**
   * @param merchantName the merchantName to set
   */
  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  /**
   * @return the merchantCode
   */
  public String getMerchantCode() {
    return merchantCode;
  }

  /**
   * @param merchantCode the merchantCode to set
   */
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }
  
  /**
   * @param markUpFee the markUpFee to set
   */
  public void setMarkUpFee(Double markUpFee) {
    this.markUpFee = markUpFee;
  }

  /**
   * @return the baseCurrency
   */
  public String getBaseCurrency() {
    return baseCurrency;
  }

  /**
   * @return the transactionCurrency
   */
  public String getTransactionCurrency() {
    return transactionCurrency;
  }

  /**
   * @return the markUpFee
   */
  public Double getMarkUpFee() {
    return markUpFee;
  }

  /**
   * @param baseCurrency the baseCurrency to set
   */
  public void setBaseCurrency(String baseCurrency) {
    this.baseCurrency = baseCurrency;
  }

  /**
   * @return the currencyCode
   */
  public String getCurrencyCode() {
    return currencyCode;
  }

  /**
   * @param currencyCode the currencyCode to set
   */
  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  /**
   * @return the status
   */
  public Integer getStatus() {
    return status;
  }
  
  /**
   * @param transactionCurrency the transactionCurrency to set
   */
  public void setTransactionCurrency(String transactionCurrency) {
    this.transactionCurrency = transactionCurrency;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * @return the dccCurrency
   */
  public List<DCCCurrency> getDccCurrency() {
    return dccCurrency;
  }

  /**
   * @param dccCurrency the dccCurrency to set
   */
  public void setDccCurrency(List<DCCCurrency> dccCurrency) {
    this.dccCurrency = dccCurrency;
  }
  
}
