package com.chatak.acquirer.admin.interceptor;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@RunWith(MockitoJUnitRunner.class)
public class ChatakLocaleChangeInterceptorTest {

  @InjectMocks
  ChatakLocaleChangeInterceptor chatakLocaleChangeInterceptor = new ChatakLocaleChangeInterceptor();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  LocaleResolver localeResolver;

  @Mock
  Object handler;

  Cookie cookie;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/pages/");
    viewResolver.setSuffix(".jsp");
  }

  @Test
  public void testPreHandle() throws ServletException {
    Cookie[] cookies = {};
    chatakLocaleChangeInterceptor = new ChatakLocaleChangeInterceptor();
    Mockito.when(request.getCookies()).thenReturn(cookies);
    Mockito.when(RequestContextUtils.getLocaleResolver(request)).thenReturn(localeResolver);
    chatakLocaleChangeInterceptor.preHandle(request, response, handler);
  }

  @Test(expected = NullPointerException.class)
  public void testPreHandleElse() throws ServletException {
    Cookie[] cookies = {cookie};
    chatakLocaleChangeInterceptor = new ChatakLocaleChangeInterceptor();
    Mockito.when(request.getCookies()).thenReturn(cookies);
    Mockito.when(RequestContextUtils.getLocaleResolver(request)).thenReturn(localeResolver);
    chatakLocaleChangeInterceptor.preHandle(request, response, handler);
  }

  @Test(expected = IllegalStateException.class)
  public void testPreHandleException() throws ServletException {
    Cookie[] cookies = {};
    chatakLocaleChangeInterceptor = new ChatakLocaleChangeInterceptor();
    Mockito.when(request.getCookies()).thenReturn(cookies);
    Mockito.when(RequestContextUtils.getLocaleResolver(request)).thenReturn(null);
    chatakLocaleChangeInterceptor.preHandle(request, response, handler);
  }

}
