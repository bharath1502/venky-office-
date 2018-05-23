package com.chatak.acquirer.admin.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.constants.StatusConstants;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.ProgramManagerService;
import com.chatak.acquirer.admin.util.CommonUtil;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.model.BankProgramManagerMap;
import com.chatak.pg.acq.dao.model.ProgramManager;
import com.chatak.pg.acq.dao.model.ProgramManagerAccount;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.AgentDTOResponse;
import com.chatak.pg.model.ValidateAgentDataRequest;
import com.chatak.pg.user.bean.BankProgramManagerMapRequest;
import com.chatak.pg.user.bean.BankRequest;
import com.chatak.pg.user.bean.BankResponse;
import com.chatak.pg.user.bean.CardProgramRequest;
import com.chatak.pg.user.bean.CardProgramResponse;
import com.chatak.pg.user.bean.PartnerGroupPartnerMapRequest;
import com.chatak.pg.user.bean.ProgramManagerAccountRequest;
import com.chatak.pg.user.bean.ProgramManagerAccountResponse;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.user.bean.ProgramManagerResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.Properties;
import com.sun.jersey.api.client.ClientResponse;

@Service
public class ProgramManagerServiceImpl implements ProgramManagerService {

  private static Logger logger = Logger.getLogger(ProgramManagerServiceImpl.class);

  @Autowired
  ProgramManagerDao programManagerDao;

  @Autowired
  MessageSource messageSource;
  
  private ObjectMapper mapper = new ObjectMapper();

  @Override
  public Response createProgramManager(ProgramManagerRequest programManagerRequest)
      throws ChatakAdminException {

    logger.info("Entering:: ProgramManagerServiceImpl :: createProgramManager method");
    Response response = new Response();
    Timestamp currentTimeStamp = getCurrentTimeStamp(programManagerRequest);

    // Check whether the program manager name already exists
    if (!StringUtil.isNull(programManagerRequest.getProgramManagerName())) {
      List<ProgramManager> listOfProgramManager =
          programManagerDao.findByProgramManagerName(programManagerRequest.getProgramManagerName());

      if (CommonUtil.isListNotNullAndEmpty(listOfProgramManager)) {
        if (programManagerRequest.getId() == null) {
          response.setErrorCode(Constants.PROGRAM_MANAGER_ALREADY_EXISTS_WITH_NAME);
          response.setErrorMessage(Constants.PROGRAM_MANAGER_ALREADY_EXISTS_WITH_NAME);
          return response;
        }

        Boolean programManagerNameExists =
            isPmNameAlreadyExist(programManagerRequest, listOfProgramManager);

        if (programManagerNameExists) {
          response.setErrorCode(Constants.PROGRAM_MANAGER_ALREADY_EXISTS_WITH_NAME);
          response.setErrorMessage("Another Program manager with name "
              + programManagerRequest.getProgramManagerName() + " already exist");
          return response;
        }
      }
    }

    try {
      ProgramManager programManager =
          CommonUtil.copyBeanProperties(programManagerRequest, ProgramManager.class);

      if (StringUtil
          .isListNotNullNEmpty(programManagerRequest.getBankProgramManagerMapRequests())) {
        Set<BankProgramManagerMap> bankProgramManagerMaps =
            getPMBankMappingData(programManagerRequest);
        programManager.setBankProgramManagerMaps(bankProgramManagerMaps);
      }

      programManager.setCreatedBy(programManagerRequest.getCreatedBy());
      programManager.setCreatedDate(currentTimeStamp);
      programManager.setStatus(Constants.ACTIVE);

      if (StringUtil.isNull(programManager.getId())) {
        Set<ProgramManagerAccount> listOfAccounts = new HashSet<ProgramManagerAccount>();

        ProgramManagerAccount systemProgramManagerAccount = new ProgramManagerAccount();
        systemProgramManagerAccount.setCreatedBy(programManagerRequest.getCreatedBy());
        systemProgramManagerAccount.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        systemProgramManagerAccount
            .setAccountNumber(programManagerDao.getProgramManagerAccountNumber());
        systemProgramManagerAccount.setCurrentBalance(0l);
        systemProgramManagerAccount.setAvailableBalance(0l);
        systemProgramManagerAccount.setAccountType("System Account");
        systemProgramManagerAccount.setStatus(Constants.ACTIVE);
        systemProgramManagerAccount.setUpdatedDate(currentTimeStamp);
        systemProgramManagerAccount.setAccountThresholdLimit(
            CommonUtil.getLongAmount(programManagerRequest.getAccountThresholdLimit()));
        systemProgramManagerAccount.setAutoReplenish(programManagerRequest.getAutoRepenish());
        systemProgramManagerAccount.setSendFundsMode(programManagerRequest.getSendFundsMode());
        systemProgramManagerAccount.setBankId(Long.parseLong(programManagerRequest.getBankNames()));
        listOfAccounts.add(systemProgramManagerAccount);

        ProgramManagerAccount revenueProgramManagerAccount = new ProgramManagerAccount();
        revenueProgramManagerAccount.setCreatedBy(programManagerRequest.getCreatedBy());
        revenueProgramManagerAccount.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        revenueProgramManagerAccount
            .setAccountNumber(programManagerDao.getRevenueProgramManagerAccountNumber());
        revenueProgramManagerAccount.setCurrentBalance(0l);
        revenueProgramManagerAccount.setAvailableBalance(0l);
        revenueProgramManagerAccount.setAccountType("Revenue Account");
        revenueProgramManagerAccount.setStatus(Constants.ACTIVE);
        revenueProgramManagerAccount.setUpdatedDate(currentTimeStamp);
        revenueProgramManagerAccount.setAccountThresholdLimit(
            CommonUtil.getLongAmount(programManagerRequest.getAccountThresholdLimit()));
        revenueProgramManagerAccount.setAutoReplenish(programManagerRequest.getAutoRepenish());
        revenueProgramManagerAccount.setSendFundsMode(programManagerRequest.getSendFundsMode());
        revenueProgramManagerAccount.setBankId(programManagerRequest.getBankId());
        listOfAccounts.add(revenueProgramManagerAccount);

        programManager.setProgramManagerAccounts(listOfAccounts);
      }
      logger.info("info:: ProgramManagerServiceImpl :: createOrUpdateProgramManager method");

      programManager = programManagerDao.saveOrUpdateProgramManager(programManager);

      logger.info("Exiting:: ProgramManagerServiceImpl :: createProgramManager method: ");
      //set the program manager ID when saved into DB to send back to the controller 
      response.setTotalNoOfRows((int) (long) programManager.getId());
      return CommonUtil.getSuccessResponse();
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerServiceImpl :: createProgramManager method: ", e);
      return CommonUtil.getResponse(response, Constants.PROGRAM_MANAGER_CREATION_ERROR,
          Constants.PROGRAM_MANAGER_CREATION_ERROR);
    }
  }

  private Timestamp getCurrentTimeStamp(ProgramManagerRequest programManagerRequest) {
    programManagerRequest.setIsAuditable(Boolean.TRUE);
    programManagerRequest.setDataChange("Yes");
    return DateUtil.getCurrentTimestamp();
  }

  @Override
  public ProgramManagerResponse searchProgramManagerDetails(
      ProgramManagerRequest programManagerRequest) throws ChatakAdminException {

    programManagerRequest.setIsAuditable(Boolean.TRUE);
    programManagerRequest.setDataChange("No");
    try {
      List<ProgramManagerRequest> programManagerRequests =
          programManagerDao.searchProgramManagers(programManagerRequest);
      ProgramManagerResponse response = new ProgramManagerResponse();
      processSearchResult(programManagerRequest, programManagerRequests, response);
      logger.info("Exiting:: ProgramManagerServiceImpl :: searchProgramManager method: ");
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerServiceImpl :: searchProgramManager method.", e);
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()), e);
    }
  }

  private void processSearchResult(ProgramManagerRequest programManagerRequest,
      List<ProgramManagerRequest> programManagerRequests, ProgramManagerResponse response) {
    if (StringUtil.isListNotNullNEmpty(programManagerRequests)) {
      response.setProgramManagersList(programManagerRequests);
      response.setTotalNoOfRows(programManagerRequest.getNoOfRecords());
    }
    response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
    response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
  }

  @Override
  public ProgramManagerResponse searchProgramManagerAccountDetails(
      ProgramManagerRequest programManagerRequest) throws ChatakAdminException {

    programManagerRequest.setIsAuditable(Boolean.TRUE);
    programManagerRequest.setDataChange("No");
    try {
      List<ProgramManagerRequest> programManagerRequests =
          programManagerDao.searchProgramManagersAccounts(programManagerRequest);
      ProgramManagerResponse response = new ProgramManagerResponse();
      processSearchResult(programManagerRequest, programManagerRequests, response);
      logger.info("Exiting:: ProgramManagerServiceImpl :: searchProgramManager method: ");
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerServiceImpl :: searchProgramManager method.", e);
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()), e);
    }
  }


  @Override
  public ProgramManagerResponse searchSystemProgramManager(
      ProgramManagerRequest programManagerRequest) throws ChatakAdminException {
    logger.info("Entering:: ProgramManagerServiceImpl :: searchSystemProgramManager method: ");
    try {
      ProgramManagerResponse response = new ProgramManagerResponse();
      ProgramManager programManagerRequests =
          programManagerDao.searchSystemProgramManager(programManagerRequest);
      if (!StringUtil.isNull(programManagerRequests)) {
        List<ProgramManagerRequest> programManagerRequestList =
            new ArrayList<ProgramManagerRequest>();
        programManagerRequestList.add((ProgramManagerRequest) CommonUtil
            .copyBeanProperties(programManagerRequests, ProgramManagerRequest.class));
        response.setProgramManagersList(programManagerRequestList);
        response.setTotalNoOfRows(programManagerRequest.getNoOfRecords());
      }
      response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
      response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
      logger.info("Exiting:: ProgramManagerServiceImpl :: searchSystemProgramManager method: ");
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerServiceImpl :: searchSystemProgramManager method.", e);
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()), e);
    }
  }

  @Override
  public ProgramManagerResponse editProgramManager(ProgramManagerRequest programManagerRequest)
      throws ChatakAdminException {
    programManagerRequest.setIsAuditable(Boolean.TRUE);
    programManagerRequest.setDataChange("No");
    logger.info("Entering:: ProgramManagerServiceImpl :: findProgramManagerById method: ");
    ProgramManagerResponse response = new ProgramManagerResponse();
    try {
      ProgramManagerRequest programManagerRequest2 =
          programManagerDao.findProgramManagerById(programManagerRequest);

      if (!StringUtil.isNull(programManagerRequest2)) {
        response.setProgramManagersList(Arrays.asList(programManagerRequest2));
      }
      response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
      response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);

      logger.info("Exiting:: ProgramManagerServiceImpl :: findProgramManagerById method: ");
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerServiceImpl :: findProgramManagerById method: ", e);
      return (ProgramManagerResponse) CommonUtil.getResponse(response,
          Constants.PROGRAM_MANAGER_SEARCH_ERROR,
          Properties.getProperty(Constants.PROGRAM_MANAGER_SEARCH_ERROR));
    }
  }

  @Override
  public ProgramManagerAccountResponse findProgramManagerAccountByAccountId(
      ProgramManagerRequest programManagerRequest) throws ChatakAdminException {
    programManagerRequest.setIsAuditable(Boolean.TRUE);
    programManagerRequest.setDataChange("No");
    logger.info("Entering:: ProgramManagerServiceImpl :: findProgramManagerAccountById method: ");
    ProgramManagerAccountResponse response = new ProgramManagerAccountResponse();
    try {
      ProgramManagerAccountRequest programManagerAccountRequest =
          getAccountDetails(programManagerRequest);

      setResponseDetails(response, programManagerAccountRequest);

      logger.info("Exiting:: ProgramManagerServiceImpl :: findProgramManagerAccountById method: ");
      return response;
    } catch (Exception e1) {
      logger.error("ERROR:: ProgramManagerServiceImpl :: findProgramManagerAccountById method: ",
          e1);
    }
    return response;
  }

  private void setResponseDetails(ProgramManagerAccountResponse response,
      ProgramManagerAccountRequest programManagerAccountRequest) {
    response.setProgramManagerAccountRequestList(Arrays.asList(programManagerAccountRequest));
    response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
    response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
  }

  @Override
  public Response editProgramManagerAccount(ProgramManagerRequest programManagerRequest)
      throws ChatakAdminException {
    programManagerRequest.setIsAuditable(Boolean.TRUE);
    programManagerRequest.setDataChange("No");
    logger.info("Entering:: ProgramManagerServiceImpl :: findProgramManagerAccountById method: ");
    ProgramManagerAccountResponse response = new ProgramManagerAccountResponse();
    try {
      ProgramManagerAccountRequest programManagerAccountRequest =
          getAccountDetails(programManagerRequest);

      setResponseDetails(response, programManagerAccountRequest);

      logger.info("Exiting:: ProgramManagerServiceImpl :: findProgramManagerAccountById method: ");
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerServiceImpl :: findProgramManagerAccountById method: ",
          e);
    }
    return response;
  }

  private ProgramManagerAccountRequest getAccountDetails(
      ProgramManagerRequest programManagerRequest) throws ReflectiveOperationException {
    ProgramManagerAccountRequest programManagerAccountRequest = new ProgramManagerAccountRequest();
    ProgramManagerAccount programManagerAccount = null;
    if (!StringUtil.isNull(programManagerRequest.getProgramManagerAccountId())) {
      programManagerAccount = programManagerDao.getProgramManagerAccountByIdAndAccountType(
          programManagerRequest.getProgramManagerAccountId(),
          programManagerRequest.getAccountType());
      programManagerAccountRequest =
          CommonUtil.copyBeanProperties(programManagerAccount, ProgramManagerAccountRequest.class);
      programManagerAccountRequest.setProgramManagerId(programManagerAccount.getProgramManagerId());
      programManagerDao.findBankDetailsByPMId(programManagerAccountRequest);
    } else {
      programManagerAccount = programManagerDao.findByProgramManagerIdAndAccountType(
          programManagerRequest.getId(), programManagerRequest.getAccountType());
    }
    programManagerAccountRequest.setAvailableBalance(
        CommonUtil.getDoubleAmount(programManagerAccount.getAvailableBalance()));
    programManagerAccountRequest
        .setCurrentBalance(CommonUtil.getDoubleAmount(programManagerAccount.getCurrentBalance()));
    programManagerAccountRequest.setAccountThresholdAmount(
        CommonUtil.getDoubleAmount(programManagerAccount.getAccountThresholdLimit()));
    programManagerAccountRequest.setAccountType(programManagerAccount.getAccountType());
    return programManagerAccountRequest;
  }

  @Override
  public Response updateProgramManager(ProgramManagerRequest programManagerRequest)
      throws ChatakAdminException {
    logger.info("Entering:: ProgramManagerServiceImpl :: updateProgramManager method: ");
    Response response = new Response();
    Timestamp currentTimeStamp = getCurrentTimeStamp(programManagerRequest);

    // Check whether the program manager name already exists
    if (!StringUtil.isNull(programManagerRequest.getProgramManagerName())) {
      List<ProgramManager> listOfProgramManager =
          programManagerDao.findByProgramManagerName(programManagerRequest.getProgramManagerName());

      if (CommonUtil.isListNotNullAndEmpty(listOfProgramManager)) {
        if (programManagerRequest.getId() == null) {
          response.setErrorCode(Constants.PROGRAM_MANAGER_ALREADY_EXISTS_WITH_NAME);
          response.setErrorMessage("Another Program manager with name "
              + programManagerRequest.getProgramManagerName() + " already exist");
          return response;
        }

        Boolean programManagerNameExists =
            isPmNameAlreadyExist(programManagerRequest, listOfProgramManager);

        if (programManagerNameExists) {
          response.setErrorCode(Constants.PROGRAM_MANAGER_ALREADY_EXISTS_WITH_NAME);
          response.setErrorMessage("Another Program manager with name "
              + programManagerRequest.getProgramManagerName() + " already exist");
          return response;
        }
      }
    }

    try {
      ProgramManagerRequest existingProgramManagerRequest =
          programManagerDao.findProgramManagerById(programManagerRequest);
      setUpdatedValues(programManagerRequest, existingProgramManagerRequest);
      ProgramManager programManager =
          CommonUtil.copyBeanProperties(existingProgramManagerRequest, ProgramManager.class);

      getPmBankDetails(programManagerRequest, existingProgramManagerRequest, programManager);

      programManager.setUpdatedBy(programManagerRequest.getUpdatedBy());
      programManager.setUpdatedDate(currentTimeStamp);

      // Delete all the child records if already mapped
      deleteChildBankRecords(programManager);

      if (StringUtil
          .isListNotNullNEmpty(existingProgramManagerRequest.getProgramManagerAccountRequests())) {
        Set<ProgramManagerAccount> programManagerAccounts = new HashSet<ProgramManagerAccount>();
        for (ProgramManagerAccountRequest programManagerAccountRequest : existingProgramManagerRequest
            .getProgramManagerAccountRequests()) {
          ProgramManagerAccount programManagerAccount = CommonUtil
              .copyBeanProperties(programManagerAccountRequest, ProgramManagerAccount.class);
          programManagerAccount.setAvailableBalance(
              CommonUtil.getLongAmount(programManagerAccountRequest.getAvailableBalance()));
          programManagerAccount.setCurrentBalance(
              CommonUtil.getLongAmount(programManagerAccountRequest.getCurrentBalance()));
          programManagerAccount.setUpdatedBy(programManagerRequest.getUpdatedBy());
          programManagerAccount.setUpdatedDate(currentTimeStamp);
          programManagerAccounts.add(programManagerAccount);
        }
        programManager.setProgramManagerAccounts(programManagerAccounts);
      }
      logger.info(
          "info:: ProgramManagerServiceImpl ::before updateProgramManager ::createOrUpdateProgramManager method: ");
      programManagerDao.saveOrUpdateProgramManager(programManager);

      logger.info("Exiting:: ProgramManagerServiceImpl :: updateProgramManager method: ");
      return CommonUtil.getSuccessResponse();
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerServiceImpl :: updateProgramManager method: ", e);
      return CommonUtil.getResponse(response, Constants.PROGRAM_MANAGER_CREATION_ERROR,
          Constants.PROGRAM_MANAGER_CREATION_ERROR);
    }
  }

  private void deleteChildBankRecords(ProgramManager programManager) {
    if (!StringUtil.isNull(programManager.getId())) {
      Set<BankProgramManagerMap> bankProgramManagerMaps =
          programManagerDao.findBankProgramManagerMapByProgramManagerId(programManager.getId());
      if (bankProgramManagerMaps != null && bankProgramManagerMaps.size() > 0) {
        programManagerDao.deleteBankProgramManager(bankProgramManagerMaps);
      }
    }
  }

  private void getPmBankDetails(ProgramManagerRequest programManagerRequest,
      ProgramManagerRequest existingProgramManagerRequest, ProgramManager programManager)
          throws ReflectiveOperationException {
    if (StringUtil.isListNotNullNEmpty(programManagerRequest.getBankProgramManagerMapRequests())) {
      Set<BankProgramManagerMap> bankProgramManagerMaps =
          getPMBankMappingData(programManagerRequest);
      programManager.setBankProgramManagerMaps(bankProgramManagerMaps);
    } else {
      if (StringUtil
          .isListNotNullNEmpty(existingProgramManagerRequest.getBankProgramManagerMapRequests())) {
        Set<BankProgramManagerMap> bankProgramManagerMaps =
            getPMBankMappingData(existingProgramManagerRequest);
        programManager.setBankProgramManagerMaps(bankProgramManagerMaps);
      }
    }
  }

  private Boolean isPmNameAlreadyExist(ProgramManagerRequest programManagerRequest,
      List<ProgramManager> listOfProgramManager) {
    Boolean programManagerNameExists = Boolean.FALSE;
    for (ProgramManager programManager : listOfProgramManager) {
      if (programManager.getId().compareTo(programManagerRequest.getId()) != 0) {
        programManagerNameExists = Boolean.TRUE;
        break;
      }
    }
    return programManagerNameExists;
  }

  private Set<BankProgramManagerMap> getPMBankMappingData(
      ProgramManagerRequest existingProgramManagerRequest) throws ReflectiveOperationException {
    Set<BankProgramManagerMap> bankProgramManagerMaps = new HashSet<BankProgramManagerMap>();
    for (BankProgramManagerMapRequest programManagerMapRequest : existingProgramManagerRequest
        .getBankProgramManagerMapRequests()) {
      BankProgramManagerMap bankProgramManagerMap =
          CommonUtil.copyBeanProperties(programManagerMapRequest, BankProgramManagerMap.class);
      bankProgramManagerMaps.add(bankProgramManagerMap);
    }
    return bankProgramManagerMaps;
  }

  @Override
  public Response updateProgramManagerAccount(
      ProgramManagerAccountRequest programManagerAccountRequest) throws ChatakAdminException {
    logger.info("Entering:: ProgramManagerServiceImpl :: updateProgramManagerAccount method: ");
    Response response = new Response();
    try {
      ProgramManagerAccount programManagerAccount =
          programManagerDao.getProgramManagerAccountByIdAndAccountType(
              programManagerAccountRequest.getId(), programManagerAccountRequest.getAccountType());
      programManagerAccount.setNickName(programManagerAccountRequest.getNickName());
      programManagerAccount.setAccountType(programManagerAccountRequest.getAccountType());
      programManagerAccount.setAccountThresholdLimit(
          CommonUtil.getLongAmount(programManagerAccountRequest.getAccountThresholdAmount()));
      programManagerAccount.setSendFundsMode(programManagerAccountRequest.getSendFundsMode());
      programManagerAccount.setBankId(programManagerAccountRequest.getBankId());
      programManagerAccount.setAutoReplenish(programManagerAccountRequest.getAutoRepenish());
      programManagerDao.saveOrUpdateProgramManagerAccount(programManagerAccount);

      logger.info("Exiting:: ProgramManagerServiceImpl :: updateProgramManagerAccount method: ");
      return CommonUtil.getSuccessResponse();
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerServiceImpl :: updateProgramManagerAccount method: ", e);
      return CommonUtil.getResponse(response, Constants.PROGRAM_MANAGER_CREATION_ERROR,
          Constants.PROGRAM_MANAGER_CREATION_ERROR);
    }

  }

  @Override
  public Response updateProgramManagerStatus(ProgramManagerRequest programManagerRequest)
      throws ChatakAdminException {
    logger.info("Entering:: ProgramManagerServiceImpl :: changeProgramManagerStatus method: ");
    try {
      ProgramManagerRequest pmRequest =
          programManagerDao.findProgramManagerById(programManagerRequest.getId());
      Set<BankProgramManagerMap> map = programManagerDao
          .findBankProgramManagerMapByProgramManagerId(programManagerRequest.getId());
      List<ProgramManagerAccount> pmAccounts = programManagerDao
          .getProgramManagerAccountByProgramManagerId(programManagerRequest.getId());
      ProgramManagerResponse response = new ProgramManagerResponse();
      ProgramManager programManager =
          CommonUtil.copyBeanProperties(pmRequest, ProgramManager.class);
      programManager.setUpdatedBy(programManagerRequest.getUpdatedBy());
      programManager.setStatus(programManagerRequest.getStatus());
      programManager.setReason(programManagerRequest.getReason());
      programManager.setUpdatedDate(DateUtil.getCurrentTimestamp());
      programManager.setBankProgramManagerMaps(map);
      programManagerDao.saveOrUpdateProgramManager(programManager);

      for (ProgramManagerAccount programManagerAccount : pmAccounts) {
        programManagerAccount.setUpdatedBy(programManagerRequest.getUpdatedBy());
        programManagerAccount.setStatus(programManagerRequest.getStatus());
        programManagerAccount.setUpdatedDate(DateUtil.getCurrentTimestamp());
        programManagerDao.saveOrUpdateProgramManagerAccount(programManagerAccount);
      }

      response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
      response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
      logger.info("Exiting:: ProgramManagerServiceImpl :: changeProgramManagerStatus method: ");
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerServiceImpl :: changeProgramManagerStatus method.", e);
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()), e);
    }
  }

  public ProgramManagerResponse getProgramManagersByBankId(
      ProgramManagerRequest programManagerRequest) throws ChatakAdminException {
    logger.info("Entering:: ProgramManagerServiceImpl :: getProgramManagersByBankId method: ");
    try {
      ProgramManagerResponse response = new ProgramManagerResponse();
      List<ProgramManagerRequest> programManagersList = new ArrayList<ProgramManagerRequest>();
      Set<BankProgramManagerMap> managerMaps = programManagerDao.findByBankId(
          programManagerRequest.getBankProgramManagerMapRequests().get(0).getBankId());
      if (CommonUtil.isSetNotNullNEmpty(managerMaps)) {
        for (BankProgramManagerMap managerMap : managerMaps) {
          ProgramManagerRequest managerRequest = new ProgramManagerRequest();
          managerRequest.setId(managerMap.getProgramManagerId());
          programManagersList.add(programManagerDao.findProgramManagerById(managerRequest));
        }
      }
      response.setProgramManagersList(programManagersList);
      response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
      response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
      logger.info("Exiting:: ProgramManagerServiceImpl :: getProgramManagersByBankId method: ");
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerServiceImpl :: getProgramManagersByBankId method.", e);
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()), e);
    }
  }

  public ProgramManagerResponse getAllProgramManagers(ProgramManagerRequest programManagerRequest)
      throws ChatakAdminException {
    logger.info("Entering:: ProgramManagerServiceImpl :: getAllProgramManagers method: ");
    try {
      List<ProgramManagerRequest> programManagerRequests =
          programManagerDao.getAllProgramManagers(programManagerRequest);
      ProgramManagerResponse response = new ProgramManagerResponse();
      if (StringUtil.isListNotNullNEmpty(programManagerRequests)) {
        response.setProgramManagersList(programManagerRequests);
      }
      response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
      response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
      logger.info("Exiting:: ProgramManagerServiceImpl :: getAllProgramManagers method: ");
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerServiceImpl :: getAllProgramManagers method.", e);
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()), e);
    }
  }


  public BankResponse findBankByProgramManagerId(ProgramManagerRequest programManagerRequest)
      throws ChatakAdminException {
    logger
        .info("Entering:: ProgramManagerServiceImpl :: getAllBankNamesForProgramManager method: ");
    try {
      BankResponse response = new BankResponse();
      List<BankRequest> bankRequests =
          programManagerDao.getAllBanksForProgramManager(programManagerRequest);
      if (StringUtil.isListNotNullNEmpty(bankRequests)) {
        response.setBankRequests(bankRequests);
        response.setTotalNoOfRows(programManagerRequest.getNoOfRecords());
      }
      response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
      response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
      logger.info("Exiting:: ProgramManagerServiceImpl :: getAllBanksForProgramManager method: ");
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: ProgramManagerServiceImpl :: getAllBankNamesForProgramManager method.",
          e);
      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
          LocaleContextHolder.getLocale()), e);
    }
  }

  private static void setUpdatedValues(ProgramManagerRequest updatedRequest,
      ProgramManagerRequest existingProgramManagerRequest) {
    existingProgramManagerRequest.setProgramManagerName(updatedRequest.getProgramManagerName());
    existingProgramManagerRequest.setCompanyName(updatedRequest.getCompanyName());
    existingProgramManagerRequest.setBusinessName(updatedRequest.getBusinessName());
    existingProgramManagerRequest.setContactName(updatedRequest.getContactName());
    existingProgramManagerRequest.setContactPhone(updatedRequest.getContactPhone());
    existingProgramManagerRequest.setExtension(updatedRequest.getExtension());
    existingProgramManagerRequest.setContactEmail(updatedRequest.getContactEmail());
    existingProgramManagerRequest.setProgramManagerLogo(updatedRequest.getProgramManagerLogo());
    existingProgramManagerRequest
        .setDefaultProgramManager(updatedRequest.getDefaultProgramManager());
  }

  @Override
  public List<Option> getActiveProgramManagers() {
    List<ProgramManager> programManagers = programManagerDao.findAllProgramManagerDetails();
    List<Option> options = new ArrayList<>();
    if (null != programManagers) {
      for (ProgramManager programManager : programManagers) {
        Option option = new Option();
        option.setLabel(programManager.getId().toString());
        option.setValue(programManager.getProgramManagerName());
        options.add(option);
      }
    }
    Collections.sort(options, StringUtil.ALPHABETICAL_ORDER);
    return options;
  }
  
	public ProgramManagerResponse getAllIssuanceProgramManagers(ProgramManagerRequest programManagerRequest)
			throws ChatakAdminException {
		logger.info("Entering:: ProgramManagerServiceImpl :: getAllProgramManagers method: ");
		try {
			ClientResponse response = JsonUtil.postIssuanceRequest(programManagerRequest,
					"/setupServices/setupService/getAllProgramManagers");
			logger.info("Exiting:: ProgramManagerServiceImpl :: getAllIssuanceProgramManagers method: ");
			return validateResponseToGetProgramManagers(response);
		} catch (Exception e1) {
			logger.error("ERROR:: ProgramManagerServiceImpl :: getAllIssuanceProgramManagers method.", e1);
		}
		return null;

	}

	public ProgramManagerResponse getIssuanceProgramManagerById(ProgramManagerRequest programManagerRequest)
			throws ChatakAdminException {
		logger.info("Entering:: ProgramManagerServiceImpl :: getIssuanceProgramManagerById method: ");
		try {
			ClientResponse response = JsonUtil.postIssuanceRequest(programManagerRequest,
					"/setupServices/setupService/getProgramManagerDetailsByProgramManagerId");
			logger.info("Exiting:: ProgramManagerServiceImpl :: getAllBanksForProgramManager method: ");
			return validateResponseToGetProgramManagers(response);
		} catch (Exception e1) {
			logger.error("ERROR:: ProgramManagerServiceImpl :: getIssuanceProgramManagerById method.", e1);
		}
		return null;
	}

	public CardProgramResponse searchCardProgramByProgramManager(
			PartnerGroupPartnerMapRequest partnerGroupPartnerMapRequest) throws ChatakAdminException {
		logger.info("Entering:: ProgramManagerServiceImpl :: searchCardProgramByProgramManager method: ");
		try {
			ClientResponse response = JsonUtil.postIssuanceRequest(partnerGroupPartnerMapRequest,
					"/setupServices/setupService/searchCardProgramByProgramManager");
			logger.info("Exiting:: ProgramManagerServiceImpl :: searchCardProgramByProgramManager method: ");
			return validateResponseToGetCardPrograms(response);
		} catch (Exception e1) {
			logger.error("ERROR:: ProgramManagerServiceImpl :: searchCardProgramByProgramManager method.", e1);
		}
		return null;
	}

	private CardProgramResponse validateResponseToGetCardPrograms(ClientResponse response)
			throws ChatakAdminException, IOException {
		logger.info("Entering:: ProgramManagerServiceImpl :: validateResponseToGetCardPrograms method: ");
		if (response.getStatus() != Constants.SUCCESS_STATUS)
			throw new ChatakAdminException(Properties.getProperty("prepaid.service.call.role.error.message"));
		String output = response.getEntity(String.class);
		CardProgramResponse resultCardProgramList = mapper.readValue(output, CardProgramResponse.class);
		logger.info("Exiting:: ProgramManagerServiceImpl :: validateResponseToGetCardPrograms method: ");
		return resultCardProgramList;
	}

	private ProgramManagerResponse validateResponseToGetProgramManagers(ClientResponse response)
			throws ChatakAdminException, IOException {
		logger.info("Entering:: ProgramManagerServiceImpl :: validateResponseToGetProgramManagers method: ");
		if (response.getStatus() != Constants.SUCCESS_STATUS)
			throw new ChatakAdminException(Properties.getProperty("prepaid.service.call.role.error.message"));
		String output = response.getEntity(String.class);
		ProgramManagerResponse programManagerResponse = mapper.readValue(output, ProgramManagerResponse.class);
		logger.info("Exiting:: ProgramManagerServiceImpl :: validateResponseToGetProgramManagers method: ");
		return programManagerResponse;
	}
}
