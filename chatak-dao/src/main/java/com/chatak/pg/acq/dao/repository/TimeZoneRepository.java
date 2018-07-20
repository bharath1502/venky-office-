package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.TimeZone;

public interface TimeZoneRepository extends JpaRepository<TimeZone, Long>,
QueryDslPredicateExecutor<TimeZone>{

	public List<TimeZone> findByCountryId(Long countryId);
	
	public List<TimeZone> findById(Long id);
}
