/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGUserActivityLog;

/**
 * @Author: Girmiti Software
 * @Date: Sep 15, 2015
 * @Time: 6:07:14 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public interface UserActivityLogRepository extends
		JpaRepository<PGUserActivityLog, Long>,
		QueryDslPredicateExecutor<PGUserActivityLog> {
	

}
