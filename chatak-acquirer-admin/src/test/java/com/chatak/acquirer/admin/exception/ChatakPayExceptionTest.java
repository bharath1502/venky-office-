package com.chatak.acquirer.admin.exception;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.chatak.pg.util.Constants;

public class ChatakPayExceptionTest {

	private static Logger logger = Logger.getLogger(ChatakPayException.class);

	@Mock
	Throwable throwable;

	@InjectMocks
	ChatakPayException chatakPayException = new ChatakPayException();

	ChatakPayException chatakPayException1 = new ChatakPayException(Constants.EXCEPTION);

	ChatakPayException chatakPayException2 = new ChatakPayException(throwable);

	ChatakPayException chatakPayException3 = new ChatakPayException(Constants.EXCEPTION, throwable);

	@Test
	public void testChatakPayException() {
		logger.info("ERROR :: ChatakPayExceptionTest :: testChatakPayException:");

	}

}
