/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGAccountHistory;

/**
 * @Author: Girmiti Software
 * @Date: Jun 16, 2015
 * @Time: 10:54:31 AM
 * @Version: 1.0
 * @Comments:
 */
public interface AccountHistoryRepository extends
                                         JpaRepository<PGAccountHistory, Long>,
                                         QueryDslPredicateExecutor<PGAccountHistory> {

  public List<PGAccountHistory> findByAccountNum(Long accountNum);
  
  public List<PGAccountHistory> findByAccountNumOrderByUpdatedDateDesc(Long accountNum);

  public List<PGAccountHistory> findByEntityId(String entityId);
}
