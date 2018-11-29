package com.chatak.pay.constants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChatakPayErrorCodeTest {

	@Test
	public void testChatakPayErrorCode() {
		ChatakPayErrorCode.values();
		ChatakPayErrorCode.fromValue("Invalid amount.");
		ChatakPayErrorCode.TXN_0999.value();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testChatakPayErrorCodeException() {
		ChatakPayErrorCode.values();
		ChatakPayErrorCode.fromValue("Invalid");
	}
}
