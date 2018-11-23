/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.bean.LitleEFTRequest;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;

/**
 * @Author: Girmiti Software
 * @Date: Aug 23, 2017
 * @Time: 5:08:26 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface ExecutedTransactionDao {

  public int getLitleExecutedTransactionsCount();

  public List<ReportsDTO> getAllAccountsExecutedTransactionsReport();

  public LitleEFTRequest getLitleExecutedTransactions(LitleEFTRequest litleEFTRequest);

  public List<ReportsDTO> getAllExecutedAccFeeOnDate(
      GetTransactionsListRequest getTransactionsListRequest);

  public List<ReportsDTO> getAllAccountsExecutedTransactionsOnDate(
      GetTransactionsListRequest getTransactionsListRequest);

  public LitleEFTRequest getLitleExecutedTransactionsOnMerchantCodeAndPayoutFrequency(
      String merchantCode, Integer payoutFrequencyDays);
}
