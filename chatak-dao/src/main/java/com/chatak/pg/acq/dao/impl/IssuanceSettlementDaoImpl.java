/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.pg.acq.dao.IssuanceSettlementDao;
import com.chatak.pg.acq.dao.model.settlement.PGSettlementEntity;
import com.chatak.pg.acq.dao.model.settlement.PGSettlementTransaction;
import com.chatak.pg.acq.dao.model.settlement.QPGSettlementEntity;
import com.chatak.pg.acq.dao.repository.IssuanceSettlementEntityRepository;
import com.chatak.pg.acq.dao.repository.IssuanceSettlementRepository;
import com.chatak.pg.bean.settlement.IssuanceSettlementTransactionEntity;
import com.chatak.pg.bean.settlement.IssuanceSettlementTransactions;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.exception.PrepaidAdminException;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * @Author: Girmiti Software
 * @Date: Jun 19, 2018
 * @Time: 9:08:40 PM
 * @Version: 1.0
 * @Comments:
 *
 */
@Repository
public class IssuanceSettlementDaoImpl implements IssuanceSettlementDao {
	
	 private static Logger logger = Logger.getLogger(IssuanceSettlementDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	IssuanceSettlementEntityRepository issuanceSettlementEntityRepository;
	
	@Autowired
	IssuanceSettlementRepository issuanceSettlementRepository;

	public void addIssuanceSettlementTransaction(PGSettlementEntity entity, Integer batchCount, Integer batchSize) {
		// First save into IssuanceSettlementTransactionEntity
		// Save all individual transactions into IssuanceSettlementTransactions
		
		//Commented for Batch Inserts
		/*issuanceSettlementEntityRepository.save(entity);*/
		
		//JPA batch processing start
		entityManager.setFlushMode(FlushModeType.COMMIT);
		entityManager.persist(entity);
		
		//Check batch size if equals batch count then flush and clear entity manager
		if(batchCount % batchSize == 0 && batchCount >0) {
			entityManager.flush();
			entityManager.clear();
		}
		//JPA batch processing end
	}
	
    @Transactional(noRollbackFor=Exception.class)
	public void deleteAllIssuanceSettlementData(String programManagerId) throws PrepaidAdminException {
		 logger.info("Entering :: IssuanceSettlementDaoImpl :: deleteAllIssuanceSettlementData");
		try {
			StringBuilder sb = new StringBuilder(" DELETE FROM PG_ISS_SETTLEMENT_ENTITY  where ISS_PM_ID = :programMangerId ");
			Query qry = entityManager.createNativeQuery(sb.toString());
			qry.setParameter("programMangerId", Long.parseLong(programManagerId));
			int i = qry.executeUpdate();
			logger.info("deleted the data from PG_ISS_SETTLEMENT_ENTITY table sucessfully " + i);
		} catch (Exception e) {
			
			logger.error("ERROR :: IssuanceSettlementDaoImpl :: deleteAllIssuanceSettlementData method", e);
		}
		logger.info("Exiting :: IssuanceSettlementDaoImpl :: deleteAllIssuanceSettlementData");
	}

	@Override
	public List<IssuanceSettlementTransactionEntity> getAllMatchedTransactions(Long pmId) {
		logger.info("Entering :: IssuanceSettlementDaoImpl :: getAllMatchedTransactions");
		List<IssuanceSettlementTransactionEntity> settlementEntityList = new ArrayList<>();
        
		JPAQuery query = new JPAQuery(entityManager);
        List<Tuple> tupleList = query
                .from(QPGSettlementEntity.pGSettlementEntity)
				.where(isProgramManagerIdEq(pmId), QPGSettlementEntity.pGSettlementEntity.batchid.isNotNull())
                .list(QPGSettlementEntity.pGSettlementEntity.id,QPGSettlementEntity.pGSettlementEntity.merchantId,
                        QPGSettlementEntity.pGSettlementEntity.acqSaleAmount,
                        QPGSettlementEntity.pGSettlementEntity.issSaleAmount,
                        QPGSettlementEntity.pGSettlementEntity.acqPmId, QPGSettlementEntity.pGSettlementEntity.issPmId,
                        QPGSettlementEntity.pGSettlementEntity.batchid,
                        QPGSettlementEntity.pGSettlementEntity.batchFileDate,
                        QPGSettlementEntity.pGSettlementEntity.batchFileProcessedDate,
                        QPGSettlementEntity.pGSettlementEntity.status, QPGSettlementEntity.pGSettlementEntity.pmAmount,
                        QPGSettlementEntity.pGSettlementEntity.merchantAmount);
		
		
        logger.info("PGSettlementEntity size : " + tupleList.size());
		
		if(StringUtil.isListNotNullNEmpty(tupleList)) {
		  
		  for(Tuple tuple : tupleList) {
		    
		    logger.info("Creating IssuanceSettlementTransactionEntity for MID : " + tuple.get(QPGSettlementEntity.pGSettlementEntity.merchantId)
		        + ", with total merchant amount: " + tuple.get(QPGSettlementEntity.pGSettlementEntity.acqSaleAmount));
		    
		      IssuanceSettlementTransactionEntity entity = new IssuanceSettlementTransactionEntity();
	          
		      entity.setMerchantId(tuple.get(QPGSettlementEntity.pGSettlementEntity.merchantId));
              entity.setAcqSaleAmount(new BigDecimal(tuple.get(QPGSettlementEntity.pGSettlementEntity.acqSaleAmount)));
              entity.setIssSaleAmount(new BigDecimal(tuple.get(QPGSettlementEntity.pGSettlementEntity.issSaleAmount)));
              entity.setAcqPmId(tuple.get(QPGSettlementEntity.pGSettlementEntity.acqPmId));
              entity.setIssPmId(tuple.get(QPGSettlementEntity.pGSettlementEntity.issPmId));
              entity.setBatchid(tuple.get(QPGSettlementEntity.pGSettlementEntity.batchid));
              entity.setBatchFileDate(tuple.get(QPGSettlementEntity.pGSettlementEntity.batchFileDate));
              entity.setBatchFileProcessedDate(tuple.get(QPGSettlementEntity.pGSettlementEntity.batchFileProcessedDate));
              entity.setStatus(tuple.get(QPGSettlementEntity.pGSettlementEntity.status));
              entity.setPmAmount(tuple.get(QPGSettlementEntity.pGSettlementEntity.pmAmount));
              entity.setMerchantAmount(tuple.get(QPGSettlementEntity.pGSettlementEntity.merchantAmount));
	          
	          List<PGSettlementTransaction>  pGSettlementTransaction = issuanceSettlementRepository.findByIssuanceSettlementEntityId(tuple.get(QPGSettlementEntity.pGSettlementEntity.id));
	          logger.info("Found pGSettlementTransactions size : " + pGSettlementTransaction.size());

	          List<IssuanceSettlementTransactions> issuanceSettlementTransactions = new ArrayList<>();
	          
	          for(PGSettlementTransaction settlementTransaction : pGSettlementTransaction) {
	            logger.info("PGSettlementTransaction for MID : " + tuple.get(QPGSettlementEntity.pGSettlementEntity.merchantId)
	                + ", PG TXN ID: " + settlementTransaction.getPgTransactionId() + ", ISS Txn Id: " + settlementTransaction.getIssuerTxnID()
	                + ", ISO amount: " + settlementTransaction.getIsoAmount());
	            
	              IssuanceSettlementTransactions issuanceSettlementTxns = new IssuanceSettlementTransactions();
	            
	              issuanceSettlementTxns.setIsoAmount(settlementTransaction.getIsoAmount());
	              issuanceSettlementTxns.setIsoId(settlementTransaction.getIsoId());
	              issuanceSettlementTxns.setIssuerTxnID(settlementTransaction.getIssuerTxnID());
	              issuanceSettlementTxns.setPgTransactionId(settlementTransaction.getPgTransactionId());
	              issuanceSettlementTxns.setTerminalId(settlementTransaction.getTerminalId());
	              issuanceSettlementTxns.setTxnDate(settlementTransaction.getTxnDate());
	              
	              issuanceSettlementTransactions.add(issuanceSettlementTxns);           
	          }
	          entity.setSettlementTransactionsList(issuanceSettlementTransactions);
	          
	          settlementEntityList.add(entity);
	        }		  
		}
		logger.info("getAllMatchedTransactions, final list size : " + settlementEntityList.size());
		
		logger.info("Exiting :: IssuanceSettlementDaoImpl :: getAllMatchedTransactions");
		return settlementEntityList;
	}
	
	private BooleanExpression isProgramManagerIdEq(Long pmId) {

		return (pmId != null) ? QPGSettlementEntity.pGSettlementEntity.acqPmId  .eq(pmId)
				: null;
	}

}
