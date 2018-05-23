package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class VirtualTerminalCaptureRequestTest {
	@InjectMocks
	VirtualTerminalCaptureRequest virtualTerminalCaptureRequest = new VirtualTerminalCaptureRequest();

	@Before
	public void setUp() {
		virtualTerminalCaptureRequest.setExpDate("expDate");
		virtualTerminalCaptureRequest.setMerchantId("merchantId");
		virtualTerminalCaptureRequest.setTerminalId("terminalId");
		virtualTerminalCaptureRequest.setInvoiceNumber("invoiceNumber");
		virtualTerminalCaptureRequest.setTxnAmount(Long.parseLong("767"));
		virtualTerminalCaptureRequest.setCardNum("cardNum");
		virtualTerminalCaptureRequest.setAuthId("authId");
		virtualTerminalCaptureRequest.setAuthTxnRefNum("authTxnRefNum");
	}

	@Test
	public void testVirtualTerminalCaptureRequest() {
		Assert.assertEquals("expDate", virtualTerminalCaptureRequest.getExpDate());
		Assert.assertEquals("merchantId", virtualTerminalCaptureRequest.getMerchantId());
		Assert.assertEquals("terminalId", virtualTerminalCaptureRequest.getTerminalId());
		Assert.assertEquals("invoiceNumber", virtualTerminalCaptureRequest.getInvoiceNumber());
		Assert.assertEquals(Long.valueOf("767"), virtualTerminalCaptureRequest.getTxnAmount());
		Assert.assertEquals("cardNum", virtualTerminalCaptureRequest.getCardNum());
		Assert.assertEquals("authId", virtualTerminalCaptureRequest.getAuthId());
		Assert.assertEquals("authTxnRefNum", virtualTerminalCaptureRequest.getAuthTxnRefNum());

	}

}
