package com.chatak.pay.controller.model;

public class LoyaltyResponse extends Response {

	private static final long serialVersionUID = 1L;
	
	private String value;

	private Long acc_holder_id;
	
	private Long redeemPointsAmount;
	
	private String loyaltyBalance;

	public Long getRedeemPointsAmount() {
		return redeemPointsAmount;
	}

	public void setRedeemPointsAmount(Long redeemPointsAmount) {
		this.redeemPointsAmount = redeemPointsAmount;
	}

	public String getLoyaltyBalance() {
		return loyaltyBalance;
	}

	public void setLoyaltyBalance(String loyaltyBalance) {
		this.loyaltyBalance = loyaltyBalance;
	}

	public Long getAcc_holder_id() {
		return acc_holder_id;
	}

	public void setAcc_holder_id(Long acc_holder_id) {
		this.acc_holder_id = acc_holder_id;
	}


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
