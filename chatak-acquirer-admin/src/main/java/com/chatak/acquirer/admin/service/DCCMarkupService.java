package com.chatak.acquirer.admin.service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.bean.DCCMarkup;
import com.chatak.pg.bean.DCCMarkupResponse;
import com.chatak.pg.bean.MerchantNameResponse;
import com.chatak.pg.bean.Response;

public interface DCCMarkupService {
	
	public MerchantNameResponse getMarkupMerchantsCode() throws ChatakAdminException;
	
	public MerchantNameResponse getActiveMerchantsCode() throws ChatakAdminException;
	
	public Response addDccMarkup(DCCMarkup dccMarkup) throws ChatakAdminException;

	public Response deleteDCCMarkup(String merchantCodeId) throws ChatakAdminException;

	public DCCMarkupResponse getDccMarkup(String merchantCode) throws ChatakAdminException;

	public Response updateDCCMarkup(DCCMarkup dccMarkup) throws ChatakAdminException;

	public DCCMarkupResponse searchMarkupFee(DCCMarkup dccMarkup) throws ChatakAdminException;

}
