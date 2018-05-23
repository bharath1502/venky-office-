package com.chatak.pay.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MerchantDataTest {

	@InjectMocks
	MerchantData merchantData = new MerchantData();

	@Before
	public void setUp() {
		merchantData.setId(Long.parseLong("43"));
		merchantData.setMerchantName("abc");
		merchantData.setMerchantId("543");
		merchantData.setMerchantCategoryCode("456");
		merchantData.setDailyTxnLimit(Long.parseLong("543"));
		merchantData.setMerchantType("534");
		merchantData.setAllowInternationTxn(true);
		merchantData.setPhone1("123");
		merchantData.setPhone2("456");
		merchantData.setAddress1("ad1");
		merchantData.setAddress2("ad2");
		merchantData.setTin("tin");
		merchantData.setSsn("ssn");
		merchantData.setCity("city");
		merchantData.setZip("zip");
		merchantData.setState("state");
		merchantData.setCreatedDate("create");
		merchantData.setCountry("country");
		merchantData.setValidFromDate("validFromDate");
		merchantData.setStatus("status");
		merchantData.setValidToDate("validToDate");
		merchantData.setUpdatedDate("updatedDate");
		merchantData.setPageSize(Integer.parseInt("3"));
		merchantData.setPageIndex(Integer.parseInt("3"));
		merchantData.setNoOfRecords(Integer.parseInt("3"));

	}

	@Test
	public void testMerchantData() {
		Assert.assertEquals(Long.valueOf("43"), merchantData.getId());
		Assert.assertEquals("abc", merchantData.getMerchantName());
		Assert.assertEquals("543", merchantData.getMerchantId());
		Assert.assertEquals("456", merchantData.getMerchantCategoryCode());
		Assert.assertEquals(Long.valueOf("543"), merchantData.getDailyTxnLimit());
		Assert.assertEquals("534", merchantData.getMerchantType());
		Assert.assertEquals(true, merchantData.getAllowInternationTxn());
		Assert.assertEquals("123", merchantData.getPhone1());
		Assert.assertEquals("456", merchantData.getPhone2());
		Assert.assertEquals("ad1", merchantData.getAddress1());
		Assert.assertEquals("ad2", merchantData.getAddress2());
		Assert.assertEquals("tin", merchantData.getTin());
		Assert.assertEquals("ssn", merchantData.getSsn());
		Assert.assertEquals("city", merchantData.getCity());
		Assert.assertEquals("zip", merchantData.getZip());
		Assert.assertEquals("state", merchantData.getState());
		Assert.assertEquals("create", merchantData.getCreatedDate());
		Assert.assertEquals("country", merchantData.getCountry());
		Assert.assertEquals("validFromDate", merchantData.getValidFromDate());
		Assert.assertEquals("status", merchantData.getStatus());
		Assert.assertEquals("validToDate", merchantData.getValidToDate());
		Assert.assertEquals("updatedDate", merchantData.getUpdatedDate());
		Assert.assertEquals(Integer.valueOf("3"), merchantData.getPageSize());
		Assert.assertEquals(Integer.valueOf("3"), merchantData.getPageIndex());
		Assert.assertEquals(Integer.valueOf("3"), merchantData.getNoOfRecords());

	}
}
