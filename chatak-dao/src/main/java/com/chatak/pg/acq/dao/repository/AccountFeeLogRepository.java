/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGAccountFeeLog;

/**
 * @Author: Girmiti Software
 * @Date: Sep 21, 2015
 * @Time: 2:46:13 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface AccountFeeLogRepository extends
JpaRepository<PGAccountFeeLog, Long>,
QueryDslPredicateExecutor<PGAccountFeeLog>  {
public List<PGAccountFeeLog> findByTransactionId(String transactionId);
}
