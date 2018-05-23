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
public class AccountBalanceInquiryRequestTest {

	@InjectMocks
	AccountBalanceInquiryRequest accountBalanceInquiryRequest = new AccountBalanceInquiryRequest();

	@Before
	public void setUp() {
		List<Long> accountNumbers = new ArrayList<>();
		accountBalanceInquiryRequest.setAccountNumbers(accountNumbers);
	}

	@Test
	public void testdeTokenizeRequest() {
		List<Long> accountNumbers = new ArrayList<>();
		Assert.assertEquals(accountNumbers, accountBalanceInquiryRequest.getAccountNumbers());

	}

}
