package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGSwitchTransaction;

/**
 * DAO Repository class to process Switch Transactions
 *
 * @author Girmiti Software
 * @date 08-Dec-2014 12:33:27 pm
 * @version 1.0
 */
public interface SwitchTransactionRepository extends
                                      JpaRepository<PGSwitchTransaction, Long>,
                                      QueryDslPredicateExecutor<PGSwitchTransaction> {

  public List<PGSwitchTransaction> findById(Long id);
}
