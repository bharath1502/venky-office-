package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class PaymentResponseTest {

	@InjectMocks
	PaymentResponse paymentResponse = new PaymentResponse();

	@Before
	public void setUp() {
		paymentResponse.setToken("45");
		paymentResponse.setLast4("45");
		paymentResponse.setcType("45");

	}

	@Test
	public void testPaymentResponse() {
		Assert.assertEquals("45", paymentResponse.getToken());
		Assert.assertEquals("45", paymentResponse.getLast4());
		Assert.assertEquals("45", paymentResponse.getcType());
	}
}
