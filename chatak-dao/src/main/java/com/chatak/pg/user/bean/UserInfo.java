package com.chatak.pg.user.bean;

import java.io.Serializable;
import java.sql.Timestamp;


public class UserInfo implements Serializable {
	
	private Long profile_id;
	private String middle_name;
	private String first_name;
	private String last_name;
	private String email;
	private Integer role_id;
	private String date_birth;
	private Long phone1;
	private String ssn;
	private Long phone2;
	private String address2;
	private String address1;
	private String city;
	private String zip;
	private String state;
	private Long merchant_id;
	private Long status;
	private Long store_id;
	private Timestamp createDate;
	
	
	/**
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}
	/**
	 * @param middle_name the middle_name to set
	 */
	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}
	/**
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}
	/**
	 * @param first_name the first_name to set
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	/**
	 * @return the middle_name
	 */
	public String getMiddle_name() {
		return middle_name;
	}
	/**
	 * @param last_name the last_name to set
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	/**
	 * @param role_id the role_id to set
	 */
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @return the role_id
	 */
	public Integer getRole_id() {
		return role_id;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the date_birth
	 */
	public String getDate_birth() {
		return date_birth;
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
	 * @param date_birth the date_birth to set
	 */
	public void setDate_birth(String date_birth) {
		this.date_birth = date_birth;
	}
	/**
	 * @return the phone1
	 */
	public Long getPhone1() {
		return phone1;
	}
	/**
	 * @param phone1 the phone1 to set
	 */
	public void setPhone1(Long phone1) {
		this.phone1 = phone1;
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
	 * @return the phone2
	 */
	public Long getPhone2() {
		return phone2;
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
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
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
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return the profile_id
	 */
	public Long getProfile_id() {
		return profile_id;
	}
	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @param profile_id the profile_id to set
	 */
	public void setProfile_id(Long profile_id) {
		this.profile_id = profile_id;
	}
	/**
	 * @return the merchant_id
	 */
	public Long getMerchant_id() {
		return merchant_id;
	}
	/**
	 * @return the store_id
	 */
	public Long getStore_id() {
		return store_id;
	}
	/**
	 * @param merchant_id the merchant_id to set
	 */
	public void setMerchant_id(Long merchant_id) {
		this.merchant_id = merchant_id;
	}
	/**
	 * @param store_id the store_id to set
	 */
	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}
	/**
	 * @return the status
	 */
	public Long getStatus() {
		return status;
	}
	/**
	 * @return the createDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	
}
