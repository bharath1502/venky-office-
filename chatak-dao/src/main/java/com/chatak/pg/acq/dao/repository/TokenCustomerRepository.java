/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGTokenCustomer;


/**
 * @Author: Girmiti Software
 * @Date: Jan 28, 2016
 * @Time: 6:38:35 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface TokenCustomerRepository extends JpaRepository<PGTokenCustomer, Long>, QueryDslPredicateExecutor<PGTokenCustomer>  {
	
	public PGTokenCustomer findByUserIdAndPassword(String userId, String password);
	
	public PGTokenCustomer findByUserId(String userId);
	
}
