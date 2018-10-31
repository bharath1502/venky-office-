package com.chatak.acquirer.admin.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantData;
import com.chatak.acquirer.admin.model.UserData;
import com.chatak.acquirer.admin.service.MerchantValidateService;
import com.chatak.acquirer.admin.util.DateUtils;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.mailsender.exception.PrepaidNotificationException;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.pg.acq.dao.AdminUserDao;
import com.chatak.pg.acq.dao.LegalEntityDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.PartnerDao;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.SwitchDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAdminUser;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGFeeProgram;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantBank;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.acq.dao.model.PGSwitch;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ProcessorType;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.model.AgentDTOResponse;
import com.chatak.pg.model.CIEntityDetailsResponse;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.ValidateAgentDataRequest;
import com.chatak.pg.model.VirtualAccGetAgentsRequest;
import com.chatak.pg.user.bean.FeeProgramNameListDTO;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MerchantValidateServiceImpl implements MerchantValidateService, PGConstants {

  private static Logger logger = Logger.getLogger(MerchantUpdateServiceImpl.class);

  @Autowired
  private MerchantDao merchantDao;

  @Autowired
  private MailServiceManagement mailingServiceManagement;

  @Autowired
  private IVelocityTemplateCreator iVelocityTemplateCreator;

  @Autowired
  private AdminUserDao adminUserDao;

  @Autowired
  MerchantRepository merchantRepository;

  @Autowired
  private CurrencyConfigRepository currencyConfigRepository;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private SwitchDao switchDao;

  @Autowired
  private LegalEntityDao legalEntityDao;

  @Autowired
  MerchantProfileDao merchantProfileDao;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;
  
  @Autowired
  PartnerDao partnerDao;
  
  @Autowired
  ProgramManagerDao programManagerDao;

  private ObjectMapper mapper = new ObjectMapper();

  @Override
  public String validateAgentDetails(String agentAccountNumber, String agentClientId,
      String agentANI, String currencyCodeAlpha) {
    ValidateAgentDataRequest agentDataRequest = new ValidateAgentDataRequest();
    PGCurrencyConfig currencyConfig =
        currencyConfigRepository.findByCurrencyCodeAlpha(currencyCodeAlpha);
    String currencyCodeNumeric = currencyConfig.getCurrencyCodeNumeric();
    agentDataRequest.setAgentAccountNumber(Long.valueOf(agentAccountNumber));
    agentDataRequest.setAgentClientId(agentClientId);
    agentDataRequest.setCurrencyCodeNumeric(currencyCodeNumeric);
    agentDataRequest.setAgentAni(agentANI);
    try {
      String output = JsonUtil.postIssuanceRequest(agentDataRequest,
              "/agentManagementService/agentService/searchAgentByAgentAccountNumber",String.class);
      Response response = mapper.readValue(output, Response.class);
      if (response.getErrorCode().equals("CEC_0001")) { //These Error Code and Error Messages will come from issuence Side
        return "true";
      } else {
        return response.getErrorMessage();
      }
    } catch (Exception exp) {
      logger.error("Error :: MerchantValidateServiceImpl :: validateAgentDetails", exp);
    }
    return null;
  }

  @Override
  public Response validateUserName(String userName) throws ChatakAdminException {
    return merchantProfileDao.getUserByUsername(userName);
  }

  @Override
  public Response validateEmailId(String emailId) throws ChatakAdminException {
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
    return merchantProfileDao.getUserByEmailId(emailID);
  }

  @Override
  public Response validateUserNameEdit(String userName, String merchantCode)
      throws ChatakAdminException {
    return merchantDao.getUserByUsernameEdit(userName, merchantCode);
  }

  @Override
  public Response validateEmailIdEdit(String emailId, String merchantCode)
      throws ChatakAdminException {
    return merchantDao.getUserByEmailIdEdit(emailId, merchantCode);
  }

  public AgentDTOResponse getAgentDataById(Long agentId) {
    ValidateAgentDataRequest agentData = new ValidateAgentDataRequest();
    agentData.setAgentId(agentId);
    try {
      String output = JsonUtil.postIssuanceRequest(agentData,
              "/agentManagementService/agentService/searchAgentByAgentId",String.class);
      return mapper.readValue(output, AgentDTOResponse.class);
    } catch (Exception exp) {
      logger.error("Error :: MerchantValidateServiceImpl :: getAgentDataById", exp);
    }
    return null;
  }

  @Override
  public CIEntityDetailsResponse getPartnerList(String mode)
      throws ChatakAdminException, IOException {
    VirtualAccGetAgentsRequest request = new VirtualAccGetAgentsRequest();
    CIEntityDetailsResponse ciEntityDetailsResponse = null;
    /* Start posting fee to issuance */
	try {
		String output = JsonUtil.sendToIssuance(request,
			        Properties.getProperty("chatak-issuance.virtual.get.partnerDetails"), mode,String.class);
	      ciEntityDetailsResponse = mapper.readValue(output, CIEntityDetailsResponse.class);

	} catch (HttpClientException e) {
		logger.error("ERROR :: RestPaymentServiceImpl :: doSale method", e);
        throw new ChatakAdminException(e.getMessage());
	}
    return ciEntityDetailsResponse;
  }

  @Override
  public List<String> getlinkedAgents(String parentMerchantId) {
    return merchantDao.getExistingAgentList(parentMerchantId);
  }

  @Override
  public List<Option> getFeeProgramNamesForEdit(String feeProgramName) {
    FeeProgramNameListDTO feeProgramNameListDTO =
        merchantDao.getActiveAndCurrentFeePrograms(feeProgramName);

    if (feeProgramNameListDTO.getFeeProgramDTOs() != null) {
      return getFeePrograms(feeProgramNameListDTO);
    }
    return Collections.emptyList();
  }

  private List<Option> getFeePrograms(FeeProgramNameListDTO feeProgramNameListDTO) {
    List<PGFeeProgram> pgFeePrograms = feeProgramNameListDTO.getFeeProgramDTOs();
    List<Option> programNames = new ArrayList<>(
        CommonUtil.isListNotNullAndEmpty(pgFeePrograms) ? pgFeePrograms.size() : 0);
    Option pgFeeProgramRespObj = null;
    for (PGFeeProgram pGFeeProgram : pgFeePrograms) {
      pgFeeProgramRespObj = new Option();
      pgFeeProgramRespObj.setValue(pGFeeProgram.getFeeProgramId().toString());
      pgFeeProgramRespObj.setLabel(pGFeeProgram.getFeeProgramName());
      programNames.add(pgFeeProgramRespObj);
    }
    return programNames;
  }

  @Override
  public List<Option> getFeeProgramNames() throws ChatakAdminException {
    FeeProgramNameListDTO feeProgramNameListDTO = merchantProfileDao.getActiveFeePrograms();

    if (feeProgramNameListDTO.getFeeProgramDTOs() != null) {
      return getFeePrograms(feeProgramNameListDTO);
    }
    return Collections.emptyList();
  }

  @Override
  public Response getLocalCurrency(Long id) {
    PGMerchant pgMerchant = merchantProfileDao.getMerchantById(id);
    Response response = new Response();
    if (pgMerchant != null) {
      response.setCurrencyCodeAlpha(pgMerchant.getLocalCurrency());
      response.setErrorCode("00");
    }
    return response;
  }

  @Override
  public Response fetchMerchantCurrencyByCode(String merchantCode) {
    Response response = new Response();
    PGMerchant pgMerchant = merchantUpdateDao.getMerchantByCode(merchantCode);
    if (pgMerchant != null) {
      response.setCurrencyCodeAlpha(pgMerchant.getLocalCurrency());
      response.setErrorCode("00");
      response.setErrorMessage("SUCCESS");
    }
    return response;
  }

  @Override
  public Response changeMerchantStatus(MerchantData merchantData) {
    logger.info("Entering:: MerchantServiceImpl:: changeMerchantStatus method");
    Response response = new Response();
    PGMerchant pgMerchant = null;
    String successMessage = null;
    try {
      if (null != merchantData) {
        pgMerchant = merchantProfileDao.getMerchantById(merchantData.getId());
        if (S_STATUS_ACTIVE.equals(merchantData.getStatus())) {
          pgMerchant.setStatus(STATUS_ACTIVE);
        } else {
          pgMerchant.setStatus(STATUS_SUSPENDED);
        }
        pgMerchant.setReason(merchantData.getReason());
        merchantUpdateDao.createOrUpdateMerchant(pgMerchant);
        merchantSubMerchantAssociatedUsersAndAccountStatusChange(pgMerchant,
            merchantData.getStatus());
        response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        if (SUB_MERCHANT.equalsIgnoreCase(pgMerchant.getMerchantType())) {
          successMessage = messageSource.getMessage("chatak.submerchant.status.change.sucess", null,
              LocaleContextHolder.getLocale());
        } else {
          successMessage = messageSource.getMessage("chatak.merchant.status.change.sucess", null,
              LocaleContextHolder.getLocale());
        }
        response.setErrorMessage(successMessage);
        if (S_STATUS_ACTIVE.equals(merchantData.getStatus())) {
          Map<String, String> map = new HashMap<>();
          map.put("firstName", pgMerchant.getFirstName());
          String body = iVelocityTemplateCreator.createEmailTemplate(map,
              Properties.getProperty("prepaid.email.template.file.path")
                  + "/merchant_change_status.vm");
          String subject = "";
          subject = getSubjectOnMerchantType(pgMerchant);
          mailingServiceManagement.sendMailHtml(Properties.getProperty("prepaid.from.email.id"),
              body, pgMerchant.getEmailId(), subject);
          response.setErrorMessage(successMessage + " " + messageSource.getMessage(
              "chatak.email.admin.email.send.success", null, LocaleContextHolder.getLocale()));
        }
      }
    } catch (PrepaidNotificationException e) {
      logger.error("Error :: MerchantServiceImpl:: changeMerchantStatus ", e);
      response.setErrorMessage(successMessage + " " + messageSource.getMessage(
          "chatak.email.admin.email.send.failed", null, LocaleContextHolder.getLocale()));
    } catch (Exception exp) {
      logger.error("Error :: MerchantServiceImpl:: changeMerchantStatus Exception", exp);
      response.setErrorCode(ActionErrorCode.ERROR_CODE_01);
      response.setErrorMessage(
          messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
      return response;
    }
    logger.info("Exiting:: MerchantServiceImpl:: changeMerchantStatus method");
    return response;
  }

  private String getSubjectOnMerchantType(PGMerchant pgMerchant) {
    String subject;
    subject = messageSource.getMessage("chatak.email.admin.merchant.activation", null,
        LocaleContextHolder.getLocale());
    if (PGConstants.SUB_MERCHANT.equalsIgnoreCase(pgMerchant.getMerchantType())) {
      subject = messageSource.getMessage("chatak.email.admin.submerchant.activation", null,
          LocaleContextHolder.getLocale());
    }
    return subject;
  }

  private void merchantSubMerchantAssociatedUsersAndAccountStatusChange(PGMerchant pgMerchant,
      String status) {
    try {
      List<PGMerchantUsers> pgMerchantUsersList = pgMerchant.getPgMerchantUsers();
      if (CommonUtil.isListNotNullAndEmpty(pgMerchantUsersList)) {
        updateMerchantUsersList(status, pgMerchantUsersList);
      }
      if (pgMerchant.getParentMerchantId() == null) {
        List<PGMerchant> subMerchantList =
            merchantUpdateDao.findByParentMerchantIdAndStatus(pgMerchant.getId(),
                (S_STATUS_ACTIVE.equals(status)) ? STATUS_SUSPENDED : STATUS_ACTIVE);
        if (CommonUtil.isListNotNullAndEmpty(subMerchantList)) {
          updateSubMerchantList(status, subMerchantList);
        }
      } else {
        List<PGAccount> pgMerchantAccounts = merchantUpdateDao
            .findByEntityIdAndStatusNotLike(pgMerchant.getMerchantCode(), S_STATUS_DELETED);
        updatePGAccount(status, pgMerchantAccounts);
      }
    } catch (Exception e) {
      //do nothing
      logger.error(
          "Error :: MerchantValidateServiceImpl :: merchantSubMerchantAssociatedUsersAndAccountStatusChange",
          e);
    }
  }

  private void updateMerchantUsersList(String status, List<PGMerchantUsers> pgMerchantUsersList) {
    for (PGMerchantUsers pgMerchantUsers : pgMerchantUsersList) {
      if (S_STATUS_ACTIVE.equals(status)) {
        pgMerchantUsers.setStatus(STATUS_ACTIVE);
      } else {
        pgMerchantUsers.setStatus(STATUS_SUSPENDED);
      }
      merchantUpdateDao.createOrUpdateMerchantUsers(pgMerchantUsers);
    }
  }

  private void updateSubMerchantList(String status, List<PGMerchant> subMerchantList) {
    for (PGMerchant subMerchant : subMerchantList) {
      if (S_STATUS_ACTIVE.equals(status)) {
        subMerchant.setStatus(STATUS_ACTIVE);
      } else {
        subMerchant.setStatus(STATUS_SUSPENDED);
      }
      merchantUpdateDao.createOrUpdateMerchant(subMerchant);
      List<PGAccount> pgMerchantAccounts = merchantUpdateDao
          .findByEntityIdAndStatusNotLike(subMerchant.getMerchantCode(), S_STATUS_DELETED);
      updatePGAccount(status, pgMerchantAccounts);
    }
  }

  private void updatePGAccount(String status, List<PGAccount> pgMerchantAccounts) {
    if (CommonUtil.isListNotNullAndEmpty(pgMerchantAccounts)) {
      for (PGAccount pgAccount : pgMerchantAccounts) {
        pgAccount.setStatus(status);
        merchantUpdateDao.createOrUpdateAccouunt(pgAccount);
      }
    }
  }

  @Override
  public List<Option> getProcessorNames() throws ChatakAdminException {
    List<PGSwitch> processorList = switchDao.getAllSwitchNamesByStatus(Constants.ZERO);

    if (CommonUtil.isListNotNullAndEmpty(processorList)) {
      List<Option> processorNames = new ArrayList<>(
          CommonUtil.isListNotNullAndEmpty(processorList) ? processorList.size() : 0);
      return getProcessorsList(processorList, processorNames);
    }
    return Collections.emptyList();
  }

  private List<Option> getProcessorsList(List<PGSwitch> processorList,
      List<Option> processorNames) {
    Option processor;
    Boolean isAvailable;
    for (PGSwitch pgSwitch : processorList) {
      isAvailable = Boolean.FALSE;
      processor = new Option();
      for (ProcessorType c : ProcessorType.values()) {
        if (c.name().equals(pgSwitch.getSwitchName())) {
          processor.setValue(c.name());
          processor.setLabel(c.value());
          isAvailable = Boolean.TRUE;
          break;
        }
      }
      if (!isAvailable) {
        processor.setValue(pgSwitch.getSwitchName());
        processor.setLabel(pgSwitch.getSwitchName());
      }
      processorNames.add(processor);
    }
    return processorNames;
  }

  @Override
  public Merchant getMerchant(Merchant merchant) throws ChatakAdminException {
    logger.info("Entering:: MerchantServiceImpl:: getMerchant method");
    PGMerchant pgMerchant = validateMerchantType(merchant);
    PGAccount pgAccount = merchantProfileDao.getPgAccount(pgMerchant.getMerchantCode());
    if (pgAccount != null) {
      merchant.setAddress1(pgMerchant.getAddress1());
      merchant.setAddress2(pgMerchant.getAddress2());
      merchant.setCountry(pgMerchant.getCountry());
      merchant.setId(pgMerchant.getId());
      merchant.setCity(pgMerchant.getCity());
      merchant.setState(pgMerchant.getState());
      merchant.setAppMode(pgMerchant.getAppMode());
      merchant.setBusinessURL(pgMerchant.getBusinessURL());
      merchant.setEmailId(pgMerchant.getEmailId());
      merchant.setBusinessName(pgMerchant.getBusinessName());
      merchant.setEstimatedYearlySale(pgMerchant.getEstimatedYearlySale());
      merchant.setFax(pgMerchant.getFax());
      merchant.setLocalCurrency(pgMerchant.getLocalCurrency());
      merchant.setFirstName(pgMerchant.getFirstName());
      merchant.setLastName(pgMerchant.getLastName());
      merchant.setFederalTaxId(pgMerchant.getFederalTaxId());
      merchant.setNoOfEmployee(pgMerchant.getNoOfEmployee());
      merchant.setOwnership(pgMerchant.getOwnership());
      merchant.setAgentId(pgMerchant.getAgentId());
      merchant.setDccEnable(pgMerchant.getDccEnable());
      if (ProcessorType.LITLE.name()
          .equalsIgnoreCase(pgMerchant.getMerchantConfig().getProcessor())) {
        merchant.setLitleMID(pgMerchant.getLitleMID());
      }
      merchant.setPhone(pgMerchant.getPhone());
      merchant.setPin(pgMerchant.getPin());
      merchant.setSalesTaxId(pgMerchant.getSalesTaxId());
      merchant.setStateTaxId(pgMerchant.getStateTaxId());
      merchant.setRole(pgMerchant.getRole());
      merchant.setStatus(pgMerchant.getStatus());
      merchant.setTimeZone(pgMerchant.getTimeZone());
      merchant.setBusinessStartDate(DateUtils.timestampToString(pgMerchant.getBusinessStartDate()));
      merchant.setMerchantCallBackURL(pgMerchant.getMerchantCallBack());
      merchant.setUpdatedDate(DateUtils.timestampToString(pgMerchant.getUpdatedDate()));
      merchant.setFeeProgram(pgMerchant.getMerchantConfig().getFeeProgram());
      merchant.setProcessor(pgMerchant.getMerchantConfig().getProcessor());
      merchant.setMerchantCode(pgMerchant.getMerchantCode());
      merchant.setShippingAmount(
          pgMerchant.getMerchantConfig().getShippingAmount() == 1 ? true : false);
      merchant.setTaxAmount(pgMerchant.getMerchantConfig().getTaxAmount() == 1 ? true : false);
      merchant.setRefunds(pgMerchant.getMerchantConfig().getRefunds() == 1 ? true : false);
      merchant.setTipAmount(pgMerchant.getMerchantConfig().getTipAmount() == 1 ? true : false);
      merchant.setUserName(pgMerchant.getUserName());
      merchant = getMerchantOtherDetails(merchant, pgMerchant);
      //pay page config
      merchant.setPayPageConfig((null != pgMerchant.getMerchantConfig().getPayPageConfig()
          && pgMerchant.getMerchantConfig().getPayPageConfig() == 1) ? true : false);
      merchant.setMerchantCategory(pgMerchant.getMerchantCategory());
      merchant.setPayOutAt(pgMerchant.getMerchantConfig().getPayOutAt());
      //END
      merchant.setBankId(pgMerchant.getBankId());
      merchant.setResellerId(pgMerchant.getResellerId());
      merchant.setAutoPaymentMethod(pgAccount.getAutoPaymentMethod());
      merchant.setBusinessType(pgMerchant.getBusinessType());
      merchant.setLookingFor(pgMerchant.getLookingFor());
      merchant.setMcc(pgMerchant.getMcc());
      
      merchant = getMerchantAutoTranaferData(merchant, pgAccount);
      merchant.setAccountId(pgAccount.getAccountNum());
      merchant.setCategory(pgAccount.getCategory());
      merchant.setMerchantCallBackURL(pgMerchant.getMerchantCallBack());
      merchant.setMerchantType(pgMerchant.getMerchantType());
      merchant.setAgentAccountNumber(pgMerchant.getAgentAccountNumber());
      merchant.setAgentANI(pgMerchant.getAgentANI());
      merchant.setAgentClientId(pgMerchant.getAgentClientId());
      merchant.setAgentId(pgMerchant.getAgentId());

      PGMerchantBank merchantBank = pgAccount.getPgMerchantBank();
      merchant.setBankAccountName(merchantBank.getBankName());
      merchant.setBankAccountType(merchantBank.getAccountType());
      merchant.setBankAddress1(merchantBank.getAddress1());
      merchant.setBankAccountNumber(merchantBank.getBankAccNum());
      merchant.setBankAddress2(merchantBank.getAddress2());
      merchant.setBankCity(merchantBank.getCity());
      merchant.setBankCurrencyCode(merchantBank.getCurrencyCode());
      merchant.setBankNameOnAccount(merchantBank.getNameOnAccount());
      merchant.setBankPin(merchantBank.getPin());
      merchant.setBankCountry(merchantBank.getCountry());
      merchant.setBankRoutingNumber(merchantBank.getRoutingNumber());
      merchant.setBankState(merchantBank.getState());

      logger.info("Exiting:: MerchantServiceImpl:: getMerchant method");
      return merchant;
    }
    logger.info("Exiting:: MerchantServiceImpl:: getMerchant method");
    return null;
  }

  private PGMerchant validateMerchantType(Merchant merchant) {
    return merchantProfileDao.getMerchantById(merchant.getId());
  }

  private Merchant getMerchantOtherDetails(Merchant merchant, PGMerchant pgMerchant) {
    if (pgMerchant.getParentMerchantId() != null) {
      merchant.setParentMerchantId(Long.valueOf(
          merchantRepository.findById(pgMerchant.getParentMerchantId()).getMerchantCode()));
    }
    merchant.setAllowAdvancedFraudFilter(null != pgMerchant.getAllowAdvancedFraudFilter()
        && pgMerchant.getAllowAdvancedFraudFilter() == 1 ? true : false);
    //NEW ADDED
    merchant.setVirtualTerminal(null != pgMerchant.getMerchantConfig().getVirtualTerminal()
        && pgMerchant.getMerchantConfig().getVirtualTerminal() == 1 ? true : false);
    merchant.setPosTerminal((null != pgMerchant.getMerchantConfig().getPosTerminal()
        && pgMerchant.getMerchantConfig().getPosTerminal() == 1) ? true : false);
    merchant.setOnline((null != pgMerchant.getMerchantConfig().getOnline()
        && pgMerchant.getMerchantConfig().getOnline() == 1) ? true : false);
    merchant.setWebSiteAddress(pgMerchant.getMerchantConfig().getWebSiteAddress());
    merchant.setReturnUrl(pgMerchant.getMerchantConfig().getReturnUrl());
    merchant.setCancelUrl(pgMerchant.getMerchantConfig().getCancelUrl());
    return merchant;
  }

  private Merchant getMerchantAutoTranaferData(Merchant merchant, PGAccount pgAccount) {
    if (null != pgAccount.getAutoTransferDay()) {
      if (pgAccount.getAutoTransferDay().startsWith(Constants.DAILY)) {
        merchant.setAutoTransferDay(pgAccount.getAutoTransferDay());
      } else if (pgAccount.getAutoTransferDay().startsWith(Constants.WEEKLY)) {
        merchant.setAutoTransferDay(pgAccount.getAutoTransferDay().split(":")[0]);
        merchant.setAutoTransferWeeklyDay(pgAccount.getAutoTransferDay().split(":")[1]);
      }
      if (pgAccount.getAutoTransferDay().startsWith(Constants.MONTHLY)) {
        merchant.setAutoTransferDay(pgAccount.getAutoTransferDay().split(":")[0]);
        merchant.setAutoTransferMonthlyDay(pgAccount.getAutoTransferDay().split(":")[1]);
      }
    }
    if (null != pgAccount.getAutoPaymentLimit()) {
      merchant.setAutoTransferLimit(new BigDecimal(pgAccount.getAutoPaymentLimit().toString()));
    }
    return merchant;
  }
}
