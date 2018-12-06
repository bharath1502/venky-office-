package com.chatak.pay.controller.model.topup;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IssuanceTopupRequestTest {

	@InjectMocks
	IssuanceTopupRequest issuanceTopupRequest = new IssuanceTopupRequest();

	@Mock
	List<TopupOfferDetailDTO> topupOfferDetailDTOs;

	@Before
	public void setUp() {
		issuanceTopupRequest.setCategoryId(Integer.parseInt("123"));
		issuanceTopupRequest.setOperatorId(Integer.parseInt("123"));
		issuanceTopupRequest.setTopupOfferId(Integer.parseInt("123"));
		issuanceTopupRequest.setAmount("123");
		issuanceTopupRequest.setMobileNumber("123");

	}

	@Test
	public void testOption() {
		Assert.assertEquals(Integer.valueOf("123"), issuanceTopupRequest.getCategoryId());
		Assert.assertEquals(Integer.valueOf("123"), issuanceTopupRequest.getOperatorId());
		Assert.assertEquals(Integer.valueOf("123"), issuanceTopupRequest.getTopupOfferId());
		Assert.assertEquals("123", issuanceTopupRequest.getAmount());
		Assert.assertEquals("123", issuanceTopupRequest.getMobileNumber());

	}
}
