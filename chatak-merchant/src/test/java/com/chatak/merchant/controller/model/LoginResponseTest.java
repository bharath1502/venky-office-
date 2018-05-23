package com.chatak.merchant.controller.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class LoginResponseTest {

	@InjectMocks
	LoginResponse loginResponse = new LoginResponse();

	@Mock
	private List<String> existingFeature;

	@Before
	public void setUp() {
		loginResponse.setStatus(true);
		loginResponse.setMessage("message");
		loginResponse.setUserId(Long.parseLong("23"));
		loginResponse.setEmail("email");
		loginResponse.setAccessToken("accessToken");
		loginResponse.setServiceProviderId(Long.parseLong("101"));
		loginResponse.setSubServiceProviderId(Long.parseLong("543"));
		loginResponse.setExistingFeature(existingFeature);
		loginResponse.setUserRoleId(Long.parseLong("7856"));
		loginResponse.setMakerCheckerRequired(true);
		loginResponse.setUserType("userType");
		loginResponse.setParentMerchantId(Long.parseLong("123"));
		loginResponse.setUserMerchantId(Long.parseLong("212"));
		loginResponse.setUserName("userName");
	}

	@Test
	public void testLoginResponse() {
		Assert.assertEquals(Boolean.valueOf(true), loginResponse.getStatus());
		Assert.assertEquals("message", loginResponse.getMessage());
		Assert.assertEquals(Long.valueOf("23"), loginResponse.getUserId());
		Assert.assertEquals("email", loginResponse.getEmail());
		Assert.assertEquals("accessToken", loginResponse.getAccessToken());
		Assert.assertEquals(Long.valueOf("101"), loginResponse.getServiceProviderId());
		Assert.assertEquals(Long.valueOf("543"), loginResponse.getSubServiceProviderId());
		Assert.assertEquals(existingFeature, loginResponse.getExistingFeature());
		Assert.assertEquals(Long.valueOf("7856"), loginResponse.getUserRoleId());
		Assert.assertEquals(Boolean.valueOf(true), loginResponse.getMakerCheckerRequired());
		Assert.assertEquals("userType", loginResponse.getUserType());
		Assert.assertEquals(Long.valueOf("123"), loginResponse.getParentMerchantId());
		Assert.assertEquals(Long.valueOf("212"), loginResponse.getUserMerchantId());
		Assert.assertEquals("userName", loginResponse.getUserName());
	}

}
