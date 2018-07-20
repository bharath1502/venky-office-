package com.chatak.merchant.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.merchant.controller.model.LoginResponse;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountHistoryDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.ExecutedTransactionDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.MerchantUserDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountHistory;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.bean.TransactionPopUpDataDto;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.FeatureConstants;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.model.SettlementActionDTOList;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

	@InjectMocks
	private TransactionServiceImpl transactionServiceImpl = new TransactionServiceImpl();

	@Mock
	private TransactionDao transactionDao;

	@Mock
	private AccountHistoryDao accountHistoryDao;

	@Mock
	private MerchantDao merchantDao;

	@Mock
	private AccountDao accountDao;

	@Mock
	private MerchantUserDao merchantUserDao;

	@Mock
	private AccountTransactionsDao accountTransactionsDao;

	@Mock
	AccountRepository accountRepository;

	@Mock
	MerchantRepository merchantRepository;

	@Mock
	VoidTransactionDao voidTransactionDao;

	@Mock
	RefundTransactionDao refundTransactionDao;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Mock
	ExecutedTransactionDao executedTransactionDao;

	@Mock
	private CurrencyConfigDao currencyConfigDao;

	@Mock
	GetTransactionsListRequest getTransactionsListRequest;

	@Mock
	HttpSession session;

	@Mock
	HttpServletRequest request;

	@Mock
	GetTransactionsListResponse getTransactionsListResponse;

	@Test
	public void testSearchTransactions() throws ChatakMerchantException {
		getTransactionsListRequest = new GetTransactionsListRequest();
		List<Transaction> transactions = new ArrayList<>();
		Transaction transaction = new Transaction();
		transaction.setMaskCardNumber("maskCard");
		transactions.add(transaction);
		Mockito.when(refundTransactionDao.getMerchantAndItsSubMerchantTransactionList(Matchers.any(GetTransactionsListRequest.class)))
				.thenReturn(transactions);
		Assert.assertNotNull(transactionServiceImpl.searchTransactions(getTransactionsListRequest));
	}

	@Test
	public void testSearchTransactionsElse() throws ChatakMerchantException {
		getTransactionsListRequest = new GetTransactionsListRequest();
		Mockito.when(refundTransactionDao.getMerchantAndItsSubMerchantTransactionList(Matchers.any(GetTransactionsListRequest.class)))
				.thenThrow(new NullPointerException());
		Assert.assertNull(transactionServiceImpl.searchTransactions(getTransactionsListRequest));
	}

	@Test
	public void testGetAccountHistory() throws ChatakMerchantException {
		List<PGAccountHistory> historyList = new ArrayList<>();
		PGAccountHistory accountHistory = new PGAccountHistory();
		historyList.add(accountHistory);
		Mockito.when(accountHistoryDao.getHistoryByAccountNum(Matchers.anyLong())).thenReturn(historyList);
		Assert.assertNotNull(transactionServiceImpl.getAccountHistory("123456789", 1));
	}

	@Test
	public void testGetAccountHistoryElse() throws ChatakMerchantException {
		List<PGAccountHistory> historyList = null;
		Mockito.when(accountHistoryDao.getHistoryByAccountNum(Matchers.anyLong())).thenReturn(historyList);
		Assert.assertNotNull(transactionServiceImpl.getAccountHistory("123456789", 1));
	}

	@Test
	public void testGetAccountHistoryException() throws ChatakMerchantException {
		Mockito.when(accountHistoryDao.getHistoryByAccountNum(Matchers.anyLong()))
				.thenThrow(new NullPointerException());
		Assert.assertNull(transactionServiceImpl.getAccountHistory("123456789", 1));
	}

	@Test
	public void testGetAllTransaction() throws ChatakMerchantException {
		List<PGTransaction> pgTransactions = new ArrayList<>();
		PGTransaction pgTransaction = new PGTransaction();
		pgTransactions.add(pgTransaction);
		PGMerchant pgMerchant = new PGMerchant();
		PGAccount account = new PGAccount();
		pgMerchant.setLocalCurrency("34");
		pgTransaction.setTxnAmount(Long.parseLong("35"));
		pgTransaction.setIssuerTxnRefNum("12354");
		pgTransaction.setStatus(0);
		Mockito.when(merchantRepository.findByMerchantCode(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(account);
		Mockito.when(voidTransactionDao.getAllTransactionsOnMerchantCode(Matchers.anyString()))
				.thenReturn(pgTransactions);
		Assert.assertNotNull(transactionServiceImpl.getAllTransactions("5679"));
	}

	@Test
	public void testGetAllTransactionDeclined() throws ChatakMerchantException {
		List<PGTransaction> pgTransactions = new ArrayList<>();
		PGTransaction pgTransaction = new PGTransaction();
		pgTransactions.add(pgTransaction);
		PGMerchant pgMerchant = new PGMerchant();
		PGAccount account = new PGAccount();
		pgMerchant.setLocalCurrency("34");
		pgTransaction.setTxnAmount(Long.parseLong("35"));
		pgTransaction.setIssuerTxnRefNum("12354");
		pgTransaction.setStatus(1);
		Mockito.when(merchantRepository.findByMerchantCode(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(account);
		Mockito.when(voidTransactionDao.getAllTransactionsOnMerchantCode(Matchers.anyString()))
				.thenReturn(pgTransactions);
		Assert.assertNotNull(transactionServiceImpl.getAllTransactions("5679"));
	}

	@Test
	public void testGetAllTransactionFailed() throws ChatakMerchantException {
		List<PGTransaction> pgTransactions = new ArrayList<>();
		PGTransaction pgTransaction = new PGTransaction();
		pgTransactions.add(pgTransaction);
		PGMerchant pgMerchant = new PGMerchant();
		PGAccount account = new PGAccount();
		pgMerchant.setLocalCurrency("34");
		pgTransaction.setTxnAmount(Long.parseLong("35"));
		pgTransaction.setIssuerTxnRefNum("12354");
		pgTransaction.setStatus(Integer.parseInt("2"));
		Mockito.when(merchantRepository.findByMerchantCode(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(account);
		Mockito.when(voidTransactionDao.getAllTransactionsOnMerchantCode(Matchers.anyString()))
				.thenReturn(pgTransactions);
		Assert.assertNotNull(transactionServiceImpl.getAllTransactions("5679"));
	}

	@Test
	public void testGetAllTransactionElse() throws ChatakMerchantException {
		List<PGTransaction> pgTransactions = null;
		Mockito.when(voidTransactionDao.getAllTransactionsOnMerchantCode(Matchers.anyString()))
				.thenReturn(pgTransactions);
		Assert.assertNotNull(transactionServiceImpl.getAllTransactions("5679"));
	}

	@Test
	public void testGetAllTransactionException() throws ChatakMerchantException {
		Mockito.when(voidTransactionDao.getAllTransactionsOnMerchantCode(Matchers.anyString()))
				.thenThrow(new NullPointerException());
		Assert.assertNull(transactionServiceImpl.getAllTransactions("5679"));
	}

	@Test
	public void testGetAllExecutedAccTransFeeOnDate() throws ChatakMerchantException {
		Assert.assertNotNull(transactionServiceImpl.getAllExecutedAccTransFeeOnDate(getTransactionsListRequest));
	}

	@Test
	public void testGetEntryModeEnumFromPosEntryMode() throws ChatakMerchantException {
		Assert.assertNotNull(transactionServiceImpl.getEntryModeEnumFromPosEntryMode("ADGS"));
	}

	@Test
	public void testGetEntryModeEnumFromPosEntryModeException() throws ChatakMerchantException {
		Assert.assertNotNull(transactionServiceImpl.getEntryModeEnumFromPosEntryMode(null));
	}

	@Test
	public void testSearchAllTransactions() throws ChatakMerchantException {
		Assert.assertNotNull(transactionServiceImpl.searchAllTransactions(getTransactionsListRequest));
	}

	@Test
	public void testSearchAllTransactionsException() throws ChatakMerchantException {
		Assert.assertNull(transactionServiceImpl.searchAllTransactions(null));
	}

	@Test
	public void testHasAccessTo() {
		List<PGMerchant> merchants = new ArrayList<>();
		PGMerchant loginMerchant = new PGMerchant();
		merchants.add(loginMerchant);
		LoginResponse loginResponse = new LoginResponse();
		List<String> existingFeature = new ArrayList<>();
		existingFeature.add("");
		loginResponse.setUserId(Long.parseLong("124"));
		loginMerchant.setMerchantCode("32453");
		loginResponse.setExistingFeature(existingFeature);
		Mockito.when(session.getAttribute(Matchers.anyString())).thenReturn(loginResponse);
		Mockito.when(merchantUserDao.getMerchant(Matchers.anyLong())).thenReturn(merchants);
		Mockito.when(merchantDao.getMerchantTypeOnMerchantCode(Matchers.anyString())).thenReturn("alex");
		Assert.assertNotNull(
				transactionServiceImpl.hasAccessTo(FeatureConstants.VOID_SUB_MERCHANT_TRANSACTIONS, null, session));
	}

	@Test(expected = ChatakMerchantException.class)
	public void testConfigureReqObj() throws ChatakMerchantException {
		SettlementActionDTOList actionDTOList = new SettlementActionDTOList();
		String[] removedTxns = { "3", "4" };
		transactionServiceImpl.configureReqObj(request, session, actionDTOList, "4", removedTxns);
	}

	@Test
	public void testSearchAccountTransactions() {
		List<AccountTransactionDTO> accountTransactionDTOs = new ArrayList<>();
		AccountTransactionDTO accountTransactionDTO = new AccountTransactionDTO();
		GetTransactionsListResponse response = new GetTransactionsListResponse();
		response.setAccountTransactionList(accountTransactionDTOs);
		response.setTotalNoOfRows(Integer.parseInt("12"));
		accountTransactionDTOs.add(accountTransactionDTO);
		getTransactionsListRequest = new GetTransactionsListRequest();
		response.setAccountTransactionList(accountTransactionDTOs);
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		pgCurrencyConfig.setCurrencyExponent(Integer.parseInt("123"));
		Mockito.when(accountTransactionsDao.getAccountTransactions(getTransactionsListRequest)).thenReturn(response);
		Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString())).thenReturn(pgCurrencyConfig);
		getTransactionsListResponse = transactionServiceImpl.searchAccountTransactions(getTransactionsListRequest);
	}

	@Test
	public void testSearchAccountTransactionsNull() {
		List<AccountTransactionDTO> accountTransactionDTOs = new ArrayList<>();
		AccountTransactionDTO accountTransactionDTO = new AccountTransactionDTO();
		GetTransactionsListResponse response = new GetTransactionsListResponse();
		response.setAccountTransactionList(accountTransactionDTOs);
		getTransactionsListRequest = new GetTransactionsListRequest();
		accountTransactionDTOs.add(accountTransactionDTO);
		response.setAccountTransactionList(accountTransactionDTOs);
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		pgCurrencyConfig.setCurrencyExponent(Integer.parseInt("123"));
		Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString())).thenReturn(pgCurrencyConfig);
		transactionServiceImpl.searchAccountTransactions(getTransactionsListRequest);
	}

	@Test
	public void testFetchTransactionDetails() {
		List<PGAccountTransactions> pgAccountTransactions = new ArrayList<>();
		PGAccountTransactions transactions = new PGAccountTransactions();
		pgAccountTransactions.add(transactions);
		PGAccount pgAccount = new PGAccount();
		TransactionPopUpDataDto transactionPopUpDto = new TransactionPopUpDataDto();
		transactions.setAccountNumber("593458");
		transactions.setPgTransactionId("5345435");
		Mockito.when(refundTransactionDao.getTransactionPopUpDataDto(Matchers.anyString()))
				.thenReturn(transactionPopUpDto);
		Mockito.when(accountRepository.findByAccountNum(Matchers.anyLong())).thenReturn(pgAccount);
		Mockito.when(accountTransactionsDao.getAccountTransactions(Matchers.anyString()))
				.thenReturn(pgAccountTransactions);
		Assert.assertNotNull(transactionServiceImpl.fetchTransactionDetails("Txn"));
	}

	@Test
	public void testFetchTransactionDetailsElse() {
		List<PGAccountTransactions> pgAccountTransactions = new ArrayList<>();
		PGAccountTransactions transactions = new PGAccountTransactions();
		transactions.setTransactionType(PGConstants.FUND_TRANSFER_CHECK);
		pgAccountTransactions.add(transactions);
		PGAccount pgAccount = new PGAccount();
		TransactionPopUpDataDto transactionPopUpDto = new TransactionPopUpDataDto();
		transactions.setAccountNumber("593458");
		transactions.setPgTransactionId(null);
		transactions.setDebit(Long.parseLong("12345"));
		pgAccount.setCurrency("4324");
		Mockito.when(refundTransactionDao.getTransactionPopUpDataDto(Matchers.anyString()))
				.thenReturn(transactionPopUpDto);
		Mockito.when(accountRepository.findByAccountNum(Matchers.anyLong())).thenReturn(pgAccount);
		Mockito.when(accountTransactionsDao.getAccountTransactions(Matchers.anyString()))
				.thenReturn(pgAccountTransactions);
		Assert.assertNotNull(transactionServiceImpl.fetchTransactionDetails("Txn"));
	}

	@Test
	public void testFetchTransactionDetailsManualCase() {
		List<PGAccountTransactions> pgAccountTransactions = new ArrayList<>();
		PGAccountTransactions transactions = new PGAccountTransactions();
		transactions.setTransactionType(AccountTransactionCode.MANUAL_DEBIT);
		pgAccountTransactions.add(transactions);
		PGAccount pgAccount = new PGAccount();
		TransactionPopUpDataDto transactionPopUpDto = new TransactionPopUpDataDto();
		transactions.setAccountNumber("593458");
		transactions.setPgTransactionId(null);
		transactions.setDebit(Long.parseLong("12345"));
		pgAccount.setCurrency("4324");
		Mockito.when(refundTransactionDao.getTransactionPopUpDataDto(Matchers.anyString()))
				.thenReturn(transactionPopUpDto);
		Mockito.when(accountRepository.findByAccountNum(Matchers.anyLong())).thenReturn(pgAccount);
		Mockito.when(accountTransactionsDao.getAccountTransactions(Matchers.anyString()))
				.thenReturn(pgAccountTransactions);
		Assert.assertNotNull(transactionServiceImpl.fetchTransactionDetails("Txn"));
	}

	@Test
	public void testFetchTransactionDetailsManualCredit() {
		List<PGAccountTransactions> pgAccountTransactions = new ArrayList<>();
		PGAccountTransactions transactions = new PGAccountTransactions();
		transactions.setTransactionType(AccountTransactionCode.MANUAL_CREDIT);
		pgAccountTransactions.add(transactions);
		PGAccount pgAccount = new PGAccount();
		TransactionPopUpDataDto transactionPopUpDto = new TransactionPopUpDataDto();
		transactions.setAccountNumber("593458");
		transactions.setPgTransactionId(null);
		transactions.setDebit(Long.parseLong("12345"));
		pgAccount.setCurrency("4324");
		Mockito.when(refundTransactionDao.getTransactionPopUpDataDto(Matchers.anyString()))
				.thenReturn(transactionPopUpDto);
		Mockito.when(accountRepository.findByAccountNum(Matchers.anyLong())).thenReturn(pgAccount);
		Mockito.when(accountTransactionsDao.getAccountTransactions(Matchers.anyString()))
				.thenReturn(pgAccountTransactions);
		Assert.assertNotNull(transactionServiceImpl.fetchTransactionDetails("Txn"));
	}

	@Test
	public void testFetchTransactionDetailsAccountDebit() {
		List<PGAccountTransactions> pgAccountTransactions = new ArrayList<>();
		PGAccountTransactions transactions = new PGAccountTransactions();
		transactions.setTransactionType(AccountTransactionCode.ACCOUNT_DEBIT);
		pgAccountTransactions.add(transactions);
		PGAccount pgAccount = new PGAccount();
		TransactionPopUpDataDto transactionPopUpDto = new TransactionPopUpDataDto();
		transactions.setAccountNumber("593458");
		transactions.setPgTransactionId(null);
		transactions.setDebit(Long.parseLong("12345"));
		pgAccount.setCurrency("4324");
		Mockito.when(refundTransactionDao.getTransactionPopUpDataDto(Matchers.anyString()))
				.thenReturn(transactionPopUpDto);
		Mockito.when(accountRepository.findByAccountNum(Matchers.anyLong())).thenReturn(pgAccount);
		Mockito.when(accountTransactionsDao.getAccountTransactions(Matchers.anyString()))
				.thenReturn(pgAccountTransactions);
		Assert.assertNotNull(transactionServiceImpl.fetchTransactionDetails("Txn"));
	}

	@Test
	public void testFetchTransactionDetailsAccountUnknown() {
		List<PGAccountTransactions> pgAccountTransactions = new ArrayList<>();
		PGAccountTransactions transactions = new PGAccountTransactions();
		transactions.setTransactionType(Constants.TXN_UNKNOWN);
		pgAccountTransactions.add(transactions);
		PGAccount pgAccount = new PGAccount();
		TransactionPopUpDataDto transactionPopUpDto = new TransactionPopUpDataDto();
		transactions.setAccountNumber("593458");
		transactions.setPgTransactionId(null);
		transactions.setDebit(Long.parseLong("12345"));
		pgAccount.setCurrency("4324");
		Mockito.when(refundTransactionDao.getTransactionPopUpDataDto(Matchers.anyString()))
				.thenReturn(transactionPopUpDto);
		Mockito.when(accountRepository.findByAccountNum(Matchers.anyLong())).thenReturn(pgAccount);
		Mockito.when(accountTransactionsDao.getAccountTransactions(Matchers.anyString()))
				.thenReturn(pgAccountTransactions);
		Assert.assertNotNull(transactionServiceImpl.fetchTransactionDetails("Txn"));
	}

	@Test
	public void testFetchTransactionDetailsException() {
		List<PGAccountTransactions> pgAccountTransactions = new ArrayList<>();
		PGAccountTransactions transactions = new PGAccountTransactions();
		pgAccountTransactions.add(transactions);
		Mockito.when(accountTransactionsDao.getAccountTransactions(Matchers.anyString()))
				.thenThrow(new NullPointerException());
		Assert.assertNotNull(transactionServiceImpl.fetchTransactionDetails("Txn"));
	}

	@Test
	public void testGetPartialRefundBalance() {
		PGTransaction pgTransaction = new PGTransaction();
		pgTransaction.setTxnTotalAmount(Long.parseLong("35"));
		PGMerchant pgMerchant = new PGMerchant();
		Mockito.when(transactionDao.getTransaction(Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgTransaction);
		Mockito.when(merchantUpdateDao.getMerchantByCode(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(refundTransactionDao.getRefundedAmountOnTxnId(Matchers.anyString()))
				.thenReturn(Long.parseLong("53"));
		Mockito.when(accountTransactionsDao.getSaleAccountTransactionId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn("SaleTxn");
		Assert.assertNotNull(transactionServiceImpl.getPartialRefundBalance("abc", "12343456"));
	}

	@Test
	public void testFindMerchantFeeByMerchantId() {
		Assert.assertNotNull(transactionServiceImpl.findMerchantFeeByMerchantId("mid"));
	}

	@Test
	public void testSearchManualAccountTransactions() {
		List<AccountTransactionDTO> accountTransactionDTOs = new ArrayList<>();
		getTransactionsListRequest = new GetTransactionsListRequest();
		AccountTransactionDTO accountTransactionDTO = new AccountTransactionDTO();
		GetTransactionsListResponse transactionsListResponse = new GetTransactionsListResponse();
		accountTransactionDTOs.add(accountTransactionDTO);
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		transactionsListResponse.setAccountTransactionList(accountTransactionDTOs);
		Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString())).thenReturn(pgCurrencyConfig);
		Mockito.when(accountTransactionsDao.getManulAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
				.thenReturn(transactionsListResponse).thenReturn(transactionsListResponse);
		Assert.assertNotNull(transactionServiceImpl.searchManulAccountTransactions(getTransactionsListRequest));
	}

}
