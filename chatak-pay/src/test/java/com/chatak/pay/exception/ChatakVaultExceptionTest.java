package com.chatak.pay.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ChatakVaultExceptionTest {

	private static Logger logger = LogManager.getLogger(ChatakVaultException.class);

	@Mock
	Throwable throwable;

	@InjectMocks
	ChatakVaultException chatakVaultException1 = new ChatakVaultException();

	ChatakVaultException chatakVaultException2 = new ChatakVaultException("exception");

	ChatakVaultException chatakVaultException3 = new ChatakVaultException(throwable);

	ChatakVaultException chatakVaultException4 = new ChatakVaultException("exception", throwable);

	@Test
	public void testChatakVaultException() {
		logger.info("Info :: ChatakVaultExceptionTest :: testChatakVaultException method");

	}

}
