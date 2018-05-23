package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGActionCodeParameters;

public interface ActionCodeParametersRepository  extends JpaRepository<PGActionCodeParameters,Long>,QueryDslPredicateExecutor<PGActionCodeParameters>
{

	public List<PGActionCodeParameters> findByActioncodeId(Long actioncodeId);
}
