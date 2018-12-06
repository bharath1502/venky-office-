package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginDetailsTest {

	@InjectMocks
	LoginDetails loginDetails = new LoginDetails();

	@Before
	public void setUp() {
		loginDetails.setAcqU("23");
		loginDetails.setjSession("23");
		loginDetails.setAcqP("23");

	}

	@Test
	public void testLoginDetails() {
		Assert.assertEquals("23", loginDetails.getAcqU());
		Assert.assertEquals("23", loginDetails.getjSession());
		Assert.assertEquals("23", loginDetails.getAcqP());

	}

}
