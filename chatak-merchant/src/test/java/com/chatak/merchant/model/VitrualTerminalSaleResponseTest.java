package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class VitrualTerminalSaleResponseTest {

	@InjectMocks
	VitrualTerminalSaleResponse vitrualTerminalSaleResponse = new VitrualTerminalSaleResponse();

	@Before
	public void setUp() {
		vitrualTerminalSaleResponse.setAuthId("authId");
		vitrualTerminalSaleResponse.setTxnRefNum("txnRefNum");
		vitrualTerminalSaleResponse.setTxnAmount("txnAmount");
		vitrualTerminalSaleResponse.setFeeAmount("feeAmount");
		vitrualTerminalSaleResponse.setTotalAmount("totalAmount");

	}

	@Test
	public void testVitrualTerminalSaleResponse() {
		Assert.assertEquals("authId", vitrualTerminalSaleResponse.getAuthId());
		Assert.assertEquals("txnRefNum", vitrualTerminalSaleResponse.getTxnRefNum());
		Assert.assertEquals("txnAmount", vitrualTerminalSaleResponse.getTxnAmount());
		Assert.assertEquals("feeAmount", vitrualTerminalSaleResponse.getFeeAmount());
		Assert.assertEquals("totalAmount", vitrualTerminalSaleResponse.getTotalAmount());

	}

}
