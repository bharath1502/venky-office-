/**
 * 
 */
package com.chatak.pay.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.pay.constants.ChatakPayErrorCode;
import com.chatak.pay.constants.Constant;
import com.chatak.pay.constants.URLMappingConstants;
import com.chatak.pay.controller.model.FeeData;
import com.chatak.pay.controller.model.FeeDataResponse;
import com.chatak.pay.controller.model.Response;
import com.chatak.pay.processor.CardPaymentProcessor;
import com.chatak.pay.processor.TokenizationService;
import com.chatak.pay.util.StringUtil;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.enums.CardAssociationEnum;
import com.chatak.pg.enums.FeeTypeEnum;
import com.chatak.pg.util.Constants;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 19-Feb-2015 11:00:25 AM
 * @version 1.0
 */
  @RestController
  @RequestMapping(value = "/fee", consumes = MediaType.APPLICATION_JSON_VALUE,
  produces = MediaType.APPLICATION_JSON_VALUE)
 
public class FeeController implements URLMappingConstants, Constant {

  private Logger logger = Logger.getLogger(FeeController.class);
  
  @Autowired
  private MessageSource messageSource;

  @Autowired
  private CardPaymentProcessor cardPaymentProcessor;

  @Autowired
  private TokenizationService tokenizationService;

  @RequestMapping(value = "/{mId}/getFeeDetails/{txnAmount}", method = RequestMethod.GET)
  public Response getFeeDetails(HttpServletRequest request,
                              HttpServletResponse response,
                              HttpSession session,
                              @PathVariable String mId,
                              @PathVariable Long txnAmount) {
    logger.debug("Entering:: VoidTransactionController:: getFeeDetails method");

    FeeDataResponse feeDataResponse = new FeeDataResponse();
    try {
      if(StringUtil.isNullEmpty(mId)) {
        feeDataResponse.setErrorCode(ChatakPayErrorCode.TXN_0996.name());
        feeDataResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0996.name(), null, LocaleContextHolder.getLocale()));
        session.invalidate(); // Invalidating the session and returning
        logger.info("Invalid Access! Returning with invalidating the session");
      }
      else if(txnAmount <= 0L) {
        feeDataResponse.setErrorCode(ChatakPayErrorCode.TXN_0013.name());
        feeDataResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0013.name(), null, LocaleContextHolder.getLocale()));
        session.invalidate(); // Invalidating the session and returning
      }
      else {
        logger.info("Processing the transaction");
        PGMerchant pgMerchant = cardPaymentProcessor.validMerchant(mId);
        if(null != pgMerchant) {
          feeDataResponse.setErrorCode(ChatakPayErrorCode.TXN_0100.name());
          feeDataResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0100.name(), null, LocaleContextHolder.getLocale()));
          feeDataResponse.setTxnAmount(txnAmount);
          Long feeAmt = 0L;
          List<FeeData> fees = new ArrayList<>();
          FeeData feeData;
          
          feeData = new FeeData();
          feeData.setFeeCode("CODE002");
          feeData.setFeeDescription("Chatak Acquirer Fee");
          feeData.setFeeType(FeeTypeEnum.PERCENT);
          feeData.setFeeValue(Constants.TWO_LONG);
          fees.add(feeData);
          feeAmt += (txnAmount * feeData.getFeeValue())/Constants.MAX_PAGE_SIZE;
          
          feeData = new FeeData();
          feeData.setFeeCode("CODE003");
          feeData.setFeeDescription("Chatak Acquirer Fee");
          feeData.setFeeType(FeeTypeEnum.RANGE);
          feeData.setAmountRange("10,1000000000000");
          feeData.setFeeValue(Constants.TWO_LONG);
          fees.add(feeData);
          getFeeAmount(txnAmount, feeAmt, feeData);
          
          feeData = new FeeData();
          feeData.setFeeCode("CODE004");
          feeData.setFeeDescription("Merchant Fee");
          feeData.setFeeType(FeeTypeEnum.FIXED);
          feeData.setFeeValue(Constants.FIVE_HUNDRED_LONG);
          fees.add(feeData);
          feeAmt += feeData.getFeeValue();
          feeDataResponse.setFees(fees);
          feeDataResponse.setTotalFeeAmt(feeAmt);
          feeDataResponse.setNetAmount(feeAmt+txnAmount);
          
        }
        else {
          feeDataResponse.setErrorCode(ChatakPayErrorCode.TXN_0007.name());
          feeDataResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0007.name(), null, LocaleContextHolder.getLocale()));
          logger.info("Unauthorized merchant! and returning.");
        }
      }
    }
    catch(Exception e) {
      logger.error("Error :: FeeController :: getFeeDetails", e);
      feeDataResponse.setErrorCode(ChatakPayErrorCode.TXN_0999.name());
      feeDataResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(), null, LocaleContextHolder.getLocale()));
    }

    logger.debug("Exiting:: FeeController:: getFeeDetails method");
    return feeDataResponse;
  }

  private Long getFeeAmount(Long txnAmount, Long feeAmt, FeeData feeData) {
    if(txnAmount > Constants.TEN && txnAmount < Constants.TRILLION_LONG) {
      feeAmt += (txnAmount * feeData.getFeeValue())/Constants.MAX_PAGE_SIZE;
    }
    return feeAmt;
  }
}
