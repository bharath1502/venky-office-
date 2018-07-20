package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_RESELLER")
public class PGReseller implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2634738974000446467L;

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_RESELLER_ID", sequenceName = "SEQ_PG_RESELLER_ID")
	@GeneratedValue(generator = "SEQ_PG_RESELLER_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RESELLER_ID")
	private Long resellerId;
	
	@Column(name = "ACCOUNT_NUMBER")
	private Long accountNumber;
	
	@Column(name = "ACCOUNT_BALANCE")
	private Long accountBalance;
	
	@Column(name = "RESELLER_NAME")
	private String resellerName;

	 @Column(name = "CONTACT_NAME") 
	private String contactName;
	 
	 @Column(name = "EMAIL_ID")
	 private String emailId;
	  
	 @Column(name = "PHONE")
	private String phone;

	@Column(name = "ADDRESS1")	  
	private String address1;

	@Column(name = "ADDRESS2")	  
	private String address2;

	@Column(name = "CITY")	
	private String city;
		  
	@Column(name = "STATE")	 
	private String state;

	@Column(name = "COUNTRY")	 
	private String country;

	@Column(name = "ZIP")	 
	private String zip;
		  
	@Column(name = "CREATED_DATE")		 
	private Timestamp createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;
	
	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "CREATED_BY")		 
	private String createdBy;
	
	
	@Column(name = "UPDATED_BY")		 
	private String  updatedBy;
	
	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the resellerId
	 */
	public Long getResellerId() {
		return resellerId;
	}

	/**
	 * @param resellerId the resellerId to set
	 */
	public void setResellerId(Long resellerId) {
		this.resellerId = resellerId;
	}

	/**
	 * @return the resellerName
	 */
	public String getResellerName() {
		return resellerName;
	}

	/**
	 * @param resellerName the resellerName to set
	 */
	public void setResellerName(String resellerName) {
		this.resellerName = resellerName;
	}
	
	/**
	 * @return the accountNumber
	 */
	public Long getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	/**
	 * @return the accountBalance
	 */
	public Long getAccountBalance() {
		return accountBalance;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * @param accountBalance the accountBalance to set
	 */
	public void setAccountBalance(Long accountBalance) {
		this.accountBalance = accountBalance;
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
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
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
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
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
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
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
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

}
