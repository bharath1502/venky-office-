/**
 * 
 */
package com.chatak.merchant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.service.AccountService;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.model.CurrencyDTO;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date May 15, 2015 1:02:00 PM
 * @version 1.0
 */
@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  AccountDao accountDao;

  @Autowired
  AccountRepository accountRepository;
  
  @Autowired
  CurrencyConfigDao currencyConfigDao;

  /**
   * @param entityId
   * @return
   */
  @Override
  public PGAccount getAccountDetailsByEntityId(String entityId) throws ChatakMerchantException {
    return accountDao.getPgAccount(entityId);
  }
  
  @Override
  public CurrencyDTO getCurrencyConfigOnCurrencyCodeAlpha(String currencyCodeAlpha) {
	  PGCurrencyConfig pgCurrencyConfig = currencyConfigDao.getCurrencyCodeNumeric(currencyCodeAlpha);
	  CurrencyDTO currencyDTO = null;
	  if (pgCurrencyConfig != null) {
		  currencyDTO = new CurrencyDTO();
		  currencyDTO.setCurrencyName(pgCurrencyConfig.getCurrencyName());
		  currencyDTO.setCurrencyExponent(pgCurrencyConfig.getCurrencyExponent());
		  currencyDTO.setCurrencySeparatorPosition(pgCurrencyConfig.getCurrencySeparatorPosition());
		  currencyDTO.setCurrencyMinorUnit(pgCurrencyConfig.getCurrencyMinorUnit());
		  currencyDTO.setCurrencyThousandsUnit(pgCurrencyConfig.getCurrencyThousandsUnit());
	  }
	  return currencyDTO;
  }

}
