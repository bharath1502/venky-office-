package com.chatak.pay.controller.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.model.AccountTransactionDTO;

@RunWith(MockitoJUnitRunner.class)
public class AccountTransactionHistoryTest {

	@InjectMocks
	AccountTransactionHistory accountTransactionHistory = new AccountTransactionHistory();

	@Mock
	private List<AccountTransactionDTO> executedTransactions;

	@Mock
	private List<AccountTransactionDTO> processingTransactions;

	@Before
	public void setUp() {
		accountTransactionHistory.setExecutedTransactions(executedTransactions);
		accountTransactionHistory.setProcessingTransactions(processingTransactions);

	}

	@Test
	public void testdeTokenizeRequest() {
		Assert.assertEquals(executedTransactions, accountTransactionHistory.getExecutedTransactions());
		Assert.assertEquals(processingTransactions, accountTransactionHistory.getProcessingTransactions());

	}

}
