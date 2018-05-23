package com.chatak.pay.model;

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
		response.setErrorMessage("abc");
		response.setErrorCode("xyz");
		response.setTotalNoOfRows(Integer.parseInt("23"));
	}

	@Test
	public void testResponse() {
		Assert.assertEquals("abc", response.getErrorMessage());
		Assert.assertEquals("xyz", response.getErrorCode());
		Assert.assertEquals(Integer.valueOf("23"), response.getTotalNoOfRows());
	}
}
