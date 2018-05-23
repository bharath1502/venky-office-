package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.litle.sdk.generate.MethodOfPaymentTypeEnum;

@RunWith(MockitoJUnitRunner.class)
public class CardDetailsTest {

	@InjectMocks
	CardDetails cardDetails = new CardDetails();

	@Before
	public void setUp() {
		cardDetails.setNumber("a");
		cardDetails.setExpMonthYear("b");
		cardDetails.setCvv("a");
		cardDetails.setType(MethodOfPaymentTypeEnum.AX);
		cardDetails.setRequestId("b");
		cardDetails.setName("a");
		cardDetails.setSaveCard("b");
		cardDetails.setEmail("a");
	}

	@Test
	public void testdeTokenizeRequest() {
		Assert.assertEquals("a", cardDetails.getNumber());
		Assert.assertEquals("b", cardDetails.getExpMonthYear());
		Assert.assertEquals("a", cardDetails.getCvv());
		Assert.assertEquals(MethodOfPaymentTypeEnum.AX, cardDetails.getType());
		Assert.assertEquals("b", cardDetails.getRequestId());
		Assert.assertEquals("a", cardDetails.getName());
		Assert.assertEquals("b", cardDetails.getSaveCard());
		Assert.assertEquals("a", cardDetails.getEmail());
	}

}
