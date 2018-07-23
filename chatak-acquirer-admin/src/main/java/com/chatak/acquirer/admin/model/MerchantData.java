/**
* 
*/
package com.chatak.acquirer.admin.model;

import java.sql.Timestamp;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 03-Jan-2015 3:49:50 PM
 * @version 1.0
 */
public class MerchantData extends ResellerMerchantCommon {

  private Long id;

  private String merchantCode;

  private String businessName;

  private String firstName;

  private String lastName;

  private Long phone;

  private Long fax;

  private String emailId;

  private String pin;

  private String timeZone;

  private String appMode;

  private String userName;

  private String businessURL;

  private Long federalTaxId;

  private Long stateTaxId;

  private Long salesTaxId;

  private String ownership;

  private Long estimatedYearlySale;

  private String status;

  private Boolean tipAmount;

  private Boolean taxAmount;

  private String role;

  private String feeProgram;
  
  private String businessStartDate;

  private String noOfEmployee;

  private String processor;

  private Boolean refunds;

  private Boolean showRecurringBilling;

  private Boolean autoSettlement;

  private Boolean shippingAmount;

  private Boolean allowRepeatSale;

  private Timestamp createdDate;

  private String updatedDate;

  private String createdDateString;

  // NEW ADDED COLUMNS

  private Boolean virtualTerminal;

  private Boolean posTerminal;

  private Boolean online;

  private String webSiteAddress;

  private String returnUrl;

  private String cancelUrl;

  private String mcc;

  private String reason;

  private String localCurrency;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *            the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getLocalCurrency() {
    return localCurrency;
  }

  public void setLocalCurrency(String localCurrency) {
    this.localCurrency = localCurrency;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName
   *            the firstName to set
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
   * @param businessName
   *            the businessName to set
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
   * @param lastName
   *            the lastName to set
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
   * @param fax
   *            the fax to set
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
   * @param phone
   *            the phone to set
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
   * @param emailId
   *            the emailId to set
   */
  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  /**
   * @return the pin
   */
  public String getPin() {
    return pin;
  }

  /**
   * @param pin
   *            the pin to set
   */
  public void setPin(String pin) {
    this.pin = pin;
  }

  /**
   * @return the appMode
   */
  public String getAppMode() {
    return appMode;
  }

  /**
   * @param appMode
   *            the appMode to set
   */
  public void setAppMode(String appMode) {
    this.appMode = appMode;
  }
  
  /**
   * @return the timeZone
   */
  public String getTimeZone() {
    return timeZone;
  }

  /**
   * @param timeZone
   *            the timeZone to set
   */
  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  /**
   * @return the userName
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @param userName
   *            the userName to set
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * @return the federalTaxId
   */
  public Long getFederalTaxId() {
    return federalTaxId;
  }

  /**
   * @param federalTaxId
   *            the federalTaxId to set
   */
  public void setFederalTaxId(Long federalTaxId) {
    this.federalTaxId = federalTaxId;
  }
  
  /**
   * @return the businessURL
   */
  public String getBusinessURL() {
    return businessURL;
  }

  /**
   * @param businessURL
   *            the businessURL to set
   */
  public void setBusinessURL(String businessURL) {
    this.businessURL = businessURL;
  }

  /**
   * @return the stateTaxId
   */
  public Long getStateTaxId() {
    return stateTaxId;
  }

  /**
   * @param stateTaxId
   *            the stateTaxId to set
   */
  public void setStateTaxId(Long stateTaxId) {
    this.stateTaxId = stateTaxId;
  }

  /**
   * @return the ownership
   */
  public String getOwnership() {
    return ownership;
  }

  /**
   * @param ownership
   *            the ownership to set
   */
  public void setOwnership(String ownership) {
    this.ownership = ownership;
  }
  
  /**
   * @return the salesTaxId
   */
  public Long getSalesTaxId() {
    return salesTaxId;
  }

  /**
   * @param salesTaxId
   *            the salesTaxId to set
   */
  public void setSalesTaxId(Long salesTaxId) {
    this.salesTaxId = salesTaxId;
  }

  /**
   * @return the estimatedYearlySale
   */
  public Long getEstimatedYearlySale() {
    return estimatedYearlySale;
  }

  /**
   * @param estimatedYearlySale
   *            the estimatedYearlySale to set
   */
  public void setEstimatedYearlySale(Long estimatedYearlySale) {
    this.estimatedYearlySale = estimatedYearlySale;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @param string
   *            the status to set
   */
  public void setStatus(String string) {
    this.status = string;
  }

  /**
   * @return the businessStartDate
   */
  public String getBusinessStartDate() {
    return businessStartDate;
  }

  /**
   * @param businessStartDate
   *            the businessStartDate to set
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
   * @param role
   *            the role to set
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
   * @param noOfEmployee
   *            the noOfEmployee to set
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
   * @param feeProgram
   *            the feeProgram to set
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
   * @param refunds
   *            the refunds to set
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
   * @param processor
   *            the processor to set
   */
  public void setProcessor(String processor) {
    this.processor = processor;
  }

  /**
   * @return the tipAmount
   */
  public Boolean getTipAmount() {
    return tipAmount;
  }

  /**
   * @param tipAmount
   *            the tipAmount to set
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
   * @param shippingAmount
   *            the shippingAmount to set
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
   * @param taxAmount
   *            the taxAmount to set
   */
  public void setTaxAmount(Boolean taxAmount) {
    this.taxAmount = taxAmount;
  }

  /**
   * @return the allowRepeatSale
   */
  public Boolean getAllowRepeatSale() {
    return allowRepeatSale;
  }

  /**
   * @param allowRepeatSale
   *            the allowRepeatSale to set
   */
  public void setAllowRepeatSale(Boolean allowRepeatSale) {
    this.allowRepeatSale = allowRepeatSale;
  }

  /**
   * @return the autoSettlement
   */
  public Boolean getAutoSettlement() {
    return autoSettlement;
  }

  /**
   * @param autoSettlement
   *            the autoSettlement to set
   */
  public void setAutoSettlement(Boolean autoSettlement) {
    this.autoSettlement = autoSettlement;
  }
  
  /**
   * @return the showRecurringBilling
   */
  public Boolean getShowRecurringBilling() {
    return showRecurringBilling;
  }

  /**
   * @param showRecurringBilling
   *            the showRecurringBilling to set
   */
  public void setShowRecurringBilling(Boolean showRecurringBilling) {
    this.showRecurringBilling = showRecurringBilling;
  }

  /**
   * @return the createdDate
   */
  public Timestamp getCreatedDate() {
    return createdDate;
  }

  /**
   * @param createdDate
   *            the createdDate to set
   */
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * @return the updatedDate
   */
  public String getUpdatedDate() {
    return updatedDate;
  }

  /**
   * @param updatedDate
   *            the updatedDate to set
   */
  public void setUpdatedDate(String updatedDate) {
    this.updatedDate = updatedDate;
  }

  /**
   * @return the merchantCode
   */
  public String getMerchantCode() {
    return merchantCode;
  }

  /**
   * @param merchantCode
   *            the merchantCode to set
   */
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public String getCreatedDateString() {
    return createdDateString;
  }

  public void setCreatedDateString(String createdDateString) {
    this.createdDateString = createdDateString;
  }

  /**
   * @return the virtualTerminal
   */
  public Boolean getVirtualTerminal() {
    return virtualTerminal;
  }

  /**
   * @param virtualTerminal the virtualTerminal to set
   */
  public void setVirtualTerminal(Boolean virtualTerminal) {
    this.virtualTerminal = virtualTerminal;
  }

  /**
   * @return the posTerminal
   */
  public Boolean getPosTerminal() {
    return posTerminal;
  }

  /**
   * @param posTerminal the posTerminal to set
   */
  public void setPosTerminal(Boolean posTerminal) {
    this.posTerminal = posTerminal;
  }

  /**
   * @return the online
   */
  public Boolean getOnline() {
    return online;
  }

  /**
   * @param online the online to set
   */
  public void setOnline(Boolean online) {
    this.online = online;
  }

  /**
   * @return the webSiteAddress
   */
  public String getWebSiteAddress() {
    return webSiteAddress;
  }

  /**
   * @param webSiteAddress the webSiteAddress to set
   */
  public void setWebSiteAddress(String webSiteAddress) {
    this.webSiteAddress = webSiteAddress;
  }

  /**
   * @return the returnUrl
   */
  public String getReturnUrl() {
    return returnUrl;
  }

  /**
   * @param returnUrl the returnUrl to set
   */
  public void setReturnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
  }

  /**
   * @return the cancelUrl
   */
  public String getCancelUrl() {
    return cancelUrl;
  }

  /**
   * @param cancelUrl the cancelUrl to set
   */
  public void setCancelUrl(String cancelUrl) {
    this.cancelUrl = cancelUrl;
  }

  public String getMcc() {
    return mcc;
  }

  public void setMcc(String mcc) {
    this.mcc = mcc;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }
}
