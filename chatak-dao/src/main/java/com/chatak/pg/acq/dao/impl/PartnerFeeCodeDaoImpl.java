/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.PartnerFeeCodeDao;
import com.chatak.pg.acq.dao.model.PGPartnerFeeCode;
import com.chatak.pg.acq.dao.repository.PartnerFeeCodeRepository;
import com.chatak.pg.util.CommonUtil;

/**
 * @Author: Girmiti Software
 * @Date: Aug 8, 2015
 * @Time: 3:26:51 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository
public class PartnerFeeCodeDaoImpl implements PartnerFeeCodeDao {
  @Autowired
  private PartnerFeeCodeRepository partnerFeeCodeRepository;

  /**
   * @return
   */
  @Override
  public List<PGPartnerFeeCode> getAllFeeCodes() {
    return partnerFeeCodeRepository.findAll();
  }

  /**
   * @param entityId
   * @return
   */
  @Override
  public PGPartnerFeeCode getPartnerFeeCodeByEntityId(String partnerEntityId) {
    List<PGPartnerFeeCode> list=partnerFeeCodeRepository.findByPartnerEntityId(partnerEntityId);
    if(CommonUtil.isListNotNullAndEmpty(list)){
      return list.get(0);
    }
    else{
      return null;
    }
  }

  /**
   * @param pgPartnerFeeCode
   */
  @Override
  public void createPartnerFeecode(PGPartnerFeeCode pgPartnerFeeCode) {
    partnerFeeCodeRepository.save(pgPartnerFeeCode);
  }

  /**
   * @param pgPartnerFeeCode
   */
  @Override
  public void updatePartnerFeeCode(PGPartnerFeeCode pgPartnerFeeCode) {
    partnerFeeCodeRepository.save(pgPartnerFeeCode);
  }

  /**
   * @param accountNumber
   * @return
   */
  @Override
  public PGPartnerFeeCode getPartnerFeeCodeByAccountNumber(Long accountNumber) {
    List<PGPartnerFeeCode> list=partnerFeeCodeRepository.findByAccountNumber(accountNumber);
    if(CommonUtil.isListNotNullAndEmpty(list)){
      return list.get(0);
    }
    else{
      return null;
    }
  }
  
  

}
