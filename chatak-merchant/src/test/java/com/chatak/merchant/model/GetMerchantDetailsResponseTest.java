package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class GetMerchantDetailsResponseTest {

	@InjectMocks
	GetMerchantDetailsResponse getMerchantDetailsResponse = new GetMerchantDetailsResponse();

	@Before
	public void setUp() {
		getMerchantDetailsResponse.setMerchantId("mid");
		getMerchantDetailsResponse.setTerminalId("tid");
		getMerchantDetailsResponse.setBusinessName("bid");
	}

	@Test
	public void testGetMerchantDetailsResponse() {
		Assert.assertEquals("mid", getMerchantDetailsResponse.getMerchantId());
		Assert.assertEquals("tid", getMerchantDetailsResponse.getTerminalId());
		Assert.assertEquals("bid", getMerchantDetailsResponse.getBusinessName());

	}
}
