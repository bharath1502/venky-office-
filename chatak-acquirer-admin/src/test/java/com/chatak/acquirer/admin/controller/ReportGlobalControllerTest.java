/**
 * 
 */
package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantData;
import com.chatak.acquirer.admin.model.MerchantSearchResponse;
import com.chatak.acquirer.admin.service.FundTransfersService;
import com.chatak.acquirer.admin.service.MerchantAccountService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.MerchantValidateService;
import com.chatak.acquirer.admin.service.TransactionService;
import com.chatak.acquirer.admin.service.UserService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.bean.TransactionPopUpDataDto;
import com.chatak.pg.model.GetTransactionIdsListResponse;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jan 6, 2018 10:53:47 AM
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class ReportGlobalControllerTest {

  private static Logger logger = Logger.getLogger(ReportGlobalControllerTest.class);

  @InjectMocks
  ReportGlobalController controller = new ReportGlobalController();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  private Response responseval;

  private MockMvc mockMvc;

  @Mock
  BindingResult bindingResult;

  @Mock
  private List<Option> optionList;

  @Mock
  private Option option;

  @Mock
  private MessageSource messageSource;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  private MerchantAccountService merchantAccountService;

  @Mock
  private TransactionService transactionService;

  @Mock
  private UserService userService;

  @Mock
  private FundTransfersService fundTransfersService;

  @Mock
  private MerchantUpdateService merchantUpdateService;

  @Mock
  private MerchantValidateService merchantValidateService;

  @Mock
  private List<ReportsDTO> reportsDTOs;

  @Mock
  private ReportsDTO reportsDTO;

  @Mock
  private TransactionPopUpDataDto transactionPopUpDataDto;

  @Mock
  private Merchant merchant;

  @Mock
  private GetTransactionsListResponse transactionsListResponse;

  @Mock
  private List<Transaction> transactionsList;

  @Mock
  private Transaction transaction;

  @Mock
  private MerchantSearchResponse merchantSearchResponse;

  @Mock
  private List<MerchantData> merchantsData;

  @Mock
  private MerchantData merchantData;

  @Mock
  private Timestamp time;

  @Mock
  private GetTransactionIdsListResponse transactionIdsListResponse;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
    optionList = new ArrayList<>();
  }

  @Test
  public void testShowAllAccExecutedTranDate() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.ALL_ACCOUNTS_EXECUTED_TRANS_DATE_SHOW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.ALL_ACCOUNTS_EXECUTED_TRANSACTIONS_SHOW));
    } catch (Exception e) {
      logger.error("Error :: ReportGlobalControllerTest :: testShowAllAccExecutedTranDate", e);
    }
  }

  @Test
  public void testShowAllAccountsExecutedTransactionsSuccess() {
    reportsDTOs = new ArrayList<>();
    reportsDTO = new ReportsDTO();
    transactionPopUpDataDto = new TransactionPopUpDataDto();
    reportsDTO.setTxnPopupDto(transactionPopUpDataDto);
    reportsDTOs.add(reportsDTO);
    try {
      Mockito.when(transactionService
          .getAllAccountsExecutedTransactionsOnDate(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(reportsDTOs);
      mockMvc
          .perform(get("/" + URLMappingConstants.ALL_ACCOUNTS_EXECUTED_TRANSACTIONS_SHOW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.ALL_ACCOUNTS_EXECUTED_TRANSACTIONS_SHOW));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testShowAllAccountsExecutedTransactionsSuccess",
          e);
    }
  }

  @Test
  public void testShowAllAccountsExecutedTransactionsFail() {
    try {
      Mockito.when(transactionService
          .getAllAccountsExecutedTransactionsOnDate(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(null);
      mockMvc
          .perform(get("/" + URLMappingConstants.ALL_ACCOUNTS_EXECUTED_TRANSACTIONS_SHOW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.ALL_ACCOUNTS_EXECUTED_TRANSACTIONS_SHOW));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testShowAllAccountsExecutedTransactionsFail", e);
    }
  }

  @Test
  public void testShowAllAccountsExecutedTransactionsException() {
    try {
      Mockito.when(transactionService
          .getAllAccountsExecutedTransactionsOnDate(Matchers.any(GetTransactionsListRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.ALL_ACCOUNTS_EXECUTED_TRANSACTIONS_SHOW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.ALL_ACCOUNTS_EXECUTED_TRANSACTIONS_SHOW));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testShowAllAccountsExecutedTransactionsException",
          e);
    }
  }

  @Test
  public void testShowStatementAccTransdetails() {
    merchant = new Merchant();
    merchant.setCreatedDate("createdDate");
    transactionsListResponse = new GetTransactionsListResponse();
    transaction = new Transaction();
    transaction.setAccountNumber(1l);
    transactionsList.add(transaction);
    transactionsListResponse.setTransactionList(transactionsList);
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchant);
      Mockito.when(merchantAccountService.getMerchantAccountDetails(Matchers.anyString()))
          .thenReturn(merchant);
      Mockito
          .when(transactionService
              .searchTransactionsReport(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionsListResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_STATEMENT_TRANSACTION_DETAILS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.SHOW_INDIVIDUAL_STATEMENT_ACC_DETAILS));
    } catch (Exception e) {
      logger.error("Error :: ReportGlobalControllerTest :: testShowStatementAccTransdetails", e);
    }
  }

  @Test
  public void testShowStatementAccTransdetailsChatakAdminException() {
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenThrow(ChatakAdminException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_STATEMENT_TRANSACTION_DETAILS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.SHOW_INDIVIDUAL_STATEMENT_ACC_DETAILS));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testShowStatementAccTransdetailsChatakAdminException",
          e);
    }
  }

  @Test
  public void testShowStatementAccTransdetailsException() {
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_STATEMENT_TRANSACTION_DETAILS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.SHOW_INDIVIDUAL_STATEMENT_ACC_DETAILS));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testShowStatementAccTransdetailsException", e);
    }
  }

  @Test
  public void testDownloadSpecificUserStatementListReportPDF() {
    transactionsList = new ArrayList<>();
    try {
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_SPECIFIC_USER_STATEMENTLIST_REPORT)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin")
          .sessionAttr("specificUserStatementList", transactionsList).param(Constants.DOWNLOAD_TYPE, "PDF"));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testShowStatementAccTransdetailsExceptionPDF", e);
    }
  }

  @Test
  public void testDownloadSpecificUserStatementListReportXLS() {
    transactionsList = new ArrayList<>();
    try {
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_SPECIFIC_USER_STATEMENTLIST_REPORT)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin")
          .sessionAttr("specificUserStatementList", transactionsList).param(Constants.DOWNLOAD_TYPE, "XLS"));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testShowStatementAccTransdetailsExceptionXLS", e);
    }
  }

  @Test
  public void testGetStatementPaginationListSuccess() {
    transactionsList = new ArrayList<>();
    merchantSearchResponse = new MerchantSearchResponse();
    merchantData = new MerchantData();
    merchantData.setAddress1("address1");
    merchantData.setCreatedDate(time);
    merchantsData = new ArrayList<>();
    merchantsData.add(merchantData);
    merchantSearchResponse.setMerchants(merchantsData);
    merchantSearchResponse.setTotalNoOfRows(Integer.parseInt("1"));
    try {
      Mockito.when(merchantUpdateService.searchAllMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchantSearchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.SPECIFIC_STATEMENT_REPORT_PAGINATION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin").param("pageNumber", "1"))
          .andExpect(view().name(URLMappingConstants.SPECIFIC_USER_STATEMENT_REPORTS_SHOW));
    } catch (Exception e) {
      logger.error("Error :: ReportGlobalControllerTest :: testGetStatementPaginationListSuccess",
          e);
    }
  }

  @Test
  public void testGetStatementPaginationListFail() {
    try {
      Mockito.when(merchantUpdateService.searchAllMerchant(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.SPECIFIC_STATEMENT_REPORT_PAGINATION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin").param("pageNumber", "1"))
          .andExpect(view().name(URLMappingConstants.SPECIFIC_USER_STATEMENT_REPORTS_SHOW));
    } catch (Exception e) {
      logger.error("Error :: ReportGlobalControllerTest :: testGetStatementPaginationListFail", e);
    }
  }

  @Test
  public void testDownloadRevenueGeneratedReportException() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_REVENUE_GENERATED_REPORT)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin")
          .sessionAttr(Constants.REVENUE_GENERATED_REPORT_LIST, reportsDTOs)
          .param(Constants.DOWNLOAD_TYPE, "PDF"));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testDownloadRevenueGeneratedReportException", e);
    }
  }

  @Test
  public void testDownloadRevenueGeneratedReportPDF() {
    reportsDTOs = new ArrayList<>();
    reportsDTO = new ReportsDTO();
    reportsDTO.setAccountNumber(1l);
    reportsDTOs.add(reportsDTO);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_REVENUE_GENERATED_REPORT)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin")
          .sessionAttr(Constants.REVENUE_GENERATED_REPORT_LIST, reportsDTOs)
          .param(Constants.DOWNLOAD_TYPE, "PDF"));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testDownloadRevenueGeneratedReportExceptionPDF",
          e);
    }
  }

  @Test
  public void testDownloadRevenueGeneratedReportXLS() {
    reportsDTOs = new ArrayList<>();
    reportsDTO = new ReportsDTO();
    reportsDTO.setAccountNumber(1l);
    reportsDTOs.add(reportsDTO);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_REVENUE_GENERATED_REPORT)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin")
          .sessionAttr(Constants.REVENUE_GENERATED_REPORT_LIST, reportsDTOs)
          .param(Constants.DOWNLOAD_TYPE, "XLS"));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testDownloadRevenueGeneratedReportExceptionXLS",
          e);
    }
  }

  @Test
  public void testShowGlobalAccessLogReport() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.GLOBAL_ACCESS_LOG_REPORT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin")
              .sessionAttr(Constants.REVENUE_GENERATED_REPORT_LIST, reportsDTOs))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_ACCESS_LOG_REPORT));
    } catch (Exception e) {
      logger.error("Error :: ReportGlobalControllerTest :: testShowGlobalAccessLogReport", e);
    }
  }

  @Test
  public void testShowGlobalAccessLogReportException() {
    try {
      Mockito.when(userService.getAllPgUserActivityLogs()).thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.GLOBAL_ACCESS_LOG_REPORT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin")
              .sessionAttr(Constants.REVENUE_GENERATED_REPORT_LIST, reportsDTOs))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_ACCESS_LOG_REPORT));
    } catch (Exception e) {
      logger.error("Error :: ReportGlobalControllerTest :: testShowGlobalAccessLogReport", e);
    }
  }

  @Test
  public void testFetchTransactionIdsListBytransferId() {
    transactionIdsListResponse = new GetTransactionIdsListResponse();
    try {
      Mockito.when(fundTransfersService.getTransactionIdListOnTransferId(Matchers.anyString()))
          .thenReturn(transactionIdsListResponse);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_EFT_FETCH_TRAN_ID)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"));
    } catch (Exception e) {
      logger.error("Error :: ReportGlobalControllerTest :: testFetchTransactionIdsListBytransferId",
          e);
    }
  }

  @Test
  public void testFetchTransactionIdsListBytransferIdException() {
    try {
      Mockito.when(fundTransfersService.getTransactionIdListOnTransferId(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_EFT_FETCH_TRAN_ID)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testFetchTransactionIdsListBytransferIdException",
          e);
    }
  }

  @Test
  public void testShowAllPendingTransactions() {
    GetTransactionsListRequest transactionsListRequest = new GetTransactionsListRequest();
    transactionsListRequest.setNoOfRecords(Integer.parseInt("1"));
    reportsDTOs = new ArrayList<>();
    reportsDTO = new ReportsDTO();
    reportsDTO.setAccountNumber(1l);
    reportsDTOs.add(reportsDTO);
    try {
      Mockito.when(transactionService
          .getAllAccountsExecutedTransactionsOnDate(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(reportsDTOs);
      mockMvc
          .perform(post("/" + URLMappingConstants.GLOBAL_PENDING_TRANSACTION_REPORT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin").param("from_date", "from_date")
              .param("to_date", "to_date").param("noOfRecords", "1"))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_PENDING_TRANS_REPORT));
    } catch (Exception e) {
      logger.error("Error :: ReportGlobalControllerTest :: testShowAllPendingTransactions", e);
    }
  }

  @Test
  public void testShowGlobalAllTransactionsDate() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.GLOBAL_ALL_TRANSACTIONS_REPORT_DATES)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_ALL_TRANS_REPORT));
    } catch (Exception e) {
      logger.error("Error :: ReportGlobalControllerTest :: testShowGlobalAllTransactionsDate", e);
    }
  }

  @Test
  public void testShowGlobalRevenueGeneratedReportsDates() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.GLOBAL_REVENUE_GENERATED_REPORTS_DATES)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_REVENUE_GENERATED_REPORT));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testShowGlobalRevenueGeneratedReportsDates", e);
    }
  }

  @Test
  public void testShowGlobalRevenueGeneratedReportException() {
    try {
      Mockito
          .when(transactionService
              .getAllExecutedAccTransFeeOnDate(Matchers.any(GetTransactionsListRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.GLOBAL_REVENUE_GENERATED_REPORT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_REVENUE_GENERATED_REPORT));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testShowGlobalRevenueGeneratedReportException",
          e);
    }
  }

  @Test
  public void testShowGlobalRevenueGeneratedReportRevenueTypePos() {
    reportsDTOs = new ArrayList<>();
    reportsDTO = new ReportsDTO();
    reportsDTO.setAccountNumber(1l);
    reportsDTO.setChatakFee("1");
    reportsDTO.setParentMerchantId("1");
    reportsDTO.setTotalTxnAmount("1");
    reportsDTO.setFee("1");
    transactionPopUpDataDto = new TransactionPopUpDataDto();
    reportsDTO.setTxnPopupDto(transactionPopUpDataDto);
    reportsDTOs.add(reportsDTO);
    try {
      Mockito
          .when(transactionService
              .getAllExecutedAccTransFeeOnDate(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(reportsDTOs);
      mockMvc
          .perform(get("/" + URLMappingConstants.GLOBAL_REVENUE_GENERATED_REPORT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin").param("currency", "USD")
              .param("merchantCode", "").param("toDate", "toDate").param(Constants.REVENUE_TYPE, "pos"))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_REVENUE_GENERATED_REPORT));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testShowGlobalRevenueGeneratedReportRevenueTypePos",
          e);
    }
  }

  @Test
  public void testShowGlobalRevenueGeneratedReportRevenueTypeMerchatWeb() {
    reportsDTOs = new ArrayList<>();
    reportsDTO = new ReportsDTO();
    reportsDTO.setAccountNumber(1l);
    reportsDTO.setChatakFee("1");
    reportsDTO.setParentMerchantId("");
    reportsDTO.setTotalTxnAmount("1");
    reportsDTO.setFee("1");
    transactionPopUpDataDto = new TransactionPopUpDataDto();
    reportsDTO.setTxnPopupDto(transactionPopUpDataDto);
    reportsDTOs.add(reportsDTO);
    try {
      Mockito
          .when(transactionService
              .getAllExecutedAccTransFeeOnDate(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(reportsDTOs);
      mockMvc
          .perform(get("/" + URLMappingConstants.GLOBAL_REVENUE_GENERATED_REPORT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin").param("currency", "USD")
              .param("merchantCode", "1234567890").param("toDate", "toDate")
              .param(Constants.REVENUE_TYPE, "MERCHAT_WEB"))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_REVENUE_GENERATED_REPORT));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testShowGlobalRevenueGeneratedReportRevenueTypeMerchatWeb",
          e);
    }
  }

  @Test
  public void testShowGlobalRevenueGeneratedReportElse() {
    try {
      Mockito
          .when(transactionService
              .getAllExecutedAccTransFeeOnDate(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(null);
      mockMvc
          .perform(get("/" + URLMappingConstants.GLOBAL_REVENUE_GENERATED_REPORT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin").param("currency", "USD")
              .param("merchantCode", "1234567890").param("toDate", "toDate")
              .param(Constants.REVENUE_TYPE, Constants.REVENUE_TYPE))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_REVENUE_GENERATED_REPORT));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testShowGlobalRevenueGeneratedReportElse", e);
    }
  }

  @Test
  public void testShowGlobalRevenueGeneratedReportRevenueType() {
    reportsDTOs = new ArrayList<>();
    reportsDTO = new ReportsDTO();
    reportsDTO.setAccountNumber(1l);
    reportsDTO.setChatakFee("1");
    reportsDTO.setParentMerchantId("");
    reportsDTO.setTotalTxnAmount("1");
    reportsDTO.setFee("1");
    transactionPopUpDataDto = new TransactionPopUpDataDto();
    reportsDTO.setTxnPopupDto(transactionPopUpDataDto);
    reportsDTOs.add(reportsDTO);
    try {
      Mockito
          .when(transactionService
              .getAllExecutedAccTransFeeOnDate(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(reportsDTOs);
      mockMvc
          .perform(get("/" + URLMappingConstants.GLOBAL_REVENUE_GENERATED_REPORT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin").param("currency", "USD")
              .param("merchantCode", "1234567890").param("toDate", "toDate")
              .param(Constants.REVENUE_TYPE, ""))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_REVENUE_GENERATED_REPORT));
    } catch (Exception e) {
      logger.error(
          "Error :: ReportGlobalControllerTest :: testShowGlobalRevenueGeneratedReportRevenueType",
          e);
    }
  }
}
