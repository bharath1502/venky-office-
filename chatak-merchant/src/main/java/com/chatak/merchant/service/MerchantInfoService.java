package com.chatak.merchant.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.MerchantData;
import com.chatak.merchant.model.MerchantSearchResponse;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.AgentDTOResponse;
import com.chatak.pg.model.CIEntityDetailsResponse;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.DeleteMerchantResponse;

public interface MerchantInfoService {

  /**
   * Method to change the Merchant status
   * 
   * @param merchant
   * @param status
   * @return
   * @throws ChatakAdminException
   */
  public Response changeMerchantStatus(Merchant merchant, Integer status)
      throws ChatakMerchantException;

  /**
   * Method to get active Merchants
   * 
   * @return
   * @throws ChatakAdminException
   */
  public List<Option> getActiveMerchants() throws ChatakMerchantException;

  /**
   * @param userName
   * @return
   * @throws ChatakMerchantException
   */
  public Response validateUserName(String userName) throws ChatakMerchantException;

  /**
   * @param id
   * @return
   */
  public DeleteMerchantResponse deleteMerchant(Long id);

  /**
   * @return
   * @throws ChatakMerchantException
   */
  public List<Option> getFeeProgramNames() throws ChatakMerchantException;

  /**
   * @param countryId
   * @return
   * @throws ChatakMerchantException
   */
  public Response getStatesByCountry(String countryId) throws ChatakMerchantException;

  /**
   * @param emailId
   * @return
   * @throws ChatakMerchantException
   */
  public Response validateEmailId(String emailId) throws ChatakMerchantException;

  /**
   * @return
   */
  public List<Option> getCountries();

  /**
   * @param merchant
   * @return
   * @throws ChatakMerchantException
   */
  public MerchantSearchResponse searchSubMerchantList(Merchant merchant)
      throws ChatakMerchantException;

  /**
   * @return
   * @throws ChatakMerchantException
   */
  public List<Option> getProcessorNames() throws ChatakMerchantException;

  public Response validateEmailIdEdit(String emailId, String merchantCode)
      throws ChatakMerchantException;

  public Response validateUserNameEdit(String userName, String merchantCode)
      throws ChatakMerchantException;

  public void sendMerchantSignUPNotification(AddMerchantResponse addMerchantResponse,
      Merchant merchant) throws ChatakMerchantException;

  public PGMerchant getMerchantOnId(Long id);

  public String getParentMerchantCode(String subMerchantCode);

  public List<String> getMerchantAndSubMerchantCode(String merchantCode);

  public List<Option> getMerchantCodeAndName(String merchantCode);

  public Response validateParentMerchantCode(String merchantCode) throws ChatakMerchantException;

  public CIEntityDetailsResponse getPartnerList(String mode)
      throws ChatakMerchantException, IOException;

  public List<String> getExistingAgentList(String partnerId);

  public List<String> getlinkedPartners();

  public List<String> getlinkedAgents(String parentMerchantId);

  public String getPartnerLinkedToMerchant(Long parentMerchantId);

  public List<Option> getFeeProgramNamesForEdit(String feeProgram);

  public String validateAgentDetails(String agentAccountNumber, String agentClientId,
      String agentANI, String currencyCodeAlpha);

  public Response getAgentNames(String currencyAlpha);

  public AgentDTOResponse getAgentDataById(Long agentId);

  public Response changeMerchantStatus(MerchantData merchantData);
  
  public Map<String, String> getMerchantMapByMerchantType(String merchantType);
}
