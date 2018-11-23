package com.chatak.acquirer.admin.model;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chatak.pg.bean.settlement.SettlementEntity;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.user.bean.Transaction;

public class TransactionResponse extends Response {

	private static final long serialVersionUID = 1186959819834121724L;
	
	private List<Transaction> transactionList;
	
	private List<AccountTransactionDTO> accountTxnList;
	
	// ISO total amounts
	private Map<Long, EntityMappedTransactions> isoTotalRevenue = new HashMap<Long, EntityMappedTransactions>();

	// Merchant total amounts
	private Map<String, EntityMappedTransactions> isoMappedMerchantTotalRevenue = new HashMap<String, EntityMappedTransactions>();

	// Merchant total amounts
	private Map<String, EntityMappedTransactions> pmMappedMerchantTotalRevenue = new HashMap<String, EntityMappedTransactions>();

	// Total amount to be debit'ed from PM's current balance, will give the remaining PM earned amount
	private BigInteger pmDebitAmount = new BigInteger("0");
	
	private List<SettlementEntity> settlementEntity;

	public List<Transaction> getTransactionList() {
		return transactionList;
	}

	public void setAccountTxnList(List<AccountTransactionDTO> accountTxnList) {
		this.accountTxnList = accountTxnList;
	}
	
	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}

	public List<AccountTransactionDTO> getAccountTxnList() {
		return accountTxnList;
	}

	public Map<Long, EntityMappedTransactions> getIsoTotalRevenue() {
		return isoTotalRevenue;
	}

	public Map<String, EntityMappedTransactions> getIsoMappedMerchantTotalRevenue() {
		return isoMappedMerchantTotalRevenue;
	}

	public Map<String, EntityMappedTransactions> getPmMappedMerchantTotalRevenue() {
		return pmMappedMerchantTotalRevenue;
	}

	public BigInteger getPmDebitAmount() {
		return pmDebitAmount;
	}

	public void setIsoTotalRevenue(Map<Long, EntityMappedTransactions> isoTotalRevenue) {
		this.isoTotalRevenue = isoTotalRevenue;
	}

	public void setIsoMappedMerchantTotalRevenue(Map<String, EntityMappedTransactions> isoMappedMerchantTotalRevenue) {
		this.isoMappedMerchantTotalRevenue = isoMappedMerchantTotalRevenue;
	}

	public void setPmMappedMerchantTotalRevenue(Map<String, EntityMappedTransactions> pmMappedMerchantTotalRevenue) {
		this.pmMappedMerchantTotalRevenue = pmMappedMerchantTotalRevenue;
	}

	public void setPmDebitAmount(BigInteger pmDebitAmount) {
		this.pmDebitAmount = pmDebitAmount;
	}

	/**
	 * @return the settlementEntity
	 */
	public List<SettlementEntity> getSettlementEntity() {
		return settlementEntity;
	}

	/**
	 * @param settlementEntity the settlementEntity to set
	 */
	public void setSettlementEntity(List<SettlementEntity> settlementEntity) {
		this.settlementEntity = settlementEntity;
	}
	
}
