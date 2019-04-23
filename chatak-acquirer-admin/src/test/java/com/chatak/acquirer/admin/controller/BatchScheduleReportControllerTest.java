package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.acquirer.admin.constants.TestConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.BatchSchedularService;
import com.chatak.acquirer.admin.service.MerchantService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.SettlementReportService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.DailyFundingReport;
import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportResponse;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;

@RunWith(MockitoJUnitRunner.class)
public class BatchScheduleReportControllerTest {

  private static Logger logger = Logger.getLogger(BatchScheduleReportControllerTest.class);

  @InjectMocks
  BatchScheduleReportController batchScheduleReportController = new BatchScheduleReportController();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  private Response responseval;

  @Mock
  private MerchantUpdateService merchantUpdateService;

  @Mock
  private BindingResult bindingResult;

  @Mock
  private List<Option> optionList;

  @Mock
  private Map<String, String> merchantMap;

  @Mock
  private SettlementReportService settlementReportService;

  @Mock
  private GetTransactionsListResponse transactionResponse;

  @Mock
  private List<Transaction> transactionList;

  @Mock
  private Transaction transaction;

  @Mock
  private GetBatchReportRequest batchReport;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  private ChatakAdminException chatakAdminException;

  @Mock
  private MerchantService merchantService;

  @Mock
  private GetDailyFundingReportRequest dailyFundingReport;

  @Mock
  private List<DailyFundingReport> dailyFundingReportList;

  @Mock
  private GetDailyFundingReportResponse getReportResponse;

  @Mock
  private BatchSchedularService batchSchedularService;

  @Mock
  private GetTransactionsListRequest getTransactionList;

  @Mock
  private GetDailyFundingReportRequest batchFundingReport;

  @Mock
  private MessageSource messageSource;

  private Locale locale;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(batchScheduleReportController)
        .setViewResolvers(viewResolver).build();
    optionList = new ArrayList<>();
    transactionList = new ArrayList<>();
    transaction = new Transaction();
  }

  @Test
  public void testShowBatchReport() {
    merchantMap = new HashMap<String, String>();
    merchantMap.put(TestConstants.ACC_ACTIVE, "Active");
    Mockito.when(merchantUpdateService.getMerchantCodeAndCompanyName(Matchers.anyString()))
        .thenReturn(merchantMap);
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_BATCH_REPORT)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SHOW_BATCH_REPORT));
    } catch (Exception e) {
      logger.error(
          "BatchScheduleReportControllerTest | testShowBatchReport | Exception " + e.getMessage(),
          e);
    }
  }

  @Test
  public void testGetBatchReport() throws ChatakAdminException {
    transactionResponse = new GetTransactionsListResponse();
    transactionList.add(transaction);
    transactionResponse.setTransactionList(transactionList);
    transactionResponse.setTotalResultCount(TestConstants.TEN);
    Mockito
        .when(settlementReportService
            .searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
        .thenReturn(transactionResponse);
    Mockito.when(merchantService.getSubMerchantCodeAndCompanyName(Matchers.anyString()))
        .thenReturn(responseval);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_GET_BATCH_REPORT)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param("batchReport", "batchReport"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SHOW_BATCH_REPORT));
    } catch (Exception e) {
      logger.error(
          "BatchScheduleReportControllerTest | testGetBatchReport | Exception " + e.getMessage(),
          e);
    }
  }

  @Test
  public void testGetBatchReportElse() throws ChatakAdminException {
    transactionResponse = new GetTransactionsListResponse();
    transactionResponse.setTransactionList(transactionList);
    transactionResponse.setTotalResultCount(TestConstants.TEN);
    Mockito
        .when(settlementReportService
            .searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
        .thenReturn(transactionResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_GET_BATCH_REPORT)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SHOW_BATCH_REPORT));
    } catch (Exception e) {
      logger.error("BatchScheduleReportControllerTest | testGetBatchReportElse | Exception "
          + e.getMessage(), e);
    }
  }

  @Test
  public void testShowBatchReportException() throws ChatakAdminException {
    Mockito
        .when(settlementReportService
            .searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
        .thenThrow(chatakAdminException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_GET_BATCH_REPORT)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SHOW_BATCH_REPORT));
    } catch (Exception e) {
      logger.error("BatchScheduleReportControllerTest | testShowBatchReportException | Exception "
          + e.getMessage(), e);
    }
  }

  @Test
  public void testShowDailyFundingReport() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_DAILY_FUNDING_REPORT)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SHOW_DAILY_FUNDING_REPORT));
    } catch (Exception e) {
      logger.error("BatchScheduleReportControllerTest | testShowDailyFundingReport | Exception "
          + e.getMessage(), e);
    }
  }

  @Test
  public void testGetDailyFundingReport() throws ChatakAdminException {
    getReportResponse = new GetDailyFundingReportResponse();
    getReportResponse.setDailyFundingReport(dailyFundingReportList);
    getReportResponse.setTotalResultCount(TestConstants.TEN);
    Mockito
        .when(batchSchedularService
            .searchDailyFundingReportDetails(Matchers.any(GetDailyFundingReportRequest.class)))
        .thenReturn(getReportResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_GET_DAILY_FUNDING_REPORT)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.DAILY_FUNDING_REQUEST_OBJECT, dailyFundingReport))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SHOW_DAILY_FUNDING_REPORT));
    } catch (Exception e) {
      logger.error("BatchScheduleReportControllerTest | testGetDailyFundingReport | Exception "
          + e.getMessage(), e);
    }
  }

  @Test
  public void testGetDailyFundingReportException() throws ChatakAdminException {
    Mockito
        .when(batchSchedularService
            .searchDailyFundingReportDetails(Matchers.any(GetDailyFundingReportRequest.class)))
        .thenThrow(chatakAdminException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_GET_DAILY_FUNDING_REPORT)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.DAILY_FUNDING_REQUEST_OBJECT, dailyFundingReport))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SHOW_DAILY_FUNDING_REPORT));
    } catch (Exception e) {
      logger.error(
          "BatchScheduleReportControllerTest | testGetDailyFundingReportException | Exception "
              + e.getMessage(),
          e);
    }
  }

  @Test
  public void testGetSubMerchantsByMerchantId() throws ChatakAdminException {
    Mockito.when(merchantService.getSubMerchantCodeAndCompanyName(Matchers.anyString()))
        .thenReturn(responseval);
    try {
      mockMvc.perform(get("/" + URLMappingConstants.GET_SUB_MERCHANTS_BY_MERCHANT_CODE));
    } catch (Exception e) {
      logger
          .error("BatchScheduleReportControllerTest | testGetSubMerchantsByMerchantId | Exception "
              + e.getMessage(), e);
    }
  }

  @Test
  public void testGetBatchReportPaginationIf() throws ChatakAdminException {
    transactionList.add(transaction);
    transactionResponse = new GetTransactionsListResponse();
    transactionResponse.setTransactionList(transactionList);
    transactionResponse.setTotalResultCount(TestConstants.TEN);
    Mockito
        .when(settlementReportService
            .searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
        .thenReturn(transactionResponse);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_BATCH_TRANSACTION_PAGINATION)
          .sessionAttr(TestConstants.BATCH_REQUEST_OBJECT, batchReport)
          .param(TestConstants.PAGE_NUMBER, "2"));
    } catch (Exception e) {
      logger.error("BatchScheduleReportControllerTest | testGetBatchReportPaginationIf | Exception "
          + e.getMessage(), e);
    }
  }

  @Test
  public void testGetBatchReportPagination() throws ChatakAdminException {
    transactionResponse = new GetTransactionsListResponse();
    transactionResponse.setTransactionList(transactionList);
    transactionResponse.setTotalResultCount(TestConstants.TEN);
    Mockito
        .when(settlementReportService
            .searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
        .thenReturn(transactionResponse);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_BATCH_TRANSACTION_PAGINATION)
          .sessionAttr(TestConstants.BATCH_REQUEST_OBJECT, batchReport));
    } catch (Exception e) {
      logger.error("BatchScheduleReportControllerTest | testGetBatchReportPagination | Exception "
          + e.getMessage(), e);
    }
  }

  @Test
  public void testGetBatchReportPaginationException() throws ChatakAdminException {
    LocaleContextHolder.setLocale(locale);
    Mockito.when(messageSource.getMessage(TestConstants.CHATAK_GENERAL_ERROR, null,
        LocaleContextHolder.getLocale())).thenReturn("abc");
    Mockito
        .when(settlementReportService
            .searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
        .thenThrow(nullPointerException);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_BATCH_TRANSACTION_PAGINATION)
          .sessionAttr(TestConstants.BATCH_REQUEST_OBJECT, batchReport));
    } catch (Exception e) {
      logger.error(
          "BatchScheduleReportControllerTest | testGetBatchReportPaginationException | Exception "
              + e.getMessage(),
          e);
    }
  }

  @Test
  public void testDownloadBatchReportXLS() throws ChatakAdminException {
    transactionResponse = new GetTransactionsListResponse();
    transactionList.add(transaction);
    transactionResponse.setTransactionList(transactionList);
    Mockito
        .when(settlementReportService
            .searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
        .thenReturn(transactionResponse);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATA_ADMIN_BATCH_TRANSACTION_EXPORT)
          .sessionAttr(TestConstants.BATCH_REQUEST_OBJECT, batchReport)
          .param("downLoadPageNumber", TestConstants.ONE.toString())
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()).param("downloadAllRecords", "true")
          .param(TestConstants.DOWNLOAD_TYPE, "XLS"));
    } catch (Exception e) {
      logger.error("BatchScheduleReportControllerTest | testDownloadBatchReportXLS | Exception "
          + e.getMessage(), e);
    }
  }

  @Test
  public void testDownloadBatchReportException() throws ChatakAdminException {
    Mockito
        .when(settlementReportService
            .searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
        .thenThrow(nullPointerException);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATA_ADMIN_BATCH_TRANSACTION_EXPORT)
          .sessionAttr(TestConstants.BATCH_REQUEST_OBJECT, batchReport)
          .param("downLoadPageNumber", TestConstants.ONE.toString())
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()).param("downloadAllRecords", "true")
          .param(TestConstants.DOWNLOAD_TYPE, "XLS"));
    } catch (Exception e) {
      logger
          .error("BatchScheduleReportControllerTest | testDownloadBatchReportException | Exception "
              + e.getMessage(), e);
    }
  }

  @Test
  public void testGetFundingReportPagination() throws ChatakAdminException {
    getReportResponse = new GetDailyFundingReportResponse();
    getReportResponse.setDailyFundingReport(dailyFundingReportList);
    getReportResponse.setTotalResultCount(TestConstants.TEN);
    Mockito
        .when(batchSchedularService
            .searchDailyFundingReportDetails(Matchers.any(GetDailyFundingReportRequest.class)))
        .thenReturn(getReportResponse);
    Mockito
        .when(settlementReportService
            .searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
        .thenReturn(transactionResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_FUNDING_REPORT_PAGINATION)
              .sessionAttr(TestConstants.DAILY_FUNDING_REQUEST_OBJECT, batchFundingReport)
              .param(TestConstants.PAGE_NUMBER, TestConstants.ONE.toString())
              .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SHOW_DAILY_FUNDING_REPORT));
    } catch (Exception e) {
      logger.error("BatchScheduleReportControllerTest | testGetFundingReportPagination | Exception "
          + e.getMessage(), e);
    }
  }

  @Test
  public void testGetFundingReportPaginationException() throws ChatakAdminException {
    Mockito
        .when(batchSchedularService
            .searchDailyFundingReportDetails(Matchers.any(GetDailyFundingReportRequest.class)))
        .thenThrow(nullPointerException);
    Mockito
        .when(settlementReportService
            .searchBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
        .thenReturn(transactionResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_FUNDING_REPORT_PAGINATION)
              .sessionAttr(TestConstants.DAILY_FUNDING_REQUEST_OBJECT, batchFundingReport)
              .param(TestConstants.PAGE_NUMBER, TestConstants.ONE.toString())
              .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SHOW_DAILY_FUNDING_REPORT));
    } catch (Exception e) {
      logger.error(
          "BatchScheduleReportControllerTest | testGetFundingReportPaginationException | Exception "
              + e.getMessage(),
          e);
    }
  }

  @Test
  public void testDownloadFundingReport() throws ChatakAdminException {
    getReportResponse = new GetDailyFundingReportResponse();
    getReportResponse.setDailyFundingReport(dailyFundingReportList);
    Mockito
        .when(batchSchedularService
            .searchDailyFundingReportDetails(Matchers.any(GetDailyFundingReportRequest.class)))
        .thenReturn(getReportResponse);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATA_ADMIN_FUNDING_REPORT_EXPORT)
          .sessionAttr(TestConstants.DAILY_FUNDING_REQUEST_OBJECT, batchFundingReport)
          .param("downLoadPageNumber", TestConstants.ONE.toString())
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()).param("downloadAllRecords", "true")
          .param(TestConstants.DOWNLOAD_TYPE, TestConstants.XLS_FILE_FORMAT));
    } catch (Exception e) {
      logger.error("BatchScheduleReportControllerTest | testDownloadFundingReport | Exception "
          + e.getMessage(), e);
    }
  }

  @Test
  public void testDownloadFundingReportException() throws ChatakAdminException {
    getReportResponse = new GetDailyFundingReportResponse();
    getReportResponse.setDailyFundingReport(dailyFundingReportList);
    Mockito
        .when(batchSchedularService
            .searchDailyFundingReportDetails(Matchers.any(GetDailyFundingReportRequest.class)))
        .thenThrow(nullPointerException);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATA_ADMIN_FUNDING_REPORT_EXPORT)
          .sessionAttr(TestConstants.DAILY_FUNDING_REQUEST_OBJECT, batchFundingReport)
          .param("downLoadPageNumber", TestConstants.ONE.toString())
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()).param("downloadAllRecords", "true")
          .param(TestConstants.DOWNLOAD_TYPE, TestConstants.XLS_FILE_FORMAT));
    } catch (Exception e) {
      logger.error(
          "BatchScheduleReportControllerTest | testDownloadFundingReportException | Exception "
              + e.getMessage(),
          e);
    }
  }

  @Test
  public void testProcessManualFundingReport() throws ChatakAdminException {
    Mockito.when(batchSchedularService.manualFundingReport()).thenReturn(responseval);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATA_ADMIN_MANUAL_FUNDING_REPORT));
    } catch (Exception e) {
      logger.error("BatchScheduleReportControllerTest | testProcessManualFundingReport | Exception "
          + e.getMessage(), e);
    }
  }

  @Test
  public void testGetTransactionHistory() throws ChatakAdminException {
    transactionList.add(transaction);
    transactionResponse = new GetTransactionsListResponse();
    transactionResponse.setTransactionList(transactionList);
    transactionResponse.setTotalResultCount(TestConstants.TEN);
    Mockito
        .when(batchSchedularService
            .getMerchantHistoryOnId(Matchers.any(GetTransactionsListRequest.class)))
        .thenReturn(transactionResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TXN_HISTORY)
              .param("batchID", TestConstants.TEN.toString()).param(TestConstants.MERCHANT_CODE, "M20")
              .param("downloadAllRecords", "true"))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_TXN_HISTORY));
    } catch (Exception e) {
      logger.error("BatchScheduleReportControllerTest | testGetTransactionHistory | Exception "
          + e.getMessage(), e);
    }
  }

  @Test
  public void testGetTransactionHistoryElse() throws ChatakAdminException {
    transactionResponse = new GetTransactionsListResponse();
    transactionResponse.setTransactionList(transactionList);
    transactionResponse.setTotalResultCount(TestConstants.TEN);
    Mockito
        .when(batchSchedularService
            .getMerchantHistoryOnId(Matchers.any(GetTransactionsListRequest.class)))
        .thenReturn(transactionResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TXN_HISTORY)
              .param("batchID", TestConstants.TEN.toString()).param(TestConstants.MERCHANT_CODE, "M20")
              .param("downloadAllRecords", "true"))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_TXN_HISTORY));
    } catch (Exception e) {
      logger.error("BatchScheduleReportControllerTest | testGetTransactionHistoryElse | Exception "
          + e.getMessage(), e);
    }
  }

  @Test
  public void testGetTransactionHistoryException() throws ChatakAdminException {
    Mockito
        .when(batchSchedularService
            .getMerchantHistoryOnId(Matchers.any(GetTransactionsListRequest.class)))
        .thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TXN_HISTORY)
              .param("batchID", TestConstants.TEN.toString()).param(TestConstants.MERCHANT_CODE, "M20")
              .param("downloadAllRecords", "true"))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "BatchScheduleReportControllerTest | testGetTransactionHistoryException | Exception "
              + e.getMessage(),
          e);
    }
  }

  @Test
  public void testDownloadTxnsHistoryReport() throws ChatakAdminException {
    transactionResponse = new GetTransactionsListResponse();
    transactionList.add(transaction);
    transactionResponse.setTransactionList(transactionList);
    transactionResponse.setTotalResultCount(TestConstants.TEN);
    Mockito
        .when(batchSchedularService
            .getMerchantHistoryOnId(Matchers.any(GetTransactionsListRequest.class)))
        .thenReturn(transactionResponse);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TXN_HISTORY_REPORT)
          .param("batchID", TestConstants.TEN.toString())
          .param("downLoadPageNumber", TestConstants.ONE.toString())
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()).param(TestConstants.MERCHANT_CODE, "M20")
          .param("downloadAllRecords", "true").param(TestConstants.DOWNLOAD_TYPE, TestConstants.XLS_FILE_FORMAT)
          .sessionAttr("transactionHistory", getTransactionList));
    } catch (Exception e) {
      logger.error("BatchScheduleReportControllerTest | testDownloadTxnsHistoryReport | Exception "
          + e.getMessage(), e);
    }
  }

  @Test
  public void testDownloadTxnsHistoryReportException() throws ChatakAdminException {
    Mockito
        .when(batchSchedularService
            .getMerchantHistoryOnId(Matchers.any(GetTransactionsListRequest.class)))
        .thenThrow(nullPointerException);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TXN_HISTORY_REPORT)
          .param("batchID", TestConstants.TEN.toString())
          .param("downLoadPageNumber", TestConstants.ONE.toString())
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()).param(TestConstants.MERCHANT_CODE, "M20")
          .param("downloadAllRecords", "true").param(TestConstants.DOWNLOAD_TYPE, TestConstants.XLS_FILE_FORMAT)
          .sessionAttr("transactionHistory", getTransactionList));
    } catch (Exception e) {
      logger.error(
          "BatchScheduleReportControllerTest | testDownloadTxnsHistoryReportException | Exception "
              + e.getMessage(),
          e);
    }
  }

  @Test
  public void testGetTransactionHistoryPagination() throws ChatakAdminException {
    transactionResponse = new GetTransactionsListResponse();
    transactionList.add(transaction);
    transactionResponse.setTransactionList(transactionList);
    transactionResponse.setTotalResultCount(TestConstants.TEN);
    Mockito
        .when(batchSchedularService
            .getMerchantHistoryOnId(Matchers.any(GetTransactionsListRequest.class)))
        .thenReturn(transactionResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_TRANSACTION_HISTORY_PAGINATION)
              .param(TestConstants.PAGE_NUMBER, TestConstants.ONE.toString())
              .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString())
              .sessionAttr("transactionHistory", getTransactionList))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_TXN_HISTORY));
    } catch (Exception e) {
      logger.error(
          "BatchScheduleReportControllerTest | testGetTransactionHistoryPagination | Exception "
              + e.getMessage(),
          e);
    }
  }

  @Test
  public void testGetTransactionHistoryPaginationException() throws ChatakAdminException {
    Mockito
        .when(batchSchedularService
            .getMerchantHistoryOnId(Matchers.any(GetTransactionsListRequest.class)))
        .thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_TRANSACTION_HISTORY_PAGINATION)
              .param(TestConstants.PAGE_NUMBER, TestConstants.ONE.toString())
              .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString())
              .sessionAttr("transactionHistory", getTransactionList))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_TXN_HISTORY));
    } catch (Exception e) {
      logger.error(
          "BatchScheduleReportControllerTest | testGetTransactionHistoryPaginationException | Exception "
              + e.getMessage(),
          e);
    }
  }
}
