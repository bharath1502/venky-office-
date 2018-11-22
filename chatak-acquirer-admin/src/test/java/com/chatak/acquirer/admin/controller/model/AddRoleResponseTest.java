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
 * @date Dec 28, 2017 4:42:21 PM
 * @version 1.0
 */
public class AddRoleResponseTest {

  private AddRoleResponse response;

  @Before
  public void init() {
    response = new AddRoleResponse();
    response.setStatus(true);
    response.setMessage("Message");
  }

  @Test
  public void testAddRoleResponse() {
    Assert.assertEquals(true, response.getStatus());
    Assert.assertEquals("Message", response.getMessage());
  }

}
