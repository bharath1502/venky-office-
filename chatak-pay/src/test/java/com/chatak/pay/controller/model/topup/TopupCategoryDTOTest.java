package com.chatak.pay.controller.model.topup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TopupCategoryDTOTest {

	@InjectMocks
	TopupCategoryDTO topupCategoryDTO = new TopupCategoryDTO();

	@Before
	public void setUp() {
		topupCategoryDTO.setCategoryID(Long.parseLong("123"));
		topupCategoryDTO.setOperatorID(Long.parseLong("123"));
		topupCategoryDTO.setCategoryName("123");

	}

	@Test
	public void testTopupCategoryDTO() {
		Assert.assertEquals(Long.valueOf("123"), topupCategoryDTO.getCategoryID());
		Assert.assertEquals(Long.valueOf("123"), topupCategoryDTO.getOperatorID());
		Assert.assertEquals("123", topupCategoryDTO.getCategoryName());
	}

}
