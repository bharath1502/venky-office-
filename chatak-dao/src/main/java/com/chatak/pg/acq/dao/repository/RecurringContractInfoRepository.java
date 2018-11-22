package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.RecurringContractInfo;

/**
 * DAO Repository class to process Activity Log
 *
 * @author Girmiti Software
 * @date 26-Feb-2014 12:33:27 pm
 * @version 1.0
 */
public interface RecurringContractInfoRepository extends
                                      JpaRepository<RecurringContractInfo, Long>,
                                      QueryDslPredicateExecutor<RecurringContractInfo> {
	
	public List<RecurringContractInfo> findByRecurringPaymentInfoId(Long paymentInfoId);
	
	
	public RecurringContractInfo findByRecurringContractInfoId(Long contractInfoId, String status);
	

}
