package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.enums.CardAssociationEnum;
import com.chatak.pg.enums.FeeTypeEnum;

@RunWith(MockitoJUnitRunner.class)
public class FeeDataTest {

	@InjectMocks
	FeeData feeData = new FeeData();

	@Before
	public void setUp() {
		feeData.setFeeCode("a");
		feeData.setFeeDescription("b");
		feeData.setFeeType(FeeTypeEnum.FIXED);
		feeData.setFeeValue(Long.parseLong("534"));
		feeData.setAmountRange("b");
		feeData.setCardType(CardAssociationEnum.AX);
	}

	@Test
	public void testdeTokenizeRequest() {
		Assert.assertEquals("a", feeData.getFeeCode());
		Assert.assertEquals("b", feeData.getFeeDescription());
		Assert.assertEquals(FeeTypeEnum.FIXED, feeData.getFeeType());
		Assert.assertEquals(Long.valueOf("534"), feeData.getFeeValue());
		Assert.assertEquals("b", feeData.getAmountRange());
		Assert.assertEquals(CardAssociationEnum.AX, feeData.getCardType());
	}

}
