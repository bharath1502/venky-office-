package com.chatak.merchant.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.TransactionResponse;
import com.chatak.merchant.service.RestPaymentService;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountFeeLogDao;
import com.chatak.pg.acq.dao.AccountHistoryDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.AcquirerFeeCodeDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.FeeDetailDao;
import com.chatak.pg.acq.dao.FeeProgramDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.OnlineTxnLogDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountFeeLog;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGOnlineTxnLog;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.SettlementActionDTOList;
import com.chatak.pg.model.SettlemetActionDTO;
import com.sun.jersey.api.client.ClientResponse;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class SettlementServiceImplTest {

	@InjectMocks
	private SettlementServiceImpl settlementServiceImpl = new SettlementServiceImpl();

	@Mock
	TransactionDao transactionDao;

	@Mock
	AccountDao accountDao;

	@Mock
	FeeDetailDao feeDetailDao;

	@Mock
	AccountRepository accountRepository;

	@Mock
	OnlineTxnLogDao onlineTxnLogDao;

	@Mock
	AccountHistoryDao accountHistoryDao;

	@Mock
	AcquirerFeeCodeDao acquirerFeeCodeDao;

	@Mock
	MerchantDao merchantDao;

	@Mock
	AccountFeeLogDao accountFeeLogDao;

	@Mock
	FeeProgramDao feeProgramDao;

	@Mock
	AccountTransactionsDao accountTransactionsDao;

	@Mock
	RestPaymentService paymentService;

	@Mock
	MessageSource messageSource;

	@Mock
	CurrencyConfigDao currencyConfigDao;

	@Mock
	VoidTransactionDao voidTransactionDao;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Mock
	PGMerchant pgMerchant;

	@Test
	public void testUpdateSettlementStatusExecuted() throws ChatakMerchantException {
		PGTransaction pgTransaction = new PGTransaction();
		pgTransaction.setTxnTotalAmount(Long.parseLong("123"));
		pgTransaction.setTxnAmount(Long.parseLong("21"));
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		pgOnlineTxnLog.setAppMode("123");
		pgOnlineTxnLog.setOrderId("1234");
		List<PGAccountFeeLog> pgAccountFeeLogList = new ArrayList<>();
		PGAccountFeeLog pGAccountFeeLog = new PGAccountFeeLog();
		pgAccountFeeLogList.add(pGAccountFeeLog);
		pgTransaction.setPan("123");
		Mockito.when(onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgOnlineTxnLog);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(Locale.class)))
				.thenReturn("chatak");
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Assert.assertNotNull(settlementServiceImpl.updateSettlementStatus("a", "b", "c", "d",
				PGConstants.PG_SETTLEMENT_EXECUTED, "e"));

	}

	@Test
	public void testUpdateSettlementStatusProcessing() throws ChatakMerchantException {
		PGTransaction pgTransaction = new PGTransaction();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		List<PGAccountFeeLog> pgAccountFeeLogList = new ArrayList<>();
		PGAccountFeeLog pGAccountFeeLog = new PGAccountFeeLog();
		pgAccountFeeLogList.add(pGAccountFeeLog);
		Mockito.when(accountFeeLogDao.getPGAccountFeeLogOnTransactionId(Matchers.anyString()))
				.thenReturn(pgAccountFeeLogList);
		Mockito.when(onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgOnlineTxnLog);
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Assert.assertNotNull(settlementServiceImpl.updateSettlementStatus("a", "b", "c", "d",
				PGConstants.PG_SETTLEMENT_PROCESSING, "e"));

	}

	@Test
	public void testUpdateSettlementStatusRejected() throws ChatakMerchantException {
		PGTransaction pgTransaction = new PGTransaction();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		List<PGAccountFeeLog> pgAccountFeeLogList = new ArrayList<>();
		PGAccountFeeLog PGAccountFeeLog = new PGAccountFeeLog();
		pgAccountFeeLogList.add(PGAccountFeeLog);
		TransactionResponse response2 = new TransactionResponse();
		response2.setErrorCode("00");
		pgTransaction.setTransactionId("12345");
		Mockito.when(accountFeeLogDao.getPGAccountFeeLogOnTransactionId(Matchers.anyString()))
				.thenReturn(pgAccountFeeLogList);
		Mockito.when(onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgOnlineTxnLog);
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Assert.assertNotNull(settlementServiceImpl.updateSettlementStatus("a", "b", "c", "d",
				PGConstants.PG_SETTLEMENT_REJECTED, "e"));

	}

	@Test
	public void testUpdateSettlementStatusElse() throws ChatakMerchantException {
		PGTransaction pgTransaction = null;
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Assert.assertNotNull(settlementServiceImpl.updateSettlementStatus("a", "b", "c", "d",
				PGConstants.PG_SETTLEMENT_EXECUTED, "e"));

	}

	@Test(expected = ChatakMerchantException.class)
	public void testUpdateChatakAccount() throws ChatakMerchantException {
		PGTransaction transaction = new PGTransaction();
		settlementServiceImpl.updateChatakAccount(Long.parseLong("11"), transaction);

	}

	@Test
	public void testLogPgAccountHistory() throws ChatakMerchantException {
		PGAccount updatedAccount = new PGAccount();
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(updatedAccount);
		settlementServiceImpl.logPgAccountHistory("a", "b", "c");

	}

	@Test
	public void testUpdateBulkSettlementStatus() {
		List<SettlemetActionDTO> actionDTOs = new ArrayList<>();
		SettlementActionDTOList settlementActionDTOList = new SettlementActionDTOList();
		SettlemetActionDTO actionDTO = new SettlemetActionDTO();
		settlementActionDTOList.setActionDTOs(actionDTOs);
		actionDTOs.add(actionDTO);
		PGTransaction pgTransaction = new PGTransaction();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		pgTransaction.setMerchantSettlementStatus("a");
		List<PGAccountFeeLog> pgAccountFeeLogList = new ArrayList<>();
		PGAccountFeeLog accountFeeLog = new PGAccountFeeLog();
		accountFeeLog.setAccountNum(Long.parseLong("1234"));
		pgAccountFeeLogList.add(accountFeeLog);
		Mockito.when(accountFeeLogDao.getPGAccountFeeLogOnTransactionId(Matchers.anyString()))
				.thenReturn(pgAccountFeeLogList);
		Mockito.when(onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgOnlineTxnLog);
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Assert.assertNotNull(settlementServiceImpl.updateBulkSettlementStatus(settlementActionDTOList,
				PGConstants.PG_SETTLEMENT_PROCESSING, "hello"));
	}

	@Test
	public void testUpdateBulkSettlementStatusRejected() {
		List<SettlemetActionDTO> actionDTOs = new ArrayList<>();
		SettlementActionDTOList settlementActionDTOList = new SettlementActionDTOList();
		SettlemetActionDTO actionDTO = new SettlemetActionDTO();
		settlementActionDTOList.setActionDTOs(actionDTOs);
		actionDTOs.add(actionDTO);
		PGTransaction pgTransaction = new PGTransaction();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		pgTransaction.setMerchantSettlementStatus("a");
		Mockito.when(onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgOnlineTxnLog);
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Assert.assertNotNull(settlementServiceImpl.updateBulkSettlementStatus(settlementActionDTOList,
				PGConstants.PG_SETTLEMENT_REJECTED, "hello"));
	}

	@Test
	public void testUpdateBulkSettlementStatusExecuted() {
		List<SettlemetActionDTO> actionDTOs = new ArrayList<>();
		SettlementActionDTOList settlementActionDTOList = new SettlementActionDTOList();
		SettlemetActionDTO actionDTO = new SettlemetActionDTO();
		settlementActionDTOList.setActionDTOs(actionDTOs);
		actionDTOs.add(actionDTO);
		PGTransaction pgTransaction = new PGTransaction();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		pgTransaction.setPan("12343456");
		pgTransaction.setTxnTotalAmount(Long.parseLong("123"));
		pgTransaction.setTxnAmount(Long.parseLong("12"));
		pgTransaction.setMerchantSettlementStatus("a");
		List<PGAccountFeeLog> pgAccountFeeLogList = new ArrayList<>();
		PGAccountFeeLog accountFeeLog = new PGAccountFeeLog();
		accountFeeLog.setAccountNum(Long.parseLong("2345"));
		pgAccountFeeLogList.add(accountFeeLog);
		Mockito.when(accountFeeLogDao.getPGAccountFeeLogOnTransactionId(Matchers.anyString()))
				.thenReturn(pgAccountFeeLogList);
		Mockito.when(onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgOnlineTxnLog);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(Locale.class)))
				.thenReturn("chatak");
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Assert.assertNotNull(settlementServiceImpl.updateBulkSettlementStatus(settlementActionDTOList,
				PGConstants.PG_SETTLEMENT_EXECUTED, "hello"));
	}

	@Test
	public void testGetProcessingFee() {
		List<PGAcquirerFeeValue> acquirerFeeValueList = new ArrayList<>();
		PGAcquirerFeeValue pGAcquirerFeeValue = new PGAcquirerFeeValue();
		acquirerFeeValueList.add(pGAcquirerFeeValue);
		Mockito.when(
				feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(acquirerFeeValueList);
		Assert.assertNotNull(
				settlementServiceImpl.getProcessingFee("a", Long.parseLong("123"), "c", Long.parseLong("456")));

	}

	@Test
	public void testGetProcessingFeeElse() {
		List<PGAcquirerFeeValue> acquirerFeeValueList = new ArrayList<>();
		Mockito.when(
				feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(acquirerFeeValueList);
		Mockito.when(merchantDao.getParentMerchantCode(Matchers.anyString())).thenReturn("abcde");
		Mockito.when(
				feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(acquirerFeeValueList);
		Assert.assertNotNull(
				settlementServiceImpl.getProcessingFee("a", Long.parseLong("23"), "c", Long.parseLong("523")));

	}

	@Test
	public void testPostVirtualAccFee() throws ChatakMerchantException, IOException {
		PGAccountFeeLog pgAccountFeeLog = new PGAccountFeeLog();
		pgAccountFeeLog.setTxnAmount(Long.parseLong("567"));
		pgAccountFeeLog.setChatakFee(Long.parseLong("356"));
		pgAccountFeeLog.setMerchantFee(Long.parseLong("342"));
		Assert.assertNotNull(settlementServiceImpl.postVirtualAccFee(pgAccountFeeLog, "a", "b", "c", "d"));

	}

	@Test
	public void testPostVirtualAccFeeReversal() throws IOException, ChatakMerchantException {
		PGAccountFeeLog pgAccountFeeLog = new PGAccountFeeLog();
		Assert.assertNotNull(settlementServiceImpl.postVirtualAccFeeReversal(pgAccountFeeLog, "a", "b", "c"));

	}

	@Test
	public void testPostVirtualAccFeeReversalElse() throws IOException, ChatakMerchantException {
		PGAccountFeeLog pgAccountFeeLog = new PGAccountFeeLog();
		ClientResponse response = new ClientResponse(null, null, null, null);
		response.setStatus(Integer.parseInt("200"));
		Assert.assertNotNull(settlementServiceImpl.postVirtualAccFeeReversal(pgAccountFeeLog, "a", "b", "c"));

	}

	@Test
	public void testUpdateAccountCCTransactionsRejected() {
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccountTransactions pGAccountTransactions = new PGAccountTransactions();
		accountTxns.add(pGAccountTransactions);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		settlementServiceImpl.updateAccountCCTransactions("a", "b", PGConstants.PG_SETTLEMENT_REJECTED);

	}

	@Test
	public void testUpdateAccountCCTransactionsCCAMOUNTCREDIT() {
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccountTransactions pGAccountTransactions = new PGAccountTransactions();
		pGAccountTransactions.setTransactionCode("CC_AMOUNT_CREDIT");
		pGAccountTransactions.setStatus("Executed");
		accountTxns.add(pGAccountTransactions);
		PGAccount account = new PGAccount();
		account.setAvailableBalance(Long.parseLong("543"));
		pGAccountTransactions.setCredit(Long.parseLong("79"));
		account.setCurrentBalance(Long.parseLong("2045"));
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(account);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		settlementServiceImpl.updateAccountCCTransactions("a", "b", "c");

	}

	@Test
	public void testUpdateAccountCCTransactionsCCAmountDebit() {
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccountTransactions pGAccountTransactions = new PGAccountTransactions();
		pGAccountTransactions.setTransactionCode("CC_FEE_DEBIT");
		pGAccountTransactions.setStatus("Executed");
		accountTxns.add(pGAccountTransactions);
		PGAccount account = new PGAccount();
		account.setAvailableBalance(Long.parseLong("54"));
		pGAccountTransactions.setDebit(Long.parseLong("876"));
		account.setCurrentBalance(Long.parseLong("53"));
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(account);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		settlementServiceImpl.updateAccountCCTransactions("a", "b", "c");

	}

	@Test
	public void testUpdateAccountCCTransactionsCCMerchantFeeCredit() {
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccountTransactions pGAccountTransactions = new PGAccountTransactions();
		pGAccountTransactions.setTransactionCode("CC_MERCHANT_FEE_CREDIT");
		pGAccountTransactions.setStatus("Executed");
		accountTxns.add(pGAccountTransactions);
		PGAccount account = new PGAccount();
		account.setAvailableBalance(Long.parseLong("876"));
		pGAccountTransactions.setCredit(Long.parseLong("287"));
		account.setCurrentBalance(Long.parseLong("2000"));
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(account);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		settlementServiceImpl.updateAccountCCTransactions("a", "b", "c");

	}

	@Test(expected = NullPointerException.class)
	public void testUpdateAccountCCTransactionsCCMerchantFeeCreditNull() {
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccountTransactions pGAccountTransactions = new PGAccountTransactions();
		pGAccountTransactions.setTransactionCode("CC_MERCHANT_FEE_CREDIT");
		pGAccountTransactions.setStatus("Executed");
		accountTxns.add(pGAccountTransactions);
		pGAccountTransactions.setCredit(Long.parseLong("245"));
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		settlementServiceImpl.updateAccountCCTransactions("a", "b", "c");

	}

	@Test
	public void testUpdateAccountCCTransactionsCCAcquirerFeeCredit() {   //ReBrand
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccountTransactions pGAccountTransactions = new PGAccountTransactions();
		pGAccountTransactions.setTransactionCode("CC_ACQUIRER_FEE_CREDIT");  //ReBrand
		pGAccountTransactions.setStatus("Executed");
		accountTxns.add(pGAccountTransactions);
		PGAccount account = new PGAccount();
		PGCurrencyConfig currencyConfig = new PGCurrencyConfig();
		pGAccountTransactions.setTxnCurrencyCode("108");
		account.setAvailableBalance(Long.parseLong("2045"));
		pGAccountTransactions.setCredit(Long.parseLong("245"));
		account.setCurrentBalance(Long.parseLong("2345"));
		Mockito.when(currencyConfigDao.getcurrencyCodeAlpha(Matchers.anyString())).thenReturn(currencyConfig);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(account);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		Mockito.when(accountRepository.findByEntityTypeAndCurrencyAndStatus(PGConstants.DEFAULT_ENTITY_TYPE,
				currencyConfig.getCurrencyCodeAlpha(), PGConstants.S_STATUS_ACTIVE)).thenReturn(account);
		settlementServiceImpl.updateAccountCCTransactions("a", "b", "c");

	}

}
