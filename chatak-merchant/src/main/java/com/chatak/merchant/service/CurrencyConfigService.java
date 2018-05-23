package com.chatak.merchant.service;

import com.chatak.pg.bean.Response;

public interface CurrencyConfigService {
		
	public Response getCurrencyCodeNumeric(String currencyCodeAlpha);
	
	public Response getcurrencyCodeAlpha(String bankCurrencyCode);
	
}
