package com.chatak.acquirer.admin.controller.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class LoginResponseTest {

  @InjectMocks
  LoginResponse loginResponse = new LoginResponse();

  @Mock
  private List<String> existingFeature;

  @Before
  public void setUp() {
    loginResponse.setStatus(true);
    loginResponse.setUserId(Long.parseLong("23"));
    loginResponse.setMessage("message");
    loginResponse.setEmail("email");
    loginResponse.setServiceProviderId(Long.parseLong("101"));
    loginResponse.setAccessToken("accessToken");
    loginResponse.setSubServiceProviderId(Long.parseLong("543"));
    loginResponse.setUserRoleId(Long.parseLong("2"));
    loginResponse.setExistingFeature(existingFeature);
    loginResponse.setUserType("userType");
    loginResponse.setMakerCheckerRequired(true);
    loginResponse.setEmailVerified(Integer.parseInt("1"));
    loginResponse.setFirstName("firstName");
    loginResponse.setLastName("lastName");
    loginResponse.setUserName("userName");
  }

  @Test
  public void testLoginResponse() {
    Assert.assertEquals(Boolean.valueOf(true), loginResponse.getStatus());
    Assert.assertEquals(Long.valueOf("23"), loginResponse.getUserId());
    Assert.assertEquals("message", loginResponse.getMessage());
    Assert.assertEquals("email", loginResponse.getEmail());
    Assert.assertEquals(Long.valueOf("101"), loginResponse.getServiceProviderId());
    Assert.assertEquals("accessToken", loginResponse.getAccessToken());
    Assert.assertEquals(Long.valueOf("543"), loginResponse.getSubServiceProviderId());
    Assert.assertEquals(Long.valueOf("2"), loginResponse.getUserRoleId());
    Assert.assertEquals(existingFeature, loginResponse.getExistingFeature());
    Assert.assertEquals("userType", loginResponse.getUserType());
    Assert.assertEquals(Boolean.valueOf(true), loginResponse.getMakerCheckerRequired());
    Assert.assertEquals(Integer.valueOf(1), loginResponse.getEmailVerified());
    Assert.assertEquals("firstName", loginResponse.getFirstName());
    Assert.assertEquals("lastName", loginResponse.getLastName());
    Assert.assertEquals("userName", loginResponse.getUserName());
  }

}
