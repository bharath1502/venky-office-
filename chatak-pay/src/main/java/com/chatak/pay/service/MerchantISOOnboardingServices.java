package com.chatak.pay.service;

import com.chatak.pay.controller.model.MerchantIsoOnboardingResponse;


public interface MerchantISOOnboardingServices {
	
	public MerchantIsoOnboardingResponse fetchPmsByCurrency(String currency);
	
	public MerchantIsoOnboardingResponse fetchIsoByPmId(Long pmId);

}
