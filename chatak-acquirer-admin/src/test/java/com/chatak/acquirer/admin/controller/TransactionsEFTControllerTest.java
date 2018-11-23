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
import com.chatak.acquirer.admin.service.FundTransfersService;
import com.chatak.acquirer.admin.service.RestPaymentService;
import com.chatak.acquirer.admin.service.SettlementService;
import com.chatak.acquirer.admin.service.TransactionService;
import com.chatak.pg.bean.LitleEFTRequest;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.LitleEFTDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Response;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jan 2, 2018 8:03:49 PM
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class TransactionsEFTControllerTest {

  private static Logger logger = Logger.getLogger(TransactionsEFTControllerTest.class);

  @InjectMocks
  TransactionsEFTController controller = new TransactionsEFTController();

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
  private FundTransfersService fundTransfersService;

  @Mock
  private TransactionService transactionService;

  @Mock
  private SettlementService settlementService;

  @Mock
  RestPaymentService paymentService;

  @Mock
  private List<LitleEFTDTO> litleEFTDTOs;

  @Mock
  private LitleEFTDTO litleEFTDTO;

  @Mock
  private LitleEFTRequest litleEFTRequest;

  @Mock
  private Response response;

  @Mock
  private GetTransactionsListRequest transactionsListRequest;

  @Mock
  private TransactionsController transactionsController;

  @Mock
  private GetTransactionsListResponse transactionsListResponse;

  @Mock
  private List<Transaction> transactionsList;

  @Mock
  private Transaction transaction;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
  }

  @Test
  public void testGetLitleEFTTransactionListToDashBoard() {
    litleEFTDTOs = new ArrayList<>();
    litleEFTDTO = new LitleEFTDTO();
    litleEFTDTO.setMerchantCode("merchantCode");
    litleEFTDTOs.add(litleEFTDTO);
    litleEFTRequest = new LitleEFTRequest();
    litleEFTRequest.setLitleEFTDTOs(litleEFTDTOs);
    litleEFTRequest.setNoOfRecords(Integer.parseInt("1"));
    try {
      Mockito.when(fundTransfersService.getLitleExecutedTransactionsCount())
          .thenReturn(Integer.parseInt("1"));
      Mockito
          .when(fundTransfersService
              .getLitleExecutedTransactions(Matchers.any(LitleEFTRequest.class)))
          .thenReturn(litleEFTRequest);
      mockMvc
          .perform(get("/" + URLMappingConstants.LITLE_EFT_EXECUTED_TRANSACTIONS)
              .sessionAttr("litleEFTDTOs", litleEFTDTOs))
          .andExpect(view().name(URLMappingConstants.LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testGetLitleEFTTransactionListToDashBoard", e);

    }
  }

  @Test
  public void testGetLitleEFTTransactionListToDashBoardException() {
    try {
      Mockito.when(fundTransfersService.getLitleExecutedTransactionsCount())
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.LITLE_EFT_EXECUTED_TRANSACTIONS)
              .sessionAttr("litleEFTDTOs", litleEFTDTOs))
          .andExpect(view().name(URLMappingConstants.LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testGetLitleEFTTransactionListToDashBoardException",
          e);

    }
  }

  @Test
  public void testGetPaginationListToEFTTransactions() {
    litleEFTDTOs = new ArrayList<>();
    litleEFTDTO = new LitleEFTDTO();
    litleEFTDTO.setMerchantCode("merchantCode");
    litleEFTDTOs.add(litleEFTDTO);
    litleEFTRequest = new LitleEFTRequest();
    litleEFTRequest.setLitleEFTDTOs(litleEFTDTOs);
    litleEFTRequest.setNoOfRecords(Integer.parseInt("1"));
    try {
      Mockito.when(fundTransfersService.getLitleExecutedTransactionsCount())
          .thenReturn(Integer.parseInt("1"));
      Mockito.when(fundTransfersService.getLitleExecutedTransactions(litleEFTRequest))
          .thenReturn(litleEFTRequest);
      mockMvc
          .perform(post("/" + URLMappingConstants.LITLE_EFT_EXECUTED_TRANSACTIONS_PAGINATION)
              .sessionAttr("litleEFTRequest", litleEFTRequest).param(Constants.PAGE_NUMBER, "1"))
          .andExpect(view().name(URLMappingConstants.LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testGetPaginationListToEFTTransactions", e);

    }
  }

  @Test
  public void testGetPaginationListToEFTTransactionsException() {
    try {
      Mockito.when(fundTransfersService.getLitleExecutedTransactionsCount())
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.LITLE_EFT_EXECUTED_TRANSACTIONS_PAGINATION)
              .sessionAttr("litleEFTRequest", litleEFTRequest).param(Constants.PAGE_NUMBER, "1"))
          .andExpect(view().name(URLMappingConstants.LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testGetPaginationListToEFTTransactionsException",
          e);

    }
  }

  @Test
  public void testProcessBulkLitleEFTActionSuccess() {
    response = new Response();
    response.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(fundTransfersService.processLitleEFT(Matchers.anyList())).thenReturn(response);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_BULK_EFT_ACTION))
          .andExpect(view().name(URLMappingConstants.LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsEFTControllerTest :: testProcessBulkLitleEFTActionSuccess",
          e);

    }
  }

  @Test
  public void testProcessBulkLitleEFTActionFailed() {
    response = new Response();
    response.setErrorCode(Constants.ERROR_CODE);
    try {
      Mockito.when(fundTransfersService.processLitleEFT(Matchers.anyList())).thenReturn(response);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_BULK_EFT_ACTION))
          .andExpect(view().name(URLMappingConstants.LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsEFTControllerTest :: testProcessBulkLitleEFTActionFailed",
          e);

    }
  }

  @Test
  public void testProcessBulkLitleEFTActionChatakAdminException() {
    response = new Response();
    response.setErrorCode(Constants.ERROR_CODE);
    try {
      Mockito.when(fundTransfersService.processLitleEFT(Matchers.anyList()))
          .thenThrow(ChatakAdminException.class);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_BULK_EFT_ACTION))
          .andExpect(view().name(URLMappingConstants.LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testProcessBulkLitleEFTActionChatakAdminException",
          e);

    }
  }

  @Test
  public void testProcessBulkLitleEFTActionException() {
    response = new Response();
    response.setErrorCode(Constants.ERROR_CODE);
    try {
      Mockito.when(fundTransfersService.processLitleEFT(Matchers.anyList()))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_BULK_EFT_ACTION))
          .andExpect(view().name(URLMappingConstants.LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testProcessBulkLitleEFTActionException", e);

    }
  }

  @Test
  public void testProcessDashBoardEFTTransfersActionSuccess() {
    response = new Response();
    response.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(fundTransfersService.processIndividualLitleEFT(Matchers.any(LitleEFTDTO.class)))
          .thenReturn(response);

      mockMvc.perform(post("/" + URLMappingConstants.DASH_BOARD_EFT_SETTLEMENT_ACTION))
          .andExpect(view().name(URLMappingConstants.LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testProcessDashBoardEFTTransfersActionSuccess",
          e);

    }
  }

  @Test
  public void testProcessDashBoardEFTTransfersActionFailure() {
    response = new Response();
    response.setErrorCode(Constants.ERROR_CODE);
    try {
      Mockito.when(fundTransfersService.processIndividualLitleEFT(Matchers.any(LitleEFTDTO.class)))
          .thenReturn(response);

      mockMvc.perform(post("/" + URLMappingConstants.DASH_BOARD_EFT_SETTLEMENT_ACTION))
          .andExpect(view().name(URLMappingConstants.LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testProcessDashBoardEFTTransfersActionFailure",
          e);

    }
  }

  @Test
  public void testProcessDashBoardEFTTransfersActionChatakAdminException() {
    try {
      Mockito.when(fundTransfersService.processIndividualLitleEFT(Matchers.any(LitleEFTDTO.class)))
          .thenThrow(ChatakAdminException.class);

      mockMvc.perform(post("/" + URLMappingConstants.DASH_BOARD_EFT_SETTLEMENT_ACTION))
          .andExpect(view().name(URLMappingConstants.LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testProcessDashBoardEFTTransfersActionChatakAdminException",
          e);

    }
  }

  @Test
  public void testProcessDashBoardEFTTransfersActionException() {
    try {
      Mockito.when(fundTransfersService.processIndividualLitleEFT(Matchers.any(LitleEFTDTO.class)))
          .thenThrow(nullPointerException);

      mockMvc.perform(post("/" + URLMappingConstants.DASH_BOARD_EFT_SETTLEMENT_ACTION))
          .andExpect(view().name(URLMappingConstants.LITLE_EFT_EXECUTED_TRANSACTIONS_SHOW));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testProcessDashBoardEFTTransfersActionException",
          e);

    }
  }

  @Test
  public void testDownloadEFTTransactionsFromDashBoardPDF() {
    litleEFTDTOs = new ArrayList<>();
    litleEFTDTO = new LitleEFTDTO();
    litleEFTDTO.setMerchantCode("merchantCode");
    litleEFTDTO.setAmount(1l);
    litleEFTDTOs.add(litleEFTDTO);
    litleEFTRequest = new LitleEFTRequest();
    litleEFTRequest.setLitleEFTDTOs(litleEFTDTOs);
    litleEFTRequest.setNoOfRecords(Integer.parseInt("1"));
    try {

      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_EFT_TRANSACTIONS_FROM_DASHBOARD)
          .param(Constants.DOWNLOAD_TYPE, "PDF").sessionAttr("litleEFTExecutedTransList", litleEFTDTOs));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testDownloadEFTTransactionsFromDashBoardPDF",
          e);

    }
  }

  @Test
  public void testDownloadEFTTransactionsFromDashBoardXLS() {
    litleEFTDTOs = new ArrayList<>();
    litleEFTDTO = new LitleEFTDTO();
    litleEFTDTO.setMerchantCode("merchantCode");
    litleEFTDTOs.add(litleEFTDTO);
    litleEFTRequest = new LitleEFTRequest();
    litleEFTRequest.setLitleEFTDTOs(litleEFTDTOs);
    litleEFTRequest.setNoOfRecords(Integer.parseInt("1"));
    try {

      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_EFT_TRANSACTIONS_FROM_DASHBOARD)
          .param(Constants.DOWNLOAD_TYPE, "XLS").sessionAttr("litleEFTExecutedTransList", litleEFTDTOs));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testDownloadEFTTransactionsFromDashBoardXLS",
          e);

    }
  }

  @Test
  public void testDownloadEFTTransactionsFromDashBoardException() {
    litleEFTDTOs = new ArrayList<>();
    litleEFTDTO = new LitleEFTDTO();
    litleEFTDTO.setMerchantCode("merchantCode");
    litleEFTDTOs.add(litleEFTDTO);
    litleEFTRequest = new LitleEFTRequest();
    litleEFTRequest.setLitleEFTDTOs(litleEFTDTOs);
    litleEFTRequest.setNoOfRecords(Integer.parseInt("1"));
    try {

      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_EFT_TRANSACTIONS_FROM_DASHBOARD)
          .param(Constants.DOWNLOAD_TYPE, "PDF").sessionAttr("litleEFTExecutedTransList", litleEFTDTOs));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testDownloadEFTTransactionsFromDashBoardException",
          e);

    }
  }

  @Test
  public void testProcessSettlementAction() {
    transactionsListRequest = new GetTransactionsListRequest();
    transactionsListRequest.setPageIndex(Integer.parseInt("1"));
    transactionsListRequest.setNoOfRecords(Integer.parseInt("1"));
    try {
      Mockito.when(settlementService.updateSettlementStatus(Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(true);

      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SETTLEMRNT_ACTION)
          .sessionAttr("transactionReqObject", transactionsListRequest).param(Constants.PAGE_NUMBER, "1")
          .param("settlementStatus", PGConstants.PG_SETTLEMENT_REJECTED)
          .param(Constants.TOTAL_RECORDS, "1"));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsEFTControllerTest :: testProcessSettlementAction", e);

    }
  }

  @Test
  public void testProcessSettlementActionFailure() {
    transactionsListRequest = new GetTransactionsListRequest();
    transactionsListRequest.setPageIndex(Integer.parseInt("1"));
    transactionsListRequest.setNoOfRecords(Integer.parseInt("1"));
    try {
      Mockito.when(settlementService.updateSettlementStatus(Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(true);

      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SETTLEMRNT_ACTION)
          .sessionAttr("transactionReqObject", transactionsListRequest).param(Constants.PAGE_NUMBER, "1")
          .param("settlementStatus", PGConstants.PG_SETTLEMENT_EXECUTED)
          .param(Constants.TOTAL_RECORDS, "1"));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsEFTControllerTest :: testProcessSettlementActionFailure",
          e);

    }
  }

  @Test
  public void testProcessSettlementActionElse() {
    transactionsListRequest = new GetTransactionsListRequest();
    transactionsListRequest.setPageIndex(Integer.parseInt("1"));
    transactionsListRequest.setNoOfRecords(Integer.parseInt("1"));
    try {
      Mockito.when(settlementService.updateSettlementStatus(Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(false);

      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SETTLEMRNT_ACTION)
          .sessionAttr("transactionReqObject", transactionsListRequest).param(Constants.PAGE_NUMBER, "1")
          .param("settlementStatus", PGConstants.PG_SETTLEMENT_EXECUTED)
          .param(Constants.TOTAL_RECORDS, "1"));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsEFTControllerTest :: testProcessSettlementActionElse", e);

    }
  }

  @Test
  public void testProcessSettlementActionChatakAdminException() {
    transactionsListRequest = new GetTransactionsListRequest();
    transactionsListRequest.setPageIndex(Integer.parseInt("1"));
    transactionsListRequest.setNoOfRecords(Integer.parseInt("1"));
    try {
      Mockito.when(settlementService.updateSettlementStatus(Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenThrow(ChatakAdminException.class);

      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SETTLEMRNT_ACTION)
          .sessionAttr("transactionReqObject", transactionsListRequest).param(Constants.PAGE_NUMBER, "1")
          .param("settlementStatus", PGConstants.PG_SETTLEMENT_EXECUTED)
          .param(Constants.TOTAL_RECORDS, "1"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testProcessSettlementActionChatakAdminException",
          e);

    }
  }

  @Test
  public void testSearchTransactionsBySettlementType() {
    transactionsListResponse = new GetTransactionsListResponse();
    transaction = new Transaction();
    transaction.setAccountNumber(1l);
    transactionsList.add(transaction);
    transactionsListResponse.setTransactionList(transactionsList);
    transactionsListResponse.setTotalResultCount(Integer.parseInt("1"));
    try {
      Mockito
          .when(transactionService
              .searchTransactionsBySettlementStatus(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionsListResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION_ON_STATUS))
          .andExpect(
              view().name(URLMappingConstants.CHATAK_ADMIN_DASH_BOARD_SEARCH_TRANSACTION_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testSearchTransactionsBySettlementType", e);

    }
  }

  @Test
  public void testSearchTransactionsBySettlementTypeElse() {
    transactionsListResponse = new GetTransactionsListResponse();
    transactionsListResponse.setTotalResultCount(Integer.parseInt("1"));
    try {
      Mockito
          .when(transactionService
              .searchTransactionsBySettlementStatus(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionsListResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION_ON_STATUS))
          .andExpect(
              view().name(URLMappingConstants.CHATAK_ADMIN_DASH_BOARD_SEARCH_TRANSACTION_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testSearchTransactionsBySettlementTypeElse",
          e);

    }
  }

  @Test
  public void testSearchTransactionsBySettlementTypeException() {
    try {
      Mockito
          .when(transactionService
              .searchTransactionsBySettlementStatus(Matchers.any(GetTransactionsListRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_TRANSACTION_ON_STATUS))
          .andExpect(
              view().name(URLMappingConstants.CHATAK_ADMIN_DASH_BOARD_SEARCH_TRANSACTION_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testSearchTransactionsBySettlementTypeException",
          e);

    }
  }

  @Test
  public void testFetchPartialRefundBalance() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PARTIAL_REFUND_BALANCE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsEFTControllerTest :: testFetchPartialRefundBalance", e);

    }
  }

  @Test
  public void testFetchTransactionPopupDetails() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_TRANSACTION_POPUP_DETAILS));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsEFTControllerTest :: testFetchTransactionPopupDetails", e);

    }
  }

  @Test
  public void testGetPaginationListToDashBoard() {
    transactionsListResponse = new GetTransactionsListResponse();
    transaction = new Transaction();
    transaction.setAccountNumber(1l);
    transactionsList.add(transaction);
    transactionsListResponse.setTransactionList(transactionsList);
    transactionsListResponse.setTotalResultCount(Integer.parseInt("1"));
    try {
      Mockito
          .when(transactionService
              .searchTransactionsBySettlementStatus(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(transactionsListResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_TRANSACTION_PAGINATION_ON_STATUS)
          .sessionAttr("transactionReqObject", transactionsListRequest).param(Constants.PAGE_NUMBER, "1"))
          .andExpect(
              view().name(URLMappingConstants.CHATAK_ADMIN_DASH_BOARD_SEARCH_TRANSACTION_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: TransactionsEFTControllerTest :: testGetPaginationListToDashBoard", e);

    }
  }

  @Test
  public void testGetPaginationListToDashBoardException() {
    try {
      Mockito
          .when(transactionService
              .searchTransactionsBySettlementStatus(Matchers.any(GetTransactionsListRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_TRANSACTION_PAGINATION_ON_STATUS)
          .sessionAttr("transactionReqObject", transactionsListRequest).param(Constants.PAGE_NUMBER, "1"))
          .andExpect(
              view().name(URLMappingConstants.CHATAK_ADMIN_DASH_BOARD_SEARCH_TRANSACTION_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testGetPaginationListToDashBoardException", e);

    }
  }

  @Test
  public void testProcessDashBoardSettlementActionSuccess() {
    try {
      Mockito.when(settlementService.updateSettlementStatus(Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(true);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_DASH_BOARD_SETTLEMRNT_ACTION)
          .sessionAttr("transactionReqObject", transactionsListRequest));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testProcessDashBoardSettlementActionSuccess",
          e);
    }
  }

  @Test
  public void testProcessDashBoardSettlementActionFail() {
    try {
      Mockito.when(settlementService.updateSettlementStatus(Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(false);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_DASH_BOARD_SETTLEMRNT_ACTION)
          .sessionAttr("transactionReqObject", transactionsListRequest));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testProcessDashBoardSettlementActionFail", e);
    }
  }

  @Test
  public void testProcessDashBoardSettlementActionChatakAdminException() {
    try {
      Mockito.when(settlementService.updateSettlementStatus(Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenThrow(ChatakAdminException.class);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_DASH_BOARD_SETTLEMRNT_ACTION)
          .sessionAttr("transactionReqObject", transactionsListRequest));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testProcessDashBoardSettlementActionChatakAdminException",
          e);
    }
  }

  @Test
  public void testProcessDashBoardSettlementActionException() {
    try {
      Mockito.when(settlementService.updateSettlementStatus(Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_DASH_BOARD_SETTLEMRNT_ACTION)
          .sessionAttr("transactionReqObject", transactionsListRequest));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testProcessDashBoardSettlementActionException",
          e);
    }
  }

  @Test
  public void testDownloadTransactionsFromDashBoardPDF() {
    transactionsList = new ArrayList<>();
    transaction = new Transaction();
    transaction.setAccountNumber(1l);
    transaction.setAcqChannel("acqChannel");
    transaction.setRef_transaction_id(1l);
    transactionsList.add(transaction);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_TRANSACTIONS_FROM_DASHBOARD)
          .sessionAttr("transactionsFromDashBoard", transactionsList).param(Constants.DOWNLOAD_TYPE, "PDF"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testDownloadTransactionsFromDashBoardPDF", e);
    }
  }

  @Test
  public void testDownloadTransactionsFromDashBoardXLS() {
    transactionsList = new ArrayList<>();
    transaction = new Transaction();
    transaction.setAccountNumber(1l);
    transaction.setAcqChannel("acqChannel");
    transaction.setRef_transaction_id(1l);
    transactionsList.add(transaction);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_TRANSACTIONS_FROM_DASHBOARD)
          .sessionAttr("transactionsFromDashBoard", transactionsList).param(Constants.DOWNLOAD_TYPE, "XLS"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: TransactionsEFTControllerTest :: testDownloadTransactionsFromDashBoardXLS", e);
    }
  }

}
