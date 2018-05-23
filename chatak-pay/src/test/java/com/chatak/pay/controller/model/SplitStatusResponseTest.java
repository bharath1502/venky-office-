package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SplitStatusResponseTest {

	@InjectMocks
	SplitStatusResponse splitStatusResponse = new SplitStatusResponse();

	@Mock
	private PaymentDetails paymentDetails;

	@Before
	public void setUp() {
		splitStatusResponse.setStatusInfo(true);

	}

	@Test
	public void testSplitStatusRequest() {
		Assert.assertEquals(true, splitStatusResponse.isStatusInfo());
	}
}
