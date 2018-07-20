package com.chatak.pg.user.bean;


/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 05-Jan-2015 3:27:54 PM
 * @version 1.0
 */
public class Terminal {

private String terminalId;
  
  private String merchantId;
  
  private String make;
  
  private String model;
  
  private String terminalType;
  
  private String category;

  private String currency;

  private String zip;

  private String city;

  private String state;

  private String country;

  private String createdDate;
  
  private String updatedDate;
  
  private String validFrom;
  
  private String validTo;
  
  private String status;

  /**
   * @return the make
   */
  public String getMake() {
    return make;
  }

  /**
   * @param make the make to set
   */
  public void setMake(String make) {
    this.make = make;
  }

  /**
   * @return the category
   */
  public String getCategory() {
    return category;
  }

  /**
   * @param category the category to set
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * @return the zip
   */
  public String getZip() {
    return zip;
  }
  
  /**
   * @return the model
   */
  public String getModel() {
    return model;
  }

  /**
   * @param zip the zip to set
   */
  public void setZip(String zip) {
    this.zip = zip;
  }
  
  /**
   * @param model the model to set
   */
  public void setModel(String model) {
    this.model = model;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }
  
  /**
   * @return the terminalType
   */
  public String getTerminalType() {
    return terminalType;
  }
  
  /**
   * @param country the country to set
   */
  public void setCountry(String country) {
    this.country = country;
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
   * @param terminalType the terminalType to set
   */
  public void setTerminalType(String terminalType) {
    this.terminalType = terminalType;
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
   * @param currency the currency to set
   */
  public void setCurrency(String currency) {
    this.currency = currency;
  }

  /**
   * @return the createdDate
   */
  public String getCreatedDate() {
    return createdDate;
  }
  
  /**
   * @return the currency
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * @param createdDate the createdDate to set
   */
  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * @return the updatedDate
   */
  public String getUpdatedDate() {
    return updatedDate;
  }

  /**
   * @param updatedDate the updatedDate to set
   */
  public void setUpdatedDate(String updatedDate) {
    this.updatedDate = updatedDate;
  }

  /**
   * @return the validFrom
   */
  public String getValidFrom() {
    return validFrom;
  }

  /**
   * @param validFrom the validFrom to set
   */
  public void setValidFrom(String validFrom) {
    this.validFrom = validFrom;
  }

  /**
   * @return the validTo
   */
  public String getValidTo() {
    return validTo;
  }

  /**
   * @param validTo the validTo to set
   */
  public void setValidTo(String validTo) {
    this.validTo = validTo;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

/**
 * @return the terminalId
 */
public String getTerminalId() {
	return terminalId;
}

/**
 * @param terminalId the terminalId to set
 */
public void setTerminalId(String terminalId) {
	this.terminalId = terminalId;
}

/**
 * @return the merchantID
 */
public String getMerchantId() {
	return merchantId;
}

/**
 * @param merchantID the merchantID to set
 */
public void setMerchantID(String merchantId) {
	this.merchantId = merchantId;
}
	
}
