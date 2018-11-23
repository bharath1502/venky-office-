/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.PGTransfers;

/**
 * @Author: Girmiti Software
 * @Date: Aug 16, 2015
 * @Time: 5:31:06 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface TransfersRepository extends JpaRepository<PGTransfers, Long>,
QueryDslPredicateExecutor<PGTransfers>{
  public List<PGTransfers> findByMerchantId(Long merchantId);
  public PGTransfers findByPgTransfersId(Long id);
  
  @Query("select t from PGTransfers t where t.transferMode=:transferMode and t.status='Pending'")
  public List<PGTransfers> getPendingTransfersCountOnTransferMode(@Param("transferMode") String transferMode);

  @Query("select t from PGTransfers t where t.transferMode=:transferMode and t.status='Executed'")
  public List<PGTransfers> getExecutedTransactionsCountOnTransferMode(@Param("transferMode") String transferMode);

  @Query("select t from PGTransfers t where t.transferMode=:transferMode and t.status='Processing'")
  public List<PGTransfers> getProcessingTransactionsCountOnTransferMode(@Param("transferMode") String transferMode);
  
  @Query("select t from PGTransfers t where t.transferMode=:transferMode and t.status='Cancelled'")
  public List<PGTransfers> getCanceledTransactionsCountOnTransferMode(@Param("transferMode") String transferMode);
  
}
