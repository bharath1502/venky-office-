package com.chatak.pay.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)

public class DETokenizeResponseTest {

	@InjectMocks
	DETokenizeResponse deTokenizeResponse = new DETokenizeResponse();

	@Before
	public void setUp() {
		deTokenizeResponse.setVersionNum("123");
		deTokenizeResponse.setStatus("456");
		deTokenizeResponse.setPan("2");
		deTokenizeResponse.setPanExpDate("21/20/20");
		deTokenizeResponse.setTransDataLen(Integer.parseInt("2"));
		deTokenizeResponse.setTransDataElements("21/20/20");
	}

	@Test
	public void testdeTokenizeResponse() {
		Assert.assertEquals("123", deTokenizeResponse.getVersionNum());
		Assert.assertEquals("456", deTokenizeResponse.getStatus());
		Assert.assertEquals("2", deTokenizeResponse.getPan());
		Assert.assertEquals("21/20/20", deTokenizeResponse.getPanExpDate());
		Assert.assertEquals(Integer.valueOf("2"), deTokenizeResponse.getTransDataLen());
		Assert.assertEquals("21/20/20", deTokenizeResponse.getTransDataElements());
	}
}
