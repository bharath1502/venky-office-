package com.chatak.pay.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

import com.chatak.pay.controller.model.CardData;
import com.chatak.pay.controller.model.Response;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pay.exception.SplitTransactionException;
import com.chatak.pay.model.TransactionDTO;
import com.chatak.pay.model.TransactionDTOResponse;
import com.chatak.pay.service.PGSplitTransactionService;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountFeeLogDao;
import com.chatak.pg.acq.dao.AccountHistoryDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.FeeProgramDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.OnlineTxnLogDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.SplitTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGOnlineTxnLog;
import com.chatak.pg.acq.dao.model.PGSplitTransaction;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.CurrencyCodeRepository;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.pg.acq.dao.repository.TransactionRepository;
import com.chatak.pg.bean.BillingData;
import com.chatak.pg.bean.CardTokenData;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.TransactionType;
import com.chatak.switches.sb.exception.ServiceException;
import com.litle.sdk.generate.MethodOfPaymentTypeEnum;

@RunWith(MockitoJUnitRunner.class)
public class PGTransactionServiceImplTest {

	private static Logger logger = Logger.getLogger(PGTransactionServiceImpl.class);

	@InjectMocks
	PGTransactionServiceImpl pgTransactionServiceImpl = new PGTransactionServiceImpl();

	@Mock
	private MessageSource messageSource;

	@Mock
	private ApplicationContext appContext;

	@Mock
	private OnlineTxnLogDao onlineTxnLogDao;

	@Mock
	private TransactionDao transactionDao;

	@Mock
	private SplitTransactionDao splitTransactionDao;

	@Mock
	private PGSplitTransactionService pgSplitTransactionService;

	@Mock
	protected AccountDao accountDao;

	@Mock
	protected AccountHistoryDao accountHistoryDao;

	@Mock
	FeeProgramDao feeProgramDao;

	@Mock
	MerchantDao merchantDao;

	@Mock
	AccountFeeLogDao accountFeeLogDao;

	@Mock
	CurrencyCodeRepository currencyCodeRepository;

	@Mock
	AccountTransactionsDao accountTransactionsDao;

	@Mock
	CurrencyConfigDao currencyConfigDao;

	@Mock
	TransactionRepository transactionRepository;

	@Mock
	AccountRepository accountRepository;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Mock
	VoidTransactionDao voidTransactionDao;

	@Mock
	RefundTransactionDao refundTransactionDao;

	@Mock
	CurrencyConfigRepository currencyConfigRepository;
	
	@Mock
	PGMerchant merchant;

	@Test
	public void testProcessTransactionAuth() {
		TransactionRequest transactionRequest = new TransactionRequest();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		BillingData billingData = new BillingData();
		CardData cardData = new CardData();
		CardTokenData cardTokenData = new CardTokenData();
		billingData.setCountry("bbb");
		transactionRequest.setTransactionType(TransactionType.AUTH);
		transactionRequest.setBillingData(billingData);
		transactionRequest.setInvoiceNumber("32");
		cardData.setCardNumber("54");
		transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
		transactionRequest.setMode("43");
		transactionRequest.setCardTokenData(cardTokenData);
		cardData.setCardType(MethodOfPaymentTypeEnum.VI);
		transactionRequest.setCardData(cardData);
		Mockito.when(onlineTxnLogDao.logRequest(Matchers.any((PGOnlineTxnLog.class)))).thenReturn(pgOnlineTxnLog);
		pgTransactionServiceImpl.processTransaction(transactionRequest,merchant);
	}

	@Test
	public void testProcessTransactionCapture() {
		TransactionRequest transactionRequest = new TransactionRequest();
		PGTransaction pgTransaction = new PGTransaction();
		BillingData billingData = new BillingData();
		CardData cardData = new CardData();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		CardTokenData cardTokenData = new CardTokenData();
		transactionRequest.setTransactionType(TransactionType.CAPTURE);
		pgTransaction.setPan("1");
		billingData.setCountry("bbb");
		pgTransaction.setExpDate(Long.parseLong("567"));
		cardData.setCardNumber("545435435435435534");
		transactionRequest.setInvoiceNumber("1");
		transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
		transactionRequest.setMode("43");
		transactionRequest.setCardTokenData(cardTokenData);
		transactionRequest.setBillingData(billingData);
		transactionRequest.setCardData(cardData);
		Mockito.when(voidTransactionDao.findTransactionToCaptureByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgTransaction);
		Mockito.when(onlineTxnLogDao.logRequest(Matchers.any((PGOnlineTxnLog.class)))).thenReturn(pgOnlineTxnLog);
		pgTransactionServiceImpl.processTransaction(transactionRequest,merchant);
	}

	@Test
	public void testProcessTransactionElse() {
		TransactionRequest transactionRequest = new TransactionRequest();
		pgTransactionServiceImpl.processTransaction(transactionRequest,merchant);
	}

	@Test
	public void testProcessTransactionSale() {
		TransactionRequest transactionRequest = new TransactionRequest();
		BillingData billingData = new BillingData();
		PGMerchant pgMerchant = new PGMerchant();
		List<PGAcquirerFeeValue> feeValues = new ArrayList<>();
		PGAcquirerFeeValue acquirerFeeValue = new PGAcquirerFeeValue();
		CardData cardData = new CardData();
		CardTokenData cardTokenData = new CardTokenData();
		billingData.setCountry("bbb");
		transactionRequest.setTransactionType(TransactionType.SALE);
		transactionRequest.setBillingData(billingData);
		transactionRequest.setInvoiceNumber("32");
		cardData.setCardNumber("54");
		transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
		transactionRequest.setMode("43");
		transactionRequest.setCardTokenData(cardTokenData);
		cardData.setCardType(MethodOfPaymentTypeEnum.VI);
		transactionRequest.setCardData(cardData);
		pgMerchant.setBusinessName("4");
		pgMerchant.setCity("5");
		transactionRequest.setTotalTxnAmount(Long.parseLong("54354"));
		feeValues.add(acquirerFeeValue);
		Mockito.when(merchantUpdateDao.getMerchantByCode(Matchers.anyString())).thenReturn(pgMerchant);
		pgTransactionServiceImpl.processTransaction(transactionRequest,merchant);
	}

	@Test
	public void testProcessTransactionRefundStatus() {
		TransactionRequest transactionRequest = new TransactionRequest();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		PGTransaction pgTransaction = new PGTransaction();
		BillingData billingData = new BillingData();
		CardData cardData = new CardData();
		CardTokenData cardTokenData = new CardTokenData();
		billingData.setCountry("bbb");
		transactionRequest.setTransactionType(TransactionType.REFUND);
		transactionRequest.setBillingData(billingData);
		transactionRequest.setInvoiceNumber("32");
		cardData.setCardNumber("54");
		transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
		transactionRequest.setMode("43");
		transactionRequest.setCardTokenData(cardTokenData);
		cardData.setCardType(MethodOfPaymentTypeEnum.VI);
		pgTransaction.setRefundStatus(1);
		transactionRequest.setCardData(cardData);
		Mockito.when(onlineTxnLogDao.logRequest(Matchers.any((PGOnlineTxnLog.class)))).thenReturn(pgOnlineTxnLog);
		Mockito.when(refundTransactionDao.findTransactionToRefundByPGTxnIdAndIssuerTxnIdAndMerchantId(
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		pgTransactionServiceImpl.processTransaction(transactionRequest,merchant);
	}

	@Test
	public void testProcessTransactionRefund() {
		TransactionRequest transactionRequest = new TransactionRequest();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		PGTransaction pgTransaction = new PGTransaction();
		BillingData billingData = new BillingData();
		CardData cardData = new CardData();
		CardTokenData cardTokenData = new CardTokenData();
		billingData.setCountry("bbb");
		transactionRequest.setTransactionType(TransactionType.REFUND);
		transactionRequest.setBillingData(billingData);
		transactionRequest.setInvoiceNumber("32");
		cardData.setCardNumber("54");
		transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
		transactionRequest.setMode("43");
		transactionRequest.setCardTokenData(cardTokenData);
		cardData.setCardType(MethodOfPaymentTypeEnum.VI);
		pgTransaction.setMerchantSettlementStatus("Processing");
		transactionRequest.setCardData(cardData);
		pgTransaction.setTransactionId("4324");
		transactionRequest.setTotalTxnAmount(Long.parseLong("10"));
		pgTransaction.setTxnTotalAmount(Long.parseLong("1534"));
		Mockito.when(onlineTxnLogDao.logRequest(Matchers.any((PGOnlineTxnLog.class)))).thenReturn(pgOnlineTxnLog);
		Mockito.when(refundTransactionDao.findTransactionToRefundByPGTxnIdAndIssuerTxnIdAndMerchantId(
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(refundTransactionDao.getRefundedAmountOnTxnId(Matchers.anyString()))
				.thenReturn(Long.parseLong("543"));
		pgTransactionServiceImpl.processTransaction(transactionRequest,merchant);
	}

	@Test
	public void testProcessTransactionVoid() {
		TransactionRequest transactionRequest = new TransactionRequest();
		PGTransaction pgTransaction = new PGTransaction();
		transactionRequest.setTransactionType(TransactionType.VOID);
		Mockito.when(refundTransactionDao.findTransactionToRefundByPGTxnIdAndIssuerTxnIdAndMerchantId(
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		pgTransactionServiceImpl.processTransaction(transactionRequest,merchant);
	}

	@Test
	public void testProcessTransactionSplitAccept() throws SplitTransactionException {
		TransactionRequest transactionRequest = new TransactionRequest();
		BillingData billingData = new BillingData();
		CardData cardData = new CardData();
		CardTokenData cardTokenData = new CardTokenData();
		billingData.setCountry("bbb");
		transactionRequest.setTransactionType(TransactionType.SPLIT_ACCEPT);
		transactionRequest.setBillingData(billingData);
		transactionRequest.setInvoiceNumber("32");
		cardData.setCardNumber("54");
		transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
		transactionRequest.setMode("43");
		transactionRequest.setCardTokenData(cardTokenData);
		cardData.setCardType(MethodOfPaymentTypeEnum.VI);
		transactionRequest.setCardData(cardData);
		pgTransactionServiceImpl.processTransaction(transactionRequest,merchant);
	}

	@Test
	public void testProcessTransactionReversal() {
		TransactionRequest transactionRequest = new TransactionRequest();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		BillingData billingData = new BillingData();
		PGTransaction pgTransaction = new PGTransaction();
		CardData cardData = new CardData();
		CardTokenData cardTokenData = new CardTokenData();
		billingData.setCountry("bbb");
		transactionRequest.setTransactionType(TransactionType.REVERSAL);
		transactionRequest.setBillingData(billingData);
		transactionRequest.setInvoiceNumber("32");
		cardData.setCardNumber("54");
		transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
		transactionRequest.setMode("43");
		transactionRequest.setCardTokenData(cardTokenData);
		cardData.setCardType(MethodOfPaymentTypeEnum.VI);
		pgTransaction.setPosEntryMode("abc");
		transactionRequest.setCardData(cardData);
		Mockito.when(onlineTxnLogDao.logRequest(Matchers.any((PGOnlineTxnLog.class)))).thenReturn(pgOnlineTxnLog);
		pgTransactionServiceImpl.processTransaction(transactionRequest,merchant);
	}

	@Test
	public void testProcessTransactionRefundVoid() {
		TransactionRequest transactionRequest = new TransactionRequest();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		PGTransaction pgTransaction = new PGTransaction();
		BillingData billingData = new BillingData();
		CardData cardData = new CardData();
		CardTokenData cardTokenData = new CardTokenData();
		billingData.setCountry("bbb");
		transactionRequest.setTransactionType(TransactionType.REFUND_VOID);
		transactionRequest.setBillingData(billingData);
		transactionRequest.setInvoiceNumber("32");
		cardData.setCardNumber("54");
		pgTransaction.setPan("1");
		pgTransaction.setExpDate(Long.parseLong("456"));
		transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
		transactionRequest.setMode("43");
		transactionRequest.setCardTokenData(cardTokenData);
		cardData.setCardType(MethodOfPaymentTypeEnum.VI);
		transactionRequest.setCardData(cardData);
		Mockito.when(onlineTxnLogDao.logRequest(Matchers.any((PGOnlineTxnLog.class)))).thenReturn(pgOnlineTxnLog);
		Mockito.when(refundTransactionDao.findRefundTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgTransaction);
		pgTransactionServiceImpl.processTransaction(transactionRequest,merchant);
	}

	@Test
	public void testProcessTransactionBalance() {
		TransactionRequest transactionRequest = new TransactionRequest();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		PGMerchant merchantData = new PGMerchant();
		PGCurrencyConfig currencyDetails = new PGCurrencyConfig();
		BillingData billingData = new BillingData();
		CardData cardData = new CardData();
		CardTokenData cardTokenData = new CardTokenData();
		billingData.setCountry("bbb");
		transactionRequest.setTransactionType(TransactionType.BALANCE);
		transactionRequest.setBillingData(billingData);
		transactionRequest.setInvoiceNumber("32");
		cardData.setCardNumber("54");
		transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
		transactionRequest.setMode("43");
		transactionRequest.setCardTokenData(cardTokenData);
		cardData.setCardType(MethodOfPaymentTypeEnum.VI);
		merchantData.setBusinessName("6");
		merchantData.setCity("8");
		transactionRequest.setCardData(cardData);
		Mockito.when(onlineTxnLogDao.logRequest(Matchers.any((PGOnlineTxnLog.class)))).thenReturn(pgOnlineTxnLog);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(merchantData);
		Mockito.when(currencyConfigRepository.findByCurrencyCodeAlpha(Matchers.anyString()))
				.thenReturn(currencyDetails);
		pgTransactionServiceImpl.processTransaction(transactionRequest,merchant);
	}

	@Test
	public void testProcessTransactionMerchant() {
		TransactionRequest transactionRequest = new TransactionRequest();
		merchant = new PGMerchant();
		transactionRequest.setTransactionType(TransactionType.AUTH);
		pgTransactionServiceImpl.processTransaction(transactionRequest, merchant);
	}

	@Test
	public void testProcessTransactionMerchantNotNull() {
		TransactionRequest transactionRequest = new TransactionRequest();
		List<TransactionDTO> dtos = new ArrayList<>();
		TransactionDTO transactionDTO = new TransactionDTO();
		TransactionDTOResponse transactionResponse = new TransactionDTOResponse();
		merchant = new PGMerchant();
		CardData cardData = new CardData();
		transactionRequest.setTransactionType(TransactionType.AUTH);
		transactionRequest.setMerchantCode("543");
		transactionRequest.setTerminalId("5435");
		cardData.setCardNumber("546");
		transactionRequest.setCardData(cardData);
		transactionResponse.setTransactionDTO(dtos);
		transactionRequest.setTotalTxnAmount(Long.parseLong("34"));
		dtos.add(transactionDTO);
		pgTransactionServiceImpl.processTransaction(transactionRequest, merchant);
	}

	@Test
	public void testProcessTransactionMerchantElse() {
		TransactionRequest transactionRequest = new TransactionRequest();
		merchant = new PGMerchant();
		pgTransactionServiceImpl.processTransaction(transactionRequest, merchant);
	}

	@Test
	public void testProcessAuthCapture() {
		TransactionRequest transactionRequest = new TransactionRequest();
		pgTransactionServiceImpl.processAuthCapture(transactionRequest,merchant);
	}

	@Test
	public void testProcessAuth() {
		TransactionRequest transactionRequest = new TransactionRequest();
		pgTransactionServiceImpl.processAuth(transactionRequest);
	}

	@Test
	public void testProcessCapture() {
		TransactionRequest transactionRequest = new TransactionRequest();
		pgTransactionServiceImpl.processCapture(transactionRequest);
	}

	@Test
	public void testProcessVoid() {
		TransactionRequest transactionRequest = new TransactionRequest();
		PGTransaction pgTransaction = new PGTransaction();
		pgTransaction.setMerchantSettlementStatus("executed");
		pgTransaction.setTransactionType("refund");
		Mockito.when(voidTransactionDao.findTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgTransaction);
		pgTransactionServiceImpl.processVoid(transactionRequest);
	}

	@Test
	public void testProcessReversal() {
		TransactionRequest transactionRequest = new TransactionRequest();
		pgTransactionServiceImpl.processReversal(transactionRequest);
	}

	@Test
	public void testProcessRefund() {
		TransactionRequest transactionRequest = new TransactionRequest();
		pgTransactionServiceImpl.processRefund(transactionRequest);
	}

	@Test
	public void testProcessSplitSale() {
		TransactionRequest transactionRequest = new TransactionRequest();
		pgTransactionServiceImpl.processSplitSale(transactionRequest,merchant);
	}

	@Test
	public void testProcessSplitReject() throws SplitTransactionException {
		TransactionRequest transactionRequest = new TransactionRequest();
		Response splitRejectResponse = new Response();
		PGSplitTransaction pgSplitTransaction = new PGSplitTransaction();
		PGTransaction txnToVoid = new PGTransaction();
		pgSplitTransaction.setStatus(Long.parseLong("1"));
		splitRejectResponse.setErrorCode("00");
		Mockito.when(splitTransactionDao.getPGSplitTransactionByMerchantIdAndPgRefTransactionIdAndSplitAmount(
				Matchers.anyString(), Matchers.anyString(), Matchers.anyLong())).thenReturn(pgSplitTransaction);
		Mockito.when(voidTransactionDao.findTransactionToReversalByMerchantIdAndPGTxnId(Matchers.anyString(),
				Matchers.anyString())).thenReturn(txnToVoid);
		pgTransactionServiceImpl.processSplitReject(transactionRequest,merchant);
	}

	@Test
	public void testReverseSplitSaleIfExists() {
		PGSplitTransaction pgSplitTransaction = new PGSplitTransaction();
		pgSplitTransaction.setStatus(0l);
		pgSplitTransaction.setPgRefTransactionId("587");
		Mockito.when(splitTransactionDao.getPGSplitTransactionByMerchantIdAndPgRefTransactionId(Matchers.anyString(),
				Matchers.anyString())).thenReturn(pgSplitTransaction);
		pgTransactionServiceImpl.reverseSplitSaleIfExists("123", "434");
	}

	@Test
	public void testProcessRefundVoid() throws SplitTransactionException {
		TransactionRequest transactionRequest = new TransactionRequest();
		pgTransactionServiceImpl.processRefundVoid(transactionRequest);
	}

	@Test
	public void testProcessLoadFund() {
		TransactionRequest transactionRequest = new TransactionRequest();
		merchant = new PGMerchant();
		pgTransactionServiceImpl.processLoadFund(transactionRequest, merchant);
	}

	@Test
	public void testLogPgAccountHistory() throws ServiceException {
		PGTransaction pgTransaction = new PGTransaction();
		PGAccount updatedAccount = new PGAccount();
		updatedAccount.setCurrentBalance(Long.parseLong("543"));
		pgTransaction.setTxnAmount(Long.parseLong("62"));
		pgTransaction.setFeeAmount(Long.parseLong("66"));
		updatedAccount.setAvailableBalance(Long.parseLong("564"));
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(updatedAccount);
		pgTransactionServiceImpl.logPgAccountHistory("credit", pgTransaction, true);
	}

	@Test
	public void testLogPgAccountHistoryElse() throws ServiceException {
		PGTransaction pgTransaction = new PGTransaction();
		PGAccount updatedAccount = new PGAccount();
		updatedAccount.setCurrentBalance(Long.parseLong("543"));
		pgTransaction.setTxnAmount(Long.parseLong("62"));
		pgTransaction.setFeeAmount(Long.parseLong("66"));
		updatedAccount.setAvailableBalance(Long.parseLong("564"));
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(updatedAccount);
		pgTransactionServiceImpl.logPgAccountHistory("abc", pgTransaction, true);
	}

	@Test
	public void testUpdateSettlementStatus() {
		PGTransaction pgTransaction = new PGTransaction();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		List<Object> objectResult = new ArrayList<>();
		Object object = new Object();
		objectResult.add(object);
		pgTransaction.setPan("1");
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgOnlineTxnLog);
		try {
			pgTransactionServiceImpl.updateSettlementStatus("1", "2", "3", "4", "5", "6", Long.parseLong("654"),"7");
		} catch (Exception e) {
			logger.info("Error:: PGTransactionServiceImplTest:: testUpdateSettlementStatus method", e);

		}
	}

	@Test
	public void testUpdateSettlementStatusException() {
		PGTransaction pgTransaction = new PGTransaction();
		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		Mockito.when(transactionDao.getTransactionOnTxnIdAndTxnType(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(pgTransaction);
		Mockito.when(onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(pgOnlineTxnLog);
		try {
			pgTransactionServiceImpl.updateSettlementStatus("1", "2", "3", "4", "Executed", "6", Long.parseLong("654"),"7");
		} catch (Exception e) {
			logger.info("Error:: PGTransactionServiceImplTest:: testUpdateSettlementStatusException method", e);

		}
	}

	@Test
	public void testGetProcessingFee() {
		List<PGAcquirerFeeValue> acquirerFeeValueList = new ArrayList<>();
		PGAcquirerFeeValue acquirerFeeValue = new PGAcquirerFeeValue();
		acquirerFeeValueList.add(acquirerFeeValue);
		Mockito.when(
				feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(acquirerFeeValueList);
		try {
			pgTransactionServiceImpl.getProcessingFee("1", "2", Long.parseLong("654"));
		} catch (Exception e) {
			logger.info("Error:: PGTransactionServiceImplTest:: testGetProcessingFee method", e);

		}
	}

	@Test
	public void testUpdateAccountCCTransactionsCcAmountCredit() {
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccount account = new PGAccount();
		PGAccountTransactions accountTransactions = new PGAccountTransactions();
		accountTransactions.setTransactionCode("CC_AMOUNT_CREDIT");
		account.setAvailableBalance(Long.parseLong("534"));
		accountTransactions.setCredit(Long.parseLong("34"));
		account.setCurrentBalance(Long.parseLong("434"));
		accountTxns.add(accountTransactions);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(account);
		pgTransactionServiceImpl.updateAccountCCTransactions("1", "2", "abc");
	}

	@Test
	public void testUpdateAccountCCTransactionsCcFeeDebit() {
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccount account = new PGAccount();
		PGAccountTransactions accountTransactions = new PGAccountTransactions();
		accountTransactions.setTransactionCode("CC_FEE_DEBIT");
		account.setAvailableBalance(Long.parseLong("534"));
		accountTransactions.setDebit(Long.parseLong("34"));
		account.setCurrentBalance(Long.parseLong("434"));
		accountTxns.add(accountTransactions);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(account);
		pgTransactionServiceImpl.updateAccountCCTransactions("1", "2", "abc");
	}

	@Test
	public void testUpdateAccountCCTransactionsCcMerchantFeeCredit() {
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccount account = new PGAccount();
		PGAccountTransactions accountTransactions = new PGAccountTransactions();
		accountTransactions.setTransactionCode("CC_MERCHANT_FEE_CREDIT");
		account.setAvailableBalance(Long.parseLong("534"));
		accountTransactions.setCredit(Long.parseLong("34"));
		account.setCurrentBalance(Long.parseLong("434"));
		accountTxns.add(accountTransactions);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(account);
		pgTransactionServiceImpl.updateAccountCCTransactions("1", "2", "abc");
	}

	@Test
	public void testUpdateAccountCCTransactionsCcAcquirerFeeCredit() {
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		List<PGTransaction> transactions = new ArrayList<>();
		PGTransaction pgTransaction = new PGTransaction();
		PGCurrencyConfig currencyConfig = new PGCurrencyConfig();
		PGAccount account = new PGAccount();
		PGAccountTransactions accountTransactions = new PGAccountTransactions();
		accountTransactions.setTransactionCode("CC_ACQUIRER_FEE_CREDIT");
		account.setAvailableBalance(Long.parseLong("534"));
		accountTransactions.setCredit(Long.parseLong("34"));
		account.setCurrentBalance(Long.parseLong("434"));
		accountTxns.add(accountTransactions);
		transactions.add(pgTransaction);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(account);
		Mockito.when(transactionRepository.findByTransactionId(Matchers.anyString())).thenReturn(transactions);
		Mockito.when(currencyConfigDao.getcurrencyCodeAlpha(Matchers.anyString())).thenReturn(currencyConfig);
		Mockito.when(accountRepository.findByEntityTypeAndCurrencyAndStatus(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString())).thenReturn(account);
		pgTransactionServiceImpl.updateAccountCCTransactions("1", "2", "abc");
	}

	@Test
	public void testUpdateAccountCCTransactionsRejected() {
		List<PGAccountTransactions> accountTxns = new ArrayList<>();
		PGAccountTransactions accountTransactions = new PGAccountTransactions();
		accountTxns.add(accountTransactions);
		Mockito.when(accountTransactionsDao
				.getAccountTransactionsOnTransactionIdAndTransactionType(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(accountTxns);
		pgTransactionServiceImpl.updateAccountCCTransactions("1", "2", "Rejected");
	}

	@Test
	public void testLogAccountHistory() {
		PGAccount updatedAccount = new PGAccount();
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(updatedAccount);
		try {
			pgTransactionServiceImpl.logAccountHistory("1", "2", "3");
		} catch (Exception e) {
			logger.info("Error:: PGTransactionServiceImplTest:: testLogAccountHistory method", e);

		}
	}

	@Test
	public void testProcessBalanceEnquiry() throws SplitTransactionException {
		TransactionRequest transactionRequest = new TransactionRequest();
		pgTransactionServiceImpl.processBalanceEnquiry(transactionRequest);
	}

}
