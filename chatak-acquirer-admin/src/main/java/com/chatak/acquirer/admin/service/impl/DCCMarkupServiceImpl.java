package com.chatak.acquirer.admin.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.DCCMarkupService;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.pg.bean.DCCMarkup;
import com.chatak.pg.bean.DCCMarkupResponse;
import com.chatak.pg.bean.MerchantNameResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DCCMarkupServiceImpl implements DCCMarkupService{
	
	private static Logger logger = Logger.getLogger(DCCMarkupServiceImpl.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public MerchantNameResponse getMarkupMerchantsCode() throws ChatakAdminException {

		logger.info("Entering  :: DCCMarkupServiceImpl :: getMarkupMerchantsCode method");
		try {
			String output = JsonUtil.postDCCRequest("/management/getMarkupMerchantsCode",String.class);
				MerchantNameResponse merchantNameResponse = mapper.readValue(output,MerchantNameResponse.class);
				return merchantNameResponse;
		} catch (Exception exp) {
			logger.error("ERROR :: DCCMarkupServiceImpl :: getMarkupMerchantsCode method",exp);
			throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
		}
	}
	
	@Override
	public MerchantNameResponse getActiveMerchantsCode() throws ChatakAdminException {
		logger.info("Entering :: getActiveMerchantsCode :: MerchantServiceImpl:: getActiveMerchantsCode method");
		try {
			String output = JsonUtil.postDCCRequest("/management/getActiveMerchantsCode",String.class);
				MerchantNameResponse merchantNameResponse = mapper.readValue(output, MerchantNameResponse.class);
				return merchantNameResponse;
		} catch (Exception e) {
			logger.error("ERROR :: DCCMarkupServiceImpl :: getActiveMerchantsCode method",e);
			throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
		}
	}
	
	@Override
	public Response addDccMarkup(DCCMarkup dccMarkup) throws ChatakAdminException {
		logger.info("Entering :: addDccMarkup :: DCCMarkupServiceImpl:: addDccMarkup method");
		try {
			String output = JsonUtil.postDCCRequest(dccMarkup, "/management/addDccMarkup", String.class);
			Response dccMarkupResponse = mapper.readValue(output, Response.class);
			return dccMarkupResponse;
		} catch (Exception exp) {
			logger.error("ERROR :: DCCMarkupServiceImpl :: addDccMarkup method", exp);
			throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
		}
	}
	
	@Override
	public DCCMarkupResponse getDccMarkup(String merchantCode) throws ChatakAdminException {
		logger.info("Entering :: createBeacon :: DCCMarkupServiceImpl:: getDccMarkup method");
		 try{
			String output = JsonUtil.getRequest("/management/getDccMarkup/" + merchantCode,String.class);
			DCCMarkupResponse dccMarkupResponse = mapper.readValue(output, DCCMarkupResponse.class);
			return dccMarkupResponse;
		 }catch(HttpClientException e){
			logger.error("ERROR :: DCCMarkupServiceImpl :: getDccMarkup method",e);
			throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
			}catch(Exception e){
			logger.error("ERROR :: DCCMarkupServiceImpl :: getDccMarkup method",e);
			throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
			}
	}
	
	@Override
	public Response updateDCCMarkup(DCCMarkup dccMarkup) throws ChatakAdminException {
		logger.info("Entering :: addDccMarkup :: DCCMarkupServiceImpl:: addDccMarkup method");
		 try{
			String output = JsonUtil.postDCCRequest(dccMarkup, "/management/updateProcessMarkup",String.class);
			Response dccMarkupResponse = mapper.readValue(output, Response.class);
			return dccMarkupResponse;
		 }catch(Exception e){
			logger.error("ERROR :: DCCMarkupServiceImpl :: updateDCCMarkup method",e);
			throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
			}
	}

	@Override
	public Response deleteDCCMarkup(String merchantCodeId) throws ChatakAdminException {
		logger.info("Entering :: deleteDccMarkup :: DCCMarkupServiceImpl:: deleteDccMarkup method");
		 try{
			String output = JsonUtil.postDCCRequest("/management/deleteDccMarkup/" + merchantCodeId,String.class);
			Response dccResponse = mapper.readValue(output, Response.class);
			return dccResponse;
		 }catch(Exception e){
			logger.error("ERROR :: DCCMarkupServiceImpl :: deleteDCCMarkup method",e);
			throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
			}
	}

	@Override
	public DCCMarkupResponse searchMarkupFee(DCCMarkup dccMarkup) throws ChatakAdminException {
		logger.info("Entering :: searchMarkupFee :: DCCMarkupServiceImpl:: searchMarkupFee method");
		 try{
			String output = JsonUtil.postDCCRequest(dccMarkup, "/management/searchMarkupFee",String.class);
			DCCMarkupResponse dccMarkupResponse = mapper.readValue(output, DCCMarkupResponse.class);
			return dccMarkupResponse;
		 }catch(Exception e){
			logger.error("ERROR :: DCCMarkupServiceImpl :: searchMarkupFee method",e);
			throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
			}
	}

}
