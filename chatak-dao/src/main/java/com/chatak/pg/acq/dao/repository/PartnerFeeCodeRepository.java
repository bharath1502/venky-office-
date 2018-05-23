/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGPartnerFeeCode;

/**
 * @Author: Girmiti Software
 * @Date: Aug 8, 2015
 * @Time: 3:27:37 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface PartnerFeeCodeRepository extends JpaRepository<PGPartnerFeeCode,Long>,QueryDslPredicateExecutor<PGPartnerFeeCode>{
  public List<PGPartnerFeeCode> findAll();
  public List<PGPartnerFeeCode> findByPartnerEntityId(String partnerEntityId);
  public List<PGPartnerFeeCode> findByAccountNumber(Long accountNumber);
}
