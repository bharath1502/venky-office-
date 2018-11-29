package com.chatak.acquirer.admin.controller.model;

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
		loginDetails.setLoginIpAddress("loginIpAddress");
	}

	@Test
	public void testLoginDetails() {
		Assert.assertEquals("acqU", loginDetails.getAcqU());
		Assert.assertEquals("acqP", loginDetails.getAcqP());
		Assert.assertEquals("jSession", loginDetails.getjSession());
		Assert.assertEquals("loginIpAddress", loginDetails.getLoginIpAddress());
	}
}
