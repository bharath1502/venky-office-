/**
 * 
 */
package com.chatak.acquirer.admin.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.bean.TransactionPopUpDataDto;
import com.chatak.pg.model.LitleEFTDTOsList;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.model.SettlementActionDTOList;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;

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
	public GetTransactionsListResponse searchTransactions(GetTransactionsListRequest getTransactionsListRequest , Long entityId) throws ChatakAdminException;
	
	public GetTransactionsListResponse searchTransactionsBySettlementStatus(GetTransactionsListRequest getTransactionsListRequest) throws ChatakAdminException;
	
	public GetTransactionsListResponse getAllTransactions(GetTransactionsListRequest getTransactionsListRequest ,  Long entityID) throws ChatakAdminException;
	
	public List<ReportsDTO> getAllAccountsExecutedTransactions();
	
	public GetTransactionsListResponse getAllTransactionsOnMerchantCode(GetTransactionsListRequest getTransactionsListRequest);
	
	public GetTransactionsListResponse searchTransactionsReport(GetTransactionsListRequest getTransactionsListRequest) throws ChatakAdminException; 
	
	public List<ReportsDTO> getAllAccountsExecutedTransactionsOnDate(GetTransactionsListRequest getTransactionsListRequest);
	
	public GetTransactionsListResponse getAllAccountsPendingTransactionsOnDate(GetTransactionsListRequest getTransactionsListRequest);

	
	public List<ReportsDTO> getAllManualTransactionListReport(GetTransactionsListRequest getTransactionsListRequest);
	
	public List<ReportsDTO> getAllTransactionsOnDate(GetTransactionsListRequest getTransactionsListRequest);
	
	public List<ReportsDTO> getAllExecutedAccTransFeeOnDate(GetTransactionsListRequest getTransactionsListRequest);
	
	public List<ReportsDTO> getSystemOverViewData();
	
	public List<ReportsDTO> getVirtualAccountFeeLogOnDate(GetTransactionsListRequest getTransactionsListRequest);
	
	public GetTransactionsListResponse searchAllTransactions(GetTransactionsListRequest getTransactionsListRequest) throws ChatakAdminException;
	
	public void configureReqObj(HttpServletRequest request, HttpSession session, SettlementActionDTOList actionDTOList, String requestObject, String[] removedTxns) throws ChatakAdminException;
	
	public void configureLitleReqObj(HttpServletRequest request, HttpSession session, LitleEFTDTOsList litleEFTDTOsList, String requestObject, String[] removedTxns) throws ChatakAdminException;
	
	public void prepareAndBindTxnPopupData(List<Transaction> transactionList) throws ChatakAdminException;
	
	public GetTransactionsListResponse searchAccountTransactions(GetTransactionsListRequest getTransactionsListRequest);
	
	public TransactionPopUpDataDto fetchTransactionDetails(String accountTransactionId);
	
	public String getPartialRefundBalance(String accountTransactionId,String merchantCode);
	
	public GetTransactionsListResponse searchAccountTransactionsForEntityId(GetTransactionsListRequest getTransactionsListRequest, Long entityId, String userType);
	
}