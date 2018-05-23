package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class TransactionRequestTest {
	@InjectMocks
	TransactionRequest transactionRequest = new TransactionRequest();

	@Mock
	private CardData cardData;

	@Before
	public void setUp() {
		transactionRequest.setCardToken("cardToken");
		transactionRequest.setCardData(cardData);
		transactionRequest.setRegisterNumber("registerNumber");
		transactionRequest.setOrderId("orderId");
		transactionRequest.setAuthId("authId");
		transactionRequest.setCgRefNumber("cgRefNumber");
		transactionRequest.setMerchantAmount(Long.parseLong("1243"));
		transactionRequest.setIp_port("ip_port");
		transactionRequest.setFeeAmount(Long.parseLong("5435"));
	}

	@Test
	public void testTransactionRequest() {

		Assert.assertEquals("cardToken", transactionRequest.getCardToken());
		Assert.assertEquals(cardData, transactionRequest.getCardData());
		Assert.assertEquals("registerNumber", transactionRequest.getRegisterNumber());
		Assert.assertEquals("orderId", transactionRequest.getOrderId());
		Assert.assertEquals("authId", transactionRequest.getAuthId());
		Assert.assertEquals("cgRefNumber", transactionRequest.getCgRefNumber());
		Assert.assertEquals(Long.valueOf("1243"), transactionRequest.getMerchantAmount());
		Assert.assertEquals("ip_port", transactionRequest.getIp_port());
		Assert.assertEquals(Long.valueOf("5435"), transactionRequest.getFeeAmount());

	}
}
