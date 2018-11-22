/**
 * 
 */
package com.chatak.pg.user.bean;

import java.math.BigDecimal;

/**
 * @Author: Girmiti Software
 * @Date: Sep 1, 2017
 * @Time: 5:19:02 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class MerchantRequest extends SearchRequest {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long id;

  private Long fax;

  private String address2;

  private String timeZone;

  private String appMode;

  private String federalTaxId;

  private String stateTaxId;

  private String salesTaxId;

  private String ownership;

  private Long estimatedYearlySale;

  private String noOfEmployee;
  
  private String feeProgram;

  private String role;

  private String processor;
  
  private Boolean tipAmount;

  private Boolean refunds;

  private Boolean taxAmount;
  
  private Boolean allowRepeatSale;

  private Boolean shippingAmount;

  private Boolean showRecurringBilling;
  
  private String createdDate;

  private Integer autoSettlement;

  private String updatedDate;

  private String merchantCallBackURL;

  private String category;

  private BigDecimal autoTransferLimit;

  private String autoPaymentMethod;

  private String autoTransferDay;

  private String litleMID;

  private String agentId;

  private String issuancePartnerId;

  private String programManagerId;

  private String bankAccountType;

  private String bankName;

  private String bankAccountNumber;

  private String bankRoutingNumber;

  private String bankCode;

  private String bankAccountName;

  private String bankCurrencyCode;

  private Integer bankStatus;

  private String bankNameOnAccount;

  private String bankAddress1;

  private String bankAddress2;

  private String bankCity;

  private String bankState;

  private String bankCountry;

  private String bankPin;

  private Boolean virtualTerminal;

  private Boolean posTerminal;

  private Boolean online;

  private String webSiteAddress;

  private String returnUrl;

  private String cancelUrl;

  private Boolean payPageConfig;

  private Long bankId;

  private Long resellerId;

  private String mcc;

  private Boolean dccEnable;

  private String localCurrency;

  private String agentAccountNumber;

  private String agentClientId;

  private String agentANI;

  private String partnerId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getFax() {
    return fax;
  }

  public void setFax(Long fax) {
    this.fax = fax;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  public String getAppMode() {
    return appMode;
  }

  public void setAppMode(String appMode) {
    this.appMode = appMode;
  }

  public String getFederalTaxId() {
    return federalTaxId;
  }

  public void setStateTaxId(String stateTaxId) {
    this.stateTaxId = stateTaxId;
  }

  public String getSalesTaxId() {
    return salesTaxId;
  }
  
  public void setFederalTaxId(String federalTaxId) {
    this.federalTaxId = federalTaxId;
  }

  public void setSalesTaxId(String salesTaxId) {
    this.salesTaxId = salesTaxId;
  }

  public String getOwnership() {
    return ownership;
  }
  
  public String getStateTaxId() {
    return stateTaxId;
  }

  public void setOwnership(String ownership) {
    this.ownership = ownership;
  }

  public Long getEstimatedYearlySale() {
    return estimatedYearlySale;
  }

  public void setNoOfEmployee(String noOfEmployee) {
    this.noOfEmployee = noOfEmployee;
  }

  public String getRole() {
    return role;
  }
  
  public void setEstimatedYearlySale(Long estimatedYearlySale) {
    this.estimatedYearlySale = estimatedYearlySale;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getFeeProgram() {
    return feeProgram;
  }
  
  public String getNoOfEmployee() {
    return noOfEmployee;
  }

  public void setFeeProgram(String feeProgram) {
    this.feeProgram = feeProgram;
  }

  public String getProcessor() {
    return processor;
  }

  public void setRefunds(Boolean refunds) {
    this.refunds = refunds;
  }

  public Boolean getTipAmount() {
    return tipAmount;
  }
  
  public void setProcessor(String processor) {
    this.processor = processor;
  }

  public void setTipAmount(Boolean tipAmount) {
    this.tipAmount = tipAmount;
  }

  public Boolean getTaxAmount() {
    return taxAmount;
  }

  public void setTaxAmount(Boolean taxAmount) {
    this.taxAmount = taxAmount;
  }
  
  public Boolean getRefunds() {
    return refunds;
  }

  public Boolean getShippingAmount() {
    return shippingAmount;
  }

  public void setShippingAmount(Boolean shippingAmount) {
    this.shippingAmount = shippingAmount;
  }

  public Boolean getShowRecurringBilling() {
    return showRecurringBilling;
  }
  
  public Boolean getAllowRepeatSale() {
    return allowRepeatSale;
  }

  public void setAllowRepeatSale(Boolean allowRepeatSale) {
    this.allowRepeatSale = allowRepeatSale;
  }

  public void setShowRecurringBilling(Boolean showRecurringBilling) {
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
  
  public String getCreatedDate() {
    return createdDate;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
  
  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public BigDecimal getAutoTransferLimit() {
    return autoTransferLimit;
  }

  public void setAutoTransferLimit(BigDecimal autoTransferLimit) {
    this.autoTransferLimit = autoTransferLimit;
  }
  
  public String getUpdatedDate() {
    return updatedDate;
  }

  public String getAutoPaymentMethod() {
    return autoPaymentMethod;
  }

  public void setAutoPaymentMethod(String autoPaymentMethod) {
    this.autoPaymentMethod = autoPaymentMethod;
  }
  
  public void setUpdatedDate(String updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getAutoTransferDay() {
    return autoTransferDay;
  }

  public void setAutoTransferDay(String autoTransferDay) {
    this.autoTransferDay = autoTransferDay;
  }

  public String getLitleMID() {
    return litleMID;
  }

  public void setLitleMID(String litleMID) {
    this.litleMID = litleMID;
  }

  public String getAgentId() {
    return agentId;
  }

  public void setAgentId(String agentId) {
    this.agentId = agentId;
  }

  public String getIssuancePartnerId() {
    return issuancePartnerId;
  }

  public void setIssuancePartnerId(String issuancePartnerId) {
    this.issuancePartnerId = issuancePartnerId;
  }

  public String getProgramManagerId() {
    return programManagerId;
  }

  public void setProgramManagerId(String programManagerId) {
    this.programManagerId = programManagerId;
  }

  public String getBankAccountType() {
    return bankAccountType;
  }

  public void setBankAccountType(String bankAccountType) {
    this.bankAccountType = bankAccountType;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getBankAccountNumber() {
    return bankAccountNumber;
  }

  public void setBankAccountNumber(String bankAccountNumber) {
    this.bankAccountNumber = bankAccountNumber;
  }

  public String getBankRoutingNumber() {
    return bankRoutingNumber;
  }

  public void setBankRoutingNumber(String bankRoutingNumber) {
    this.bankRoutingNumber = bankRoutingNumber;
  }

  public String getBankCode() {
    return bankCode;
  }

  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }

  public String getBankAccountName() {
    return bankAccountName;
  }

  public void setBankAccountName(String bankAccountName) {
    this.bankAccountName = bankAccountName;
  }

  public String getBankCurrencyCode() {
    return bankCurrencyCode;
  }

  public void setBankCurrencyCode(String bankCurrencyCode) {
    this.bankCurrencyCode = bankCurrencyCode;
  }

  public Integer getBankStatus() {
    return bankStatus;
  }

  public void setBankStatus(Integer bankStatus) {
    this.bankStatus = bankStatus;
  }

  public String getBankNameOnAccount() {
    return bankNameOnAccount;
  }

  public void setBankNameOnAccount(String bankNameOnAccount) {
    this.bankNameOnAccount = bankNameOnAccount;
  }

  public String getBankAddress1() {
    return bankAddress1;
  }

  public void setBankAddress1(String bankAddress1) {
    this.bankAddress1 = bankAddress1;
  }

  public String getBankAddress2() {
    return bankAddress2;
  }

  public void setBankAddress2(String bankAddress2) {
    this.bankAddress2 = bankAddress2;
  }

  public String getBankCity() {
    return bankCity;
  }

  public void setBankCity(String bankCity) {
    this.bankCity = bankCity;
  }

  public String getBankState() {
    return bankState;
  }

  public void setBankState(String bankState) {
    this.bankState = bankState;
  }

  public String getBankCountry() {
    return bankCountry;
  }

  public void setBankCountry(String bankCountry) {
    this.bankCountry = bankCountry;
  }

  public String getBankPin() {
    return bankPin;
  }

  public void setBankPin(String bankPin) {
    this.bankPin = bankPin;
  }

  public Boolean getVirtualTerminal() {
    return virtualTerminal;
  }
  
  public void setResellerId(Long resellerId) {
    this.resellerId = resellerId;
  }

  public void setVirtualTerminal(Boolean virtualTerminal) {
    this.virtualTerminal = virtualTerminal;
  }
  
  public Long getResellerId() {
    return resellerId;
  }

  public Boolean getPosTerminal() {
    return posTerminal;
  }

  public void setPosTerminal(Boolean posTerminal) {
    this.posTerminal = posTerminal;
  }
  
  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public Boolean getOnline() {
    return online;
  }

  public void setOnline(Boolean online) {
    this.online = online;
  }
  
  public Long getBankId() {
    return bankId;
  }

  public String getWebSiteAddress() {
    return webSiteAddress;
  }

  public void setWebSiteAddress(String webSiteAddress) {
    this.webSiteAddress = webSiteAddress;
  }
  
  public void setPayPageConfig(Boolean payPageConfig) {
    this.payPageConfig = payPageConfig;
  }

  public String getReturnUrl() {
    return returnUrl;
  }

  public void setReturnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
  }
  
  public Boolean getPayPageConfig() {
    return payPageConfig;
  }

  public String getCancelUrl() {
    return cancelUrl;
  }

  public void setCancelUrl(String cancelUrl) {
    this.cancelUrl = cancelUrl;
  }

  public String getMcc() {
    return mcc;
  }

  public void setMcc(String mcc) {
    this.mcc = mcc;
  }

  public Boolean getDccEnable() {
    return dccEnable;
  }

  public void setDccEnable(Boolean dccEnable) {
    this.dccEnable = dccEnable;
  }

  public String getLocalCurrency() {
    return localCurrency;
  }

  public void setLocalCurrency(String localCurrency) {
    this.localCurrency = localCurrency;
  }

  public String getAgentAccountNumber() {
    return agentAccountNumber;
  }

  public void setAgentAccountNumber(String agentAccountNumber) {
    this.agentAccountNumber = agentAccountNumber;
  }

  public String getAgentClientId() {
    return agentClientId;
  }

  public void setAgentClientId(String agentClientId) {
    this.agentClientId = agentClientId;
  }

  public String getAgentANI() {
    return agentANI;
  }

  public void setAgentANI(String agentANI) {
    this.agentANI = agentANI;
  }

  public String getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }

}
