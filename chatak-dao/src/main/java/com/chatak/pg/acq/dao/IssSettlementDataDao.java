/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.sql.Timestamp;
import java.util.List;

import com.chatak.pg.acq.dao.model.PGIssSettlementData;

/**
 * @Author: Girmiti Software
 * @Date: Jun 15, 2018
 * @Time: 4:12:37 PM
 * @Version: 1.0
 */
public interface IssSettlementDataDao {
	
	public List<PGIssSettlementData> findByProgramManagerId(Long programManagerId);
	
	public List<PGIssSettlementData> getAllPendingPM();
	
	public PGIssSettlementData saveIssSettlementData(PGIssSettlementData issSettlementData);
	
	public List<PGIssSettlementData> findByAcqPmIdAndBatchDate(Long programManagerId,  Timestamp batchDate);


}
