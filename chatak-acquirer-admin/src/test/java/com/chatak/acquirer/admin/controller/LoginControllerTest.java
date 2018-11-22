package com.chatak.acquirer.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.acquirer.admin.controller.model.LoginResponse;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.FundTransfersService;
import com.chatak.acquirer.admin.service.LoginService;
import com.chatak.acquirer.admin.service.MerchantUpdateService;
import com.chatak.acquirer.admin.service.RoleService;
import com.chatak.acquirer.admin.service.TransactionService;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {
  
  private static Logger logger = Logger.getLogger(LoginControllerTest.class);

	@InjectMocks
	LoginController loginController = new LoginController();
	@Mock
	private LoginValidator loginValidator;

	@Mock
	private LoginService loginService;

	@Mock
	private SessionRegistryImpl sessionRegistry;

	@Mock
	private RoleService roleService;

	@Mock
	private TransactionDao transactionDao;

	@Mock
	private MerchantDao merchantDao;

	@Mock
	private FundTransfersService fundTransfersService;

	@Mock
	private TransactionService transactionService;

	@Mock
	private MerchantUpdateService merchantUpdateService;

	@Mock
	private MessageSource messageSource;

	@Mock
	HttpSession session;

	@Mock
	Map model;

	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	@Mock
	BindingResult bindingResult;

	@Mock
	LoginDetails loginDetails;

	@Mock
	SessionInformation information;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/pages/");
		viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).setViewResolvers(viewResolver).build();
	}

    @Before
    public void pro() {
      java.util.Properties properties = new java.util.Properties();
      properties.setProperty("merchant.services.funding.report.feature.id", "existingFeature");
      properties.setProperty("chatak.admin.session.timeout", "10");
      Properties.mergeProperties(properties);
    }

  @Test
  public void testShowBatchReport() {
    try {
      mockMvc.perform(
          get("/" + URLMappingConstants.CHATAK_ADMIN_LOGIN).sessionAttr("loginUser", "loginUser"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_LOGIN));
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testShowBatchReport method", e);
    }
  }

  @Test
  public void testAuthenticate() {
    LoginResponse loginResponse = new LoginResponse();
    loginResponse.setUserType(PGConstants.NEW_USER);
    try {
      Mockito.when(loginService.authenticate(Matchers.any(LoginDetails.class)))
          .thenReturn(loginResponse);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_AUTHENTICATE)
          .sessionAttr("loginUserId", "loginUserId").sessionAttr("loginUserType", "loginUserType")
          .header("user-agent", "user-agent").sessionAttr("loginUserId", Integer.parseInt("1234")))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_LOGIN));
    } catch (NumberFormatException e) {
      logger.error("ERROR:: LoginControllerTest:: testAuthenticate method1", e);
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testAuthenticate method2", e);
    }
  }

  @Test
  public void testAuthenticateWhenSessionInformationIsNull() {
    LoginResponse loginResponse = new LoginResponse();
    loginResponse.setUserType(PGConstants.NEW_USER);
    loginResponse.setUserId(Long.parseLong("123"));
    loginDetails = new LoginDetails();
    loginDetails.setAcqP("girmiti");
    loginDetails.setAcqU("soft");
    loginDetails.setjSession("123");
    List<Object> listOfObject = new ArrayList<Object>();
    listOfObject.add(loginDetails);
    Date date = new Date();
    date.setTime(Long.parseLong("123"));
    try {
      Mockito.when(information.getLastRequest()).thenReturn(date);
      Mockito.when(sessionRegistry.getAllPrincipals()).thenReturn(listOfObject);
      Mockito.when(sessionRegistry.getSessionInformation(Matchers.anyString()))
          .thenReturn(information);
      Mockito.when(loginService.authenticate(Matchers.any(LoginDetails.class)))
          .thenReturn(loginResponse);

      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_AUTHENTICATE)
              .sessionAttr("loginUserType", "loginUserType").header("user-agent", "user-agent")
              .sessionAttr("loginUserId", "loginUserId").param("acqU", "soft"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_LOGIN));
    } catch (Exception e) {
      logger.error(
          "ERROR:: LoginControllerTest:: testAuthenticateWhenSessionInformationIsNull method", e);
    }
  }

  @Test
  public void testAuthenticateWhenSessionInformationFalse() {
    LoginResponse loginResponse = new LoginResponse();
    loginResponse.setStatus(false);
    loginDetails = new LoginDetails();
    loginDetails.setAcqP("girmiti");
    loginDetails.setAcqU("soft");
    loginDetails.setjSession("123");
    List<Object> listOfObject = new ArrayList<Object>();
    listOfObject.add(loginDetails);
    Date date = new Date();
    date.setTime(Long.parseLong("10"));
    try {
      Mockito.when(information.getLastRequest()).thenReturn(date);
      Mockito.when(sessionRegistry.getAllPrincipals()).thenReturn(listOfObject);
      Mockito.when(sessionRegistry.getSessionInformation(Matchers.anyString()))
          .thenReturn(information);
      Mockito.when(loginService.authenticate(Matchers.any(LoginDetails.class)))
          .thenReturn(loginResponse);
      Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
          Matchers.any(Locale.class))).thenReturn("12");

      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_AUTHENTICATE)
          .sessionAttr("loginUserType", "loginUserType").header("user-agent", "user-agent")
          .sessionAttr("loginUserId", "loginUserId").sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
          .param("acqU", "soft"));
    } catch (Exception e) {
      logger.error(
          "ERROR:: LoginControllerTest:: testAuthenticateWhenSessionInformationFalse method", e);
    }
  }

  @Test
  public void testAuthenticateWhenSessionInformation() {
    LoginResponse loginResponse = new LoginResponse();
    loginResponse.setStatus(true);
    List<String> existingFeature = new ArrayList<String>();
    existingFeature.add("");
    loginResponse.setExistingFeature(existingFeature);
    loginDetails = new LoginDetails();
    loginDetails.setAcqP("girmiti");
    loginDetails.setAcqU("soft");
    loginDetails.setjSession("123");
    List<Object> listOfObject = new ArrayList<Object>();
    List<Merchant> merchants = new ArrayList<>();
    Merchant merchant = new Merchant();
    merchants.add(merchant);
    Date date = new Date();
    date.setTime(Long.parseLong("10"));
    try {
      Mockito.when(information.getLastRequest()).thenReturn(date);
      Mockito.when(sessionRegistry.getAllPrincipals()).thenReturn(listOfObject);
      Mockito.when(sessionRegistry.getSessionInformation(Matchers.anyString()))
          .thenReturn(information);
      Mockito.when(loginService.authenticate(Matchers.any(LoginDetails.class)))
          .thenReturn(loginResponse);
      Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
          Matchers.any(Locale.class))).thenReturn("12");
      Mockito.when(merchantUpdateService.getMerchantByStatusPendingandDecline())
          .thenReturn(merchants);
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_AUTHENTICATE)
          .sessionAttr("loginUserType", "loginUserType").header("user-agent", "user-agent")
          .sessionAttr("loginUserId", "loginUserId").sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
          .param("acqU", "soft"));
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testAuthenticateWhenSessionInformation method", e);
    }
  }

  @Test
  public void testAuthenticateWhenSessionInformationNotNull() {
    try {
      LoginResponse loginResponse = new LoginResponse();
      loginResponse.setUserType("NEW-USERS");
      loginDetails = new LoginDetails();
      loginDetails.setAcqP("girmiti");
      loginDetails.setAcqU("soft");
      List<Object> listOfObject = new ArrayList<Object>();
      listOfObject.add(loginDetails);
      Mockito.when(sessionRegistry.getAllPrincipals()).thenReturn(listOfObject);
      Mockito.when(sessionRegistry.getSessionInformation(Matchers.anyString()))
          .thenReturn(information);
      Mockito.when(loginService.authenticate(Matchers.any(LoginDetails.class)))
          .thenReturn(loginResponse);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_AUTHENTICATE)
              .sessionAttr("loginUserType", "loginUserType").header("user-agent", "user-agent")
              .sessionAttr("loginUserId", Long.parseLong("1234")).param("acqU", "soft"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_LOGIN));
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testAuthenticateWhenSessionInformationNotNull method", e);
    }
  }

  @Test
  public void testAuthenticateNull() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_AUTHENTICATE)
              .sessionAttr("bindingResult", true))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_LOGIN));
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testAuthenticateNull method", e);
    }
  }

  @Test
  public void testReloadErrorPage() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.INVALID_REQUEST_PAGE)
          .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin"))
          .andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testReloadErrorPage method", e);
    }
  }

  @Test
  public void testLogout() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_LOG_OUT)
          .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_LOG_OUT));
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testLogout method", e);
    }
  }

  @Test
  public void testChangePasswordData() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CHANGE_PSWD)
          .sessionAttr("loginUserId", Long.parseLong("121"))
          .sessionAttr("chatakAcqAdmin", "chatakAcqAdmin"));
    } catch (NumberFormatException e) {
      logger.error("ERROR:: LoginControllerTest:: testChangePasswordData method1", e);
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testChangePasswordData method2", e);
    }
  }

  @Test
  public void testChangePasswordDataException() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CHANGE_PSWD)
              .sessionAttr("loginUserId", Long.parseLong("121")));
    } catch (NumberFormatException e) {
      logger.error("ERROR:: LoginControllerTest:: testChangePasswordDataException method1", e);
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testChangePasswordDataException method2", e);
    }
  }

  @Test
  public void testChangePasswordDataChatakAdminException() {
    try {
      Mockito.when(loginService.changdPassword(Matchers.anyLong(), Matchers.anyString(),
          Matchers.anyString())).thenThrow(ChatakAdminException.class);
      mockMvc
          .perform(post("/" + URLMappingConstants.CHATAK_ADMIN_CHANGE_PSWD)
              .sessionAttr("loginUserId", Long.parseLong("121")))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_CHANGE_PSWD));
    } catch (NumberFormatException e) {
      logger.error("ERROR:: LoginControllerTest:: testChangePasswordDataChatakAdminException method1", e);
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testChangePasswordDataChatakAdminException method2", e);
    }
  }

  @Test
  public void testMyProfile() {
    try {
      mockMvc
          .perform(get("/" + URLMappingConstants.CHATAK_ADMIN_MYPROFILE).sessionAttr("loginUserId",
              Long.parseLong("121")))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MYPROFILE));
    } catch (NumberFormatException e) {
      logger.error("ERROR:: LoginControllerTest:: testMyProfile method1", e);
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testMyProfile method2", e);
    }
  }

  @Test
  public void testMyProfileException() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_ADMIN_MYPROFILE)
          .sessionAttr("loginUserId", "loginUserId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MYPROFILE));
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testMyProfileException method", e);
    }
  }

  @Test
  public void testUpdateUserProfile() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.UPDATE_USER_PROFILE).sessionAttr("loginUserId",
              Long.parseLong("121")))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MYPROFILE));
    } catch (NumberFormatException e) {
      logger.error("ERROR:: LoginControllerTest:: testUpdateUserProfile method1", e);
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testUpdateUserProfile method2", e);
    }
  }

  @Test
  public void testUpdateUserProfileException() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.UPDATE_USER_PROFILE).sessionAttr("loginUserId",
          "loginUserId")).andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_MYPROFILE));
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testUpdateUserProfileException method", e);
    }
  }

  @Test
  public void testForgotPassword() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.FORGOT_PSWD).sessionAttr("loginUserId",
          Long.parseLong("121"))).andExpect(view().name(URLMappingConstants.FORGOT_PSWD));
    } catch (NumberFormatException e) {
      logger.error("ERROR:: LoginControllerTest:: testForgotPassword method1", e);
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testForgotPassword method2", e);
    }
  }

  @Test
  public void testSubmitPassword() {
    try {
      mockMvc
          .perform(post("/" + URLMappingConstants.FORGOT_PSWD).sessionAttr("loginUserId",
              Long.parseLong("121")))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_LOGIN));
    } catch (NumberFormatException e) {
      logger.error("ERROR:: LoginControllerTest:: testSubmitPassword method1", e);
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testSubmitPassword method2", e);
    }
  }

  @Test
  public void testSubmitPasswordException() {
    try {
      Mockito.when(loginService.userExist(Matchers.anyString(), Matchers.anyString()))
          .thenThrow(new NullPointerException());
      mockMvc
          .perform(
              post("/" + URLMappingConstants.FORGOT_PSWD).sessionAttr("loginUserId", "loginUserId"))
          .andExpect(view().name(URLMappingConstants.FORGOT_PSWD));
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testSubmitPasswordException method", e);
    }
  }

  @Test
  public void testGetResetPassword() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.PSWD_RESET).sessionAttr("loginUserId",
          Long.parseLong("121"))).andExpect(view().name(URLMappingConstants.INVALID_TOKEN));
    } catch (NumberFormatException e) {
      logger.error("ERROR:: LoginControllerTest:: testGetResetPassword method1", e);
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testGetResetPassword method2", e);
    }
  }

  @Test
  public void testGetResetPasswordValidToken() {
    try {
      Mockito.when(loginService.validToken(Matchers.anyLong(), Matchers.anyString()))
          .thenReturn(true);
      mockMvc
          .perform(get("/" + URLMappingConstants.PSWD_RESET)
              .sessionAttr("loginUserId", "loginUserId").param("value", "1"))
          .andExpect(view().name(URLMappingConstants.INVALID_TOKEN));
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testGetResetPasswordValidToken method", e);
    }
  }

  @Test
  public void testResetPassword() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.PSWD_RESET).sessionAttr("resetUserId", "123"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_LOGIN));
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testResetPassword method", e);
    }
  }

  @Test
  public void testResetPasswordException() {
    try {
      mockMvc
          .perform(
              post("/" + URLMappingConstants.PSWD_RESET).sessionAttr("resetUserId", "resetUserId"))
          .andExpect(view().name(URLMappingConstants.RESET_PSWD));
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testResetPasswordException method", e);
    }
  }

  @Test
  public void testResetPasswordChatakAdminException() {
    try {
      mockMvc.perform(post("/" + URLMappingConstants.PSWD_RESET).sessionAttr("resetUserId", "123"))
          .andExpect(view().name(URLMappingConstants.CHATAK_ADMIN_LOGIN));
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testResetPasswordChatakAdminException method", e);
    }
  }

  @Test
  public void testAccessInvalid() {
    try {
      mockMvc.perform(
          get("/" + URLMappingConstants.ACCESS_INVALID).sessionAttr("resetUserId", "resetUserId"))
          .andExpect(view().name(URLMappingConstants.ACCESS_INVALID));
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testAccessInvalid method", e);
    }
  }

  @Test
  public void testSessionInvalid() {
    try {
      mockMvc.perform(get("/" + URLMappingConstants.CHATAK_INVALID_SESSION)
          .sessionAttr("resetUserId", "resetUserId"))
          .andExpect(view().name(URLMappingConstants.CHATAK_INVALID_SESSION));
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testSessionInvalid method", e);
    }
  }

  @Test
  public void testSwitchToIssuance() {
    try {
      mockMvc.perform(get("/" + "switchToIssuance").sessionAttr("resetUserId", "resetUserId"))
          .andExpect(view().name("issuance-loading"));
    } catch (Exception e) {
      logger.error("ERROR:: LoginControllerTest:: testSwitchToIssuance method", e);
    }
  }

  @Test(expected = NullPointerException.class)
  public void testAccessAuthenticateInvalid() {
    Mockito.doNothing().when(information).expireNow();
    loginController.accessAuthenticateInvalid(request, response, session, information);
  }
}
