package com.chatak.acquirer.admin.service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.bean.ExchangeRate;
import com.chatak.pg.bean.ExchangeRateResponse;
import com.chatak.pg.bean.Response;

public interface ExchangeRateService {

	public Response addExchangeRate(ExchangeRate exchangeRate) throws ChatakAdminException;

	public ExchangeRateResponse searchExchangeRateInfo(ExchangeRate exchangeInfo) throws ChatakAdminException;

	public ExchangeRate getExchangeInfoById(Long getExchangeId) throws ChatakAdminException;

	public ExchangeRateResponse updateExchangeRateInfo(ExchangeRate updateExchangeRate) throws ChatakAdminException;

	public Response deleteExchangeRate(Long getExchangeId) throws ChatakAdminException;

}
