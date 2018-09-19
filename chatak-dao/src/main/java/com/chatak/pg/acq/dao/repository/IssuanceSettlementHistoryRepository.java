/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.settlement.PGSettlementEntityHistory;

/**
 * @Author: Girmiti Software
 * @Date: Aug 18, 2018
 * @Time: 11:57:57 AM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface IssuanceSettlementHistoryRepository extends JpaRepository<PGSettlementEntityHistory, Long>,QueryDslPredicateExecutor<PGSettlementEntityHistory>{
        
	
	 public PGSettlementEntityHistory findByAcqPmIdAndBatchFileDate(Long programManagerId, Timestamp batchDate);
}
