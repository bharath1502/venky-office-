package com.chatak.pg.user.bean;

import java.io.Serializable;

import com.chatak.pg.util.CommonUtil;

public class MerchantBank implements Serializable{
	private Long merchant_code;
	private String bank_name;
	private String bank_acc_number;
	private String bank_code;
	private Integer currency_code;
	private String account_type;
	private String created_date;
	private String updated_date;
	private Integer status;
	
	public String validate(){
		String message = "";
		if(merchant_code == null){
			message="merchant_name is the Required field";
		} else if(CommonUtil.isNullAndEmpty(bank_acc_number)){
			message="bank_acc_number is the Required field";
		} else if(CommonUtil.isNullAndEmpty(bank_name)){
			message="bank_name is the Required field";
		} else if(CommonUtil.isNullAndEmpty(bank_code)){
			message="bank_code is the Required field";
		} else if(currency_code == null){
			message="currency_code is the Required field";
		} else if(CommonUtil.isNullAndEmpty(account_type)){
			message="account_type is the Required field";
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
	 * @param bank_name the bank_name to set
	 */
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	/**
	 * @return the bank_acc_number
	 */
	public String getBank_acc_number() {
		return bank_acc_number;
	}
	
	/**
	 * @param merchant_code the merchant_code to set
	 */
	public void setMerchant_code(Long merchant_code) {
		this.merchant_code = merchant_code;
	}

	/**
	 * @param bank_acc_number the bank_acc_number to set
	 */
	public void setBank_acc_number(String bank_acc_number) {
		this.bank_acc_number = bank_acc_number;
	}
	
	/**
	 * @return the bank_name
	 */
	public String getBank_name() {
		return bank_name;
	}

	/**
	 * @param bank_code the bank_code to set
	 */
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	/**
	 * @return the currency_code
	 */
	public Integer getCurrency_code() {
		return currency_code;
	}
	
	/**
	 * @return the bank_code
	 */
	public String getBank_code() {
		return bank_code;
	}

	/**
	 * @param currency_code the currency_code to set
	 */
	public void setCurrency_code(Integer currency_code) {
		this.currency_code = currency_code;
	}

	/**
	 * @return the account_type
	 */
	public String getAccount_type() {
		return account_type;
	}

	/**
	 * @param account_type the account_type to set
	 */
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	/**
	 * @return the created_date
	 */
	public String getCreated_date() {
		return created_date;
	}

	/**
	 * @param created_date the created_date to set
	 */
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	/**
	 * @return the updated_date
	 */
	public String getUpdated_date() {
		return updated_date;
	}

	/**
	 * @param updated_date the updated_date to set
	 */
	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

}
