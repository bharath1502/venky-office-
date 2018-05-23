package com.chatak.merchant.model;

import static org.junit.Assert.*;

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
		response.setTotalNoOfRows(Integer.parseInt("11"));
	}
	
	@Test
	public void testResponse() {

		Assert.assertEquals("errorCode", response.getErrorCode());
		Assert.assertEquals("errorMessage", response.getErrorMessage());
		Assert.assertEquals(Integer.valueOf("11"), response.getTotalNoOfRows());

}
}
