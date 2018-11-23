package com.chatak.pay.controller.model.topup;

public class TopupOfferDetailDTO {

	private Long topupOfferID;

	private Long categoryID;

	private String rechargeAmount;

	private String talktime;

	private String description;

	private String validity;

	/**
	 * @return the topupOfferID
	 */
	public Long getTopupOfferID() {
		return topupOfferID;
	}

	/**
	 * @param topupOfferID
	 *            the topupOfferID to set
	 */
	public void setTopupOfferID(Long topupOfferID) {
		this.topupOfferID = topupOfferID;
	}

	/**
	 * @return the categoryID
	 */
	public Long getCategoryID() {
		return categoryID;
	}

	/**
	 * @param categoryID
	 *            the categoryID to set
	 */
	public void setCategoryID(Long categoryID) {
		this.categoryID = categoryID;
	}

	/**
	 * @return the rechargeAmount
	 */
	public String getRechargeAmount() {
		return rechargeAmount;
	}

	/**
	 * @param rechargeAmount
	 *            the rechargeAmount to set
	 */
	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	/**
	 * @return the talktime
	 */
	public String getTalktime() {
		return talktime;
	}

	/**
	 * @param talktime
	 *            the talktime to set
	 */
	public void setTalktime(String talktime) {
		this.talktime = talktime;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the validity
	 */
	public String getValidity() {
		return validity;
	}

	/**
	 * @param validity
	 *            the validity to set
	 */
	public void setValidity(String validity) {
		this.validity = validity;
	}

}
