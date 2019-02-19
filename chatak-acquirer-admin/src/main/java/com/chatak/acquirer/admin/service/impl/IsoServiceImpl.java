package com.chatak.acquirer.admin.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import com.chatak.acquirer.admin.util.CommonUtil;
import com.chatak.pg.acq.dao.IsoServiceDao;
import com.chatak.pg.acq.dao.model.Iso;
import com.chatak.pg.acq.dao.model.IsoAccount;
import com.chatak.pg.acq.dao.model.IsoCardProgramMap;
import com.chatak.pg.acq.dao.model.IsoPmMap;
import com.chatak.pg.acq.dao.model.PanRanges;
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
import com.chatak.pg.user.bean.PanRangeRequest;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.util.Constants;
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
		logger.info("Entering :: IsoServiceImpl :: fetchCardProgramByPm");
		CardProgramResponse response = new CardProgramResponse();
		try{
			response = isoServiceDao.fetchCardProgramByPm(id);
			response.setErrorCode(PGConstants.SUCCESS);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);			
		}catch(Exception e){
			logger.error("Error :: IsoServiceImpl :: fetchCardProgramByPm : " + e.getMessage(), e);
			response.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
		}
		logger.info("Exiting :: IsoServiceImpl :: fetchCardProgramByPm");
		return response;
	}

	@Override
	public Response findISONameByAccountCurrency(String currencyId) throws ChatakAdminException {
		logger.info("Entering :: IsoServiceImpl :: findISONameByAccountCurrency");
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
		logger.info("Exiting :: IsoServiceImpl :: findISONameByAccountCurrency");
		return response;
	}

	@Override
	public CardProgramResponse fetchCardProgramByIso(Long id,String currencyId) {
		logger.info("Entering :: IsoServiceImpl :: fetchCardProgramByIso");
		CardProgramResponse response = new CardProgramResponse();
		try{
			response = isoServiceDao.fetchCardProgramByIso(id,currencyId);
			response.setErrorCode(PGConstants.SUCCESS);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);			
		}catch(Exception e){
			logger.error("Error :: IsoServiceImpl :: fetchCardProgramByIso : " + e.getMessage(), e);
			response.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
		}
		logger.info("Exiting :: IsoServiceImpl :: fetchCardProgramByIso");
		return response;
	}
	
	@Override
	@Transactional(rollbackFor=ChatakAdminException.class)
	public com.chatak.pg.user.bean.Response createIso(IsoRequest isoRequest)throws ChatakAdminException {
		logger.info("Entering :: IsoServiceImpl :: createIso");
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
			if (StringUtil.isListNotNullNEmpty(isoRequest.getPanRangeList())) {
				Set<PanRanges> panRanges = new HashSet<PanRanges>();
				for (PanRangeRequest panRange : isoRequest.getPanRangeList()) {
					PanRanges panRanges2 = CommonUtil
							.copyBeanProperties(panRange, PanRanges.class);
					panRanges.add(panRanges2); 
				}
				iso.setPanRanges(panRanges);
			}
			iso = isoServiceDao.saveIso(iso);
			//save iso account
			createIsoAccount(isoRequest, iso, accountNumber);
			response.setErrorCode(PGConstants.SUCCESS);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
			logger.info("Exiting :: IsoServiceImpl :: createIso");
			return response;
		}catch(ChatakAdminException ex){
		  logger.error("Error :: IsoServiceImpl :: createIso : ChatakAdminException : " + ex.getMessage(), ex);
			response.setErrorCode(ex.getErrorCode());
			response.setErrorMessage(ex.getErrorMessage());
			throw new ChatakAdminException(ex.getErrorCode(),ex.getErrorMessage());
		}catch(Exception e){
			logger.error("Error :: IsoServiceImpl :: createIso : " + e.getMessage(), e);
			response.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
			throw new ChatakAdminException(Constants.ISO_CREATE_ERROR,messageSource.getMessage(Constants.ISO_CREATE_ERROR, null,LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public IsoResponse searchIso(IsoRequest isoRequest)
			throws ChatakAdminException {
		logger.info("Entering :: IsoServiceImpl :: searchIso");
		IsoResponse isoResponse = new IsoResponse();
		try {
			isoResponse = isoServiceDao.searchIso(isoRequest);
			isoResponse.setErrorCode(PGConstants.SUCCESS);
			isoResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
		} catch (Exception e) {
		    logger.error("Error :: IsoServiceImpl :: searchIso : " + e.getMessage(), e);
			isoResponse.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			isoResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
			throw new ChatakAdminException(Constants.ISO_CREATE_ERROR,
					messageSource.getMessage(Constants.ISO_CREATE_ERROR, null,
							LocaleContextHolder.getLocale()));
		}
		logger.info("Exiting :: IsoServiceImpl :: searchIso");
		return isoResponse;
	}
	
	@Override
	public IsoResponse getIsoById(IsoRequest isoRequest)
			throws ChatakAdminException {
		logger.info("Entering :: IsoServiceImpl :: getIsoById");
		IsoResponse isoResponse = new IsoResponse();
		try {
			isoResponse = isoServiceDao.getIsoById(isoRequest);
			isoResponse.setErrorCode(PGConstants.SUCCESS);
			isoResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
		} catch (Exception e) {
		    logger.error("Error :: IsoServiceImpl :: getIsoById : " + e.getMessage(), e);
			isoResponse.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			isoResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
			throw new ChatakAdminException(Constants.ISO_CREATE_ERROR,
					messageSource.getMessage(Constants.ISO_CREATE_ERROR, null,
							LocaleContextHolder.getLocale()));
		}
		logger.info("Exiting :: IsoServiceImpl :: getIsoById");
		return isoResponse;
	}

	@Override
	@Transactional(rollbackFor=ChatakAdminException.class)
	public IsoResponse updateIso(IsoRequest isoRequest)
			throws ChatakAdminException {
		logger.info("Entering :: IsoServiceImpl :: updateIso");
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
			Set<PanRanges> panRanges = new HashSet<PanRanges>();
			for (PanRangeRequest panRange : isoRequest.getPanRangeList()) {
				PanRanges panRanges2 = CommonUtil
						.copyBeanProperties(panRange, PanRanges.class);
				panRanges.add(panRanges2); 
			}
			iso.setPanRanges(panRanges);
			if(isoRequest.getProgramManagerRequest().getProgramManagerLogo()!=null){
				iso.setIsoLogo(isoRequest.getProgramManagerRequest().getProgramManagerLogo());				
			}
			setPmAndCpMapping(isoRequest, iso);
			isoServiceDao.updateIso(iso);
			response.setErrorCode(PGConstants.SUCCESS);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
		}catch(ChatakAdminException ex){
		    logger.error("Error :: IsoServiceImpl :: updateIso : ChatakAdminException : " + ex.getMessage(), ex);
			response.setErrorCode(ex.getErrorCode());
			response.setErrorMessage(ex.getErrorMessage());
			throw new ChatakAdminException(ex.getErrorCode(),ex.getErrorMessage());
		}catch(DataAccessException ex){
		    logger.error("Error :: IsoServiceImpl :: updateIso : DataAccessException : " + ex.getMessage(), ex);
			response.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
			throw new ChatakAdminException(Constants.ISO_CREATE_ERROR,messageSource.getMessage(Constants.ISO_CREATE_ERROR, null,LocaleContextHolder.getLocale()));
		}catch(Exception e){
			logger.error("Error :: IsoServiceImpl :: updateIso : " + e.getMessage(), e);
			response.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
			throw new ChatakAdminException(Constants.ISO_CREATE_ERROR,messageSource.getMessage(Constants.ISO_CREATE_ERROR, null,LocaleContextHolder.getLocale()));
		}
		logger.info("Exiting :: IsoServiceImpl :: updateIso");
		return response;
	}
	
	public IsoResponse fetchCardProgramByIso(Long isoId)throws ChatakAdminException{
		logger.info("Entering :: IsoServiceImpl :: fetchCardProgramByIso");
		IsoResponse response = new IsoResponse();
		try{
			List<CardProgramRequest> cardProgramList = isoServiceDao.fetchCardProgramByIso(isoId);
			if(StringUtil.isListNotNullNEmpty(cardProgramList)){
				response.setCardProgramRequestList(cardProgramList);			
			}
			response.setErrorCode(PGConstants.SUCCESS);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
			logger.info("Exiting :: IsoServiceImpl :: fetchCardProgramByIso");
			return response;
		}catch(Exception e){
			logger.error("Error :: IsoServiceImpl :: fetchCardProgramByIso : " + e.getMessage(), e);
			response.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
			throw new ChatakAdminException();
		}
	}
	private void setPmAndCpMapping(IsoRequest isoRequest,Iso iso){
		Set<IsoPmMap> isoPmMap = new HashSet<>();
			IsoPmMap pgIsoPmMap = new IsoPmMap();
			pgIsoPmMap.setPmId(isoRequest.getProgramManagerRequest().getProgramManagerId());
			isoPmMap.add(pgIsoPmMap);
		iso.setPgIsoPmMap(isoPmMap);
		Set<IsoCardProgramMap> isoCardProgramMap = new HashSet<>();
		for(Map.Entry<Long, Long> id : isoRequest.getCardProgramAndEntityId().entrySet()){
		  IsoCardProgramMap pgIsoCardProgramMap = new IsoCardProgramMap();
          pgIsoCardProgramMap.setCardProgramId(id.getKey());
          pgIsoCardProgramMap.setAmbiguityPmId(id.getValue());
          isoCardProgramMap.add(pgIsoCardProgramMap);
      }
		iso.setPgIsoCardProgramMap(isoCardProgramMap);
	}

	@Override
	public IsoResponse fetchProgramManagerByIsoCurrency(Long isoId,
			String currency) throws ChatakAdminException {
		logger.info("Entering :: IsoServiceImpl :: fetchProgramManagerByIsoCurrency");
		IsoResponse isoResponse = new IsoResponse();
		try{
			isoResponse = isoServiceDao.fetchProgramManagerByIsoCurrency(isoId, currency);	
			isoResponse.setErrorCode(PGConstants.SUCCESS);
			isoResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
		}catch(Exception e){
			logger.error("Error :: IsoServiceImpl :: fetchProgramManagerByIsoCurrency : " + e.getMessage(), e);
			isoResponse.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			isoResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
		}
		logger.info("Exiting :: IsoServiceImpl :: fetchProgramManagerByIsoCurrency");		
		return isoResponse;
	}

	@Override
	public IsoResponse getAllIso(IsoRequest isoRequest) throws ChatakAdminException {
		logger.info("Entering :: IsoServiceImpl :: getAllIso");
	    try {
	    	List<IsoRequest> isoRequests =
	    		  isoServiceDao.getAllIso(isoRequest);
	    	IsoResponse isoResponse = new IsoResponse();
	      if (StringUtil.isListNotNullNEmpty(isoRequests)) {
	    	  isoResponse.setIsoRequest(isoRequests);
	      }
	      isoResponse.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
	      isoResponse.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
	      logger.info("Exiting :: IsoServiceImpl :: getAllIso");
	      return isoResponse;
	    } catch (Exception e) {
	    	logger.error("Error :: IsoServiceImpl :: getAllIso : " + e.getMessage(), e);
	      throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
	          LocaleContextHolder.getLocale()), e);
	    }
	  
	}

	@Override
	public List<Long> findByPmId(Long pmId) throws ChatakAdminException {
		logger.info("Entering :: IsoServiceImpl :: findByPmId");
		List<Long> isoIds = new ArrayList<>();
		List<IsoPmMap> isoPmMaps = isoServiceDao.findByPmId(pmId);
		for(IsoPmMap isoPmMap : isoPmMaps){
			isoIds.add(isoPmMap.getIsoId());
		}
		logger.info("Exiting :: IsoServiceImpl :: findByPmId");
		return isoIds;
	}
	
	private void createIsoAccount(IsoRequest isoRequest,Iso iso,String accountNumber){
		logger.info("Entering :: IsoServiceImpl :: createIsoAccount");
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
		logger.info("Exiting :: IsoServiceImpl :: createIsoAccount");
	}

	@Override
	public Response findIsoNameByCurrencyAndId(Long id, String currencyId) {
	    logger.info("Entering :: IsoServiceImpl :: findIsoNameByCurrencyAndId");
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
		logger.info("Exiting :: IsoServiceImpl :: findIsoNameByCurrencyAndId");
		return response;
	}
	
	@Override
	public Response findIsoNameByProgramManagerId(Long pmId) {
		logger.info("Entering :: IsoServiceImpl :: findIsoNameByProgramManagerId");
		IsoResponse isoResponse = isoServiceDao.getIsoNameByProgramManagerId(pmId);
		Response response = new Response();
		if (isoResponse != null && !StringUtil.isNull(isoResponse.getIsoRequest())) {
			List<Option> options = new ArrayList<>(isoResponse.getIsoRequest().size());
			Option option = null;
			for (IsoRequest isoRequest : isoResponse.getIsoRequest()) {
				option = new Option();
				option.setValue(isoRequest.getId().toString());
				option.setLabel(isoRequest.getIsoName());
				options.add(option);
			}
			response.setResponseList(options);
			response.setErrorCode(PGConstants.SUCCESS);
			response.setTotalNoOfRows(options.size());

		}
		logger.info("Exiting :: IsoServiceImpl :: findIsoNameByProgramManagerId");
		return response;
	}
	
	@Override
	public IsoResponse changeStatus(IsoRequest isoRequest) throws  ChatakAdminException {
		logger.info("Entering :: IsoServiceImpl :: changeStatus");
		IsoResponse response = new IsoResponse();
		try {
		  isoServiceDao.updateISOStatusById(isoRequest.getId(), isoRequest.getReason(), isoRequest.getUpdatedBy(), 
		      new Timestamp(System.currentTimeMillis()), isoRequest.getProgramManagerRequest().getStatus());
		  
		response.setErrorCode(StatusConstants.STATUS_CODE_SUCCESS);
		response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);
			logger.info("Exiting :: IsoServiceImpl :: changeStatus");
		} catch (Exception e) {
			throw new ChatakAdminException(messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null,
			          LocaleContextHolder.getLocale()), e);
		}
		return response;
		
	}

	@Override
	public CardProgramResponse fetchIsoCardProgramByMerchantId(Long merchantId) {
		logger.info("Entering :: IsoServiceImpl :: fetchIsoCardProgramByMerchantId");
		CardProgramResponse response = new CardProgramResponse();
		try{
			response = isoServiceDao.fetchIsoCardProgramByMerchantId(merchantId);
			response.setErrorCode(PGConstants.SUCCESS);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_SUCCESS);			
		}catch(Exception e){
			logger.error("Error :: IsoServiceImpl :: fetchIsoCardProgramByMerchantId : " + e.getMessage(), e);
			response.setErrorCode(StatusConstants.STATUS_CODE_FAILED);
			response.setErrorMessage(StatusConstants.STATUS_MESSAGE_FAILED);
		}
		logger.info("Exiting :: IsoServiceImpl :: fetchIsoCardProgramByMerchantId");
		return response;
	}
	
	@Override
	public List<Iso> findIsoByIsoId(Long isoId) {
		return isoServiceDao.findByIsoId(isoId);
	}
	
	@Override
	public List<IsoRequest> findIsoByProgramaManagerId(Long pmid) {
		return isoServiceDao.findIsoByProgramaManagerId(pmid);
		
	}
	
	public List<ProgramManagerRequest> findPmByIsoId(Long isoId) {
		return isoServiceDao.findPmByIsoId(isoId);
	}

	@Override
	public Response findIsoNameAndIdByEntityId(Long pmId) {
		List<Response> list = isoServiceDao.findIsoNameAndIdByEntityId(pmId);
		Response response = new Response();
		if (StringUtil.isListNotNullNEmpty(list)) {
			List<Option> options = new ArrayList<>(list.size());
			Option option = null;
			for (Response isoRequest : list) {
				option = new Option();
				option.setValue(isoRequest.getIsoId().toString());
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
		logger.info("Exiting :: IsoServiceImpl :: findIsoNameByCurrencyAndId");
		return response;
	}
}
