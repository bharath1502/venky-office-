package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginRequestTest {

	@InjectMocks
	LoginRequest loginRequest = new LoginRequest();

	@Before
	public void setUp() {
		loginRequest.setUsername("23");
		loginRequest.setPassword("23");
		loginRequest.setDeviceSerial("23");
		loginRequest.setCurrentAppVersion("23");
	}

	@Test
	public void testLoginRequest() {
		Assert.assertEquals("23", loginRequest.getUsername());
		Assert.assertEquals("23", loginRequest.getPassword());
		Assert.assertEquals("23", loginRequest.getDeviceSerial());
		Assert.assertEquals("23", loginRequest.getCurrentAppVersion());

	}

}
