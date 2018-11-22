package com.chatak.pay.controller;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pay.controller.model.AccountBalanceInquiryRequest;
import com.chatak.pay.controller.model.Response;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.service.AccountService;

@RunWith(MockitoJUnitRunner.class)
public class AccountRestControllerTest {

	@InjectMocks
	AccountRestController accountRestController = new AccountRestController();

	@Mock
	HttpServletRequest request;

	@Mock
	private AccountService accountService;

	@Test
	public void testValidate() throws ChatakPayException {
		Response response = new Response();
		Mockito.when(accountService.validate(Matchers.anyLong())).thenReturn(response);
		accountRestController.validate(request, Long.parseLong("342"));
	}

	@Test
	public void testBalanceInquiry() {
		AccountBalanceInquiryRequest accountBalanceInquiryRequest = new AccountBalanceInquiryRequest();
		accountRestController.balanceInquiry(request, accountBalanceInquiryRequest);
	}

	@Test
	public void testGetAccountHistory() {
		accountRestController.getAccountHistory(request, Long.parseLong("342"));
	}
}
