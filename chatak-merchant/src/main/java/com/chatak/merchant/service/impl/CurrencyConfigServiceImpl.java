package com.chatak.merchant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.merchant.service.CurrencyConfigService;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.PGConstants;

@Service("currencyConfigService")
public class CurrencyConfigServiceImpl implements CurrencyConfigService, PGConstants {


  @Autowired
  CurrencyConfigDao currencyConfigDao;

  @Override
  public Response getCurrencyCodeNumeric(String currencyCodeAlpha) {
    PGCurrencyConfig pgCurrencyConfig = currencyConfigDao.getCurrencyCodeNumeric(currencyCodeAlpha);
    Response response = new Response();

    if (pgCurrencyConfig != null) {
      response.setCurrencyCodeNumeric(pgCurrencyConfig.getCurrencyCodeNumeric());
      response.setCurrencyId(pgCurrencyConfig.getId());
    }

    return response;
  }

  @Override
  public Response getcurrencyCodeAlpha(String bankCurrencyCode) {

    PGCurrencyConfig pgCurrencyConfig = currencyConfigDao.getcurrencyCodeAlpha(bankCurrencyCode);
    Response response = new Response();
    if (pgCurrencyConfig != null) {
      response.setCurrencyCodeAlpha(pgCurrencyConfig.getCurrencyCodeAlpha());
    }
    return response;
  }

}
