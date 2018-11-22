package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SplitStatusRequestTest {

	@InjectMocks
	SplitStatusRequest splitStatusRequest = new SplitStatusRequest();

	@Mock
	private PaymentDetails paymentDetails;

	@Before
	public void setUp() {
		splitStatusRequest.setSplitTxnAmount(Long.parseLong("543"));
		splitStatusRequest.setSplitRefNumber("45");

	}

	@Test
	public void testSplitStatusRequest() {
		Assert.assertEquals(Long.valueOf("543"), splitStatusRequest.getSplitTxnAmount());
		Assert.assertEquals("45", splitStatusRequest.getSplitRefNumber());
	}

}
