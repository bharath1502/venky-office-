package com.chatak.pg.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ResellerData implements Serializable {

  private static final long serialVersionUID = 7865474308177662062L;

  private String zip;
  
  private String updatedBy;
  
  private String createdBy;

  private Timestamp createdDate;
  
  private Integer status;

  private Timestamp updatedDate;
  
  private Long accountNumber;
  
  private Integer pageIndex;
  
  private Integer pageSize;
  
  private Integer noOfRecords;
  
  private Long resellerId;
  
  private String accountBalance;

  private String resellerName;
  
  private String emailId;

  private String contactName;
  
  private String phone;
  
  private String address2;

  private String address1;

  private String city;
  
  private String country;
  
  private String state;


  /**
   * @return the accountBalance
   */
  public String getAccountBalance() {
    return accountBalance;
  }
  
  /**
   * @return the noOfRecords
   */
  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  /**
   * @param accountBalance the accountBalance to set
   */
  public void setAccountBalance(String accountBalance) {
    this.accountBalance = accountBalance;
  }

  /**
   * @return the pageSize
   */
  public Integer getPageSize() {
    return pageSize;
  }
  
  /**
   * @param accountNumber the accountNumber to set
   */
  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   * @param pageSize the pageSize to set
   */
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
  
  /**
   * @param noOfRecords the noOfRecords to set
   */
  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }

  /**
   * @return the pageIndex
   */
  public Integer getPageIndex() {
    return pageIndex;
  }
  
  /**
   * @return the accountNumber
   */
  public Long getAccountNumber() {
    return accountNumber;
  }

  /**
   * @param pageIndex the pageIndex to set
   */
  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  /**
   * @return the resellerId
   */
  public Long getResellerId() {
    return resellerId;
  }

  /**
   * @param resellerName the resellerName to set
   */
  public void setResellerName(String resellerName) {
    this.resellerName = resellerName;
  }
  
  /**
   * @param resellerId the resellerId to set
   */
  public void setResellerId(Long resellerId) {
    this.resellerId = resellerId;
  }

  /**
   * @return the contactName
   */
  public String getContactName() {
    return contactName;
  }

  /**
   * @return the resellerName
   */
  public String getResellerName() {
    return resellerName;
  }

  /**
   * @param contactName the contactName to set
   */
  public void setContactName(String contactName) {
    this.contactName = contactName;
  }
  
  /**
   * @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * @return the emailId
   */
  public String getEmailId() {
    return emailId;
  }
  
  /**
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }
  
  /**
   * @param phone the phone to set
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * @param emailId the emailId to set
   */
  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  /**
   * @return the address2
   */
  public String getAddress2() {
    return address2;
  }
  
  /**
   * @param state the state to set
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }
  
  /**
   * @param address2 the address2 to set
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }
  
  /**
   * @param address1 the address1 to set
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * @return the zip
   */
  public String getZip() {
    return zip;
  }
  
  /**
   * @param createdBy the createdBy to set
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
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
   * @return the updatedBy
   */
  public String getUpdatedBy() {
    return updatedBy;
  }
  
  /**
   * @return the updatedDate
   */
  public Timestamp getUpdatedDate() {
    return updatedDate;
  }
  
  /**
   * @param country the country to set
   */
  public void setCountry(String country) {
    this.country = country;
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
  public Timestamp getCreatedDate() {
    return createdDate;
  }
  
  /**
   * @param updatedDate the updatedDate to set
   */
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  /**
   * @param createdDate the createdDate to set
   */
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * @return the status
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

}
