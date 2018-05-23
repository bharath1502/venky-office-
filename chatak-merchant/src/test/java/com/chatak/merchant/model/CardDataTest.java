package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class CardDataTest {

	@InjectMocks
	CardData cardData = new CardData();

	@Before
	public void setUp() {
		cardData.setCardNumber("123232");
		cardData.setExpDate("1-3-1989");
		cardData.setCvv("5566");
		cardData.setCardHolderName("chatak");
		cardData.setTrack1("track1");
		cardData.setTrack2("track2");
		cardData.setTrack3("track3");
		cardData.setCardType("VISA");
	}

	@Test
	public void testCardData() {
		Assert.assertEquals("123232", cardData.getCardNumber());
		Assert.assertEquals("1-3-1989", cardData.getExpDate());
		Assert.assertEquals("5566", cardData.getCvv());
		Assert.assertEquals("chatak", cardData.getCardHolderName());
		Assert.assertEquals("track1", cardData.getTrack1());
		Assert.assertEquals("track2", cardData.getTrack2());
		Assert.assertEquals("track3", cardData.getTrack3());
		Assert.assertEquals("VISA", cardData.getCardType());
	}

}