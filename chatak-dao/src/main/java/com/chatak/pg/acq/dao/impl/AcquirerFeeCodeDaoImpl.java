/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.AcquirerFeeCodeDao;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeCode;
import com.chatak.pg.acq.dao.repository.AcquirerFeeCodeRepository;
import com.chatak.pg.util.CommonUtil;

/**
 * @Author: Girmiti Software
 * @Date: Aug 10, 2015
 * @Time: 4:18:16 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository
public class AcquirerFeeCodeDaoImpl implements AcquirerFeeCodeDao {
  @Autowired
  AcquirerFeeCodeRepository acquirerFeeCodeRepository;

  /**
   * @return
   */
  @Override
  public List<PGAcquirerFeeCode> getAllFeeCodes() {
    return acquirerFeeCodeRepository.findAll();
  }

  /**
   * @param acquirerEntityId
   * @return
   */
  @Override
  public List<PGAcquirerFeeCode> getAcquirerFeeCodeByPartnerId(Long partnerId) {
return acquirerFeeCodeRepository.findByPartnerId(partnerId);
}

  /**
   * @param pgAcquirerFeeCode
   */
  @Override
  public void updateAcquirerFeecode(PGAcquirerFeeCode pgAcquirerFeeCode) {
    acquirerFeeCodeRepository.save(pgAcquirerFeeCode);
  }

  /**
   * @param feeCodeId
   * @return
   */
  @Override
  public PGAcquirerFeeCode getAcquirerFeeCodeByFeeCodeId(Long feeCodeId) {
    List<PGAcquirerFeeCode> list=acquirerFeeCodeRepository.findByAcquirerFeeCodeId(feeCodeId);
    if(CommonUtil.isListNotNullAndEmpty(list)){
      return list.get(0);
    }
    else{
      return null;
    }
  }

  /**
   * @param pgAcquirerFeeCode
   */
  @Override
  public void addAcquirerFeecode(PGAcquirerFeeCode pgAcquirerFeeCode) {
    acquirerFeeCodeRepository.save(pgAcquirerFeeCode);
  }

  /**
   * @param acquirerName
   * @param partnerId
   * @return
   */
  @Override
  public PGAcquirerFeeCode getAcquirerFeeCodeByAcquirerNameAndPartnerIdAndMerchantCode(String acquirerName, Long partnerId,String merchantCode) {
    return acquirerFeeCodeRepository.findByAcquirerNameAndPartnerIdAndMerchantCode(acquirerName, partnerId, merchantCode);
  }

  /**
   * @param partnerID
   * @param merchantCode
   * @return
   */
  @Override
  public List<PGAcquirerFeeCode> getFeeCodesByPartnerIdAndMerchantCode(Long partnerId, String merchantCode) {
    return acquirerFeeCodeRepository.findByPartnerIdAndMerchantCode(partnerId, merchantCode);
  }

/**
 * @param merchantCode
 * @return
 */
@Override
public List<PGAcquirerFeeCode> getAcquirerFeeCodesByMerchantCode(String merchantCode) {
	return acquirerFeeCodeRepository.findByMerchantCode(merchantCode);
}

}
