package com.chatak.merchant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.exception.ChatakPayException;
import com.chatak.merchant.model.GetTransactionResponse;
import com.chatak.merchant.model.TransactionResponse;
import com.chatak.merchant.service.AccountService;
import com.chatak.merchant.service.RestPaymentService;
import com.chatak.merchant.service.TransactionService;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.model.VirtualTerminalRefundDTO;
import com.chatak.pg.model.VirtualTerminalVoidDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class MerchantTransactionsControllerTest {

	private Logger logger = Logger.getLogger(MerchantTransactionsController.class);

	@InjectMocks
	MerchantTransactionsController merchantTransactionsController = new MerchantTransactionsController();

	@Mock
	MessageSource messageSource;

	@Mock
	private TransactionService transactionService;

	@Mock
	private RestPaymentService paymentService;

	@Mock
	AccountService accountService;

	@Mock
	private MerchantDao merchantDao;

	@Mock
	MerchantProfileDao merchantProfileDao;

	@Mock
	HttpServletRequest request;

	@Mock
	HttpSession session;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/pages/");
		viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(merchantTransactionsController).setViewResolvers(viewResolver)
				.build();
	}

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("merchant.service.dashboard.feature.id", "existing");
		properties.setProperty("refund.submerchant.transactions.id", "refund");
		properties.setProperty("merchant.services.reports.merchant.transaction.revenue.feature.id", "abcd");
		Properties.mergeProperties(properties);
	}

	@Test
	public void testShowTransactionRefundHeader() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_REFUND_PAGE)
					.sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testShowTransactionRefundHeader method" + e);

		}
	}

	@Test
	public void testShowTransactionRefundExistingFeature() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_REFUND_PAGE)
					.sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(Constants.REDIRECT_PG + URLMappingConstants.CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE));
		} catch (Exception e) {
			logger.error(
					"Error:: MerchantTransactionsControllerTest:: testShowTransactionRefundExistingFeature method" + e);

		}
	}

	@Test
	public void testShowTransactionRefund() throws ChatakPayException {
		GetTransactionResponse getTransactionResponse = new GetTransactionResponse();
		Mockito.when(transactionService.hasAccessTo(Matchers.anyString(), Matchers.anyString(),
				Matchers.any(HttpSession.class))).thenReturn(true);
		Mockito.when(paymentService.getTransactionByRefId(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(getTransactionResponse);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_REFUND_PAGE)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.SHOW_CHATAK_MERCHANT_TRANSACTION_REFUND));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testShowTransactionRefund method" + e);

		}
	}

	@Test
	public void testShowTransactionRefundElse() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_REFUND_PAGE)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(Constants.REDIRECT_PG + URLMappingConstants.CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testShowTransactionRefundElse method" + e);

		}
	}

	@Test
	public void testShowTransactionRefundChatakPayException() {
		Mockito.when(transactionService.hasAccessTo(Matchers.anyString(), Matchers.anyString(),
				Matchers.any(HttpSession.class))).thenThrow(ChatakPayException.class);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_REFUND_PAGE)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.SHOW_CHATAK_MERCHANT_TRANSACTION_REFUND));
		} catch (Exception e) {
			logger.error(
					"Error:: MerchantTransactionsControllerTest:: testShowTransactionRefundChatakPayException method"
							+ e);

		}
	}

	@Test
	public void testShowTransactionRefundException() throws ChatakPayException {
		Mockito.when(transactionService.hasAccessTo(Matchers.anyString(), Matchers.anyString(),
				Matchers.any(HttpSession.class))).thenReturn(true);
		Mockito.when(paymentService.getTransactionByRefId(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenThrow(new NullPointerException());
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_REFUND_PAGE)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.SHOW_CHATAK_MERCHANT_TRANSACTION_REFUND));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testShowTransactionRefundException method" + e);

		}
	}

	@Test
	public void testFetchTransactionForVoidHeader() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_VOID_PROCESS)
					.sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testFetchTransactionForVoidHeader method" + e);

		}
	}

	@Test
	public void testFetchTransactionForVoidExistingFeature() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_VOID_PROCESS)
					.sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).header(Constants.REFERER, Constants.REFERER));
		} catch (Exception e) {
			logger.error(
					"Error:: MerchantTransactionsControllerTest:: testFetchTransactionForVoidExistingFeature method"
							+ e);

		}
	}

	@Test
	public void testFetchTransactionForVoid() throws ChatakPayException {
		GetTransactionResponse getTransactionResponse = new GetTransactionResponse();
		Mockito.when(transactionService.hasAccessTo(Matchers.anyString(), Matchers.anyString(),
				Matchers.any(HttpSession.class))).thenReturn(true);
		Mockito.when(paymentService.getTransactionByRefId(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(getTransactionResponse);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_VOID_PROCESS)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_VOID_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testFetchTransactionForVoid method" + e);

		}
	}

	@Test
	public void testFetchTransactionForVoidElse() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_VOID_PROCESS)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(Constants.REDIRECT_PG + URLMappingConstants.CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testFetchTransactionForVoidElse method" + e);

		}
	}

	@Test
	public void testFetchTransactionForVoidChatakPayException() {
		Mockito.when(transactionService.hasAccessTo(Matchers.anyString(), Matchers.anyString(),
				Matchers.any(HttpSession.class))).thenThrow(ChatakPayException.class);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_VOID_PROCESS)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_VOID_PAGE));
		} catch (Exception e) {
			logger.error(
					"Error:: MerchantTransactionsControllerTest:: testFetchTransactionForVoidChatakPayException method"
							+ e);

		}
	}

	@Test
	public void testFetchTransactionForVoidException() throws ChatakPayException {
		Mockito.when(transactionService.hasAccessTo(Matchers.anyString(), Matchers.anyString(),
				Matchers.any(HttpSession.class))).thenReturn(true);
		Mockito.when(paymentService.getTransactionByRefId(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenThrow(new NullPointerException());
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_VOID_PROCESS)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_VOID_PAGE));
		} catch (Exception e) {
			logger.error(
					"Error:: MerchantTransactionsControllerTest:: testFetchTransactionForVoidException method" + e);

		}
	}

	@Test
	public void testProcessVoidHeader() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_CHATAK_MERCHANT_TRANSACTION_VOID)
					.sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testProcessVoidHeader method" + e);

		}
	}

	@Test
	public void testProcessVoidExistingFeatures() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_CHATAK_MERCHANT_TRANSACTION_VOID)
					.sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(Constants.REDIRECT_PG + URLMappingConstants.CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testProcessVoidExistingFeatures method" + e);

		}
	}

	@Test
	public void testProcessVoid() throws ChatakPayException {
		TransactionResponse response2 = new TransactionResponse();
		GetTransactionResponse getTransactionResponse = new GetTransactionResponse();
		response2.setErrorCode(Constants.SUCCESS_CODE);
		Mockito.when(paymentService.doVoid(Matchers.any(VirtualTerminalVoidDTO.class))).thenReturn(response2);
		Mockito.when(transactionService.hasAccessTo(Matchers.anyString(), Matchers.anyString(),
				Matchers.any(HttpSession.class))).thenReturn(true);
		Mockito.when(paymentService.getTransactionByRefId(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(getTransactionResponse);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_CHATAK_MERCHANT_TRANSACTION_VOID)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_VOID_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testProcessVoid method" + e);

		}
	}

	@Test
	public void testProcessVoidElseIf() throws ChatakPayException {
		TransactionResponse response2 = new TransactionResponse();
		GetTransactionResponse getTransactionResponse = new GetTransactionResponse();
		response2.setErrorCode(Constants.ERROR);
		Mockito.when(paymentService.doVoid(Matchers.any(VirtualTerminalVoidDTO.class))).thenReturn(response2);
		Mockito.when(transactionService.hasAccessTo(Matchers.anyString(), Matchers.anyString(),
				Matchers.any(HttpSession.class))).thenReturn(true);
		Mockito.when(paymentService.getTransactionByRefId(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString())).thenReturn(getTransactionResponse);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_CHATAK_MERCHANT_TRANSACTION_VOID)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_VOID_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testProcessVoidElseIf method" + e);

		}
	}

	@Test
	public void testProcessVoidElse() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_CHATAK_MERCHANT_TRANSACTION_VOID)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(Constants.REDIRECT_PG + URLMappingConstants.CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testProcessVoidElse method" + e);

		}
	}

	@Test
	public void testProcessVoidChatakPayException() {
		Mockito.when(transactionService.hasAccessTo(Matchers.anyString(), Matchers.anyString(),
				Matchers.any(HttpSession.class))).thenThrow(ChatakPayException.class);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_CHATAK_MERCHANT_TRANSACTION_VOID)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_VOID_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testProcessVoidChatakPayException method" + e);

		}
	}

	@Test
	public void testProcessVoidException() throws ChatakPayException {
		Mockito.when(transactionService.hasAccessTo(Matchers.anyString(), Matchers.anyString(),
				Matchers.any(HttpSession.class))).thenReturn(true);
		Mockito.when(paymentService.doVoid(Matchers.any(VirtualTerminalVoidDTO.class)))
				.thenThrow(new NullPointerException());
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_CHATAK_MERCHANT_TRANSACTION_VOID)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_VOID_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testProcessVoidException method" + e);

		}
	}

	@Test
	public void testProcessRefundHeader() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_CHATAK_MERCHANT_TRANSACTION_REFUND)
					.sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testProcessRefundHeader method" + e);

		}
	}

	@Test
	public void testProcessRefundExistingFeatures() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_CHATAK_MERCHANT_TRANSACTION_REFUND)
					.sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(Constants.REDIRECT_PG + URLMappingConstants.CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testProcessRefundExistingFeatures method" + e);

		}
	}

	@Test
	public void testProcessRefund() throws ChatakPayException {
		VirtualTerminalRefundDTO virtualTerminalRefundDTO = new VirtualTerminalRefundDTO();
		TransactionResponse response2 = new TransactionResponse();
		virtualTerminalRefundDTO.setMerchantId("54");
		response2.setErrorCode(Constants.SUCCESS_CODE);
		Mockito.when(paymentService.doRefund(Matchers.any(VirtualTerminalRefundDTO.class))).thenReturn(response2);
		Mockito.when(transactionService.hasAccessTo(Matchers.anyString(), Matchers.anyString(),
				Matchers.any(HttpSession.class))).thenReturn(true);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_CHATAK_MERCHANT_TRANSACTION_REFUND)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
					.header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.SHOW_CHATAK_MERCHANT_TRANSACTION_REFUND));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testProcessRefund method" + e);

		}
	}

	@Test
	public void testProcessRefundElseIf() throws ChatakPayException {
		VirtualTerminalRefundDTO virtualTerminalRefundDTO = new VirtualTerminalRefundDTO();
		TransactionResponse response2 = new TransactionResponse();
		virtualTerminalRefundDTO.setMerchantId("54");
		response2.setErrorCode(Constants.ERROR);
		Mockito.when(paymentService.doRefund(Matchers.any(VirtualTerminalRefundDTO.class))).thenReturn(response2);
		Mockito.when(transactionService.hasAccessTo(Matchers.anyString(), Matchers.anyString(),
				Matchers.any(HttpSession.class))).thenReturn(true);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_CHATAK_MERCHANT_TRANSACTION_REFUND)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
					.header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.SHOW_CHATAK_MERCHANT_TRANSACTION_REFUND));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testProcessRefundElseIf method" + e);

		}
	}

	@Test
	public void testProcessRefundElse() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_CHATAK_MERCHANT_TRANSACTION_REFUND)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
					.header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(Constants.REDIRECT_PG + URLMappingConstants.CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testProcessRefundElse method" + e);

		}
	}

	@Test
	public void testProcessRefundChatakPayException() {
		Mockito.when(transactionService.hasAccessTo(Matchers.anyString(), Matchers.anyString(),
				Matchers.any(HttpSession.class))).thenThrow(ChatakPayException.class);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_CHATAK_MERCHANT_TRANSACTION_REFUND)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
					.header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.SHOW_CHATAK_MERCHANT_TRANSACTION_REFUND));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testProcessRefundChatakPayException method" + e);

		}
	}

	@Test
	public void testProcessRefundException() throws ChatakPayException {
		Mockito.when(transactionService.hasAccessTo(Matchers.anyString(), Matchers.anyString(),
				Matchers.any(HttpSession.class))).thenReturn(true);
		Mockito.when(paymentService.doRefund(Matchers.any(VirtualTerminalRefundDTO.class)))
				.thenThrow(new NullPointerException());
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_CHATAK_MERCHANT_TRANSACTION_REFUND)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.SHOW_CHATAK_MERCHANT_TRANSACTION_REFUND));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testProcessRefundException method" + e);

		}
	}

	@Test
	public void testFetchTransactionPopupDetails() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_TRANSACTION_POPUP_DETAILS)
					.sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testFetchTransactionPopupDetails method" + e);

		}
	}

	@Test
	public void testFetchPartialRefundBalance() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PARTIAL_REFUND_BALANCE)
					.sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testFetchPartialRefundBalance method" + e);

		}
	}

	@Test
	public void testShowManualTransactionsHeader() {
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_MANUAL_TRANSACTIONS)
					.sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).sessionAttr("loginUserMerchantId", Long.parseLong("21432")))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testShowManualTransactionsHeader method" + e);

		}
	}

	@Test
	public void testShowManualTransactions() {
		PGMerchant parentMerchant = new PGMerchant();
		List<PGMerchant> subMerchantList = new ArrayList<>();
		List<String> merchantCodeList = new ArrayList<>();
		PGMerchant merchant = new PGMerchant();
		merchantCodeList.add("");
		subMerchantList.add(merchant);
		GetTransactionsListResponse manualTransactionsReportList = new GetTransactionsListResponse();
		manualTransactionsReportList.setTotalResultCount(Integer.parseInt("3543"));
		Mockito.when(transactionService.searchManulAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
				.thenReturn(manualTransactionsReportList);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(parentMerchant);
		Mockito.when(merchantDao.findById(Matchers.anyLong())).thenReturn(subMerchantList);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_MANUAL_TRANSACTIONS)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").sessionAttr("loginUserMerchantId", Long.parseLong("21432")));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testShowManualTransactions method" + e);

		}
	}

	@Test
	public void testShowManualTransactionsException() {
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_MANUAL_TRANSACTIONS)
					.sessionAttr(Constants.EXISTING_FEATURES, "abcd").sessionAttr("loginUserMerchantId", Long.parseLong("21432")))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testShowManualTransactionsException method" + e);

		}
	}

	@Test
	public void testManualTransactionsPaginationHeader() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_MANUAL_TRANSACTIONS_PAGINATION)
					.sessionAttr("loginUserMerchantId", Long.parseLong("21432")))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (Exception e) {
			logger.error(
					"Error:: MerchantTransactionsControllerTest:: testManualTransactionsPaginationHeader method" + e);
		}

	}

	@Test
	public void testManualTransactionsPagination() {
		PGMerchant parentMerchant = new PGMerchant();
		List<PGMerchant> subMerchantList = new ArrayList<>();
		List<String> merchantCodeList = new ArrayList<>();
		PGMerchant merchant = new PGMerchant();
		merchantCodeList.add("");
		subMerchantList.add(merchant);
		GetTransactionsListResponse manualTransactionsReportList = new GetTransactionsListResponse();
		manualTransactionsReportList.setTotalResultCount(Integer.parseInt("435"));
		Mockito.when(transactionService.searchManulAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
				.thenReturn(manualTransactionsReportList);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(parentMerchant);
		Mockito.when(merchantDao.findById(Matchers.anyLong())).thenReturn(subMerchantList);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_MANUAL_TRANSACTIONS_PAGINATION)
					.sessionAttr("loginUserMerchantId", Long.parseLong("234")).param("pageNumber", "10").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_MANUAL_TRANSACTIONS));
		} catch (Exception e) {
			logger.error("Error:: MerchantTransactionsControllerTest:: testManualTransactionsPagination method" + e);

		}
	}

	@Test
	public void testManualTransactionsPaginationElse() {
		PGMerchant parentMerchant = new PGMerchant();
		List<PGMerchant> subMerchantList = new ArrayList<>();
		List<String> merchantCodeList = new ArrayList<>();
		PGMerchant merchant = new PGMerchant();
		merchantCodeList.add("");
		subMerchantList.add(merchant);
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(parentMerchant);
		Mockito.when(merchantDao.findById(Matchers.anyLong())).thenReturn(subMerchantList);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_MANUAL_TRANSACTIONS_PAGINATION)
					.sessionAttr("loginUserMerchantId", Long.parseLong("21432")).header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_MANUAL_TRANSACTIONS));
		} catch (Exception e) {
			logger.error(
					"Error:: MerchantTransactionsControllerTest:: testManualTransactionsPaginationElse method" + e);

		}
	}

}
