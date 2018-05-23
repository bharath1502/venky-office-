/**
 * 
 */
package com.chatak.pg.user.bean;

import java.sql.Timestamp;

/**
 * @Author: Girmiti Software
 * @Date: Aug 3, 2016
 * @Time: 9:09:35 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class UpdateResellerRequest extends SearchRequest{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -855376414595838680L;

	private Long resellerId;

	  private String resellerName;

	  private String contactName;

	  private String emailId;
	  
	  private String phone;

	  private String address1;

	  private String address2;

	  private String city;
	  
	  private String state;

	  private String country;

	  private String zip;
	  
	  private String createdBy;

	  private String updatedBy;
	  
	  private Timestamp createdDate;

	  private Timestamp updatedDate;
	  
	  private Integer status;
	  
	  private Long accountNumber;
	  
	  private String accountBalance;
	  
	  
	  /**
	   * @return the accountBalance
	   */
	  public String getAccountBalance() {
	    return accountBalance;
	  }

	  /**
	   * @param accountBalance the accountBalance to set
	   */
	  public void setAccountBalance(String accountBalance) {
	    this.accountBalance = accountBalance;
	  }

	  /**
	   * @return the accountNumber
	   */
	  public Long getAccountNumber() {
	    return accountNumber;
	  }

	  /**
	   * @param resellerName the resellerName to set
	   */
	  public void setResellerName(String resellerName) {
	    this.resellerName = resellerName;
	  }

	  /**
	   * @return the resellerId
	   */
	  public Long getResellerId() {
	    return resellerId;
	  }

	  /**
	   * @return the contactName
	   */
	  public String getContactName() {
	    return contactName;
	  }
	  
	  /**
	   * @param accountNumber the accountNumber to set
	   */
	  public void setAccountNumber(Long accountNumber) {
	    this.accountNumber = accountNumber;
	  }

	  /**
	   * @param contactName the contactName to set
	   */
	  public void setContactName(String contactName) {
	    this.contactName = contactName;
	  }

	  /**
	   * @return the emailId
	   */
	  public String getEmailId() {
	    return emailId;
	  }
	  
	  /**
	   * @param resellerId the resellerId to set
	   */
	  public void setResellerId(Long resellerId) {
	    this.resellerId = resellerId;
	  }

	  /**
	   * @param emailId the emailId to set
	   */
	  public void setEmailId(String emailId) {
	    this.emailId = emailId;
	  }

	  /**
	   * @return the phone
	   */
	  public String getPhone() {
	    return phone;
	  }

	  /**
	   * @return the resellerName
	   */
	  public String getResellerName() {
	    return resellerName;
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
	   * @param phone the phone to set
	   */
	  public void setPhone(String phone) {
	    this.phone = phone;
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
	   * @return the country
	   */
	  public String getCountry() {
	    return country;
	  }

	  /**
	   * @param city the city to set
	   */
	  public void setCity(String city) {
	    this.city = city;
	  }

	  /**
	   * @param state the state to set
	   */
	  public void setState(String state) {
	    this.state = state;
	  }

	  /**
	   * @return the updatedBy
	   */
	  public String getUpdatedBy() {
	    return updatedBy;
	  }

	  /**
	   * @param country the country to set
	   */
	  public void setCountry(String country) {
	    this.country = country;
	  }
	  /**
	   * @return the city
	   */
	  public String getCity() {
	    return city;
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
	   * @return the createdBy
	   */
	  public String getCreatedBy() {
	    return createdBy;
	  }
	  
	  /**
	   * @return the state
	   */
	  public String getState() {
	    return state;
	  }

	  /**
	   * @param createdBy the createdBy to set
	   */
	  public void setCreatedBy(String createdBy) {
	    this.createdBy = createdBy;
	  }

	  /**
	   * @param updatedBy the updatedBy to set
	   */
	  public void setUpdatedBy(String updatedBy) {
	    this.updatedBy = updatedBy;
	  }

	  /**
	   * @return the createdDate
	   */
	  public Timestamp getCreatedDate() {
	    return createdDate;
	  }

	  /**
	   * @param createdDate the createdDate to set
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
	   * @param updatedDate the updatedDate to set
	   */
	  public void setUpdatedDate(Timestamp updatedDate) {
	    this.updatedDate = updatedDate;
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
