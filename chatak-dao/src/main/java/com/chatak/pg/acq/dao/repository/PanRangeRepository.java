/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PanRanges;

/**
 * @Author: Girmiti Software
 * @Date: 21-Feb-2019
 * @Time: 3:33:55 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface PanRangeRepository extends JpaRepository<PanRanges, Long>, QueryDslPredicateExecutor<PanRanges> {
	
	public List<PanRanges> findByIsoId(Long isoId);
	
	public PanRanges findById(Long panId);

}
