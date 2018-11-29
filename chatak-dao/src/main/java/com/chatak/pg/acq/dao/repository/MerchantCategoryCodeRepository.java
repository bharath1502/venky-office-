/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGMerchantCategoryCode;

/**
 * @Author: Girmiti Software
 * @Date: Aug 22, 2016
 * @Time: 2:54:34 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public interface MerchantCategoryCodeRepository extends
		JpaRepository<PGMerchantCategoryCode, Long>,
		QueryDslPredicateExecutor<PGMerchantCategoryCode> {

	public PGMerchantCategoryCode findByMerchantCategoryCode(String mcc);
	
	public PGMerchantCategoryCode findById(Long id);
}
