/**
 * 
 */
package com.chatak.pay.controller.model;


/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 03-Jan-2015 3:49:50 PM
 * @version 1.0
 */
public class Merchant {

	  private Long id;

	  private String merchantId;

	  private String merchantType;
	  
	  private Long dailyTxnLimit;
	  
	  private String merchantName;
	  
	  private String merchantCategoryCode;
	  
	  private Boolean allowInternationTxn;

	  private String phone1;

	  private String address1;

	  private String address2;
	  
	  private String phone2;

	  private String tin;

	  private String ssn;

	  private Integer noOfRecords;

	  private String city;

	  private String state;
	  
	  private String zip;

	  private String country;

	  private String createdDate;

	  private Integer pageSize;

	  private String validToDate;

	  private String status;
	  
	  private String validFromDate;

	  private String updatedDate;
	  
	  private Integer pageIndex;

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
   * @return the merchantName
   */
  public String getMerchantName() {
    return merchantName;
  }

  /**
   * @param merchantName the merchantName to set
   */
  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }
  
  /**
   * @return the merchantId
   */
  public String getMerchantId() {
    return merchantId;
  }

  /**
   * @param merchantId the merchantId to set
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * @return the merchantCategoryCode
   */
  public String getMerchantCategoryCode() {
    return merchantCategoryCode;
  }

  /**
   * @param merchantCategoryCode the merchantCategoryCode to set
   */
  public void setMerchantCategoryCode(String merchantCategoryCode) {
    this.merchantCategoryCode = merchantCategoryCode;
  }

  /**
   * @return the dailyTxnLimit
   */
  public Long getDailyTxnLimit() {
    return dailyTxnLimit;
  }

  /**
   * @param dailyTxnLimit the dailyTxnLimit to set
   */
  public void setDailyTxnLimit(Long dailyTxnLimit) {
    this.dailyTxnLimit = dailyTxnLimit;
  }
  
  /**
   * @return the merchantType
   */
  public String getMerchantType() {
    return merchantType;
  }

  /**
   * @param merchantType the merchantType to set
   */
  public void setMerchantType(String merchantType) {
    this.merchantType = merchantType;
  }

  /**
   * @return the phone1
   */
  public String getPhone1() {
    return phone1;
  }

  /**
   * @param phone1 the phone1 to set
   */
  public void setPhone1(String phone1) {
    this.phone1 = phone1;
  }
  
  /**
   * @return the allowInternationTxn
   */
  public Boolean getAllowInternationTxn() {
    return allowInternationTxn;
  }

  /**
   * @param allowInternationTxn the allowInternationTxn to set
   */
  public void setAllowInternationTxn(Boolean allowInternationTxn) {
    this.allowInternationTxn = allowInternationTxn;
  }

  /**
   * @return the phone2
   */
  public String getPhone2() {
    return phone2;
  }

  /**
   * @param phone2 the phone2 to set
   */
  public void setPhone2(String phone2) {
    this.phone2 = phone2;
  }

  /**
   * @return the address2
   */
  public String getAddress2() {
    return address2;
  }

  /**
   * @param address2 the address2 to set
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }
  
  /**
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }

  /**
   * @param address1 the address1 to set
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  /**
   * @return the tin
   */
  public String getTin() {
    return tin;
  }

  /**
   * @param tin the tin to set
   */
  public void setTin(String tin) {
    this.tin = tin;
  }

  /**
   * @return the zip
   */
  public String getZip() {
    return zip;
  }

  /**
   * @param zip the zip to set
   */
  public void setZip(String zip) {
    this.zip = zip;
  }
  
  /**
   * @return the ssn
   */
  public String getSsn() {
    return ssn;
  }

  /**
   * @param ssn the ssn to set
   */
  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * @param country the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }
  
  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @param state the state to set
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * @return the createdDate
   */
  public String getCreatedDate() {
    return createdDate;
  }

  /**
   * @param createdDate the createdDate to set
   */
  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * @return the validToDate
   */
  public String getValidToDate() {
    return validToDate;
  }
  
  /**
   * @param pageSize the pageSize to set
   */
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * @return the noOfRecords
   */
  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  /**
   * @param validToDate the validToDate to set
   */
  public void setValidToDate(String validToDate) {
    this.validToDate = validToDate;
  }
  
  /**
   * @return the validFromDate
   */
  public String getValidFromDate() {
    return validFromDate;
  }

  /**
   * @param validFromDate the validFromDate to set
   */
  public void setValidFromDate(String validFromDate) {
    this.validFromDate = validFromDate;
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
   * @return the pageIndex
   */
  public Integer getPageIndex() {
    return pageIndex;
  }

  /**
   * @param pageIndex the pageIndex to set
   */
  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
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
   * @return the pageSize
   */
  public Integer getPageSize() {
    return pageSize;
  }

  /**
   * @param noOfRecords the noOfRecords to set
   */
  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }
  
}
