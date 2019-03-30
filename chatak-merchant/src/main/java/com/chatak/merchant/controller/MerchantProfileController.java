package com.chatak.merchant.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.controller.model.ChangePasswordRequest;
import com.chatak.merchant.controller.model.ForgotPasswordRequest;
import com.chatak.merchant.controller.model.LoginDetails;
import com.chatak.merchant.controller.model.MerchantProfile;
import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.controller.model.ResetPasswordData;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.UserProfileRequest;
import com.chatak.merchant.service.AccountService;
import com.chatak.merchant.service.LoginService;
import com.chatak.merchant.service.MerchantProfileService;
import com.chatak.merchant.util.PasswordHandler;
import com.chatak.merchant.util.StringUtil;
import com.chatak.pg.bean.Response;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.validator.CSRFTokenManager;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
public class MerchantProfileController implements URLMappingConstants {

  private Logger logger = Logger.getLogger(MerchantProfileController.class);

  @Autowired
  AccountService accountService;

  @Autowired
  private LoginService loginSevice;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private SessionRegistryImpl sessionRegistry;

  @Autowired
  MerchantProfileService merchantProfileService;
  
  @Autowired
  LoginService loginService;

  @RequestMapping(value = SHOW_MERCHANT_FORGOT_PSWD, method = RequestMethod.GET)
  public ModelAndView showMerchantForgotPassword(Map model, HttpServletRequest request) {
    logger.info("Entering:: LoginController:: forgotPassword method");
    ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
    model.put("forgotPasswordRequest", forgotPasswordRequest);
    ModelAndView modelAndView = new ModelAndView(SHOW_MERCHANT_FORGOT_PSWD);
    logger.info("Exit:: LoginController:: forgotPassword method");

    return modelAndView;
  }

  @RequestMapping(value = PROCESS_MERCHANT_FORGOT_PSWD, method = RequestMethod.POST)
  public ModelAndView processMerchantForgotPassword(Map model, HttpServletRequest request,
      ForgotPasswordRequest forgotPasswordRequest) {
    logger.info("Entering:: LoginController:: submiPassword method");
    ModelAndView modelAndView = null;
    if (!StringUtil.isNullAndEmpty(forgotPasswordRequest.getUserName())) {
      try {
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        String ctx = request.getContextPath();
        String baseUrl = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
        loginSevice.userExist(forgotPasswordRequest.getUserName(), baseUrl);
        model.put(Constants.SUCESS, messageSource.getMessage("chatak.merchant.user.message", null,
            LocaleContextHolder.getLocale()));
        model.put(Constants.LOGIN_DETAILS, new LoginDetails());
        modelAndView = new ModelAndView(CHATAK_MERCHANT_LOGIN);
      } catch (ChatakMerchantException e) {
        logger.error("Error:: LoginController:: submiPassword method2" + e);
        modelAndView = new ModelAndView(SHOW_MERCHANT_FORGOT_PSWD);
        model.put(Constants.ERROR, e.getMessage());
      } catch (Exception e) {
        logger.error("Error:: LoginController:: submiPassword method2" + e);
        modelAndView = new ModelAndView(SHOW_MERCHANT_FORGOT_PSWD);
        model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
            LocaleContextHolder.getLocale()));
      }
    } else {
      modelAndView = new ModelAndView(SHOW_MERCHANT_FORGOT_PSWD);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_INVALIDINPUT_ERROR, null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: LoginController:: submiPassword method");
    return modelAndView;

  }

  @RequestMapping(value = SHOW_MERCHANT_CHANGE_PSWD, method = RequestMethod.GET)
  public ModelAndView showMerchantChangePassword(Map model, HttpServletRequest request,
      HttpSession session) {
    logger.info("Entering:: LoginController:: getResetPassword method");

    // Check if user directly hit the URL from the browser
    Long userId = (Long) session.getAttribute(Constants.LOING_USER_ID);
    if (userId == null) {
      ModelAndView modelAndView = new ModelAndView(Constants.CHATAK_INVALID_SESSION);
      session.invalidate();
      return modelAndView;
    }

    ModelAndView modelAndView = new ModelAndView(SHOW_MERCHANT_CHANGE_PSWD);
    modelAndView.addObject("changePasswordData", new ChangePasswordRequest());
    logger.info("Exit:: LoginController:: getResetPassword method");
    return modelAndView;
  }

  @RequestMapping(value = PROCESS_MERCHANT_CHANGE_PSWD, method = RequestMethod.POST)
  public ModelAndView processMerchantChangePassword(Map model, HttpServletRequest request,
      HttpServletResponse response, ChangePasswordRequest changePasswordRequest,
      HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(SHOW_MERCHANT_CHANGE_PSWD);
    if (!loginService.checkUserActive(session)) {
      model.put(Constants.ERROR, messageSource.getMessage("user.has.been.inactivated", null,
          LocaleContextHolder.getLocale()));
      session.invalidate();
      return new ModelAndView(CHATAK_MERCHANT_LOG_OUT);
    }
    if (!((StringUtil.isNullAndEmpty(changePasswordRequest.getConfirmPassword()))
        || (StringUtil.isNullAndEmpty(changePasswordRequest.getNewPassword())))) {

      try {

        if (!changePasswordRequest.getNewPassword()
            .equals(changePasswordRequest.getConfirmPassword())) {
          throw new ChatakMerchantException(
              messageSource.getMessage(Constants.CHANGE_PASS_WORD_LABEL_CONFIRM_PSWD_ERROR, null,
                  LocaleContextHolder.getLocale()));
        }

        Long userId = (Long) session.getAttribute(Constants.LOING_USER_ID);
        loginSevice.changdPassword(userId, changePasswordRequest.getCurrentPassword(),
            changePasswordRequest.getNewPassword());
        model.put(Constants.SUCESS, messageSource.getMessage("change.password.error.message", null,
            LocaleContextHolder.getLocale()));
        resetCookie(request, response, session);
        modelAndView.setViewName(CHATAK_MERCHANT_LOGIN);
        LoginDetails loginDetails = new LoginDetails();
        model.put(Constants.LOGIN_DETAILS, loginDetails);

      } catch (ChatakMerchantException e) {
        logger.error(" LoginController:: changePasswordData method1" + e);
        model.put(Constants.ERROR, e.getMessage());
      } catch (Exception exp) {
        logger.error(" LoginController:: changePasswordData method2" + exp);
        model.put(Constants.ERROR, exp.getMessage());
      }
      logger.info("Exit:: LoginController:: changePasswordData method");
    } else {
      logger.error(" LoginController:: changePasswordData empty input error");
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_INVALIDINPUT_ERROR, null,
          LocaleContextHolder.getLocale()));
    }
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_EDIT_PROFILE, method = RequestMethod.GET)
  public ModelAndView showMerchantEditProfile(Map model, HttpServletRequest request,
      HttpSession session) {
    logger.info("Entering:: LoginController:: myProfile method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_EDIT_PROFILE);
    MerchantProfile merchantProfile = new MerchantProfile();

    try {
      Long userId = (Long) session.getAttribute(Constants.LOING_USER_ID);

      UserProfileRequest userProfileRequest = loginSevice.getUserProfile(userId);
      model.put(Constants.USER_PROFILE_REQUEST, userProfileRequest);
      merchantProfile.setId(userProfileRequest.getMerchantId());
      merchantProfile.setMerchantUserId(userProfileRequest.getMerchantUserId().toString());
      merchantProfile = merchantProfileService.getMerchantProfile(merchantProfile);
      model.put(Constants.MERCHANT_PROFILE, merchantProfile);
      List<Option> countryList = merchantProfileService.getCountries();
      modelAndView.addObject("countryList", countryList);
      session.setAttribute("countryList", new ArrayList(countryList));
      Response stateList = merchantProfileService.getStatesByCountry(merchantProfile.getCountry());
      modelAndView.addObject("stateList", stateList.getResponseList());
      session.setAttribute("stateList", new ArrayList(stateList.getResponseList()));
    } catch (Exception e) {
      logger.error("Error:: LoginController:: myProfile method", e);
      model.put(Constants.ERROR, messageSource.getMessage("chatak.normal.error.message", null,
          LocaleContextHolder.getLocale()));
      model.put(Constants.MERCHANT_PROFILE, merchantProfile);
    }
    logger.info("Exit:: LoginController:: myProfile method");
    return modelAndView;
  }

  @RequestMapping(value = CHATAK_MERCHANT_EDIT_PROFILE_PROCESS, method = RequestMethod.POST)
  public ModelAndView updateMerchantProfile(Map model, HttpServletRequest request,
      HttpServletResponse response, UserProfileRequest userProfileRequest,
      MerchantProfile merchantProfile, HttpSession session) {
    logger.info("Entering:: LoginController:: updateUserProfile method");
    ModelAndView modelAndView = new ModelAndView(CHATAK_MERCHANT_EDIT_PROFILE);


    try {
      merchantProfileService.updateMerchantProfile(merchantProfile);

      model.put(Constants.SUCESS, messageSource.getMessage(
          "chatak.changeProfile.request.error.message", null, LocaleContextHolder.getLocale()));
    } catch (ChatakMerchantException e) {
      logger.error("Error:: LoginController:: updateUserProfile method" + e);
      model.put(Constants.USER_PROFILE_REQUEST, userProfileRequest);
      model.put(Constants.MERCHANT_PROFILE, merchantProfile);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      model.put(Constants.USER_PROFILE_REQUEST, userProfileRequest);
      model.put(Constants.MERCHANT_PROFILE, merchantProfile);
      logger.error("Error:: LoginController:: updateUserProfile method2" + e);
      model.put(Constants.ERROR, Properties.getProperty("chatak.normal.error.message"));
    }
    logger.info("Exit:: LoginController:: updateUserProfile method");
    return modelAndView;
  }

  @RequestMapping(value = SHOW_MERCHANT_RESET_PSWD, method = RequestMethod.GET)
  public ModelAndView showMerchantResetPassword(Map model, HttpServletRequest request,
      HttpSession session, @FormParam("t") final String t) {
    logger.info("Entering:: LoginController:: getResetPassword method");
    ModelAndView modelAndView = new ModelAndView(INVALID_TOKEN);

    String[] paramsArray = null;
    try {

      if (StringUtil.isNullAndEmpty(t)) {
        modelAndView.addObject(Constants.TOKEN_ERROR,
            Properties.getProperty("chatak.admin.invalid.token"));
        return modelAndView;
      }

      paramsArray = StringUtil.parseEmailToken(t);
      String userId = paramsArray[Constants.ONE];
      String email = paramsArray[Constants.TWO];
      if (!loginSevice.validToken(Long.parseLong(userId), t))
        throw new ChatakMerchantException(messageSource.getMessage("chatak.merchant.invalid.token",
            null, LocaleContextHolder.getLocale()));
      session.setAttribute(Constants.RESET_USER_ID, userId);
      session.setAttribute(Constants.RESET_EMAIL, email);
      ResetPasswordData resetPasswordData = new ResetPasswordData();
      model.put("resetPasswordData", resetPasswordData);
      modelAndView.setViewName(PROCESS_MERCHANT_RESET_PSWD);
    } catch (ChatakMerchantException e) {
      logger.error("Error:: LoginController:: getResetPassword method1" + e);
      model.put(Constants.ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("Error:: LoginController:: getResetPassword method2" + e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: LoginController:: getResetPassword method");
    return modelAndView;
  }

  @RequestMapping(value = PROCESS_MERCHANT_RESET_PSWD, method = RequestMethod.POST)
  public ModelAndView processMerchantResetPassword(Map model, HttpServletRequest request,
      HttpSession session, ResetPasswordData resetPasswordData) {
    logger.info("Entering:: LoginController:: resetPassword method");
    ModelAndView modelAndView = null;
    try {
      if (!resetPasswordData.getNewPassword().equals(resetPasswordData.getConfirmPassword())) {
        throw new ChatakMerchantException(
            messageSource.getMessage(Constants.CHANGE_PASS_WORD_LABEL_CONFIRM_PSWD_ERROR, null,
                LocaleContextHolder.getLocale()));

      }

      String userId = (String) session.getAttribute(Constants.RESET_USER_ID);
      loginSevice.resetPassword(resetPasswordData, Long.parseLong(userId));
      modelAndView = new ModelAndView(CHATAK_MERCHANT_LOGIN);
      model.put(Constants.LOGIN_DETAILS, new LoginDetails());
      model.put(Constants.SUCESS, messageSource.getMessage("chatak.merchant.reset.password.message",
          null, LocaleContextHolder.getLocale()));
    }

    catch (ChatakMerchantException e) {
      logger.error("Error:: LoginController:: resetPassword method1" + e);
      modelAndView = new ModelAndView(PROCESS_MERCHANT_RESET_PSWD);
      model.put(Constants.ERROR, e.getMessage());

    } catch (Exception e) {
      logger.error("Error:: LoginController:: resetPassword method2" + e);
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exit:: LoginController:: resetPassword method");
    return modelAndView;
  }

  @RequestMapping(value = NEW_USER_PSWD_MANAGEMENT, method = RequestMethod.POST)
  public ModelAndView changePasswordForNewUsers(Map model, HttpServletRequest request,
      HttpServletResponse response, ChangePasswordRequest changePasswordRequest,
      HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(NEW_USER_PSWD_MANAGEMENT);

    if (request.getHeader("referer") == null) {
      session.invalidate();
      modelAndView.setViewName(INVALID_REQUEST_PAGE);
      return modelAndView;
    }

    if (!StringUtil.isNullAndEmpty(changePasswordRequest.getConfirmPassword())
        && !StringUtil.isNullAndEmpty(changePasswordRequest.getNewPassword())) {

      try {

        if (!changePasswordRequest.getNewPassword()
            .equals(changePasswordRequest.getConfirmPassword())) {
          throw new ChatakMerchantException(
              messageSource.getMessage(Constants.CHANGE_PASS_WORD_LABEL_CONFIRM_PSWD_ERROR, null,
                  LocaleContextHolder.getLocale()));

        }

        Long userId = (Long) session.getAttribute(Constants.LOING_USER_ID);
        loginSevice.changdPassword(userId, changePasswordRequest.getCurrentPassword(),
            changePasswordRequest.getNewPassword());
        session.invalidate();
        HttpSession newsession = request.getSession();
        String token = CSRFTokenManager.getTokenForSession(newsession);
        newsession.setAttribute("tokenval", token);
        modelAndView.setViewName(CHATAK_MERCHANT_LOGIN);
        LoginDetails loginDetails = new LoginDetails();
        modelAndView.addObject(Constants.SUCESS, messageSource
            .getMessage("change.password.error.message", null, LocaleContextHolder.getLocale()));
        model.put(Constants.LOGIN_DETAILS, loginDetails);

      } catch (ChatakMerchantException e) {
        logger.error(" LoginController:: changePasswordData method1" + e);
        model.put(Constants.ERROR, e.getMessage());
      } catch (Exception e) {
        logger.error(" LoginController:: changePasswordData method2" + e);
        model.put(Constants.ERROR, e.getMessage());
      }
      logger.info("Exit:: LoginController:: changePasswordData method");
    } else {
      logger.error(" LoginController:: changePasswordData empty input error");
      model.put(Constants.ERROR, messageSource.getMessage(Constants.CHATAK_INVALIDINPUT_ERROR, null,
          LocaleContextHolder.getLocale()));
    }
    return modelAndView;
  }

  private void resetCookie(HttpServletRequest request, HttpServletResponse response,
      HttpSession session) {
    String encUName =
        PasswordHandler.encrypt((String) session.getAttribute(CHATAK_ADMIN_USER_NAME));
    sessionRegistry.removeSessionInformation(encUName);
    String cookieValue = StringUtil.getCookieValue(request);
    sessionRegistry.removeSessionInformation(cookieValue);
    Cookie myCookie = new Cookie(Constants.COOKIE_CHATAK_NAME, null);
    myCookie.setHttpOnly(true);
    myCookie.setMaxAge(0);
    response.addCookie(myCookie);
  }
}
