package com.chatak.acquirer.admin.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.PartnerService;
import com.chatak.acquirer.admin.util.CommonUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.acq.dao.PartnerDao;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.model.BankPartnerMap;
import com.chatak.pg.acq.dao.model.Partner;
import com.chatak.pg.acq.dao.model.PartnerAccount;
import com.chatak.pg.bean.Response;
import com.chatak.pg.exception.PrepaidAdminException;
import com.chatak.pg.user.bean.BankPartnerMapRequest;
import com.chatak.pg.user.bean.PartnerAccountRequest;
import com.chatak.pg.user.bean.PartnerAccountResponse;
import com.chatak.pg.user.bean.PartnerRequest;
import com.chatak.pg.user.bean.PartnerResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.Properties;

@Service("partnerService")
public class PartnerServiceImpl implements PartnerService {

  protected static Logger logger = Logger.getLogger(PartnerServiceImpl.class);

  @Autowired
  PartnerDao partnerDao;

  @Autowired
  ProgramManagerDao programManagerDao;

  @Autowired
  MessageSource messageSource;

  /**
   * Method used for old Create Partner
   * 
   * @param request
   * @param response
   * @param model
   * 
   * @param partnerRequest
   * @return
   */
  @Override
  public Response createPartner(PartnerRequest partnerRequest) throws ChatakAdminException {
    logger.info("Entering:: PartnerServiceImpl :: createPartner method: ");
    Response response = new Response();
    partnerRequest.setIsAuditable(Boolean.TRUE);
    partnerRequest.setDataChange("Yes");
    Timestamp currentTimeStamp = DateUtil.getCurrentTimestamp();

    // Check whether the partner name already exists
    if (!StringUtil.isNull(partnerRequest.getPartnerName())) {
      List<Partner> partnerList = partnerDao.findByPartnerName(partnerRequest.getPartnerName());

      if (CommonUtil.isListNotNullAndEmpty(partnerList) && partnerRequest.getPartnerId() == null) {
        response.setErrorCode(Constants.PARTNER_ALREADY_EXISTS_WITH_SAME_NAME);
        response.setErrorMessage(messageSource.getMessage("admin.partner.duplicate.partner.name",
            null, LocaleContextHolder.getLocale()));
        return response;
      }

    }

    // Check whether the partner client id already exists
    if (!StringUtil.isNull(partnerRequest.getPartnerClientId())) {
      List<Partner> partnerList =
          partnerDao.findByPartnerClientId(partnerRequest.getPartnerClientId());

      if (CommonUtil.isListNotNullAndEmpty(partnerList) && partnerRequest.getPartnerId() == null) {
        response.setErrorCode(Constants.PARTNER_ALREADY_EXISTS_WITH_SAME_CLIENT_ID);
        response.setErrorMessage("Another Partner with client Id "
            + partnerRequest.getPartnerClientId() + " already exist");
        return response;
      }
    }

    try {
      Partner partner = CommonUtil.copyBeanProperties(partnerRequest, Partner.class);
      if (StringUtil.isNull(partner.getPartnerId())) {
        partner.setCreatedBy(partnerRequest.getCreatedBy());
        partner.setCreatedDate(currentTimeStamp);
      }

      partner.setStatus(Constants.ACTIVE);
      partner.setProgramManagerId(partnerRequest.getProgramManagerRequest().getId());
      if (StringUtil.isListNotNullNEmpty(partnerRequest.getBankPartnerMapRequests())) {
        Set<BankPartnerMap> bankPartnerMaps = new HashSet<>();
        for (BankPartnerMapRequest bankPartnerMapRequest : partnerRequest
            .getBankPartnerMapRequests()) {
          BankPartnerMap bankPartnerMap =
              CommonUtil.copyBeanProperties(bankPartnerMapRequest, BankPartnerMap.class);
          bankPartnerMaps.add(bankPartnerMap);
        }
        partner.setBankPartnerMaps(bankPartnerMaps);
      }

      synchronized (partner) {

        if (StringUtil.isNull(partner.getPartnerId())) {
          Set<PartnerAccount> listOfAccounts = new HashSet<>();
          PartnerAccount revenuePartnerAccount = new PartnerAccount();
          revenuePartnerAccount.setCreatedBy(partnerRequest.getCreatedBy());
          revenuePartnerAccount.setCreatedDate(new Timestamp(System.currentTimeMillis()));
          revenuePartnerAccount.setUpdatedDate(currentTimeStamp);
          revenuePartnerAccount.setAccountNumber(partnerDao.revenuePartnerAccount());
          revenuePartnerAccount.setCurrentBalance(0l);
          revenuePartnerAccount.setAvailableBalance(0l);
          revenuePartnerAccount.setAccountType("Revenue Account");
          revenuePartnerAccount.setStatus(Constants.ACTIVE);
          revenuePartnerAccount.setAutoReplenish(partnerRequest.getAutoRepenish());
          revenuePartnerAccount.setAccountThresholdLimit(
              CommonUtil.getLongAmount(partnerRequest.getAccountThresholdamount()));
          revenuePartnerAccount.setSendFundsMode(partnerRequest.getSendFundsMode());
          revenuePartnerAccount.setBankId(partnerRequest.getBankId());
          listOfAccounts.add(revenuePartnerAccount);

          PartnerAccount systemPartnerAccount = new PartnerAccount();
          systemPartnerAccount.setCreatedBy(partnerRequest.getCreatedBy());
          systemPartnerAccount.setCreatedDate(new Timestamp(System.currentTimeMillis()));
          systemPartnerAccount.setUpdatedDate(currentTimeStamp);
          systemPartnerAccount.setAccountNumber(partnerDao.systemPartnerAccount());
          systemPartnerAccount.setCurrentBalance(0l);
          systemPartnerAccount.setAvailableBalance(0l);
          systemPartnerAccount.setAccountType("System Account");
          systemPartnerAccount.setStatus(Constants.ACTIVE);
          systemPartnerAccount.setAutoReplenish(partnerRequest.getAutoRepenish());
          systemPartnerAccount.setAccountThresholdLimit(
              CommonUtil.getLongAmount(partnerRequest.getAccountThresholdamount()));
          systemPartnerAccount.setSendFundsMode(partnerRequest.getSendFundsMode());
          systemPartnerAccount.setBankId(partnerRequest.getBankId());
          listOfAccounts.add(systemPartnerAccount);

          PartnerAccount networkPartnerAccount = new PartnerAccount();
          networkPartnerAccount.setCreatedBy(partnerRequest.getCreatedBy());
          networkPartnerAccount.setCreatedDate(new Timestamp(System.currentTimeMillis()));
          networkPartnerAccount.setUpdatedDate(currentTimeStamp);
          networkPartnerAccount.setAccountNumber(partnerDao.networkPartnerAccount());
          networkPartnerAccount.setCurrentBalance(0l);
          networkPartnerAccount.setAvailableBalance(0l);
          networkPartnerAccount.setAccountType("Network Account");
          networkPartnerAccount.setStatus(Constants.ACTIVE);
          networkPartnerAccount.setAutoReplenish(partnerRequest.getAutoRepenish());
          networkPartnerAccount.setAccountThresholdLimit(
              CommonUtil.getLongAmount(partnerRequest.getAccountThresholdamount()));
          networkPartnerAccount.setSendFundsMode(partnerRequest.getSendFundsMode());
          networkPartnerAccount.setBankId(partnerRequest.getBankId());
          listOfAccounts.add(networkPartnerAccount);

          partner.setPartnerAccounts(listOfAccounts);
        }
        logger.info("PartnerServiceImpl :: PartnerHandlerImpl ::Before saveOrUpdatePartner ");
        partnerDao.saveOrUpdatePartner(partner);
      }
      logger.info("Exiting:: PartnerServiceImpl :: createPartner method: ");
      return CommonUtil.getSuccessResponse();
    } catch (Exception e) {
      logger.error("ERROR:: PartnerServiceImpl :: createPartner method: ", e);
      return CommonUtil.getResponse(response, Constants.PARTNER_CREATION_ERROR,
          Constants.PARTNER_CREATION_ERROR);
    }
  }

  /**
   * 
   */
  @Override
  public Response changePartnerStatus(PartnerRequest partnerRequest) throws ChatakAdminException {
    logger.info("Entering:: PartnerServiceImpl :: changePartnerStatus method: ");
    partnerRequest.setIsAuditable(Boolean.TRUE);
    partnerRequest.setDataChange("Yes");
    try {
      Partner partner = partnerDao.findByPartnerId(partnerRequest.getPartnerId());
      Set<BankPartnerMap> map =
          partnerDao.findBankPartnerMapByPartnerId(partnerRequest.getPartnerId());
      partner.setUpdatedBy(partnerRequest.getUpdatedBy());
      partner.setStatus(partnerRequest.getStatus());
      partner.setReason(partnerRequest.getReason());
      partner.setUpdatedDate(DateUtil.getCurrentTimestamp());
      partner.setBankPartnerMaps(map);
      partnerDao.saveOrUpdatePartner(partner);

      List<PartnerAccount> partnerAccounts =
          partnerDao.getPartnerAccountsByPartnerId(partnerRequest.getPartnerId(), null);

      for (PartnerAccount partnerAccount : partnerAccounts) {
        partnerAccount.setUpdatedBy(partnerRequest.getUpdatedBy());
        partnerAccount.setStatus(partnerRequest.getStatus());
        partnerAccount.setUpdatedDate(DateUtil.getCurrentTimestamp());
        partnerDao.saveOrUpdatePartnerAccount(partnerAccount);
      }

      PartnerResponse response = new PartnerResponse();
      response.setErrorCode(Constants.STATUS_CODE_SUCCESS);
      logger.info("PartnerServiceImpl :: changePartnerStatus Exiting");
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: PartnerServiceImpl :: changePartnerStatus method.", e);
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()), e);
    }
  }

  /**
   * Method used for New method Create Partner
   * 
   * @param request
   * @param response
   * @param model
   * 
   * @param partnerRequest
   * @return 
   */

  @Override
  public Response updatePartner(PartnerRequest partnerRequest) throws ChatakAdminException {
    logger.info("Entering:: PartnerServiceImpl :: updatePartner method: ");
    Response response = new Response();
    partnerRequest.setIsAuditable(Boolean.TRUE);
    partnerRequest.setDataChange("Yes");
    Timestamp currentTimeStamp = DateUtil.getCurrentTimestamp();

    PartnerRequest partnerRequestFromDB = null;
    try {
      partnerRequestFromDB = partnerDao.getDetailsByPartnerId(partnerRequest);

      if (!StringUtil.isNull(partnerRequestFromDB)) {
        setFieldsForUpdate(partnerRequest, partnerRequestFromDB);
      }

      // Check whether the partner name already exists
      if (!StringUtil.isNull(partnerRequestFromDB.getPartnerName())) {
        List<Partner> partnerList =
            partnerDao.findByPartnerName(partnerRequestFromDB.getPartnerName());

        if (CommonUtil.isListNotNullAndEmpty(partnerList)) {
          processCheckIdNull(partnerRequest, response);

          Boolean partnerNameExists = Boolean.FALSE;
          partnerNameExists =
              isPartnerClientAlreadyExist(partnerRequestFromDB, partnerList, partnerNameExists);

          processCheckPartnerNameExist(partnerRequest, response, partnerNameExists);
        }
      }

      // Check whether the partner client id already exists
      if (!StringUtil.isNull(partnerRequestFromDB.getPartnerClientId())) {
        List<Partner> partnerList =
            partnerDao.findByPartnerClientId(partnerRequestFromDB.getPartnerClientId());

        if (CommonUtil.isListNotNullAndEmpty(partnerList)) {
          processCheckPartnerIdNull(partnerRequest, response);

          Boolean partnerClientIdExists = Boolean.FALSE;
          partnerClientIdExists =
              isPartnerClientAlreadyExist(partnerRequestFromDB, partnerList, partnerClientIdExists);

          processCheckPartnerClientIdExists(partnerRequest, response, partnerClientIdExists);
        }
      }

      Partner partner = CommonUtil.copyBeanProperties(partnerRequestFromDB, Partner.class);
      partner.setUpdatedBy(partnerRequest.getUpdatedBy());
      partner.setUpdatedDate(currentTimeStamp);
      getPartnerBankDetails(partnerRequest, currentTimeStamp, partnerRequestFromDB, partner);

      logger.info("PartnerServiceImpl :: PartnerHandlerImpl ::Before saveOrUpdatePartner ");
      partnerDao.saveOrUpdatePartner(partner);

      logger.info("Exiting:: PartnerServiceImpl :: updatePartner method: ");
      return CommonUtil.getSuccessResponse();
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: PartnerServiceImpl :: updatePartner method: ", e);
      return CommonUtil.getResponse(response, response.getErrorCode(), response.getErrorMessage());
    } catch (Exception e) {
      logger.error("ERROR:: PartnerServiceImpl :: updatePartner method: ", e);
      return CommonUtil.getResponse(response, Constants.PARTNER_CREATION_ERROR,
          Constants.PARTNER_CREATION_ERROR);
    }
  }

  private void processCheckIdNull(PartnerRequest partnerRequest, Response response)
      throws ChatakAdminException {
    if (partnerRequest.getPartnerId() == null) {
      response.setErrorCode(Constants.PARTNER_ALREADY_EXISTS_WITH_SAME_NAME);
      response.setErrorMessage(
          "Another Partner with name " + partnerRequest.getPartnerName() + " already exist");
      throw new ChatakAdminException(
          "Another Partner with name " + partnerRequest.getPartnerName() + " already exist");
    }
  }

  private void processCheckPartnerNameExist(PartnerRequest partnerRequest, Response response,
      Boolean partnerNameExists) throws ChatakAdminException {
    if (partnerNameExists) {
      response.setErrorCode(Constants.PARTNER_ALREADY_EXISTS_WITH_SAME_NAME);
      response.setErrorMessage(
          "Another Partner with name " + partnerRequest.getPartnerName() + " already exist");
      throw new ChatakAdminException(
          "Another Partner with name " + partnerRequest.getPartnerName() + " already exist");
    }
  }

  private void processCheckPartnerIdNull(PartnerRequest partnerRequest, Response response)
      throws ChatakAdminException {
    if (partnerRequest.getPartnerId() == null) {
      response.setErrorCode(Constants.PARTNER_ALREADY_EXISTS_WITH_SAME_CLIENT_ID);
      response.setErrorMessage("Another Partner with same client id "
          + partnerRequest.getPartnerClientId() + " already exist");
      throw new ChatakAdminException("Another Partner with same client id "
          + partnerRequest.getPartnerClientId() + " already exist");
    }
  }

  private void processCheckPartnerClientIdExists(PartnerRequest partnerRequest, Response response,
      Boolean partnerClientIdExists) throws ChatakAdminException {
    if (partnerClientIdExists) {
      response.setErrorCode(Constants.PARTNER_ALREADY_EXISTS_WITH_SAME_CLIENT_ID);
      response.setErrorMessage("Another Partner with client id "
          + partnerRequest.getPartnerClientId() + " already exist");
      throw new ChatakAdminException("Another Partner with client id "
          + partnerRequest.getPartnerClientId() + " already exist");
    }
  }

  private void getPartnerBankDetails(PartnerRequest partnerRequest, Timestamp currentTimeStamp,
      PartnerRequest partnerRequestFromDB, Partner partner) throws ReflectiveOperationException {
    // Delete all the child records if already mapped
    // This will be used in case of updating program manager
    if (!StringUtil.isNull(partner.getPartnerId())) {
      Set<BankPartnerMap> bankPartnerMaps =
          partnerDao.findBankPartnerMapByPartnerId(partner.getPartnerId());
      if (bankPartnerMaps != null && bankPartnerMaps.size() > 0) {
        logger.info("PartnerServiceImpl :: PartnerHandlerImpl ::Before deleteBankPartner ");
        partnerDao.deleteBankPartner(bankPartnerMaps);
      }
    }

    partner.setProgramManagerId(partnerRequestFromDB.getProgramManagerRequest().getId());
    if (StringUtil.isListNotNullNEmpty(partnerRequestFromDB.getBankPartnerMapRequests())) {
      Set<BankPartnerMap> bankPartnerMaps = new HashSet<>();
      for (BankPartnerMapRequest bankPartnerMapRequest : partnerRequestFromDB
          .getBankPartnerMapRequests()) {
        BankPartnerMap bankPartnerMap =
            CommonUtil.copyBeanProperties(bankPartnerMapRequest, BankPartnerMap.class);
        bankPartnerMaps.add(bankPartnerMap);
      }
      partner.setBankPartnerMaps(bankPartnerMaps);
    }

    if (StringUtil.isListNotNullNEmpty(partnerRequestFromDB.getPartnerAccountRequests())) {
      Set<PartnerAccount> partnerAccounts = new HashSet<>();
      for (PartnerAccountRequest partnerAccountRequest : partnerRequestFromDB
          .getPartnerAccountRequests()) {
        PartnerAccount partnerAccount =
            CommonUtil.copyBeanProperties(partnerAccountRequest, PartnerAccount.class);
        partnerAccount
            .setCurrentBalance(CommonUtil.getLongAmount(partnerAccountRequest.getCurrentBalance()));
        partnerAccount.setAvailableBalance(
            CommonUtil.getLongAmount(partnerAccountRequest.getAvailableBalance()));
        partnerAccount.setAccountThresholdLimit(
            CommonUtil.getLongAmount(partnerAccountRequest.getAccountThresholdAmount()));
        partnerAccount.setUpdatedBy(partnerRequest.getUpdatedBy());
        partnerAccount.setUpdatedDate(currentTimeStamp);
        partnerAccounts.add(partnerAccount);
      }
      partner.setPartnerAccounts(partnerAccounts);
    }
  }

  private Boolean isPartnerClientAlreadyExist(PartnerRequest partnerRequestFromDB,
      List<Partner> partnerList, Boolean partnerClientIdExists) {
    for (Partner partner : partnerList) {
      if (partner.getPartnerId().compareTo(partnerRequestFromDB.getPartnerId()) != 0) {
        partnerClientIdExists = Boolean.TRUE;
        break;
      }
    }
    return partnerClientIdExists;
  }

  /**
   * Method used for New method Create Partner
   * 
   * @param request
   * @param response
   * @param model
   * 
   * @param partnerRequest
   * @return 
   */

  @Override
  public Response updatePartnerAccount(PartnerAccountRequest partnerAccountRequest)
      throws ChatakAdminException {
    logger.info("Entering:: PartnerServiceImpl :: updatePartnerAccount method: ");
    Response response = new Response();
    try {
      PartnerAccount partnerAccount = partnerDao.getPartnerAccountByAccountIdAndActType(
          partnerAccountRequest.getPartnerAccountId(), partnerAccountRequest.getAccountType());
      partnerAccount.setNickName(partnerAccountRequest.getNickName());
      partnerAccount.setAccountType(partnerAccountRequest.getAccountType());
      partnerAccount.setSendFundsMode(partnerAccountRequest.getSendFundsMode());
      partnerAccount.setAccountThresholdLimit(
          CommonUtil.getLongAmount(partnerAccountRequest.getAccountThresholdAmount()));
      partnerAccount.setBankId(partnerAccountRequest.getBankId());
      partnerAccount.setAutoReplenish(partnerAccountRequest.getAutoRepenish());
      partnerDao.saveOrUpdatePartnerAccount(partnerAccount);

      logger.info("Exiting:: PartnerServiceImpl :: updatePartnerAccount method: ");
      return CommonUtil.getSuccessResponse();
    } catch (Exception e) {
      logger.error("ERROR:: PartnerServiceImpl :: updatePartnerAccount method: ", e);
      return CommonUtil.getResponse(response, Constants.PARTNER_CREATION_ERROR,
          Constants.PARTNER_CREATION_ERROR);
    }
  }

  /**
   * Method for reload the Search Partner after Create partner
   * 
   * @param request
   * @param response
   * @param model
   * @param session
   * @return
   */
  @Override
  public PartnerResponse searchPartner(PartnerRequest partnerRequest) throws ChatakAdminException {
    partnerRequest.setIsAuditable(Boolean.TRUE);
    partnerRequest.setDataChange("No");
    logger.info("Entering:: PartnerServiceImpl :: searchPartner method: ");
    try {
      PartnerResponse response = new PartnerResponse();
      List<PartnerRequest> partnerList = partnerDao.searchPartner(partnerRequest);
      setResponseDetails(partnerRequest, partnerList, response);
      logger.info("PartnerServiceImpl :: searchPartner Exiting");
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: PartnerServiceImpl :: searchPartner method.", e);
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()), e);
    }
  }


  /**
   * Method for reload the Search Partner after Create partner
   * 
   * @param request
   * @param response
   * @param model
   * @param session
   * @return
   */
  @Override
  public PartnerResponse searchPartnerAccount(PartnerRequest partnerRequest)
      throws ChatakAdminException {
    partnerRequest.setIsAuditable(Boolean.TRUE);
    partnerRequest.setDataChange("No");
    logger.info("Entering:: PartnerServiceImpl :: searchAccountPartner method: ");
    try {
      List<PartnerRequest> partnerList = null;
      PartnerResponse response = new PartnerResponse();
      if (partnerRequest.getPartnerId() == null) {
        partnerList = partnerDao.searchPartnerAccountByPm(partnerRequest);
      } else
        partnerList = partnerDao.searchAccountPartner(partnerRequest);
      setResponseDetails(partnerRequest, partnerList, response);
      logger.info("PartnerServiceImpl :: searchAccountPartner Exiting");
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: PartnerServiceImpl :: searchAccountPartner method.", e);
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()), e);
    }
  }

  private void setResponseDetails(PartnerRequest partnerRequest, List<PartnerRequest> partnerList,
      PartnerResponse response) {
    if (StringUtil.isListNotNullNEmpty(partnerList)) {
      response.setPartnerList(partnerList);
      response.setTotalNoOfRows(partnerRequest.getNoOfRecords());
    }
    response.setErrorCode(Constants.CEC_0001);
    response.setErrorMessage(Properties.getProperty(response.getErrorCode()));
  }

  /**
   * Method used to get all partner id
   * 
   * @param request
   * @param response
   * @param model
   * @param adminUserData
   * @return
   */
  public PartnerResponse getAllPartners(PartnerRequest partnerRequest) throws ChatakAdminException {

    logger.info("Entering:: PartnerServiceImpl :: getAllPartners method: ");
    PartnerResponse partnerTempResponse = new PartnerResponse();
    try {
      if (StringUtil.isNull(partnerRequest.getPartnerTypeList())) {
        partnerRequest.setPartnerTypeList(Arrays.asList(Constants.NORMAL_PARTNER));
      }
      List<PartnerRequest> partnerList = partnerDao.getAllPartners(partnerRequest);

      if (CommonUtil.isListNotNullAndEmpty(partnerList)) {
        partnerTempResponse.setPartnerList(partnerList);
        partnerTempResponse.setErrorCode(Constants.CEC_0001);
        partnerTempResponse
            .setErrorMessage(Properties.getProperty(partnerTempResponse.getErrorCode()));
      } else {
        partnerTempResponse.setErrorCode(Constants.CEC_0002);
        partnerTempResponse
            .setErrorMessage(Properties.getProperty(partnerTempResponse.getErrorCode()));
      }

      logger.info("Exiting:: PartnerServiceImpl :: getAllPartners method: ");
      return partnerTempResponse;
    } catch (Exception e) {
      logger.error("ERROR:: PartnerServiceImpl :: getAllPartners method", e);
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()), e);
    }
  }


  @Override
  public PartnerResponse getAllPartnersForAdminUser(PartnerRequest partnerRequest)
      throws ChatakAdminException {
    logger.info("Entering:: PartnerServiceManagementImpl:: getAllPartnersForAdminUser method: ");
    PartnerResponse partnerTempResponse = new PartnerResponse();
    try {
      List<PartnerRequest> partnerList = partnerDao.getAllPartnersForAdminUser(partnerRequest);
      if (CommonUtil.isListNotNullAndEmpty(partnerList)) {
        partnerTempResponse.setPartnerList(partnerList);
        partnerTempResponse.setErrorCode(Constants.CEC_0001);
        partnerTempResponse
            .setErrorMessage(Properties.getProperty(partnerTempResponse.getErrorCode()));
      } else {
        partnerTempResponse.setErrorCode(Constants.CEC_0002);
        partnerTempResponse.setErrorMessage(partnerTempResponse.getErrorCode());
      }

      logger.info("Exiting:: PartnerServiceManagementImpl:: getAllPartnersForAdminUser method: ");
      return partnerTempResponse;
    } catch (Exception e) {
      logger.error("ERROR: PartnerServiceManagementImpl:: getAllPartnersForAdminUser method", e);
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()), e);
    }
  }

  @Override
  public PartnerResponse findByPartnerId(PartnerRequest partnerRequest)
      throws ChatakAdminException {
    logger.info("Entering:: PartnerServiceImpl :: findByPartnerId method: ");
    partnerRequest.setIsAuditable(Boolean.TRUE);
    partnerRequest.setDataChange("No");
    PartnerResponse response = new PartnerResponse();
    try {
      getPartnerDetails(partnerRequest, response);

      logger.info("Exiting:: PartnerServiceImpl :: findByPartnerId method: ");
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: PartnerServiceImpl :: findByPartnerId method: ", e);
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()), e);
    }
  }

  @Override
  public PartnerAccountResponse findByPartnerAccountId(PartnerRequest partnerRequest)
      throws ChatakAdminException {
    logger.info("Entering:: PartnerServiceImpl :: findByPartnerAccountId method: ");
    partnerRequest.setIsAuditable(Boolean.TRUE);
    partnerRequest.setDataChange("No");
    PartnerAccountResponse response = new PartnerAccountResponse();
    try {
      PartnerAccount partnerAccount = partnerDao.getPartnerAccountByAccountIdAndActType(
          partnerRequest.getPartnerAccountId(), partnerRequest.getAccountType());

      PartnerAccountRequest partnerAccountRequest =
          CommonUtil.copyBeanProperties(partnerAccount, PartnerAccountRequest.class);
      partnerAccountRequest
          .setAvailableBalance(CommonUtil.getDoubleAmount(partnerAccount.getAvailableBalance()));
      partnerAccountRequest
          .setCurrentBalance(CommonUtil.getDoubleAmount(partnerAccount.getCurrentBalance()));
      partnerAccountRequest.setAccountThresholdAmount(
          CommonUtil.getDoubleAmount(partnerAccount.getAccountThresholdLimit()));
      partnerAccountRequest.setPartnerId(partnerAccount.getPartnerId());
      partnerDao.findBankDetailsByPartnerId(partnerAccountRequest);
      response.setTotalNoOfRows(1);
      response.setPartnerList(Arrays.asList(partnerAccountRequest));

      response.setErrorCode(Constants.CEC_0001);
      response.setErrorMessage(Properties.getProperty(response.getErrorCode()));
      logger.info("Exiting:: PartnerServiceImpl :: findByPartnerAccountId method: ");
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: PartnerServiceImpl :: findByPartnerAccountId method: ", e);
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()), e);
    }
  }

  @Override
  public PartnerResponse editPartner(PartnerRequest partnerRequest) throws ChatakAdminException {
    logger.info("Entering:: PartnerServiceImpl :: findByPartnerId method: ");
    partnerRequest.setIsAuditable(Boolean.TRUE);
    partnerRequest.setDataChange("No");
    PartnerResponse response = new PartnerResponse();
    try {
      getPartnerDetails(partnerRequest, response);

      logger.info("Exiting:: PartnerServiceImpl :: findByPartnerId method: ");
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: PartnerServiceImpl :: findByPartnerId method: ", e);
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()), e);
    }
  }

  private void getPartnerDetails(PartnerRequest partnerRequest, PartnerResponse response)
      throws PrepaidAdminException {
    partnerRequest = partnerDao.getDetailsByPartnerId(partnerRequest);
    if (!StringUtil.isNull(partnerRequest)) {
      response.setTotalNoOfRows(1);
      response.setPartnerList(Arrays.asList(partnerRequest));
      response.setErrorCode(Constants.CEC_0001);
      response.setErrorMessage(Properties.getProperty(response.getErrorCode()));
    } else {
      response.setErrorCode(Constants.CEC_0002);
      response.setErrorMessage(Properties.getProperty(response.getErrorCode()));
    }
  }

  private void setFieldsForUpdate(PartnerRequest srcPartnerRequest,
      PartnerRequest tgtPartnerRequest) {
    tgtPartnerRequest.setCompanyName(srcPartnerRequest.getCompanyName());
    tgtPartnerRequest.setBusinessEntityName(srcPartnerRequest.getBusinessEntityName());
    tgtPartnerRequest.setPartnerClientId(srcPartnerRequest.getPartnerClientId());
    tgtPartnerRequest.setContactPerson(srcPartnerRequest.getContactPerson());
    tgtPartnerRequest.setExtension(srcPartnerRequest.getExtension());
    tgtPartnerRequest.setAddress1(srcPartnerRequest.getAddress1());
    tgtPartnerRequest.setAddress2(srcPartnerRequest.getAddress2());
    tgtPartnerRequest.setContactPhone(srcPartnerRequest.getContactPhone());
    tgtPartnerRequest.setCountry(srcPartnerRequest.getCountry());
    tgtPartnerRequest.setState(srcPartnerRequest.getState());
    tgtPartnerRequest.setCity(srcPartnerRequest.getCity());
    tgtPartnerRequest.setZip(srcPartnerRequest.getZip());
    tgtPartnerRequest.setPartnerLogo(srcPartnerRequest.getPartnerLogo());
    tgtPartnerRequest.setBankId(srcPartnerRequest.getBankId());
    tgtPartnerRequest.setReason(srcPartnerRequest.getReason());
    tgtPartnerRequest.setBankPartnerMapRequests(srcPartnerRequest.getBankPartnerMapRequests());
    tgtPartnerRequest.setProgramManagerRequest(srcPartnerRequest.getProgramManagerRequest());
    tgtPartnerRequest.setWhiteListIPAddress(srcPartnerRequest.getWhiteListIPAddress());
    tgtPartnerRequest.setPartnerType(srcPartnerRequest.getPartnerType());
  }

  @Override
  public Response getPartnersByProgramManagerId(String programManagerId) {
    Response response = new Response();
    List<Partner> partners = null;
    if (programManagerId != null && !"".equals(programManagerId)) {  
      partners = partnerDao.getPartnersByPMId(Long.valueOf(programManagerId));
    } else {
      partners = partnerDao.getActivePartners();
    }
    Map<String, String> partnerMap = new HashMap<>();
    if (StringUtil.isListNotNullNEmpty(partners)) {
      for (Partner partner : partners) {
        partnerMap.put(partner.getPartnerId().toString(), partner.getPartnerName());
      }
    }
    List<Option> options = new ArrayList<>();
    Option option = null;
    for (Map.Entry<String, String> entry : partnerMap.entrySet()) {
      option = new Option();
      option.setValue(entry.getKey());
      option.setLabel(entry.getValue());

      options.add(option);
    }
     Collections.sort(options, StringUtil.ALPHABETICAL_ORDER);    
    response.setResponseList(options);
    response.setErrorCode("00");
    response.setErrorMessage("SUCCESS");
    response.setTotalNoOfRows(options.size());
    return response;
  }
}
