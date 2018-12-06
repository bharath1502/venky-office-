/**
 * 
 */
package com.chatak.pg.acq.dao;

import com.chatak.pg.acq.dao.model.PGBatch;

/**
 * @Author: Girmiti Software
 * @Date: May 28, 2018
 * @Time: 10:03:23 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface BatchDao {
		
	public PGBatch getBatchIdByProgramManagerId(Long programManagerId);
	
	public PGBatch save(PGBatch batch);
	
	public PGBatch findByProgramManagerIdAndStatus(Long programManagerId, String status);
	
}
