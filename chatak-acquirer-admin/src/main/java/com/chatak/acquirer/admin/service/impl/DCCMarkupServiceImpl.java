package com.chatak.acquirer.admin.service.impl;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.DCCMarkupService;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.pg.bean.DCCMarkup;
import com.chatak.pg.bean.DCCMarkupResponse;
import com.chatak.pg.bean.MerchantNameResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.util.Constants;
import com.sun.jersey.api.client.ClientResponse;

@Service
public class DCCMarkupServiceImpl implements DCCMarkupService{
	
	private static Logger logger = Logger.getLogger(DCCMarkupServiceImpl.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public MerchantNameResponse getMarkupMerchantsCode() throws ChatakAdminException {

		logger.info("Entering  :: DCCMarkupServiceImpl :: getMarkupMerchantsCode method");
		try {
			ClientResponse response = JsonUtil.postDCCRequest("/management/getMarkupMerchantsCode");
			if (HttpStatus.SC_OK != response.getStatus()) {
				throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
			} else {
				String output = response.getEntity(String.class);
				MerchantNameResponse merchantNameResponse = mapper.readValue(output,MerchantNameResponse.class);
				return merchantNameResponse;
			}
		} catch (Exception exp) {
			logger.error("ERROR :: DCCMarkupServiceImpl :: getMarkupMerchantsCode method",exp);
			throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
		}
	}
	
	@Override
	public MerchantNameResponse getActiveMerchantsCode() throws ChatakAdminException {
		logger.info("Entering :: getActiveMerchantsCode :: MerchantServiceImpl:: getActiveMerchantsCode method");
		try {
			ClientResponse response = JsonUtil.postDCCRequest("/management/getActiveMerchantsCode");
			if (response.getStatus() != HttpStatus.SC_OK) {
				throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
			} else {
				String output = response.getEntity(String.class);
				MerchantNameResponse merchantNameResponse = mapper.readValue(output, MerchantNameResponse.class);
				return merchantNameResponse;
			}
		} catch (Exception e) {
			logger.error("ERROR :: DCCMarkupServiceImpl :: getActiveMerchantsCode method",e);
			throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
		}
	}
	
	@Override
	public Response addDccMarkup(DCCMarkup dccMarkup) throws ChatakAdminException {
		logger.info("Entering :: addDccMarkup :: DCCMarkupServiceImpl:: addDccMarkup method");
		 try{
			 	ClientResponse response = JsonUtil.postDCCRequest(dccMarkup, "/management/addDccMarkup");
				if (HttpStatus.SC_OK != response.getStatus()) {
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				} else {
					String output = response.getEntity(String.class);
					Response dccMarkupResponse = mapper.readValue(output, Response.class);
					return dccMarkupResponse;
				}
		 }	catch(Exception exp){
			 logger.error("ERROR :: DCCMarkupServiceImpl :: addDccMarkup method",exp);
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				}
	}
	
	@Override
	public DCCMarkupResponse getDccMarkup(String merchantCode) throws ChatakAdminException {
		logger.info("Entering :: createBeacon :: DCCMarkupServiceImpl:: getDccMarkup method");
		 try{
			ClientResponse response = JsonUtil.getRequest("/management/getDccMarkup/" + merchantCode);
				if (response.getStatus() != HttpStatus.SC_OK) {
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				} else {
					String output = response.getEntity(String.class);
					DCCMarkupResponse dccMarkupResponse = mapper.readValue(output, DCCMarkupResponse.class);
					return dccMarkupResponse;
				}
		 }	catch(Exception e){
			 logger.error("ERROR :: DCCMarkupServiceImpl :: getDccMarkup method",e);
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				}
	}
	
	@Override
	public Response updateDCCMarkup(DCCMarkup dccMarkup) throws ChatakAdminException {
		logger.info("Entering :: addDccMarkup :: DCCMarkupServiceImpl:: addDccMarkup method");
		 try{
			 	ClientResponse response = JsonUtil.postDCCRequest(dccMarkup, "/management/updateProcessMarkup");
				if (response.getStatus() != HttpStatus.SC_OK) {
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				} else {
					String output = response.getEntity(String.class);
					Response dccMarkupResponse = mapper.readValue(output, Response.class);
					return dccMarkupResponse;
				}
		 }	catch(Exception e){
			 logger.error("ERROR :: DCCMarkupServiceImpl :: updateDCCMarkup method",e);
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				}
	}

	@Override
	public Response deleteDCCMarkup(String merchantCodeId) throws ChatakAdminException {
		logger.info("Entering :: deleteDccMarkup :: DCCMarkupServiceImpl:: deleteDccMarkup method");
		 try{
			 	ClientResponse response = JsonUtil.postDCCRequest("/management/deleteDccMarkup/" + merchantCodeId);
				if (response.getStatus() != HttpStatus.SC_OK) {
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				} else {
					String output = response.getEntity(String.class);
					Response dccResponse = mapper.readValue(output, Response.class);
					return dccResponse;
				}
		 }	catch(Exception e){
			 logger.error("ERROR :: DCCMarkupServiceImpl :: deleteDCCMarkup method",e);
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				}
	}

	@Override
	public DCCMarkupResponse searchMarkupFee(DCCMarkup dccMarkup) throws ChatakAdminException {
		logger.info("Entering :: searchMarkupFee :: DCCMarkupServiceImpl:: searchMarkupFee method");
		 try{
			 	ClientResponse response = JsonUtil.postDCCRequest(dccMarkup, "/management/searchMarkupFee");
				if (response.getStatus() != HttpStatus.SC_OK) {
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				} else {
					String output = response.getEntity(String.class);
					DCCMarkupResponse dccMarkupResponse = mapper.readValue(output, DCCMarkupResponse.class);
					return dccMarkupResponse;
				}
		 }	catch(Exception e){
			    logger.error("ERROR :: DCCMarkupServiceImpl :: searchMarkupFee method",e);
					throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
				}
	}

}
