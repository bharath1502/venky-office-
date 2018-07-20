/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.SplitTransactionDao;
import com.chatak.pg.acq.dao.model.PGSplitTransaction;
import com.chatak.pg.acq.dao.repository.SplitTransactionRepository;
import com.chatak.pg.util.CommonUtil;


/**
 * @Author: Girmiti Software
 * @Date: Jun 5, 2015
 * @Time: 8:39:01 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository("SplitTransactionDao")
public class SplitTransactionDaoImpl implements SplitTransactionDao {
  @Autowired
  SplitTransactionRepository splitTransactionRepository;


  /**
   * @param pgSplitTransaction
   * @return
   * @throws DataAccessException
   */
  @Override
  public PGSplitTransaction createOrUpdateTransaction(PGSplitTransaction pgSplitTransaction) {
    return splitTransactionRepository.save(pgSplitTransaction);
  }


  /**
   * @param merchantId
   * @param pgRefTransactionid
   * @param splitAmount
   * @return
   * @throws DataAccessException
   */
  @Override
  public PGSplitTransaction getPGSplitTransactionByMerchantIdAndPgRefTransactionIdAndSplitAmount(
      String merchantId, String pgRefTransactionid, Long splitAmount) {
    List<PGSplitTransaction> list =
        splitTransactionRepository.findByMerchantIdAndPgRefTransactionIdAndSplitAmount(merchantId,
            pgRefTransactionid, splitAmount);
    if (CommonUtil.isListNotNullAndEmpty(list)) {
      return list.get(0);
    }
    return null;
  }


  /**
   * @param merchantId
   * @param pgRefTransactionid
   * @return
   * @throws DataAccessException
   */
  @Override
  public PGSplitTransaction getPGSplitTransactionByMerchantIdAndPgRefTransactionId(
      String merchantId, String pgRefTransactionid) {
    List<PGSplitTransaction> list = splitTransactionRepository
        .findByMerchantIdAndPgRefTransactionId(merchantId, pgRefTransactionid);
    if (CommonUtil.isListNotNullAndEmpty(list)) {
      return list.get(0);
    }
    return null;
  }

}
