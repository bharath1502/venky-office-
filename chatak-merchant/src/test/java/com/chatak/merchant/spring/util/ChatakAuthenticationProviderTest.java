package com.chatak.merchant.spring.util;

import java.util.Collection;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionRegistryImpl;

public class ChatakAuthenticationProviderTest {

	@InjectMocks
	ChatakAuthenticationProvider chatakAuthenticationProvider = new ChatakAuthenticationProvider();

	@Mock
	Authentication authentication;

	@Mock
	Class<?> authentications;

	@Mock
	SessionRegistryImpl sessionRegistry;

	@Test(expected = NullPointerException.class)
	public void testAuthenticate() {
		chatakAuthenticationProvider.authenticate(authentication);
	}

	public void testSupports() {
		chatakAuthenticationProvider.supports(authentications);

	}

	@Test
	public void testGetSessionRegistry() {
		chatakAuthenticationProvider.getSessionRegistry();
	}

	@Test
	public void testSetSessionRegistry() {
		chatakAuthenticationProvider.setSessionRegistry(sessionRegistry);
	}

}
