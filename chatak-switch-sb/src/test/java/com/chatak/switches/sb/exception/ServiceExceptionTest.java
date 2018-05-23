package com.chatak.switches.sb.exception;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.chatak.pg.util.Constants;

public class ServiceExceptionTest {
	
	private static Logger logger = Logger.getLogger(ServiceException.class);

	@Mock
	Throwable throwable;

	@InjectMocks
	ServiceException serviceException = new ServiceException();

	ServiceException serviceException1 = new ServiceException(Constants.EXCEPTION);

	ServiceException serviceException2 = new ServiceException(throwable);

	ServiceException serviceException3 = new ServiceException(Constants.EXCEPTION, throwable);

	ServiceException serviceException4 = new ServiceException(Constants.EXCEPTION, "message");

	@Before
	public void setUp() {
		serviceException.setErrorCode("errorCode");
	}

	@Test
	public void testResponse() {
		Assert.assertEquals("errorCode", serviceException.getErrorCode());
	}

	@Test
	public void testServiceException() {
		logger.info("ERROR :: ServiceExceptionTest :: testServiceException:");

	}

}
