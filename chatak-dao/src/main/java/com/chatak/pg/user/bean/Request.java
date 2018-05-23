package com.chatak.pg.user.bean;

import java.io.Serializable;

public class Request implements Serializable{

	private Long merchant_id;
	private Long store_id;
	private static final long serialVersionUID = -1099231713497132980L;
	/**
	 * @return the merchant_id
	 */
	public Long getMerchant_id() {
		return merchant_id;
	}
	/**
	 * @param merchant_id the merchant_id to set
	 */
	public void setMerchant_id(Long merchant_id) {
		this.merchant_id = merchant_id;
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
