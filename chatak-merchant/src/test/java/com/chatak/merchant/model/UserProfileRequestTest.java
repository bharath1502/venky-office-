package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class UserProfileRequestTest {

	@InjectMocks
	UserProfileRequest userProfileRequest = new UserProfileRequest();

	@Before
	public void setUp() {
		userProfileRequest.setUserId(Long.parseLong("12345"));
		userProfileRequest.setMerchantUserId(Long.parseLong("12345"));
		userProfileRequest.setMerchantId(Long.parseLong("12345"));
		userProfileRequest.setRoleName("roleName");
		userProfileRequest.setUserName("userName");
		userProfileRequest.setFirstName("firstName");
		userProfileRequest.setLastName("lastName");
		userProfileRequest.setEmailId("emailId");
		userProfileRequest.setPhone("phone");
		userProfileRequest.setRoleId(Long.parseLong("4351"));
		userProfileRequest.setCompanyName("companyName");
		userProfileRequest.setEntityLegalName("entityLegalName");
		userProfileRequest.setTaxId("taxId");
		userProfileRequest.setType("type");
		userProfileRequest.setBusinessAs("businessAs");
		userProfileRequest.setCompanyEmailAddress("companyEmailAddress");
		userProfileRequest.setOfficePhone("officePhone");
		userProfileRequest.setFax("fax");
		userProfileRequest.setCompanyURL("companyURL");
		userProfileRequest.setAnnualCardSales("annualCardSales");
		userProfileRequest.setPhyAddress1("phyAddress1");
		userProfileRequest.setPhyAddress2("phyAddress2");
		userProfileRequest.setPhyCity("phyCity");
		userProfileRequest.setPhyState("phyState");
		userProfileRequest.setPhyZipCode("phyZipCode");
		userProfileRequest.setPhyCountry("phyCountry");
		userProfileRequest.setMailAddress1("mailAddress1");
		userProfileRequest.setMailAddress2("mailAddress2");
		userProfileRequest.setMailCity("mailCity");
		userProfileRequest.setMailState("mailState");
		userProfileRequest.setMailZipCode("mailZipCode");
		userProfileRequest.setMailCountry("mailCountry");
		userProfileRequest.setPrimaryName("primaryName");
		userProfileRequest.setPrimaryEmailId("primaryEmailId");
		userProfileRequest.setPrimaryContactPhone("primaryContactPhone");
	}

	@Test
	public void testUserProfileRequest() {
		Assert.assertEquals(Long.valueOf("12345"), userProfileRequest.getUserId());
		Assert.assertEquals(Long.valueOf("12345"), userProfileRequest.getMerchantUserId());
		Assert.assertEquals(Long.valueOf("12345"), userProfileRequest.getMerchantId());
		Assert.assertEquals("roleName", userProfileRequest.getRoleName());
		Assert.assertEquals("userName", userProfileRequest.getUserName());
		Assert.assertEquals("firstName", userProfileRequest.getFirstName());
		Assert.assertEquals("lastName", userProfileRequest.getLastName());
		Assert.assertEquals("emailId", userProfileRequest.getEmailId());
		Assert.assertEquals("phone", userProfileRequest.getPhone());
		Assert.assertEquals(Long.valueOf("4351"), userProfileRequest.getRoleId());
		Assert.assertEquals("companyName", userProfileRequest.getCompanyName());
		Assert.assertEquals("entityLegalName", userProfileRequest.getEntityLegalName());
		Assert.assertEquals("taxId", userProfileRequest.getTaxId());
		Assert.assertEquals("type", userProfileRequest.getType());
		Assert.assertEquals("businessAs", userProfileRequest.getBusinessAs());
		Assert.assertEquals("companyEmailAddress", userProfileRequest.getCompanyEmailAddress());
		Assert.assertEquals("officePhone", userProfileRequest.getOfficePhone());
		Assert.assertEquals("fax", userProfileRequest.getFax());
		Assert.assertEquals("companyURL", userProfileRequest.getCompanyURL());
		Assert.assertEquals("annualCardSales", userProfileRequest.getAnnualCardSales());
		Assert.assertEquals("phyAddress1", userProfileRequest.getPhyAddress1());
		Assert.assertEquals("phyAddress2", userProfileRequest.getPhyAddress2());
		Assert.assertEquals("phyCity", userProfileRequest.getPhyCity());
		Assert.assertEquals("phyState", userProfileRequest.getPhyState());
		Assert.assertEquals("phyZipCode", userProfileRequest.getPhyZipCode());
		Assert.assertEquals("phyCountry", userProfileRequest.getPhyCountry());
		Assert.assertEquals("mailAddress1", userProfileRequest.getMailAddress1());
		Assert.assertEquals("mailAddress2", userProfileRequest.getMailAddress2());
		Assert.assertEquals("mailCity", userProfileRequest.getMailCity());
		Assert.assertEquals("mailState", userProfileRequest.getMailState());
		Assert.assertEquals("mailZipCode", userProfileRequest.getMailZipCode());
		Assert.assertEquals("mailCountry", userProfileRequest.getMailCountry());
		Assert.assertEquals("primaryName", userProfileRequest.getPrimaryName());
		Assert.assertEquals("primaryEmailId", userProfileRequest.getPrimaryEmailId());
		Assert.assertEquals("primaryContactPhone", userProfileRequest.getPrimaryContactPhone());

	}
}
