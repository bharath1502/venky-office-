/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.acq.dao.model.PGMerchantCurrencyMapping;
import com.chatak.pg.acq.dao.repository.CurrencyCodeMappingRepository;
import com.chatak.pg.acq.dao.repository.CurrencyCodeRepository;

/**
 * @Author: Girmiti Software
 * @Date: 16-Feb-2018
 * @Time: 7:08:22 pm
 * @Version: 1.0
 * @Comments:
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CurrencyDaoImplTest {

	@InjectMocks
	CurrencyDaoImpl currencyDaoImpl;

	@Mock
	private EntityManager entityManager;

	@Mock
	Query query;

	@Mock
	private EntityManagerFactory emf;

	@Mock
	private CurrencyCodeRepository currencyCodeRepository;

	@Mock
	private CurrencyCodeMappingRepository currencyCodeMappingRepository;

	@Test
	public void testFindAllCurrencies() {
		currencyDaoImpl.findAllCurrencies();
	}

	@Test
	public void testCreateOrUpdateCurrencyMap() {
		PGMerchantCurrencyMapping pgMerchantCurrencyMapping = new PGMerchantCurrencyMapping();
		currencyDaoImpl.createOrUpdateCurrencyMap(pgMerchantCurrencyMapping);
	}

	@Test
	public void testDeleteCurrencyMap() {
		currencyDaoImpl.deleteCurrencyMap("12243");
	}

	@Test
	public void testFindByMerchantCode() {
		currencyDaoImpl.findByMerchantCode("12243");
	}

	@Test
	public void testGetMerchantCode() {
		currencyDaoImpl.getMerchantCode("12243");
	}

	@Test
	public void testFindByCurrencyName() {
		currencyDaoImpl.findByCurrencyName("12243");
	}

	@Test
	public void testFindCurrencies() {
		currencyDaoImpl.findCurrencies();
	}

}
