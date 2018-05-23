package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.bean.BillingData;
import com.chatak.pg.bean.CardTokenData;

@RunWith(MockitoJUnitRunner.class)
public class TransactionRequestTest {

	@InjectMocks
	TransactionRequest transactionRequest = new TransactionRequest();
	
	@Mock
	private CardData cardData;
	
	@Mock
	private BillingData billingData;
	
	@Mock
	  private SplitTxnData splitTxnData;
	
	@Mock
	 private CardTokenData cardTokenData;
	
	

	@Before
	public void setUp() {
		transactionRequest.setTxnAmount(Long.parseLong("45"));
		transactionRequest.setMerchantName("45");
		transactionRequest.setInvoiceNumber("45");
		transactionRequest.setRegisterNumber("45");
		transactionRequest.setCardToken("45");
		transactionRequest.setCardData(cardData);
		transactionRequest.setBillingData(billingData);
		transactionRequest.setOrderId("45");
		transactionRequest.setTxnRefNumber("45");
		transactionRequest.setAuthId("45");
		transactionRequest.setIp_port("45");
		transactionRequest.setCgRefNumber("45");
		transactionRequest.setMerchantAmount(Long.parseLong("45"));
		transactionRequest.setFeeAmount(Long.parseLong("45"));
		transactionRequest.setTotalTxnAmount(Long.parseLong("45"));
		transactionRequest.setDescription("45");
		transactionRequest.setSplitTxnData(splitTxnData);
		transactionRequest.setSplitRefNumber("45");
		transactionRequest.setMobileNumber("45");
		transactionRequest.setAccountNumber("45");
		transactionRequest.setCardTokenData(cardTokenData);
		transactionRequest.setQrCode("45");
		transactionRequest.setCurrencyCode("45");
		transactionRequest.setUserName("45");
	}

	@Test
	public void testTransactionRequest() {
		Assert.assertEquals(Long.valueOf("45"), transactionRequest.getTxnAmount());
		Assert.assertEquals("45", transactionRequest.getMerchantName());
		Assert.assertEquals("45", transactionRequest.getInvoiceNumber());
		Assert.assertEquals("45", transactionRequest.getRegisterNumber());
		Assert.assertEquals("45", transactionRequest.getCardToken());
		Assert.assertEquals(cardData, transactionRequest.getCardData());
		Assert.assertEquals(billingData, transactionRequest.getBillingData());
		Assert.assertEquals("45", transactionRequest.getOrderId());
		Assert.assertEquals("45", transactionRequest.getTxnRefNumber());
		Assert.assertEquals("45", transactionRequest.getAuthId());
		Assert.assertEquals("45", transactionRequest.getIp_port());
		Assert.assertEquals("45", transactionRequest.getCgRefNumber());
		Assert.assertEquals(Long.valueOf("45"), transactionRequest.getMerchantAmount());
		Assert.assertEquals(Long.valueOf("45"), transactionRequest.getFeeAmount());
		Assert.assertEquals(Long.valueOf("45"), transactionRequest.getTotalTxnAmount());
		Assert.assertEquals("45", transactionRequest.getDescription());
		Assert.assertEquals(splitTxnData, transactionRequest.getSplitTxnData());
		Assert.assertEquals("45", transactionRequest.getSplitRefNumber());
		Assert.assertEquals("45", transactionRequest.getMobileNumber());
		Assert.assertEquals("45", transactionRequest.getAccountNumber());
		Assert.assertEquals(cardTokenData, transactionRequest.getCardTokenData());
		Assert.assertEquals("45", transactionRequest.getQrCode());
		Assert.assertEquals("45", transactionRequest.getCurrencyCode());
		Assert.assertEquals("45", transactionRequest.getUserName());
	}

}
