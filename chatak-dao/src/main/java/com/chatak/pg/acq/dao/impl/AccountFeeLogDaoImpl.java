/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.AccountFeeLogDao;
import com.chatak.pg.acq.dao.model.PGAccountFeeLog;
import com.chatak.pg.acq.dao.repository.AccountFeeLogRepository;

/**
 * @Author: Girmiti Software
 * @Date: Sep 21, 2015
 * @Time: 2:45:54 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository
public class AccountFeeLogDaoImpl implements AccountFeeLogDao {
	@Autowired
	AccountFeeLogRepository accountFeeLogRepository;

	/**
	 * @param pgAccountFeeLog
	 * @throws DataAccessException
	 */
	@Override
	public void createOrSave(PGAccountFeeLog pgAccountFeeLog)
			throws DataAccessException {
		accountFeeLogRepository.save(pgAccountFeeLog);

	}

	/**
	 * @param transactionId
	 * @return
	 */
	@Override
	public List<PGAccountFeeLog> getPGAccountFeeLogOnTransactionId(
			String transactionId) {
		return accountFeeLogRepository.findByTransactionId(transactionId);
	}

}
