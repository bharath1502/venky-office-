package com.chatak.merchant.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class MerchantSearchResponseTest {
	
	@InjectMocks
	MerchantSearchResponse merchantSearchResponse = new MerchantSearchResponse();
	
	@Mock
	private List<MerchantData> merchants;

	@Before
	public void setUp() {
		merchantSearchResponse.setMerchants(merchants);
	}
	
	@Test
	public void testMerchantSearchResponse() {

		Assert.assertEquals(merchants, merchantSearchResponse.getMerchants());
}
}