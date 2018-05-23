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
import com.chatak.acquirer.admin.service.MerchantAccountService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.MerchantValidateService;
import com.chatak.acquirer.admin.service.SettlementReportService;
import com.chatak.acquirer.admin.service.TransactionService;
import com.chatak.pg.model.AccessLogsDTO;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jan 1, 2018 9:39:50 AM
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class UserReportsControllerTest {

  private static Logger logger = Logger.getLogger(UserReportsControllerTest.class);

  @InjectMocks
  private UserReportsController controller = new UserReportsController();

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private BindingResult bindingResult;

  private MockMvc mockMvc;

  @Mock
  private MessageSource messageSource;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  private List<Option> optionList;

  @Mock
  private MerchantValidateService merchantValidateService;

  @Mock
  private MerchantUpdateService merchantUpdateService;

  @Mock
  private MerchantAccountService merchantAccountService;

  @Mock
  private TransactionService transactionService;

  @Mock
  private SettlementReportService settlementReportService;

  @Mock
  private Merchant merchant;

  @Mock
  private GetTransactionsListResponse transactionsListResponse;

  @Mock
  private List<Transaction> transactionsList;

  @Mock
  private MerchantSearchResponse merchantSearchResponse;

  @Mock
  private List<MerchantData> merchantsData;

  @Mock
  private MerchantData merchantData;

  @Mock
  private Timestamp date;

  @Mock
  private List<ReportsDTO> reportDTOs;

  @Mock
  private List<AccessLogsDTO> accessLogsDTOs;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
  }

  @Before
  public void pro() {
    java.util.Properties properties = new java.util.Properties();
    properties.setProperty("admin.services.reports.feature.id", "notExist");
    properties.setProperty("admin.services.reports.feature.id", "exist");
    properties.setProperty("reseller.services.reports.feature.id", "notExist");
    properties.setProperty("reseller.services.reports.feature.id", "exist");
    Properties.mergeProperties(properties);
  }

  @Test
  public void testShowIndividualUserAccTransdetails() {
    merchant = new Merchant();
    merchant.setCreatedDate("createdDate");
    transactionsListResponse = new GetTransactionsListResponse();
    transactionsListResponse.setTransactionList(transactionsList);
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchant);
      Mockito.when(merchantAccountService.getMerchantAccountDetails(Matchers.anyString()))
          .thenReturn(merchant);
      Mockito
          .when(transactionService
              .getAllTransactionsOnMerchantCode(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionsListResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_USER_ACCOUNT_TRANSACTION_DETAILS)
              .sessionAttr("loginUserType", "loginUserType")
              .param("getMerchantCode", "getMerchantCode"))
          .andExpect(view().name(URLMappingConstants.SHOW_INDIVIDUAL_USER_ACCOUNT_DETAILS));
    } catch (Exception e) {
      logger.error("ERROR :: UserReportsControllerTest :: testShowIndividualUserAccTransdetails",
          e);

    }
  }

  @Test
  public void testShowIndividualUserAccTransdetailsChatakAdminException() {
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenThrow(ChatakAdminException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_USER_ACCOUNT_TRANSACTION_DETAILS)
              .sessionAttr("loginUserType", "loginUserType")
              .param("getMerchantCode", "getMerchantCode"))
          .andExpect(view().name(URLMappingConstants.SHOW_INDIVIDUAL_USER_ACCOUNT_DETAILS));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserReportsControllerTest :: testShowIndividualUserAccTransdetailsChatakAdminException",
          e);

    }
  }

  @Test
  public void testShowIndividualUserAccTransdetailsException() {
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_USER_ACCOUNT_TRANSACTION_DETAILS)
              .sessionAttr("loginUserType", "loginUserType")
              .param("getMerchantCode", "getMerchantCode"))
          .andExpect(view().name(URLMappingConstants.SHOW_INDIVIDUAL_USER_ACCOUNT_DETAILS));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserReportsControllerTest :: testShowIndividualUserAccTransdetailsException",
          e);

    }
  }

  @Test
  public void testShowSpecificUserStatementReports() {
    merchantsData = new ArrayList<>();
    merchantData = new MerchantData();
    merchantData.setAddress1("address1");
    merchantsData.add(merchantData);
    merchantSearchResponse = new MerchantSearchResponse();
    merchantSearchResponse.setMerchants(merchantsData);
    merchantSearchResponse.setTotalNoOfRows(Integer.parseInt("1"));
    merchantData = new MerchantData();
    merchantData.setCreatedDate(date);
    try {
      Mockito.when(merchantUpdateService.searchAllMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchantSearchResponse);
      mockMvc
          .perform(get("/" + URLMappingConstants.SPECIFIC_USER_STATEMENT_REPORT)
              .sessionAttr("loginUserType", "loginUserType"))
          .andExpect(view().name(URLMappingConstants.SPECIFIC_USER_STATEMENT_REPORTS_SHOW));
    } catch (Exception e) {
      logger.error("ERROR :: UserReportsControllerTest :: testShowSpecificUserStatementReports", e);

    }
  }

  @Test
  public void testShowSpecificUserStatementReportsException() {
    merchantsData = new ArrayList<>();
    merchantData = new MerchantData();
    merchantData.setAddress1("address1");
    merchantsData.add(merchantData);
    merchantSearchResponse = new MerchantSearchResponse();
    merchantSearchResponse.setMerchants(merchantsData);
    merchantSearchResponse.setTotalNoOfRows(Integer.parseInt("1"));
    merchantData = new MerchantData();
    merchantData.setCreatedDate(date);
    try {
      Mockito.when(merchantUpdateService.searchAllMerchant(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.SPECIFIC_USER_STATEMENT_REPORT)
              .sessionAttr("loginUserType", "loginUserType"))
          .andExpect(view().name(URLMappingConstants.SPECIFIC_USER_STATEMENT_REPORTS_SHOW));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserReportsControllerTest :: testShowSpecificUserStatementReportsException", e);

    }
  }

  @Test
  public void testDownloadAllUsersExecutedTransactionsPDF() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.ALL_ACCOUNTS_EXECUTED_TRANSACTIONS)
          .sessionAttr("loginUserType", "loginUserType")
          .sessionAttr("executedTransactionsReportList", reportDTOs).param(Constants.DOWNLOAD_TYPE, "PDF"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserReportsControllerTest :: testDownloadAllUsersExecutedTransactionsPDF", e);

    }
  }

  @Test
  public void testDownloadAllUsersExecutedTransactionsXLS() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.ALL_ACCOUNTS_EXECUTED_TRANSACTIONS)
          .sessionAttr("loginUserType", "loginUserType")
          .sessionAttr("executedTransactionsReportList", reportDTOs).param(Constants.DOWNLOAD_TYPE, "XLS"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserReportsControllerTest :: testDownloadAllUsersExecutedTransactionsXLS", e);

    }
  }

  @Test
  public void testDownloadAllUsersExecutedTransactionsException() {
    merchant = new Merchant();
    merchant.setCreatedDate("createdDate");
    try {
      mockMvc.perform(post("/" + URLMappingConstants.ALL_ACCOUNTS_EXECUTED_TRANSACTIONS)
          .sessionAttr("loginUserType", "loginUserType")
          .sessionAttr("executedTransactionsReportList", null));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserReportsControllerTest :: testDownloadAllUsersExecutedTransactionsException",
          e);

    }
  }

  @Test
  public void testShowIndividualStamentAccdetails() {
    merchant = new Merchant();
    merchant.setCreatedDate("createdDate");
    transactionsListResponse = new GetTransactionsListResponse();
    transactionsListResponse.setTransactionList(transactionsList);
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchant);
      Mockito.when(merchantAccountService.getMerchantAccountDetails(Matchers.anyString()))
          .thenReturn(merchant);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_INDIVIDUAL_STATEMENT_REPORT)
              .sessionAttr("loginUserType", "loginUserType")
              .sessionAttr("executedTransactionsReportList", reportDTOs)
              .param("getMerchantCode", "getMerchantCode"))
          .andExpect(view().name(URLMappingConstants.SHOW_INDIVIDUAL_STATEMENT_ACC_DETAILS));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserReportsControllerTest :: testDownloadAllUsersExecutedTransactionsException",
          e);
    }
  }

  @Test
  public void testShowIndividualStamentAccdetailsChatakAdminException() {
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenThrow(ChatakAdminException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_INDIVIDUAL_STATEMENT_REPORT)
              .sessionAttr("loginUserType", "loginUserType")
              .sessionAttr("executedTransactionsReportList", reportDTOs)
              .param("getMerchantCode", "getMerchantCode"))
          .andExpect(view().name(URLMappingConstants.SHOW_INDIVIDUAL_STATEMENT_ACC_DETAILS));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserReportsControllerTest :: testShowIndividualStamentAccdetailsChatakAdminException",
          e);

    }
  }

  @Test
  public void testShowIndividualStamentAccdetailsException() {
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_INDIVIDUAL_STATEMENT_REPORT)
              .sessionAttr("loginUserType", "loginUserType")
              .sessionAttr("executedTransactionsReportList", reportDTOs)
              .param("getMerchantCode", "getMerchantCode"))
          .andExpect(view().name(URLMappingConstants.SHOW_INDIVIDUAL_STATEMENT_ACC_DETAILS));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserReportsControllerTest :: testShowIndividualStamentAccdetailsException", e);

    }
  }

  @Test
  public void testDownloadAccessLogsReportPDF() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ACCESS_LOGS_REPORT_DOWNLOAD)
              .sessionAttr("accessLogs", accessLogsDTOs).param(Constants.DOWNLOAD_TYPE, "PDf"))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_ACCESS_LOG_REPORT));
    } catch (Exception e) {
      logger.error("ERROR :: UserReportsControllerTest :: testDownloadAccessLogsReportPDF", e);

    }
  }

  @Test
  public void testDownloadAccessLogsReportXLS() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ACCESS_LOGS_REPORT_DOWNLOAD)
              .sessionAttr("accessLogs", accessLogsDTOs).param(Constants.DOWNLOAD_TYPE, "XLS"))
          .andExpect(view().name(URLMappingConstants.SHOW_GLOBAL_ACCESS_LOG_REPORT));
    } catch (Exception e) {
      logger.error("ERROR :: UserReportsControllerTest :: testDownloadAccessLogsReportXLS", e);

    }
  }

  @Test
  public void testDownloadSpecificUserStatementReportPDFDownloadAll() {
    merchantData = new MerchantData();
    merchantData.setAddress1("address1");
    merchantsData = new ArrayList<>();
    merchantsData.add(merchantData);
    merchantSearchResponse = new MerchantSearchResponse();
    merchantSearchResponse.setMerchants(merchantsData);
    try {
      Mockito.when(merchantUpdateService.searchAllMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchantSearchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_SPECIFIC_USER_STATEMENT_REPORT)
          .sessionAttr("accessLogs", accessLogsDTOs).param(Constants.DOWNLOAD_TYPE, "PDF")
          .param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserReportsControllerTest :: testDownloadSpecificUserStatementReportPDFDownloadAll",
          e);

    }
  }

  @Test
  public void testDownloadSpecificUserStatementReportPDF() {
    merchantData = new MerchantData();
    merchantData.setAddress1("address1");
    merchantsData = new ArrayList<>();
    merchantsData.add(merchantData);
    merchantSearchResponse = new MerchantSearchResponse();
    merchantSearchResponse.setMerchants(merchantsData);
    try {
      Mockito.when(merchantUpdateService.searchAllMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchantSearchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_SPECIFIC_USER_STATEMENT_REPORT)
          .sessionAttr("accessLogs", accessLogsDTOs).param(Constants.DOWNLOAD_TYPE, "PDF")
          .param("downloadAllRecords", "false"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserReportsControllerTest :: testDownloadSpecificUserStatementReportPDF", e);

    }
  }

  @Test
  public void testDownloadSpecificUserStatementReportXLSDownloadAll() {
    merchantData = new MerchantData();
    merchantData.setAddress1("address1");
    merchantsData = new ArrayList<>();
    merchantsData.add(merchantData);
    merchantSearchResponse = new MerchantSearchResponse();
    merchantSearchResponse.setMerchants(merchantsData);
    try {
      Mockito.when(merchantUpdateService.searchAllMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchantSearchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_SPECIFIC_USER_STATEMENT_REPORT)
          .sessionAttr("accessLogs", accessLogsDTOs).param(Constants.DOWNLOAD_TYPE, "XLS")
          .param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserReportsControllerTest :: testDownloadSpecificUserStatementReportXLSDownloadAll",
          e);

    }
  }

  @Test
  public void testDownloadSpecificUserStatementReportXLS() {
    merchantData = new MerchantData();
    merchantData.setAddress1("address1");
    merchantsData = new ArrayList<>();
    merchantsData.add(merchantData);
    merchantSearchResponse = new MerchantSearchResponse();
    merchantSearchResponse.setMerchants(merchantsData);
    try {
      Mockito.when(merchantUpdateService.searchAllMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchantSearchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_SPECIFIC_USER_STATEMENT_REPORT)
          .sessionAttr("accessLogs", accessLogsDTOs).param(Constants.DOWNLOAD_TYPE, "XLS")
          .param("downloadAllRecords", "false"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserReportsControllerTest :: testDownloadSpecificUserStatementReportXLS", e);

    }
  }

  @Test
  public void testDownloadSpecificUserStatementReportException() {
    try {
      Mockito.when(merchantUpdateService.searchAllMerchant(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_SPECIFIC_USER_STATEMENT_REPORT)
          .sessionAttr("accessLogs", accessLogsDTOs).param(Constants.DOWNLOAD_TYPE, "XLS")
          .param("downloadAllRecords", "false"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserReportsControllerTest :: testDownloadSpecificUserStatementReportException",
          e);

    }
  }

  @Test
  public void testGetSettlementReport() {
    transactionsListResponse = new GetTransactionsListResponse();
    transactionsListResponse.setTransactionList(transactionsList);
    try {
      Mockito
          .when(settlementReportService
              .searchSettlementReportTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionsListResponse);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_GET_SETTLEMENT_REPORT));
    } catch (Exception e) {
      logger.error("ERROR :: UserReportsControllerTest :: testGetSettlementReport", e);

    }
  }

  @Test
  public void testGetSettlementReportException() {
    try {
      Mockito
          .when(settlementReportService
              .searchSettlementReportTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_GET_SETTLEMENT_REPORT));
    } catch (Exception e) {
      logger.error("ERROR :: UserReportsControllerTest :: testGetSettlementReportException", e);

    }
  }

  @Test
  public void testDownloadAllUsersExecutedTransactionsNoExistingFeaturesAdmin() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ALL_ACCOUNTS_EXECUTED_TRANSACTIONS)
              .sessionAttr("loginUserType", "Admin").sessionAttr("existingFeatures", "notExist")
              .param("userType", "Admin").param("getMerchantCode", "getMerchantCode"))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserReportsControllerTest :: testDownloadAllUsersExecutedTransactionsNoExistingFeaturesAdmin",
          e);

    }
  }

  @Test
  public void testDownloadAllUsersExecutedTransactionsNoExistingFeaturesReseller() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.ALL_ACCOUNTS_EXECUTED_TRANSACTIONS)
              .sessionAttr("loginUserType", "reseller").sessionAttr("existingFeatures", "notExist")
              .param("userType", "reseller").param("getMerchantCode", "getMerchantCode"))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: UserReportsControllerTest :: testDownloadAllUsersExecutedTransactionsNoExistingFeaturesReseller",
          e);

    }
  }

}
