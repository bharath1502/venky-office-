/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGAccountHistory;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;

/**
 * @Author: Girmiti Software
 * @Date: Feb 29, 2016
 * @Time: 11:30:09 AM
 * @Version: 1.0
 * @Comments:
 */
public interface AccountTransactionsDao {

  public boolean isDuplicateAccountTransactionId(String transactionId);

  public PGAccountTransactions createOrUpdate(PGAccountTransactions pgAccountTransactions);
  
  public List<PGAccountTransactions> createOrUpdate(PGAccountTransactions... pgAccountTransactions);

  public String generateAccountTransactionId();

  public PGAccountTransactions getAccountTransactionByTransferId(String transferId);

  public List<PGAccountTransactions> getAccountTransactionsOnTransactionId(String pgTransactionId);

  public List<PGAccountTransactions> getAccountTransactionsOnTransactionIdAndTransactionType(String pgTransactionId,
                                                                                             String transactionType);
  
  public PGAccountTransactions getAccountTransactionsOnTransactionIdAndTransactionCode(String pgTransactionId,String transactionCode);
  
  public GetTransactionsListResponse getAccountTransactions(GetTransactionsListRequest getTransactionsListRequest);
  
  public String getSaleAccountTransactionId(String accountTransactionId, String merchantCode);
  
  public List<PGAccountTransactions> getAccountTransactions(String accountTransactionId);
  
  public GetTransactionsListResponse getAccountAllTransactions(GetTransactionsListRequest getTransactionsListRequest);

/**
 * @param transferAccountCreditLog
 */
public PGAccountHistory saveAccountHistory(PGAccountHistory pgActHistory);

public GetTransactionsListResponse getManulAccountTransactions(GetTransactionsListRequest getTransactionsListRequest);
  
}
