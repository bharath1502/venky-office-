package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class VirtualTerminalVoidResponseTest {
	@InjectMocks
	VirtualTerminalVoidResponse virtualTerminalVoidResponse = new VirtualTerminalVoidResponse();

	@Before
	public void setUp() {
		virtualTerminalVoidResponse.setAuthId("authId");
		virtualTerminalVoidResponse.setTxnRefNum("txnRefNum");
		virtualTerminalVoidResponse.setTxnAmount("txnAmount");

	}

	@Test
	public void testVirtualTerminalVoidResponse() {
		Assert.assertEquals("authId", virtualTerminalVoidResponse.getAuthId());
		Assert.assertEquals("txnRefNum", virtualTerminalVoidResponse.getTxnRefNum());
		Assert.assertEquals("txnAmount", virtualTerminalVoidResponse.getTxnAmount());

	}
}
