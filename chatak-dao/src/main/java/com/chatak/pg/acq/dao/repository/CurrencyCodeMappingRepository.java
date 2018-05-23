/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.PGMerchantCurrencyMapping;

/**
 * @Author: Girmiti Software
 * @Date: Jun 30, 2016
 * @Time: 10:08:54 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface CurrencyCodeMappingRepository extends JpaRepository<PGMerchantCurrencyMapping,Long>,QueryDslPredicateExecutor<PGMerchantCurrencyMapping> {

	
	public List<PGMerchantCurrencyMapping> findByMerchantCode(String merchantCode);

	/**
	 * @param merchantCode
	 */
	
	@Query("select currencyCode from PGMerchantCurrencyMapping where merchantCode = :merchantCode")
	public List<String> getMerchantCurrencyCode(@Param("merchantCode") String merchantCode);
}
