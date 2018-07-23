package com.chatak.pg.user.bean;


public class GetFeaturesListRequest extends Request{

	private Integer role_id;
	private Long merchant_id;
	
	public String validate(){
		String message = "";
		if(role_id == null){
			message="role_id is required";
		} else if(merchant_id == null){
			message="merchant_id is required";
		}
		return message;
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

}
