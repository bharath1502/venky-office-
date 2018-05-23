package com.chatak.pg.user.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.chatak.pg.util.CommonUtil;

public class AddMerchantRequest extends MerchantRequest {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public static final String DATE_REGEX = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";

  private String createdBy;

  private String merchantType;

  private Long parentMerchantId;

  private String password;

  private String merchantLink;

  private String lookinFor;

  private String businessType;

  private String payOutAt;

  private String merchantCategory;

  private Boolean allowAdvancedFraudFilter;
  
  private String businessName;
  
  private Long phone;

  private String merchantCode;
  
  private String address1;
  
  private String state;
  
  private String city;

  private String country;
  
  private Integer status;

  private String pin;
  
  private String businessStartDate;
  
  private String businessURL;
  
  private String userName;

  private String firstName;
  
  private String emailId;

  private String lastName;

  /**
   * @return the createdBy
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * @param createdBy
   *            the createdBy to set
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String validate() {
    String message = "";
    Pattern pattern = Pattern.compile(DATE_REGEX);
    Matcher matcher = pattern.matcher(businessStartDate);
    if (CommonUtil.isNullAndEmpty(businessName)) {
      message += "businessName is the Required field";
    } else if (phone == null) {
      message += "phone is the Required field";
    } else if (CommonUtil.isNullAndEmpty(address1)) {
      message += "address1 is the Required field";
    } else if (CommonUtil.isNullAndEmpty(city)) {
      message += "city is the Required field";
    } else if (CommonUtil.isNullAndEmpty(state)) {
      message += "state is the Required field";
    } else if (CommonUtil.isNullAndEmpty(pin)) {
      message += "pin is the Required field";
    } else if (CommonUtil.isNullAndEmpty(country)) {
      message += "country is the Required field";
    } else if (CommonUtil.isNullAndEmpty(userName)) {
      message += "username is the Required field";
    } else if (CommonUtil.isNullAndEmpty(firstName)) {
      message += "firstName is the Required field";
    } else if (CommonUtil.isNullAndEmpty(lastName)) {
      message += "lastName is the Required field";
    } else if (CommonUtil.isNullAndEmpty(emailId)) {
      message += "emailId is the Required field";
    } else if (CommonUtil.isNullAndEmpty(businessURL)) {
      message += "businessURL is the Required field";
    } else if (!matcher.matches()) {
      message += "Date not valid";
    }

    return message;
  }

  public String getMerchantType() {
    return merchantType;
  }

  public void setMerchantType(String merchantType) {
    this.merchantType = merchantType;
  }

  public Long getParentMerchantId() {
    return parentMerchantId;
  }

  public void setParentMerchantId(Long parentMerchantId) {
    this.parentMerchantId = parentMerchantId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMerchantLink() {
    return merchantLink;
  }

  public void setMerchantLink(String merchantLink) {
    this.merchantLink = merchantLink;
  }

  public String getLookinFor() {
    return lookinFor;
  }

  public void setLookinFor(String lookinFor) {
    this.lookinFor = lookinFor;
  }

  public String getBusinessType() {
    return businessType;
  }

  public void setBusinessType(String businessType) {
    this.businessType = businessType;
  }

  public String getPayOutAt() {
    return payOutAt;
  }

  public void setMerchantCategory(String merchantCategory) {
    this.merchantCategory = merchantCategory;
  }

  public Boolean getAllowAdvancedFraudFilter() {
    return allowAdvancedFraudFilter;
  }
  
  public void setPayOutAt(String payOutAt) {
    this.payOutAt = payOutAt;
  }

  public String getMerchantCategory() {
    return merchantCategory;
  }

  public void setAllowAdvancedFraudFilter(Boolean allowAdvancedFraudFilter) {
    this.allowAdvancedFraudFilter = allowAdvancedFraudFilter;
  }

  public String getBusinessName() {
    return businessName;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public Long getPhone() {
    return phone;
  }
  
  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setPhone(Long phone) {
    this.phone = phone;
  }

  public String getAddress1() {
    return address1;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }
  
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getCity() {
    return city;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  public Integer getStatus() {
    return status;
  }
  
  public void setCountry(String country) {
    this.country = country;
  }

  public String getPin() {
    return pin;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getBusinessStartDate() {
    return businessStartDate;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getBusinessURL() {
    return businessURL;
  }
  
  public void setBusinessStartDate(String businessStartDate) {
    this.businessStartDate = businessStartDate;
  }

  public String getUserName() {
    return userName;
  }

  public void setBusinessURL(String businessURL) {
    this.businessURL = businessURL;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmailId() {
    return emailId;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

}
