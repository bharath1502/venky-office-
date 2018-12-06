package com.chatak.acquirer.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.PaymentSchemeService;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.acq.dao.PaymentSchemeDao;
import com.chatak.pg.acq.dao.model.PGPaymentScheme;
import com.chatak.pg.bean.PaymentSchemeNameResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.PaymentScheme;
import com.chatak.pg.user.bean.PaymentSchemeRequest;
import com.chatak.pg.user.bean.PaymentSchemeResponse;
import com.chatak.pg.util.DateUtil;

@Service
public class PaymentSchemeServiceImpl implements PaymentSchemeService, PGConstants {
  private static Logger logger = Logger.getLogger(PaymentSchemeServiceImpl.class);

  @Autowired
  private PaymentSchemeDao paymentSchemeDao;

  @Override
  public PaymentSchemeRequest getpaymentSchemeyInfoId(Long id) throws ChatakAdminException {
    logger.info("Entering:: PaymentSchemesServicesImpl:: getpaymentSchemeyInfoId method");

    PaymentSchemeRequest paymentSchemesRequest = new PaymentSchemeRequest();
    PGPaymentScheme pgPaymentScheme = paymentSchemeDao.getPaymentSchemeInfoId(id);
    if (pgPaymentScheme != null) {
      paymentSchemesRequest.setId(pgPaymentScheme.getId());
      paymentSchemesRequest.setPaymentSchemeName(pgPaymentScheme.getPaymentSchemeName());
      paymentSchemesRequest.setContactName(pgPaymentScheme.getContactName());
      paymentSchemesRequest.setContactEmail(pgPaymentScheme.getContactEmail());
      paymentSchemesRequest.setContactPhone(pgPaymentScheme.getContactPhone());
      paymentSchemesRequest.setRid(pgPaymentScheme.getRid());
      paymentSchemesRequest.setTypeOfCard(pgPaymentScheme.getTypeOfCard());
      paymentSchemesRequest.setStatus(pgPaymentScheme.getStatus());
    }
    logger.info("Exiting:: PaymentSchemesServicesImpl:: getpaymentSchemeyInfoId method");
    return paymentSchemesRequest;
  }

  @Override
  public PaymentSchemeResponse updatePaymentSchemeInformation(
      PaymentSchemeRequest paymentSchemesRequest, String userid) throws ChatakAdminException {
    logger.info("Entering:: PaymentSchemesServicesImpl:: updatePaymentSchemeInformation method");
    paymentSchemesRequest.setStatus(STATUS_ACTIVE);
    PaymentSchemeResponse paymentSchemeResponse =
        paymentSchemeDao.updatePaymentSchemeInformation(paymentSchemesRequest, userid);
    logger.info("Exiting:: PaymentSchemesServicesImpl:: updatePaymentSchemeInformation method");
    return paymentSchemeResponse;
  }

  @Override
  public PaymentSchemeResponse addPaymentSchemeInformation(PaymentScheme paymentSchemeInfo,
      String userid) throws ChatakAdminException {
    logger.info("Entering:: PaymentSchemeServiceImpl:: addPaymentSchemeInformation method");
    
    PaymentSchemeResponse addPaymentSchemeResponse = new PaymentSchemeResponse();
    try {
      PaymentSchemeRequest addPaymentSchemeRequest = new PaymentSchemeRequest();
      addPaymentSchemeRequest.setPaymentSchemeName(paymentSchemeInfo.getPaymentSchemeName());
      addPaymentSchemeRequest.setContactName(paymentSchemeInfo.getContactName());
      addPaymentSchemeRequest.setContactEmail(paymentSchemeInfo.getContactEmail());
      addPaymentSchemeRequest.setContactPhone(paymentSchemeInfo.getContactPhone());
      addPaymentSchemeRequest.setRid(paymentSchemeInfo.getRid());
      addPaymentSchemeRequest.setTypeOfCard(paymentSchemeInfo.getTypeOfCard());
      addPaymentSchemeRequest.setStatus(STATUS_ACTIVE);
      addPaymentSchemeRequest.setCreatedBy(userid);
      addPaymentSchemeRequest.setCreatedDate(DateUtil.getCurrentTimestamp());
      addPaymentSchemeResponse =
          paymentSchemeDao.addPaymentSchemeInformation(addPaymentSchemeRequest, userid);
    } catch (Exception e) {
      logger.error("PaymentSchemeDaoImpl | addPaymentSchemeInformation | Exception" + e);
    }
    logger.info("Exiting:: PaymentSchemeServiceImpl:: addPaymentSchemeInformation method");
    return addPaymentSchemeResponse;
  }

  @Override
  public PaymentSchemeResponse searchPaymentScheme(PaymentSchemeRequest paymentSchemeRequest)
      throws ChatakAdminException {
    logger.info("Entering:: PaymentSchemesServicesImpl:: searchPaymentScheme method");
    PaymentSchemeResponse response = new PaymentSchemeResponse();
    try {
      List<PaymentSchemeRequest> paymentScheme = null;
      paymentScheme = paymentSchemeDao.findPaymentScheme(paymentSchemeRequest);
      if (StringUtil.isListNotNullNEmpty(paymentScheme)) {
        List<PaymentSchemeRequest> list = new ArrayList<>(paymentScheme.size());
        PaymentSchemeRequest serchPaymentSchemeRequest = null;
        for (PaymentSchemeRequest psRequest : paymentScheme) {
          serchPaymentSchemeRequest = new PaymentSchemeRequest();
          serchPaymentSchemeRequest.setId(psRequest.getId());
          serchPaymentSchemeRequest.setPaymentSchemeName(psRequest.getPaymentSchemeName());
          serchPaymentSchemeRequest.setContactName(psRequest.getContactName());
          serchPaymentSchemeRequest.setContactEmail(psRequest.getContactEmail());
          serchPaymentSchemeRequest.setContactPhone(psRequest.getContactPhone());
          serchPaymentSchemeRequest.setRid(psRequest.getRid());
          serchPaymentSchemeRequest.setStatus(psRequest.getStatus());
          serchPaymentSchemeRequest.setTypeOfCard(psRequest.getTypeOfCard());
          list.add(serchPaymentSchemeRequest);
        }
        response.setPaymentSchemesRequest(list);
        response.setTotalNoOfRows(paymentSchemeRequest.getNoOfRecords());
        response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      }
    } catch (Exception e) {
      logger.info("ERROR:: PaymentSchemesServicesImpl:: searchPaymentScheme method", e);
    }
    logger.info("Exiting:: PaymentSchemesServicesImpl:: searchPaymentScheme method");
    return response;
  }

  @Override
  public Response validateEmailId(String contactEmail) throws ChatakAdminException {
    return paymentSchemeDao.getUserByEmailId(contactEmail);
  }

  @Override
  public PaymentSchemeResponse changePaymentSchemeStatus(PaymentSchemeRequest paymentSchemeRequest,
      String paymentSchemeStatus) throws ChatakAdminException {
    logger.info("Entering:: PaymentSchemesServicesImpl:: changePaymentSchemeStatus method");
    PaymentSchemeResponse paymentSchemeResponse = new PaymentSchemeResponse();
    try {
      if (null != paymentSchemeRequest) {
        PGPaymentScheme pgPaymentScheme =
            paymentSchemeDao.findPaymentSchemeById(paymentSchemeRequest.getId());
        if ("Active".equals(paymentSchemeStatus)) {
          pgPaymentScheme.setStatus(STATUS_ACTIVE);
        } else {
          pgPaymentScheme.setStatus(STATUS_SUSPENDED);
        }
        pgPaymentScheme.setReason(paymentSchemeRequest.getReason());
        paymentSchemeDao.createOrUpdatePaymentScheme(pgPaymentScheme);
        paymentSchemeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      }
    } catch (DataAccessException e) {
      paymentSchemeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
      logger.error("Error:: PaymentSchemesServicesImpl:: changePaymentSchemeStatus method", e);
    }
    return paymentSchemeResponse;
  }

  @Override
  public PaymentSchemeNameResponse validatePaymentSchemeName(String paymentSchemeName) {
    PaymentSchemeNameResponse paymentSchemeNameResponse = new PaymentSchemeNameResponse();
    PGPaymentScheme paymentScheme = paymentSchemeDao.getPaymentSchemeName(paymentSchemeName);
    if (paymentScheme != null) {
      if (paymentScheme.getStatus() == PGConstants.STATUS_INACTIVE
          || paymentScheme.getStatus() == PGConstants.STATUS_ACTIVE) {
        paymentSchemeNameResponse.setId(paymentScheme.getId());
        paymentSchemeNameResponse.setPaymentSchemeName(paymentScheme.getPaymentSchemeName());
        paymentSchemeNameResponse.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
        paymentSchemeNameResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY));
        return paymentSchemeNameResponse;
      } else {
        paymentSchemeNameResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        paymentSchemeNameResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
        return paymentSchemeNameResponse;
      }
    } else {
      paymentSchemeNameResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      paymentSchemeNameResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      return paymentSchemeNameResponse;
    }
  }
}
