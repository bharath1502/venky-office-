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
import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.CurrencyDTO;
import com.chatak.pg.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyControllerTest {

  private static Logger logger = Logger.getLogger(CurrencyControllerTest.class);

  @InjectMocks
  CurrencyController currencyController = new CurrencyController();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  private Response responseval;

  @Mock
  BindingResult bindingResult;

  @Mock
  private Option option;

  @Mock
  private List<Option> optionList;

  private MockMvc mockMvc;

  @Mock
  private MessageSource messageSource;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  private LoginDetails loginDetails;

  @Mock
  private CurrencyDTO currencyDTO;

  @Mock
  private CurrencyConfigService currencyConfigService;;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc =
        MockMvcBuilders.standaloneSetup(currencyController).setViewResolvers(viewResolver).build();
    optionList = new ArrayList<>();
    option = new Option();
  }

  @Test
  public void testShowCurrencySearchPage() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_CURRENCY_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testShowCurrencySearchPage | Exception ", e);
    }
  }

  @Test
  public void testSearchCurrencyAccount() {
    optionList.add(option);
    responseval = new Response();
    responseval.setResponseList(optionList);
    responseval.setTotalNoOfRows(Constants.TEN);
    try {
      Mockito.when(currencyConfigService.searchCurrencyConfig(Matchers.any(CurrencyDTO.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_ACTION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testSearchCurrencyAccount | Exception ", e);
    }
  }

  @Test
  public void testSearchCurrencyAccountException() {
    try {
      Mockito.when(currencyConfigService.searchCurrencyConfig(Matchers.any(CurrencyDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_ACTION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testSearchCurrencyAccountException | Exception ", e);
    }
  }

  @Test
  public void testShowCreateCurrencyPage() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_CURRENCY_CREATE_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SHOW_CURRENCY_CREATE_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testShowCreateCurrencyPage | Exception ", e);
    }
  }

  @Test
  public void testCreateCurrencyPage() {
    currencyDTO = new CurrencyDTO();
    currencyDTO.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(currencyConfigService.saveCurrencyConfig(Matchers.any(CurrencyDTO.class)))
          .thenReturn(currencyDTO);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CURRENCY_CREATE_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString())))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testCreateCurrencyPage | Exception ", e);
    }
  }

  @Test
  public void testCreateCurrencyPageElse() {
    currencyDTO = new CurrencyDTO();
    currencyDTO.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(currencyConfigService.saveCurrencyConfig(Matchers.any(CurrencyDTO.class)))
          .thenReturn(currencyDTO);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CURRENCY_CREATE_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString())))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SHOW_CURRENCY_CREATE_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testCreateCurrencyPageElse | Exception ", e);
    }
  }

  @Test
  public void testCreateCurrencyPageException() {
    try {
      Mockito.when(currencyConfigService.saveCurrencyConfig(Matchers.any(CurrencyDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CURRENCY_CREATE_PAGE)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString())))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testCreateCurrencyPageException | Exception ", e);
    }
  }

  @Test
  public void testShowEditCurrency() {
    currencyDTO = new CurrencyDTO();
    currencyDTO.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(currencyConfigService.getCurrencyConfigById(Matchers.anyLong()))
          .thenReturn(currencyDTO);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_EDIT_CURRENCY)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString()))
              .param("getId", Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_EDIT_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testShowEditCurrency | Exception ", e);
    }
  }

  @Test
  public void testShowEditCurrencyException() {
    currencyDTO = new CurrencyDTO();
    currencyDTO.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(currencyConfigService.getCurrencyConfigById(Matchers.anyLong()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_EDIT_CURRENCY)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString()))
              .param("getId", Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_EDIT_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testShowEditCurrencyException | Exception ", e);
    }
  }

  @Test
  public void testShowViewCurrency() {
    try {
      Mockito.when(currencyConfigService.getCurrencyConfigById(Matchers.anyLong()))
          .thenReturn(currencyDTO);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_VIEW_CURRENCY)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString()))
              .param("getId", Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SHOW_VIEW_CURRENCY));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testShowViewCurrency | Exception ", e);
    }
  }

  @Test
  public void testShowViewCurrencyException() {
    try {
      Mockito.when(currencyConfigService.getCurrencyConfigById(Matchers.anyLong()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_SHOW_VIEW_CURRENCY)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString()))
              .param("getId", Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_SHOW_VIEW_CURRENCY));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testShowViewCurrencyException | Exception ", e);
    }
  }

  @Test
  public void testUpdateCurrency() {
    currencyDTO = new CurrencyDTO();
    currencyDTO.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(currencyConfigService.updateCurrencyConfig(Matchers.any(CurrencyDTO.class)))
          .thenReturn(currencyDTO);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_CURRENCY)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString()))
              .param("getId", Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testUpdateCurrency | Exception ", e);
    }
  }

  @Test
  public void testUpdateCurrencyElse() {
    currencyDTO = new CurrencyDTO();
    currencyDTO.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(currencyConfigService.updateCurrencyConfig(Matchers.any(CurrencyDTO.class)))
          .thenReturn(currencyDTO);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_CURRENCY)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString()))
              .param("getId", Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testUpdateCurrencyElse | Exception ", e);
    }
  }

  @Test
  public void testUpdateCurrencyException() {
    try {
      Mockito.when(currencyConfigService.updateCurrencyConfig(Matchers.any(CurrencyDTO.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UPDATE_CURRENCY)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString()))
              .param("getId", Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testUpdateCurrencyException | Exception ", e);
    }
  }

  @Test
  public void testDeleteCurrency() {
    currencyDTO = new CurrencyDTO();
    currencyDTO.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(currencyConfigService.deleteCurrencyConfig(Matchers.anyLong()))
          .thenReturn(currencyDTO);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_DELETE_CURRENCY)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString()))
              .param("getDeleteId", Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testDeleteCurrency | Exception ", e);
    }
  }

  @Test
  public void testDeleteCurrencyElse() {
    currencyDTO = new CurrencyDTO();
    currencyDTO.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(currencyConfigService.deleteCurrencyConfig(Matchers.anyLong()))
          .thenReturn(currencyDTO);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_DELETE_CURRENCY)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString()))
              .param("getDeleteId", Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testDeleteCurrencyElse | Exception ", e);
    }
  }

  @Test
  public void testDeleteCurrencyException() {
    try {
      Mockito.when(currencyConfigService.deleteCurrencyConfig(Matchers.anyLong()))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_DELETE_CURRENCY)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString()))
              .param("getDeleteId", Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testDeleteCurrencyException | Exception ", e);
    }
  }

  @Test
  public void testGetCurrencyBycurrencyName() {
    currencyDTO = new CurrencyDTO();
    currencyDTO.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(currencyConfigService.findByCurrencyName(Matchers.anyString()))
          .thenReturn(responseval);
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_GET_CURRENCY_DATA)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString()))
          .param("getDeleteId", Constants.ONE.toString()).param("currencyName", "Dollor"));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testGetCurrencyBycurrencyName | Exception ", e);
    }
  }

  @Test
  public void testChangeCurrencyStatus() {
    responseval = new Response();
    responseval.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(currencyConfigService.changeCurrencyStatus(Matchers.any(CurrencyDTO.class),
          Matchers.anyString())).thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CURRENCY_ACTIVATION_SUSPENTION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString()))
              .param("suspendActiveId", Constants.ONE.toString())
              .param("suspendActiveStatus", "Status").param("reason", "reason"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testChangeCurrencyStatus | Exception ", e);
    }
  }

  @Test
  public void testChangeCurrencyStatusElse() {
    responseval = new Response();
    responseval.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    try {
      Mockito.when(currencyConfigService.changeCurrencyStatus(Matchers.any(CurrencyDTO.class),
          Matchers.anyString())).thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CURRENCY_ACTIVATION_SUSPENTION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString()))
              .param("suspendActiveId", Constants.ONE.toString())
              .param("suspendActiveStatus", "Status").param("reason", "reason"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testChangeCurrencyStatusElse | Exception ", e);
    }
  }

  @Test
  public void testChangeCurrencyStatusException() {
    try {
      Mockito.when(currencyConfigService.changeCurrencyStatus(Matchers.any(CurrencyDTO.class),
          Matchers.anyString())).thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CURRENCY_ACTIVATION_SUSPENTION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, Long.valueOf(Constants.ONE.toString()))
              .param("suspendActiveId", Constants.ONE.toString())
              .param("suspendActiveStatus", "Status").param("reason", "reason"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testChangeCurrencyStatusException | Exception ", e);
    }
  }

  @Test
  public void testGetPaginationList() {
    try {
      Mockito.when(currencyConfigService.searchCurrencyConfig(Matchers.any(CurrencyDTO.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_PAGINATION_CURRENCY)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.CURRENCY_MODEL, currencyDTO)
              .param("pageNumber", Constants.ONE.toString())
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testGetPaginationList | Exception ", e);
    }
  }

  @Test
  public void testGetPaginationListException() {
    try {
      Mockito.when(currencyConfigService.searchCurrencyConfig(Matchers.any(CurrencyDTO.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_PAGINATION_CURRENCY)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.CURRENCY_MODEL, currencyDTO)
              .param("pageNumber", Constants.ONE.toString())
              .param(Constants.TOTAL_RECORDS, Constants.TEN.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CURRENCY_SEARCH_PAGE));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testGetPaginationListException | Exception ", e);
    }
  }

  @Test
  public void testDownloadCurrencyReport() {
    responseval = new Response();
    optionList.add(option);
    responseval.setResponseList(optionList);
    try {
      Mockito.when(currencyConfigService.searchCurrencyConfig(Matchers.any(CurrencyDTO.class)))
          .thenReturn(responseval);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_DOWNLOAD_CURRENCY)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.CURRENCY_MODEL, currencyDTO)
          .param("downLoadPageNumber", Constants.ONE.toString()).param("downloadType", "XLS")
          .param("downloadAllRecords", "true").param(Constants.TOTAL_RECORDS, Constants.TEN.toString()));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testDownloadCurrencyReport | Exception ", e);
    }
  }

  @Test
  public void testDownloadCurrencyReportPDF() {
    responseval = new Response();
    optionList.add(option);
    responseval.setResponseList(optionList);
    try {
      Mockito.when(currencyConfigService.searchCurrencyConfig(Matchers.any(CurrencyDTO.class)))
          .thenReturn(responseval);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_DOWNLOAD_CURRENCY)
          .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
          .sessionAttr(Constants.CURRENCY_MODEL, currencyDTO)
          .param("downLoadPageNumber", Constants.ONE.toString()).param("downloadType", "PDF")
          .param("downloadAllRecords", "true").param(Constants.TOTAL_RECORDS, Constants.TEN.toString()));
    } catch (Exception e) {
      logger.error("CurrencyControllerTest | testDownloadCurrencyReportPDF | Exception ", e);
    }
  }
}
