package com.litle.sdk.generate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.switches.sb.exception.ChatakInvalidTransactionException;

@RunWith(MockitoJUnitRunner.class)
public class CountryTypeEnumTest {

	@Test
	public void testCountryTypeEnum() {
		CountryTypeEnum.values();
		CountryTypeEnum.SS.value();

	}

	@Test
	public void testFromValue() throws ChatakInvalidTransactionException {
		CountryTypeEnum.fromValue("VU");
	}

	@Test(expected = ChatakInvalidTransactionException.class)
	public void testFromValueElse() throws ChatakInvalidTransactionException {
		CountryTypeEnum.fromValue("abc");
	}

}
