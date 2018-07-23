/**
 * 
 */
package com.chatak.pg.acq.dao;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGMerchantUserAddress;
import com.chatak.pg.user.bean.UpdateMerchantRequest;
import com.chatak.pg.user.bean.UpdateMerchantResponse;

/**
 * @Author: Girmiti Software
 * @Date: Jun 24, 2015
 * @Time: 1:17:47 PM
 * @Version: 1.0
 * @Comments:
 */
public interface MerchantUserAddressDao {
  
  public PGMerchantUserAddress createOrUpdateMerchantUserAddress(PGMerchantUserAddress merchantUserAddress) throws DataAccessException;

  public PGMerchantUserAddress getMerchantUserAddressByUserId(String userId) throws DataAccessException;

  public UpdateMerchantResponse updateMerchantProfile(UpdateMerchantRequest updateMerchantRequest);
}
