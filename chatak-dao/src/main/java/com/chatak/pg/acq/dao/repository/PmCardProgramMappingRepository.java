/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.pg.acq.dao.model.PmCardProgamMapping;

/**
 * @Author: Girmiti Software
 * @Date: May 7, 2018
 * @Time: 7:59:44 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface PmCardProgramMappingRepository extends JpaRepository<PmCardProgamMapping, Long>,QueryDslPredicateExecutor<PmCardProgamMapping> {

	public PmCardProgamMapping findByCardProgramId(Long cardProgramId);
	
	@Modifying
	@Transactional
	@Query("delete from PmCardProgamMapping cpPmMap where cpPmMap.programManagerId = :pmId")
	public void deleteByPmId(@Param("pmId")Long pmId);
}
