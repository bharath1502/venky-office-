package com.chatak.merchant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
import com.chatak.pg.util.Constants;

public class VirtualTerminalControllerAuthCompletionTest {

  private static Logger logger =
      Logger.getLogger(VirtualTerminalControllerAuthCompletionTest.class);

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
  public void testProcessPreAuthCompleationMerchantIdNull() {
    try {
      mockMvc
          .perform(post(
              "/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PROCESS))
          .andExpect(view()
              .name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalControllerAuthCompletionTest :: testProcessPreAuthCompleationMerchantIdNull:",
          e);
    }
  }

  @Test
  public void testProcessPreAuthCompleation() {
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doPreAuthCapture(Matchers.any(VirtualTerminalCaptureDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post(
              "/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PROCESS)
                  .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view()
              .name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalControllerAuthCompletionTest :: testProcessPreAuthCompleation:",
          e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testProcessPreAuthCompleationChatakPayException() {
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.ERROR_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(ChatakPayException.class);
      mockMvc
          .perform(post(
              "/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PROCESS)
                  .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view()
              .name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalControllerAuthCompletionTest :: testProcessPreAuthCompleationChatakPayException:",
          e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testProcessPreAuthCompleationException() {
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.ERROR_CODE);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(NullPointerException.class);
      mockMvc
          .perform(post(
              "/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PROCESS)
                  .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view()
              .name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalControllerAuthCompletionTest :: testProcessPreAuthCompleationException:",
          e);
    }
  }

  @Test
  public void testProcessPreAuthCompleationElse() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.ERROR_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      mockMvc
          .perform(post(
              "/" + URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PROCESS)
                  .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view()
              .name(URLMappingConstants.CHATAK_MERCHANT_VIRTUAL_TERMINAL_PREAUTH_COMPLEATION_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalControllerAuthCompletionTest :: testProcessPreAuthCompleationElse:",
          e);
    }
  }

  @Test
  public void testprocessPopupActionVoid() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PROCESS_POPUP_ACTION)
          .param("transactionType", "VOID"));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTest ::  testprocessPopupActionVoid:", e);
    }
  }

  @Test
  public void testprocessPopupActionRefund() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PROCESS_POPUP_ACTION)
          .param("transactionType", "REFUND"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalControllerAuthCompletionTest ::  testprocessPopupActionVoid:",
          e);
    }
  }

  @Test
  public void testprocessPopupAction() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PROCESS_POPUP_ACTION)
          .param("transactionType", "SPLIT_ACCEPT"));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalControllerTest ::  testprocessPopupAction:", e);
    }
  }

  @Test
  public void testprocessPopupActionException() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PROCESS_POPUP_ACTION));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalControllerAuthCompletionTest ::  testprocessPopupActionException:",
          e);
    }
  }
}
