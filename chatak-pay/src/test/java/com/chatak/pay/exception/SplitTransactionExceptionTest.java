package com.chatak.pay.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class SplitTransactionExceptionTest {

	private static Logger logger = LogManager.getLogger(SplitTransactionException.class);

	@Mock
	Throwable throwable;

	@InjectMocks
	SplitTransactionException splitTransactionException1 = new SplitTransactionException();

	SplitTransactionException splitTransactionException2 = new SplitTransactionException("exception");

	SplitTransactionException splitTransactionException3 = new SplitTransactionException(throwable);

	SplitTransactionException splitTransactionException4 = new SplitTransactionException("exception", throwable);

	@Test
	public void testSplitTransactionException() {
		logger.info("Info :: SplitTransactionExceptionTest :: testSplitTransactionException method");

	}

	@Test
	public void testGetErrorCode() {
		splitTransactionException1.getErrorCode();
	}
}
