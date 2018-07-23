/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.MerchantUserAddressDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantBank;
import com.chatak.pg.acq.dao.model.PGMerchantUserAddress;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.acq.dao.repository.MerchantUserAddressRepository;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.user.bean.UpdateMerchantRequest;
import com.chatak.pg.user.bean.UpdateMerchantResponse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;

/**
 * @Author: Girmiti Software
 * @Date: Jun 24, 2015
 * @Time: 1:27:48 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository
public class MerchantUserAddressDaoImpl implements MerchantUserAddressDao {

  private static Logger logger = Logger.getLogger(MerchantUpdateDaoImpl.class);

  @Autowired
  MerchantUserAddressRepository merchantUserAddressRepository;

  @Autowired
  private MerchantRepository merchantRepository;

  @Autowired
  private AccountRepository accountRepository;

  /**
   * @param merchantUserAddress
   * @return
   * @throws DataAccessException
   */
  @Override
  public PGMerchantUserAddress createOrUpdateMerchantUserAddress(
      PGMerchantUserAddress merchantUserAddress) throws DataAccessException {
    return merchantUserAddressRepository.save(merchantUserAddress);
  }

  /**
   * @param userId
   * @return
   * @throws DataAccessException
   */
  @Override
  public PGMerchantUserAddress getMerchantUserAddressByUserId(String userId)
      throws DataAccessException {
    List<PGMerchantUserAddress> list =
        merchantUserAddressRepository.findByMerchantUserId(Long.valueOf(userId));
    if (CommonUtil.isListNotNullAndEmpty(list)) {
      return list.get(0);
    } else {
      return null;
    }
  }

  /**
   * @param updateMerchantRequest
   * @return
   */
  @Override
  public UpdateMerchantResponse updateMerchantProfile(UpdateMerchantRequest updateMerchantRequest) {
    logger.info("Entering:: MerchantController:: updateMerchant method");
    UpdateMerchantResponse updateMerchantResponse = new UpdateMerchantResponse();
    PGMerchant merchantDb =
        merchantRepository.findByMerchantCode(updateMerchantRequest.getMerchantCode());
    PGAccount pgAccount = accountRepository.findByEntityIdAndCategory(merchantDb.getMerchantCode(),
        PGConstants.PRIMARY_ACCOUNT);
    if (null != pgAccount) {
      merchantDb.getPgMerchantUsers().get(0).setEmail(updateMerchantRequest.getEmailId());
      merchantDb.getPgMerchantUsers().get(0).setUserName(updateMerchantRequest.getUserName());

      merchantDb.setBusinessName(updateMerchantRequest.getBusinessName());
      merchantDb.setMerchantCode(updateMerchantRequest.getMerchantCode());
      merchantDb.setFirstName(updateMerchantRequest.getFirstName());
      merchantDb.setLastName(updateMerchantRequest.getLastName());
      merchantDb.setPhone(updateMerchantRequest.getPhone());
      merchantDb.setEmailId(updateMerchantRequest.getEmailId());
      merchantDb.setBusinessURL(updateMerchantRequest.getBusinessURL());

      merchantDb.setAddress1(updateMerchantRequest.getAddress1());
      merchantDb.setAddress2(updateMerchantRequest.getAddress2());
      merchantDb.setCity(updateMerchantRequest.getCity());
      merchantDb.setCountry(updateMerchantRequest.getCountry());
      merchantDb.setState(updateMerchantRequest.getState());
      merchantDb.setPin(updateMerchantRequest.getPin());

      merchantRepository.save(merchantDb);
      accountRepository.save(pgAccount);
      if (null != merchantDb.getStatus() && merchantDb.getStatus().equals(Constants.THREE)) {
        updateMerchantResponse.setDeclined(true);
      } else {
        updateMerchantResponse.setDeclined(false);
      }
      updateMerchantResponse.setUpdated(true);
      updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      updateMerchantResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
    } else {
      logger.error("Error:: MerchantController:: updateMerchant method");
      updateMerchantResponse.setUpdated(false);
      updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      updateMerchantResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }
    return updateMerchantResponse;
  }

}
