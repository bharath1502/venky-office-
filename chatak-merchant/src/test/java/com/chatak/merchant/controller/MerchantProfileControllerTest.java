package com.chatak.merchant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.controller.model.MerchantProfile;
import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.UserProfileRequest;
import com.chatak.merchant.service.AccountService;
import com.chatak.merchant.service.LoginService;
import com.chatak.merchant.service.MerchantProfileService;
import com.chatak.pg.bean.Response;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class MerchantProfileControllerTest {

	private Logger logger = Logger.getLogger(MerchantProfileController.class);

	@InjectMocks
	MerchantProfileController merchantProfileController = new MerchantProfileController();

	@Mock
	AccountService accountService;

	@Mock
	private LoginService loginSevice;

	@Mock
	private MessageSource messageSource;

	@Mock
	private SessionRegistryImpl sessionRegistry;

	@Mock
	MerchantProfileService merchantProfileService;

	@Mock
	HttpServletRequest request;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/pages/");
		viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(merchantProfileController).setViewResolvers(viewResolver).build();
	}

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("merchant.service.dashboard.feature.id", "existingFeature");
		properties.setProperty("chatak.admin.invalid.token", "token");
		Properties.mergeProperties(properties);
	}

	@Test
	public void testShowMerchantForgotPassword() {
		try {
			mockMvc.perform(get("/" + URLMappingConstants.SHOW_MERCHANT_FORGOT_PSWD).sessionAttr(Constants.EXISTING_FEATURES,
					Constants.EXISTING_FEATURES)).andExpect(view().name(URLMappingConstants.SHOW_MERCHANT_FORGOT_PSWD));
		} catch (Exception e) {
			logger.error("Error:: MerchantProfileControllerTest:: testShowMerchantForgotPassword method" + e);

		}

	}

	@Test
	public void testProcessMerchantForgotPassword() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_MERCHANT_FORGOT_PSWD)
					.sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("userName", "usrName"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_LOGIN));
		} catch (Exception e) {
			logger.error("Error:: MerchantProfileControllerTest:: testProcessMerchantForgotPassword method" + e);

		}
	}

	@Test
	public void testProcessMerchantForgotPasswordElse() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_MERCHANT_FORGOT_PSWD).sessionAttr(Constants.EXISTING_FEATURES,
					Constants.EXISTING_FEATURES)).andExpect(view().name(URLMappingConstants.SHOW_MERCHANT_FORGOT_PSWD));
		} catch (Exception e) {
			logger.error("Error:: MerchantProfileControllerTest:: testProcessMerchantForgotPasswordElse method" + e);

		}

	}

	@Test
	public void testProcessMerchantForgotPasswordChatakMerchantException() throws ChatakMerchantException {
		Mockito.when(loginSevice.userExist(Matchers.anyString(), Matchers.anyString()))
				.thenThrow(ChatakMerchantException.class);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_MERCHANT_FORGOT_PSWD)
					.sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("userName", "usrName"))
					.andExpect(view().name(URLMappingConstants.SHOW_MERCHANT_FORGOT_PSWD));
		} catch (Exception e) {
			logger.error(
					"Error:: MerchantProfileControllerTest:: testProcessMerchantForgotPasswordChatakMerchantException method"
							+ e);

		}
	}

	@Test
	public void testProcessMerchantForgotPasswordException() throws ChatakMerchantException {
		Mockito.when(loginSevice.userExist(Matchers.anyString(), Matchers.anyString()))
				.thenThrow(new NullPointerException());
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_MERCHANT_FORGOT_PSWD)
					.sessionAttr(Constants.EXISTING_FEATURES, Constants.EXISTING_FEATURES).param("userName", "usrName"))
					.andExpect(view().name(URLMappingConstants.SHOW_MERCHANT_FORGOT_PSWD));
		} catch (Exception e) {
			logger.error("Error:: MerchantProfileControllerTest:: testShowMerchantChangePassword method" + e);
		}
	}

	@Test
	public void testShowMerchantChangePassword() {
		try {
			mockMvc.perform(get("/" + URLMappingConstants.SHOW_MERCHANT_CHANGE_PSWD).sessionAttr("loginUserId",
					Long.parseLong("2345"))).andExpect(view().name(URLMappingConstants.SHOW_MERCHANT_CHANGE_PSWD));
		} catch (NumberFormatException e) {
			logger.error("Error:: MerchantProfileControllerTest:: testShowMerchantChangePassword method" + e);

		} catch (Exception e) {
			logger.error("Error:: MerchantProfileControllerTest:: testShowMerchantChangePassword method" + e);

		}

	}

	@Test
	public void testShowMerchantChangePasswordNull() {
		try {
			mockMvc.perform(get("/" + URLMappingConstants.SHOW_MERCHANT_CHANGE_PSWD))
					.andExpect(view().name(URLMappingConstants.CHATAK_INVALID_SESSION));
		} catch (Exception e) {
			logger.error("Error:: MerchantProfileControllerTest:: testShowMerchantChangePasswordNull method" + e);

		}
	}

	@Test
	public void testProcessMerchantChangePassword() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_MERCHANT_CHANGE_PSWD)
					.sessionAttr("loginUserId", Long.parseLong("2345")).param("confirmPassword", "confirmPassword")
					.param("newPassword", "newPassword"))
					.andExpect(view().name(URLMappingConstants.SHOW_MERCHANT_CHANGE_PSWD));
		} catch (NumberFormatException e) {
			logger.error("Error:: MerchantProfileControllerTest:: testProcessMerchantChangePassword method" + e);

		} catch (Exception e) {
			logger.error("Error:: MerchantProfileControllerTest:: testProcessMerchantChangePassword method" + e);

		}
	}

	@Test
	public void testProcessMerchantChangePasswordSession() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_MERCHANT_CHANGE_PSWD)
					.sessionAttr("loginUserId", Long.parseLong("2345")).sessionAttr("chatakAcqAdmin", "chatakAcqAdmin")
					.param("confirmPassword", "newPassword").param("newPassword", "newPassword"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_LOGIN));
		} catch (NumberFormatException e) {
			logger.error("Error:: MerchantProfileControllerTest:: testProcessMerchantChangePasswordSession method" + e);

		} catch (Exception e) {
			logger.error("Error:: MerchantProfileControllerTest:: testProcessMerchantChangePasswordSession method" + e);

		}
	}

	@Test
	public void testProcessMerchantChangePasswordElse() throws ChatakMerchantException {
		Mockito.when(loginSevice.changdPassword(Matchers.anyLong(), Matchers.anyString(), Matchers.anyString()))
				.thenThrow(new NullPointerException());
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_MERCHANT_CHANGE_PSWD).sessionAttr("loginUserId",
					Long.parseLong("2345"))).andExpect(view().name(URLMappingConstants.SHOW_MERCHANT_CHANGE_PSWD));
		} catch (NumberFormatException e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testProcessMerchantChangePasswordElse method" + e);

		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testProcessMerchantChangePasswordElse method" + e);

		}
	}

	@Test
	public void testProcessMerchantChangePasswordChatakMerchantException() throws ChatakMerchantException {
		Mockito.when(loginSevice.changdPassword(Matchers.anyLong(), Matchers.anyString(), Matchers.anyString()))
				.thenThrow(new NullPointerException());
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_MERCHANT_CHANGE_PSWD)
					.sessionAttr("loginUserId", Long.parseLong("2345")).param("confirmPassword", "confirmPassword")
					.param("newPassword", "newPassword"))
					.andExpect(view().name(URLMappingConstants.SHOW_MERCHANT_CHANGE_PSWD));
		} catch (NumberFormatException e) {
			logger.info(
					"Exit:: MerchantProfileControllerTest:: testProcessMerchantChangePasswordChatakMerchantException method"
							+ e);

		} catch (Exception e) {
			logger.info(
					"Exit:: MerchantProfileControllerTest:: testProcessMerchantChangePasswordChatakMerchantException method"
							+ e);

		}
	}

	@Test
	public void testProcessMerchantChangePasswordException() throws ChatakMerchantException {
		Mockito.when(loginSevice.changdPassword(Matchers.anyLong(), Matchers.anyString(), Matchers.anyString()))
				.thenThrow(new NullPointerException());
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_MERCHANT_CHANGE_PSWD)
					.sessionAttr("loginUserId", Long.parseLong("2345")).param("confirmPassword", "newPassword")
					.param("newPassword", "newPassword"))
					.andExpect(view().name(URLMappingConstants.SHOW_MERCHANT_CHANGE_PSWD));
		} catch (NumberFormatException e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testProcessMerchantChangePasswordException method" + e);

		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testProcessMerchantChangePasswordException method" + e);

		}
	}

	@Test
	public void testShowMerchantEditProfile() throws ChatakMerchantException {
		UserProfileRequest userProfileRequest = new UserProfileRequest();
		MerchantProfile merchantProfile = new MerchantProfile();
		List<Option> countryList = new ArrayList<>();
		Option option = new Option();
		countryList.add(option);
		merchantProfile.setCountry("In");
		Response stateList = new Response();
		userProfileRequest.setMerchantId(Long.parseLong("344"));
		userProfileRequest.setMerchantUserId(Long.parseLong("534543"));
		Mockito.when(merchantProfileService.getMerchantProfile(Matchers.any(MerchantProfile.class)))
				.thenReturn(merchantProfile);
		Mockito.when(merchantProfileService.getCountries()).thenReturn(countryList);
		Mockito.when(merchantProfileService.getStatesByCountry(Matchers.anyString())).thenReturn(stateList);
		Mockito.when(loginSevice.getUserProfile(Matchers.anyLong())).thenReturn(userProfileRequest);
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_EDIT_PROFILE).sessionAttr("loginUserId",
					Long.parseLong("2345"))).andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_EDIT_PROFILE));
		} catch (NumberFormatException e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testShowMerchantEditProfile method" + e);

		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testShowMerchantEditProfile method" + e);

		}
	}

	@Test
	public void testShowMerchantEditProfileException() {
		try {
			mockMvc.perform(get("/" + URLMappingConstants.CHATAK_MERCHANT_EDIT_PROFILE).sessionAttr("loginUserId",
					Long.parseLong("2345"))).andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_EDIT_PROFILE));
		} catch (NumberFormatException e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testShowMerchantEditProfileException method" + e);

		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testShowMerchantEditProfileException method" + e);

		}
	}

	@Test
	public void testUpdateMerchantProfile() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_EDIT_PROFILE_PROCESS)
					.sessionAttr("loginUserId", Long.parseLong("2345")))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_EDIT_PROFILE));
		} catch (NumberFormatException e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testUpdateMerchantProfile method" + e);

		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testUpdateMerchantProfile method" + e);

		}
	}

	@Test
	public void testUpdateMerchantProfileChatakMerchantException() throws ChatakMerchantException {
		Mockito.when(merchantProfileService.updateMerchantProfile(Matchers.any(MerchantProfile.class)))
				.thenThrow(ChatakMerchantException.class);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_EDIT_PROFILE_PROCESS)
					.sessionAttr("loginUserId", Long.parseLong("2345")))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_EDIT_PROFILE));
		} catch (NumberFormatException e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testUpdateMerchantProfileChatakMerchantException method"
					+ e);

		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testUpdateMerchantProfileChatakMerchantException method"
					+ e);

		}

	}

	@Test
	public void testUpdateMerchantProfileException() throws ChatakMerchantException {
		Mockito.when(merchantProfileService.updateMerchantProfile(Matchers.any(MerchantProfile.class)))
				.thenThrow(new NullPointerException());
		try {
			mockMvc.perform(post("/" + URLMappingConstants.CHATAK_MERCHANT_EDIT_PROFILE_PROCESS)
					.sessionAttr("loginUserId", Long.parseLong("2345")))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_EDIT_PROFILE));
		} catch (NumberFormatException e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testUpdateMerchantProfileException method" + e);

		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testUpdateMerchantProfileException method" + e);

		}
	}

	@Test
	public void testShowMerchantResetPassword() {
		try {
			mockMvc.perform(get("/" + URLMappingConstants.SHOW_MERCHANT_RESET_PSWD)
					.sessionAttr("loginUserId", Long.parseLong("2345")).param("t", "5189552984989"))
					.andExpect(view().name(URLMappingConstants.INVALID_TOKEN));
		} catch (NumberFormatException e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testShowMerchantResetPassword method" + e);

		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testShowMerchantResetPassword method" + e);

		}
	}

	@Test
	public void testShowMerchantResetPasswordException() {
		try {
			mockMvc.perform(get("/" + URLMappingConstants.SHOW_MERCHANT_RESET_PSWD)
					.sessionAttr("loginUserId", Long.parseLong("2345")).param("t", "t"))
					.andExpect(view().name(URLMappingConstants.INVALID_TOKEN));
		} catch (NumberFormatException e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testShowMerchantResetPasswordException method" + e);

		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testShowMerchantResetPasswordException method" + e);

		}
	}

	@Test
	public void testShowMerchantResetPasswordParam() {
		try {
			mockMvc.perform(get("/" + URLMappingConstants.SHOW_MERCHANT_RESET_PSWD).sessionAttr("loginUserId",
					Long.parseLong("2345"))).andExpect(view().name(URLMappingConstants.INVALID_TOKEN));
		} catch (NumberFormatException e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testShowMerchantResetPasswordParam method" + e);

		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testShowMerchantResetPasswordParam method" + e);

		}

	}

	@Test
	public void testProcessMerchantResetPassword() throws ChatakMerchantException {
		Mockito.when(loginSevice.validToken(Matchers.anyLong(), Matchers.anyString())).thenReturn(true);
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_MERCHANT_RESET_PSWD)
					.sessionAttr(Constants.RESET_USER_ID, Constants.RESET_USER_ID)
					.param("confirmPassword", "confirmPassword").param("newPassword", "newPassword"))
					.andExpect(view().name(URLMappingConstants.PROCESS_MERCHANT_RESET_PSWD));
		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testProcessMerchantResetPassword method" + e);

		}
	}

	@Test
	public void testProcessMerchantResetPasswordSession() {
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("hello");
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_MERCHANT_RESET_PSWD)
					.sessionAttr(Constants.RESET_USER_ID, "243").param("confirmPassword", "newPassword")
					.param("newPassword", "newPassword"))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_LOGIN));
		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testProcessMerchantResetPasswordSession method" + e);

		}
	}

	@Test
	public void testProcessMerchantResetPasswordException() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.PROCESS_MERCHANT_RESET_PSWD).sessionAttr("loginUserId",
					Long.parseLong("2345")));
		} catch (NumberFormatException e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testProcessMerchantResetPasswordException method" + e);

		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testProcessMerchantResetPasswordException method" + e);

		}
	}

	@Test
	public void testChangePasswordForNewUsersHeader() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.NEW_USER_PSWD_MANAGEMENT).sessionAttr("loginUserId",
					Long.parseLong("2345"))).andExpect(view().name(URLMappingConstants.INVALID_REQUEST_PAGE));
		} catch (NumberFormatException e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testChangePasswordForNewUsersHeader method" + e);

		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testChangePasswordForNewUsersHeader method" + e);

		}
	}

	@Test
	public void testChangePasswordForNewUsers() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.NEW_USER_PSWD_MANAGEMENT)
					.sessionAttr("loginUserId", Long.parseLong("2345")).param("confirmPassword", "confirmPassword")
					.param("newPassword", "confirmPassword").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.CHATAK_MERCHANT_LOGIN));
		} catch (NumberFormatException e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testChangePasswordForNewUsers method" + e);

		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testChangePasswordForNewUsers method" + e);

		}
	}

	@Test
	public void testChangePasswordForNewUsersElse() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.NEW_USER_PSWD_MANAGEMENT)
					.sessionAttr("loginUserId", "loginUserId").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.NEW_USER_PSWD_MANAGEMENT));
		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testChangePasswordForNewUsersElse method" + e);

		}
	}

	@Test
	public void testChangePasswordForNewUsersException() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.NEW_USER_PSWD_MANAGEMENT)
					.sessionAttr("loginUserId", "loginUserId").param("confirmPassword", "confirmPassword")
					.param("newPassword", "confirmPassword").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.NEW_USER_PSWD_MANAGEMENT));
		} catch (Exception e) {
			logger.info("Exit:: MerchantProfileControllerTest:: testChangePasswordForNewUsersException method" + e);

		}
	}

	@Test
	public void testChangePasswordForNewUsersConfirmPassword() {
		try {
			mockMvc.perform(post("/" + URLMappingConstants.NEW_USER_PSWD_MANAGEMENT)
					.sessionAttr("loginUserId", Long.parseLong("2345")).param("confirmPassword", "confirmPassword")
					.param("newPassword", "newPassword").header(Constants.REFERER, Constants.REFERER))
					.andExpect(view().name(URLMappingConstants.NEW_USER_PSWD_MANAGEMENT));
		} catch (NumberFormatException e) {
			logger.info(
					"Exit:: MerchantProfileControllerTest:: testChangePasswordForNewUsersConfirmPassword method" + e);

		} catch (Exception e) {
			logger.info(
					"Exit:: MerchantProfileControllerTest:: testChangePasswordForNewUsersConfirmPassword method" + e);

		}
	}

}
