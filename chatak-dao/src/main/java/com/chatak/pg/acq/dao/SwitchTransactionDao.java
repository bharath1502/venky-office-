package com.chatak.pg.acq.dao;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGSwitchTransaction;

/**
 *
 * DAO class to handle Switch Transaction DB operations
 *
 * @author Girmiti Software
 * @date 22-Dec-2014 5:23:00 pm
 * @version 1.0
 */
public interface SwitchTransactionDao {
	

	/**
	 * Creates a Switch Transaction record with pre populated object 
	 * 
	 * @param pgSwitchTransaction
	 * @throws DataAccessException
	 */
	public void createTransaction(PGSwitchTransaction pgSwitchTransaction) throws DataAccessException;
	

}
