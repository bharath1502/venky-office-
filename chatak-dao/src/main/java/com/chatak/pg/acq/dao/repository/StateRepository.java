package com.chatak.pg.acq.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGState;

public interface StateRepository extends JpaRepository<PGState, Long>,
		QuerydslPredicateExecutor<PGState> {

	public Optional<PGState> findById(Long id);
	
	public List<PGState> findByStatus(String status);
	
	public List<PGState> findByCountryId(Long countryId);
	
}
