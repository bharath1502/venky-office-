package com.chatak.acquirer.admin.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResetPasswordDataTest {

  private ResetPasswordData resetPasswordData;

  @Before
  public void setUp() {
    resetPasswordData = new ResetPasswordData();
    resetPasswordData.setNewPassword("newPassword");
    resetPasswordData.setConfirmPassword("confirmPassword");
  }

  @Test
  public void testResetPasswordData() {
    Assert.assertEquals("newPassword", resetPasswordData.getNewPassword());
    Assert.assertEquals("confirmPassword", resetPasswordData.getConfirmPassword());

  }

}
