package com.chatak.pay.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TokenizeResponseTest {

	@InjectMocks
	TokenizeResponse tokenizeResponse = new TokenizeResponse();

	@Before
	public void setUp() {
		tokenizeResponse.setVersionNum("abc");
		tokenizeResponse.setPaymentToken("abcd");
		tokenizeResponse.setTokenExpDate("abcd");
		tokenizeResponse.setTokenAssuranceLevel("534");

	}

	@Test
	public void testtokenizeResponse() {
		Assert.assertEquals("abc", tokenizeResponse.getVersionNum());
		Assert.assertEquals("abcd", tokenizeResponse.getPaymentToken());
		Assert.assertEquals("abcd", tokenizeResponse.getTokenExpDate());
		Assert.assertEquals("534", tokenizeResponse.getTokenAssuranceLevel());

	}

}
