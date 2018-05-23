package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGPosDevice;

public interface AssetManagementRepository extends JpaRepository<PGPosDevice,Long>,QueryDslPredicateExecutor<PGPosDevice> {
	
	public List<PGPosDevice> findByDeviceSerialNoIgnoreCase(String deviceSerialNo);

	/**
	 * @param deviceSerialNo
	 * @return
	 */
	public PGPosDevice findById(Long Id);
    
	
}
