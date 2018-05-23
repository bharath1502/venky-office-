package com.chatak.merchant.exception;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ChatakPayExceptionTest {

	private static Logger logger = Logger.getLogger(ChatakPayException.class);

	@Mock
	Throwable throwable;

	@InjectMocks
	ChatakPayException chatakPayException = new ChatakPayException();

	ChatakPayException chatakPayException1 = new ChatakPayException("exception");

	ChatakPayException chatakPayException2 = new ChatakPayException(throwable);

	ChatakPayException chatakPayException3 = new ChatakPayException("exception", throwable);

	@Test
	public void testChatakPayException() {
		logger.info("ERROR :: ChatakPayException :: testChatakPayException:");

	}

}
