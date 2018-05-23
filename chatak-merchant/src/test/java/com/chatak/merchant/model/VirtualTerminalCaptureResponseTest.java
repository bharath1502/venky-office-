package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class VirtualTerminalCaptureResponseTest {

	@InjectMocks
	VirtualTerminalCaptureResponse virtualTerminalCaptureResponse = new VirtualTerminalCaptureResponse();

	@Before
	public void setUp() {
		virtualTerminalCaptureResponse.setAuthId("authId");
		virtualTerminalCaptureResponse.setTxnRefNum("txnRefNum");
		virtualTerminalCaptureResponse.setTxnAmount("txnAmount");
		virtualTerminalCaptureResponse.setFeeAmount("feeAmount");
		virtualTerminalCaptureResponse.setTotalAmount("totalAmount");
	}

	@Test
	public void testVirtualTerminalCaptureResponse() {
		Assert.assertEquals("authId", virtualTerminalCaptureResponse.getAuthId());
		Assert.assertEquals("txnRefNum", virtualTerminalCaptureResponse.getTxnRefNum());
		Assert.assertEquals("txnAmount", virtualTerminalCaptureResponse.getTxnAmount());
		Assert.assertEquals("feeAmount", virtualTerminalCaptureResponse.getFeeAmount());
		Assert.assertEquals("totalAmount", virtualTerminalCaptureResponse.getTotalAmount());

	}

}
