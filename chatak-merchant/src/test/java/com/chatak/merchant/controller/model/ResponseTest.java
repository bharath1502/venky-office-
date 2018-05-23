package com.chatak.merchant.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class ResponseTest {

	@InjectMocks
	Response response = new Response();

	@Before
	public void setUp() {
		response.setErrorCode("errorCode");
		response.setErrorMessage("errorMessage");
		response.setErrorMessageExt("errorMessageExt");
		response.setTxnDateTime(Long.parseLong("9789"));
	}

	@Test
	public void testResponse() {
		Assert.assertEquals("errorCode", response.getErrorCode());
		Assert.assertEquals("errorMessage", response.getErrorMessage());
		Assert.assertEquals("errorMessageExt", response.getErrorMessageExt());
		Assert.assertEquals(Long.valueOf("9789"), response.getTxnDateTime());
	}
}
