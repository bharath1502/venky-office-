package com.chatak.pay.controller.model.topup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OperatorDTOTest {

	@InjectMocks
	OperatorDTO operatorDTO = new OperatorDTO();

	@Before
	public void setUp() {
		operatorDTO.setOperatorID(Integer.parseInt("123"));
		operatorDTO.setOperatorSpecs("123");
		operatorDTO.setOperatorName("123");

	}

	@Test
	public void testOperatorDTO() {
		Assert.assertEquals(Integer.valueOf("123"), operatorDTO.getOperatorID());
		Assert.assertEquals("123", operatorDTO.getOperatorSpecs());
		Assert.assertEquals("123", operatorDTO.getOperatorName());
	}
}
