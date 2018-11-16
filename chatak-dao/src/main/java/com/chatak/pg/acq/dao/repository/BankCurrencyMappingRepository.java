/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGBankCurrencyMapping;

/**
 * @Author: Girmiti Software
 * @Date: Jan 23, 2017
 * @Time: 7:53:11 PM
 * @Version: 1.0
 * @Comments: 
 *
 */

public interface BankCurrencyMappingRepository extends JpaRepository<PGBankCurrencyMapping, Long>,
QuerydslPredicateExecutor<PGBankCurrencyMapping> 
{
	
	public Optional<PGBankCurrencyMapping> findById(Long currencyId);


}
