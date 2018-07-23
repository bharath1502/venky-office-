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
import com.chatak.acquirer.admin.model.MerchantData;
import com.chatak.acquirer.admin.model.MerchantSearchResponse;
import com.chatak.acquirer.admin.service.BankService;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.acquirer.admin.service.MerchantCategoryCodeService;
import com.chatak.acquirer.admin.service.MerchantService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.MerchantValidateService;
import com.chatak.acquirer.admin.service.ProgramManagerService;
import com.chatak.acquirer.admin.service.ResellerService;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.MerchantCurrencyMapping;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.MerchantResponse;
import com.chatak.pg.user.bean.UpdateMerchantResponse;
import com.chatak.pg.util.Constants;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jan 5, 2018 12:52:43 PM
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class MerchantControllerTest {
	
  private static Logger logger = Logger.getLogger(MerchantControllerTest.class);

  @InjectMocks
  MerchantController controller = new MerchantController();

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
  MerchantResponse selectedCurrencyList;

  @Mock
  private BankService bankService;

  @Mock
  private ResellerService resellerService;

  @Mock
  private MerchantCategoryCodeService merchantCategoryCodeService;

  @Mock
  private CurrencyConfigService currencyConfigService;

  @Mock
  private MerchantUpdateService merchantUpdateService;

  @Mock
  private MerchantValidateService merchantValidateService;

  @Mock
  private AddMerchantResponse addMerchantResponse;

  @Mock
  private MerchantSearchResponse merchantSearchResponse;

  @Mock
  private List<MerchantData> merchantsData;

  @Mock
  private MerchantData merchantData;

  @Mock
  private Merchant merchant;

  @Mock
  private List<MerchantCurrencyMapping> merchantCurrencyMappings;

  @Mock
  private MerchantCurrencyMapping merchantCurrencyMapping;

  @Mock
  private PGMerchant pgMerchant;

  @Mock
  private UpdateMerchantResponse updateMerchantResponse;

  @Mock
  private ProgramManagerService programManagerService;

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
  public void testShowCreateMerchantPage() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_MERCHANT_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_MERCHANT_PAGE));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testShowCreateMerchantPage", e);
    }
  }

  @Test
  public void testShowCreateMerchantPageChatakAdminException() {
    try {
      Mockito.when(merchantValidateService.getFeeProgramNames())
          .thenThrow(ChatakAdminException.class);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_MERCHANT_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_MERCHANT_PAGE));
    } catch (Exception e) {
      logger.error(
          "Error :: MerchantControllerTest :: testShowCreateMerchantPageChatakAdminException", e);
    }
  }

  @Test
  public void testShowCreateMerchantPageException() {
    try {
      Mockito.when(merchantValidateService.getFeeProgramNames()).thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_MERCHANT_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_MERCHANT_PAGE));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testShowCreateMerchantPageException", e);
    }
  }

  @Test
  public void testCreateMerchantSuccess() {
    responseval = new Response();
    responseval.setCurrencyCodeNumeric("840");
    addMerchantResponse = new AddMerchantResponse();
    addMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(currencyConfigService.getCurrencyCodeNumeric(Matchers.anyString()))
          .thenReturn(responseval);
      Mockito.when(merchantService.addMerchant(Matchers.any(Merchant.class), Matchers.anyString()))
          .thenReturn(addMerchantResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_MERCHANT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).sessionAttr("loginUserId", 1l)
              .param("legalAnnualCard", "$1111").param("currencyCode", "USD")
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_MERCHANT_PAGE));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testCreateMerchantSuccess", e);
    }
  }

  @Test
  public void testCreateMerchantDuplicate() {
    responseval = new Response();
    responseval.setCurrencyCodeNumeric("840");
    addMerchantResponse = new AddMerchantResponse();
    addMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
    try {
      Mockito.when(currencyConfigService.getCurrencyCodeNumeric(Matchers.anyString()))
          .thenReturn(responseval);
      Mockito.when(merchantService.addMerchant(Matchers.any(Merchant.class), Matchers.anyString()))
          .thenReturn(addMerchantResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_MERCHANT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).sessionAttr("loginUserId", 1l)
              .param("legalAnnualCard", "$1111").param("currencyCode", "USD")
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_MERCHANT_PAGE));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testCreateMerchantDuplicate", e);
    }
  }

  @Test
  public void testCreateMerchantElse() {
    responseval = new Response();
    responseval.setCurrencyCodeNumeric("840");
    addMerchantResponse = new AddMerchantResponse();
    addMerchantResponse.setErrorCode(Constants.EMPTY_STRING);
    try {
      Mockito.when(currencyConfigService.getCurrencyCodeNumeric(Matchers.anyString()))
          .thenReturn(responseval);
      Mockito.when(merchantService.addMerchant(Matchers.any(Merchant.class), Matchers.anyString()))
          .thenReturn(addMerchantResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_MERCHANT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).sessionAttr("loginUserId", 1l)
              .param("legalAnnualCard", "$1111").param("currencyCode", "USD")
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_MERCHANT_PAGE));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testCreateMerchantElse", e);
    }
  }

  @Test
  public void testCreateMerchantException() {
    try {
      Mockito.when(currencyConfigService.getCurrencyCodeNumeric(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_MERCHANT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).sessionAttr("loginUserId", 1l)
              .param("legalAnnualCard", "1111").param("currencyCode", "USD")
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_MERCHANT_PAGE));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testCreateMerchantException", e);
    }
  }

  @Test
  public void testShowSearchMerchantPageException() {
    try {
      Mockito.when(merchantUpdateService.getCountries()).thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testShowSearchMerchantPageException", e);
    }
  }

  @Test
  public void testSearchMerchant() {
    merchantSearchResponse = new MerchantSearchResponse();
    merchantData = new MerchantData();
    merchantData.setAddress1("address1");
    merchantsData = new ArrayList<>();
    merchantsData.add(merchantData);
    merchantSearchResponse.setMerchants(merchantsData);
    merchantSearchResponse.setSubMerchants(merchantsData);
    merchantSearchResponse.setTotalNoOfRows(Integer.parseInt("1"));
    try {
      Mockito.when(merchantUpdateService.searchMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchantSearchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testSearchMerchant", e);
    }
  }

  @Test
  public void testSearchMerchantException() {
    try {
      Mockito.when(merchantUpdateService.searchMerchant(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testSearchMerchantException", e);
    }
  }

  @Test
  public void testShowEditMerchant() {
    merchantCurrencyMapping = new MerchantCurrencyMapping();
    merchantCurrencyMapping.setCurrencyCode("USD");
    merchantCurrencyMappings = new ArrayList<>();
    merchantCurrencyMappings.add(merchantCurrencyMapping);
    merchant = new Merchant();
    merchant.setStatus(Integer.parseInt("1"));
    merchant.setSelectedCurrencyMapping(merchantCurrencyMappings);
    merchant.setIssuancePartnerId("issuancePartnerId");
    responseval = new Response();
    responseval.setResponseList(null);
    option = new Option();
    option.setLabel("USD");
    option.setValue("840");
    optionList = new ArrayList<>();
    optionList.add(option);
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchant);
      Mockito.when(merchantUpdateService.getAgentNames(Matchers.anyString()))
          .thenReturn(responseval);
      Mockito.when(merchantUpdateService.getCurrencies()).thenReturn(optionList);
      Mockito.when(merchantUpdateService.findByMerchantId(Matchers.anyLong())).thenReturn(selectedCurrencyList);
      Mockito.when(merchantUpdateService.getStatesByCountry(Matchers.anyString()))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_EDIT_MERCHANT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_EDIT_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testShowEditMerchant", e);
    }
  }

  @Test
  public void testShowEditMerchantChatakAdminException() {
    try {
      Mockito.when(merchantUpdateService.getCountries()).thenThrow(ChatakAdminException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_EDIT_MERCHANT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testShowEditMerchantChatakAdminException",
          e);
    }
  }

  @Test
  public void testShowEditMerchantException() {
    try {
      Mockito.when(merchantUpdateService.getCountries()).thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_EDIT_MERCHANT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_EDIT_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testShowEditMerchantException", e);
    }
  }

  @Test
  public void testGetPaginationList() {
    merchantSearchResponse = new MerchantSearchResponse();
    merchantData = new MerchantData();
    merchantData.setAddress1("address1");
    merchantsData = new ArrayList<>();
    merchantsData.add(merchantData);
    merchantSearchResponse.setMerchants(merchantsData);
    merchantSearchResponse.setSubMerchants(merchantsData);
    merchantSearchResponse.setTotalNoOfRows(Integer.parseInt("1"));
    try {
      Mockito.when(merchantUpdateService.searchMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchantSearchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANTS_PAGINATION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin")
              .sessionAttr("merchants", merchant).param(Constants.PAGE_NUMBER, "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testGetPaginationList", e);
    }
  }

  @Test
  public void testGetPaginationListException() {
    merchant = new Merchant();
    try {
      Mockito.when(merchantUpdateService.searchMerchant(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANTS_PAGINATION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin")
              .sessionAttr("merchants", merchant).param(Constants.PAGE_NUMBER, "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testGetPaginationListException", e);
    }
  }

  @Test
  public void testChangeMerchantStatus() {
    responseval = new Response();
    responseval.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    responseval.setErrorMessage("Approved");
    try {
      Mockito.when(merchantValidateService.changeMerchantStatus(Matchers.any(MerchantData.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_STATUS_CHANGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin")
              .sessionAttr(Constants.PAGE_NUMBER, 1).sessionAttr(Constants.TOTAL_RECORDS, 1)
              .sessionAttr("merchants", merchant).param(Constants.PAGE_NUMBER, "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testChangeMerchantStatus", e);
    }
  }

  @Test
  public void testChangeMerchantStatusElse() {
    responseval = new Response();
    responseval.setErrorCode(ActionErrorCode.ERROR_CODE_18);
    responseval.setErrorMessage("Retry");
    try {
      Mockito.when(merchantValidateService.changeMerchantStatus(Matchers.any(MerchantData.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_STATUS_CHANGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin")
              .sessionAttr(Constants.PAGE_NUMBER, 1).sessionAttr(Constants.TOTAL_RECORDS, 1)
              .sessionAttr("merchants", merchant).param(Constants.PAGE_NUMBER, "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testChangeMerchantStatusElse", e);
    }
  }

  @Test
  public void testChangeMerchantStatusException() {
    try {
      Mockito.when(merchantValidateService.changeMerchantStatus(Matchers.any(MerchantData.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_MERCHANT_STATUS_CHANGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testChangeMerchantStatusException", e);
    }
  }

  @Test
  public void testUpdateMerchant() {
    merchantSearchResponse = new MerchantSearchResponse();
    pgMerchant = new PGMerchant();
    pgMerchant.setStatus(Integer.parseInt("1"));
    merchantSearchResponse.setMerchantData(pgMerchant);
    responseval = new Response();
    responseval.setCurrencyCodeNumeric("currencyCodeNumeric");
    updateMerchantResponse = new UpdateMerchantResponse();
    updateMerchantResponse.setUpdated(true);
    updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(merchantUpdateService.getMerchantCode(Matchers.anyString()))
          .thenReturn(merchantSearchResponse);
      Mockito.when(currencyConfigService.getCurrencyCodeNumeric(Matchers.anyString()))
          .thenReturn(responseval);
      Mockito.when(merchantService.updateMerchant((Matchers.any(Merchant.class))))
          .thenReturn(updateMerchantResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_MERCHANT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin")
              .sessionAttr(Constants.PREVIOUS_CI_PARTNER_ID, "1").param("issuancePartnerId", "2")
              .param("merchantType", "subMerchant").param("legalAnnualCard", "$1111")
              .param("currencyId", "currencyId").param("currencyCode", "USD"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testUpdateMerchant", e);
    }
  }

  @Test
  public void testUpdateMerchantElse() {
    merchantSearchResponse = new MerchantSearchResponse();
    pgMerchant = new PGMerchant();
    pgMerchant.setStatus(Integer.parseInt("1"));
    merchantSearchResponse.setMerchantData(pgMerchant);
    responseval = new Response();
    responseval.setCurrencyCodeNumeric("currencyCodeNumeric");
    updateMerchantResponse = new UpdateMerchantResponse();
    updateMerchantResponse.setUpdated(false);
    updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    responseval = new Response();
    responseval.setResponseList(null);
    try {
      Mockito.when(merchantUpdateService.getMerchantCode(Matchers.anyString()))
          .thenReturn(merchantSearchResponse);
      Mockito.when(currencyConfigService.getCurrencyCodeNumeric(Matchers.anyString()))
          .thenReturn(responseval);
      Mockito.when(merchantService.updateMerchant((Matchers.any(Merchant.class))))
          .thenReturn(updateMerchantResponse);
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchant);
      Mockito.when(merchantUpdateService.getStatesByCountry(Matchers.anyString()))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_MERCHANT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin")
              .sessionAttr(Constants.PREVIOUS_CI_PARTNER_ID, "1")
              .param("issuancePartnerId", Constants.EMPTY_STRING)
              .param("merchantType", "subMerchant").param("legalAnnualCard", "1111")
              .param("currencyId", "currencyId").param("currencyCode", "USD")
              .sessionAttr(Constants.STATUS, 1).param("getMerchantId", "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testUpdateMerchantElse", e);
    }
  }

  @Test
  public void testUpdateMerchantException() {
    try {
      Mockito.when(merchantUpdateService.getMerchantCode(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_MERCHANT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testUpdateMerchantException", e);
    }
  }
  
  @Test
  public void testGetProgramManagerDetailsByPartner() {
    responseval = new Response();
    responseval.setAgentAccountNumber(Long.parseLong("15646545656"));
    try {
      Mockito.when(merchantService.findProgramManagerByPartnerId(Matchers.anyString())).thenReturn(responseval);
      mockMvc
      .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_GET_PM_DATA_BY_PARTNER));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testGetProgramManagerDetailsByPartner", e);
    }
  }
  
  @Test
  public void testGetProgramManagerDetailsByPartnerExp() {
    try {
      Mockito.when(merchantService.findProgramManagerByPartnerId(Matchers.anyString())).thenThrow(ChatakAdminException.class);
      mockMvc
      .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_GET_PM_DATA_BY_PARTNER));
    } catch (Exception e) {
      logger.error("Error :: MerchantControllerTest :: testGetProgramManagerDetailsByPartner", e);
    }
  }
}
