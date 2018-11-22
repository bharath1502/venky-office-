package com.chatak.pay.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class MerchantSearchResponseTest {

	@InjectMocks
	MerchantSearchResponse merchantSearchResponse = new MerchantSearchResponse();

	@Before
	public void setUp() {
		List<MerchantData> merchants=new ArrayList<>();
		merchantSearchResponse.setMerchants(merchants);
	}

	@Test
	public void testMerchantSearchResponse() {
		List<MerchantData> merchants=new ArrayList<>();
		Assert.assertEquals(merchants, merchantSearchResponse.getMerchants());
	}

}
