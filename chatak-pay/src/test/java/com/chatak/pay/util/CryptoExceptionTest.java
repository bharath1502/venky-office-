package com.chatak.pay.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pay.exception.ChatakPayException;
@RunWith(MockitoJUnitRunner.class)
public class CryptoExceptionTest {


	private static Logger logger = LogManager.getLogger(ChatakPayException.class);

	@Mock
	Throwable throwable;

	@InjectMocks
	CryptoException cryptoException = new CryptoException();

	CryptoException cryptoException1 = new CryptoException("exception");

	CryptoException cryptoException2 = new CryptoException(throwable);

	CryptoException cryptoException3 = new CryptoException("exception", throwable);

	@Test
	public void testCryptoException() {
		logger.info("Info :: CryptoExceptionTest :: testCryptoException Method");
	}

}
