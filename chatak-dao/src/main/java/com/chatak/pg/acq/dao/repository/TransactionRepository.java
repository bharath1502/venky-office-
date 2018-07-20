package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGTransaction;

/**
 * DAO Repository class to process Transactions
 * 
 * @author Girmiti Software
 * @date 08-Dec-2014 12:33:27 pm
 * @version 1.0
 */
public interface TransactionRepository extends
                                      JpaRepository<PGTransaction, Long>,
                                      QueryDslPredicateExecutor<PGTransaction> {

  public List<PGTransaction> findById(Long pGTransactionId);

  public List<PGTransaction> findByTransactionType(String transactionType);

  public List<PGTransaction> findByMerchantIdAndTerminalIdAndTransactionIdAndTransactionTypeAndAuthId(String merchantId,
                                                                                                      String terminalId,
                                                                                                      String txnId,
                                                                                                      String txnType,
                                                                                                      String authId);

  @Query("select t from PGTransaction t where t.merchantId = :merchantId and t.terminalId = :terminalId and t.transactionId = :txnId and (t.transactionType = :saleTxnType or t.transactionType = :refundTxnType) and t.status='0' and t.transactionId not in(select tin.refTransactionId from PGTransaction tin where tin.refTransactionId=:txnId and tin.transactionType='void' and tin.status='0') ")
  public List<PGTransaction> findByVoidTransactionType(@Param("merchantId") String merchantId,
                                                       @Param("terminalId") String terminalId,
                                                       @Param("txnId") String txnId,
                                                       @Param("saleTxnType") String saleTxnType,
                                                       @Param("refundTxnType") String refundTxnType);

  public List<PGTransaction> findByMerchantIdAndTerminalIdAndRefTransactionId(String merchantId,
                                                                              String terminalId,
                                                                              String refTransactionId);

  public List<PGTransaction> findByMerchantIdAndTerminalIdAndRefTransactionIdAndStatus(String merchantId,
                                                                                       String terminalId,
                                                                                       String refTransactionId,
                                                                                       Integer status);

  public List<PGTransaction> findByMerchantIdAndTerminalIdAndTransactionId(String merchantId,
                                                                           String terminalId,
                                                                           String txnId);

  public List<PGTransaction> findByMerchantIdAndTerminalIdAndInvoiceNumber(String merchantId,
                                                                           String terminalId,
                                                                           String invoiceNumber);

  @Query("select t from PGTransaction t where t.issuerTxnRefNum = :issuerTxnRefNum and t.status = 0 and t.issuerTxnRefNum not in (select t from PGTransaction t where t.issuerTxnRefNum = :issuerTxnRefNum and t.status = 0 and t.transactionType = 'void')")
  public List<PGTransaction> findTransactionToVoidByIssuerTxnId(@Param("issuerTxnRefNum") String issuerTxnRefNum);

  @Query("select t from PGTransaction t where t.transactionId = :transactionId and t.issuerTxnRefNum = :issuerTxnRefNum and t.status = 0 and t.transactionId not in (select tin.refTransactionId from PGTransaction tin where tin.refTransactionId = :transactionId and tin.status = 0 and tin.transactionType = 'void')")
  public List<PGTransaction> findTransactionToVoidByPGTxnIdAndIssuerTxnId(@Param("transactionId") String transactionId,
                                                                          @Param("issuerTxnRefNum") String issuerTxnRefNum);

  public PGTransaction findByMerchantIdAndTerminalIdAndAuthIdAndPanAndInvoiceNumber(String merchantId,
                                                                                    String terminalId,
                                                                                    String authId,
                                                                                    String pan,
                                                                                    String invoiceNumber);

  public List<PGTransaction> findByMerchantIdAndTerminalIdAndTransactionIdAndTransactionType(String merchantId,
                                                                                             String terminalId,
                                                                                             String txnId,
                                                                                             String txnType);

  @Query("select t from PGTransaction t where t.merchantId = :merchantId and t.terminalId = :terminalId and t.transactionId = :txnId and t.transactionType = :saleTxnType ")
  public List<PGTransaction> findBySaleTransactionType(@Param("merchantId") String merchantId,
                                                       @Param("terminalId") String terminalId,
                                                       @Param("txnId") String txnId,
                                                       @Param("saleTxnType") String saleTxnType);

  //@Query("select t from PGTransaction t where t.transactionId = :transactionId and t.issuerTxnRefNum = :issuerTxnRefNum and t.terminalId=:terminalId and t.merchantId=:merchantId and t.status = 0 and t.merchantSettlementStatus='Pending' and t.transactionId not in (select tin.refTransactionId from PGTransaction tin where tin.refTransactionId = :transactionId and tin.status = 0 and tin.transactionType = 'void')")
  @Query("select t from PGTransaction t where t.transactionId = :transactionId and t.issuerTxnRefNum = :issuerTxnRefNum and t.terminalId=:terminalId and t.merchantId=:merchantId and t.status = 0 and t.transactionId not in (select tin.refTransactionId from PGTransaction tin where tin.refTransactionId = :transactionId and tin.status = 0 and tin.transactionType = 'void')")
  public List<PGTransaction> findTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(@Param("transactionId") String transactionId,
                                                                                                    @Param("issuerTxnRefNum") String issuerTxnRefNum,
                                                                                                    @Param("merchantId") String merchantId,
                                                                                                    @Param("terminalId") String terminalId);

  @Query("select t from PGTransaction t where t.transactionId = :transactionId and t.issuerTxnRefNum = :issuerTxnRefNum and t.terminalId=:terminalId and t.merchantId=:merchantId and t.status = 0 and t.transactionType ='auth' and t.transactionId not in (select tin.refTransactionId from PGTransaction tin where tin.refTransactionId = :transactionId and tin.status = 0 and tin.transactionType = 'sale')")
  public List<PGTransaction> findTransactionToCaptureByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(@Param("transactionId") String transactionId,
                                                                                                       @Param("issuerTxnRefNum") String issuerTxnRefNum,
                                                                                                       @Param("merchantId") String merchantId,
                                                                                                       @Param("terminalId") String terminalId);

  @Query("select t from PGTransaction t where t.transactionId = :transactionId and t.issuerTxnRefNum = :issuerTxnRefNum and t.merchantId=:merchantId and t.status = 0 and t.transactionType ='sale' and t.transactionId not in (select tin.refTransactionId from PGTransaction tin where tin.refTransactionId = :transactionId and tin.status = 0 and (tin.transactionType ='void') )")
  public List<PGTransaction> findTransactionToRefundByPGTxnIdAndIssuerTxnIdAndMerchantId(@Param("transactionId") String transactionId,
                                                                                                      @Param("issuerTxnRefNum") String issuerTxnRefNum,
                                                                                                      @Param("merchantId") String merchantId);

  @Query("select t from PGTransaction t where t.transactionId = :transactionId and t.merchantId=:merchantId and t.status = 0 and t.transactionType ='sale' and t.transactionId not in (select tin.refTransactionId from PGTransaction tin where tin.refTransactionId = :transactionId and tin.status = 0 and (tin.transactionType = 'refund' or tin.transactionType ='void') )")
  public List<PGTransaction> findTransactionToReverseByPGTxnIdAndMerchantId(@Param("transactionId") String transactionId,
                                                                            @Param("merchantId") String merchantId);

  @Query("select t from PGTransaction t where t.transactionId = :transactionId and t.issuerTxnRefNum = :issuerTxnRefNum and t.terminalId=:terminalId and t.merchantId=:merchantId and t.status = 0 and t.transactionType ='refund' and t.transactionId not in (select tin.refTransactionId from PGTransaction tin where tin.refTransactionId = :transactionId and tin.status = 0 and tin.transactionType ='void' )")
  public List<PGTransaction> findRefundTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(@Param("transactionId") String transactionId,
                                                                                                          @Param("issuerTxnRefNum") String issuerTxnRefNum,
                                                                                                          @Param("merchantId") String merchantId,
                                                                                                          @Param("terminalId") String terminalId);
  
  
  @Query("select t from PGTransaction t where t.merchantSettlementStatus= :status")
  public List<PGTransaction> getTransactionsOnStatus(@Param("status") String status);
  
  @Query("select count(t) from PGTransaction t where t.merchantSettlementStatus= :status")
  public Long getTransactionsCountOnStatus(@Param("status") String status);

  /**
   * 
   * 
   * @param transactionId
   * @param merchantId
   * @param terminalId
   * @return
   */
  @Query("select t from PGTransaction t where t.transactionId = :transactionId  and t.terminalId=:terminalId and t.merchantId=:merchantId and t.status = 0 and t.merchantSettlementStatus='Pending' and t.transactionId not in (select tin.refTransactionId from PGTransaction tin where tin.refTransactionId = :transactionId and tin.status = 0 and tin.transactionType = 'void')")
  public List<PGTransaction> findTransactionToVoidByPGTxnIdAndMerchantIdAndTerminalId(@Param("transactionId") String transactionId,
                                                                                      @Param("merchantId") String merchantId,
                                                                                      @Param("terminalId") String terminalId);

  @Query("select t from PGTransaction t where t.transactionId = :transactionId  and t.terminalId=:terminalId and t.merchantId=:merchantId and t.status = 0 and t.transactionType ='auth' and t.transactionId not in (select tin.refTransactionId from PGTransaction tin where tin.refTransactionId = :transactionId and tin.status = 0 and tin.transactionType = 'sale')")
  public List<PGTransaction> findTransactionToCaptureByPGTxnIdAndMerchantIdAndTerminalId(@Param("transactionId") String transactionId,
                                                                                         @Param("merchantId") String merchantId,
                                                                                         @Param("terminalId") String terminalId);

  @Query("select t from PGTransaction t where t.transactionId = :transactionId and t.terminalId=:terminalId and t.merchantId=:merchantId and t.status = 0 and t.transactionType ='sale' and t.transactionId not in (select tin.refTransactionId from PGTransaction tin where tin.refTransactionId = :transactionId and tin.status = 0 and (tin.transactionType = 'refund' or tin.transactionType ='void') )")
  public List<PGTransaction> findTransactionToRefundByPGTxnIdAndMerchantIdAndTerminalId(@Param("transactionId") String transactionId,
                                                                                        @Param("merchantId") String merchantId,
                                                                                        @Param("terminalId") String terminalId);
  public List<PGTransaction> findByMerchantIdAndTerminalIdAndInvoiceNumberAndTxnAmountAndPan(String merchantId,
                                                                           String terminalId,
                                                                           String invoiceNumber,Long txnAmount,String pan);
  @Query("select t from PGTransaction t where t.merchantId=:merchantId )")
  public List<PGTransaction> getAllTransactionsOnMerchantCode(@Param("merchantId") String merchantId);
  
  @Query("select t from PGTransaction t where t.merchantId = :merchantId and t.merchantSettlementStatus='Executed'")
  public List<PGTransaction> getExecutedTransactionsOnMerchantCode(@Param("merchantId") String merchantId);
  
  public List<PGTransaction> findByTransactionId(String pGTransactionId);
  
  @Query("select sum(tin.txnTotalAmount) from PGTransaction tin where tin.refTransactionId = :transactionId and tin.status = 0 and tin.transactionType ='refund' ")
  public Long getRefundedAmountByPGTxnId(@Param("transactionId") String transactionId);
  
  public List<PGTransaction> findByMerchantIdAndTerminalIdAndTransactionIdAndStatusAndMerchantSettlementStatusInAndRefundStatusNotLike(String merchantId,
          String terminalId,
          String refTransactionId,
          Integer status,
          List<String> merchantSettlementStatus,
          Integer refundStatus);
  
  public List<PGTransaction> findByMerchantIdAndTransactionId(String merchantId, String txnId);
  
  @Query("select sum(t.merchantFeeAmount) from PGTransaction t where t.merchantId=:merchantId")
  public Long getMerchantFeeByMerchantId(@Param("merchantId") String merchantId);
    
  public List<PGTransaction> findByBatchId(String batchId);
}
