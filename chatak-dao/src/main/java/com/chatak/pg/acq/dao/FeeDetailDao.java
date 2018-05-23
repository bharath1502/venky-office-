package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGFeeDetail;

public interface FeeDetailDao {

	/**
	 * Method to obtain Fee Details associated with PG
	 * @return Fee Detail list 
	 * @throws DataAccessException
	 */
	public List<PGFeeDetail> getPGFeeDetail() throws DataAccessException;
	
	/**
	 * Method to obtain Fee Detail associated with PG transaction type
	 * @param transactionType
	 * @return PGFeeDetail
	 * @throws DataAccessException
	 */
	public PGFeeDetail getPGFeeDetail(String transactionType) throws DataAccessException;
	
	/**
	 * Method to obtain Fee amount associated with PG transaction type
	 * @param transactionType
	 * @return amount
	 * @throws DataAccessException
	 */
	public Long getPGFeeAmount(String transactionType) throws DataAccessException;
	
}
