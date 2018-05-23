package com.chatak.pay.controller.model.topup;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetOperatorsResponseTest {

	@InjectMocks
	GetOperatorsResponse getOperatorsResponse=new GetOperatorsResponse();
	
	@Mock
	private List<OperatorDTO> operators;
	
	@Before
	public void setUp() {
		getOperatorsResponse.setOperators(operators);
	}

	@Test
	public void testOption() {
		Assert.assertEquals(operators, getOperatorsResponse.getOperators());
	}


}
