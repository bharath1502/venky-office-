package com.chatak.pay.model;

import java.util.List;

import com.chatak.pay.model.Merchant;
import com.chatak.pay.controller.model.Response;

public class MerchantListResponse extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<Merchant> merchants;

	/**
	 * @return the merchants
	 */
	public List<Merchant> getMerchants() {
		return merchants;
	}

	/**
	 * @param merchants
	 *            the merchants to set
	 */
	public void setMerchants(List<Merchant> merchants) {
		this.merchants = merchants;
	}
}
