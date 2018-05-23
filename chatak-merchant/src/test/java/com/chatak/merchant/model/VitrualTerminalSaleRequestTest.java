package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class VitrualTerminalSaleRequestTest {

	@InjectMocks
	VitrualTerminalSaleRequest vitrualTerminalSaleRequest = new VitrualTerminalSaleRequest();

	@Before
	public void setUp() {
		vitrualTerminalSaleRequest.setCardNum("cardNum");
		vitrualTerminalSaleRequest.setExpDate("expDate");
		vitrualTerminalSaleRequest.setMerchantId("merchantId");
		vitrualTerminalSaleRequest.setTerminalId("terminalId");
		vitrualTerminalSaleRequest.setTxnAmount(Long.parseLong("456"));
		vitrualTerminalSaleRequest.setInvoiceNumber("invoiceNumber");

	}

	@Test
	public void testVitrualTerminalSaleRequest() {
		Assert.assertEquals("cardNum", vitrualTerminalSaleRequest.getCardNum());
		Assert.assertEquals("expDate", vitrualTerminalSaleRequest.getExpDate());
		Assert.assertEquals("merchantId", vitrualTerminalSaleRequest.getMerchantId());
		Assert.assertEquals("terminalId", vitrualTerminalSaleRequest.getTerminalId());
		Assert.assertEquals(Long.valueOf("456"), vitrualTerminalSaleRequest.getTxnAmount());
		Assert.assertEquals("invoiceNumber", vitrualTerminalSaleRequest.getInvoiceNumber());

	}

}
