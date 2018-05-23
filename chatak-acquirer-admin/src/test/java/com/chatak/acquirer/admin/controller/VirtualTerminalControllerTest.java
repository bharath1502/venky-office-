package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.exception.ChatakPayException;
import com.chatak.acquirer.admin.service.BlackListedCardService;
import com.chatak.acquirer.admin.service.MerchantValidateService;
import com.chatak.acquirer.admin.service.RestPaymentService;
import com.chatak.pg.acq.dao.repository.MerchantConfigRepositrory;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.GetMerchantDetailsResponse;
import com.chatak.pg.model.TransactionResponse;
import com.chatak.pg.model.VirtualTerminalAdjustmentDTO;
import com.chatak.pg.model.VirtualTerminalAdjustmentResponse;
import com.chatak.pg.model.VirtualTerminalCaptureDTO;
import com.chatak.pg.model.VirtualTerminalRefundDTO;
import com.chatak.pg.model.VirtualTerminalSaleDTO;
import com.chatak.pg.model.VirtualTerminalVoidDTO;
import com.chatak.pg.util.Constants;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class VirtualTerminalControllerTest {

  private static Logger logger = Logger.getLogger(VirtualTerminalController.class);

  @InjectMocks
  VirtualTerminalController virtualTerminalController = new VirtualTerminalController();

  @Mock
  RestPaymentService paymentService;

  @Mock
  private MessageSource messageSource;

  @Mock
  private MerchantConfigRepositrory merchantConfigRepositrory;

  private MockMvc mockMvc;

  @Mock
  GetMerchantDetailsResponse merchantDetailsResponse;

  @Mock
  VirtualTerminalAdjustmentDTO terminalAdjustmentDTO;

  @Mock
  VirtualTerminalAdjustmentResponse virtualTerminalAdjustmentResponse;

  @Mock
  TransactionResponse transactionResponse;

  @Mock
  Response response;

  @Mock
  BlackListedCardService blackListedCardService;

  @Mock
  private VirtualTerminalSaleDTO VirtualTerminalSaleDTO;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  private MerchantValidateService merchantValidateService;

  @Before
  public void setup() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(virtualTerminalController)
        .setViewResolvers(viewResolver).build();
  }

  @Test
  public void testShowVirtualterminalSale() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testShowVirtualterminalSale", e);

    }

  }

  @Test
  public void testProcessSaleCardBlocked() {
    response = new Response();
    response.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
    Mockito.when(blackListedCardService.findByCardNumber(Matchers.anyString()))
        .thenReturn(response);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PROCESS)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessSaleCardBlocked:", e);
    }

  }

  @Test
  public void testProcessSale() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    response = new Response();
    response.setErrorCode(Constants.SUCCESS_CODE);
    Mockito.when(blackListedCardService.findByCardNumber(Matchers.anyString()))
        .thenReturn(response);
    Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
        .thenReturn(merchantDetailsResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PROCESS)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("year", "2000")
              .param("month", "05").param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessSale:", e);
    }

  }

  @Test
  public void testProcessSaleWrongID() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.ERROR_CODE);
    response = new Response();
    response.setErrorCode(Constants.SUCCESS_CODE);
    Mockito.when(blackListedCardService.findByCardNumber(Matchers.anyString()))
        .thenReturn(response);
    Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
        .thenReturn(merchantDetailsResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PROCESS)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("year", "2019")
              .param("month", "2").param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessSaleWrongID:", e);
    }

  }

  @Test
  public void testProcessSaleWrongIDChatakPayException() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.ERROR_CODE);
    response = new Response();
    response.setErrorCode(Constants.SUCCESS_CODE);
    Mockito.when(blackListedCardService.findByCardNumber(Matchers.anyString()))
        .thenReturn(response);
    Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
        .thenThrow(ChatakPayException.class);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PROCESS)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("year", "2019")
              .param("month", "2").param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalController :: testProcessSaleWrongIDChatakPayException:", e);
    }

  }

  @Test
  public void testProcessSaleWrongIDException() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.ERROR_CODE);
    response = new Response();
    response.setErrorCode(Constants.SUCCESS_CODE);
    Mockito.when(blackListedCardService.findByCardNumber(Matchers.anyString()))
        .thenReturn(response);
    Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
        .thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PROCESS)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("year", "2019")
              .param("month", "2").param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessSaleWrongIDException:", e);
    }

  }

  @Test
  public void testProcessSaleElse() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PROCESS)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("year", "2019")
              .param("month", "2"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessSaleElse", e);
    }

  }

  @Test
  public void testSetSaleTxnResponse() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    response = new Response();
    response.setErrorCode(Constants.SUCCESS_CODE);
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    Mockito.when(blackListedCardService.findByCardNumber(Matchers.anyString()))
        .thenReturn(response);
    Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
        .thenReturn(merchantDetailsResponse);
    try {
      Mockito.when(paymentService.doSale(Matchers.any(VirtualTerminalSaleDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PROCESS)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("year", "2000")
              .param("month", "05").param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testSetSaleTxnResponse", e);
    }

  }

  @Test
  public void testProcessSaleOnRefresh() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessSaleOnRefresh", e);
    }
  }

  @Test
  public void testFetchTransaction() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_FETCH_TRAN_ADJUST)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testFetchTransaction", e);
    }
  }

  @Test
  public void testFetchTransactionChatakPayException() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(ChatakPayException.class);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_FETCH_TRAN_ADJUST)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testFetchTransactionChatakPayException:",
          e);
    }
  }

  @Test
  public void testFetchTransactionException() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_FETCH_TRAN_ADJUST)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testFetchTransactionException:", e);
    }
  }

  @Test
  public void testFetchTransactionbyRefIdIf() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();

    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_FETCH_TRAN)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("txnType", "sale"));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testFetchTransactionbyRefIdIf", e);
    }
  }

  @Test
  public void testFetchTransactionbyRefIdelse() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();

    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_FETCH_TRAN)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("txnType", "txnType"));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testFetchTransactionbyRefIdelse", e);
    }
  }

  @Test
  public void testFetchTransactionbyRefIdChatakPayException() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();

    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(ChatakPayException.class);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_FETCH_TRAN)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("txnType", "txnType"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalController :: testFetchTransactionbyRefIdChatakPayException", e);
    }
  }

  @Test
  public void testFetchTransactionbyRefIdException() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();

    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_FETCH_TRAN)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("txnType", "txnType"));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testFetchTransactionbyRefIdException", e);
    }
  }

  @Test
  public void testShowVirtualterminalVoid() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testShowVirtualterminalVoid", e);
    }
  }

  @Test
  public void testProcessVoidElse() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessVoidElse", e);
    }
  }

  @Test
  public void testProcessVoidforValidId() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doVoid(Matchers.any(VirtualTerminalVoidDTO.class)))
          .thenReturn(transactionResponse);

      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin").param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessVoidforValidId", e);
    }
  }

  @Test
  public void testProcessVoidforInvalidId() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);

      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin").param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessVoidforInvalidId", e);
    }
  }

  @Test
  public void testProcessVoidforVoidFailure() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doVoid(Matchers.any(VirtualTerminalVoidDTO.class)))
          .thenReturn(transactionResponse);

      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin").param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessVoidforVoidFailure", e);
    }
  }

  @Test
  public void testProcessVoidforChatakpayException() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(ChatakPayException.class);

      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin").param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessVoidforChatakpayException", e);
    }
  }

  @Test
  public void testProcessVoidforException() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(nullPointerException);

      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin").param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessVoidforException", e);
    }
  }

  @Test
  public void testShowVirtualterminalRefund() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testShowVirtualterminalRefund", e);
    }
  }

  @Test
  public void testProcessRefundElse() {
    try {

      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessRefundElse", e);
    }
  }

  @Test
  public void testProcessRefund() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doRefund(Matchers.any(VirtualTerminalRefundDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessRefund", e);
    }
  }

  @Test
  public void testProcessRefundInvalidId() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doRefund(Matchers.any(VirtualTerminalRefundDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessRefundInvalidId", e);
    }
  }

  @Test
  public void testProcessRefundFailed() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doRefund(Matchers.any(VirtualTerminalRefundDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessRefundFailed", e);
    }
  }

  @Test
  public void testProcessRefundChatakPayException() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(ChatakPayException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessRefundChatakPayException", e);
    }
  }

  @Test
  public void testProcessRefundException() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessRefundException", e);
    }
  }

  @Test
  public void testProcessRefundOnRefresh() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PROCESS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_REFUND_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessRefundOnRefresh", e);
    }
  }

  @Test
  public void testshowVirtualterminalPreAuthCompleation() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(
              get("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE)
                  .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view()
              .name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalController :: testshowVirtualterminalPreAuthCompleation", e);
    }
  }

  @Test
  public void testProcessPreAuthCompleationElse() {
    try {
      mockMvc
          .perform(post(
              "/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PROCESS)
                  .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view()
              .name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessPreAuthCompleationElse", e);
    }
  }

  @Test
  public void testProcessPreAuthCompleation() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doPreAuthCapture(Matchers.any(VirtualTerminalCaptureDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post(
              "/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PROCESS)
                  .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
                  .sessionAttr("loginUserId", 1l).param("merchantId", "1"))
          .andExpect(view()
              .name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessPreAuthCompleation", e);
    }
  }

  @Test
  public void testProcessPreAuthCompleationInvalidId() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      mockMvc
          .perform(post(
              "/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PROCESS)
                  .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
                  .sessionAttr("loginUserId", 1l).param("merchantId", "1"))
          .andExpect(view()
              .name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessPreAuthCompleationInvalidId",
          e);
    }
  }

  @Test
  public void testProcessPreAuthFailure() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doPreAuthCapture(Matchers.any(VirtualTerminalCaptureDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post(
              "/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PROCESS)
                  .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
                  .sessionAttr("loginUserId", 1l).param("merchantId", "1"))
          .andExpect(view()
              .name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessPreAuthFailure", e);
    }
  }

  @Test
  public void testProcessPreAuthCompleationChatakPayException() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(ChatakPayException.class);
      mockMvc
          .perform(post(
              "/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PROCESS)
                  .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
                  .sessionAttr("loginUserId", 1l).param("merchantId", "1"))
          .andExpect(view()
              .name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalController :: testProcessPreAuthCompleationChatakPayException",
          e);
    }
  }

  @Test
  public void testProcessPreAuthCompleationException() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post(
              "/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PROCESS)
                  .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
                  .sessionAttr("loginUserId", 1l).param("merchantId", "1"))
          .andExpect(view()
              .name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController :: testProcessPreAuthCompleationException",
          e);
    }
  }


  @Test
  public void testprocessPopupActionVoid() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PROCESS_POPUP_ACTION)
          .param("transactionType", "VOID"));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController ::  testprocessPopupActionVoid:", e);
    }
  }

  @Test
  public void testprocessPopupActionRefund() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PROCESS_POPUP_ACTION)
          .param("transactionType", "REFUND").param("partialRefundFlag", "1"));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController ::  testprocessPopupActionVoid:", e);

    }
  }

  @Test
  public void testprocessPopupAction() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PROCESS_POPUP_ACTION)
          .param("transactionType", "SPLIT_ACCEPT"));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController ::  testprocessPopupAction:", e);
    }
  }

  @Test
  public void testprocessPopupActionException() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PROCESS_POPUP_ACTION));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController ::  testprocessPopupActionException:", e);
    }
  }

  @Test
  public void testFetchMerchantCurrencyByMerchantCodeException() {
    try {
      Mockito.when(merchantValidateService.fetchMerchantCurrencyByCode(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_GET_MERCHANT_CURRENCY));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalController ::  testFetchMerchantCurrencyByMerchantCodeException",
          e);
    }
  }

  @Test
  public void testFetchMerchantCurrencyByMerchantCode() {
    response = new Response();
    try {
      Mockito.when(merchantValidateService.fetchMerchantCurrencyByCode(Matchers.anyString()))
          .thenReturn(response);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_GET_MERCHANT_CURRENCY));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalController ::  testFetchMerchantCurrencyByMerchantCode",
          e);
    }
  }
}
