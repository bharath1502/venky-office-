package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.RecurringCustomerInfo;

/**
 * DAO Repository class to process Activity Log
 *
 * @author Girmiti Software
 * @date 26-Feb-2014 12:33:27 pm
 * @version 1.0
 */
public interface RecurringCustomerInfoRepository extends
                                      JpaRepository<RecurringCustomerInfo, Long>,
                                      QuerydslPredicateExecutor<RecurringCustomerInfo> {
  
  public RecurringCustomerInfo findByCustomerId(String customerId);
  
  public  List<RecurringCustomerInfo> findByEmailId(String emailId);
  
}
