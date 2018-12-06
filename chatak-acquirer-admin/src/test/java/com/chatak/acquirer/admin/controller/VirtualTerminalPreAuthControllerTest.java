/**
 * 
 */
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
import com.chatak.acquirer.admin.service.RestPaymentService;
import com.chatak.pg.model.GetMerchantDetailsResponse;
import com.chatak.pg.model.TransactionResponse;
import com.chatak.pg.model.VirtualTerminalAdjustmentDTO;
import com.chatak.pg.model.VirtualTerminalAdjustmentResponse;
import com.chatak.pg.model.VirtualTerminalPreAuthDTO;
import com.chatak.pg.util.Constants;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 30, 2017 12:50:20 PM
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class VirtualTerminalPreAuthControllerTest {

  private static Logger logger = Logger.getLogger(VirtualTerminalPreAuthControllerTest.class);

  @InjectMocks
  VirtualTerminalPreAuthController controller = new VirtualTerminalPreAuthController();

  @Mock
  private MessageSource messageSource;

  private MockMvc mockMvc;

  @Mock
  private GetMerchantDetailsResponse merchantDetailsResponse;

  @Mock
  private RestPaymentService paymentService;

  @Mock
  private TransactionResponse transactionResponse;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  private VirtualTerminalAdjustmentResponse virtualTerminalAdjustmentResponse;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
  }

  @Test
  public void testShowVirtualterminalPreAuth() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PAGE))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalPreAuthControllerTest :: testShowVirtualterminalPreAuth", e);

    }
  }

  @Test
  public void testProcessPreAuth() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doPreAuth(Matchers.any(VirtualTerminalPreAuthDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PROCESS)
              .param("merchantId", "merchantId").param("month", "12").param("year", "2018"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalPreAuthControllerTest :: testProcessPreAuth", e);

    }
  }

  @Test
  public void testProcessPreAuthWrongId() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PROCESS)
              .param("merchantId", "merchantId").param("month", "12").param("year", "2018"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalPreAuthControllerTest :: testProcessPreAuthWrongId", e);

    }
  }

  @Test
  public void testProcessPreAuthFailed() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doPreAuth(Matchers.any(VirtualTerminalPreAuthDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PROCESS)
              .param("merchantId", "merchantId").param("month", "12").param("year", "2018"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalPreAuthControllerTest :: testProcessPreAuthFailed", e);
    }
  }

  @Test
  public void testProcessPreAuthFailedNull() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doPreAuth(Matchers.any(VirtualTerminalPreAuthDTO.class)))
          .thenReturn(null);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PROCESS)
              .param("merchantId", "merchantId").param("month", "12").param("year", "2018"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalPreAuthControllerTest :: testProcessPreAuthFailedNull",
          e);
    }
  }

  @Test
  public void testProcessPreAuthWrongMid() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    transactionResponse = new TransactionResponse();
    transactionResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doPreAuth(Matchers.any(VirtualTerminalPreAuthDTO.class)))
          .thenReturn(transactionResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PROCESS)
              .param("month", "12").param("year", "2018"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalPreAuthControllerTest :: testProcessPreAuthWrongMid",
          e);

    }
  }

  @Test
  public void testProcessPreAuthChatakPayException() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(ChatakPayException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PROCESS)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalPreAuthControllerTest :: testProcessPreAuthChatakPayException",
          e);

    }
  }

  @Test
  public void testProcessPreAuthException() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PROCESS)
              .param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_PRE_AUTH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalPreAuthControllerTest :: testProcessPreAuthException",
          e);

    }
  }

  @Test
  public void testShowVirtualterminalAdjust() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PAGE))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalPreAuthControllerTest :: testShowVirtualterminalAdjust",
          e);

    }
  }

  @Test
  public void testProcessAdjustElse() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PROCESS)
              .param("month", "12").param("year", "2018").param("merchantId", "merchantId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalPreAuthControllerTest :: testProcessAdjustElse", e);

    }
  }

  @Test
  public void testProcessAdjustWrongID() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PROCESS)
              .sessionAttr("loginUserId", 1l).sessionAttr("merchantId", 1l))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalPreAuthControllerTest :: testProcessAdjustWrongID", e);

    }
  }

  @Test
  public void testProcessAdjust() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    virtualTerminalAdjustmentResponse = new VirtualTerminalAdjustmentResponse();
    virtualTerminalAdjustmentResponse.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doAdjust(Matchers.any(VirtualTerminalAdjustmentDTO.class)))
          .thenReturn(virtualTerminalAdjustmentResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PROCESS)
              .sessionAttr("loginUserId", 1l).sessionAttr("merchantId", 1l))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalPreAuthControllerTest :: testProcessAdjust", e);

    }
  }

  @Test
  public void testProcessAdjustFailure() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    virtualTerminalAdjustmentResponse = new VirtualTerminalAdjustmentResponse();
    virtualTerminalAdjustmentResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doAdjust(Matchers.any(VirtualTerminalAdjustmentDTO.class)))
          .thenReturn(virtualTerminalAdjustmentResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PROCESS)
              .sessionAttr("loginUserId", 1l).sessionAttr("merchantId", 1l))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalPreAuthControllerTest :: testProcessAdjustFailure", e);

    }
  }

  @Test
  public void testProcessAdjustFailureResponse() {
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
    virtualTerminalAdjustmentResponse = new VirtualTerminalAdjustmentResponse();
    virtualTerminalAdjustmentResponse.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(paymentService.doAdjust(Matchers.any(VirtualTerminalAdjustmentDTO.class)))
          .thenReturn(null);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PROCESS)
              .sessionAttr("loginUserId", 1l).sessionAttr("merchantId", 1l))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalPreAuthControllerTest :: testProcessAdjustFailureResponse", e);

    }
  }

  @Test
  public void testProcessAdjustChatakPayException() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(ChatakPayException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PROCESS)
              .sessionAttr("loginUserId", 1l).sessionAttr("merchantId", 1l))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: VirtualTerminalPreAuthControllerTest :: testProcessAdjustChatakPayException",
          e);

    }
  }

  @Test
  public void testProcessAdjustException() {
    try {
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PROCESS)
              .sessionAttr("loginUserId", 1l).sessionAttr("merchantId", 1l))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIRTUAL_TERMINAL_ADJUST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: VirtualTerminalPreAuthControllerTest :: testProcessAdjustException",
          e);

    }
  }
}
