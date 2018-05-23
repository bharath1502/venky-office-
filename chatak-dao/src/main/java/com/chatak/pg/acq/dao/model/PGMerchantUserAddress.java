/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Jun 24, 2015
 * @Time: 12:55:29 PM
 * @Version: 1.0
 * @Comments:
 */
@Entity
@Table(name = "PG_MERCHANT_USER_ADDRESS")
public class PGMerchantUserAddress {

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_MERCHANT_USER_ADDRESS_ID", sequenceName = "SEQ_PG_MERCHANT_USER_ADDRESS")
  @GeneratedValue(generator = "SEQ_PG_MERCHANT_USER_ADDRESS_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "ADDRESS1")
  private String address1;
  
  @Column(name = "PIN")
  private String pin;

  @Column(name = "ADDRESS2")
  private String address2;
  
  @Column(name = "MERCHANT_CODE")
  private String merchantCode;

  @Column(name = "CITY")
  private String city;
  
  @Column(name = "MERCHANT_USER_ID")
  Long merchantUserId;

  @Column(name = "STATE")
  private String state;

  @Column(name = "COUNTRY")
  private String country;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }
  
  /**
   * @return the merchantCode
   */
  public String getMerchantCode() {
    return merchantCode;
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
   * @param merchantCode the merchantCode to set
   */
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  /**
   * @param address2 the address2 to set
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
   * @return the merchantUserId
   */
  public Long getMerchantUserId() {
    return merchantUserId;
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
   * @param merchantUserId the merchantUserId to set
   */
  public void setMerchantUserId(Long merchantUserId) {
    this.merchantUserId = merchantUserId;
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
   * @return the pin
   */
  public String getPin() {
    return pin;
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
