/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.sql.Timestamp;

import com.chatak.pg.acq.dao.model.settlement.PGSettlementEntityHistory;

/**
 * @Author: Girmiti Software
 * @Date: Jun 27, 2018
 * @Time: 7:34:12 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface IssuanceSettlementTransactionHistoryDao {

	public void insertDataFromIssuanceSettlementTransaction();
	
	public PGSettlementEntityHistory findByBatchFileDateandAcqpmid(Long pmId, Timestamp date);
}
