package com.chatak.merchant.service;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.PayPageConfig;
import com.chatak.merchant.model.PayPageConfigResponse;
import com.chatak.pg.model.Response;

public interface PayPageConfigService {
	
	public Response saveOrUpdatePayPageConfig(PayPageConfig payPageConfig) throws ChatakMerchantException;
	
	public PayPageConfigResponse getPayPageConfigDetails(Long merchantId) throws ChatakMerchantException;

}
