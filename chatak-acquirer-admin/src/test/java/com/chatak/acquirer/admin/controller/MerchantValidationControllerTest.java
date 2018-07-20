/**
 * 
 */
package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantData;
import com.chatak.acquirer.admin.model.MerchantSearchResponse;
import com.chatak.acquirer.admin.service.BankService;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.acquirer.admin.service.MerchantCategoryCodeService;
import com.chatak.acquirer.admin.service.MerchantService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.MerchantValidateService;
import com.chatak.acquirer.admin.service.PartnerService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.AgentDTOResponse;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.UpdateMerchantResponse;
import com.chatak.pg.util.Constants;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jan 5, 2018 6:20:31 PM
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class MerchantValidationControllerTest {

  private static Logger logger = Logger.getLogger(MerchantValidationControllerTest.class);

  @InjectMocks
  MerchantValidationController controller = new MerchantValidationController();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  private Response responseval;

  private MockMvc mockMvc;

  @Mock
  BindingResult bindingResult;

  @Mock
  private List<Option> optionList;

  @Mock
  private Option option;

  @Mock
  private MessageSource messageSource;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  private MerchantService merchantService;

  @Mock
  private BankService bankService;

  @Mock
  private MerchantCategoryCodeService merchantCategoryCodeService;

  @Mock
  private CurrencyConfigService currencyConfigService;

  @Mock
  private MerchantValidateService merchantValidateService;

  @Mock
  MerchantUpdateService merchantUpdateService;

  @Mock
  MerchantController merchantController;

  @Mock
  private AgentDTOResponse agentDTOResponse;

  @Mock
  private UpdateMerchantResponse updateMerchantResponse;

  @Mock
  private List<Merchant> merchants;

  @Mock
  private Merchant merchant;

  @Mock
  private MerchantSearchResponse merchantSearchResponse;

  @Mock
  private List<MerchantData> merchantsData;

  @Mock
  private MerchantData merchantData;

  @Mock
  private PartnerService partnerService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
    optionList = new ArrayList<>();
  }

  @Test
  public void testValidateUniqueEmailId() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_UNIQUE_EMAIL_VALIDATE)
          .param("emailId", "emailId"));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testValidateUniqueEmailId", e);
    }
  }

  @Test
  public void testValidateUniqueEmailIdChatakAdminException() {
    try {
      Mockito.when(merchantValidateService.validateEmailId(Matchers.anyString()))
          .thenThrow(ChatakAdminException.class);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_UNIQUE_EMAIL_VALIDATE)
          .param("emailId", "emailId"));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantValidationControllerTest :: testValidateUniqueEmailIdChatakAdminException",
          e);
    }
  }

  @Test
  public void testValidateUniqueEmailIdException() {
    try {
      Mockito.when(merchantValidateService.validateEmailId(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_UNIQUE_EMAIL_VALIDATE)
          .param("emailId", "emailId"));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantValidationControllerTest :: testValidateUniqueEmailIdException", e);
    }
  }

  @Test
  public void testValidateUniqueUserName() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_UNIQUE_USER_VALIDATE)
          .param("userName", "userName"));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testValidateUniqueUserName", e);
    }
  }

  @Test
  public void testValidateUniqueUserNameChatakAdminException() {
    try {
      Mockito.when(merchantValidateService.validateUserName(Matchers.anyString()))
          .thenThrow(ChatakAdminException.class);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_UNIQUE_USER_VALIDATE)
          .param("userName", "userName"));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantValidationControllerTest :: testValidateUniqueUserNameChatakAdminException",
          e);
    }
  }

  @Test
  public void testValidateUniqueUserNameException() {
    try {
      Mockito.when(merchantValidateService.validateUserName(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_UNIQUE_USER_VALIDATE)
          .param("userName", "userName"));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantValidationControllerTest :: testValidateUniqueUserNameException", e);
    }
  }

  @Test
  public void testGetStatesById() {
    responseval = new Response();
    try {
      Mockito.when(merchantUpdateService.getStatesByCountry(Matchers.anyString()))
          .thenReturn(responseval);
      mockMvc.perform(get("/" + URLMappingConstants.GET_STATES).param("country", "country"));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testGetStatesById", e);
    }
  }

  @Test
  public void testGetStatesByIdChatakAdminException() {
    try {
      Mockito.when(merchantUpdateService.getStatesByCountry(Matchers.anyString()))
          .thenThrow(ChatakAdminException.class);
      mockMvc.perform(get("/" + URLMappingConstants.GET_STATES).param("country", "country"));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantValidationControllerTest :: testGetStatesByIdChatakAdminException", e);
    }
  }

  @Test
  public void testGetAgentNamesByCurrency() {
    responseval = new Response();
    try {
      Mockito.when(merchantUpdateService.getAgentNames(Matchers.anyString()))
          .thenReturn(responseval);
      mockMvc.perform(get("/" + URLMappingConstants.GET_AGENT_NAMES_BY_CURRENCY_ALPHA)
          .param("currencyAlpha", "currencyAlpha"));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testGetAgentNamesByCurrency", e);
    }
  }

  @Test
  public void testGetAgentNamesByCurrencyChatakAdminException() {
    try {
      Mockito.when(merchantUpdateService.getAgentNames(Matchers.anyString()))
          .thenThrow(ChatakAdminException.class);
      mockMvc.perform(get("/" + URLMappingConstants.GET_AGENT_NAMES_BY_CURRENCY_ALPHA)
          .param("currencyAlpha", "currencyAlpha"));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantValidationControllerTest :: testGetAgentNamesByCurrencyChatakAdminException",
          e);
    }
  }

  @Test
  public void testGetAgentById() {
    agentDTOResponse = new AgentDTOResponse();
    try {
      Mockito.when(merchantValidateService.getAgentDataById(Matchers.anyLong()))
          .thenReturn(agentDTOResponse);
      mockMvc.perform(get("/" + URLMappingConstants.GET_AGENT_DATA_BY_ID).param("agentId", "1"));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testGetAgentById", e);
    }
  }

  @Test
  public void testGetAgentByIdChatakAdminException() {
    try {
      Mockito.when(merchantValidateService.getAgentDataById(Matchers.anyLong()))
          .thenThrow(ChatakAdminException.class);
      mockMvc.perform(get("/" + URLMappingConstants.GET_AGENT_DATA_BY_ID).param("agentId", "1"));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantValidationControllerTest :: testGetAgentByIdChatakAdminException", e);
    }
  }

  @Test
  public void testValidateAgentData() {
    agentDTOResponse = new AgentDTOResponse();
    try {
      Mockito
          .when(merchantValidateService.validateAgentDetails(Matchers.anyString(),
              Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
          .thenReturn("");
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_VALIDATE_AGENT_DETAILS)
          .param("agentAccountNumber", "agentAccountNumber").param("agentClientId", "agentClientId")
          .param("agentANI", "agentANI").param("currencyCodeAlpha", "currencyCodeAlpha"));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testValidateAgentData", e);
    }
  }

  @Test
  public void testValidateAgentDataChatakAdminException() {
    try {
      Mockito
          .when(merchantValidateService.validateAgentDetails(Matchers.anyString(),
              Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_VALIDATE_AGENT_DETAILS)
          .param("agentAccountNumber", "agentAccountNumber").param("agentClientId", "agentClientId")
          .param("agentANI", "agentANI").param("currencyCodeAlpha", "currencyCodeAlpha"));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantValidationControllerTest :: testValidateAgentDataChatakAdminException",
          e);
    }
  }

  @Test
  public void testGetmerchantCodeByCurrency() {
    responseval = new Response();
    try {
      Mockito.when(merchantValidateService.getLocalCurrency(Matchers.anyLong()))
          .thenReturn(responseval);
      mockMvc.perform(
          get("/" + URLMappingConstants.GET_CURRENCY_MERCHANT_CODE).param("parentMerchantId", "1"));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testGetmerchantCodeByCurrency", e);
    }
  }

  @Test
  public void testGetmerchantCodeByCurrencyChatakAdminException() {
    try {
      Mockito.when(merchantValidateService.getLocalCurrency(Matchers.anyLong()))
          .thenThrow(ChatakAdminException.class);
      mockMvc.perform(
          get("/" + URLMappingConstants.GET_CURRENCY_MERCHANT_CODE).param("parentMerchantId", "1"));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantValidationControllerTest :: testGetmerchantCodeByCurrencyChatakAdminException",
          e);
    }
  }

  @Test
  public void testGetLocalCurrencyandbankName() {
    responseval = new Response();
    responseval.setCurrencyId(1l);
    try {
      Mockito.when(currencyConfigService.getCurrencyCodeNumeric(Matchers.anyString()))
          .thenReturn(responseval);
      Mockito.when(bankService.getBankName(Matchers.anyLong())).thenReturn(responseval);
      mockMvc.perform(get("/" + URLMappingConstants.GET_LOCAL_CURRENCY_BNAK_NAME)
          .param("currencyCodeAlpha", "USD"));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testGetLocalCurrencyandbankName",
          e);
    }
  }

  @Test
  public void testGetLocalCurrencyandbankNameChatakAdminException() {
    responseval = new Response();
    responseval.setCurrencyId(1l);
    try {
      Mockito.when(currencyConfigService.getCurrencyCodeNumeric(Matchers.anyString()))
          .thenReturn(responseval);
      Mockito.when(bankService.getBankName(Matchers.anyLong()))
          .thenThrow(ChatakAdminException.class);
      mockMvc.perform(get("/" + URLMappingConstants.GET_LOCAL_CURRENCY_BNAK_NAME)
          .param("currencyCodeAlpha", "USD"));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantValidationControllerTest :: testGetLocalCurrencyandbankNameChatakAdminException",
          e);
    }
  }



  @Test
  public void testValidateUniqueUserNameEdit() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_UNIQUE_USER_VALIDATE_EDIT)
          .param("userName", "userName").param("merchantCode", "merchantCode"));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testValidateUniqueUserNameEdit",
          e);
    }
  }

  @Test
  public void testValidateUniqueUserNameEditChatakAdminException() {
    try {
      Mockito.when(
          merchantValidateService.validateUserNameEdit(Matchers.anyString(), Matchers.anyString()))
          .thenThrow(ChatakAdminException.class);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_UNIQUE_USER_VALIDATE_EDIT)
          .param("userName", "userName").param("merchantCode", "merchantCode"));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantValidationControllerTest :: testValidateUniqueUserNameEditChatakAdminException",
          e);
    }
  }

  @Test
  public void testValidateUniqueUserNameEditException() {
    try {
      Mockito.when(
          merchantValidateService.validateUserNameEdit(Matchers.anyString(), Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_UNIQUE_USER_VALIDATE_EDIT)
          .param("userName", "userName").param("merchantCode", "merchantCode"));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantValidationControllerTest :: testValidateUniqueUserNameEditException",
          e);
    }
  }

  @Test
  public void testDeclineMerchantWithPagination() {
    updateMerchantResponse = new UpdateMerchantResponse();
    updateMerchantResponse.setUpdated(true);
    updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    merchants = new ArrayList<>();
    merchant = new Merchant();
    merchant.setAccountId(1l);
    merchants.add(merchant);
    merchant = new Merchant();
    merchant.setAccountId(Long.parseLong("2"));
    merchants.add(merchant);
    merchant = new Merchant();
    merchant.setAccountId(Long.parseLong("3"));
    merchants.add(merchant);
    merchant = new Merchant();
    merchant.setAccountId(Long.parseLong("4"));
    merchants.add(merchant);
    merchant = new Merchant();
    merchant.setAccountId(Long.parseLong("5"));
    merchants.add(merchant);
    merchant = new Merchant();
    merchant.setAccountId(Long.parseLong("6"));
    merchants.add(merchant);
    merchant = new Merchant();
    merchant.setAccountId(Long.parseLong("7"));
    merchants.add(merchant);
    merchant = new Merchant();
    merchant.setAccountId(Long.parseLong("8"));
    merchants.add(merchant);
    merchant = new Merchant();
    merchant.setAccountId(Long.parseLong("9"));
    merchants.add(merchant);
    merchant = new Merchant();
    merchant.setAccountId(Long.parseLong("12"));
    merchants.add(merchant);
    merchant = new Merchant();
    merchant.setAccountId(Long.parseLong("21"));
    merchants.add(merchant);
    merchant = new Merchant();
    merchants.add(merchant);
    try {
      Mockito.when(merchantService.updateMerchant(Matchers.any(Merchant.class)))
          .thenReturn(updateMerchantResponse);
      Mockito.when(merchantUpdateService.getMerchantByStatusPendingandDecline())
          .thenReturn(merchants);
      mockMvc
          .perform(post("/" + URLMappingConstants.DECLINE_MERCHANT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param("declineReason", "declineReason"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_HOME));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testDeclineMerchantWithPagination",
          e);
    }
  }

  @Test
  public void testDeclineMerchantException() {
    updateMerchantResponse = new UpdateMerchantResponse();
    updateMerchantResponse.setUpdated(true);
    updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(merchantService.updateMerchant(Matchers.any(Merchant.class)))
          .thenThrow(ChatakAdminException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.DECLINE_MERCHANT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param("declineReason", "declineReason"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_HOME));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testDeclineMerchantException", e);
    }
  }

  @Test
  public void testDeclineMerchant() {
    updateMerchantResponse = new UpdateMerchantResponse();
    updateMerchantResponse.setUpdated(false);
    updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    merchants = new ArrayList<>();
    merchant = new Merchant();
    merchant.setAccountId(1l);
    merchants.add(merchant);
    try {
      Mockito.when(merchantService.updateMerchant(Matchers.any(Merchant.class)))
          .thenReturn(updateMerchantResponse);
      Mockito.when(merchantUpdateService.getMerchantByStatusPendingandDecline())
          .thenReturn(merchants);
      mockMvc
          .perform(post("/" + URLMappingConstants.DECLINE_MERCHANT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param("declineReason", "declineReason"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_HOME));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testDeclineMerchant", e);
    }
  }

  @Test
  public void testDeclineMerchantElse() {
    updateMerchantResponse = new UpdateMerchantResponse();
    updateMerchantResponse.setUpdated(true);
    updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    merchants = new ArrayList<>();
    merchant = new Merchant();
    merchant.setAccountId(1l);
    merchants.add(merchant);
    try {
      Mockito.when(merchantService.updateMerchant(Matchers.any(Merchant.class)))
          .thenReturn(updateMerchantResponse);
      Mockito.when(merchantUpdateService.getMerchantByStatusPendingandDecline())
          .thenReturn(merchants);
      mockMvc
          .perform(post("/" + URLMappingConstants.DECLINE_MERCHANT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param("declineReason", "declineReason"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_HOME));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testDeclineMerchantElse", e);
    }
  }



  @Test
  public void testActiveMerchantSuccess() {
    updateMerchantResponse = new UpdateMerchantResponse();
    updateMerchantResponse.setUpdated(true);
    updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(merchantService.updateMerchant(Matchers.any(Merchant.class)))
          .thenReturn(updateMerchantResponse);
      mockMvc.perform(post("/" + URLMappingConstants.MERCHANT_PENDING_TO_ACTIVE_STATE))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_HOME));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testActiveMerchantSuccess", e);
    }
  }

  @Test
  public void testActiveMerchantFail() {
    updateMerchantResponse = new UpdateMerchantResponse();
    updateMerchantResponse.setUpdated(true);
    updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(merchantService.updateMerchant(Matchers.any(Merchant.class)))
          .thenReturn(updateMerchantResponse);
      mockMvc.perform(post("/" + URLMappingConstants.MERCHANT_PENDING_TO_ACTIVE_STATE))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_HOME));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testActiveMerchantFail", e);
    }
  }

  @Test
  public void testActiveMerchantException() {
    try {
      Mockito.when(merchantService.updateMerchant(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.MERCHANT_PENDING_TO_ACTIVE_STATE))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_HOME));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testActiveMerchantException", e);
    }
  }

  @Test
  public void testShowMerchantAndSubMerchantListException() {
    ModelAndView modelAndView = new ModelAndView();
    optionList = new ArrayList<Option>();
    option = new Option();
    option.setLabel("00");
    option.setValue("00");
    optionList.add(option);
    try {
      Mockito.when(merchantUpdateService.getCountries()).thenReturn(optionList);
      Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
          Matchers.any(Locale.class))).thenReturn("abc");
      Mockito.when(merchantController.showSearchMerchantPage(Matchers.any(HttpServletRequest.class),
          Matchers.any(HttpServletResponse.class), Matchers.any(Merchant.class),
          Matchers.any(BindingResult.class), Matchers.anyMap(), Matchers.any(HttpSession.class)))
          .thenReturn(modelAndView);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_SUB_MERCHANT_LIST)
          .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantValidationControllerTest :: testShowMerchantAndSubMerchantList", e);
    }
  }

  @Test
  public void testShowMerchantAndSubMerchantList() {
    optionList = new ArrayList<Option>();
    option = new Option();
    option.setLabel("00");
    option.setValue("00");
    optionList.add(option);
    merchant = new Merchant();
    responseval = new Response();
    responseval = new Response();
    responseval.setResponseList(null);
    merchantSearchResponse = new MerchantSearchResponse();
    merchantData = new MerchantData();
    merchantData.setAddress1("address1");
    merchantsData = new ArrayList<>();
    merchantsData.add(merchantData);
    merchantSearchResponse.setMerchants(merchantsData);
    merchantSearchResponse.setTotalNoOfRows(Integer.parseInt("1"));
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchant);
      Mockito.when(merchantUpdateService.getStatesByCountry(Matchers.anyString()))
          .thenReturn(responseval);
      Mockito.when(merchantUpdateService.getAgentNames(Matchers.anyString()))
          .thenReturn(responseval);
      Mockito.when(merchantUpdateService.searchSubMerchantList(Matchers.any(Merchant.class)))
          .thenReturn(merchantSearchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_SUB_MERCHANT_LIST)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantValidationControllerTest :: testShowMerchantAndSubMerchantList", e);
    }
  }

  @Test
  public void testGetSubMerchantListException() {
    optionList = new ArrayList<Option>();
    option = new Option();
    option.setLabel("00");
    option.setValue("00");
    optionList.add(option);
    merchant = new Merchant();
    responseval = new Response();
    responseval = new Response();
    responseval.setResponseList(null);
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchant);
      Mockito.when(merchantUpdateService.getStatesByCountry(Matchers.anyString()))
          .thenReturn(responseval);
      Mockito.when(merchantUpdateService.getAgentNames(Matchers.anyString()))
          .thenReturn(responseval);
      Mockito.when(merchantUpdateService.searchSubMerchantList(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_SUB_MERCHANT_LIST)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testGetSubMerchantListException",
          e);
    }
  }

  @Test
  public void testDownloadMerchantReportPDF() {
    merchant = new Merchant();
    merchantSearchResponse = new MerchantSearchResponse();
    merchantData = new MerchantData();
    merchantData.setAddress1("address1");
    merchantsData = new ArrayList<>();
    merchantsData.add(merchantData);
    merchantSearchResponse.setMerchants(merchantsData);
    try {
      Mockito.when(merchantUpdateService.searchMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchantSearchResponse);
      Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
          Matchers.any(Locale.class))).thenReturn("abc");
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_REPORT)
          .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin").sessionAttr("merchants", merchant)
          .param("downloadAllRecords", "true").param("downloadType", "PDF"));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testDownloadMerchantReportPDF", e);
    }
  }

  @Test
  public void testDownloadMerchantReportXLS() {
    merchant = new Merchant();
    merchantSearchResponse = new MerchantSearchResponse();
    merchantData = new MerchantData();
    merchantData.setAddress1("address1");
    merchantsData = new ArrayList<>();
    merchantsData.add(merchantData);
    merchantSearchResponse.setMerchants(merchantsData);
    try {
      Mockito.when(merchantUpdateService.searchMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchantSearchResponse);
      Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
          Matchers.any(Locale.class))).thenReturn("abc");
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_REPORT)
          .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin").sessionAttr("merchants", merchant)
          .param("downloadAllRecords", "true").param("downloadType", "XLS"));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testDownloadMerchantReportXLS", e);
    }
  }

  @Test
  public void testDownloadMerchantReportException() {
    try {
      Mockito.when(merchantUpdateService.searchMerchant(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_REPORT)
          .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin").sessionAttr("merchants", merchant));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantValidationControllerTest :: testDownloadMerchantReportException", e);
    }
  }

  @Test
  public void testGetPartnersByProgramManagerId() {
    responseval = new Response();
    try {
      Mockito.when(partnerService.getPartnersByProgramManagerId(Matchers.anyString())).thenReturn(responseval);
      mockMvc.perform(get("/" + URLMappingConstants.GET_PARTNERS_BY_PROGRAM_MANAGER_ID)
          .param("programManagerId", "programManagerId"));
    } catch (Exception e) {
      logger.error("Error :: MerchantValidationControllerTest :: testGetLocalCurrencyandbankName",
          e);
    }
  }

  @Test
  public void testGetPartnersByProgramManagerIdChatakAdminException() {
    responseval = new Response();
    try {
      Mockito.when(partnerService.getPartnersByProgramManagerId(Matchers.anyString()))
          .thenThrow(ChatakAdminException.class);
      mockMvc.perform(get("/" + URLMappingConstants.GET_PARTNERS_BY_PROGRAM_MANAGER_ID)
          .param("programManagerId", "programManagerId"));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantValidationControllerTest :: testGetLocalCurrencyandbankNameChatakAdminException",
          e);
    }
  }
}
