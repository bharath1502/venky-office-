package com.chatak.pay.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class TSMResponseTest {

	@InjectMocks
	TSMResponse tSMResponse = new TSMResponse();

	@Before
	public void setUp() {
		tSMResponse.setTerminalId("abc");
		tSMResponse.setUpdateType("xyz");
		tSMResponse.setUpdateURL("abc");
		tSMResponse.setUpdateVersion("abc");

	}

	@Test
	public void testTSMResponse() {
		Assert.assertEquals("abc", tSMResponse.getTerminalId());
		Assert.assertEquals("xyz", tSMResponse.getUpdateType());
		Assert.assertEquals("abc", tSMResponse.getUpdateURL());
		Assert.assertEquals("abc", tSMResponse.getUpdateVersion());

	}

}
