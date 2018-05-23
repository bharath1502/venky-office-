package com.chatak.merchant.model;

public class Terminals {

  private Long merchantId;
  
  private Long terminalCode;
  
  private String validFromDate;

  private String validToDate;
  
  private String status;
  
  private Long id;
  
  private Long merchantCode;

  /**
   * @return the merchantId
   */
  public Long getMerchantId() {
    return merchantId;
  }

  /**
   * @param merchantId the merchantId to set
   */
  public void setMerchantId(Long merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * @return the terminalCode
   */
  public Long getTerminalCode() {
    return terminalCode;
  }

  /**
   * @param terminalCode the terminalCode to set
   */
  public void setTerminalCode(Long terminalCode) {
    this.terminalCode = terminalCode;
  }

  /**
   * @return the validFromDate
   */
  public String getValidFromDate() {
    return validFromDate;
  }

  /**
   * @param validFromDate the validFromDate to set
   */
  public void setValidFromDate(String validFromDate) {
    this.validFromDate = validFromDate;
  }

  /**
   * @return the validToDate
   */
  public String getValidToDate() {
    return validToDate;
  }

  /**
   * @param validToDate the validToDate to set
   */
  public void setValidToDate(String validToDate) {
    this.validToDate = validToDate;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the merchantCode
   */
  public Long getMerchantCode() {
    return merchantCode;
  }

  /**
   * @param merchantCode the merchantCode to set
   */
  public void setMerchantCode(Long merchantCode) {
    this.merchantCode = merchantCode;
  }
  
}
