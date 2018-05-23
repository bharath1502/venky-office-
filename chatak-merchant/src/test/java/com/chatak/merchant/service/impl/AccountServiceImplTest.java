package com.chatak.merchant.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.repository.AccountRepository;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

	@InjectMocks
	private AccountServiceImpl accountServiceImpl = new AccountServiceImpl();

	@Mock
	AccountDao accountDao;

	@Mock
	AccountRepository accountRepository;
	
	@Mock
	PGAccount pgAccount;

	@Test
	  public void testGetAccountDetailsByEntityId() throws ChatakMerchantException {
		Mockito.when(accountDao.getPgAccount("123")).thenReturn(pgAccount);
		pgAccount = accountServiceImpl.getAccountDetailsByEntityId("2343");
	    Assert.assertNull(pgAccount);
	    pgAccount = accountServiceImpl.getAccountDetailsByEntityId("123");     
	    Assert.assertNotNull(pgAccount); 
	  }
	

}
