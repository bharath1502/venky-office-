package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class VirtualTerminalAdjustmentRequestTest {

	@InjectMocks
	VirtualTerminalAdjustmentRequest virtualTerminalAdjustmentRequest = new VirtualTerminalAdjustmentRequest();

	@Before
	public void setUp() {
		virtualTerminalAdjustmentRequest.setExpDate("expDate");
		virtualTerminalAdjustmentRequest.setMerchantId("merchantId");
		virtualTerminalAdjustmentRequest.setTerminalId("terminalId");
		virtualTerminalAdjustmentRequest.setInvoiceNumber("invoiceNumber");
		virtualTerminalAdjustmentRequest.setTxnAmount(Long.parseLong("5435"));
		virtualTerminalAdjustmentRequest.setCardNum("cardNum");
		virtualTerminalAdjustmentRequest.setAuthId("authId");
		virtualTerminalAdjustmentRequest.setAdjAmount(Long.parseLong("7865"));
		virtualTerminalAdjustmentRequest.setTxnRefNum("txnRefNum");

	}

	@Test
	public void testVirtualTerminalAdjustmentRequest() {
		Assert.assertEquals("expDate", virtualTerminalAdjustmentRequest.getExpDate());
		Assert.assertEquals("merchantId", virtualTerminalAdjustmentRequest.getMerchantId());
		Assert.assertEquals("terminalId", virtualTerminalAdjustmentRequest.getTerminalId());
		Assert.assertEquals("invoiceNumber", virtualTerminalAdjustmentRequest.getInvoiceNumber());
		Assert.assertEquals(Long.valueOf("5435"), virtualTerminalAdjustmentRequest.getTxnAmount());
		Assert.assertEquals("cardNum", virtualTerminalAdjustmentRequest.getCardNum());
		Assert.assertEquals("authId", virtualTerminalAdjustmentRequest.getAuthId());
		Assert.assertEquals(Long.valueOf("7865"), virtualTerminalAdjustmentRequest.getAdjAmount());
		Assert.assertEquals("txnRefNum", virtualTerminalAdjustmentRequest.getTxnRefNum());

	}

}
