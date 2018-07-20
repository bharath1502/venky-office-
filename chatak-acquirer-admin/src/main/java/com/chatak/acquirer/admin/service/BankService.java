/**
 * 
 */
package com.chatak.acquirer.admin.service;

import java.util.List;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.BankSearchResponse;
import com.chatak.pg.bean.CountryRequest;
import com.chatak.pg.bean.CountryResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.bean.TimeZoneRequest;
import com.chatak.pg.bean.TimeZoneResponse;
import com.chatak.pg.exception.PrepaidException;
import com.chatak.pg.model.Bank;
import com.chatak.pg.user.bean.BankResponse;


public interface BankService {
	
	public BankResponse createBank(Bank bank)throws ChatakAdminException;
	
	public BankSearchResponse searchBank(Bank bank)throws ChatakAdminException;
	
	public Bank findByBankName(Bank bank)throws ChatakAdminException;

	public List<Option> getCountries();
	
	public BankResponse deleteBankByName(String bankName);
	
	public BankResponse updateBank(Bank bank)throws ChatakAdminException;
	
	public Response getStatesByCountry(String countryId) throws ChatakAdminException;

	public List<Option> getBankData() throws ChatakAdminException;

	public Response getCurrencyByBankId(Long bankId) throws ChatakAdminException;
		
	public Response getBankName(Long currencyId)throws ChatakAdminException;
	
	public BankResponse changeBankStatus(Bank bank) throws ChatakAdminException;
	
	public TimeZoneResponse searchAllTimeZone(Long countryId) throws ChatakAdminException;
	
	public Response getTimeZone(Long timeZoneId) throws ChatakAdminException;
	
	public List<Option> getCountry();
	
	public Response getPmCountryById(Long countryId) throws ChatakAdminException;

}
