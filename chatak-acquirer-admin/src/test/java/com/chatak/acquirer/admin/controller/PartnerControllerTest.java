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
import com.chatak.pg.enums.RoleLevel;
import com.chatak.pg.exception.PrepaidException;
import com.chatak.pg.user.bean.BankRequest;
import com.chatak.pg.user.bean.BankResponse;
import com.chatak.pg.user.bean.PartnerRequest;
import com.chatak.pg.user.bean.PartnerResponse;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.user.bean.ProgramManagerResponse;
import com.chatak.pg.util.Constants;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class PartnerControllerTest {

  private static Logger logger = Logger.getLogger(PartnerControllerTest.class);

  @InjectMocks
  PartnerController controller = new PartnerController();

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
  public void testShowCreatePartner() {
    programManagerResponse = new ProgramManagerResponse();
    bankResponse = new BankResponse();
    bankRequest = new BankRequest();
    bankRequest.setAcquirerId("acquirerId");
    bankRequests = new ArrayList<>();
    bankRequests.add(bankRequest);
    bankResponse.setBankRequests(bankRequests);
    programManagerRequests = new ArrayList<>();
    programManagerRequest = new ProgramManagerRequest();
    programManagerRequest.setAccountCurrency("accountCurrency");
    programManagerRequests.add(programManagerRequest);
    programManagerResponse.setProgramManagersList(programManagerRequests);
    try {
      Mockito
          .when(programManagerService
              .getAllProgramManagers(Matchers.any(ProgramManagerRequest.class)))
          .thenReturn(programManagerResponse);
      Mockito
          .when(programManagerService
              .findBankByProgramManagerId(Matchers.any(ProgramManagerRequest.class)))
          .thenReturn(bankResponse);
      Mockito
          .when(programManagerService.editProgramManager(Matchers.any(ProgramManagerRequest.class)))
          .thenReturn(programManagerResponse);
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_PREPAID_ADMIN_CREATE_PARTNER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.DEFAULT_PM_ID, 1l))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_CREATE_PARTNER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testShowCreatePartner", e);
    }
  }

  @Test
  public void testShowCreatePartnerException() {
    try {
      Mockito
          .when(programManagerService
              .getAllProgramManagers(Matchers.any(ProgramManagerRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_PREPAID_ADMIN_CREATE_PARTNER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_CREATE_PARTNER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testShowCreatePartnerException", e);
    }
  }

  @Test
  public void testShowPartner() {
    loginResponse = new LoginResponse();
    loginResponse.setUserType(RoleLevel.CP_SUPER_ADMIN.name());
    programManagerResponse = new ProgramManagerResponse();
    try {
      Mockito
          .when(programManagerService
              .getAllProgramManagers(Matchers.any(ProgramManagerRequest.class)))
          .thenReturn(programManagerResponse);
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_PREPAID_ADMIN_SEARCH_PARTNER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PARTNER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testShowPartner", e);
    }
  }

  @Test
  public void testShowPartnerPrepaidException() {
    loginResponse = new LoginResponse();
    loginResponse.setUserType(RoleLevel.CP_SUPER_ADMIN.name());
    try {
      Mockito
          .when(programManagerService
              .getAllProgramManagers(Matchers.any(ProgramManagerRequest.class)))
          .thenThrow(PrepaidException.class);
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_PREPAID_ADMIN_SEARCH_PARTNER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PARTNER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testShowPartnerPrepaidException", e);
    }
  }

  @Test
  public void testShowPartnerException() {
    loginResponse = new LoginResponse();
    loginResponse.setUserType(RoleLevel.CP_SUPER_ADMIN.name());
    try {
      Mockito
          .when(programManagerService
              .getAllProgramManagers(Matchers.any(ProgramManagerRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.SHOW_PREPAID_ADMIN_SEARCH_PARTNER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PARTNER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testShowPartnerException", e);
    }
  }

  @Test
  public void testSearchPartner() {
    loginResponse = new LoginResponse();
    partnerResponse = new PartnerResponse();
    partnerList = new ArrayList<>();
    partner = new PartnerRequest();
    partner.setAccountCurrency("accountCurrency");
    partnerList.add(partner);
    partnerResponse.setPartnerList(partnerList);
    try {
      Mockito.when(partnerService.searchPartner(Matchers.any(PartnerRequest.class)))
          .thenReturn(partnerResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.PREPAID_ADMIN_SEARCH_PARTNER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse).param("pageSize", "1"))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PARTNER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testSearchPartner", e);
    }
  }

  @Test
  public void testSearchPartnerAdminException() {
    loginResponse = new LoginResponse();
    partnerResponse = new PartnerResponse();
    partnerList = new ArrayList<>();
    partner = new PartnerRequest();
    partner.setAccountCurrency("accountCurrency");
    partnerList.add(partner);
    partnerResponse.setPartnerList(partnerList);
    try {
      Mockito.when(partnerService.searchPartner(Matchers.any(PartnerRequest.class)))
          .thenThrow(PrepaidException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.PREPAID_ADMIN_SEARCH_PARTNER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse).param("pageSize", "1"))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PARTNER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testSearchPartnerAdminException", e);
    }
  }

  @Test
  public void testSearchPartnerException() {
    loginResponse = new LoginResponse();
    try {
      Mockito.when(partnerService.searchPartner(Matchers.any(PartnerRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.PREPAID_ADMIN_SEARCH_PARTNER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PARTNER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testSearchPartnerException", e);
    }
  }

  @Test
  public void testShowEditPartner() {
    loginResponse = new LoginResponse();
    partnerResponse = new PartnerResponse();
    partnerList = new ArrayList<>();
    partner = new PartnerRequest();
    partner.setAccountCurrency("accountCurrency");
    partner.setWhiteListIPAddress("whiteListIPAddress");
    bankRequest = new BankRequest();
    bankRequest.setAcquirerId("acquirerId");
    bankRequests = new ArrayList<>();
    bankRequests.add(bankRequest);
    partner.setBankRequests(bankRequests);
    programManagerRequest = new ProgramManagerRequest();
    programManagerRequest.setProgramManagerName("programManagerName");
    byte[] partnerLogo = new byte[Integer.parseInt("5")];
    partner.setPartnerLogo(partnerLogo);
    partner.setProgramManagerRequest(programManagerRequest);
    partnerList.add(partner);
    partnerResponse.setPartnerList(partnerList);
    bankResponse = new BankResponse();
    bankResponse.setBankRequests(bankRequests);
    list = new ArrayList<>();
    list.add(1);
    responseval = new Response();
    responseval.setResponseList(list);
    try {
      Mockito.when(partnerService.findByPartnerId(Matchers.any(PartnerRequest.class)))
          .thenReturn(partnerResponse);
      Mockito
          .when(programManagerService
              .findBankByProgramManagerId(Matchers.any(ProgramManagerRequest.class)))
          .thenReturn(bankResponse);
      Mockito.when(merchantUpdateService.getStatesByCountry(Matchers.anyString()))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_PREPAID_ADMIN_EDIT_PARTNER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_EDIT_PARTNER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testShowEditPartner", e);
    }
  }

  @Test
  public void testShowEditPartnerException() {
    partnerResponse = new PartnerResponse();
    partnerList = new ArrayList<>();
    partner = new PartnerRequest();
    partner.setAccountCurrency("accountCurrency");
    byte[] partnerLogo = new byte[Integer.parseInt("5")];
    partner.setPartnerLogo(partnerLogo);
    partnerList.add(partner);
    partnerResponse.setPartnerList(partnerList);
    try {
      Mockito.when(partnerService.findByPartnerId(Matchers.any(PartnerRequest.class)))
          .thenReturn(partnerResponse);
      Mockito
          .when(programManagerService
              .findBankByProgramManagerId(Matchers.any(ProgramManagerRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.SHOW_PREPAID_ADMIN_EDIT_PARTNER)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_EDIT_PARTNER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testShowEditPartnerException", e);
    }
  }

  @Test
  public void testChangePartnerStatus() {
    responseval = new Response();
    responseval.setErrorCode(Constants.STATUS_CODE_SUCCESS);
    try {
      Mockito.when(partnerService.changePartnerStatus(Matchers.any(PartnerRequest.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHANGE_PARTNER_STATUS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, "1"))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PARTNER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testChangePartnerStatus", e);
    }
  }

  @Test
  public void testChangePartnerStatusElse() {
    responseval = new Response();
    responseval.setErrorCode(Constants.FAILED);
    try {
      Mockito.when(partnerService.changePartnerStatus(Matchers.any(PartnerRequest.class)))
          .thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHANGE_PARTNER_STATUS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, "1"))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PARTNER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testChangePartnerStatusElse", e);
    }
  }

  @Test
  public void testChangePartnerStatusException() {
    try {
      Mockito.when(partnerService.changePartnerStatus(Matchers.any(PartnerRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHANGE_PARTNER_STATUS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, "1"))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PARTNER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testChangePartnerStatusException", e);
    }
  }

  @Test
  public void testGetPartnerPagination() {
    loginResponse = new LoginResponse();
    loginResponse.setUserType(RoleLevel.CP_SUPER_ADMIN.name());
    partner = new PartnerRequest();
    programManagerResponse = new ProgramManagerResponse();
    programManagerRequests = new ArrayList<>();
    programManagerRequest = new ProgramManagerRequest();
    programManagerRequest.setAccountCurrency("accountCurrency");
    programManagerRequests.add(programManagerRequest);
    programManagerResponse.setProgramManagersList(programManagerRequests);
    try {
      Mockito
          .when(programManagerService
              .getAllProgramManagers(Matchers.any(ProgramManagerRequest.class)))
          .thenReturn(programManagerResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.PARTNER_PAGINATION_ACTION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, "1")
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse)
              .sessionAttr(Constants.PARTNER_REQUEST_LIST_EXPORTDATA, partner))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PARTNER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testGetPartnerPagination", e);
    }
  }

  @Test
  public void testGetPartnerPaginationException() {
    loginResponse = new LoginResponse();
    loginResponse.setUserType(RoleLevel.CP_SUPER_ADMIN.name());
    try {
      Mockito
          .when(programManagerService
              .getAllProgramManagers(Matchers.any(ProgramManagerRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.PARTNER_PAGINATION_ACTION)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_ID, "1")
              .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse))
          .andExpect(view().name(URLMappingConstants.PREPAID_ADMIN_SEARCH_PARTNER_PAGE));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testGetPartnerPaginationException", e);
    }
  }

  @Test
  public void testFetchBanksofPgmng() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.FETCH_BANKS_OF_PGMNG)
          .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse)
          .param("selectedProgramManagerId", "1").param("programManagerId", "1"));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testFetchBanksofPgmng", e);
    }
  }

  @Test
  public void testFetchBanksofPgmngException() {
    try {
      Mockito
          .when(programManagerService
              .findBankByProgramManagerId(Matchers.any(ProgramManagerRequest.class)))
          .thenThrow(ChatakAdminException.class);
      mockMvc.perform(get("/" + URLMappingConstants.FETCH_BANKS_OF_PGMNG)
          .sessionAttr(Constants.LOGIN_RESPONSE_DATA, loginResponse)
          .param("selectedProgramManagerId", "1").param("programManagerId", "1"));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testFetchBanksofPgmngException", e);
    }
  }

  @Test
  public void testDownloadPartnerReportPDF() {
    partnerResponse = new PartnerResponse();
    partner = new PartnerRequest();
    partner.setPartnerName("partnerName");
    partner.setCompanyName("companyName");
    partner.setBusinessEntityName("businessEntityName");
    partner.setStatus("status");
    partnerList = new ArrayList<>();
    partnerList.add(partner);
    partnerResponse.setPartnerList(partnerList);
    try {
      Mockito.when(partnerService.searchPartner(Matchers.any(PartnerRequest.class)))
          .thenReturn(partnerResponse);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_PARTNER_REPORT)
          .sessionAttr(Constants.PARTNER_REQUEST_LIST_EXPORTDATA, partner)
          .param(Constants.DOWNLOAD_TYPE, Constants.PDF_FILE_FORMAT).param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testDownloadPartnerReportPDF", e);
    }
  }

  @Test
  public void testDownloadPartnerReportXLS() {
    partnerResponse = new PartnerResponse();
    partner = new PartnerRequest();
    partner.setPartnerName("partnerName");
    partner.setCompanyName("companyName");
    partner.setBusinessEntityName("businessEntityName");
    partner.setStatus("status");
    partnerList = new ArrayList<>();
    partnerList.add(partner);
    partnerResponse.setPartnerList(partnerList);
    try {
      Mockito.when(partnerService.searchPartner(Matchers.any(PartnerRequest.class)))
          .thenReturn(partnerResponse);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_PARTNER_REPORT)
          .sessionAttr(Constants.PARTNER_REQUEST_LIST_EXPORTDATA, partner)
          .param(Constants.DOWNLOAD_TYPE, Constants.XLS_FILE_FORMAT).param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testDownloadPartnerReportXLS", e);
    }
  }

  @Test
  public void testDownloadPartnerReportException() {
    try {
      Mockito.when(partnerService.searchPartner(Matchers.any(PartnerRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc.perform(post("/" + URLMappingConstants.DOWNLOAD_PARTNER_REPORT)
          .sessionAttr(Constants.PARTNER_REQUEST_LIST_EXPORTDATA, partner)
          .param(Constants.DOWNLOAD_TYPE, Constants.XLS_FILE_FORMAT).param("downloadAllRecords", "true"));
    } catch (Exception e) {
      logger.error("Error :: PartnerControllerTest :: testDownloadPartnerReportException", e);
    }
  }
}
