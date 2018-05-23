package com.chatak.merchant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chatak.merchant.service.CurrencyConfigService;
import com.chatak.merchant.service.MerchantInfoService;
import com.chatak.merchant.service.MerchantService;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.DeleteMerchantResponse;
import com.chatak.pg.user.bean.UpdateMerchantResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class SubMerchantControllerTest {

  private static Logger logger = Logger.getLogger(SubMerchantControllerTest.class);

  @InjectMocks
  SubMerchantController subMerchantController = new SubMerchantController();

  @Mock
  private MessageSource messageSource;

  @Mock
  MerchantService merchantService;

  @Mock
  CurrencyConfigService currencyConfigService;

  @Mock
  MerchantInfoService merchantInfoService;

  @Mock
  DeleteMerchantResponse deleteMerchantResponse;

  @Mock
  Response response;

  @Mock
  Merchant merchant;

  @Mock
  List<MerchantData> merchants;

  @Mock
  MerchantData merchantData;

  @Mock
  List<Option> countryList;

  @Mock
  MerchantSearchResponse searchResponse;

  @Mock
  UpdateMerchantResponse updateMerchantResponse;

  @Mock
  PGMerchant pgMerchant;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(subMerchantController).setViewResolvers(viewResolver)
        .build();
  }

  @Before
  public void pro() {
    java.util.Properties properties = new java.util.Properties();
    properties.setProperty("merchant.services.subMerchant.create.feature.id", "notExist");
    properties.setProperty("merchant.services.subMerchant.create.feature.id", "exist");
    properties.setProperty("chatak.merchant.portal", "www.merchant.com");
    properties.setProperty("merchant.services.subMerchant.feature.id", "notExist");
    properties.setProperty("merchant.services.subMerchant.feature.id", "exist");
    properties.setProperty("merchant.services.subMerchant.delete.feature.id", "exist");
    properties.setProperty("search-sub-merchant", "exist");
    properties.setProperty("viewSubMerchant", "exist");
    Properties.mergeProperties(properties);
  }

  @Test
  public void testCreateMerchantExistingFeature() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_PROCESS_SUMBERCHANT_CREATION)
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist"))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testCreateMerchantExistingFeature:", e);
    }
  }

  @Test
  public void testCreateMerchant() {
    response = new Response();
    response.setCurrencyCodeNumeric("123");
    merchant = new Merchant();
    merchant.setCurrencyId(Matchers.anyString());
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_PROCESS_SUMBERCHANT_CREATION)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .param("legalAnnualCard", PGConstants.DOLLAR_SYMBOL).param("currencyId", "122")
              .param("currencyCodeNumeric", "123"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testCreateMerchant:", e);
    }
  }

  @Test
  public void testShowSearchMerchantPageExistingFeature() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, "exist")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr("parentMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.SUBMERCHANT_ERROR_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: SubMerchantControllerTest :: testShowSearchMerchantPageExistingFeature:", e);
    }
  }

  @Test
  public void testShowSearchMerchantPageMerchantIdNull() {
    try {

      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr("parentMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.SUBMERCHANT_ERROR_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: SubMerchantControllerTest :: testShowSearchMerchantPageMerchantIdNull:", e);
    }
  }

  @Test
  public void testShowSearchMerchantPage() {
    searchResponse = new MerchantSearchResponse();
    merchantData();
    try {
      Mockito.when(merchantInfoService.getCountries()).thenReturn(countryList);
      Mockito.when(merchantInfoService.searchSubMerchantList(Matchers.any(Merchant.class)))
          .thenReturn(searchResponse);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE)
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))

          .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testShowSearchMerchantPage:", e);
    }
  }

  private void merchantData() {
    merchants = new ArrayList<>();
    merchantData = new MerchantData();
    merchantData.setAddress1("address1");
    merchants.add(merchantData);
    countryList = new ArrayList<>();
    searchResponse.setErrorCode(Constants.ERROR_CODE);
    searchResponse.setMerchants(merchants);
    searchResponse.setTotalNoOfRows(Integer.parseInt("10"));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testShowSearchMerchantPageException() {
    try {
      Mockito.when(merchantInfoService.getCountries()).thenReturn(countryList);
      Mockito.when(merchantInfoService.searchSubMerchantList(Matchers.any(Merchant.class)))
          .thenThrow(NullPointerException.class);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testShowSearchMerchantPageException:", e);
    }
  }

  @Test
  public void testSearchMerchantExistingFeature() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testSearchMerchantExistingFeature:", e);
    }
  }

  @Test
  public void testSearchMerchant() {
    merchant = new Merchant();
    searchResponse = new MerchantSearchResponse();
    merchantData();
    try {
      Mockito.when(merchantService.searchMerchant(Matchers.any(Merchant.class)))
          .thenReturn(searchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "exist").param("subMerchantCode", "12355"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testSearchMerchant:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testSearchMerchantException() {
    merchant = new Merchant();
    searchResponse = new MerchantSearchResponse();
    merchantData();
    try {
      Mockito.when(merchantService.searchMerchant(Matchers.any(Merchant.class)))
          .thenThrow(NullPointerException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "exist").param("subMerchantCode", "12355"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testSearchMerchantException:", e);
    }
  }


  @Test
  public void testDeleteMerchantExistingFeature() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_DELETE_SUB_MERCHANT)
              .param("getMerchantsId", "10")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testDeleteMerchantExistingFeature:", e);
    }
  }

  @Test
  public void testDeleteMerchant() {
    deleteMerchantResponse = new DeleteMerchantResponse();
    deleteMerchantResponse.setIsdeleated(true);
    try {
      Mockito.when(merchantInfoService.deleteMerchant(Matchers.anyLong()))
          .thenReturn(deleteMerchantResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_DELETE_SUB_MERCHANT)
          .param("getMerchantsId", "10").sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
          .sessionAttr(Constants.EXISTING_FEATURES, "exist"));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testDeleteMerchant:", e);
    }
  }

  @Test
  public void testDeleteMerchantElse() {
    deleteMerchantResponse = new DeleteMerchantResponse();
    deleteMerchantResponse.setIsdeleated(false);
    try {
      Mockito.when(merchantInfoService.deleteMerchant(Matchers.anyLong()))
          .thenReturn(deleteMerchantResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_DELETE_SUB_MERCHANT)
          .param("getMerchantsId", "10").sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
          .sessionAttr(Constants.EXISTING_FEATURES, "exist"));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testDeleteMerchantElse:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testDeleteMerchantException() {
    deleteMerchantResponse = new DeleteMerchantResponse();
    deleteMerchantResponse.setIsdeleated(false);
    try {
      Mockito.when(merchantInfoService.deleteMerchant(Matchers.anyLong()))
          .thenThrow(NullPointerException.class);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_DELETE_SUB_MERCHANT)
          .param("getMerchantsId", "10").sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
          .sessionAttr(Constants.EXISTING_FEATURES, "exist"));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testDeleteMerchantException:", e);
    }
  }

  @Test
  public void testUpdateSubMerchantExistingFeature() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_PROCESS_SUB_MERCHANT_UPDATE_PAGE)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testUpdateSubMerchantExistingFeature:",
          e);
    }
  }

  @Test
  public void testUpdateSubMerchant() {
    merchant = new Merchant();
    updateMerchantResponse = new UpdateMerchantResponse();
    updateMerchantResponse.setUpdated(true);
    updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);

    try {
      Mockito.when(currencyConfigService.getCurrencyCodeNumeric(Matchers.anyString()))
          .thenReturn(response);
      Mockito.when(merchantService.updateMerchant(Matchers.any(Merchant.class)))
          .thenReturn(updateMerchantResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_PROCESS_SUB_MERCHANT_UPDATE_PAGE)
              .param("legalAnnualCard", "$123").sessionAttr("currencyId", "123")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testUpdateSubMerchant:", e);
    }

  }

  @Test
  public void testUpdateSubMerchantElse() {
    merchant = new Merchant();

    try {
      Mockito.when(currencyConfigService.getCurrencyCodeNumeric(Matchers.anyString()))
          .thenReturn(response);
      Mockito.when(merchantService.updateMerchant(Matchers.any(Merchant.class)))
          .thenReturn(updateMerchantResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_PROCESS_SUB_MERCHANT_UPDATE_PAGE)
          .param("legalAnnualCard", "$123").sessionAttr("currencyId", "123")
          .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
          .sessionAttr(Constants.EXISTING_FEATURES, "exist"));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testUpdateSubMerchantElse:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testUpdateSubMerchantException() {
    merchant = new Merchant();

    try {
      Mockito.when(currencyConfigService.getCurrencyCodeNumeric(Matchers.anyString()))
          .thenThrow(NullPointerException.class);
      Mockito.when(merchantService.updateMerchant(Matchers.any(Merchant.class)))
          .thenReturn(updateMerchantResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_PROCESS_SUB_MERCHANT_UPDATE_PAGE)
              .param("legalAnnualCard", "$123").sessionAttr("currencyId", "123")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testUpdateSubMerchantException:", e);
    }
  }

  @Test
  public void testShowViewSubMerchantExistingFeature() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_VIEW_SUB_MERCHANT_UPDATE_PAGE)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_VIEW_SUB_MERCHANT_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testShowViewSubMerchantExistingFeature:",
          e);
    }
  }

  @Test
  public void testShowViewSubMerchant() throws ChatakMerchantException {
    Mockito.when(merchantService.getMerchant(Matchers.any(Merchant.class))).thenReturn(merchant);
    Mockito.when(merchantInfoService.getStatesByCountry(Matchers.anyString())).thenReturn(response);
    Mockito.when(merchantInfoService.getAgentNames(Matchers.anyString())).thenReturn(response);
    Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong())).thenReturn(pgMerchant);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_VIEW_SUB_MERCHANT_UPDATE_PAGE)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_VIEW_SUB_MERCHANT_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testShowViewSubMerchant:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testShowViewSubMerchantException() throws ChatakMerchantException {
    Mockito.when(merchantService.getMerchant(Matchers.any(Merchant.class)))
        .thenThrow(ChatakMerchantException.class);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_VIEW_SUB_MERCHANT_UPDATE_PAGE)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_VIEW_SUB_MERCHANT_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testShowViewSubMerchantException:", e);
    }
  }

  @Test
  public void testChangeSubMerchantStatusExistingFeatures() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SUB_MERCHANT_STATUS_CHANGE)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: SubMerchantControllerTest :: testChangeSubMerchantStatusExistingFeatures:", e);
    }
  }


  @Test
  public void testChangeSubMerchantStatus() {
    response = new Response();
    response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(merchantInfoService.changeMerchantStatus(Matchers.any(MerchantData.class)))
          .thenReturn(response);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SUB_MERCHANT_STATUS_CHANGE)
              .param(Constants.TOTAL_RECORDS, "12").param("pageNumber", "1")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testChangeSubMerchantStatus:", e);
    }
  }

  @Test
  public void testChangeSubMerchantStatusElse() {
    response = new Response();
    response.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(merchantInfoService.changeMerchantStatus(Matchers.any(MerchantData.class)))
          .thenReturn(response);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SUB_MERCHANT_STATUS_CHANGE)
              .param(Constants.TOTAL_RECORDS, "12")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testChangeSubMerchantStatusElse:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testChangeSubMerchantStatusException() {
    try {
      Mockito.when(merchantInfoService.changeMerchantStatus(Matchers.any(MerchantData.class)))
          .thenThrow(NullPointerException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SUB_MERCHANT_STATUS_CHANGE)
              .param(Constants.TOTAL_RECORDS, "12")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testChangeSubMerchantStatusException:",
          e);
    }
  }

  @Test
  public void testGetPaginationList() {
    searchResponse = new MerchantSearchResponse();
    searchResponse.setTotalNoOfRows(Integer.parseInt("10"));
    merchant = new Merchant();
    merchants = new ArrayList<>();
    merchantData = new MerchantData();
    merchantData.setAddress1("address1");
    merchants.add(merchantData);
    searchResponse.setMerchants(merchants);
    response = new Response();
    response.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(merchantInfoService.searchSubMerchantList(Matchers.any(Merchant.class)))
          .thenReturn(searchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_SUB_MERCHANTS_PAGINATION)
              .sessionAttr("merchants", merchant).param("pageSize", "10")
              .param(Constants.TOTAL_RECORDS, "12").param("pageNumber", "112")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testGetPaginationList:", e);
    }
  }

  @Test
  public void testShowCreateSubMerchantPage() {
    pgMerchant = new PGMerchant();
    pgMerchant.setLocalCurrency("$");
    Map<String, String> mainMerchantMap = new HashMap<>();
    mainMerchantMap.put("user", "admin");
    try {
      Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong())).thenReturn(pgMerchant);
      Mockito.when(merchantInfoService.getMerchantMapByMerchantType(Matchers.anyString())).thenReturn(mainMerchantMap);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_CREATION_PAGE)
              .param(Constants.TOTAL_RECORDS, "12")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_CREATION_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testShowCreateSubMerchantPage:", e);
    }
  }

  @Test
  public void testShowCreateSubMerchantPageExistingFeature() {
    pgMerchant = new PGMerchant();
    pgMerchant.setLocalCurrency("$");
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_CREATION_PAGE)
              .param(Constants.TOTAL_RECORDS, "12")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist"))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: SubMerchantControllerTest :: testShowCreateSubMerchantPageExistingFeature:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testShowCreateSubMerchantPageChatakMerchantException() {
    pgMerchant = new PGMerchant();
    pgMerchant.setLocalCurrency("$");
    try {
      Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong()))
          .thenThrow(ChatakMerchantException.class);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_CREATION_PAGE)
              .param(Constants.TOTAL_RECORDS, "12")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_CREATION_PAGE));
    } catch (Exception e) {
      logger.error(
          "ERROR :: SubMerchantControllerTest :: testShowCreateSubMerchantPageChatakMerchantException:",
          e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testShowEditSubMerchantExistingFeature() {
    try {
      Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong()))
          .thenThrow(ChatakMerchantException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_UPDATE_PAGE)
              .param(Constants.TOTAL_RECORDS, "12")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "notExist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_PROCESS_SUB_MERCHANT_UPDATE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testShowEditSubMerchantExistingFeature:",
          e);
    }
  }

  @Test
  public void testShowEditSubMerchant() {
    merchant = new Merchant();
    merchant.setAllowRepeatSale(true);
    try {
      Mockito.when(merchantService.getMerchant(Matchers.any(Merchant.class))).thenReturn(merchant);
      Mockito.when(merchantInfoService.getMerchantOnId(Matchers.anyLong())).thenReturn(pgMerchant);
      Mockito.when(merchantInfoService.getAgentNames(Matchers.anyString())).thenReturn(response);
      Mockito.when(merchantInfoService.getStatesByCountry(Matchers.anyString()))
          .thenReturn(response);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_UPDATE_PAGE)
              .param(Constants.TOTAL_RECORDS, "12")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_PROCESS_SUB_MERCHANT_UPDATE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testShowEditSubMerchant:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testShowEditSubMerchantException() {
    merchant = new Merchant();
    merchant.setAllowRepeatSale(true);
    try {
      Mockito.when(merchantService.getMerchant(Matchers.any(Merchant.class)))
          .thenThrow(ChatakMerchantException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_SHOW_SUB_MERCHANT_UPDATE_PAGE)
              .param(Constants.TOTAL_RECORDS, "12")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234"))
              .sessionAttr(Constants.EXISTING_FEATURES, "exist"))
          .andExpect(view().name(URLMappingConstants.CHATAK_PROCESS_SUB_MERCHANT_UPDATE_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testShowEditSubMerchantException:", e);
    }
  }
  
  @Test
  public void testGetPartnerName() {
    response = new Response();
    response.setAgentAccountNumber(Long.parseLong("2156161566"));
    try {
      Mockito.when(merchantService.findPartnerByMerchantCode(Matchers.anyString())).thenReturn(response);
      mockMvc
      .perform(get("/" + URLMappingConstants.GET_PARTNER_NAME_BY_MERCHANT_CODE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testGetPartnerName:", e);
    }
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void testGetPartnerNameExp() {
    response = new Response();
    response.setAgentAccountNumber(Long.parseLong("2156161566"));
    try {
      Mockito.when(merchantService.findPartnerByMerchantCode(Matchers.anyString())).thenThrow(ChatakMerchantException.class);
      mockMvc.perform(get("/" + URLMappingConstants.GET_PARTNER_NAME_BY_MERCHANT_CODE));
    } catch (Exception e) {
      logger.error("ERROR :: SubMerchantControllerTest :: testGetPartnerName:", e);
    }
  }

}

