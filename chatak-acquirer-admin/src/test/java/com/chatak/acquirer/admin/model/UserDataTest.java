/**
 * 
 */
package com.chatak.acquirer.admin.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Dec 28, 2017 7:15:46 PM
 * @version 1.0
 */
public class UserDataTest {

  private UserData userData;

  @Before
  public void init() {
    userData = new UserData();
    userData.setUserId(Long.parseLong("1"));
    userData.setFirstName("firstName");
    userData.setRoleName("roleName");
    userData.setLastName("lastName");
    userData.setPhone("phone");
    userData.setEmailId("emailId");
    userData.setRoleId(Long.parseLong("1"));
    userData.setAddress("address");
    userData.setName("name");
    userData.setMerchantId("merchantId");
    userData.setRequestType("requestType");
    userData.setUsersGrouopType("usersGrouopType");
    userData.setMerchantLink("merchantLink");
    userData.setMerchantName("merchantName");
    userData.setStatus(Integer.parseInt("1"));
    userData.setRoleType("roleType");
    userData.setUserType("userType");
  }

  @Test
  public void testUserData() {
    Assert.assertEquals(Long.valueOf(1), userData.getUserId());
    Assert.assertEquals("firstName", userData.getFirstName());
    Assert.assertEquals("roleName", userData.getRoleName());
    Assert.assertEquals("lastName", userData.getLastName());
    Assert.assertEquals("phone", userData.getPhone());
    Assert.assertEquals("emailId", userData.getEmailId());
    Assert.assertEquals(Long.valueOf(1), userData.getRoleId());
    Assert.assertEquals("address", userData.getAddress());
    Assert.assertEquals("name", userData.getName());
    Assert.assertEquals("merchantId", userData.getMerchantId());
    Assert.assertEquals("requestType", userData.getRequestType());
    Assert.assertEquals("usersGrouopType", userData.getUsersGrouopType());
    Assert.assertEquals("merchantLink", userData.getMerchantLink());
    Assert.assertEquals("merchantName", userData.getMerchantName());
    Assert.assertEquals(Integer.valueOf(1), userData.getStatus());
    Assert.assertEquals("roleType", userData.getRoleType());
    Assert.assertEquals("userType", userData.getUserType());
  }

}
