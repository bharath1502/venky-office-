package com.chatak.acquirer.admin.model;

import java.util.List;

import com.chatak.pg.model.Bank;

public class BankSearchResponse extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2396099009684888892L;
	
	private List<Bank> banks;

	public List<Bank> getBanks() {
		return banks;
	}

	public void setBanks(List<Bank> banks) {
		this.banks = banks;
	}
	

}
