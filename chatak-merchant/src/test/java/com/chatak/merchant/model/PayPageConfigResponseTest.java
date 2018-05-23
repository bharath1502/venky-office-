package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class PayPageConfigResponseTest {

	@InjectMocks
	PayPageConfigResponse payPageConfigResponse = new PayPageConfigResponse();

	private byte[] payPageLogo;

	@Before
	public void setUp() {
		payPageConfigResponse.setHeader("header");
		payPageConfigResponse.setFooter("footer");
		payPageConfigResponse.setPayPageLogo(payPageLogo);
	}
	
	@Test
	public void testPayPageConfigResponse() {

		Assert.assertEquals("header", payPageConfigResponse.getHeader());
		Assert.assertEquals("footer", payPageConfigResponse.getFooter());
		Assert.assertEquals(payPageLogo, payPageConfigResponse.getPayPageLogo());

}
}
