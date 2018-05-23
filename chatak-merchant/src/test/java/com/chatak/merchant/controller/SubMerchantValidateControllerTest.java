package com.chatak.merchant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.MerchantData;
import com.chatak.merchant.model.MerchantSearchResponse;
import com.chatak.merchant.service.MerchantInfoService;
import com.chatak.merchant.service.MerchantService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.AgentDTOResponse;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class SubMerchantValidateControllerTest {

  private static Logger logger = Logger.getLogger(SubMerchantValidateController.class);

  @InjectMocks
  SubMerchantValidateController subMerchantValidateController = new SubMerchantValidateController();

  @Mock
  private MerchantService merchantService;

  @Mock
  private MessageSource messageSource;

  @Mock
  MerchantInfoService merchantInfoService;

  @Mock
  Response response;

  @Mock
  List<Option> options;

  @Mock
  Merchant merchant;

  @Mock
  AddMerchantResponse addMerchantResponse;

  @Mock
  AgentDTOResponse merchantAgent;

  @Mock
  MerchantSearchResponse searchResponse;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(subMerchantValidateController)
        .setViewResolvers(viewResolver).build();
  }

  @Test
  public void testValidateUniqueUserName() {
    try {
      Mockito.when(merchantInfoService.validateUserName(Matchers.anyString())).thenReturn(response);
      mockMvc.perform(get("/" + URLMappingConstants.UNIQUE_USER).sessionAttr("loginUserMerchantId",
          Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantValidateControllerTest :: testValidateUniqueUserName:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testValidateUniqueUserNameException() {
    try {
      Mockito.when(merchantInfoService.validateUserName(Matchers.anyString()))
          .thenThrow(NullPointerException.class);
      mockMvc.perform(get("/" + URLMappingConstants.UNIQUE_USER).sessionAttr("loginUserMerchantId",
          Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error(
          "ERROR :: SubMerchantValidateControllerTest :: testValidateUniqueUserNameException:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testValidateUniqueUserNameChatakMerchantException() {
    try {
      Mockito.when(merchantInfoService.validateUserName(Matchers.anyString()))
          .thenThrow(ChatakMerchantException.class);
      mockMvc.perform(get("/" + URLMappingConstants.UNIQUE_USER).sessionAttr("loginUserMerchantId",
          Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error(
          "ERROR :: SubMerchantValidateControllerTest :: testValidateUniqueUserNameChatakMerchantException:",
          e);
    }
  }

  @Test
  public void testGetStatesById() {
    try {
      Mockito.when(merchantInfoService.getStatesByCountry(Matchers.anyString()))
          .thenReturn(response);
      mockMvc.perform(get("/" + URLMappingConstants.GET_STATES).sessionAttr("loginUserMerchantId",
          Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantValidateControllerTest :: testGetStatesById:", e);
    }
  }

  @Test
  public void testValidateUniqueEmailId() {
    try {
      Mockito.when(merchantInfoService.validateEmailId(Matchers.anyString())).thenReturn(response);
      mockMvc.perform(get("/" + URLMappingConstants.UNIQUE_EMAIL).sessionAttr("loginUserMerchantId",
          Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantValidateControllerTest :: testValidateUniqueEmailId:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testValidateUniqueEmailIdChatakMerchantException() {
    try {
      Mockito.when(merchantInfoService.validateEmailId(Matchers.anyString()))
          .thenThrow(NullPointerException.class);
      mockMvc.perform(get("/" + URLMappingConstants.UNIQUE_EMAIL).sessionAttr("loginUserMerchantId",
          Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error(
          "ERROR :: SubMerchantValidateControllerTest :: testValidateUniqueEmailIdChatakMerchantException:",
          e);
    }
  }

  @Test
  public void testValidateUniqueUserNameEdit() {
    try {
      Mockito
          .when(
              merchantInfoService.validateUserNameEdit(Matchers.anyString(), Matchers.anyString()))
          .thenReturn(response);
      mockMvc.perform(get("/" + URLMappingConstants.UNIQUE_USER_EDIT)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantValidateControllerTest :: testValidateUniqueUserNameEdit:",
          e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testValidateUniqueUserNameEditException() {
    try {
      Mockito
          .when(
              merchantInfoService.validateUserNameEdit(Matchers.anyString(), Matchers.anyString()))
          .thenThrow(NullPointerException.class);
      mockMvc.perform(get("/" + URLMappingConstants.UNIQUE_USER_EDIT)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")));
    } catch (Exception e) {
      logger.error(
          "ERROR :: SubMerchantValidateControllerTest :: testValidateUniqueUserNameEditException:",
          e);
    }
  }

  @Test
  public void testShowCreateMerchantSignUp() {
    try {
      Mockito.when(merchantInfoService.getFeeProgramNames()).thenReturn(options);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_SIGNUP));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantValidateControllerTest :: testShowCreateMerchantSignUp:",
          e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testShowCreateMerchantSignUpException() {
    try {
      Mockito.when(merchantInfoService.getFeeProgramNames())
          .thenThrow(ChatakMerchantException.class);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_SIGNUP));
    } catch (Exception e) {
      logger.error(
          "ERROR :: SubMerchantValidateControllerTest :: testShowCreateMerchantSignUpException:",
          e);
    }
  }

  @Test
  public void testProcessMerchantSignUp() {
    merchant = new Merchant();
    addMerchantResponse = new AddMerchantResponse();
    addMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(merchantService.addMerchant(Matchers.any(Merchant.class), Matchers.anyString()))
          .thenReturn(addMerchantResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_SIGNUP_PROCESS)
          .param("merchantType", "SubMerchant").param("legalAnnualCard", "123454444"));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantValidateControllerTest :: testProcessMerchantSignUp:", e);
    }
  }

  @Test
  public void testValidateParentMerchantCode() {
    try {
      Mockito.when(merchantInfoService.validateParentMerchantCode(Matchers.anyString()))
          .thenReturn(response);
      mockMvc.perform(get("/" + URLMappingConstants.VALIDATE_MERCHANT_CODE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantValidateControllerTest :: testValidateParentMerchantCode:",
          e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testValidateParentMerchantCodeChatakMerchantException() {
    try {
      Mockito.when(merchantInfoService.validateParentMerchantCode(Matchers.anyString()))
          .thenThrow(NullPointerException.class);
      mockMvc.perform(get("/" + URLMappingConstants.VALIDATE_MERCHANT_CODE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: SubMerchantValidateControllerTest :: testValidateParentMerchantCodeChatakMerchantException:",
          e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testValidateAgentData() {
    try {
      Mockito.when(merchantInfoService.validateAgentDetails(Matchers.anyString(),
          Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
          .thenThrow(NullPointerException.class);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_VALIDATE_AGENT_DETAILS));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantValidateControllerTest :: testValidateAgentData:", e);
    }
  }

  @Test
  public void testGetAgentById() {
    try {
      Mockito.when(merchantInfoService.getAgentDataById(Matchers.anyLong()))
          .thenReturn(merchantAgent);
      mockMvc.perform(get("/" + URLMappingConstants.GET_AGENT_DATA_BY_ID).param("agentId", "12"));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantValidateControllerTest :: testGetAgentById:", e);
    }
  }

  @Test
  public void testGetAgentNamesByCurrency() {
    try {
      Mockito.when(merchantInfoService.getAgentNames(Matchers.anyString())).thenReturn(response);
      mockMvc.perform(get("/" + URLMappingConstants.GET_AGENT_NAMES_BY_CURRENCY_ALPHA));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantValidateControllerTest :: testGetAgentNamesByCurrency:", e);
    }
  }

  @Test
  public void testDownloadMerchantReport() {
    try {
      searchResponse = new MerchantSearchResponse();
      MerchantData merchantData = new MerchantData();
      List<MerchantData> merchants = new ArrayList<>();
      merchantData.setAddress1("Bangalore");
      merchantData.setAddress2("Karnataka");
      merchants.add(merchantData);
      searchResponse.setMerchants(merchants);
      Mockito.when(merchantInfoService.searchSubMerchantList(Matchers.any(Merchant.class)))
          .thenReturn(searchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_SUB_MERCHANT_REPORT)
          .param("downLoadPageNumber", "10").sessionAttr("merchants", merchant)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")).param(Constants.TOTAL_RECORDS, "2")
          .param("downloadAllRecords", "true").param("downloadType", "PDF"));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantValidateControllerTest :: testDownloadMerchantReport:", e);
    }
  }

  @Test
  public void testDownloadMerchantReportXls() {
    try {
      searchResponse = new MerchantSearchResponse();
      List<MerchantData> merchants = new ArrayList<>();
      MerchantData merchantData = new MerchantData();
      merchantData.setAddress1("Bangalore");
      merchants.add(merchantData);
      searchResponse.setMerchants(merchants);
      Mockito.when(merchantInfoService.searchSubMerchantList(Matchers.any(Merchant.class)))
          .thenReturn(searchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_SUB_MERCHANT_REPORT)
          .param("downLoadPageNumber", "10").sessionAttr("merchants", merchant)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")).param(Constants.TOTAL_RECORDS, "2")
          .param("downloadAllRecords", "true").param("downloadType", "XLS"));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantValidateControllerTest :: testDownloadMerchantReportXls:",
          e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testDownloadMerchantReportException() {
    try {
      Mockito.when(merchantInfoService.searchSubMerchantList(Matchers.any(Merchant.class)))
          .thenThrow(NullPointerException.class);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_SUB_MERCHANT_REPORT)
          .param("downLoadPageNumber", "10").sessionAttr("merchants", merchant)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")).param(Constants.TOTAL_RECORDS, "2")
          .param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: SubMerchantValidateControllerTest :: testDownloadMerchantReportException:", e);
    }
  }

  @Test
  public void testValidateUniqueEmailIdEdit() {
    try {
      Mockito
          .when(merchantInfoService.validateEmailIdEdit(Matchers.anyString(), Matchers.anyString()))
          .thenReturn(response);
      mockMvc.perform(get("/" + URLMappingConstants.UNIQUE_EMAIL_EDIT)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")).param(Constants.TOTAL_RECORDS, "2")
          .param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantValidateControllerTest :: testValidateUniqueEmailIdEdit:",
          e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testValidateUniqueEmailIdEditException() {
    try {
      Mockito
          .when(merchantInfoService.validateEmailIdEdit(Matchers.anyString(), Matchers.anyString()))
          .thenThrow(NullPointerException.class);
      mockMvc.perform(get("/" + URLMappingConstants.UNIQUE_EMAIL_EDIT)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234")).param(Constants.TOTAL_RECORDS, "2")
          .param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "ERROR :: SubMerchantValidateControllerTest :: testValidateUniqueEmailIdEditException:",
          e);
    }
  }
}
