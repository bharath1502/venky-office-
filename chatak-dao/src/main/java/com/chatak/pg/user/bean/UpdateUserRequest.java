package com.chatak.pg.user.bean;

public class UpdateUserRequest 
{
	private Long profile_id;
	
	private String first_name;
	
	private String last_name;
	
	private String middle_name;
	
	private Long phone1;
	
	private Long phone2;
	
	private String address1;
	
	private String address2;
	
	private String city;
	
	private String state;
	
	private String zip;
	
	public String validate(){
		String message = "";
		if(profile_id == null){
			message="profileId is the Required field";
		}
		return message;
	}

	/**
	 * @return the profile_id
	 */
	public Long getProfile_id() {
		return profile_id;
	}

	/**
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}

	/**
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}

	/**
	 * @return the middle_name
	 */
	public String getMiddle_name() {
		return middle_name;
	}

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
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
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
	 * @param profile_id the profile_id to set
	 */
	public void setProfile_id(Long profile_id) {
		this.profile_id = profile_id;
	}

	/**
	 * @param first_name the first_name to set
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	/**
	 * @param last_name the last_name to set
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	/**
	 * @param middle_name the middle_name to set
	 */
	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
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
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
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
	

}
