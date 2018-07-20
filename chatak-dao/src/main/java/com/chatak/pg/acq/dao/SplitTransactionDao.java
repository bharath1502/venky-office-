/**
 * 
 */
package com.chatak.pg.acq.dao;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGSplitTransaction;

/**
 * @Author: Girmiti Software
 * @Date: Jun 5, 2015
 * @Time: 8:38:09 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface SplitTransactionDao {
  
  public PGSplitTransaction createOrUpdateTransaction(PGSplitTransaction pgSplitTransaction) throws DataAccessException;
  
  public PGSplitTransaction getPGSplitTransactionByMerchantIdAndPgRefTransactionIdAndSplitAmount(String merchantId,String pgRefTransactionid,Long splitAmount) throws DataAccessException;
  
  public PGSplitTransaction getPGSplitTransactionByMerchantIdAndPgRefTransactionId(String merchantId,String pgRefTransactionid) throws DataAccessException;

}
