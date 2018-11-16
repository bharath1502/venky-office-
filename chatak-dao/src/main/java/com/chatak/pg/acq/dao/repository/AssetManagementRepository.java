package com.chatak.pg.acq.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGPosDevice;

public interface AssetManagementRepository extends JpaRepository<PGPosDevice,Long>,QuerydslPredicateExecutor<PGPosDevice> {
	
	public List<PGPosDevice> findByDeviceSerialNoIgnoreCase(String deviceSerialNo);

	/**
	 * @param deviceSerialNo
	 * @return
	 */
	public Optional<PGPosDevice> findById(Long Id);
    
	
}
