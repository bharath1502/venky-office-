package com.chatak.pg.user.bean;


public class Store {

	private Long merchant_code;
	private String store_name;
	private Long store_id;
	private Long phone1;
	private String address1;
	private Long phone2;
	private String address2;
	private String state;
	private String city;
	private String zip;
	private Integer status;
	private String createdDate;
	private String updatedDate;
	private String valid_from_date;
	private String valid_to_date;
	
	/**
	 * @return the phone1
	 */
	public Long getPhone1() {
		return phone1;
	}
	/**
	 * @return the phone2
	 */
	public Long getPhone2() {
		return phone2;
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
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return the store_id
	 */
	public Long getStore_id() {
		return store_id;
	}
	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @return the merchant_code
	 */
	public Long getMerchant_code() {
		return merchant_code;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @return the store_name
	 */
	public String getStore_name() {
		return store_name;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @param store_name the store_name to set
	 */
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @param merchant_code the merchant_code to set
	 */
	public void setMerchant_code(Long merchant_code) {
		this.merchant_code = merchant_code;
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
	public Integer getStatus() {
		return status;
	}
	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the updatedDate
	 */
	public String getUpdatedDate() {
		return updatedDate;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	/**
	 * @return the valid_from_date
	 */
	public String getValid_from_date() {
		return valid_from_date;
	}
	/**
	 * @return the valid_to_date
	 */
	public String getValid_to_date() {
		return valid_to_date;
	}
	/**
	 * @param valid_from_date the valid_from_date to set
	 */
	public void setValid_from_date(String valid_from_date) {
		this.valid_from_date = valid_from_date;
	}
	/**
	 * @param valid_to_date the valid_to_date to set
	 */
	public void setValid_to_date(String valid_to_date) {
		this.valid_to_date = valid_to_date;
	}
	
}
