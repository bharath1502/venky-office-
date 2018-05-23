package com.chatak.acquirer.admin.spring.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.service.BatchSchedularService;
import com.chatak.acquirer.admin.service.FundTransfersService;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.ExecutedTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.bean.LitleEFTRequest;
import com.chatak.pg.model.LitleEFTDTO;

@RunWith(MockitoJUnitRunner.class)
public class MerchantPayoutSchedulerTest {

	@InjectMocks
	MerchantPayoutScheduler merchantPayoutScheduler;

	@Mock
	AccountDao accountDao;

	@Mock
	FundTransfersService fundTransfersService;

	@Mock
	TransactionDao transactionDao;

	@Mock
	ExecutedTransactionDao executedTransactionDao;

	@Mock
	BatchSchedularService batchSchedularService;

	@Test
	public void testProcessAutoPayment() {
		List<LitleEFTRequest> dailyLitleEFTRequests = new ArrayList<>();
		List<LitleEFTDTO> eftdtos = new ArrayList<>();
		LitleEFTDTO eftdto = new LitleEFTDTO();
		LitleEFTRequest eftRequest = new LitleEFTRequest();
		eftRequest.setLitleEFTDTOs(eftdtos);
		dailyLitleEFTRequests.add(eftRequest);
		eftdtos.add(eftdto);
		merchantPayoutScheduler.processAutoPayment();
	}

	@Test
	public void testGetLitleEFTDTOsListsDaily() {
		List<PGAccount> accounts = new ArrayList<>();
		List<LitleEFTRequest> litleEFTDTOsLists = new ArrayList<>();
		LitleEFTRequest eftRequest = new LitleEFTRequest();
		PGAccount account = new PGAccount();
		account.setEntityId("435");
		accounts.add(account);
		litleEFTDTOsLists.add(eftRequest);
		Mockito.when(accountDao.getPGAccountsOnPayoutFrequency(Matchers.anyString())).thenReturn(accounts);
		Mockito.when(executedTransactionDao
				.getLitleExecutedTransactionsOnMerchantCodeAndPayoutFrequency(Matchers.anyString(), Matchers.anyInt()))
				.thenReturn(eftRequest);
		merchantPayoutScheduler.getLitleEFTDTOsLists("D");
	}

	@Test
	public void testGetLitleEFTDTOsListsWeekly() {
		List<PGAccount> accounts = new ArrayList<>();
		List<LitleEFTRequest> litleEFTDTOsLists = new ArrayList<>();
		LitleEFTRequest eftRequest = new LitleEFTRequest();
		PGAccount account = new PGAccount();
		account.setEntityId("435");
		accounts.add(account);
		litleEFTDTOsLists.add(eftRequest);
		Mockito.when(accountDao.getPGAccountsOnPayoutFrequency(Matchers.anyString())).thenReturn(accounts);
		Mockito.when(executedTransactionDao
				.getLitleExecutedTransactionsOnMerchantCodeAndPayoutFrequency(Matchers.anyString(), Matchers.anyInt()))
				.thenReturn(eftRequest);
		merchantPayoutScheduler.getLitleEFTDTOsLists("W");
	}

	@Test
	public void testGetLitleEFTDTOsListsMonthly() {
		List<PGAccount> accounts = new ArrayList<>();
		List<LitleEFTRequest> litleEFTDTOsLists = new ArrayList<>();
		LitleEFTRequest eftRequest = new LitleEFTRequest();
		PGAccount account = new PGAccount();
		account.setEntityId("435");
		accounts.add(account);
		litleEFTDTOsLists.add(eftRequest);
		Mockito.when(accountDao.getPGAccountsOnPayoutFrequency(Matchers.anyString())).thenReturn(accounts);
		Mockito.when(executedTransactionDao
				.getLitleExecutedTransactionsOnMerchantCodeAndPayoutFrequency(Matchers.anyString(), Matchers.anyInt()))
				.thenReturn(eftRequest);
		merchantPayoutScheduler.getLitleEFTDTOsLists("M");
	}

}
