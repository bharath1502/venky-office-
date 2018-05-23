package com.chatak.merchant.service;

import java.util.List;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.MerchantData;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportResponse;
import com.chatak.pg.user.bean.GetTransactionsListResponse;

public interface BatchSchedularReportService {
  
  public MerchantData getMerchantCodeAndCompanyName(Long userid);
  
  public List<PGMerchant> findById(Long parentMerchantId);
  
  public GetTransactionsListResponse searchBatchReportTransactions(
      GetBatchReportRequest batchReportRequest) throws ChatakMerchantException;
  
  public GetDailyFundingReportResponse searchDailyFundingReportDetails(
      GetDailyFundingReportRequest getDailyFundingReportRequest, String userType) throws ChatakMerchantException;

}
