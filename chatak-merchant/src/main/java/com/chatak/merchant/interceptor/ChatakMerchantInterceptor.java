package com.chatak.merchant.interceptor;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.controller.model.LoginDetails;
import com.chatak.merchant.util.PasswordHandler;
import com.chatak.merchant.util.StringUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

public class ChatakMerchantInterceptor extends HandlerInterceptorAdapter implements Serializable {
  private static final long serialVersionUID = -5306853636280146748L;
  private static Logger logger = Logger.getLogger(ChatakMerchantInterceptor.class);
  @Autowired
  private SessionRegistryImpl sessionRegistry;

  public boolean preHandle(javax.servlet.http.HttpServletRequest request,
      javax.servlet.http.HttpServletResponse response, java.lang.Object object)
          throws java.lang.Exception {
    boolean isDone = false;

    if (null != request) {
      isDone = isValidRequest(request.getRequestURI());
      if (isDone) {
        // DO nothing
      } else {
        final HttpSession session = request.getSession(false);
        if (null != session) {
          isDone = checkUserRegistry(request, response, session);
        }
        if (!isDone) {
          response.sendRedirect(
              request.getContextPath() + Constants.URL_SPERATOR + Constants.CHATAK_INVALID_SESSION);
        }
      }
    }

    return isDone;
  }

  private boolean isValidRequest(final String requestURI) {
    boolean isDone = false;
    if (StringUtil.isNullAndEmpty(requestURI)) {
      isDone = true;
    } else if (requestURI.contains(URLMappingConstants.CHATAK_MERCHANT_LOGIN)
        || requestURI.contains(Constants.CHATAK_INVALID_SESSION)
        || requestURI.contains(Constants.CHATAK_INVALID_ACCESS)
        || requestURI.contains(URLMappingConstants.CHATAK_MERCHANT_LOG_OUT)
        || requestURI.contains(URLMappingConstants.SHOW_MERCHANT_FORGOT_PSWD)
        || requestURI.contains(URLMappingConstants.PROCESS_MERCHANT_RESET_PSWD)
        || requestURI.contains(URLMappingConstants.NEW_USER_PSWD_MANAGEMENT)
        || requestURI.contains(URLMappingConstants.SHOW_MERCHANT_RESET_PSWD)
        || requestURI.contains(URLMappingConstants.CHATAK_MERCHANT_AUTHENTICATE)) {
      isDone = true;
    }

    return isDone;
  }

  private boolean checkUserRegistry(javax.servlet.http.HttpServletRequest request,
      javax.servlet.http.HttpServletResponse response, HttpSession session) {

    String userName = (String) session.getAttribute(URLMappingConstants.CHATAK_ADMIN_USER_NAME);
    if (!StringUtil.isNullAndEmpty(userName)) {

      for (Object object : sessionRegistry.getAllPrincipals()) {
        LoginDetails loginResponseData = (LoginDetails) object;
        if (isValidLoginResponse(userName, loginResponseData)) {
          return validateSessionRequestAndInformation(request, response, session, loginResponseData, userName);
          }
      }
    } else {
      Cookie myCookie = new Cookie(Constants.COOKIE_CHATAK_NAME, null);
      myCookie.setMaxAge(0);
      response.addCookie(myCookie);
      try {
        response.sendRedirect(
            request.getContextPath() + Constants.URL_SPERATOR + Constants.CHATAK_INVALID_SESSION);
      } catch (Exception e) {
    	  logger.error("ERROR :: ChatakMerchantInterceptor :: checkUserRegistry method", e);
      }
      return true;
    }
    return true;
  }
  
  private boolean validateSessionRequestAndInformation(HttpServletRequest request, HttpServletResponse response, HttpSession session,
      LoginDetails loginResponseData, String userName) {

    String userAgent = getUserAgentData(request);
    String encUName = PasswordHandler.encrypt(userName);
    Cookie[] cookies = request.getCookies();
    String cookieValue = "";
    cookieValue = gerCookiesResponse(response, cookies, cookieValue);

    SessionInformation sessionInformation = sessionRegistry.getSessionInformation(encUName);

    if (null == sessionInformation) {
      return false;
    } else {
      sessionInformation.refreshLastRequest();
      if (!loginResponseData.getjSession().equals(userAgent + cookieValue)) {
        Date lastSessionRequestDate = sessionInformation.getLastRequest();
        Date lastRequestDate = new Date(lastSessionRequestDate.getTime());
        lastRequestDate.setTime(lastRequestDate.getTime()
            + (Integer.parseInt(Properties.getProperty("chatak.merchant.session.timeout"))
                * Constants.TIME_OUT));
        Date curDate = new Date();
        if (lastRequestDate.after(curDate)) {
          Cookie myCookie = new Cookie(Constants.COOKIE_CHATAK_NAME, null);
          myCookie.setMaxAge(0);
          response.addCookie(myCookie);
          sessionRegistry.removeSessionInformation(encUName);
          session.setAttribute(URLMappingConstants.CHATAK_ADMIN_USER_NAME, null);
          validateContextPath(request, response);
          return false;
        }
      }
      sessionInformation.refreshLastRequest();
    }
    return true;
  
  }

  private void validateContextPath(HttpServletRequest request, HttpServletResponse response) {
    try {
      response.sendRedirect(request.getContextPath() + Constants.URL_SPERATOR
          + Constants.CHATAK_INVALID_ACCESS);
    } catch (IOException e) {
      logger.error("ChatakMerchantInterceptor| checkUserRegistry " + e);
    }
  }

  private boolean isValidLoginResponse(String userName, LoginDetails loginResponseData) {
    return null != loginResponseData && loginResponseData.getAcqU().trim().equals(userName);
  }

  private String getUserAgentData(javax.servlet.http.HttpServletRequest request) {
    String userAgent = request.getHeader("user-agent");
    if (null != userAgent) {
      userAgent = userAgent.replaceAll("\\ ", "");
    }
    return userAgent;
  }

  private String gerCookiesResponse(javax.servlet.http.HttpServletResponse response,
      Cookie[] cookies, String cookieValue) {
    for (Cookie cookie : cookies) {
      if (Constants.COOKIE_CHATAK_NAME.equalsIgnoreCase(cookie.getName())) {
        cookieValue = cookie.getValue();

        // Resetting Logged in user encrypted data into
        // Cookie
        cookie.setMaxAge(Constants.FIFTEEN * Constants.SIXTY);
        response.addCookie(cookie);
        break;
      }
    }
    return cookieValue;
  }
}
