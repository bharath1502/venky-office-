/**
 * 
 */
package com.chatak.merchant.controller.model;

import java.math.BigDecimal;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 03-Jan-2015 3:49:50 PM
 * @version 1.0
 */
public class MerchantProfile {

  private Long id;

  private String merchantCode;

  private String businessName;

  private String firstName;

  private String lastName;

  private Long phone;

  private Long fax;

  private String emailId;

  private String address1;

  private String address2;

  private String city;

  private String state;

  private String country;

  private String pin;

  private String timeZone;

  private String appMode;

  private String userName;

  private String businessURL;

  private String federalTaxId;

  private String stateTaxId;

  private String salesTaxId;

  private String ownership;

  private Long estimatedYearlySale;

  private Integer status;

  private String businessStartDate;

  private String noOfEmployee;

  private String feeProgram;

  private String processor;
  
  private String role;

  private Boolean refunds;

  private Boolean tipAmount;

  private Boolean shippingAmount;

  private Boolean allowRepeatSale;
  
  private Boolean taxAmount;

  private Boolean showRecurringBilling;

  private Integer autoSettlement;

  private String createdDate;

  private String updatedDate;

  private Integer pageIndex;

  private Integer pageSize;

  private Integer noOfRecords;

  private String merchantCallBackURL;

  private String category;

  private BigDecimal autoTransferLimit;

  private String autoPaymentMethod;

  private String autoTransferDay;
  
  private String mailingAddress1;

  private String mailingAddress2;

  private String mailingCity;

  private String mailingState;

  private String mailingCountry;

  private String mailingPin;
  
  private String merchantUserId;


  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the merchantCode
   */
  public String getMerchantCode() {
    return merchantCode;
  }

  /**
   * @param merchantCode
   *          the merchantCode to set
   */
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param businessName
   *          the businessName to set
   */
  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }
  
  /**
   * @param firstName
   *          the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  /**
   * @return the businessName
   */
  public String getBusinessName() {
    return businessName;
  }

  /**
   * @param lastName
   *          the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the fax
   */
  public Long getFax() {
    return fax;
  }

  /**
   * @param phone
   *          the phone to set
   */
  public void setPhone(Long phone) {
    this.phone = phone;
  }

  /**
   * @return the emailId
   */
  public String getEmailId() {
    return emailId;
  }
  
  /**
   * @param fax
   *          the fax to set
   */
  public void setFax(Long fax) {
    this.fax = fax;
  }
  
  /**
   * @return the phone
   */
  public Long getPhone() {
    return phone;
  }

  /**
   * @param emailId
   *          the emailId to set
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
   * @param address2
   *          the address2 to set
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }
  
  /**
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }
  
  /**
   * @param city
   *          the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @param address1
   *          the address1 to set
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * @param state
   *          the state to set
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * @return the pin
   */
  public String getPin() {
    return pin;
  }

  /**
   * @param pin
   *          the pin to set
   */
  public void setPin(String pin) {
    this.pin = pin;
  }
  
  /**
   * @param country
   *          the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }
  
  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @return the timeZone
   */
  public String getTimeZone() {
    return timeZone;
  }

  /**
   * @param timeZone
   *          the timeZone to set
   */
  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  /**
   * @return the appMode
   */
  public String getAppMode() {
    return appMode;
  }

  /**
   * @param appMode
   *          the appMode to set
   */
  public void setAppMode(String appMode) {
    this.appMode = appMode;
  }

  /**
   * @return the userName
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @param userName
   *          the userName to set
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * @return the businessURL
   */
  public String getBusinessURL() {
    return businessURL;
  }

  /**
   * @param businessURL
   *          the businessURL to set
   */
  public void setBusinessURL(String businessURL) {
    this.businessURL = businessURL;
  }

  /**
   * @return the federalTaxId
   */
  public String getFederalTaxId() {
    return federalTaxId;
  }

  /**
   * @param federalTaxId
   *          the federalTaxId to set
   */
  public void setFederalTaxId(String federalTaxId) {
    this.federalTaxId = federalTaxId;
  }

  /**
   * @return the stateTaxId
   */
  public String getStateTaxId() {
    return stateTaxId;
  }

  /**
   * @param stateTaxId
   *          the stateTaxId to set
   */
  public void setStateTaxId(String stateTaxId) {
    this.stateTaxId = stateTaxId;
  }

  /**
   * @return the salesTaxId
   */
  public String getSalesTaxId() {
    return salesTaxId;
  }

  /**
   * @param salesTaxId
   *          the salesTaxId to set
   */
  public void setSalesTaxId(String salesTaxId) {
    this.salesTaxId = salesTaxId;
  }

  /**
   * @return the ownership
   */
  public String getOwnership() {
    return ownership;
  }

  /**
   * @param ownership
   *          the ownership to set
   */
  public void setOwnership(String ownership) {
    this.ownership = ownership;
  }

  /**
   * @return the estimatedYearlySale
   */
  public Long getEstimatedYearlySale() {
    return estimatedYearlySale;
  }

  /**
   * @param estimatedYearlySale
   *          the estimatedYearlySale to set
   */
  public void setEstimatedYearlySale(Long estimatedYearlySale) {
    this.estimatedYearlySale = estimatedYearlySale;
  }

  /**
   * @return the status
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * @return the businessStartDate
   */
  public String getBusinessStartDate() {
    return businessStartDate;
  }

  /**
   * @param role
   *          the role to set
   */
  public void setRole(String role) {
    this.role = role;
  }
  
  /**
   * @return the noOfEmployee
   */
  public String getNoOfEmployee() {
    return noOfEmployee;
  }
  
  /**
   * @param businessStartDate
   *          the businessStartDate to set
   */
  public void setBusinessStartDate(String businessStartDate) {
    this.businessStartDate = businessStartDate;
  }

  /**
   * @return the role
   */
  public String getRole() {
    return role;
  }

  /**
   * @param noOfEmployee
   *          the noOfEmployee to set
   */
  public void setNoOfEmployee(String noOfEmployee) {
    this.noOfEmployee = noOfEmployee;
  }

  /**
   * @return the feeProgram
   */
  public String getFeeProgram() {
    return feeProgram;
  }

  /**
   * @param refunds
   *          the refunds to set
   */
  public void setRefunds(Boolean refunds) {
    this.refunds = refunds;
  }
  
  /**
   * @return the processor
   */
  public String getProcessor() {
    return processor;
  }
  
  /**
   * @param feeProgram
   *          the feeProgram to set
   */
  public void setFeeProgram(String feeProgram) {
    this.feeProgram = feeProgram;
  }

  /**
   * @return the refunds
   */
  public Boolean getRefunds() {
    return refunds;
  }

  /**
   * @param processor
   *          the processor to set
   */
  public void setProcessor(String processor) {
    this.processor = processor;
  }

  /**
   * @return the autoSettlement
   */
  public Integer getAutoSettlement() {
    return autoSettlement;
  }

  /**
   * @param autoSettlement
   *          the autoSettlement to set
   */
  public void setAutoSettlement(Integer autoSettlement) {
    this.autoSettlement = autoSettlement;
  }

  /**
   * @return the tipAmount
   */
  public Boolean getTipAmount() {
    return tipAmount;
  }

  /**
   * @param shippingAmount
   *          the shippingAmount to set
   */
  public void setShippingAmount(Boolean shippingAmount) {
    this.shippingAmount = shippingAmount;
  }
  
  /**
   * @return the taxAmount
   */
  public Boolean getTaxAmount() {
    return taxAmount;
  }
  
  /**
   * @param tipAmount
   *          the tipAmount to set
   */
  public void setTipAmount(Boolean tipAmount) {
    this.tipAmount = tipAmount;
  }

  /**
   * @return the shippingAmount
   */
  public Boolean getShippingAmount() {
    return shippingAmount;
  }

  /**
   * @param taxAmount
   *          the taxAmount to set
   */
  public void setTaxAmount(Boolean taxAmount) {
    this.taxAmount = taxAmount;
  }

  /**
   * @param allowRepeatSale
   *          the allowRepeatSale to set
   */
  public void setAllowRepeatSale(Boolean allowRepeatSale) {
    this.allowRepeatSale = allowRepeatSale;
  }

  /**
   * @return the createdDate
   */
  public String getCreatedDate() {
    return createdDate;
  }
  
  /**
   * @return the allowRepeatSale
   */
  public Boolean getAllowRepeatSale() {
    return allowRepeatSale;
  }

  /**
   * @param createdDate
   *          the createdDate to set
   */
  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }
  
  /**
   * @return the showRecurringBilling
   */
  public Boolean getShowRecurringBilling() {
    return showRecurringBilling;
  }

  /**
   * @param showRecurringBilling
   *          the showRecurringBilling to set
   */
  public void setShowRecurringBilling(Boolean showRecurringBilling) {
    this.showRecurringBilling = showRecurringBilling;
  }

  /**
   * @return the updatedDate
   */
  public String getUpdatedDate() {
    return updatedDate;
  }

  /**
   * @param updatedDate
   *          the updatedDate to set
   */
  public void setUpdatedDate(String updatedDate) {
    this.updatedDate = updatedDate;
  }

  /**
   * @return the pageSize
   */
  public Integer getPageSize() {
    return pageSize;
  }

  /**
   * @param pageSize
   *          the pageSize to set
   */
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
  
  /**
   * @return the merchantCallBackURL
   */
  public String getMerchantCallBackURL() {
    return merchantCallBackURL;
  }

  /**
   * @param merchantCallBackURL the merchantCallBackURL to set
   */
  public void setMerchantCallBackURL(String merchantCallBackURL) {
    this.merchantCallBackURL = merchantCallBackURL;
  }

  /**
   * @return the noOfRecords
   */
  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  /**
   * @param noOfRecords
   *          the noOfRecords to set
   */
  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }

  /**
   * @return the category
   */
  public String getCategory() {
    return category;
  }

  /**
   * @param category the category to set
   */
  public void setCategory(String category) {
    this.category = category;
  }
  
  /**
   * @return the pageIndex
   */
  public Integer getPageIndex() {
    return pageIndex;
  }

  /**
   * @param pageIndex
   *          the pageIndex to set
   */
  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  /**
   * @return the autoTransferLimit
   */
  public BigDecimal getAutoTransferLimit() {
    return autoTransferLimit;
  }

  /**
   * @param autoTransferLimit the autoTransferLimit to set
   */
  public void setAutoTransferLimit(BigDecimal autoTransferLimit) {
    this.autoTransferLimit = autoTransferLimit;
  }

  /**
   * @return the autoPaymentMethod
   */
  public String getAutoPaymentMethod() {
    return autoPaymentMethod;
  }

  /**
   * @param autoPaymentMethod the autoPaymentMethod to set
   */
  public void setAutoPaymentMethod(String autoPaymentMethod) {
    this.autoPaymentMethod = autoPaymentMethod;
  }

  /**
   * @return the mailingAddress1
   */
  public String getMailingAddress1() {
    return mailingAddress1;
  }

  /**
   * @param mailingAddress1 the mailingAddress1 to set
   */
  public void setMailingAddress1(String mailingAddress1) {
    this.mailingAddress1 = mailingAddress1;
  }


  /**
   * @return the mailingAddress2
   */
  public String getMailingAddress2() {
    return mailingAddress2;
  }

  /**
   * @param mailingAddress2 the mailingAddress2 to set
   */
  public void setMailingAddress2(String mailingAddress2) {
    this.mailingAddress2 = mailingAddress2;
  }

  /**
   * @return the mailingCity
   */
  public String getMailingCity() {
    return mailingCity;
  }

  /**
   * @param mailingCity the mailingCity to set
   */
  public void setMailingCity(String mailingCity) {
    this.mailingCity = mailingCity;
  }

  /**
   * @return the mailingState
   */
  public String getMailingState() {
    return mailingState;
  }

  /**
   * @param mailingState the mailingState to set
   */
  public void setMailingState(String mailingState) {
    this.mailingState = mailingState;
  }

  /**
   * @return the mailingCountry
   */
  public String getMailingCountry() {
    return mailingCountry;
  }

  /**
   * @param mailingCountry the mailingCountry to set
   */
  public void setMailingCountry(String mailingCountry) {
    this.mailingCountry = mailingCountry;
  }

  /**
   * @return the mailingPin
   */
  public String getMailingPin() {
    return mailingPin;
  }

  /**
   * @param mailingPin the mailingPin to set
   */
  public void setMailingPin(String mailingPin) {
    this.mailingPin = mailingPin;
  }

  /**
   * @return the merchantUserId
   */
  public String getMerchantUserId() {
    return merchantUserId;
  }

  /**
   * @param merchantUserId the merchantUserId to set
   */
  public void setMerchantUserId(String merchantUserId) {
    this.merchantUserId = merchantUserId;
  }

  public String getAutoTransferDay() {
    return autoTransferDay;
  }

  public void setAutoTransferDay(String autoTransferDay) {
    this.autoTransferDay = autoTransferDay;
  }

}
