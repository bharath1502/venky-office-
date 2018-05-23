package com.chatak.merchant.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.PayPageConfig;
import com.chatak.pg.acq.dao.PayPageConfigServiceDao;
import com.chatak.pg.acq.dao.model.PGPayPageConfig;
import com.chatak.pg.model.Response;

@RunWith(MockitoJUnitRunner.class)
public class PayPageConfigServiceImplTest {

	@InjectMocks
	private PayPageConfigServiceImpl payPageConfigServiceImpl = new PayPageConfigServiceImpl();

	@Mock
	PayPageConfigServiceDao payPageConfigServiceDao;

	@Mock
	Response response;

	@Mock
	DataAccessException dataAccessException;

	@Test
	public void testSaveOrUpdatePayPageConfig() throws ChatakMerchantException {
		PGPayPageConfig pgPayPageConfig = new PGPayPageConfig();
		PayPageConfig payPageConfig = new PayPageConfig();
		payPageConfig.setMerchantId(Long.parseLong("123"));
		Mockito.when(payPageConfigServiceDao.findByPayPageConfigMerchantId(null)).thenReturn(pgPayPageConfig);
		Assert.assertNotNull(payPageConfigServiceImpl.saveOrUpdatePayPageConfig(payPageConfig));
	}

	@Test
	public void testSaveOrUpdatePayPageConfigNotNull() throws ChatakMerchantException {
		PGPayPageConfig pgPayPageConfig = new PGPayPageConfig();
		PayPageConfig payPageConfig = new PayPageConfig();
		payPageConfig.setMerchantId(Long.parseLong("123"));
		Mockito.when(payPageConfigServiceDao.findByPayPageConfigMerchantId(Matchers.anyLong()))
				.thenReturn(pgPayPageConfig);
		Assert.assertNotNull(payPageConfigServiceImpl.saveOrUpdatePayPageConfig(payPageConfig));
	}

	@Test
	public void testSaveOrUpdatePayPageConfigNotNullException() throws ChatakMerchantException {
		PayPageConfig payPageConfig = new PayPageConfig();
		payPageConfig.setMerchantId(Long.parseLong("123"));
		Mockito.when(payPageConfigServiceDao.findByPayPageConfigMerchantId(Matchers.anyLong()))
				.thenThrow(dataAccessException);
		Assert.assertNotNull(payPageConfigServiceImpl.saveOrUpdatePayPageConfig(payPageConfig));
	}

	@Test
	public void testGetPayPageConfigDetails() throws ChatakMerchantException {
		PGPayPageConfig pgPayPageConfig = new PGPayPageConfig();
		Mockito.when(payPageConfigServiceDao.findByPayPageConfigMerchantId(Long.parseLong("2"))).thenReturn(pgPayPageConfig);
		Assert.assertNotNull(payPageConfigServiceImpl.getPayPageConfigDetails(Long.parseLong("22")));

	}

	@Test
	public void testGetPayPageConfigDetailsNotNull() throws ChatakMerchantException {
		PGPayPageConfig pgPayPageConfig = new PGPayPageConfig();
		Mockito.when(payPageConfigServiceDao.findByPayPageConfigMerchantId(null)).thenReturn(pgPayPageConfig);
		Assert.assertNotNull(payPageConfigServiceImpl.getPayPageConfigDetails(null));

	}

	@Test
	public void testGetPayPageConfigDetailsNotNullException() throws ChatakMerchantException {
		PayPageConfig payPageConfig = new PayPageConfig();
		payPageConfig.setMerchantId(Long.parseLong("123"));
		Mockito.when(payPageConfigServiceDao.findByPayPageConfigMerchantId(Matchers.anyLong()))
				.thenThrow(dataAccessException);
		Assert.assertNotNull(payPageConfigServiceImpl.getPayPageConfigDetails(Long.parseLong("123")));
	}
}
