package com.chatak.pay.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.chatak.pay.controller.model.CardData;
import com.chatak.pay.controller.model.LoginRequest;
import com.chatak.pay.controller.model.LoginResponse;
import com.chatak.pay.controller.model.Request;
import com.chatak.pay.controller.model.SplitStatusRequest;
import com.chatak.pay.controller.model.SplitTxnData;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pay.controller.model.TransactionResponse;
import com.chatak.pay.controller.model.topup.TopupRequest;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.model.TSMResponse;
import com.chatak.pay.processor.CardPaymentProcessor;
import com.chatak.pay.service.BINService;
import com.chatak.pay.service.PGMerchantService;
import com.chatak.pay.service.PGSplitTransactionService;
import com.chatak.pay.service.PGTransactionService;
import com.chatak.pay.service.TopupService;
import com.chatak.pay.service.VaultService;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.bean.ChangePasswordRequest;
import com.chatak.pg.bean.ForgotPasswordRequest;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.OriginalChannelEnum;
import com.chatak.pg.enums.ShareModeEnum;
import com.chatak.pg.enums.TransactionType;
import com.chatak.pg.util.Properties;
import com.litle.sdk.generate.MethodOfPaymentTypeEnum;

@RunWith(MockitoJUnitRunner.class)
public class TransactionRestControllerTest {

	@InjectMocks
	TransactionRestController transactionRestController = new TransactionRestController();

	@Mock
	private MessageSource messageSource;

	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	@Mock
	HttpSession session;

	@Mock
	protected PGTransactionService pgTransactionService;

	@Mock
	protected CardPaymentProcessor cardPaymentProcessor;

	@Mock
	protected TransactionDao transactionDao;

	@Mock
	protected PGSplitTransactionService pgSplitTransactionService;

	@Mock
	protected VaultService vaultService;

	@Mock
	protected PGMerchantService pgMerchantService;

	@Mock
	protected BINService binService;

	@Mock
	protected TopupService issuanceService;

	@Autowired
	VoidTransactionDao voidTransactionDao;
	
	@Mock
	protected PGMerchant pgMerchant;

	@Mock
	TransactionResponse transactionResponse;

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("max.download.limit", "12");
        properties.setProperty("thread.pool.size", "500");
        properties.setProperty("chatak-tsm.service.fetch.merchant.tid", "fetchByMerchantIdAndTId");
        properties.setProperty("chatak-tms.rest.service.url", "https://dev.ipsidy.net/tms/admin/management/");
        properties.setProperty("thread.max.per.route", "500");
		properties.setProperty("chatak-pay.skip.card.type.check", "false");
		properties.setProperty("chatak.username.required", "null");
		properties.setProperty("chatak.password.required", "null");
		properties.setProperty("chatak-tsm.service.url", "https://dev.ipsidy.net/issuance-admin");
		properties.setProperty("chatak-pay.skip.card.type.check", "false");
		Properties.mergeProperties(properties);
	}

	@Test
	public void testProcess() {
		TransactionRequest transactionRequest = new TransactionRequest();
		CardData cardData = new CardData();
		cardData.setCardType(MethodOfPaymentTypeEnum.AX);
		transactionRequest.setEntryMode(EntryModeEnum.QR_SALE);
		transactionRequest.setCardData(cardData);
		transactionRestController.process(request, response, session, transactionRequest);
	}

	@Test
	public void testProcessShareMode() {
		TransactionRequest transactionRequest = new TransactionRequest();
		SplitTxnData splitTxnData = new SplitTxnData();
		CardData cardData = new CardData();
		TSMResponse tsmResponse = new TSMResponse();
		cardData.setCardType(MethodOfPaymentTypeEnum.AX);
		transactionRequest.setCardData(cardData);
		transactionRequest.setShareMode(ShareModeEnum.SINGLE);
		tsmResponse.setErrorCode("243");
		splitTxnData.setSplitAmount(10l);
		splitTxnData.setRefMobileNumber(1234561234l);
		transactionRequest.setSplitTxnData(splitTxnData);
		transactionRequest.setMerchantCode("123456789123456");
		transactionRequest.setTerminalId("12345678");
		transactionRequest.setTransactionType(TransactionType.SALE);
		transactionRequest.setOriginChannel(OriginalChannelEnum.ADMIN_WEB.value());
		pgMerchant = new PGMerchant();
		Mockito.when(cardPaymentProcessor.validateMerchantId(Matchers.anyString())).thenReturn(pgMerchant);
		transactionRequest.setMerchantAmount(10l);
		pgMerchant.setBusinessName("Bagota");
		transactionRequest.setInvoiceNumber("123456789");
		transactionRequest.setRegisterNumber("12354687");
		transactionRequest.setOrderId("54879525");
		transactionRequest.setSplitRefNumber("9874661223");
		cardData.setCardHolderName("smita");
		cardData.setCardNumber("1234567891234567894");
		cardData.setCvv("111");
		cardData.setExpDate("1801");
		cardData.setCardType(MethodOfPaymentTypeEnum.BLANK);
		transactionRequest.setTotalTxnAmount(15l);
		transactionRequest.setFeeAmount(15l);
		transactionRequest.setCardData(cardData);
		transactionRequest.setTimeZoneOffset("‎UTC+00:00");
		transactionResponse = new TransactionResponse();
		Mockito.when(pgTransactionService.processTransaction(Matchers.any(TransactionRequest.class),
				Matchers.any(PGMerchant.class))).thenReturn(transactionResponse);
		transactionRestController.process(request, response, session, transactionRequest);
	}

	@Test
	public void testProcessShareModeSplitAmount() {
	  TransactionRequest transactionRequest = new TransactionRequest();
      CardData cardData = new CardData();
      TSMResponse tsmResponse = new TSMResponse();
      SplitTxnData splitTxnData = new SplitTxnData();
      cardData.setCardType(MethodOfPaymentTypeEnum.AX);
      transactionRequest.setCardData(cardData);
      splitTxnData.setSplitAmount(Long.parseLong("01234567"));
      splitTxnData.setRefMobileNumber(9518476230l);
      transactionRequest.setSplitTxnData(splitTxnData);
      transactionRequest.setShareMode(ShareModeEnum.SINGLE);
      transactionRequest.setMerchantCode("951847629518476");
      transactionRequest.setTerminalId("95184762");
      transactionRequest.setTransactionType(TransactionType.SALE);
      transactionRequest.setOriginChannel(OriginalChannelEnum.ADMIN_WEB.value());
      transactionRequest.setTimeZoneOffset("GMT+0200");
      tsmResponse.setErrorCode("243");
      pgMerchant = new PGMerchant();
	  Mockito.when(cardPaymentProcessor.validateMerchantId(Matchers.anyString())).thenReturn(pgMerchant);
	  transactionRequest.setMerchantAmount(10l);
	  pgMerchant.setBusinessName("Bagota");
	  transactionRequest.setInvoiceNumber("123456789");
	  transactionRequest.setRegisterNumber("12354687");
	  transactionRequest.setOrderId("54879525");
	  transactionRequest.setSplitRefNumber("9874661223");
	  cardData.setCardHolderName("smita");
	  cardData.setCardNumber("1234567891234567894");
	  cardData.setCvv("111");
	  cardData.setExpDate("1801");
	  cardData.setCardType(MethodOfPaymentTypeEnum.BLANK);
	  transactionRequest.setTotalTxnAmount(15l);
	  transactionRequest.setFeeAmount(15l);
	  transactionRequest.setCardData(cardData);
	  transactionRequest.setTimeZoneOffset("‎UTC+00:00");
	  transactionResponse = new TransactionResponse();
	  Mockito.when(pgTransactionService.processTransaction(Matchers.any(TransactionRequest.class),
			  Matchers.any(PGMerchant.class))).thenReturn(transactionResponse);
      transactionRestController.process(request, response, session, transactionRequest);
	}

	@Test
	public void testEnquiry() {
		SplitStatusRequest splitStatusRequest = new SplitStatusRequest();
		splitStatusRequest.setSplitRefNumber("12545");
		splitStatusRequest.setMerchantCode("252512345647989");
		splitStatusRequest.setSplitTxnAmount(10l);
		transactionRestController.enquiry(request, response, session, splitStatusRequest);
	}

	@Test
	public void testLogin() {
		LoginRequest loginRequest = new LoginRequest();
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		transactionRestController.login(request, response, session, loginRequest);
	}

	@Test
	public void testLoginsetUsername() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("1234");
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		transactionRestController.login(request, response, session, loginRequest);
	}

	@Test
	public void testLoginSetUsernameAndPassword() {
		LoginRequest loginRequest = new LoginRequest();
		LoginResponse loginResponse = new LoginResponse();
		loginRequest.setUsername("1234");
		loginRequest.setPassword("1234");
		Mockito.when(pgMerchantService.authenticateMerchantUser(Matchers.any(LoginRequest.class)))
				.thenReturn(loginResponse);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		transactionRestController.login(request, response, session, loginRequest);
	}

	@Test
	public void testGetOperatorList() {
		Request getOperatorsRequest = new Request();
		transactionRestController.getOperatorList(request, response, session, getOperatorsRequest);
	}

	@Test
	public void testGetOfferCategories() {
		TopupRequest topupRequest = new TopupRequest();
		transactionRestController.getOfferCategories(request, response, session, topupRequest);
	}

	@Test
	public void testGetOfferDetails() {
		TopupRequest topupRequest = new TopupRequest();
		transactionRestController.getOfferDetails(request, response, session, topupRequest);
	}

	@Test
	public void testDoTopup() {
		TopupRequest topupRequest = new TopupRequest();
		transactionRestController.doTopup(request, response, session, topupRequest);
	}

	@Test
	public void testGetMerchants() {
		transactionRestController.getMerchants(request, response, session);
	}

	@Test
	public void testChangePassword() throws ChatakPayException {
		ChangePasswordRequest changePassword = new ChangePasswordRequest();
		Mockito.when(
				pgMerchantService.changedPassword(Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
				.thenReturn(true);
		transactionRestController.changePassword(changePassword);
	}

	@Test
	public void testForgotPassword() throws ChatakPayException {
		ForgotPasswordRequest changePassword = new ForgotPasswordRequest();
		Mockito.when(pgMerchantService.forgotPassword(Matchers.anyString(), Matchers.anyString())).thenReturn(true);
		transactionRestController.forgotPassword(request, changePassword);
	}
	
}
