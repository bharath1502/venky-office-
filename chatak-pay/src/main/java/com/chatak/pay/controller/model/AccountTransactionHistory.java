package com.chatak.pay.controller.model;

import java.util.List;

import com.chatak.pg.model.AccountTransactionDTO;

public class AccountTransactionHistory extends Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2856801880347892941L;

	
	private List<AccountTransactionDTO> executedTransactions;
	
	private List<AccountTransactionDTO> processingTransactions;

	public List<AccountTransactionDTO> getExecutedTransactions() {
		return executedTransactions;
	}

	public void setExecutedTransactions(
			List<AccountTransactionDTO> executedTransactions) {
		this.executedTransactions = executedTransactions;
	}

	public List<AccountTransactionDTO> getProcessingTransactions() {
		return processingTransactions;
	}

	public void setProcessingTransactions(
			List<AccountTransactionDTO> processingTransactions) {
		this.processingTransactions = processingTransactions;
	}


}
