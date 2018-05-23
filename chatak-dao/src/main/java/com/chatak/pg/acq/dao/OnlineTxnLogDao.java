package com.chatak.pg.acq.dao;

import java.sql.Timestamp;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGOnlineTxnLog;


public interface OnlineTxnLogDao {
	
	/**
	 * Method to log online transaction request
	 * 
	 * @param pgOnlineTxnLog
	 * @throws DataAccessException
	 */
	public PGOnlineTxnLog logRequest(PGOnlineTxnLog pgOnlineTxnLog) throws DataAccessException;
	
	/**
   * Method to check duplicate txn
   * 
   * @param merchantCode
   * @param orderId
   * @return
   * @throws DataAccessException
   */
  public boolean isDuplicateRequest(String merchantCode, String orderId) throws DataAccessException;
  
  public boolean isDuplicateRequest(String merchantCode, String orderId,Long txnAmount,String invoiceNumber,String registerNumber,String txnType) throws DataAccessException;
  public boolean isDuplicateRequest(String merchantCode, String orderId,String txnType) throws DataAccessException;
  public Timestamp duplicateRequest(String merchantCode,
                                    String orderId,
                                    Long txnAmount,
                                    String registerNumber,
                                    String panData,
                                    String txnType)throws DataAccessException;
  
  public PGOnlineTxnLog getTransactionOnPgTxnIdAndMerchantId(String transactionId,String merchantId);
  public Timestamp duplicateOrderRequest(String merchantCode,
                                    String orderId,
                                    Long txnAmount,
                                    String panData,
                                    String txnType)throws DataAccessException;
  
 
}
