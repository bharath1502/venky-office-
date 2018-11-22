package com.chatak.pay.controller.model.topup;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class GetTopupOffersResponseTest {

	@InjectMocks
	GetTopupOffersResponse getTopupOffersResponse=new GetTopupOffersResponse();
	
	@Mock
	List<TopupOfferDetailDTO> topupOfferDetailDTOs;
	
	@Before
	public void setUp() {
		getTopupOffersResponse.setTopupOfferDetailDTOs(topupOfferDetailDTOs);
	}

	@Test
	public void testOption() {
		Assert.assertEquals(topupOfferDetailDTOs, getTopupOffersResponse.getTopupOfferDetailDTOs());
	}



}
