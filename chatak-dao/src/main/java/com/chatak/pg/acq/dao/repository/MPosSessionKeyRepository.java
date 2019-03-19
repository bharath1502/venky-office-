package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.pg.acq.dao.model.MPosSessionKey;

public interface MPosSessionKeyRepository
		extends JpaRepository<MPosSessionKey, Long>, QueryDslPredicateExecutor<MPosSessionKey> {

	@Modifying
	@Transactional
	@Query("update MPosSessionKey sk set sk.deviceSk= :deviceSk where sk.deviceSerial = :deviceSerial")
    public int updateMPosSessionKeyDeviceSkByDeviceSerail(@Param("deviceSk")String deviceSk,@Param("deviceSerial")String deviceSerail);

	public MPosSessionKey findByDeviceSerial(String deviceSerail);

}
