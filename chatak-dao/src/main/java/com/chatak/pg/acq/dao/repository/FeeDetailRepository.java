package com.chatak.pg.acq.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

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
QuerydslPredicateExecutor<PGFeeDetail> {
  
  public Optional<PGFeeDetail> findById(Long pGFeeDetailId);
  
  public List<PGFeeDetail> findByTxnType(String txnType);
}
