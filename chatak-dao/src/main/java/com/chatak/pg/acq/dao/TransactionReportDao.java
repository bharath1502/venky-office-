/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;

/**
 * @Author: Girmiti Software
 * @Date: Aug 24, 2017
 * @Time: 2:33:45 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface TransactionReportDao {

  public List<ReportsDTO> getAllTransactionsReport(
      GetTransactionsListRequest getTransactionsListRequest);

  public List<ReportsDTO> getTransactionListReport(
      GetTransactionsListRequest getTransactionsListRequest);

  public List<ReportsDTO> getAllTransactionsOnDate(
      GetTransactionsListRequest getTransactionsListRequest);

  public List<ReportsDTO> getVirtualAccountFeeLogOnDate(
      GetTransactionsListRequest getTransactionsListRequest);

  public PGTransaction getAuthTransaction(String merchantId, String terminalId, String txnId,
      String txnType, String authId);

  public PGTransaction findDuplicateTransactionOnPanAndInvoiceNumberAndMerchantIdAndTerminalIdAndTxnAmount(
      String pan, String invoiceNumber, String merchantId, String terminalId, Long txnAmount);
}
