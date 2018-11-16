/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGMerchantBank;

/**
 * @Author: Girmiti Software
 * @Date: 05-May-2015
 * @Time: 7:55:33 PM
 * @Version: 1.0
 * @Comments:
 */
public interface MerchantBankRepository extends
                                       JpaRepository<PGMerchantBank, Long>,
                                       QuerydslPredicateExecutor<PGMerchantBank> {
  public List<PGMerchantBank> findByMerchantId(String merchantId);
  

}
