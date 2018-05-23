package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class VirtualTerminalPreAuthResponseTest {

	@InjectMocks
	VirtualTerminalPreAuthResponse virtualTerminalPreAuthResponse = new VirtualTerminalPreAuthResponse();

	@Before
	public void setUp() {
		virtualTerminalPreAuthResponse.setAuthId("authId");
		virtualTerminalPreAuthResponse.setTxnAmount("txnAmount");
		virtualTerminalPreAuthResponse.setFeeAmount("feeAmount");
		virtualTerminalPreAuthResponse.setTxnRefNum("txnRefNum");
		virtualTerminalPreAuthResponse.setTotalAmount("totalAmount");

	}

	@Test
	public void testVirtualTerminalPreAuthResponse() {
		Assert.assertEquals("authId", virtualTerminalPreAuthResponse.getAuthId());
		Assert.assertEquals("txnAmount", virtualTerminalPreAuthResponse.getTxnAmount());
		Assert.assertEquals("feeAmount", virtualTerminalPreAuthResponse.getFeeAmount());
		Assert.assertEquals("txnRefNum", virtualTerminalPreAuthResponse.getTxnRefNum());
		Assert.assertEquals("totalAmount", virtualTerminalPreAuthResponse.getTotalAmount());

	}
}
