package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGFeeDetail;

/**
 *
 * DAO Repository class to process fee detail
 *
 * @author Girmiti Software
 * @date 08-Dec-2014 12:33:27 pm
 * @version 1.0
 */
public interface FeeDetailRepository extends JpaRepository<PGFeeDetail, Long>,
QueryDslPredicateExecutor<PGFeeDetail> {
  
  public List<PGFeeDetail> findById(Long pGFeeDetailId);
  
  public List<PGFeeDetail> findByTxnType(String txnType);
}
