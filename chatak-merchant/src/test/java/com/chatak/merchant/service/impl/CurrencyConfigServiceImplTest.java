package com.chatak.merchant.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.bean.Response;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConfigServiceImplTest {

	@InjectMocks
	private CurrencyConfigServiceImpl currencyConfigServiceImpl = new CurrencyConfigServiceImpl();

	@Mock
	CurrencyConfigDao currencyConfigDao;

	@Mock
	Response response;

	@Mock
	PGCurrencyConfig pgCurrencyConfig;

	@Test
	public void testGetCurrencyCodeNumeric() throws ChatakMerchantException {
		pgCurrencyConfig = new PGCurrencyConfig();
		pgCurrencyConfig.setCurrencyCodeNumeric("840");
		pgCurrencyConfig.setId(Long.parseLong("12"));
		Mockito.when(currencyConfigDao.getCurrencyCodeNumeric(Matchers.anyString())).thenReturn(pgCurrencyConfig);
		response = currencyConfigServiceImpl.getCurrencyCodeNumeric("840");
		Assert.assertNotNull(response);
	}
	
	@Test
	public void testGetcurrencyCodeAlpha() throws ChatakMerchantException {
		pgCurrencyConfig = new PGCurrencyConfig();
		pgCurrencyConfig.setCurrencyCodeAlpha("USD");
		Mockito.when(currencyConfigDao.getcurrencyCodeAlpha(Matchers.anyString())).thenReturn(pgCurrencyConfig);
		response = currencyConfigServiceImpl.getcurrencyCodeAlpha("USD");
		Assert.assertNotNull(response);
	}

}
