package com.chatak.acquirer.admin.service;

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

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.exception.ChatakPayException;
import com.chatak.acquirer.admin.service.impl.SettlementServiceImpl;
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
import com.chatak.pg.acq.dao.model.PGCurrencyCode;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGOnlineTxnLog;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.CurrencyCodeRepository;
import com.chatak.pg.acq.dao.repository.TransactionRepository;
import com.chatak.pg.model.SettlementActionDTOList;
import com.chatak.pg.model.SettlemetActionDTO;
import com.chatak.pg.model.TransactionResponse;
import com.chatak.pg.model.VirtualTerminalVoidDTO;

@RunWith(MockitoJUnitRunner.class)
public class SettlementServiceImplTest {

	@InjectMocks
	SettlementServiceImpl settlementServiceImpl = new SettlementServiceImpl();

	@Mock
	TransactionDao transactionDao;

	@Mock
	FeeDetailDao feeDetailDao;

	@Mock
	AccountRepository accountRepository;

	@Mock
	AccountDao accountDao;

	@Mock
	OnlineTxnLogDao onlineTxnLogDao;

	@Mock
	AccountHistoryDao accountHistoryDao;

	@Mock
	MerchantDao merchantDao;

	@Mock
	AccountFeeLogDao accountFeeLogDao;

	@Mock
	AcquirerFeeCodeDao acquirerFeeCodeDao;

	@Mock
	FeeProgramDao feeProgramDao;

	@Mock
	AccountTransactionsDao accountTransactionsDao;

	@Mock
	MessageSource messageSource;

	@Mock
	CurrencyConfigDao currencyConfigDao;

	@Mock
	RestPaymentService paymentService;

	@Mock
	TransactionRepository transactionRepository;

	@Mock
	CurrencyCodeRepository currencyCodeRepository;

	@Mock
	VoidTransactionDao voidTransactionDao;

	@Mock
	MerchantUpdateDao merchantUpdateDao;
	
	@Test
	public void testUpdateSettlementStatus() throws ChatakAdminException {
		PGTransaction pgTransaction = new PGTransaction();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgOnlineTxnLog);
		settlementServiceImpl.updateSettlementStatus("merchantId", "terminalId", "txnId", "txnType", "status",
				"comments", "userName", "timeZoneOffset", "timeZoneRegion");

	}

	@Test
	public void testUpdateSettlementStatusExecuted() throws ChatakAdminException {
		PGTransaction pgTransaction = new PGTransaction();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		PGCurrencyCode pGCurrencyCode = new PGCurrencyCode();
		pgTransaction.setPan("543");
		pgTransaction.setTxnTotalAmount(Long.parseLong("543"));
		pgTransaction.setTxnAmount(Long.parseLong("43"));
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgOnlineTxnLog);
		Mockito.when(currencyCodeRepository.findByCurrencyCodeNumeric(Matchers.anyString())).thenReturn(pGCurrencyCode);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(Locale.class))).thenReturn("ABCD");
		settlementServiceImpl.updateSettlementStatus("merchantId", "terminalId", "txnId", "txnType", "Executed",
				"comments", "userName", "timeZoneOffset", "timeZoneRegion");

	}

	@Test
	public void testUpdateSettlementStatusExecutedProcessing() throws ChatakAdminException {
		PGTransaction pgTransaction = new PGTransaction();
		List<PGAccountFeeLog> pgAccountFeeLogList = new ArrayList<>();
		PGAccountFeeLog accountFeeLog = new PGAccountFeeLog();
		pgAccountFeeLogList.add(accountFeeLog);
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgOnlineTxnLog);
		Mockito.when(accountFeeLogDao.getPGAccountFeeLogOnTransactionId(Matchers.anyString()))
				.thenReturn(pgAccountFeeLogList);
		settlementServiceImpl.updateSettlementStatus("merchantId", "terminalId", "txnId", "txnType", "Processing",
				"comments", "userName", "timeZoneOffset", "timeZoneRegion");

	}

	@Test(expected = ChatakAdminException.class)
	public void testUpdateSettlementStatusExecutedProcessingRejected() throws ChatakAdminException {
		PGTransaction pgTransaction = new PGTransaction();
		List<PGAccountFeeLog> pgAccountFeeLogList = new ArrayList<>();
		PGAccountFeeLog accountFeeLog = new PGAccountFeeLog();
		pgAccountFeeLogList.add(accountFeeLog);
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgOnlineTxnLog);
		Mockito.when(accountFeeLogDao.getPGAccountFeeLogOnTransactionId(Matchers.anyString()))
				.thenReturn(pgAccountFeeLogList);
		settlementServiceImpl.updateSettlementStatus("merchantId", "terminalId", "txnId", "txnType", "Rejected",
				"comments", "userName", "timeZoneOffset", "timeZoneRegion");

	}

	@Test
	public void testUpdateChatakAccount() throws ChatakAdminException {
		PGTransaction transaction = new PGTransaction();
		PGAccount pgAccount = new PGAccount();
		PGCurrencyConfig currencyConfig = new PGCurrencyConfig();
		pgAccount.setFeeBalance(Long.parseLong("6546"));
		Mockito.when(currencyConfigDao.getcurrencyCodeAlpha(Matchers.anyString())).thenReturn(currencyConfig);
		Mockito.when(accountRepository.findByEntityTypeAndCurrencyAndStatus(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString())).thenReturn(pgAccount);
		settlementServiceImpl.updateChatakAccount(Long.parseLong("543"), transaction);
	}

	@Test(expected = ChatakAdminException.class)
	public void testUpdateChatakAccountException() throws ChatakAdminException {
		PGTransaction transaction = new PGTransaction();
		settlementServiceImpl.updateChatakAccount(Long.parseLong("543"), transaction);
	}

	@Test
	public void testLogPgAccountHistory() throws ChatakAdminException {
		PGAccount updatedAccount = new PGAccount();
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(updatedAccount);
		settlementServiceImpl.logPgAccountHistory("123", "4234", "435");
	}

	@Test
	public void testUpdateBulkSettlementStatus() throws ChatakAdminException {
		SettlementActionDTOList settlementActionDTOList = new SettlementActionDTOList();
		List<SettlemetActionDTO> actionDTOs = new ArrayList<>();
		PGTransaction pgTransaction = new PGTransaction();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		SettlemetActionDTO actionDTO = new SettlemetActionDTO();
		actionDTOs.add(actionDTO);
		pgTransaction.setMerchantSettlementStatus("12354");
		settlementActionDTOList.setActionDTOs(actionDTOs);
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgOnlineTxnLog);
		settlementServiceImpl.updateBulkSettlementStatus(settlementActionDTOList, "xyz", "4234", "435");
	}

	@Test
	public void testUpdateBulkSettlementStatusExecuted() throws ChatakAdminException {
		SettlementActionDTOList settlementActionDTOList = new SettlementActionDTOList();
		List<SettlemetActionDTO> actionDTOs = new ArrayList<>();
		PGCurrencyCode pGCurrencyCode = new PGCurrencyCode();
		PGTransaction pgTransaction = new PGTransaction();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		SettlemetActionDTO actionDTO = new SettlemetActionDTO();
		actionDTOs.add(actionDTO);
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccountTransactions accountTransactions = new PGAccountTransactions();
		accountTransactions.setTransactionCode("534");
		accountTransactions.setMerchantCode("534");
		accountTxns.add(accountTransactions);
		pgTransaction.setProcessor("LITLE_EXECUTED");
		pgTransaction.setPan("5345");
		pgTransaction.setTxnTotalAmount(Long.parseLong("543"));
		pgTransaction.setTxnAmount(Long.parseLong("43"));
		pgTransaction.setMerchantSettlementStatus("12354");
		settlementActionDTOList.setActionDTOs(actionDTOs);
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgOnlineTxnLog);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(Locale.class))).thenReturn("ABCD");
		Mockito.when(currencyCodeRepository.findByCurrencyCodeNumeric(Matchers.anyString())).thenReturn(pGCurrencyCode);

		settlementServiceImpl.updateBulkSettlementStatus(settlementActionDTOList, "Executed", "a", "b");
	}

	@Test
	public void testUpdateBulkSettlementStatusProcessing() throws ChatakAdminException {
		SettlementActionDTOList settlementActionDTOList = new SettlementActionDTOList();
		List<SettlemetActionDTO> actionDTOs = new ArrayList<>();
		PGTransaction pgTransaction = new PGTransaction();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		SettlemetActionDTO actionDTO = new SettlemetActionDTO();
		actionDTOs.add(actionDTO);
		List<PGAccountFeeLog> pgAccountFeeLogList = new ArrayList<>();
		PGAccountFeeLog accountFeeLog = new PGAccountFeeLog();
		pgAccountFeeLogList.add(accountFeeLog);
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccountTransactions accountTransactions = new PGAccountTransactions();
		accountTransactions.setTransactionCode("534");
		accountTransactions.setMerchantCode("534");
		accountTxns.add(accountTransactions);
		pgTransaction.setProcessor("LITLE_EXECUTED");
		pgTransaction.setPan("5345");
		pgTransaction.setTxnTotalAmount(Long.parseLong("543"));
		pgTransaction.setTxnAmount(Long.parseLong("43"));
		pgTransaction.setMerchantSettlementStatus("12354");
		settlementActionDTOList.setActionDTOs(actionDTOs);
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgOnlineTxnLog);
		Mockito.when(accountFeeLogDao.getPGAccountFeeLogOnTransactionId(Matchers.anyString()))
				.thenReturn(pgAccountFeeLogList);
		settlementServiceImpl.updateBulkSettlementStatus(settlementActionDTOList, "Processing", "a", "b");
	}

	@Test(expected = NullPointerException.class)
	public void testUpdateBulkSettlementStatusRejected() throws ChatakPayException {
		SettlementActionDTOList settlementActionDTOList = new SettlementActionDTOList();
		List<SettlemetActionDTO> actionDTOs = new ArrayList<>();
		com.chatak.pg.model.TransactionResponse response2 = new TransactionResponse();
		response2.setErrorCode("00");
		List<PGAccountFeeLog> pgAccountFeeLogList = new ArrayList<>();
		PGAccountFeeLog accountFeeLog = new PGAccountFeeLog();
		pgAccountFeeLogList.add(accountFeeLog);
		PGTransaction pgTransaction = new PGTransaction();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		SettlemetActionDTO actionDTO = new SettlemetActionDTO();
		actionDTOs.add(actionDTO);
		pgTransaction.setTxnTotalAmount(Long.parseLong("543"));
		pgTransaction.setTxnAmount(Long.parseLong("43"));
		pgTransaction.setMerchantSettlementStatus("12354");
		settlementActionDTOList.setActionDTOs(actionDTOs);
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgOnlineTxnLog);
		Mockito.when(paymentService.doVoid(Matchers.any(VirtualTerminalVoidDTO.class))).thenReturn(response2);
		Mockito.when(accountFeeLogDao.getPGAccountFeeLogOnTransactionId(Matchers.anyString()))
				.thenReturn(pgAccountFeeLogList);
		settlementServiceImpl.updateBulkSettlementStatus(settlementActionDTOList, "Rejected", "a", "b");
	}

	@Test
	public void testGetProcessingFee() throws ChatakAdminException {
		List<PGAcquirerFeeValue> acquirerFeeValueList = new ArrayList<>();
		PGAcquirerFeeValue acquirerFeeValue = new PGAcquirerFeeValue();
		acquirerFeeValueList.add(acquirerFeeValue);
		Mockito.when(
				feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(acquirerFeeValueList);
		settlementServiceImpl.getProcessingFee("123", Long.parseLong("534"), "435", Long.parseLong("534"));
	}

	@Test
	public void testGetProcessingFeeElse() throws ChatakAdminException {
		Mockito.when(merchantDao.getParentMerchantCode(Matchers.anyString())).thenReturn("5435");
		settlementServiceImpl.getProcessingFee("123", Long.parseLong("534"), "435", Long.parseLong("534"));
	}

	@Test
	public void testUpdateAccountCCTransactions() {
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccountTransactions accountTransactions = new PGAccountTransactions();
		accountTxns.add(accountTransactions);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		settlementServiceImpl.updateAccountCCTransactions("5434", "6546", "Rejected");
	}

	@Test
	public void testUpdateAccountCCTransactionsCcAmountCredit() {
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccountTransactions accountTransactions = new PGAccountTransactions();
		PGAccount account = new PGAccount();
		accountTransactions.setTransactionCode("CC_AMOUNT_CREDIT");
		accountTransactions.setMerchantCode("354");
		account.setAvailableBalance(Long.parseLong("435"));
		account.setCurrentBalance(Long.parseLong("435"));
		accountTransactions.setCredit(Long.parseLong("6546"));
		accountTxns.add(accountTransactions);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(account);
		settlementServiceImpl.updateAccountCCTransactions("5434", "6546", "CC_AMOUNT_CREDIT");
	}

	@Test
	public void testUpdateAccountCCTransactionsCcAmountCreditCcFeeDebit() {
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccountTransactions accountTransactions = new PGAccountTransactions();
		PGAccount account = new PGAccount();
		accountTransactions.setTransactionCode("CC_FEE_DEBIT");
		accountTransactions.setMerchantCode("354");
		account.setAvailableBalance(Long.parseLong("435"));
		account.setCurrentBalance(Long.parseLong("435"));
		accountTransactions.setDebit(Long.parseLong("6546"));
		accountTxns.add(accountTransactions);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(account);
		settlementServiceImpl.updateAccountCCTransactions("5434", "6546", "CC_FEE_DEBIT");
	}

	@Test
	public void testUpdateAccountCCTransactionsCcMerchantFeeCredit() {
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccountTransactions accountTransactions = new PGAccountTransactions();
		PGAccount account = new PGAccount();
		accountTransactions.setTransactionCode("CC_MERCHANT_FEE_CREDIT");
		account.setAvailableBalance(Long.parseLong("435"));
		account.setCurrentBalance(Long.parseLong("435"));
		accountTransactions.setCredit(Long.parseLong("6546"));
		accountTxns.add(accountTransactions);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(account);
		settlementServiceImpl.updateAccountCCTransactions("5434", "6546", "CC_MERCHANT_FEE_CREDIT");
	}

	@Test
	public void testUpdateAccountCCTransactionsCcAcquirerFeeCredit() {
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		List<PGTransaction> transactions = new ArrayList<>();
		PGTransaction value = new PGTransaction();
		PGCurrencyConfig currencyConfig = new PGCurrencyConfig();
		PGAccountTransactions accountTransactions = new PGAccountTransactions();
		PGAccount account = new PGAccount();
		accountTransactions.setTransactionCode("CC_ACQUIRER_FEE_CREDIT");
		account.setAvailableBalance(Long.parseLong("435"));
		account.setCurrentBalance(Long.parseLong("435"));
		accountTransactions.setCredit(Long.parseLong("6546"));
		accountTxns.add(accountTransactions);
		transactions.add(value);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(account);
		Mockito.when(transactionRepository.findByTransactionId(Matchers.anyString())).thenReturn(value);  
		Mockito.when(currencyConfigDao.getcurrencyCodeAlpha(Matchers.anyString())).thenReturn(currencyConfig);
		Mockito.when(accountRepository.findByEntityTypeAndCurrencyAndStatus(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString())).thenReturn(account);
		settlementServiceImpl.updateAccountCCTransactions("5434", "6546", "CC_ACQUIRER_FEE_CREDIT");
	}

	@Test
	public void testUpdateAccountCCTransactionsRejected() {
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccountTransactions accountTransactions = new PGAccountTransactions();
		accountTxns.add(accountTransactions);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		settlementServiceImpl.updateAccountCCTransactions("5434", "6546", "Rejected");
	}

}
