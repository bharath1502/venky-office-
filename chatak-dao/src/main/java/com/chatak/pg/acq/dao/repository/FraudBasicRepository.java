package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGFraudBasic;

public interface FraudBasicRepository extends JpaRepository<PGFraudBasic,Long>,QueryDslPredicateExecutor<PGFraudBasic>{

	public PGFraudBasic findByMerchantId(Long merchantId);
	
}
