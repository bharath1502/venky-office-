package com.chatak.pg.user.bean;


public class GetMerchantBankResponse extends Response {
	private MerchantBank merchantbank;

	/**
	 * @return the merchantbank
	 */
	public MerchantBank getMerchantbank() {
		return merchantbank;
	}

	/**
	 * @param merchantbank the merchantbank to set
	 */
	public void setMerchantbank(MerchantBank merchantbank) {
		this.merchantbank = merchantbank;
	}
}
