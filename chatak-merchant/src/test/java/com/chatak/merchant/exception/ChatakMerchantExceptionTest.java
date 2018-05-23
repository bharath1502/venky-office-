package com.chatak.merchant.exception;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ChatakMerchantExceptionTest {

	private static Logger logger = Logger.getLogger(ChatakMerchantException.class);

	@Mock
	Throwable throwable;

	@InjectMocks
	ChatakMerchantException chatakMerchantException = new ChatakMerchantException();

	ChatakMerchantException chatakMerchantException1 = new ChatakMerchantException("exception");

	ChatakMerchantException chatakMerchantException2 = new ChatakMerchantException(throwable);

	ChatakMerchantException chatakMerchantException3 = new ChatakMerchantException("exception", throwable);

	@Test
	public void testChatakMerchantException() {
		logger.info("ERROR :: ChatakMerchantExceptionTest :: testChatakMerchantException:");
	}
}
