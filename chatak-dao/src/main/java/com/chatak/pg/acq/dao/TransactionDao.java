package com.chatak.pg.acq.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.bean.settlement.SettlementEntity;
import com.chatak.pg.model.TransactionRequest;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.PanRangeRequest;
import com.chatak.pg.user.bean.Transaction;

public interface TransactionDao {

  /**
   * Method to search transaction record on reference transaction number
   * 
   * @param merchantId
   * @param terminalId
   * @param refTxnId
   * @return Transaction record
   * @throws DataAccessException
   */
  public PGTransaction getTransactionOnRefNumber(String merchantId, String terminalId,
      String refTxnId);

  /**
   * Method to search transaction record on transaction number
   * 
   * @param merchantId
   * @param terminalId
   * @param transactionrefNum
   *          /RRN
   * @return Transaction record
   * @throws DataAccessException
   */
  public PGTransaction getTransaction(String merchantId, String terminalId, String txnId);

  /**
   * @param merchantId
   * @param terminalId
   * @param txnId
   * @param authId
   * @param txnType
   * @return Transaction record
   * @throws DataAccessException
   */
  public PGTransaction getTransaction(String merchantId, String terminalId, String txnId,
      String authId, String txnType);

  /**
   * Method to search transaction record on Invoice number
   * 
   * @param merchantId
   * @param terminalId
   * @param inVoiceNum
   * @return Transaction record
   * @throws DataAccessException
   */
  public PGTransaction getTransactionOnInvoiceNum(String merchantId, String terminalId,
      String inVoiceNum);

  /**
   * Method to get all transactions
   * 
   * @return
   * @throws DataAccessException
   */
  public List<PGTransaction> getAllTransactions();

  /**
   * Method to get all transactions on criteria
   * 
   * @return
   * @throws DataAccessException
   */
  public List<PGTransaction> getTransactionList(
      GetTransactionsListRequest getTransactionsListRequest);



  public PGTransaction getTransactionOnTxnIdAndTxnType(String merchantId, String terminalId,
      String txnId, String txnType);

  /**
   * @param merchantId
   * @param terminalId
   * @param refId
   * @return
   */



  public List<Transaction> getTransactions(GetTransactionsListRequest getTransactionsListRequest, Long entityId);
  
  public List<Long> fetchCardProgramDetailsByMerchantCode(TransactionRequest transactionRequest);
  
  public List<SettlementEntity> getPgTransactions(String merchantId, String terminalId, String issuerTxnRefNum,
			String transactionId);
	
  public List<PGTransaction> getTransactionsByBatchId(String batchId);

  public List<PGTransaction> getPGTransactionListNotInAcquiring(String batchId, List<String> pgTxnIds);
  
  public List<PGAccountTransactions> getPGAccTransactionsByTxnId(String pgTxnId);
    
  public void saveorUpdate(List<String> pgTxnIdsList);
  
  public String generateTransactionRefNumber() throws DataAccessException;

  public List<PanRangeRequest> getPgPanRanges(String merchantId);
}
