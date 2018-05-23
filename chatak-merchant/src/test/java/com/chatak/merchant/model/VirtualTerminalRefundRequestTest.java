package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class VirtualTerminalRefundRequestTest {

	@InjectMocks
	VirtualTerminalRefundRequest virtualTerminalRefundRequest = new VirtualTerminalRefundRequest();

	@Before
	public void setUp() {
		virtualTerminalRefundRequest.setCardNum("cardNum");
		virtualTerminalRefundRequest.setExpDate("expDate");
		virtualTerminalRefundRequest.setMerchantId("merchantId");
		virtualTerminalRefundRequest.setTerminalId("terminalId");
		virtualTerminalRefundRequest.setTxnAmount(Long.parseLong("24"));
		virtualTerminalRefundRequest.setInvoiceNumber("invoiceNumber");
		virtualTerminalRefundRequest.setTxnRefNum("txnRefNum");
	}

	@Test
	public void testVirtualTerminalRefundRequest() {
		Assert.assertEquals("cardNum", virtualTerminalRefundRequest.getCardNum());
		Assert.assertEquals("expDate", virtualTerminalRefundRequest.getExpDate());
		Assert.assertEquals("merchantId", virtualTerminalRefundRequest.getMerchantId());
		Assert.assertEquals("terminalId", virtualTerminalRefundRequest.getTerminalId());
		Assert.assertEquals(Long.valueOf("24"), virtualTerminalRefundRequest.getTxnAmount());
		Assert.assertEquals("invoiceNumber", virtualTerminalRefundRequest.getInvoiceNumber());
		Assert.assertEquals("txnRefNum", virtualTerminalRefundRequest.getTxnRefNum());
	}

}
