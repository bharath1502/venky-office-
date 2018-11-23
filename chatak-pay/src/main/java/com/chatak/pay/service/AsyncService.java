package com.chatak.pay.service;

import com.chatak.pg.acq.dao.model.PGOnlineTxnLog;
import com.chatak.pg.enums.TransactionStatus;

public interface AsyncService {

  /**
   * @param pgOnlineTxnLog
   * @param txnState
   * @param reason
   * @param pgTxnId
   * @param processorResponse
   * @param processTxnId
   */
  public void logExit(PGOnlineTxnLog pgOnlineTxnLog, TransactionStatus txnState, String reason,
      String pgTxnId, String processorResponse, String processTxnId);
  
  /**
   * @param merchantId
   * @param terminalId
   * @param txnId
   * @param txnType
   * @param status
   * @param comments
   * @param feeAmount
   * @param batchId
   * @param pgOnlineTxnLog
   * @throws Exception
   */
  public void updateSettlementStatus(String merchantId,
          String terminalId, String txnId, String txnType,
          String status, String comments, long feeAmount,String batchId, PGOnlineTxnLog pgOnlineTxnLog) throws Exception;
  
  /**
   * @param pgTransactionId
   * @param txnType
   * @param newStatus
   */
  public void updateAccountCCTransactions(String pgTransactionId, String txnType, String newStatus, Long totalFeeAmount);
}
