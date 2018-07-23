package com.chatak.pg.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class MerchantRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String bankAccountName;

  private String bankRoutingNumber;

  private String bankAccountNumber;

  private String bankAccountType;

  private String bankAddress1;

  private String bankAddress2;

  private String bankCity;

  private String bankCountry;

  private String bankState;

  private String bankPin;

  private String bankNameOnAccount;

  private String legalName;

  private String legalTaxId;

  private String legalType;

  private String legalAnnualCard;

  private String legalAddress1;

  private String legalAddress2;

  private String legalCity;

  private String legalCountry;

  private String legalState;

  private String legalPin;

  private String legalFirstName;

  private String legalLastName;

  private String legalSSN;

  private String legalDOB;

  private String legalPassport;

  private String legalCountryResidence;

  private String legalCitizen;

  private String legalHomePhone;

  private String legalMobilePhone;

  private Integer pageIndex;

  private Boolean allowRepeatSale;

  private Boolean taxAmount;
  
  private String updatedDate;

  private Boolean shippingAmount;

  private Integer autoSettlement;

  private Boolean showRecurringBilling;

  private Boolean tipAmount;

  private String createdDate;

  private Boolean refunds;

  private Integer pageSize;

  private String autoTransferWeeklyDay;

  private String autoTransferMonthlyDay;

  private List<String> autoTransferMonthlyDayList;

  private List<String> autoTransferWeeklyDayList;

  private String litleMID;

  private String availableBalance;

  private String currentBalance;

  private String currency;

  private String accountStatus;

  private boolean transactionDiv;

  private boolean merchantFlag;

  private String lookingFor;

  private String businessType;

  private String agentId;

  private String issuancePartnerId;
  
  private Long id;
  
  private String businessName;

  private String merchantCode;
  
  private String emailId;
  
  private Long phone;
  
  private String programManagerName;
  
  private Integer status;
  
  private String city;

  private String country;
  
  private Long cardProgramId;
  
  private String cardProgramName;
  
  private String isoName;
  
  private Long isoid;
  
  private String entityType;
  
  private String state;
  
  private String action;
  
  private String entityName;
  
  public String getAutoTransferWeeklyDay() {
    return autoTransferWeeklyDay;
  }

  public void setAutoTransferWeeklyDay(String autoTransferWeeklyDay) {
    this.autoTransferWeeklyDay = autoTransferWeeklyDay;
  }

  public String getAutoTransferMonthlyDay() {
    return autoTransferMonthlyDay;
  }

  public void setAutoTransferMonthlyDay(String autoTransferMonthlyDay) {
    this.autoTransferMonthlyDay = autoTransferMonthlyDay;
  }

  public List<String> getAutoTransferMonthlyDayList() {
    return autoTransferMonthlyDayList;
  }

  public void setAutoTransferMonthlyDayList(List<String> autoTransferMonthlyDayList) {
    this.autoTransferMonthlyDayList = autoTransferMonthlyDayList;
  }

  public List<String> getAutoTransferWeeklyDayList() {
    return autoTransferWeeklyDayList;
  }

  public void setAutoTransferWeeklyDayList(List<String> autoTransferWeeklyDayList) {
    this.autoTransferWeeklyDayList = autoTransferWeeklyDayList;
  }

  public String getLitleMID() {
    return litleMID;
  }

  public void setLitleMID(String litleMID) {
    this.litleMID = litleMID;
  }

  public String getAvailableBalance() {
    return availableBalance;
  }

  public void setAvailableBalance(String availableBalance) {
    this.availableBalance = availableBalance;
  }

  public String getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrentBalance(String currentBalance) {
    this.currentBalance = currentBalance;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(String accountStatus) {
    this.accountStatus = accountStatus;
  }

  public boolean isTransactionDiv() {
    return transactionDiv;
  }

  public void setTransactionDiv(boolean transactionDiv) {
    this.transactionDiv = transactionDiv;
  }

  public boolean isMerchantFlag() {
    return merchantFlag;
  }

  public void setMerchantFlag(boolean merchantFlag) {
    this.merchantFlag = merchantFlag;
  }

  public String getLookingFor() {
    return lookingFor;
  }
  
  public String getBusinessType() {
    return businessType;
  }

  public void setLookingFor(String lookingFor) {
    this.lookingFor = lookingFor;
  }

  public String getAgentId() {
    return agentId;
  }

  public void setBusinessType(String businessType) {
    this.businessType = businessType;
  }

  public String getIssuancePartnerId() {
    return issuancePartnerId;
  }

  public void setAgentId(String agentId) {
    this.agentId = agentId;
  }

  public String getProgramManagerId() {
    return programManagerId;
  }

  public void setIssuancePartnerId(String issuancePartnerId) {
    this.issuancePartnerId = issuancePartnerId;
  }

  public void setProgramManagerId(String programManagerId) {
    this.programManagerId = programManagerId;
  }

  public Long getMerchantConfigId() {
    return merchantConfigId;
  }

  public void setMerchantConfigId(Long merchantConfigId) {
    this.merchantConfigId = merchantConfigId;
  }

  public String getAutoSettlementStatus() {
    return autoSettlementStatus;
  }

  public void setAutoSettlementStatus(String autoSettlementStatus) {
    this.autoSettlementStatus = autoSettlementStatus;
  }

  private String programManagerId;

  private Long merchantConfigId;

  private String autoSettlementStatus;

  public Boolean getRefunds() {
    return refunds;
  }

  public Boolean getTipAmount() {
    return tipAmount;
  }

  public void setRefunds(Boolean refunds) {
    this.refunds = refunds;
  }

  public Boolean getShippingAmount() {
    return shippingAmount;
  }

  public Boolean getTaxAmount() {
    return taxAmount;
  }
  
  public void setTaxAmount(Boolean taxAmount) {
    this.taxAmount = taxAmount;
  }

  public Boolean getAllowRepeatSale() {
    return allowRepeatSale;
  }

  public void setShippingAmount(Boolean shippingAmount) {
    this.shippingAmount = shippingAmount;
  }

  public Boolean getShowRecurringBilling() {
    return showRecurringBilling;
  }

  public void setAllowRepeatSale(Boolean allowRepeatSale) {
    this.allowRepeatSale = allowRepeatSale;
  }

  public void setTipAmount(Boolean tipAmount) {
    this.tipAmount = tipAmount;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setAutoSettlement(Integer autoSettlement) {
    this.autoSettlement = autoSettlement;
  }

  public String getUpdatedDate() {
    return updatedDate;
  }
  
  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public void setUpdatedDate(String updatedDate) {
    this.updatedDate = updatedDate;
  }

  public Integer getAutoSettlement() {
    return autoSettlement;
  }

  public void setShowRecurringBilling(Boolean showRecurringBilling) {
    this.showRecurringBilling = showRecurringBilling;
  }

  public Integer getPageSize() {
    return pageSize;
  }
  
  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getPageIndex() {
    return pageIndex;
  }

  public String getMerchantCallBackURL() {
    return merchantCallBackURL;
  }
  
  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  public void setMerchantCallBackURL(String merchantCallBackURL) {
    this.merchantCallBackURL = merchantCallBackURL;
  }

  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public BigDecimal getAutoTransferLimit() {
    return autoTransferLimit;
  }
  
  public String getAutoTransferDay() {
    return autoTransferDay;
  }

  public String getCategory() {
    return category;
  }

  public String getAutoPaymentMethod() {
    return autoPaymentMethod;
  }

  public void setAutoPaymentMethod(String autoPaymentMethod) {
    this.autoPaymentMethod = autoPaymentMethod;
  }

  public void setAutoTransferLimit(BigDecimal autoTransferLimit) {
    this.autoTransferLimit = autoTransferLimit;
  }

  public void setAutoTransferDay(String autoTransferDay) {
    this.autoTransferDay = autoTransferDay;
  }

  private Integer noOfRecords;

  private String merchantCallBackURL;

  private String category;

  private BigDecimal autoTransferLimit;

  private String autoPaymentMethod;

  private String autoTransferDay;

  public String getBankAccountName() {
    return bankAccountName;
  }

  public void setBankAccountName(String bankAccountName) {
    this.bankAccountName = bankAccountName;
  }

  public String getBankRoutingNumber() {
    return bankRoutingNumber;
  }

  public void setBankRoutingNumber(String bankRoutingNumber) {
    this.bankRoutingNumber = bankRoutingNumber;
  }

  public String getBankAccountNumber() {
    return bankAccountNumber;
  }

  public void setBankAccountNumber(String bankAccountNumber) {
    this.bankAccountNumber = bankAccountNumber;
  }

  public String getBankAccountType() {
    return bankAccountType;
  }

  public void setBankAccountType(String bankAccountType) {
    this.bankAccountType = bankAccountType;
  }

  public void setBankAddress1(String bankAddress1) {
    this.bankAddress1 = bankAddress1;
  }
  
  public String getBankCountry() {
    return bankCountry;
  }

  public String getBankAddress2() {
    return bankAddress2;
  }
  
  public String getBankCity() {
    return bankCity;
  }

  public void setBankAddress2(String bankAddress2) {
    this.bankAddress2 = bankAddress2;
  }
  
  public String getBankAddress1() {
    return bankAddress1;
  }

  public void setBankCity(String bankCity) {
    this.bankCity = bankCity;
  }

  public void setBankCountry(String bankCountry) {
    this.bankCountry = bankCountry;
  }

  public String getBankState() {
    return bankState;
  }

  public void setBankState(String bankState) {
    this.bankState = bankState;
  }

  public String getBankPin() {
    return bankPin;
  }

  public void setBankPin(String bankPin) {
    this.bankPin = bankPin;
  }

  public String getBankNameOnAccount() {
    return bankNameOnAccount;
  }

  public void setBankNameOnAccount(String bankNameOnAccount) {
    this.bankNameOnAccount = bankNameOnAccount;
  }

  public String getLegalName() {
    return legalName;
  }

  public void setLegalName(String legalName) {
    this.legalName = legalName;
  }

  public String getLegalTaxId() {
    return legalTaxId;
  }

  public void setLegalTaxId(String legalTaxId) {
    this.legalTaxId = legalTaxId;
  }

  public String getLegalType() {
    return legalType;
  }

  public void setLegalType(String legalType) {
    this.legalType = legalType;
  }

  public String getLegalAnnualCard() {
    return legalAnnualCard;
  }

  public void setLegalAnnualCard(String legalAnnualCard) {
    this.legalAnnualCard = legalAnnualCard;
  }

  public String getLegalAddress1() {
    return legalAddress1;
  }

  public void setLegalAddress1(String legalAddress1) {
    this.legalAddress1 = legalAddress1;
  }

  public String getLegalAddress2() {
    return legalAddress2;
  }

  public void setLegalAddress2(String legalAddress2) {
    this.legalAddress2 = legalAddress2;
  }

  public String getLegalCity() {
    return legalCity;
  }

  public void setLegalCity(String legalCity) {
    this.legalCity = legalCity;
  }

  public String getLegalCountry() {
    return legalCountry;
  }

  public void setLegalCountry(String legalCountry) {
    this.legalCountry = legalCountry;
  }

  public String getLegalState() {
    return legalState;
  }

  public void setLegalState(String legalState) {
    this.legalState = legalState;
  }

  public String getLegalPin() {
    return legalPin;
  }

  public void setLegalPin(String legalPin) {
    this.legalPin = legalPin;
  }

  public String getLegalFirstName() {
    return legalFirstName;
  }

  public void setLegalFirstName(String legalFirstName) {
    this.legalFirstName = legalFirstName;
  }

  public String getLegalLastName() {
    return legalLastName;
  }

  public void setLegalLastName(String legalLastName) {
    this.legalLastName = legalLastName;
  }

  public String getLegalSSN() {
    return legalSSN;
  }

  public void setLegalSSN(String legalSSN) {
    this.legalSSN = legalSSN;
  }

  public String getLegalDOB() {
    return legalDOB;
  }

  public void setLegalDOB(String legalDOB) {
    this.legalDOB = legalDOB;
  }

  public String getLegalPassport() {
    return legalPassport;
  }

  public void setLegalPassport(String legalPassport) {
    this.legalPassport = legalPassport;
  }

  public String getLegalCountryResidence() {
    return legalCountryResidence;
  }

  public void setLegalCountryResidence(String legalCountryResidence) {
    this.legalCountryResidence = legalCountryResidence;
  }

  public String getLegalCitizen() {
    return legalCitizen;
  }

  public void setLegalCitizen(String legalCitizen) {
    this.legalCitizen = legalCitizen;
  }

  public String getLegalHomePhone() {
    return legalHomePhone;
  }

  public void setLegalHomePhone(String legalHomePhone) {
    this.legalHomePhone = legalHomePhone;
  }

  public String getLegalMobilePhone() {
    return legalMobilePhone;
  }

  public void setLegalMobilePhone(String legalMobilePhone) {
    this.legalMobilePhone = legalMobilePhone;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBusinessName() {
    return businessName;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public String getEmailId() {
    return emailId;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public Long getPhone() {
    return phone;
  }

  public void setPhone(Long phone) {
    this.phone = phone;
  }

  public String getProgramManagerName() {
    return programManagerName;
  }

  public void setProgramManagerName(String programManagerName) {
    this.programManagerName = programManagerName;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
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

  public void setCountry(String country) {
    this.country = country;
  }

  public Long getCardProgramId() {
    return cardProgramId;
  }

  public void setCardProgramId(Long cardProgramId) {
    this.cardProgramId = cardProgramId;
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

  public Long getIsoid() {
    return isoid;
  }

  public void setIsoid(Long isoid) {
    this.isoid = isoid;
  }

  public String getEntityType() {
    return entityType;
  }

  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getEntityName() {
    return entityName;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }
}
