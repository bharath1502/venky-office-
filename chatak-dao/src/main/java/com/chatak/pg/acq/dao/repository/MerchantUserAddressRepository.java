/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGMerchantUserAddress;

/**
 * @Author: Girmiti Software
 * @Date: Jun 24, 2015
 * @Time: 1:28:32 PM
 * @Version: 1.0
 * @Comments:
 */
public interface MerchantUserAddressRepository extends
                                              JpaRepository<PGMerchantUserAddress, Long>,
                                              QuerydslPredicateExecutor<PGMerchantUserAddress> {
  public List<PGMerchantUserAddress> findByMerchantUserId(Long merchantUserId);
  

}
