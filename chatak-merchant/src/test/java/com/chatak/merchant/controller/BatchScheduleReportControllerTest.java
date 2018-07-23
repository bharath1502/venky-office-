package com.chatak.merchant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.MerchantData;
import com.chatak.merchant.service.BatchSchedularReportService;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.user.bean.DailyFundingReport;
import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportResponse;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class BatchScheduleReportControllerTest {
	
	private static final String EXISTING_FEATURE = "existingFeature";

	private Logger logger = Logger.getLogger(BatchScheduleReportController.class);

	@InjectMocks
	BatchScheduleReportController batchScheduleReportController = new BatchScheduleReportController();

	@Mock
	BatchSchedularReportService batchSchedularReportService;

	@Mock
	private MessageSource messageSource;

	@Mock
	HttpSession session;

	@Mock
	Map model;

	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	@Mock
	BindingResult bindingResult;

	@Mock
	MerchantData merchantData = new MerchantData();

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/pages/");
		viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(batchScheduleReportController).setViewResolvers(viewResolver).build();
	}

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("merchant.services.funding.report.feature.id", EXISTING_FEATURE);
		Properties.mergeProperties(properties);
	}

	@Test
	public void testShowBatchReport() {
		List<PGMerchant> subMerchantList = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		subMerchantList.add(pgMerchant);
		Mockito.when(batchSchedularReportService.getMerchantCodeAndCompanyName(Matchers.anyLong()))
				.thenReturn(merchantData);
		Mockito.when(batchSchedularReportService.findById(Matchers.anyLong())).thenReturn(subMerchantList);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_SHOW_BATCH_REPORT)
					.sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_SHOW_BATCH_REPORT));
		} catch (NumberFormatException e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testShowBatchReport ", e);

		} catch (Exception e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testShowBatchReport ", e);

		}

	}

	@Test
	public void testGetBatchReport() throws ChatakMerchantException {
		List<Transaction> transactionList = new ArrayList<>();
		Transaction transaction = new Transaction();
		transactionList.add(transaction);
		merchantData = new MerchantData();
		merchantData.setId(Long.parseLong("1234"));
		List<PGMerchant> subMerchantList = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setMerchantCode("12345");
		pgMerchant.setBusinessName("abcde");
		subMerchantList.add(pgMerchant);
		GetTransactionsListResponse transactionResponse = new GetTransactionsListResponse();
		transactionResponse.setTransactionList(transactionList);
		transactionResponse.setTotalResultCount(Integer.parseInt("123456"));
		Mockito.when(
				batchSchedularReportService.searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
				.thenReturn(transactionResponse);
		Mockito.when(batchSchedularReportService.getMerchantCodeAndCompanyName(Matchers.anyLong()))
				.thenReturn(merchantData);
		Mockito.when(batchSchedularReportService.findById(Matchers.anyLong())).thenReturn(subMerchantList);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_GET_BATCH_REPORT)
					.sessionAttr(Constants.EXISTING_FEATURES, EXISTING_FEATURE)
					.sessionAttr("loginUserMerchantId", Long.parseLong("1256")))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_SHOW_BATCH_REPORT));
		} catch (NumberFormatException e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testGetBatchReport ", e);

		} catch (Exception e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testGetBatchReport ", e);

		}

	}

	@Test
	public void testGetBatchReportExisting() throws ChatakMerchantException {
		List<Transaction> transactionList = new ArrayList<>();
		Transaction transaction = new Transaction();
		transactionList.add(transaction);
		merchantData = new MerchantData();
		merchantData.setId(Long.parseLong("1234"));
		List<PGMerchant> subMerchantList = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setMerchantCode("12345");
		pgMerchant.setBusinessName("abcde");
		subMerchantList.add(pgMerchant);
		GetTransactionsListResponse transactionResponse = new GetTransactionsListResponse();
		transactionResponse.setTransactionList(transactionList);
		transactionResponse.setTotalResultCount(Integer.parseInt("123456"));
		Mockito.when(
				batchSchedularReportService.searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
				.thenReturn(transactionResponse);
		Mockito.when(batchSchedularReportService.getMerchantCodeAndCompanyName(Matchers.anyLong()))
				.thenReturn(merchantData);
		Mockito.when(batchSchedularReportService.findById(Matchers.anyLong())).thenReturn(subMerchantList);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_GET_BATCH_REPORT)
					.sessionAttr(Constants.EXISTING_FEATURES, "1234").sessionAttr("loginUserMerchantId", Long.parseLong("1256")))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_SHOW_BATCH_REPORT));
		} catch (NumberFormatException e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testGetBatchReportExisting ", e);

		} catch (Exception e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testGetBatchReportExisting ", e);

		}

	}

	@Test
	public void testGetBatchReportElse() throws ChatakMerchantException {
		GetTransactionsListResponse transactionResponse = new GetTransactionsListResponse();
		transactionResponse.setTransactionList(null);
		transactionResponse.setTotalResultCount(Integer.parseInt("45"));
		List<Transaction> transactionList = new ArrayList<>();
		Transaction transaction = new Transaction();
		transactionList.add(transaction);
		merchantData = new MerchantData();
		merchantData.setId(Long.parseLong("1234"));
		List<PGMerchant> subMerchantList = new ArrayList<>();
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setMerchantCode("12345");
		pgMerchant.setBusinessName("abcde");
		subMerchantList.add(pgMerchant);
		transactionResponse.setTransactionList(transactionList);
		transactionResponse.setTotalResultCount(Integer.parseInt("12356"));
		Mockito.when(
				batchSchedularReportService.searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
				.thenReturn(transactionResponse);
		Mockito.when(batchSchedularReportService.getMerchantCodeAndCompanyName(Matchers.anyLong()))
				.thenReturn(merchantData);
		Mockito.when(batchSchedularReportService.findById(Matchers.anyLong())).thenReturn(subMerchantList);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_GET_BATCH_REPORT)
					.sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
					.sessionAttr("loginUserMerchantId", Long.parseLong("1256")))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_SHOW_BATCH_REPORT));
		} catch (NumberFormatException e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testGetBatchReportElse ", e);

		} catch (Exception e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testGetBatchReportElse ", e);

		}

	}

	@Test
	public void testDownloadBatchReport() throws ChatakMerchantException {
		GetBatchReportRequest batchReport = new GetBatchReportRequest();
		GetTransactionsListResponse transactionsList = new GetTransactionsListResponse();
		List<Transaction> transactionList = new ArrayList<>();
		Transaction transaction = new Transaction();
		transactionList.add(transaction);
		transactionsList.setTransactionList(transactionList);
		Mockito.when(
				batchSchedularReportService.searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
				.thenReturn(transactionsList);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATA_MERCHANT_BATCH_TRANSACTION_EXPORT)
					.sessionAttr("batchRequestObj", batchReport).param("downloadType", "XLS")
					.param("downloadAllRecords", "true"));
		} catch (Exception e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testDownloadBatchReport ", e);

		}
	}

	@Test
	public void testDownloadBatchReportElse() throws ChatakMerchantException {
		GetBatchReportRequest batchReport = new GetBatchReportRequest();
		GetTransactionsListResponse transactionsList = new GetTransactionsListResponse();
		List<Transaction> transactionList = new ArrayList<>();
		Transaction transaction = new Transaction();
		transaction.setAccountNumber(Long.parseLong("123456"));
		transaction.setRef_transaction_id(Long.parseLong("123"));
		transactionList.add(transaction);
		transactionsList.setTransactionList(transactionList);
		Mockito.when(
				batchSchedularReportService.searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
				.thenReturn(transactionsList);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATA_MERCHANT_BATCH_TRANSACTION_EXPORT)
					.sessionAttr("batchRequestObj", batchReport).param("downloadType", "PDF")
					.param("downloadAllRecords", "true"));
		} catch (Exception e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testDownloadBatchReportElse ", e);

		}

	}

	@Test
	public void testDownloadBatchReportException() throws ChatakMerchantException {
		GetBatchReportRequest batchReport = new GetBatchReportRequest();
		GetTransactionsListResponse transactionsList = new GetTransactionsListResponse();
		List<Transaction> transactionList = new ArrayList<>();
		Transaction transaction = new Transaction();
		transactionList.add(transaction);
		transactionsList.setTransactionList(transactionList);
		Mockito.when(
				batchSchedularReportService.searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
				.thenThrow(new NullPointerException());
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATA_MERCHANT_BATCH_TRANSACTION_EXPORT)
					.sessionAttr("batchRequestObj", batchReport).param("downloadType", "XLS")
					.param("downloadAllRecords", "true"));
		} catch (Exception e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testDownloadBatchReportException ", e);

		}

	}

	@Test
	public void testGetBatchReportPagination() throws ChatakMerchantException {
		GetBatchReportRequest batchReport = new GetBatchReportRequest();
		List<Transaction> transactions = new ArrayList<>();
		List<PGMerchant> subMerchantList = new ArrayList<>();
		PGMerchant merchant = new PGMerchant();
		subMerchantList.add(merchant);
		Transaction transaction = new Transaction();
		transactions.add(transaction);
		GetTransactionsListResponse transactionsList = new GetTransactionsListResponse();
		transactionsList.setTransactionList(transactions);
		transactionsList.setTotalResultCount(Integer.parseInt("56"));
		merchantData = new MerchantData();
		merchantData.setId(Long.parseLong("4567"));
		Mockito.when(batchSchedularReportService.findById(Matchers.anyLong())).thenReturn(subMerchantList);
		Mockito.when(batchSchedularReportService.getMerchantCodeAndCompanyName(Matchers.anyLong()))
				.thenReturn(merchantData);
		Mockito.when(
				batchSchedularReportService.searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
				.thenReturn(transactionsList);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHNAT_BATCH_TRANSACTION_PAGINATION)
					.sessionAttr("batchRequestObj", batchReport).sessionAttr("transactionsReport", transactionsList)
					.sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_SHOW_BATCH_REPORT));
		} catch (NumberFormatException e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testGetBatchReportPagination ", e);

		} catch (Exception e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testGetBatchReportPagination ", e);

		}
	}

	@Test
	public void testShowDailyFundingReportExistingFeature() {
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_SHOW_DAILY_FUNDING_REPORT)
					.sessionAttr(Constants.EXISTING_FEATURES, EXISTING_FEATURE))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_SHOW_DAILY_FUNDING_REPORT));
		} catch (Exception e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testShowDailyFundingReportExistingFeature ", e);

		}
	}

	@Test
	public void testShowDailyFundingReport() {
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_SHOW_DAILY_FUNDING_REPORT)
					.sessionAttr(Constants.EXISTING_FEATURES, EXISTING_FEATURE))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_SHOW_DAILY_FUNDING_REPORT));
		} catch (Exception e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testShowDailyFundingReport ", e);

		}
	}

	@Test
	public void testGetDailyFundingReport() throws ChatakMerchantException{
		GetDailyFundingReportResponse getReportResponse = new GetDailyFundingReportResponse();
		List<DailyFundingReport> dailyFundingReport = new ArrayList<>();
		DailyFundingReport fundingReport = new DailyFundingReport();
		dailyFundingReport.add(fundingReport);
		getReportResponse.setDailyFundingReport(dailyFundingReport);
		getReportResponse.setTotalResultCount(Integer.parseInt("100"));
		Mockito.when(batchSchedularReportService.searchDailyFundingReportDetails(
				Matchers.any(GetDailyFundingReportRequest.class), Matchers.anyString())).thenReturn(getReportResponse);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_GET_DAILY_FUNDING_REPORT)
					.sessionAttr(Constants.EXISTING_FEATURES, EXISTING_FEATURE))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_SHOW_DAILY_FUNDING_REPORT));
		} catch (Exception e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testGetDailyFundingReport ", e);

		}
	}

	@Test
	public void testGetDailyFundingReportExistingFeature() throws ChatakMerchantException {
		GetDailyFundingReportResponse getReportResponse = new GetDailyFundingReportResponse();
		List<DailyFundingReport> dailyFundingReport = new ArrayList<>();
		DailyFundingReport fundingReport = new DailyFundingReport();
		dailyFundingReport.add(fundingReport);
		getReportResponse.setDailyFundingReport(dailyFundingReport);
		getReportResponse.setTotalResultCount(Integer.parseInt("100"));
		Mockito.when(batchSchedularReportService.searchDailyFundingReportDetails(
				Matchers.any(GetDailyFundingReportRequest.class), Matchers.anyString())).thenReturn(getReportResponse);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_GET_DAILY_FUNDING_REPORT)
					.sessionAttr(Constants.EXISTING_FEATURES, EXISTING_FEATURE))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_SHOW_DAILY_FUNDING_REPORT));
		} catch (Exception e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testGetDailyFundingReportExistingFeature ", e);

		}
	}

	@Test
	public void testGetDailyFundingReportException() throws ChatakMerchantException {
		GetDailyFundingReportResponse getReportResponse = new GetDailyFundingReportResponse();
		List<DailyFundingReport> dailyFundingReport = new ArrayList<>();
		DailyFundingReport fundingReport = new DailyFundingReport();
		dailyFundingReport.add(fundingReport);
		getReportResponse.setDailyFundingReport(dailyFundingReport);
		getReportResponse.setTotalResultCount(Integer.parseInt("100"));
		Mockito.when(batchSchedularReportService.searchDailyFundingReportDetails(
				Matchers.any(GetDailyFundingReportRequest.class), Matchers.anyString()))
				.thenThrow(ChatakMerchantException.class);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_GET_DAILY_FUNDING_REPORT)
					.sessionAttr(Constants.EXISTING_FEATURES, EXISTING_FEATURE))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_SHOW_DAILY_FUNDING_REPORT));
		} catch (Exception e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testGetDailyFundingReportException ", e);

		}
	}

	@Test
	public void testDownloadFundingReport() throws ChatakMerchantException {
		List<DailyFundingReport> fundingReports = new ArrayList<>();
		DailyFundingReport fundingReport = new DailyFundingReport();
		fundingReports.add(fundingReport);
		GetDailyFundingReportRequest batchReport = new GetDailyFundingReportRequest();
		GetDailyFundingReportResponse getReportResponse = new GetDailyFundingReportResponse();
		getReportResponse.setDailyFundingReport(fundingReports);
		Mockito.when(batchSchedularReportService.searchDailyFundingReportDetails(
				Matchers.any(GetDailyFundingReportRequest.class), Matchers.anyString())).thenReturn(getReportResponse);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATA_MERCHANT_FUNDING_REPORT_EXPORT)
					.sessionAttr("dailyFundingRequestObj", batchReport).sessionAttr("userType", "userType")
					.param("downloadType", "XLS").param("downloadAllRecords", "true"));
		} catch (Exception e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testDownloadFundingReport ", e);

		}
	}

	@Test
	public void testDownloadFundingReportElse() throws ChatakMerchantException {
		List<DailyFundingReport> fundingReports = new ArrayList<>();
		DailyFundingReport fundingReport = new DailyFundingReport();
		fundingReports.add(fundingReport);
		GetDailyFundingReportRequest batchReport = new GetDailyFundingReportRequest();
		GetDailyFundingReportResponse getReportResponse = new GetDailyFundingReportResponse();
		getReportResponse.setDailyFundingReport(fundingReports);
		Mockito.when(batchSchedularReportService.searchDailyFundingReportDetails(
				Matchers.any(GetDailyFundingReportRequest.class), Matchers.anyString())).thenReturn(getReportResponse);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATA_MERCHANT_FUNDING_REPORT_EXPORT)
					.sessionAttr("dailyFundingRequestObj", batchReport).sessionAttr("userType", "userType")
					.param("downloadType", "PDF").param("downloadAllRecords", "true"));
		} catch (Exception e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testDownloadFundingReportElse ", e);

		}
	}

	@Test
	public void testGetFundingReportPagination() throws ChatakMerchantException {
		List<DailyFundingReport> fundingReports = new ArrayList<>();
		DailyFundingReport fundingReport = new DailyFundingReport();
		fundingReports.add(fundingReport);
		GetDailyFundingReportRequest batchReport = new GetDailyFundingReportRequest();
		GetDailyFundingReportResponse getReportResponse = new GetDailyFundingReportResponse();
		getReportResponse.setDailyFundingReport(fundingReports);
		getReportResponse.setTotalResultCount(Integer.parseInt("41234"));
		Mockito.when(batchSchedularReportService.searchDailyFundingReportDetails(
				Matchers.any(GetDailyFundingReportRequest.class), Matchers.anyString())).thenReturn(getReportResponse);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_FUNDING_REPORT_PAGINATION)
					.sessionAttr("dailyFundingRequestObj", batchReport).sessionAttr("userType", "userType")
					.param("downloadType", "PDF").param("downloadAllRecords", "true"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_SHOW_DAILY_FUNDING_REPORT));
		} catch (Exception e) {
			logger.error("ERROR:: BatchScheduleReportControllerTest::testGetFundingReportPagination ", e);

		}
	}

}
