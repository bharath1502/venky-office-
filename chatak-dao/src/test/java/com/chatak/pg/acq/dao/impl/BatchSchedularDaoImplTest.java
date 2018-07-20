/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportRequest;
import com.chatak.pg.user.bean.GetTransactionsListRequest;

/**
 * @Author: Girmiti Software
 * @Date: 16-Feb-2018
 * @Time: 2:44:21 pm
 * @Version: 1.0
 * @Comments:
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BatchSchedularDaoImplTest {

	@InjectMocks
	BatchSchedularDaoImpl batchSchedularDaoImpl;

	@Mock
	private EntityManager entityManager;

	@Mock
	Query query;

	@Mock
	private EntityManagerFactory emf;

	@Mock
	private MerchantRepository merchantRepository;

	Timestamp time = new Timestamp(System.currentTimeMillis());

	@Test
	public void testSearchDailyFundingReportDetails() {
		GetDailyFundingReportRequest reportRequest = new GetDailyFundingReportRequest();
		reportRequest.setPageIndex(null);
		reportRequest.setFromDate("11/01/2000");
		reportRequest.setToDate("11/11/2011");
		reportRequest.setPageSize(Integer.parseInt("2"));
		time = new Timestamp(Integer.parseInt("2018"));
		List<Object> tuplelist = new ArrayList<>();
		Object objects[] = new Object[Integer.parseInt("13")];
		objects[0] = new Long("12");
		objects[1] = "name";
		objects[Integer.parseInt("2")] = "name";
		objects[Integer.parseInt("3")] = "name";
		objects[Integer.parseInt("4")] = "phone";
		objects[Integer.parseInt("5")] = "name";
		objects[Integer.parseInt("6")] = "status";
		objects[Integer.parseInt("7")] = "status";
		objects[Integer.parseInt("8")] = "bankRoutingNumber";
		objects[Integer.parseInt("9")] = new Double("12.5");
		objects[Integer.parseInt("10")] = new Double("12.5");
		objects[Integer.parseInt("11")] = new Double("12.5");
		objects[Integer.parseInt("12")] = time;
		tuplelist.add(objects);

		Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
		Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(query.getResultList()).thenReturn(tuplelist);
		batchSchedularDaoImpl.searchDailyFundingReportDetails(reportRequest);
	}

	@Test
	public void testInsertFundingReport() {
		batchSchedularDaoImpl.insertFundingReport();
	}

	@Test
	public void testShowManualFundingReport() {
		List<String> str = new ArrayList<>();
		String st = "string";
		str.add(st);
		Mockito.when(entityManager.createNativeQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(str);
		batchSchedularDaoImpl.showManualFundingReport();
	}

	@Test
	public void getMerchantCodeAndCompanyName() {
		Long userid = Long.parseLong("10");
		batchSchedularDaoImpl.getMerchantCodeAndCompanyName(userid);
	}

	@Test
	public void testSearchMerchantDailyFundingReportDetails() {

		GetDailyFundingReportRequest reportRequest = new GetDailyFundingReportRequest();
		String userType = "Merchant";

		reportRequest.setFromDate("20/09/1888");
		reportRequest.setToDate("20/09/1890");

		List<String> merchantList = new ArrayList<>();
		Mockito.when(merchantRepository.findMerchantsList(Matchers.anyLong())).thenReturn(merchantList);
		reportRequest.setPageIndex(null);
		List<Long> list = new ArrayList<>();
		Long lon = new Long("10");
		list.add(lon);

		List<Object> tupleList = new ArrayList<>();
		Object objects[] = new Object[Integer.parseInt("13")];
		objects[0] = new Long("12");
		objects[1] = "batchId";
		objects[Integer.parseInt("2")] = "merchantCode";
		objects[Integer.parseInt("3")] = "merchantName";
		objects[Integer.parseInt("4")] = "subMerchantCode";
		objects[Integer.parseInt("5")] = "subMerchantName";
		objects[Integer.parseInt("6")] = "currency";
		objects[Integer.parseInt("7")] = "bankAccountNumber";
		objects[Integer.parseInt("8")] = "bankRoutingNumber";
		objects[Integer.parseInt("9")] = new Double("12.3357");
		objects[Integer.parseInt("10")] = new Double("12.3357");
		objects[Integer.parseInt("11")] = new Double("12.3357");
		objects[Integer.parseInt("12")] = time;
		tupleList.add(objects);

		Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
		Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(query.getResultList()).thenReturn(list, tupleList);
		batchSchedularDaoImpl.searchMerchantDailyFundingReportDetails(reportRequest, userType);
	}

	@Test
	public void testGetMerchantBatchReportTransactions() {
		List<String> merchantList = new ArrayList<>();
		merchantList.add("");
		GetBatchReportRequest batchReportRequest = new GetBatchReportRequest();
		batchReportRequest.setPageIndex(null);
		batchReportRequest.setFromDate("11/01/2000");
		batchReportRequest.setToDate("11/11/2011");
		batchReportRequest.setPageSize(Integer.parseInt("2"));
		Timestamp time1 = new Timestamp(Integer.parseInt("2018"));
		List<Object> tuplelist = new ArrayList<>();
		Object objects[] = new Object[Integer.parseInt("13")];
		objects[0] = new Long("12");
		objects[1] = "name";
		objects[Integer.parseInt("2")] = "name";
		objects[Integer.parseInt("3")] = "name";
		objects[Integer.parseInt("4")] = "phone";
		objects[Integer.parseInt("5")] = "name";
		objects[Integer.parseInt("6")] = "status";
		objects[Integer.parseInt("7")] = "status";
		objects[Integer.parseInt("8")] = "bankRoutingNumber";
		objects[Integer.parseInt("9")] = new Double("12.5");
		objects[Integer.parseInt("10")] = new Double("12.5");
		objects[Integer.parseInt("11")] = new Double("12.5");
		objects[Integer.parseInt("12")] = time1;
		tuplelist.add(objects);

		Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
		Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(query.getResultList()).thenReturn(merchantList, tuplelist);
		batchSchedularDaoImpl.getMerchantBatchReportTransactions(batchReportRequest);
	}

	@Test
	public void testGetTxnHistoryOnId() {
		GetTransactionsListRequest transaction = new GetTransactionsListRequest();
		transaction.setPageIndex(null);
		transaction.setPageSize(Integer.parseInt("2"));
		time = new Timestamp(Integer.parseInt("2018"));
		List<Object> tuplelist = new ArrayList<>();
		Object objects[] = new Object[Integer.parseInt("13")];
		objects[0] = new Long("12");
		objects[1] = "name";
		objects[Integer.parseInt("2")] = "name";
		objects[Integer.parseInt("3")] = "name";
		objects[Integer.parseInt("4")] = "phone";
		objects[Integer.parseInt("5")] = "name";
		objects[Integer.parseInt("6")] = "status";
		objects[Integer.parseInt("7")] = "status";
		objects[Integer.parseInt("8")] = "bankRoutingNumber";
		objects[Integer.parseInt("9")] = new Double("12.5");
		objects[Integer.parseInt("10")] = new Double("12.5");
		objects[Integer.parseInt("11")] = new Double("12.5");
		objects[Integer.parseInt("12")] = time;
		tuplelist.add(objects);

		Mockito.when(entityManager.getDelegate()).thenReturn(Object.class);
		Mockito.when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		Mockito.when(entityManager.getEntityManagerFactory()).thenReturn(emf);
		Mockito.when(query.getResultList()).thenReturn(tuplelist);
		batchSchedularDaoImpl.getTxnHistoryOnId(transaction);
	}

}
