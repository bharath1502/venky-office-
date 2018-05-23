package com.chatak.mailsender.model;

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
		response.setErrorCode("errorCode");
		response.setErrorMessage("errorMessage");
	}

	@Test
	public void testResponse() {
		Assert.assertEquals("errorCode", response.getErrorCode());
		Assert.assertEquals("errorMessage", response.getErrorMessage());
	}
}
