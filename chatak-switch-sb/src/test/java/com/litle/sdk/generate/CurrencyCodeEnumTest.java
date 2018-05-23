package com.litle.sdk.generate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyCodeEnumTest {

	@Test
	public void testCurrencyCodeEnum() {
		CurrencyCodeEnum.values();
		CurrencyCodeEnum.AUD.value();
		CurrencyCodeEnum.fromValue("AUD");

	}

}
