package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class VirtualTerminalRefundResponseTest {

	@InjectMocks
	VirtualTerminalRefundResponse virtualTerminalRefundResponse = new VirtualTerminalRefundResponse();

	@Before
	public void setUp() {
		virtualTerminalRefundResponse.setAuthId("authId");
		virtualTerminalRefundResponse.setTxnRefNum("txnRefNum");
		virtualTerminalRefundResponse.setTxnAmount("txnAmount");

	}

	@Test
	public void testVirtualTerminalRefundResponse() {

		Assert.assertEquals("authId", virtualTerminalRefundResponse.getAuthId());
		Assert.assertEquals("txnRefNum", virtualTerminalRefundResponse.getTxnRefNum());
		Assert.assertEquals("txnAmount", virtualTerminalRefundResponse.getTxnAmount());

	}

}
