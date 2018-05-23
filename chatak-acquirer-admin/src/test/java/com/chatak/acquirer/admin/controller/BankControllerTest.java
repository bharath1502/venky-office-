package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.BankSearchResponse;
import com.chatak.acquirer.admin.service.BankService;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.Bank;
import com.chatak.pg.user.bean.BankResponse;
import com.chatak.pg.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class BankControllerTest {

  private static Logger logger = Logger.getLogger(BankControllerTest.class);

  private static final String INDIA = "india";
  
  @InjectMocks
  BankController bankController = new BankController();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  private Response responseval;

  @Mock
  BindingResult bindingResult;

  @Mock
  private BankService bankService;

  @Mock
  private BankResponse bankResponse;

  @Mock
  private BankSearchResponse searchResponse;

  @Mock
  private Bank bank;

  @Mock
  private List<Bank> banks;

  @Mock
  private CurrencyConfigService currencyConfigService;

  @Mock
  private List<Option> optionList;

  private MockMvc mockMvc;

  private Locale locale;

  @Mock
  private MessageSource messageSource;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  private LoginDetails loginDetails;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc =
        MockMvcBuilders.standaloneSetup(bankController).setViewResolvers(viewResolver).build();
    optionList = new ArrayList<>();
  }

  @Test
  public void testShowCreateBankPage() {
    Mockito.when(bankService.getCountries()).thenReturn(optionList);
    Mockito.when(currencyConfigService.getCurrencyConfigCode()).thenReturn(optionList);
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.BANK_CREATE).sessionAttr(Constants.LOGIN_USER, Constants.LOGIN_USER))
          .andExpect(view().name(URLMappingConstants.BANK_CREATE));
    } catch (Exception e) {
      logger.error("BankControllerTest | testShowCreateBankPage | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testShowCreateBankPageException() {
    LocaleContextHolder.setLocale(locale);
    Mockito.when(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
        LocaleContextHolder.getLocale())).thenReturn("abc");
    Mockito.when(bankService.getCountries()).thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.BANK_CREATE).sessionAttr(Constants.LOGIN_USER, Constants.LOGIN_USER))
          .andExpect(view().name(URLMappingConstants.BANK_CREATE));
    } catch (Exception e) {
      logger.error(
          "BankControllerTest | testShowCreateBankPageException | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testCreateBank() throws ChatakAdminException {
    bankResponse = new BankResponse();
    bankResponse.setBankName("Axis");
    bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_B0);
    Mockito.when(bankService.getStatesByCountry(bank.getCountry())).thenReturn(responseval);
    Mockito.when(currencyConfigService.getCurrencyCodeNumeric(Matchers.anyString()))
        .thenReturn(responseval);
    Mockito.when(bankService.createBank(Matchers.any(Bank.class))).thenReturn(bankResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_CREATE).sessionAttr(Constants.EXISTING_FEATURES,
              Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error("BankControllerTest | testCreateBank | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testCreateBankWhenBankResponseNull() throws ChatakAdminException {
    Mockito.when(bankService.getStatesByCountry(bank.getCountry())).thenReturn(responseval);
    Mockito.when(currencyConfigService.getCurrencyCodeNumeric(Matchers.anyString()))
        .thenReturn(responseval);
    Mockito.when(bankService.createBank(bank)).thenReturn(bankResponse);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.BANK_CREATE).sessionAttr(Constants.EXISTING_FEATURES,
          Constants.EXISTING_FEATURES)).andExpect(view().name(URLMappingConstants.BANK_CREATE));
    } catch (Exception e) {
      logger.error(
          "BankControllerTest | testCreateBankWhenBankResponseNull | Exception " + e.getMessage(),
          e);
    }
  }

  @Test
  public void testCreateBankException() {
    LocaleContextHolder.setLocale(locale);
    Mockito.when(bankService.getCountries()).thenThrow(nullPointerException);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.BANK_CREATE).sessionAttr(Constants.EXISTING_FEATURES,
          Constants.EXISTING_FEATURES)).andExpect(view().name(URLMappingConstants.BANK_CREATE));
    } catch (Exception e) {
      logger.error("BankControllerTest | testCreateBankException | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testSearchBank() {
    Mockito.when(request.getHeader(Matchers.anyString())).thenReturn(Constants.REFERER);
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.BANK_SEARCH).header(Constants.REFERER, Constants.REFERER))
          .andExpect(view().name(URLMappingConstants.BANK_SEARCH));
    } catch (Exception e) {
      logger.error("BankControllerTest | testSearchBank | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testSearchBankListIfHeaderNull() {
    try {
      mockMvc
          .perform(
              post("/" + URLMappingConstants.BANK_SEARCH).sessionAttr(Constants.LOGIN_USER, Constants.LOGIN_USER))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "BankControllerTest | testSearchBankListIfHeaderNull | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testSearchBankList() throws ChatakAdminException {
    Mockito.when(bankService.searchBank(Matchers.any(Bank.class))).thenReturn(searchResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_SEARCH).header(Constants.REFERER, Constants.REFERER))
          .andExpect(view().name(URLMappingConstants.BANK_SEARCH));
    } catch (Exception e) {
      logger.error("BankControllerTest | testSearchBankList | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testSearchBankListException() throws ChatakAdminException {
    Mockito.when(bankService.searchBank(Matchers.any(Bank.class))).thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_SEARCH).header(Constants.REFERER, Constants.REFERER))
          .andExpect(view().name(URLMappingConstants.BANK_SEARCH));
    } catch (Exception e) {
      logger.error("BankControllerTest | testSearchBankListException | Exception " + e.getMessage(),
          e);
    }
  }

  @Test
  public void testGetPaginationList() throws ChatakAdminException {
    searchResponse = new BankSearchResponse();
    searchResponse.setBanks(banks);
    searchResponse.setTotalNoOfRows(Constants.TEN);
    Mockito.when(bankService.searchBank(Matchers.any(Bank.class))).thenReturn(searchResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_PAGINATION)
              .sessionAttr(Constants.BANK_MODEL, bank).param(Constants.PAGE_NUMBER, Constants.TWO.toString())
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.BANK_SEARCH));
    } catch (Exception e) {
      logger.error("BankControllerTest | testGetPaginationList | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testGetPaginationListException() throws ChatakAdminException {
    Mockito.when(bankService.searchBank(Matchers.any(Bank.class))).thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_PAGINATION)
              .sessionAttr(Constants.BANK_MODEL, bank).param(Constants.PAGE_NUMBER, Constants.TWO.toString())
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.BANK_SEARCH));
    } catch (Exception e) {
      logger.error(
          "BankControllerTest | testGetPaginationListException | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testShowEditBank() throws ChatakAdminException {
    bank.setCountry(INDIA);
    Mockito.when(bankService.findByBankName(Matchers.any(Bank.class))).thenReturn(bank);
    Mockito.when(bankService.getStatesByCountry(Matchers.anyString())).thenReturn(responseval);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_EDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("editBankName", "Axis")
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.BANK_EDIT));
    } catch (Exception e) {
      logger.error("BankControllerTest | testShowEditBank | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testShowEditBankException() throws ChatakAdminException {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_EDIT)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("editBankName", "Axis")
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error("BankControllerTest | testShowEditBankException | Exception " + e.getMessage(),
          e);
    }
  }

  @Test
  public void testShowViewBank() throws ChatakAdminException {
    bank.setCountry(INDIA);
    Mockito.when(bankService.findByBankName(Matchers.any(Bank.class))).thenReturn(bank);
    Mockito.when(bankService.getStatesByCountry(Matchers.anyString())).thenReturn(responseval);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_VIEW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("bankViewName", "Axis")
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.BANK_VIEW));
    } catch (Exception e) {
      logger.error("BankControllerTest | testShowViewBank | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testShowViewBankChatakAdminException() throws ChatakAdminException {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_VIEW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("bankViewName", "Axis")
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "BankControllerTest | testShowViewBankChatakAdminException | Exception " + e.getMessage(),
          e);
    }
  }

  @Test
  public void testShowViewBankException() throws ChatakAdminException {
    bank.setCountry(INDIA);
    Mockito.when(bankService.findByBankName(Matchers.any(Bank.class)))
        .thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_VIEW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("bankViewName", "Axis")
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error("BankControllerTest | testShowViewBankException | Exception " + e.getMessage(),
          e);
    }
  }

  @Test
  public void testDeleteBank() throws ChatakAdminException {
    bankResponse = new BankResponse();
    bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_B2);
    Mockito.when(bankService.deleteBankByName(Matchers.anyString())).thenReturn(bankResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_DELETE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("deleteBankName", "Axis")
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.BANK_SEARCH));
    } catch (Exception e) {
      logger.error("BankControllerTest | testDeleteBank | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testDeleteBankElseIf() throws ChatakAdminException {
    bankResponse = new BankResponse();
    bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    Mockito.when(bankService.deleteBankByName(Matchers.anyString())).thenReturn(bankResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_DELETE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("deleteBankName", "Axis")
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.BANK_SEARCH));
    } catch (Exception e) {
      logger.error("BankControllerTest | testDeleteBankElseIf | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testDeleteBankException() throws ChatakAdminException {
    Mockito.when(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
        LocaleContextHolder.getLocale())).thenReturn("abc");
    Mockito.when(bankService.deleteBankByName(Matchers.anyString()))
        .thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_DELETE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("deleteBankName", "Axis")
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.BANK_SEARCH));
    } catch (Exception e) {
      logger.error("BankControllerTest | testDeleteBankException | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testUpdateBank() throws ChatakAdminException {
    bankResponse = new BankResponse();
    bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_B4);
    Mockito.when(bankService.updateBank(Matchers.any(Bank.class))).thenReturn(bankResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_UPDATE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("deleteBankName", "Axis")
              .header(Constants.REFERER, Constants.REFERER).param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.BANK_SEARCH));
    } catch (Exception e) {
      logger.error("BankControllerTest | testUpdateBank | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testUpdateBankElse() throws ChatakAdminException {
    bankResponse = new BankResponse();
    bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    Mockito.when(bankService.updateBank(Matchers.any(Bank.class))).thenReturn(bankResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_UPDATE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("deleteBankName", "Axis")
              .header(Constants.REFERER, Constants.REFERER).param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.BANK_EDIT));
    } catch (Exception e) {
      logger.error("BankControllerTest | testUpdateBankElse | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testUpdateBankException() throws ChatakAdminException {
    Mockito.when(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
        LocaleContextHolder.getLocale())).thenReturn("abc");
    bankResponse = new BankResponse();
    bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_B4);
    Mockito.when(bankService.updateBank(Matchers.any(Bank.class))).thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_UPDATE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("deleteBankName", "Axis")
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.BANK_EDIT));
    } catch (Exception e) {
      logger.error("BankControllerTest | testUpdateBankException | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testGetStatesById() throws ChatakAdminException {
    Mockito.when(bankService.getStatesByCountry(Matchers.anyString())).thenReturn(responseval);
    try {
      mockMvc.perform(get("/" + URLMappingConstants.GET_STATES_BY_COUNTRY_ID)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("deleteBankName", "Axis")
          .header(Constants.REFERER, Constants.REFERER).param(Constants.TOTAL_RECORDS, Constants.TEN.toString()));
    } catch (Exception e) {
      logger.error("BankControllerTest | testGetStatesById | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testDownloadBankListPDF() throws ChatakAdminException {
    searchResponse = new BankSearchResponse();
    searchResponse.setBanks(banks);
    Mockito.when(bankService.searchBank(Matchers.any(Bank.class))).thenReturn(searchResponse);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.GET_BANK_REPORT)
          .sessionAttr(Constants.BANK_MODEL, bank)
          .param("downLoadPageNumber", Constants.ONE.toString()).param("downloadType", "PDF")
          .param(Constants.TOTAL_RECORDS, "20").param("downloadAllRecords", "true")
          .header(Constants.REFERER, Constants.REFERER).param(Constants.TOTAL_RECORDS, Constants.TEN.toString()));
    } catch (Exception e) {
      logger.error("BankControllerTest | testDownloadBankListPDF | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testDownloadBankListXLS() throws ChatakAdminException {
    searchResponse = new BankSearchResponse();
    searchResponse.setBanks(banks);
    Mockito.when(bankService.searchBank(Matchers.any(Bank.class))).thenReturn(searchResponse);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.GET_BANK_REPORT)
          .sessionAttr(Constants.BANK_MODEL, bank)
          .param("downLoadPageNumber", Constants.ONE.toString()).param("downloadType", "XLS")
          .param(Constants.TOTAL_RECORDS, "20").param("downloadAllRecords", "true")
          .header(Constants.REFERER, Constants.REFERER).param(Constants.TOTAL_RECORDS, Constants.TEN.toString()));
    } catch (Exception e) {
      logger.error("BankControllerTest | testDownloadBankListXLS | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testDownloadBankListException() throws ChatakAdminException {
    Mockito.when(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
        LocaleContextHolder.getLocale())).thenReturn("abc");
    bankResponse = new BankResponse();
    bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_B4);
    Mockito.when(bankService.updateBank(Matchers.any(Bank.class))).thenThrow(nullPointerException);
    try {
      mockMvc.perform(post("/" + URLMappingConstants.GET_BANK_REPORT)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("deleteBankName", "Axis")
          .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()));
    } catch (Exception e) {
      logger.error(
          "BankControllerTest | testDownloadBankListException | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testChangeBankStatusHeaderNull() throws ChatakAdminException {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_STATUS_CHANGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param(Constants.PAGE_NUMBER, Constants.ONE.toString())
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error(
          "BankControllerTest | testChangeBankStatusHeaderNull | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testChangeBankStatus() throws ChatakAdminException {
    bankResponse = new BankResponse();
    bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    Mockito.when(bankService.changeBankStatus(Matchers.any(Bank.class))).thenReturn(bankResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_STATUS_CHANGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param(Constants.PAGE_NUMBER, Constants.ONE.toString()).header(Constants.REFERER, Constants.REFERER)
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.BANK_SEARCH));
    } catch (Exception e) {
      logger.error("BankControllerTest | testChangeBankStatus | Exception " + e.getMessage(), e);
    }
  }

  @Test
  public void testChangeBankStatusNotZero() throws ChatakAdminException {
    bankResponse = new BankResponse();
    bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    Mockito.when(bankService.changeBankStatus(Matchers.any(Bank.class))).thenReturn(bankResponse);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_STATUS_CHANGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param(Constants.PAGE_NUMBER, Constants.ONE.toString()).header(Constants.REFERER, Constants.REFERER)
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.BANK_SEARCH));
    } catch (Exception e) {
      logger.error("BankControllerTest | testChangeBankStatusNotZero | Exception " + e.getMessage(),
          e);
    }
  }

  @Test
  public void testChangeBankStatusException() throws ChatakAdminException {
    bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    Mockito.when(bankService.changeBankStatus(Matchers.any(Bank.class)))
        .thenThrow(nullPointerException);
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.BANK_STATUS_CHANGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .param(Constants.PAGE_NUMBER, Constants.ONE.toString()).header(Constants.REFERER, Constants.REFERER)
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.BANK_SEARCH));
    } catch (Exception e) {
      logger.error(
          "BankControllerTest | testChangeBankStatusException | Exception " + e.getMessage(), e);
    }
  }
}
