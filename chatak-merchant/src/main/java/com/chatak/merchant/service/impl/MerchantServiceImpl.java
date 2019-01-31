package com.chatak.merchant.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.chatak.mailsender.exception.PrepaidNotificationException;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.MerchantData;
import com.chatak.merchant.model.MerchantSearchResponse;
import com.chatak.merchant.service.MerchantService;
import com.chatak.merchant.util.DateUtils;
import com.chatak.merchant.util.EncryptionUtil;
import com.chatak.merchant.util.PasswordHandler;
import com.chatak.merchant.util.StringUtil;
import com.chatak.pg.acq.dao.AdminUserDao;
import com.chatak.pg.acq.dao.LegalEntityDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.PartnerDao;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.SubMerchantDao;
import com.chatak.pg.acq.dao.SwitchDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantBank;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ProcessorType;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.AddMerchantRequest;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.GetMerchantListResponse;
import com.chatak.pg.user.bean.UpdateMerchantRequest;
import com.chatak.pg.user.bean.UpdateMerchantResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 03-Jan-2015 4:28:42 PM
 * @version 1.0
 */
@Service
public class MerchantServiceImpl implements MerchantService, PGConstants {

  private static Logger logger = Logger.getLogger(MerchantServiceImpl.class);

  @Autowired
  private MerchantDao merchantDao;

  @Autowired
  private IVelocityTemplateCreator iVelocityTemplateCreator;

  @Autowired
  private LegalEntityDao legalEntityDao;

  @Autowired
  SwitchDao switchDao;

  @Autowired
  MailServiceManagement mailingServiceManagement;

  @Autowired
  AdminUserDao adminUserDao;

  @Autowired
  CurrencyConfigRepository currencyConfigRepository;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;

  @Autowired
  SubMerchantDao subMerchantDao;

  @Autowired
  MerchantProfileDao merchantProfileDao;
  
  @Autowired
  ProgramManagerDao programManagerDao;
  
  @Autowired
  PartnerDao partnerDao;

  @Override
  public AddMerchantResponse addMerchant(Merchant merchant, String userid)
      throws ChatakMerchantException {

    AddMerchantRequest addMerchantRequest = new AddMerchantRequest();
    addMerchantRequest.setAddress1(merchant.getAddress1());
    addMerchantRequest.setAddress2(merchant.getAddress2());
    addMerchantRequest.setAllowRepeatSale(merchant.getAllowRepeatSale());
    addMerchantRequest.setAppMode(merchant.getAppMode());
    addMerchantRequest.setAutoSettlement(merchant.getAutoSettlement());
    addMerchantRequest.setBusinessName(merchant.getBusinessName());
    addMerchantRequest.setBusinessStartDate(merchant.getBusinessStartDate());
    addMerchantRequest.setBusinessURL(merchant.getBusinessURL());
    addMerchantRequest.setCity(merchant.getCity());
    addMerchantRequest.setCountry(merchant.getCountry());
    addMerchantRequest.setEmailId(merchant.getEmailId());
    addMerchantRequest.setEstimatedYearlySale(merchant.getEstimatedYearlySale());
    addMerchantRequest.setFax(merchant.getFax());
    addMerchantRequest.setFederalTaxId(merchant.getFederalTaxId());
    addMerchantRequest.setFeeProgram(merchant.getFeeProgram());
    addMerchantRequest.setFirstName(merchant.getFirstName());
    addMerchantRequest.setLastName(merchant.getLastName());
    addMerchantRequest.setNoOfEmployee(merchant.getNoOfEmployee());
    addMerchantRequest.setOwnership(merchant.getOwnership());
    addMerchantRequest.setPhone(merchant.getPhone());
    addMerchantRequest.setPin(merchant.getPin());
    addMerchantRequest.setProcessor(merchant.getProcessor());
    addMerchantRequest.setRefunds(merchant.getRefunds());
    addMerchantRequest.setUserName(merchant.getUserName());
    addMerchantRequest.setTipAmount(merchant.getTipAmount());
    addMerchantRequest.setTimeZone(merchant.getTimeZone());
    addMerchantRequest.setTaxAmount(merchant.getTaxAmount());
    addMerchantRequest.setStatus(merchant.getStatus());
    addMerchantRequest.setStateTaxId(merchant.getStateTaxId());
    addMerchantRequest.setState(merchant.getState());
    addMerchantRequest.setShowRecurringBilling(merchant.getShowRecurringBilling());
    addMerchantRequest.setShippingAmount(merchant.getShippingAmount());
    addMerchantRequest.setSalesTaxId(merchant.getSalesTaxId());
    addMerchantRequest.setRole(merchant.getRole());
    addMerchantRequest.setAutoSettlement(merchant.getAutoSettlement());
    addMerchantRequest.setMerchantCallBackURL(merchant.getMerchantCallBackURL());
    addMerchantRequest.setCategory(merchant.getCategory());
    addMerchantRequest.setAutoPaymentMethod(merchant.getAutoPaymentMethod());
    addMerchantRequest.setBusinessType(merchant.getBusinessType());
    addMerchantRequest.setLookinFor(merchant.getLookingFor());
    //NEW ADDED FIELDS
    addMerchantRequest.setVirtualTerminal(merchant.getVirtualTerminal());
    addMerchantRequest.setPosTerminal(merchant.getPosTerminal());
    addMerchantRequest.setOnline(merchant.getOnline());
    addMerchantRequest.setWebSiteAddress(merchant.getWebSiteAddress());
    addMerchantRequest.setReturnUrl(merchant.getReturnUrl());
    addMerchantRequest.setCancelUrl(merchant.getCancelUrl());
    //END

    addMerchantRequest
        .setIssuancePartnerId(StringUtil.isNullAndEmpty(merchant.getIssuancePartnerId()) ? null
            : merchant.getIssuancePartnerId());
    addMerchantRequest.setAgentId(
        StringUtil.isNullAndEmpty(merchant.getAgentId()) ? null : merchant.getAgentId());
    addMerchantRequest.setProgramManagerId(StringUtil.isNullAndEmpty(merchant.getProgramManagerId())
        ? null : merchant.getProgramManagerId());

    /* Assigning auto transfer day with ',' separated values */
    if (null != merchant.getAutoTransferDay()) {
      autoTransferDay(merchant, addMerchantRequest);
    } else {
      addMerchantRequest.setAutoTransferDay(merchant.getAutoTransferDay());
    }

    if (ProcessorType.LITLE.name().equals(merchant.getProcessor())) {
      addMerchantRequest.setLitleMID(merchant.getLitleMID());
    }
    if (null != merchant.getAutoTransferLimit()) {
      addMerchantRequest.setAutoTransferLimit(merchant.getAutoTransferLimit()/* * 100*/);
    }
    addMerchantRequest.setMerchantType(PGConstants.SUB_MERCHANT);
    addMerchantRequest.setParentMerchantId(merchant.getParentMerchantId());
    addMerchantRequest.setCreatedBy(userid);
    String password = PasswordHandler.getSystemGeneratedPassword(Constants.EIGHT);
    try {
      addMerchantRequest.setPassword(EncryptionUtil.encodePassword(password));
    } catch (Exception e1) {
      logger.error("Error :: MerchantServiceImpl :: addMerchant", e1);
      throw new ChatakMerchantException(e1.getMessage());
    }

    addMerchantRequest.setBankAccountType(merchant.getBankAccountType());
    addMerchantRequest.setBankAccountNumber(merchant.getBankAccountNumber());
    addMerchantRequest.setBankRoutingNumber(merchant.getBankRoutingNumber());
    addMerchantRequest.setBankCode(null);
    addMerchantRequest.setBankName(merchant.getBankAccountName());
    addMerchantRequest.setBankCurrencyCode(merchant.getBankCurrencyCode());
    addMerchantRequest.setBankStatus(Constants.ZERO);
    addMerchantRequest.setBankNameOnAccount(merchant.getBankNameOnAccount());
    addMerchantRequest.setBankAddress1(merchant.getBankAddress1());
    addMerchantRequest.setBankAddress2(merchant.getBankAddress2());
    addMerchantRequest.setBankCity(merchant.getBankCity());
    addMerchantRequest.setBankState(merchant.getBankState());
    addMerchantRequest.setBankCountry(merchant.getBankCountry());
    addMerchantRequest.setBankPin(merchant.getBankPin());
    addMerchantRequest.setLocalCurrency(merchant.getCurrencyId());

    //it's a SubMerchant, So taking BankId of ParentMerchant
    Long parentMID = merchant.getParentMerchantId();
    PGMerchant pgMerchant = merchantProfileDao.getMerchantById(parentMID);
    addMerchantRequest.setBankId(pgMerchant.getBankId());

    AddMerchantResponse addMerchantResponse = merchantUpdateDao.addMerchant(addMerchantRequest);
    if (addMerchantResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00) && (0 == merchant.getStatus())) {
    	 merchantGetStatus(merchant, password);
    }
    logger.info("Exiting:: MerchantServiceImpl:: addMerchant method");
    return addMerchantResponse;
  }

  private void merchantGetStatus(Merchant merchant, String password)
      throws ChatakMerchantException {
    Map<String, String> map = new HashMap<>();
    map.put("firstName", merchant.getFirstName());
    map.put("userName", merchant.getUserName());
    map.put("tempPassword", password.trim());
    map.put("hLink", merchant.getMerchantLink());
    try {
      String body = iVelocityTemplateCreator.createEmailTemplate(map,
          Properties.getProperty("prepaid.email.template.file.path")
              + "/submerchant_create.vm");
      mailingServiceManagement.sendMailHtml(Properties.getProperty("prepaid.from.email.id"),
          body, merchant.getEmailId(),
          Properties.getProperty("prepaid.email.admin.submerchant.creation"));
    } catch (PrepaidNotificationException e) {
      logger.error("Error :: MerchantServiceImpl :: merchantGetStatus", e);
      throw new ChatakMerchantException(
          Properties.getProperty("chatak.admin.user.inactive.error.message"));
    } catch (Exception e) {
      logger.error("Error :: MerchantServiceImpl :: merchantGetStatus - Exception", e);
      throw new ChatakMerchantException(e.getMessage());
    }
  }

  private void autoTransferDay(Merchant merchant, AddMerchantRequest addMerchantRequest) {
    if (merchant.getAutoTransferDay().startsWith(Constants.DAILY)) {
      addMerchantRequest.setAutoTransferDay(merchant.getAutoTransferDay());
    } else if (merchant.getAutoTransferDay().startsWith(Constants.WEEKLY)) {
      addMerchantRequest.setAutoTransferDay(
          merchant.getAutoTransferDay() + ":" + merchant.getAutoTransferWeeklyDay());
    } else if (merchant.getAutoTransferDay().startsWith(Constants.MONTHLY)) {
      addMerchantRequest.setAutoTransferDay(
          merchant.getAutoTransferDay() + ":" + merchant.getAutoTransferMonthlyDay());
    }
  }

  @Override
  public MerchantSearchResponse searchMerchant(Merchant merchant) throws ChatakMerchantException {
    GetMerchantListRequest searchMerchant = new GetMerchantListRequest();
    GetMerchantListResponse getMerchantListResponse = null;
    searchMerchant.setBusinessName(merchant.getBusinessName());
    searchMerchant.setCity(merchant.getCity());
    searchMerchant.setCountry(merchant.getCountry());
    searchMerchant.setEmailId(merchant.getEmailId());
    searchMerchant.setFirstName(merchant.getFirstName());
    searchMerchant.setStatus(merchant.getStatus());
    searchMerchant.setId(merchant.getParentMerchantId());
    searchMerchant.setLastName(merchant.getLastName());
    searchMerchant.setPhone(merchant.getPhone());
    searchMerchant.setPageSize(merchant.getPageSize());
    searchMerchant.setPageIndex(merchant.getPageIndex());
    searchMerchant.setNoOfRecords(merchant.getNoOfRecords());
    searchMerchant.setMerchantCode(merchant.getMerchantCode());
    searchMerchant.setSubMerchantCode(merchant.getSubMerchantCode());
    if ("" != searchMerchant.getSubMerchantCode() && null != searchMerchant.getSubMerchantCode()) {
      getMerchantListResponse = subMerchantDao.getMerchantlistOnSubMerchantCode(searchMerchant);
    } else {
      getMerchantListResponse = merchantProfileDao.getMerchantlist(searchMerchant);
    }
    List<PGMerchant> pgMerchants = getMerchantListResponse.getMerchants();
    List<PGMerchant> subMerchants = getMerchantListResponse.getSubMerchants();
    MerchantSearchResponse response = new MerchantSearchResponse();
    if (!CollectionUtils.isEmpty(pgMerchants)) {
      pgMerchantStatus(pgMerchants, response);
    }
    if (!CollectionUtils.isEmpty(subMerchants)) {
      List<MerchantData> merchants = new ArrayList<>(subMerchants.size());
      MerchantData merchantRespObj = null;
      for (PGMerchant pgMerchant : subMerchants) {
        merchantRespObj = setMerchantData(pgMerchant);

        validateStatus(merchantRespObj, pgMerchant);
        
        merchantRespObj.setMerchantCode(pgMerchant.getMerchantCode());
        merchants.add(merchantRespObj);
      }
      response.setMerchants(merchants);
    }
    response.setTotalNoOfRows(getMerchantListResponse.getNoOfRecords());
    return response;
  }

  private MerchantData setMerchantData(PGMerchant pgMerchant) {
	MerchantData merchantRespObj;
	merchantRespObj = new MerchantData();
	merchantRespObj.setId(pgMerchant.getId());
	merchantRespObj.setBusinessName(pgMerchant.getBusinessName());
	merchantRespObj.setFirstName(pgMerchant.getFirstName());
	merchantRespObj.setLastName(pgMerchant.getLastName());
	merchantRespObj.setEmailId(pgMerchant.getEmailId());
	merchantRespObj.setPhone(pgMerchant.getPhone());
	merchantRespObj.setCity(pgMerchant.getCity());
	merchantRespObj.setCountry(pgMerchant.getCountry());
	return merchantRespObj;
  }

  private void validateStatus(MerchantData merchantRespObj, PGMerchant pgMerchant) {
	if (pgMerchant.getStatus() == STATUS_SUCCESS) {
	  merchantRespObj.setStatus(S_STATUS_ACTIVE);
	} else if (pgMerchant.getStatus() == STATUS_DELETED) {
	  merchantRespObj.setStatus(S_STATUS_DELETED);
	} else if (pgMerchant.getStatus() == STATUS_SUSPENDED) {
	  merchantRespObj.setStatus(S_STATUS_SUSPENDED);
	} else if (pgMerchant.getStatus() == STATUS_PENDING) {
		  merchantRespObj.setStatus(S_STATUS_PENDING);
	} else if (pgMerchant.getStatus() == STATUS_SELF_REGISTERATION_PENDING) {
	  merchantRespObj.setStatus(S_STATUS_SELFREGISTERED);
	}
  }

  private void pgMerchantStatus(List<PGMerchant> pgMerchants, MerchantSearchResponse response) {
    List<MerchantData> merchants = new ArrayList<>(pgMerchants.size());
    MerchantData merchantRespObj = null;
    for (PGMerchant pgMerchant : pgMerchants) {
      merchantRespObj = setMerchantData(pgMerchant);

      validateStatus(merchantRespObj, pgMerchant);
      merchantRespObj.setMerchantCode(pgMerchant.getMerchantCode());
      merchantRespObj.setLocalCurrency(pgMerchant.getLocalCurrency());
      merchants.add(merchantRespObj);
    }
    response.setMerchants(merchants);
  }

  @Override
  public Merchant getMerchant(Merchant merchant) throws ChatakMerchantException {

    PGMerchant pgMerchant = merchantProfileDao.getMerchantById(merchant.getId());
    PGAccount pgAccount= null;
    if (!StringUtil.isNull(pgMerchant)) {
    	 pgAccount = merchantProfileDao.getPgAccount(pgMerchant.getMerchantCode());
    }
    
    if (pgAccount != null) {
      return getMerchantExistingValues(merchant, pgMerchant, pgAccount);
    }
    logger.info("Exiting:: MerchantServiceImpl:: getMerchant method");
    return null;
  }

  private Merchant getMerchantExistingValues(Merchant merchant, PGMerchant pgMerchant,
      PGAccount pgAccount) {
    merchant.setAddress1(pgMerchant.getAddress1());
    merchant.setAddress2(pgMerchant.getAddress2());
    merchant.setCity(pgMerchant.getCity());
    merchant.setCountry(pgMerchant.getCountry());
    merchant.setId(pgMerchant.getId());
    merchant.setState(pgMerchant.getState());
    merchant.setAppMode(pgMerchant.getAppMode());
    merchant.setBusinessName(pgMerchant.getBusinessName());
    merchant.setBusinessURL(pgMerchant.getBusinessURL());
    merchant.setEmailId(pgMerchant.getEmailId());
    merchant.setEstimatedYearlySale(pgMerchant.getEstimatedYearlySale());
    merchant.setFax(pgMerchant.getFax());
    merchant.setFederalTaxId(pgMerchant.getFederalTaxId());
    merchant.setFirstName(pgMerchant.getFirstName());
    merchant.setLastName(pgMerchant.getLastName());
    merchant.setNoOfEmployee(pgMerchant.getNoOfEmployee());
    merchant.setOwnership(pgMerchant.getOwnership());
    merchant.setLocalCurrency(pgMerchant.getLocalCurrency());
    merchant.setCurrencyId(pgMerchant.getLocalCurrency());
    merchant.setBusinessType(pgMerchant.getBusinessType());
    merchant.setLookingFor(pgMerchant.getLookingFor());

    merchant.setPhone(pgMerchant.getPhone());
    merchant.setPin(pgMerchant.getPin());
    merchant.setRole(pgMerchant.getRole());
    merchant.setSalesTaxId(pgMerchant.getSalesTaxId());
    merchant.setStateTaxId(pgMerchant.getStateTaxId());
    merchant.setStatus(pgMerchant.getStatus());
    merchant.setTimeZone(pgMerchant.getTimeZone());
    merchant.setUpdatedDate(DateUtils.timestampToString(pgMerchant.getUpdatedDate()));

    merchant.setBusinessStartDate(DateUtils.timestampToString(pgMerchant.getBusinessStartDate()));
    merchant.setMerchantCallBackURL(pgMerchant.getMerchantCallBack());
    merchant.setFeeProgram(pgMerchant.getMerchantConfig().getFeeProgram());
    merchant.setProcessor(pgMerchant.getMerchantConfig().getProcessor());
    merchant.setRefunds(pgMerchant.getMerchantConfig().getRefunds() == 1 ? true : false);
    merchant.setShippingAmount(
        pgMerchant.getMerchantConfig().getShippingAmount() == 1 ? true : false);
    merchant.setTaxAmount(pgMerchant.getMerchantConfig().getTaxAmount() == 1 ? true : false);
    merchant.setTipAmount(pgMerchant.getMerchantConfig().getTipAmount() == 1 ? true : false);
    merchant.setUserName(pgMerchant.getUserName());
    merchant.setMerchantCode(pgMerchant.getMerchantCode());

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
    //END

    if (ProcessorType.LITLE.name()
        .equalsIgnoreCase(pgMerchant.getMerchantConfig().getProcessor())) {
      merchant.setLitleMID(pgMerchant.getLitleMID());
    }
    merchant.setAutoPaymentMethod(pgAccount.getAutoPaymentMethod());
    if (null != pgAccount.getAutoTransferDay()) {
      getMerchantAutoTransferDay(merchant, pgAccount);
    }
    if (null != pgAccount.getAutoPaymentLimit()) {
      merchant.setAutoTransferLimit(pgAccount.getAutoPaymentLimit());
    }
    merchant.setCategory(pgAccount.getCategory());
    merchant.setMerchantCallBackURL(pgMerchant.getMerchantCallBack());
    merchant.setMerchantType(pgMerchant.getMerchantType());
    merchant.setAccountId(pgAccount.getAccountNum());

    PGMerchantBank merchantBank = pgAccount.getPgMerchantBank();

    merchant.setBankAccountName(merchantBank.getBankName());
    merchant.setBankAccountNumber(merchantBank.getBankAccNum());
    merchant.setBankAccountType(merchantBank.getAccountType());
    merchant.setBankAddress1(merchantBank.getAddress1());
    merchant.setBankAddress2(merchantBank.getAddress2());
    merchant.setBankCity(merchantBank.getCity());
    merchant.setBankCountry(merchantBank.getCountry());
    merchant.setBankNameOnAccount(merchantBank.getNameOnAccount());
    merchant.setBankPin(merchantBank.getPin());
    merchant.setBankRoutingNumber(merchantBank.getRoutingNumber());
    merchant.setBankState(merchantBank.getState());
    merchant.setBankCurrencyCode(merchantBank.getCurrencyCode());
    logger.info("Exiting:: MerchantServiceImpl:: getMerchant method");
    return merchant;
  }

  private void getMerchantAutoTransferDay(Merchant merchant, PGAccount pgAccount) {
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

  @Override
  public UpdateMerchantResponse updateMerchant(Merchant merchant) throws ChatakMerchantException {
    UpdateMerchantRequest updateMerchantRequest = new UpdateMerchantRequest();

    updateMerchantRequest.setAddress1(merchant.getAddress1());
    updateMerchantRequest.setMerchantCode(merchant.getMerchantCode());
    updateMerchantRequest.setAddress2(merchant.getAddress2());
    updateMerchantRequest.setBusinessName(merchant.getBusinessName());
    updateMerchantRequest.setBusinessURL(merchant.getBusinessURL());
    updateMerchantRequest.setCity(merchant.getCity());
    updateMerchantRequest.setCountry(merchant.getCountry());
    updateMerchantRequest.setEmailId(merchant.getEmailId());
    updateMerchantRequest.setEstimatedYearlySale(merchant.getEstimatedYearlySale());
    updateMerchantRequest.setFax(merchant.getFax());
    updateMerchantRequest.setFederalTaxId(merchant.getFederalTaxId());
    updateMerchantRequest.setFeeProgram(merchant.getFeeProgram());
    updateMerchantRequest.setFirstName(merchant.getFirstName());
    updateMerchantRequest.setLastName(merchant.getLastName());
    updateMerchantRequest.setNoOfEmployee(merchant.getNoOfEmployee());
    updateMerchantRequest.setOwnership(merchant.getOwnership());
    updateMerchantRequest.setPhone(merchant.getPhone());
    updateMerchantRequest.setPin(merchant.getPin());
    updateMerchantRequest.setState(merchant.getState());
    updateMerchantRequest.setUserName(merchant.getUserName());
    updateMerchantRequest.setTimeZone(merchant.getTimeZone());
    updateMerchantRequest.setStatus(merchant.getStatus());
    updateMerchantRequest.setStateTaxId(merchant.getStateTaxId());
    updateMerchantRequest.setAppMode(merchant.getAppMode());
    updateMerchantRequest.setRole(merchant.getRole());
    updateMerchantRequest.setSalesTaxId(merchant.getSalesTaxId());

    //NEW ADDED
    updateMerchantRequest.setVirtualTerminal(merchant.getVirtualTerminal());
    updateMerchantRequest.setPosTerminal(merchant.getPosTerminal());
    updateMerchantRequest.setOnline(merchant.getOnline());
    updateMerchantRequest.setWebSiteAddress(merchant.getWebSiteAddress());
    updateMerchantRequest.setReturnUrl(merchant.getReturnUrl());
    updateMerchantRequest.setCancelUrl(merchant.getCancelUrl());
    //END

    updateMerchantRequest.setProcessor(merchant.getProcessor());
    updateMerchantRequest.setRefunds(merchant.getRefunds());
    updateMerchantRequest.setTipAmount(merchant.getTipAmount());
    updateMerchantRequest.setTaxAmount(merchant.getTaxAmount());
    updateMerchantRequest.setAllowRepeatSale(merchant.getAllowRepeatSale());
    updateMerchantRequest.setAutoSettlement(merchant.getAutoSettlement());
    updateMerchantRequest.setShowRecurringBilling(merchant.getShowRecurringBilling());
    updateMerchantRequest.setShippingAmount(merchant.getShippingAmount());
    updateMerchantRequest.setAutoPaymentMethod(merchant.getAutoPaymentMethod());
    updateMerchantRequest.setBusinessType(merchant.getBusinessType());
    updateMerchantRequest.setLookingFor(merchant.getLookingFor());
    updateMerchantRequest
        .setIssuancePartnerId(StringUtil.isNullAndEmpty(merchant.getIssuancePartnerId()) ? null
            : merchant.getIssuancePartnerId());
    updateMerchantRequest.setAgentId(
        StringUtil.isNullAndEmpty(merchant.getAgentId()) ? null : merchant.getAgentId());
    updateMerchantRequest
        .setProgramManagerId(StringUtil.isNullAndEmpty(merchant.getProgramManagerId()) ? null
            : merchant.getProgramManagerId());
    /* Assigning auto transfer day with ',' separated values */
    if (null != merchant.getAutoTransferDay()) {
      if (merchant.getAutoTransferDay().startsWith(Constants.DAILY)) {
        updateMerchantRequest.setAutoTransferDay(merchant.getAutoTransferDay());
      } else if (merchant.getAutoTransferDay().startsWith(Constants.WEEKLY)) {
        updateMerchantRequest.setAutoTransferDay(
            merchant.getAutoTransferDay() + ":" + merchant.getAutoTransferWeeklyDay());
      } else if (merchant.getAutoTransferDay().startsWith(Constants.MONTHLY)) {
        updateMerchantRequest.setAutoTransferDay(
            merchant.getAutoTransferDay() + ":" + merchant.getAutoTransferMonthlyDay());
      }
    } else {
      updateMerchantRequest.setAutoTransferDay(merchant.getAutoTransferDay());
    }

    if (ProcessorType.LITLE.name().equals(merchant.getProcessor())) {
      updateMerchantRequest.setLitleMID(merchant.getLitleMID());
    }
    if (null != merchant.getAutoTransferLimit()) {
      updateMerchantRequest.setAutoTransferLimit(merchant.getAutoTransferLimit());
    }
    updateMerchantRequest.setCategory(merchant.getCategory());
    updateMerchantRequest.setMerchantCallBackURL(merchant.getMerchantCallBackURL());

    updateMerchantRequest.setBankAccountType(merchant.getBankAccountType());
    updateMerchantRequest.setBankAccountNumber(merchant.getBankAccountNumber());
    updateMerchantRequest.setBankRoutingNumber(merchant.getBankRoutingNumber());
    updateMerchantRequest.setBankCode(null);
    updateMerchantRequest.setBankName(merchant.getBankAccountName());
    updateMerchantRequest.setBankNameOnAccount(merchant.getBankNameOnAccount());
    updateMerchantRequest.setBankAddress1(merchant.getBankAddress1());
    updateMerchantRequest.setBankAddress2(merchant.getBankAddress2());
    updateMerchantRequest.setBankCity(merchant.getBankCity());
    updateMerchantRequest.setBankState(merchant.getBankState());
    updateMerchantRequest.setBankCountry(merchant.getBankCountry());
    updateMerchantRequest.setBankPin(merchant.getBankPin());
    updateMerchantRequest.setLocalCurrency(merchant.getCurrencyId());
    updateMerchantRequest.setMerchantType(merchant.getMerchantType());
    //it's a SubMerchant, So taking BankId of ParentMerchant
    String parentMID = merchantDao.getParentMerchantCode(merchant.getMerchantCode());
    PGMerchant pgMerchant = merchantUpdateDao.getMerchantByCode(parentMID);
    updateMerchantRequest.setBankId(pgMerchant.getBankId());
    return merchantUpdateDao.updateMerchant(updateMerchantRequest);
  }
}
