package com.chatak.pg.user.bean;


public class GetMerchantBankDetailsResponse extends Response{

	private MerchantBank merchantBank;

	/**
	 * @return the merchantBank
	 */
	public MerchantBank getMerchantBank() {
		return merchantBank;
	}

	/**
	 * @param merchantBank the merchantBank to set
	 */
	public void setMerchantBank(MerchantBank merchantBank) {
		this.merchantBank = merchantBank;
	}
	
}
