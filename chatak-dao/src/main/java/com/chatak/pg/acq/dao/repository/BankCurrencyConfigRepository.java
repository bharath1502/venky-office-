/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGBankCurrencyMapping;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;

/**
 * @Author: Girmiti Software
 * @Date: Jan 24, 2017
 * @Time: 5:09:42 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface BankCurrencyConfigRepository extends JpaRepository<PGCurrencyConfig, Long>, QuerydslPredicateExecutor<PGBankCurrencyMapping> 
{
	public Optional<PGCurrencyConfig> findById(Long currencyId);

}
