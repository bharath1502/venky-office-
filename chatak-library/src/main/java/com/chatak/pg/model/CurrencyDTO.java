package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class CurrencyDTO extends SearchRequest {

  /**
   * 
   */
  private static final long serialVersionUID = -7262191654484666494L;

  private Long id;
  
  private String currencyName;
  
  private Integer currencyExponent;
  
  private Integer currencySeparatorPosition;
  
  private Character currencyMinorUnit;
  
  private Character currencyThousandsUnit;
  
  private Integer status;

  /**
   * @return the id
   */
  @Override
  public Long getId() {
    return id;
  }
  
  /**
   * @return the currencyName
   */
  public String getCurrencyName() {
    return currencyName;
  }

  /**
   * @param id the id to set
   */
  @Override
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the currencyExponent
   */
  public Integer getCurrencyExponent() {
    return currencyExponent;
  }
  
  /**
   * @param currencyName the currencyName to set
   */
  public void setCurrencyName(String currencyName) {
    this.currencyName = currencyName;
  }

  /**
   * @param currencyExponent the currencyExponent to set
   */
  public void setCurrencyExponent(Integer currencyExponent) {
    this.currencyExponent = currencyExponent;
  }

  /**
   * @return the currencySeparatorPosition
   */
  public Integer getCurrencySeparatorPosition() {
    return currencySeparatorPosition;
  }
  
  /**
   * @return the currencyMinorUnit
   */
  public Character getCurrencyMinorUnit() {
    return currencyMinorUnit;
  }

  /**
   * @param currencySeparatorPosition the currencySeparatorPosition to set
   */
  public void setCurrencySeparatorPosition(Integer currencySeparatorPosition) {
    this.currencySeparatorPosition = currencySeparatorPosition;
  }

  /**
   * @return the currencyThousandsUnit
   */
  public Character getCurrencyThousandsUnit() {
    return currencyThousandsUnit;
  }

  /**
   * @param currencyMinorUnit the currencyMinorUnit to set
   */
  public void setCurrencyMinorUnit(Character currencyMinorUnit) {
    this.currencyMinorUnit = currencyMinorUnit;
  }
  
  /**
   * @param currencyThousandsUnit the currencyThousandsUnit to set
   */
  public void setCurrencyThousandsUnit(Character currencyThousandsUnit) {
    this.currencyThousandsUnit = currencyThousandsUnit;
  }

  /**
   * @return the status
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

}
