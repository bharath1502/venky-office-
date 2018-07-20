package com.chatak.pg.user.bean;


public class GetMerchantBankDetailsRequest {

	private Long merchant_code;
	
	public String validate(){
		String message = "";
		if(merchant_code == null){
			message="merchant_code is the Required field";
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
	
	
	
}
