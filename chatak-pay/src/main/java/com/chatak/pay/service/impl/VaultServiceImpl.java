package com.chatak.pay.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.crypto.dukpt.DUKPTUtil;
import com.chatak.crypto.dukpt.util.HexUtil;
import com.chatak.pay.controller.model.CardData;
import com.chatak.pay.exception.ChatakVaultException;
import com.chatak.pay.service.VaultService;
import com.chatak.pay.util.CardTrackParser;
import com.chatak.pay.util.CreditCardValidation;
import com.chatak.pay.util.StringUtil;
import com.chatak.pg.acq.dao.TokenCustomerDao;
import com.chatak.pg.acq.dao.TokenDao;
import com.chatak.pg.acq.dao.model.PGCardTokenDetails;
import com.chatak.pg.acq.dao.model.PGTokenCustomer;
import com.chatak.pg.bean.CardToken;
import com.chatak.pg.bean.CardTokenData;
import com.chatak.pg.bean.GetCardTokensRequest;
import com.chatak.pg.bean.GetCardTokensResponse;
import com.chatak.pg.bean.RegisterCardRequest;
import com.chatak.pg.bean.VaultResponse;
import com.chatak.pg.constants.VaultErrorCodes;
import com.chatak.pg.enums.CardAssociationEnum;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.PGUtils;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.crypto.ChatakEncryptionHandler;
import com.chatak.switches.sb.util.ProcessorConfig;
import com.litle.sdk.generate.MethodOfPaymentTypeEnum;

@Service
public class VaultServiceImpl implements VaultService {

  @Autowired
  private TokenDao tokenDao;

  @Autowired
  private TokenCustomerDao tokenCustomerDao;

  private static Logger logger = LogManager.getLogger(VaultServiceImpl.class);

  @Override
  public VaultResponse registerCardToken(RegisterCardRequest registerCardRequest) throws ChatakVaultException {
    logger.debug("Entering:: VaultServiceImpl:: registerCardToken method");
    VaultResponse response = new VaultResponse();
    try {
      return validateForPGTokenCustomer(registerCardRequest, response);
    } catch(ChatakVaultException e) {
      logger.error("ERROR:: VaultServiceImpl:: registerCardToken method::ChatakVaultException", e);
      throw new ChatakVaultException(e.getMessage());
    } catch(Exception e) {
      logger.error("ERROR:: VaultServiceImpl:: registerCardToken method", e);
      throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V14);
    }
  }

  private VaultResponse validateForPGTokenCustomer(RegisterCardRequest registerCardRequest,
      VaultResponse response) throws ChatakVaultException {
    PGTokenCustomer pgTokenCustomer;
    String encryptedPassword;
    if(null != registerCardRequest.getCardData()) {
      encryptedPassword = EncryptionUtil.encrypt(registerCardRequest.getPassword());
      pgTokenCustomer = tokenCustomerDao.getTokenCustomerByUserId(registerCardRequest.getUserId());
      if(null != pgTokenCustomer) {
        if(!encryptedPassword.equals(pgTokenCustomer.getPassword())) {
          response.setErrorCode(VaultErrorCodes.ERROR_CODE_V12);
          response.setErrorMessage(VaultErrorCodes.getInstance().getMessage(VaultErrorCodes.ERROR_CODE_V12));
          return response;
        }
      } else {
        pgTokenCustomer = new PGTokenCustomer();
        pgTokenCustomer.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        pgTokenCustomer.setPassword(encryptedPassword);
        pgTokenCustomer.setUserId(registerCardRequest.getUserId());
        pgTokenCustomer = tokenCustomerDao.createOrUpdateTokenCustomer(pgTokenCustomer);
      }
      if(null!=registerCardRequest.getEntryMode()&&registerCardRequest.getEntryMode().equals(EntryModeEnum.MAGNETIC_STRIP)) {
        logger.debug(" VaultServiceImpl:: registerCardToken method::Fetching PAN from Magnetic Stripe Data");
        registerCardRequest.setCardData(getCardData(registerCardRequest.getCardData()));
      }
      String pan = registerCardRequest.getCardData().getCardNumber();
      PGCardTokenDetails duplicateTokenEntry = tokenDao.findByPan(EncryptionUtil.encrypt(pan));
      if(null != duplicateTokenEntry) {
        response.setErrorCode(VaultErrorCodes.ERROR_CODE_V01);
        response.setErrorMessage(VaultErrorCodes.getInstance().getMessage(VaultErrorCodes.ERROR_CODE_V01));
        return response;

      }
      validatePGTokenCustomer(registerCardRequest, response, pgTokenCustomer, pan);
    } else {
      response.setErrorCode(VaultErrorCodes.ERROR_CODE_V03);
      response.setErrorMessage(VaultErrorCodes.getInstance().getMessage(VaultErrorCodes.ERROR_CODE_V03));
    }

    logger.debug("Exiting:: VaultServiceImpl:: registerCardToken method");
    return response;
  }

  private void validatePGTokenCustomer(RegisterCardRequest registerCardRequest, VaultResponse response,
		PGTokenCustomer pgTokenCustomer, String pan) throws ChatakVaultException {
	if(null != pgTokenCustomer) {
	  PGCardTokenDetails pgCardTokenDetails = new PGCardTokenDetails();
	  pgCardTokenDetails.setCardUserEmail(registerCardRequest.getEmail());
	  pgCardTokenDetails.setPgTokenCustomerId(pgTokenCustomer.getId());
	  pgCardTokenDetails.setCreatedDate(new Timestamp(System.currentTimeMillis()));
	  pgCardTokenDetails.setCardHolderName(registerCardRequest.getCardData().getCardHolderName());
	  pgCardTokenDetails.setCardLastFourDigits(pan.substring(pan.length() - Constants.FOUR, pan.length()));
	  pgCardTokenDetails.setCardType(registerCardRequest.getCardData().getCardType());
	  pgCardTokenDetails.setPan(EncryptionUtil.encrypt(pan));
	  pgCardTokenDetails.setExpiryDate(EncryptionUtil.encrypt(registerCardRequest.getCardData().getExpDate()));
	  pgCardTokenDetails.setToken(generateToken(pan, pgCardTokenDetails));
	  pgCardTokenDetails.setTokenExpDate(EncryptionUtil.encrypt(registerCardRequest.getCardData().getExpDate()));// Encrypted using
	  tokenDao.createTokenData(pgCardTokenDetails);
	  response.setErrorCode(VaultErrorCodes.ERROR_CODE_V00);
	  response.setErrorMessage(VaultErrorCodes.getInstance().getMessage(VaultErrorCodes.ERROR_CODE_V00));
	} else {
	  response.setErrorCode(VaultErrorCodes.ERROR_CODE_V02);
	  response.setErrorMessage(VaultErrorCodes.getInstance().getMessage(VaultErrorCodes.ERROR_CODE_V02));
	}
  }

  @Override
  public void validateRegisterCardRequest(RegisterCardRequest registerCardRequest) throws ChatakVaultException {
    logger.info("Entering:: VaultServiceImpl:: validateRegisterCardRequest method");
    if(null != registerCardRequest) {
      if(CommonUtil.isNullAndEmpty(registerCardRequest.getUserId())
         || CommonUtil.isNullAndEmpty(registerCardRequest.getPassword())) {
        throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V04);
      } else if(!isValidNumericPassword(registerCardRequest.getPassword(), StringUtil.getLong(Properties.getProperty("chatak-pay.carduser.password.min.length")), StringUtil.getLong(Properties.getProperty("chatak-pay.carduser.password.max.length")))) {
        throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V15);
      }
      if(CommonUtil.isNullAndEmpty(registerCardRequest.getEmail())) {
        throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V05);
      }
      if(null == registerCardRequest.getCardData()) {
        throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V06);
      } else {
        isValidCardData(registerCardRequest);
      }
    } else {
      logger.error("Error :: VaultServiceImpl :: validateTokensRequest - Invalid carddetails");
      throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V11);
    }
    logger.debug("Exiting:: VaultServiceImpl:: validateRegisterCardRequest method");
  }

  private void isValidCardData(RegisterCardRequest registerCardRequest)
      throws ChatakVaultException {
    if(null==registerCardRequest.getEntryMode()||!registerCardRequest.getEntryMode().equals(EntryModeEnum.MAGNETIC_STRIP)) {
      if(CommonUtil.isNullAndEmpty(registerCardRequest.getCardData().getCardNumber())) {
        throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V08);
      }
      if((CommonUtil.isNullAndEmpty(registerCardRequest.getCardData().getExpDate())) || (!PGUtils.isValidCardExpiryDate(registerCardRequest.getCardData().getExpDate())) ) {
        throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V09);
      }
    } else {
      if(CommonUtil.isNullAndEmpty(registerCardRequest.getCardData().getTrack())) {
        throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V17);
      }
    }
    if(CommonUtil.isNullAndEmpty(registerCardRequest.getCardData().getCardHolderName())) {
      throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V07);
    }

    if(CommonUtil.isNullAndEmpty(registerCardRequest.getCardData().getCardType())) {
      throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V10);
    } else {
      try {
        CardAssociationEnum.fromValue(registerCardRequest.getCardData().getCardType());
      } catch(Exception e) {
        logger.error("ERROR:: VaultServiceImpl:: validateRegisterCardRequest method ", e);
        throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V10);
      }
    }
  }

  @Override
  public void validateTokensRequest(GetCardTokensRequest getCardTokensRequest) throws ChatakVaultException {
    logger.debug("Entering:: VaultServiceImpl:: validateTokensRequest method");
    if(null != getCardTokensRequest) {
      if(CommonUtil.isNullAndEmpty(getCardTokensRequest.getUserId())
         || CommonUtil.isNullAndEmpty(getCardTokensRequest.getPassword())) {
        logger.error("Error :: VaultServiceImpl :: validateTokensRequest - Invalid authentication");
        throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V12);
      }
    } else {
      logger.error("Error :: VaultServiceImpl :: validateTokensRequest - Invalid Request");
      throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V13);
    }
    logger.debug("Exiting:: VaultServiceImpl:: validateTokensRequest method");
  }

  public String generateToken(String pan, PGCardTokenDetails pgCardTokenDetails) throws ChatakVaultException {
    logger.debug("Entering:: VaultServiceImpl:: generateToken method");

    try {
      SecureRandom r = new SecureRandom();
      String token = "";
      String encryptedToken = null;

      int counter = 0;

      while(counter++ < pan.length())
        token += r.nextInt(Constants.NINE);

      encryptedToken = EncryptionUtil.encrypt(token,
                                              pgCardTokenDetails.getCardUserEmail() + "|"
                                                  + pgCardTokenDetails.getCardLastFourDigits() + "|"
                                                  + pgCardTokenDetails.getCardType());// Token
                                                                                      // is
                                                                                      // encrypting
                                                                                      // using
                                                                                      // card
                                                                                      // user
                                                                                      // email
                                                                                      // and
                                                                                      // last
                                                                                      // 4
                                                                                      // digits
                                                                                      // and
                                                                                      // card
                                                                                      // type

      if(null != tokenDao.getPgCardTokenDetails(encryptedToken)) {
        encryptedToken = generateToken(pan, pgCardTokenDetails);
      }
      logger.debug("Exiting:: VaultServiceImpl:: generateToken method");

      return encryptedToken;
    } catch(NoSuchAlgorithmException e) {
      logger.error("ERROR:: VaultController:: getTokens method", e);
      throw new ChatakVaultException(e.getMessage());
    } catch(Exception e) {
      logger.error("ERROR:: VaultController:: getTokens method", e);
      throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V16);
    }

  }

  @Override
  public GetCardTokensResponse getCardTokens(GetCardTokensRequest getCardTokensRequest) throws ChatakVaultException {
    logger.info("Entering:: VaultServiceImpl:: getCardTokens method");
    GetCardTokensResponse cardTokensResponses = null;
    try {
      PGTokenCustomer tokenCustomer = tokenCustomerDao.getTokenCustomerByUserIdAndPassword(getCardTokensRequest.getUserId(),
                                                                                           EncryptionUtil.encrypt(getCardTokensRequest.getPassword()));
      if(null != tokenCustomer) {
        List<PGCardTokenDetails> list = tokenDao.findByTokenCustomerId(tokenCustomer.getId());
        if(null != list && list.size() != 0) {
          cardTokensResponses = new GetCardTokensResponse();
          List<CardToken> tokenList = new ArrayList<>();
          getCardTokenList(list, tokenList);
          cardTokensResponses.setCardTokenList(tokenList);
          cardTokensResponses.setErrorCode(VaultErrorCodes.ERROR_CODE_V00);
          cardTokensResponses.setErrorMessage(VaultErrorCodes.getInstance().getMessage(VaultErrorCodes.ERROR_CODE_V00));

        }
      } else {
        logger.info("VaultServiceImpl:: getCardTokens invalid Authentication");
        throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V12);
      }
      logger.info("Exiting:: VaultServiceImpl:: getCardTokens method");
      return cardTokensResponses;
    } catch(ChatakVaultException e) {
      logger.error("ERROR:: VaultServiceImpl:: getCardTokens method", e);
      throw new ChatakVaultException(e.getMessage());
    } catch(Exception e) {
      logger.error("ERROR:: VaultServiceImpl:: getCardTokens method", e);
      throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V14);
    }
  }

  private void getCardTokenList(List<PGCardTokenDetails> list, List<CardToken> tokenList)
      throws InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
      NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
      UnsupportedEncodingException
        {
    CardToken cardToken;
    for(PGCardTokenDetails tokenDetails : list) {
      cardToken = new CardToken();
      cardToken.setToken(EncryptionUtil.decrypt(tokenDetails.getToken(), tokenDetails.getCardUserEmail() + "|"
                                                                         + tokenDetails.getCardLastFourDigits()
                                                                         + "|" + tokenDetails.getCardType()));
      cardToken.setCardLastFourDigit(tokenDetails.getCardLastFourDigits());
      cardToken.setCardType(tokenDetails.getCardType());
      cardToken.setEmail(tokenDetails.getCardUserEmail());
      tokenList.add(cardToken);
    }
  }

  @Override
  public CardData getCardDataOnTokenData(CardTokenData cardTokenData) throws ChatakVaultException {
    logger.debug("Entering:: VaultServiceImpl:: getCardDataOnTokenData method");
    CardData cardData = null;
    try {
      PGTokenCustomer tokenCustomer = tokenCustomerDao.getTokenCustomerByUserIdAndPassword(cardTokenData.getUserId(),
                                                                                           EncryptionUtil.encrypt(cardTokenData.getPassword()));
      if(null != tokenCustomer) {

        PGCardTokenDetails tokenDetails = tokenDao.findByTokenAndTokenCustomerId(EncryptionUtil.encrypt(cardTokenData.getToken(),
                                                                                                        cardTokenData.getEmail()
                                                                                                            + "|"
                                                                                                            + cardTokenData.getCardLastFourDigit()
                                                                                                            + "|"
                                                                                                            + cardTokenData.getCardType()),
                                                                                 tokenCustomer.getId());
        if(null != tokenDetails) {

          cardData = new CardData();
          cardData.setCardHolderName(tokenDetails.getCardHolderName());
          cardData.setCardNumber(EncryptionUtil.decrypt(tokenDetails.getPan()));
          cardData.setExpDate(EncryptionUtil.decrypt(tokenDetails.getExpiryDate()));
          cardData.setCardType(MethodOfPaymentTypeEnum.fromValue(tokenDetails.getCardType()));
          cardData.setCvv(cardTokenData.getCvv());

        }
      }

    } catch(Exception e) {
      logger.error("ERROR:: VaultServiceImpl:: getCardDataOnTokenData method ", e);
      throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V14);
    }
    logger.debug("Exiting:: VaultServiceImpl:: getCardDataOnTokenData method");
    return cardData;
  }

  public com.chatak.pg.model.CardData getCardData(com.chatak.pg.model.CardData cardData) throws ChatakVaultException {
    boolean isValidCard = false;
    CardTrackParser cardTrackParser = null;
    ChatakEncryptionHandler chatakEncryptionHandler = null;
    logger.info("******************************************************************");
    logger.info("***************************" + cardData.getTrack() + "***************************************");
    logger.info("********************************KSN*********************************");
    logger.info("***************************" + cardData.getKeySerial() + "***************************************");
    logger.info("********************************KSN*********************************");

    String trackData = cardData.getTrack();
    try {
      if(StringUtil.isNullAndEmpty(cardData.getKeySerial())) {
        chatakEncryptionHandler = new ChatakEncryptionHandler();
        // Decryption of encrypted plain track data

        trackData = chatakEncryptionHandler.decrypt(Properties.getProperty("chatak.pay.salt.key"), cardData.getTrack());
        if(StringUtil.isNullAndEmpty(trackData)) {
          logger.info("VaultServiceImpl::getCardData:: plain track data exception");
          throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V17);
        }
      } else {
        // Decryption of the encrypted DUKPT track data
        trackData = StringUtil.hexToAscii(HexUtil.toHex(DUKPTUtil.tripleDESDecrypt(HexUtil.hex2Bytes(cardData.getTrack()),
                                                                                   HexUtil.hex2Bytes(cardData.getKeySerial()),
                                                                                   HexUtil.hex2Bytes(ProcessorConfig.get(ProcessorConfig.MAG_TEK_KEY))),
                                                        false));
      }
      logger.info("***************************" + trackData + "***************************************");
      logger.info("******************************************************************");
      cardTrackParser = new CardTrackParser(trackData.trim());
      cardData.setTrack1(cardTrackParser.getTrack1());
      cardData.setTrack2(cardTrackParser.getTrack2());
      cardData.setCardNumber(cardTrackParser.getCardData().getCardNumber());
      cardData.setExpDate(cardTrackParser.getCardData().getExpDate());
      isValidCard = CreditCardValidation.isValid(Long.valueOf(cardData.getCardNumber()));
      if(isValidCard) {

        isValidExpDate(cardData);
      } else {
        throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V08);
      }
    } catch(ChatakVaultException e) {
      logger.error("Error :: VaultServiceImpl :: getCardData ChatakVaultException", e);
      throw new ChatakVaultException(e.getMessage());
    } catch(Exception e1) {
      logger.error("Error :: VaultServiceImpl :: getCardData Exception", e1);
      throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V17);
    }
    return cardData;
  }

  private void isValidExpDate(com.chatak.pg.model.CardData cardData) throws ChatakVaultException {
    if (CommonUtil.isNullAndEmpty(cardData.getExpDate())) {
      throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V09);
    } else {
      if (!DateUtil.isValidCardExpiryDate(cardData.getExpDate())) {
        throw new ChatakVaultException(VaultErrorCodes.ERROR_CODE_V09);
      }
    }
  }

  /**
   * <<Method to validate numeric password >>
   * 
   * @param password
   * @param minLength
   * @param maxLength
   * @return
   * @throws
   */
  private boolean isValidNumericPassword(String password, Long minLength, Long maxLength) {
    return (null != password && (password.length() >= minLength && password.length() <= maxLength)
        && null != StringUtil.getLong(password));
  }

}
