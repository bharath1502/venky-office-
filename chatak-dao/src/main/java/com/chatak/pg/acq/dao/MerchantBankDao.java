/**
 * 
 */
package com.chatak.pg.acq.dao;

import com.chatak.pg.acq.dao.model.PGMerchantBank;

/**
 * @Author: Girmiti Software
 * @Date: Aug 16, 2015
 * @Time: 5:10:05 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface MerchantBankDao {
	
	public PGMerchantBank getMerchantBankByMerchantId(String merchantId);
	
	public PGMerchantBank addMerchantBank(PGMerchantBank pgMerchantBank);
	
	public void updateMerchantBank(PGMerchantBank pgMerchantBank);
	
}