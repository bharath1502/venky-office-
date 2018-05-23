package com.chatak.switches.sb.exception;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.chatak.pg.util.Constants;

public class ChatakInvalidTransactionExceptionTest {

	private static Logger logger = Logger.getLogger(ChatakInvalidTransactionException.class);

	@Mock
	Throwable throwable;

	@InjectMocks
	ChatakInvalidTransactionException chatakInvalidTransactionException = new ChatakInvalidTransactionException();

	ChatakInvalidTransactionException chatakInvalidTransactionException1 = new ChatakInvalidTransactionException(
			Constants.EXCEPTION);

	ChatakInvalidTransactionException chatakInvalidTransactionException2 = new ChatakInvalidTransactionException(
			throwable);

	ChatakInvalidTransactionException chatakInvalidTransactionException3 = new ChatakInvalidTransactionException(
			Constants.EXCEPTION, throwable);

	ChatakInvalidTransactionException chatakInvalidTransactionException4 = new ChatakInvalidTransactionException(
			Constants.EXCEPTION, throwable, true, true);

	@Test
	public void testChatakInvalidTransactionException() {
		logger.info("ERROR :: ChatakInvalidTransactionExceptionTest :: testChatakInvalidTransactionException:");

	}

}
