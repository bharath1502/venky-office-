package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PG_MERCHANT")
public class PGMerchant implements Serializable {

  /**
  * 
  */
  private static final long serialVersionUID = 3377782740320110549L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_MERCHANT_ID", sequenceName = "SEQ_PG_MERCHANT")
  @GeneratedValue(generator = "SEQ_PG_MERCHANT_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "MERCHANT_ID", referencedColumnName = "ID")
  private Set<PGMerchantEntityMap> pgMerchantEntityMaps;
  
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "MERCHANT_ID", referencedColumnName = "ID")
  private Set<PGMerchantCardProgramMap> pgMerchantCardProgramMaps;
  
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "MER_CONFIG_ID")
  PGMerchantConfig merchantConfig;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "PG_MERCHANT_ID")
  private List<PGMerchantUsers> pgMerchantUsers;
  
  @Column(name = "MERCHANT_CODE")
  private String merchantCode;

  @Column(name = "BUSINESS_NAME")
  private String businessName;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "ADDRESS1")
  private String address1;
  
  @Column(name = "PHONE")
  private Long phone;

  @Column(name = "ADDRESS2")
  private String address2;

  @Column(name = "CITY")
  private String city;
  
  @Column(name = "FAX")
  private Long fax;

  @Column(name = "STATE")
  private String state;

  @Column(name = "COUNTRY")
  private String country;
  
  @Column(name = "EMAIL")
  private String emailId;

  @Column(name = "PIN")
  private String pin;

  @Column(name = "TIMEZONE")
  private String timeZone;

  @Column(name = "APPLICATION_MODE")
  private String appMode;

  @Column(name = "USER_NAME")
  private String userName;

  @Column(name = "BUSINESS_URL")
  private String businessURL;

  @Column(name = "FEDERAL_TAX_ID")
  private String federalTaxId;

  @Column(name = "STATE_TAX_ID")
  private String stateTaxId;

  @Column(name = "SALES_TAX_ID")
  private String salesTaxId;

  @Column(name = "OWNERSHIP")
  private String ownership;

  @Column(name = "ESTIMATED_YEAR_SALE")
  private Long estimatedYearlySale;

  @Column(name = "BUSINESS_START_DATE")
  private Timestamp businessStartDate;

  @Column(name = "NO_OF_EMPLOYEE")
  private String noOfEmployee;

  @Column(name = "ROLE")
  private String role;

  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;

  @Column(name = "STATUS")
  private Integer status;

  @Column(name = "DCC_ENABLE")
  private Boolean dccEnable;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  @Column(name = "MERCHANT_CALLBACK_URL")
  private String merchantCallBack;

  @Column(name = "MERCHANT_TYPE")
  private String merchantType;

  @Column(name = "PARENT_MERCHANT_ID")
  private Long parentMerchantId;

  @Column(name = "LITLE_MID")
  private String litleMID;

  @Column(name = "LOOKING_FOR")
  private String lookingFor;

  @Column(name = "BUSINESS_TYPE")
  private String businessType;

  @Column(name = "AGENT_ID")
  private String agentId;

  @Column(name = "MERCHANT_CATEGORY")
  private String merchantCategory;

  @Column(name = "CREATED_BY")
  private String createdBy;

  @Column(name = "MCC")
  private String mcc;

  @Column(name = "LOCAL_CURRENCY")
  private String localCurrency;

  @Column(name = "AGENT_ACC_NUMBER")
  private String agentAccountNumber;

  @Column(name = "AGENT_CLIENT_ID")
  private String agentClientId;

  @Column(name = "AGENT_ANI")
  private String agentANI;

  @Column(name = "ALLOW_ADVANCED_FRAUD_FILTER")
  private Integer allowAdvancedFraudFilter;

  @Column(name = "BANK_ID")
  private Long bankId;

  @Column(name = "RESELLER_ID")
  private Long resellerId;

  @Column(name = "DECLINED_REASON")
  private String declineReason;

  @Column(name = "REASON")
  private String reason;
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PGMerchantConfig getMerchantConfig() {
    return merchantConfig;
  }

  public void setMerchantConfig(PGMerchantConfig merchantConfig) {
    this.merchantConfig = merchantConfig;
  }

  public List<PGMerchantUsers> getPgMerchantUsers() {
    return pgMerchantUsers;
  }

  public void setPgMerchantUsers(List<PGMerchantUsers> pgMerchantUsers) {
    this.pgMerchantUsers = pgMerchantUsers;
  }

  public String getMerchantCode() {
    return merchantCode;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public String getBusinessName() {
    return businessName;
  }
  
  public String getLastName() {
    return lastName;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Long getPhone() {
    return phone;
  }
  
  public String getAddress1() {
    return address1;
  }

  public void setPhone(Long phone) {
    this.phone = phone;
  }

  public Long getFax() {
    return fax;
  }
  
  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public void setFax(Long fax) {
    this.fax = fax;
  }

  public String getEmailId() {
    return emailId;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }
  
  public String getCountry() {
    return country;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getCity() {
    return city;
  }
  
  public void setState(String state) {
    this.state = state;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPin() {
    return pin;
  }
  
  public void setAppMode(String appMode) {
    this.appMode = appMode;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  public String getTimeZone() {
    return timeZone;
  }
  
  public String getUserName() {
    return userName;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  public String getAppMode() {
    return appMode;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public void setRole(String role) {
    this.role = role;
  }

  public String getBusinessURL() {
    return businessURL;
  }

  public void setBusinessURL(String businessURL) {
    this.businessURL = businessURL;
  }
  
  public String getRole() {
    return role;
  }

  public String getFederalTaxId() {
    return federalTaxId;
  }

  public void setFederalTaxId(String federalTaxId) {
    this.federalTaxId = federalTaxId;
  }
  
  public void setNoOfEmployee(String noOfEmployee) {
    this.noOfEmployee = noOfEmployee;
  }

  public String getStateTaxId() {
    return stateTaxId;
  }

  public void setStateTaxId(String stateTaxId) {
    this.stateTaxId = stateTaxId;
  }
  
  public String getNoOfEmployee() {
    return noOfEmployee;
  }

  public String getSalesTaxId() {
    return salesTaxId;
  }

  public void setSalesTaxId(String salesTaxId) {
    this.salesTaxId = salesTaxId;
  }
  
  public void setBusinessStartDate(Timestamp businessStartDate) {
    this.businessStartDate = businessStartDate;
  }

  public String getOwnership() {
    return ownership;
  }

  public void setOwnership(String ownership) {
    this.ownership = ownership;
  }
  
  public Timestamp getBusinessStartDate() {
    return businessStartDate;
  }

  public Long getEstimatedYearlySale() {
    return estimatedYearlySale;
  }

  public void setEstimatedYearlySale(Long estimatedYearlySale) {
    this.estimatedYearlySale = estimatedYearlySale;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Boolean getDccEnable() {
    return dccEnable;
  }

  public void setDccEnable(Boolean dccEnable) {
    this.dccEnable = dccEnable;
  }

  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getMerchantCallBack() {
    return merchantCallBack;
  }

  public void setMerchantCallBack(String merchantCallBack) {
    this.merchantCallBack = merchantCallBack;
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

  public String getLitleMID() {
    return litleMID;
  }

  public void setLookingFor(String lookingFor) {
    this.lookingFor = lookingFor;
  }

  public String getBusinessType() {
    return businessType;
  }

  public void setBusinessType(String businessType) {
    this.businessType = businessType;
  }

  public String getAgentId() {
    return agentId;
  }
  

  
  public void setLitleMID(String litleMID) {
    this.litleMID = litleMID;
  }

  public void setAgentId(String agentId) {
    this.agentId = agentId;
  }


  public String getLookingFor() {
    return lookingFor;
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

  public String getMcc() {
    return mcc;
  }

  public void setMcc(String mcc) {
    this.mcc = mcc;
  }

  public String getLocalCurrency() {
    return localCurrency;
  }
  
  public String getAgentANI() {
	    return agentANI;
	  }

  public void setLocalCurrency(String localCurrency) {
    this.localCurrency = localCurrency;
  }
  
  public String getAgentClientId() {
	    return agentClientId;
	  }

  public void setAgentAccountNumber(String agentAccountNumber) {
    this.agentAccountNumber = agentAccountNumber;
  }

  public void setAgentClientId(String agentClientId) {
    this.agentClientId = agentClientId;
  }

  public void setAgentANI(String agentANI) {
    this.agentANI = agentANI;
  }
  
  public String getAgentAccountNumber() {
	    return agentAccountNumber;
	  }

  public Integer getAllowAdvancedFraudFilter() {
    return allowAdvancedFraudFilter;
  }

  public void setAllowAdvancedFraudFilter(Integer allowAdvancedFraudFilter) {
    this.allowAdvancedFraudFilter = allowAdvancedFraudFilter;
  }

  public Long getBankId() {
    return bankId;
  }

  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public Long getResellerId() {
    return resellerId;
  }

  public void setResellerId(Long resellerId) {
    this.resellerId = resellerId;
  }

  public String getDeclineReason() {
    return declineReason;
  }

  public void setDeclineReason(String declineReason) {
    this.declineReason = declineReason;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public Set<PGMerchantEntityMap> getPgMerchantEntityMaps() {
		return pgMerchantEntityMaps;
  }

  public void setPgMerchantEntityMaps(Set<PGMerchantEntityMap> pgMerchantEntityMaps) {
		this.pgMerchantEntityMaps = pgMerchantEntityMaps;
  }

  public Set<PGMerchantCardProgramMap> getPgMerchantCardProgramMaps() {
		return pgMerchantCardProgramMaps;
  }

  public void setPgMerchantCardProgramMaps(Set<PGMerchantCardProgramMap> pgMerchantCardProgramMaps) {
		this.pgMerchantCardProgramMaps = pgMerchantCardProgramMaps;
  }
}
