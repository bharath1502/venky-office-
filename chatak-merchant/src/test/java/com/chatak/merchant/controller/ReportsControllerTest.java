package com.chatak.merchant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.chatak.merchant.model.GetMerchantDetailsResponse;
import com.chatak.merchant.service.FundTransferService;
import com.chatak.merchant.service.MerchantInfoService;
import com.chatak.merchant.service.RestPaymentService;
import com.chatak.merchant.service.TransactionService;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.model.EFTDetails;
import com.chatak.pg.model.GetTransactionIdsListResponse;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.GetTransferListRequest;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class ReportsControllerTest {

  private static Logger logger = Logger.getLogger(VirtualTerminalControllerTest.class);

  @InjectMocks
  ReportsController reportsController = new ReportsController();

  @Mock
  MessageSource messageSource;

  @Mock
  private TransactionService transactionService;

  @Mock
  private FundTransferService fundTransfersService;

  @Mock
  private MerchantDao merchantDao;

  private MockMvc mockMvc;

  @Mock
  private RestPaymentService paymentService;

  @Mock
  MerchantInfoService merchantInfoService;

  @Mock
  MerchantProfileDao merchantProfileDao;

  @Mock
  PGMerchant pgMerchant;

  @Mock
  Map<String, Long> tempMap;

  @Mock
  List<ReportsDTO> revenueGeneratedReportList;

  @Mock
  ReportsDTO reportsDTO;

  @Mock
  List<EFTDetails> searchDetails;

  @Mock
  List<ReportsDTO> eftTransferDownloadList;

  @Mock
  GetTransactionsListRequest listRequest;

  @Mock
  GetTransactionIdsListResponse gettransactionIdsListResponse;

  @Mock
  GetTransactionsListResponse transactionResponse;

  @Mock
  List<AccountTransactionDTO> processingTxnList;

  @Mock
  GetMerchantDetailsResponse merchantDetailsResponse;

  @Mock
  List<AccountTransactionDTO> executedTxnsList;

  @Mock
  List<PGMerchant> subMerchantList;



  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc =
        MockMvcBuilders.standaloneSetup(reportsController).setViewResolvers(viewResolver).build();
  }

  @Before
  public void pro() {
    java.util.Properties properties = new java.util.Properties();
    properties.setProperty("merchant.services.reports.feature.id", "exist");
    properties.setProperty("merchant.services.reports.feature.id", "notExist");
    properties.setProperty("merchant.services.reports.feature.id", "exist");
    properties.setProperty("merchant.services.reports.merchant.transaction.revenue.feature.id",
        "exist");
    Properties.mergeProperties(properties);
  }

  @Test
  public void testShowGlobalRevenueGeneratedReportsDatesExistingFeature() {
    try {
      Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong())).thenReturn(pgMerchant);
      mockMvc
          .perform(get("/" + URLMappingConstants.GLOBAL_REVENUE_GENERATED_REPORTS_DATES)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_REVENUE_GENERATED_REPORT));
    } catch (Exception e) {
      logger.error(
          "ERROR :: ReportsControllerTest :: testShowGlobalRevenueGeneratedReportsDatesExistingFeature:",
          e);
    }
  }

  @Test
  public void testShowGlobalRevenueGeneratedReportsDates() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.GLOBAL_REVENUE_GENERATED_REPORTS_DATES)
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testShowGlobalRevenueGeneratedReportsDates:",
          e);
    }
  }

  @Test
  public void testShowGlobalRevenueGeneratedReportExistingFeature() {
    reportsDTO();
    pgMerchant.setMerchantCode("merchantCode");
    try {
      Mockito
          .when(transactionService
              .getAllExecutedAccTransFeeOnDate(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(revenueGeneratedReportList);
      Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong())).thenReturn(pgMerchant);
      Mockito.when(merchantInfoService.getParentMerchantCode(Matchers.anyString()))
          .thenReturn(pgMerchant.toString());
      mockMvc
          .perform(get("/" + URLMappingConstants.GLOBAL_REVENUE_GENERATED_REPORT)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_REVENUE_GENERATED_REPORT));
    } catch (Exception e) {
      logger.error(
          "ERROR :: ReportsControllerTest :: testShowGlobalRevenueGeneratedReportExistingFeature:",
          e);
    }
  }


  @Test
  public void testShowGlobalRevenueGeneratedReportRevenue() {
    pgMerchant.setMerchantCode("merchantCode");
    reportsDTO();
    try {
      Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong())).thenReturn(pgMerchant);
      Mockito.when(merchantInfoService.getParentMerchantCode(Matchers.anyString()))
          .thenReturn(Matchers.anyString());
      Mockito
          .when(transactionService
              .getAllExecutedAccTransFeeOnDate(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(revenueGeneratedReportList);
      mockMvc
          .perform(get("/" + URLMappingConstants.GLOBAL_REVENUE_GENERATED_REPORT)
              .param("revenueType", "pos").sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_REVENUE_GENERATED_REPORT));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testShowGlobalRevenueGeneratedReportRevenue:",
          e);
    }
  }

  @Test
  public void testShowGlobalRevenueGeneratedReportList() {
    pgMerchant = new PGMerchant();
    pgMerchant.setMerchantCode("merchantCode");
    try {
      Mockito
          .when(transactionService
              .getAllExecutedAccTransFeeOnDate(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(revenueGeneratedReportList);
      Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong())).thenReturn(pgMerchant);
      Mockito.when(merchantInfoService.getParentMerchantCode(Matchers.anyString()))
          .thenReturn(pgMerchant.toString());
      mockMvc
      
          .perform(get("/" + URLMappingConstants.GLOBAL_REVENUE_GENERATED_REPORT)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_REVENUE_GENERATED_REPORT));
    } catch (Exception e) {
      logger.error(
          "ERROR :: ReportsControllerTest :: testShowGlobalRevenueGeneratedReportsDatesExistingFeature:",
          e);
    }
  }

  private void reportsDTO() {
    revenueGeneratedReportList = new ArrayList<>();
    reportsDTO = new ReportsDTO();
    reportsDTO.setAccountNumber(Long.valueOf("123456"));
    reportsDTO.setChatakFee("123");
    reportsDTO.setParentMerchantId("0202");
    reportsDTO.setTotalTxnAmount("653");
    reportsDTO.setFee("22");
    revenueGeneratedReportList.add(reportsDTO);
    pgMerchant = new PGMerchant();
    pgMerchant.setLocalCurrency("localCurrency");
  }

  @Test
  public void testShowGlobalRevenueGeneratedReport() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.GLOBAL_REVENUE_GENERATED_REPORT)
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testShowGlobalRevenueGeneratedReport:", e);
    }
  }

  @Test
  public void testShowGlobalRevenueGeneratedReportElse() {
    pgMerchant.setMerchantCode("merchantCode456");
    pgMerchant.setAddress1("address1");
    reportsDTO();
    try {
      Mockito.when(merchantInfoService.getParentMerchantCode(Matchers.anyString()))
          .thenReturn(Matchers.anyString());
      Mockito
          .when(transactionService
              .getAllExecutedAccTransFeeOnDate(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(revenueGeneratedReportList);
      Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong())).thenReturn(pgMerchant);

      mockMvc
          .perform(get("/" + URLMappingConstants.GLOBAL_REVENUE_GENERATED_REPORT)
              .param("merchantCode", "merchantCode123").sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_REVENUE_GENERATED_REPORT));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testShowGlobalRevenueGeneratedReportElse:",
          e);
    }
  }

  @Test
  public void testDwnloadRevenueGeneratedReport() {
    try {
      Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong())).thenReturn(pgMerchant);
      mockMvc
          .perform(post("/" + URLMappingConstants.DOWNLOAD_REVENUE_GENERATED_REPORT)
              .param(Constants.DOWNLOAD_TYPE, Constants.PDF_FILE_FORMAT)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_REVENUE_GENERATED_REPORT));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testDwnloadRevenueGeneratedReport:", e);
    }
  }

  @Test
  public void testDownloadRevenueGeneratedReportXls() {
    try {
      Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong())).thenReturn(pgMerchant);
      mockMvc
          .perform(post("/" + URLMappingConstants.DOWNLOAD_REVENUE_GENERATED_REPORT)
              .param(Constants.DOWNLOAD_TYPE, Constants.XLS_FILE_FORMAT)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_REVENUE_GENERATED_REPORT));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testDownloadRevenueGeneratedReportXls:", e);
    }
  }

  @Test
  public void testshowSpecificEFTTransfersReports() {
    try {
      Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong())).thenReturn(pgMerchant);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS)
          .param(Constants.DOWNLOAD_TYPE, Constants.XLS_FILE_FORMAT)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))).andExpect(
              view().name(URLMappingConstants.SHOW_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testshowSpecificEFTTransfersReports:", e);
    }
  }

  @Test
  public void testProcessSpecificEFTTransfersReportsReferer() {
    try {
      Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong())).thenReturn(pgMerchant);
      mockMvc
          .perform(get("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS)
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: ReportsControllerTest :: testProcessSpecificEFTTransfersReportsReferer:", e);
    }
  }

  @Test
  public void testProcessSpecificEFTTransfersReports() {
    revenueGeneratedReportList = new ArrayList<>();
    pgMerchant = new PGMerchant();
    pgMerchant.setMerchantCode("merchantCode");
    searchDetails = new ArrayList<>();
    reportsDTO = new ReportsDTO();
    revenueGeneratedReportList.add(reportsDTO);
    reportsDTO = new ReportsDTO();
    reportsDTO.setAmount("1230");
    try {
      Mockito
          .when(fundTransfersService
              .getAllEftTransfersListOnMerchantCode(Matchers.any(GetTransferListRequest.class)))
          .thenReturn(revenueGeneratedReportList);
      Mockito.when(fundTransfersService.splitReportsAmount(Matchers.anyList())).thenReturn(tempMap);
      Mockito.when(fundTransfersService.getReportsEFTAmount(Matchers.anyMap()))
          .thenReturn(searchDetails);
      Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong())).thenReturn(pgMerchant);
      Mockito.when(merchantInfoService.getParentMerchantCode(Matchers.anyString()))
          .thenReturn("parentMerchantCode");
      mockMvc
          .perform(get("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS)
              .param("merchantCode", "merchantCode").param("amount", "2")
              .header(Constants.REFERER, Constants.REFERER).sessionAttr(Constants.EXISTING_FEATURES, "notExist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(
              view().name(URLMappingConstants.SHOW_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testProcessSpecificEFTTransfersReports:", e);
    }
  }

  @Test
  public void testProcessSpecificEFTTransfersReportsElseIf() {
    pgMerchant = new PGMerchant();
    pgMerchant.setMerchantCode("");
    try {
      Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong())).thenReturn(pgMerchant);
      Mockito.when(merchantInfoService.getParentMerchantCode(Matchers.anyString()))
          .thenReturn("parentMerchantCode");
      mockMvc
          .perform(get("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS)
              .param("merchantCode", "").header(Constants.REFERER, Constants.REFERER)
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(
              view().name(URLMappingConstants.SHOW_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS));
    } catch (Exception e) {
      logger.error(
          "ERROR :: ReportsControllerTest :: testProcessSpecificEFTTransfersReportsElseIf:", e);
    }
  }

  @Test
  public void testProcessSpecificEFTTransfersReportsElse() {
    pgMerchant = new PGMerchant();
    pgMerchant.setMerchantCode("");
    try {
      Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong())).thenReturn(pgMerchant);
      Mockito.when(merchantInfoService.getParentMerchantCode(Matchers.anyString()))
          .thenReturn("parentMerchantCode");
      mockMvc
          .perform(get("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS)
              .param("merchantCode", "merchantCode").header(Constants.REFERER, Constants.REFERER)
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(
              view().name(URLMappingConstants.SHOW_CHATAK_ADMIN_SPECIFIC_USER_EFT_TRANSFERS));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testProcessSpecificEFTTransfersReportsElse:",
          e);
    }
  }

  @Test
  public void testDownloadSpecificEFTTransferReport() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_SPECIFIC_EFT_TRANSFERS_REPORT)
          .param(Constants.DOWNLOAD_TYPE, Constants.PDF_FILE_FORMAT)
          .sessionAttr("eftTransferReportList", eftTransferDownloadList)
          .sessionAttr(Constants.EXISTING_FEATURES, "notExist")
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testDownloadSpecificEFTTransferReport:", e);
    }
  }

  @Test
  public void testDownloadSpecificEFTTransferReportXls() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_SPECIFIC_EFT_TRANSFERS_REPORT)
          .param(Constants.DOWNLOAD_TYPE, Constants.XLS_FILE_FORMAT)
          .sessionAttr("eftTransferReportList", eftTransferDownloadList)
          .sessionAttr(Constants.EXISTING_FEATURES, "notExist")
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testDownloadSpecificEFTTransferReportXls:",
          e);
    }
  }


  @Test
  public void testFetchTransactionIdsListBytransferId() {
    gettransactionIdsListResponse = new GetTransactionIdsListResponse();
    try {
      Mockito.when(fundTransfersService.getTransactionIdListOnTransferId(Matchers.anyString()))
          .thenReturn(gettransactionIdsListResponse);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_EFT_FETCH_TRAN_ID)
          .sessionAttr(Constants.EXISTING_FEATURES, "exist")
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testFetchTransactionIdsListBytransferId:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testFetchTransactionIdsListBytransferIdException() {
    gettransactionIdsListResponse = new GetTransactionIdsListResponse();
    try {
      Mockito.when(fundTransfersService.getTransactionIdListOnTransferId(Matchers.anyString()))
          .thenThrow(NullPointerException.class);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_EFT_FETCH_TRAN_ID)
          .sessionAttr(Constants.EXISTING_FEATURES, "exist")
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error(
          "ERROR :: ReportsControllerTest :: testFetchTransactionIdsListBytransferIdException:", e);
    }
  }

  @Test
  public void testDownloadTransactionReportReferer() {
    gettransactionIdsListResponse = new GetTransactionIdsListResponse();
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_EXPORT)
          .sessionAttr("transactionReqObject", listRequest)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testDownloadTransactionReportReferer:", e);
    }
  }

  @Test
  public void testDownloadTransactionReport() {
    gettransactionIdsListResponse = new GetTransactionIdsListResponse();
    try {
      Mockito
          .when(
              transactionService.searchTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_EXPORT)
          .header(Constants.REFERER, Constants.REFERER).sessionAttr("transactionReqObject", listRequest)
          .param("downloadAllRecords", "true").param(Constants.DOWNLOAD_TYPE, Constants.PDF_FILE_FORMAT)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testDownloadTransactionReport:", e);
    }
  }

  @Test
  public void testDownloadTransactionReportXls() {
    gettransactionIdsListResponse = new GetTransactionIdsListResponse();
    try {
      Mockito
          .when(
              transactionService.searchTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_EXPORT)
          .header(Constants.REFERER, Constants.REFERER).sessionAttr("transactionReqObject", listRequest)
          .param("downloadAllRecords", "true").param(Constants.DOWNLOAD_TYPE, Constants.XLS_FILE_FORMAT)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testDownloadTransactionReportXls:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testDownloadTransactionReportException() {
    gettransactionIdsListResponse = new GetTransactionIdsListResponse();
    try {
      Mockito
          .when(
              transactionService.searchTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenThrow(NullPointerException.class);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_EXPORT)
          .header(Constants.REFERER, Constants.REFERER).sessionAttr("transactionReqObject", listRequest)
          .param("downloadAllRecords", "true").param(Constants.DOWNLOAD_TYPE, Constants.XLS_FILE_FORMAT)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testDownloadTransactionReportException:", e);
    }
  }

  @Test
  public void testProcessingTransactionsReportReferer() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_REPORT)
          .sessionAttr("transactionReqObject", listRequest).param("downloadAllRecords", "true")
          .param(Constants.DOWNLOAD_TYPE, Constants.XLS_FILE_FORMAT)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testProcessingTransactionsReportReferer:", e);
    }
  }

  @Test
  public void testProcessingTransactionsReportExistingFeature() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_REPORT)
          .sessionAttr(Constants.EXISTING_FEATURES, "notExist").header(Constants.REFERER, Constants.REFERER)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error(
          "ERROR :: ReportsControllerTest :: testProcessingTransactionsReportExistingFeature:", e);
    }
  }

  @Test
  public void testProcessingTransactionsReport() {
    try {
      transactionResponse = new GetTransactionsListResponse();
      transactionResponse();
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_REPORT)
          .sessionAttr("processingTxnList", processingTxnList)
          .sessionAttr(Constants.EXISTING_FEATURES, "exist").header(Constants.REFERER, Constants.REFERER)
          .sessionAttr("transactionReqObject", listRequest).param("downloadAllRecords", "true")
          .param("requestFrom", "requestFrom").param(Constants.DOWNLOAD_TYPE, Constants.XLS_FILE_FORMAT)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testProcessingTransactionsReport:", e);
    }
  }

  @Test
  public void testProcessingTransactionsReportPdf() {
    try {
      transactionResponse = new GetTransactionsListResponse();
      transactionResponse();
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_REPORT)
          .sessionAttr("processingTxnList", processingTxnList)
          .sessionAttr(Constants.EXISTING_FEATURES, "exist").header(Constants.REFERER, Constants.REFERER)
          .sessionAttr("transactionReqObject", listRequest).param("downloadAllRecords", "true")
          .param("requestFrom", "requestFrom").param(Constants.DOWNLOAD_TYPE, Constants.PDF_FILE_FORMAT)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testProcessingTransactionsReportPdf:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testProcessingTransactionsReportException() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(NullPointerException.class);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_REPORT)
          .sessionAttr("processingTxnList", processingTxnList)
          .sessionAttr(Constants.EXISTING_FEATURES, "exist").header(Constants.REFERER, Constants.REFERER)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testProcessingTransactionsReportException:",
          e);
    }
  }

  @Test
  public void testExecutedTransactionsReportReferer() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_REPORT));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testExecutedTransactionsReportReferer:", e);
    }
  }

  @Test
  public void testExecutedTransactionsReport() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setMerchantId("125");
    transactionResponse();
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionResponse);

      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_REPORT)
          .param(Constants.DOWNLOAD_TYPE, Constants.PDF_FILE_FORMAT).param("downloadAllRecords", "true")
          .sessionAttr("executedTxnList", executedTxnsList).header(Constants.REFERER, Constants.REFERER)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testExecutedTransactionsReport:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testExecutedTransactionsReportException() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setMerchantId("125");
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(NullPointerException.class);

      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_REPORT)
          .sessionAttr("executedTxnList", executedTxnsList).header(Constants.REFERER, Constants.REFERER)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testExecutedTransactionsReportException:", e);
    }
  }

  @Test
  public void testManualTransactionsReport() {
    transactionResponse();
    try {
      Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(merchantDao.findById(Matchers.anyLong())).thenReturn(subMerchantList);
      Mockito
          .when(transactionService
              .searchManulAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_MANUAL_TRANSACTIONS_REPORT)
          .param(Constants.DOWNLOAD_TYPE, Constants.PDF_FILE_FORMAT).param("downloadAllRecords", "true")
          .sessionAttr("executedTxnList", executedTxnsList).header(Constants.REFERER, Constants.REFERER)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testManualTransactionsReport:", e);
    }
  }

  private void transactionResponse() {
    transactionResponse = new GetTransactionsListResponse();
    List<AccountTransactionDTO> accountTransactionList = new ArrayList<>();
    AccountTransactionDTO accountTransactionDTO = new AccountTransactionDTO();
    accountTransactionDTO.setAccountNumber("accountNumber");
    accountTransactionList.add(accountTransactionDTO);
    transactionResponse.setAccountTransactionList(accountTransactionList);
    subMerchantList = new ArrayList<>();
    subMerchantList.add(pgMerchant);
    pgMerchant = new PGMerchant();
    pgMerchant.setMerchantCode("merchantCode");
  }

  @Test
  public void testManualTransactionsReportXls() {
    transactionResponse();
    try {
      Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong())).thenReturn(pgMerchant);
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(merchantDao.findById(Matchers.anyLong())).thenReturn(subMerchantList);
      Mockito
          .when(transactionService
              .searchManulAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_MANUAL_TRANSACTIONS_REPORT)
          .param(Constants.DOWNLOAD_TYPE, Constants.XLS_FILE_FORMAT).param("downloadAllRecords", "true")
          .sessionAttr("executedTxnList", executedTxnsList).header(Constants.REFERER, Constants.REFERER)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testManualTransactionsReportXls:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testManualTransactionsReportException() {
    transactionResponse();
    try {
      Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong()))
          .thenThrow(NullPointerException.class);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_MANUAL_TRANSACTIONS_REPORT)
          .param(Constants.DOWNLOAD_TYPE, Constants.XLS_FILE_FORMAT).param("downloadAllRecords", "true")
          .sessionAttr("executedTxnList", executedTxnsList).header(Constants.REFERER, Constants.REFERER)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: ReportsControllerTest :: testManualTransactionsReportException:", e);
    }
  }

}
