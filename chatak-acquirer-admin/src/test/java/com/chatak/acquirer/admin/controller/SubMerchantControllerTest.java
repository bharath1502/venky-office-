/**
 * 
 */
package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import com.chatak.acquirer.admin.model.MerchantData;
import com.chatak.acquirer.admin.model.MerchantSearchResponse;
import com.chatak.acquirer.admin.service.BankService;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.acquirer.admin.service.MerchantAccountService;
import com.chatak.acquirer.admin.service.MerchantCategoryCodeService;
import com.chatak.acquirer.admin.service.MerchantService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.MerchantValidateService;
import com.chatak.acquirer.admin.service.ResellerService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.DeleteMerchantResponse;
import com.chatak.pg.user.bean.UpdateMerchantResponse;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jan 4, 2018 10:59:27 AM
 * @version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class SubMerchantControllerTest {

  private static Logger logger = Logger.getLogger(SubMerchantControllerTest.class);

  @InjectMocks
  private SubMerchantController controller = new SubMerchantController();

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private BindingResult bindingResult;

  private MockMvc mockMvc;

  @Mock
  private MessageSource messageSource;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  private MerchantUpdateService merchantUpdateService;

  @Mock
  private BankService bankService;

  @Mock
  private ResellerService resellerService;

  @Mock
  private MerchantCategoryCodeService merchantCategoryCodeService;

  @Mock
  private CurrencyConfigService currencyConfigService;

  @Mock
  private MerchantController merchantController;

  @Mock
  private MerchantService merchantService;

  @Mock
  private MerchantValidateService merchantValidateService;

  @Mock
  private MerchantAccountService merchantAccountService;

  @Mock
  private MerchantSearchResponse merchantSearchResponse;

  @Mock
  private List<MerchantData> merchants;

  @Mock
  private MerchantData merchant;

  @Mock
  private Map<String, String> mainMerchantMap;

  @Mock
  private AddMerchantResponse addMerchantResponse;

  @Mock
  private Merchant merchantResponse;

  @Mock
  private Response response2;

  @Mock
  private UpdateMerchantResponse updateMerchantResponse;

  @Mock
  private DeleteMerchantResponse deleteMerchantResponse;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
  }

  @Test
  public void testShowSearchSubMerchantPage() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT_PAGE)
              .sessionAttr("existingFeatures", "exist").sessionAttr("loginUserType", "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testShowSearchSubMerchantPage", e);
    }
  }

  @Test
  public void testShowSearchSubMerchantPageException() {
    try {
      Mockito.when(merchantUpdateService.getCountries()).thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT_PAGE)
              .sessionAttr("existingFeatures", "exist").sessionAttr("loginUserType", "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testShowSearchSubMerchantPageException",
          e);
    }
  }

  @Test
  public void testSubMerchantSearch() {
    merchant = new MerchantData();
    merchant.setAddress1("address1");
    merchants = new ArrayList<>();
    merchants.add(merchant);
    merchantSearchResponse = new MerchantSearchResponse();
    merchantSearchResponse.setMerchants(merchants);
    merchantSearchResponse.setTotalNoOfRows(Integer.parseInt("1"));
    try {
      Mockito.when(merchantUpdateService.searchSubMerchants(Matchers.any(Merchant.class), Matchers.anyString(), Matchers.anyLong()))
          .thenReturn(merchantSearchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT)
              .sessionAttr("existingFeatures", "exist").sessionAttr("loginUserType", "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testSubMerchantSearch", e);
    }
  }

  @Test
  public void testSubMerchantSearchResponseNull() {
    try {
      Mockito.when(merchantUpdateService.searchSubMerchants(Matchers.any(Merchant.class), Matchers.anyString(), Matchers.anyLong()))
          .thenReturn(null);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT)
              .sessionAttr("existingFeatures", "exist").sessionAttr("loginUserType", "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testSubMerchantSearchResponseNull", e);
    }
  }

  @Test
  public void testSubMerchantSearchResponseException() {
    try {
      Mockito.when(merchantUpdateService.searchSubMerchants(Matchers.any(Merchant.class), Matchers.anyString(), Matchers.anyLong()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT)
              .sessionAttr("existingFeatures", "exist").sessionAttr("loginUserType", "superAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testSubMerchantSearchResponseException",
          e);
    }
  }

  @Test
  public void testShowCreateSubMerchantPage() {
    mainMerchantMap = new TreeMap<>();
    mainMerchantMap.put("0", "0");
    mainMerchantMap.put("1", "1");
    try {
      Mockito.when(merchantAccountService.getMerchantMapByMerchantType(Matchers.anyString()))
          .thenReturn(mainMerchantMap);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_SUB_MERCHANT_PAGE)
              .sessionAttr("existingFeatures", "exist").sessionAttr("loginUserType", "admin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_SUB_MERCHANT_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testShowCreateSubMerchantPage", e);
    }
  }

  @Test
  public void testShowCreateSubMerchantPageException() {
    try {
      Mockito.when(merchantAccountService.getMerchantMapByMerchantType(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_SUB_MERCHANT_PAGE)
              .sessionAttr("existingFeatures", "exist").sessionAttr("loginUserType", "admin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_SUB_MERCHANT_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testShowCreateSubMerchantPageException",
          e);
    }
  }

  @Test
  public void testCreateSubMerchantDoller() {
    addMerchantResponse = new AddMerchantResponse();
    addMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(merchantService.addMerchant(Matchers.any(Merchant.class), Matchers.anyString()))
          .thenReturn(addMerchantResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_SUB_MERCHANT)
              .sessionAttr("existingFeatures", "exist").sessionAttr("loginUserType", "admin")
              .param("legalAnnualCard", "$1111"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testCreateSubMerchantDoller", e);
    }
  }

  @Test
  public void testCreateSubMerchantDuplicate() {
    addMerchantResponse = new AddMerchantResponse();
    addMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
    try {
      Mockito.when(merchantService.addMerchant(Matchers.any(Merchant.class), Matchers.anyString()))
          .thenReturn(addMerchantResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_SUB_MERCHANT)
              .sessionAttr("existingFeatures", "exist").sessionAttr("loginUserType", "admin")
              .param("legalAnnualCard", ".1111"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_SUB_MERCHANT_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testCreateSubMerchantDuplicate", e);
    }
  }

  @Test
  public void testCreateSubMerchantError() {
    try {
      Mockito.when(merchantService.addMerchant(Matchers.any(Merchant.class), Matchers.anyString()))
          .thenReturn(null);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_SUB_MERCHANT)
              .sessionAttr("existingFeatures", "exist").sessionAttr("loginUserType", "admin")
              .param("legalAnnualCard", ".1111"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_SUB_MERCHANT_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testCreateSubMerchantError", e);
    }
  }

  @Test
  public void testCreateSubMerchantException() {
    try {
      Mockito.when(merchantService.addMerchant(Matchers.any(Merchant.class), Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CREATE_SUB_MERCHANT)
              .sessionAttr("existingFeatures", "exist").sessionAttr("loginUserType", "admin")
              .param("legalAnnualCard", ".1111"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CREATE_SUB_MERCHANT_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testCreateSubMerchantException", e);
    }
  }

  @Test
  public void testShowViewSubMerchantNull() {

    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIEW_MERCHANT_PAGE)
          .sessionAttr("existingFeatures", "exist").sessionAttr("loginUserType", "superAdmin")
          .param("merchantType", "SubMerchant"));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testShowViewSubMerchantNull", e);
    }
  }

  @Test
  public void testShowViewSubMerchant() {
    merchantResponse = new Merchant();
    response2 = new Response();
    response2.setCurrencyCodeAlpha("currencyCodeAlpha");
    response2.setResponseList(merchants);
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchantResponse);
      Mockito.when(currencyConfigService.getcurrencyCodeAlpha(Matchers.anyString()))
          .thenReturn(response2);
      Mockito.when(merchantUpdateService.getStatesByCountry(Matchers.anyString()))
          .thenReturn(response2);
      Mockito.when(merchantUpdateService.getAgentNames(Matchers.anyString())).thenReturn(response2);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIEW_MERCHANT_PAGE)
          .sessionAttr("existingFeatures", "exist").sessionAttr("loginUserType", "superAdmin")
          .param("merchantType", "SubMerchant"));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testShowViewSubMerchant", e);
    }
  }

  @Test
  public void testShowViewSubMerchantException() {
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_VIEW_MERCHANT_PAGE)
          .sessionAttr("existingFeatures", "exist").sessionAttr("loginUserType", "superAdmin")
          .param("merchantType", "SubMerchant"));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testShowViewSubMerchantException", e);
    }
  }

  @Test
  public void testGetSubMerchantPaginationList() {
    Merchant subMerchant = new Merchant();
    merchant = new MerchantData();
    merchant.setAddress1("address1");
    merchants.add(merchant);
    merchantSearchResponse = new MerchantSearchResponse();
    merchantSearchResponse.setMerchants(merchants);
    merchantSearchResponse.setTotalNoOfRows(Integer.parseInt("1"));

    try {
      Mockito.when(merchantUpdateService.searchSubMerchants(Matchers.any(Merchant.class), Matchers.anyString(), Matchers.anyLong()))
          .thenReturn(merchantSearchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SUB_MERCHANTS_PAGINATION)
              .sessionAttr("existingFeatures", "exist").sessionAttr("loginUserType", "superAdmin")
              .param("merchantType", "SubMerchant").sessionAttr("merchants", subMerchant)
              .param("pageNumber", "1"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testGetSubMerchantPaginationList", e);
    }
  }

  @Test
  public void testGetSubMerchantPaginationListException() {

    try {
      Mockito.when(merchantUpdateService.searchSubMerchants(Matchers.any(Merchant.class), Matchers.anyString(), Matchers.anyLong()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SUB_MERCHANTS_PAGINATION)
              .sessionAttr("existingFeatures", "exist").sessionAttr("loginUserType", "superAdmin")
              .param("merchantType", "SubMerchant"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error(
          "Error :: SubMerchantControllerTest :: testGetSubMerchantPaginationListException", e);
    }
  }

  @Test
  public void testDownloadSubMerchantReportPDF() {
    merchantResponse = new Merchant();
    merchantResponse.setPageSize(Integer.parseInt("1"));
    merchantSearchResponse = new MerchantSearchResponse();
    merchant = new MerchantData();
    merchant.setAddress1("address1");
    merchants.add(merchant);
    merchantSearchResponse.setMerchants(merchants);
    try {
      Mockito.when(merchantUpdateService.searchSubMerchants(Matchers.any(Merchant.class), Matchers.anyString(), Matchers.anyLong()))
          .thenReturn(merchantSearchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SUB_MERCHANT_REPORT)
          .sessionAttr("merchants", merchantResponse).param("downloadAllRecords", "true")
          .param("downloadType", "PDF"));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testDownloadSubMerchantReportPDF", e);
    }
  }

  @Test
  public void testDownloadSubMerchantReportXLS() {
    merchantResponse = new Merchant();
    merchantResponse.setPageSize(Integer.parseInt("1"));
    merchantSearchResponse = new MerchantSearchResponse();
    merchant = new MerchantData();
    merchant.setAddress1("address1");
    merchants.add(merchant);
    merchantSearchResponse.setMerchants(merchants);
    try {
      Mockito.when(merchantUpdateService.searchSubMerchants(Matchers.any(Merchant.class), Matchers.anyString(), Matchers.anyLong()))
          .thenReturn(merchantSearchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SUB_MERCHANT_REPORT)
          .sessionAttr("merchants", merchantResponse).param("downloadAllRecords", "true")
          .param("downloadType", "XLS"));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testDownloadSubMerchantReportXLS", e);
    }
  }

  @Test
  public void testUpdateSubMerchant() {
    updateMerchantResponse = new UpdateMerchantResponse();
    updateMerchantResponse.setUpdated(true);
    updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(merchantService.updateMerchant(Matchers.any(Merchant.class)))
          .thenReturn(updateMerchantResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_SUB_MERCHANT)
              .sessionAttr("existingFeatures", "exist").param("legalAnnualCard", "$11000")
              .sessionAttr("loginUserType", "admin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testUpdateSubMerchant", e);
    }
  }

  @Test
  public void testUpdateSubMerchantFailed() {
    updateMerchantResponse = new UpdateMerchantResponse();
    updateMerchantResponse.setUpdated(false);
    updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    response2 = new Response();
    response2.setCurrencyCodeAlpha("currencyCodeAlpha");
    response2.setResponseList(merchants);
    mainMerchantMap = new TreeMap<>();
    mainMerchantMap.put("0", "0");
    mainMerchantMap.put("1", "1");
    try {
      Mockito.when(merchantAccountService.getMerchantMapByMerchantType(Matchers.anyString()))
          .thenReturn(mainMerchantMap);
      Mockito.when(merchantService.updateMerchant(Matchers.any(Merchant.class)))
          .thenReturn(updateMerchantResponse);
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchantResponse);
      Mockito.when(currencyConfigService.getcurrencyCodeAlpha(Matchers.anyString()))
          .thenReturn(response2);
      Mockito.when(merchantUpdateService.getStatesByCountry(Matchers.anyString()))
          .thenReturn(response2);
      Mockito.when(merchantUpdateService.getAgentNames(Matchers.anyString())).thenReturn(response2);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_SUB_MERCHANT)
              .sessionAttr("existingFeatures", "exist").param("legalAnnualCard", "111000")
              .sessionAttr("loginUserType", "admin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testUpdateSubMerchantFailed", e);
    }
  }

  @Test
  public void testUpdateSubMerchantException() {
    try {
      Mockito.when(merchantService.updateMerchant(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_SUB_MERCHANT)
              .sessionAttr("existingFeatures", "exist").param("legalAnnualCard", "$11000")
              .sessionAttr("loginUserType", "admin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testUpdateSubMerchantException", e);
    }
  }

  @Test
  public void testShowEditSubMerchant() {
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenReturn(null);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_EDIT_SUB_MERCHANT)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_EDIT_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testShowEditSubMerchant", e);
    }
  }

  @Test
  public void testShowEditSubMerchantException() {
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_EDIT_SUB_MERCHANT)
              .sessionAttr("existingFeatures", "existingFeatures"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_EDIT_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testShowEditSubMerchantException", e);
    }
  }

  @Test
  public void testDeleteSubMerchant() {
    deleteMerchantResponse = new DeleteMerchantResponse();
    deleteMerchantResponse.setIsdeleated(true);
    try {
      Mockito.when(merchantUpdateService.deleteMerchant(Matchers.anyLong()))
          .thenReturn(deleteMerchantResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_DELETE_MERCHANT)
              .sessionAttr("existingFeatures", "existingFeatures")
              .sessionAttr("loginUserType", "admin")
              .param("merchantsType", PGConstants.SUB_MERCHANT))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testDeleteSubMerchant", e);
    }
  }

  @Test
  public void testDeleteMerchant() {
    deleteMerchantResponse = new DeleteMerchantResponse();
    deleteMerchantResponse.setIsdeleated(true);
    try {
      Mockito.when(merchantUpdateService.deleteMerchant(Matchers.anyLong()))
          .thenReturn(deleteMerchantResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_DELETE_MERCHANT)
              .sessionAttr("existingFeatures", "existingFeatures")
              .sessionAttr("loginUserType", "admin").param("merchantsType", PGConstants.MERCHANT))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testDeleteMerchant", e);
    }
  }

  @Test
  public void testDeleteSubMerchantFail() {
    deleteMerchantResponse = new DeleteMerchantResponse();
    deleteMerchantResponse.setIsdeleated(false);
    try {
      Mockito.when(merchantUpdateService.deleteMerchant(Matchers.anyLong()))
          .thenReturn(deleteMerchantResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_DELETE_MERCHANT)
              .sessionAttr("existingFeatures", "existingFeatures")
              .sessionAttr("loginUserType", "admin")
              .param("merchantsType", PGConstants.SUB_MERCHANT))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testDeleteSubMerchantFail", e);
    }
  }

  @Test
  public void testDeleteMerchantFail() {
    deleteMerchantResponse = new DeleteMerchantResponse();
    deleteMerchantResponse.setIsdeleated(false);
    try {
      Mockito.when(merchantUpdateService.deleteMerchant(Matchers.anyLong()))
          .thenReturn(deleteMerchantResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_DELETE_MERCHANT)
          .sessionAttr("existingFeatures", "existingFeatures").sessionAttr("loginUserType", "admin")
          .param("merchantsType", PGConstants.MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testDeleteMerchantFail", e);
    }
  }

  @Test
  public void testChangeSubMerchantStatus() {
    response2 = new Response();
    response2.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(merchantValidateService.changeMerchantStatus(Matchers.any(MerchantData.class)))
          .thenReturn(response2);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SUB_MERCHANT_STATUS_CHANGE)
              .sessionAttr("existingFeatures", "existingFeatures")
              .sessionAttr("loginUserType", "admin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testChangeSubMerchantStatus", e);
    }
  }

  @Test
  public void testChangeSubMerchantStatusFail() {
    response2 = new Response();
    response2.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(merchantValidateService.changeMerchantStatus(Matchers.any(MerchantData.class)))
          .thenReturn(response2);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SUB_MERCHANT_STATUS_CHANGE)
              .sessionAttr("existingFeatures", "existingFeatures")
              .sessionAttr("loginUserType", "admin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SEARCH_SUB_MERCHANT));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testChangeSubMerchantStatusFail", e);
    }
  }

  @Test
  public void testChangeSubMerchantStatusException() {
    try {
      Mockito.when(merchantValidateService.changeMerchantStatus(Matchers.any(MerchantData.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SUB_MERCHANT_STATUS_CHANGE)
              .sessionAttr("existingFeatures", "existingFeatures")
              .sessionAttr("loginUserType", "admin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("Error :: SubMerchantControllerTest :: testChangeSubMerchantStatusException", e);
    }
  }
}
