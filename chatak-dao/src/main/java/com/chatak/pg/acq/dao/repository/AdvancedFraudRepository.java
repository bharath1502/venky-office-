package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGMerchantAdvancedFraud;

/**
 * @Author: Girmiti Software
 * @Date: Aug 17, 2016
 * @Time: 6:44:13 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public interface AdvancedFraudRepository extends
		JpaRepository<PGMerchantAdvancedFraud, Long>,
		QueryDslPredicateExecutor<PGMerchantAdvancedFraud> {

	/**
	 * @param id
	 * @param merchantCode
	 * @return
	 */
	public PGMerchantAdvancedFraud findByIdAndMerchantCode(Long id,String merchantCode);

}
