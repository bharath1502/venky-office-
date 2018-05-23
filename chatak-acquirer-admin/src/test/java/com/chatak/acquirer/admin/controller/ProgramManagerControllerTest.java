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
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.BankService;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.PartnerService;
import com.chatak.acquirer.admin.service.ProgramManagerService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.BankRequest;
import com.chatak.pg.user.bean.BankResponse;
import com.chatak.pg.user.bean.PartnerRequest;
import com.chatak.pg.user.bean.PartnerResponse;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.user.bean.ProgramManagerResponse;
import com.chatak.pg.util.Constants;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class ProgramManagerControllerTest {

  private static Logger logger = Logger.getLogger(ProgramManagerControllerTest.class);

  @InjectMocks
  ProgramManagerController controller = new ProgramManagerController();

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
  private MessageSource messageSource;

  @Mock
  private NullPointerException nullPointerException;

  @Mock
  ProgramManagerService programManagerService;

  @Mock
  PartnerService partnerService;

  @Mock
  MerchantUpdateService merchantUpdateService;

  @Mock
  CurrencyConfigService currencyConfigService;

  @Mock
  BankService bankService;

  @Mock
  private ProgramManagerResponse programManagerResponse;

  @Mock
  private BankResponse bankResponse;

  @Mock
  private List<BankRequest> bankRequests;

  @Mock
  private BankRequest bankRequest;

  @Mock
  private LoginResponse loginResponse;

  @Mock
  private PartnerResponse partnerResponse;

  @Mock
  private List<PartnerRequest> partnerList;

  @Mock
  private PartnerRequest partner;

  @Mock
  private ProgramManagerRequest programManagerRequest;

  @Mock
  private List<Integer> list;

  @Mock
  private List<ProgramManagerRequest> programManagerRequests;

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
  public void testShowSearchProgramManager() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: ProgramManagerControllerTest :: testShowSearchProgramManager", e);
    }
  }

  @Test
  public void testShowSearchProgramManagerChatakAdminException() {
    try {
      Mockito.when(bankService.getBankData()).thenThrow(ChatakAdminException.class);
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error(
          "Error :: ProgramManagerControllerTest :: testShowSearchProgramManagerChatakAdminException",
          e);
    }
  }

  @Test
  public void testShowSearchProgramManagerException() {
    try {
      Mockito.when(bankService.getBankData()).thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: ProgramManagerControllerTest :: testShowSearchProgramManagerException",
          e);
    }
  }

  @Test
  public void testShowCreateProgramManager() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_PREPAID_ADMIN_CREATE_PROGRAM_MANAGER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_CREATE_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: ProgramManagerControllerTest :: testShowCreateProgramManager", e);
    }
  }

  @Test
  public void testShowCreateProgramManagerException() {
    try {
      Mockito.when(bankService.getBankData()).thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_PREPAID_ADMIN_CREATE_PROGRAM_MANAGER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_CREATE_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: ProgramManagerControllerTest :: testShowCreateProgramManagerException",
          e);
    }
  }

  @Test
  public void testProcessSearchProgramManagerIf() {
    programManagerResponse = new ProgramManagerResponse();
    programManagerResponse.setTotalNoOfRows(Integer.parseInt("1"));
    programManagerRequests = new ArrayList<>();
    programManagerRequest = new ProgramManagerRequest();
    programManagerRequest.setAccountCurrency("accountCurrency");
    programManagerRequests.add(programManagerRequest);
    programManagerResponse.setProgramManagersList(programManagerRequests);
    try {
      Mockito
          .when(programManagerService
              .searchProgramManagerDetails(Matchers.any(ProgramManagerRequest.class)))
          .thenReturn(programManagerResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, "1").param("pageSize", "1"))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: ProgramManagerControllerTest :: testProcessSearchProgramManagerIf", e);
    }
  }

  @Test
  public void testProcessSearchProgramManagerElse() {
    programManagerResponse = new ProgramManagerResponse();
    programManagerResponse.setTotalNoOfRows(Integer.parseInt("1"));
    try {
      Mockito
          .when(programManagerService
              .searchProgramManagerDetails(Matchers.any(ProgramManagerRequest.class)))
          .thenReturn(programManagerResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, "1"))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: ProgramManagerControllerTest :: testProcessSearchProgramManagerElse",
          e);
    }
  }

  @Test
  public void testProcessSearchProgramManagerChatakAdminException() {
    programManagerResponse = new ProgramManagerResponse();
    programManagerResponse.setTotalNoOfRows(Integer.parseInt("1"));
    try {
      Mockito.when(bankService.getBankData()).thenThrow(ChatakAdminException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, "1"))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error(
          "Error :: ProgramManagerControllerTest :: testProcessSearchProgramManagerChatakAdminException",
          e);
    }
  }

  @Test
  public void testProcessSearchProgramManagerException() {
    programManagerResponse = new ProgramManagerResponse();
    programManagerResponse.setTotalNoOfRows(Integer.parseInt("1"));
    try {
      Mockito.when(bankService.getBankData()).thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, "1"))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error(
          "Error :: ProgramManagerControllerTest :: testProcessSearchProgramManagerException", e);
    }
  }

  @Test
  public void testGetProgramManagerPagination() {
    programManagerRequest = new ProgramManagerRequest();
    programManagerResponse = new ProgramManagerResponse();
    programManagerRequests = new ArrayList<>();
    programManagerRequest.setProgramManagerName("programManagerName");
    programManagerRequest.setPageSize(1);
    programManagerRequests.add(programManagerRequest);
    programManagerResponse.setProgramManagersList(programManagerRequests);
    programManagerResponse.setTotalNoOfRows(1);
    try {
      Mockito
          .when(programManagerService
              .searchProgramManagerDetails(Matchers.any(ProgramManagerRequest.class)))
          .thenReturn(programManagerResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.PREPAID_ADMIN_PROGRAM_MANAGER_PAGINATION_ACTION)
              .sessionAttr(Constants.LOGIN_USER_ID, "1")
              .sessionAttr(Constants.PROGRAM_MANAGER_REQUEST_LIST_EXPORTDATA, programManagerRequest)
              .param("programManagerPageData", "1").param("pageSize", "1"))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: ProgramManagerControllerTest :: testGetProgramManagerPagination", e);
    }
  }

  @Test
  public void testGetProgramManagerPaginationException() {
    programManagerRequest = new ProgramManagerRequest();
    try {
      Mockito.when(bankService.getBankData()).thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.PREPAID_ADMIN_PROGRAM_MANAGER_PAGINATION_ACTION)
              .sessionAttr(Constants.LOGIN_USER_ID, "1").sessionAttr(
                  Constants.PROGRAM_MANAGER_REQUEST_LIST_EXPORTDATA, programManagerRequest))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error(
          "Error :: ProgramManagerControllerTest :: testGetProgramManagerPaginationException", e);
    }
  }

  @Test
  public void testDownloadProgramManagerReportPDF() {
    programManagerResponse = new ProgramManagerResponse();
    programManagerRequests = new ArrayList<>();
    programManagerRequest = new ProgramManagerRequest();
    programManagerRequest.setAccountCurrency("accountCurrency");
    programManagerRequests.add(programManagerRequest);
    programManagerResponse.setProgramManagersList(programManagerRequests);
    try {
      Mockito
          .when(programManagerService
              .searchProgramManagerDetails(Matchers.any(ProgramManagerRequest.class)))
          .thenReturn(programManagerResponse);
      mockMvc.perform(post("/" + URLMappingConstants.PREPAID_ADMIN_PROGRAM_MANAGER_REPORT)
          .sessionAttr(Constants.LOGIN_USER_ID, "1").param("downloadAllRecords", "true")
          .param("downloadType", "PDF")
          .sessionAttr(Constants.PROGRAM_MANAGER_REQUEST_LIST_EXPORTDATA, programManagerRequest));
    } catch (Exception e) {
      logger.error("Error :: ProgramManagerControllerTest :: testDownloadProgramManagerReportPDF",
          e);
    }
  }

  @Test
  public void testDownloadProgramManagerReportXLS() {
    programManagerResponse = new ProgramManagerResponse();
    programManagerRequests = new ArrayList<>();
    programManagerRequest = new ProgramManagerRequest();
    programManagerRequest.setAccountCurrency("accountCurrency");
    programManagerRequests.add(programManagerRequest);
    programManagerResponse.setProgramManagersList(programManagerRequests);
    try {
      Mockito
          .when(programManagerService
              .searchProgramManagerDetails(Matchers.any(ProgramManagerRequest.class)))
          .thenReturn(programManagerResponse);
      mockMvc.perform(post("/" + URLMappingConstants.PREPAID_ADMIN_PROGRAM_MANAGER_REPORT)
          .sessionAttr(Constants.LOGIN_USER_ID, "1").param("downloadAllRecords", "true")
          .param("downloadType", "XLS")
          .sessionAttr(Constants.PROGRAM_MANAGER_REQUEST_LIST_EXPORTDATA, programManagerRequest));
    } catch (Exception e) {
      logger.error("Error :: ProgramManagerControllerTest :: testDownloadProgramManagerReportXLS",
          e);
    }
  }

  @Test
  public void testShowEditProgramManager() {
    programManagerResponse = new ProgramManagerResponse();
    programManagerRequests = new ArrayList<>();
    programManagerRequest = new ProgramManagerRequest();
    programManagerRequest.setAccountCurrency("accountCurrency");
    programManagerRequests.add(programManagerRequest);
    programManagerResponse.setProgramManagersList(programManagerRequests);
    try {
      Mockito
          .when(programManagerService.editProgramManager(Matchers.any(ProgramManagerRequest.class)))
          .thenReturn(programManagerResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_PREPAID_ADMIN_EDIT_PROGRAM_MANAGER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_EDIT_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: ProgramManagerControllerTest :: testShowEditProgramManager", e);
    }
  }

  @Test
  public void testShowEditProgramManagerException() {
    programManagerResponse = new ProgramManagerResponse();
    programManagerRequests = new ArrayList<>();
    programManagerRequest = new ProgramManagerRequest();
    programManagerRequest.setAccountCurrency("accountCurrency");
    programManagerRequests.add(programManagerRequest);
    programManagerResponse.setProgramManagersList(programManagerRequests);
    try {
      Mockito
          .when(programManagerService.editProgramManager(Matchers.any(ProgramManagerRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_PREPAID_ADMIN_EDIT_PROGRAM_MANAGER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_EDIT_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: ProgramManagerControllerTest :: testShowEditProgramManagerException",
          e);
    }
  }

  @Test
  public void testChangeProgramManagerStatus() {
    responseval = new Response();
    responseval.setErrorCode(Constants.STATUS_CODE_SUCCESS);
    try {
      Mockito
          .when(programManagerService
              .updateProgramManagerStatus(Matchers.any(ProgramManagerRequest.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.PREPAID_ADMIN_CHANGE_PROGRAM_MANAGER_STATUS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, "1").param("manageProgramManagerStatus", ""))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: ProgramManagerControllerTest :: testShowEditProgramManagerException",
          e);
    }
  }

  @Test
  public void testChangeProgramManagerStatusElse() {
    responseval = new Response();
    responseval.setErrorCode(Constants.FAILED);
    try {
      Mockito
          .when(programManagerService
              .updateProgramManagerStatus(Matchers.any(ProgramManagerRequest.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.PREPAID_ADMIN_CHANGE_PROGRAM_MANAGER_STATUS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, "1").param("manageProgramManagerStatus", ""))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error(
          "Error :: ProgramManagerControllerTest :: testShowEditProgramManagerExceptionElse", e);
    }
  }

  @Test
  public void testChangeProgramManagerStatusAdminException() {
    try {
      Mockito
          .when(programManagerService
              .updateProgramManagerStatus(Matchers.any(ProgramManagerRequest.class)))
          .thenThrow(ChatakAdminException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.PREPAID_ADMIN_CHANGE_PROGRAM_MANAGER_STATUS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, "1").param("manageProgramManagerStatus", ""))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error(
          "Error :: ProgramManagerControllerTest :: testShowEditProgramManagerExceptionAdminException",
          e);
    }
  }

  @Test
  public void testChangeProgramManagerStatusException() {
    try {
      Mockito
          .when(programManagerService
              .updateProgramManagerStatus(Matchers.any(ProgramManagerRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.PREPAID_ADMIN_CHANGE_PROGRAM_MANAGER_STATUS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, "1").param("manageProgramManagerStatus", ""))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PROGRAM_MANAGER_PAGE));
    } catch (Exception e) {
      logger.error(
          "Error :: ProgramManagerControllerTest :: testShowEditProgramManagerExceptionException",
          e);
    }
  }

  @Test
  public void testFetchCurrencyByPM() {
    programManagerResponse = new ProgramManagerResponse();
    programManagerRequests = new ArrayList<>();
    programManagerRequest = new ProgramManagerRequest();
    programManagerRequest.setAccountCurrency("accountCurrency");
    programManagerRequests.add(programManagerRequest);
    programManagerResponse.setProgramManagersList(programManagerRequests);
    try {
      Mockito
          .when(programManagerService.editProgramManager(Matchers.any(ProgramManagerRequest.class)))
          .thenReturn(programManagerResponse);
      mockMvc.perform(get("/" + URLMappingConstants.PREPAID_ADMIN_FETCH_CURRENCY)
          .param("entityType", Constants.PROGRAM_MANAGER));
    } catch (Exception e) {
      logger.error("Error :: ProgramManagerControllerTest :: testFetchCurrencyByPM", e);
    }
  }

  @Test
  public void testFetchCurrency() {
    try {
      Mockito.when(partnerService.findByPartnerId(partner)).thenReturn(partnerResponse);
      mockMvc.perform(get("/" + URLMappingConstants.PREPAID_ADMIN_FETCH_CURRENCY)
          .param("entityType", Constants.PARTNER));
    } catch (Exception e) {
      logger.error("Error :: ProgramManagerControllerTest :: testFetchCurrency", e);
    }
  }
}
