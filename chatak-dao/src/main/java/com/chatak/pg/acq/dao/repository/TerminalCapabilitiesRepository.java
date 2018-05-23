package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGTerminalCapabilities;

public interface TerminalCapabilitiesRepository extends JpaRepository<PGTerminalCapabilities,Long>,QueryDslPredicateExecutor<PGTerminalCapabilities> {
	
	public PGTerminalCapabilities findByTerminalCapabilitiesId(Long terminalCapablitiesId);	
	

}
