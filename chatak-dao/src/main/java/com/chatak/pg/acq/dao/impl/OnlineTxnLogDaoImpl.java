package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.OnlineTxnLogDao;
import com.chatak.pg.acq.dao.model.PGOnlineTxnLog;
import com.chatak.pg.acq.dao.repository.OnlineTxnLogRepository;

@Repository("onlineTxnLogDao")
public class OnlineTxnLogDaoImpl implements OnlineTxnLogDao {

  private static Logger log = Logger.getLogger(OnlineTxnLogDaoImpl.class);

  @Autowired
  private OnlineTxnLogRepository onlineTxnLogRepository;

  /**
   * Method to log request packet
   * 
   * @param pgOnlineTxnLog
   */
  @Override
  public PGOnlineTxnLog logRequest(PGOnlineTxnLog pgOnlineTxnLog) throws DataAccessException {
    log.debug("OnlineTxnLogDaoImpl | logRequest | Entering");
    return onlineTxnLogRepository.save(pgOnlineTxnLog);
  }
  
  /**
   * Method to check duplicate txn
   * 
   * @param merchantCode
   * @param orderId
   * @return
   * @throws DataAccessException
   */
  public boolean isDuplicateRequest(String merchantCode, String orderId) throws DataAccessException {
    List<PGOnlineTxnLog> pgOnlineTxnLogs = onlineTxnLogRepository.findByMerchantIdAndOrderId(merchantCode, orderId);
    return (null != pgOnlineTxnLogs && !pgOnlineTxnLogs.isEmpty());
  }
  /**
   * Method to check duplicate txn
   * 
   * @param merchantCode
   * @param orderId
   * @return
   * @throws DataAccessException
   */
  public boolean isDuplicateRequest(String merchantCode, String orderId,Long txnAmount,String invoiceNumber,String registerNumber,String txnType) throws DataAccessException {
    List<PGOnlineTxnLog> pgOnlineTxnLogs = onlineTxnLogRepository.findByMerchantIdAndOrderIdAndTxnTotalAmountAndInvoceNumberAndRegisterNumberAndTxnType(merchantCode, orderId, txnAmount, invoiceNumber, registerNumber,txnType);
    return (null != pgOnlineTxnLogs && !pgOnlineTxnLogs.isEmpty());
  }

  /**
   * @param merchantCode
   * @param orderId
   * @param txnType
   * @return
   * @throws DataAccessException
   */
  @Override
  public boolean isDuplicateRequest(String merchantCode, String orderId, String txnType) throws DataAccessException {
    List<PGOnlineTxnLog> pgOnlineTxnLogs = onlineTxnLogRepository.findByMerchantIdAndOrderIdAndTxnType(merchantCode, orderId, txnType);
    return (null != pgOnlineTxnLogs && !pgOnlineTxnLogs.isEmpty());
  }

  /**
   * @param merchantCode
   * @param orderId
   * @param registerNumber
   * @param panData
   * @param txnType
   * @return
   * @throws DataAccessException
   */
  @Override
  public Timestamp duplicateRequest(String merchantCode,
                                    String orderId,
                                    Long txnAmount,
                                    String registerNumber,
                                    String panData,
                                    String txnType) throws DataAccessException {
    Timestamp pgOnlineTxnLogsTimestamp = onlineTxnLogRepository.findByMerchantIdAndOrderIdAndTxnTotalAmountAndRegisterNumberAndPanDataAndTxnType(merchantCode, orderId, txnAmount, registerNumber, panData, txnType);
   
    return pgOnlineTxnLogsTimestamp;
  }

  /**
   * @param transactionId
   * @param merchantId
   * @return
   */
  @Override
  public PGOnlineTxnLog getTransactionOnPgTxnIdAndMerchantId(String transactionId, String merchantId) {
    PGOnlineTxnLog pgOnlineTxnLog=null;
    List<PGOnlineTxnLog>  pgOnlineTxnLogsList=onlineTxnLogRepository.findByPgTxnIdAndMerchantId(transactionId,merchantId);
      
      if(pgOnlineTxnLogsList != null && pgOnlineTxnLogsList.size() > 0) {
        pgOnlineTxnLog = pgOnlineTxnLogsList.get(0);
      }
      log.debug("OnlineTxnLogDaoImpl | getTransactionOnPgTxnIdAndMerchantId | Exiting");
      return pgOnlineTxnLog;
  }
  
  @Override
  public Timestamp duplicateOrderRequest(String merchantCode,
                                    String orderId,
                                    Long txnAmount,
                                    String panData,
                                    String txnType) throws DataAccessException {
    Timestamp pgOnlineTxnLogsTimestamp = onlineTxnLogRepository.findByMerchantIdAndOrderIdAndTxnTotalAmountAndPanDataAndTxnType(merchantCode, orderId, txnAmount, panData, txnType);
   
    return pgOnlineTxnLogsTimestamp;
  }

}
