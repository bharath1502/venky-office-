/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.Device;

/**
 * @Author: Girmiti Software
 * @Date: Aug 3, 2016
 * @Time: 5:16:37 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface DeviceRepository extends JpaRepository<Device, Long>,QueryDslPredicateExecutor<Device>{

	
}
