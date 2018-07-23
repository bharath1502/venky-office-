/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.IssSettlementDataDao;
import com.chatak.pg.acq.dao.model.PGIssSettlementData;
import com.chatak.pg.acq.dao.repository.IssSettlementDataRepository;
import com.chatak.pg.constants.PGConstants;

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
	
	@Override
	public List<PGIssSettlementData> findByProgramManagerId(Long programManagerId) {
		return issSettlementDataRepository.findByAcqPmId(programManagerId);
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
}
