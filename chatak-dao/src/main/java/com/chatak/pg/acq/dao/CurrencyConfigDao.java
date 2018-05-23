/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.model.CurrencyDTO;
import com.chatak.pg.user.bean.CurrencyDTOList;

/**
 * @Author: Girmiti Software
 * @Date: Nov 24, 2016
 * @Time: 4:16:43 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface CurrencyConfigDao {

	/**
	 * @param pgCurrencyConfig
	 * @return
	 */
	PGCurrencyConfig saveCurrencyConfig(PGCurrencyConfig pgCurrencyConfig);
	
	public List<PGCurrencyConfig> searchCurrencyConfig(CurrencyDTO currencyDTO);

	/**
	 * @param currencyConfigId
	 * @return
	 */
	 public PGCurrencyConfig findByCurrencyConfigId(Long currencyConfigId);

	/**
	 * @param pgCurrencyConfig
	 * @return
	 */
	 public  PGCurrencyConfig updateCurrencyConfig(PGCurrencyConfig pgCurrencyConfig);

	/**
	 * @return
	 */
	 public CurrencyDTOList getActiveCurrencyConfigCode();
	 
	 public PGCurrencyConfig getCurrencyCodeNumeric(String currencyCodeAlpha);
	 
	 public PGCurrencyConfig getcurrencyCodeAlpha(String bankCurrencyCode);
	 
	 public PGCurrencyConfig createOrUpdateCurrencyConfig(PGCurrencyConfig pgCurrencyConfig);
	

}
