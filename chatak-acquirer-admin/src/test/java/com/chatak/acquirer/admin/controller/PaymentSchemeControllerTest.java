package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.acquirer.admin.constants.TestConstants;
import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.PaymentSchemeService;
import com.chatak.pg.bean.PaymentSchemeNameResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.PaymentScheme;
import com.chatak.pg.user.bean.PaymentSchemeRequest;
import com.chatak.pg.user.bean.PaymentSchemeResponse;

@RunWith(MockitoJUnitRunner.class)
public class PaymentSchemeControllerTest {

  private static Logger logger = LoggerFactory.getLogger(PaymentSchemeControllerTest.class);

  @InjectMocks
  PaymentSchemeController paymentSchemeController = new PaymentSchemeController();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  private Response responseval;

  @Mock
  BindingResult bindingResult;

  @Mock
  private List<Option> optionList;

  private MockMvc mockMvc;

  @Mock
  private MessageSource messageSource;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  private ChatakAdminException chatakAdminException;

  @Mock
  private LoginDetails loginDetails;

  @Mock
  private PaymentScheme paymentScheme;

  @Mock
  private PaymentSchemeService paymentSchemeService;

  @Mock
  private PaymentSchemeResponse addPaymentSchemeResponse;

  @Mock
  private List<PaymentSchemeRequest> paymentSchemesRequestList;

  @Mock
  private PaymentSchemeRequest paymentSchemesRequest;

  @Mock
  private PaymentSchemeNameResponse paymentSchemeNameResponse;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(paymentSchemeController)
        .setViewResolvers(viewResolver).build();
    optionList = new ArrayList<>();
  }

  @Test
  public void testShowCreatePaymentSchemePage() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_PAYMENT_SCHEME_SHOW_CREATE_PAGE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_PAYMENT_SCHEME_SHOW_CREATE_PAGE));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testShowCreatePaymentSchemePage | Exception ", e);
    }
  }

  @Test
  public void testCreatePaymentScheme() {
    addPaymentSchemeResponse = new PaymentSchemeResponse();
    addPaymentSchemeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(paymentSchemeService
          .addPaymentSchemeInformation(Matchers.any(PaymentScheme.class), Matchers.anyString()))
          .thenReturn(addPaymentSchemeResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_PAYMENT_SCHEME_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)))
          .andExpect(view().name(URLMappingConstants.CHATAK_PAYMENT_SCHEME_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testCreatePaymentScheme | Exception ", e);
    }
  }

  @Test
  public void testCreatePaymentSchemeElse() {
    addPaymentSchemeResponse = new PaymentSchemeResponse();
    addPaymentSchemeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(paymentSchemeService
          .addPaymentSchemeInformation(Matchers.any(PaymentScheme.class), Matchers.anyString()))
          .thenReturn(addPaymentSchemeResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_PAYMENT_SCHEME_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_PAYMENT_SCHEME_SHOW_CREATE_PAGE));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testCreatePaymentSchemeElse | Exception ", e);
    }
  }

  @Test
  public void testCreatePaymentSchemeException() {
    try {
      Mockito.when(paymentSchemeService
          .addPaymentSchemeInformation(Matchers.any(PaymentScheme.class), Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_PAYMENT_SCHEME_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_PAYMENT_SCHEME_SHOW_CREATE_PAGE));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testCreatePaymentSchemeException | Exception ",
          e);
    }
  }

  @Test
  public void testSearchPaymentSchemeAccount() {
    addPaymentSchemeResponse = new PaymentSchemeResponse();
    addPaymentSchemeResponse.setTotalNoOfRows(TestConstants.TEN);
    addPaymentSchemeResponse.setPaymentSchemesRequest(paymentSchemesRequestList);
    try {
      Mockito
          .when(paymentSchemeService.searchPaymentScheme(Matchers.any(PaymentSchemeRequest.class)))
          .thenReturn(addPaymentSchemeResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_PAYMENT_SCHEME_SEARCH_ACTION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)))
          .andExpect(view().name(URLMappingConstants.CHATAK_PAYMENT_SCHEME_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testSearchPaymentSchemeAccount | Exception ", e);
    }
  }

  @Test
  public void testSearchPaymentSchemeAccountException() {
    try {
      Mockito
          .when(paymentSchemeService.searchPaymentScheme(Matchers.any(PaymentSchemeRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_PAYMENT_SCHEME_SEARCH_ACTION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)))
          .andExpect(view().name(URLMappingConstants.CHATAK_PAYMENT_SCHEME_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error(
          "PaymentSchemeControllerTest | testSearchPaymentSchemeAccountException | Exception ", e);
    }
  }

  @Test
  public void testShowEditPaymentSchemes() {
    try {
      Mockito.when(paymentSchemeService.getpaymentSchemeyInfoId(Matchers.anyLong()))
          .thenReturn(paymentSchemesRequest);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_EDIT_PAYMENT_SCHEME)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_EDIT_PAYMENT_SCHEME));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testShowEditPaymentSchemes | Exception ", e);
    }
  }

  @Test
  public void testShowEditPaymentSchemesException() {
    try {
      Mockito.when(paymentSchemeService.getpaymentSchemeyInfoId(Matchers.anyLong()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_EDIT_PAYMENT_SCHEME)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_EDIT_PAYMENT_SCHEME));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testShowEditPaymentSchemesException | Exception ",
          e);
    }
  }

  @Test
  public void testShowViewPaymentSchemes() {
    try {
      Mockito.when(paymentSchemeService.getpaymentSchemeyInfoId(Matchers.anyLong()))
          .thenReturn(paymentSchemesRequest);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIEW_PAYMENT_SCHEME)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param("getpaymentschemeId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIEW_PAYMENT_SCHEME));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testShowViewPaymentSchemes | Exception ", e);
    }
  }

  @Test
  public void testShowViewPaymentSchemesException() {
    try {
      Mockito.when(paymentSchemeService.getpaymentSchemeyInfoId(Matchers.anyLong()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIEW_PAYMENT_SCHEME)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param("getpaymentschemeId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_VIEW_PAYMENT_SCHEME));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testShowViewPaymentSchemesException | Exception ",
          e);
    }
  }

  @Test
  public void testUpdatePaymentScheme() {
    addPaymentSchemeResponse = new PaymentSchemeResponse();
    addPaymentSchemeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito
          .when(paymentSchemeService.updatePaymentSchemeInformation(
              Matchers.any(PaymentSchemeRequest.class), Matchers.anyString()))
          .thenReturn(addPaymentSchemeResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_PAYMENT_SCHEME)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param("getpaymentschemeId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_PAYMENT_SCHEME_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testUpdatePaymentScheme | Exception ", e);
    }
  }

  @Test
  public void testUpdatePaymentSchemeElse() {
    addPaymentSchemeResponse = new PaymentSchemeResponse();
    addPaymentSchemeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito
          .when(paymentSchemeService.updatePaymentSchemeInformation(
              Matchers.any(PaymentSchemeRequest.class), Matchers.anyString()))
          .thenReturn(addPaymentSchemeResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_PAYMENT_SCHEME)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param("getpaymentschemeId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_EDIT_PAYMENT_SCHEME));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testUpdatePaymentSchemeElse | Exception ", e);
    }
  }

  @Test
  public void testUpdatePaymentSchemeException() {
    try {
      Mockito
          .when(paymentSchemeService.updatePaymentSchemeInformation(
              Matchers.any(PaymentSchemeRequest.class), Matchers.anyString()))
          .thenThrow(chatakAdminException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_PAYMENT_SCHEME)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param("getpaymentschemeId", TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_EDIT_PAYMENT_SCHEME));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testUpdatePaymentSchemeException | Exception ",
          e);
    }
  }

  @Test
  public void testValidateUniqueEmailId() {
    try {
      Mockito.when(paymentSchemeService.validateEmailId(Matchers.anyString()))
          .thenReturn(responseval);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_UNIQUE_PAYMENT_SCHEME_EMAIL_ID)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testValidateUniqueEmailId | Exception ", e);
    }
  }

  @Test
  public void testValidateUniqueEmailIdException() {
    try {
      Mockito.when(paymentSchemeService.validateEmailId(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_UNIQUE_PAYMENT_SCHEME_EMAIL_ID)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testValidateUniqueEmailIdException | Exception ",
          e);
    }
  }

  @Test
  public void testChangePaymentSchemeStatus() {
    addPaymentSchemeResponse = new PaymentSchemeResponse();
    addPaymentSchemeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito
          .when(paymentSchemeService.changePaymentSchemeStatus(
              Matchers.any(PaymentSchemeRequest.class), Matchers.anyString()))
          .thenReturn(addPaymentSchemeResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_PAYMENT_SCHEME_ACTIVATION_SUSPENTION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .param("suspendActiveId", TestConstants.ONE.toString())
              .param("suspendActiveStatus", "suspendActiveStatus").param("reason", "reason"))
          .andExpect(view().name(URLMappingConstants.CHATAK_PAYMENT_SCHEME_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testChangePaymentSchemeStatus | Exception ", e);
    }
  }

  @Test
  public void testChangePaymentSchemeStatusElse() {
    addPaymentSchemeResponse = new PaymentSchemeResponse();
    addPaymentSchemeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    addPaymentSchemeResponse.setPaymentSchemesRequest(paymentSchemesRequestList);
    addPaymentSchemeResponse.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito
          .when(paymentSchemeService.changePaymentSchemeStatus(
              Matchers.any(PaymentSchemeRequest.class), Matchers.anyString()))
          .thenReturn(addPaymentSchemeResponse);
      Mockito
          .when(paymentSchemeService.searchPaymentScheme(Matchers.any(PaymentSchemeRequest.class)))
          .thenReturn(addPaymentSchemeResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_PAYMENT_SCHEME_ACTIVATION_SUSPENTION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .sessionAttr(TestConstants.PAYMENT_SCHEME_INFO, paymentSchemesRequest)
              .param("suspendActiveId", TestConstants.ONE.toString())
              .param("suspendActiveStatus", "suspendActiveStatus").param("reason", "reason"))
          .andExpect(view().name(URLMappingConstants.CHATAK_PAYMENT_SCHEME_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testChangePaymentSchemeStatusElse | Exception ",
          e);
    }
  }

  @Test
  public void testMerchantDataMethodException() {
    addPaymentSchemeResponse = new PaymentSchemeResponse();
    addPaymentSchemeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    addPaymentSchemeResponse.setPaymentSchemesRequest(paymentSchemesRequestList);
    addPaymentSchemeResponse.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito
          .when(paymentSchemeService.changePaymentSchemeStatus(
              Matchers.any(PaymentSchemeRequest.class), Matchers.anyString()))
          .thenReturn(addPaymentSchemeResponse);
      Mockito
          .when(paymentSchemeService.searchPaymentScheme(Matchers.any(PaymentSchemeRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_PAYMENT_SCHEME_ACTIVATION_SUSPENTION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE))
              .sessionAttr(TestConstants.PAYMENT_SCHEME_INFO, paymentSchemesRequest)
              .param("suspendActiveId", TestConstants.ONE.toString())
              .param("suspendActiveStatus", "suspendActiveStatus").param("reason", "reason"))
          .andExpect(view().name(URLMappingConstants.CHATAK_PAYMENT_SCHEME_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testMerchantDataMethodException | Exception ", e);
    }
  }

  @Test
  public void testChangePaymentSchemeStatusException() {
    try {
      Mockito
          .when(paymentSchemeService.changePaymentSchemeStatus(
              Matchers.any(PaymentSchemeRequest.class), Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_PAYMENT_SCHEME_ACTIVATION_SUSPENTION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.LOGIN_USER_ID, Long.valueOf(TestConstants.ONE)))
          .andExpect(view().name(URLMappingConstants.CHATAK_PAYMENT_SCHEME_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error(
          "PaymentSchemeControllerTest | testChangePaymentSchemeStatusException | Exception ", e);
    }
  }


  @Test
  public void testdownloadPaymentShemeReport() {
    addPaymentSchemeResponse = new PaymentSchemeResponse();
    addPaymentSchemeResponse.setPaymentSchemesRequest(paymentSchemesRequestList);
    try {
      Mockito
          .when(paymentSchemeService.searchPaymentScheme(Matchers.any(PaymentSchemeRequest.class)))
          .thenReturn(addPaymentSchemeResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_REPORT_PAYMENT_SCHEME)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.PAYMENT_SCHEME_INFO, paymentSchemesRequest)
          .param("downLoadPageNumber", TestConstants.ONE.toString()).param("downloadAllRecords", "true")
          .param(TestConstants.DOWNLOAD_TYPE, "XLS"));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testdownloadPaymentShemeReport | Exception ", e);
    }
  }

  @Test
  public void testdownloadPaymentShemeReportPDF() {
    addPaymentSchemeResponse = new PaymentSchemeResponse();
    addPaymentSchemeResponse.setPaymentSchemesRequest(paymentSchemesRequestList);
    try {
      Mockito
          .when(paymentSchemeService.searchPaymentScheme(Matchers.any(PaymentSchemeRequest.class)))
          .thenReturn(addPaymentSchemeResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_REPORT_PAYMENT_SCHEME)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.PAYMENT_SCHEME_INFO, paymentSchemesRequest)
          .param("downLoadPageNumber", TestConstants.ONE.toString()).param("downloadAllRecords", "true")
          .param(TestConstants.DOWNLOAD_TYPE, "PDF"));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testdownloadPaymentShemeReportPDF | Exception ",
          e);
    }
  }

  @Test
  public void testValidatepaymentSchemeName() {
    addPaymentSchemeResponse = new PaymentSchemeResponse();
    addPaymentSchemeResponse.setPaymentSchemesRequest(paymentSchemesRequestList);
    try {
      Mockito.when(paymentSchemeService.validatePaymentSchemeName(Matchers.anyString()))
          .thenReturn(paymentSchemeNameResponse);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_PAYMENT_SCHEME_VALIDATE)
          .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.PAYMENT_SCHEME_INFO, paymentSchemesRequest)
          .param("downLoadPageNumber", TestConstants.ONE.toString()).param("downloadAllRecords", "true")
          .param(TestConstants.DOWNLOAD_TYPE, "XLS"));
    } catch (Exception e) {
      logger.error("PaymentSchemeControllerTest | testValidatepaymentSchemeName | Exception ", e);
    }
  }

}
