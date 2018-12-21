/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.bean.TransactionPopUpDataDto;
import com.chatak.pg.model.EFTRefTxnData;
import com.chatak.pg.model.TransactionHistoryRequest;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.user.bean.TransactionHistory;

/**
 * @Author: Girmiti Software
 * @Date: Aug 23, 2017
 * @Time: 6:29:26 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface RefundTransactionDao {

  public String generateBatchId();

  public String getBatchidOnEFTId(String eftId);

  public boolean isDuplicateBatchId(String batchId);

  public PGTransaction getTransactionOnId(String id);

  public Integer getRefundStatus(String pgTransactionId);

  public Long findMerchantFeeByMerchantId(String merchantId);

  public List<String> getTransactionIdsOnEftIds(String eftId);

  public Long getRefundedAmountOnTxnId(String pgTransactionId);

  public List<EFTRefTxnData> getEFTRefTxnDataList(String eftId);

  public TransactionPopUpDataDto getTransactionPopUpDataDto(String pgTransactionId);

  public PGTransaction getTransactionToRefund(String merchantId, String terminalId, String txnId);

  public PGTransaction findTransactionToRefundByPGTxnIdAndMerchantIdAndTerminalId(
      String transactionId, String merchantId, String terminalId);

  public PGTransaction getTransactionForVoidOrRefundByAccountTransactionId(
      String accountTransactionId, String merchantCode);

  public List<Transaction> getMerchantAndItsSubMerchantTransactionList(
      GetTransactionsListRequest getTransactionsListRequest);

  public PGTransaction findTransactionToRefundByPGTxnIdAndIssuerTxnIdAndMerchantId(
      String transactionId, String issuerTxnRefNum, String merchantId);

  public PGTransaction findRefundTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(
      String transactionId, String issuerTxnRefNum, String merchantId, String terminalId);

  public List<PGTransaction> findByMerchantIdAndTerminalIdAndTransactionIdAndStatusAndMerchantSettlementStatusInAndRefundStatusNotLike(
      String merchantId, String terminalId, String refId, Integer status, Integer refundStatus,
      List<String> merchantSettlementStatus);
  
  public List<TransactionHistory> getMerchantTransactionList(TransactionHistoryRequest transactionHistoryRequest);
}
