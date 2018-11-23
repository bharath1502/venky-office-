/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGBatch;

/**
 * @Author: Girmiti Software
 * @Date: May 28, 2018
 * @Time: 10:27:08 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface BatchRepository extends JpaRepository<PGBatch, Long>,QueryDslPredicateExecutor<PGBatch> {
	
	public PGBatch findByProgramManagerIdAndStatus(Long programManagerId, String status);

}
