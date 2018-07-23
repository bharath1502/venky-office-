package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantTerminal;

public interface MerchantTerminalDao {

	public PGMerchantTerminal createOrUpdateMerchantTerminal(PGMerchantTerminal merchantTerminal)throws DataAccessException;
	
	public PGMerchantTerminal findByTerminalId(String terminalId)throws DataAccessException;
	
	public PGMerchantTerminal findById(Long id)throws DataAccessException;
	
	public List<PGMerchantTerminal> findByMerchantId(String merchantId)throws DataAccessException;
	
	public List<Long> getTerminalsByMerchantIdList(List<Long> merchantIdList)throws DataAccessException;
	
	public PGMerchant validateMerchantIdAndTerminalId(String merchantId, String terminalId)throws DataAccessException;

	/**
	 * @param merchantId
	 * @return
	 */
	public PGMerchant validateMerchantId(String merchantId);
	
}
