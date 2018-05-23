package com.chatak.acquirer.admin.service.impl;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.ExchangeRateService;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.pg.bean.ExchangeRate;
import com.chatak.pg.bean.ExchangeRateResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.util.Constants;
import com.sun.jersey.api.client.ClientResponse;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService{
	
	private static Logger logger = Logger.getLogger(ExchangeRateServiceImpl.class);
	
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public Response addExchangeRate(ExchangeRate exchangeRate) throws ChatakAdminException {
		logger.info("Entering :: addExchangeRate :: ExchangeRateServiceImpl:: addExchangeRate method");
		 try{
			 	ClientResponse response = JsonUtil.postDCCRequest(exchangeRate, "/exchangeRate/addExchangeRate");
				if (response.getStatus() != HttpStatus.SC_OK) {
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				} else {
					String output = response.getEntity(String.class);
					Response exchangeResponse = mapper.readValue(output, Response.class);
					return exchangeResponse;
				}
		 }	catch(Exception e){
			 logger.error("ERROR  :: ExchangeRateServiceImpl:: addExchangeRate method",e);
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				}
	}

	@Override
	public ExchangeRateResponse searchExchangeRateInfo(ExchangeRate exchangeInfo) throws ChatakAdminException {
		logger.info("Entering :: searchExchangeRateInfo :: ExchangeRateServiceImpl:: addExchangeRate method");
		 try{
			 	ClientResponse response = JsonUtil.postDCCRequest(exchangeInfo, "/exchangeRate/searchExchangeRate");
				return validateStatus(response);
		 }	catch(Exception e){
			 logger.error("ERROR  :: ExchangeRateServiceImpl:: searchExchangeRateInfo method",e);
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				}
	}

	private ExchangeRateResponse validateStatus(ClientResponse response)
			throws ChatakAdminException, IOException, JsonParseException, JsonMappingException {
		if (response.getStatus() != HttpStatus.SC_OK) {
			throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
		} else {
			String output = response.getEntity(String.class);
			ExchangeRateResponse exchangeResponse = mapper.readValue(output, ExchangeRateResponse.class);
			return exchangeResponse;
		}
	}

	@Override
	public ExchangeRate getExchangeInfoById(Long getExchangeId) throws ChatakAdminException {
		logger.info("Entering :: getExchangeInfoById :: ExchangeRateServiceImpl:: getExchangeInfoById method");
		 try{
			ClientResponse response = JsonUtil.getRequest("/exchangeRate/getExchangeInfoById/" + getExchangeId);
				if (response.getStatus() != HttpStatus.SC_OK) {
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				} else {
					String output = response.getEntity(String.class);
					ExchangeRate exchangeResponse = mapper.readValue(output, ExchangeRate.class);
					return exchangeResponse;
				}
		 }	catch(Exception e){
			 logger.error("ERROR  :: ExchangeRateServiceImpl:: getExchangeInfoById method",e);
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				}

	}

	@Override
	public ExchangeRateResponse updateExchangeRateInfo(ExchangeRate updateExchangeRate) throws ChatakAdminException {
		logger.info("Entering :: updateExchangeRateInfo :: ExchangeRateServiceImpl:: updateExchangeRateInfo method");
		 try{
			 	ClientResponse response = JsonUtil.postDCCRequest(updateExchangeRate, "/exchangeRate/updateExchangeRateInfo");
				return validateStatus(response);
		 }	catch(Exception e){
			 logger.error("ERROR  :: ExchangeRateServiceImpl:: updateExchangeRateInfo method",e);
				throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				}
	}

	@Override
	public Response deleteExchangeRate(Long getExchangeRateId) throws ChatakAdminException {
		logger.info("Entering :: updateExchangeRateInfo :: ExchangeRateServiceImpl:: deleteExchangeRate method");
		 try{
			 	ClientResponse response = JsonUtil.postDCCRequest("/exchangeRate/deleteExchangeRate/" + getExchangeRateId);
				if (response.getStatus() != HttpStatus.SC_OK) {
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				} else {
					String output = response.getEntity(String.class);
					Response dccResponse = mapper.readValue(output, Response.class);
					return dccResponse;
				}
		 }	catch(Exception e){
			 logger.error("ERROR  :: ExchangeRateServiceImpl:: deleteExchangeRate method",e);
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				}
	}
}
