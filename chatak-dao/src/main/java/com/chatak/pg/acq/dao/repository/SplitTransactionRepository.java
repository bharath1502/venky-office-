/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGSplitTransaction;

/**
 * @Author: Girmiti Software
 * @Date: Jun 5, 2015
 * @Time: 8:40:34 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface SplitTransactionRepository extends JpaRepository<PGSplitTransaction, Long>,
QueryDslPredicateExecutor<PGSplitTransaction>{
  
  public List<PGSplitTransaction> findByMerchantIdAndPgRefTransactionIdAndSplitAmount( String merchantId, String pgRefTransactionId,Long splitAmount);
  public List<PGSplitTransaction> findByMerchantIdAndPgRefTransactionId( String merchantId, String pgRefTransactionId);

}
