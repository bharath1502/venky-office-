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
import com.chatak.pg.bean.ChangePasswordRequest;
import com.chatak.pg.bean.ForgotPasswordRequest;
import com.chatak.pg.enums.EntryModeEnum;
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

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
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

	@Test(expected=NullPointerException.class)
	public void testProcessTransactionType() {
		TransactionRequest transactionRequest = new TransactionRequest();
		CardData cardData = new CardData();
		TSMResponse tsmResponse = new TSMResponse();
		cardData.setCardHolderEmail("gfshgdf");
		cardData.setCardType(MethodOfPaymentTypeEnum.AX);
		transactionRequest.setCardData(cardData);
		transactionRequest.setTransactionType(TransactionType.LOAD_FUND);
		transactionRequest.setShareMode(ShareModeEnum.SINGLE);
		tsmResponse.setErrorCode("success");
		transactionRestController.process(request, response, session, transactionRequest);
	}

	@Test(expected=NullPointerException.class)
	public void testProcessShareMode() {
		TransactionRequest transactionRequest = new TransactionRequest();
		CardData cardData = new CardData();
		TSMResponse tsmResponse = new TSMResponse();
		cardData.setCardType(MethodOfPaymentTypeEnum.AX);
		transactionRequest.setCardData(cardData);
		transactionRequest.setShareMode(ShareModeEnum.SINGLE);
		tsmResponse.setErrorCode("243");
		transactionRestController.process(request, response, session, transactionRequest);
	}

	@Test(expected=NullPointerException.class)
	public void testProcessShareModeSplitAmount() {
		TransactionRequest transactionRequest = new TransactionRequest();
		CardData cardData = new CardData();
		TSMResponse tsmResponse = new TSMResponse();
		SplitTxnData splitTxnData = new SplitTxnData();
		cardData.setCardType(MethodOfPaymentTypeEnum.AX);
		transactionRequest.setCardData(cardData);
		splitTxnData.setSplitAmount(Long.parseLong("012345678901112"));
		transactionRequest.setSplitTxnData(splitTxnData);
		transactionRequest.setShareMode(ShareModeEnum.SINGLE);
		tsmResponse.setErrorCode("243");
		transactionRestController.process(request, response, session, transactionRequest);
	}

	@Test
	public void testEnquiry() {
		SplitStatusRequest splitStatusRequest = new SplitStatusRequest();
		splitStatusRequest.setSplitRefNumber("Invalid billing data.");
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
	public void testLoginSetUsernameAndPasswordException() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("1234");
		loginRequest.setPassword("1234");
		Mockito.when(pgMerchantService.authenticateMerchantUser(Matchers.any(LoginRequest.class)))
				.thenThrow(new NullPointerException());
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
	public void testGetOperatorListException() {
		Request getOperatorsRequest = new Request();
		Mockito.when(issuanceService.getOperators(Matchers.any(Request.class))).thenThrow(new NullPointerException());
		transactionRestController.getOperatorList(request, response, session, getOperatorsRequest);
	}

	@Test
	public void testGetOfferCategories() {
		TopupRequest topupRequest = new TopupRequest();
		transactionRestController.getOfferCategories(request, response, session, topupRequest);
	}

	@Test
	public void testGetOfferCategoriesException() {
		TopupRequest topupRequest = new TopupRequest();
		Mockito.when(issuanceService.getTopupCategories(Matchers.any(TopupRequest.class)))
				.thenThrow(new NullPointerException());
		transactionRestController.getOfferCategories(request, response, session, topupRequest);
	}

	@Test
	public void testGetOfferDetails() {
		TopupRequest topupRequest = new TopupRequest();
		transactionRestController.getOfferDetails(request, response, session, topupRequest);
	}

	@Test
	public void testGetOfferDetailsException() {
		TopupRequest topupRequest = new TopupRequest();
		Mockito.when(issuanceService.getTopupOffers(Matchers.any(TopupRequest.class)))
				.thenThrow(new NullPointerException());
		transactionRestController.getOfferDetails(request, response, session, topupRequest);
	}

	@Test
	public void testDoTopup() {
		TopupRequest topupRequest = new TopupRequest();
		transactionRestController.doTopup(request, response, session, topupRequest);
	}

	@Test
	public void testDoTopupException() {
		TopupRequest topupRequest = new TopupRequest();
		Mockito.when(issuanceService.doTopup(Matchers.any(TopupRequest.class))).thenThrow(new NullPointerException());
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

	@Test(expected=NullPointerException.class)
	public void testForgotPassword() throws ChatakPayException {
		ForgotPasswordRequest changePassword = new ForgotPasswordRequest();
		Mockito.when(pgMerchantService.forgotPassword(Matchers.anyString(), Matchers.anyString())).thenReturn(true);
		transactionRestController.forgotPassword(request, changePassword);
	}
	

}
