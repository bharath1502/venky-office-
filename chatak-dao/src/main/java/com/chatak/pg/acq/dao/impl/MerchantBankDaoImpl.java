/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.MerchantBankDao;
import com.chatak.pg.acq.dao.model.PGMerchantBank;
import com.chatak.pg.acq.dao.repository.MerchantBankRepository;
import com.chatak.pg.util.DateUtil;

/**
 * @Author: Girmiti Software
 * @Date: Aug 16, 2015
 * @Time: 5:11:17 PM
 * @Version: 1.0
 * @Comments:
 */
@Repository
public class MerchantBankDaoImpl implements MerchantBankDao {
  @Autowired
  MerchantBankRepository merchantBankRepository;

  /**
   * @param merchantId
   * @return
   */
  @Override
  public PGMerchantBank getMerchantBankByMerchantId(String merchantId) {
    List<PGMerchantBank> list = merchantBankRepository.findByMerchantId(merchantId);
    if(null != list && !list.isEmpty()) {

      return list.get(0);
    }
    return null;
  }

  /**
   * @param pgMerchantBank
   */
  @Override
  public PGMerchantBank addMerchantBank(PGMerchantBank pgMerchantBank) {
    return merchantBankRepository.save(pgMerchantBank);
  }

  /**
   * @param pgMerchantBank
   */
  @Override
  public void updateMerchantBank(PGMerchantBank pgNewMerchantBank) {

    PGMerchantBank pgMerchantBank = null;
    List<PGMerchantBank> list = merchantBankRepository.findByMerchantId(pgNewMerchantBank.getMerchantId());
    if(null != list && !list.isEmpty()) {
      pgMerchantBank = list.get(0);
    }
    if(null == pgMerchantBank) {
    	pgMerchantBank=new PGMerchantBank();
    	pgMerchantBank.setMerchantId(pgNewMerchantBank.getMerchantId());
    	pgMerchantBank.setCreatedDate(new Timestamp(System.currentTimeMillis()));
    }
      pgMerchantBank.setAccountType(pgNewMerchantBank.getAccountType());
      pgMerchantBank.setBankAccNum(pgNewMerchantBank.getBankAccNum());
      pgMerchantBank.setRoutingNumber(pgNewMerchantBank.getRoutingNumber());
      pgMerchantBank.setBankCode(null);
      pgMerchantBank.setBankName(pgNewMerchantBank.getBankName());
      pgMerchantBank.setCurrencyCode(pgNewMerchantBank.getCurrencyCode()); // Need to change later
      pgMerchantBank.setMerchantId(pgNewMerchantBank.getMerchantId());
      pgMerchantBank.setUpdatedDate(DateUtil.getCurrentTimestamp());
      pgMerchantBank.setAddress1(pgNewMerchantBank.getAddress1());
      pgMerchantBank.setAddress2(pgNewMerchantBank.getAddress2());
      pgMerchantBank.setCity(pgNewMerchantBank.getCity());
      pgMerchantBank.setCountry(pgNewMerchantBank.getCountry());
      pgMerchantBank.setPin(pgNewMerchantBank.getPin());
      pgMerchantBank.setState(pgNewMerchantBank.getState());
      pgMerchantBank.setNameOnAccount(pgNewMerchantBank.getNameOnAccount());
      merchantBankRepository.save(pgMerchantBank);
  }

}
