package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
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

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantAccountSearchResponse;
import com.chatak.acquirer.admin.service.MerchantAccountService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.user.bean.MerchantAccountSearchDto;
import com.chatak.pg.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class MerchantAccountBalancesControllerTest {

  private static Logger logger =
      LoggerFactory.getLogger(MerchantAccountBalancesControllerTest.class);

  @InjectMocks
  MerchantAccountBalancesController merchantAccountBalancesController =
      new MerchantAccountBalancesController();

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
  private AccountBalanceDTO accountBalanceDTO;

  @Mock
  private Map<String, String> merchantDataMap;

  @Mock
  private MerchantAccountSearchDto merchantAccountSearchDto;

  @Mock
  private MerchantAccountSearchResponse searchResponse;

  @Mock
  private List<MerchantAccountSearchDto> merchantAccountSearchDtoList;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc = MockMvcBuilders.standaloneSetup(merchantAccountBalancesController)
        .setViewResolvers(viewResolver).build();
    optionList = new ArrayList<>();
    merchantAccountSearchDtoList = new ArrayList<>();
  }

  @Test
  public void testShowAccManualCredit() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_ACC_MANUAL_CREDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.SHOW_ACC_MANUAL_CREDIT));
    } catch (Exception e) {
      logger.error("MerchantAccountBalancesControllerTest | testShowAccManualCredit | Exception ",
          e);
    }
  }

  @Test
  public void testShowAccManualDebit() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_ACC_MANUAL_DEBIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.SHOW_ACC_MANUAL_DEBIT));
    } catch (Exception e) {
      logger.error("MerchantAccountBalancesControllerTest | testShowAccManualDebit | Exception ",
          e);
    }
  }

  @Test
  public void testProcessAccManualCredit() {
    responseval = new Response();
    responseval.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(merchantAccountService.processMerchantAccountBalanceUpdate(
          Matchers.any(AccountBalanceDTO.class), Matchers.anyString())).thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_ACC_MANUAL_CREDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).header(Constants.REFERER, Constants.REFERER))
          .andExpect(view().name(URLMappingConstants.SHOW_ACC_MANUAL_CREDIT));
    } catch (Exception e) {
      logger.error("MerchantAccountBalancesControllerTest | testShowAccManualCredit | Exception ",
          e);
    }
  }

  @Test
  public void testProcessAccManualCreditElse() {
    responseval = new Response();
    responseval.setErrorCode(Constants.SUCCESS);
    try {
      Mockito.when(merchantAccountService.processMerchantAccountBalanceUpdate(
          Matchers.any(AccountBalanceDTO.class), Matchers.anyString())).thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_ACC_MANUAL_CREDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).header(Constants.REFERER, Constants.REFERER))
          .andExpect(view().name(URLMappingConstants.SHOW_ACC_MANUAL_CREDIT));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountBalancesControllerTest | testProcessAccManualCreditElse | Exception ", e);
    }
  }

  @Test
  public void testProcessAccManualCreditException() {
    try {
      Mockito
          .when(merchantAccountService.processMerchantAccountBalanceUpdate(
              Matchers.any(AccountBalanceDTO.class), Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_ACC_MANUAL_CREDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).header(Constants.REFERER, Constants.REFERER))
          .andExpect(view().name(URLMappingConstants.SHOW_ACC_MANUAL_CREDIT));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountBalancesControllerTest | testProcessAccManualCreditException | Exception ",
          e);
    }
  }

  @Test
  public void testProcessAccManualDebit() {
    responseval = new Response();
    responseval.setErrorCode(Constants.SUCCESS_CODE);
    try {
      Mockito.when(merchantAccountService.processMerchantAccountBalanceUpdate(
          Matchers.any(AccountBalanceDTO.class), Matchers.anyString())).thenReturn(responseval);
      Mockito.when(merchantAccountService.getAccountBalanceDTO(Matchers.anyString()))
          .thenReturn(accountBalanceDTO);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_ACC_MANUAL_DEBIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).header(Constants.REFERER, Constants.REFERER))
          .andExpect(view().name(URLMappingConstants.SHOW_ACC_MANUAL_DEBIT));
    } catch (Exception e) {
      logger.error("MerchantAccountBalancesControllerTest | testProcessAccManualDebit | Exception ",
          e);
    }
  }

  @Test
  public void testProcessAccManualDebitElse() {
    responseval = new Response();
    responseval.setErrorCode(Constants.SUCCESS);
    responseval.setErrorMessage("error");
    try {
      Mockito.when(merchantAccountService.processMerchantAccountBalanceUpdate(
          Matchers.any(AccountBalanceDTO.class), Matchers.anyString())).thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_ACC_MANUAL_DEBIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).header(Constants.REFERER, Constants.REFERER)
              .param("inputAmount", Constants.TWO_LONG.toString()))
          .andExpect(view().name(URLMappingConstants.SHOW_ACC_MANUAL_DEBIT));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountBalancesControllerTest | testProcessAccManualDebitElse | Exception ", e);
    }
  }

  @Test
  public void testProcessAccManualDebitException() {
    try {
      Mockito
          .when(merchantAccountService.processMerchantAccountBalanceUpdate(
              Matchers.any(AccountBalanceDTO.class), Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.PROCESS_ACC_MANUAL_DEBIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).header(Constants.REFERER, Constants.REFERER)
              .param("inputAmount", Constants.TWO_LONG.toString()))
          .andExpect(view().name(URLMappingConstants.SHOW_ACC_MANUAL_DEBIT));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountBalancesControllerTest | testProcessAccManualDebitException | Exception ",
          e);
    }
  }

  @Test
  public void testFetchAccountDetialsbyMerchantCode() {
    try {
      Mockito.when(merchantAccountService.getAccountBalanceDTO(Matchers.anyString()))
          .thenReturn(accountBalanceDTO);
      mockMvc.perform(get("/" + URLMappingConstants.AJAX_MERCHANT_BALANCE_DETAILS)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).header(Constants.REFERER, Constants.REFERER)
          .param("inputAmount", Constants.TWO_LONG.toString()));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountBalancesControllerTest | testFetchAccountDetialsbyMerchantCode | Exception ",
          e);
    }
  }

  @Test
  public void testDownloadSubMerchantAccountReport() {
    searchResponse = new MerchantAccountSearchResponse();
    merchantAccountSearchDtoList.add(merchantAccountSearchDto);
    searchResponse.setMerchantAccountSearchDtoList(merchantAccountSearchDtoList);
    try {
      Mockito.when(merchantAccountService.getMerchantMapByMerchantType(Matchers.anyString()))
          .thenReturn(merchantDataMap);
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenReturn(searchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_REPORT)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).header(Constants.REFERER, Constants.REFERER)
          .sessionAttr(Constants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto)
          .param("downLoadPageNumber", Constants.ONE.toString()).param(Constants.DOWNLOAD_TYPE, "XLS")
          .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountBalancesControllerTest | testDownloadSubMerchantAccountReport | Exception ",
          e);
    }
  }

  @Test
  public void testDownloadSubMerchantAccountReportPDF() {
    searchResponse = new MerchantAccountSearchResponse();
    merchantAccountSearchDtoList.add(merchantAccountSearchDto);
    searchResponse.setMerchantAccountSearchDtoList(merchantAccountSearchDtoList);
    try {
      Mockito.when(merchantAccountService.getMerchantMapByMerchantType(Matchers.anyString()))
          .thenReturn(merchantDataMap);
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenReturn(searchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_REPORT)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).header(Constants.REFERER, Constants.REFERER)
          .sessionAttr(Constants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto)
          .param("downLoadPageNumber", Constants.ONE.toString()).param(Constants.DOWNLOAD_TYPE, "PDF")
          .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountBalancesControllerTest | testDownloadSubMerchantAccountReportPDF | Exception ",
          e);
    }
  }

  @Test
  public void testDownloadSubMerchantAccountReportException() {
    try {
      Mockito.when(merchantAccountService.getMerchantMapByMerchantType(Matchers.anyString()))
          .thenThrow(nullPointerException);
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenReturn(searchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_REPORT)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).header(Constants.REFERER, Constants.REFERER)
          .sessionAttr(Constants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto)
          .param("downLoadPageNumber", Constants.ONE.toString()).param(Constants.DOWNLOAD_TYPE, "PDF")
          .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountBalancesControllerTest | testDownloadSubMerchantAccountReportException | Exception ",
          e);
    }
  }

  @Test
  public void testSubMerchantAccountSearchPagination() {
    searchResponse = new MerchantAccountSearchResponse();
    merchantAccountSearchDtoList.add(merchantAccountSearchDto);
    searchResponse.setMerchantAccountSearchDtoList(merchantAccountSearchDtoList);
    searchResponse.setTotalNoOfRows(Constants.TEN);
    try {
      Mockito.when(merchantAccountService.getMerchantMapByMerchantType(Matchers.anyString()))
          .thenReturn(merchantDataMap);
      Mockito
          .when(merchantAccountService.searchMerchantAccount(
              Matchers.any(MerchantAccountSearchDto.class), Matchers.anyMap()))
          .thenReturn(searchResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGINATION)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).header(Constants.REFERER, Constants.REFERER)
          .sessionAttr(Constants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto)
          .param("pageNumber", Constants.ONE.toString()).param("sortProperty", "sortProperty")
          .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountBalancesControllerTest | testSubMerchantAccountSearchPagination | Exception ",
          e);
    }
  }

  @Test
  public void testSubMerchantAccountSearchPaginationException() {
    try {
      Mockito.when(merchantAccountService.getMerchantMapByMerchantType(Matchers.anyString()))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_SUB_MERCHANT_ACCOUNT_SEARCH_PAGINATION)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).header(Constants.REFERER, Constants.REFERER)
          .sessionAttr(Constants.MERCHANT_ACCOUNT_SEARCH_MODEL, merchantAccountSearchDto)
          .param("pageNumber", Constants.ONE.toString()).param("sortProperty", "sortProperty")
          .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()).param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error(
          "MerchantAccountBalancesControllerTest | testSubMerchantAccountSearchPaginationException | Exception ",
          e);
    }
  }
}
