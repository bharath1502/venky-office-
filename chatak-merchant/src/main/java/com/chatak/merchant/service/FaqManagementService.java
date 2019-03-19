package com.chatak.merchant.service;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.model.FaqManagementRequest;
import com.chatak.pg.user.bean.FaqManagementResponse;

public interface FaqManagementService {

	public FaqManagementResponse searchFaqManagement(FaqManagementRequest faqManagementRequest) throws ChatakMerchantException;

}
