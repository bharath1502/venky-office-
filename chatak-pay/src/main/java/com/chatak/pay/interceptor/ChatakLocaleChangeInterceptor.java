package com.chatak.pay.interceptor;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.chatak.pay.util.StringUtil;

public class ChatakLocaleChangeInterceptor extends LocaleChangeInterceptor {
	
	private String defaultLocale = "en";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if("locale".equalsIgnoreCase(cookie.getName()) && !StringUtil.isNullAndEmpty(cookie.getValue())) {
					defaultLocale = cookie.getValue();
					break;
				}
			}
		}
		
		// Check for header value
		String localeHeader = request.getHeader("locale");
		if(!StringUtil.isNullAndEmpty(localeHeader)) {
			defaultLocale = localeHeader;
		}
		
		String newLocale = defaultLocale;
		if (newLocale != null) {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			if (localeResolver == null) {
				throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
			}
			localeResolver.setLocale(request, response, StringUtils.parseLocaleString(newLocale));
		}
		// Proceed in any case.
		return true;
	}
}
