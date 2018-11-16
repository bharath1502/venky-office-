package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.RecurringPaymentInfo;

/**
 * DAO Repository class to process Activity Log
 *
 * @author Girmiti Software
 * @date 26-Feb-2014 12:33:27 pm
 * @version 1.0
 */
public interface RecurringPaymentInfoRepository extends
                                      JpaRepository<RecurringPaymentInfo, Long>,
                                      QuerydslPredicateExecutor<RecurringPaymentInfo> {

}
