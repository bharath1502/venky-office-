package com.chatak.switches.enums;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionTypeTest {

	@Test
	public void testTransactionType() {
		TransactionType.AUTH.values();

	}

}
