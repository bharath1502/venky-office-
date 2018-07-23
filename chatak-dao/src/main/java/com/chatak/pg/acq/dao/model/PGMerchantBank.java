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
 * @Date: 05-May-2015
 * @Time: 7:08:27 PM
 * @Version: 1.0
 * @Comments:
 */
@Entity
@Table(name = "PG_MERCHANT_BANK")
public class PGMerchantBank implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_MERCHANT_BANK_ID", sequenceName = "SEQ_PG_MERCHANT_BANK")
  @GeneratedValue(generator = "SEQ_PG_MERCHANT_BANK_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "MERCHANT_ID")
  private String merchantId;

  @Column(name = "BANK_NAME")
  private String bankName;

  @Column(name = "BANK_ACC_NUM")
  private String bankAccNum;

  @Column(name = "BANK_CODE")
  private String bankCode;

  @Column(name = "ROUTING_NUMBER")
  private String routingNumber;

  @Column(name = "CURRENCY_CODE")
  private String currencyCode;

  @Column(name = "ACCOUNT_TYPE")
  private String accountType;
  
  @Column(name = "CREATED_DATE")
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  private Timestamp updatedDate;

  @Column(name = "CREATED_BY")
  private Long createdBy;
  
  @Column(name = "ADDRESS1")
  private String address1;
  
  @Column(name = "NAME_ON_ACCOUNT")
  private String nameOnAccount;

  @Column(name = "ADDRESS2")
  private String address2;

  @Column(name = "CITY")
  private String city;
  
  @Column(name = "STATUS")
  private Integer status;

  @Column(name = "STATE")
  private String state;
  
  @Column(name = "PIN")
  private String pin;

  @Column(name = "COUNTRY")
  private String country;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the bankName
   */
  public String getBankName() {
    return bankName;
  }

  public String getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * @param bankName
   *          the bankName to set
   */
  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  /**
   * @return the bankAccNum
   */
  public String getBankAccNum() {
    return bankAccNum;
  }

  /**
   * @param bankAccNum
   *          the bankAccNum to set
   */
  public void setBankAccNum(String bankAccNum) {
    this.bankAccNum = bankAccNum;
  }

  /**
   * @return the bankCode
   */
  public String getBankCode() {
    return bankCode;
  }

  /**
   * @param bankCode
   *          the bankCode to set
   */
  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }

  /**
   * @return the routingNumber
   */
  public String getRoutingNumber() {
    return routingNumber;
  }

  /**
   * @param routingNumber
   *          the routingNumber to set
   */
  public void setRoutingNumber(String routingNumber) {
    this.routingNumber = routingNumber;
  }

  /**
   * @return the currencyCode
   */
  public String getCurrencyCode() {
    return currencyCode;
  }

  /**
   * @return the status
   */
  public Integer getStatus() {
    return status;
  }
  
  /**
   * @param currencyCode
   *          the currencyCode to set
   */
  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * @return the createdDate
   */
  public Timestamp getCreatedDate() {
    return createdDate;
  }
  
  /**
   * @return the accountType
   */
  public String getAccountType() {
    return accountType;
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
   * @param accountType
   *          the accountType to set
   */
  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  /**
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }
  
  /**
   * @param updatedDate
   *          the updatedDate to set
   */
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  /**
   * @param address1 the address1 to set
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
   * @return the createdBy
   */
  public Long getCreatedBy() {
    return createdBy;
  }

  /**
   * @param address2 the address2 to set
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }
  
  /**
   * @return the pin
   */
  public String getPin() {
    return pin;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }
  
  /**
   * @param createdBy
   *          the createdBy to set
   */
  public void setCreatedBy(Long createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }
  
  /**
   * @return the nameOnAccount
   */
  public String getNameOnAccount() {
    return nameOnAccount;
  }

  /**
   * @param state the state to set
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
   * @param nameOnAccount the nameOnAccount to set
   */
  public void setNameOnAccount(String nameOnAccount) {
    this.nameOnAccount = nameOnAccount;
  }

  /**
   * @param country the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * @param pin the pin to set
   */
  public void setPin(String pin) {
    this.pin = pin;
  }

}
