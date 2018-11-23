package com.chatak.pay.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pay.controller.model.AccountBalanceInquiryRequest;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

	@InjectMocks
	AccountServiceImpl accountServiceImpl = new AccountServiceImpl();

	@Mock
	private AccountDao accountDao;

	@Mock
	private AccountTransactionsDao accountTransactionDao;

	@Test
	public void testValidate() throws ChatakPayException {
		accountServiceImpl.validate(Long.parseLong("43"));
	}

	@Test
	public void testValidateElse() throws ChatakPayException {
		PGAccount account = new PGAccount();
		Mockito.when(accountDao.getAccountonAccountNumber(Matchers.anyLong())).thenReturn(account);
		accountServiceImpl.validate(Long.parseLong("43"));
	}

	@Test
	public void testBalanceInquiry() throws ChatakPayException {
		AccountBalanceInquiryRequest accountBalanceInquiryRequest = new AccountBalanceInquiryRequest();
		accountServiceImpl.balanceInquiry(accountBalanceInquiryRequest);
	}

	@Test
	public void testBalanceInquiryElse() throws ChatakPayException {
		AccountBalanceInquiryRequest accountBalanceInquiryRequest = new AccountBalanceInquiryRequest();
		List<Long> accounts = new ArrayList<>();
		Long long1 = Long.valueOf("243");
		accounts.add(long1);
		accountBalanceInquiryRequest.setAccountNumbers(accounts);
		accountServiceImpl.balanceInquiry(accountBalanceInquiryRequest);
	}

	@Test
	public void testGetAccountHistory() throws ChatakPayException {
		GetTransactionsListResponse transResponse = new GetTransactionsListResponse();
		Mockito.when(accountTransactionDao.getAccountAllTransactions(Matchers.any(GetTransactionsListRequest.class)))
				.thenReturn(transResponse);
		accountServiceImpl.getAccountHistory(Long.parseLong("43"));
	}

	@Test
	public void testGetAccountHistoryNull() throws ChatakPayException {
		accountServiceImpl.getAccountHistory(null);
	}

}
