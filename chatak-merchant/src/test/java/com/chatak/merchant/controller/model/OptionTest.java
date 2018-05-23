package com.chatak.merchant.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class OptionTest {

	@InjectMocks
	Option option = new Option();

	@Before
	public void setUp() {
		option.setValue("value");
		option.setLabel("label");
	}

	@Test
	public void testOption() {
		Assert.assertEquals("value", option.getValue());
		Assert.assertEquals("label", option.getLabel());

	}

}
