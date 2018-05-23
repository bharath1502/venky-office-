package com.chatak.merchant.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class ChangePasswordRequestTest {
	@InjectMocks
	ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();

	@Before
	public void setUp() {
		changePasswordRequest.setUserId(Long.parseLong("343"));
		changePasswordRequest.setCurrentPassword("currentPassword");
		changePasswordRequest.setNewPassword("newPassword");
		changePasswordRequest.setConfirmPassword("confirmPassword");
	}

	@Test
	public void testChangePasswordRequest() {
		Assert.assertEquals(Long.valueOf("343"), changePasswordRequest.getUserId());
		Assert.assertEquals("currentPassword", changePasswordRequest.getCurrentPassword());
		Assert.assertEquals("newPassword", changePasswordRequest.getNewPassword());
		Assert.assertEquals("confirmPassword", changePasswordRequest.getConfirmPassword());
	}

}
