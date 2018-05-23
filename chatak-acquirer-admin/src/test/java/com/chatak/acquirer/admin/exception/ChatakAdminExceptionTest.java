package com.chatak.acquirer.admin.exception;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.chatak.pg.util.Constants;

public class ChatakAdminExceptionTest {

	private static Logger logger = Logger.getLogger(ChatakAdminException.class);

	@Mock
	Throwable throwable;

	@InjectMocks
	ChatakAdminException chatakMerchantException = new ChatakAdminException();

	ChatakAdminException chatakMerchantException1 = new ChatakAdminException(Constants.EXCEPTION);

	ChatakAdminException chatakMerchantException2 = new ChatakAdminException(throwable);
	ChatakAdminException chatakMerchantException3 = new ChatakAdminException(Constants.EXCEPTION, throwable);

	@Test
	public void testChatakAdminException() {
		logger.info("ERROR :: ChatakAdminExceptionTest :: testChatakAdminException:");
	}
}
