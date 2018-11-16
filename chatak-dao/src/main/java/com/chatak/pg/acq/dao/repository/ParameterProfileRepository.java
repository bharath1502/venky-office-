package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGParameterProfile;

public interface ParameterProfileRepository extends JpaRepository<PGParameterProfile,Long>,QuerydslPredicateExecutor<PGParameterProfile> {
	
}
