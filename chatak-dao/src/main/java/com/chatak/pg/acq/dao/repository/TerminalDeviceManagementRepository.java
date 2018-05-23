package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGTerminalDeviceMangement;

public interface TerminalDeviceManagementRepository extends JpaRepository<PGTerminalDeviceMangement,Long>,QueryDslPredicateExecutor<PGTerminalDeviceMangement>
{
	public PGTerminalDeviceMangement findByDeviceManagementId(Long deviceManagementId);
	
	public PGTerminalDeviceMangement findByDeviceId(Long deviceId);
	
}
