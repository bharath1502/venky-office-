package com.chatak.pay.service.impl;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.chatak.pay.constants.ChatakPayErrorCode;
import com.chatak.pay.controller.model.Request;
import com.chatak.pay.controller.model.topup.GetOperatorsResponse;
import com.chatak.pay.controller.model.topup.GetTopupCategoriesResponse;
import com.chatak.pay.controller.model.topup.GetTopupOffersResponse;
import com.chatak.pay.controller.model.topup.IssuanceTopupRequest;
import com.chatak.pay.controller.model.topup.TopupRequest;
import com.chatak.pay.controller.model.topup.TopupResponse;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.service.TopupService;
import com.chatak.pay.util.JsonUtil;
import com.chatak.pay.util.StringUtil;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.TerminalDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGTerminal;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.util.Properties;

@Service
public class TopupServiceImpl implements TopupService {

	private Logger logger = Logger.getLogger(TopupServiceImpl.class);
	
	private static ObjectMapper mapper=new ObjectMapper();	

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private TerminalDao terminalDao;
	
	@Autowired
	MerchantUpdateDao merchantUpdateDao;

	@Override
	public GetOperatorsResponse getOperators(Request request) {
		logger.debug("Entering:: IssuanceServiceImpl:: getOperators method");
		GetOperatorsResponse getOperatorsResponse = new GetOperatorsResponse();
		try {
			PGMerchant pgMerchant = merchantUpdateDao.getMerchant(request.getMerchantCode());
			if (pgMerchant != null && !StringUtil.isNullAndEmpty(request.getTerminalId())) {
				PGTerminal pgTerminal = terminalDao.getTerminal(Long.valueOf(request.getTerminalId()));
				if (pgTerminal == null) {
					logger.info("Invalid Merchant: " + request.getMerchantCode());
					getOperatorsResponse.setErrorCode(ChatakPayErrorCode.TXN_0007.name());
					getOperatorsResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0007.name(),
							null, LocaleContextHolder.getLocale()));
				} else {
					getOperatorsResponse = pgTerminalNotNull();
				}
			} else {
				logger.info("Invalid Merchant: " + request.getMerchantCode());
				getOperatorsResponse.setErrorCode(ChatakPayErrorCode.TXN_0007.name());
				getOperatorsResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0007.name(), null,
						LocaleContextHolder.getLocale()));
			}
		} catch (Exception e) {
		  logger.error("ERROR:: TopupServiceImpl:: getOperators method", e);
			getOperatorsResponse.setErrorCode(ChatakPayErrorCode.TXN_0992.name());
			getOperatorsResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(), null,
					LocaleContextHolder.getLocale()));
		}
		logger.debug("Exiting:: IssuanceServiceImpl:: getOperators method");
		return getOperatorsResponse;
	}

	private GetOperatorsResponse pgTerminalNotNull() throws ChatakPayException, IOException, HttpClientException {
		GetOperatorsResponse getOperatorsResponse;
		String output = (String) JsonUtil.sendToIssuance(String.class,
				"", Properties.getProperty("chatak-issuance.get.operators"));
		getOperatorsResponse=mapper.readValue(output, GetOperatorsResponse.class);
		if (getOperatorsResponse == null) {
			throw new ChatakPayException();
		}
		getOperatorsResponse.setErrorCode(ChatakPayErrorCode.GEN_001.name());
		getOperatorsResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.GEN_001.name(),
				null, LocaleContextHolder.getLocale()));
		return getOperatorsResponse;
	}
	
	@Override
	public GetTopupCategoriesResponse getTopupCategories(TopupRequest topupRequest) {
		logger.debug("Entering:: IssuanceServiceImpl:: getTopupCategories method");
		GetTopupCategoriesResponse getTopupCategoriesResponse = new GetTopupCategoriesResponse();
		try {
			PGMerchant pgMerchant = merchantUpdateDao.getMerchant(topupRequest.getMerchantCode());
			if (pgMerchant != null && !StringUtil.isNullAndEmpty(topupRequest.getTerminalId())) {
				PGTerminal pgTerminal = terminalDao.getTerminal(Long.valueOf(topupRequest.getTerminalId()));
				if (pgTerminal == null) {
					logger.info("Invalid Merchant: " + topupRequest.getMerchantCode());
					getTopupCategoriesResponse.setErrorCode(ChatakPayErrorCode.TXN_0007.name());
					getTopupCategoriesResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0007.name(),
							null, LocaleContextHolder.getLocale()));
				} else {
					getTopupCategoriesResponse = pgTerminalIsNotNull(topupRequest);
				}
			} else {
				logger.info("Invalid Merchant: " + topupRequest.getMerchantCode());
				getTopupCategoriesResponse.setErrorCode(ChatakPayErrorCode.TXN_0007.name());
				getTopupCategoriesResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0007.name(), null,
						LocaleContextHolder.getLocale()));
			}
		} catch (Exception e) {
		  logger.error("ERROR:: TopupServiceImpl:: getTopupCategories method", e);
			getTopupCategoriesResponse.setErrorCode(ChatakPayErrorCode.TXN_0992.name());
			getTopupCategoriesResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(), null,
					LocaleContextHolder.getLocale()));
		}
		logger.debug("Exiting:: IssuanceServiceImpl:: getTopupCategories method");
		return getTopupCategoriesResponse;
	}

	private GetTopupCategoriesResponse pgTerminalIsNotNull(TopupRequest topupRequest) throws ChatakPayException, HttpClientException,IOException {
		GetTopupCategoriesResponse getTopupCategoriesResponse;
		IssuanceTopupRequest issuanceTopupRequest = new IssuanceTopupRequest();
		issuanceTopupRequest.setAmount(topupRequest.getAmount());
		issuanceTopupRequest.setMobileNumber(topupRequest.getMobileNumber());
		issuanceTopupRequest.setOperatorId(topupRequest.getOperatorId());
		issuanceTopupRequest.setCategoryId(topupRequest.getCategoryId());
		issuanceTopupRequest.setTopupOfferId(topupRequest.getTopupOfferId());
		String output=(String)JsonUtil.sendToIssuance(String.class,
				issuanceTopupRequest, Properties.getProperty("chatak-issuance.get.topupCategoryList"));
		getTopupCategoriesResponse=mapper.readValue(output, GetTopupCategoriesResponse.class);
		if (getTopupCategoriesResponse == null) {
			throw new ChatakPayException();
		}
		getTopupCategoriesResponse.setErrorCode(ChatakPayErrorCode.GEN_001.name());
		getTopupCategoriesResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.GEN_001.name(),
				null, LocaleContextHolder.getLocale()));
		return getTopupCategoriesResponse;
	}
	
	@Override
	public GetTopupOffersResponse getTopupOffers(TopupRequest topupRequest) {
		logger.debug("Entering:: IssuanceServiceImpl:: getTopupOffers method");
		GetTopupOffersResponse getTopupOffersResponse = new GetTopupOffersResponse();
		try {
			PGMerchant pgMerchant = merchantUpdateDao.getMerchant(topupRequest.getMerchantCode());
			if (pgMerchant != null && !StringUtil.isNullAndEmpty(topupRequest.getTerminalId())) {
				PGTerminal pgTerminal = terminalDao.getTerminal(Long.valueOf(topupRequest.getTerminalId()));
				if (pgTerminal == null) {
					logger.info("Invalid Merchant: " + topupRequest.getMerchantCode());
					getTopupOffersResponse.setErrorCode(ChatakPayErrorCode.TXN_0007.name());
					getTopupOffersResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0007.name(),
							null, LocaleContextHolder.getLocale()));
				} else {
					getTopupOffersResponse = pgTerminalValidation(topupRequest);
				}
			} else {
				logger.info("Invalid Merchant: " + topupRequest.getMerchantCode());
				getTopupOffersResponse.setErrorCode(ChatakPayErrorCode.TXN_0007.name());
				getTopupOffersResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0007.name(), null,
						LocaleContextHolder.getLocale()));
			}
		} catch (Exception e) {
		  logger.error("ERROR:: TopupServiceImpl:: getTopupOffers method", e);
			getTopupOffersResponse.setErrorCode(ChatakPayErrorCode.TXN_0992.name());
			getTopupOffersResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(), null,
					LocaleContextHolder.getLocale()));
		}
		logger.debug("Exiting:: IssuanceServiceImpl:: getTopupOffers method");
		return getTopupOffersResponse;
	}

	private GetTopupOffersResponse pgTerminalValidation(TopupRequest topupRequest) throws ChatakPayException, HttpClientException, IOException {
		GetTopupOffersResponse getTopupOffersResponse;
		IssuanceTopupRequest issuanceTopupRequest = new IssuanceTopupRequest();
		issuanceTopupRequest.setAmount(topupRequest.getAmount());
		issuanceTopupRequest.setMobileNumber(topupRequest.getMobileNumber());
		issuanceTopupRequest.setOperatorId(topupRequest.getOperatorId());
		issuanceTopupRequest.setCategoryId(topupRequest.getCategoryId());
		issuanceTopupRequest.setTopupOfferId(topupRequest.getTopupOfferId());
		String output=(String)JsonUtil.sendToIssuance(String.class,
				issuanceTopupRequest, Properties.getProperty("chatak-issuance.get.topupCategoryList"));
		getTopupOffersResponse=mapper.readValue(output, GetTopupOffersResponse.class);
		if (getTopupOffersResponse == null) {
			throw new ChatakPayException();
		}
		getTopupOffersResponse.setErrorCode(ChatakPayErrorCode.GEN_001.name());
		getTopupOffersResponse.setErrorMessage(
				messageSource.getMessage(ChatakPayErrorCode.GEN_001.name(), null, LocaleContextHolder.getLocale()));
		return getTopupOffersResponse;
	}

	@Override
	public TopupResponse doTopup(TopupRequest topupRequest) {
		logger.debug("Entering:: IssuanceServiceImpl:: doTopup method");
		TopupResponse topupResponse = new TopupResponse();
		try {
			PGMerchant pgMerchant = merchantUpdateDao.getMerchant(topupRequest.getMerchantCode());
			if (pgMerchant != null) {
				PGTerminal pgTerminal = terminalDao.getTerminal(Long.valueOf(topupRequest.getTerminalId()));
				if (pgTerminal == null) {
					logger.info("Invalid Merchant: " + topupRequest.getMerchantCode());
					topupResponse.setErrorCode(ChatakPayErrorCode.TXN_0007.name());
					topupResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0007.name(), null,
							LocaleContextHolder.getLocale()));
				} else {
					topupResponse = validatePGTerminal(topupRequest);
				}
			} else {
				logger.info("Invalid Merchant: " + topupRequest.getMerchantCode());
				topupResponse.setErrorCode(ChatakPayErrorCode.TXN_0007.name());
				topupResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0007.name(), null,
						LocaleContextHolder.getLocale()));
			}
		} catch (Exception e) {
			logger.error("ERROR:: TopupServiceImpl:: doTopup method", e);
			topupResponse.setErrorCode(ChatakPayErrorCode.TXN_0992.name());
			topupResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(), null,
					LocaleContextHolder.getLocale()));
		}
		logger.debug("Exiting:: IssuanceServiceImpl:: doTopup method");
		return topupResponse;
	}

	private TopupResponse validatePGTerminal(TopupRequest topupRequest) throws ChatakPayException,IOException, HttpClientException {
		TopupResponse topupResponse;
		IssuanceTopupRequest issuanceTopupRequest = new IssuanceTopupRequest();
		issuanceTopupRequest.setAmount(topupRequest.getAmount());
		issuanceTopupRequest.setMobileNumber(topupRequest.getMobileNumber());
		issuanceTopupRequest.setOperatorId(topupRequest.getOperatorId());
		issuanceTopupRequest.setCategoryId(topupRequest.getCategoryId());
		issuanceTopupRequest.setTopupOfferId(topupRequest.getTopupOfferId());
		String output = (String) JsonUtil.sendToIssuance(String.class,
				"", Properties.getProperty("chatak-issuance.get.operators"));
		topupResponse=mapper.readValue(output, TopupResponse.class);
		if (topupResponse == null) {
			throw new ChatakPayException();
		}
		topupResponse.setErrorCode(ChatakPayErrorCode.GEN_001.name());
		topupResponse.setErrorMessage(
				messageSource.getMessage(ChatakPayErrorCode.GEN_001.name(), null, LocaleContextHolder.getLocale()));
		return topupResponse;
	}
}
