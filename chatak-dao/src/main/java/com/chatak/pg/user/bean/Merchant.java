package com.chatak.pg.user.bean;

import com.chatak.pg.util.CommonUtil;

public class Merchant {

  private String businessName;
  
  private String lastName;

  private String firstName;

  private Long phone;
  
  private String emailId;

  private Long fax;

  private String address1;
  
  private String city;

  private String address2;

  private String state;
  
  private String pin;

  private String country;

  private String timeZone;
  
  private String userName;

  private String appMode;

  private String businessURL;
  
  private String stateTaxId;

  private String federalTaxId;

  private String salesTaxId;

  private String ownership;

  private String estimatedYearlySale;

  private Integer status;

  private String businessStartDate;

  private String noOfEmployee;

  private String role;

  private String feeProgram;

  private String processor;

  private Integer refunds;

  private Integer tipAmount;

  private Integer taxAmount;

  private Integer shippingAmount;

  private Integer allowRepeatSale;

  private Integer showRecurringBilling;

  private Integer autoSettlement;

  private String merchantCallBackURL;

  private String category;

  private Integer autoTransferLimit;

  private String autoPaymentMethod;

  private Integer autoTransferDay;

  private Integer pageIndex;

  private Integer pageSize;

  private Integer noOfRecords;

  private Integer virtualTerminal;

  private Integer posTerminal;

  private Integer online;

  private String webSiteAddress;

  private String returnUrl;

  private String cancelUrl;

  public String validate() {
    String message = "";
    if (CommonUtil.isNullAndEmpty(businessName)) {
      message = "merchant_name is the Required field";
    } else if (phone == null) {
      message = "phone1 is the Required field";
    } else if (CommonUtil.isNullAndEmpty(address1)) {
      message = "address1 is the Required field";
    } else if (CommonUtil.isNullAndEmpty(city)) {
      message = "city is the Required field";
    } else if (CommonUtil.isNullAndEmpty(state)) {
      message = "state is the Required field";
    } else if (CommonUtil.isNullAndEmpty(pin)) {
      message = "zip is the Required field";
    }
    return message;
  }

  public String getBusinessName() {
    return businessName;
  }

  public String getFirstName() {
    return firstName;
  }
  
  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Long getPhone() {
    return phone;
  }

  public void setPhone(Long phone) {
    this.phone = phone;
  }
  
  public String getEmailId() {
    return emailId;
  }

  public Long getFax() {
    return fax;
  }

  public void setFax(Long fax) {
    this.fax = fax;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public String getAddress1() {
    return address1;
  }
  
  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }
  
  public String getCountry() {
    return country;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPin() {
    return pin;
  }
  
  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  public String getTimeZone() {
    return timeZone;
  }

  public String getAppMode() {
    return appMode;
  }

  public void setAppMode(String appMode) {
    this.appMode = appMode;
  }
  
  public String getBusinessURL() {
    return businessURL;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setBusinessURL(String businessURL) {
    this.businessURL = businessURL;
  }

  public String getFederalTaxId() {
    return federalTaxId;
  }
  
  public void setStateTaxId(String stateTaxId) {
    this.stateTaxId = stateTaxId;
  }

  public void setFederalTaxId(String federalTaxId) {
    this.federalTaxId = federalTaxId;
  }

  public String getStateTaxId() {
    return stateTaxId;
  }

  public String getSalesTaxId() {
    return salesTaxId;
  }

  public void setSalesTaxId(String salesTaxId) {
    this.salesTaxId = salesTaxId;
  }
  
  public String getEstimatedYearlySale() {
    return estimatedYearlySale;
  }

  public String getOwnership() {
    return ownership;
  }

  public void setOwnership(String ownership) {
    this.ownership = ownership;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public void setEstimatedYearlySale(String estimatedYearlySale) {
    this.estimatedYearlySale = estimatedYearlySale;
  }

  public String getBusinessStartDate() {
    return businessStartDate;
  }

  public void setBusinessStartDate(String businessStartDate) {
    this.businessStartDate = businessStartDate;
  }

  public void setNoOfEmployee(String noOfEmployee) {
    this.noOfEmployee = noOfEmployee;
  }

  public String getRole() {
    return role;
  }
  
  public String getNoOfEmployee() {
    return noOfEmployee;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getFeeProgram() {
    return feeProgram;
  }

  public String getProcessor() {
    return processor;
  }
  
  public void setFeeProgram(String feeProgram) {
    this.feeProgram = feeProgram;
  }

  public void setProcessor(String processor) {
    this.processor = processor;
  }

  public Integer getRefunds() {
    return refunds;
  }

  public void setRefunds(Integer refunds) {
    this.refunds = refunds;
  }

  public Integer getTipAmount() {
    return tipAmount;
  }

  public void setTipAmount(Integer tipAmount) {
    this.tipAmount = tipAmount;
  }

  public Integer getTaxAmount() {
    return taxAmount;
  }

  public void setTaxAmount(Integer taxAmount) {
    this.taxAmount = taxAmount;
  }

  public Integer getShippingAmount() {
    return shippingAmount;
  }

  public void setShippingAmount(Integer shippingAmount) {
    this.shippingAmount = shippingAmount;
  }

  public Integer getAllowRepeatSale() {
    return allowRepeatSale;
  }

  public void setAllowRepeatSale(Integer allowRepeatSale) {
    this.allowRepeatSale = allowRepeatSale;
  }

  public Integer getShowRecurringBilling() {
    return showRecurringBilling;
  }

  public void setShowRecurringBilling(Integer showRecurringBilling) {
    this.showRecurringBilling = showRecurringBilling;
  }

  public Integer getAutoSettlement() {
    return autoSettlement;
  }

  public void setAutoSettlement(Integer autoSettlement) {
    this.autoSettlement = autoSettlement;
  }

  public String getMerchantCallBackURL() {
    return merchantCallBackURL;
  }

  public void setMerchantCallBackURL(String merchantCallBackURL) {
    this.merchantCallBackURL = merchantCallBackURL;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Integer getAutoTransferLimit() {
    return autoTransferLimit;
  }

  public void setAutoTransferLimit(Integer autoTransferLimit) {
    this.autoTransferLimit = autoTransferLimit;
  }

  public String getAutoPaymentMethod() {
    return autoPaymentMethod;
  }

  public void setAutoPaymentMethod(String autoPaymentMethod) {
    this.autoPaymentMethod = autoPaymentMethod;
  }

  public Integer getPageIndex() {
    return pageIndex;
  }
  
  public Integer getAutoTransferDay() {
    return autoTransferDay;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }
  
  public void setAutoTransferDay(Integer autoTransferDay) {
    this.autoTransferDay = autoTransferDay;
  }

  public Integer getPageSize() {
    return pageSize;
  }
  
  public void setVirtualTerminal(Integer virtualTerminal) {
    this.virtualTerminal = virtualTerminal;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getNoOfRecords() {
    return noOfRecords;
  }
  
  public Integer getVirtualTerminal() {
    return virtualTerminal;
  }

  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }

  public Integer getPosTerminal() {
    return posTerminal;
  }

  public void setPosTerminal(Integer posTerminal) {
    this.posTerminal = posTerminal;
  }

  public Integer getOnline() {
    return online;
  }

  public void setOnline(Integer online) {
    this.online = online;
  }

  public String getWebSiteAddress() {
    return webSiteAddress;
  }

  public String getReturnUrl() {
    return returnUrl;
  }
  
  public void setWebSiteAddress(String webSiteAddress) {
    this.webSiteAddress = webSiteAddress;
  }
  
  public String getCancelUrl() {
    return cancelUrl;
  }

  public void setReturnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
  }

  public void setCancelUrl(String cancelUrl) {
    this.cancelUrl = cancelUrl;
  }

}
