package com.chatak.acquirer.admin.model;

public class ResellerValue extends ResellerMerchantCommon {

  private Long resellerId;

  private String resellerName;

  private String contactName;

  private String emailId;

  private String phone;

  private String zip;

  private String createdBy;

  private String updatedBy;

  private String createdDate;

  private String updatedDate;

  private String status;

  private Long accountNumber;

  private Integer pageSize;

  private Integer pageIndex;

  private Integer noOfRecords;

  private String accountBalance;

  /**
   * @return the accountBalance
   */
  public String getAccountBalance() {
    return accountBalance;
  }

  /**
   * @param accountBalance the accountBalance to set
   */
  public void setAccountBalance(String accountBalance) {
    this.accountBalance = accountBalance;
  }

  /**
   * @return the noOfRecords
   */
  @Override
  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  /**
   * @param noOfRecords the noOfRecords to set
   */
  @Override
  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }

  /**
   * @return the resellerId
   */
  public Long getResellerId() {
    return resellerId;
  }

  /**
   * @param resellerId the resellerId to set
   */
  public void setResellerId(Long resellerId) {
    this.resellerId = resellerId;
  }

  /**
   * @return the resellerName
   */
  public String getResellerName() {
    return resellerName;
  }

  /**
   * @param resellerName the resellerName to set
   */
  public void setResellerName(String resellerName) {
    this.resellerName = resellerName;
  }

  /**
   * @return the contactName
   */
  public String getContactName() {
    return contactName;
  }

  /**
   * @param contactName the contactName to set
   */
  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  /**
   * @return the emailId
   */
  public String getEmailId() {
    return emailId;
  }

  /**
   * @param emailId the emailId to set
   */
  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  /**
   * @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * @param phone the phone to set
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * @return the zip
   */
  public String getZip() {
    return zip;
  }

  /**
   * @param zip the zip to set
   */
  public void setZip(String zip) {
    this.zip = zip;
  }

  /**
   * @return the createdBy
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * @param createdBy the createdBy to set
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * @return the updatedBy
   */
  public String getUpdatedBy() {
    return updatedBy;
  }

  /**
   * @param updatedBy the updatedBy to set
   */
  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  /**
   * @return the createdDate
   */
  public String getCreatedDate() {
    return createdDate;
  }

  /**
   * @param createdDate the createdDate to set
   */
  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * @return the updatedDate
   */
  public String getUpdatedDate() {
    return updatedDate;
  }

  /**
   * @param updatedDate the updatedDate to set
   */
  public void setUpdatedDate(String updatedDate) {
    this.updatedDate = updatedDate;
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
   * @return the accountNumber
   */
  public Long getAccountNumber() {
    return accountNumber;
  }

  /**
   * @param accountNumber the accountNumber to set
   */
  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   * @return the pageSize
   */
  @Override
  public Integer getPageSize() {
    return pageSize;
  }

  /**
   * @param pageSize the pageSize to set
   */
  @Override
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * @return the pageIndex
   */
  @Override
  public Integer getPageIndex() {
    return pageIndex;
  }

  /**
   * @param pageIndex the pageIndex to set
   */
  @Override
  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

}
