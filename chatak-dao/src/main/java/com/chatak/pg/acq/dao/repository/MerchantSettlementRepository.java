package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGMerchantSettlement;

/**
 * DAO Repository class to provides merchant settlement methods
 * @Author: Girmiti Software
 * @Date: Apr 28, 2015
 * @Time: 9:00:14 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface MerchantSettlementRepository extends
                                      JpaRepository<PGMerchantSettlement, Long>,
                                      QueryDslPredicateExecutor<PGMerchantSettlement> {

}
