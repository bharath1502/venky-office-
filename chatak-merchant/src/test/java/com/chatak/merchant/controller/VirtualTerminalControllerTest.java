package com.chatak.merchant.controller;

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

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.exception.ChatakPayException;
import com.chatak.merchant.model.GetMerchantDetailsResponse;
import com.chatak.merchant.model.TransactionResponse;
import com.chatak.merchant.model.VirtualTerminalAdjustmentResponse;
import com.chatak.merchant.service.RestPaymentService;
import com.chatak.pg.acq.dao.model.PGMerchantConfig;
import com.chatak.pg.acq.dao.repository.MerchantConfigRepositrory;
import com.chatak.pg.model.VirtualTerminalAdjustmentDTO;
import com.chatak.pg.model.VirtualTerminalCaptureDTO;
import com.chatak.pg.model.VirtualTerminalPreAuthDTO;
import com.chatak.pg.model.VirtualTerminalRefundDTO;
import com.chatak.pg.model.VirtualTerminalSaleDTO;
import com.chatak.pg.model.VirtualTerminalVoidDTO;
import com.chatak.pg.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class VirtualTerminalControllerTest {

  private static Logger logger = Logger.getLogger(VirtualTerminalControllerTest.class);

  @InjectMocks
  VirtualTerminalController VirtualTerminalController = new VirtualTerminalController();

  @Mock
  RestPaymentService paymentService;

  @Mock
  private MessageSource messageSource;

  @Mock
  private MerchantConfigRepositrory merchantConfigRepositrory;

  private MockMvc mockMvc;

  @Mock
  PGMerchantConfig pgmerchantconfig;

  @Mock
  GetMerchantDetailsResponse merchantDetailsResponse;

  @Mock
  VirtualTerminalAdjustmentDTO terminalAdjustmentDTO;

  @Mock
  VirtualTerminalAdjustmentResponse virtualTerminalAdjustmentResponse;

  @Mock
  TransactionResponse transactionResponse;

  @Before
  public void setup() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(VirtualTerminalController)
        .setViewResolvers(viewResolver).build();
  }

  @Test
  public void testShowVirtualterminalSale() {
    try {
      Mockito.when(merchantConfigRepositrory.findById(Matchers.anyLong()))
          .thenReturn(pgmerchantconfig);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PAGE)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTest :: testShowVirtualterminalSale:", e);
    }
  }

  @Test
  public void testprocessSale() {
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    merchantDetailsResponse.setMerchantId("12");
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doSale(Matchers.any(VirtualTerminalSaleDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PROCESS)
              .param("year", "2019").param("month", "2")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testprocessSale:", e);
    }

  }

  @Test
  public void testprocessSaleMerchantResponse() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.ERROR_CODE);

    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PROCESS)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testprocessSaleMerchantResponse:",
          e);
    }

  }

  @Test
  public void testprocessSaleElse() {
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.ERROR_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doSale(Matchers.any(VirtualTerminalSaleDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PROCESS)
              .param("VirtualTerminalSaleDTO", "terminalSaleDTO").param("year", "2019")
              .param("month", "2").sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testprocessSaleElse:", e);
    }

  }

  @SuppressWarnings("unchecked")
  @Test
  public void testprocessSaleChatakPayException() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(ChatakPayException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PROCESS)
              .param("year", "2019").param("month", "2")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalControllerTestTest :: testprocessSaleChatakPayException:", e);
    }

  }

  @SuppressWarnings("unchecked")
  @Test
  public void testprocessSaleException() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(NullPointerException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PROCESS)
              .param("year", "2019").param("month", "2")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testprocessSaleException:", e);
    }

  }

  @SuppressWarnings("unchecked")
  @Test
  public void testprocessSaleMerchantIdNull() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PROCESS)
              .param("year", "2019").param("month", "2"))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testprocessSaleMerchantIdNull:",
          e);
    }
  }

  @Test
  public void testProcessSaleOnRefresh() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PROCESS)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_SALE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testProcessSaleOnRefresh:", e);
    }
  }

  @Test
  public void testShowVirtualterminalPreAuth() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PAGE))
          .andExpect(
              view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testShowVirtualterminalPreAuth:",
          e);
    }
  }

  @Test
  public void testprocessPreAuth() {
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doPreAuth(Matchers.any(VirtualTerminalPreAuthDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PROCESS)
              .param("month", "2").param("year", "2018")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(
              view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testprocessPreAuth:", e);
    }
  }

  @Test
  public void testprocessPreAuthElse() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.ERROR_CODE);
    try {
      Mockito.when(paymentService.doPreAuth(Matchers.any(VirtualTerminalPreAuthDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PROCESS)
              .param("month", "2").param("year", "2018")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(
              view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testprocessPreAuthElse:", e);
    }
  }

  @Test
  public void testprocessPreAuthMerchantIdNull() {
    try {
      mockMvc
          .perform(
              post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PROCESS))
          .andExpect(
              view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalControllerTestTest :: testprocessPreAuthMerchantIdNull:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testprocessPreAuthChatakPayException() {
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(ChatakPayException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PROCESS)
              .param("month", "2").param("year", "2018")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(
              view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalControllerTestTest :: testprocessPreAuthChatakPayException:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testprocessPreAuthException() {
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(NullPointerException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PROCESS)
              .param("month", "2").param("year", "2018")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(
              view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PRE_AUTH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testprocessPreAuthException:", e);
    }
  }

  @Test
  public void testShowVirtualterminalAdjust() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PAGE))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testShowVirtualterminalAdjust:",
          e);
    }
  }

  @Test
  public void testProcessAdjust() {
    virtualTerminalAdjustmentResponse = new VirtualTerminalAdjustmentResponse();
    virtualTerminalAdjustmentResponse.setErrorCode(Constants.SUCCESS_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doAdjust(Matchers.any(VirtualTerminalAdjustmentDTO.class)))
          .thenReturn(virtualTerminalAdjustmentResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PROCESS)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testProcessAdjust:", e);
    }
  }

  @Test
  public void testProcessAdjustMerchantDetailsResponse() {
    virtualTerminalAdjustmentResponse = new VirtualTerminalAdjustmentResponse();
    virtualTerminalAdjustmentResponse.setErrorCode(Constants.SUCCESS_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.ERROR_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doAdjust(Matchers.any(VirtualTerminalAdjustmentDTO.class)))
          .thenReturn(virtualTerminalAdjustmentResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PROCESS)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalControllerTestTest :: testProcessAdjustMerchantDetailsResponse:",
          e);
    }
  }

  @Test
  public void testProcessAdjustMerchantIdNull() {
    virtualTerminalAdjustmentResponse = new VirtualTerminalAdjustmentResponse();
    virtualTerminalAdjustmentResponse.setErrorCode(Constants.SUCCESS_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doAdjust(Matchers.any(VirtualTerminalAdjustmentDTO.class)))
          .thenReturn(virtualTerminalAdjustmentResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PROCESS))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testProcessAdjustMerchantIdNull:",
          e);
    }
  }

  @Test
  public void testProcessAdjustElse() {
    virtualTerminalAdjustmentResponse = new VirtualTerminalAdjustmentResponse();
    virtualTerminalAdjustmentResponse.setErrorCode(Constants.ERROR_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doAdjust(Matchers.any(VirtualTerminalAdjustmentDTO.class)))
          .thenReturn(virtualTerminalAdjustmentResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PROCESS)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testProcessAdjustElse:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testProcessAdjustException() {
    virtualTerminalAdjustmentResponse = new VirtualTerminalAdjustmentResponse();
    virtualTerminalAdjustmentResponse.setErrorCode(Constants.ERROR_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(ChatakPayException.class);
      Mockito.when(paymentService.doAdjust(Matchers.any(VirtualTerminalAdjustmentDTO.class)))
          .thenReturn(virtualTerminalAdjustmentResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PROCESS)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testProcessAdjustException:", e);
    }
  }


  @SuppressWarnings("unchecked")
  @Test
  public void testProcessAdjustNullPointerException() {
    virtualTerminalAdjustmentResponse = new VirtualTerminalAdjustmentResponse();
    virtualTerminalAdjustmentResponse.setErrorCode(Constants.ERROR_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(NullPointerException.class);
      Mockito.when(paymentService.doAdjust(Matchers.any(VirtualTerminalAdjustmentDTO.class)))
          .thenReturn(virtualTerminalAdjustmentResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PROCESS)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_ADJUST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalControllerTestTest :: testProcessAdjustNullPointerException:",
          e);
    }
  }

  @Test
  public void testShowVirtualterminalVoid() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PAGE))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testShowVirtualterminalVoid:", e);
    }
  }

  @Test
  public void testProcessVoid() {
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.doVoid(Matchers.any(VirtualTerminalVoidDTO.class)))
          .thenReturn(transactionResponse);
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PROCESS)
              .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testProcessVoid:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testProcessVoidChatakPayException() {
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.doVoid(Matchers.any(VirtualTerminalVoidDTO.class)))
          .thenThrow(ChatakPayException.class);
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PROCESS)
              .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalControllerTestTest :: testProcessVoidChatakPayException:", e);


    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testProcessVoidException() {
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.doVoid(Matchers.any(VirtualTerminalVoidDTO.class)))
          .thenThrow(NullPointerException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PROCESS)
              .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testProcessVoidException:", e);

    }
  }

  @Test
  public void testProcessVoidElseResponse() {
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.ERROR_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.doVoid(Matchers.any(VirtualTerminalVoidDTO.class)))
          .thenReturn(transactionResponse);
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PROCESS)
              .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testProcessVoidElseResponse:", e);

    }
  }

  @Test
  public void testProcessVoidElse() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.ERROR_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PROCESS)
              .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testProcessVoidElse:", e);

    }
  }

  @Test
  public void testProcessVoidMerchantIdNull() {

    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PROCESS))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_VOID_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testProcessVoidMerchantIdNull:",
          e);
    }
  }

  @Test
  public void testShowVirtualterminalRefund() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PAGE))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testProcessRefundMerchantIdNull:",
          e);

    }

  }

  @Test
  public void testProcessRefundMerchantIdNull() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PROCESS))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testProcessRefundMerchantIdNull:",
          e);

    }
  }

  @Test
  public void testProcessRefund() {
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doRefund(Matchers.any(VirtualTerminalRefundDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PROCESS)
              .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testProcessRefund:", e);

    }

  }

  @Test
  public void testProcessRefundElse() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.ERROR_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PROCESS)
              .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testProcessRefundElse:", e);

    }

  }

  @SuppressWarnings("unchecked")
  @Test
  public void testProcessRefundChatakPayException() {
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(ChatakPayException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PROCESS)
              .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalControllerTestTest :: testProcessRefundChatakPayException:", e);

    }

  }

  @SuppressWarnings("unchecked")
  @Test
  public void testProcessRefundException() {
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(NullPointerException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PROCESS)
              .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTestTest :: testProcessRefundException:", e);
    }

  }

  @Test
  public void testProcessRefundOnRefresh() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PROCESS))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_REFUND_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTest :: testProcessRefundOnRefresh:", e);
    }
  }

  @Test
  public void testShowVirtualterminalPreAuthCompleation() {
    try {
      mockMvc
          .perform(get(
              "/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE))
          .andExpect(view()
              .name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalControllerTest :: testShowVirtualterminalPreAuthCompleation:",
          e);
    }
  }
}
