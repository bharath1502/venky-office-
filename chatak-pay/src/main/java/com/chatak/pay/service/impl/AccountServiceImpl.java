/**
 * 
 */
package com.chatak.pay.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.mailsender.service.impl.ForkAdvice;
import com.chatak.pay.controller.model.AccountBalanceInquiryRequest;
import com.chatak.pay.controller.model.AccountBalanceInquiryResponse;
import com.chatak.pay.controller.model.AccountTransactionHistory;
import com.chatak.pay.controller.model.Response;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.service.AccountService;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 19-May-2016 3:02:36 pm
 * @version 1.0
 */
@Service
public class AccountServiceImpl implements AccountService {
  
  private static Logger logger = Logger.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private AccountTransactionsDao accountTransactionDao;

	@Override
	public Response validate(Long accountNumber) throws ChatakPayException {
		Response response = new Response();
		try {
			PGAccount account = accountDao.getAccountonAccountNumber(accountNumber);
			if(null == account) {
				response.setErrorCode(ActionErrorCode.ERROR_CODE_201);
			} else {
				response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
			}
		}
		catch(Exception e) {
		  logger.error("ERROR:: AccountServiceImpl:: validate method", e);
			response.setErrorCode(ActionErrorCode.ERROR_CODE_202);
		}
		response.setErrorMessage(ActionErrorCode.getInstance().getMessage(response.getErrorCode()));
		return response;
	}

	@Override
	public AccountBalanceInquiryResponse balanceInquiry(AccountBalanceInquiryRequest accountBalanceInquiryRequest) throws ChatakPayException {
		AccountBalanceInquiryResponse response = new AccountBalanceInquiryResponse();
		try {
			List<Long> accounts = accountBalanceInquiryRequest.getAccountNumbers();
			if(null == accounts || accounts.isEmpty()) {
				response.setErrorCode(ActionErrorCode.ERROR_CODE_201);
			} else {
				List<AccountBalanceDTO> accountBalances = accountDao.getAccDetailsOnAccNums(accounts);
				response.setAccountBalances(accountBalances);
				response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
			}
		}
		catch(Exception e) {
		  logger.error("ERROR:: AccountServiceImpl:: balanceInquiry method", e);
			response.setErrorCode(ActionErrorCode.ERROR_CODE_202);
		}
		response.setErrorMessage(ActionErrorCode.getInstance().getMessage(response.getErrorCode()));
		return response;

	}

	@Override
	public AccountTransactionHistory getAccountHistory(Long accountNumber) throws ChatakPayException {
		AccountTransactionHistory response = new AccountTransactionHistory();
		GetTransactionsListRequest transactionRequest = new GetTransactionsListRequest();
		GetTransactionsListResponse transResponse = new GetTransactionsListResponse();
		try{
			if(null == accountNumber){
				response.setErrorCode(ActionErrorCode.ERROR_CODE_201);
			}else{
				transactionRequest.setAccountNumber(accountNumber.toString());
				transactionRequest.setSettlementStatus(PGConstants.PG_SETTLEMENT_PROCESSING);
				transactionRequest.setTransactionCodeList(getTransactionCodeList());
				transResponse = accountTransactionDao.getAccountAllTransactions(transactionRequest);
				if(null != transResponse){
					response.setProcessingTransactions(transResponse.getAccountTransactionList());
				}
				transactionRequest.setSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
				transResponse = accountTransactionDao.getAccountAllTransactions(transactionRequest);
				if(null != transResponse){
					response.setExecutedTransactions(transResponse.getAccountTransactionList());
				}
				response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
			}
		}catch(Exception e) {
		  logger.error("ERROR:: AccountServiceImpl:: getAccountHistory method", e);
			response.setErrorCode(ActionErrorCode.ERROR_CODE_202);
		}
		response.setErrorMessage(ActionErrorCode.getInstance().getMessage(response.getErrorCode()));
		return response;
	}
	
public List<String> getTransactionCodeList() {
		List<String> txnCodeList = new ArrayList<String>(Integer.parseInt("13"));
		txnCodeList.add(AccountTransactionCode.CC_AMOUNT_CREDIT);
		txnCodeList.add(AccountTransactionCode.CC_AMOUNT_DEBIT);
		txnCodeList.add(AccountTransactionCode.MANUAL_CREDIT);
		txnCodeList.add(AccountTransactionCode.MANUAL_DEBIT);
		txnCodeList.add(AccountTransactionCode.CC_FEE_CREDIT);
		txnCodeList.add(AccountTransactionCode.CC_FEE_DEBIT);
		txnCodeList.add(AccountTransactionCode.EFT_DEBIT);
		txnCodeList.add(AccountTransactionCode.FT_BANK);
		txnCodeList.add(AccountTransactionCode.FT_CHECK);
		txnCodeList.add(AccountTransactionCode.ACCOUNT_CREDIT);
		txnCodeList.add(AccountTransactionCode.ACCOUNT_DEBIT);
		txnCodeList.add(AccountTransactionCode.MANUAL_CREDIT_CB);
		txnCodeList.add(AccountTransactionCode.MANUAL_DEBIT_CB);
		return txnCodeList;
	}
}
