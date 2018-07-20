package com.chatak.pg.acq.dao;

import java.util.List;
import java.util.Map;

import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantConfig;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.AddStoreRequest;
import com.chatak.pg.user.bean.AddStoreResponse;
import com.chatak.pg.user.bean.AddTerminalRequest;
import com.chatak.pg.user.bean.AddTerminalResponse;
import com.chatak.pg.user.bean.DeleteStoreRequest;
import com.chatak.pg.user.bean.DeleteStoreResponse;
import com.chatak.pg.user.bean.DeleteTerminalRequest;
import com.chatak.pg.user.bean.DeleteTerminalResponse;
import com.chatak.pg.user.bean.FeeProgramNameListDTO;
import com.chatak.pg.user.bean.GetMerchantBankDetailsRequest;
import com.chatak.pg.user.bean.GetMerchantBankDetailsResponse;
import com.chatak.pg.user.bean.GetMerchantListResponse;
import com.chatak.pg.user.bean.GetStoreListRequest;
import com.chatak.pg.user.bean.GetStoreListResponse;
import com.chatak.pg.user.bean.GetTerminalListRequest;
import com.chatak.pg.user.bean.GetTerminalListResponse;
import com.chatak.pg.user.bean.UpdateMerchantResponse;
import com.chatak.pg.user.bean.UpdateStoreRequest;
import com.chatak.pg.user.bean.UpdateStoreResponse;
import com.chatak.pg.user.bean.UpdateTerminalRequest;
import com.chatak.pg.user.bean.UpdateTerminalResponse;

public interface MerchantDao {

  /**
   * Method to get active merchant/merchants detail
   * 
   * @param searchMerchant
   * @return the list of Merchants based on search criteria
   */
  public GetMerchantListResponse getActiveMerchants();

  /**
   * This method used to update the merchant status
   * 
   * @param merchantCode
   * @param status
   * @return
   */
  public UpdateMerchantResponse updateMerchantStatus(String merchantCode, Integer status);

  /**
   * Method to get the bank details associated with merchant
   * 
   * @param getMerchantBankDetailsRequest
   * @return GetMerchantBankDetailsResponse
   */
  public GetMerchantBankDetailsResponse getMerchantBankDetails(
      GetMerchantBankDetailsRequest getMerchantBankDetailsRequest);

  /**
   * Method to add/associate a store to Merchant
   * 
   * @param addStoreRequest
   * @return AddStoreResponse
   */
  public AddStoreResponse addStore(AddStoreRequest addStoreRequest);

  /**
   * Method to update the store details already associated with merchant
   * 
   * @param UpdateStoreRequest
   * @return UpdateStoreResponse
   */
  public UpdateStoreResponse updateStore(UpdateStoreRequest updateStoreRequest);

  /**
   * Method used to delete store associated to merchant
   * 
   * @param deleteStoreRequest
   * @return DeleteStoreResponse
   */
  public DeleteStoreResponse deleteStore(DeleteStoreRequest deleteStoreRequest);

  /**
   * Method to fetch the list of store/stores associated with merchant
   * 
   * @param searchStore
   * @return GetStoreListResponse
   */
  public GetStoreListResponse getStorelist(GetStoreListRequest searchStore);

  /**
   * Method to add/associate the terminal to store/Merchant
   * 
   * @param addTerminalRequest
   * @return AddTerminalResponse
   */
  public AddTerminalResponse addTerminal(AddTerminalRequest addTerminalRequest);

  /**
   * Method to update the Terminal
   * 
   * @param UpdateTerminalRequest
   * @return UpdateTerminalResponse
   */
  public UpdateTerminalResponse updateTerminal(UpdateTerminalRequest updateTerminalRequest);

  /**
   * Method to soft delete the Terminal
   * 
   * @param deleteTerminalRequest
   * @return DeleteTerminalResponse
   */
  public DeleteTerminalResponse deleteTerminal(DeleteTerminalRequest deleteTerminalRequest);

  /**
   * Method to get the list of terminals available for merchant/store
   * 
   * @param searchTerminal
   * @return GetTerminalListResponse
   */
  public GetTerminalListResponse getTerminallist(GetTerminalListRequest searchTerminal);

  public List<ReportsDTO> getAccountTransferList();

  public Response getUserByEmailIdEdit(String email, String merchantCode);

  public Response getUserByUsernameEdit(String userName, String merchantCode);

  public String getParentMerchantCode(String merchantCode);

  public Response validateMerchantCode(String merchantCode);

  public String getAgentId(String merchantCode);

  public List<String> getExistingAgentList(String partnerId);

  public String getApplicationMode(String merchantCode);

  public List<Map<String, String>> getMerchantNamesAndMerchantCode();

  public List<PGMerchant> findById(Long parentMerchantId);

  public Long getMerchantIdOnMerchantCode(String merchantCode);

  public List<Map<String, String>> getMerchantNameAndMerchantCodeByMerchantType(
      String merchantType);

  public String getMerchantTypeOnMerchantCode(String merchantCode);

  public String getMerchantCompanyNameOnMerchantCode(String merchantCode);

  public Map<String, String> getAllMerchantMap();

  public List<Map<String, String>> getMerchantMapByMerchantType(String merchantType);

  public FeeProgramNameListDTO getActiveAndCurrentFeePrograms(String feeProgram);

  public List<PGMerchant> getMerchantDetailsForAccountCreation(Merchant merchant);

  public void getMerchantConfigDetailsForAccountCreate(Merchant merchant);

  public List<Map<String, String>> getMerchantCodeAndCompanyName(String merchantType);
  
  public PGMerchant findBymerchantConfig(String merchantId);

}
