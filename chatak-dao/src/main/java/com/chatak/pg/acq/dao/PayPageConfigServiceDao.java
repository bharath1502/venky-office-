/**
 * 
 */
package com.chatak.pg.acq.dao;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGPayPageConfig;

/**
 * @Author: Girmiti Software
 * @Date: Sep 30, 2016
 * @Time: 5:14:50 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public interface PayPageConfigServiceDao {

	public PGPayPageConfig findByPayPageConfigMerchantId(Long id)
			throws DataAccessException;

	public PGPayPageConfig saveOrUpdatePGPayPageConfig(
			PGPayPageConfig pgPayPageConfig) throws DataAccessException;

}
