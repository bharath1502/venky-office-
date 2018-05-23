package com.chatak.merchant.controller.model;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class MerchantProfileTest {

	@InjectMocks
	MerchantProfile merchantProfile = new MerchantProfile();

	private BigDecimal autoTransferLimit;

	@Before
	public void setUp() {
		merchantProfile.setId(Long.parseLong("987"));
		merchantProfile.setMerchantCode("merchantCode");
		merchantProfile.setBusinessName("businessName");
		merchantProfile.setFirstName("firstName");
		merchantProfile.setLastName("lastName");
		merchantProfile.setPhone(Long.parseLong("987546897"));
		merchantProfile.setFax(Long.parseLong("8654968"));
		merchantProfile.setEmailId("emailId");
		merchantProfile.setAddress1("address1");
		merchantProfile.setAddress2("address2");
		merchantProfile.setCity("city");
		merchantProfile.setState("state");
		merchantProfile.setCountry("country");
		merchantProfile.setPin("pin");
		merchantProfile.setTimeZone("timeZone");
		merchantProfile.setAppMode("appMode");
		merchantProfile.setUserName("userName");
		merchantProfile.setBusinessURL("businessURL");
		merchantProfile.setFederalTaxId("federalTaxId");
		merchantProfile.setStateTaxId("stateTaxId");
		merchantProfile.setSalesTaxId("salesTaxId");
		merchantProfile.setOwnership("ownership");
		merchantProfile.setEstimatedYearlySale(Long.parseLong("89543"));
		merchantProfile.setStatus(Integer.parseInt("12"));
		merchantProfile.setBusinessStartDate("businessStartDate");
		merchantProfile.setNoOfEmployee("noOfEmployee");
		merchantProfile.setFeeProgram("feeProgram");
		merchantProfile.setProcessor("processor");
		merchantProfile.setRole("role");
		merchantProfile.setRefunds(true);
		merchantProfile.setTipAmount(true);
		merchantProfile.setShippingAmount(true);
		merchantProfile.setAllowRepeatSale(true);
		merchantProfile.setTaxAmount(true);
		merchantProfile.setShowRecurringBilling(true);
		merchantProfile.setAutoSettlement(Integer.parseInt("543"));
		merchantProfile.setCreatedDate("createdDate");
		merchantProfile.setUpdatedDate("updatedDate");
		merchantProfile.setPageIndex(Integer.parseInt("5"));
		merchantProfile.setPageSize(Integer.parseInt("20"));
		merchantProfile.setNoOfRecords(Integer.parseInt("98"));
		merchantProfile.setMerchantCallBackURL("merchantCallBackURL");
		merchantProfile.setCategory("category");
		merchantProfile.setAutoTransferLimit(autoTransferLimit);
		merchantProfile.setAutoPaymentMethod("autoPaymentMethod");
		merchantProfile.setAutoTransferDay("autoTransferDay");
		merchantProfile.setMailingAddress1("mailingAddress1");
		merchantProfile.setMailingAddress2("mailingAddress2");
		merchantProfile.setMailingCity("mailingCity");
		merchantProfile.setMailingState("mailingState");
		merchantProfile.setMailingCountry("mailingCountry");
		merchantProfile.setMailingPin("mailingPin");
		merchantProfile.setMerchantUserId("merchantUserId");
	}

	@Test
	public void testMerchantProfile() {
		Assert.assertEquals(Long.valueOf("987"), merchantProfile.getId());
		Assert.assertEquals("merchantCode", merchantProfile.getMerchantCode());
		Assert.assertEquals("businessName", merchantProfile.getBusinessName());
		Assert.assertEquals("firstName", merchantProfile.getFirstName());
		Assert.assertEquals("lastName", merchantProfile.getLastName());
		Assert.assertEquals(Long.valueOf("987546897"), merchantProfile.getPhone());
		Assert.assertEquals(Long.valueOf("8654968"), merchantProfile.getFax());
		Assert.assertEquals("emailId", merchantProfile.getEmailId());
		Assert.assertEquals("address1", merchantProfile.getAddress1());
		Assert.assertEquals("address2", merchantProfile.getAddress2());
		Assert.assertEquals("city", merchantProfile.getCity());
		Assert.assertEquals("state", merchantProfile.getState());
		Assert.assertEquals("country", merchantProfile.getCountry());
		Assert.assertEquals("pin", merchantProfile.getPin());
		Assert.assertEquals("timeZone", merchantProfile.getTimeZone());
		Assert.assertEquals("appMode", merchantProfile.getAppMode());
		Assert.assertEquals("userName", merchantProfile.getUserName());
		Assert.assertEquals("businessURL", merchantProfile.getBusinessURL());
		Assert.assertEquals("federalTaxId", merchantProfile.getFederalTaxId());
		Assert.assertEquals("stateTaxId", merchantProfile.getStateTaxId());
		Assert.assertEquals("salesTaxId", merchantProfile.getSalesTaxId());
		Assert.assertEquals("ownership", merchantProfile.getOwnership());
		Assert.assertEquals(Long.valueOf("89543"), merchantProfile.getEstimatedYearlySale());
		Assert.assertEquals(Integer.valueOf("12"), merchantProfile.getStatus());
		Assert.assertEquals("businessStartDate", merchantProfile.getBusinessStartDate());
		Assert.assertEquals("noOfEmployee", merchantProfile.getNoOfEmployee());
		Assert.assertEquals("feeProgram", merchantProfile.getFeeProgram());
		Assert.assertEquals("processor", merchantProfile.getProcessor());
		Assert.assertEquals("role", merchantProfile.getRole());
		Assert.assertEquals(Boolean.valueOf(true), merchantProfile.getRefunds());
		Assert.assertEquals(Boolean.valueOf(true), merchantProfile.getTipAmount());
		Assert.assertEquals(Boolean.valueOf(true), merchantProfile.getShippingAmount());
		Assert.assertEquals(Boolean.valueOf(true), merchantProfile.getAllowRepeatSale());
		Assert.assertEquals(Boolean.valueOf(true), merchantProfile.getTaxAmount());
		Assert.assertEquals(Boolean.valueOf(true), merchantProfile.getShowRecurringBilling());
		Assert.assertEquals(Integer.valueOf("543"), merchantProfile.getAutoSettlement());
		Assert.assertEquals("createdDate", merchantProfile.getCreatedDate());
		Assert.assertEquals("updatedDate", merchantProfile.getUpdatedDate());
		Assert.assertEquals(Integer.valueOf("5"), merchantProfile.getPageIndex());
		Assert.assertEquals(Integer.valueOf("20"), merchantProfile.getPageSize());
		Assert.assertEquals(Integer.valueOf("98"), merchantProfile.getNoOfRecords());
		Assert.assertEquals("merchantCallBackURL", merchantProfile.getMerchantCallBackURL());
		Assert.assertEquals("category", merchantProfile.getCategory());
		Assert.assertEquals(autoTransferLimit, merchantProfile.getAutoTransferLimit());
		Assert.assertEquals("autoPaymentMethod", merchantProfile.getAutoPaymentMethod());
		Assert.assertEquals("autoTransferDay", merchantProfile.getAutoTransferDay());
		Assert.assertEquals("mailingAddress1", merchantProfile.getMailingAddress1());
		Assert.assertEquals("mailingAddress2", merchantProfile.getMailingAddress2());
		Assert.assertEquals("mailingCity", merchantProfile.getMailingCity());
		Assert.assertEquals("mailingState", merchantProfile.getMailingState());
		Assert.assertEquals("mailingCountry", merchantProfile.getMailingCountry());
		Assert.assertEquals("mailingPin", merchantProfile.getMailingPin());
		Assert.assertEquals("merchantUserId", merchantProfile.getMerchantUserId());
	}

}
