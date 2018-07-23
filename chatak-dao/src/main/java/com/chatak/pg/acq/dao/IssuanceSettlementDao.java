/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.settlement.PGSettlementEntity;
import com.chatak.pg.acq.dao.model.settlement.PGSettlementTransaction;
import com.chatak.pg.bean.settlement.IssuanceSettlementTransactionEntity;
import com.chatak.pg.bean.settlement.SettlementEntity;
import com.chatak.pg.exception.PrepaidAdminException;

/**
 * @Author: Girmiti Software
 * @Date: Jun 19, 2018
 * @Time: 9:08:03 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public interface IssuanceSettlementDao {

	public void addIssuanceSettlementTransaction(PGSettlementEntity settlementEntity, Integer batchCount, Integer batchSize);

	/**
	 * @return
	 */
	public List<IssuanceSettlementTransactionEntity> getAllMatchedTransactions(Long pmId);

	public void deleteAllIssuanceSettlementData(String programManagerId) throws PrepaidAdminException;

}
