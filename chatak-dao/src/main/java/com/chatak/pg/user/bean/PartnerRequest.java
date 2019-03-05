package com.chatak.pg.user.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.chatak.pg.bean.SearchRequest;

public class PartnerRequest extends SearchRequest implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -882201936202428952L;

  private Long partnerId;

  private String partnerName;

  private String companyName;

  private String businessEntityName;

  private String address1;

  private String address2;

  private String city;

  private String zip;

  private String state;

  private String country;

  private byte[] partnerLogo;

  private String contactPerson;

  private String contactPhone;

  private String contactEmail;

  private String websiteUrl;

  private String status;

  private String accountType;

  private Timestamp createdDate;

  private Timestamp updatedDate;

  private Long bankId;

  private String bankName;

  private String reason;

  private List<String> statusList;

  private List<String> partnerTypeList;

  private Long salesAgentId;

  private String sendFundsMode;

  private String partnerGroupName;

  private List<BankPartnerMapRequest> bankPartnerMapRequests;

  private List<PartnerAccountRequest> partnerAccountRequests;

  private ProgramManagerRequest programManagerRequest;

  private String mccCode;

  private String partnerClientId;

  private boolean defaultPartnerAPI;

  private String whiteListIPAddress;

  private String extension;

  private String partnerType;

  private Long partnerAccountId;

  private Double availableBalance;

  private Double currentBalance;

  private String nickName;

  private Long accountNumber;

  private Double accountThresholdamount;

  private Boolean autoRepenish;

  private String accountCurrency;

  private String currencyCodeAlpha;

  private List<BankRequest> bankRequests;

  private String programManagerId;

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

  /**
   * @return the partnerId
   */
  @Override
  public Long getPartnerId() {
    return partnerId;
  }

  public String getMccCode() {
    return mccCode;
  }

  public void setMccCode(String mccCode) {
    this.mccCode = mccCode;
  }

  /**
   * @param partnerId
   *          the partnerId to set
   */
  @Override
  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  /**
   * @return the partnerName
   */
  @Override
  public String getPartnerName() {
    return partnerName;
  }

  /**
   * @param partnerName
   *          the partnerName to set
   */
  @Override
  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  /**
   * @return the companyName
   */
  public String getCompanyName() {
    return companyName;
  }

  /**
   * @param companyName
   *          the companyName to set
   */
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  /**
   * @return the businessEntityName
   */
  public String getBusinessEntityName() {
    return businessEntityName;
  }

  /**
   * @param businessEntityName
   *          the businessEntityName to set
   */
  public void setBusinessEntityName(String businessEntityName) {
    this.businessEntityName = businessEntityName;
  }

  /**
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }

  /**
   * @param address1
   *          the address1 to set
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
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
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @param city
   *          the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the zip
   */
  public String getZip() {
    return zip;
  }

  /**
   * @param zip
   *          the zip to set
   */
  public void setZip(String zip) {
    this.zip = zip;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @param state
   *          the state to set
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * @param country
   *          the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * @return the partnerLogo
   */
  public byte[] getPartnerLogo() {
    return partnerLogo;
  }

  /**
   * @param partnerLogo
   *          the partnerLogo to set
   */
  public void setPartnerLogo(byte[] partnerLogo) {
    this.partnerLogo = partnerLogo;
  }

  /**
   * @return the contactPerson
   */
  public String getContactPerson() {
    return contactPerson;
  }

  /**
   * @param contactPerson
   *          the contactPerson to set
   */
  public void setContactPerson(String contactPerson) {
    this.contactPerson = contactPerson;
  }

  /**
   * @return the contactPhone
   */
  public String getContactPhone() {
    return contactPhone;
  }

  /**
   * @param contactPhone
   *          the contactPhone to set
   */
  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  /**
   * @return the contactEmail
   */
  public String getContactEmail() {
    return contactEmail;
  }

  /**
   * @param contactEmail
   *          the contactEmail to set
   */
  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  /**
   * @return the websiteUrl
   */
  public String getWebsiteUrl() {
    return websiteUrl;
  }

  /**
   * @param websiteUrl
   *          the websiteUrl to set
   */
  public void setWebsiteUrl(String websiteUrl) {
    this.websiteUrl = websiteUrl;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(String status) {
    this.status = status;
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
   * @return the createdDate
   */
  @Override
  public Timestamp getCreatedDate() {
    return createdDate;
  }

  /**
   * @param createdDate
   *          the createdDate to set
   */
  @Override
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * @return the updatedDate
   */
  @Override
  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  /**
   * @param updatedDate
   *          the updatedDate to set
   */
  @Override
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  /**
   * @return the bankId
   */
  public Long getBankId() {
    return bankId;
  }

  /**
   * @param bankId
   *          the bankId to set
   */
  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  /**
   * @return the bankName
   */
  public String getBankName() {
    return bankName;
  }

  /**
   * @param bankName
   *          the bankName to set
   */
  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  /**
   * @return the reason
   */
  @Override
  public String getReason() {
    return reason;
  }

  /**
   * @param reason
   *          the reason to set
   */
  @Override
  public void setReason(String reason) {
    this.reason = reason;
  }

  /**
   * @return the statusList
   */
  public List<String> getStatusList() {
    return statusList;
  }

  /**
   * @param statusList
   *          the statusList to set
   */
  public void setStatusList(List<String> statusList) {
    this.statusList = statusList;
  }

  /**
   * @return the salesAgentId
   */
  public Long getSalesAgentId() {
    return salesAgentId;
  }

  /**
   * @param salesAgentId
   *          the salesAgentId to set
   */
  public void setSalesAgentId(Long salesAgentId) {
    this.salesAgentId = salesAgentId;
  }

  /**
   * @return the programManagerRequest
   */
  public ProgramManagerRequest getProgramManagerRequest() {
    return programManagerRequest;
  }

  /**
   * @param programManagerRequest
   *          the programManagerRequest to set
   */
  public void setProgramManagerRequest(ProgramManagerRequest programManagerRequest) {
    this.programManagerRequest = programManagerRequest;
  }

  public String getPartnerClientId() {
    return partnerClientId;
  }

  public void setPartnerClientId(String partnerClientId) {
    this.partnerClientId = partnerClientId;
  }

  public boolean isDefaultPartnerAPI() {
    return defaultPartnerAPI;
  }

  public void setDefaultPartnerAPI(boolean defaultPartnerAPI) {
    this.defaultPartnerAPI = defaultPartnerAPI;
  }

  public String getWhiteListIPAddress() {
    return whiteListIPAddress;
  }

  public void setWhiteListIPAddress(String whiteListIPAddress) {
    this.whiteListIPAddress = whiteListIPAddress;
  }

  /**
   * @return the partnerType
   */
  public String getPartnerType() {
    return partnerType;
  }

  /**
   * @param partnerType
   *          the partnerType to set
   */
  public void setPartnerType(String partnerType) {
    this.partnerType = partnerType;
  }

  /**
   * @return the partnerTypeList
   */
  public List<String> getPartnerTypeList() {
    return partnerTypeList;
  }

  /**
   * @param partnerTypeList
   *          the partnerTypeList to set
   */
  public void setPartnerTypeList(List<String> partnerTypeList) {
    this.partnerTypeList = partnerTypeList;
  }

  /**
   * @return the partnerAccountId
   */
  public Long getPartnerAccountId() {
    return partnerAccountId;
  }

  /**
   * @param partnerAccountId
   *          the partnerAccountId to set
   */
  public void setPartnerAccountId(Long partnerAccountId) {
    this.partnerAccountId = partnerAccountId;
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
   * @return the accountNumber
   */
  public Long getAccountNumber() {
    return accountNumber;
  }

  /**
   * @param accountNumber
   *          the accountNumber to set
   */
  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   * @return the accountThresholdamount
   */
  public Double getAccountThresholdamount() {
    return accountThresholdamount;
  }

  /**
   * @param accountThresholdamount
   *          the accountThresholdamount to set
   */
  public void setAccountThresholdamount(Double accountThresholdamount) {
    this.accountThresholdamount = accountThresholdamount;
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

  public String getSendFundsMode() {
    return sendFundsMode;
  }

  public void setSendFundsMode(String sendFundsMode) {
    this.sendFundsMode = sendFundsMode;
  }

  public String getPartnerGroupName() {
    return partnerGroupName;
  }

  public void setPartnerGroupName(String partnerGroupName) {
    this.partnerGroupName = partnerGroupName;
  }

  public String getAccountCurrency() {
    return accountCurrency;
  }

  public void setAccountCurrency(String accountCurrency) {
    this.accountCurrency = accountCurrency;
  }

  @Override
  public String getCurrencyCodeAlpha() {
    return currencyCodeAlpha;
  }

  @Override
  public void setCurrencyCodeAlpha(String currencyCodeAlpha) {
    this.currencyCodeAlpha = currencyCodeAlpha;
  }

  public List<BankPartnerMapRequest> getBankPartnerMapRequests() {
    return bankPartnerMapRequests;
  }

  public void setBankPartnerMapRequests(List<BankPartnerMapRequest> bankPartnerMapRequests) {
    this.bankPartnerMapRequests = bankPartnerMapRequests;
  }

  public List<PartnerAccountRequest> getPartnerAccountRequests() {
    return partnerAccountRequests;
  }

  public void setPartnerAccountRequests(List<PartnerAccountRequest> partnerAccountRequests) {
    this.partnerAccountRequests = partnerAccountRequests;
  }

  public List<BankRequest> getBankRequests() {
    return bankRequests;
  }

  public void setBankRequests(List<BankRequest> bankRequests) {
    this.bankRequests = bankRequests;
  }

  public String getProgramManagerId() {
    return programManagerId;
  }

  public void setProgramManagerId(String programManagerId) {
    this.programManagerId = programManagerId;
  }

}
