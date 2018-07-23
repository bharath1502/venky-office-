/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.LegalEntityDao;
import com.chatak.pg.acq.dao.model.PGLegalEntity;
import com.chatak.pg.acq.dao.repository.LegalEntityRepository;
import com.chatak.pg.util.DateUtil;

/**
 * @Author: Girmiti Software
 * @Date: 17-Aug-2015
 * @Time: 11:57:26 PM
 * @Version: 1.0
 * @Comments:
 */
@Repository
public class LegalEntityDaoImpl implements LegalEntityDao {
  @Autowired
  LegalEntityRepository legalEntityRepository;

  /**
   * @param merchantId
   * @return
   */
  @Override
  public PGLegalEntity getLegalEntityByMerchantId(String merchantId) {
    List<PGLegalEntity> list = legalEntityRepository.findByMerchantId(Long.valueOf(merchantId));
    if(null != list && !list.isEmpty()) {

      return list.get(0);
    }
    return null;
  }

  /**
   * @param pgLegalEntity
   */
  @Override
  public void addLegalEntity(PGLegalEntity pgLegalEntity) {
    legalEntityRepository.save(pgLegalEntity);
  }

  /**
   * @param pgLegalEntity
   */
  @Override
  public void updateLegalEntity(PGLegalEntity pgLegalEntity) {
    PGLegalEntity pgLegalEntityOld = null;
    List<PGLegalEntity> pgOldLegalEntities = legalEntityRepository.findByMerchantId(pgLegalEntity.getMerchantId());
    if(null != pgOldLegalEntities && !pgOldLegalEntities.isEmpty()) {
      pgLegalEntityOld = pgOldLegalEntities.get(0);
    }else{
    	pgLegalEntityOld=new PGLegalEntity();
    	pgLegalEntityOld.setMerchantId(pgLegalEntity.getMerchantId());
    	pgLegalEntityOld.setCreatedDate(new Timestamp(System.currentTimeMillis()));
    }
      pgLegalEntityOld.setAddress1(pgLegalEntity.getAddress1());
      pgLegalEntityOld.setAddress2(pgLegalEntity.getAddress2());
      pgLegalEntityOld.setCity(pgLegalEntity.getCity());
      pgLegalEntityOld.setCountry(pgLegalEntity.getCountry());
      pgLegalEntityOld.setCountryOfCitizenship(pgLegalEntity.getCountryOfCitizenship());
      pgLegalEntityOld.setDateOfBirth(pgLegalEntity.getDateOfBirth());
      pgLegalEntityOld.setFirstName(pgLegalEntity.getFirstName());
      pgLegalEntityOld.setHomePhone(pgLegalEntity.getHomePhone());
      pgLegalEntityOld.setLastName(pgLegalEntity.getLastName());
      pgLegalEntityOld.setLegalEntityName(pgLegalEntity.getLegalEntityName());
      pgLegalEntityOld.setAnnualCardSale(pgLegalEntity.getAnnualCardSale());
      pgLegalEntityOld.setLegalEntityType(pgLegalEntity.getLegalEntityType());
      pgLegalEntityOld.setMobilePhone(pgLegalEntity.getMobilePhone());
      pgLegalEntityOld.setPassportNumber(pgLegalEntity.getPassportNumber());
      pgLegalEntityOld.setCountryOfResidence(pgLegalEntity.getCountryOfResidence());
      pgLegalEntityOld.setPin(pgLegalEntity.getPin());
      pgLegalEntityOld.setSsn(pgLegalEntity.getSsn());
      pgLegalEntityOld.setState(pgLegalEntity.getState());
      pgLegalEntityOld.setTaxId(pgLegalEntity.getTaxId());
      pgLegalEntityOld.setUpdatedDate(DateUtil.getCurrentTimestamp());
      legalEntityRepository.save(pgLegalEntityOld);
  }

}
