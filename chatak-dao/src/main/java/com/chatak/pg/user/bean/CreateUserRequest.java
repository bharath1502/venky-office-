package com.chatak.pg.user.bean;

import com.chatak.pg.util.CommonUtil;

public class CreateUserRequest extends Request{
	
	private String first_name;
	private String last_name;
	private String middle_name;
	private Integer role_id;
	private String date_birth;
	private String email;
	private String ssn;
	private Long phone2;
	private Long phone1;
	private String address1;
	private String city;
	private String address2;
	private String state;
	private Integer status;
	private String zip;
	
	public String validate(){
		String message = "";
		if(CommonUtil.isNullAndEmpty(first_name)){
			message="first_name is the Required field";
		} else if(CommonUtil.isNullAndEmpty(email)){
			message= "email is the Required field";
		} else if(CommonUtil.isNullAndEmpty(date_birth)){
			message= "date_birth is the Required field";
		} else if(role_id == null){
			message= "role_id is the Required field";
		} else if(CommonUtil.isNullAndEmpty(address1)){
			message= "address1 is the Required field";
		} else if(CommonUtil.isNullAndEmpty(city)){
			message= "city is the Required field";
		} else if(CommonUtil.isNullAndEmpty(state)){
			message= "state is the Required field";
		} else if(CommonUtil.isNullAndEmpty(zip)){
			message= "zip is the Required field";
		} else if(getMerchant_id() == null){
			message= "merchant_id is the Required field";
		} else if(getStore_id() == null){
			message= "store_id is the Required field";
		} 
		return message;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}
	
	/**
	 * @param first_name the first_name to set
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/**
	 * @return the middle_name
	 */
	public String getMiddle_name() {
		return middle_name;
	}
	
	/**
	 * @param middle_name the middle_name to set
	 */
	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}
	
	/**
	 * @param phone2 the phone2 to set
	 */
	public void setPhone2(Long phone2) {
		this.phone2 = phone2;
	}
	
	/**
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}
	
	/**
	 * @param last_name the last_name to set
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
	/**
	 * @param phone1 the phone1 to set
	 */
	public void setPhone1(Long phone1) {
		this.phone1 = phone1;
	}
	
	/**
	 * @return the role_id
	 */
	public Integer getRole_id() {
		return role_id;
	}
	
	/**
	 * @param role_id the role_id to set
	 */
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	
	/**
	 * @return the phone2
	 */
	public Long getPhone2() {
		return phone2;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the phone1
	 */
	public Long getPhone1() {
		return phone1;
	}
	
	/**
	 * @return the date_birth
	 */
	public String getDate_birth() {
		return date_birth;
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
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
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
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

}
