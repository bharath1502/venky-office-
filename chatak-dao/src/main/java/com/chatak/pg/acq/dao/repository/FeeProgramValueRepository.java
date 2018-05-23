package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGFeeProgramValue;

public interface FeeProgramValueRepository extends JpaRepository<PGFeeProgramValue, Long>, QueryDslPredicateExecutor<PGFeeProgramValue>
{

	public List<PGFeeProgramValue> findByFeeProgramId(Long feeProgramId);
}
