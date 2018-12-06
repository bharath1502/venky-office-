/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGAccountFeeLog;

/**
 * @Author: Girmiti Software
 * @Date: Sep 21, 2015
 * @Time: 2:44:27 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface AccountFeeLogDao {
	public void createOrSave(PGAccountFeeLog pgAccountFeeLog) throws DataAccessException;
	
	public List<PGAccountFeeLog> getPGAccountFeeLogOnTransactionId(String transactionId);
}
