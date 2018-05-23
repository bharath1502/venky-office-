/**
 * 
 */
package com.chatak.merchant.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.merchant.controller.model.MerchantProfile;
import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.service.MerchantProfileService;
import com.chatak.pg.acq.dao.CountryDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.MerchantUserAddressDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantUserAddress;
import com.chatak.pg.acq.dao.model.PGState;
import com.chatak.pg.bean.CountryRequest;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.UpdateMerchantRequest;
import com.chatak.pg.user.bean.UpdateMerchantResponse;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jun 17, 2015 6:26:27 PM
 * @version 1.0
 */
@Service("merchantProfileService")
public class MerchantProfileServiceImpl implements MerchantProfileService {
  @Autowired
  MerchantDao merchantDao;

  @Autowired
  CountryDao countryDao;

  @Autowired
  MerchantUserAddressDao merchantUserAddressDao;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;

  @Autowired
  MerchantProfileDao merchantProfileDao;

  @Override
  public MerchantProfile getMerchantProfile(MerchantProfile merchantProfile)
      throws ChatakMerchantException {

    PGMerchant pgMerchant =
        merchantProfileDao.getMerchantById(Long.valueOf(merchantProfile.getId()));
    PGAccount pgAccount = merchantProfileDao.getPgAccount(pgMerchant.getMerchantCode());

    if (pgAccount != null) {
      merchantProfile.setAddress1(pgMerchant.getAddress1());
      merchantProfile.setAddress2(pgMerchant.getAddress2());
      merchantProfile.setCity(pgMerchant.getCity());
      merchantProfile.setCountry(pgMerchant.getCountry());
      merchantProfile.setId(pgMerchant.getId());
      merchantProfile.setState(pgMerchant.getState());
      merchantProfile.setAppMode(pgMerchant.getAppMode());
      merchantProfile.setBusinessName(pgMerchant.getBusinessName());
      merchantProfile.setBusinessURL(pgMerchant.getBusinessURL());
      merchantProfile.setEmailId(pgMerchant.getEmailId());
      merchantProfile.setEstimatedYearlySale(pgMerchant.getEstimatedYearlySale());
      merchantProfile.setFax(pgMerchant.getFax());
      merchantProfile.setFederalTaxId(pgMerchant.getFederalTaxId());
      merchantProfile.setFirstName(pgMerchant.getFirstName());
      merchantProfile.setLastName(pgMerchant.getLastName());
      merchantProfile.setNoOfEmployee(pgMerchant.getNoOfEmployee());
      merchantProfile.setOwnership(pgMerchant.getOwnership());

      merchantProfile
          .setAutoSettlement(pgMerchant.getMerchantConfig().getAutoSettlement() == 0 ? 0 : 1);

      merchantProfile.setPhone(pgMerchant.getPhone());
      merchantProfile.setPin(pgMerchant.getPin());
      merchantProfile.setRole(pgMerchant.getRole());
      merchantProfile.setSalesTaxId(pgMerchant.getSalesTaxId());
      merchantProfile.setStateTaxId(pgMerchant.getStateTaxId());
      merchantProfile.setStatus(pgMerchant.getStatus());
      merchantProfile.setTimeZone(pgMerchant.getTimeZone());
      merchantProfile.setUpdatedDate(
          com.chatak.merchant.util.DateUtils.timestampToString(pgMerchant.getUpdatedDate()));

      merchantProfile.setBusinessStartDate(
          com.chatak.merchant.util.DateUtils.timestampToString(pgMerchant.getBusinessStartDate()));
      merchantProfile.setMerchantCallBackURL(pgMerchant.getMerchantCallBack());
      merchantProfile.setFeeProgram(pgMerchant.getMerchantConfig().getFeeProgram());
      merchantProfile.setProcessor(pgMerchant.getMerchantConfig().getProcessor());
      merchantProfile.setRefunds(pgMerchant.getMerchantConfig().getRefunds() == 1 ? true : false);
      merchantProfile.setShippingAmount(
          pgMerchant.getMerchantConfig().getShippingAmount() == 1 ? true : false);
      merchantProfile
          .setTaxAmount(pgMerchant.getMerchantConfig().getTaxAmount() == 1 ? true : false);
      merchantProfile
          .setTipAmount(pgMerchant.getMerchantConfig().getTipAmount() == 1 ? true : false);
      merchantProfile.setUserName(pgMerchant.getUserName());
      merchantProfile.setMerchantCode(pgMerchant.getMerchantCode());

      merchantProfile.setAutoPaymentMethod(pgAccount.getAutoPaymentMethod());
      merchantProfile.setAutoTransferDay(pgAccount.getAutoTransferDay());
      merchantProfile.setCategory(pgAccount.getCategory());
      merchantProfile.setMerchantCallBackURL(pgMerchant.getMerchantCallBack());
      /* Mailing address start */
      PGMerchantUserAddress mailinAddress = merchantUserAddressDao.getMerchantUserAddressByUserId(
          pgMerchant.getPgMerchantUsers().get(0).getId().toString());
      if (null == mailinAddress) {
        PGMerchantUserAddress merchantUserAddress = new PGMerchantUserAddress();
        merchantUserAddress.setMerchantUserId(pgMerchant.getPgMerchantUsers().get(0).getId());
        merchantUserAddress.setMerchantCode(pgMerchant.getMerchantCode());
        mailinAddress =
            merchantUserAddressDao.createOrUpdateMerchantUserAddress(merchantUserAddress);
      }
      merchantProfile.setMailingAddress1(mailinAddress.getAddress1());
      merchantProfile.setMailingAddress2(mailinAddress.getAddress2());
      merchantProfile.setMailingCity(mailinAddress.getCity());
      merchantProfile.setMailingCountry(mailinAddress.getCountry());
      merchantProfile.setMailingState(mailinAddress.getState());
      merchantProfile.setMailingPin(mailinAddress.getPin());
      merchantProfile.setMerchantUserId(mailinAddress.getMerchantUserId().toString());

      /* Mailing address end */

      return merchantProfile;
    }
    return null;
  }

  @Override
  public UpdateMerchantResponse updateMerchantProfile(MerchantProfile merchantProfile)
      throws ChatakMerchantException {
    UpdateMerchantRequest updateMerchantRequest = new UpdateMerchantRequest();

    updateMerchantRequest.setAddress1(merchantProfile.getAddress1());
    updateMerchantRequest.setMerchantCode(merchantProfile.getMerchantCode());
    updateMerchantRequest.setAddress2(merchantProfile.getAddress2());
    updateMerchantRequest.setBusinessName(merchantProfile.getBusinessName());
    updateMerchantRequest.setBusinessURL(merchantProfile.getBusinessURL());
    updateMerchantRequest.setCity(merchantProfile.getCity());
    updateMerchantRequest.setCountry(merchantProfile.getCountry());
    updateMerchantRequest.setEmailId(merchantProfile.getEmailId());
    updateMerchantRequest.setEstimatedYearlySale(merchantProfile.getEstimatedYearlySale());
    updateMerchantRequest.setFax(merchantProfile.getFax());
    updateMerchantRequest.setFederalTaxId(merchantProfile.getFederalTaxId());
    updateMerchantRequest.setFeeProgram(merchantProfile.getFeeProgram());
    updateMerchantRequest.setFirstName(merchantProfile.getFirstName());
    updateMerchantRequest.setLastName(merchantProfile.getLastName());
    updateMerchantRequest.setNoOfEmployee(merchantProfile.getNoOfEmployee());
    updateMerchantRequest.setOwnership(merchantProfile.getOwnership());
    updateMerchantRequest.setPhone(merchantProfile.getPhone());
    updateMerchantRequest.setPin(merchantProfile.getPin());
    updateMerchantRequest.setState(merchantProfile.getState());
    updateMerchantRequest.setUserName(merchantProfile.getUserName());
    updateMerchantRequest.setTimeZone(merchantProfile.getTimeZone());
    updateMerchantRequest.setStatus(merchantProfile.getStatus());
    updateMerchantRequest.setStateTaxId(merchantProfile.getStateTaxId());
    updateMerchantRequest.setAppMode(merchantProfile.getAppMode());
    updateMerchantRequest.setRole(merchantProfile.getRole());
    updateMerchantRequest.setSalesTaxId(merchantProfile.getSalesTaxId());

    updateMerchantRequest.setProcessor(merchantProfile.getProcessor());
    updateMerchantRequest.setRefunds(merchantProfile.getRefunds());
    updateMerchantRequest.setTipAmount(merchantProfile.getTipAmount());
    updateMerchantRequest.setTaxAmount(merchantProfile.getTaxAmount());
    updateMerchantRequest.setAllowRepeatSale(merchantProfile.getAllowRepeatSale());
    updateMerchantRequest.setAutoSettlement(merchantProfile.getAutoSettlement());
    updateMerchantRequest.setShowRecurringBilling(merchantProfile.getShowRecurringBilling());
    updateMerchantRequest.setShippingAmount(merchantProfile.getShippingAmount());
    updateMerchantRequest.setAutoPaymentMethod(merchantProfile.getAutoPaymentMethod());
    updateMerchantRequest.setAutoTransferDay(merchantProfile.getAutoTransferDay());
    updateMerchantRequest.setAutoTransferLimit(merchantProfile.getAutoTransferLimit());
    updateMerchantRequest.setCategory(merchantProfile.getCategory());
    updateMerchantRequest.setMerchantCallBackURL(merchantProfile.getMerchantCallBackURL());

    UpdateMerchantResponse updateMerchantResponse =
        merchantUserAddressDao.updateMerchantProfile(updateMerchantRequest);
    PGMerchantUserAddress mailinAddress =
        merchantUserAddressDao.getMerchantUserAddressByUserId(merchantProfile.getMerchantUserId());
    mailinAddress.setAddress1(merchantProfile.getMailingAddress1());
    mailinAddress.setAddress2(merchantProfile.getMailingAddress2());
    mailinAddress.setCity(merchantProfile.getMailingCity());
    mailinAddress.setState(merchantProfile.getMailingState());
    mailinAddress.setCountry(merchantProfile.getMailingCountry());
    mailinAddress.setPin(merchantProfile.getMailingPin());
    merchantUserAddressDao.createOrUpdateMerchantUserAddress(mailinAddress);
    return updateMerchantResponse;

  }

  @Override
  public Response getStatesByCountry(String countryId) throws ChatakMerchantException {
    Response response = new Response();
    CountryRequest countryRequest = countryDao.getCountryByName(countryId);
    if (countryRequest.getName() != null) {

      List<PGState> pgStates = merchantProfileDao.getStateByCountryId(countryRequest.getId());

      List<Option> options = new ArrayList<>(pgStates.size());
      for (PGState state : pgStates) {
        Option option = new Option();
        option.setLabel(state.getName());
        option.setValue(state.getName());
        options.add(option);
      }
      Collections.sort(options, alphabeticalOrder);
      response.setResponseList(options);
      response.setErrorCode("00");
      response.setErrorMessage("SUCCESS");
      response.setTotalNoOfRows(options.size());

    } else {
      response.setErrorCode("99");
      response.setErrorMessage("failure");
    }
    return response;
  }

  @Override
  public List<Option> getCountries() throws ChatakMerchantException {
	  List<Option> options = new ArrayList<>();
    List<CountryRequest> countryRequests = countryDao.findAllCountries();
    if (countryRequests != null) {
      for (CountryRequest countryRequest : countryRequests) {
        Option option = new Option();
        option.setLabel(countryRequest.getName());
        option.setValue(countryRequest.getName());
        options.add(option);
      }
    }
    Collections.sort(options, alphabeticalOrder);
    return options;
  }

  /**
   * Comparator method for option class
   */
  private static Comparator<Option> alphabeticalOrder = ((Option str1, Option str2) -> {
    int res = String.CASE_INSENSITIVE_ORDER.compare(str1.getValue(), str2.getValue());
    if (res == 0) {
      res = str1.getValue().compareTo(str2.getValue());
    }
    return res;
  });

}
