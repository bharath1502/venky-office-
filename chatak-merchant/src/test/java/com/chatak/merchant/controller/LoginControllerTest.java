package com.chatak.merchant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;

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
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.controller.model.LoginDetails;
import com.chatak.merchant.controller.model.LoginResponse;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.GetMerchantDetailsResponse;
import com.chatak.merchant.service.AccountService;
import com.chatak.merchant.service.LoginService;
import com.chatak.merchant.service.MerchantProfileService;
import com.chatak.merchant.service.RestPaymentService;
import com.chatak.merchant.service.TransactionService;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUserDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

  private static Logger logger = Logger.getLogger(VirtualTerminalControllerTest.class);

  @InjectMocks
  LoginController loginController = new LoginController();

  @Mock
  private LoginValidator loginValidator;

  @Mock
  private LoginService loginSevice;

  @Mock
  private SessionRegistryImpl sessionRegistry;

  @Mock
  private TransactionService transactionService;

  @Mock
  private RestPaymentService paymentService;

  @Mock
  AccountService accountService;

  @Mock
  MerchantProfileService merchantProfileService;

  @Mock
  private MerchantUserDao merchantUserDao;

  @Mock
  private MerchantDao merchantDao;

  @Mock
  private MessageSource messageSource;

  @Mock
  MerchantProfileDao merchantProfileDao;

  @Mock
  LoginResponse loginResponse;

  @Mock
  List<String> existingFeature;

  @Mock
  LoginDetails loginDetails;

  @Mock
  SessionInformation information;

  @Mock
  Cookie cookie;

  @Mock
  GetMerchantDetailsResponse merchantDetailsResponse;

  @Mock
  PGAccount accountDetails;

  private MockMvc mockMvc;

  @Mock
  Date lastSessionRequestDate;

  @Mock
  List<AccountTransactionDTO> accountTransactionList;

  @Mock
  PGMerchant parentMerchant;

  @Mock
  AccountTransactionDTO accountTransactionDTO;

  @Mock
  GetTransactionsListResponse manualTransactionsReportList;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
    mockMvc =
        MockMvcBuilders.standaloneSetup(loginController).setViewResolvers(viewResolver).build();
  }

  @Before
  public void pro() {
    java.util.Properties properties = new java.util.Properties();
    properties.setProperty("chatak.merchant.login.password.expiration.error.message", "success");
    Properties.mergeProperties(properties);
  }


  @Test
  public void testShowLogin() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_LOGIN)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_LOGIN));
    } catch (Exception e) {
      logger.error("ERROR :: LoginControllerTest :: testShowLogin:", e);
    }
  }

  @Test
  public void testaAthenticateUser() {
    loginResponse();
    try {
      Mockito.when(loginSevice.authenticate(Matchers.any(LoginDetails.class)))
          .thenReturn(loginResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_AUTHENTICATE)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.NEW_USER_PSWD_MANAGEMENT));
    } catch (Exception e) {
      logger.error("ERROR :: LoginControllerTest :: testaAthenticateUser:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testaAthenticateUserException() {
    loginResponse();
    try {
      Mockito.when(loginSevice.authenticate(Matchers.any(LoginDetails.class)))
          .thenThrow(NullPointerException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_AUTHENTICATE)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_LOGIN));
    } catch (Exception e) {
      logger.error("ERROR :: LoginControllerTest :: testaAthenticateUserException:", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testaAthenticateUserChatakMerchantException() {
    loginResponse();
    try {
      Mockito.when(loginSevice.authenticate(Matchers.any(LoginDetails.class)))
          .thenThrow(ChatakMerchantException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_AUTHENTICATE)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_LOGIN));
    } catch (Exception e) {
      logger.error("ERROR :: LoginControllerTest :: testaAthenticateUserChatakMerchantException:",
          e);
    }
  }

  private void loginResponse() {
    existingFeature = new ArrayList<>();
    loginResponse = new LoginResponse();
    loginResponse.setUserId(Long.parseLong("10"));
    loginResponse.setUserMerchantId(Long.parseLong("10"));
    loginResponse.setUserType("Admin");
    loginResponse.setExistingFeature(existingFeature);
    loginResponse.setStatus(true);
    loginResponse.setUserType(PGConstants.NEW_USER);
    loginResponse.setUserId(Long.parseLong("12"));
  }

  @Test
  public void testaAthenticateUserUserTypeNull() {
    existingFeature = new ArrayList<>();
    loginResponse = new LoginResponse();
    loginResponse.setUserId(Long.parseLong("10"));
    loginResponse.setStatus(true);
    loginResponse.setErrorCode(Constants.SUCCESS);
    loginResponse.setErrorMessage(Constants.SUCCESS);
    try {
      Mockito.when(loginSevice.authenticate(Matchers.any(LoginDetails.class)))
          .thenReturn(loginResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_AUTHENTICATE)
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.NEW_USER_PSWD_MANAGEMENT));
    } catch (Exception e) {
      logger.error("ERROR :: LoginControllerTest :: testaAthenticateUserUserTypeNull:", e);
    }
  }

  @Test
  public void testaAthenticatesetLoginSuccessResponse() {
    loginDetails = new LoginDetails();
    List<Object> listOfObject = new ArrayList<Object>();
    loginDetails.setjSession("123");
    loginDetails.setAcqP("password");
    loginDetails.setAcqU("userName");
    listOfObject.add(loginDetails);
    existingFeature = new ArrayList<>();
    try {
      Mockito.when(information.getLastRequest()).thenReturn(lastSessionRequestDate);
      Mockito.when(sessionRegistry.getSessionInformation(Matchers.anyString()))
          .thenReturn(information);
      Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
          Matchers.any(Locale.class))).thenReturn("12");
      Mockito.when(sessionRegistry.getAllPrincipals()).thenReturn(listOfObject);
      Mockito.when(loginSevice.authenticate(Matchers.any(LoginDetails.class)))
          .thenReturn(loginResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_AUTHENTICATE)
              .param("acqU", "userName").param("acqP", "userName")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_INVALID_SESSION));
    } catch (Exception e) {
      logger.error("ERROR :: LoginControllerTest :: testaAthenticatesetLoginSuccessResponse:", e);
    }
  }

  @Test
  public void testaAthenticatesetLoginSuccessResponseElse() {
    accountTransactionDTO = new AccountTransactionDTO();
    accountTransactionDTO.setMerchantCode("123");
    List<Object> listOfObject = new ArrayList<Object>();
    loginDetails = new LoginDetails();
    loginDetails.setAcqP("password");
    loginDetails.setAcqU("userName");
    loginDetails.setjSession("123");
    listOfObject.add(loginDetails);
    existingFeature = new ArrayList<>();
    try {
      Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
          Matchers.any(Locale.class))).thenReturn("12");
      Mockito.when(information.getLastRequest()).thenReturn(lastSessionRequestDate);
      Mockito.when(sessionRegistry.getSessionInformation(Matchers.anyString()))
          .thenReturn(information);
      Mockito.when(sessionRegistry.getAllPrincipals()).thenReturn(listOfObject);
      Mockito.when(loginSevice.authenticate(Matchers.any(LoginDetails.class)))
          .thenReturn(loginResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_AUTHENTICATE)
              .header("user-agent", "123").param("acqU", "userName").param("acqP", "userName")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_LOGIN));
    } catch (Exception e) {
      logger.error("ERROR :: LoginControllerTest :: testShowLogin:", e);
    }
  }


  @Test
  public void testaAthenticatesetLoginStatus() {
    GetTransactionsListResponse processingTxnList = new GetTransactionsListResponse();
    accountTransactionList = new ArrayList<>();
    accountTransactionDTO = new AccountTransactionDTO();
    accountTransactionDTO.setMerchantCode("2562");
    accountTransactionDTO.setAccountNumber("1122");
    accountTransactionList.add(accountTransactionDTO);
    manualTransactionsReportList.setAccountTransactionList(accountTransactionList);
    parentMerchant = new PGMerchant();
    parentMerchant.setMerchantCode("2562");
    accountTransactionList = new ArrayList<>();
    accountTransactionDTO = new AccountTransactionDTO();
    accountTransactionList.add(accountTransactionDTO);
    processingTxnList.setAccountTransactionList(accountTransactionList);
    merchantDetailsResponse = new GetMerchantDetailsResponse();
    merchantDetailsResponse.setMerchantId("1254");
    existingFeature = new ArrayList<>();
    accountDetails = new PGAccount();
    accountDetails.setCurrency("Rupee");
    loginResponse = new LoginResponse();
    loginResponse.setUserMerchantId(Long.parseLong("12"));
    loginResponse.setExistingFeature(existingFeature);
    List<Object> listOfObject = new ArrayList<Object>();
    loginDetails = new LoginDetails();
    loginDetails.setAcqP("password");
    loginDetails.setAcqU("userName");
    loginDetails.setjSession("123");
    listOfObject.add(loginDetails);
    loginResponse.setStatus(true);
    try {
      Mockito.when(manualTransactionsReportList.getAccountTransactionList())
          .thenReturn(accountTransactionList);
      Mockito.when(manualTransactionsReportList.getAccountTransactionList())
          .thenReturn(accountTransactionList);
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(processingTxnList);
      Mockito.when(merchantProfileDao.getMerchantById(Matchers.anyLong()))
          .thenReturn(parentMerchant);
      Mockito
          .when(transactionService
              .searchAccountTransactions(Matchers.any(GetTransactionsListRequest.class)))
          .thenReturn(processingTxnList);
      Mockito.when(accountService.getAccountDetailsByEntityId(Matchers.anyString()))
          .thenReturn(accountDetails);
      Mockito.when(paymentService.getMerchantIdAndTerminalId(Matchers.anyString()))
          .thenReturn(merchantDetailsResponse);
      Mockito.when(loginSevice.authenticate(Matchers.any(LoginDetails.class)))
          .thenReturn(loginResponse);
      Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
          Matchers.any(Locale.class))).thenReturn("12");
      Mockito.when(information.getLastRequest()).thenReturn(lastSessionRequestDate);
      Mockito.when(loginSevice.authenticate(Matchers.any(LoginDetails.class)))
          .thenReturn(loginResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_AUTHENTICATE)
              .header("user-agent", "123").param("acqU", "userName").param("acqP", "userName")
              .param("merchantCode", "12").param("status", "true")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: LoginControllerTest :: testaAthenticatesetLoginStatus:", e);
    }
  }

  @Test
  public void testReloadErrorPage() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.INVALID_REQUEST_PAGE)
              .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
              .sessionAttr("loginUserMerchantId", Long.parseLong("1234")))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR :: LoginControllerTest :: testReloadErrorPage:", e);
    }
  }

  @Test
  public void testLogout() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_LOG_OUT))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_LOG_OUT));
    } catch (Exception e) {
      logger.error("ERROR :: LoginControllerTest :: testLogout:", e);
    }
  }

  @Test
  public void testAccessInvalid() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_INVALID_ACCESS))
          .andExpect(view().name(URLMappingConstants.CHATAK_INVALID_ACCESS));
    } catch (Exception e) {
      logger.error("ERROR :: LoginControllerTest :: testAccessInvalid:", e);
    }
  }

  @Test
  public void testSessionInvalid() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_INVALID_SESSION))
          .andExpect(view().name(URLMappingConstants.CHATAK_INVALID_SESSION));
    } catch (Exception e) {
      logger.error("ERROR :: LoginControllerTest :: testSessionInvalid:", e);
    }
  }

  @Test
  public void testAuthenticateLogin() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_AUTHENTICATE))
          .andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_LOGIN));
    } catch (Exception e) {
      logger.error("ERROR :: LoginControllerTest :: testAuthenticateLogin:", e);
    }
  }

}
