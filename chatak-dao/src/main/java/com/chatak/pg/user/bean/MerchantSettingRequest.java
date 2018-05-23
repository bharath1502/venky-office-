package com.chatak.pg.user.bean;

public class MerchantSettingRequest {

	private String merchant_code;
	private Long max_txn_month;
	private String settlement_option;
	private Long max_txn_day;
	private Long max_txn_week;
	private Integer settlement_frequency;
	private Double max_amt_per_txn;
	
	/**
	 * @return the max_txn_week
	 */
	public Long getMax_txn_week() {
		return max_txn_week;
	}
	/**
	 * @param merchant_code the merchant_code to set
	 */
	public void setMerchant_code(String merchant_code) {
		this.merchant_code = merchant_code;
	}
	/**
	 * @return the max_amt_per_txn
	 */
	public Double getMax_amt_per_txn() {
		return max_amt_per_txn;
	}
	/**
	 * @param settlement_option the settlement_option to set
	 */
	public void setSettlement_option(String settlement_option) {
		this.settlement_option = settlement_option;
	}
	/**
	 * @param settlement_frequency the settlement_frequency to set
	 */
	public void setSettlement_frequency(Integer settlement_frequency) {
		this.settlement_frequency = settlement_frequency;
	}
	/**
	 * @return the max_txn_day
	 */
	public Long getMax_txn_day() {
		return max_txn_day;
	}
	/**
	 * @return the max_txn_month
	 */
	public Long getMax_txn_month() {
		return max_txn_month;
	}
	/**
	 * @param max_txn_day the max_txn_day to set
	 */
	public void setMax_txn_day(Long max_txn_day) {
		this.max_txn_day = max_txn_day;
	}
	/**
	 * @return the settlement_frequency
	 */
	public Integer getSettlement_frequency() {
		return settlement_frequency;
	}
	/**
	 * @param max_txn_week the max_txn_week to set
	 */
	public void setMax_txn_week(Long max_txn_week) {
		this.max_txn_week = max_txn_week;
	}
	/**
	 * @return the settlement_option
	 */
	public String getSettlement_option() {
		return settlement_option;
	}
	/**
	 * @param max_txn_month the max_txn_month to set
	 */
	public void setMax_txn_month(Long max_txn_month) {
		this.max_txn_month = max_txn_month;
	}
	/**
	 * @return the merchant_code
	 */
	public String getMerchant_code() {
		return merchant_code;
	}
	/**
	 * @param max_amt_per_txn the max_amt_per_txn to set
	 */
	public void setMax_amt_per_txn(Double max_amt_per_txn) {
		this.max_amt_per_txn = max_amt_per_txn;
	}
	
}
