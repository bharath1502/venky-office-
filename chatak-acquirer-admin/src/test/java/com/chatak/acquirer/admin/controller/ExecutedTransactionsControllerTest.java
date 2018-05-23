package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.service.TransactionService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class ExecutedTransactionsControllerTest {

  private static Logger logger = Logger.getLogger(ExecutedTransactionsControllerTest.class);

  @InjectMocks
  ExecutedTransactionsController executedTransactionsController =
      new ExecutedTransactionsController();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  private Response responseval;

  @Mock
  BindingResult bindingResult;

  @Mock
  private Option option;

  @Mock
  private List<Option> optionList;

  private MockMvc mockMvc;

  @Mock
  private MessageSource messageSource;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  private LoginDetails loginDetails;

  @Mock
  private TransactionService transactionService;

  @Mock
  private GetTransactionsListRequest getTransactionsListRequest;

  @Mock
  private GetTransactionsListResponse getTransactionsListResponse;

  @Mock
  private AccountTransactionDTO accountTransactionDTO;

  @Mock
  private List<AccountTransactionDTO> accountTxnsList;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(executedTransactionsController)
        .setViewResolvers(viewResolver).build();
    optionList = new ArrayList<>();
    option = new Option();
  }

  @Test
  public void testShowExecutedTransactions() {
    try {
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(getTransactionsListResponse);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS));
    } catch (Exception e) {
      logger.error("ExecutedTransactionsControllerTest | testShowExecutedTransactions | Exception ",
          e);
    }
  }

  @Test
  public void testShowExecutedTransactionsException() {
    try {
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ExecutedTransactionsControllerTest | testShowExecutedTransactionsException | Exception ",
          e);
    }
  }

  @Test
  public void testExecutedTransactionsPagination() {
    try {
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(getTransactionsListResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_PAGINATION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param(Constants.PAGE_NUMBER, Constants.ONE.toString())
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS));
    } catch (Exception e) {
      logger.error(
          "ExecutedTransactionsControllerTest | testExecutedTransactionsPagination | Exception ",
          e);
    }
  }

  @Test
  public void testExecutedTransactionsPaginationException() {
    try {
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_PAGINATION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param(Constants.PAGE_NUMBER, Constants.ONE.toString())
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ExecutedTransactionsControllerTest | testExecutedTransactionsPaginationException | Exception ",
          e);
    }
  }

  @Test
  public void testExecutedTransactionsReport() {
    try {
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(getTransactionsListResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_REPORT)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.EXECUTED_TXN_LIST, accountTxnsList)
          .param("downLoadPageNumber", Constants.ONE.toString())
          .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param("downloadType", "XLS")
          .param("requestFrom", "dashobard").param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "ExecutedTransactionsControllerTest | testExecutedTransactionsReport | Exception ", e);
    }
  }

  @Test
  public void testExecutedTransactionsReportPDF() {
    try {
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(getTransactionsListResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_REPORT)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.EXECUTED_TXN_LIST, accountTxnsList)
          .param("downLoadPageNumber", Constants.ONE.toString())
          .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param("downloadType", "PDF")
          .param("requestFrom", "dashobard").param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "ExecutedTransactionsControllerTest | testExecutedTransactionsReportPDF | Exception ", e);
    }
  }

  @Test
  public void testExecutedTransactionsReportException() {
    try {
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_REPORT)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.EXECUTED_TXN_LIST, accountTxnsList)
          .param("downLoadPageNumber", Constants.ONE.toString())
          .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param("downloadType", "PDF")
          .param("requestFrom", "dashobard").param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "ExecutedTransactionsControllerTest | testExecutedTransactionsReportException | Exception ",
          e);
    }
  }

  @Test
  public void testShowProcessingTransactions() {
    try {
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(getTransactionsListResponse);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.EXECUTED_TXN_LIST, accountTxnsList).header(Constants.REFERER, Constants.REFERER))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS));
    } catch (Exception e) {
      logger.error(
          "ExecutedTransactionsControllerTest | testShowProcessingTransactions | Exception ", e);
    }
  }

  @Test
  public void testShowProcessingTransactionsException() {
    try {
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.EXECUTED_TXN_LIST, accountTxnsList).header(Constants.REFERER, Constants.REFERER))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ExecutedTransactionsControllerTest | testShowProcessingTransactionsException | Exception ",
          e);
    }
  }

  @Test
  public void testProcessingTransactionsPagination() {
    try {
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(getTransactionsListResponse);
      mockMvc.perform(
          post("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_PAGINATION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.EXECUTED_TXN_LIST, accountTxnsList)
              .param(Constants.PAGE_NUMBER, Constants.ONE.toString())
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()));
    } catch (Exception e) {
      logger.error(
          "ExecutedTransactionsControllerTest | testProcessingTransactionsPagination | Exception ",
          e);
    }
  }

  @Test
  public void testProcessingTransactionsPaginationException() {
    try {
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(
          post("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_PAGINATION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.EXECUTED_TXN_LIST, accountTxnsList)
              .param(Constants.PAGE_NUMBER, Constants.ONE.toString())
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()));
    } catch (Exception e) {
      logger.error(
          "ExecutedTransactionsControllerTest | testProcessingTransactionsPaginationException | Exception ",
          e);
    }
  }

  @Test
  public void testProcessingTransactionsReport() {
    try {
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(getTransactionsListResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_REPORT)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.PROCESSING_TXN_LIST, accountTxnsList)
          .param("downLoadPageNumber", Constants.ONE.toString())
          .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param("downloadType", "PDF")
          .param("requestFrom", "dashobard").param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "ExecutedTransactionsControllerTest | testProcessingTransactionsReport | Exception ", e);
    }
  }

  @Test
  public void testProcessingTransactionsReportXLS() {
    try {
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(getTransactionsListResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_REPORT)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.PROCESSING_TXN_LIST, accountTxnsList)
          .param("downLoadPageNumber", Constants.ONE.toString())
          .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param("downloadType", "XLS")
          .param("requestFrom", "dashobard").param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "ExecutedTransactionsControllerTest | testProcessingTransactionsReportXLS | Exception ",
          e);
    }
  }

  @Test
  public void testProcessingTransactionsReportException() {
    try {
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_REPORT)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.PROCESSING_TXN_LIST, accountTxnsList)
          .param("downLoadPageNumber", Constants.ONE.toString())
          .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param("downloadType", "XLS")
          .param("requestFrom", "dashobard").param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "ExecutedTransactionsControllerTest | testProcessingTransactionsReportException | Exception ",
          e);
    }
  }

}
