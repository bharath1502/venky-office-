/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.settlement.PGSettlementTransaction;

/**
 * @Author: Girmiti Software
 * @Date: Jun 19, 2018
 * @Time: 8:59:05 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public interface IssuanceSettlementRepository
		extends JpaRepository<PGSettlementTransaction, Long>, QueryDslPredicateExecutor<PGSettlementTransaction> {


  public List<PGSettlementTransaction> findByIssuanceSettlementEntityId(Long issuanceSettlementEntityId);
}
