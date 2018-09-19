/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.IssSettlementDataDao;
import com.chatak.pg.acq.dao.model.PGIssSettlementData;
import com.chatak.pg.acq.dao.model.QPGIssSettlementData;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGTransaction;
import com.chatak.pg.acq.dao.repository.IssSettlementDataRepository;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.DateUtil;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * @Author: Girmiti Software
 * @Date: Jun 15, 2018
 * @Time: 4:13:16 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository
public class IssSettlementDataDaoImpl implements IssSettlementDataDao {
    
	@Autowired
	private IssSettlementDataRepository issSettlementDataRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<PGIssSettlementData> findByProgramManagerId(Long programManagerId) {
		return issSettlementDataRepository.findByAcqPmId(programManagerId);
	}
	
	@Override
	public List<PGIssSettlementData> findByProgramManagerIdByStatus(Long programManagerId, String status) {
		return issSettlementDataRepository.findByAcqPmIdAndStatus(programManagerId, status);
	}

	/**
	 * @return
	 */
	@Override
	public List<PGIssSettlementData> getAllPendingPM() {
		return issSettlementDataRepository.findByStatus(PGConstants.S_STATUS_PENDING);
	}

	/**
	 * @param issSettlementData
	 * @return
	 */
	@Override
	public PGIssSettlementData saveIssSettlementData(PGIssSettlementData issSettlementData) {
		return issSettlementDataRepository.save(issSettlementData);
	}
	
	@Override
	public List<PGIssSettlementData> findByAcqPmIdAndBatchDate(Long programManagerId, Timestamp batchDate) {
		return issSettlementDataRepository.findByAcqPmIdAndBatchDate(programManagerId, batchDate);
	}
	
	@Override
	public List<PGIssSettlementData> getIssSettlementData(Long programManagerId, Timestamp batchDate) {
		List<PGIssSettlementData> listOfIssSettlementData = new ArrayList<>();
		PGIssSettlementData issSettlementData = null;
		Timestamp startDate = null;
		Timestamp endDate = null;
		startDate = DateUtil.getStartDayTimestamp(DateUtil.toDateStringFormat(batchDate, PGConstants.DATE_FORMAT),
				PGConstants.DATE_FORMAT);
		endDate = DateUtil.getEndDayTimestamp(DateUtil.toDateStringFormat(batchDate, PGConstants.DATE_FORMAT),
				PGConstants.DATE_FORMAT);
		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> tupleList = query.distinct().from(QPGIssSettlementData.pGIssSettlementData)
				.where(isPmIdEq(programManagerId).and(isValidDate(startDate, endDate)))
				.list(QPGIssSettlementData.pGIssSettlementData.acqPmId,
						QPGIssSettlementData.pGIssSettlementData.batchDate,
						QPGIssSettlementData.pGIssSettlementData.programManagerId,
						QPGIssSettlementData.pGIssSettlementData.programManagerName,
						QPGIssSettlementData.pGIssSettlementData.status,
						QPGIssSettlementData.pGIssSettlementData.totalAmount,
						QPGIssSettlementData.pGIssSettlementData.totalTxnCount,
						QPGIssSettlementData.pGIssSettlementData.id);
		if (StringUtil.isListNotNullNEmpty(tupleList)) {
			for (Tuple tuple : tupleList) {
				issSettlementData = new PGIssSettlementData();
				issSettlementData.setAcqPmId(tuple.get(QPGIssSettlementData.pGIssSettlementData.acqPmId));
				issSettlementData.setBatchDate(tuple.get(QPGIssSettlementData.pGIssSettlementData.batchDate));
				issSettlementData
						.setProgramManagerId(tuple.get(QPGIssSettlementData.pGIssSettlementData.programManagerId));
				issSettlementData
						.setProgramManagerName(tuple.get(QPGIssSettlementData.pGIssSettlementData.programManagerName));
				issSettlementData.setStatus(tuple.get(QPGIssSettlementData.pGIssSettlementData.status));
				issSettlementData.setTotalAmount(tuple.get(QPGIssSettlementData.pGIssSettlementData.totalAmount));
				issSettlementData.setTotalTxnCount(tuple.get(QPGIssSettlementData.pGIssSettlementData.totalTxnCount));
				issSettlementData.setId(tuple.get(QPGIssSettlementData.pGIssSettlementData.id));
				listOfIssSettlementData.add(issSettlementData);
			}
		}

		return listOfIssSettlementData;
	}

	private BooleanExpression isPmIdEq(Long programManagerId) {
		return (programManagerId != 0l) ? QPGIssSettlementData.pGIssSettlementData.acqPmId.eq(programManagerId) : null;
	}

	private BooleanExpression isValidDate(Timestamp fromDate, Timestamp toDate) {
		if ((fromDate != null && toDate == null)) {
			return QPGIssSettlementData.pGIssSettlementData.batchDate.gt(fromDate);
		} else if ((fromDate == null && toDate != null)) {
			return QPGIssSettlementData.pGIssSettlementData.batchDate.lt(toDate);
		} else if ((fromDate == null))
			return null;
		return QPGIssSettlementData.pGIssSettlementData.batchDate.between(fromDate, toDate);
	}
}
