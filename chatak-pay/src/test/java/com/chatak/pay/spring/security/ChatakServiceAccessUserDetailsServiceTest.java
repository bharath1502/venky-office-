package com.chatak.pay.spring.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.service.ChatakPayService;
import com.chatak.pg.acq.dao.model.PGApplicationClient;

@RunWith(MockitoJUnitRunner.class)
public class ChatakServiceAccessUserDetailsServiceTest {

	@InjectMocks
	ChatakServiceAccessUserDetailsService chatakServiceAccessUserDetailsService = new ChatakServiceAccessUserDetailsService();
	
	 @Mock
	  private ChatakPayService chatakPayService;
	
	@Test
	public void testLoadUserByUsername() throws ChatakPayException{
		 PGApplicationClient pgApplicationClient=new PGApplicationClient();
		 pgApplicationClient.setAppClientRole("abcd:\\");
		 pgApplicationClient.setAppClientId("123");
		 pgApplicationClient.setAppClientAccess("123");
		 Mockito.when(chatakPayService.getApplicationClient(Matchers.anyString())).thenReturn(pgApplicationClient);
		chatakServiceAccessUserDetailsService.loadUserByUsername("54321");
	}

}
