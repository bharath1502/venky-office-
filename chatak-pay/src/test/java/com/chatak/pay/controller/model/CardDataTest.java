package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.litle.sdk.generate.MethodOfPaymentTypeEnum;

@RunWith(MockitoJUnitRunner.class)
public class CardDataTest {

	@InjectMocks
	CardData cardData = new CardData();

	@Before
	public void setUp() {
		cardData.setCardNumber("a");
		cardData.setExpDate("b");
		cardData.setCvv("a");
		cardData.setCardHolderName("b");
		cardData.setCardType(MethodOfPaymentTypeEnum.AX);
		cardData.setTrack1("b");
		cardData.setTrack2("a");
		cardData.setTrack3("b");
		cardData.setTrack("a");
		cardData.setKeySerial("b");
		cardData.setCardHolderEmail("a");
		cardData.setEmv("b");
		cardData.setUid("a");

	}

	@Test
	public void testdeTokenizeRequest() {
		Assert.assertEquals("a", cardData.getCardNumber());
		Assert.assertEquals("b", cardData.getExpDate());
		Assert.assertEquals("a", cardData.getCvv());
		Assert.assertEquals("b", cardData.getCardHolderName());
		Assert.assertEquals(MethodOfPaymentTypeEnum.AX, cardData.getCardType());
		Assert.assertEquals("b", cardData.getTrack1());
		Assert.assertEquals("a", cardData.getTrack2());
		Assert.assertEquals("b", cardData.getTrack3());
		Assert.assertEquals("a", cardData.getTrack());
		Assert.assertEquals("b", cardData.getKeySerial());
		Assert.assertEquals("a", cardData.getCardHolderEmail());
		Assert.assertEquals("b", cardData.getEmv());
		Assert.assertEquals("a", cardData.getUid());

	}
}
