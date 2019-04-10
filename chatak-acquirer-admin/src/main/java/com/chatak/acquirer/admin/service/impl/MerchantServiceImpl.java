package com.chatak.acquirer.admin.service.impl;

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

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.MerchantService;
import com.chatak.acquirer.admin.util.EncryptionUtil;
import com.chatak.acquirer.admin.util.PasswordHandler;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.mailsender.exception.PrepaidNotificationException;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.pg.acq.dao.CurrencyDao;
import com.chatak.pg.acq.dao.LegalEntityDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.PartnerDao;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.SubMerchantDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantCurrencyMapping;
import com.chatak.pg.acq.dao.model.ProgramManager;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ProcessorType;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.MerchantCurrencyMapping;
import com.chatak.pg.user.bean.AddMerchantRequest;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.UpdateMerchantRequest;
import com.chatak.pg.user.bean.UpdateMerchantResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.Properties;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 03-Jan-2015 4:28:42 PM
 * @version 1.0
 */
/**
 * @author sharath
 */
@Service
public class MerchantServiceImpl implements MerchantService, PGConstants {

  private static Logger logger = Logger.getLogger(MerchantServiceImpl.class);

  @Autowired
  private CurrencyDao currencyDao;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private MailServiceManagement mailingServiceManagement;

  @Autowired
  private IVelocityTemplateCreator iVelocityTemplateCreator;

  @Autowired
  private LegalEntityDao legalEntityDao;

  @Autowired
  private MerchantProfileDao merchantProfileDao;

  @Autowired
  private MerchantUpdateDao merchantUpdateDao;
  
  @Autowired
  SubMerchantDao subMerchantDao;

  @Autowired
  MerchantDao merchantDao;
  
  @Autowired
  PartnerDao partnerDao;
  
  @Autowired
  ProgramManagerDao programManagerDao;

  @Override
  public AddMerchantResponse addMerchant(Merchant merchant, String userid)
      throws ChatakAdminException {
    logger.info("Entering:: MerchantServiceImpl:: addMerchant method");

    AddMerchantRequest addMerchantRequest = new AddMerchantRequest();
    addMerchantRequest.setAddress1(merchant.getAddress1());
    addMerchantRequest.setAddress2(merchant.getAddress2());
    addMerchantRequest.setAllowRepeatSale(merchant.getAllowRepeatSale());
    addMerchantRequest.setAppMode(merchant.getAppMode());
    addMerchantRequest.setBusinessName(merchant.getBusinessName());
    addMerchantRequest.setBusinessStartDate(merchant.getBusinessStartDate());
    addMerchantRequest.setCity(merchant.getCity());
    addMerchantRequest.setCountry(merchant.getCountry());
    addMerchantRequest.setBusinessURL(merchant.getBusinessURL());
    addMerchantRequest.setEmailId(merchant.getEmailId());
    addMerchantRequest.setEstimatedYearlySale(merchant.getEstimatedYearlySale());
    addMerchantRequest.setFederalTaxId(merchant.getFederalTaxId());
    addMerchantRequest.setFeeProgram(merchant.getFeeProgram());
    addMerchantRequest.setFax(merchant.getFax());
    addMerchantRequest.setFirstName(merchant.getFirstName());
    addMerchantRequest.setLastName(merchant.getLastName());
    addMerchantRequest.setOwnership(merchant.getOwnership());
    addMerchantRequest.setPhone(merchant.getPhone());
    addMerchantRequest.setNoOfEmployee(merchant.getNoOfEmployee());
    addMerchantRequest.setPin(merchant.getPin());
    addMerchantRequest.setProcessor(merchant.getProcessor());
    addMerchantRequest.setUserName(merchant.getUserName());
    addMerchantRequest.setTipAmount(merchant.getTipAmount());
    addMerchantRequest.setRefunds(merchant.getRefunds());
    addMerchantRequest.setTimeZone(merchant.getTimeZone());
    addMerchantRequest.setTaxAmount(merchant.getTaxAmount());
    addMerchantRequest.setStateTaxId(merchant.getStateTaxId());
    addMerchantRequest.setState(merchant.getState());
    addMerchantRequest.setStatus(merchant.getStatus());
    addMerchantRequest.setShowRecurringBilling(merchant.getShowRecurringBilling());
    addMerchantRequest.setShippingAmount(merchant.getShippingAmount());
    addMerchantRequest.setRole(merchant.getRole());
    addMerchantRequest.setAutoSettlement(merchant.getAutoSettlement());
    addMerchantRequest.setSalesTaxId(merchant.getSalesTaxId());
    addMerchantRequest.setMerchantCallBackURL(merchant.getMerchantCallBackURL());
    addMerchantRequest.setCategory(merchant.getCategory());
    addMerchantRequest.setVirtualTerminal(merchant.getVirtualTerminal());
    addMerchantRequest.setBusinessType(merchant.getBusinessType());
    addMerchantRequest.setLookinFor(merchant.getLookingFor());
    addMerchantRequest.setAutoPaymentMethod(merchant.getAutoPaymentMethod());
    addMerchantRequest.setOnline(merchant.getOnline());
    addMerchantRequest.setPosTerminal(merchant.getPosTerminal());
    addMerchantRequest.setWebSiteAddress(merchant.getWebSiteAddress());
    addMerchantRequest.setReturnUrl(merchant.getReturnUrl());
    addMerchantRequest.setCancelUrl(merchant.getCancelUrl());
    addMerchantRequest.setPayPageConfig(merchant.getPayPageConfig());//Pay page configuration for merchant
    addMerchantRequest.setMerchantCategory(merchant.getMerchantCategory());
    addMerchantRequest.setPayOutAt(merchant.getPayOutAt());
    addMerchantRequest.setAllowAdvancedFraudFilter(merchant.getAllowAdvancedFraudFilter());
    
    getMerchantBankId(merchant, addMerchantRequest);
    
    addMerchantRequest.setMcc(merchant.getMcc());
    addMerchantRequest.setDccEnable(merchant.getDccEnable());
    addMerchantRequest.setLocalCurrency(merchant.getLocalCurrency());

    getAutoTransferDetails(merchant, addMerchantRequest);

    if (ProcessorType.LITLE.name().equals(merchant.getProcessor())) {
      addMerchantRequest.setLitleMID(merchant.getLitleMID());
    }
    if (null != merchant.getAutoTransferLimit()) {
      addMerchantRequest.setAutoTransferLimit(merchant.getAutoTransferLimit());
    }
    addMerchantRequest.setMerchantType(merchant.getMerchantType());
    addMerchantRequest.setParentMerchantId(merchant.getParentMerchantId());
    addMerchantRequest.setCreatedBy(userid);

    String password = PasswordHandler.getSystemGeneratedPassword(Constants.EIGHT);
    try {
      addMerchantRequest.setPassword(EncryptionUtil.encodePassword(password));
    } catch (Exception e1) {
      logger.error("Error :: MerchantServiceImpl :: addMerchant Exception while encodingPassword", e1);
      throw new ChatakAdminException(e1.getMessage());
    }

    addMerchantRequest.setBankAccountType(merchant.getBankAccountType());
    addMerchantRequest.setBankAccountNumber(merchant.getBankAccountNumber());
    addMerchantRequest.setBankRoutingNumber(merchant.getBankRoutingNumber());
    addMerchantRequest.setBankCode(null);
    addMerchantRequest.setBankName(merchant.getBankAccountName());
    addMerchantRequest.setBankCurrencyCode(merchant.getBankCurrencyCode());
    addMerchantRequest.setBankStatus(Constants.ZERO);
    addMerchantRequest.setCategory(
        (null == merchant.getCategory()) ? PGConstants.PRIMARY_ACCOUNT : merchant.getCategory());
    addMerchantRequest.setBankNameOnAccount(merchant.getBankNameOnAccount());
    addMerchantRequest.setBankAddress1(merchant.getBankAddress1());
    addMerchantRequest.setBankAddress2(merchant.getBankAddress2());
    addMerchantRequest.setBankCity(merchant.getBankCity());
    addMerchantRequest.setBankState(merchant.getBankState());
    addMerchantRequest.setBankCountry(merchant.getBankCountry());
    addMerchantRequest.setBankPin(merchant.getBankPin());
    addMerchantRequest.setAssociatedTo(merchant.getAssociatedTo());
    addMerchantRequest.setCardProgramIds(merchant.getCardProgramIds());
    addMerchantRequest.setEntitiesId(merchant.getEntitiesId());
    addMerchantRequest.setCardProgramAndEntityId(merchant.getCardProgramAndEntityId());
    AddMerchantResponse addMerchantResponse = merchantUpdateDao.addMerchant(addMerchantRequest);
    try {
      saveCurrencyMap(addMerchantResponse.getMerchantCode(), merchant,
          merchant.getSelectedCurrencyMapping());
    } catch (DataAccessException e1) {
      logger.error("Error :: MerchantServiceImpl :: addMerchant Method", e1);
    } catch (Exception e1) {
      logger.error("Error :: MerchantServiceImpl :: addMerchant Method Exception", e1);
    }

    // Send email in case the sub merchant has been created in active state, in case it is in pending state,
    // email will be sent when it is approved by the 'checker'.
    if (addMerchantResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)
        && (merchant.getMerchantType() != null
            && merchant.getMerchantType().equalsIgnoreCase(PGConstants.SUB_MERCHANT))
        && (merchant.getStatus() != null && 0 == merchant.getStatus())) {
      Map<String, String> map = new HashMap<>();
      map.put("firstName", merchant.getFirstName());
      map.put("userName", merchant.getUserName());
      map.put("tempPassword", password);
      map.put("hLink", merchant.getMerchantLink());
      try {
        String body = iVelocityTemplateCreator.createEmailTemplate(map,
            Properties.getProperty(Constants.PREPAID_EMAIL_TEMPLATE_FILE_PATH) + "/submerchant_create.vm");
        mailingServiceManagement.sendMailHtml(Properties.getProperty(Constants.PREPAID_FROM_EMAIL_ID), body,
            merchant.getEmailId(), messageSource.getMessage(
                "chatak.admin.email.submerchant.creation", null, LocaleContextHolder.getLocale()));
      } catch (PrepaidNotificationException e) {
        logger.error("Error:: MerchantServiceImpl:: addMerchant method sendMailHtml", e);
        throw new ChatakAdminException(
            Properties.getProperty("chatak.admin.user.inactive.error.message"));
      } catch (Exception e) {
        logger.error("Error:: MerchantServiceImpl:: addMerchant method createEmailTemplate", e);
        throw new ChatakAdminException(e.getMessage());
      }
    }
    logger.info("Exiting:: MerchantServiceImpl:: addMerchant method");
    return addMerchantResponse;
  }

  private AddMerchantRequest getAutoTransferDetails(Merchant merchant,
      AddMerchantRequest addMerchantRequest) {
    /* Assigning auto transfer day with ',' separated values */
    if (!StringUtil.isNullAndEmpty(merchant.getAutoTransferDay())) {
      if (merchant.getAutoTransferDay().startsWith(Constants.DAILY)) {
        addMerchantRequest.setAutoTransferDay(merchant.getAutoTransferDay());
      } else if (merchant.getAutoTransferDay().startsWith(Constants.WEEKLY)) {
        addMerchantRequest.setAutoTransferDay(
            merchant.getAutoTransferDay() + ":" + merchant.getAutoTransferWeeklyDay());
      } else if (merchant.getAutoTransferDay().startsWith(Constants.MONTHLY)) {
        addMerchantRequest.setAutoTransferDay(
            merchant.getAutoTransferDay() + ":" + merchant.getAutoTransferMonthlyDay());
      }
    } else {
      addMerchantRequest.setAutoTransferDay(merchant.getAutoTransferDay());
    }
    return addMerchantRequest;
  }

  private AddMerchantRequest getMerchantBankId(Merchant merchant,
      AddMerchantRequest addMerchantRequest) {
    if (merchant.getParentMerchantId() != null) {
      //it will be a SubMerchant, So taking BankId of ParentMerchant
      PGMerchant pgMerchant = merchantProfileDao.getMerchantById(merchant.getParentMerchantId());
      addMerchantRequest.setBankId(pgMerchant.getBankId());
    } else {
      //it's a Merchant
      addMerchantRequest.setBankId(merchant.getBankId());
    }
    return addMerchantRequest;
  }

  @Override
  public UpdateMerchantResponse updateMerchant(Merchant merchant) throws ChatakAdminException {
    logger.info("Entering:: MerchantServiceImpl:: updateMerchant method");
    UpdateMerchantRequest updateMerchantRequest = new UpdateMerchantRequest();
    updateMerchantRequest.setAddress1(merchant.getAddress1());
    updateMerchantRequest.setAddress2(merchant.getAddress2());
    updateMerchantRequest.setBusinessName(merchant.getBusinessName());
    updateMerchantRequest.setMerchantCode(merchant.getMerchantCode());
    updateMerchantRequest.setBusinessURL(merchant.getBusinessURL());
    updateMerchantRequest.setCity(merchant.getCity());
    updateMerchantRequest.setEmailId(merchant.getEmailId());
    updateMerchantRequest.setEstimatedYearlySale(merchant.getEstimatedYearlySale());
    updateMerchantRequest.setCountry(merchant.getCountry());
    updateMerchantRequest.setFax(merchant.getFax());
    updateMerchantRequest.setFederalTaxId(merchant.getFederalTaxId());
    updateMerchantRequest.setFirstName(merchant.getFirstName());
    updateMerchantRequest.setLastName(merchant.getLastName());
    updateMerchantRequest.setFeeProgram(merchant.getFeeProgram());
    updateMerchantRequest.setNoOfEmployee(merchant.getNoOfEmployee());
    updateMerchantRequest.setOwnership(merchant.getOwnership());
    updateMerchantRequest.setPin(merchant.getPin());
    updateMerchantRequest.setState(merchant.getState());
    updateMerchantRequest.setPhone(merchant.getPhone());
    updateMerchantRequest.setUserName(merchant.getUserName());
    updateMerchantRequest.setTimeZone(merchant.getTimeZone());
    updateMerchantRequest.setStatus(merchant.getStatus());
    updateMerchantRequest.setStateTaxId(merchant.getStateTaxId());
    updateMerchantRequest.setAppMode(merchant.getAppMode());
    updateMerchantRequest.setRole(merchant.getRole());
    updateMerchantRequest.setDccEnable(merchant.getDccEnable());
    updateMerchantRequest.setSalesTaxId(merchant.getSalesTaxId());
    updateMerchantRequest.setAllowAdvancedFraudFilter(merchant.getAllowAdvancedFraudFilter());
    updateMerchantRequest.setLocalCurrency(merchant.getLocalCurrency());
    //NEW ADDED
    updateMerchantRequest.setVirtualTerminal(merchant.getVirtualTerminal());
    updateMerchantRequest.setPosTerminal(merchant.getPosTerminal());
    updateMerchantRequest.setOnline(merchant.getOnline());
    updateMerchantRequest.setWebSiteAddress(merchant.getWebSiteAddress());
    updateMerchantRequest.setReturnUrl(merchant.getReturnUrl());
    updateMerchantRequest.setCancelUrl(merchant.getCancelUrl());
    updateMerchantRequest.setPayPageConfig(merchant.getPayPageConfig());
    updateMerchantRequest.setMerchantCategory(merchant.getMerchantCategory());
    updateMerchantRequest.setPayOutAt(merchant.getPayOutAt());
    //END
    getBankIdForMerchantUpdate(merchant, updateMerchantRequest);
    updateMerchantRequest.setResellerId(merchant.getResellerId());
    updateMerchantRequest.setProcessor(merchant.getProcessor());
    updateMerchantRequest.setRefunds(merchant.getRefunds());
    updateMerchantRequest.setTaxAmount(merchant.getTaxAmount());
    updateMerchantRequest.setAllowRepeatSale(merchant.getAllowRepeatSale());
    updateMerchantRequest.setTipAmount(merchant.getTipAmount());
    updateMerchantRequest.setAutoSettlement(merchant.getAutoSettlement());
    updateMerchantRequest.setShowRecurringBilling(merchant.getShowRecurringBilling());
    updateMerchantRequest.setDeclineReason(merchant.getDeclineReason());
    updateMerchantRequest.setAutoPaymentMethod(merchant.getAutoPaymentMethod());
    updateMerchantRequest.setBusinessType(merchant.getBusinessType());
    updateMerchantRequest.setShippingAmount(merchant.getShippingAmount());
    updateMerchantRequest.setLookingFor(merchant.getLookingFor());
    updateMerchantRequest.setMcc(merchant.getMcc());
    if (ProcessorType.LITLE.name().equals(merchant.getProcessor())) {
      updateMerchantRequest.setLitleMID(merchant.getLitleMID());
    }
    getAutoTransaferDetailsForUpdate(merchant, updateMerchantRequest);
    updateMerchantRequest.setCategory(
        (null == merchant.getCategory()) ? PGConstants.PRIMARY_ACCOUNT : merchant.getCategory());
    updateMerchantRequest.setMerchantCallBackURL(merchant.getMerchantCallBackURL());
    String password = PasswordHandler.getSystemGeneratedPassword(Constants.EIGHT);
    setMerchantUserPassword(updateMerchantRequest, password);
    updateMerchantRequest.setBankAccountType(merchant.getBankAccountType());
    updateMerchantRequest.setBankAccountNumber(merchant.getBankAccountNumber());
    updateMerchantRequest.setBankCode(null);
    updateMerchantRequest.setBankName(merchant.getBankAccountName());
    updateMerchantRequest.setBankRoutingNumber(merchant.getBankRoutingNumber());
    updateMerchantRequest.setBankNameOnAccount(merchant.getBankNameOnAccount());
    updateMerchantRequest.setBankAddress1(merchant.getBankAddress1());
    updateMerchantRequest.setBankPin(merchant.getBankPin());
    updateMerchantRequest.setBankCity(merchant.getBankCity());
    updateMerchantRequest.setBankState(merchant.getBankState());
    updateMerchantRequest.setBankAddress2(merchant.getBankAddress2());
    updateMerchantRequest.setBankCountry(merchant.getBankCountry());
    updateMerchantRequest.setBankCurrencyCode(merchant.getBankCurrencyCode());
    updateMerchantRequest.setEntitiesId(merchant.getEntitiesId());
    updateMerchantRequest.setCardProgramIds(merchant.getCardProgramIds());
    updateMerchantRequest.setAssociatedTo(merchant.getAssociatedTo());
    updateMerchantRequest.setId(merchant.getId());
    updateMerchantRequest.setMerchantType(merchant.getMerchantType());
    updateMerchantRequest.setProcess(merchant.getProcess());
    updateMerchantRequest.setCardProgramAndEntityId(merchant.getCardProgramAndEntityId());
    UpdateMerchantResponse updateMerchantResponse =
        merchantUpdateDao.updateMerchant(updateMerchantRequest);
    
    updatePGMerchantCurrencyMapping(merchant);
    
    if (updateMerchantResponse.isUpdated()) {
      mailNotificationForMerchantDeclined(updateMerchantRequest, updateMerchantResponse);
      
      mailNotificationForMerchantActivation(merchant, updateMerchantRequest);
      
      if (null != updateMerchantResponse.getPassword()) {
        Map<String, String> map = new HashMap<>();
        map.put("firstName", updateMerchantRequest.getFirstName());
        map.put("userName", updateMerchantRequest.getUserName());
        map.put("tempPassword", password);
        map.put("hLink", merchant.getMerchantLink());
        try {
          String body = iVelocityTemplateCreator.createEmailTemplate(map,
              Properties.getProperty(Constants.PREPAID_EMAIL_TEMPLATE_FILE_PATH) + "/merchant_create.vm");
          String subject = messageSource.getMessage("chatak.admin.email.admin.merchant.creation",
              null, LocaleContextHolder.getLocale());
          
          String vmSourceFile = "/submerchant_create.vm";
          String messageKey = "chatak.admin.email.submerchant.creation";
          
          body = getMailBodyOnMerchantType(merchant, map, body, vmSourceFile);
          subject = getMailSubjectOnMerchantType(merchant, subject, messageKey);

          mailingServiceManagement.sendMailHtml(Properties.getProperty(Constants.PREPAID_FROM_EMAIL_ID),
              body, updateMerchantRequest.getEmailId(), subject);
        } catch (PrepaidNotificationException e) {
          logger.error("Error :: MerchantServiceImpl:: updateMerchant Mail sending", e);
          throw new ChatakAdminException(
              Properties.getProperty("chatak.admin.user.inactive.error.message"));
        } catch (Exception e) {
          logger.error("Error :: MerchantServiceImpl:: updateMerchant Mail sending Exception", e);
          throw new ChatakAdminException(e.getMessage());
        }
      }
    }
    logger.info("Exiting:: MerchantServiceImpl:: updateMerchant method");
    return updateMerchantResponse;
  }

  private void mailNotificationForMerchantActivation(Merchant merchant,
      UpdateMerchantRequest updateMerchantRequest) throws ChatakAdminException {
    if (PGConstants.STATUS_ACTIVE.equals(merchant.getStatus())
        && PGConstants.STATUS_INACTIVE.equals(merchant.getSessionStatus())) {
      Map<String, String> map = new HashMap<>();
      map.put("firstName", updateMerchantRequest.getFirstName());
      try {
        String body = iVelocityTemplateCreator.createEmailTemplate(map,
            Properties.getProperty(Constants.PREPAID_EMAIL_TEMPLATE_FILE_PATH)
                + "/merchant_change_status.vm");
        String subject = messageSource.getMessage("chatak.email.admin.merchant.activation", null,
            LocaleContextHolder.getLocale());
        
        String messageKey = "chatak.email.admin.submerchant.activation";
        String vmSourceFile = "/merchant_change_status.vm";
        body = getMailBodyOnMerchantType(merchant, map, body, vmSourceFile);
        subject = getMailSubjectOnMerchantType(merchant, subject, messageKey);

        mailingServiceManagement.sendMailHtml(Properties.getProperty(Constants.PREPAID_FROM_EMAIL_ID),
            body, updateMerchantRequest.getEmailId(), subject);
      } catch (PrepaidNotificationException exp) {
        logger.error("Error :: MerchantServiceImpl:: updateMerchant", exp);
        throw new ChatakAdminException(
            Properties.getProperty("chatak.admin.user.inactive.error.message"));
      } catch (Exception e) {
        logger.error("Error :: MerchantServiceImpl:: updateMerchant Exception", e);
        throw new ChatakAdminException(e.getMessage());
      }
    }
  }

  private void mailNotificationForMerchantDeclined(UpdateMerchantRequest updateMerchantRequest,
      UpdateMerchantResponse updateMerchantResponse) throws ChatakAdminException {
    if (updateMerchantResponse.isDeclined()) {
      Map<String, String> map = new HashMap<>();
      map.put("firstName", updateMerchantRequest.getFirstName());
      map.put("declineReason", updateMerchantRequest.getDeclineReason());
      try {
        String body = iVelocityTemplateCreator.createEmailTemplate(map,
            Properties.getProperty(Constants.PREPAID_EMAIL_TEMPLATE_FILE_PATH) + "/merchant_decline.vm");
        mailingServiceManagement.sendMailHtml(Properties.getProperty(Constants.PREPAID_FROM_EMAIL_ID),
            body, updateMerchantRequest.getEmailId(),
            Properties.getProperty("prepaid.email.admin.merchant.declined"));
      } catch (PrepaidNotificationException e) {
        logger.error("Error :: MerchantServiceImpl:: updateMerchant sendMailHtml", e);
        throw new ChatakAdminException(
            Properties.getProperty("chatak.admin.user.inactive.error.message"));
      } catch (Exception e) {
        logger.error("Error :: MerchantServiceImpl:: updateMerchant createEmailTemplate", e);
        throw new ChatakAdminException(e.getMessage());
      }
    }
  }

  private void updatePGMerchantCurrencyMapping(Merchant merchant) {
    try {
      saveCurrencyMap(merchant.getMerchantCode(), merchant, merchant.getSelectedCurrencyMapping());
    } catch (DataAccessException e1) {
      logger.error("Error :: MerchantServiceImpl :: updateMerchant Method", e1);
    } catch (Exception e1) {
      logger.error("Error :: MerchantServiceImpl :: updateMerchant Method Exception", e1);
    }
  }

  private UpdateMerchantRequest setMerchantUserPassword(UpdateMerchantRequest updateMerchantRequest, String password)
      throws ChatakAdminException {
    try {
      updateMerchantRequest.setUserPassword(EncryptionUtil.encodePassword(password));
    } catch (Exception e1) {
      logger.error("Error :: MerchantServiceImpl:: updateMerchant encodePassword", e1);
      throw new ChatakAdminException(e1.getMessage());
    }
    return updateMerchantRequest;
  }

  private UpdateMerchantRequest getAutoTransaferDetailsForUpdate(Merchant merchant,
      UpdateMerchantRequest updateMerchantRequest) {
    /* Assigning auto transfer day with ',' separated values */
    if (!StringUtil.isNullAndEmpty(merchant.getAutoTransferDay())) {
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

    if (null != merchant.getAutoTransferLimit()) {
      updateMerchantRequest.setAutoTransferLimit(merchant.getAutoTransferLimit());
    }
    return updateMerchantRequest;
  }

  private UpdateMerchantRequest getBankIdForMerchantUpdate(Merchant merchant,
      UpdateMerchantRequest updateMerchantRequest) {
    if (merchant.getParentMerchantId() != null) {
      //it will be SubMerchant, So taking BankId of ParentMerchant
      String parentMID = String.valueOf(merchant.getParentMerchantId());
      PGMerchant pgMerchant = merchantUpdateDao.getMerchantByCode(parentMID);
      updateMerchantRequest.setBankId(pgMerchant.getBankId());
    } else {
      //it's a Merchant
      updateMerchantRequest.setBankId(merchant.getBankId());
    }
    if (merchant.getBankId() == null) {
      PGMerchant pgMerchant = merchantUpdateDao.getMerchant(merchant.getMerchantCode());
      updateMerchantRequest.setBankId(pgMerchant.getBankId());
    }
    return updateMerchantRequest;
  }

  private String getMailSubjectOnMerchantType(Merchant merchant, String subject, String messageKey) {
    if (merchant.getMerchantType().equalsIgnoreCase(PGConstants.SUB_MERCHANT)) {
      subject = messageSource.getMessage(messageKey, null,
          LocaleContextHolder.getLocale());
    }
    return subject;
  }

  private String getMailBodyOnMerchantType(Merchant merchant, Map<String, String> map, String body, String fileName) {
    if (merchant.getMerchantType().equalsIgnoreCase(PGConstants.SUB_MERCHANT)) {
      body = iVelocityTemplateCreator.createEmailTemplate(map,
          Properties.getProperty(Constants.PREPAID_EMAIL_TEMPLATE_FILE_PATH)
              + fileName);
    }
    return body;
  }

  private void saveCurrencyMap(String merchantCode, Merchant merchant,
      List<MerchantCurrencyMapping> currencyMappingList) {
    List<PGMerchantCurrencyMapping> pgMerchantCurrencyMappingList =
        currencyDao.findByMerchantCode(merchantCode);
    if (StringUtil.isListNotNullNEmpty(pgMerchantCurrencyMappingList)) {
      currencyDao.deleteCurrencyMap(merchantCode);
      if (StringUtil.isListNotNullNEmpty(currencyMappingList)) {
        for (MerchantCurrencyMapping selectedCurrencyMapping : currencyMappingList) {
          PGMerchantCurrencyMapping pgMerchantCurrencyMapping = new PGMerchantCurrencyMapping();
          pgMerchantCurrencyMapping.setUpdatedBy(merchant.getCreatedBy());
          pgMerchantCurrencyMapping.setUpdatedDate(DateUtil.getCurrentTimestamp());
          pgMerchantCurrencyMapping.setCreatedBy(merchant.getCreatedBy());
          pgMerchantCurrencyMapping.setCreatedDate(DateUtil.getCurrentTimestamp());
          pgMerchantCurrencyMapping.setMerchantCode(merchantCode);
          pgMerchantCurrencyMapping.setCurrencyCode(selectedCurrencyMapping.getCurrencyCode());
          pgMerchantCurrencyMapping.setStatus(1);
          currencyDao.createOrUpdateCurrencyMap(pgMerchantCurrencyMapping);
        }
      }
    } else {
      if (StringUtil.isListNotNullNEmpty(currencyMappingList)) {
        for (MerchantCurrencyMapping selectedCurrencyMapping : currencyMappingList) {
          PGMerchantCurrencyMapping pgMerchantCurrencyMapping = new PGMerchantCurrencyMapping();
          pgMerchantCurrencyMapping.setCreatedBy(merchant.getCreatedBy());
          pgMerchantCurrencyMapping.setCreatedDate(DateUtil.getCurrentTimestamp());
          pgMerchantCurrencyMapping.setMerchantCode(merchantCode);
          pgMerchantCurrencyMapping.setCurrencyCode(selectedCurrencyMapping.getCurrencyCode());
          pgMerchantCurrencyMapping.setStatus(1);
          currencyDao.createOrUpdateCurrencyMap(pgMerchantCurrencyMapping);
        }
      }
    }
  }

  @Override
  public Response getSubMerchantCodeAndCompanyName(String merchantCode) {
    Response response = new Response();
    List<Map<String, String>> dataList = null;
    if(merchantCode.equals("")) {
      dataList = merchantDao.getMerchantCodeAndCompanyName(PGConstants.SUB_MERCHANT);
    } else {
      dataList = subMerchantDao.getSubMerchantCodeAndCompanyName(merchantCode);
    }
    Map<String, String> subMerchantDataMap = new HashMap<>();
    if (StringUtil.isListNotNullNEmpty(dataList)) {
      for (Map<String, String> map : dataList) {
        subMerchantDataMap.put(map.get("0"), map.get("1"));
      }
    }
    List<Option> options = new ArrayList<>();
    Option option = null;
    for (Map.Entry<String, String> entry : subMerchantDataMap.entrySet()) {
      option = new Option();
      option.setValue(entry.getKey());
      option.setLabel(entry.getValue());

      options.add(option);
    }
    Collections.sort(options, ALPHABETICAL_ORDER);
    response.setResponseList(options);
    response.setErrorCode("00");
    response.setErrorMessage("SUCCESS");
    response.setTotalNoOfRows(options.size());
    return response;
  }

  private static Comparator<Option> ALPHABETICAL_ORDER = ((Option str1, Option str2) -> {
    int res = String.CASE_INSENSITIVE_ORDER.compare(str1.getValue(), str2.getValue());
    if (res == 0) {
      res = str1.getValue().compareTo(str2.getValue());
    }
    return res;
  });
  
	@Override
	public Response findProgramManagerByPartnerId(String partnerId) throws ChatakAdminException {
		Response response = new Response();
		ProgramManager programManager = partnerDao.findProgramManagerByPartnerId(partnerId);
		response.setProgramManagerName(programManager.getProgramManagerName());
		response.setErrorMessage("SUCCESS");
		return response;
	}

}
