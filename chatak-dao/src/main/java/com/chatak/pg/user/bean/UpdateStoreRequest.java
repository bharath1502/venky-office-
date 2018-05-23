package com.chatak.pg.user.bean;

import java.sql.Timestamp;

import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.DateUtil;

public class UpdateStoreRequest {

	private Long merchant_code;
	private Long store_id;
	private String store_name;
	private Long phone1;
	private Long phone2;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String action;
	private String valid_from_date;
	private String valid_to_date;
	
	public String validate(){
		String message = "";
		if(CommonUtil.isNullAndEmpty(store_name)){
			message="store_name is the Required field";
		} else if(merchant_code == null){
			message="merchant_code is the Required field";
		} else if(store_id == null){
			message="store_id is the Required field";
		} else if(phone1 == null){
			message="phone1 is the Required field";
		} else if(CommonUtil.isNullAndEmpty(address1)){
			message="address1 is the Required field";
		} else if(CommonUtil.isNullAndEmpty(city)){
			message="city is the Required field";
		} else if(CommonUtil.isNullAndEmpty(state)){
			message="state is the Required field";
		} else if(CommonUtil.isNullAndEmpty(zip)){
			message="zip is the Required field";
		} else if(!CommonUtil.isNullAndEmpty(action)){
			if(!action.equalsIgnoreCase(PGConstants.ACTION_UPDATE) &&
					!action.equalsIgnoreCase(PGConstants.ACTION_DELETE) &&	
					!action.equalsIgnoreCase(PGConstants.ACTION_BLOCK) &&
					!action.equalsIgnoreCase(PGConstants.ACTION_UNBLOCK) &&
					!action.equalsIgnoreCase(PGConstants.ACTION_ACTIVE)){
				message="action field must have any one of the value { update, delete, block, unblock, active }";
			}
			
			Timestamp currentDate = new Timestamp(System.currentTimeMillis());
			Timestamp validFromDate = DateUtil.getStartDayTimestamp(valid_from_date, "dd/MM/yyyy");
			Timestamp validToDate = DateUtil.getEndDayTimestamp(valid_to_date, "dd/MM/yyyy");
			
			if(validFromDate.after(validToDate) || currentDate.after(validToDate)){
				message="validFromDate and valid_to_date field values incorrect";
			}
		}
		return message;
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
	 * @return the phone2
	 */
	public Long getPhone2() {
		return phone2;
	}
	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param phone2 the phone2 to set
	 */
	public void setPhone2(Long phone2) {
		this.phone2 = phone2;
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
	 * @return the phone1
	 */
	public Long getPhone1() {
		return phone1;
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
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param phone1 the phone1 to set
	 */
	public void setPhone1(Long phone1) {
		this.phone1 = phone1;
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
	 * @return the store_name
	 */
	public String getStore_name() {
		return store_name;
	}

	/**
	 * @return the merchant_code
	 */
	public Long getMerchant_code() {
		return merchant_code;
	}

	/**
	 * @param store_name the store_name to set
	 */
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	/**
	 * @param merchant_code the merchant_code to set
	 */
	public void setMerchant_code(Long merchant_code) {
		this.merchant_code = merchant_code;
	}

	/**
	 * @return the store_id
	 */
	public Long getStore_id() {
		return store_id;
	}

	/**
	 * @param store_id the store_id to set
	 */
	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
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
