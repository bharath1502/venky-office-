package com.litle.sdk.generate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MethodOfPaymentTypeEnumTest {

	@Test
	public void testMethodOfPaymentTypeEnum() {
		MethodOfPaymentTypeEnum.values();
		MethodOfPaymentTypeEnum.MC.value();
		MethodOfPaymentTypeEnum.fromValue("ME");

	}

	@Test(expected = IllegalArgumentException.class)
	public void testMethodOfPaymentTypeEnumElse() {
		MethodOfPaymentTypeEnum.values();
		MethodOfPaymentTypeEnum.MC.value();
		MethodOfPaymentTypeEnum.fromValue("MEU");

	}

}
