package com.chatak.acquirer.admin.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.ExchangeRateService;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.pg.bean.ExchangeRate;
import com.chatak.pg.bean.ExchangeRateResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService{
	
	private static Logger logger = Logger.getLogger(ExchangeRateServiceImpl.class);
	
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public Response addExchangeRate(ExchangeRate exchangeRate) throws ChatakAdminException {
		logger.info("Entering :: addExchangeRate :: ExchangeRateServiceImpl:: addExchangeRate method");
		 try{
			String output =  JsonUtil.postDCCRequest(exchangeRate, "/exchangeRate/addExchangeRate",String.class);
			Response exchangeResponse = mapper.readValue(output, Response.class);
			return exchangeResponse;
		 }catch(Exception e){
			logger.error("ERROR  :: ExchangeRateServiceImpl:: addExchangeRate method",e);
			throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
			}
	}

	@Override
	public ExchangeRateResponse searchExchangeRateInfo(ExchangeRate exchangeInfo) throws ChatakAdminException {
		logger.info("Entering :: searchExchangeRateInfo :: ExchangeRateServiceImpl:: addExchangeRate method");
		 try{
			 String output = JsonUtil.postDCCRequest(exchangeInfo, "/exchangeRate/searchExchangeRate",String.class);
			 ExchangeRateResponse exchangeResponse=mapper.readValue(output, ExchangeRateResponse.class);
			 return exchangeResponse;
		 }catch(Exception e){
			 logger.error("ERROR  :: ExchangeRateServiceImpl:: searchExchangeRateInfo method",e);
			 throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
			 }
	}

	@Override
	public ExchangeRate getExchangeInfoById(Long getExchangeId) throws ChatakAdminException {
		logger.info("Entering :: getExchangeInfoById :: ExchangeRateServiceImpl:: getExchangeInfoById method");
		 try{
			 String output = JsonUtil.getRequest("/exchangeRate/getExchangeInfoById/" + getExchangeId,String.class);
			 ExchangeRate exchangeResponse = mapper.readValue(output, ExchangeRate.class);	
			 return exchangeResponse;
		 }catch(HttpClientException e){
			 logger.error("ERROR  :: ExchangeRateServiceImpl:: getExchangeInfoById method",e);
			 throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
			}
	      catch(Exception e){
			 logger.error("ERROR  :: ExchangeRateServiceImpl:: getExchangeInfoById method",e);
			 throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
			 }
	}

	@Override
	public ExchangeRateResponse updateExchangeRateInfo(ExchangeRate updateExchangeRate) throws ChatakAdminException {
		logger.info("Entering :: updateExchangeRateInfo :: ExchangeRateServiceImpl:: updateExchangeRateInfo method");
		 try{
			 String output = JsonUtil.postDCCRequest(updateExchangeRate, "/exchangeRate/updateExchangeRateInfo",String.class);
			 ExchangeRateResponse exchangeResponse=mapper.readValue(output, ExchangeRateResponse.class);
			 return exchangeResponse;
		 }catch(Exception e){
			logger.error("ERROR  :: ExchangeRateServiceImpl:: updateExchangeRateInfo method",e);
			throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
			}
	}

	@Override
	public Response deleteExchangeRate(Long getExchangeRateId) throws ChatakAdminException {
		logger.info("Entering :: updateExchangeRateInfo :: ExchangeRateServiceImpl:: deleteExchangeRate method");
		 try{
			String output = JsonUtil.postDCCRequest("/exchangeRate/deleteExchangeRate/" + getExchangeRateId,String.class);
			Response dccResponse = mapper.readValue(output, Response.class);
			return dccResponse;
		 }catch(Exception e){
			logger.error("ERROR  :: ExchangeRateServiceImpl:: deleteExchangeRate method",e);
			throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
			}
	}
}
