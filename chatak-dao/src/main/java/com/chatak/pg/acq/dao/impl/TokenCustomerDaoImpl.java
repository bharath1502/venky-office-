/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.TokenCustomerDao;
import com.chatak.pg.acq.dao.model.PGTokenCustomer;
import com.chatak.pg.acq.dao.repository.TokenCustomerRepository;

/**
 * @Author: Girmiti Software
 * @Date: Jan 28, 2016
 * @Time: 6:36:58 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository
public class TokenCustomerDaoImpl implements TokenCustomerDao {
	
	@Autowired
	TokenCustomerRepository tokenCustomerRepository;

	/**
	 * @param userId
	 * @param password
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public PGTokenCustomer getTokenCustomerByUserIdAndPassword(String userId,
			String password) throws DataAccessException {
		return tokenCustomerRepository.findByUserIdAndPassword(userId, password);
	}

	/**
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public PGTokenCustomer getTokenCustomerByUserId(String userId)
			throws DataAccessException {
		return tokenCustomerRepository.findByUserId(userId);
	}

	/**
	 * @param tokenCustomer
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public PGTokenCustomer createOrUpdateTokenCustomer(
			PGTokenCustomer tokenCustomer) throws DataAccessException {
		return tokenCustomerRepository.save(tokenCustomer);
	}

}
