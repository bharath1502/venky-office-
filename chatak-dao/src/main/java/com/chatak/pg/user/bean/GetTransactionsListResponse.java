package com.chatak.pg.user.bean;

import java.util.List;

import com.chatak.pg.model.AccountTransactionDTO;

public class GetTransactionsListResponse extends Response

{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Transaction> transactionList;
	
	private List<AccountTransactionDTO> accountTransactionList;
	private Integer totalResultCount;
	/**
	 * @return the totalNoOfRows
	 */
	public Integer getTotalNoOfRows() {
		return TotalNoOfRows;
	}
	/**
	 * @param totalNoOfRows the totalNoOfRows to set
	 */
	public void setTotalNoOfRows(Integer totalNoOfRows) {
		TotalNoOfRows = totalNoOfRows;
	}
	private Integer fetchedRecordsCount;
	
	private Integer TotalNoOfRows;
	/**
	 * @return the transactionList
	 */
	public List<Transaction> getTransactionList() {
		return transactionList;
	}
	/**
	 * @return the fetchedRecordsCount
	 */
	public Integer getFetchedRecordsCount() {
		return fetchedRecordsCount;
	}
	/**
	 * @param transactionList the transactionList to set
	 */
	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}
	/**
	 * @param fetchedRecordsCount the fetchedRecordsCount to set
	 */
	public void setFetchedRecordsCount(Integer fetchedRecordsCount) {
		this.fetchedRecordsCount = fetchedRecordsCount;
	}
	/**
	 * @return the totalResultCount
	 */
	public Integer getTotalResultCount() {
		return totalResultCount;
	}
	/**
	 * @param totalResultCount the totalResultCount to set
	 */
	public void setTotalResultCount(Integer totalResultCount) {
		this.totalResultCount = totalResultCount;
	}
  public List<AccountTransactionDTO> getAccountTransactionList() {
    return accountTransactionList;
  }
  public void setAccountTransactionList(List<AccountTransactionDTO> accountTransactionList) {
    this.accountTransactionList = accountTransactionList;
  } 

}
