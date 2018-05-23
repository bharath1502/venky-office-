package com.chatak.acquirer.admin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.impl.CurrencyServiceImpl;
import com.chatak.pg.acq.dao.CurrencyDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.model.PGMerchant;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceImplTest {
	@InjectMocks
	CurrencyServiceImpl currencyServiceImpl = new CurrencyServiceImpl();

	@Mock
	CurrencyDao currencyDao;

	@Mock
	MerchantDao merchantDao;

	@Mock
	MerchantUpdateDao merchantUpdateDao;

	@Test
	public void testGetCurrencyCode() throws ChatakAdminException {
		PGMerchant pgMerchant = new PGMerchant();
		pgMerchant.setLocalCurrency("r");
		Mockito.when(merchantUpdateDao.getMerchant(Matchers.anyString())).thenReturn(pgMerchant);
		currencyServiceImpl.getCurrencyCode("abcde");

	}

}
