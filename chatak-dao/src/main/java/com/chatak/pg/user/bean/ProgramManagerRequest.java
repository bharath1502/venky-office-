package com.chatak.pg.user.bean;

import java.sql.Timestamp;
import java.util.List;

import com.chatak.pg.bean.SearchRequest;

public class ProgramManagerRequest extends SearchRequest {

  private static final long serialVersionUID = -3348275668511102325L;

  private String userType;

  private Long id;

  private Boolean defaultProgramManager;

  private Long programManagerId;

  private String companyName;

  private String businessName;

  private String programManagerName;

  private String contactName;

  private String contactPhone;

  private String contactEmail;

  private String status;

  private String reason;

  private byte[] programManagerLogo;

  private String bankNames;

  private Timestamp createdDate;

  private Timestamp updatedDate;

  private String updatedBy;

  private String createdBy;

  private List<String> statuses;

  private List<BankProgramManagerMapRequest> bankProgramManagerMapRequests;

  private List<PartnerRequest> partnerRequests;

  private List<ProgramManagerAccountRequest> programManagerAccountRequests;

  private List<Long> programManagerIds;

  private String mccCode;

  private String extension;

  private String accountType;

  private Long programManagerAccountId;

  private Double availableBalance;

  private Double currentBalance;

  private String nickName;

  private Long pmAccountNumber;

  private Double accountThresholdLimit;

  private Boolean autoRepenish;

  private Long bankId;

  private String sendFundsMode;

  private String accountCurrency;

  private String currencyCodeAlpha;

  private List<BankRequest> bankRequest;
  
  private CurrencyConfigRequest currencyConfigRequest;
  
  private String issuanceProgramManagerLogo;
  
  private String programManagerType;
  
  private String acquirerCurrencyName;
  
  private String acquirerBankName;
  /**
   * @return the extension
   */
  public String getExtension() {
    return extension;
  }

  /**
   * @param extension
   *          the extension to set
   */
  public void setExtension(String extension) {
    this.extension = extension;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProgramManagerId() {
    return programManagerId;
  }

  public void setProgramManagerId(Long programManagerId) {
    this.programManagerId = programManagerId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getBusinessName() {
    return businessName;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public String getProgramManagerName() {
    return programManagerName;
  }

  public void setProgramManagerName(String programManagerName) {
    this.programManagerName = programManagerName;
  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getBankNames() {
    return bankNames;
  }

  public void setBankNames(String bankNames) {
    this.bankNames = bankNames;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public List<String> getStatuses() {
    return statuses;
  }

  public void setStatuses(List<String> statuses) {
    this.statuses = statuses;
  }

  public byte[] getProgramManagerLogo() {
    return programManagerLogo;
  }

  public void setProgramManagerLogo(byte[] programManagerLogo) {
    this.programManagerLogo = programManagerLogo;
  }

  /**
   * @return the programManagerIds
   */
  public List<Long> getProgramManagerIds() {
    return programManagerIds;
  }

  /**
   * @param programManagerIds
   *          the programManagerIds to set
   */
  public void setProgramManagerIds(List<Long> programManagerIds) {
    this.programManagerIds = programManagerIds;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public String getMccCode() {
    return mccCode;
  }

  public void setMccCode(String mccCode) {
    this.mccCode = mccCode;
  }

  /**
   * @return the accountType
   */
  public String getAccountType() {
    return accountType;
  }

  /**
   * @param accountType
   *          the accountType to set
   */
  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  /**
   * @return the programManagerAccountId
   */
  public Long getProgramManagerAccountId() {
    return programManagerAccountId;
  }

  /**
   * @param programManagerAccountId
   *          the programManagerAccountId to set
   */
  public void setProgramManagerAccountId(Long programManagerAccountId) {
    this.programManagerAccountId = programManagerAccountId;
  }

  /**
   * @return the availableBalance
   */
  public Double getAvailableBalance() {
    return availableBalance;
  }

  /**
   * @param availableBalance
   *          the availableBalance to set
   */
  public void setAvailableBalance(Double availableBalance) {
    this.availableBalance = availableBalance;
  }

  /**
   * @return the nickName
   */
  public String getNickName() {
    return nickName;
  }

  /**
   * @param nickName
   *          the nickName to set
   */
  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  /**
   * @return the currentBalance
   */
  public Double getCurrentBalance() {
    return currentBalance;
  }

  /**
   * @param currentBalance
   *          the currentBalance to set
   */
  public void setCurrentBalance(Double currentBalance) {
    this.currentBalance = currentBalance;
  }

  /**
   * @return the pmAccountNumber
   */
  public Long getPmAccountNumber() {
    return pmAccountNumber;
  }

  /**
   * @param pmAccountNumber
   *          the pmAccountNumber to set
   */
  public void setPmAccountNumber(Long pmAccountNumber) {
    this.pmAccountNumber = pmAccountNumber;
  }

  /**
   * @return the accountThresholdLimit
   */
  public Double getAccountThresholdLimit() {
    return accountThresholdLimit;
  }

  /**
   * @param accountThresholdLimit
   *          the accountThresholdLimit to set
   */
  public void setAccountThresholdLimit(Double accountThresholdLimit) {
    this.accountThresholdLimit = accountThresholdLimit;
  }

  /**
   * @return the autoRepenish
   */
  public Boolean getAutoRepenish() {
    return autoRepenish;
  }

  /**
   * @param autoRepenish
   *          the autoRepenish to set
   */
  public void setAutoRepenish(Boolean autoRepenish) {
    this.autoRepenish = autoRepenish;
  }

  public Long getBankId() {
    return bankId;
  }

  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public String getSendFundsMode() {
    return sendFundsMode;
  }

  public void setSendFundsMode(String sendFundsMode) {
    this.sendFundsMode = sendFundsMode;
  }

  public Boolean getDefaultProgramManager() {
    return defaultProgramManager;
  }

  public void setDefaultProgramManager(Boolean defaultProgramManager) {
    this.defaultProgramManager = defaultProgramManager;
  }

  public String getAccountCurrency() {
    return accountCurrency;
  }

  public void setAccountCurrency(String accountCurrency) {
    this.accountCurrency = accountCurrency;
  }

  public String getCurrencyCodeAlpha() {
    return currencyCodeAlpha;
  }

  public void setCurrencyCodeAlpha(String currencyCodeAlpha) {
    this.currencyCodeAlpha = currencyCodeAlpha;
  }

  public List<BankProgramManagerMapRequest> getBankProgramManagerMapRequests() {
    return bankProgramManagerMapRequests;
  }

  public void setBankProgramManagerMapRequests(
      List<BankProgramManagerMapRequest> bankProgramManagerMapRequests) {
    this.bankProgramManagerMapRequests = bankProgramManagerMapRequests;
  }

  public List<PartnerRequest> getPartnerRequests() {
    return partnerRequests;
  }

  public void setPartnerRequests(List<PartnerRequest> partnerRequests) {
    this.partnerRequests = partnerRequests;
  }

  public List<ProgramManagerAccountRequest> getProgramManagerAccountRequests() {
    return programManagerAccountRequests;
  }

  public void setProgramManagerAccountRequests(
      List<ProgramManagerAccountRequest> programManagerAccountRequests) {
    this.programManagerAccountRequests = programManagerAccountRequests;
  }

/**
 * @return the bankRequest
 */
public List<BankRequest> getBankRequest() {
	return bankRequest;
}

/**
 * @param bankRequest the bankRequest to set
 */
public void setBankRequest(List<BankRequest> bankRequest) {
	this.bankRequest = bankRequest;
}

/**
 * @return the currencyConfigRequest
 */
public CurrencyConfigRequest getCurrencyConfigRequest() {
	return currencyConfigRequest;
}

/**
 * @param currencyConfigRequest the currencyConfigRequest to set
 */
public void setCurrencyConfigRequest(CurrencyConfigRequest currencyConfigRequest) {
	this.currencyConfigRequest = currencyConfigRequest;
}

/**
 * @return the issuanceProgramManagerLogo
 */
public String getIssuanceProgramManagerLogo() {
	return issuanceProgramManagerLogo;
}

/**
 * @param issuanceProgramManagerLogo the issuanceProgramManagerLogo to set
 */
public void setIssuanceProgramManagerLogo(String issuanceProgramManagerLogo) {
	this.issuanceProgramManagerLogo = issuanceProgramManagerLogo;
}

/**
 * @return the programManagerType
 */
public String getProgramManagerType() {
	return programManagerType;
}

/**
 * @param programManagerType the programManagerType to set
 */
public void setProgramManagerType(String programManagerType) {
	this.programManagerType = programManagerType;
}

/**
 * @return the acquirerCurrencyName
 */
public String getAcquirerCurrencyName() {
	return acquirerCurrencyName;
}

/**
 * @param acquirerCurrencyName the acquirerCurrencyName to set
 */
public void setAcquirerCurrencyName(String acquirerCurrencyName) {
	this.acquirerCurrencyName = acquirerCurrencyName;
}

/**
 * @return the acquirerBankName
 */
public String getAcquirerBankName() {
	return acquirerBankName;
}

/**
 * @param acquirerBankName the acquirerBankName to set
 */
public void setAcquirerBankName(String acquirerBankName) {
	this.acquirerBankName = acquirerBankName;
}

}
