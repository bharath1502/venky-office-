/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.Transaction;

/**
 * @Author: Girmiti Software
 * @Date: Sep 16, 2017
 * @Time: 12:52:44 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface SettlementReportDao {

  public List<Transaction> getSettlementReportTransactions(
      GetTransactionsListRequest getTransactionsListRequest);

  public List<Transaction> getBatchReportTransactions(GetBatchReportRequest batchReportRequest);
}
