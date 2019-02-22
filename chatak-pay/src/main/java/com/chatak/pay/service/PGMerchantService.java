package com.chatak.pay.service;

import com.chatak.pay.controller.model.LoginRequest;
import com.chatak.pay.controller.model.LoginResponse;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.model.MerchantListResponse;
import com.chatak.pg.acq.dao.model.PGApplicationClient;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.model.ApplicationClientDTO;
import com.chatak.pg.user.bean.AddMerchantBankRequest;
import com.chatak.pg.user.bean.AddMerchantBankResponse;
import com.chatak.pg.user.bean.AddMerchantRequest;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.DeleteMerchantRequest;
import com.chatak.pg.user.bean.DeleteMerchantResponse;
import com.chatak.pg.user.bean.GetMerchantBankDetailsRequest;
import com.chatak.pg.user.bean.GetMerchantBankDetailsResponse;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.GetMerchantListResponse;
import com.chatak.pg.user.bean.UpdateMerchantRequest;
import com.chatak.pg.user.bean.UpdateMerchantResponse;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 05-May-2015 3:14:41 PM
 * @version 1.0
 */
public interface PGMerchantService {

  /**
   * @param addMerchantRequest
   * @return
   */
  public AddMerchantResponse addMerchant(AddMerchantRequest addMerchantRequest);

  /**
   * @param updateMerchantRequest
   * @return
   */
  public UpdateMerchantResponse updateMerchant(UpdateMerchantRequest updateMerchantRequest);

  /**
   * @param deleteMerchantRequest
   * @return
   */
  public DeleteMerchantResponse deleteMerchant(DeleteMerchantRequest deleteMerchantRequest);

  /**
   * @param getMerchantListRequest
   * @return
   */
  public GetMerchantListResponse getMerchantList(GetMerchantListRequest getMerchantListRequest);

  /**
   * @param addMerchantBankRequest
   * @return
   */
  public AddMerchantBankResponse addMerchantBank(AddMerchantBankRequest addMerchantBankRequest);

  /**
   * @param getMerchantBankDetailsRequest
   * @return
   */
  public GetMerchantBankDetailsResponse getMerchantBankDetails(GetMerchantBankDetailsRequest getMerchantBankDetailsRequest);
  
  /**
   * @param loginRequest
   * @return
   */
  public LoginResponse authenticateMerchantUser(LoginRequest loginRequest);
  
  /**
   * @param non
   * @return
   */
  public MerchantListResponse getMerchantNamesAndMerchantCode();
  
  public Boolean changedPassword(String userName, String currentPassword, String newPassword) throws ChatakPayException;

  public Boolean forgotPassword(String userName, String baseUrl)throws ChatakPayException;

  public ApplicationClientDTO getApplicationClientAuth(String appAuthUser);

  public void saveOrUpdateApplicationClient(PGApplicationClient applicationClient);
  
  public PGMerchantUsers fetchMerchantUserByUserName(String userName);
}
