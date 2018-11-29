package com.chatak.acquirer.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.chatak.acquirer.admin.constants.StatusConstants;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.BankSearchResponse;
import com.chatak.acquirer.admin.service.BankService;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.acq.dao.BankDao;
import com.chatak.pg.acq.dao.CountryDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.model.BankProgramManagerMap;
import com.chatak.pg.acq.dao.model.PGBank;
import com.chatak.pg.acq.dao.model.PGBankCurrencyMapping;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGState;
import com.chatak.pg.acq.dao.repository.StateRepository;
import com.chatak.pg.bean.CountryRequest;
import com.chatak.pg.bean.Response;
import com.chatak.pg.bean.TimeZoneRequest;
import com.chatak.pg.bean.TimeZoneResponse;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.Bank;
import com.chatak.pg.model.CurrencyDTO;
import com.chatak.pg.user.bean.BankRequest;
import com.chatak.pg.user.bean.BankResponse;
import com.chatak.pg.user.bean.GetBankListResopnse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.StringUtils;

@Service
public class BankServiceImpl implements BankService, PGConstants {

  private static Logger logger = Logger.getLogger(MerchantServiceImpl.class);

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private BankDao bankDao;

  @Autowired
  private CountryDao countryDao;

  @Autowired
  private StateRepository stateRepository;

  @Autowired
  private CurrencyConfigServiceImpl currencyService;

  @Autowired
  private CurrencyConfigService currencyConfigService;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;

  @Autowired
  ProgramManagerDao programManagerDao;

  @Override
  public BankResponse createBank(Bank bank) throws ChatakAdminException {
    logger.info("Entering:: BankServiceImpl:: createBank method");
    BankRequest bankRequest = new BankRequest();
    setBankRequestValue(bank, bankRequest);
    bankRequest.setCreatedBy(bank.getCreatedBy());
    bankRequest.setCurrencyCodeAlpha(bank.getCurrencyCodeAlpha());
    bankRequest.setCurrencyId(bank.getCurrencyId());
    BankResponse bankResponse = bankDao.createBank(bankRequest);
    logger.info("Exiting:: BankServiceImpl:: createBank method");
    return bankResponse;
  }

  private void setBankRequestValue(Bank bank, BankRequest bankRequest) {
    bankRequest.setBankName(bank.getBankName());
    bankRequest.setBankShortName(bank.getBankShortName());
    bankRequest.setAcquirerId(bank.getAcquirerId());
    bankRequest.setAddress1(bank.getAddress1());
    bankRequest.setAddress2(bank.getAddress2());
    bankRequest.setCity(bank.getCity());
    bankRequest.setCountry(bank.getCountry());
    bankRequest.setState(bank.getState());
    bankRequest.setZip(bank.getZip());
    bankRequest.setBankCode(bank.getBankCode());
    bankRequest.setSettlRoutingNumber(bank.getSettlRoutingNumber());
    bankRequest.setExtension(bank.getExtension());
    bankRequest.setSettlAccountNumber(bank.getSettlAccountNumber());
    bankRequest.setContactPersonCell(bank.getContactPersonCell());
    bankRequest.setContactPersonEmail(bank.getContactPersonEmail());
    bankRequest.setContactPersonFax(bank.getContactPersonFax());
    bankRequest.setContactPersonName(bank.getContactPersonName());
    bankRequest.setContactPersonPhone(bank.getContactPersonPhone());
  }

  @Override
  public BankResponse updateBank(Bank bank) throws ChatakAdminException {
    logger.info("Entering:: BankServiceImpl:: updateBank method");
    BankRequest bankRequest = new BankRequest();
    setBankRequestValue(bank, bankRequest);
    bankRequest.setStatus(S_STATUS_ACTIVE);
    bankRequest.setUpdatedBy(bank.getUpdatedBy());
    BankResponse bankResponse = bankDao.updateBank(bankRequest);
    logger.info("Exiting:: BankServiceImpl:: updateBank method");
    return bankResponse;
  }

  @Override
  public Bank findByBankName(Bank bank) throws ChatakAdminException {
    logger.info("Entering:: BankServiceImpl:: findByBankName method");
    PGBank pgBank = bankDao.getBankByName(bank.getBankName());
    if (null != pgBank) {
      bank.setBankName(pgBank.getBankName());
      bank.setBankShortName(pgBank.getBankShortName());
      bank.setAcquirerId(pgBank.getAcqirerId());
      bank.setAddress1(pgBank.getAddress1());
      bank.setAddress2(pgBank.getAddress2());
      bank.setCity(pgBank.getCity());
      bank.setCountry(pgBank.getCountry());
      bank.setState(pgBank.getState());
      bank.setStatus(pgBank.getStatus());
      bank.setZip(pgBank.getZip());
      bank.setCurrencyId(pgBank.getCurrencyId());
      bank.setBankCode(pgBank.getBankCode());
      bank.setSettlRoutingNumber(pgBank.getSettlRoutingNumber());
      bank.setExtension(pgBank.getExtension());
      bank.setSettlAccountNumber(pgBank.getSettlAccountNumber());
      if (StringUtils.isNotNullAndEmpty(pgBank.getContactPersonCell())) {
    	  bank.setContactPersonCell(pgBank.getContactPersonCell().replaceAll("[-+.^:,]",""));		
      } else {
    	  bank.setContactPersonCell(pgBank.getContactPersonCell());
      }
      bank.setContactPersonEmail(pgBank.getContactPersonEmail());
      bank.setContactPersonFax(pgBank.getContactPersonFax());
      bank.setContactPersonName(pgBank.getContactPersonName());
      if (StringUtils.isNotNullAndEmpty(pgBank.getContactPersonPhone())) {
    	  bank.setContactPersonPhone(pgBank.getContactPersonPhone().replaceAll("[-+.^:,]",""));		
      } else {
    	  bank.setContactPersonPhone(pgBank.getContactPersonPhone());
      }
      CurrencyDTO currencyDTO = currencyService.getCurrencyConfigById(pgBank.getCurrencyId());
      bank.setCurrencyCodeAlpha(currencyDTO.getCurrencyCodeAlpha());
    }
    logger.info("Exiting:: BankServiceImpl:: findByBankName method");
    return bank;
  }

  @Override
  public BankSearchResponse searchBank(Bank bank) throws ChatakAdminException {
    logger.info("Entering:: BankServiceImpl:: searchBank method");
    BankRequest bankSerachRequest = new BankRequest();
    GetBankListResopnse getBankSearchResponse;
    BankSearchResponse bankSearchResponse = new BankSearchResponse();
    bankSerachRequest.setBankName(bank.getBankName());
    bankSerachRequest.setBankCode(bank.getBankCode());
    bankSerachRequest.setContactPersonEmail(bank.getContactPersonEmail());
    bankSerachRequest.setStatus(bank.getStatus());
    bankSerachRequest.setPageSize(bank.getPageSize());
    bankSerachRequest.setPageIndex(bank.getPageIndex());
    bankSerachRequest.setNoOfRecords(bank.getNoOfRecords());

    getBankSearchResponse = bankDao.getBanklist(bankSerachRequest);

    List<PGBank> pgBanks = getBankSearchResponse.getBanks();

    if (!CollectionUtils.isEmpty(pgBanks)) {
      List<Bank> banks = new ArrayList<Bank>(pgBanks.size());
      Bank bankRespObj = null;
      for (PGBank pgBank : pgBanks) {
        bankRespObj = new Bank();
        bankRespObj.setBankName(pgBank.getBankName());
        bankRespObj.setBankCode(pgBank.getBankCode());
        bankRespObj.setContactPersonEmail(pgBank.getContactPersonEmail());
        bankRespObj.setStatus(pgBank.getStatus());
        bankRespObj.setLoginuserType(bank.getLoginuserType());
        CurrencyDTO currencyDTO =
            currencyConfigService.getCurrencyConfigById(pgBank.getCurrencyId());
        bankRespObj.setCurrencyCodeAlpha(currencyDTO.getCurrencyCodeAlpha());
        banks.add(bankRespObj);
      }
      bankSearchResponse.setBanks(banks);
    }
    bankSearchResponse.setTotalNoOfRows(getBankSearchResponse.getTotalNoOfRows());
    logger.info("Exiting:: BankServiceImpl:: searchBank method");
    return bankSearchResponse;
  }

  @Override
  public BankResponse deleteBankByName(String bankName) {
    logger.info("Entering:: BankServiceImpl:: deleteBankByName method");
    BankResponse response = bankDao.deleteBank(bankName);
    if (response.getErrorCode().equals(ActionErrorCode.ERROR_CODE_01)) {
      response.setErrorMessage(messageSource.getMessage("chatak.bank.pm.association.error", null,
          LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting :: BankServiceImpl :: deleteBankByName");
    return response;
  }

  @Override
  public List<Option> getCountries() {
    List<CountryRequest> countryRequests = countryDao.findAllCountries();
    List<Option> options = new ArrayList<Option>();
    if (countryRequests != null) {
      for (CountryRequest countryRequest : countryRequests) {
        Option option = new Option();
        option.setLabel(countryRequest.getName());
        option.setValue(countryRequest.getName());
        options.add(option);
      }
    }
    Collections.sort(options, ALPHABETICAL_ORDER);
    return options;
  }

  @Override
  public Response getStatesByCountry(String countryId) throws ChatakAdminException {
    Response response = new Response();
    CountryRequest countryRequest = countryDao.getCountryByName(countryId);
    if (countryRequest.getName() != null) {

      List<PGState> pgStates = stateRepository.findByCountryId(countryRequest.getId());

      List<Option> options = new ArrayList<Option>(pgStates.size());
      for (PGState state : pgStates) {
        Option option = new Option();
        option.setValue(state.getName());
        option.setLabel(state.getName());
        options.add(option);
      }
      Collections.sort(options, ALPHABETICAL_ORDER);
      response.setResponseList(options);
      response.setTotalNoOfRows(options.size());
      response.setErrorCode("00");
      response.setErrorMessage("SUCCESS");

    } else {
      response.setErrorCode("99");
      response.setErrorMessage("failure");
    }
    return response;
  }

  /**
   * Comparator method for option class
   */
  private static Comparator<Option> ALPHABETICAL_ORDER = ((Option str1, Option str2) -> {
    int res = String.CASE_INSENSITIVE_ORDER.compare(str1.getValue(), str2.getValue());
    if (res == 0) {
      res = str1.getValue().compareTo(str2.getValue());
    }
    return res; });

  @Override
  public List<Option> getBankData() throws ChatakAdminException {
    List<PGBank> bankList = bankDao.getBankData();

    List<PGBank> pgBank = bankList;
    List<Option> bankNames =
        new ArrayList<Option>(CommonUtil.isListNotNullAndEmpty(pgBank) ? pgBank.size() : 0);

    if (bankList != null) {
      Option bankNamesList = null;
      for (PGBank pgBankList : pgBank) {
        bankNamesList = new Option();
        bankNamesList.setValue(pgBankList.getId().toString());
        bankNamesList.setLabel(pgBankList.getBankName());
        bankNames.add(bankNamesList);
      }
    }

    return bankNames;
  }

  @Override
  public Response getCurrencyByBankId(Long bankId) throws ChatakAdminException {
    Response response = new Response();

    List<PGBankCurrencyMapping> currencymappingList = bankDao.getCurrencyByBankId(bankId);

    List<Option> currencies = null;

    if (currencymappingList != null) {
      currencies = new ArrayList<Option>();

      Option mapping = null;
      for (PGBankCurrencyMapping pgBankCurrencyMapping : currencymappingList) {
        mapping = new Option();
        mapping.setLabel(pgBankCurrencyMapping.getCurrencyCodeAlpha());
        mapping.setValue(pgBankCurrencyMapping.getId().toString());
        currencies.add(mapping);
      }

      response.setResponseList(currencies);
      response.setErrorCode("00");
      response.setErrorMessage("SUCCESS");
      response.setTotalNoOfRows(currencies.size());
    } else {
      response.setErrorCode("99");
      response.setErrorMessage("failure");
    }
    return response;
  }


  @Override
  public Response getBankName(Long currencyId) {
    List<PGBank> pgList = bankDao.getBankName(currencyId);
    Response response = new Response();
    if (pgList != null) {
      List<Option> options = new ArrayList<Option>(pgList.size());
      Option option = null;
      for (PGBank pgBankList : pgList) {
        option = new Option();
        option.setValue(pgBankList.getId().toString());
        option.setLabel(pgBankList.getBankName());
        options.add(option);
      }

      response.setResponseList(options);
      response.setErrorCode("00");
      response.setTotalNoOfRows(options.size());

    } else {
      response.setErrorCode("99");
      response.setErrorMessage("failure");
    }
    return response;
  }

  @Override
  public BankResponse changeBankStatus(Bank bank) throws ChatakAdminException {
    logger.info("Entering:: BankServiceImpl:: changeBankStatus method");
    BankResponse bankResponse = new BankResponse();
    try {
      if (null != bank) {
        PGBank pgBank = bankDao.getBankByName(bank.getBankName());
        if (S_STATUS_SUSPENDED.equals(bank.getStatus())) {
          List<PGMerchant> pgMerchantList = merchantUpdateDao.findByBankId(pgBank.getId());
          getPgmerchantValue(pgMerchantList);
        }
        Set<BankProgramManagerMap> set = programManagerDao.findByBankId(pgBank.getId());
        if (!set.isEmpty()) {
          logger.info("Select bank linked to " + set.size() + " Entitie(s)");
          bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
          bankResponse.setErrorMessage(messageSource.getMessage("chatak.bank.pm.association.error",
              null, LocaleContextHolder.getLocale()));
          return bankResponse;
        }
        pgBank.setStatus(bank.getStatus());
        pgBank.setReason(bank.getReason());
        bankDao.createOrUpdateBank(pgBank);
        bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        bankResponse.setErrorMessage(messageSource.getMessage("chatak.bank.status.change.sucess",
            null, LocaleContextHolder.getLocale()));
      }
    } catch (DataAccessException e) {
      logger.error("ERROR:: BankServiceImpl:: changeBankStatus method", e);
      bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
      bankResponse.setErrorMessage(
          messageSource.getMessage("chatak.general.error", null, LocaleContextHolder.getLocale()));
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: BankServiceImpl:: changeBankStatus method", e);
      bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_02);
      bankResponse.setErrorMessage(messageSource.getMessage(
          "chatak.bank.merchant.association.error", null, LocaleContextHolder.getLocale()));
    }
    logger.info("Exiting:: BankServiceImpl:: changeBankStatus method");
    return bankResponse;
  }

	@Override
	public TimeZoneResponse searchAllTimeZone(Long countryId) throws ChatakAdminException {
		TimeZoneResponse timeZoneResponse = new TimeZoneResponse();
		logger.info("Entering:: CountryHandlerImpl:: searchAllTimeZone method: ");
		if(StringUtil.isNull(countryId)){
			throw new ChatakAdminException(ActionErrorCode.ERROR_CODE_TIME_ZONE_02);
		}
		logger.info("info:: CountryHandlerImpl::before findAllTimeZone:: searchAllTimeZone method: ");
		List<TimeZoneRequest> timeZoneList = countryDao.findAllTimeZone(countryId);
		if (StringUtil.isListNullNEmpty(timeZoneList)) {
			throw new ChatakAdminException(ActionErrorCode.ERROR_CODE_TIME_ZONE_03);
		}
		
		List<TimeZoneRequest> timeZoneRequestList = null;
		try{
			timeZoneRequestList = CommonUtil.copyListBeanProperty(timeZoneList, TimeZoneRequest.class);	
		}catch(Exception e){
			logger.error("Error in retrieving all the time zone", e);
			throw new ChatakAdminException(ActionErrorCode.ERROR_CODE_TIME_ZONE_03);
		}
		
		timeZoneResponse.setListOfTimeZoneRequests(timeZoneRequestList);
		getSuccessResponse(timeZoneResponse);
		logger.info("Exiting:: CountryHandlerImpl:: searchAllTimeZone method: ");
		return timeZoneResponse;
	}
	
	@Override
	public  Response getTimeZone(Long timeZoneId) throws ChatakAdminException {
		logger.info("Entering:: CountryHandlerImpl:: getTimeZone method: ");
		TimeZoneResponse timeZoneResponse = new TimeZoneResponse();
		TimeZoneRequest timeZoneRequest = countryDao.findTimeZoneByID(timeZoneId);
		
		if (StringUtil.isNull(timeZoneRequest)) {
			throw new ChatakAdminException(ActionErrorCode.ERROR_CODE_TIME_ZONE_03);
		}
		
		List<TimeZoneRequest> timeZoneRequestList = new ArrayList<>();
		timeZoneRequestList.add(timeZoneRequest);
		timeZoneResponse.setListOfTimeZoneRequests(timeZoneRequestList);
		getSuccessResponse(timeZoneResponse);
		logger.info("Exiting:: CountryHandlerImpl:: getTimeZone method: ");
		return timeZoneResponse;
	}
  
  private void getPgmerchantValue(List<PGMerchant> pgMerchantList) throws ChatakAdminException {
    if (CommonUtil.isListNotNullAndEmpty(pgMerchantList)) {
      for (PGMerchant pgMerchant : pgMerchantList) {
        if (STATUS_ACTIVE == pgMerchant.getStatus()) {
          throw new ChatakAdminException();
        }
      }
    }
  }
  
  public static Response getSuccessResponse(Response response){
	    response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
	    response.setErrorMessage(StatusConstants.STATUS_CODE_SUCCESS);
	    return response;
	  }

	  public static Response getErrorResponse(){
	    Response response = new Response();
	    response.setErrorCode(StatusConstants.STATUS_MESSAGE_FAILED);
	    response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILURE);
	    return response;
	  }

	  public static Response getSuccessResponse(){
	    Response response = new Response();
	    response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
	    response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
	    return response;
	  }
	  
	  @Override
	  public Response getPmCountryById(Long countryId) throws ChatakAdminException {
	    Response response = new Response();
	    CountryRequest countryRequest = countryDao.findCountryByID(countryId);
	    if (countryRequest.getName() != null) {

	      List<PGState> pgStates = stateRepository.findByCountryId(countryRequest.getId());

	      List<Option> options = new ArrayList<Option>(pgStates.size());
	      for (PGState state : pgStates) {
	        Option option = new Option();
	        option.setValue(state.getName());
	        option.setLabel(state.getId().toString());
	        options.add(option);
	      }
	      Collections.sort(options, ALPHABETICAL_ORDER);
	      response.setResponseList(options);
	      response.setTotalNoOfRows(options.size());
	      response.setErrorCode("00");
	      response.setErrorMessage("SUCCESS");

	    } else {
	      response.setErrorCode("99");
	      response.setErrorMessage("failure");
	    }
	    return response;
	  }
	  
	  
	  @Override
	  public List<Option> getCountry() {
	    List<CountryRequest> countryRequests = countryDao.findAllCountries();
	    List<Option> options = new ArrayList<Option>();
	    if (countryRequests != null) {
	      for (CountryRequest countryRequest : countryRequests) {
	        Option option = new Option();
	        option.setLabel(countryRequest.getId().toString());
	        option.setValue(countryRequest.getName());
	        options.add(option);
	      }
	    }
	    Collections.sort(options, ALPHABETICAL_ORDER);
	    return options;
	  }
}
