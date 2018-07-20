package com.chatak.pg.acq.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.FraudBasicServicesDao;
import com.chatak.pg.acq.dao.model.PGFraudBasic;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.acq.dao.repository.FraudBasicRepository;
import com.chatak.pg.acq.dao.repository.MerchantUserRepository;

@Repository("fraudBasicServicesDao")
public class FraudBasicServicesDaoImpl implements FraudBasicServicesDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	FraudBasicRepository fraudBasicRepository;
	
	@Autowired
	MerchantUserRepository merchantUserRepository;

	@Override
	public PGFraudBasic saveOrUpdatePGFraudBasic(PGFraudBasic PGFraudBasic)
			throws DataAccessException {
		return fraudBasicRepository.save(PGFraudBasic);
	}

	@Override
	public PGFraudBasic findByFraudBasicId(Long id) throws DataAccessException {
		return fraudBasicRepository.findOne(id);
	}

	@Override
	public PGFraudBasic findByFraudBasicMerchantId(Long id)
			throws DataAccessException {
		return fraudBasicRepository.findByMerchantId(id);
	}

	/**
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public PGMerchantUsers findById(Long id) throws DataAccessException {
		return merchantUserRepository.findById(id);
	}

}