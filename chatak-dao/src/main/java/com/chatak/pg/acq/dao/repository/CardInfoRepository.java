package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGTxnCardInfo;

/**
 *
 * DAO Repository class to process Card Info
 *
 * @author Girmiti Software
 * @date 08-Dec-2014 12:33:27 pm
 * @version 1.0
 */
public interface CardInfoRepository extends JpaRepository<PGTxnCardInfo, Long>,
QueryDslPredicateExecutor<PGTxnCardInfo> {
  
  public List<PGTxnCardInfo> findById(Long id);
  
  public List<PGTxnCardInfo> findByTransactionId(String transactionId);

  public List<PGTxnCardInfo> findByCardNumber(String cardNumber);
  
}
