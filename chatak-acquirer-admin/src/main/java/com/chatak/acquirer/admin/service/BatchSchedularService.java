package com.chatak.acquirer.admin.service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.GetDailyFundingReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportResponse;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;

public interface BatchSchedularService {

  public GetDailyFundingReportResponse searchDailyFundingReportDetails(
      GetDailyFundingReportRequest getDailyFundingReportRequest) throws ChatakAdminException;

  public void fundingReportSchedular();

  public Response manualFundingReport();

  public GetTransactionsListResponse getMerchantHistoryOnId(GetTransactionsListRequest transaction);
}
