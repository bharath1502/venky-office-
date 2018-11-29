package com.chatak.acquirer.admin.interceptor;

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

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.LoginDetails;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class ChatakAcqInterceptorTest {

  private static Logger logger = Logger.getLogger(ChatakAcqInterceptorTest.class);

  @InjectMocks
  ChatakAcqInterceptor chatakAcqInterceptor = new ChatakAcqInterceptor();
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
    chatakAcqInterceptor = new ChatakAcqInterceptor();
    try {
    	chatakAcqInterceptor.preHandle(request, response, object);
    } catch (Exception e) {
      logger.error("ERROR:: ChatakAcqInterceptorTest::testPreHandle ", e);
    }
  }

  @Test
  public void testPreHandleElse() {
    MockHttpServletRequest requestMock = new MockHttpServletRequest();
    requestMock.setRequestURI(null);
    chatakAcqInterceptor = new ChatakAcqInterceptor();
    try {
    	chatakAcqInterceptor.preHandle(request, response, object);
    } catch (Exception e) {
      logger.error("ERROR:: ChatakAcqInterceptorTest::testPreHandleElse ", e);
    }
  }

  @Test
  public void testPreHandleUri() {
    MockHttpSession mockHttpSession = new MockHttpSession();
    mockHttpSession.setAttribute(Constants.SESSION, object);
    MockHttpServletRequest requestMock = new MockHttpServletRequest();
    requestMock.setRequestURI(Constants.MERCHANT);
    chatakAcqInterceptor = new ChatakAcqInterceptor();
    try {
    	chatakAcqInterceptor.preHandle(request, response, object);
    } catch (Exception e) {
      logger.error("ERROR:: ChatakAcqInterceptorTest::testPreHandleUri ", e);
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
    loginDetails.setjSession(Constants.SESSION);
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
    	chatakAcqInterceptor.preHandle(request, response, object);
    } catch (Exception e) {
      logger.error("ERROR:: ChatakAcqInterceptorTest::testPreHandleSession ", e);
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
    loginDetails.setjSession(Constants.SESSION);
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
    	chatakAcqInterceptor.preHandle(request, response, object);
    } catch (Exception e) {
      logger.error("ERROR:: ChatakAcqInterceptorTest::testPreHandleSessionElse ", e);
    }
  }
}
