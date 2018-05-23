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
 * @date Dec 28, 2017 5:09:39 PM
 * @version 1.0
 */
public class ForgotPasswordRequestTest {

  private ForgotPasswordRequest request;

  @Before
  public void init() {
    request = new ForgotPasswordRequest();
    request.setUserName("userName");
    request.setEmail("email");
  }

  @Test
  public void testForgotPasswordRequest() {
    Assert.assertEquals("userName", request.getUserName());
    Assert.assertEquals("email", request.getEmail());
  }
}
