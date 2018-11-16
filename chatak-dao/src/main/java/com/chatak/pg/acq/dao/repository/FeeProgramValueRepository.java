package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGFeeProgramValue;

public interface FeeProgramValueRepository extends JpaRepository<PGFeeProgramValue, Long>, QuerydslPredicateExecutor<PGFeeProgramValue>
{

	public List<PGFeeProgramValue> findByFeeProgramId(Long feeProgramId);
}
