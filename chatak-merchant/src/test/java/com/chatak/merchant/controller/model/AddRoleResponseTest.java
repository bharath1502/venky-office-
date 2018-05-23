package com.chatak.merchant.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class AddRoleResponseTest {

	@InjectMocks
	AddRoleResponse addRoleResponse = new AddRoleResponse();

	@Before
	public void setUp() {
		addRoleResponse.setStatus(true);
		addRoleResponse.setMessage("message");
	}

	@Test
	public void testAddRoleResponse() {
		Assert.assertEquals(Boolean.valueOf(true), addRoleResponse.getStatus());
		Assert.assertEquals("message", addRoleResponse.getMessage());
	}

}
