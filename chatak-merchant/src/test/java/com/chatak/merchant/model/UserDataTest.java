package com.chatak.merchant.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class UserDataTest {

	@InjectMocks
	UserData userData = new UserData();

	@Before
	public void setUp() {
		userData.setUserId(Long.parseLong("12345"));
		userData.setRoleName("roleName");
		userData.setUserName("userName");
		userData.setFirstName("firstName");
		userData.setLastName("lastName");
		userData.setEmailId("emailId");
		userData.setPhone("phone");
		userData.setRoleId(Long.parseLong("4351"));
		userData.setName("name");
		userData.setAddress("address");
		userData.setMerchantId("merchantId");
		userData.setUsersGrouopType("usersGrouopType");
		userData.setRequestType("requestType");
		userData.setMerchantLink("merchantLink");
		userData.setStatus(Integer.parseInt("24"));
		userData.setMerchantName("merchantName");
		userData.setRoleType("roleType");
		userData.setUserType("userType");
	}

	@Test
	public void testUserData() {
		Assert.assertEquals(Long.valueOf("12345"), userData.getUserId());
		Assert.assertEquals("roleName", userData.getRoleName());
		Assert.assertEquals("userName", userData.getUserName());
		Assert.assertEquals("firstName", userData.getFirstName());
		Assert.assertEquals("lastName", userData.getLastName());
		Assert.assertEquals("emailId", userData.getEmailId());
		Assert.assertEquals("phone", userData.getPhone());
		Assert.assertEquals(Long.valueOf("4351"), userData.getRoleId());
		Assert.assertEquals("name", userData.getName());
		Assert.assertEquals("address", userData.getAddress());
		Assert.assertEquals("merchantId", userData.getMerchantId());
		Assert.assertEquals("usersGrouopType", userData.getUsersGrouopType());
		Assert.assertEquals("requestType", userData.getRequestType());
		Assert.assertEquals("merchantLink", userData.getMerchantLink());
		Assert.assertEquals(Integer.valueOf("24"), userData.getStatus());
		Assert.assertEquals("merchantName", userData.getMerchantName());
		Assert.assertEquals("roleType", userData.getRoleType());
		Assert.assertEquals("userType", userData.getUserType());

	}
}
