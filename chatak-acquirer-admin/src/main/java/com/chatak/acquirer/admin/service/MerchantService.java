package com.chatak.acquirer.admin.service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.UpdateMerchantResponse;

/**
 * 
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 03-Jan-2015 4:28:46 PM
 * @version 1.0
 */
public interface MerchantService {

	public AddMerchantResponse addMerchant(Merchant merchant , String userid)
			throws ChatakAdminException;

	public UpdateMerchantResponse updateMerchant(Merchant merchant)
			throws ChatakAdminException;

	public Response getSubMerchantCodeAndCompanyName(String merchantCode);
	
	public Response findProgramManagerByPartnerId(String partnerId) throws ChatakAdminException;
	
	public Response findPartnerByMerchantCode(String merchantId);
}