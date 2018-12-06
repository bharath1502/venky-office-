package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.chatak.acquirer.admin.model.MerchantAccountSearchResponse;
import com.chatak.acquirer.admin.service.MerchantAccountService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.MerchantValidateService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.MerchantAccountSearchDto;
import com.chatak.pg.user.bean.MerchantDetailsForAccountCreate;
import com.chatak.pg.user.bean.MerchantDetailsForAccountResponse;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class MerchantAccountControllerTest {

  private static Logger logger = LoggerFactory.getLogger(MerchantAccountControllerTest.class);

  @InjectMocks
  MerchantAccountController merchantAccountController = new MerchantAccountController();

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
  private MerchantAccountService merchantAccountService;

  @Mock
  private MerchantDetailsForAccountResponse merchantDetailsResponse;

  @Mock
  private List<MerchantDetailsForAccountCreate> merchantDetailsList;

  @Mock
  private MerchantDetailsForAccountCreate merchantDetailsForAccountCreate;

  @Mock
  private MerchantUpdateService merchantUpdateService;

  @Mock
  private MerchantValidateService merchantValidateService;

  @Mock
  private MerchantAccountSearchResponse merchantAccountSearchResponse;

  @Mock
  private List<MerchantAccountSearchDto> merchantAccountSearchDtoList;

  @Mock
  private MerchantAccountSearchDto merchantAccountSearchDto;

  @Mock
  private Map<String, String> merchantDataMap;

  @Mock
  private Merchant merchant;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(merchantAccountController)
        .setViewResolvers(viewResolver).build();
    optionList = new ArrayList<>();
    merchantAccountSearchDtoList = new ArrayList<>();
  }

  @Test
  public void testShowMerchantAccountInitialCreatePage() {
    merchantDetailsResponse = new MerchantDetailsForAccountResponse();
    merchantDetailsResponse.setMerchantDetailsList(merchantDetailsList);
    try {
      Mockito
          .when(merchantAccountService
              .getMerchantDetailsForAccountCreation(Matchers.any(Merchant.class)))
          .thenReturn(merchantDetailsResponse);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param("entityType", TestConstants.TYPE_MERCHANT))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testShowMerchantAccountInitialCreatePage | Exception ",
          e);
    }
  }

  @Test
  public void testShowMerchantAccountInitialCreatePageException() {
    try {
      Mockito
          .when(merchantAccountService
              .getMerchantDetailsForAccountCreation(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param("entityType", TestConstants.TYPE_MERCHANT))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testShowMerchantAccountInitialCreatePageException | Exception ",
          e);
    }
  }

  @Test
  public void testShowMerchantAccountCreatePage() {
    try {
      Mockito.when(merchantUpdateService.getCountries()).thenReturn(optionList);
      Mockito.when(merchantValidateService.getFeeProgramNames()).thenReturn(optionList);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_CREATE_PAGE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param("entityType", TestConstants.TYPE_MERCHANT)
              .header(TestConstants.REFERER, TestConstants.REFERER))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_CREATE));
    } catch (Exception e) {
      logger.error("MerchantAccountControllerTest | testShowMerchantAccountCreatePage | Exception ",
          e);
    }
  }

  @Test
  public void testShowMerchantAccountCreatePageException() {
    try {
      Mockito.when(merchantUpdateService.getCountries()).thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_CREATE_PAGE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param("entityType", TestConstants.TYPE_MERCHANT)
              .header(TestConstants.REFERER, TestConstants.REFERER))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_CREATE));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testShowMerchantAccountCreatePageException | Exception ",
          e);
    }
  }

  @Test
  public void testCreateMerchantAccount() {
    try {
      Mockito.when(merchantUpdateService.getCountries()).thenReturn(optionList);
      Mockito.when(merchantValidateService.getFeeProgramNames()).thenReturn(optionList);
      Mockito.when(merchantAccountService.createMerchantAccount(Matchers.any(Merchant.class),
          Matchers.anyLong())).thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param("entityType", TestConstants.TYPE_MERCHANT)
              .param("merchantType", TestConstants.TYPE_MERCHANT).header(TestConstants.REFERER, TestConstants.REFERER))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE));
    } catch (Exception e) {
      logger.error("MerchantAccountControllerTest | testCreateMerchantAccount | Exception ", e);
    }
  }

  @Test
  public void testCreateMerchantAccountElse() {
    merchantDetailsResponse = new MerchantDetailsForAccountResponse();
    merchantDetailsResponse.setMerchantDetailsList(merchantDetailsList);
    try {
      Mockito.when(merchantUpdateService.getCountries()).thenReturn(optionList);
      Mockito.when(merchantValidateService.getFeeProgramNames()).thenReturn(optionList);
      Mockito.when(merchantAccountService.createMerchantAccount(Matchers.any(Merchant.class),
          Matchers.anyLong())).thenReturn(responseval);
      Mockito
          .when(merchantAccountService
              .getMerchantDetailsForAccountCreation(Matchers.any(Merchant.class)))
          .thenReturn(merchantDetailsResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param("entityType", TestConstants.TYPE_MERCHANT)
              .param("merchantType", "Reseller").header(TestConstants.REFERER, TestConstants.REFERER))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE));
    } catch (Exception e) {
      logger.error("MerchantAccountControllerTest | testCreateMerchantAccountElse | Exception ", e);
    }
  }

  @Test
  public void testCreateMerchantAccountException() {
    try {
      Mockito.when(merchantUpdateService.getCountries()).thenThrow(nullPointerException);
      Mockito.when(merchantValidateService.getFeeProgramNames()).thenReturn(optionList);
      Mockito.when(merchantAccountService.createMerchantAccount(Matchers.any(Merchant.class),
          Matchers.anyLong())).thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_CREATE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param("entityType", TestConstants.TYPE_MERCHANT)
              .param("merchantType", "Reseller").header(TestConstants.REFERER, TestConstants.REFERER))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_CREATE));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testCreateMerchantAccountException | Exception ", e);
    }
  }

  @Test
  public void testShowMerchantAccountSearchPage() {
    merchantAccountSearchResponse = new MerchantAccountSearchResponse();
    merchantAccountSearchDtoList.add(merchantAccountSearchDto);
    merchantAccountSearchResponse.setMerchantAccountSearchDtoList(merchantAccountSearchDtoList);
    merchantAccountSearchResponse.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenReturn(merchantAccountSearchResponse);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param(TestConstants.PAGE_SIZE,
                  TestConstants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("MerchantAccountControllerTest | testShowMerchantAccountSearchPage | Exception ",
          e);
    }
  }

  @Test
  public void testShowMerchantAccountSearchPageException() {
    try {
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param(TestConstants.PAGE_SIZE,
                  TestConstants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testShowMerchantAccountSearchPageException | Exception ",
          e);
    }
  }

  @Test
  public void testMerchantAccountSearch() {
    merchantAccountSearchResponse = new MerchantAccountSearchResponse();
    merchantAccountSearchDtoList.add(merchantAccountSearchDto);
    merchantAccountSearchResponse.setMerchantAccountSearchDtoList(merchantAccountSearchDtoList);
    merchantAccountSearchResponse.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenReturn(merchantAccountSearchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param(TestConstants.PAGE_SIZE,
                  TestConstants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("MerchantAccountControllerTest | testMerchantAccountSearch | Exception ", e);
    }
  }

  @Test
  public void testMerchantAccountSearchException() {
    try {
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES).param(TestConstants.PAGE_SIZE,
                  TestConstants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testMerchantAccountSearchException | Exception ", e);
    }
  }

  @Test
  public void testMerchantAccountSearchPagination() {
    merchantAccountSearchResponse = new MerchantAccountSearchResponse();
    merchantAccountSearchDtoList.add(merchantAccountSearchDto);
    merchantAccountSearchResponse.setMerchantAccountSearchDtoList(merchantAccountSearchDtoList);
    merchantAccountSearchResponse.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenReturn(merchantAccountSearchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGINATION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto)
              .param(TestConstants.PAGE_NUMBER, TestConstants.TEN.toString())
              .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString())
              .param("sortProperty", "sortProperty"))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testMerchantAccountSearchPagination | Exception ", e);
    }
  }

  @Test
  public void testMerchantAccountSearchPaginationException() {
    try {
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGINATION)
              .sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto)
              .param(TestConstants.PAGE_NUMBER, TestConstants.TEN.toString())
              .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString())
              .param("sortProperty", "sortProperty"))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testMerchantAccountSearchPaginationException | Exception ",
          e);
    }
  }

  @Test
  public void testMerchantDetailsSearchForAccountCreation() {
    merchantDetailsResponse = new MerchantDetailsForAccountResponse();
    merchantDetailsList.add(merchantDetailsForAccountCreate);
    merchantDetailsResponse.setMerchantDetailsList(merchantDetailsList);
    try {
      Mockito
          .when(merchantAccountService
              .getMerchantDetailsForAccountCreation(Matchers.any(Merchant.class)))
          .thenReturn(merchantDetailsResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE)
              .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testMerchantDetailsSearchForAccountCreation | Exception ",
          e);
    }
  }

  @Test
  public void testMerchantDetailsSearchForAccountCreationException() {
    try {
      Mockito
          .when(merchantAccountService
              .getMerchantDetailsForAccountCreation(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE)
              .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testMerchantDetailsSearchForAccountCreationException | Exception ",
          e);
    }
  }

  @Test
  public void testGetMerchantDetailsPaginationListForMerchantAccountCreate() {
    merchantDetailsResponse = new MerchantDetailsForAccountResponse();
    merchantDetailsList.add(merchantDetailsForAccountCreate);
    merchantDetailsResponse.setMerchantDetailsList(merchantDetailsList);
    merchantDetailsResponse.setTotalRecords(TestConstants.TEN);
    try {
      Mockito
          .when(merchantAccountService
              .getMerchantDetailsForAccountCreation(Matchers.any(Merchant.class)))
          .thenReturn(merchantDetailsResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANTDETAILS_ACCOUNT_CREATE_PAGINATION)
              .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MERCHANTS_MODEL, merchant)
              .param(TestConstants.PAGE_NUMBER, TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testGetMerchantDetailsPaginationListForMerchantAccountCreate | Exception ",
          e);
    }
  }

  @Test
  public void testGetMerchantDetailsPaginationListForMerchantAccountCreateException() {
    try {
      Mockito
          .when(merchantAccountService
              .getMerchantDetailsForAccountCreation(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANTDETAILS_ACCOUNT_CREATE_PAGINATION)
              .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MERCHANTS_MODEL, merchant)
              .param(TestConstants.PAGE_NUMBER, TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_INITIAL_CREATE_PAGE));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testGetMerchantDetailsPaginationListForMerchantAccountCreateException | Exception ",
          e);
    }
  }

  @Test
  public void testChangeAccountStatus() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_STATUS_CHANGE)
              .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MERCHANTS_MODEL, merchant)
              .param(TestConstants.PAGE_NUMBER, TestConstants.ONE.toString()).param("merchantType", TestConstants.TYPE_MERCHANT))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("MerchantAccountControllerTest | testChangeAccountStatus | Exception ", e);
    }
  }

  @Test
  public void testChangeAccountStatusElse() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_STATUS_CHANGE)
              .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MERCHANTS_MODEL, merchant)
              .param(TestConstants.PAGE_NUMBER, TestConstants.ONE.toString()).param("merchantType", "Seller"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("MerchantAccountControllerTest | testChangeAccountStatusElse | Exception ", e);
    }
  }

  @Test
  public void testUpdateMerchantAccount() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_EDIT)
              .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MERCHANTS_MODEL, merchant)
              .param(TestConstants.PAGE_NUMBER, TestConstants.ONE.toString()).param("merchantType", TestConstants.TYPE_MERCHANT))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("MerchantAccountControllerTest | testUpdateMerchantAccount | Exception ", e);
    }
  }

  @Test
  public void testUpdateMerchantAccountElse() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_EDIT)
              .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MERCHANTS_MODEL, merchant)
              .param(TestConstants.PAGE_NUMBER, TestConstants.ONE.toString()).param("merchantType", "Seller"))
          .andExpect(view().name(URLMappingConstants.CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("MerchantAccountControllerTest | testUpdateMerchantAccountElse | Exception ", e);
    }
  }

  @Test
  public void testMerchantAccountEditPage() {
    try {
      Mockito.when(merchantAccountService.getAccount(Matchers.any(Merchant.class)))
          .thenReturn(merchant);
      Mockito.when(merchantUpdateService.getStatesByCountry(Matchers.anyString()))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_EDIT_PAGE)
              .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MERCHANTS_MODEL, merchant)
              .param(TestConstants.PAGE_NUMBER, TestConstants.ONE.toString()).param("merchantType", "Seller"))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_EDIT));
    } catch (Exception e) {
      logger.error("MerchantAccountControllerTest | testMerchantAccountEditPage | Exception ", e);
    }
  }

  @Test
  public void testMerchantAccountEditPageException() {
    try {
      Mockito.when(merchantAccountService.getAccount(Matchers.any(Merchant.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_EDIT_PAGE)
              .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MERCHANTS_MODEL, merchant)
              .param(TestConstants.PAGE_NUMBER, TestConstants.ONE.toString()).param("merchantType", "Seller"))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_EDIT));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testMerchantAccountEditPageException | Exception ", e);
    }
  }

  @Test
  public void testDownloadMerchantAccountReport() {
    merchantAccountSearchResponse = new MerchantAccountSearchResponse();
    merchantAccountSearchDtoList.add(merchantAccountSearchDto);
    merchantAccountSearchResponse.setMerchantAccountSearchDtoList(merchantAccountSearchDtoList);
    merchantAccountSearchResponse.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenReturn(merchantAccountSearchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_REPORT)
          .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto)
          .param("downLoadPageNumber", TestConstants.ONE.toString()).param(TestConstants.DOWNLOAD_TYPE, "XLS")
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()).param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error("MerchantAccountControllerTest | testDownloadMerchantAccountReport | Exception ",
          e);
    }
  }

  @Test
  public void testDownloadMerchantAccountReportPDF() {
    merchantAccountSearchResponse = new MerchantAccountSearchResponse();
    merchantAccountSearchDtoList.add(merchantAccountSearchDto);
    merchantAccountSearchResponse.setMerchantAccountSearchDtoList(merchantAccountSearchDtoList);
    merchantAccountSearchResponse.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenReturn(merchantAccountSearchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_REPORT)
          .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto)
          .param("downLoadPageNumber", TestConstants.ONE.toString()).param(TestConstants.DOWNLOAD_TYPE, "PDF")
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()).param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testDownloadMerchantAccountReportPDF | Exception ", e);
    }
  }

  @Test
  public void testDownloadMerchantAccountReportException() {
    try {
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_ACCOUNT_SEARCH_REPORT)
          .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto)
          .param("downLoadPageNumber", TestConstants.ONE.toString()).param(TestConstants.DOWNLOAD_TYPE, "PDF")
          .param(TestConstants.TOTAL_RECORDS, TestConstants.TEN.toString()).param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testDownloadMerchantAccountReportException | Exception ",
          e);
    }
  }

  @Test
  public void testDownloadPendingNmas() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_PENDING_NMAS));
    } catch (Exception e) {
      logger.error("MerchantAccountControllerTest | testDownloadPendingNmas | Exception ", e);
    }
  }

  @Test
  public void testShowAllPendingMerchants() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.SHOW_ALL_PENDING_MERCHANTS));
    } catch (Exception e) {
      logger.error("MerchantAccountControllerTest | testShowAllPendingMerchants | Exception ", e);
    }
  }

  @Test
  public void testShowSubMerchantAccountSearchPage() {
    merchantAccountSearchResponse = new MerchantAccountSearchResponse();
    merchantAccountSearchDtoList.add(merchantAccountSearchDto);
    merchantAccountSearchResponse.setMerchantAccountSearchDtoList(merchantAccountSearchDtoList);
    merchantAccountSearchResponse.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito.when(merchantAccountService.getMerchantMapByMerchantType(Matchers.anyString()))
          .thenReturn(merchantDataMap);
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenReturn(merchantAccountSearchResponse);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE)
          .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto)
          .param("downLoadPageNumber", TestConstants.ONE.toString())
          .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString()));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testShowSubMerchantAccountSearchPage | Exception ", e);
    }
  }

  @Test
  public void testShowSubMerchantAccountSearchPageException() {
    try {
      Mockito.when(merchantAccountService.getMerchantMapByMerchantType(Matchers.anyString()))
          .thenThrow(nullPointerException);
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenReturn(merchantAccountSearchResponse);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE)
          .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
          .sessionAttr(TestConstants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto)
          .param("downLoadPageNumber", TestConstants.ONE.toString())
          .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString()));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testShowSubMerchantAccountSearchPageException | Exception ",
          e);
    }
  }

  @Test
  public void testSearchSubMerchantAccount() {
    merchantAccountSearchResponse = new MerchantAccountSearchResponse();
    merchantAccountSearchDtoList.add(merchantAccountSearchDto);
    merchantAccountSearchResponse.setMerchantAccountSearchDtoList(merchantAccountSearchDtoList);
    merchantAccountSearchResponse.setTotalNoOfRows(TestConstants.TEN);
    try {
      Mockito.when(merchantAccountService.getMerchantMapByMerchantType(Matchers.anyString()))
          .thenReturn(merchantDataMap);
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenReturn(merchantAccountSearchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE)
              .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto)
              .param("downLoadPageNumber", TestConstants.ONE.toString())
              .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("MerchantAccountControllerTest | testSearchSubMerchantAccount | Exception ", e);
    }
  }

  @Test
  public void testSearchSubMerchantAccountException() {
    try {
      Mockito.when(merchantAccountService.getMerchantMapByMerchantType(Matchers.anyString()))
          .thenThrow(nullPointerException);
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenReturn(merchantAccountSearchResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE)
              .header(TestConstants.REFERER, TestConstants.REFERER).sessionAttr(TestConstants.EXISTING_FEATURES, TestConstants.EXISTING_FEATURES)
              .sessionAttr(TestConstants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto)
              .param("downLoadPageNumber", TestConstants.ONE.toString())
              .param(TestConstants.PAGE_SIZE, TestConstants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountControllerTest | testSearchSubMerchantAccountException | Exception ", e);
    }
  }

}
