package com.chatak.acquirer.admin.service;

import java.io.IOException;
import java.util.List;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantData;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.AgentDTOResponse;
import com.chatak.pg.model.CIEntityDetailsResponse;
import com.chatak.pg.model.Merchant;

public interface MerchantValidateService {
  
  public String validateAgentDetails(String agentAccountNumber, String agentClientId, String agentANI, String currencyCodeAlpha);
  
  public Response validateUserName(String userName) throws ChatakAdminException;
  
  public Response validateEmailId(String emailId) throws ChatakAdminException;
  
  public Response validateUserNameEdit(String userName,String merchantCode) throws ChatakAdminException;
  
  public Response validateEmailIdEdit(String emailId,String merchantCode) throws ChatakAdminException;
  
  public AgentDTOResponse getAgentDataById(Long agentId);
  
  public CIEntityDetailsResponse getPartnerList(String mode)throws ChatakAdminException, IOException;
  
  public String getPartnerLinkedToMerchant(Long parentMerchantId);
  
  public List<String> getlinkedPartners();
  
  public List<String> getlinkedAgents(String parentMerchantId);
  
  public List<Option> getFeeProgramNamesForEdit(String feeProgram);
  
  public List<Option> getFeeProgramNames() throws ChatakAdminException;
  
  public Response getLocalCurrency(Long id);

  public Response fetchMerchantCurrencyByCode(String merchantCode);
  
  public Response changeMerchantStatus(MerchantData merchantData);
  
  public List<Option> getProcessorNames() throws ChatakAdminException;

  public Merchant getMerchant(Merchant merchant) throws ChatakAdminException;
}
