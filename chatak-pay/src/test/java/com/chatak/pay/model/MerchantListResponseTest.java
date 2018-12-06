package com.chatak.pay.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class MerchantListResponseTest {
	
	@InjectMocks
	MerchantListResponse merchantListResponse = new MerchantListResponse();

	@Before
	public void setUp() {
		List<Merchant> merchants=new ArrayList<>();
		merchantListResponse.setMerchants(merchants);
		
	}

	@Test
	public void testMerchantListResponse() {
		List<Merchant> merchants=new ArrayList<>();
		Assert.assertEquals(merchants, merchantListResponse.getMerchants());
		
	}

}
