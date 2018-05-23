/**
 * 
 */
package com.chatak.acquirer.admin.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 5:00:30 PM
 * @version 1.0
 */
public class ChangePasswordRequestTest {

  private ChangePasswordRequest request;

  @Before
  public void init() {
    request = new ChangePasswordRequest();
    request.setUserId(Long.parseLong("1"));
    request.setCurrentPassword("currentPassword");
    request.setNewPassword("newPassword");
    request.setConfirmPassword("confirmPassword");
    request.setIsNewUser(true);
  }

  @Test
  public void testChangePasswordRequest() {
    Assert.assertEquals(Long.valueOf("1"), request.getUserId());
    Assert.assertEquals("currentPassword", request.getCurrentPassword());
    Assert.assertEquals("newPassword", request.getNewPassword());
    Assert.assertEquals("confirmPassword", request.getConfirmPassword());
    Assert.assertEquals(true, request.getIsNewUser());
  }
}
