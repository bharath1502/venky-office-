package com.chatak.acquirer.admin.service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.bean.DCCCurrency;

public interface CurrencyService {

	public DCCCurrency getCurrencyCode(String merchantCode) throws ChatakAdminException;

}
