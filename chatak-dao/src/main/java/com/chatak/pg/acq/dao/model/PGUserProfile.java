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
@Table(name = "PG_USER_PROFILE")
public class PGUserProfile implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	/*@SequenceGenerator(name = "SEQ_PG_USER_PROFILE_ID", sequenceName = "SEQ_PG_USER_PROFILE")
	@GeneratedValue(generator = "SEQ_PG_USER_PROFILE_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PROFILE_ID")
	private Long profileId;  
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "MIDDLE_NAME")
	private String middleName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "DOB")
	private Timestamp dob;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "SSN")
	private String ssn;
	
	@Column(name = "PHONE1")
	private Long phone1;
	
	@Column(name = "PHONE2")
	private Long phone2;
	
	@Column(name = "ADDRESS1")
	private String address1;
	
	@Column(name = "ROLE_ID")
	private Integer roleId;
	
	@Column(name = "ADDRESS2")
	private String address2;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "MERCHANT_ID")
	private Long merchantId;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "STORE_ID")
	private Long storeId;
	
	@Column(name = "ZIP")
	private String zip;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;
	
	@Column(name = "STATUS")
	private Integer status;

	/**
	 * @return the profileId
	 */
	public Long getProfileId() {
		return profileId;
	}

	/**
	 * @param profileId the profileId to set
	 */
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the dob
	 */
	public Timestamp getDob() {
		return dob;
	}

	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}
	
	/**
	 * @param dob the dob to set
	 */
	public void setDob(Timestamp dob) {
		this.dob = dob;
	}

	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return the phone1
	 */
	public Long getPhone1() {
		return phone1;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param phone1 the phone1 to set
	 */
	public void setPhone1(Long phone1) {
		this.phone1 = phone1;
	}

	/**
	 * @return the phone2
	 */
	public Long getPhone2() {
		return phone2;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param phone2 the phone2 to set
	 */
	public void setPhone2(Long phone2) {
		this.phone2 = phone2;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
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
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
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
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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
	 * @return the merchantId
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * @return the storeId
	 */
	public Long getStoreId() {
		return storeId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
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
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

}
