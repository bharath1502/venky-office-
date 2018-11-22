package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGCountry;

public interface CountryRepository extends JpaRepository<PGCountry, Long>,
		QueryDslPredicateExecutor<PGCountry> {

	public List<PGCountry> findById(Long id);
	
	public PGCountry findByName(String name);
}
