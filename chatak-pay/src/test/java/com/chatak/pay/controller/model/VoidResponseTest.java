package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VoidResponseTest {

	@InjectMocks
	VoidResponse voidResponse = new VoidResponse();

	@Mock
	private PaymentDetails paymentDetails;

	@Before
	public void setUp() {
		voidResponse.setTxnId("45");
		voidResponse.setTxnReferenceId("45");
		voidResponse.setTxnAuthId("45");

	}

	@Test
	public void testVoidResponse() {
		Assert.assertEquals("45", voidResponse.getTxnId());
		Assert.assertEquals("45", voidResponse.getTxnReferenceId());
		Assert.assertEquals("45", voidResponse.getTxnAuthId());
	}

}
