package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGParameterMagstripe;

public interface ParameterMagstripeRepository extends JpaRepository<PGParameterMagstripe,Long>,QueryDslPredicateExecutor<PGParameterMagstripe> {
	
	public List<PGParameterMagstripe> findByMagstripeName(String magstripeName);

}
