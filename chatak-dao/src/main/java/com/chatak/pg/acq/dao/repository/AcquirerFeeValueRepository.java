/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;

/**
 * @Author: Girmiti Software
 * @Date: Feb 6, 2016
 * @Time: 1:38:45 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface AcquirerFeeValueRepository extends JpaRepository<PGAcquirerFeeValue,Long>,QueryDslPredicateExecutor<PGAcquirerFeeValue>{
  public List<PGAcquirerFeeValue> findByFeeProgramId(Long feeProgramId);
}
