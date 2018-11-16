/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.PGCurrencyConfig;

/**
 * @Author: Girmiti Software
 * @Date: Nov 24, 2016
 * @Time: 4:55:48 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public interface CurrencyConfigRepository
		extends JpaRepository<PGCurrencyConfig, Long>, QuerydslPredicateExecutor<PGCurrencyConfig> {

	/**
	 * @param currencyName
	 * @param currencyCodeNumeric
	 * @return
	 */
	@Query("select t from PGCurrencyConfig t where t.currencyName=:currencyName and t.status <> 3")
	public PGCurrencyConfig findByCurrencyName(@Param("currencyName") String currencyConfigCurrencyName);

	/**
	 * @param currencyConfigId
	 * @return
	 */
	public Optional<PGCurrencyConfig> findById(Long currencyConfigId);

	/**
	 * @param currencyCodeNumeric
	 * @return
	 */
	@Query("select t from PGCurrencyConfig t where t.currencyCodeNumeric=:currencyCodeNumeric and t.status <> 3 ")
	public PGCurrencyConfig findByCurrencyCodeNumeric(@Param("currencyCodeNumeric") String currencyConfigCurrencyCodeNumeric);

	public List<PGCurrencyConfig> findByStatus(Integer status);

	@Query("select t from PGCurrencyConfig t where t.currencyCodeAlpha=:currencyCodeAlpha and t.status <> 3 ")
	public PGCurrencyConfig findByCurrencyCodeAlpha(@Param("currencyCodeAlpha") String currencyCodeAlpha);

}
