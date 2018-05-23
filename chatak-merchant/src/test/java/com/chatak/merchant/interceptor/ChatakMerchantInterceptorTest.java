package com.chatak.merchant.interceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chatak.merchant.constants.URLMappingConstants;
import com.chatak.merchant.controller.model.LoginDetails;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class ChatakMerchantInterceptorTest {

  private static Logger logger = Logger.getLogger(ChatakMerchantInterceptorTest.class);

  @InjectMocks
  ChatakMerchantInterceptor chatakMerchantInterceptor = new ChatakMerchantInterceptor();
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  Object handler;

  @Mock
  Object object;

  @Mock
  HttpSession session;

  @Mock
  private SessionRegistryImpl sessionRegistry;

  @Mock
  LoginDetails loginDetails;

  @Mock
  List<Object> objectList;


  @Mock
  SessionInformation sessionInformation;

  @Mock
  Date date;

  @Before
  public void setup() {
    loginDetails = new LoginDetails();
    objectList = new ArrayList<>();
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
  }

  @Before
  public void pro() {
    java.util.Properties properties = new java.util.Properties();
    properties.setProperty("chatak.merchant.session.timeout", "10");
    Properties.mergeProperties(properties);
  }

  @Test
  public void testPreHandle() {
    MockHttpServletRequest requestMock = new MockHttpServletRequest();
    requestMock.setRequestURI("authenticate");
    chatakMerchantInterceptor = new ChatakMerchantInterceptor();
    try {
      chatakMerchantInterceptor.preHandle(request, response, object);
    } catch (Exception e) {
      logger.error("ERROR:: ChatakMerchantInterceptorTest::testPreHandle ", e);
    }
  }

  @Test
  public void testPreHandleElse() {
    MockHttpServletRequest requestMock = new MockHttpServletRequest();
    requestMock.setRequestURI(null);
    chatakMerchantInterceptor = new ChatakMerchantInterceptor();
    try {
      chatakMerchantInterceptor.preHandle(request, response, object);
    } catch (Exception e) {
      logger.error("ERROR:: ChatakMerchantInterceptorTest::testPreHandleElse ", e);
    }
  }

  @Test
  public void testPreHandleUri() {
    MockHttpSession mockHttpSession = new MockHttpSession();
    mockHttpSession.setAttribute("session", object);
    MockHttpServletRequest requestMock = new MockHttpServletRequest();
    requestMock.setRequestURI(Constants.MERCHANT);
    chatakMerchantInterceptor = new ChatakMerchantInterceptor();
    try {
      chatakMerchantInterceptor.preHandle(request, response, object);
    } catch (Exception e) {
      logger.error("ERROR:: ChatakMerchantInterceptorTest::testPreHandleUri ", e);
    }
  }

  @Test
  public void testPreHandleSession() {
    Cookie cookie = new Cookie(Constants.COOKIE_CHATAK_NAME, Constants.COOKIE_CHATAK_NAME);
    Cookie[] cookies = {cookie};
    objectList = new ArrayList<>();
    loginDetails = new LoginDetails();
    String username = Constants.MERCHANT;
    loginDetails.setAcqU(Constants.MERCHANT);
    loginDetails.setjSession("session");
    loginDetails.setAcqP("chatak");
    objectList.add(loginDetails);
    Mockito.when(request.getRequestURI()).thenReturn("list");
    Mockito.when(request.getSession(false)).thenReturn(session);
    Mockito.when(sessionInformation.getLastRequest()).thenReturn(date);
    Mockito.when(request.getHeader("user-agent")).thenReturn(username);
    Mockito.when(sessionRegistry.getSessionInformation(Matchers.anyString()))
        .thenReturn(sessionInformation);
    Mockito.when(sessionRegistry.getAllPrincipals()).thenReturn(objectList);
    Mockito.when(request.getCookies()).thenReturn(cookies);
    Mockito.when(session.getAttribute(URLMappingConstants.CHATAK_ADMIN_USER_NAME))
        .thenReturn(username);

    try {
      chatakMerchantInterceptor.preHandle(request, response, object);
    } catch (Exception e) {
      logger.error("ERROR:: ChatakMerchantInterceptorTest::testPreHandleSession ", e);
    }
  }

  @Test
  public void testPreHandleSessionElse() {
    objectList = new ArrayList<>();
    loginDetails = new LoginDetails();
    String username = "";
    Cookie[] cookies = {};
    loginDetails.setAcqP("chatak");
    loginDetails.setAcqU(Constants.MERCHANT);
    loginDetails.setjSession("session");
    objectList.add(loginDetails);
    Mockito.when(sessionInformation.getLastRequest()).thenReturn(date);
    Mockito.when(request.getHeader("user-agent")).thenReturn(username);
    Mockito.when(request.getRequestURI()).thenReturn("list");
    Mockito.when(request.getSession(false)).thenReturn(session);
    Mockito.when(sessionRegistry.getAllPrincipals()).thenReturn(objectList);
    Mockito.when(session.getAttribute(URLMappingConstants.CHATAK_ADMIN_USER_NAME))
        .thenReturn(username);
    Mockito.when(sessionRegistry.getSessionInformation(Matchers.anyString()))
        .thenReturn(sessionInformation);
    Mockito.when(request.getCookies()).thenReturn(cookies);
    try {
      chatakMerchantInterceptor.preHandle(request, response, object);
    } catch (Exception e) {
      logger.error("ERROR:: ChatakMerchantInterceptorTest::testPreHandleSessionElse ", e);
    }
  }
}
