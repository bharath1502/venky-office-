package com.chatak.pay.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TSMRequestTest {

	@InjectMocks
	TSMRequest tSMRequest = new TSMRequest();

	@Before
	public void setUp() {
		tSMRequest.setMerchantCode("abc");
		tSMRequest.setDeviceSerial("xyz");
		tSMRequest.setCurrentAppVersion("abc");
		tSMRequest.setTid("abc");

	}

	@Test
	public void testTSMRequest() {
		Assert.assertEquals("abc", tSMRequest.getMerchantCode());
		Assert.assertEquals("xyz", tSMRequest.getDeviceSerial());
		Assert.assertEquals("abc", tSMRequest.getCurrentAppVersion());
		Assert.assertEquals("abc", tSMRequest.getTid());

	}

}
