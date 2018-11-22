package com.chatak.pay.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TokenizeRequestTest {

	@InjectMocks
	TokenizeRequest tokenizeRequest = new TokenizeRequest();

	@Before
	public void setUp() {
		tokenizeRequest.setVersionNum("abc");
		tokenizeRequest.setTokenRequestorId(Long.parseLong("43"));
		tokenizeRequest.setPanNum("abcd");
		tokenizeRequest.setExpDate("456");
		tokenizeRequest.setTokenAssuranceLevel("abcd");
		tokenizeRequest.setStorageLoc("534");
		tokenizeRequest.setIdvPerformed(Integer.parseInt("543"));
		tokenizeRequest.setAccVerificationResults(Integer.parseInt("543"));
		tokenizeRequest.setAccVerificationRefNum("abc");
		tokenizeRequest.setCardHolderDataLen(Integer.parseInt("543"));
		tokenizeRequest.setCardHolderData("456");
		tokenizeRequest.setTokenDeviceInfolen(Integer.parseInt("543"));
		tokenizeRequest.setDeviceInfoData("534");

	}

	@Test
	public void testtokenizeRequest() {
		Assert.assertEquals("abc", tokenizeRequest.getVersionNum());
		Assert.assertEquals(Long.valueOf("43"), tokenizeRequest.getTokenRequestorId());
		Assert.assertEquals("abcd", tokenizeRequest.getPanNum());
		Assert.assertEquals("456", tokenizeRequest.getExpDate());
		Assert.assertEquals("abcd", tokenizeRequest.getTokenAssuranceLevel());
		Assert.assertEquals("534", tokenizeRequest.getStorageLoc());
		Assert.assertEquals(Integer.valueOf("543"), tokenizeRequest.getIdvPerformed());
		Assert.assertEquals(Integer.valueOf("543"), tokenizeRequest.getAccVerificationResults());
		Assert.assertEquals("abc", tokenizeRequest.getAccVerificationRefNum());
		Assert.assertEquals(Integer.valueOf("543"), tokenizeRequest.getCardHolderDataLen());
		Assert.assertEquals("456", tokenizeRequest.getCardHolderData());
		Assert.assertEquals(Integer.valueOf("543"), tokenizeRequest.getTokenDeviceInfolen());
		Assert.assertEquals("534", tokenizeRequest.getDeviceInfoData());

	}

}
