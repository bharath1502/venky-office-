package com.chatak.acquirer.admin.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OptionTest {

  private Option option;

  @Before
  public void setUp() {
    option = new Option();
    option.setValue("value");
    option.setLabel("label");
  }

  @Test
  public void testOption() {
    Assert.assertEquals("value", option.getValue());
    Assert.assertEquals("label", option.getLabel());

  }

}
