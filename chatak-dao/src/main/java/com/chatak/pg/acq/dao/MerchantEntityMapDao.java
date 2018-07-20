/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGMerchantEntityMap;

/**
 * @Author: Girmiti Software
 * @Date: May 10, 2018
 * @Time: 3:55:45 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface MerchantEntityMapDao {
	
	public List<PGMerchantEntityMap> findByMerchantId(Long merchantId);

}
