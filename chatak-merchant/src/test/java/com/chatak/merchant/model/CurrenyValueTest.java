package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class CurrenyValueTest {

	@InjectMocks
	CurrenyValue currenyValue = new CurrenyValue();

	@Before
	public void setUp() {
		currenyValue.setId(Long.parseLong("12345"));
		currenyValue.setCurrencyName("rupee");
		currenyValue.setCurrencyCodeNumeric("356");
		currenyValue.setCurrencyCodeAlpha("IN");
		currenyValue.setCurrencySymbol('r');
		currenyValue.setCurrencyExponent(Integer.parseInt("2"));
		currenyValue.setCurrencySeparatorPosition(1);
		currenyValue.setCurrencyMinorUnit('p');
		currenyValue.setCurrencyThousandsUnit('a');
		currenyValue.setStatus("Success");

	}

	@Test
	public void testCurrenyValue() {
		Assert.assertEquals(Long.valueOf("12345"), currenyValue.getId());
		Assert.assertEquals("rupee", currenyValue.getCurrencyName());
		Assert.assertEquals("356", currenyValue.getCurrencyCodeNumeric());
		Assert.assertEquals("IN", currenyValue.getCurrencyCodeAlpha());
		Assert.assertEquals(Character.valueOf('r'), currenyValue.getCurrencySymbol());
		Assert.assertEquals(Integer.valueOf("2"), currenyValue.getCurrencyExponent());
		Assert.assertEquals(Integer.valueOf(1), currenyValue.getCurrencySeparatorPosition());
		Assert.assertEquals(Character.valueOf('p'), currenyValue.getCurrencyMinorUnit());
		Assert.assertEquals(Character.valueOf('a'), currenyValue.getCurrencyThousandsUnit());
		Assert.assertEquals("Success", currenyValue.getStatus());

	}
}
