package com.chatak.acquirer.admin.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RequestTest {

  private Request request;

  @Before
  public void setUp() {
    request = new Request();
    request.setCreatedBy("createdBy");
    request.setOrginChannel("orginChannel");
  }

  @Test
  public void testRequest() {
    Assert.assertEquals("createdBy", request.getCreatedBy());
    Assert.assertEquals("orginChannel", request.getOrginChannel());
  }

}
