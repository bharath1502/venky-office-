package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class MerchantDataTest {

	@InjectMocks
	MerchantData merchantData = new MerchantData();

	@Before
	public void setUp() {
		merchantData.setId(Long.parseLong("12345"));
		merchantData.setMerchantId("mid");
		merchantData.setMerchantName("Name");
		merchantData.setMerchantCategoryCode("123");
		merchantData.setMerchantType("Mtype");
		merchantData.setDailyTxnLimit(Long.parseLong("12345"));
		merchantData.setAllowInternationTxn(true);
		merchantData.setPhone1("phone1");
		merchantData.setPhone2("phone2");
		merchantData.setAddress1("address1");
		merchantData.setAddress2("address2");
		merchantData.setTin("tin");
		merchantData.setSsn("ssn");
		merchantData.setZip("zip");
		merchantData.setCity("city");
		merchantData.setState("state");
		merchantData.setCountry("country");
		merchantData.setCreatedDate("createdDate");
		merchantData.setValidFromDate("validFromDate");
		merchantData.setValidToDate("validToDate");
		merchantData.setStatus("status");
		merchantData.setUpdatedDate("updatedDate");
		merchantData.setPageIndex(Integer.parseInt("1"));
		merchantData.setPageSize(Integer.parseInt("20"));
		merchantData.setNoOfRecords(Integer.parseInt("40"));
		merchantData.setBusinessStartDate("businessStartDate");
		merchantData.setNoOfEmployee("noOfEmployee");
		merchantData.setRole("role");
		merchantData.setFeeProgram("feeProgram");
		merchantData.setProcessor("processor");
		merchantData.setRefunds(true);
		merchantData.setTipAmount(true);
		merchantData.setTaxAmount(true);
		merchantData.setShippingAmount(false);
		merchantData.setAllowRepeatSale(false);
		merchantData.setShowRecurringBilling(true);
		merchantData.setAutoSettlement(true);
		merchantData.setUserName("username");
		merchantData.setBusinessURL("businessURL");
		merchantData.setFederalTaxId(Long.parseLong("456"));
		merchantData.setStateTaxId(Long.parseLong("45"));
		merchantData.setSalesTaxId(Long.parseLong("543"));
		merchantData.setOwnership("ownership");
		merchantData.setEstimatedYearlySale(Long.parseLong("9876"));
		merchantData.setTimeZone("timeZone");
		merchantData.setAppMode("appMode");
		merchantData.setMerchantCode("merchantCode");
		merchantData.setBusinessName("businessName");
		merchantData.setFirstName("firstName");
		merchantData.setLastName("lastName");
		merchantData.setPhone(Long.parseLong("987654321"));
		merchantData.setFax(Long.parseLong("7584765"));
		merchantData.setEmailId("emailId");
		merchantData.setCreatedDateString("createdDateString");
		merchantData.setReason("reason");
		merchantData.setLocalCurrency("localCurrency");

	}

	@Test
	public void testMerchantData() {

		Assert.assertEquals(Long.valueOf("12345"), merchantData.getId());
		Assert.assertEquals("mid", merchantData.getMerchantId());
		Assert.assertEquals("Name", merchantData.getMerchantName());
		Assert.assertEquals("123", merchantData.getMerchantCategoryCode());
		Assert.assertEquals("Mtype", merchantData.getMerchantType());
		Assert.assertEquals(Long.valueOf("12345"), merchantData.getDailyTxnLimit());
		Assert.assertEquals(Boolean.valueOf(true), merchantData.getAllowInternationTxn());
		Assert.assertEquals("phone1", merchantData.getPhone1());
		Assert.assertEquals("phone2", merchantData.getPhone2());
		Assert.assertEquals("address1", merchantData.getAddress1());
		Assert.assertEquals("address2", merchantData.getAddress2());
		Assert.assertEquals("tin", merchantData.getTin());
		Assert.assertEquals("ssn", merchantData.getSsn());
		Assert.assertEquals("zip", merchantData.getZip());
		Assert.assertEquals("city", merchantData.getCity());
		Assert.assertEquals("state", merchantData.getState());
		Assert.assertEquals("country", merchantData.getCountry());
		Assert.assertEquals("createdDate", merchantData.getCreatedDate());
		Assert.assertEquals("validFromDate", merchantData.getValidFromDate());
		Assert.assertEquals("validToDate", merchantData.getValidToDate());
		Assert.assertEquals("status", merchantData.getStatus());
		Assert.assertEquals("updatedDate", merchantData.getUpdatedDate());
		Assert.assertEquals(Integer.valueOf("1"), merchantData.getPageIndex());
		Assert.assertEquals(Integer.valueOf("20"), merchantData.getPageSize());
		Assert.assertEquals(Integer.valueOf("40"), merchantData.getNoOfRecords());
		Assert.assertEquals("businessStartDate", merchantData.getBusinessStartDate());
		Assert.assertEquals("noOfEmployee", merchantData.getNoOfEmployee());
		Assert.assertEquals("role", merchantData.getRole());
		Assert.assertEquals("feeProgram", merchantData.getFeeProgram());
		Assert.assertEquals("processor", merchantData.getProcessor());
		Assert.assertEquals(Boolean.valueOf(true), merchantData.getRefunds());
		Assert.assertEquals(Boolean.valueOf(true), merchantData.getTipAmount());
		Assert.assertEquals(Boolean.valueOf(true), merchantData.getTaxAmount());
		Assert.assertEquals(Boolean.valueOf(false), merchantData.getShippingAmount());
		Assert.assertEquals(Boolean.valueOf(false), merchantData.getAllowRepeatSale());
		Assert.assertEquals(Boolean.valueOf(true), merchantData.getShowRecurringBilling());
		Assert.assertEquals(Boolean.valueOf(true), merchantData.getAutoSettlement());
		Assert.assertEquals("username", merchantData.getUserName());
		Assert.assertEquals("businessURL", merchantData.getBusinessURL());
		Assert.assertEquals(Long.valueOf("456"), merchantData.getFederalTaxId());
		Assert.assertEquals(Long.valueOf("45"), merchantData.getStateTaxId());
		Assert.assertEquals(Long.valueOf("543"), merchantData.getSalesTaxId());
		Assert.assertEquals("ownership", merchantData.getOwnership());
		Assert.assertEquals(Long.valueOf("9876"), merchantData.getEstimatedYearlySale());
		Assert.assertEquals("timeZone", merchantData.getTimeZone());
		Assert.assertEquals("appMode", merchantData.getAppMode());
		Assert.assertEquals("merchantCode", merchantData.getMerchantCode());
		Assert.assertEquals("businessName", merchantData.getBusinessName());
		Assert.assertEquals("firstName", merchantData.getFirstName());
		Assert.assertEquals("lastName", merchantData.getLastName());
		Assert.assertEquals(Long.valueOf("987654321"), merchantData.getPhone());
		Assert.assertEquals(Long.valueOf("7584765"), merchantData.getFax());
		Assert.assertEquals("emailId", merchantData.getEmailId());
		Assert.assertEquals("createdDateString", merchantData.getCreatedDateString());
		Assert.assertEquals("reason", merchantData.getReason());
		Assert.assertEquals("localCurrency", merchantData.getLocalCurrency());
	}

}
