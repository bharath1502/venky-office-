package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class VirtualTerminalVoidRequestTest {

	@InjectMocks
	VirtualTerminalVoidRequest virtualTerminalVoidRequest = new VirtualTerminalVoidRequest();

	@Before
	public void setUp() {
		virtualTerminalVoidRequest.setCardNum("cardNum");
		virtualTerminalVoidRequest.setExpDate("expDate");
		virtualTerminalVoidRequest.setMerchantId("merchantId");
		virtualTerminalVoidRequest.setTerminalId("terminalId");
		virtualTerminalVoidRequest.setInvoiceNumber("invoiceNumber");
		virtualTerminalVoidRequest.setTxnRefNum("txnRefNum");
		virtualTerminalVoidRequest.setAuthId("authId");

	}

	@Test
	public void testVirtualTerminalVoidRequest() {
		Assert.assertEquals("cardNum", virtualTerminalVoidRequest.getCardNum());
		Assert.assertEquals("expDate", virtualTerminalVoidRequest.getExpDate());
		Assert.assertEquals("merchantId", virtualTerminalVoidRequest.getMerchantId());
		Assert.assertEquals("terminalId", virtualTerminalVoidRequest.getTerminalId());
		Assert.assertEquals("invoiceNumber", virtualTerminalVoidRequest.getInvoiceNumber());
		Assert.assertEquals("txnRefNum", virtualTerminalVoidRequest.getTxnRefNum());
		Assert.assertEquals("authId", virtualTerminalVoidRequest.getAuthId());
	}
}
