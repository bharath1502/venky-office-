package com.chatak.pay.spring.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.service.ChatakPayService;
import com.chatak.pg.acq.dao.model.PGApplicationClient;
@RunWith(MockitoJUnitRunner.class)
public class ChatakServiceBasicAccessClientDetailsTest {

	@InjectMocks
	ChatakServiceBasicAccessClientDetails chatakServiceBasicAccessClientDetails = new ChatakServiceBasicAccessClientDetails();

	@Mock
	private ChatakPayService chatakPayService;

	@Test
	public void testLoadUserByUsername() throws ChatakPayException {
		PGApplicationClient pgApplicationClient = new PGApplicationClient();
		pgApplicationClient.setAppClientRole("abcd:\\");
		pgApplicationClient.setAppAuthUser("123");
		pgApplicationClient.setAppAuthPass("123");
		Mockito.when(chatakPayService.getApplicationClientAuth(Matchers.anyString())).thenReturn(pgApplicationClient);
		chatakServiceBasicAccessClientDetails.loadUserByUsername("54321");
	}
	
	@Test(expected=UsernameNotFoundException.class)
	public void testLoadUserByUsernameException() throws ChatakPayException {
		Mockito.when(chatakPayService.getApplicationClientAuth(Matchers.anyString())).thenThrow(ChatakPayException.class);
		chatakServiceBasicAccessClientDetails.loadUserByUsername("54321");
	}

}
