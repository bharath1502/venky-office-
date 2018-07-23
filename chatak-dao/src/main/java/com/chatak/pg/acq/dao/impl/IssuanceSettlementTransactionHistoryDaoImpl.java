/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.IssuanceSettlementTransactionHistoryDao;
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;

/**
 * @Author: Girmiti Software
 * @Date: Jun 27, 2018
 * @Time: 7:35:48 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository
public class IssuanceSettlementTransactionHistoryDaoImpl implements IssuanceSettlementTransactionHistoryDao {
	
	private static Logger logger = Logger.getLogger(IssuanceSettlementTransactionHistoryDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void insertDataFromIssuanceSettlementTransaction() {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		try {
			StringBuilder sb = new StringBuilder("call `sp_settlement_txn_history`()");
			Query qry = entityManager.createNativeQuery(sb.toString());
			int i = qry.executeUpdate();
			LogHelper.logInfo(logger, LoggerMessage.getCallerName(),
					"Inserted Into IsuuanceSettlementTransactionHistory Successfully" + i);
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e.getMessage());
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());

	}

}
