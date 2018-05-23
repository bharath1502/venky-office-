package com.chatak.merchant.constants;

import org.junit.Test;

public class ChatakMerchantErrorCodeTest {

	@Test
	public void testChatakMerchantErrorCode() {
		ChatakMerchantErrorCode.values();
		ChatakMerchantErrorCode.TXN_0999.value();
		ChatakMerchantErrorCode.fromValue("Invalid amount.");

	}

	@Test(expected = IllegalArgumentException.class)
	public void testChatakMerchantErrorCodeException() {
		ChatakMerchantErrorCode.values();
		ChatakMerchantErrorCode.TXN_0999.value();
		ChatakMerchantErrorCode.fromValue("Invalid");

	}

}
