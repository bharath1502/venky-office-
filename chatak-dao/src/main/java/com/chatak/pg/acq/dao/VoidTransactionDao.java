/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.model.PGTxnCardInfo;
import com.chatak.pg.model.DashBoardRecords;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.Transaction;

/**
 * @Author: Girmiti Software
 * @Date: Aug 24, 2017
 * @Time: 11:46:40 AM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface VoidTransactionDao {

  public List<ReportsDTO> getAllEftTransfers();

  public DashBoardRecords findDashBoardRecords();

  public void createCardInfo(PGTxnCardInfo txnCardInfo);

  public PGTransaction findTransactionToVoidByIssuerTxnId(String issuerTxnRefNum);

  public PGTransaction findTransactionToVoidByPGTxnIdAndIssuerTxnId(String txnId,
      String issuerTxnRefNum);

  public PGTransaction getTransactionToVoid(String merchantId, String terminalId, String txnId,
      String authId);

  public PGTransaction findTransactionToVoidByPGTxnIdAndMerchantIdAndTerminalId(
      String transactionId, String merchantId, String terminalId);

  public PGTransaction findTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(
      String transactionId, String issuerTxnRefNum, String merchantId, String terminalId);

  public PGTransaction createTransaction(PGTransaction pgTransaction);

  public List<Transaction> getTransactionListToDashBoard(
      GetTransactionsListRequest getTransactionsListRequest);

  public PGTransaction findTransaction(String merchantId, String terminalId, String authId,
      String cardNum, String invoiceNum);

  public List<PGTransaction> findByMerchantIdAndTerminalIdAndRefTransactionId(String merchantId,
      String terminalId, String refId);

  public List<PGTransaction> findByMerchantIdAndTerminalIdAndRefTransactionIdAndStatus(
      String merchantId, String terminalId, String refId, Integer status);

  public PGTransaction findTransactionToCaptureByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(
      String transactionId, String issuerTxnRefNum, String merchantId, String terminalId);

  public PGTransaction findTransactionToReversalByMerchantIdAndPGTxnId(String merchantId,
      String transactionId);

  public PGTransaction findTransactionToCaptureByPGTxnIdAndMerchantIdAndTerminalId(
      String transactionId, String merchantId, String terminalId);

  public List<Transaction> getSearchTransactions(
      GetTransactionsListRequest getTransactionsListRequest);

  public List<PGTransaction> getAllTransactionsOnMerchantCode(String merchantCode);
}
