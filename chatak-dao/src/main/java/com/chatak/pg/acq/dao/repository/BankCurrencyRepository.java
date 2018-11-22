/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGBankCurrencyMapping;

/**
 * @Author: Girmiti Software
 * @Date: 20-Dec-2016
 * @Time: 11:44:35 pm
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface BankCurrencyRepository extends JpaRepository<PGBankCurrencyMapping, Long>, QueryDslPredicateExecutor<PGBankCurrencyMapping> {

	public PGBankCurrencyMapping findByBankId(Long bankId);

	/**
	 * @param currencyId
	 * @return
	 */
	public PGBankCurrencyMapping findById(Long currencyId);
}
