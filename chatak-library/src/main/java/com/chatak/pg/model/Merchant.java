/**
 * 
 */
package com.chatak.pg.model;

import java.util.List;
import java.util.Map;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 03-Jan-2015 3:49:50 PM
 * @version 1.0
 */
public class Merchant extends MerchantRequest {

  private Long id;
  
  private String businessName;

  private String merchantCode;

  private String firstName;
  
  private Long phone;

  private String lastName;

  private Long fax;
  
  private String address1;

  private String emailId;

  private String address2;
  
  private String state;

  private String city;

  private String country;
  
  private String timeZone;

  private String pin;

  private String appMode;
  
  private String businessURL;

  private String userName;

  private String federalTaxId;
  
  private String salesTaxId;

  private String stateTaxId;

  private String ownership;
  
  private Integer status;

  private Long estimatedYearlySale;

  private String processor;

  private String businessStartDate;
  
  private String role;

  private String noOfEmployee;

  private String feeProgram;

  private String merchantType;

  private Long parentMerchantId;

  private String subMerchantCode;

  private String merchantLink;

  private Long accountId;
  
  private String programManagerName;

  private String parentMerchantName;
  
  private List<String> cardProgramIds;
  
  private List<Long> entitiesId;
  
  private Long entityId;
  
  private Long cardProgramId;
  
  private Map<Long, Long> cardProgramAndEntityId;

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  private String createdBy;

  private Long emailNotificationFlag;

  private String batchMailId;

  private Boolean virtualTerminal;

  private Boolean posTerminal;

  private Boolean online;

  private String webSiteAddress;

  private String returnUrl;

  private String cancelUrl;

  private Boolean payPageConfig;

  private Boolean tbd;

  private String payOutAt;

  private String merchantCategory;

  private Boolean allowAdvancedFraudFilter;

  private Long bankId;

  private Long resellerId;

  private String mcc;

  private Boolean dccEnable;

  private String currencyId;

  private List<MerchantCurrencyMapping> selectedCurrencyMapping;

  private Integer nmasRequired;

  private String localCurrency;

  private String declineReason;

  private String agentAccountNumber;

  private String agentClientId;

  private String agentANI;

  private String bankCurrencyCode;

  private String agentName;

  private Integer sessionStatus;

  private String partnerId;
  
  private String partnerName;
  
  private String associatedTo;
  
  private String process;
  
  private String entityType;
  
  private String cardProgramName;
  
  private String isoName;
  
  public String getBankCurrencyCode() {
    return bankCurrencyCode;
  }

  public void setBankCurrencyCode(String bankCurrencyCode) {
    this.bankCurrencyCode = bankCurrencyCode;
  }

  public Long getId() {
    return id;
  }
  
  public String getMerchantCode() {
    return merchantCode;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBusinessName() {
    return businessName;
  }
  
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }
  
  public String getLastName() {
    return lastName;
  }

  public String getFirstName() {
    return firstName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Long getPhone() {
    return phone;
  }
  
  public Long getFax() {
    return fax;
  }

  public void setPhone(Long phone) {
    this.phone = phone;
  }

  public String getEmailId() {
    return emailId;
  }
  
  public void setFax(Long fax) {
    this.fax = fax;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }
  
  public String getAddress2() {
    return address2;
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

  public String getCity() {
    return city;
  }
  
  public String getState() {
    return state;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }
  
  public void setState(String state) {
    this.state = state;
  }

  public void setCountry(String country) {
    this.country = country;
  }
  
  public String getTimeZone() {
    return timeZone;
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

  public String getAppMode() {
    return appMode;
  }
  
  public String getUserName() {
    return userName;
  }

  public void setAppMode(String appMode) {
    this.appMode = appMode;
  }

  public String getBusinessURL() {
    return businessURL;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setBusinessURL(String businessURL) {
    this.businessURL = businessURL;
  }

  public String getStateTaxId() {
    return stateTaxId;
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

  public String getSalesTaxId() {
    return salesTaxId;
  }

  public void setSalesTaxId(String salesTaxId) {
    this.salesTaxId = salesTaxId;
  }

  public Long getEstimatedYearlySale() {
    return estimatedYearlySale;
  }
  
  public String getOwnership() {
    return ownership;
  }

  public void setEstimatedYearlySale(Long estimatedYearlySale) {
    this.estimatedYearlySale = estimatedYearlySale;
  }
  
  public String getBusinessStartDate() {
    return businessStartDate;
  }

  public Integer getStatus() {
    return status;
  }
  
  public void setOwnership(String ownership) {
    this.ownership = ownership;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getNoOfEmployee() {
    return noOfEmployee;
  }
  
  public void setBusinessStartDate(String businessStartDate) {
    this.businessStartDate = businessStartDate;
  }

  public void setNoOfEmployee(String noOfEmployee) {
    this.noOfEmployee = noOfEmployee;
  }
  
  public String getFeeProgram() {
    return feeProgram;
  }

  public String getRole() {
    return role;
  }
  
  public void setFeeProgram(String feeProgram) {
    this.feeProgram = feeProgram;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getProcessor() {
    return processor;
  }

  public void setProcessor(String processor) {
    this.processor = processor;
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

  public String getSubMerchantCode() {
    return subMerchantCode;
  }

  public void setSubMerchantCode(String subMerchantCode) {
    this.subMerchantCode = subMerchantCode;
  }

  public String getMerchantLink() {
    return merchantLink;
  }

  public void setMerchantLink(String merchantLink) {
    this.merchantLink = merchantLink;
  }

  public Boolean getTbd() {
    return tbd;
  }

  public void setTbd(Boolean tbd) {
    this.tbd = tbd;
  }

  public String getPayOutAt() {
    return payOutAt;
  }

  public void setPayOutAt(String payOutAt) {
    this.payOutAt = payOutAt;
  }

  public String getMerchantCategory() {
    return merchantCategory;
  }

  public void setMerchantCategory(String merchantCategory) {
    this.merchantCategory = merchantCategory;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Long getEmailNotificationFlag() {
    return emailNotificationFlag;
  }

  public void setEmailNotificationFlag(Long emailNotificationFlag) {
    this.emailNotificationFlag = emailNotificationFlag;
  }

  public String getBatchMailId() {
    return batchMailId;
  }

  public void setBatchMailId(String batchMailId) {
    this.batchMailId = batchMailId;
  }

  public Boolean getPosTerminal() {
    return posTerminal;
  }
  
  public Boolean getVirtualTerminal() {
    return virtualTerminal;
  }

  public void setPosTerminal(Boolean posTerminal) {
    this.posTerminal = posTerminal;
  }
  
  public String getWebSiteAddress() {
    return webSiteAddress;
  }

  public Boolean getOnline() {
    return online;
  }
  
  public void setVirtualTerminal(Boolean virtualTerminal) {
    this.virtualTerminal = virtualTerminal;
  }

  public void setOnline(Boolean online) {
    this.online = online;
  }

  public String getReturnUrl() {
    return returnUrl;
  }
  
  public void setWebSiteAddress(String webSiteAddress) {
    this.webSiteAddress = webSiteAddress;
  }

  public void setReturnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
  }

  public String getCancelUrl() {
    return cancelUrl;
  }
  
  public Boolean getPayPageConfig() {
    return payPageConfig;
  }

  public void setCancelUrl(String cancelUrl) {
    this.cancelUrl = cancelUrl;
  }

  public Long getBankId() {
    return bankId;
  }
  
  public void setPayPageConfig(Boolean payPageConfig) {
    this.payPageConfig = payPageConfig;
  }
  
  public Long getResellerId() {
    return resellerId;
  }

  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public void setResellerId(Long resellerId) {
    this.resellerId = resellerId;
  }

  public Boolean getAllowAdvancedFraudFilter() {
    return allowAdvancedFraudFilter;
  }

  public void setAllowAdvancedFraudFilter(Boolean allowAdvancedFraudFilter) {
    this.allowAdvancedFraudFilter = allowAdvancedFraudFilter;
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

  public List<MerchantCurrencyMapping> getSelectedCurrencyMapping() {
    return selectedCurrencyMapping;
  }

  public void setSelectedCurrencyMapping(List<MerchantCurrencyMapping> selectedCurrencyMapping) {
    this.selectedCurrencyMapping = selectedCurrencyMapping;
  }

  public String getCurrencyId() {
    return currencyId;
  }

  public void setCurrencyId(String currencyId) {
    this.currencyId = currencyId;
  }

  public Integer getNmasRequired() {
    return nmasRequired;
  }

  public void setNmasRequired(Integer nmasRequired) {
    this.nmasRequired = nmasRequired;
  }

  public String getLocalCurrency() {
    return localCurrency;
  }

  public void setLocalCurrency(String localCurrency) {
    this.localCurrency = localCurrency;
  }

  public String getDeclineReason() {
    return declineReason;
  }

  public void setDeclineReason(String declineReason) {
    this.declineReason = declineReason;
  }

  public String getAgentAccountNumber() {
    return agentAccountNumber;
  }
  
  public void setAgentClientId(String agentClientId) {
    this.agentClientId = agentClientId;
  }

  public void setAgentAccountNumber(String agentAccountNumber) {
    this.agentAccountNumber = agentAccountNumber;
  }

  public String getAgentANI() {
    return agentANI;
  }
  
  public String getAgentClientId() {
    return agentClientId;
  }

  public void setAgentANI(String agentANI) {
    this.agentANI = agentANI;
  }

  public String getAgentName() {
    return agentName;
  }

  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }

  public Integer getSessionStatus() {
    return sessionStatus;
  }

  public void setSessionStatus(Integer sessionStatus) {
    this.sessionStatus = sessionStatus;
  }

  public String getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }

  public String getPartnerName() {
    return partnerName;
  }

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }
  
  public String getProgramManagerName() {
    return programManagerName;
  }

  public void setProgramManagerName(String programManagerName) {
    this.programManagerName = programManagerName;
  }

  public String getParentMerchantName() {
    return parentMerchantName;
  }

  public void setParentMerchantName(String parentMerchantName) {
    this.parentMerchantName = parentMerchantName;
  }

  public String getAssociatedTo() {
    return associatedTo;
  }

  public void setAssociatedTo(String associatedTo) {
    this.associatedTo = associatedTo;
  }

  public List<Long> getEntitiesId() {
    return entitiesId;
  }

  public void setEntitiesId(List<Long> entitiesId) {
    this.entitiesId = entitiesId;
  }

  public Long getEntityId() {
    return entityId;
  }

  public void setEntityId(Long entityId) {
    this.entityId = entityId;
  }

  public Long getCardProgramId() {
    return cardProgramId;
  }

  public void setCardProgramId(Long cardProgramId) {
    this.cardProgramId = cardProgramId;
  }

  public String getProcess() {
    return process;
  }

  public void setProcess(String process) {
    this.process = process;
  }

  public String getEntityType() {
    return entityType;
  }

  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

  public String getCardProgramName() {
    return cardProgramName;
  }

  public void setCardProgramName(String cardProgramName) {
    this.cardProgramName = cardProgramName;
  }

  public String getIsoName() {
    return isoName;
  }

  public void setIsoName(String isoName) {
    this.isoName = isoName;
  }

  /**
   * @return the cardProgramIds
   */
  public List<String> getCardProgramIds() {
    return cardProgramIds;
  }

  /**
   * @param cardProgramIds the cardProgramIds to set
   */
  public void setCardProgramIds(List<String> cardProgramIds) {
    this.cardProgramIds = cardProgramIds;
  }

  /**
   * @return the cardProgramAndEntityId
   */
  public Map<Long, Long> getCardProgramAndEntityId() {
    return cardProgramAndEntityId;
  }

  /**
   * @param cardProgramAndEntityId the cardProgramAndEntityId to set
   */
  public void setCardProgramAndEntityId(Map<Long, Long> cardProgramAndEntityId) {
    this.cardProgramAndEntityId = cardProgramAndEntityId;
  }


}
