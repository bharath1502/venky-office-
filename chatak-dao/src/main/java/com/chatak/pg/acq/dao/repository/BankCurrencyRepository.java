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
 * @Date: 20-Dec-2016
 * @Time: 11:44:35 pm
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface BankCurrencyRepository extends JpaRepository<PGBankCurrencyMapping, Long>, QuerydslPredicateExecutor<PGBankCurrencyMapping> {

	public PGBankCurrencyMapping findByBankId(Long bankId);

	/**
	 * @param currencyId
	 * @return
	 */
	public Optional<PGBankCurrencyMapping> findById(Long currencyId);
}
