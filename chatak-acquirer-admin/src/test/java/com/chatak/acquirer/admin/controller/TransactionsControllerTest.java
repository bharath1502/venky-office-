/**
 * 
 */
package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.junit.After;
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
import com.chatak.acquirer.admin.exception.ChatakPayException;
import com.chatak.acquirer.admin.service.RestPaymentService;
import com.chatak.acquirer.admin.service.SettlementService;
import com.chatak.acquirer.admin.service.TransactionService;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.model.BulkSettlementResponse;
import com.chatak.pg.model.GetMerchantDetailsResponse;
import com.chatak.pg.model.GetTransactionResponse;
import com.chatak.pg.model.SettlementActionDTOList;
import com.chatak.pg.model.SettlemetActionDTO;
import com.chatak.pg.model.TransactionResponse;
import com.chatak.pg.model.VirtualTerminalRefundDTO;
import com.chatak.pg.model.VirtualTerminalVoidDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jan 3, 2018 5:46:39 PM
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class TransactionsControllerTest {

  private static Logger logger = Logger.getLogger(TransactionsControllerTest.class);

  @InjectMocks
  TransactionsController controller = new TransactionsController();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse httpServletResponse;

  @Mock
  BindingResult bindingResult;

  MockMvc mockMvc;

  @Mock
  MessageSource messageSource;

  @Mock
  NullPointerException nullPointerException;

  @Mock
  List<Option> optionList;

  @Mock
  private TransactionService transactionService;

  @Mock
  private SettlementService settlementService;

  @Mock
  private RestPaymentService paymentService;

  @Mock
  private GetTransactionsListResponse transactionsListResponse;

  @Mock
  private List<Transaction> transactionList;

  @Mock
  private Transaction transaction;

  @Mock
  private List<AccountTransactionDTO> accountTransactionList;

  @Mock
  private GetTransactionsListRequest transactionsListRequest;

  @Mock
  private SettlementActionDTOList list;

  @Mock
  private List<SettlemetActionDTO> settlemetActionDTOs;

  @Mock
  private SettlemetActionDTO settlemetActionDTO;

  @Mock
  private BulkSettlementResponse bulkSettlementResponse;

  @Mock
  private GetMerchantDetailsResponse getMerchantDetailsResponse;

  @Mock
  private GetTransactionResponse getTransactionResponse;

  @Mock
  private TransactionResponse transactionResponse;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
  }

  @Test
  public void testShowSearchTransactionsPage() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testShowSearchTransactionsPage", e);

    }
  }

  @Test
  public void testSearchTransactionsSuccess() {
    transaction = new Transaction();
    transaction.setAccountNumber(1l);
    transactionList.add(transaction);
    transactionsListResponse = new GetTransactionsListResponse();
    transactionsListResponse.setTransactionList(transactionList);
    transactionsListResponse.setTotalResultCount(Integer.parseInt("1"));
    try {
      Mockito
          .when(
              transactionService.searchTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionsListResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testSearchTransactionsSuccess", e);

    }
  }

  @Test
  public void testSearchTransactionsFailure() {
    transaction = new Transaction();
    try {
      Mockito
          .when(
              transactionService.searchTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionsListResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testSearchTransactionsFailure", e);

    }
  }

  @Test
  public void testSearchTransactionsException() {
    transaction = new Transaction();
    try {
      Mockito
          .when(
              transactionService.searchTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testSearchTransactionsException", e);

    }
  }

  @Test
  public void testGetPaginationList() {
    transaction = new Transaction();
    transaction.setAccountNumber(1l);
    transactionList.add(transaction);
    transactionsListResponse = new GetTransactionsListResponse();
    transactionsListResponse.setTransactionList(transactionList);
    transactionsListResponse.setTotalResultCount(Integer.parseInt("1"));
    try {
      Mockito
          .when(
              transactionService.searchTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionsListResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_TRANSACTION_PAGINATION)
              .sessionAttr("transactionReqObject", transactionsListRequest)
              .param("pageNumber", "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testGetPaginationList", e);

    }
  }

  @Test
  public void testGetPaginationListException() {
    try {
      Mockito
          .when(
              transactionService.searchTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_TRANSACTION_PAGINATION)
              .sessionAttr("transactionReqObject", transactionsListRequest))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testGetPaginationListException", e);

    }
  }

  @Test
  public void testProcessBulkSettlementAction() {
    list = new SettlementActionDTOList();
    settlemetActionDTO.setComments("comments");
    settlemetActionDTOs = new ArrayList<>();
    settlemetActionDTOs.add(settlemetActionDTO);
    list.setActionDTOs(settlemetActionDTOs);
    bulkSettlementResponse = new BulkSettlementResponse();
    bulkSettlementResponse.setSuccess(true);
    transactionsListRequest.setPageIndex(Integer.parseInt("1"));
    transactionsListRequest.setNoOfRecords(Integer.parseInt("1"));
    try {
      Mockito.when(
          settlementService.updateBulkSettlementStatus(Matchers.any(SettlementActionDTOList.class),
              Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
          .thenReturn(bulkSettlementResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_BULK_SETTLEMRNT_ACTION)
              .sessionAttr("selected_bulk_settlement_list_obj", list)
              .sessionAttr("transactionReqObject", transactionsListRequest)
              .param("removedTxns", "removedTxns"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testDownloadTransactionReport", e);

    }
  }

  @Test
  public void testProcessBulkSettlementActionElse() {
    list = new SettlementActionDTOList();
    settlemetActionDTO.setComments("comments");
    settlemetActionDTOs = new ArrayList<>();
    settlemetActionDTOs.add(settlemetActionDTO);
    list.setActionDTOs(settlemetActionDTOs);
    bulkSettlementResponse = new BulkSettlementResponse();
    bulkSettlementResponse.setSuccess(false);
    transactionsListRequest.setPageIndex(Integer.parseInt("1"));
    transactionsListRequest.setNoOfRecords(Integer.parseInt("1"));
    try {
      Mockito.when(
          settlementService.updateBulkSettlementStatus(Matchers.any(SettlementActionDTOList.class),
              Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
          .thenReturn(bulkSettlementResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_BULK_SETTLEMRNT_ACTION)
              .sessionAttr("selected_bulk_settlement_list_obj", list)
              .sessionAttr("transactionReqObject", transactionsListRequest)
              .param("removedTxns", "removedTxns"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testProcessBulkSettlementActionElse", e);

    }
  }

  @Test
  public void testProcessBulkSettlementActionChatakAdminException() {
    bulkSettlementResponse = new BulkSettlementResponse();
    bulkSettlementResponse.setSuccess(false);
    transactionsListRequest.setPageIndex(Integer.parseInt("1"));
    transactionsListRequest.setNoOfRecords(Integer.parseInt("1"));
    try {
      Mockito.when(
          settlementService.updateBulkSettlementStatus(Matchers.any(SettlementActionDTOList.class),
              Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
          .thenThrow(ChatakAdminException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_BULK_SETTLEMRNT_ACTION)
              .sessionAttr("transactionReqObject", transactionsListRequest)
              .sessionAttr("actionDTOs", settlemetActionDTOs))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsControllerTest :: testProcessBulkSettlementActionChatakAdminException",
          e);

    }
  }

  @Test
  public void testProcessBulkSettlementActionException() {
    bulkSettlementResponse = new BulkSettlementResponse();
    bulkSettlementResponse.setSuccess(false);
    transactionsListRequest.setPageIndex(Integer.parseInt("1"));
    transactionsListRequest.setNoOfRecords(Integer.parseInt("1"));
    try {
      Mockito.when(
          settlementService.updateBulkSettlementStatus(Matchers.any(SettlementActionDTOList.class),
              Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_BULK_SETTLEMRNT_ACTION)
              .sessionAttr("transactionReqObject", transactionsListRequest)
              .param("removedTxns", "removedTxns").sessionAttr("actionDTOs", settlemetActionDTOs))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsControllerTest :: testProcessBulkSettlementActionException", e);

    }
  }

  @Test
  public void testShowTransactionRefund() {
    getMerchantDetailsResponse = new GetMerchantDetailsResponse();
    getTransactionResponse = new GetTransactionResponse();
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(getMerchantDetailsResponse);
      Mockito.when(paymentService.getTransactionByRefId(Matchers.anyString(), Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString())).thenReturn(getTransactionResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_TRANSACTION_REFUND_PAGE))
          .andExpect(view().name(URLMappingConstants.SHOW_CHATAK_ADMIN_TRANSACTION_REFUND));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testShowTransactionRefund", e);

    }
  }

  @Test
  public void testShowTransactionRefundException() {
    getMerchantDetailsResponse = new GetMerchantDetailsResponse();
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_TRANSACTION_REFUND_PAGE))
          .andExpect(view().name(URLMappingConstants.SHOW_CHATAK_ADMIN_TRANSACTION_REFUND));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testShowTransactionRefundException", e);

    }
  }

  @Test
  public void testFetchTransactionForVoid() {
    getMerchantDetailsResponse = new GetMerchantDetailsResponse();
    getTransactionResponse = new GetTransactionResponse();
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(getMerchantDetailsResponse);
      Mockito.when(paymentService.getTransactionByRefId(Matchers.anyString(), Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString())).thenReturn(getTransactionResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_TRANSACTION_VOID_PROCESS))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_TRANSACTION_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testFetchTransactionForVoid", e);

    }
  }

  @Test
  public void testFetchTransactionForVoidException() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_TRANSACTION_VOID_PROCESS))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_TRANSACTION_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testFetchTransactionForVoidException",
          e);

    }
  }

  @Test
  public void testProcessVoid() {
    getMerchantDetailsResponse = new GetMerchantDetailsResponse();
    getMerchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(getMerchantDetailsResponse);
      Mockito.when(paymentService.doVoid(Matchers.any(VirtualTerminalVoidDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_TRANSACTION_VOID)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_TRANSACTION_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testProcessVoid", e);

    }
  }

  @Test
  public void testProcessVoidWrongIDs() {
    getMerchantDetailsResponse = new GetMerchantDetailsResponse();
    getMerchantDetailsResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(getMerchantDetailsResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_TRANSACTION_VOID)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_TRANSACTION_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testProcessVoidWrongIDs", e);

    }
  }

  @Test
  public void testProcessVoidFail() {
    getMerchantDetailsResponse = new GetMerchantDetailsResponse();
    getMerchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(getMerchantDetailsResponse);
      Mockito.when(paymentService.doVoid(Matchers.any(VirtualTerminalVoidDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_TRANSACTION_VOID)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_TRANSACTION_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testProcessVoidFail", e);

    }
  }

  @Test
  public void testProcessVoidNull() {
    getMerchantDetailsResponse = new GetMerchantDetailsResponse();
    getMerchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(getMerchantDetailsResponse);
      Mockito.when(paymentService.doVoid(Matchers.any(VirtualTerminalVoidDTO.class)))
          .thenReturn(null);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_TRANSACTION_VOID)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_TRANSACTION_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testProcessVoidNull", e);

    }
  }

  @Test
  public void testProcessVoidChatakPayException() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(ChatakPayException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_TRANSACTION_VOID)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_TRANSACTION_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testProcessVoidChatakPayException", e);

    }
  }

  @Test
  public void testProcessVoidException() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_TRANSACTION_VOID)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_TRANSACTION_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testProcessVoidException", e);

    }
  }

  @Test
  public void testProcessVoidMidNull() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_TRANSACTION_VOID))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_TRANSACTION_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testProcessVoidMidNull", e);

    }
  }

  @Test
  public void testProcessRefund() {
    getMerchantDetailsResponse = new GetMerchantDetailsResponse();
    getMerchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(getMerchantDetailsResponse);
      Mockito.when(paymentService.doRefund(Matchers.any(VirtualTerminalRefundDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_TRANSACTION_REFUND)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.SHOW_CHATAK_ADMIN_TRANSACTION_REFUND));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testProcessRefund", e);

    }
  }

  @Test
  public void testProcessRefundElse() {
    getMerchantDetailsResponse = new GetMerchantDetailsResponse();
    getMerchantDetailsResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(getMerchantDetailsResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_TRANSACTION_REFUND)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.SHOW_CHATAK_ADMIN_TRANSACTION_REFUND));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testProcessRefundElse", e);

    }
  }

  @Test
  public void testProcessRefundFailed() {
    getMerchantDetailsResponse = new GetMerchantDetailsResponse();
    getMerchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(getMerchantDetailsResponse);
      Mockito.when(paymentService.doRefund(Matchers.any(VirtualTerminalRefundDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_TRANSACTION_REFUND)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.SHOW_CHATAK_ADMIN_TRANSACTION_REFUND));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testProcessRefundFailed", e);

    }
  }

  @Test
  public void testProcessRefundChatakPayException() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(ChatakPayException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_TRANSACTION_REFUND)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.SHOW_CHATAK_ADMIN_TRANSACTION_REFUND));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testProcessRefundChatakPayException", e);

    }
  }

  @Test
  public void testProcessRefundException() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_TRANSACTION_REFUND)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.SHOW_CHATAK_ADMIN_TRANSACTION_REFUND));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testProcessRefundException", e);

    }
  }

  @Test
  public void testProcessRefundInvalidMid() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.PROCESS_CHATAK_ADMIN_TRANSACTION_REFUND))
          .andExpect(view().name(URLMappingConstants.SHOW_CHATAK_ADMIN_TRANSACTION_REFUND));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsControllerTest :: testProcessRefundInvalidMid", e);

    }
  }
}
