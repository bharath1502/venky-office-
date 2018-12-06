package com.chatak.pay.spring.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pay.exception.ChatakPayException;

@RunWith(MockitoJUnitRunner.class)
public class ChatakServiceClientDetailsServiceTest {

	@InjectMocks
	ChatakServiceClientDetailsService chatakServiceClientDetailsService = new ChatakServiceClientDetailsService();

	@Test
	public void testLoadClientByClientId() throws ChatakPayException {
		chatakServiceClientDetailsService.loadClientByClientId("54321");
	}

}
