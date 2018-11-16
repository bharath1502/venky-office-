/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGTransactionCategoryCode;

/**
 * @Author: Girmiti Software
 * @Date: Aug 20, 2016
 * @Time: 3:25:01 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public interface TransactionCategoryCodeRepository extends
		JpaRepository<PGTransactionCategoryCode, Long>,
		QuerydslPredicateExecutor<PGTransactionCategoryCode> {

}
