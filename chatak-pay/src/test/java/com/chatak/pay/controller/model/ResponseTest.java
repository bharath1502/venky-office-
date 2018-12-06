package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResponseTest {

	@InjectMocks
	Response response = new Response();

	@Before
	public void setUp() {
		response.setErrorCode("45");
		response.setErrorMessage("45");
		response.setErrorMessageExt("45");
		response.setTxnDateTime(Long.parseLong("45"));
		response.setTxnRefNumber("45");

	}

	@Test
	public void testResponse() {
		Assert.assertEquals("45", response.getErrorCode());
		Assert.assertEquals("45", response.getErrorMessage());
		Assert.assertEquals("45", response.getErrorMessageExt());
		Assert.assertEquals(Long.valueOf("45"), response.getTxnDateTime());
		Assert.assertEquals("45", response.getTxnRefNumber());
	}
}
