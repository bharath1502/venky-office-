/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGPayPageConfig;

/**
 * @Author: Girmiti Software
 * @Date: Sep 30, 2016
 * @Time: 6:38:24 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface PayPageConfigRepository extends JpaRepository<PGPayPageConfig, Long>,QueryDslPredicateExecutor<PGPayPageConfig>{
	
	public PGPayPageConfig findByMerchantId(Long merchantId);

}
