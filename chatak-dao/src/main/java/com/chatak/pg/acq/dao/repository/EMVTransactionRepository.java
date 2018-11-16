package com.chatak.pg.acq.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGEMVTransaction;

/**
 * DAO Repository class to process EMV Transactions
 *
 * @author Girmiti Software
 * @date 08-Dec-2014 12:33:27 pm
 * @version 1.0
 */
public interface EMVTransactionRepository extends
                                         JpaRepository<PGEMVTransaction, Long>,
                                         QuerydslPredicateExecutor<PGEMVTransaction> {

  public Optional<PGEMVTransaction> findById(Long pGEmvTransactionId);

  public List<PGEMVTransaction> findByPgTransactionId(Long pgTransactionId);

}
