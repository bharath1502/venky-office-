/**
 * 
 */
package com.chatak.merchant.service;

import java.util.List;

import com.chatak.merchant.controller.model.MerchantProfile;
import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.UpdateMerchantResponse;

/**
 *
 * << Services for getting and updating merchant profiles >>
 *
 * @author Girmiti Software
 * @date Jun 17, 2015 6:26:10 PM
 * @version 1.0
 */
public interface MerchantProfileService {
  public UpdateMerchantResponse updateMerchantProfile(MerchantProfile merchantProfile) throws ChatakMerchantException;
  public MerchantProfile getMerchantProfile(MerchantProfile merchantProfile) throws ChatakMerchantException;
  public Response getStatesByCountry(String countryId) throws ChatakMerchantException;
  public List<Option> getCountries() throws ChatakMerchantException;
}
