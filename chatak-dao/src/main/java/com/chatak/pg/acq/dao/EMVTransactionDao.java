package com.chatak.pg.acq.dao;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGEMVTransaction;

public interface EMVTransactionDao {
	

	/**
	 * Creates a Transaction record with pre populated object 
	 * 
	 * @param pgTransaction
	 * @throws DataAccessException
	 */
	public void createTransaction(PGEMVTransaction pgTransaction) throws DataAccessException;


}
