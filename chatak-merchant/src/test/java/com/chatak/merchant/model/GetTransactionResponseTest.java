package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class GetTransactionResponseTest {

	@InjectMocks
	GetTransactionResponse getTransactionResponse = new GetTransactionResponse();

	@Before
	public void setUp() {
		getTransactionResponse.setCardNum("CardNum");
		getTransactionResponse.setCvv2("Cvv2");
		getTransactionResponse.setCardHolderName("Name");
		getTransactionResponse.setExpDate("ExpDate");
		getTransactionResponse.setMerchantId("Mid");
		getTransactionResponse.setTerminalId("Tid");
		getTransactionResponse.setSubTotal(Double.parseDouble("4.5"));
		getTransactionResponse.setTaxAmt(Double.parseDouble("6.5"));
		getTransactionResponse.setTipAmount(Double.parseDouble("2.5"));
		getTransactionResponse.setShippingAmt(Double.parseDouble("1.5"));
		getTransactionResponse.setTxnAmount(Double.parseDouble("20.5"));
		getTransactionResponse.setStreet("Street");
		getTransactionResponse.setCity("City");
		getTransactionResponse.setZip("Zip");
		getTransactionResponse.setInvoiceNumber("Invoice");
		getTransactionResponse.setAuthId("Aid");
		getTransactionResponse.setTxnRefNum("RefNum");
		getTransactionResponse.setCgRefNumber("cgRefNumber");
		getTransactionResponse.setSettlementStatus("settlementStatus");
		getTransactionResponse.setFeeAmount(Double.parseDouble("11.5"));
		getTransactionResponse.setCardNumMasked("cardNumMasked");
		getTransactionResponse.setTotalRefundableBalance(Double.parseDouble("45.2"));

	}

	@Test
	public void testGetTransactionResponse() {

		Assert.assertEquals("CardNum", getTransactionResponse.getCardNum());
		Assert.assertEquals("Cvv2", getTransactionResponse.getCvv2());
		Assert.assertEquals("Name", getTransactionResponse.getCardHolderName());
		Assert.assertEquals("ExpDate", getTransactionResponse.getExpDate());
		Assert.assertEquals("Mid", getTransactionResponse.getMerchantId());
		Assert.assertEquals("Tid", getTransactionResponse.getTerminalId());
		Assert.assertEquals(Double.valueOf("4.5"), getTransactionResponse.getSubTotal());
		Assert.assertEquals(Double.valueOf("6.5"), getTransactionResponse.getTaxAmt());
		Assert.assertEquals(Double.valueOf("2.5"), getTransactionResponse.getTipAmount());
		Assert.assertEquals(Double.valueOf("1.5"), getTransactionResponse.getShippingAmt());
		Assert.assertEquals(Double.valueOf("20.5"), getTransactionResponse.getTxnAmount());
		Assert.assertEquals("Street", getTransactionResponse.getStreet());
		Assert.assertEquals("City", getTransactionResponse.getCity());
		Assert.assertEquals("Zip", getTransactionResponse.getZip());
		Assert.assertEquals("Invoice", getTransactionResponse.getInvoiceNumber());
		Assert.assertEquals("Aid", getTransactionResponse.getAuthId());
		Assert.assertEquals("RefNum", getTransactionResponse.getTxnRefNum());
		Assert.assertEquals("cgRefNumber", getTransactionResponse.getCgRefNumber());
		Assert.assertEquals("settlementStatus", getTransactionResponse.getSettlementStatus());
		Assert.assertEquals(Double.valueOf("11.5"), getTransactionResponse.getFeeAmount());
		Assert.assertEquals("cardNumMasked", getTransactionResponse.getCardNumMasked());
		Assert.assertEquals(Double.valueOf("45.2"), getTransactionResponse.getTotalRefundableBalance());

	}

}
