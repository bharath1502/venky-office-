package com.chatak.pay.controller.model;

public class LoyaltyProgramRequest extends Request{
	
/**
	 * 
	 */
	private static final long serialVersionUID = -1210724023437455155L;

    private Long txnAmount;
	
	private String mobileNumber;
	
	private String accountNumber;
	
	private Long merchantId;
	
	private Long isoId;
	
	private String loyaltyUrl;
	
	private String email;
	
	private String loyaltyProgramType;
	
	private String password;
	
	public String getLoyaltyProgramType() {
		return loyaltyProgramType;
	}

	public void setLoyaltyProgramType(String loyaltyProgramType) {
		this.loyaltyProgramType = loyaltyProgramType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(Long txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getIsoId() {
		return isoId;
	}

	public void setIsoId(Long isoId) {
		this.isoId = isoId;
	}

	public String getLoyaltyUrl() {
		return loyaltyUrl;
	}

	public void setLoyaltyUrl(String loyaltyUrl) {
		this.loyaltyUrl = loyaltyUrl;
	}
	

}
