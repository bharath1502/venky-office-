package com.chatak.pay.controller.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OptionTest {

	@InjectMocks
	Option option = new Option();

	@Before
	public void setUp() {
		option.setValue("45");
		option.setLabel("45");
	}

	@Test
	public void testOption() {
		Assert.assertEquals("45", option.getValue());
		Assert.assertEquals("45", option.getLabel());
	}

}
