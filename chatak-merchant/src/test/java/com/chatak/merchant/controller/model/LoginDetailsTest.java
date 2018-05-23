package com.chatak.merchant.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class LoginDetailsTest {

	@InjectMocks
	LoginDetails loginDetails = new LoginDetails();

	@Before
	public void setUp() {
		loginDetails.setAcqU("acqU");
		loginDetails.setAcqP("acqP");
		loginDetails.setjSession("jSession");
	}

	@Test
	public void testLoginDetails() {
		Assert.assertEquals("acqU", loginDetails.getAcqU());
		Assert.assertEquals("acqP", loginDetails.getAcqP());
		Assert.assertEquals("jSession", loginDetails.getjSession());
	}
}
