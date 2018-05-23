package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class VirtualTerminalPreAuthRequestTest {

	@InjectMocks
	VirtualTerminalPreAuthRequest virtualTerminalPreAuthRequest = new VirtualTerminalPreAuthRequest();

	@Before
	public void setUp() {
		virtualTerminalPreAuthRequest.setExpDate("expDate");
		virtualTerminalPreAuthRequest.setMerchantId("merchantId");
		virtualTerminalPreAuthRequest.setTerminalId("terminalId");
		virtualTerminalPreAuthRequest.setInvoiceNumber("invoiceNumber");
		virtualTerminalPreAuthRequest.setTxnAmount(Long.parseLong("8976"));
		virtualTerminalPreAuthRequest.setCardNum("cardNum");

	}

	@Test
	public void testVirtualTerminalPreAuthRequest() {
		Assert.assertEquals("expDate", virtualTerminalPreAuthRequest.getExpDate());
		Assert.assertEquals("merchantId", virtualTerminalPreAuthRequest.getMerchantId());
		Assert.assertEquals("terminalId", virtualTerminalPreAuthRequest.getTerminalId());
		Assert.assertEquals("invoiceNumber", virtualTerminalPreAuthRequest.getInvoiceNumber());
		Assert.assertEquals(Long.valueOf("8976"), virtualTerminalPreAuthRequest.getTxnAmount());
		Assert.assertEquals("cardNum", virtualTerminalPreAuthRequest.getCardNum());

	}
}
