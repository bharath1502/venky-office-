/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGDynamicMDR;
import com.chatak.pg.model.DynamicMDRDTO;

/**
 * @Author: Girmiti Software
 * @Date: Aug 31, 2016
 * @Time: 7:52:43 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface MDRDao {
	
	public PGDynamicMDR saveDynamicMDR(PGDynamicMDR pgDynamicMDR);

	public DynamicMDRDTO findById(Long getMDRId);
	
	public List<PGDynamicMDR> searchDynamicMDR(DynamicMDRDTO dynamicMDRDTO);

}
