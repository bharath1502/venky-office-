/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGSettlementReport;

/**
 * @Author: Girmiti Software
 * @Date: Jun 6, 2018
 * @Time: 6:50:33 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface SettlementRepository extends JpaRepository<PGSettlementReport, Long>,QueryDslPredicateExecutor<PGSettlementReport>{

}
