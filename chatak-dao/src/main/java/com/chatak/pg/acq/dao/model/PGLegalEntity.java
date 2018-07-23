/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Aug 14, 2015
 * @Time: 8:49:40 PM
 * @Version: 1.0
 * @Comments:
 */
@Entity
@Table(name = "PG_LEGAL_ENTITY")
public class PGLegalEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -4732399515303285686L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_LEGAL_ENTITY_ID", sequenceName = "SEQ_PG_LEGAL_ENTITY")
  @GeneratedValue(generator = "SEQ_PG_LEGAL_ENTITY_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PG_LEGAL_ENTITY_ID")
  private Long pgLegalEntityId;

  @Column(name = "LEGAL_ENTITY_NAME")
  private String legalEntityName;

  @Column(name = "LEGAL_ENTITY_TYPE")
  private String legalEntityType;

  @Column(name = "TAX_ID")
  private String taxId;

  @Column(name = "MERCHANT_ID")
  private Long merchantId;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "DATE_OF_BIRTH")
  private String dateOfBirth;

  @Column(name = "SSN")
  private String ssn;

  @Column(name = "COUNTRY_OF_RESIDENCE")
  private String countryOfResidence;

  @Column(name = "COUNTRY_OF_CITIZENSHIP")
  private String countryOfCitizenship;

  @Column(name = "HOME_PHONE")
  private String homePhone;

  @Column(name = "MOBILE_PHONE")
  private String mobilePhone;

  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  @Column(name = "ADDRESS1")
  private String address1;
  
  @Column(name = "PASSPORT_NUMBER")
  private String passportNumber;

  @Column(name = "ADDRESS2")
  private String address2;

  @Column(name = "CITY")
  private String city;
  
  @Column(name = "ANNUAL_CARD_SALE")
  private Long annualCardSale;

  @Column(name = "STATE")
  private String state;
  
  @Column(name = "PIN")
  private String pin;

  @Column(name = "COUNTRY")
  private String country;

  
  /**
   * @return the pgLegalEntityId
   */
  public Long getPgLegalEntityId() {
    return pgLegalEntityId;
  }

  /**
   * @param pgLegalEntityId
   *          the pgLegalEntityId to set
   */
  public void setPgLegalEntityId(Long pgLegalEntityId) {
    this.pgLegalEntityId = pgLegalEntityId;
  }

  /**
   * @return the legalEntityName
   */
  public String getLegalEntityName() {
    return legalEntityName;
  }

  /**
   * @param legalEntityName
   *          the legalEntityName to set
   */
  public void setLegalEntityName(String legalEntityName) {
    this.legalEntityName = legalEntityName;
  }

  /**
   * @return the legalEntityType
   */
  public String getLegalEntityType() {
    return legalEntityType;
  }

  /**
   * @param legalEntityType
   *          the legalEntityType to set
   */
  public void setLegalEntityType(String legalEntityType) {
    this.legalEntityType = legalEntityType;
  }

  /**
   * @return the taxId
   */
  public String getTaxId() {
    return taxId;
  }

  /**
   * @param taxId
   *          the taxId to set
   */
  public void setTaxId(String taxId) {
    this.taxId = taxId;
  }

  /**
   * @return the merchantId
   */
  public Long getMerchantId() {
    return merchantId;
  }

  /**
   * @param merchantId
   *          the merchantId to set
   */
  public void setMerchantId(Long merchantId) {
    this.merchantId = merchantId;
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
   * @return the dateOfBirth
   */
  public String getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * @param dateOfBirth
   *          the dateOfBirth to set
   */
  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  /**
   * @return the ssn
   */
  public String getSsn() {
    return ssn;
  }

  /**
   * @param ssn
   *          the ssn to set
   */
  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  /**
   * @return the countryOfResidence
   */
  public String getCountryOfResidence() {
    return countryOfResidence;
  }

  /**
   * @param countryOfResidence
   *          the countryOfResidence to set
   */
  public void setCountryOfResidence(String countryOfResidence) {
    this.countryOfResidence = countryOfResidence;
  }

  /**
   * @return the countryOfCitizenship
   */
  public String getCountryOfCitizenship() {
    return countryOfCitizenship;
  }

  /**
   * @param countryOfCitizenship
   *          the countryOfCitizenship to set
   */
  public void setCountryOfCitizenship(String countryOfCitizenship) {
    this.countryOfCitizenship = countryOfCitizenship;
  }

  /**
   * @return the homePhone
   */
  public String getHomePhone() {
    return homePhone;
  }

  /**
   * @param homePhone
   *          the homePhone to set
   */
  public void setHomePhone(String homePhone) {
    this.homePhone = homePhone;
  }

  /**
   * @return the mobilePhone
   */
  public String getMobilePhone() {
    return mobilePhone;
  }

  /**
   * @param mobilePhone
   *          the mobilePhone to set
   */
  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  /**
   * @return the createdDate
   */
  public Timestamp getCreatedDate() {
    return createdDate;
  }

  /**
   * @param createdDate
   *          the createdDate to set
   */
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * @return the updatedDate
   */
  public Timestamp getUpdatedDate() {
    return updatedDate;
  }

  

  



/**
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }
  
  /**
   * @param annualCardSale the annualCardSale to set
   */
  public void setAnnualCardSale(Long annualCardSale) {
  	this.annualCardSale = annualCardSale;
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
   * @return the annualCardSale
   */
  public Long getAnnualCardSale() {
  	return annualCardSale;
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
   * @param updatedDate
   *          the updatedDate to set
   */
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  /**
   * @param city
   *          the city to set
   */
  public void setCity(String city) {
    this.city = city;
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
   * @param passportNumber
   *          the passportNumber to set
   */
  public void setPassportNumber(String passportNumber) {
    this.passportNumber = passportNumber;
  }

  /**
   * @param country
   *          the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * @return the pin
   */
  public String getPin() {
    return pin;
  }

  /**
   * @return the passportNumber
   */
  public String getPassportNumber() {
    return passportNumber;
  }
  
  /**
   * @param pin
   *          the pin to set
   */
  public void setPin(String pin) {
    this.pin = pin;
  }
  
  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

}
