package com.chatak.pg.acq.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGActivityLog;

/**
 * DAO Repository class to process Activity Log
 *
 * @author Girmiti Software
 * @date 08-Dec-2014 12:33:27 pm
 * @version 1.0
 */
public interface ActivityLogRepository extends
                                      JpaRepository<PGActivityLog, Long>,
                                      QuerydslPredicateExecutor<PGActivityLog> {

  public Optional<PGActivityLog> findById(Long pGActivitLogId);

}
