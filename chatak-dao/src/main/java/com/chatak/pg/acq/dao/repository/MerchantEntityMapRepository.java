/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.pg.acq.dao.model.PGMerchantEntityMap;

/**
 * @Author: Girmiti Software
 * @Date: May 10, 2018
 * @Time: 4:38:15 PM
 * @Version: 1.0
 * @Comments: 
 *
 */

public interface MerchantEntityMapRepository extends JpaRepository<PGMerchantEntityMap,Long>, QueryDslPredicateExecutor<PGMerchantEntityMap> {
	
	public List<PGMerchantEntityMap> findByMerchantId(Long merchantId);
	
	@Modifying
	@Transactional
	@Query("delete from PGMerchantEntityMap merchantEntMap where merchantEntMap.merchantId = :merchantId")
	public void deleteMerchantEntityMapByMerchantId(@Param("merchantId")Long merchantId);
	
	public List<PGMerchantEntityMap> findByEntityIdAndEntitytype(Long entityId, String entityType);
	
}
