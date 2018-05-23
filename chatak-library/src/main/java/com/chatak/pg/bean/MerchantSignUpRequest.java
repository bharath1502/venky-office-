package com.chatak.pg.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class MerchantSignUpRequest implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 6547786606060915407L;
  
  
  private String firstName;
  private String lastName;
  private String mobileNumber;
  private String address;
  private String zipCode;
  private String country;
  private String emailId;
  private String businessType;
  private String businessName;
  private String lookingFor;
  private Timestamp createdDate;
  private Timestamp updatedDate;
  private Integer status;
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getMobileNumber() {
    return mobileNumber;
  }
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getZipCode() {
    return zipCode;
  }
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }
  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  public String getEmailId() {
    return emailId;
  }
  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }
  public String getBusinessType() {
    return businessType;
  }
  public void setBusinessType(String businessType) {
    this.businessType = businessType;
  }
  public String getBusinessName() {
    return businessName;
  }
  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }
  public Timestamp getCreatedDate() {
    return createdDate;
  }
  public Timestamp getUpdatedDate() {
    return updatedDate;
  }
  public Integer getStatus() {
    return status;
  }
  public String getLookingFor() {
    return lookingFor;
  }
  public void setLookingFor(String lookingFor) {
    this.lookingFor = lookingFor;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

}
