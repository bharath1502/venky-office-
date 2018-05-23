package com.chatak.pay.controller.model.topup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TopupRequestTest {

	@InjectMocks
	TopupRequest topupRequest = new TopupRequest();

	@Before
	public void setUp() {
		topupRequest.setCategoryId(Integer.parseInt("123"));
		topupRequest.setTopupOfferId(Integer.parseInt("123"));
		topupRequest.setOperatorId(Integer.parseInt("123"));
		topupRequest.setMobileNumber("123");
		topupRequest.setAmount("123");
	}

	@Test
	public void testTopupRequest() {
		Assert.assertEquals(Integer.valueOf("123"), topupRequest.getCategoryId());
		Assert.assertEquals(Integer.valueOf("123"), topupRequest.getTopupOfferId());
		Assert.assertEquals(Integer.valueOf("123"), topupRequest.getOperatorId());
		Assert.assertEquals("123", topupRequest.getMobileNumber());
		Assert.assertEquals("123", topupRequest.getAmount());
	}

}
