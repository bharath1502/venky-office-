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
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.BankService;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.MerchantValidateService;
import com.chatak.acquirer.admin.service.ResellerService;
import com.chatak.acquirer.admin.service.TransactionService;
import com.chatak.acquirer.admin.service.UserService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.model.AdminUserDTO;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class DashboardControllerTest {

  private static Logger logger = Logger.getLogger(DashboardControllerTest.class);

  @InjectMocks
  DashboardController dashboardController = new DashboardController();

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

  @Mock
  private List<Merchant> merchants;

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
  private MerchantUpdateService merchantUpdateService;

  @Mock
  private TransactionService transactionService;

  @Mock
  private GetTransactionsListRequest getTransactionsListRequest;

  @Mock
  private GetTransactionsListResponse getTransactionsListResponse;

  @Mock
  private MerchantValidateService merchantValidateService;

  @Mock
  private CurrencyConfigService currencyConfigService;

  @Mock
  private BankService bankService;

  @Mock
  private ResellerService resellerService;

  @Mock
  private UserService userService;

  @Mock
  private Merchant merchant;

  @Mock
  private List<AdminUserDTO> adminUserList;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc =
        MockMvcBuilders.standaloneSetup(dashboardController).setViewResolvers(viewResolver).build();
    optionList = new ArrayList<>();
    option = new Option();
  }

  @Test
  public void testShowLogin() {
    try {
      Mockito.when(merchantUpdateService.getMerchantByStatusPendingandDecline())
          .thenReturn(merchants);
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(getTransactionsListResponse);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_HOME)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, Constants.ADMIN_VALUE))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_HOME));
    } catch (Exception e) {
      logger.error("DashboardControllerTest | testShowLogin | Exception ", e);
    }
  }

  @Test
  public void testShowLoginException() {
    try {
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenThrow(nullPointerException);
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_HOME)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, Constants.ADMIN_VALUE))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error("DashboardControllerTest | testShowLoginException | Exception ", e);
    }
  }

  @Test
  public void testShowViewSubMerchant() {
    merchant = new Merchant();
    merchant.setBankCurrencyCode(Constants.TEN.toString());
    try {
      Mockito.when(merchantUpdateService.getCountries()).thenReturn(optionList);
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenReturn(merchant);
      Mockito.when(merchantUpdateService.getStatesByCountry(Matchers.anyString()))
          .thenReturn(responseval);
      Mockito.when(currencyConfigService.getcurrencyCodeAlpha(Matchers.anyString()))
          .thenReturn(responseval);
      Mockito.when(merchantUpdateService.getAgentNames(Matchers.anyString()))
          .thenReturn(responseval);
      Mockito.when(merchantValidateService.getProcessorNames()).thenReturn(optionList);
      Mockito.when(bankService.getBankData()).thenReturn(optionList);
      Mockito.when(resellerService.getResellerData()).thenReturn(optionList);
      mockMvc
          .perform(post("/" + URLMappingConstants.PENDING_MERCHANT_SHOW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, Constants.ADMIN_VALUE)
              .param("merchantViewId", Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.PENDING_MERCHANT_SHOW));
    } catch (Exception e) {
      logger.error("DashboardControllerTest | testShowViewSubMerchant | Exception ", e);
    }
  }

  @Test
  public void testShowViewSubMerchantException() {
    try {
      Mockito.when(merchantValidateService.getMerchant(Matchers.any(Merchant.class)))
          .thenThrow(chatakAdminException);
      mockMvc
          .perform(post("/" + URLMappingConstants.PENDING_MERCHANT_SHOW)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, Constants.ADMIN_VALUE)
              .param("merchantViewId", Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error("DashboardControllerTest | testShowViewSubMerchantException | Exception ", e);
    }
  }

  @Test
  public void testShowUnblockUsers() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_UNBLOCK_USERS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, Constants.ADMIN_VALUE)
              .param("merchantViewId", Constants.ONE.toString()))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_UNBLOCK_USERS));
    } catch (Exception e) {
      logger.error("DashboardControllerTest | testShowUnblockUsers | Exception ", e);
    }
  }

  @Test
  public void testSearchAdminUser() {
    try {
      Mockito.when(userService.searchAdminUserList()).thenReturn(adminUserList);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UNBLOCK_USERS_SEARCH)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, Constants.ADMIN_VALUE).param("userType", Constants.ADMIN_USER_TYPE))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_UNBLOCK_USERS));
    } catch (Exception e) {
      logger.error("DashboardControllerTest | testSearchAdminUser | Exception ", e);
    }
  }

  @Test
  public void testSearchAdminUserMerchant() {
    try {
      Mockito.when(userService.searchAdminUserList()).thenReturn(adminUserList);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UNBLOCK_USERS_SEARCH)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, Constants.ADMIN_VALUE).param("userType", Constants.TYPE_MERCHANT))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_UNBLOCK_USERS));
    } catch (Exception e) {
      logger.error("DashboardControllerTest | testSearchAdminUserMerchant | Exception ", e);
    }
  }

  @Test
  public void testSearchAdminUserException() {
    try {
      Mockito.when(userService.searchAdminUserList()).thenThrow(nullPointerException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_UNBLOCK_USERS_SEARCH)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, Constants.ADMIN_VALUE))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_UNBLOCK_USERS));
    } catch (Exception e) {
      logger.error("DashboardControllerTest | testSearchAdminUserException | Exception ", e);
    }
  }

  @Test
  public void testunblockUser() {
    responseval = new Response();
    responseval.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(userService.unblockAdminUser(Matchers.anyString())).thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_DO_UNBLOCK_USERS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, Constants.ADMIN_VALUE).param("userName", Constants.ADMIN_USER_TYPE)
              .param("entityType", Constants.ADMIN_USER_TYPE))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_UNBLOCK_USERS));
    } catch (Exception e) {
      logger.error("DashboardControllerTest | testunblockUser | Exception ", e);
    }
  }

  @Test
  public void testunblockUserMerchant() {
    responseval = new Response();
    responseval.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    try {
      Mockito.when(userService.unblockAdminUser(Matchers.anyString())).thenReturn(responseval);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_DO_UNBLOCK_USERS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "merchant").param("userName", Constants.TYPE_MERCHANT)
              .param("entityType", Constants.TYPE_MERCHANT))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_UNBLOCK_USERS));
    } catch (Exception e) {
      logger.error("DashboardControllerTest | testunblockUserMerchant | Exception ", e);
    }
  }

  @Test
  public void testunblockUserMerchantException() {
    try {
      Mockito.when(userService.unblockMerchantUser(Matchers.anyString()))
          .thenThrow(chatakAdminException);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_DO_UNBLOCK_USERS)
              .sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES)
              .sessionAttr(Constants.LOGIN_USER_TYPE, "merchant").param("userName", Constants.TYPE_MERCHANT)
              .param("entityType", Constants.TYPE_MERCHANT))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_UNBLOCK_USERS));
    } catch (Exception e) {
      logger.error("DashboardControllerTest | testunblockUserMerchantException | Exception ", e);
    }
  }
}
