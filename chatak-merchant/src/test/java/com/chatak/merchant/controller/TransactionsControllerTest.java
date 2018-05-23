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
import com.chatak.merchant.exception.ChatakPayException;
import com.chatak.merchant.model.GetMerchantDetailsResponse;
import com.chatak.merchant.model.GetTransactionResponse;
import com.chatak.merchant.service.AccountService;
import com.chatak.merchant.service.RestPaymentService;
import com.chatak.merchant.service.TransactionService;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)

public class TransactionsControllerTest {

  private static Logger logger = Logger.getLogger(VirtualTerminalController.class);

  @InjectMocks
  TransactionsController transactionsController = new TransactionsController();

  @Mock
  MessageSource messageSource;

  @Mock
  private TransactionService transactionService;

  @Mock
  private RestPaymentService paymentService;

  @Mock
  AccountService accountService;

  private MockMvc mockMvc;

  @Mock
  PGAccount accountDetails;

  @Mock
  GetMerchantDetailsResponse merchantDetailsResponse;

  @Mock
  GetTransactionsListResponse transactionsListResponse;

  @Mock
  GetTransactionsListRequest getTransactionListRequest;

  @Mock
  Transaction transaction;

  @Mock
  GetTransactionResponse getTransactionResponse;



  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(transactionsController).setViewResolvers(viewResolver)
        .build();
  }

  @Before
  public void pro() {
    java.util.Properties properties = new java.util.Properties();
    properties.setProperty("merchant.services.reports.merchant.transaction.revenue.feature.id",
        "notExist");
    properties.setProperty("merchant.services.reports.merchant.transaction.revenue.feature.id",
        "exist");
    Properties.mergeProperties(properties);
  }

  @Test
  public void testShowSearchTransactionPageExistingFeature() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsControllerTest :: testShowSearchTransactionPageExistingFeature:",
          e);
    }
  }

  @Test
  public void testShowSearchTransactionPage() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setMerchantId("1234l");
    try {
      Mockito.when(accountService.getAccountDetailsByEntityId(Matchers.anyString()))
          .thenReturn(accountDetails);
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(transactionService.findMerchantFeeByMerchantId(Matchers.anyString()))
          .thenReturn(Long.parseLong("12"));
      Mockito
          .when(
              transactionService.searchTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionsListResponse);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist").param("req-from", "dash-board")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testShowSearchTransactionPage:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testShowSearchTransactionPageException() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setMerchantId("1234l");
    try {
      Mockito.when(accountService.getAccountDetailsByEntityId(Matchers.anyString()))
          .thenReturn(accountDetails);
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(NullPointerException.class);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_SEARCH_TRANSACTION_PAGE)
          .sessionAttr(Constants.EXISTING_FEATURES, "exist")

          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testShowSearchTransactionPageException:",
          e);
    }
  }

  @Test
  public void testSearchTransactions() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_SEARCH_TRANSACTION)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testSearchTransactions:", e);
    }
  }

  @Test
  public void testSearchTransactionsExistingFeature() {
    try {
      List<Transaction> transactionList = new ArrayList<>();
      transaction = new Transaction();
      transaction.setEntryMode("NORMAL");
      transaction.setAcqChannel("acqChannel");
      transactionList.add(transaction);
      transactionsListResponse.setTransactionList(transactionList);
      merchantDetailsResponse.setMerchantId("1234l");
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(transactionService.getAllTransactions(Matchers.anyString()))
          .thenReturn(transactionsListResponse);
      Mockito.when(transactionService.findMerchantFeeByMerchantId(Matchers.anyString()))
          .thenReturn(Long.parseLong("12"));
      Mockito.when(accountService.getAccountDetailsByEntityId(Matchers.anyString()))
          .thenReturn(accountDetails);
      Mockito
          .when(
              transactionService.searchTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionsListResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_SEARCH_TRANSACTION)
              .header(Constants.REFERER, Constants.REFERER).param("merchantId", "12l")
              .sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testSearchTransactionsExistingFeature:",
          e);

    }
  }


  @Test
  public void testSearchTransactionsExistingFeatureNotExist() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_SEARCH_TRANSACTION)
              .header(Constants.REFERER, Constants.REFERER).sessionAttr(Constants.EXISTING_FEATURES, "notExist"))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsControllerTest :: testSearchTransactionsExistingFeatureNotExist:",
          e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testSearchTransactionsException() {
    try {

      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(NullPointerException.class);

      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_SEARCH_TRANSACTION)
              .header(Constants.REFERER, Constants.REFERER).param("merchantId", "12l")
              .sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testSearchTransactionsException:", e);
    }
  }

  @Test
  public void testGetPaginationList() {
    try {
      Mockito
          .when(
              transactionService.searchTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionsListResponse);
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_PAGINATION)
          .header(Constants.REFERER, Constants.REFERER)
          .sessionAttr("transactionReqObject", getTransactionListRequest)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testGetPaginationList:", e);
    }
  }

  @Test
  public void testGetPaginationListRequestHeader() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_TRANSACTION_PAGINATION)
          .sessionAttr("transactionReqObject", getTransactionListRequest)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testGetPaginationListRequestHeader:", e);

    }
  }

  @Test
  public void testshowProcessingTransactions() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionsListResponse);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testshowProcessingTransactions:", e);
    }
  }

  @Test
  public void testshowProcessingTransactionsExistingFeatureNotExist() {
    try {

      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS)
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist"))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsControllerTest :: testshowProcessingTransactionsExistingFeatureNotExist:",
          e);
    }
  }

  @Test
  public void testshowProcessingTransactionsElse() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(null);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testshowProcessingTransactionsElse:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testshowProcessingTransactionsException() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow((NullPointerException.class));

      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsControllerTest :: testshowProcessingTransactionsException:", e);
    }
  }

  @Test
  public void testProcessingTransactionsPaginationReferer() {
    try {
      mockMvc.perform(
          post("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_PAGINATION)

              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsControllerTest :: testProcessingTransactionsPaginationReferer:", e);
    }
  }

  @Test
  public void testProcessingTransactionsPagination() {
    merchantDetailsResponse.setMerchantId("1234l");
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionsListResponse);
      mockMvc
          .perform(
              post("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_PAGINATION)
                  .header(Constants.REFERER, Constants.REFERER).param("pageNumber", "10")
                  .param(Constants.TOTAL_RECORDS, "12").sessionAttr("processingTxnList", "processingTxnList")
                  .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testProcessingTransactionsPagination:",
          e);
    }
  }

  @Test
  public void testProcessingTransactionMerchantIdNull() {
    try {
      mockMvc
          .perform(
              post("/" + URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS_PAGINATION)
                  .header(Constants.REFERER, Constants.REFERER)
                  .sessionAttr("processingTxnList", "processingTxnList"))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_PROCESSING_TRANSACTIONS));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsControllerTest :: testProcessingTransactionMerchantIdNull:", e);
    }
  }

  @Test
  public void testShowExecutedTransactionsExistingFeature() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS)
              .sessionAttr(Constants.EXISTING_FEATURES, "notexist"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsControllerTest :: testShowExecutedTransactionsExistingFeature:", e);
    }
  }

  @Test
  public void testShowExecutedTransactions() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionsListResponse);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS)
              .header(Constants.REFERER, Constants.REFERER).sessionAttr("processingTxnList", "processingTxnList")
              .sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testShowExecutedTransactions:", e);
    }
  }

  @Test
  public void testShowExecutedTransactionsElse() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS)
              .header(Constants.REFERER, Constants.REFERER).sessionAttr("processingTxnList", "processingTxnList")
              .sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testShowExecutedTransactionsElse:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testShowExecutedTransactionsElseException() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(NullPointerException.class);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS)
              .header(Constants.REFERER, Constants.REFERER).sessionAttr("processingTxnList", "processingTxnList")
              .sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsControllerTest :: testShowExecutedTransactionsElseException:", e);
    }
  }

  @Test
  public void testExecutedTransactionsPaginationExistingFeature() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_PAGINATION)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsControllerTest :: testExecutedTransactionsPaginationExistingFeature:",
          e);
    }
  }

  @Test
  public void testExecutedTransactionsPagination() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionsListResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_PAGINATION)
              .header(Constants.REFERER, Constants.REFERER).param("pageNumber", "12").param(Constants.TOTAL_RECORDS, "25")
              .param(Constants.TOTAL_RECORDS, "10").sessionAttr("executedTxnList", "executedTxnList")
              .sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testExecutedTransactionsPagination:", e);

    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testExecutedTransactionsPaginationException() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenThrow(NullPointerException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_EXECUTED_TRANSACTIONS_PAGINATION)
              .header(Constants.REFERER, Constants.REFERER).sessionAttr("executedTxnList", "executedTxnList")
              .sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsControllerTest :: testExecutedTransactionsPaginationException:", e);

    }
  }

  @Test
  public void testFetchTransaction() {
    getTransactionResponse = new GetTransactionResponse();
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito
          .when(paymentService.getTransaction(Matchers.anyString(), Matchers.anyString(),
              Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
          .thenReturn(getTransactionResponse);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_FETCH_TRAN_ADJUST)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testFetchTransaction:", e);

    }
  }

  @Test
  public void testFetchTransactionException() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito
          .when(paymentService.getTransaction(Matchers.anyString(), Matchers.anyString(),
              Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
          .thenReturn(getTransactionResponse);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_FETCH_TRAN_ADJUST)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testFetchTransactionException:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testFetchTransactionChatakPayException() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito
          .when(paymentService.getTransaction(Matchers.anyString(), Matchers.anyString(),
              Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
          .thenThrow(ChatakPayException.class);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_FETCH_TRAN_ADJUST)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testFetchTransactionChatakPayException:",
          e);
    }
  }

  @Test
  public void testFetchTransactionbyRefId() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.getTransactionByRefId(Matchers.anyString(), Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString())).thenReturn(getTransactionResponse);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_FETCH_TRAN)
          .param("txnType", "sale").sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testFetchTransactionbyRefId:", e);
    }
  }

  @Test
  public void testFetchTransactionbyRefIdVoid() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.getTransactionByRefId(Matchers.anyString(), Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString())).thenReturn(getTransactionResponse);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_FETCH_TRAN)
          .param("txnType", "void").sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testFetchTransactionbyRefIdVoid:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testFetchTransactionbyRefIdChatakPayException() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(ChatakPayException.class);

      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_FETCH_TRAN)
          .param("txnType", "sale").sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsControllerTest :: testFetchTransactionbyRefIdChatakPayException:",
          e);
    }
  }
}
