package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class TransactionResponseTest {

	@InjectMocks
	TransactionResponse transactionResponse = new TransactionResponse();

	@Before
	public void setUp() {
		transactionResponse.setTxnRefNumber("txnRefNumber");
		transactionResponse.setAuthId("authId");
		transactionResponse.setCgRefNumber("cgRefNumber");
		transactionResponse.setMerchantCode("merchantCode");
		transactionResponse.setTotalTxnAmount(Double.parseDouble("45.0"));
	}

	@Test
	public void testTransactionResponse() {

		Assert.assertEquals("txnRefNumber", transactionResponse.getTxnRefNumber());
		Assert.assertEquals("authId", transactionResponse.getAuthId());
		Assert.assertEquals("cgRefNumber", transactionResponse.getCgRefNumber());
		Assert.assertEquals("merchantCode", transactionResponse.getMerchantCode());
		Assert.assertEquals(Double.valueOf("45.0"), transactionResponse.getTotalTxnAmount());

	}

}
