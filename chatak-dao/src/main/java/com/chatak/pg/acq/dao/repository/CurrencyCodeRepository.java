package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGCurrencyCode;

public interface CurrencyCodeRepository extends JpaRepository<PGCurrencyCode,Long>,QuerydslPredicateExecutor<PGCurrencyCode>{

	/**
	 * @param currencyCodeNumeric
	 * @return
	 */
	public PGCurrencyCode findByCurrencyName(String currencyName);
	
	public PGCurrencyCode findByCurrencyCodeNumeric(String currencyCodeNumeric);

}
