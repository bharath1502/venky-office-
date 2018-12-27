package com.chatak.pay.controller.model;

import java.util.List;

import com.chatak.pg.user.bean.TransactionHistory;

public class TransactionHistoryResponse extends Response {

	private static final long serialVersionUID = 1L;

	private List<TransactionHistory> transactionList;

	public List<TransactionHistory> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<TransactionHistory> transactionList) {
		this.transactionList = transactionList;
	}
}
