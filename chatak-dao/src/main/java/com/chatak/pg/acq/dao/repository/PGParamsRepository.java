package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGParams;

/**
 * DAO Repository class to create merchant account
 * @Author: Girmiti Software
 * @Date: Apr 28, 2015
 * @Time: 9:00:14 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface PGParamsRepository extends
                                      JpaRepository<PGParams, Long>,
                                      QuerydslPredicateExecutor<PGParams> {
  
  
  
  public List<PGParams> findByStatus(Integer status);

}
