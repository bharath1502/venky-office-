package com.chatak.pay.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MerchantTest {
	
	@InjectMocks
	Merchant merchant = new Merchant();

	@Before
	public void setUp() {
		merchant.setMerchantName("abc");
		merchant.setMerchantCode("456");
	}

	@Test
	public void testMerchant() {
		Assert.assertEquals("abc", merchant.getMerchantName());
		Assert.assertEquals("456", merchant.getMerchantCode());
	}
}
