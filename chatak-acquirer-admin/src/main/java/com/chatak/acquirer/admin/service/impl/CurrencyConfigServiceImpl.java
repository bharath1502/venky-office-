package com.chatak.acquirer.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.CurrenyValue;
import com.chatak.acquirer.admin.service.CurrencyConfigService;
import com.chatak.acquirer.admin.util.CommonUtil;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.CurrencyDao;
import com.chatak.pg.acq.dao.model.PGBank;
import com.chatak.pg.acq.dao.model.PGCurrencyCode;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.repository.BankRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.CurrencyDTO;
import com.chatak.pg.user.bean.CurrencyDTOList;
import com.chatak.pg.util.DateUtil;

@Service("currencyConfigService")
public class CurrencyConfigServiceImpl implements CurrencyConfigService,PGConstants {
	
	private static Logger logger = Logger.getLogger(CurrencyConfigServiceImpl.class);
	
	@Autowired
	CurrencyConfigDao currencyConfigDao;
	
	@Autowired
	CurrencyDao currencyDao;
	
	@Autowired
	BankRepository bankRepository;
	
	@Autowired
	MerchantRepository merchantRepository;
	
    @Autowired
	MessageSource messageSource;

	@Override
	public CurrencyDTO saveCurrencyConfig(CurrencyDTO currencyDTO) throws ChatakAdminException {
		
		logger.info("CurrencyConfigServiceImpl | saveCurrencyConfig | Entering");	
		try{
			PGCurrencyConfig pgCurrencyConfig = new PGCurrencyConfig();
			
			setPGCurrencyConfigDetails(currencyDTO, pgCurrencyConfig);
			
			PGCurrencyConfig pgCurrencyConfigResponse = currencyConfigDao.saveCurrencyConfig(pgCurrencyConfig);
			if(pgCurrencyConfigResponse != null ) {
				currencyDTO.setErrorMessage(messageSource.getMessage("chatak.acquirer.createcurrency.success.message", null,
						LocaleContextHolder.getLocale()));
				currencyDTO.setErrorCode(ActionErrorCode.ERROR_CODE_00);
			} else {
				currencyDTO.setErrorMessage(messageSource.getMessage("chatak.acquirer.createcurrency.failure.message", null,
						LocaleContextHolder.getLocale()));
				currencyDTO.setErrorCode(ActionErrorCode.ERROR_CODE_CURRENCY_CREATE);
			}
			} catch(Exception exception ) {
				logger.error("ERROR:: CurrencyConfigServiceImpl:: saveCurrencyConfig method", exception);
				throw new ChatakAdminException(exception.getMessage());
			}
			logger.info("CurrencyConfigServiceImpl | saveCurrencyConfig | Exiting");
		return currencyDTO;
	}

	private void setPGCurrencyConfigDetails(CurrencyDTO currencyDTO, PGCurrencyConfig pgCurrencyConfig) {
		pgCurrencyConfig.setCurrencyName(currencyDTO.getCurrencyName().toUpperCase());
		pgCurrencyConfig.setCurrencyCodeNumeric(currencyDTO.getCurrencyCodeNumeric());
		pgCurrencyConfig.setCurrencyCodeAlpha(currencyDTO.getCurrencyCodeAlpha().toUpperCase());
		pgCurrencyConfig.setCurrencyExponent(currencyDTO.getCurrencyExponent());
		pgCurrencyConfig.setCurrencySeparatorPosition(currencyDTO.getCurrencySeparatorPosition());
		pgCurrencyConfig.setCurrencyMinorUnit(currencyDTO.getCurrencyMinorUnit());
		pgCurrencyConfig.setCurrencyThousandsUnit(currencyDTO.getCurrencyThousandsUnit());
		pgCurrencyConfig.setStatus(PGConstants.STATUS_SUCCESS);
		pgCurrencyConfig.setCreatedDate(DateUtil.getCurrentTimestamp());
		pgCurrencyConfig.setCreatedBy(currencyDTO.getCreatedBy());
		pgCurrencyConfig.setModifiedDate(DateUtil.getCurrentTimestamp());
	}

	@Override
	public Response searchCurrencyConfig(CurrencyDTO currencyDTO) throws ChatakAdminException {
		logger.info("CurrencyConfigServiceImpl | searchCurrencyConfig | Entering");
		CurrenyValue currenyValue=null;
		List<CurrenyValue> currencyDTOs = new ArrayList<CurrenyValue>();
		List<PGCurrencyConfig> pgCurrencyConfigList = currencyConfigDao.searchCurrencyConfig(currencyDTO);
		if(StringUtil.isListNotNullNEmpty(pgCurrencyConfigList)){
			for(PGCurrencyConfig pgCurrencyConfig: pgCurrencyConfigList){
				currenyValue = new CurrenyValue();
				currenyValue.setId(pgCurrencyConfig.getId());
				currenyValue.setCurrencyName(pgCurrencyConfig.getCurrencyName());
				currenyValue.setCurrencyCodeNumeric(pgCurrencyConfig.getCurrencyCodeNumeric());
				currenyValue.setCurrencyCodeAlpha(pgCurrencyConfig.getCurrencyCodeAlpha());
				currenyValue.setCurrencyExponent(pgCurrencyConfig.getCurrencyExponent());
				if (pgCurrencyConfig.getStatus() == STATUS_SUCCESS){
					currenyValue.setStatus(S_STATUS_ACTIVE);
        		}else if (pgCurrencyConfig.getStatus() == STATUS_PENDING) {
        			currenyValue.setStatus(S_STATUS_PENDING);
        		}
        		 else if (pgCurrencyConfig.getStatus() == STATUS_DELETED) {
        			 currenyValue.setStatus(S_STATUS_DELETED);
         		} else if (pgCurrencyConfig.getStatus() == STATUS_SUSPENDED) {
         			currenyValue.setStatus(S_STATUS_SUSPENDED);
         		}
				currencyDTOs.add(currenyValue);
			}
		}
		Response response = CommonUtil.getSuccessResponse(); 
		response.setTotalNoOfRows(currencyDTO.getNoOfRecords());
		response.setResponseList(currencyDTOs);
		logger.info("CurrencyConfigServiceImpl | searchCurrencyConfig | Exiting");
		return response;
	}

	@Override
	public CurrencyDTO getCurrencyConfigById(Long currencyConfigId) throws ChatakAdminException {

		CurrencyDTO currencyDTO = new CurrencyDTO();
		
		PGCurrencyConfig currencyConfig=currencyConfigDao.findByCurrencyConfigId(currencyConfigId);
		if(currencyConfig!=null){
			currencyDTO.setId(currencyConfig.getId());
			currencyDTO.setCurrencyName(currencyConfig.getCurrencyName());
			currencyDTO.setCurrencyCodeNumeric(currencyConfig.getCurrencyCodeNumeric());
			currencyDTO.setCurrencyCodeAlpha(currencyConfig.getCurrencyCodeAlpha());
			currencyDTO.setCurrencyExponent(currencyConfig.getCurrencyExponent());
			currencyDTO.setCurrencyMinorUnit(currencyConfig.getCurrencyMinorUnit());
			currencyDTO.setCurrencySeparatorPosition(currencyConfig.getCurrencySeparatorPosition());
			currencyDTO.setCurrencyThousandsUnit(currencyConfig.getCurrencyThousandsUnit());
			currencyDTO.setStatus(currencyConfig.getStatus());
			return currencyDTO;
		}
		return currencyDTO;
	}
	
	@Override
	public CurrencyDTO updateCurrencyConfig(CurrencyDTO currencyDTO) throws ChatakAdminException {
		
		logger.info("CurrencyConfigServiceImpl | updateCurrencyConfig | Entering");	
		try{
			PGCurrencyConfig pgCurrencyConfig=currencyConfigDao.findByCurrencyConfigId(currencyDTO.getId());
			if(pgCurrencyConfig!=null){
			setPGCurrencyConfigDetails(currencyDTO, pgCurrencyConfig);
			pgCurrencyConfig.setUpdatedBy(currencyDTO.getCreatedBy());
			
			PGCurrencyConfig pgCurrencyConfigResponse = currencyConfigDao.updateCurrencyConfig(pgCurrencyConfig);
			if(pgCurrencyConfigResponse != null ){
				currencyDTO.setErrorMessage("Successfully update !");
				currencyDTO.setErrorCode(ActionErrorCode.ERROR_CODE_00);
			}else{
				currencyDTO.setErrorMessage("Failed to update!");
				currencyDTO.setErrorCode(ActionErrorCode.ERROR_CODE_MDR1);
			 }
			}
			}catch(Exception exception ){
				logger.error("ERROR:: CurrencyConfigServiceImpl:: updateCurrencyConfig method", exception);
				throw new ChatakAdminException(exception.getMessage());
			}
			logger.info("CurrencyConfigServiceImpl | updateCurrencyConfig | Exiting");
		return currencyDTO;
	
	}

	@Override
	public CurrencyDTO deleteCurrencyConfig(Long currencyConfigId) throws ChatakAdminException {
		
		CurrencyDTO currencyDTO=new CurrencyDTO();
		try {
				PGCurrencyConfig pgCurrencyConfig = currencyConfigDao.findByCurrencyConfigId(currencyConfigId);
				if (null != pgCurrencyConfig) {
					
					List<PGBank> pgBank = bankRepository.findByCurrencyIdAndStatusLike(currencyConfigId, PGConstants.S_STATUS_ACTIVE);
					if (StringUtil.isListNotNullNEmpty(pgBank)) {
						currencyDTO.setErrorCode(ActionErrorCode.ERROR_CODE_CURRENCY_LINKED);
						currencyDTO.setErrorMessage(messageSource.getMessage("chatak.acquirer.deletecurrency.linked.error", null,
								LocaleContextHolder.getLocale()));
						return currencyDTO;
					}
					List<PGMerchant> pgMerchant = merchantRepository.getMerchantByLocalCurrency(pgCurrencyConfig.getCurrencyCodeAlpha());
					if (StringUtil.isListNotNullNEmpty(pgMerchant)) {
						currencyDTO.setErrorCode(ActionErrorCode.ERROR_CODE_CURRENCY_LINKED);
						currencyDTO.setErrorMessage(messageSource.getMessage("chatak.acquirer.deletecurrency.linked.error", null,
								LocaleContextHolder.getLocale()));
						return currencyDTO;
					}
					
					pgCurrencyConfig.setStatus(PGConstants.STATUS_DELETED);
					PGCurrencyConfig pgCurrencyConfigStatus = currencyConfigDao.updateCurrencyConfig(pgCurrencyConfig);
					if(null != pgCurrencyConfigStatus){
						currencyDTO.setErrorCode(PGConstants.SUCCESS);
						currencyDTO.setErrorMessage(messageSource.getMessage("chatak.acquirer.deletecurrency.delete.message", null,
								LocaleContextHolder.getLocale()));
						return currencyDTO;	
					}
				}
			}
		catch (Exception exception) {
			logger.error("ERROR:: CurrencyConfigServiceImpl:: deleteCurrencyConfig method", exception);
			throw new ChatakAdminException();
		}
		return null;
	}

	@Override
	public List<Option> getCurrencyConfigCode() {
		CurrencyDTOList currencyDTOList = currencyConfigDao.getActiveCurrencyConfigCode();

	    if(currencyDTOList.getPgCurrencyDTOLists() != null) {
	      List<PGCurrencyConfig> pgcurrencyConfig = currencyDTOList.getPgCurrencyDTOLists();
	      List<Option> currencyCode = new ArrayList<Option>(CommonUtil.isListNotNullAndEmpty(pgcurrencyConfig) ? pgcurrencyConfig.size()
	                                                                                                       : 0);
	      Option pgCurrencyConfigRespObj = null;
	      for(PGCurrencyConfig pGCurrencyConfig : pgcurrencyConfig) {
	    	  pgCurrencyConfigRespObj = new Option();
	    	  pgCurrencyConfigRespObj.setValue(pGCurrencyConfig.getCurrencyCodeAlpha());
	    	  pgCurrencyConfigRespObj.setLabel(pGCurrencyConfig.getCurrencyCodeAlpha());
	        currencyCode.add(pgCurrencyConfigRespObj);
	      }
	      return currencyCode;
	    }
	    return Collections.emptyList();
	}

	@Override
	public Response getCurrencyCodeNumeric(String currencyCodeAlpha) 
	{
		PGCurrencyConfig pgCurrencyConfig = currencyConfigDao.getCurrencyCodeNumeric(currencyCodeAlpha);
		Response response = new Response();
		
		if(null != pgCurrencyConfig)
		{
			response.setCurrencyCodeNumeric(pgCurrencyConfig.getCurrencyCodeNumeric());
			response.setCurrencyId(pgCurrencyConfig.getId());
			
		}
         		
		return response;
	}

	@Override
	public Response getcurrencyCodeAlpha(String bankCurrencyCode) {
		
         PGCurrencyConfig pgCurrencyConfig = currencyConfigDao.getcurrencyCodeAlpha(bankCurrencyCode);
         Response response = new Response();
         if(null != pgCurrencyConfig)
         {
        	 response.setCurrencyCodeAlpha(pgCurrencyConfig.getCurrencyCodeAlpha());
         }
		return response;
	}

	@Override
	public Response findByCurrencyName(String currencyName) {
		Response response= new Response();
		PGCurrencyCode pgCurrencyCode = currencyDao.findByCurrencyName(currencyName);
		response.setCurrencyCodeAlpha(pgCurrencyCode.getCurrencyCodeAlpha());
		response.setCurrencyCodeNumeric(pgCurrencyCode.getCurrencyCodeNumeric());
		response.setErrorMessage("SUCCESS");
		return response;
	}

	@Override
	public List<Option> getAllCurrencies() {
		List<PGCurrencyCode> pgCurrencyCode = currencyDao.findCurrencies();
		List<Option> options = new ArrayList<Option>();
		if (pgCurrencyCode != null) {
			for (PGCurrencyCode currencyCode : pgCurrencyCode) {
				Option option = new Option();
				option.setLabel(currencyCode.getCurrencyName());
				option.setValue(currencyCode.getCurrencyName());
				options.add(option);
			}
		}
		Collections.sort(options, ALPHABETICAL_ORDER);
		return options;
	}
	
	@Override
	public Response changeCurrencyStatus(CurrencyDTO currencyDTO, String currencyStatus){
		logger.info("Entering:: CurrencyConfigServiceImpl:: changeCurrencyStatus method");
		Response response = new Response();
		try {
			if (null != currencyDTO) {
				PGCurrencyConfig pgCurrencyConfig = currencyConfigDao.findByCurrencyConfigId(currencyDTO.getId());
				if(S_STATUS_ACTIVE.equals(currencyStatus)){
					pgCurrencyConfig.setStatus(STATUS_ACTIVE);
				} else {
					pgCurrencyConfig.setStatus(STATUS_SUSPENDED);
				}
				pgCurrencyConfig.setReason(currencyDTO.getReason());
				currencyConfigDao.createOrUpdateCurrencyConfig(pgCurrencyConfig);
				response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
			}
		} catch (DataAccessException e) {
			response.setErrorCode(ActionErrorCode.ERROR_CODE_01);
			logger.error("", e);
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
      return res;
	});

}
