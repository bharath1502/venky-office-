package com.chatak.merchant.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.user.bean.Transaction;

public class TransactionListResponseTest {

	@InjectMocks
	TransactionListResponse transactionListResponse = new TransactionListResponse();

	private List<Transaction> transactionList;

	private List<AccountTransactionDTO> accountTxnList;

	@Before
	public void setUp() {
		transactionListResponse.setTransactionList(transactionList);
		transactionListResponse.setAccountTxnList(accountTxnList);

	}

	@Test
	public void testTransactionListResponse() {

		Assert.assertEquals(transactionList, transactionListResponse.getTransactionList());
		Assert.assertEquals(accountTxnList, transactionListResponse.getAccountTxnList());

	}

}
