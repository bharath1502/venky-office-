package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGFeeCode;

public interface FeeCodeRepository  extends JpaRepository<PGFeeCode, Long>, QueryDslPredicateExecutor<PGFeeCode>
{
	public List<PGFeeCode> findByFeeCode(Long feeCode);
}
