/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.pg.acq.dao.BatchDao;
import com.chatak.pg.acq.dao.model.PGBatch;
import com.chatak.pg.acq.dao.repository.BatchRepository;
import com.chatak.pg.dao.util.StringUtil;

/**
 * @Author: Girmiti Software
 * @Date: May 28, 2018
 * @Time: 10:04:47 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository
public class BatchDaoImpl implements BatchDao{

	private static Logger logger = Logger.getLogger(BatchDaoImpl.class);

	@PersistenceContext
	 private EntityManager entityManager;
	
	@Autowired
	private BatchRepository batchRepository;

	@Override
	public PGBatch findByProgramManagerIdAndStatus(Long programManagerId, String status) {
		return batchRepository.findByProgramManagerIdAndStatus(programManagerId, status);
	}

	@Override
	public PGBatch getBatchIdByProgramManagerId(Long programManagerId) {
	  logger.info("Entering :: BatchDaoImpl :: getBatchIdByProgramManagerId :: Program Manger Id ::" + programManagerId);
		PGBatch batch = new PGBatch();
		Query qry = entityManager.createNativeQuery(
				"SELECT DISTINCT a.PM_ID,a.BATCH_ID, pb.STATUS FROM (SELECT PM_ID, MAX(BATCH_ID) AS BATCH_ID,max(CREATED_DATE) as CREATED_DATE  FROM PG_BATCH  WHERE PM_ID=:programManagerId GROUP BY PM_ID)a JOIN PG_BATCH pb ON pb.BATCH_ID=a.BATCH_ID and a.CREATED_DATE=pb.CREATED_DATE");
		qry.setParameter("programManagerId", programManagerId);
		List<Object> list = qry.getResultList();
		if (StringUtil.isListNotNullNEmpty(list)) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Object[] objs = (Object[]) it.next();
				batch.setProgramManagerId(StringUtil.isNull(objs[0]) ? null : ((BigInteger) objs[0]).longValue());
				batch.setBatchId(StringUtil.isNull(objs[1]) ? null : ((String) objs[1]));
				batch.setStatus(StringUtil.isNull(objs[2]) ? null : ((String) objs[2]));
			}
		}
    logger.info("Entering :: BatchDaoImpl :: getBatchIdByProgramManagerId :: Batch Id : "
        + batch.getBatchId() + " Batch Status : " + batch.getStatus());
		return batch;
	}

	@Override
	public PGBatch save(PGBatch batch) {
		return batchRepository.save(batch);
	}

}
