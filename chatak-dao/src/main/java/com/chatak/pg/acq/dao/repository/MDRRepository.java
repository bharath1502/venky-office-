/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGDynamicMDR;

/**
 * @Author: Girmiti Software
 * @Date: Aug 31, 2016
 * @Time: 7:56:03 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface MDRRepository extends JpaRepository<PGDynamicMDR, Long>, QuerydslPredicateExecutor<PGDynamicMDR>{

	/**
	 * @param bin
	 * @return
	 */
	public PGDynamicMDR findByBinNumber(Long bin);

	/**
	 * @param getMDRBinId
	 * @return
	 */
	public Optional<PGDynamicMDR> findById(Long getMDRBinId);

}
