package com.chatak.merchant.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.exception.ChatakPayException;
import com.chatak.merchant.model.GetFraudDetailsResponse;
import com.chatak.merchant.service.FraudService;
import com.chatak.pg.acq.dao.FraudAdvancedServicesDao;
import com.chatak.pg.acq.dao.FraudBasicServicesDao;
import com.chatak.pg.acq.dao.ISOCountryDao;
import com.chatak.pg.acq.dao.model.PGFraudBasic;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantAdvancedFraud;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.bean.ISOCountryCodeRequest;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.AdvancedFraudDTO;
import com.chatak.pg.model.FraudBasicDTO;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;

@Service("fraudService")
public class FraudServiceImpl implements FraudService {

  private static Logger logger = Logger.getLogger(FraudServiceImpl.class);

  @Autowired
  FraudBasicServicesDao fraudBasicServicesDao;

  @Autowired
  FraudAdvancedServicesDao fraudAdvancedServicesDao;

  @Autowired
  private ISOCountryDao isoCountryDao;

  @Override
  public Response createFraudBasic(FraudBasicDTO fraudBasicDTO) throws ChatakMerchantException {
    logger.info("Chatak-Merchant :: FraudServiceImpl :: createFraudBasic Entering");
    Response response = new Response();
    PGFraudBasic fraudBasic = new PGFraudBasic();
    try {
      fraudBasic = fraudBasicServicesDao.findByFraudBasicMerchantId(fraudBasicDTO.getMerchantID());
      if (fraudBasic != null) {
        fraudBasic.setId(fraudBasic.getId());
        fraudBasic.setDeniedBin(fraudBasicDTO.getDeniedBin());
        fraudBasic.setDeniedCountry(fraudBasicDTO.getDeniedCountry());
        fraudBasic.setDeniedEMail(fraudBasicDTO.getDeniedEMail());
        fraudBasic.setDeniedIP(fraudBasicDTO.getDeniedIp());
        fraudBasic.setMerchantId(fraudBasicDTO.getMerchantID());
        fraudBasic.setCreatedDate(fraudBasic.getCreatedDate());
        fraudBasic.setUpdatedDate(DateUtil.getCurrentTimestamp());
        fraudBasicServicesDao.saveOrUpdatePGFraudBasic(fraudBasic);
      } else {
        PGFraudBasic fraudBasic1 = new PGFraudBasic();
        fraudBasic1.setDeniedBin(fraudBasicDTO.getDeniedBin());
        fraudBasic1.setDeniedCountry(fraudBasicDTO.getDeniedCountry());
        fraudBasic1.setDeniedEMail(fraudBasicDTO.getDeniedEMail());
        fraudBasic1.setDeniedIP(fraudBasicDTO.getDeniedIp());
        fraudBasic1.setMerchantId(fraudBasicDTO.getMerchantID());
        fraudBasic1.setCreatedDate(DateUtil.getCurrentTimestamp());
        fraudBasicServicesDao.saveOrUpdatePGFraudBasic(fraudBasic1);

      }
      response.setErrorCode(Constants.SUCCESS_CODE);
      response.setErrorMessage(Constants.SUCESS);
    } catch (Exception e) {

      logger.error("ERROR:: FraudServiceImpl:: createFraudBasic method", e);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    logger.info("Chatak-Merchant :: FraudServiceImpl :: createFraudBasic Exiting");
    return response;

  }

  @Override
  public GetFraudDetailsResponse getFraudDetails(Long id) throws ChatakMerchantException {
    GetFraudDetailsResponse basicDTO = new GetFraudDetailsResponse();
    try {
      PGFraudBasic pgFraudBasic = fraudBasicServicesDao.findByFraudBasicMerchantId(id);

      if (pgFraudBasic != null) {
        if (pgFraudBasic.getDeniedIP() != null)
          basicDTO.setiPMultiple(Arrays.asList(pgFraudBasic.getDeniedIP().split(",")));
        if (pgFraudBasic.getDeniedCountry() != null)
          basicDTO.setCountryMultiple(Arrays.asList(pgFraudBasic.getDeniedCountry().split(",")));
        if (pgFraudBasic.getDeniedBin() != null)
          basicDTO.setBinMultiple(Arrays.asList(pgFraudBasic.getDeniedBin().split(",")));
        if (pgFraudBasic.getDeniedEMail() != null)
          basicDTO.seteMailMultiple(Arrays.asList(pgFraudBasic.getDeniedEMail().split(",")));
        basicDTO.setErrorCode(Constants.SUCCESS_CODE);
        basicDTO.setErrorMessage(Constants.SUCESS);
      } else {
        basicDTO.setErrorCode(Constants.ERROR);
        basicDTO.setErrorMessage(Constants.ERROR);
      }
    } catch (Exception e) {
      logger.error("ERROR:: FraudServiceImpl:: createFraudBasic method", e);
      basicDTO.setErrorCode(Constants.ERROR);
      basicDTO.setErrorMessage(Constants.ERROR);
    }
    return basicDTO;
  }

  @Override
  public List<Option> getISOCountries() {
    List<ISOCountryCodeRequest> isoCountry = isoCountryDao.findAllISOCountries();
    List<Option> options = new ArrayList<>();
    if (isoCountry != null) {
      for (ISOCountryCodeRequest countryRequest : isoCountry) {
        Option option = new Option();
        option.setLabel(countryRequest.getName());
        option.setValue(countryRequest.getCode());
        options.add(option);
      }
    }
    Collections.sort(options, ALPHABETICAL_ORDER);
    return options;
  }

  /**
   * Comparator method for option class
   */
  private static Comparator<Option> ALPHABETICAL_ORDER = ((Option str1, Option str2) -> {
    int res = String.CASE_INSENSITIVE_ORDER.compare(str1.getValue(), str2.getValue());
    if (res == 0) {
      res = str1.getValue().compareTo(str2.getValue());
    }
    return res;
  });

  @Override
  public AdvancedFraudDTO createAdvancedFraud(AdvancedFraudDTO advancedFraudDTO)
      throws ChatakMerchantException {
    PGMerchant merchant = null;
    PGMerchantUsers pgMerchantUsers = null;
    try {
      pgMerchantUsers = fraudBasicServicesDao.findById(advancedFraudDTO.getId());

      merchant = fraudAdvancedServicesDao.findById(pgMerchantUsers.getPgMerchantId());
      if (null != merchant && merchant.getAllowAdvancedFraudFilter().equals(1)) {
        PGMerchantAdvancedFraud pgMerchantAdvancedFraud = null;
        pgMerchantAdvancedFraud =
            CommonUtil.copyBeanProperties(advancedFraudDTO, PGMerchantAdvancedFraud.class);
        pgMerchantAdvancedFraud.setFilterType(pgMerchantAdvancedFraud.getFilterType());
        pgMerchantAdvancedFraud.setFilterOn(pgMerchantAdvancedFraud.getFilterOn());
        pgMerchantAdvancedFraud.setDuration(pgMerchantAdvancedFraud.getDuration());
        pgMerchantAdvancedFraud.setTransactionLimit(pgMerchantAdvancedFraud.getTransactionLimit());
        pgMerchantAdvancedFraud.setMaxLimit(pgMerchantAdvancedFraud.getMaxLimit());
        pgMerchantAdvancedFraud.setMerchantCode(merchant.getMerchantCode());
        pgMerchantAdvancedFraud.setAction(pgMerchantAdvancedFraud.getAction());
        fraudAdvancedServicesDao.saveOrUpdatePGMerchantAdvancedFraud(pgMerchantAdvancedFraud);

        advancedFraudDTO.setErrorCode(Constants.SUCCESS_CODE);
        advancedFraudDTO.setErrorMessage(Constants.SUCESS);
      } else {
        advancedFraudDTO.setErrorCode(Constants.ERROR_CODE);
      }
    } catch (Exception ex) {
      logger.error("Exception occured while createAdvancedFraud: ", ex);
      throw new ChatakMerchantException();
    }
    return advancedFraudDTO;
  }

  @Override
  public List<AdvancedFraudDTO> searchAdvancedFraudByCreatedBy(AdvancedFraudDTO advancedFraudDTO)
      throws ChatakMerchantException {
    List<AdvancedFraudDTO> advancedFraudList = null;
    try {
      advancedFraudList = fraudAdvancedServicesDao.searchAdvancedFraud(advancedFraudDTO);
    } catch (Exception ex) {
      logger.error("Exception occured while searchAdvancedFraudByCreatedBy: ", ex);
      throw new ChatakMerchantException();
    }
    return advancedFraudList;
  }

  public AdvancedFraudDTO searchAdvancedFraudByIdAndMerchantCode(AdvancedFraudDTO advancedFraudDTO)
      throws ChatakMerchantException {
    PGMerchantAdvancedFraud pgMerchantAdvancedFraud = null;
    try {
      advancedFraudDTO.setId(advancedFraudDTO.getId());
      advancedFraudDTO.setMerchantCode(advancedFraudDTO.getMerchantCode());
      pgMerchantAdvancedFraud = fraudAdvancedServicesDao
          .findByIdAndMerchantCode(advancedFraudDTO.getId(), advancedFraudDTO.getMerchantCode());
      CommonUtil.copyBeanProperties(pgMerchantAdvancedFraud, AdvancedFraudDTO.class);
    } catch (Exception ex) {
      logger.error("Exception occured while searchAdvancedFraudByIdAndMerchantCode: ", ex);
      throw new ChatakMerchantException();
    }
    return advancedFraudDTO;
  }

  @Override
  public void updateAdvancedFraud(AdvancedFraudDTO advancedFraudDTO) throws ChatakPayException {
    PGMerchantAdvancedFraud pgMerchantAdvancedFraud = null;
    try {
    fraudAdvancedServicesDao.findByIdAndMerchantCode(advancedFraudDTO.getId(), advancedFraudDTO.getMerchantCode());
      pgMerchantAdvancedFraud =
          CommonUtil.copyBeanProperties(advancedFraudDTO, PGMerchantAdvancedFraud.class);
      if (null != pgMerchantAdvancedFraud) {
        fraudAdvancedServicesDao.saveOrUpdatePGMerchantAdvancedFraud(pgMerchantAdvancedFraud);

        advancedFraudDTO.setErrorCode(Constants.SUCCESS_CODE);
        advancedFraudDTO.setErrorMessage(Constants.SUCESS);
      }
    } catch (Exception ex) {
      logger.error("Exception occured while updateAdvancedFraud: ", ex);
      throw new ChatakPayException();
    }
  }

  @Override
  public void deleteAdvancedFraud(Long id) throws ChatakMerchantException {
    try {
      fraudAdvancedServicesDao.deleteAdvancedFraud(id);
    } catch (Exception ex) {
      logger.error("Exception occured while deleteAdvancedFraud: ", ex);
      throw new ChatakMerchantException();
    }
  }
}
