package com.chatak.pay.controller.model;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SplitTxnDataTest {

	@InjectMocks
	SplitTxnData splitTxnData = new SplitTxnData();
	
	@Mock
	private PaymentDetails paymentDetails;

	@Before
	public void setUp() {
		splitTxnData.setSplitAmount(Long.parseLong("543"));
		splitTxnData.setRefMaskedPAN("45");
		splitTxnData.setRefMobileNumber(Long.parseLong("543"));

	}

	@Test
	public void testSplitTxnData() {
		Assert.assertEquals(Long.valueOf("543"), splitTxnData.getSplitAmount());
		Assert.assertEquals("45", splitTxnData.getRefMaskedPAN());
		Assert.assertEquals(Long.valueOf("543"), splitTxnData.getRefMobileNumber());
	}

}
