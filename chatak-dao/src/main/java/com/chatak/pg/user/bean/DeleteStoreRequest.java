package com.chatak.pg.user.bean;


public class DeleteStoreRequest {

	private Long merchant_code;
	private Long store_id;
	
	public String validate(){
		String message = "";
		if(merchant_code == null){
			message="merchant_code is the Required field";
		} else if(store_id == null){
			message="store_id is the Required field";
		} 
		return message;
	}

	/**
	 * @return the merchant_code
	 */
	public Long getMerchant_code() {
		return merchant_code;
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
	
}
