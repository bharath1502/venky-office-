package com.chatak.acquirer.admin.service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;

public interface SettlementReportService {

  public GetTransactionsListResponse searchSettlementReportTransactions(
      GetTransactionsListRequest getTransactionsListRequest) throws ChatakAdminException;

  public GetTransactionsListResponse searchBatchReportTransactions(
      GetBatchReportRequest batchReportRequest) throws ChatakAdminException;
}
