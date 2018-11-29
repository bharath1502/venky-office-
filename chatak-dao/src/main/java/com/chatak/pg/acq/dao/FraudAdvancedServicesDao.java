package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantAdvancedFraud;
import com.chatak.pg.model.AdvancedFraudDTO;

/**
 * @Author: Girmiti Software
 * @Date: Aug 17, 2016
 * @Time: 6:11:22 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface FraudAdvancedServicesDao {
	
	public PGMerchant findById(Long id) throws DataAccessException;
	
	public PGMerchantAdvancedFraud saveOrUpdatePGMerchantAdvancedFraud(PGMerchantAdvancedFraud pGMerchantAdvancedFraud) throws DataAccessException;
	
	public List<AdvancedFraudDTO> searchAdvancedFraud(AdvancedFraudDTO advancedFraudDTO);
	
	public PGMerchantAdvancedFraud findByIdAndMerchantCode(Long id,String merchantCode) throws DataAccessException;

	public void deleteAdvancedFraud(Long id) throws DataAccessException;

}