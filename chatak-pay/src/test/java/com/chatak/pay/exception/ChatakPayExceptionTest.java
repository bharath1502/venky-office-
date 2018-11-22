package com.chatak.pay.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ChatakPayExceptionTest {

	private static Logger logger = LogManager.getLogger(ChatakPayException.class);

	@Mock
	Throwable throwable;

	@InjectMocks
	ChatakPayException chatakPayException = new ChatakPayException();

	ChatakPayException chatakPayException1 = new ChatakPayException("exception");

	ChatakPayException chatakPayException2 = new ChatakPayException(throwable);

	ChatakPayException chatakPayException3 = new ChatakPayException("exception", throwable);

	@Test
	public void testChatakPayException() {
		logger.info("Info :: ChatakPayExceptionTest :: testChatakPayException method");

	}

}
