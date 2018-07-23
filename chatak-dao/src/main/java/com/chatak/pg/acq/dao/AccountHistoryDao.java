/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGAccountHistory;
import com.chatak.pg.user.bean.MerchantAccountHistory;

/**
 * @Author: Girmiti Software
 * @Date: Jun 16, 2015
 * @Time: 10:53:07 AM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface AccountHistoryDao {

  public void createOrSave(PGAccountHistory accountHistory) throws DataAccessException;
  public List<PGAccountHistory> getHistoryByAccountNum(Long accountNumber) throws DataAccessException;
  public List<PGAccountHistory> SearchAccountHistory(MerchantAccountHistory merchantAccountHistory);
}
