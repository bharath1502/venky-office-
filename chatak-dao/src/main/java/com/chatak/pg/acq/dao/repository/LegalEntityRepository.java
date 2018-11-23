/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGLegalEntity;

/**
 * @Author: Girmiti Software
 * @Date: 05-May-2015
 * @Time: 7:55:33 PM
 * @Version: 1.0
 * @Comments:
 */
public interface LegalEntityRepository extends
                                       JpaRepository<PGLegalEntity, Long>,
                                       QueryDslPredicateExecutor<PGLegalEntity> {
  public List<PGLegalEntity> findByMerchantId(Long merchantId);
  

}
