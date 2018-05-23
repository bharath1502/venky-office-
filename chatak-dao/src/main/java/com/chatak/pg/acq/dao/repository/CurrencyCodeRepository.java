package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGCurrencyCode;

public interface CurrencyCodeRepository extends JpaRepository<PGCurrencyCode,Long>,QueryDslPredicateExecutor<PGCurrencyCode>{

	/**
	 * @param currencyCodeNumeric
	 * @return
	 */
	public PGCurrencyCode findByCurrencyName(String currencyName);
	
	public PGCurrencyCode findByCurrencyCodeNumeric(String currencyCodeNumeric);

}
