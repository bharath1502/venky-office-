package com.chatak.acquirer.admin.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.acquirer.admin.constants.StatusConstants;
import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.IsoService;
import com.chatak.pg.acq.dao.IsoServiceDao;
import com.chatak.pg.acq.dao.model.Iso;
import com.chatak.pg.acq.dao.model.IsoAccount;
import com.chatak.pg.acq.dao.model.IsoCardProgramMap;
import com.chatak.pg.acq.dao.model.IsoPmMap;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.enums.AccountType;
import com.chatak.pg.user.bean.CardProgramRequest;
import com.chatak.pg.user.bean.CardProgramResponse;
import com.chatak.pg.user.bean.IsoRequest;
import com.chatak.pg.user.bean.IsoResponse;
import com.chatak.pg.user.bean.MerchantResponse;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;
import com.chatak.pg.util.Properties;
@Service("isoService")
@Transactional
public class IsoServiceImpl implements IsoService{
	
	private static Logger logger = Logger.getLogger(IsoServiceImpl.class);
	
	 @Autowired
	 private MessageSource messageSource;
	
	@Autowired
	private IsoServiceDao isoServiceDao;
	
	@Override
	public CardProgramResponse fetchCardProgramByPm(Long id) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		CardProgramResponse response = new CardProgramResponse();
		try{
			response = isoServiceDao.fetchCardProgramByPm(id);
			response.setErrorCode(PGConstants.SUCCESS);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);			
		}catch(Exception e){
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
			response.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return response;
	}

	@Override
	public Response findISONameByAccountCurrency(String currencyId) throws ChatakAdminException {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		IsoResponse isoResponse = isoServiceDao.getISONameByAccountCurrency(currencyId);
		Response response = new Response();
		if (isoResponse != null) {
			List<Option> options = new ArrayList<>(isoResponse.getIsoRequest().size());
			Option option = null;
			for (IsoRequest isoList : isoResponse.getIsoRequest()) {
				option = new Option();
				option.setValue(isoList.getId().toString());
				option.setLabel(isoList.getIsoName());
				options.add(option);
			}
			response.setResponseList(options);
			response.setErrorCode(PGConstants.SUCCESS);
			response.setTotalNoOfRows(options.size());

		} else {
			response.setErrorCode(ActionCode.ERROR_CODE_99);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return response;
	}

	@Override
	public CardProgramResponse fetchCardProgramByIso(Long id,String currencyId) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		CardProgramResponse response = new CardProgramResponse();
		try{
			response = isoServiceDao.fetchCardProgramByIso(id,currencyId);
			response.setErrorCode(PGConstants.SUCCESS);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);			
		}catch(Exception e){
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
			response.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return response;
	}
	
	@Override
	@Transactional(rollbackFor=ChatakAdminException.class)
	public com.chatak.pg.user.bean.Response createIso(IsoRequest isoRequest)throws ChatakAdminException {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		CardProgramResponse response = new CardProgramResponse();
		String accountNumber = Properties.getProperty("iso.account.series");
		try{
			List<Iso> isoName = isoServiceDao.findByIsoName(isoRequest.getIsoName());
			if(StringUtil.isListNotNullNEmpty(isoName)){
				throw new ChatakAdminException(Constants.ISO_NAME_ALREADY_EXIST,messageSource.getMessage(Constants.ISO_NAME_ALREADY_EXIST, null, LocaleContextHolder.getLocale()));
			}
			Iso iso = new Iso();
			iso.setIsoName(isoRequest.getIsoName().trim());
			iso.setBusinessEntityName(isoRequest.getProgramManagerRequest().getBusinessName());
			iso.setContactPerson(isoRequest.getProgramManagerRequest().getContactName());
			iso.setPhoneNumber(isoRequest.getProgramManagerRequest().getContactPhone());
			iso.setStatus(PGConstants.S_STATUS_ACTIVE);
			iso.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			iso.setCreatedBy(isoRequest.getCreatedBy());
			iso.setCurrency(isoRequest.getProgramManagerRequest().getAccountCurrency());
			iso.setEmail(isoRequest.getProgramManagerRequest().getContactEmail());
			iso.setIsoLogo(isoRequest.getProgramManagerRequest().getProgramManagerLogo());
			iso.setAddress(isoRequest.getAddress());
			iso.setCountry(isoRequest.getCountry());
			iso.setState(isoRequest.getState());
			iso.setCity(isoRequest.getCity());
			iso.setZipCode(isoRequest.getZipCode());
			iso.setBankName(isoRequest.getBankName());
			iso.setBankAccNum(isoRequest.getBankAccNum());
			iso.setRoutingNumber(isoRequest.getRoutingNumber());
			setPmAndCpMapping(isoRequest, iso);
			iso = isoServiceDao.saveIso(iso);
			//save iso account
			createIsoAccount(isoRequest, iso, accountNumber);
			response.setErrorCode(PGConstants.SUCCESS);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
			LogHelper.logExit(logger, LoggerMessage.getCallerName());
			return response;
		}catch(ChatakAdminException ex){
			LogHelper.logError(logger, LoggerMessage.getCallerName(), ex, Constants.CHATAK_ADMIN_EXCEPTION);
			response.setErrorCode(ex.getErrorCode());
			response.setErrorMessage(ex.getErrorMessage());
			throw new ChatakAdminException(ex.getErrorCode(),ex.getErrorMessage());
		}catch(Exception e){
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
			response.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
			throw new ChatakAdminException(Constants.ISO_CREATE_ERROR,messageSource.getMessage(Constants.ISO_CREATE_ERROR, null,LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public IsoResponse searchIso(IsoRequest isoRequest)
			throws ChatakAdminException {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		IsoResponse isoResponse = new IsoResponse();
		try {
			isoResponse = isoServiceDao.searchIso(isoRequest);
			isoResponse.setErrorCode(PGConstants.SUCCESS);
			isoResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e,
					Constants.EXCEPTION);
			isoResponse.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			isoResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
			throw new ChatakAdminException(Constants.ISO_CREATE_ERROR,
					messageSource.getMessage(Constants.ISO_CREATE_ERROR, null,
							LocaleContextHolder.getLocale()));
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return isoResponse;
	}
	
	@Override
	public IsoResponse getIsoById(IsoRequest isoRequest)
			throws ChatakAdminException {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		IsoResponse isoResponse = new IsoResponse();
		try {
			isoResponse = isoServiceDao.getIsoById(isoRequest);
			isoResponse.setErrorCode(PGConstants.SUCCESS);
			isoResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
		} catch (Exception e) {
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e,
					Constants.EXCEPTION);
			isoResponse.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			isoResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
			throw new ChatakAdminException(Constants.ISO_CREATE_ERROR,
					messageSource.getMessage(Constants.ISO_CREATE_ERROR, null,
							LocaleContextHolder.getLocale()));
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return isoResponse;
	}

	@Override
	@Transactional(rollbackFor=ChatakAdminException.class)
	public IsoResponse updateIso(IsoRequest isoRequest)
			throws ChatakAdminException {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		IsoResponse response = new IsoResponse();
		try{
			List<Iso> isoModel = isoServiceDao.findByIsoId(isoRequest.getId());
			if(!(isoModel.get(0).getIsoName().equals(isoRequest.getIsoName()))){
				List<Iso> existingName = isoServiceDao.findByIsoName(isoRequest.getIsoName());
				if(StringUtil.isListNotNullNEmpty(existingName)){
					throw new ChatakAdminException(Constants.ISO_NAME_ALREADY_EXIST,messageSource.getMessage(Constants.ISO_NAME_ALREADY_EXIST, null, LocaleContextHolder.getLocale()));	
				}
			}
			//Delete existing references
			isoServiceDao.deleteIsoPmMappingByIsoId(isoRequest.getId());
			isoServiceDao.deleteIsoCardProgramMappingByIsoId(isoRequest.getId());
			Iso iso = new Iso();
			iso.setId(isoRequest.getId());
			iso.setIsoName(isoRequest.getIsoName().trim());
			iso.setBusinessEntityName(isoRequest.getProgramManagerRequest().getBusinessName());
			iso.setContactPerson(isoRequest.getProgramManagerRequest().getContactName());
			iso.setPhoneNumber(isoRequest.getProgramManagerRequest().getContactPhone());
			iso.setExtension(isoRequest.getProgramManagerRequest().getExtension());
			iso.setStatus(isoModel.get(0).getStatus());
			iso.setEmail(isoRequest.getProgramManagerRequest().getContactEmail());
			iso.setIsoLogo(isoModel.get(0).getIsoLogo());
			iso.setAddress(isoRequest.getAddress());
			iso.setCountry(isoRequest.getCountry());
			iso.setState(isoRequest.getState());
			iso.setCity(isoRequest.getCity());
			iso.setZipCode(isoRequest.getZipCode());
			iso.setBankAccNum(isoRequest.getBankAccNum());
			iso.setBankName(isoRequest.getBankName());
			iso.setRoutingNumber(isoRequest.getRoutingNumber());
			if(isoRequest.getProgramManagerRequest().getProgramManagerLogo()!=null){
				iso.setIsoLogo(isoRequest.getProgramManagerRequest().getProgramManagerLogo());				
			}
			setPmAndCpMapping(isoRequest, iso);
			isoServiceDao.updateIso(iso);
			response.setErrorCode(PGConstants.SUCCESS);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
		}catch(ChatakAdminException ex){
			LogHelper.logError(logger, LoggerMessage.getCallerName(), ex, Constants.CHATAK_ADMIN_EXCEPTION);
			response.setErrorCode(ex.getErrorCode());
			response.setErrorMessage(ex.getErrorMessage());
			throw new ChatakAdminException(ex.getErrorCode(),ex.getErrorMessage());
		}catch(DataAccessException ex){
			LogHelper.logError(logger, LoggerMessage.getCallerName(), ex, Constants.DATA_ACCESS_EXCEPTION);
			response.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
			throw new ChatakAdminException(Constants.ISO_CREATE_ERROR,messageSource.getMessage(Constants.ISO_CREATE_ERROR, null,LocaleContextHolder.getLocale()));
		}catch(Exception e){
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
			response.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
			throw new ChatakAdminException(Constants.ISO_CREATE_ERROR,messageSource.getMessage(Constants.ISO_CREATE_ERROR, null,LocaleContextHolder.getLocale()));
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return response;
	}
	
	public IsoResponse fetchCardProgramByIso(Long isoId)throws ChatakAdminException{
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		IsoResponse response = new IsoResponse();
		try{
			List<CardProgramRequest> cardProgramList = isoServiceDao.fetchCardProgramByIso(isoId);
			if(StringUtil.isListNotNullNEmpty(cardProgramList)){
				response.setCardProgramRequestList(cardProgramList);			
			}
			response.setErrorCode(PGConstants.SUCCESS);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
			LogHelper.logExit(logger, LoggerMessage.getCallerName());
			return response;
		}catch(Exception e){
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
			response.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
			throw new ChatakAdminException();
		}
	}
	private void setPmAndCpMapping(IsoRequest isoRequest,Iso iso){
		Set<IsoPmMap> isoPmMap = new HashSet<>();
		for(Long id : isoRequest.getProgramManagerIds()){
			IsoPmMap pgIsoPmMap = new IsoPmMap();
			pgIsoPmMap.setPmId(id);
			isoPmMap.add(pgIsoPmMap);
		}
		iso.setPgIsoPmMap(isoPmMap);
		Set<IsoCardProgramMap> isoCardProgramMap = new HashSet<>();
		for(Long id : isoRequest.getCardProgramIds()){
			IsoCardProgramMap pgIsoCardProgramMap = new IsoCardProgramMap();
			pgIsoCardProgramMap.setCardProgramId(id);
			isoCardProgramMap.add(pgIsoCardProgramMap);
		}
		iso.setPgIsoCardProgramMap(isoCardProgramMap);
	}

	@Override
	public IsoResponse fetchProgramManagerByIsoCurrency(Long isoId,
			String currency) throws ChatakAdminException {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		IsoResponse isoResponse = new IsoResponse();
		try{
			isoResponse = isoServiceDao.fetchProgramManagerByIsoCurrency(isoId, currency);	
			isoResponse.setErrorCode(PGConstants.SUCCESS);
			isoResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
		}catch(Exception e){
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
			isoResponse.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			isoResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());		
		return isoResponse;
	}

	@Override
	public IsoResponse getAllIso(IsoRequest isoRequest) throws ChatakAdminException {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
	    try {
	    	List<IsoRequest> isoRequests =
	    		  isoServiceDao.getAllIso(isoRequest);
	    	IsoResponse isoResponse = new IsoResponse();
	      if (StringUtil.isListNotNullNEmpty(isoRequests)) {
	    	  isoResponse.setIsoRequest(isoRequests);
	      }
	      isoResponse.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
	      isoResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
	      LogHelper.logExit(logger, LoggerMessage.getCallerName());
	      return isoResponse;
	    } catch (Exception e) {
	    	LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
	      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
	          LocaleContextHolder.getLocale()), e);
	    }
	  
	}

	@Override
	public List<Long> findByPmId(Long pmId) throws ChatakAdminException {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		List<Long> isoIds = new ArrayList<>();
		List<IsoPmMap> isoPmMaps = isoServiceDao.findByPmId(pmId);
		for(IsoPmMap isoPmMap : isoPmMaps){
			isoIds.add(isoPmMap.getIsoId());
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return isoIds;
	}
	
	private void createIsoAccount(IsoRequest isoRequest,Iso iso,String accountNumber){
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		List<IsoAccount> isoAccountList = new ArrayList<>();
		Long accountNum = isoServiceDao.getAccountNumberSeries(accountNumber);
		IsoAccount isoSystemAccount = new IsoAccount();
		isoSystemAccount.setIsoId(iso.getId());
		isoSystemAccount.setAccountNumber(accountNum);
		isoSystemAccount.setCreatedBy(isoRequest.getCreatedBy());
		isoSystemAccount.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		isoSystemAccount.setAvailableBalance(0L);
		isoSystemAccount.setCurrentBalance(0L);
		isoSystemAccount.setStatus(PGConstants.S_STATUS_ACTIVE);
		isoSystemAccount.setAccountType(AccountType.SYSTEM_ACCOUNT.name());
		
		IsoAccount isoRevenueAccount = new IsoAccount();
		isoRevenueAccount.setIsoId(iso.getId());
		isoRevenueAccount.setAccountNumber(accountNum + 1);
		isoRevenueAccount.setCreatedBy(isoRequest.getCreatedBy());
		isoRevenueAccount.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		isoRevenueAccount.setAvailableBalance(0L);
		isoRevenueAccount.setCurrentBalance(0L);
		isoRevenueAccount.setStatus(PGConstants.S_STATUS_ACTIVE);
		isoRevenueAccount.setAccountType(AccountType.REVENUE_ACCOUNT.name());
		isoAccountList.add(isoSystemAccount);
		isoAccountList.add(isoRevenueAccount);
		for(IsoAccount isoAccount : isoAccountList){
			isoServiceDao.saveIsoAccount(isoAccount);			
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
	}

	@Override
	public Response findIsoNameByCurrencyAndId(Long id, String currencyId) {
		MerchantResponse merchantResponse = isoServiceDao.getIsoNameByCurrencyAndId(id,currencyId);
		Response response = new Response();
		if (merchantResponse != null && !StringUtil.isNull(merchantResponse.getIsoRequests())) {
			List<Option> options = new ArrayList<>(merchantResponse.getIsoRequests().size());
			Option option = null;
			for (IsoRequest isoRequest : merchantResponse.getIsoRequests()) {
				option = new Option();
				option.setValue(isoRequest.getId().toString());
				option.setLabel(isoRequest.getIsoName());
				options.add(option);
			}
			response.setResponseList(options);
			response.setErrorCode(PGConstants.SUCCESS);
			response.setTotalNoOfRows(options.size());

		} else {
			response.setErrorCode(ActionCode.ERROR_CODE_99);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return response;
	}
	
	@Override
	public IsoResponse changeStatus(IsoRequest isoRequest) throws  ChatakAdminException {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		IsoResponse response = new IsoResponse();
		try{
		List<Iso> IsoList = isoServiceDao.findByIsoId(isoRequest.getId());
		Iso iso = new Iso();
		iso.setId(IsoList.get(0).getId());
		iso.setIsoName(IsoList.get(0).getIsoName().trim());
		iso.setBusinessEntityName(IsoList.get(0).getBusinessEntityName());
		iso.setContactPerson(IsoList.get(0).getContactPerson());
		iso.setPhoneNumber(IsoList.get(0).getPhoneNumber());
		iso.setExtension(IsoList.get(0).getExtension());
		iso.setStatus(isoRequest.getProgramManagerRequest().getStatus());
		iso.setEmail(IsoList.get(0).getEmail());
		iso.setIsoLogo(IsoList.get(0).getIsoLogo());
		iso.setReason(isoRequest.getReason());
		iso.setUpdatedBy(isoRequest.getUpdatedBy());
		iso.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
		iso.setAddress(IsoList.get(0).getAddress());
		iso.setCity(IsoList.get(0).getCity());
		iso.setCountry(IsoList.get(0).getCountry());
		iso.setState(IsoList.get(0).getState());
		iso.setZipCode(IsoList.get(0).getZipCode());
		 isoServiceDao.updateIso(iso);
		response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
		response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
			LogHelper.logExit(logger, LoggerMessage.getCallerName());
		} catch (Exception e) {
			throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
			          LocaleContextHolder.getLocale()), e);
		}
		return response;
		
	}

	@Override
	public CardProgramResponse fetchIsoCardProgramByMerchantId(Long merchantId) {
		LogHelper.logEntry(logger, LoggerMessage.getCallerName());
		CardProgramResponse response = new CardProgramResponse();
		try{
			response = isoServiceDao.fetchIsoCardProgramByMerchantId(merchantId);
			response.setErrorCode(PGConstants.SUCCESS);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);			
		}catch(Exception e){
			LogHelper.logError(logger, LoggerMessage.getCallerName(), e, Constants.EXCEPTION);
			response.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
		}
		LogHelper.logExit(logger, LoggerMessage.getCallerName());
		return response;
	}
	
	@Override
	public List<Iso> findIsoByIsoId(Long isoId) {
		return isoServiceDao.findByIsoId(isoId);
	}
	
	@Override
	public List<IsoRequest> findIsoByProgramaManagerId(Long Pmid) {
		return isoServiceDao.findIsoByProgramaManagerId(Pmid);
		
	}
	
	public List<ProgramManagerRequest> findPmByIsoId(Long isoId) {
		return isoServiceDao.findPmByIsoId(isoId);
	}

}
