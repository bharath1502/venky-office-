package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class Bank extends SearchRequest {

  private static final long serialVersionUID = -5946160678690959898L;

  private String bankName;
  
  private String settlRoutingNumber;
  
  private String bankCode;
  
  private String extension;
  
  private String settlAccountNumber;
  
  private String contactPersonPhone;
  
  private String contactPersonCell;
  
  private String contactPersonName;
  
  private String contactPersonFax;
  
  private String contactPersonEmail;
  
  private Long id;

  private String bankShortName;

  private String acquirerId;
  
  private String status;

  private String address1;

  private String city;
  
  private String address2;

  private String state;

  private String zip;
  
  private String country;
  
  private String loginuserType;
  
  /**
   * @return the loginuserType
   */
  public String getLoginuserType() {
    return loginuserType;
  }

  /**
   * @param loginuserType the loginuserType to set
   */
  public void setLoginuserType(String loginuserType) {
    this.loginuserType = loginuserType;
  }

  @Override
  public Long getId() {
    return id;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getBankName() {
    return bankName;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getBankShortName() {
    return bankShortName;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStatus() {
    return status;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getAcquirerId() {
    return acquirerId;
  }

  public String getAddress1() {
    return address1;
  }
  
  public String getAddress2() {
    return address2;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public void setAcquirerId(String acquirerId) {
    this.acquirerId = acquirerId;
  }

  public String getCity() {
    return city;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getState() {
    return state;
  }

  public void setBankShortName(String bankShortName) {
    this.bankShortName = bankShortName;
  }

  public String getCountry() {
    return country;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getZip() {
    return zip;
  }
  
  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getSettlRoutingNumber() {
    return settlRoutingNumber;
  }

  public void setSettlRoutingNumber(String settlRoutingNumber) {
    this.settlRoutingNumber = settlRoutingNumber;
  }

  public String getBankCode() {
    return bankCode;
  }

  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getSettlAccountNumber() {
    return settlAccountNumber;
  }

  public void setSettlAccountNumber(String settlAccountNumber) {
    this.settlAccountNumber = settlAccountNumber;
  }

  public String getContactPersonPhone() {
    return contactPersonPhone;
  }

  public void setContactPersonPhone(String contactPersonPhone) {
    this.contactPersonPhone = contactPersonPhone;
  }

  public String getContactPersonCell() {
    return contactPersonCell;
  }

  public void setContactPersonCell(String contactPersonCell) {
    this.contactPersonCell = contactPersonCell;
  }

  public String getContactPersonName() {
    return contactPersonName;
  }

  public void setContactPersonName(String contactPersonName) {
    this.contactPersonName = contactPersonName;
  }

  public String getContactPersonFax() {
    return contactPersonFax;
  }

  public void setContactPersonFax(String contactPersonFax) {
    this.contactPersonFax = contactPersonFax;
  }

  public String getContactPersonEmail() {
    return contactPersonEmail;
  }

  public void setContactPersonEmail(String contactPersonEmail) {
    this.contactPersonEmail = contactPersonEmail;
  }
  
}