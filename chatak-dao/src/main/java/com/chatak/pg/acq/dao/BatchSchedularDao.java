/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.DailyFundingReport;
import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportRequest;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.Transaction;

/**
 * @Author: Girmiti Software
 * @Date: Sep 21, 2017
 * @Time: 12:27:43 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface BatchSchedularDao {

  public List<DailyFundingReport> searchDailyFundingReportDetails(
      GetDailyFundingReportRequest reportRequest);

  public void insertFundingReport();

  public Response showManualFundingReport();

  public PGMerchant getMerchantCodeAndCompanyName(Long userid);

  public List<DailyFundingReport> searchMerchantDailyFundingReportDetails(
      GetDailyFundingReportRequest reportRequest, String userType);

  public List<Transaction> getMerchantBatchReportTransactions(
      GetBatchReportRequest batchReportRequest);

  public List<Transaction> getTxnHistoryOnId(GetTransactionsListRequest transaction);
}
