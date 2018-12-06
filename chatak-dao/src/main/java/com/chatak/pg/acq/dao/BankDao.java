/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.BankProgramManagerMap;
import com.chatak.pg.acq.dao.model.PGBank;
import com.chatak.pg.acq.dao.model.PGBankCurrencyMapping;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.user.bean.BankRequest;
import com.chatak.pg.user.bean.BankResponse;
import com.chatak.pg.user.bean.GetBankListResopnse;

/**
 * @Author: Girmiti Software
 * @Date: Aug 2, 2016
 * @Time: 3:25:28 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface BankDao {
	
	public BankResponse createBank(BankRequest bankRequest);
	
	public GetBankListResopnse getBanklist(BankRequest bankSerachRequest);
	
	public BankResponse deleteBank(String bankName);
	
	public BankResponse updateBank(BankRequest bankRequest);
	
	public PGBank getBankByName(String bankName);

	public List<PGBank> getBankData();
	
	public List<PGBankCurrencyMapping> getCurrencyByBankId(Long bankId);
	

	/**
	 * @param currencyId
	 * @return
	 */
	public PGCurrencyConfig getCurrencyAlpha(Long currencyId);
	
	public List<PGBank> getBankName(Long currencyId);
	
	public PGBank createOrUpdateBank(PGBank pgBank) throws DataAccessException;
	
	public BankProgramManagerMap createOrUpdateBankProgramManagerMapping(BankProgramManagerMap bankProgramManagerMap) throws DataAccessException;
	
	public List<PGBank> findByIssuanceBankId(Long issuanceBankId);
	
}
