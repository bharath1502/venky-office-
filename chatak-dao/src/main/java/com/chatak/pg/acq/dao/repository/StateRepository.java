package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGState;

public interface StateRepository extends JpaRepository<PGState, Long>,
		QueryDslPredicateExecutor<PGState> {

	public List<PGState> findById(Long id);
	
	public List<PGState> findByStatus(String status);
	
	public List<PGState> findByCountryId(Long countryId);
	
}
