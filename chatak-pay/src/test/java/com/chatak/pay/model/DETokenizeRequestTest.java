package com.chatak.pay.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class DETokenizeRequestTest {
	
	@InjectMocks
	DETokenizeRequest deTokenizeRequest = new DETokenizeRequest();


	@Before
	public void setUp() {
		deTokenizeRequest.setVersionNum("123");
		deTokenizeRequest.setTokenRequestorID("456");
		deTokenizeRequest.setPaymentToken("2");
		deTokenizeRequest.setTokenExpiryDate("21/20/20");
	}

	@Test
	public void testdeTokenizeRequest() {
		Assert.assertEquals("123", deTokenizeRequest.getVersionNum());
		Assert.assertEquals("456", deTokenizeRequest.getTokenRequestorID());
		Assert.assertEquals("2", deTokenizeRequest.getPaymentToken());
		Assert.assertEquals("21/20/20", deTokenizeRequest.getTokenExpiryDate());
	}
}
