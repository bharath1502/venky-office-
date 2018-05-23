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
public class GetTopupCategoriesResponseTest {

	@InjectMocks
	GetTopupCategoriesResponse getTopupCategoriesResponse=new GetTopupCategoriesResponse();
	
	@Mock
	private List<TopupCategoryDTO> topupOfferCategoryDTOs;
	
	@Before
	public void setUp() {
		getTopupCategoriesResponse.setTopupOfferCategoryDTOs(topupOfferCategoryDTOs);
	}

	@Test
	public void testOption() {
		Assert.assertEquals(topupOfferCategoryDTOs, getTopupCategoriesResponse.getTopupOfferCategoryDTOs());
	}



}
