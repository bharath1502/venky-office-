package com.chatak.mailsender.exception;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PrepaidNotificationExceptionTest {

	private static Logger logger = Logger.getLogger(PrepaidNotificationException.class);

	@Mock
	Exception e;

	@InjectMocks
	PrepaidNotificationException prepaidNotificationException = new PrepaidNotificationException(e);

	PrepaidNotificationException prepaidNotificationException1 = new PrepaidNotificationException("a", "b");

	PrepaidNotificationException prepaidNotificationException2 = new PrepaidNotificationException(e, "a", "b");

	PrepaidNotificationException prepaidNotificationException3 = new PrepaidNotificationException(e, "a");

	@Before
	public void setUp() {
		prepaidNotificationException.setErrorCode("errorCode");
		prepaidNotificationException.setErrorMessage("errorMessage");
	}

	@Test
	public void testResponse() {
		Assert.assertEquals("errorCode", prepaidNotificationException.getErrorCode());
		Assert.assertEquals("errorMessage", prepaidNotificationException.getErrorMessage());
	}

	@Test

	public void testPrepaidNotificationException() {
		logger.info("ERROR :: PrepaidNotificationExceptionTest :: testPrepaidNotificationException:");
	}

}
