/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.MerchantEntityMapDao;
import com.chatak.pg.acq.dao.model.PGMerchantEntityMap;
import com.chatak.pg.acq.dao.repository.MerchantEntityMapRepository;

/**
 * @Author: Girmiti Software
 * @Date: May 10, 2018
 * @Time: 3:58:02 PM
 * @Version: 1.0
 * @Comments: 
 *
 */

@Repository("merchantEntityMapDao")
public class MerchantEntityMapDaoImpl implements MerchantEntityMapDao {

	@Autowired
	private MerchantEntityMapRepository merchantEntityMapRepository;
	
	@Override
	public List<PGMerchantEntityMap> findByMerchantId(Long merchantId) {
		return merchantEntityMapRepository.findByMerchantId(merchantId);
	}
}
