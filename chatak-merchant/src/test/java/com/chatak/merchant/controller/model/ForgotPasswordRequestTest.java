package com.chatak.merchant.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class ForgotPasswordRequestTest {

	@InjectMocks
	ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();

	@Before
	public void setUp() {
		forgotPasswordRequest.setUserName("userName");
		forgotPasswordRequest.setEmail("email");
		forgotPasswordRequest.setIsNewUser(true);
	}

	@Test
	public void testForgotPasswordRequest() {
		Assert.assertEquals("userName", forgotPasswordRequest.getUserName());
		Assert.assertEquals("email", forgotPasswordRequest.getEmail());
		Assert.assertEquals(true, forgotPasswordRequest.getIsNewUser());
	}
}
