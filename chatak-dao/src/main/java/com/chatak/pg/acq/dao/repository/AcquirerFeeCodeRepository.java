/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGAcquirerFeeCode;

/**
 * @Author: Girmiti Software
 * @Date: Aug 8, 2015
 * @Time: 3:27:37 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface AcquirerFeeCodeRepository extends JpaRepository<PGAcquirerFeeCode,Long>,QuerydslPredicateExecutor<PGAcquirerFeeCode>{
  public List<PGAcquirerFeeCode> findByPartnerId(Long partnerId);
  public List<PGAcquirerFeeCode> findByAcquirerFeeCodeId(Long acquirerFeeCodeId);
  public PGAcquirerFeeCode findByAcquirerNameAndPartnerIdAndMerchantCode(String acquirerName,Long partnerId,String merchantCode);
  public List<PGAcquirerFeeCode> findByPartnerIdAndMerchantCode(Long partnerId,String merchantCode);
  public List<PGAcquirerFeeCode> findByMerchantCode(String merchantCode);
 
}
