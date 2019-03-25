package com.chatak.acquirer.admin.interceptor;

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

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.acquirer.admin.util.PasswordHandler;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.validator.CSRFTokenManager;

public class ChatakAcqInterceptor extends HandlerInterceptorAdapter implements Serializable {

  private static final long serialVersionUID = -5306853636280146778L;

  private static Logger logger = Logger.getLogger(ChatakAcqInterceptor.class);

  @Autowired
  private transient SessionRegistryImpl sessionRegistry;

  @Override
  public boolean preHandle(javax.servlet.http.HttpServletRequest request,
      javax.servlet.http.HttpServletResponse response, java.lang.Object object)
          throws java.lang.Exception {
    boolean isDone = false;
    if (!request.getMethod().equalsIgnoreCase("POST")) {
        // Not a POST - allow the request           
        // Valid request        
      } else {
			// This is a POST request - need to check the CSRF token
			String sessionToken = CSRFTokenManager.getTokenForSession(request.getSession());
			String requestToken = CSRFTokenManager.getTokenFromRequest(request);
			if (sessionToken.equals(requestToken)) {
				// Valid request

			} else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bad or missing CSRF value");
				return false;
			}
		}
    
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
              request.getContextPath() + Constants.ADMIN_URL_SPERATOR  + Constants.CHATAK_INVALID_SESSION);
        }
      }
    return isDone;
  }

  private boolean isValidRequest(final String requestURI) {
    boolean isDone = false;
    if (requestURI.contains(URLMappingConstants.CHATAK_ADMIN_LOGIN)
        || requestURI.contains(Constants.CHATAK_INVALID_SESSION)
        || requestURI.contains(Constants.CHATAK_INVALID_ACCESS)
        || requestURI.contains(URLMappingConstants.CHATAK_ADMIN_AUTHENTICATE)
        || requestURI.contains(URLMappingConstants.CHATAK_ADMIN_LOG_OUT)
        || requestURI.contains(URLMappingConstants.CHATAK_ADMIN_CHANGE_PSWD)
        || requestURI.contains(URLMappingConstants.FORGOT_PSWD)
        || requestURI.contains(URLMappingConstants.PSWD_RESET)
        || requestURI.contains(URLMappingConstants.RESET_PSWD)
        || requestURI.contains(URLMappingConstants.INVALID_REQUEST_PAGE)
        || StringUtil.isNullAndEmpty(requestURI)) {
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
        if (null != loginResponseData && loginResponseData.getAcqU().trim().equals(userName)) {
          return validateSessionInformation(request, response, session, userName, loginResponseData);
        }
      }
    } else {
      return invalidSession(response);
    }
    return true;
  }
  
  private boolean validateSessionInformation(HttpServletRequest request,
      HttpServletResponse response, HttpSession session, String userName,LoginDetails loginResponseData) {
    String userAgent = getUserAgent(request);
    String encUName = PasswordHandler.encrypt(userName);
    String cookieValue = getCookiesInformation(request, response);

    SessionInformation sessionInformation = sessionRegistry.getSessionInformation(encUName);
    if (null == sessionInformation) {
      return false;
    } else {
      sessionInformation.refreshLastRequest();
      if (!loginResponseData.getjSession().equals(userAgent + cookieValue)) {
        Date curDate = new Date();
        Date lastSessionRequestDate = sessionInformation.getLastRequest();
        Date lastRequestDate = new Date(lastSessionRequestDate.getTime());
        lastRequestDate.setTime(lastRequestDate.getTime()
            + (Integer.parseInt(Properties.getProperty("chatak.admin.session.timeout"))
                * Constants.TIME_OUT));
        if (lastRequestDate.after(curDate)) {
          Cookie myCookie = new Cookie(Constants.COOKIE_CHATAK_NAME, null);
          myCookie.setHttpOnly(true);
          myCookie.setMaxAge(0);
          response.addCookie(myCookie);
          sessionRegistry.removeSessionInformation(encUName);
          session.setAttribute(URLMappingConstants.CHATAK_ADMIN_USER_NAME, null);
          getResponseRedirectURL(request, response);
          return false;
        }
      }
      sessionInformation.refreshLastRequest();
    }
    return true;
  }

  private void getResponseRedirectURL(javax.servlet.http.HttpServletRequest request,
      javax.servlet.http.HttpServletResponse response) {
    try {
      response.sendRedirect(request.getContextPath() + Constants.URL_SPERATOR
          + Constants.CHATAK_INVALID_ACCESS);
    } catch (IOException e) {
      logger.error("ChatakAcqInterceptor | checkUserRegistry " + e);
    }
  }

  private String getUserAgent(javax.servlet.http.HttpServletRequest request) {
    String userAgent = request.getHeader("user-agent");
    if (null != userAgent) {
      userAgent = userAgent.replaceAll("\\ ", "");
    }
    return userAgent;
  }

  private boolean invalidSession(javax.servlet.http.HttpServletResponse response) {
    Cookie myCookie = new Cookie(Constants.COOKIE_CHATAK_NAME, null);
    myCookie.setHttpOnly(true);
    myCookie.setMaxAge(0);
    response.addCookie(myCookie);
    try {
    	response.sendRedirect(URLMappingConstants.INVALID_REQUEST_PAGE);
    } catch (Exception e) {
      logger.error("Error :: ChatakAcqInterceptor :: checkUserRegistry", e);
    }
    return true;
  }

  private String getCookiesInformation(javax.servlet.http.HttpServletRequest request,
      javax.servlet.http.HttpServletResponse response) {
    Cookie[] cookies = request.getCookies();
    String cookieValue = "";
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
