package com.chatak.pay.controller.model;

public class LoyaltyResponse extends Response {

	private static final long serialVersionUID = 1L;
	
	private String value;

	private Long accountHolderId;
	
	private Long redeemPointsAmount;
	
	private String loyaltyBalance;
	
	public Long getAccountHolderId() {
		return accountHolderId;
	}

	public void setAccountHolderId(Long accountHolderId) {
		this.accountHolderId = accountHolderId;
	}

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
