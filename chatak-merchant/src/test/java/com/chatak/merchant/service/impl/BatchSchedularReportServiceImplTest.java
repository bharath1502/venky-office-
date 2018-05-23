package com.chatak.merchant.service.impl;

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

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.acq.dao.BatchSchedularDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.user.bean.DailyFundingReport;
import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportRequest;
import com.chatak.pg.user.bean.Transaction;
@RunWith(MockitoJUnitRunner.class)
public class BatchSchedularReportServiceImplTest {

	@InjectMocks
	private BatchSchedularReportServiceImpl batchSchedularReportServiceImpl = new BatchSchedularReportServiceImpl();
    
	@Mock
	private BatchSchedularDao batchSchedularDao;
	
	@Mock
	private MerchantDao merchantDao;
	
	@Mock
	private CurrencyConfigDao currencyConfigDao;

	@Mock
	private PGMerchant pgMerchant;

	@Mock
	GetBatchReportRequest batchReportRequest;
	
	@Mock
	PGCurrencyConfig pgCurrencyConfig;
	
	@Mock
	GetDailyFundingReportRequest getDailyFundingReportRequest;
	
	@Test
	public void testGetMerchantCodeAndCompanyName() throws ChatakMerchantException{
		Mockito.when(batchSchedularDao.getMerchantCodeAndCompanyName(Matchers.anyLong())).thenReturn(pgMerchant);
		Assert.assertNotNull(batchSchedularReportServiceImpl.getMerchantCodeAndCompanyName(Long.parseLong("11")));
		
	}
	
	@Test
	public void testFindById()throws ChatakMerchantException{
		List<PGMerchant> list=new ArrayList<>();
		Mockito.when(merchantDao.findById(Matchers.anyLong())).thenReturn(list);
		Assert.assertNotNull(batchSchedularReportServiceImpl.findById(Long.parseLong("123")));
	}
	
	
	@Test
	public void testSearchBatchReportTransactions()throws ChatakMerchantException{
		List<Transaction> transactions =new ArrayList<>();
		Transaction transaction=new Transaction();
		Mockito.when(batchSchedularDao.getMerchantBatchReportTransactions(Matchers.any(GetBatchReportRequest.class))).thenReturn(transactions);
		transaction.setMaskCardNumber("1234");
		transaction.setTxn_total_amount(Double.parseDouble("5"));
		transaction.setLocalCurrency("12345");
		transactions.add(transaction);
		Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString())).thenReturn(pgCurrencyConfig);
		Assert.assertNotNull(batchSchedularReportServiceImpl.searchBatchReportTransactions(batchReportRequest));
}
	
	@Test
	public void testSearchBatchReportTransactionsElse()throws ChatakMerchantException{
		List<Transaction> transactions = null;
		Mockito.when(batchSchedularDao.getMerchantBatchReportTransactions(Matchers.any(GetBatchReportRequest.class))).thenReturn(transactions);
		Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString())).thenReturn(pgCurrencyConfig);
		Assert.assertNotNull(batchSchedularReportServiceImpl.searchBatchReportTransactions(batchReportRequest));
}
	@Test
	public void testSearchBatchReportTransactionsException()throws ChatakMerchantException{
		Mockito.when(batchSchedularDao.getMerchantBatchReportTransactions(Matchers.any(GetBatchReportRequest.class))).thenThrow(new NullPointerException());
		Assert.assertNull(batchSchedularReportServiceImpl.searchBatchReportTransactions(batchReportRequest));

}
	@Test
	public void testSearchDailyFundingReportDetails()throws ChatakMerchantException{
		List<DailyFundingReport> dailyFundingReports=new ArrayList<DailyFundingReport>();
		Mockito.when(batchSchedularDao.searchMerchantDailyFundingReportDetails(Matchers.any(GetDailyFundingReportRequest.class),Matchers.anyString())).thenReturn(dailyFundingReports);
		Assert.assertNotNull(batchSchedularReportServiceImpl.searchDailyFundingReportDetails(getDailyFundingReportRequest,"Utype"));
		
	}
	
	@Test
	public void testSearchDailyFundingReportDetailsElse()throws ChatakMerchantException{
		List<DailyFundingReport> dailyFundingReports=null;
		Mockito.when(batchSchedularDao.searchMerchantDailyFundingReportDetails(Matchers.any(GetDailyFundingReportRequest.class),Matchers.anyString())).thenReturn(dailyFundingReports);
		Assert.assertNotNull(batchSchedularReportServiceImpl.searchDailyFundingReportDetails(getDailyFundingReportRequest,"Utype"));
		
	}
	@Test
	public void testSearchDailyFundingReportDetailsException()throws ChatakMerchantException{
		Mockito.when(batchSchedularDao.searchMerchantDailyFundingReportDetails(Matchers.any(GetDailyFundingReportRequest.class),Matchers.anyString())).thenThrow(new NullPointerException());
		Assert.assertNull(batchSchedularReportServiceImpl.searchDailyFundingReportDetails(getDailyFundingReportRequest,"Utype"));
		
	}

}
