package com.chatak.acquirer.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.impl.SettlementReportServiceImpl;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.SettlementReportDao;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.Transaction;

@RunWith(MockitoJUnitRunner.class)
public class SettlementReportServiceImplTest {

	@InjectMocks
	SettlementReportServiceImpl settlementReportServiceImpl = new SettlementReportServiceImpl();

	@Mock
	private SettlementReportDao settlementReportDao;

	@Mock
	private CurrencyConfigDao currencyConfigDao;

	@Test
	public void testSearchSettlementReportTransactions() throws ChatakAdminException {
		GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		List<Transaction> transactions = new ArrayList<>();
		Transaction transaction = new Transaction();
		transaction.setMaskCardNumber("maskCardNumber");
		transactions.add(transaction);
		Mockito.when(
				settlementReportDao.getSettlementReportTransactions(Matchers.any(GetTransactionsListRequest.class)))
				.thenReturn(transactions);
		Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString())).thenReturn(pgCurrencyConfig);
		settlementReportServiceImpl.searchSettlementReportTransactions(getTransactionsListRequest);
	}

	@Test
	public void testSearchSettlementReportTransactionsElse() throws ChatakAdminException {
		GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
		List<Transaction> transactions = new ArrayList<>();
		getTransactionsListRequest.setNoOfRecords(1);
		Mockito.when(
				settlementReportDao.getSettlementReportTransactions(Matchers.any(GetTransactionsListRequest.class)))
				.thenReturn(transactions);
		settlementReportServiceImpl.searchSettlementReportTransactions(getTransactionsListRequest);
	}

	@Test
	public void testSearchSettlementReportTransactionsException() throws ChatakAdminException {
		GetTransactionsListRequest getTransactionsListRequest = new GetTransactionsListRequest();
		List<Transaction> transactions = new ArrayList<>();
		Transaction transaction = new Transaction();
		transactions.add(transaction);
		Mockito.when(
				settlementReportDao.getSettlementReportTransactions(Matchers.any(GetTransactionsListRequest.class)))
				.thenReturn(transactions);
		settlementReportServiceImpl.searchSettlementReportTransactions(getTransactionsListRequest);
	}

	@Test
	public void testSearchBatchReportTransactions() throws ChatakAdminException {
		GetBatchReportRequest batchReportRequest = new GetBatchReportRequest();
		List<Transaction> transactions = new ArrayList<>();
		PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
		Transaction transaction = new Transaction();
		transaction.setMaskCardNumber("maskCardNumber");
		transaction.setTxn_total_amount(Double.parseDouble("5345"));
		transactions.add(transaction);
		Mockito.when(settlementReportDao.getBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
				.thenReturn(transactions);
		Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString())).thenReturn(pgCurrencyConfig);
		settlementReportServiceImpl.searchBatchReportTransactions(batchReportRequest);
	}

	@Test
	public void testSearchBatchReportTransactionsElse() throws ChatakAdminException {
		GetBatchReportRequest batchReportRequest = new GetBatchReportRequest();
		List<Transaction> transactions = new ArrayList<>();
		Transaction transaction = new Transaction();
		transactions.add(transaction);
		Mockito.when(settlementReportDao.getBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
				.thenReturn(transactions);
		settlementReportServiceImpl.searchBatchReportTransactions(batchReportRequest);
	}

	@Test
	public void testSearchBatchReportTransactionsException() throws ChatakAdminException {
		GetBatchReportRequest batchReportRequest = new GetBatchReportRequest();
		List<Transaction> transactions = new ArrayList<>();
		Transaction transaction = new Transaction();
		transactions.add(transaction);
		Mockito.when(settlementReportDao.getBatchReportTransactions(Matchers.any(GetBatchReportRequest.class)))
				.thenReturn(transactions);
		settlementReportServiceImpl.searchBatchReportTransactions(batchReportRequest);
	}

}
