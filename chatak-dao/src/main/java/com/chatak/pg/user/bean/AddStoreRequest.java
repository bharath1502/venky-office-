package com.chatak.pg.user.bean;

import java.sql.Timestamp;

import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.DateUtil;

public class AddStoreRequest {

	private String store_id;
	private String store_name;
	private Long merchant_code;
	private Long phone1;
	private Long phone2;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String valid_from_date;
	private String valid_to_date;
	
	public String validate(){
		String message = "";
		
		if(!CommonUtil.isNullAndEmpty(store_id) && store_id.length()!=PGConstants.LENGTH_STORE_ID){
			message="store_id value should be of 8 digits";
		} else if(CommonUtil.isNullAndEmpty(store_name)){
			message="store_name is the Required field";
		} else if(merchant_code == null){
			message="merchant_code is the Required field";
		} else if(CommonUtil.isNullAndEmpty(address1)){
			message="address1 is the Required field";
		} else if(phone1 == null){
			message="phone1 is the Required field";
		} else if(CommonUtil.isNullAndEmpty(city)){
			message="city is the Required field";
		} else if(CommonUtil.isNullAndEmpty(zip)){
			message="zip is the Required field";
		} else if(CommonUtil.isNullAndEmpty(state)){
			message="state is the Required field";
		} else if(CommonUtil.isNullAndEmpty(valid_from_date)){
			message="valid_from_date is the Required field";
		} else if(CommonUtil.isNullAndEmpty(valid_to_date)){
			message="valid_to_date is the Required field";
		} 
		
		Timestamp currentDate = new Timestamp(System.currentTimeMillis());
		Timestamp validToDate = DateUtil.getEndDayTimestamp(valid_to_date, "dd/MM/yyyy");
		Timestamp validFromDate = DateUtil.getStartDayTimestamp(valid_from_date, "dd/MM/yyyy");
		
		if(validFromDate.after(validToDate) || currentDate.after(validToDate)){
			message="validFromDate and valid_to_date field values incorrect";
		} 
		return message;
	}
	
	/**
	 * @return the phone1
	 */
	public Long getPhone1() {
		return phone1;
	}
	
	/**
	 * @param store_name the store_name to set
	 */
	public void setStore_name(String store_name) {
		this.store_name = store_name;
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
	 * @param merchant_code the merchant_code to set
	 */
	public void setMerchant_code(Long merchant_code) {
		this.merchant_code = merchant_code;
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
	 * @return the store_id
	 */
	public String getStore_id() {
		return store_id;
	}
	
	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	
	/**
	 * @param store_id the store_id to set
	 */
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	/**
	 * @return the valid_to_date
	 */
	public String getValid_to_date() {
		return valid_to_date;
	}
	
	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	
	/**
	 * @return the valid_from_date
	 */
	public String getValid_from_date() {
		return valid_from_date;
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
	 * @return the merchant_code
	 */
	public Long getMerchant_code() {
		return merchant_code;
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
	 * @return the store_name
	 */
	public String getStore_name() {
		return store_name;
	}
	
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
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
