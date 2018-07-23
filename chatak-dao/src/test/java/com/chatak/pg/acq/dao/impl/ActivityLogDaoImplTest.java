/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.acq.dao.model.PGActivityLog;
import com.chatak.pg.acq.dao.repository.ActivityLogRepository;

/**
 * @Author: Girmiti Software
 * @Date: 15-Feb-2018
 * @Time: 3:08:52 pm
 * @Version: 1.0
 * @Comments:
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ActivityLogDaoImplTest {

	@InjectMocks
	ActivityLogDaoImpl activityLogDaoImpl;

	@Mock
	ActivityLogRepository activityLogRepository;

	@Test
	public void testLogRequest() {
		PGActivityLog pgActivityLog = new PGActivityLog();
		activityLogDaoImpl.logRequest(pgActivityLog);
	}

}
