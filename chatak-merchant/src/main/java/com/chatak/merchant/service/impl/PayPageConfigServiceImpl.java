package com.chatak.merchant.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.PayPageConfig;
import com.chatak.merchant.model.PayPageConfigResponse;
import com.chatak.merchant.service.PayPageConfigService;
import com.chatak.pg.acq.dao.PayPageConfigServiceDao;
import com.chatak.pg.acq.dao.model.PGPayPageConfig;
import com.chatak.pg.model.Response;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;

@Service
public class PayPageConfigServiceImpl implements PayPageConfigService {

  private static Logger logger = Logger.getLogger(PayPageConfigServiceImpl.class);

  @Autowired
  PayPageConfigServiceDao payPageConfigServiceDao;

  @Override
  public Response saveOrUpdatePayPageConfig(PayPageConfig payPageConfig)
      throws ChatakMerchantException {
    logger
        .info("Chatak-Merchant :: PayPageConfigServiceImpl :: saveOrUpdatePayPageConfig Entering");

    Response response = new Response();
    PGPayPageConfig pgPayPageConfig = new PGPayPageConfig();
    try {
      pgPayPageConfig =
          payPageConfigServiceDao.findByPayPageConfigMerchantId(payPageConfig.getMerchantId());

      if (null != pgPayPageConfig) {
        pgPayPageConfig.setId(pgPayPageConfig.getId());
        pgPayPageConfig.setMerchantId(pgPayPageConfig.getMerchantId());
        pgPayPageConfig.setHeader(payPageConfig.getHeader());
        pgPayPageConfig.setFooter(payPageConfig.getFooter());
        pgPayPageConfig.setPayPageLogo(payPageConfig.getPayPageLogo());
        pgPayPageConfig.setCreatedDate(pgPayPageConfig.getCreatedDate());
        pgPayPageConfig.setUpdatedDate(DateUtil.getCurrentTimestamp());
        pgPayPageConfig.setCreatedBy(pgPayPageConfig.getCreatedBy());
        pgPayPageConfig.setUpdatedBy(payPageConfig.getMerchantId().toString());
        payPageConfigServiceDao.saveOrUpdatePGPayPageConfig(pgPayPageConfig);
      } else {
        PGPayPageConfig pgPayPageConfig2 = new PGPayPageConfig();
        pgPayPageConfig2.setMerchantId(payPageConfig.getMerchantId());
        pgPayPageConfig2.setHeader(payPageConfig.getHeader());
        pgPayPageConfig2.setFooter(payPageConfig.getFooter());
        pgPayPageConfig2.setPayPageLogo(payPageConfig.getPayPageLogo());
        pgPayPageConfig2.setCreatedDate(DateUtil.getCurrentTimestamp());
        pgPayPageConfig2.setCreatedBy(payPageConfig.getMerchantId().toString());
        payPageConfigServiceDao.saveOrUpdatePGPayPageConfig(pgPayPageConfig2);

      }
      response.setErrorCode(Constants.SUCCESS_CODE);
      response.setErrorMessage(Constants.SUCESS);
    } catch (DataAccessException e) {
      logger.error("ERROR:: PayPageConfigServiceImpl:: saveOrUpdatePayPageConfig method", e);
      response.setErrorCode(Constants.ERROR_CODE);
      response.setErrorMessage(Constants.ERROR);
    }
    logger.info("Chatak-Merchant :: PayPageConfigServiceImpl :: saveOrUpdatePayPageConfig Exiting");
    return response;

  }

  @Override
  public PayPageConfigResponse getPayPageConfigDetails(Long merchantId)
      throws ChatakMerchantException {
    logger.info("Chatak-Merchant :: PayPageConfigServiceImpl :: getPayPageConfigDetails Entering");
    PayPageConfigResponse payPageConfigResponse = new PayPageConfigResponse();
    PGPayPageConfig pgPayPageConfig = new PGPayPageConfig();
    try {
      pgPayPageConfig = payPageConfigServiceDao.findByPayPageConfigMerchantId(merchantId);
      if (null != pgPayPageConfig) {
        payPageConfigResponse.setHeader(pgPayPageConfig.getHeader());
        payPageConfigResponse.setFooter(pgPayPageConfig.getFooter());
        payPageConfigResponse.setPayPageLogo(pgPayPageConfig.getPayPageLogo());
        payPageConfigResponse.setErrorMessage(Constants.SUCESS);
        payPageConfigResponse.setErrorCode(Constants.SUCCESS_CODE);
      } else {
        payPageConfigResponse.setErrorMessage(Constants.ERROR);
        payPageConfigResponse.setErrorCode(Constants.ERROR_CODE);
      }
    } catch (DataAccessException e) {
      logger.error("ERROR:: PayPageConfigServiceImpl:: getPayPageConfigDetails method", e);
      payPageConfigResponse.setErrorMessage(Constants.ERROR);
      payPageConfigResponse.setErrorCode(Constants.ERROR_CODE);
    }
    logger.info("Chatak-Merchant :: PayPageConfigServiceImpl :: getPayPageConfigDetails Exiting");
    return payPageConfigResponse;
  }
}
