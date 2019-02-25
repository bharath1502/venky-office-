/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PgMposFeatures;

/**
 * @Author: Girmiti Software
 * @Date: 11-Feb-2019
 * @Time: 3:59:09 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface PgMposFeaturesRepository extends JpaRepository<PgMposFeatures, Long>,QueryDslPredicateExecutor<PgMposFeatures>{
	
	public List<PgMposFeatures> findAll();

}