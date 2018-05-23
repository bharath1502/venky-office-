package com.chatak.pay.controller.model.topup;

public class IssuanceTopupRequest {

	private Integer categoryId;

	private Integer operatorId;

	private Integer topupOfferId;

	private String amount;

	private String mobileNumber;

	/**
	 * @return the categoryId
	 */
	public Integer getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the operatorId
	 */
	public Integer getOperatorId() {
		return operatorId;
	}

	/**
	 * @param operatorId
	 *            the operatorId to set
	 */
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * @return the topupOfferId
	 */
	public Integer getTopupOfferId() {
		return topupOfferId;
	}

	/**
	 * @param topupOfferId
	 *            the topupOfferId to set
	 */
	public void setTopupOfferId(Integer topupOfferId) {
		this.topupOfferId = topupOfferId;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber
	 *            the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
