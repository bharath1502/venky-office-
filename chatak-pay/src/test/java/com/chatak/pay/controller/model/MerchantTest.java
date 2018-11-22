package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MerchantTest {

	@InjectMocks
	Merchant merchant = new Merchant();

	@Before
	public void setUp() {
		merchant.setId(Long.parseLong("45"));
		merchant.setMerchantId("45");
		merchant.setMerchantType("23");
		merchant.setDailyTxnLimit(Long.parseLong("45"));
		merchant.setMerchantName("23");
		merchant.setMerchantCategoryCode("23");
		merchant.setAllowInternationTxn(true);
		merchant.setPhone1("23");
		merchant.setAddress1("23");
		merchant.setAddress2("23");
		merchant.setPhone2("23");
		merchant.setTin("23");
		merchant.setSsn("23");
		merchant.setNoOfRecords(Integer.parseInt("5"));
		merchant.setCity("23");
		merchant.setState("23");
		merchant.setZip("23");
		merchant.setCountry("23");
		merchant.setCreatedDate("23");
		merchant.setPageSize(Integer.parseInt("5"));
		merchant.setValidToDate("23");
		merchant.setStatus("23");
		merchant.setValidFromDate("23");
		merchant.setUpdatedDate("23");
		merchant.setPageIndex(Integer.parseInt("5"));
	}

	@Test
	public void testMerchant() {
		Assert.assertEquals(Long.valueOf("45"), merchant.getId());
		Assert.assertEquals("45", merchant.getMerchantId());
		Assert.assertEquals("23", merchant.getMerchantType());
		Assert.assertEquals(Long.valueOf("45"), merchant.getDailyTxnLimit());
		Assert.assertEquals("23", merchant.getMerchantName());
		Assert.assertEquals("23", merchant.getMerchantCategoryCode());
		Assert.assertEquals(true, merchant.getAllowInternationTxn());
		Assert.assertEquals("23", merchant.getPhone1());
		Assert.assertEquals("23", merchant.getAddress1());
		Assert.assertEquals("23", merchant.getAddress2());
		Assert.assertEquals("23", merchant.getPhone2());
		Assert.assertEquals("23", merchant.getTin());
		Assert.assertEquals("23", merchant.getSsn());
		Assert.assertEquals(Integer.valueOf("5"), merchant.getNoOfRecords());
		Assert.assertEquals("23", merchant.getCity());
		Assert.assertEquals("23", merchant.getState());
		Assert.assertEquals("23", merchant.getZip());
		Assert.assertEquals("23", merchant.getCountry());
		Assert.assertEquals("23", merchant.getCreatedDate());
		Assert.assertEquals(Integer.valueOf("5"), merchant.getPageSize());
		Assert.assertEquals("23", merchant.getValidToDate());
		Assert.assertEquals("23", merchant.getStatus());
		Assert.assertEquals("23", merchant.getValidFromDate());
		Assert.assertEquals("23", merchant.getUpdatedDate());
		Assert.assertEquals(Integer.valueOf("5"), merchant.getPageIndex());
	}

}
