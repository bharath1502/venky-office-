package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class PayPageConfigTest {

	@InjectMocks
	PayPageConfig payPageConfig = new PayPageConfig();

	private byte[] payPageLogo;

	@Before
	public void setUp() {
		payPageConfig.setMerchantId(Long.parseLong("3123"));
		payPageConfig.setHeader("header");
		payPageConfig.setFooter("footer");
		payPageConfig.setPayPageLogo(payPageLogo);
		payPageConfig.setCreatedBy("createdBy");
		payPageConfig.setUpdatedBy("updatedBy");

	}

	@Test
	public void testPayPageConfig() {

		Assert.assertEquals(Long.valueOf("3123"), payPageConfig.getMerchantId());
		Assert.assertEquals("header", payPageConfig.getHeader());
		Assert.assertEquals("footer", payPageConfig.getFooter());
		Assert.assertEquals(payPageLogo, payPageConfig.getPayPageLogo());
		Assert.assertEquals("createdBy", payPageConfig.getCreatedBy());
		Assert.assertEquals("updatedBy", payPageConfig.getUpdatedBy());

	}
}
