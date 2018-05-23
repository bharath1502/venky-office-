package com.chatak.pay.controller.model.topup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TopupOfferDetailDTOTest {
	
	@InjectMocks
	TopupOfferDetailDTO topupOfferDetailDTO = new TopupOfferDetailDTO();

	@Before
	public void setUp() {
		topupOfferDetailDTO.setTopupOfferID(Long.parseLong("123"));
		topupOfferDetailDTO.setCategoryID(Long.parseLong("123"));
		topupOfferDetailDTO.setRechargeAmount("123");
		topupOfferDetailDTO.setTalktime("123");
		topupOfferDetailDTO.setDescription("123");
		topupOfferDetailDTO.setValidity("123");

	}

	@Test
	public void testTopupCategoryDTO() {
		Assert.assertEquals(Long.valueOf("123"), topupOfferDetailDTO.getTopupOfferID());
		Assert.assertEquals(Long.valueOf("123"), topupOfferDetailDTO.getCategoryID());
		Assert.assertEquals("123", topupOfferDetailDTO.getRechargeAmount());
		Assert.assertEquals("123", topupOfferDetailDTO.getTalktime());
		Assert.assertEquals("123", topupOfferDetailDTO.getDescription());
		Assert.assertEquals("123", topupOfferDetailDTO.getValidity());
	}
}
