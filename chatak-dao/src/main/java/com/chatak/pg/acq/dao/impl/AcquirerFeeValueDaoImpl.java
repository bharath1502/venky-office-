/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.AcquirerFeeValueDao;
import com.chatak.pg.acq.dao.FeeProgramDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;
import com.chatak.pg.acq.dao.repository.AcquirerFeeValueRepository;

/**
 * @Author: Girmiti Software
 * @Date: Feb 6, 2016
 * @Time: 1:37:10 PM
 * @Version: 1.0
 * @Comments:
 */
@Repository
public class AcquirerFeeValueDaoImpl implements AcquirerFeeValueDao {

  @Autowired
  AcquirerFeeValueRepository acquirerFeeValueRepository;
  
  @Autowired
  MerchantDao merchantDao;
  
  @Autowired
  
  FeeProgramDao feeProgramDao;

  @Override
  public List<PGAcquirerFeeValue> getAcquirerFeeValuesByFeeProgramId(Long feeProgramId) {
    return acquirerFeeValueRepository.findByFeeProgramId(feeProgramId);
  }

  @Override
  public void removeAcquirerFeeValues(List<PGAcquirerFeeValue> acquirerFeeValuesDaoDetails) {
    acquirerFeeValueRepository.deleteAll(acquirerFeeValuesDaoDetails);
    
  }

}
