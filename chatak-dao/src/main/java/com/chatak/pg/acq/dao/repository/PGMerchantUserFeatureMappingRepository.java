/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGMerchantUserFeatureMapping;

/**
 * @Author: Girmiti Software
 * @Date: Feb 13, 2019
 * @Time: 12:14:07 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface PGMerchantUserFeatureMappingRepository extends JpaRepository<PGMerchantUserFeatureMapping, Long>, QueryDslPredicateExecutor<PGMerchantUserFeatureMapping> {
 /*public PGMerchantUserFeatureMapping findByRoleId(Long roleId);*/
}
