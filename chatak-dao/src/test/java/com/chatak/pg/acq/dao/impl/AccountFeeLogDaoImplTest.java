/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.acq.dao.model.PGAccountFeeLog;
import com.chatak.pg.acq.dao.repository.AccountFeeLogRepository;

/**
 * @Author: Girmiti Software
 * @Date: 15-Feb-2018
 * @Time: 2:26:20 pm
 * @Version: 1.0
 * @Comments:
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountFeeLogDaoImplTest {

	@InjectMocks
	AccountFeeLogDaoImpl accountDaoImpl;

	@Mock
	AccountFeeLogRepository accountFeeLogRepository;

	@Test
	public void testCreateOrSave() {
		PGAccountFeeLog pgAccountFeeLog = new PGAccountFeeLog();
		accountDaoImpl.createOrSave(pgAccountFeeLog);
	}

	@Test
	public void testGetPGAccountFeeLogOnTransactionId() {
		accountDaoImpl.getPGAccountFeeLogOnTransactionId("23143");
	}

}
