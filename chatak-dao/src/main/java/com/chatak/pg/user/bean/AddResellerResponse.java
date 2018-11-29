/**
 * 
 */
package com.chatak.pg.user.bean;

/**
 * @Author: Girmiti Software
 * @Date: Aug 1, 2016
 * @Time: 4:46:44 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class AddResellerResponse extends Response 
{
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = 4725601794234616321L;

	private String merchantCode;
	
	private String password;
	  
	private Long accNum;

	/**
	 * @param accNum the accNum to set
	 */
	public void setAccNum(Long accNum) {
		this.accNum = accNum;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @return the merchantCode
	 */
	public String getMerchantCode() {
		return merchantCode;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
