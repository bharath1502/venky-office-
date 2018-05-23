package com.chatak.pg.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class CurrencyRequest implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -7095385062056990859L;

  private Long id;

  private Long countryId;

  private String code;

  private Long status;

  private String createdBy;

  private Timestamp createdDate;

  private String bankName;
  
  private Long currencyId;
  
  private String currencyCodeAlpha;


  /**
   * @return the currencyCodeAlpha
   */
  public String getCurrencyCodeAlpha() {
    return currencyCodeAlpha;
  }
  
  /**
   * @return the currencyId
   */
  public Long getCurrencyId() {
    return currencyId;
  }

  /**
   * @param currencyCodeAlpha the currencyCodeAlpha to set
   */
  public void setCurrencyCodeAlpha(String currencyCodeAlpha) {
    this.currencyCodeAlpha = currencyCodeAlpha;
  }
  
  public Long getId() {
    return id;
  }

  /**
   * @param currencyId the currencyId to set
   */
  public void setCurrencyId(Long currencyId) {
    this.currencyId = currencyId;
  }
  
  public Long getCountryId() {
    return countryId;
  }

  public String getBankName() {
    return bankName;
  }
  
  public String getCode() {
    return code;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCountryId(Long countryId) {
    this.countryId = countryId;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

}
