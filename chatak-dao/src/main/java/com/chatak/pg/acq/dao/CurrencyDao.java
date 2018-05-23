/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGCurrencyCode;
import com.chatak.pg.acq.dao.model.PGMerchantCurrencyMapping;
import com.chatak.pg.bean.CurrencyRequest;

/**
 * @Author: Girmiti Software
 * @Date: Jun 30, 2016
 * @Time: 5:27:50 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface CurrencyDao {
	
	public List<CurrencyRequest> findAllCurrencies() throws DataAccessException;

	public PGMerchantCurrencyMapping createOrUpdateCurrencyMap(PGMerchantCurrencyMapping pgMerchantCurrencyMapping)throws DataAccessException;

	public List<PGMerchantCurrencyMapping> findByMerchantCode(String merchantCode);
	
	public void deleteCurrencyMap(String merchantCode);
	
	public List<String> getMerchantCode(String merchantCode);

	public PGCurrencyCode findByCurrencyName(String currencyName);

	public List<PGCurrencyCode> findCurrencies();

}
