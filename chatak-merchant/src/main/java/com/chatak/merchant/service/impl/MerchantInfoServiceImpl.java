package com.chatak.merchant.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.chatak.mailsender.exception.PrepaidNotificationException;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.MerchantData;
import com.chatak.merchant.model.MerchantSearchResponse;
import com.chatak.merchant.model.UserData;
import com.chatak.merchant.service.MerchantInfoService;
import com.chatak.merchant.util.JsonUtil;
import com.chatak.merchant.util.StringUtil;
import com.chatak.pg.acq.dao.AdminUserDao;
import com.chatak.pg.acq.dao.CountryDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.SubMerchantDao;
import com.chatak.pg.acq.dao.SwitchDao;
import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGFeeProgram;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGState;
import com.chatak.pg.acq.dao.model.PGSwitch;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.pg.bean.CountryRequest;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ProcessorType;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.model.AgentDTO;
import com.chatak.pg.model.AgentDTOResponse;
import com.chatak.pg.model.CIEntityDetailsResponse;
import com.chatak.pg.model.CurrencyDTO;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.ValidateAgentDataRequest;
import com.chatak.pg.model.VirtualAccGetAgentsRequest;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.DeleteMerchantResponse;
import com.chatak.pg.user.bean.FeeProgramNameListDTO;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.GetMerchantListResponse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("merchantInfoService")
public class MerchantInfoServiceImpl implements MerchantInfoService, PGConstants {

  private static Logger logger = Logger.getLogger(MerchantInfoServiceImpl.class);

  @Autowired
  private MerchantDao merchantDao;

  @Autowired
  private CountryDao countryDao;

  @Autowired
  private IVelocityTemplateCreator iVelocityTemplateCreator;

  @Autowired
  SwitchDao switchDao;

  @Autowired
  MailServiceManagement mailingServiceManagement;

  @Autowired
  AdminUserDao adminUserDao;

  @Autowired
  CurrencyConfigRepository currencyConfigRepository;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;

  @Autowired
  MerchantProfileDao merchantProfileDao;

  @Autowired
  SubMerchantDao subMerchantDao;

  private ObjectMapper mapper = new ObjectMapper();

  @Override
  public Response validateUserName(String userName) throws ChatakMerchantException {
    return merchantProfileDao.getUserByUsername(userName);

  }

  @Override
  public Response changeMerchantStatus(Merchant merchant, Integer status)
      throws ChatakMerchantException {
    return null;
  }

  @Override
  public List<Option> getActiveMerchants() throws ChatakMerchantException {
    return Collections.emptyList();  
  }

  @Override
  public DeleteMerchantResponse deleteMerchant(Long id) {
    return merchantProfileDao.deleteMerchant(id);

  }

  @Override
  public List<Option> getFeeProgramNames() throws ChatakMerchantException {
    FeeProgramNameListDTO feeProgramNameListDTO = merchantProfileDao.getActiveFeePrograms();

    if (feeProgramNameListDTO.getFeeProgramDTOs() != null) {
      return validateFeeProgramNameListDTO(feeProgramNameListDTO);
    }
    return Collections.emptyList();  
  }

  private List<Option> validateFeeProgramNameListDTO(FeeProgramNameListDTO feeProgramNameListDTO) {
	List<PGFeeProgram> pgFeePrograms = feeProgramNameListDTO.getFeeProgramDTOs();
      List<Option> programNames = new ArrayList<>(
          CommonUtil.isListNotNullAndEmpty(pgFeePrograms) ? pgFeePrograms.size() : 0);
      Option pgFeeProgramRespObj = null;
      for (PGFeeProgram pGFeeProgram : pgFeePrograms) {
        pgFeeProgramRespObj = new Option();
        pgFeeProgramRespObj.setLabel(pGFeeProgram.getFeeProgramName());
        pgFeeProgramRespObj.setValue(pGFeeProgram.getFeeProgramId().toString());
        programNames.add(pgFeeProgramRespObj);
      }
      return programNames;
  }

  /**
   * @param countryId
   * @return
   * @throws ChatakAdminException
   */
  @Override
  public Response getStatesByCountry(String countryId) throws ChatakMerchantException {
    CountryRequest countryRequest = countryDao.getCountryByName(countryId);
    Response response = new Response();
    if (countryRequest.getName() != null) {

      List<PGState> pgStates = merchantProfileDao.getStateByCountryId(countryRequest.getId());

      List<Option> options = new ArrayList<>(pgStates.size());
      for (PGState state : pgStates) {
        Option option = new Option();
        option.setValue(state.getName());
        option.setLabel(state.getName());
        options.add(option);
      }
      Collections.sort(options, alphabeticalOrder);
      response.setErrorCode("00");
      response.setErrorMessage("SUCCESS");
      response.setResponseList(options);
      response.setTotalNoOfRows(options.size());

    } else {
      response.setErrorCode("99");
      response.setErrorMessage("failure");
    }
    return response;
  }
  
  /**
   * Comparator method for option class
   */
  private static Comparator<Option> alphabeticalOrder = ((Option str1, Option str2) -> {
    int res = String.CASE_INSENSITIVE_ORDER.compare(str1.getValue(), str2.getValue());
    if (res == 0) {
      res = str1.getValue().compareTo(str2.getValue());
    }
    return res;
  });

  @Override
  public List<Option> getCountries() {
    List<CountryRequest> countryRequests = countryDao.findAllCountries();
    List<Option> options = new ArrayList<>();
    if (countryRequests != null) {
      for (CountryRequest countryRequest : countryRequests) {
        Option option = new Option();
        option.setLabel(countryRequest.getName());
        option.setValue(countryRequest.getName());
        options.add(option);
      }
    }
    Collections.sort(options, alphabeticalOrder);
    return options;
  }

  /**
   * @param emailId
   * @return
   * @throws ChatakAdminException
   */
  @Override
  public Response validateEmailId(String emailId) throws ChatakMerchantException {
    UserData response = new UserData();
    // Check for admin user by the same email and status not as deleted
    String emailID = emailId.toLowerCase();
    PGAdminUser adminUsers = adminUserDao.findByEmail(emailID);
    if (null != adminUsers) {
      response.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
      response.setErrorMessage(
          ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY));
      response.setEmailId(emailId);
      return response;
    }
    return merchantProfileDao.getUserByEmailId(emailId);
  }

  @Override
  public MerchantSearchResponse searchSubMerchantList(Merchant merchant)
      throws ChatakMerchantException {
    GetMerchantListRequest searchMerchant = new GetMerchantListRequest();
    GetMerchantListResponse getMerchantListResponse = null;
    searchMerchant.setId(merchant.getParentMerchantId());
    searchMerchant.setBusinessName(merchant.getBusinessName());
    searchMerchant.setCity(merchant.getCity());
    searchMerchant.setPhone(merchant.getPhone());
    searchMerchant.setFirstName(merchant.getFirstName());
    searchMerchant.setLastName(merchant.getLastName());
    searchMerchant.setStatus(merchant.getStatus());
    searchMerchant.setCountry(merchant.getCountry());
    searchMerchant.setEmailId(merchant.getEmailId());
    searchMerchant.setPageIndex(merchant.getPageIndex());
    searchMerchant.setNoOfRecords(merchant.getNoOfRecords());
    searchMerchant.setPageSize(merchant.getPageSize());
    getMerchantListResponse = subMerchantDao.getSubMerchantListOnMerchantId(searchMerchant);
    List<PGMerchant> pgMerchants = getMerchantListResponse.getSubMerchants();
    MerchantSearchResponse response = new MerchantSearchResponse();
    if (!CollectionUtils.isEmpty(pgMerchants)) {
      List<MerchantData> merchants = new ArrayList<>(pgMerchants.size());
      MerchantData merchantRespObj = null;
      for (PGMerchant pgMerchant : pgMerchants) {
        merchantRespObj = new MerchantData();
        merchantRespObj.setId(pgMerchant.getId());
        merchantRespObj.setBusinessName(pgMerchant.getBusinessName());
        merchantRespObj.setFirstName(pgMerchant.getFirstName());
        merchantRespObj.setLastName(pgMerchant.getLastName());
        merchantRespObj.setEmailId(pgMerchant.getEmailId());
        merchantRespObj.setPhone(pgMerchant.getPhone());
        merchantRespObj.setCity(pgMerchant.getCity());
        merchantRespObj.setCountry(pgMerchant.getCountry());

        if (pgMerchant.getStatus().equals(STATUS_SUCCESS)) {
          merchantRespObj.setStatus(S_STATUS_ACTIVE);
        } else if (pgMerchant.getStatus().equals(STATUS_PENDING)) {
          merchantRespObj.setStatus(S_STATUS_PENDING);
        } else if (pgMerchant.getStatus().equals(STATUS_DELETED)) {
          merchantRespObj.setStatus(S_STATUS_DELETED);
        } else if (pgMerchant.getStatus().equals(STATUS_SUSPENDED)) {
          merchantRespObj.setStatus(S_STATUS_SUSPENDED);
        } else if (pgMerchant.getStatus().equals(STATUS_SELF_REGISTERATION_PENDING)) {
          merchantRespObj.setStatus(S_STATUS_SELFREGISTERED);
        }

        merchantRespObj.setMerchantCode(pgMerchant.getMerchantCode());
        merchantRespObj.setLocalCurrency(pgMerchant.getLocalCurrency());
        merchants.add(merchantRespObj);
      }
      response.setMerchants(merchants);
    }
    response.setTotalNoOfRows(getMerchantListResponse.getNoOfRecords());
    return response;
  }

  @Override
  public List<Option> getProcessorNames() throws ChatakMerchantException {
    // 0 Means Active
    Boolean isAvailable;
    List<PGSwitch> processorList = switchDao.getAllSwitchNamesByStatus(Constants.ZERO);

    if (CommonUtil.isListNotNullAndEmpty(processorList)) {
      List<Option> processorNames = new ArrayList<>(
          CommonUtil.isListNotNullAndEmpty(processorList) ? processorList.size() : 0);
      Option processor = null;
      for (PGSwitch pgSwitch : processorList) {
        isAvailable = Boolean.FALSE;
        processor = new Option();
        isAvailable = getProcessorDetails(isAvailable, processor, pgSwitch);
        if (!isAvailable) {
          processor.setValue(pgSwitch.getSwitchName());
          processor.setLabel(pgSwitch.getSwitchName());
        }
        processorNames.add(processor);
      }
      return processorNames;
    }
   return Collections.emptyList();  
  }

  private Boolean getProcessorDetails(Boolean isAvailable, Option processor, PGSwitch pgSwitch) {
    for (ProcessorType c : ProcessorType.values()) {
      if (c.name().equals(pgSwitch.getSwitchName())) {
        processor.setValue(c.name());
        processor.setLabel(c.value());
        isAvailable = Boolean.TRUE;
        break;
      }
    }
    return isAvailable;
  }

  @Override
  public Response validateEmailIdEdit(String emailId, String merchantCode)
      throws ChatakMerchantException {
    return merchantDao.getUserByEmailIdEdit(emailId, merchantCode);
  }

  @Override
  public Response validateUserNameEdit(String userName, String merchantCode)
      throws ChatakMerchantException {
    return merchantDao.getUserByUsernameEdit(userName, merchantCode);

  }

  @Override
  public void sendMerchantSignUPNotification(AddMerchantResponse addMerchantResponse,
      Merchant merchant) throws ChatakMerchantException {
    Map<String, String> map = new HashMap<>();
    map.put("firstName", merchant.getFirstName());
    map.put("lastName", merchant.getLastName());
    map.put("pNumber", merchant.getPhone().toString());
    map.put("mId", addMerchantResponse.getMerchantCode());

    try {
      String merchantNotityBody = iVelocityTemplateCreator.createEmailTemplate(map,
          Properties.getProperty(Constants.PREPAID_EMAIL_TEMPLATE_FILE_PATH) + "/merchant_signup.vm");
      String adminNotityBody = iVelocityTemplateCreator.createEmailTemplate(map,
          Properties.getProperty(Constants.PREPAID_EMAIL_TEMPLATE_FILE_PATH)
              + "/user_signup_admin_notification.vm");
      mailingServiceManagement.sendMailHtml(Properties.getProperty(Constants.PREPAID_FROM_EMAIL_ID),
          merchantNotityBody, merchant.getEmailId(),
          Properties.getProperty("chatak.user.signup.notify"));
      mailingServiceManagement.sendMailHtml(Properties.getProperty(Constants.PREPAID_FROM_EMAIL_ID),
          adminNotityBody, Properties.getProperty(Constants.PREPAID_FROM_EMAIL_ID),
          Properties.getProperty("chatak.user.signup.notify"));
    } catch (PrepaidNotificationException e) {
      logger.error("Error :: MerchantInfoServiceImpl :: sendMerchantSignUPNotification method", e);
      throw new ChatakMerchantException(Properties.getProperty("chatak.user.signup.notify.fail"));
    } catch (Exception e) {
      logger.error("Error :: MerchantInfoServiceImpl :: sendMerchantSignUPNotification - Exception", e);
      throw new ChatakMerchantException(e.getMessage());
    }
  }

  @Override
  public PGMerchant getMerchantOnId(Long id) {
    return merchantProfileDao.getMerchantById(id);
  }

  @Override
  public String getParentMerchantCode(String subMerchantCode) {

    return merchantDao.getParentMerchantCode(subMerchantCode);
  }

  @Override
  public List<String> getMerchantAndSubMerchantCode(String merchantCode) {
    return subMerchantDao.getMerchantAndSubMerchantList(merchantCode);
  }

  @Override
  public List<Option> getMerchantCodeAndName(String merchantCode) {
    List<String> merchantCodes = subMerchantDao.getMerchantAndSubMerchantList(merchantCode);
    List<Option> merchantNames = new ArrayList<>();
    for (String mCode : merchantCodes) {
      PGMerchant pgMerchant = merchantUpdateDao.getMerchant(mCode);
      if (null != pgMerchant) {
        Option merchantData = new Option();
        merchantData.setValue(mCode);
        merchantData.setLabel(mCode + "-" + pgMerchant.getBusinessName());
        merchantNames.add(merchantData);
      }
    }
    return merchantNames;
  }

  @Override
  public Response validateParentMerchantCode(String merchantCode) throws ChatakMerchantException {
    return merchantDao.validateMerchantCode(merchantCode);
  }

  @Override
  public CIEntityDetailsResponse getPartnerList(String mode)
      throws ChatakMerchantException, IOException {
    VirtualAccGetAgentsRequest request = new VirtualAccGetAgentsRequest();
    CIEntityDetailsResponse ciEntityDetailsResponse = null;
    /* Start posting fee to issuance */
		try {
			String output = JsonUtil.sendToIssuance(request,
					Properties.getProperty("chatak-issuance.virtual.get.partnerDetails"), mode, String.class);
			/* End posting fee to issuance */
			ciEntityDetailsResponse = mapper.readValue(output, CIEntityDetailsResponse.class);
			return ciEntityDetailsResponse;
		} catch (HttpClientException e) {
			logger.error("ERROR:: RestPaymentServiceImpl:: doVoid method", e);
			throw new ChatakMerchantException(messageSource.getMessage(ActionErrorCode.ERROR_CODE_API_CONNECT, null,
					LocaleContextHolder.getLocale()));
		}
}

  @Override
  public List<String> getExistingAgentList(String partnerId) {
    return merchantDao.getExistingAgentList(partnerId);
  }

  @Override
  public List<String> getlinkedAgents(String parentMerchantId) {
    List<String> linkedAgentsList = merchantDao.getExistingAgentList(parentMerchantId);
    return (StringUtil.isListNotNullNEmpty(linkedAgentsList)) ? linkedAgentsList
        : new ArrayList<String>();
  }

  @Override
  public List<Option> getFeeProgramNamesForEdit(String feeProgramName) {
    FeeProgramNameListDTO feeProgramNameListDTO =
        merchantDao.getActiveAndCurrentFeePrograms(feeProgramName);

    if (feeProgramNameListDTO.getFeeProgramDTOs() != null) {
      return validateFeeProgramNameListDTO(feeProgramNameListDTO);
    }
    return Collections.emptyList();  
  }

  @Override
  public String validateAgentDetails(String agentAccountNumber, String agentClientId,
		  String agentANI, String currencyCodeAlpha) {

    ValidateAgentDataRequest agentDataRequest = new ValidateAgentDataRequest();

    PGCurrencyConfig currencyConfig =
        currencyConfigRepository.findByCurrencyCodeAlpha(currencyCodeAlpha);
    String currencyCodeNumeric = currencyConfig.getCurrencyCodeNumeric();

    agentDataRequest.setAgentAccountNumber(Long.valueOf(agentAccountNumber));
    agentDataRequest.setAgentClientId(agentClientId);
    agentDataRequest.setAgentAni(agentANI);
    agentDataRequest.setCurrencyCodeNumeric(currencyCodeNumeric);
    String output = null;
    try {
    	output = JsonUtil.postIssuanceRequest(agentDataRequest,
          "/agentManagementService/agentService/searchAgentByAgentAccountNumber",String.class);
      Response response = mapper.readValue(output, Response.class);

      if (response.getErrorCode().equals("CEC_0001")) { //These Error Code and Error Messages will come from issuence Side
        return "true";
      } else {
        return response.getErrorMessage();
      }


    } catch (Exception e) {
      logger.error("Error :: MerchantInfoServiceImpl :: validateAgentDetails method", e);
    }
    return null;
  }

  public AgentDTOResponse getAgentDataById(Long agentId) {
    ValidateAgentDataRequest agentData = new ValidateAgentDataRequest();
    agentData.setAgentId(agentId);
    try {
      String output = JsonUtil.postIssuanceRequest(agentData,
          "/agentManagementService/agentService/searchAgentByAgentId", String.class);
      return mapper.readValue(output, AgentDTOResponse.class);
    } catch (Exception e) {
      logger.error("Error :: MerchantInfoServiceImpl :: getAgentDataById method", e);
    }
    return null;
  }

  public Response getAgentNames(String currencyAlpha) {
    Response response = new Response();
    CurrencyDTO currency = new CurrencyDTO();
    currency.setCurrencyCodeAlpha(currencyAlpha);
    try {
     String output = JsonUtil.postIssuanceRequest(currency,
          "/agentManagementService/agentService/searchAllAgent", String.class);
      AgentDTOResponse agentDTOlist = mapper.readValue(output, AgentDTOResponse.class);
      List<Option> options = new ArrayList<>();
      if (agentDTOlist != null && agentDTOlist.getAgentDTOlist() != null) {
        for (AgentDTO agentRequest : agentDTOlist.getAgentDTOlist()) {
          Option option = new Option();
          option.setLabel(String.valueOf(agentRequest.getAgentId()));
          option.setValue(agentRequest.getAgentName());
          options.add(option);
        }
      }
      Collections.sort(options, alphabeticalOrder);
      response.setResponseList(options);
      response.setErrorCode("00");
      response.setErrorMessage("SUCCESS");
      response.setTotalNoOfRows(options.size());

      return response;
    } catch (Exception e) {
      logger.error("Error :: MerchantInfoServiceImpl :: getAgentNames method", e);
    }
    return null;
  }

  @Override
  public Response changeMerchantStatus(MerchantData merchantData) {
    logger.info("Entering:: MerchantInfoServiceImpl:: changeMerchantStatus method");
    Response response = new Response();
    String successMessage = "";
    try {
      if (null != merchantData) {
        PGMerchant pgMerchant = merchantProfileDao.getMerchantById(merchantData.getId());
        if (merchantData.getStatus().equals(S_STATUS_ACTIVE)) {
          pgMerchant.setStatus(STATUS_ACTIVE);
        } else {
          pgMerchant.setStatus(STATUS_SUSPENDED);
        }
        pgMerchant.setReason(merchantData.getReason());
        merchantUpdateDao.createOrUpdateMerchant(pgMerchant);
        response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        if (SUB_MERCHANT.equalsIgnoreCase(pgMerchant.getMerchantType())) {
          successMessage = messageSource.getMessage("chatak.submerchant.status.change.sucess", null,
              LocaleContextHolder.getLocale());
        }
        response.setErrorMessage(successMessage);
        processMailNotification(merchantData, response, successMessage, pgMerchant);
      }
    } catch (DataAccessException e) {
      logger.error("Error :: MerchantInfoServiceImpl :: changeMerchantStatus method", e);
      response.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    } catch (PrepaidNotificationException e) {
      logger.error("Error :: MerchantInfoServiceImpl :: changeMerchantStatus method", e);
      response.setErrorMessage(successMessage + " " + messageSource.getMessage(
          "chatak.email.admin.email.send.failed", null, LocaleContextHolder.getLocale()));
    } catch (Exception e) {
      logger.error("Error :: MerchantInfoServiceImpl :: changeMerchantStatus - Exception", e);
      response.setErrorCode(ActionErrorCode.ERROR_CODE_01);
      response.setErrorMessage(
          messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
      return response;
    }
    logger.info("Exiting:: MerchantInfoServiceImpl:: changeMerchantStatus method");
    return response;
  }

  private void processMailNotification(MerchantData merchantData, Response response,
      String successMessage, PGMerchant pgMerchant) throws PrepaidNotificationException {
    if (S_STATUS_ACTIVE.equals(merchantData.getStatus())) {
      Map<String, String> map = new HashMap<>();
      map.put("firstName", pgMerchant.getFirstName());
      String body = iVelocityTemplateCreator.createEmailTemplate(map,
          Properties.getProperty(Constants.PREPAID_EMAIL_TEMPLATE_FILE_PATH)
              + "/merchant_change_status.vm");
      String subject = "";
      if (PGConstants.SUB_MERCHANT.equalsIgnoreCase(pgMerchant.getMerchantType())) {
        body = iVelocityTemplateCreator.createEmailTemplate(map,
            Properties.getProperty(Constants.PREPAID_EMAIL_TEMPLATE_FILE_PATH)
                + "/merchant_change_status.vm");
        subject = messageSource.getMessage("chatak.email.admin.submerchant.activation", null,
            LocaleContextHolder.getLocale());
      }
      mailingServiceManagement.sendMailHtml(Properties.getProperty(Constants.PREPAID_FROM_EMAIL_ID),
          body, pgMerchant.getEmailId(), subject);
      response.setErrorMessage(successMessage + " " + messageSource.getMessage(
          "chatak.email.admin.email.send.success", null, LocaleContextHolder.getLocale()));
    }
  }
  
  @Override
  public Map<String, String> getMerchantMapByMerchantType(String merchantType) {
    Map<String, String> merchantMap = new HashMap<String, String>();
    List<Map<String, String>> merchantList = merchantDao.getMerchantMapByMerchantType(merchantType);
    if (StringUtil.isListNotNullNEmpty(merchantList)) {
      for (Map<String, String> map : merchantList) {
        merchantMap.put(String.valueOf(map.get("0")), map.get("1"));
      }
    }
    return merchantMap;
  }

}
