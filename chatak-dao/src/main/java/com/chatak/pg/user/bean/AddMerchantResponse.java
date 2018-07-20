package com.chatak.pg.user.bean;


public class AddMerchantResponse extends Response {
	
	/**
   * 
   */
  private static final long serialVersionUID = 4124080161329849537L;
  
  private String merchantCode;
  
  private Long accNum;
  
  private String password;

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

  /**
   * @return the accNum
   */
  public Long getAccNum() {
    return accNum;
  }

  /**
   * @param accNum the accNum to set
   */
  public void setAccNum(Long accNum) {
    this.accNum = accNum;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


}
