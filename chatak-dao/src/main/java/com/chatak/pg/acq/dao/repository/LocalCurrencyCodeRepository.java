package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGLocalCurrencyCode;

public interface LocalCurrencyCodeRepository extends JpaRepository<PGLocalCurrencyCode,Long>,QueryDslPredicateExecutor<PGLocalCurrencyCode>{

}
