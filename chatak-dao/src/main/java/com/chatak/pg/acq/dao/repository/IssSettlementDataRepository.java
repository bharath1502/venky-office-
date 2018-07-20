/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGIssSettlementData;

/**
 * @Author: Girmiti Software
 * @Date: Jun 15, 2018
 * @Time: 4:14:42 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface IssSettlementDataRepository extends JpaRepository<PGIssSettlementData, Long>,QueryDslPredicateExecutor<PGIssSettlementData> {
	
	List<PGIssSettlementData> findByAcqPmId(Long acqPmId);
	
	List<PGIssSettlementData> findByStatus(String status);

	/**
	 * @param programManagerId
	 * @param batchDate
	 * @return
	 */
	List<PGIssSettlementData> findByAcqPmIdAndBatchDate(Long programManagerId, Timestamp batchDate);

}
