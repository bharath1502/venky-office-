package com.chatak.pg.user.bean;


public class CreateRoleFeatureMappingRequest  {

	private Long merchant_code;
	
	private Integer feature_id;
	
	private Integer role_id;
	
	private Integer display_order;
	
	public String validate(){
		String message = "";
		if(merchant_code == null || feature_id == null || role_id == null){
			message="merchant_code, feature_id and role_id  are the Required fields";
		}
		return message;
	}
	
	/**
	 * @param merchant_code the merchant_code to set
	 */
	public void setMerchant_code(Long merchant_code) {
		this.merchant_code = merchant_code;
	}

	/**
	 * @return the merchant_code
	 */
	public Long getMerchant_code() {
		return merchant_code;
	}

	/**
	 * @return the feature_id
	 */
	public Integer getFeature_id() {
		return feature_id;
	}
	
	/**
	 * @param feature_id the feature_id to set
	 */
	public void setFeature_id(Integer feature_id) {
		this.feature_id = feature_id;
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
	 * @return the display_order
	 */
	public Integer getDisplay_order() {
		return display_order;
	}

	/**
	 * @param display_order the display_order to set
	 */
	public void setDisplay_order(Integer display_order) {
		this.display_order = display_order;
	}
	
}
