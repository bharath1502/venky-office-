package com.chatak.pg.acq.dao;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGFraudBasic;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;

public interface FraudBasicServicesDao {

	/**
	 * @param saveOrUpdatePGFraudBasic
	 * @return
	 * @throws DataAccessException
	 */
	public PGFraudBasic saveOrUpdatePGFraudBasic(PGFraudBasic PGFraudBasic)
			throws DataAccessException;

	/**
	 * @param findByFraudBasicId
	 * @return
	 * @throws DataAccessException
	 */
	public PGFraudBasic findByFraudBasicId(Long id) throws DataAccessException;

	
	/**
	 * @param findByFraudBasicId
	 * @return
	 * @throws DataAccessException
	 */
	public PGFraudBasic findByFraudBasicMerchantId(Long id) throws DataAccessException;
	
	public PGMerchantUsers findById(Long id) throws DataAccessException;
}
