package com.chatak.pay.controller.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FeeDataResponseTest {

	@InjectMocks
	FeeDataResponse feeDataResponse = new FeeDataResponse();

	@Before
	public void setUp() {
		List<FeeData> fees = new ArrayList<>();
		feeDataResponse.setFees(fees);
		feeDataResponse.setTxnAmount(Long.parseLong("23"));
		feeDataResponse.setTotalFeeAmt(Long.parseLong("23"));
		feeDataResponse.setNetAmount(Long.parseLong("23"));

	}

	@Test
	public void testFeeDataResponse() {
		List<FeeData> fees = new ArrayList<>();
		Assert.assertEquals(fees, feeDataResponse.getFees());
		Assert.assertEquals(Long.valueOf("23"), feeDataResponse.getTxnAmount());
		Assert.assertEquals(Long.valueOf("23"), feeDataResponse.getTotalFeeAmt());
		Assert.assertEquals(Long.valueOf("23"), feeDataResponse.getNetAmount());

	}

}
