package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class TransactionResponseTest {

	@InjectMocks
	TransactionResponse transactionResponse = new TransactionResponse();

	@Mock
	private PaymentDetails paymentDetails;

	@Before
	public void setUp() {
		transactionResponse.setTxnRefNumber("45");
		transactionResponse.setAuthId("45");
		transactionResponse.setPaymentDetails(paymentDetails);
		transactionResponse.setCgRefNumber("45");
		transactionResponse.setMerchantCode("45");
		transactionResponse.setTotalAmount(Long.parseLong("45"));
		transactionResponse.setTotalTxnAmount(Double.parseDouble("543"));
		transactionResponse.setMerchantName("45");
		transactionResponse.setCustomerBalance("45");
		transactionResponse.setCurrency("45");
		transactionResponse.setTxnId("45");
		transactionResponse.setProcTxnId("45");
		transactionResponse.setMerchantId("45");
		transactionResponse.setTerminalId("45");
	}

	@Test
	public void testtransactionResponse() {
		Assert.assertEquals("45", transactionResponse.getTxnRefNumber());
		Assert.assertEquals("45", transactionResponse.getAuthId());
		Assert.assertEquals(paymentDetails, transactionResponse.getPaymentDetails());
		Assert.assertEquals("45", transactionResponse.getCgRefNumber());
		Assert.assertEquals("45", transactionResponse.getMerchantCode());
		Assert.assertEquals(Long.valueOf("45"), transactionResponse.getTotalAmount());
		Assert.assertEquals(Double.valueOf("543"), transactionResponse.getTotalTxnAmount());
		Assert.assertEquals("45", transactionResponse.getMerchantName());
		Assert.assertEquals("45", transactionResponse.getCustomerBalance());
		Assert.assertEquals("45", transactionResponse.getCurrency());
		Assert.assertEquals("45", transactionResponse.getTxnId());
		Assert.assertEquals("45", transactionResponse.getProcTxnId());
		Assert.assertEquals("45", transactionResponse.getMerchantId());
		Assert.assertEquals("45", transactionResponse.getTerminalId());
	}

}
