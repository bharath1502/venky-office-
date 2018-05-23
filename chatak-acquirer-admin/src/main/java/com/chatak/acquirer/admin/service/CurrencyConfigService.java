package com.chatak.acquirer.admin.service;

import java.util.List;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.CurrencyDTO;

public interface CurrencyConfigService {
	
	public CurrencyDTO saveCurrencyConfig(CurrencyDTO currencyDTO)throws ChatakAdminException;
	
	public Response searchCurrencyConfig(CurrencyDTO currencyDTO)throws ChatakAdminException;
	
	public CurrencyDTO getCurrencyConfigById(Long currencyConfigId) throws ChatakAdminException;
	
	public CurrencyDTO updateCurrencyConfig(CurrencyDTO currencyDTO)throws ChatakAdminException;
	
	public CurrencyDTO deleteCurrencyConfig(Long currencyConfigId) throws ChatakAdminException;

	public List<Option> getCurrencyConfigCode();
	
	public Response getCurrencyCodeNumeric(String currencyCodeAlpha);
	
	public Response getcurrencyCodeAlpha(String bankCurrencyCode);

	public Response findByCurrencyName(String currencyName);

	public List<Option> getAllCurrencies();
	
	public Response changeCurrencyStatus(CurrencyDTO currencyDTO, String cardStatus);
	

}
