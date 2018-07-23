/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.CurrencyDao;
import com.chatak.pg.acq.dao.model.PGCurrencyCode;
import com.chatak.pg.acq.dao.model.PGMerchantCurrencyMapping;
import com.chatak.pg.acq.dao.repository.CurrencyCodeMappingRepository;
import com.chatak.pg.acq.dao.repository.CurrencyCodeRepository;
import com.chatak.pg.bean.CurrencyRequest;
import com.chatak.pg.util.CommonUtil;

/**
 * @Author: Girmiti Software
 * @Date: Jun 30, 2016
 * @Time: 5:28:50 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository("currencyDao")
public class CurrencyDaoImpl implements CurrencyDao {
  
    private static Logger logger = Logger.getLogger(CurrencyDaoImpl.class);
	
	@Autowired
	private CurrencyCodeRepository currencyCodeRepository;
	
	@Autowired
	private CurrencyCodeMappingRepository currencyCodeMappingRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<CurrencyRequest> findAllCurrencies() throws DataAccessException {
		List<CurrencyRequest> list = new ArrayList<CurrencyRequest>();
		try {
			List<PGCurrencyCode> currencyList = currencyCodeRepository.findAll();
			list = CommonUtil.copyListBeanProperty(currencyList, CurrencyRequest.class);
		} catch (Exception e) {
		  logger.error("Error in retrieving the list of countries", e);
		}

		return list;
	}
	
	@Override
	public PGMerchantCurrencyMapping createOrUpdateCurrencyMap(PGMerchantCurrencyMapping pgMerchantCurrencyMapping)throws DataAccessException {
		return currencyCodeMappingRepository.save(pgMerchantCurrencyMapping);
	}
	
	@Override
	public void deleteCurrencyMap(String merchantCode) {
		List<PGMerchantCurrencyMapping> pgMerchantCurrencyMapping = currencyCodeMappingRepository.findByMerchantCode(merchantCode);
	    currencyCodeMappingRepository.delete(pgMerchantCurrencyMapping);
	}

	/**
	 * @param merchantId
	 * @return
	 */
	@Override
	public List<PGMerchantCurrencyMapping> findByMerchantCode(String merchantCode) {
		return currencyCodeMappingRepository.findByMerchantCode(merchantCode);
	}
	
	@Override
	public List<String> getMerchantCode(String merchantCode) {
		List<String> merchantCodeList = currencyCodeMappingRepository.getMerchantCurrencyCode(merchantCode);
		return merchantCodeList;
	}

	/**
	 * @param currencyId
	 * @return
	 */
	@Override
	public PGCurrencyCode findByCurrencyName(String currencyName) {
		PGCurrencyCode pgCurrencyCode = currencyCodeRepository.findByCurrencyName(currencyName);
		return pgCurrencyCode;
	}

	/**
	 * @return
	 */
	@Override
	public List<PGCurrencyCode> findCurrencies() {
		List<PGCurrencyCode> pgCurrencyCode = currencyCodeRepository.findAll();
		return pgCurrencyCode;
	}
}
