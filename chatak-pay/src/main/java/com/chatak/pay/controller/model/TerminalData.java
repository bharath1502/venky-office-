package com.chatak.pay.controller.model;

public class TerminalData {

  private Long merchantId;
  
  private Long terminalCode;
  
  private String validFromDate;

  private String validToDate;
  
  private String status;
  
  private Long id;
  
  private Integer pageIndex;

  private Integer pageSize;
  
  private Integer noOfRecords;

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
   * @return the pageIndex
   */
  public Integer getPageIndex() {
    return pageIndex;
  }

  /**
   * @param pageIndex the pageIndex to set
   */
  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
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
   * @return the noOfRecords
   */
  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  /**
   * @param noOfRecords the noOfRecords to set
   */
  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
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
   * @return the pageSize
   */
  public Integer getPageSize() {
    return pageSize;
  }

  /**
   * @param pageSize the pageSize to set
   */
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

}
