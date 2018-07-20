package com.chatak.merchant.service;

import java.util.List;

import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.exception.ChatakPayException;
import com.chatak.merchant.model.GetFraudDetailsResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.AdvancedFraudDTO;
import com.chatak.pg.model.FraudBasicDTO;

public interface FraudService {
	
	 /**
	   * Service method to save fraud basic details
	   * 
	   * @param fraudBasicDTO
	   * @return
	   * @throws ChatakAdminException
	   */
	  public Response createFraudBasic(FraudBasicDTO fraudBasicDTO) throws ChatakMerchantException;
	  

		 /**
		   * Service method to get fraud basic details
		   * 
		   * @param id
		   * @return
		 * @throws ChatakMerchantException 
		   * @throws ChatakAdminException
		   */
	  public GetFraudDetailsResponse getFraudDetails(Long id) throws ChatakMerchantException;
	  
	  public List<Option> getISOCountries();
	  
	  public AdvancedFraudDTO createAdvancedFraud(AdvancedFraudDTO advancedFraudDTO) throws ChatakMerchantException;
	  
	  public List<AdvancedFraudDTO> searchAdvancedFraudByCreatedBy(AdvancedFraudDTO advancedFraudDTO) throws ChatakMerchantException;
	  
	  public AdvancedFraudDTO searchAdvancedFraudByIdAndMerchantCode(AdvancedFraudDTO advancedFraudDTO) throws ChatakMerchantException;
	  
	  public void updateAdvancedFraud(AdvancedFraudDTO advancedFraudDTO) throws ChatakPayException;
	  
	  public void deleteAdvancedFraud(Long id) throws ChatakMerchantException;
	  
}
