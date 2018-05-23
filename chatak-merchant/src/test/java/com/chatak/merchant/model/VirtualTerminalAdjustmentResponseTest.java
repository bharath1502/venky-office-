package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class VirtualTerminalAdjustmentResponseTest {

	@InjectMocks
	VirtualTerminalAdjustmentResponse virtualTerminalAdjustmentResponse = new VirtualTerminalAdjustmentResponse();

	@Before
	public void setUp() {
		virtualTerminalAdjustmentResponse.setAuthId("authId");
		virtualTerminalAdjustmentResponse.setTxnRefNum("txnRefNum");
		virtualTerminalAdjustmentResponse.setTxnAmount("txnAmount");
		virtualTerminalAdjustmentResponse.setAdjAmount("adjAmount");
		virtualTerminalAdjustmentResponse.setFeeAmount("feeAmount");
		virtualTerminalAdjustmentResponse.setTotalAmount("totalAmount");
	}

	@Test
	public void testVirtualTerminalAdjustmentResponse() {
		Assert.assertEquals("authId", virtualTerminalAdjustmentResponse.getAuthId());
		Assert.assertEquals("txnRefNum", virtualTerminalAdjustmentResponse.getTxnRefNum());
		Assert.assertEquals("txnAmount", virtualTerminalAdjustmentResponse.getTxnAmount());
		Assert.assertEquals("adjAmount", virtualTerminalAdjustmentResponse.getAdjAmount());
		Assert.assertEquals("feeAmount", virtualTerminalAdjustmentResponse.getFeeAmount());
		Assert.assertEquals("totalAmount", virtualTerminalAdjustmentResponse.getTotalAmount());

	}

}
