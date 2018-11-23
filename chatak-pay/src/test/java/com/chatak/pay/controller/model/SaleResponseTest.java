package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SaleResponseTest {
	
	@InjectMocks
	SaleResponse saleResponse = new SaleResponse();
	
	@Mock
	private PaymentDetails paymentDetails;

	@Before
	public void setUp() {
		saleResponse.setTxnId("45");
		saleResponse.setTxnReferenceId("45");
		saleResponse.setTxnAuthId("45");
		saleResponse.setPaymentDetails(paymentDetails);

	}

	@Test
	public void testsaleResponse() {
		Assert.assertEquals("45", saleResponse.getTxnId());
		Assert.assertEquals("45", saleResponse.getTxnReferenceId());
		Assert.assertEquals("45", saleResponse.getTxnAuthId());
		Assert.assertEquals(paymentDetails, saleResponse.getPaymentDetails());
	}
}
