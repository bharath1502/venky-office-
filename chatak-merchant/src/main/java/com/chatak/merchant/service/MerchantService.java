package com.chatak.merchant.service;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.MerchantSearchResponse;
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

	/**
	 * Method to create Merchant
	 * 
	 * @param merchant
	 * @return
	 * @throws ChatakAdminException
	 */
	public AddMerchantResponse addMerchant(Merchant merchant , String userid)
			throws ChatakMerchantException;

	/**
	 * Method to update Merchant
	 * 
	 * @param updateMerchantRequest
	 * @return
	 * @throws ChatakAdminException
	 */
	public UpdateMerchantResponse updateMerchant(Merchant merchant)
			throws ChatakMerchantException;

	/**
	 * Method to update Merchant
	 * 
	 * @param merchant
	 * @return
	 * @throws ChatakAdminException
	 */
	public MerchantSearchResponse searchMerchant(Merchant merchant)
			throws ChatakMerchantException;

	/**
	 * @param merchant
	 * @return
	 * @throws ChatakMerchantException
	 */
	public Merchant getMerchant(Merchant merchant) throws ChatakMerchantException;
	
    public Response findPartnerByMerchantCode(String merchantId);
	
}
