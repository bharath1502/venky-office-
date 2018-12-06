package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pay.constants.PaymentProcessTypeEnum;
import com.chatak.pg.enums.CardAssociationEnum;
import com.chatak.switches.enums.TransactionType;
import com.litle.sdk.generate.CountryTypeEnum;
import com.litle.sdk.generate.CurrencyCodeEnum;

@RunWith(MockitoJUnitRunner.class)
public class PaymentDetailsTest {

	@InjectMocks
	PaymentDetails paymentDetails = new PaymentDetails();

	@Before
	public void setUp() {
		paymentDetails = new PaymentDetails(null, null, null, Long.parseLong("123"), null, null, null, null, null, null,
				null, null, null, null, null, null, "100.00", null, null, null, null);
		paymentDetails.setTransactionId(Long.parseLong("45"));
		paymentDetails.setTransactionType(TransactionType.AUTH);
		paymentDetails.setOrderId("23");
		paymentDetails.setTotalAmount(Long.parseLong("45"));
		paymentDetails.setMerchantAmount(Long.parseLong("45"));
		paymentDetails.setCardAssociation(CardAssociationEnum.AX);
		paymentDetails.setDescription("23");
		paymentDetails.setBillerName("23");
		paymentDetails.setBillerEmail("23");
		paymentDetails.setBillerCity("23");
		paymentDetails.setBillerState("23");
		paymentDetails.setBillerCountry(CountryTypeEnum.AD);
		paymentDetails.setBillerZip("23");
		paymentDetails.setAddress("23");
		paymentDetails.setAddress2("23");
		paymentDetails.setReturnURL("23");
		paymentDetails.setMerchantId("23");
		paymentDetails.setClientIP("23");
		paymentDetails.setClientPort(Integer.parseInt("5"));
		paymentDetails.setOriginTime(Long.parseLong("45"));
		paymentDetails.setCurrencyCode(CurrencyCodeEnum.AUD);
		paymentDetails.setFormatedTotalAmt("23");
		paymentDetails.setPaymentProcessTypeEnum(PaymentProcessTypeEnum.IB);
		paymentDetails.setToken("23");
		paymentDetails.setAccessToken("23");
		paymentDetails.setMode("23");
		paymentDetails.setProcessorMid("23");

	}

	@Test
	public void testPaymentDetails() {
		Assert.assertEquals(Long.valueOf("45"), paymentDetails.getTransactionId());
		Assert.assertEquals(TransactionType.AUTH, paymentDetails.getTransactionType());
		Assert.assertEquals("23", paymentDetails.getOrderId());
		Assert.assertEquals(Long.valueOf("45"), paymentDetails.getTotalAmount());
		Assert.assertEquals(Long.valueOf("45"), paymentDetails.getMerchantAmount());
		Assert.assertEquals(CardAssociationEnum.AX, paymentDetails.getCardAssociation());
		Assert.assertEquals("23", paymentDetails.getDescription());
		Assert.assertEquals("23", paymentDetails.getBillerName());
		Assert.assertEquals("23", paymentDetails.getBillerEmail());
		Assert.assertEquals("23", paymentDetails.getBillerCity());
		Assert.assertEquals("23", paymentDetails.getBillerState());
		Assert.assertEquals(CountryTypeEnum.AD, paymentDetails.getBillerCountry());
		Assert.assertEquals("23", paymentDetails.getBillerZip());
		Assert.assertEquals("23", paymentDetails.getAddress());
		Assert.assertEquals("23", paymentDetails.getAddress2());
		Assert.assertEquals("23", paymentDetails.getReturnURL());
		Assert.assertEquals("23", paymentDetails.getMerchantId());
		Assert.assertEquals("23", paymentDetails.getClientIP());
		Assert.assertEquals(Integer.valueOf("5"), paymentDetails.getClientPort());
		Assert.assertEquals(Long.valueOf("45"), paymentDetails.getOriginTime());
		Assert.assertEquals(CurrencyCodeEnum.AUD, paymentDetails.getCurrencyCode());
		Assert.assertEquals("23", paymentDetails.getFormatedTotalAmt());
		Assert.assertEquals(PaymentProcessTypeEnum.IB, paymentDetails.getPaymentProcessTypeEnum());
		Assert.assertEquals("23", paymentDetails.getToken());
		Assert.assertEquals("23", paymentDetails.getAccessToken());
		Assert.assertEquals("23", paymentDetails.getMode());
		Assert.assertEquals("23", paymentDetails.getProcessorMid());
	}

}
