package com.chatak.merchant.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class ResetPasswordDataTest {

	@InjectMocks
	ResetPasswordData resetPasswordData = new ResetPasswordData();

	@Before
	public void setUp() {
		resetPasswordData.setNewPassword("newPassword");
		resetPasswordData.setConfirmPassword("confirmPassword");
	}

	@Test
	public void testResetPasswordData() {
		Assert.assertEquals("newPassword", resetPasswordData.getNewPassword());
		Assert.assertEquals("confirmPassword", resetPasswordData.getConfirmPassword());

	}

}
