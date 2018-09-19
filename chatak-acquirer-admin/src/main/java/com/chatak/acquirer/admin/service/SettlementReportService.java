package com.chatak.acquirer.admin.service;

import java.sql.Timestamp;
import java.util.List;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.TransactionResponse;
import com.chatak.pg.acq.dao.model.settlement.PGSettlementEntityHistory;
import com.chatak.pg.bean.settlement.SettlementMerchantDetails;
import com.chatak.pg.model.FeeReportRequest;
import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;

public interface SettlementReportService {

  public GetTransactionsListResponse searchSettlementReportTransactions(
      GetTransactionsListRequest getTransactionsListRequest) throws ChatakAdminException;

  public GetTransactionsListResponse searchBatchReportTransactions(
      GetBatchReportRequest batchReportRequest) throws ChatakAdminException;

  public TransactionResponse calculateSettlementAmounts(Long pmId);
  
  public TransactionResponse executeSettlement(Long pmId, String timeZoneOffset, String timeZoneRegion);
  
  public TransactionResponse getAllMatchedTxnsByPgTxns(FeeReportRequest transactionRequest) throws ChatakAdminException;

  public List<SettlementMerchantDetails> fetchMerchantDetailsByMerchantCode(String merchantCode);
  
  public void insertDataFromIssuanceSettlementTransaction();
 
  public PGSettlementEntityHistory findByBatchFileDateandAcqpmid(Long pmId, Timestamp date);

}
