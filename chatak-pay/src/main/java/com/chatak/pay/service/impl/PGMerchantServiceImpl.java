package com.chatak.pay.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.chatak.mailsender.exception.PrepaidNotificationException;
import com.chatak.mailsender.service.MailServiceManagement;
import com.chatak.pay.constants.ChatakPayErrorCode;
import com.chatak.pay.constants.Constant;
import com.chatak.pay.controller.model.LoginRequest;
import com.chatak.pay.controller.model.LoginResponse;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.model.Merchant;
import com.chatak.pay.model.MerchantListResponse;
import com.chatak.pay.model.TSMRequest;
import com.chatak.pay.model.TSMResponse;
import com.chatak.pay.service.PGMerchantService;
import com.chatak.pay.util.EncryptionUtil;
import com.chatak.pay.util.JsonUtil;
import com.chatak.pay.util.PasswordHandler;
import com.chatak.pay.util.StringUtil;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.MerchantUserDao;
import com.chatak.pg.acq.dao.TerminalDao;
import com.chatak.pg.acq.dao.model.PGApplicationClient;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.acq.dao.model.PGTerminal;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.model.ApplicationClientDTO;
import com.chatak.pg.model.CurrencyDTO;
import com.chatak.pg.model.MposFeatures;
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
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.chatak.prepaid.velocity.IVelocityTemplateCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 05-May-2015 3:05:00 PM
 * @version 1.0
 */
@Service

public class PGMerchantServiceImpl implements PGMerchantService {
  private static Logger log = LogManager.getLogger(PGMerchantServiceImpl.class);
  
  private static ObjectMapper mapper=new ObjectMapper();	

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private MerchantDao merchantDao;

  @Autowired
  private TerminalDao terminalDao;

  @Autowired
  private MerchantUserDao merchantUserDao;

  @Autowired
  private CurrencyConfigDao currencyConfigDao;

  @Autowired
  MailServiceManagement mailingServiceManagement;

  @Autowired
  IVelocityTemplateCreator iVelocityTemplateCreator;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;

  @Autowired
  MerchantProfileDao merchantProfileDao;

  /**
   * Method to search merchant/merchants detail on various search criteria
   * 
   * @param searchMerchant
   * @return the list of Merchants based on search criteria
   */
  public AddMerchantResponse addMerchant(AddMerchantRequest addMerchantRequest) {
    log.info("RestService | PGMerchantServiceImpl | addMerchant | Entering");

    AddMerchantResponse addMerchantResponse = new AddMerchantResponse();
    String validationMsg = addMerchantRequest.validate();
    if (!validationMsg.equals("")) {
      log.info("RestService | PGMerchantServiceImpl | addMerchant | Required fields missing");
      addMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z6);
      addMerchantResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z6)
              + ", " + validationMsg);
    } else {
      try {

        addMerchantResponse = merchantUpdateDao.addMerchant(addMerchantRequest);
        if (addMerchantResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
          log.info("RestService | PGMerchantServiceImpl | addMerchant | Merchant Creation Success");
        } else {
          log.info(
              "RestService | PGMerchantServiceImpl | addMerchant | Merchant Creation Failed, Try Again");
        }
      }

      catch (Exception e) {
        addMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        addMerchantResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
        log.error(
            "RestService | PGMerchantServiceImpl | addMerchant | Exception :" + e.getMessage(), e);
      }
    }

    log.info("RestService | PGMerchantServiceImpl | addMerchant | Exiting");
    return addMerchantResponse;
  }

  public UpdateMerchantResponse updateMerchant(UpdateMerchantRequest updateMerchantRequest) {
    log.info("RestService | PGMerchantServiceImpl | updateMerchant | Entering");
    UpdateMerchantResponse updateMerchantResponse = new UpdateMerchantResponse();
    String validationMsg = "";
    if (!validationMsg.equals("")) {
      log.info("RestService | PGMerchantServiceImpl | updateMerchant | Required fields missing");
      updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z6);
      updateMerchantResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z6)
              + ", " + validationMsg);
    } else {
      try {

        updateMerchantResponse = merchantUpdateDao.updateMerchant(updateMerchantRequest);
        if (updateMerchantResponse.getErrorCode()
            .equals(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00))) {
          log.info(
              "RestService | PGMerchantServiceImpl | updateMerchant | Merchant details update success");
        } else {
          log.info(
              "RestService | PGMerchantServiceImpl | updateMerchant | Merchant details update failed, Retry");
        }
      } catch (Exception e) {
        updateMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        updateMerchantResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
        log.error(
            "RestService | PGMerchantServiceImpl | updateMerchant | Exception :" + e.getMessage(),
            e);
      }
    }

    log.info("RestService | PGMerchantServiceImpl | updateMerchant | Exiting");
    return updateMerchantResponse;
  }

  public DeleteMerchantResponse deleteMerchant(DeleteMerchantRequest deleteMerchantRequest) {
    log.info("RestService | PGMerchantServiceImpl | deleteMerchant | Entering");
    DeleteMerchantResponse deleteMerchantResponse = new DeleteMerchantResponse();

    if (deleteMerchantRequest.getMerchantCode() == null) {
      log.info("RestService | PGMerchantServiceImpl | deleteMerchant | Required fields missing");
      deleteMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z6);
      deleteMerchantResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z6)
              + ", merchant_code is required field");
    } else {
      try {
        deleteMerchantResponse = merchantProfileDao.deleteMerchant(deleteMerchantRequest);
        if (deleteMerchantResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
          log.info(
              "RestService | PGMerchantServiceImpl | deleteMerchant | Merchant details delete success");
        } else {
          log.info(
              "RestService | PGMerchantServiceImpl | deleteMerchant | Merchant details delete failed, Retry");
        }
      } catch (Exception e) {
        deleteMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        deleteMerchantResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
        log.error(
            "RestService | PGMerchantServiceImpl | deleteMerchant | Exception :" + e.getMessage(),
            e);
      }
    }

    log.info("RestService | PGMerchantServiceImpl | deleteMerchant | Exiting");
    return deleteMerchantResponse;
  }

  public GetMerchantListResponse getMerchantList(GetMerchantListRequest getMerchantListRequest) {
    log.info("RestService | PGMerchantServiceImpl | getMerchantList | Entering");
    GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();

    try {
      getMerchantListResponse = merchantProfileDao.getMerchantlist(getMerchantListRequest);
      if (getMerchantListResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        log.info(
            "RestService | PGMerchantServiceImpl | getMerchantList | Merchant records found for input search criteria");
      } else {
        log.info(
            "RestService | PGMerchantServiceImpl | getMerchantList | Merchant records not found for input search criteria");
      }
    } catch (Exception e) {
      getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      getMerchantListResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      log.error(
          "RestService | PGMerchantServiceImpl | getMerchantList | Exception :" + e.getMessage(),
          e);
    }

    log.info("RestService | PGMerchantServiceImpl | getMerchantList | Exiting");
    return getMerchantListResponse;
  }

  public AddMerchantBankResponse addMerchantBank(AddMerchantBankRequest addMerchantBankRequest) {
    log.info("RestService | PGMerchantServiceImpl | addMerchantBank | Entering");
    AddMerchantBankResponse addMerchantBankResponse = new AddMerchantBankResponse();
    String validationMsg = addMerchantBankRequest.validate();
    if (!validationMsg.equals("")) {
      log.info("RestService | PGMerchantServiceImpl | addMerchantBank | Required fields missing");
      addMerchantBankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z6);
      addMerchantBankResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z6)
              + ", " + validationMsg);
    } else {
      try {
        addMerchantBankResponse = merchantProfileDao.addMerchantBank(addMerchantBankRequest);
        if (addMerchantBankResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
          log.info(
              "RestService | PGMerchantServiceImpl | addMerchantBank | Merchant Bank Details Configured Successfully");
        } else {
          log.info(
              "RestService | PGMerchantServiceImpl | addMerchantBank | Merchant Bank Details Configurtion Failed, Try Again");
        }
      } catch (Exception e) {
        addMerchantBankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        addMerchantBankResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
        log.error(
            "RestService | PGMerchantServiceImpl | addMerchantBank | Exception :" + e.getMessage(),
            e);
      }
    }

    log.info("RestService | PGMerchantServiceImpl | addMerchantBank | Exiting");
    return addMerchantBankResponse;
  }

  public GetMerchantBankDetailsResponse getMerchantBankDetails(
      GetMerchantBankDetailsRequest getMerchantBankDetailsRequest) {
    log.info("RestService | PGMerchantServiceImpl | getMerchantBank | Entering");
    GetMerchantBankDetailsResponse getMerchantBankResponse = new GetMerchantBankDetailsResponse();
    try {

      getMerchantBankResponse = merchantDao.getMerchantBankDetails(getMerchantBankDetailsRequest);
      if (getMerchantBankResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        log.info(
            "RestService | PGMerchantServiceImpl | getMerchantBankDetails | Merchant Bank Details Record Found");
      } else {
        log.info(
            "RestService | PGMerchantServiceImpl | getMerchantBankDetails | Merchant Bank Details Record Not Found");
      }
    } catch (Exception e) {
      getMerchantBankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      getMerchantBankResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      log.error(
          "RestService | PGMerchantServiceImpl | getMerchantBank | Exception :" + e.getMessage(),
          e);
    }

    log.info("RestService | PGMerchantServiceImpl | getMerchantBank | Exiting");
    return getMerchantBankResponse;
  }

  public LoginResponse authenticateMerchantUser(LoginRequest loginRequest) {
    log.info("RestService | PGMerchantServiceImpl | authenticateMerchantUser | Entering");
    LoginResponse loginResponse = new LoginResponse();
    try {
      PGMerchantUsers merchantUsers = merchantUserDao.findByUserName(loginRequest.getUsername());
      if (merchantUsers != null) {
    	if (merchantUsers.getStatus() == PGConstants.STATUS_SUCCESS) {
        log.info(
            "RestService | PGMerchantServiceImpl | authenticateMerchantUser | Merchant user Details Found");
        if (EncryptionUtil.encodePassword(loginRequest.getPassword())
            .equals(merchantUsers.getMerPassword())) {
          PGMerchant pgMerchant =
              merchantProfileDao.getMerchantById(merchantUsers.getPgMerchantId());
          List<String> PGMerchantUserFeatureMapping = merchantUserDao.findByFeatureStatus(merchantUsers.getId());
          loginResponse.setMpsoFeatures(PGMerchantUserFeatureMapping);
          loginResponse.setMerchantCode(pgMerchant.getMerchantCode());
          loginResponse.setBussinessName(pgMerchant.getBusinessName());
          loginResponse.setAddress(pgMerchant.getAddress1());
          loginResponse.setCity(pgMerchant.getCity());
          loginResponse.setState(pgMerchant.getState());
          loginResponse.setCountry(pgMerchant.getCountry());
          loginResponse.setPin(pgMerchant.getPin());

          log.info(
              "fetching the currency details from CURRENCY_CONFIG table based on currency name : "
                  + pgMerchant.getLocalCurrency());
          PGCurrencyConfig pgCurrencyConfig =
              currencyConfigDao.getCurrencyCodeNumeric(pgMerchant.getLocalCurrency());
          validatePGCurrencyConfig(loginRequest, loginResponse, merchantUsers, pgMerchant, pgCurrencyConfig);
          
          if(loginResponse.getTerminalData() != null) {
			if (loginResponse.getTerminalData().getErrorCode().equals(Constant.SIXTEEN.toString())) {
				loginResponse.setErrorCode(Constant.SIXTEEN.toString());
				return loginResponse;
			}
			if (loginResponse.getTerminalData().getErrorCode().equals(Constant.ELEVEN.toString())) {
				loginResponse.setErrorCode(Constant.ELEVEN.toString());
				return loginResponse;
			}
          }
          loginResponse.setErrorCode(ChatakPayErrorCode.GEN_001.name());
          loginResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.GEN_001.name(),
              null, LocaleContextHolder.getLocale()));
          log.info("Setting login  response after validating currency");

          //Check for New User. Means is user login with Temp Password?
          if (merchantUsers.getEmailVerified().equals(Constants.ZERO)) {
            loginResponse.setErrorCode(ChatakPayErrorCode.GEN_003.name());
          }
        } else {
          log.info(ChatakPayErrorCode.GEN_002.name());
          loginResponse.setErrorCode(ChatakPayErrorCode.GEN_002.name());
          loginResponse.setErrorMessage(messageSource.getMessage("chatak.admin.login.error.message",
              null, LocaleContextHolder.getLocale()));
        }
    	} else {
    		loginResponse.setStatus(false);
			loginResponse.setErrorCode(Constants.ERROR_CODE);
			loginResponse.setErrorMessage(messageSource.getMessage("chatak.merchant.user.suspended.error.message",
							null, LocaleContextHolder.getLocale()));
    	}
    	} else {
        loginResponse.setErrorCode(ChatakPayErrorCode.GEN_002.name());
        loginResponse.setErrorMessage(messageSource.getMessage("chatak.admin.login.error.message",
            null, LocaleContextHolder.getLocale()));
        log.info(
            "RestService | PGMerchantServiceImpl | authenticateMerchantUser | Merchant user Details Not Found");
      }
    } catch (Exception e) {
      loginResponse.setErrorCode(ChatakPayErrorCode.TXN_0999.name());
      loginResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(),
          null, LocaleContextHolder.getLocale()));
      log.error("RestService | PGMerchantServiceImpl | authenticateMerchantUser | Exception :"
          + e.getMessage(), e);
    }

    log.info(loginResponse.getErrorCode() + loginResponse.getErrorMessage());
    log.info("RestService | PGMerchantServiceImpl | authenticateMerchantUser | Exiting");
    return loginResponse;
  }

  private void validatePGCurrencyConfig(LoginRequest loginRequest, LoginResponse loginResponse,
		PGMerchantUsers merchantUsers, PGMerchant pgMerchant, PGCurrencyConfig pgCurrencyConfig) throws HttpClientException, IOException {
    log.info("Entering :: PGMerchantServiceImpl :: validatePGCurrencyConfig");
	if (pgCurrencyConfig != null) {
	  log.info("Found Valid Currency");
	    CurrencyDTO currencyDTO = new CurrencyDTO();
	    currencyDTO.setCurrencyCodeAlpha(pgCurrencyConfig.getCurrencyCodeAlpha());
	    currencyDTO.setCurrencyCodeNumeric(pgCurrencyConfig.getCurrencyCodeNumeric());
	    currencyDTO
	        .setCurrencySeparatorPosition(pgCurrencyConfig.getCurrencySeparatorPosition());
	    currencyDTO.setCurrencyExponent(pgCurrencyConfig.getCurrencyExponent());
	    currencyDTO.setCurrencyMinorUnit(pgCurrencyConfig.getCurrencyMinorUnit());
	    currencyDTO.setCurrencyThousandsUnit(pgCurrencyConfig.getCurrencyThousandsUnit());
	    loginResponse.setCurrencyDTO(currencyDTO);
	  }
	  // If TMS is enabled
	  if (Constants.FLAG_TRUE.equals(Properties.getProperty("chatak-tms.enabled", "false"))) {
	    log.info("fetching tms details");
	    // Fetch from TSM any app update available for the device serial number and merchant ID.
	    TSMRequest request = new TSMRequest();
	    request.setMerchantCode(pgMerchant.getMerchantCode());
	    request.setDeviceSerial(loginRequest.getDeviceSerial());
	    request.setCurrentAppVersion(loginRequest.getCurrentAppVersion());
	    request.setUsername(loginRequest.getUsername());
	    request.setMerchantName(pgMerchant.getBusinessName());
	    request.setOs(loginRequest.getOs());
	    request.setOsName(loginRequest.getOsName());
	    request.setOsVersion(loginRequest.getOsVersion());
	    request.setTimeZoneOffset(loginRequest.getTimeZoneOffset());
	    request.setTimeZoneRegion(loginRequest.getTimeZoneRegion());
	    request.setManufacturer(loginRequest.getManufacturer());

	    log.info("Before hitting tms");
	    String output = JsonUtil.sendToTSM(String.class, request,
		        Properties.getProperty("chatak-tsm.service.fetch.merchant"));
	    log.info("TMS Response " + output);
	    TSMResponse tsmResponse=mapper.readValue(output, TSMResponse.class);
	    loginResponse.setTerminalData(tsmResponse);
	  } else {
	    log.info("Chatak TMS Disabled");
	    PGTerminal pgTerminal = terminalDao
	        .getTerminalonMerchantCode(merchantUsers.getPgMerchantId());
	    loginResponse.setTerminalID(String.valueOf(pgTerminal.getTerminalId()));
	  }
	  log.info("Exiting :: PGMerchantServiceImpl :: validatePGCurrencyConfig");
  }

  public MerchantListResponse getMerchantNamesAndMerchantCode() {
    MerchantListResponse response = new MerchantListResponse();

    try {

      List<Map<String, String>> merchantList = merchantUpdateDao.getMerchantList();
      List<Merchant> merchants = new ArrayList<>();

      for (Map<String, String> map : merchantList) {
        StringTokenizer stringTokenizer = new StringTokenizer(map.get("1"), "-");
        Merchant merchant = new Merchant();
        while (stringTokenizer.hasMoreTokens()) {
          merchant.setMerchantCode(stringTokenizer.nextToken());
          merchant.setMerchantName(stringTokenizer.nextToken());
        }
        merchants.add(merchant);
      }

      response.setMerchants(merchants);
    } catch (Exception e) {
      response.setErrorCode(ChatakPayErrorCode.TXN_0999.name());
      response.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(), null,
          LocaleContextHolder.getLocale()));
      log.error("RestService | PGMerchantServiceImpl | authenticateMerchantUser | Exception :"
          + e.getMessage(), e);
    }
    return response;
  }

  /**
   * @param userId
   * @param currentPassword
   * @param newPassword
   * @return
   * @throws ChatakMerchantException
   */
  @Override
  public Boolean changedPassword(String userName, String currentPassword, String newPassword)
      throws ChatakPayException {
    try {
      PGMerchantUsers pgMerchantUsers = merchantUserDao.findByUserName(userName);
      if (pgMerchantUsers.getStatus() == PGConstants.STATUS_SUCCESS) {
      if (!(EncryptionUtil.encodePassword(currentPassword))
          .equals(pgMerchantUsers.getMerPassword()))
        throw new ChatakPayException(messageSource.getMessage("current.password.error.message",
            null, LocaleContextHolder.getLocale()));
      PasswordHandler passwordHandler = new PasswordHandler();
      if (!passwordHandler.validate(newPassword))
        throw new ChatakPayException(messageSource.getMessage("password.policy.format", null,
            LocaleContextHolder.getLocale()));
      String encryptionPassword = EncryptionUtil.encodePassword(newPassword);
      checkPreviousPassword(pgMerchantUsers, encryptionPassword,
          Integer.parseInt(messageSource.getMessage("merchant.user.previous.password.count", null,
              LocaleContextHolder.getLocale())));
      if (!pgMerchantUsers.getStatus().equals(Constants.ONE)
    		  && !pgMerchantUsers.getStatus().equals(Constants.ZERO)) {
    	  throw new ChatakPayException(messageSource.getMessage(
    			  "chatak.merchant.user.inactive.error.message", null, LocaleContextHolder.getLocale()));
      }
      if (pgMerchantUsers.getEmailVerified().equals(Constant.ZERO)
          || pgMerchantUsers.getStatus().equals(1)) {
        pgMerchantUsers.setEmailVerified(PGConstants.ONE);
        pgMerchantUsers.setStatus(PGConstants.ZERO.intValue());
      }
      pgMerchantUsers.setMerPassword(encryptionPassword);
      pgMerchantUsers.setLastPassWordChange(new Timestamp(System.currentTimeMillis()));
      merchantUserDao.createOrUpdateUser(pgMerchantUsers);
      return true;
      } else {
    	  throw new ChatakPayException(messageSource.getMessage("chatak.merchant.user.suspended.error.message",
    			  null, LocaleContextHolder.getLocale()));
      }
     } catch (ChatakPayException e) {
      log.error("ERROR:: PGMerchantServiceImpl:: changedPassword method", e);
      throw new ChatakPayException(e.getMessage());
    } catch (Exception e) {
      log.error("ERROR:: PGMerchantServiceImpl:: changedPassword method", e);
      throw new ChatakPayException(Properties.getProperty("chatak.general.error"));
    }
  }

  private PGMerchantUsers checkPreviousPassword(PGMerchantUsers pgMerchantUsers, String newPassword,
      Integer count) throws ChatakPayException {
    if (StringUtil.isNullAndEmpty(pgMerchantUsers.getPreviousPasswords())) {

      if (pgMerchantUsers.getMerPassword().equals(newPassword)) {
        throw new ChatakPayException(
            messageSource.getMessage("chatak.current.new.password.equal.error.message", null,
                LocaleContextHolder.getLocale()));
      } else {
        pgMerchantUsers.setPreviousPasswords(Constant.SEPARATOR + newPassword + Constant.SEPARATOR
            + pgMerchantUsers.getMerPassword() + Constant.SEPARATOR);
      }
    } else if (pgMerchantUsers.getPreviousPasswords()
        .contains(Constant.SEPARATOR + newPassword + Constant.SEPARATOR)) {
      throw new ChatakPayException(messageSource.getMessage(
          "chatak.previous.password.error.message", null, LocaleContextHolder.getLocale()));
    } else {
      fetchPreviousPasswords(pgMerchantUsers, newPassword, count);
    }
    return pgMerchantUsers;
  }

  private void fetchPreviousPasswords(PGMerchantUsers pgMerchantUsers, String newPassword, Integer count) {
	String[] passwordData = pgMerchantUsers.getPreviousPasswords()
          .substring(1, pgMerchantUsers.getPreviousPasswords().length()).trim()
          .split("\\" + Constant.SEPARATOR);
      if (passwordData.length < count.intValue()) {
        pgMerchantUsers.setPreviousPasswords(
            Constant.SEPARATOR + newPassword + pgMerchantUsers.getPreviousPasswords());
      } else {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < passwordData.length - 1; i++) {
          sb.append((i == 0) ? Constant.SEPARATOR + passwordData[i] + Constant.SEPARATOR
              : passwordData[i] + Constant.SEPARATOR);
        }
        pgMerchantUsers.setPreviousPasswords(Constant.SEPARATOR + newPassword + sb.toString());
      }
  }


  @Override
  public Boolean forgotPassword(String userName, String baseUrl) throws ChatakPayException {

    PGMerchantUsers pgMerchantUsers = merchantUserDao.findByUserName(userName);
    if (pgMerchantUsers == null)
      throw new ChatakPayException(messageSource.getMessage(
          "chatak.merchant.user.not.exist.error.message", null, LocaleContextHolder.getLocale()));

    if (pgMerchantUsers.getStatus().equals(0)) {
      String emailToken =
          StringUtil.getEmailToken(pgMerchantUsers.getId().toString(), pgMerchantUsers.getEmail());
      pgMerchantUsers.setEmailVerified(0);
      pgMerchantUsers.setEmailToken(emailToken);
      merchantUserDao.createOrUpdateUser(pgMerchantUsers);
      String url =
          baseUrl + Properties.getProperty("chatak.merchant.forgot.password.redirection.url")
              + "?t=" + emailToken;

      Map<String, String> map = new HashMap<>();
      map.put("url", url);
      map.put("username", pgMerchantUsers.getUserName());
      try {
        String body = iVelocityTemplateCreator.createEmailTemplate(map,
            Properties.getProperty("prepaid.email.template.file.path")
                + "/merchant_forgot_password.vm");
        mailingServiceManagement.sendMailHtml(Properties.getProperty("prepaid.from.email.id"), body,
            pgMerchantUsers.getEmail(),
            messageSource.getMessage("prepaid.email.merchant.forgot.password.subject", null,
                LocaleContextHolder.getLocale()));
      } catch (PrepaidNotificationException e) {
        log.error("ERROR:: PGMerchantServiceImpl:: forgotPassword method", e);
        throw new ChatakPayException(messageSource.getMessage(
            "chatak.merchant.user.inactive.error.message", null, LocaleContextHolder.getLocale()));
      } catch (Exception e) {
        log.error("ERROR:: PGMerchantServiceImpl:: forgotPassword method", e);
        throw new ChatakPayException(e.getMessage());
      }
      log.info("");
      log.info("Token URL is " + url);
      log.info("");
      return true;
    } else {
      throw new ChatakPayException(messageSource.getMessage(
          "chatak.merchant.user.inactive.error.message", null, LocaleContextHolder.getLocale()));
    }
  }

  public ApplicationClientDTO getApplicationClientAuth(String appAuthUser) {
    try {
      log.info("Entering :: PGMerchantServiceImpl :: getApplicationClientAuth");
      PGApplicationClient applicationClient = merchantUserDao.getApplicationClientAuth(appAuthUser);
      if (applicationClient != null) {
        ApplicationClientDTO applicationClientDTO =
            CommonUtil.copyBeanProperties(applicationClient, ApplicationClientDTO.class);
        log.info("Exiting :: PGMerchantServiceImpl :: getApplicationClientAuth");
        return applicationClientDTO;
      }
    } catch (Exception e) {
      log.error("ERROR: AdminUserHandlerImpl:: getApplicationClientAuth method", e);
    }
    return null;
  }

  public void saveOrUpdateApplicationClient(PGApplicationClient applicationClient) {
    merchantUserDao.saveOrUpdateApplicationClient(applicationClient);
  }
  
  public PGMerchantUsers fetchMerchantUserByUserName(String userName) {
	  return merchantUserDao.findByUserName(userName);
  }
  
}
