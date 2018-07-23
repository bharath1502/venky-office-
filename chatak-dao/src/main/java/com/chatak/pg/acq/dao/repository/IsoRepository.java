/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.Iso;

/**
 * @Author: Girmiti Software
 * @Date: May 8, 2018
 * @Time: 5:09:34 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface IsoRepository extends JpaRepository<Iso, Long>, QueryDslPredicateExecutor<Iso>{

	public List<Iso> findByIsoName(String isoName);
	public List<Iso> findById(Long isoId);
}
