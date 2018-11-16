package com.chatak.pg.acq.dao.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.model.PGOnlineTxnLog;

/**
 * DAO Repository class to process Activity Log
 *
 * @author Girmiti Software
 * @date 08-Dec-2014 12:33:27 pm
 * @version 1.0
 */
@Repository
public interface OnlineTxnLogRepository extends
                                      JpaRepository<PGOnlineTxnLog, Long>,
                                      QuerydslPredicateExecutor<PGOnlineTxnLog> {

  public Optional<PGOnlineTxnLog> findById(Long pGOnlineTxnLog);
  
  public List<PGOnlineTxnLog> findByMerchantIdAndOrderId(String merchantId, String orderId);
  
  public List<PGOnlineTxnLog> findByMerchantIdAndOrderIdAndTxnTotalAmountAndInvoceNumberAndRegisterNumberAndTxnType(String merchantId, String orderId,Long txnTotalAmount,String invoiceNumber,String registerNumber,String txnType);
  public List<PGOnlineTxnLog> findByMerchantIdAndOrderIdAndTxnType(String merchantId, String orderId,String txnType);
  @Query("select max(tt.requestDateTime) from PGOnlineTxnLog tt where tt.requestDateTime in (select t.requestDateTime from PGOnlineTxnLog t where t.merchantId=:merchantId and t.orderId=:orderId and t.txnTotalAmount=:txnTotalAmount and t.registerNumber=:registerNumber and t.panData=:panData and t.txnType=:txnType)")
  public Timestamp findByMerchantIdAndOrderIdAndTxnTotalAmountAndRegisterNumberAndPanDataAndTxnType(@Param("merchantId") String merchantId,@Param ("orderId") String orderId,@Param ("txnTotalAmount") Long txnTotalAmount,@Param ("registerNumber") String registerNumber,@Param ("panData") String panData,@Param ("txnType") String txnType);
  public List<PGOnlineTxnLog> findByPgTxnIdAndMerchantId(String processorTxnId,String merchantId);
  @Query("select max(tt.requestDateTime) from PGOnlineTxnLog tt where tt.requestDateTime in (select t.requestDateTime from PGOnlineTxnLog t where t.merchantId=:merchantId and t.orderId=:orderId and t.txnTotalAmount=:txnTotalAmount and t.panData=:panData and t.txnType=:txnType)")
  public Timestamp findByMerchantIdAndOrderIdAndTxnTotalAmountAndPanDataAndTxnType(@Param("merchantId") String merchantId,@Param ("orderId") String orderId,@Param ("txnTotalAmount") Long txnTotalAmount,@Param ("panData") String panData,@Param ("txnType") String txnType);  
}
