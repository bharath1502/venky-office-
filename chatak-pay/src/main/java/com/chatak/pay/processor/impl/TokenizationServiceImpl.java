/**
 * 
 */
package com.chatak.pay.processor.impl;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.pay.controller.model.CardDetails;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.model.DETokenizeRequest;
import com.chatak.pay.model.DETokenizeResponse;
import com.chatak.pay.model.TokenizeRequest;
import com.chatak.pay.model.TokenizeResponse;
import com.chatak.pay.processor.TokenizationService;
import com.chatak.pay.util.JsonUtil;
import com.chatak.pg.acq.dao.TokenDao;
import com.chatak.pg.acq.dao.model.PGCardTokenDetails;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.RandomGenerator;
import com.litle.sdk.generate.MethodOfPaymentTypeEnum;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 01-Apr-2015 4:28:18 PM
 * @version 1.0
 */

@Service
public class TokenizationServiceImpl implements TokenizationService {

  private static Logger logger = Logger.getLogger(CardPaymentProcessorImpl.class);

  @Autowired
  TokenDao tokenDao;

  @Override
  public String tokenize(CardDetails cardDetails)throws ChatakPayException {
    final Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
    PGCardTokenDetails pgCardTokenDetails = new PGCardTokenDetails();
    String id = RandomGenerator.generateRandNumeric(Constants.TEN);
    pgCardTokenDetails.setId(Long.valueOf(id));
    pgCardTokenDetails.setCardUserEmail(cardDetails.getEmail());
    pgCardTokenDetails.setCardLastFourDigits(cardDetails.getNumber().substring(cardDetails.getNumber().length() - Constants.FOUR));
    pgCardTokenDetails.setCardType(cardDetails.getType().toString());
    PGCardTokenDetails tokenExists = tokenDao.findByEmailAndCardLastFourAndCardType(pgCardTokenDetails.getCardUserEmail(),
                                                                                   pgCardTokenDetails.getCardLastFourDigits(),
                                                                                   pgCardTokenDetails.getCardType());

    TokenizeRequest request = new TokenizeRequest();
    if(null == tokenExists) {
    request.setAccVerificationRefNum(Properties.getProperty("chatak-pay.accVerificationRefNum"));
    request.setAccVerificationResults(Integer.valueOf(Properties.getProperty("chatak-pay.accVerificationResults")));
    request.setCardHolderData(cardDetails.getName());
    request.setExpDate(cardDetails.getExpMonthYear().substring(0, Constants.TWO) + "/" + cardDetails.getExpMonthYear().substring(Constants.TWO));
    request.setCardHolderDataLen(Integer.valueOf(Properties.getProperty("chatak-pay.accCardHolderDataLen")));
    request.setPanNum(cardDetails.getNumber());
    request.setIdvPerformed(Integer.valueOf(Properties.getProperty("chatak-pay.idvPerformed")));
    request.setVersionNum(Properties.getProperty("chatak-pay.versionNum"));
    request.setStorageLoc(Properties.getProperty("chatak-pay.storageLoc"));
    request.setTokenAssuranceLevel(Properties.getProperty("chatak-pay.tokenAssuranceLevel"));
    request.setTokenDeviceInfolen(Integer.valueOf(Properties.getProperty("chatak-pay.tokenDeviceInfolen")));
    request.setDeviceInfoData(Properties.getProperty("chatak-pay.deviceInfoData"));
    TokenizeResponse response = null;
    try {
      response = (TokenizeResponse) JsonUtil.postRequest(TokenizeResponse.class,
                                                         request,
                                                         Properties.getProperty("chatak-pay.serviceEndPointTokenize"));

      if(null != response) {
        String encryptedToken = EncryptionUtil.encrypt(response.getPaymentToken(),
                                                       pgCardTokenDetails.getCardUserEmail() + "|"
                                                           + pgCardTokenDetails.getCardLastFourDigits() + "|"
                                                           + pgCardTokenDetails.getCardType());
        pgCardTokenDetails.setToken(encryptedToken);
        pgCardTokenDetails.setTokenExpDate(response.getTokenExpDate());
        pgCardTokenDetails.setCreatedDate(timeStamp);
        tokenDao.createTokenData(pgCardTokenDetails);
        return encryptedToken;
      }

    } catch(Exception e) {
      logger.error("Error :: TokenizationServiceImpl :: tokenize", e);
    }
    return null;
    } else {
      return tokenExists.getToken();
    }
  }

  @Override
  public CardDetails deTokenize(String token) {

    PGCardTokenDetails pgCardTokenDetails = tokenDao.getPgCardTokenDetails(token);
    if(null != pgCardTokenDetails) {

      DETokenizeRequest request = new DETokenizeRequest();
      String decryptedToken = null;
      try {
        decryptedToken = EncryptionUtil.decrypt(pgCardTokenDetails.getToken(),
                                                pgCardTokenDetails.getCardUserEmail() + "|"
                                                    + pgCardTokenDetails.getCardLastFourDigits() + "|"
                                                    + pgCardTokenDetails.getCardType());
      } catch(Exception e1) {
        logger.error("Error :: TokenizationServiceImpl :: deTokenize-decrypt", e1);
      }
      request.setPaymentToken(decryptedToken);
      request.setTokenExpiryDate(pgCardTokenDetails.getTokenExpDate().substring(0, Constants.TEN));// TO-DO:
                                                                                        // need
                                                                                        // to
                                                                                        // add
      request.setVersionNum(Properties.getProperty("chatak-pay.versionNum"));
      DETokenizeResponse tokenResponse = null;
      try {
        tokenResponse = (DETokenizeResponse) JsonUtil.postRequest(DETokenizeResponse.class,
                                                                  request,
                                                                  Properties.getProperty("chatak-pay.serviceEndPointDeTokenize"));

        if(null != tokenResponse) {
          String expDate = tokenResponse.getPanExpDate().replace("/", "");
          CardDetails cardDetails = new CardDetails();
          cardDetails.setExpMonthYear(expDate);
          cardDetails.setName("Test");
          cardDetails.setType(MethodOfPaymentTypeEnum.fromValue(pgCardTokenDetails.getCardType()));
          cardDetails.setNumber(tokenResponse.getPan());
          return cardDetails;
        }

      } catch(Exception e) {
        logger.error("Error :: TokenizationServiceImpl :: deTokenize-JsonUtil", e);
      }

    } else {
      return null;
    }
    return null;
  }

}
