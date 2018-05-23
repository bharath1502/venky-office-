package com.chatak.pay.exception;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.chatak.pg.util.Constants;

public class InvalidRequestExceptionTest {

private static Logger logger = Logger.getLogger(InvalidRequestException.class);
	
	

	@Mock
	Throwable throwable;

	@InjectMocks
	InvalidRequestException invalidRequestException1 = new InvalidRequestException();

	InvalidRequestException invalidRequestException2 = new InvalidRequestException(Constants.EXCEPTION);
	
	InvalidRequestException invalidRequestException3 = new InvalidRequestException(Constants.EXCEPTION,Constants.EXCEPTION);

	InvalidRequestException invalidRequestException4 = new InvalidRequestException(throwable);

	InvalidRequestException invalidRequestException5 = new InvalidRequestException(Constants.EXCEPTION, throwable);

	@Test
	public void testInvalidRequestException() {
		logger.info("ERROR :: InvalidRequestExceptionTest :: testInvalidRequestException:");
	}
	
	@Test
	public void testGetErrorCode(){
		invalidRequestException1.getErrorCode();
	}
}
