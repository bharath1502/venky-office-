package com.chatak.pay.controller.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.model.AccountBalanceDTO;

@RunWith(MockitoJUnitRunner.class)
public class AccountBalanceInquiryResponseTest {

	@InjectMocks
	AccountBalanceInquiryResponse accountBalanceInquiryResponse = new AccountBalanceInquiryResponse();

	@Before
	public void setUp() {
		List<AccountBalanceDTO> accountBalances = new ArrayList<>();
		accountBalanceInquiryResponse.setAccountBalances(accountBalances);
	}

	@Test
	public void testdeTokenizeRequest() {
		List<AccountBalanceDTO> accountBalances = new ArrayList<>();
		Assert.assertEquals(accountBalances, accountBalanceInquiryResponse.getAccountBalances());

	}
}
