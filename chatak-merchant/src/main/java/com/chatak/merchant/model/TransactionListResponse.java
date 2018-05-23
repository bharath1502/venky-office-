package com.chatak.merchant.model;

import java.util.List;

import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.user.bean.Transaction;

public class TransactionListResponse extends Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1186959819834121724L;
	
	private List<Transaction> transactionList;
	
	private List<AccountTransactionDTO> accountTxnList;

	/**
	 * @return the transactionList
	 */
	public List<Transaction> getTransactionList() {
		return transactionList;
	}

	/**
	 * @param transactionList the transactionList to set
	 */
	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}

	/**
	 * @return the accountTxnList
	 */
	public List<AccountTransactionDTO> getAccountTxnList() {
		return accountTxnList;
	}

	/**
	 * @param accountTxnList the accountTxnList to set
	 */
	public void setAccountTxnList(List<AccountTransactionDTO> accountTxnList) {
		this.accountTxnList = accountTxnList;
	}
	
}
