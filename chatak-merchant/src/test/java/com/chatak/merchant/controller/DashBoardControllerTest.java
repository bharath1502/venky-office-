package com.chatak.merchant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

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
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.exception.ChatakPayException;
import com.chatak.merchant.model.GetMerchantDetailsResponse;
import com.chatak.merchant.service.AccountService;
import com.chatak.merchant.service.FundTransferService;
import com.chatak.merchant.service.RestPaymentService;
import com.chatak.merchant.service.TransactionService;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.PGParamsDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.user.bean.GetAccountHistoryListResponse;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.MerchantAccountHistory;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class DashBoardControllerTest {

	private Logger logger = Logger.getLogger(DashBoardController.class);

	@InjectMocks
	DashBoardController dashBoardController = new DashBoardController();

	@Mock
	private MessageSource messageSource;

	@Mock
	private TransactionService transactionService;

	@Mock
	private RestPaymentService paymentService;

	@Mock
	AccountService accountService;

	@Mock
	FundTransferService fundTransferService;

	@Mock
	private PGParamsDao paramsDao;

	@Mock
	private MerchantDao merchantDao;

	@Mock
	MerchantProfileDao merchantProfileDao;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/pages/");
		viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(dashBoardController).setViewResolvers(viewResolver).build();
	}

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("merchant.service.dashboard.feature.id", "existingFeature");
		Properties.mergeProperties(properties);
	}

	@Test
	public void testShowDashBoard() throws ChatakMerchantException, ChatakPayException {
		GetMerchantDetailsResponse merchantDetailsResponse = new GetMerchantDetailsResponse();
		PGAccount accountDetails = new PGAccount();
		merchantDetailsResponse.setMerchantId("5455");
		GetTransactionsListResponse manualTransactionsReportList = new GetTransactionsListResponse();
		List<PGMerchant> subMerchantList = new ArrayList<>();
		List<AccountTransactionDTO> accountTransactionDTOs = new ArrayList<>();
		AccountTransactionDTO accountTransactionDTO = new AccountTransactionDTO();
		accountTransactionDTOs.add(accountTransactionDTO);
		PGMerchant merchant = new PGMerchant();
		subMerchantList.add(merchant);
		merchant.setMerchantCode("5464");
		manualTransactionsReportList.setAccountTransactionList(accountTransactionDTOs);
		accountTransactionDTO.setMerchantCode("9080");
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(merchant);
		Mockito.when(merchantDao.findById(Matchers.anyLong())).thenReturn(subMerchantList);
		Mockito.when(transactionService.searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
				.thenReturn(manualTransactionsReportList);
		Mockito.when(accountService.getAccountDetailsByEntityId(Matchers.anyString())).thenReturn(accountDetails);
		Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
				.thenReturn(merchantDetailsResponse);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_DASH_BOARD)
					.sessionAttr("existingFeatures", "existingFeatures")
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")));
		} catch (NumberFormatException e) {
			logger.error("ERROR:: DashBoardControllerTest::testShowDashBoard ", e);

		} catch (Exception e) {
			logger.error("ERROR:: DashBoardControllerTest::testShowDashBoard ", e);
		}

	}

	@Test
	public void testShowDashBoardElse() throws ChatakPayException, ChatakMerchantException {
		GetMerchantDetailsResponse merchantDetailsResponse = new GetMerchantDetailsResponse();
		PGAccount accountDetails = new PGAccount();
		GetTransactionsListResponse manualTransactionsReportList = new GetTransactionsListResponse();
		List<PGMerchant> subMerchantList = new ArrayList<>();
		List<AccountTransactionDTO> accountTransactionDTOs = new ArrayList<>();
		AccountTransactionDTO accountTransactionDTO = new AccountTransactionDTO();
		accountTransactionDTOs.add(accountTransactionDTO);
		PGMerchant merchant = new PGMerchant();
		subMerchantList.add(merchant);
		merchant.setMerchantCode("5464");
		accountTransactionDTO.setMerchantCode("9080");
		Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(merchant);
		Mockito.when(merchantDao.findById(Matchers.anyLong())).thenReturn(subMerchantList);
		Mockito.when(transactionService.searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
				.thenReturn(manualTransactionsReportList);
		Mockito.when(accountService.getAccountDetailsByEntityId(Matchers.anyString())).thenReturn(accountDetails);
		Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
				.thenReturn(merchantDetailsResponse);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_DASH_BOARD)
					.sessionAttr("existingFeatures", "existingFeatures")
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")));
		} catch (NumberFormatException e) {
			logger.error("ERROR:: DashBoardControllerTest::testShowDashBoardElse ", e);

		} catch (Exception e) {
			logger.error("ERROR:: DashBoardControllerTest::testShowDashBoardElse ", e);

		}

	}

	@Test
	public void testShowDashBoardException() {
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_DASH_BOARD)
					.sessionAttr("existingFeatures", "existingFeatures")
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (NumberFormatException e) {
			logger.error("ERROR:: DashBoardControllerTest::testShowDashBoardException ", e);
		} catch (Exception e) {
			logger.error("ERROR:: DashBoardControllerTest::testShowDashBoardException ", e);

		}

	}

	@Test
	public void testGetAccountHistory() throws ChatakMerchantException {
		GetAccountHistoryListResponse historyListResponse = new GetAccountHistoryListResponse();
		List<MerchantAccountHistory> accountHistoryList = new ArrayList<>();
		MerchantAccountHistory merchantAccountHistory = new MerchantAccountHistory();
		accountHistoryList.add(merchantAccountHistory);
		merchantAccountHistory.setPageIndex(1);
		historyListResponse.setAccountHistoryList(accountHistoryList);
		historyListResponse.setTotalResultCount(Integer.parseInt("13"));
		Mockito.when(transactionService.getAccountHistory(Matchers.anyString(), Matchers.anyInt()))
				.thenReturn(historyListResponse);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_HISTORY)
					.sessionAttr("accountNumber", "accountNumber")
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")));
		} catch (NumberFormatException e) {
			logger.error("ERROR:: DashBoardControllerTest::testGetAccountHistory ", e);

		} catch (Exception e) {
			logger.error("ERROR:: DashBoardControllerTest::testGetAccountHistory ", e);

		}

	}

	@Test
	public void testGetAccountHistoryElse() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_HISTORY)
					.sessionAttr("accountNumber", "accountNumber")
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_HISTORY));
		} catch (NumberFormatException e) {
			logger.error("ERROR:: DashBoardControllerTest::testGetAccountHistoryElse ", e);

		} catch (Exception e) {
			logger.error("ERROR:: DashBoardControllerTest::testGetAccountHistoryElse ", e);

		}
	}

	@Test
	public void testGetAccountHistoryException() throws ChatakMerchantException {
		Mockito.when(transactionService.getAccountHistory(Matchers.anyString(), Matchers.anyInt()))
				.thenThrow(new NullPointerException());
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_HISTORY)
					.sessionAttr("accountNumber", "accountNumber")
					.sessionAttr("loginUserMerchantId", Long.parseLong("23")));
		} catch (NumberFormatException e) {
			logger.error("ERROR:: DashBoardControllerTest::testGetAccountHistoryException ", e);

		} catch (Exception e) {
			logger.error("ERROR:: DashBoardControllerTest::testGetAccountHistoryException ", e);

		}

	}

	@Test
	public void testGetPaginationList() throws ChatakMerchantException {
		GetAccountHistoryListResponse historyListResponse = new GetAccountHistoryListResponse();
		List<MerchantAccountHistory> accountHistoryList = new ArrayList<>();
		MerchantAccountHistory merchantAccountHistory = new MerchantAccountHistory();
		accountHistoryList.add(merchantAccountHistory);
		historyListResponse.setAccountHistoryList(accountHistoryList);
		Mockito.when(transactionService.getAccountHistory(Matchers.anyString(), Matchers.anyInt()))
				.thenReturn(historyListResponse);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_HISTORY_PAGINATION)
					.sessionAttr("accountNumber", "123456789").param("pageNumber", "21"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_HISTORY));
		} catch (Exception e) {
			logger.error("ERROR:: DashBoardControllerTest::testGetPaginationList ", e);

		}
	}

	@Test
	public void testDownloadTransactionReportHeaderIsNull() throws ChatakMerchantException {
		GetAccountHistoryListResponse transactionResponse = new GetAccountHistoryListResponse();
		Mockito.when(transactionService.getAccountHistory(Matchers.anyString(), Matchers.anyInt()))
				.thenReturn(transactionResponse);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_HISTORY_EXPORT)
					.sessionAttr("accountNumber", "accountNumber"))
					.andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (Exception e) {
			logger.error("ERROR:: DashBoardControllerTest::testDownloadTransactionReportHeaderIsNull ", e);

		}
	}

	@Test
	public void testDownloadTransactionReport() throws ChatakMerchantException {
		GetAccountHistoryListResponse transactionResponse = new GetAccountHistoryListResponse();
		MerchantAccountHistory transaction = new MerchantAccountHistory();
		transaction.setUpdatedDateString("abcd");
		Mockito.when(transactionService.getAccountHistory(Matchers.anyString(), Matchers.anyInt()))
				.thenReturn(transactionResponse);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_HISTORY_EXPORT)
					.sessionAttr("accountNumber", "12431234").header("referer", "referer")
					.param("downLoadPageNumber", "45").param("downloadType", "PDF"));
		} catch (Exception e) {
			logger.error("ERROR:: DashBoardControllerTest::testDownloadTransactionReport ", e);

		}
	}

	@Test
	public void testDownloadTransactionReportXLS() throws ChatakMerchantException {
		GetAccountHistoryListResponse transactionResponse = new GetAccountHistoryListResponse();
		MerchantAccountHistory transaction = new MerchantAccountHistory();
		transaction.setUpdatedDateString("abcd");
		Mockito.when(transactionService.getAccountHistory(Matchers.anyString(), Matchers.anyInt()))
				.thenReturn(transactionResponse);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_HISTORY_EXPORT)
					.sessionAttr("accountNumber", "12431234").header("referer", "referer")
					.param("downLoadPageNumber", "45").param("downloadType", "XLS"));
		} catch (Exception e) {
			logger.error("ERROR:: DashBoardControllerTest::testDownloadTransactionReportXLS ", e);

		}
	}

	@Test
	public void testDownloadTransactionReportException() throws ChatakMerchantException {
		Mockito.when(transactionService.getAccountHistory(Matchers.anyString(), Matchers.anyInt()))
				.thenThrow(new NullPointerException());
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_HISTORY_EXPORT)
					.sessionAttr("accountNumber", "accountNumber").header("referer", "referer")
					.param("downLoadPageNumber", "45"));
		} catch (Exception e) {
			logger.error("ERROR:: DashBoardControllerTest::testDownloadTransactionReportException ", e);

		}
	}

}
