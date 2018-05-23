package com.chatak.pay.controller.model.topup;

import com.chatak.pay.controller.model.Response;

public class TopupResponse extends Response {

	private static final long serialVersionUID = 1L;

	private Integer operatorId;

	private Integer categoryId;

	private Integer topupOfferId;

	private String mobileNumber;

	private String amount;

	private String status;

	private String transRefNumb;

	private String createdDate;

	private String updatedDate;

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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the transRefNumb
	 */
	public String getTransRefNumb() {
		return transRefNumb;
	}

	/**
	 * @param transRefNumb
	 *            the transRefNumb to set
	 */
	public void setTransRefNumb(String transRefNumb) {
		this.transRefNumb = transRefNumb;
	}

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedDate
	 */
	public String getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate
	 *            the updatedDate to set
	 */
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

}
