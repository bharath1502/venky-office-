package com.chatak.pg.acq.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGCountry;

public interface CountryRepository extends JpaRepository<PGCountry, Long>,
		QuerydslPredicateExecutor<PGCountry> {

	public Optional<PGCountry> findById(Long id);
	
	public PGCountry findByName(String name);
}
