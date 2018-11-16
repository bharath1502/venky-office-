package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGTerminalCapabilities;

public interface TerminalCapabilitiesRepository extends JpaRepository<PGTerminalCapabilities,Long>,QuerydslPredicateExecutor<PGTerminalCapabilities> {
	
	public PGTerminalCapabilities findByTerminalCapabilitiesId(Long terminalCapablitiesId);	
	

}
