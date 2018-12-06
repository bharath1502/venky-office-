package com.chatak.pay.constants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PaymentProcessTypeEnumTest {

	@Test
	public void testPaymentProcessTypeEnum() {
		PaymentProcessTypeEnum.values();
		PaymentProcessTypeEnum.fromValue("3");
		PaymentProcessTypeEnum.C.value();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPaymentProcessTypeEnumException() {
		PaymentProcessTypeEnum.values();
		PaymentProcessTypeEnum.fromValue("15");
	}

}
