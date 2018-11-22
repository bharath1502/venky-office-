/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.PayPageConfigServiceDao;
import com.chatak.pg.acq.dao.model.PGPayPageConfig;
import com.chatak.pg.acq.dao.repository.PayPageConfigRepository;

/**
 * @Author: Girmiti Software
 * @Date: Sep 30, 2016
 * @Time: 6:13:39 PM
 * @Version: 1.0
 * @Comments:
 *
 */
@Repository
public class PayPageConfigServiceDaoImpl implements PayPageConfigServiceDao {
	@Autowired
	PayPageConfigRepository payPageConfigRepository;

	/**
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public PGPayPageConfig findByPayPageConfigMerchantId(Long id)
			throws DataAccessException {

		return payPageConfigRepository.findByMerchantId(id);
	}

	/**
	 * @param pgPayPageConfig
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public PGPayPageConfig saveOrUpdatePGPayPageConfig(
			PGPayPageConfig pgPayPageConfig) throws DataAccessException {

		return payPageConfigRepository.save(pgPayPageConfig);
	}

}
