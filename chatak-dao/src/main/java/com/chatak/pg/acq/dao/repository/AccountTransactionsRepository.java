/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.PGAccountTransactions;

/**
 * @Author: Girmiti Software
 * @Date: Feb 29, 2016
 * @Time: 11:31:39 AM
 * @Version: 1.0
 * @Comments:
 */
public interface AccountTransactionsRepository extends
                                              JpaRepository<PGAccountTransactions, Long>,
                                              QueryDslPredicateExecutor<PGAccountTransactions> {
  public List<PGAccountTransactions> findByAccountTransactionId(String accountTransactionId);
  
  public PGAccountTransactions findByPgTransferId(String pgTransferId);
  
  public List<PGAccountTransactions> findByPgTransactionId(String pgTransactionId);
  
  public List<PGAccountTransactions> findByPgTransactionIdAndTransactionType(String pgTransactionId,String transactionType);
  
  public PGAccountTransactions findByPgTransactionIdAndTransactionCode(String pgTransactionId,String transactionCode);
  
  public List<PGAccountTransactions> findByAccountTransactionIdAndTransactionTypeAndMerchantCode(String accountTransactionId,String transactionType,String merchantCode);
  
  @Query("select sum(t.debit) from PGAccountTransactions t where t.merchantCode=:merchantCode and t.transactionCode='CC_MERCHANT_FEE_DEBIT' and t.transactionType='refund'")
  public Long getMerchantDebitedFeeByMerchantId(@Param("merchantCode") String merchantId);

}
