package com.chatak.merchant.model;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class BillingDataTest {

	@InjectMocks
	BillingData billingData = new BillingData();

	@Before
	public void setUp() {
		billingData.setAddress1("BLR");
		billingData.setAddress2("CHE");
		billingData.setCity("MH");
		billingData.setState("KA");
		billingData.setCountry("IN");
		billingData.setZipCode("560037");
		billingData.setEmail("Support@mail.com");
	}

	@Test
	public void testBillingData() {
		Assert.assertEquals("BLR", billingData.getAddress1());
		Assert.assertEquals("CHE", billingData.getAddress2());
		Assert.assertEquals("MH", billingData.getCity());
		Assert.assertEquals("KA", billingData.getState());
		Assert.assertEquals("IN", billingData.getCountry());
		Assert.assertEquals("560037", billingData.getZipCode());
		Assert.assertEquals("Support@mail.com", billingData.getEmail());
	}
}
