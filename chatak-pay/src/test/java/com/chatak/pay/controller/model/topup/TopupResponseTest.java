package com.chatak.pay.controller.model.topup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TopupResponseTest {

	@InjectMocks
	TopupResponse topupResponse = new TopupResponse();

	@Before
	public void setUp() {
		topupResponse.setOperatorId(Integer.parseInt("123"));
		topupResponse.setCategoryId(Integer.parseInt("123"));
		topupResponse.setTopupOfferId(Integer.parseInt("123"));
		topupResponse.setMobileNumber("123");
		topupResponse.setAmount("123");
		topupResponse.setStatus("123");
		topupResponse.setTransRefNumb("123");
		topupResponse.setCreatedDate("123");
		topupResponse.setUpdatedDate("123");
	}

	@Test
	public void testTopupResponse() {
		Assert.assertEquals(Integer.valueOf("123"), topupResponse.getOperatorId());
		Assert.assertEquals(Integer.valueOf("123"), topupResponse.getCategoryId());
		Assert.assertEquals(Integer.valueOf("123"), topupResponse.getTopupOfferId());
		Assert.assertEquals("123", topupResponse.getMobileNumber());
		Assert.assertEquals("123", topupResponse.getAmount());
		Assert.assertEquals("123", topupResponse.getStatus());
		Assert.assertEquals("123", topupResponse.getTransRefNumb());
		Assert.assertEquals("123", topupResponse.getCreatedDate());
		Assert.assertEquals("123", topupResponse.getUpdatedDate());
	}

}
