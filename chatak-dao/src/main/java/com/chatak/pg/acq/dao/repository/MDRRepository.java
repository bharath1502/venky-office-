/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGDynamicMDR;

/**
 * @Author: Girmiti Software
 * @Date: Aug 31, 2016
 * @Time: 7:56:03 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface MDRRepository extends JpaRepository<PGDynamicMDR, Long>, QueryDslPredicateExecutor<PGDynamicMDR>{

	/**
	 * @param bin
	 * @return
	 */
	public PGDynamicMDR findByBinNumber(Long bin);

	/**
	 * @param getMDRBinId
	 * @return
	 */
	public PGDynamicMDR findById(Long getMDRBinId);

}
