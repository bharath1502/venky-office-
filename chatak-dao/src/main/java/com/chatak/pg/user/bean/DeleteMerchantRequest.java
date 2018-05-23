package com.chatak.pg.user.bean;


public class DeleteMerchantRequest {
  
	private String merchantCode;
	
	public String validate(){
		String message = "";
		if(merchantCode == null){
			message="merchant_code is the Required field";
		} 
		return message;
	}

  /**
   * @return the merchantCode
   */
  public String getMerchantCode() {
    return merchantCode;
  }

  /**
   * @param merchantCode the merchantCode to set
   */
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

	
}
