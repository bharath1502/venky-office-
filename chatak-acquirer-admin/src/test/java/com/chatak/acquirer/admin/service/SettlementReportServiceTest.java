package com.chatak.acquirer.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
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
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;

@RunWith(MockitoJUnitRunner.class)
public class SettlementReportServiceTest {

  @InjectMocks
  private SettlementReportServiceImpl bankService = new SettlementReportServiceImpl();

  @Mock
  private SettlementReportDao settlementReportDao;

  @Mock
  private CurrencyConfigDao currencyConfigDao;

  @Mock
  private GetBatchReportRequest batchReportRequest;

  @Mock
  private GetTransactionsListRequest transactionsListRequest;

  @Mock
  private GetTransactionsListResponse transactionsListResponse;

  @Mock
  private PGCurrencyConfig pgCurrencyConfig;

  @Test
  public void testSearchSettlementReportTransactions() throws ChatakAdminException {
    List<Transaction> list = new ArrayList<Transaction>();
    Transaction transaction = new Transaction();
    transaction.setMaskCardNumber("557896");
    list.add(transaction);
    Mockito.when(settlementReportDao.getSettlementReportTransactions(transactionsListRequest))
        .thenReturn(list);
    Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString()))
        .thenReturn(pgCurrencyConfig);
    transactionsListResponse =
        bankService.searchSettlementReportTransactions(transactionsListRequest);
    Assert.assertNotNull(transactionsListResponse);
  }

  @Test
  public void testSearchSettlementReportTransactionsNull() throws ChatakAdminException {
    List<Transaction> list = new ArrayList<Transaction>();
    Transaction transaction = new Transaction();
    transaction.setMaskCardNumber("557896");
    list.add(transaction);
    Mockito.when(settlementReportDao.getSettlementReportTransactions(transactionsListRequest))
        .thenReturn(null);
    Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString()))
        .thenReturn(pgCurrencyConfig);
    transactionsListResponse =
        bankService.searchSettlementReportTransactions(transactionsListRequest);
    Assert.assertNotNull(transactionsListResponse);
  }

}
