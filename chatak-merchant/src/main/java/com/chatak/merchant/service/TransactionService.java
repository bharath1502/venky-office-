/**
 * 
 */
package com.chatak.merchant.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.bean.TransactionPopUpDataDto;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.model.SettlementActionDTOList;
import com.chatak.pg.user.bean.GetAccountHistoryListResponse;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 05-Jan-2015 10:05:06 PM
 * @version 1.0
 */
public interface TransactionService {

	/**
	 * Method to search Transactions
	 * 
	 * @param getTransactionsListRequest
	 * @return
	 * @throws ChatakAdminException
	 */
	public GetTransactionsListResponse searchTransactions(GetTransactionsListRequest getTransactionsListRequest) throws ChatakMerchantException;
	
	public GetAccountHistoryListResponse getAccountHistory(String accountNumber, int pageNumber) throws ChatakMerchantException;
	
	public GetTransactionsListResponse getAllTransactions(String merchantCode) throws ChatakMerchantException;
	
	public List<ReportsDTO> getAllExecutedAccTransFeeOnDate(GetTransactionsListRequest getTransactionsListRequest);
	
	public GetTransactionsListResponse searchAllTransactions(GetTransactionsListRequest getTransactionsListRequest) throws ChatakMerchantException;
	
	public boolean hasAccessTo(String operation, String merchantId, HttpSession session) ;
	
	public void configureReqObj(HttpServletRequest request, HttpSession session, SettlementActionDTOList actionDTOList, String requestObject, String[] removedTxns) throws ChatakMerchantException;
	
	public GetTransactionsListResponse searchAccountTransactions(GetTransactionsListRequest getTransactionsListRequest);

	public TransactionPopUpDataDto fetchTransactionDetails(String accountTransactionId);
	
	public String getPartialRefundBalance(String accountTransactionId,String merchantCode);

	public Long findMerchantFeeByMerchantId(String merchantId);
	
	public GetTransactionsListResponse searchManulAccountTransactions(GetTransactionsListRequest getTransactionsListRequest);
}
