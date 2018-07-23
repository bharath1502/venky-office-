package com.chatak.merchant.model;

import java.io.Serializable;

public class UserProfileRequest implements Serializable {
	
  private Long userId;

  private Long merchantUserId;
  
  private Long merchantId;

  private String roleName;

  private String userName;

  private String firstName;

  private String lastName;

  private String emailId;

  private String phone;

  private Long roleId;

  /* Company information */
  private String companyName;

  private String entityLegalName;

  private String taxId;

  private String type;

  private String businessAs;

  private String companyEmailAddress;

  private String officePhone;

  private String fax;
  
  private String companyURL;

  private String annualCardSales;

  /* Physical Address */

  private String phyAddress1;

  private String phyAddress2;

  private String phyCity;

  private String phyState;

  private String phyZipCode;

  private String phyCountry;

  /* Mailing address */

  private String mailAddress1;

  private String mailAddress2;

  private String mailCity;

  private String mailState;

  private String mailZipCode;

  private String mailCountry;

  /* Company primary contact */

  private String primaryName;

  private String primaryEmailId;

  private String primaryContactPhone;

  /**
   * @return the merchantUserId
   */
  public Long getMerchantUserId() {
    return merchantUserId;
  }

  /**
   * @param merchantUserId
   *          the merchantUserId to set
   */
  public void setMerchantUserId(Long merchantUserId) {
    this.merchantUserId = merchantUserId;
  }

  public Long getMerchantId() {
	return merchantId;
  }

  public void setMerchantId(Long merchantId) {
	this.merchantId = merchantId;
  }

  /**
   * @return the userId
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * @param userId
   *          the userId to set
   */
  public void setUserId(Long userId) {
    this.userId = userId;
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
   * @return the roleName
   */
  public String getRoleName() {
    return roleName;
  }

  /**
   * @param roleName
   *          the roleName to set
   */
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName
   *          the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the emailId
   */
  public String getEmailId() {
    return emailId;
  }

  /**
   * @param emailId
   *          the emailId to set
   */
  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }
  
  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName
   *          the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * @param phone
   *          the phone to set
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * @return the companyName
   */
  public String getCompanyName() {
    return companyName;
  }

  /**
   * @param companyName the companyName to set
   */
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
  
  /**
   * @return the roleId
   */
  public Long getRoleId() {
    return roleId;
  }

  /**
   * @param roleId
   *          the roleId to set
   */
  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  /**
   * @return the entityLegalName
   */
  public String getEntityLegalName() {
    return entityLegalName;
  }

  /**
   * @param entityLegalName the entityLegalName to set
   */
  public void setEntityLegalName(String entityLegalName) {
    this.entityLegalName = entityLegalName;
  }

  /**
   * @return the taxId
   */
  public String getTaxId() {
    return taxId;
  }

  /**
   * @param taxId the taxId to set
   */
  public void setTaxId(String taxId) {
    this.taxId = taxId;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * @return the businessAs
   */
  public String getBusinessAs() {
    return businessAs;
  }

  /**
   * @param businessAs the businessAs to set
   */
  public void setBusinessAs(String businessAs) {
    this.businessAs = businessAs;
  }

  /**
   * @return the companyEmailAddress
   */
  public String getCompanyEmailAddress() {
    return companyEmailAddress;
  }

  /**
   * @param companyEmailAddress the companyEmailAddress to set
   */
  public void setCompanyEmailAddress(String companyEmailAddress) {
    this.companyEmailAddress = companyEmailAddress;
  }

  /**
   * @return the officePhone
   */
  public String getOfficePhone() {
    return officePhone;
  }

  /**
   * @param officePhone the officePhone to set
   */
  public void setOfficePhone(String officePhone) {
    this.officePhone = officePhone;
  }

  /**
   * @return the fax
   */
  public String getFax() {
    return fax;
  }

  /**
   * @param fax the fax to set
   */
  public void setFax(String fax) {
    this.fax = fax;
  }

  /**
   * @return the annualCardSales
   */
  public String getAnnualCardSales() {
    return annualCardSales;
  }

  /**
   * @param annualCardSales the annualCardSales to set
   */
  public void setAnnualCardSales(String annualCardSales) {
    this.annualCardSales = annualCardSales;
  }

  /**
   * @return the phyAddress1
   */
  public String getPhyAddress1() {
    return phyAddress1;
  }

  /**
   * @param phyAddress1 the phyAddress1 to set
   */
  public void setPhyAddress1(String phyAddress1) {
    this.phyAddress1 = phyAddress1;
  }

  /**
   * @return the phyAddress2
   */
  public String getPhyAddress2() {
    return phyAddress2;
  }

  /**
   * @param phyAddress2 the phyAddress2 to set
   */
  public void setPhyAddress2(String phyAddress2) {
    this.phyAddress2 = phyAddress2;
  }

  /**
   * @return the phyCity
   */
  public String getPhyCity() {
    return phyCity;
  }

  /**
   * @param phyCity the phyCity to set
   */
  public void setPhyCity(String phyCity) {
    this.phyCity = phyCity;
  }

  /**
   * @return the phyState
   */
  public String getPhyState() {
    return phyState;
  }

  /**
   * @param phyState the phyState to set
   */
  public void setPhyState(String phyState) {
    this.phyState = phyState;
  }

  /**
   * @return the phyZipCode
   */
  public String getPhyZipCode() {
    return phyZipCode;
  }

  /**
   * @param phyZipCode the phyZipCode to set
   */
  public void setPhyZipCode(String phyZipCode) {
    this.phyZipCode = phyZipCode;
  }

  /**
   * @return the phyCountry
   */
  public String getPhyCountry() {
    return phyCountry;
  }

  /**
   * @param phyCountry the phyCountry to set
   */
  public void setPhyCountry(String phyCountry) {
    this.phyCountry = phyCountry;
  }

  /**
   * @return the mailAddress1
   */
  public String getMailAddress1() {
    return mailAddress1;
  }

  /**
   * @param mailAddress1 the mailAddress1 to set
   */
  public void setMailAddress1(String mailAddress1) {
    this.mailAddress1 = mailAddress1;
  }

  /**
   * @return the mailAddress2
   */
  public String getMailAddress2() {
    return mailAddress2;
  }

  /**
   * @param mailAddress2 the mailAddress2 to set
   */
  public void setMailAddress2(String mailAddress2) {
    this.mailAddress2 = mailAddress2;
  }

  /**
   * @return the mailCity
   */
  public String getMailCity() {
    return mailCity;
  }

  /**
   * @param mailCity the mailCity to set
   */
  public void setMailCity(String mailCity) {
    this.mailCity = mailCity;
  }

  /**
   * @return the mailState
   */
  public String getMailState() {
    return mailState;
  }

  /**
   * @param mailState the mailState to set
   */
  public void setMailState(String mailState) {
    this.mailState = mailState;
  }

  /**
   * @return the mailZipCode
   */
  public String getMailZipCode() {
    return mailZipCode;
  }

  /**
   * @param mailZipCode the mailZipCode to set
   */
  public void setMailZipCode(String mailZipCode) {
    this.mailZipCode = mailZipCode;
  }

  /**
   * @return the mailCountry
   */
  public String getMailCountry() {
    return mailCountry;
  }

  /**
   * @param mailCountry the mailCountry to set
   */
  public void setMailCountry(String mailCountry) {
    this.mailCountry = mailCountry;
  }

  /**
   * @return the primaryName
   */
  public String getPrimaryName() {
    return primaryName;
  }

  /**
   * @param primaryName the primaryName to set
   */
  public void setPrimaryName(String primaryName) {
    this.primaryName = primaryName;
  }

  /**
   * @return the primaryEmailId
   */
  public String getPrimaryEmailId() {
    return primaryEmailId;
  }

  /**
   * @param primaryEmailId the primaryEmailId to set
   */
  public void setPrimaryEmailId(String primaryEmailId) {
    this.primaryEmailId = primaryEmailId;
  }

  /**
   * @return the primaryContactPhone
   */
  public String getPrimaryContactPhone() {
    return primaryContactPhone;
  }

  /**
   * @param primaryContactPhone the primaryContactPhone to set
   */
  public void setPrimaryContactPhone(String primaryContactPhone) {
    this.primaryContactPhone = primaryContactPhone;
  }

  /**
   * @return the companyURL
   */
  public String getCompanyURL() {
    return companyURL;
  }

  /**
   * @param companyURL the companyURL to set
   */
  public void setCompanyURL(String companyURL) {
    this.companyURL = companyURL;
  }
  
}
