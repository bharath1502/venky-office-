/**
 * 
 */
package com.chatak.pg.acq.dao;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGTokenCustomer;

/**
 * @Author: Girmiti Software
 * @Date: Jan 28, 2016
 * @Time: 6:35:09 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface TokenCustomerDao {
	
	public PGTokenCustomer getTokenCustomerByUserIdAndPassword(String userId,String password) throws DataAccessException;
	
	public PGTokenCustomer getTokenCustomerByUserId(String userId) throws DataAccessException;
	
	public PGTokenCustomer createOrUpdateTokenCustomer(PGTokenCustomer tokenCustomer) throws DataAccessException;
	
	

}
