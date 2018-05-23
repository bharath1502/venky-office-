package com.chatak.pay.processor.impl;

import java.sql.Timestamp;

import org.jpos.iso.ISOMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.pay.controller.model.CardDetails;
import com.chatak.pay.controller.model.PaymentDetails;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.exception.InvalidRequestException;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.MerchantTerminalDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.OnlineTxnLogDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGOnlineTxnLog;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.switches.enums.TransactionType;
import com.litle.sdk.generate.CountryTypeEnum;

@RunWith(MockitoJUnitRunner.class)
public class CardPaymentProcessorImplTest {

	@InjectMocks
	CardPaymentProcessorImpl cardPaymentProcessorImpl = new CardPaymentProcessorImpl();

	@Mock
	private MessageSource messageSource;

	@Mock
	private OnlineTxnLogDao onlineTxnLogDao;

	@Mock
	private TransactionDao transactionDao;

	@Mock
	protected MerchantTerminalDao merchantTerminalDao;

	@Mock
	private AccountDao accountDao;

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private CurrencyConfigRepository currencyConfigRepository;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Mock
	VoidTransactionDao voidTransactionDao;

	@Test
	public void testValidMerchant() {
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setStatus(0);
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		cardPaymentProcessorImpl.validMerchant("123");
	}

	@Test
	public void testValidMerchantException() {
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenThrow(new NullPointerException());
		cardPaymentProcessorImpl.validMerchant("123");
	}

	@Test
	public void testValidateMerchantIdAndTerminalId() {
		cardPaymentProcessorImpl.validateMerchantIdAndTerminalId("123", "456");
	}

	@Test
	public void testValidateMerchantIdAndTerminalIdException() {
		Mockito.when(merchantTerminalDao.validateMerchantIdAndTerminalId(Matchers.anyString(), Matchers.anyString()))
				.thenThrow(new NullPointerException());
		cardPaymentProcessorImpl.validateMerchantIdAndTerminalId("123", "456");
	}

	@Test
	public void testInitializeCardPayment() {
		PaymentDetails paymentDetails = new PaymentDetails();
		CardDetails cardDetails = new CardDetails();
		paymentDetails.setBillerCountry(CountryTypeEnum.AE);
		cardDetails.setNumber("45");
		paymentDetails.setClientPort(1);
		paymentDetails.setTransactionType(TransactionType.AUTH);
		cardPaymentProcessorImpl.initializeCardPayment(paymentDetails, cardDetails);
	}

	@Test
	public void testInitializeCardPaymentException() {
		PaymentDetails paymentDetails = new PaymentDetails();
		CardDetails cardDetails = new CardDetails();
		paymentDetails.setBillerCountry(CountryTypeEnum.AE);
		cardDetails.setNumber("45");
		paymentDetails.setClientPort(1);
		paymentDetails.setTransactionType(TransactionType.AUTH);
		Mockito.when(onlineTxnLogDao.logRequest(Matchers.any(PGOnlineTxnLog.class)))
				.thenThrow(new NullPointerException());
		cardPaymentProcessorImpl.initializeCardPayment(paymentDetails, cardDetails);
	}

	@Test
	public void testProcessCardPayment() {
		ISOMsg isoMsg = new ISOMsg();
		cardPaymentProcessorImpl.processCardPayment(isoMsg);
	}

	@Test(expected = ChatakPayException.class)
	public void testIsDuplicateRequestTrue() throws ChatakPayException {
		boolean isDuplicate = true;
		Mockito.when(onlineTxnLogDao.isDuplicateRequest(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(isDuplicate);
		cardPaymentProcessorImpl.isDuplicateRequest("123", "456");
	}

	@Test
	public void testIsDuplicateRequestFalse() throws ChatakPayException {
		boolean isDuplicate = false;
		Mockito.when(onlineTxnLogDao.isDuplicateRequest(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(isDuplicate);
		cardPaymentProcessorImpl.isDuplicateRequest("123", "456");
	}

	@Test
	public void testUpdateAccountCredit() {
		PGAccount pgAccount = new PGAccount();
		pgAccount.setCurrentBalance(Long.parseLong("45"));
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		cardPaymentProcessorImpl.updateAccount("123", "credit", Long.parseLong("34"));
	}

	@Test
	public void testUpdateAccountDebit() {
		PGAccount pgAccount = new PGAccount();
		pgAccount.setCurrentBalance(Long.parseLong("45"));
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		cardPaymentProcessorImpl.updateAccount("123", "debit", Long.parseLong("34"));
	}

	@Test
	public void testUpdateAccountAuthPayment() {
		PGAccount pgAccount = new PGAccount();
		pgAccount.setCurrentBalance(Long.parseLong("45"));
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		cardPaymentProcessorImpl.updateAccount("123", "auth_payment", Long.parseLong("34"));
	}

	@Test
	public void testUpdateAccountCapturePayment() {
		PGAccount pgAccount = new PGAccount();
		pgAccount.setAvailableBalance(Long.parseLong("786"));
		Mockito.when(accountDao.getPgAccount(Matchers.anyString())).thenReturn(pgAccount);
		cardPaymentProcessorImpl.updateAccount("123", "capture_payment", Long.parseLong("34"));
	}

	@Test(expected = ChatakPayException.class)
	public void testIsDuplicateRequestTxnTypeTrue() throws ChatakPayException {
		Mockito.when(onlineTxnLogDao.isDuplicateRequest(Matchers.anyString(), Matchers.anyString(), Matchers.anyLong(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(true);
		cardPaymentProcessorImpl.isDuplicateRequest("123", "456", Long.parseLong("34"), "45", "51", "44");
	}

	@Test
	public void testIsDuplicateRequestTxnTypeFalse() throws ChatakPayException {
		cardPaymentProcessorImpl.isDuplicateRequest("123", "456", Long.parseLong("34"), "45", "51", "44");
	}

	@Test(expected = ChatakPayException.class)
	public void testIsDuplicateRequestOrderIdTrue() throws ChatakPayException {
		Mockito.when(
				onlineTxnLogDao.isDuplicateRequest(Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
				.thenReturn(true);
		cardPaymentProcessorImpl.isDuplicateRequest("123", "456", "543");
	}

	@Test
	public void testIsDuplicateRequestOrderIdFalse() throws ChatakPayException {
		cardPaymentProcessorImpl.isDuplicateRequest("123", "456", "543");
	}

	@Test(expected = ChatakPayException.class)
	public void testDuplicateRequest() throws ChatakPayException {
		Timestamp previousRequest = new Timestamp(Long.parseLong("43"));
		Mockito.when(onlineTxnLogDao.duplicateRequest(Matchers.anyString(), Matchers.anyString(), Matchers.anyLong(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(previousRequest);
		cardPaymentProcessorImpl.duplicateRequest("123", "456", Long.parseLong("34"), "45", "51", "44");
	}

	@Test(expected = InvalidRequestException.class)
	public void testDuplicateInvoice() throws InvalidRequestException {
		PGTransaction transaction = new PGTransaction();
		Mockito.when(transactionDao.getTransactionOnInvoiceNum(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString())).thenReturn(transaction);
		cardPaymentProcessorImpl.duplicateInvoice("123", "456", "543");
	}

	@Test(expected = ChatakPayException.class)
	public void testDuplicateOrderRequest() throws ChatakPayException {
		Timestamp previousRequest = new Timestamp(Long.parseLong("43"));
		Mockito.when(onlineTxnLogDao.duplicateOrderRequest(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyLong(), Matchers.anyString(), Matchers.anyString())).thenReturn(previousRequest);
		cardPaymentProcessorImpl.duplicateOrderRequest("123", "456", Long.parseLong("34"), "54", "465");
	}

	@Test
	public void testFetchCurrencyCodeNumeric() throws ChatakPayException {
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		Mockito.when(currencyConfigRepository.findByCurrencyCodeAlpha(Matchers.anyString()))
				.thenReturn(pgCurrencyConfig);
		cardPaymentProcessorImpl.fetchCurrencyCodeNumeric("123");
	}

	@Test
	public void testFetchCurrencyCodeNumericNull() throws ChatakPayException {
		cardPaymentProcessorImpl.fetchCurrencyCodeNumeric("123");
	}

	@Test
	public void testValidateMerchantId() throws ChatakPayException {
		cardPaymentProcessorImpl.validateMerchantId("123");
	}

	@Test
	public void testValidateMerchantIdException() throws ChatakPayException {
		Mockito.when(merchantTerminalDao.validateMerchantId(Matchers.anyString()))
				.thenThrow(new NullPointerException());
		cardPaymentProcessorImpl.validateMerchantId("123");
	}

}
