package com.chatak.acquirer.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.CurrencyService;
import com.chatak.pg.acq.dao.CurrencyDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.bean.DCCCurrency;
import com.chatak.pg.constants.PGConstants;

@Service
public class CurrencyServiceImpl implements CurrencyService, PGConstants {

  @Autowired
  CurrencyDao currencyDao;

  @Autowired
  MerchantDao merchantDao;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;

  @Override
  public DCCCurrency getCurrencyCode(String merchantCode) throws ChatakAdminException {
    DCCCurrency dccCurrency = new DCCCurrency();
    List<String> merchantCodeList = currencyDao.getMerchantCode(merchantCode);
    PGMerchant pgMerchant = merchantUpdateDao.getMerchant(merchantCode);
    dccCurrency.setBaseCurrency(pgMerchant.getLocalCurrency());
    dccCurrency.setCurrencyCodes(merchantCodeList);
    return dccCurrency;

  }

}
