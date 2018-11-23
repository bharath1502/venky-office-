/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.IssuanceSettlementTransactionHistoryDao;
import com.chatak.pg.acq.dao.model.settlement.PGSettlementEntityHistory;
import com.chatak.pg.acq.dao.repository.IssuanceSettlementHistoryRepository;

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

	
	@Autowired
	private IssuanceSettlementHistoryRepository issuanceSettlementHistoryRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public void insertDataFromIssuanceSettlementTransaction() {
		logger.info("Entering :: IssuanceSettlementTransactionHistoryDaoImpl :: insertDataFromIssuanceSettlementTransaction");
		try {
			StringBuilder sb = new StringBuilder("call `sp_settlement_txn_history`()");
			Query qry = entityManager.createNativeQuery(sb.toString());
			qry.getResultList();
		} catch (Exception e) {
		  logger.error("Error :: IssuanceSettlementTransactionHistoryDaoImpl :: insertDataFromIssuanceSettlementTransaction :: " + e.getMessage(), e);
		}
		logger.info("Exiting :: IssuanceSettlementTransactionHistoryDaoImpl :: insertDataFromIssuanceSettlementTransaction");

	}
	
	public PGSettlementEntityHistory findByBatchFileDateandAcqpmid(Long pmId, Timestamp batchDate) {
		return issuanceSettlementHistoryRepository.findByAcqPmIdAndBatchFileDate(pmId, batchDate);
	}

}
