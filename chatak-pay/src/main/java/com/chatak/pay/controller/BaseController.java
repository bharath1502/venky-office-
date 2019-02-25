/**
 * 
 */
package com.chatak.pay.controller;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.chatak.crypto.dukpt.DUKPTUtil;
import com.chatak.crypto.dukpt.util.HexUtil;
import com.chatak.pay.constants.ChatakPayErrorCode;
import com.chatak.pay.controller.model.CardData;
import com.chatak.pay.controller.model.SplitStatusRequest;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.exception.InvalidRequestException;
import com.chatak.pay.model.TSMRequest;
import com.chatak.pay.model.TSMResponse;
import com.chatak.pay.processor.CardPaymentProcessor;
import com.chatak.pay.service.BINService;
import com.chatak.pay.service.PGMerchantService;
import com.chatak.pay.service.PGSplitTransactionService;
import com.chatak.pay.service.PGTransactionService;
import com.chatak.pay.service.TopupService;
import com.chatak.pay.service.VaultService;
import com.chatak.pay.util.CardTrackParser;
import com.chatak.pay.util.CreditCardValidation;
import com.chatak.pay.util.JsonUtil;
import com.chatak.pay.util.StringUtil;
import com.chatak.pg.acq.dao.MerchantUserDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.bean.BillingData;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.OriginalChannelEnum;
import com.chatak.pg.enums.TransactionType;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.PGUtils;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.crypto.ChatakEncryptionHandler;
import com.chatak.switches.sb.exception.ChatakInvalidTransactionException;
import com.chatak.switches.sb.util.ProcessorConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 21-May-2015 7:39:06 PM
 * @version 1.0
 */
public abstract class BaseController {

  private static Logger logger = LogManager.getLogger(BaseController.class.getName());
  
  private static ObjectMapper mapper=new ObjectMapper();

  @Autowired
  protected PGTransactionService pgTransactionService;

  @Autowired
  protected CardPaymentProcessor cardPaymentProcessor;

  @Autowired
  protected TransactionDao transactionDao;

  @Autowired
  protected PGSplitTransactionService pgSplitTransactionService;

  @Autowired
  protected VaultService vaultService;

  @Autowired
  protected PGMerchantService pgMerchantService;

  @Autowired
  protected BINService binService;

  @Autowired
  protected TopupService issuanceService;

  @Autowired
  VoidTransactionDao voidTransactionDao;
  
  @Autowired
  private MerchantUserDao merchantUserDao;

  private static String dateRegex1 = "^[0-9][0-9](0[1-9]|1[0-2])$";

  private Pattern expDatePattern = Pattern.compile(dateRegex1);

  /**
   * Method to validate the base request
   * 
   * @param transactionRequest
   * @throws Exception
   * @throws ChatakPayException
 * @throws IllegalAccessException 
 * @throws InstantiationException 
 * @throws IOException 
 * @throws HttpClientException 
 * @throws ChatakInvalidTransactionException 
   */
  protected PGMerchant validateProcessRequest(TransactionRequest transactionRequest)
      throws ChatakPayException, InvalidRequestException, InstantiationException, IllegalAccessException,IOException, HttpClientException, ChatakInvalidTransactionException {
    logger.info("Validating the request");
    if (CommonUtil.isNullAndEmpty(transactionRequest.getMerchantCode())
        || CommonUtil.isNullAndEmpty(transactionRequest.getTerminalId())) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0007.name(),
          ChatakPayErrorCode.TXN_0007.value());
    } else if (null == transactionRequest.getTransactionType()) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0001.name(),
          ChatakPayErrorCode.TXN_0001.value());
    }

    logger
        .info("validateProcessRequest :: origin channel: " + transactionRequest.getOriginChannel());

    PGMerchant pgMerchant = null;
    if (isRequestFromWeb(transactionRequest)) {
      logger.info("validateProcessRequest :: origin channel: VT");

      pgMerchant = cardPaymentProcessor.validateMerchantId(transactionRequest.getMerchantCode());
      
      if(pgMerchant != null) {
    	  
    	  PGMerchantUsers merchantUsers = pgMerchantService.fetchMerchantUserByUserName(transactionRequest.getUserName());
    	  
    	  if(merchantUsers != null) {
    		  List<String> PGMerchantUserFeatureMapping = merchantUserDao.findByFeatureStatus(merchantUsers.getId());
    		  boolean flag = false;
    		  for (String FeatureName : PGMerchantUserFeatureMapping) {
    		    if (FeatureName.equalsIgnoreCase(transactionRequest.getTransactionType().name())) {
    		      flag = true;
    		    }
    		  } if (!flag) {
    		    throw new ChatakInvalidTransactionException(ChatakPayErrorCode.TXN_0027.name());
    		  }
    	  }
    	  
    	  // Ignore for an admin user who logged in from the admin portal
      }
      
    } else {
      logger.info("validateProcessRequest :: origin channel: mPOS");

      // Sale from mPOS
      String mock = Properties.getProperty("tms.mock");
      logger.info(">>>>>>> Issuance Mock Flag: " + mock);
      if ("true".equals(mock)) {
    	  pgMerchant = getMerchantOnCode(transactionRequest,null);
      } else {
    	  
        TSMRequest request = new TSMRequest();
        request.setMerchantCode(transactionRequest.getMerchantCode());
        request.setTid(transactionRequest.getTerminalId());
        request.setDeviceSerial(transactionRequest.getDeviceSerial());
  
        String output = (String) JsonUtil.sendToTSM(String.class, request,
                Properties.getProperty("chatak-tsm.service.fetch.merchant.tid"));
        TSMResponse tsmResponse =mapper.readValue(output, TSMResponse.class);
        pgMerchant = getMerchantOnCode(transactionRequest,tsmResponse);
      }
    }

    if (null != pgMerchant) {

      logger.info("Valid Merchant: " + transactionRequest.getMerchantCode());
      logger.info("Valid Terminal: " + transactionRequest.getTerminalId());

      transactionRequest.setMode(pgMerchant.getAppMode());
      transactionRequest.setProcessorMid(pgMerchant.getLitleMID());
      transactionRequest.setMerchantName(pgMerchant.getBusinessName());
      transactionRequest.setMerchantCode(pgMerchant.getMerchantCode());
      logger.info("Litle Merchant ID: " + pgMerchant.getLitleMID());

      // Set merchant currency code
      transactionRequest.setCurrencyCode(
          cardPaymentProcessor.fetchCurrencyCodeNumeric(pgMerchant.getLocalCurrency()));

      switch (transactionRequest.getTransactionType()) {
        case SALE:
        case AUTH:
        case SPLIT_ACCEPT:
          validateSALEOrAUTHRequest(transactionRequest, pgMerchant);
          break;
        case VOID:
        case REFUND_VOID:
          validateVOIDRequest(transactionRequest);
          break;
        case SPLIT_REJECT:
          if (CommonUtil.isNullAndEmpty(transactionRequest.getSplitRefNumber())) {
            throw new InvalidRequestException(ChatakPayErrorCode.TXN_0109.name(),
                ChatakPayErrorCode.TXN_0109.value());
          }
          break;
        case REVERSAL:
          break;
        default:
          break;
      }

    } else {
      logger.info("Invalid Merchant: " + transactionRequest.getMerchantCode());
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0007.name(),
          ChatakPayErrorCode.TXN_0007.value());
    }
    
    return pgMerchant;
  }

  private boolean isRequestFromWeb(TransactionRequest transactionRequest) {
    return transactionRequest.getOriginChannel() != null && (transactionRequest.getOriginChannel()
        .equals(OriginalChannelEnum.ADMIN_WEB.value())
        || transactionRequest.getOriginChannel().equals(OriginalChannelEnum.MERCHANT_WEB.value()));
  }

  private PGMerchant getMerchantOnCode(TransactionRequest transactionRequest,
      TSMResponse tsmResponse) throws InvalidRequestException {
	  PGMerchant pgMerchant = null;
	  String mock = Properties.getProperty("tms.mock");
      logger.info(">>>>>>> Issuance Mock Flag: "+mock);
      if ("true".equals(mock)) {
    	  pgMerchant = cardPaymentProcessor.validateMerchantId(transactionRequest.getMerchantCode());
    	  
      } else {
			if(tsmResponse!=null){
				if (tsmResponse.getErrorCode() != null && tsmResponse.getErrorCode().equals("0")
						&& tsmResponse.getErrorMessage() != null
						&& tsmResponse.getErrorMessage().equalsIgnoreCase("success")) {
					pgMerchant = cardPaymentProcessor.validateMerchantId(transactionRequest.getMerchantCode());
					logger.info("validateProcessRequest :: validated mPOS");

				} else {
					logger.info("Invalid Merchant/Terminal ID: " + transactionRequest.getMerchantCode());
					throw new InvalidRequestException(ChatakPayErrorCode.TXN_0114.name(),
							ChatakPayErrorCode.TXN_0114.value());
				}
			}
      }
	  
    return pgMerchant;
  }

  /**
   * Method to validate SALE transaction
   * 
   * @param transactionRequest
   * @throws Exception
   * @throws ChatakPayException
 * @throws IllegalAccessException 
 * @throws InstantiationException 
   */
  private void validateSALEOrAUTHRequest(TransactionRequest transactionRequest, PGMerchant pgMerchant)
      throws ChatakPayException, InvalidRequestException, InstantiationException, IllegalAccessException {
    logger.info("Entering :: validateSALEOrAUTHRequest");
    
    amountValidation(transactionRequest);

    if (CommonUtil.isNullAndEmpty(transactionRequest.getMerchantName())) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0014.name(),
          ChatakPayErrorCode.TXN_0014.value());
    }

    if (CommonUtil.isNullAndEmpty(transactionRequest.getInvoiceNumber())
        || CommonUtil.isNullAndEmpty(transactionRequest.getOrderId())
        || (!StringUtils.isAlphanumeric(transactionRequest.getOrderId()))
        || (!StringUtils.isAlphanumeric(transactionRequest.getInvoiceNumber()))
        || (Long.valueOf(transactionRequest.getOrderId()) == 0l)
        || (Long.valueOf(transactionRequest.getInvoiceNumber()) == 0l)) {

      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0993.name(),
          ChatakPayErrorCode.TXN_0993.value());
    }
    if ((transactionRequest.getTransactionType().equals(TransactionType.SPLIT_ACCEPT)
        || transactionRequest.getTransactionType().equals(TransactionType.SPLIT_REJECT))
        && CommonUtil.isNullAndEmpty(transactionRequest.getSplitRefNumber())) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0109.name(),
          ChatakPayErrorCode.TXN_0109.value());
    }
    if (null != transactionRequest.getCardData() && null != transactionRequest.getCardTokenData()) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0111.name(),
          ChatakPayErrorCode.TXN_0111.value());
    } else if (null != transactionRequest.getCardTokenData()) {
      try {
        CardData cardData =
            vaultService.getCardDataOnTokenData(transactionRequest.getCardTokenData());//Fetching token from vault and setting to card data
        transactionRequest.setCardData(cardData);
      } catch (Exception e) {
        logger.error("Error :: BaseController :: validateSALEOrAUTHRequest", e);
        throw new InvalidRequestException(ChatakPayErrorCode.TXN_0112.name(),
            ChatakPayErrorCode.TXN_0112.value());
      }
    }
    logger.info("Entering :: validateSALEOrAUTHRequest :: proceeding to validate card data");
    validateCardData(transactionRequest, pgMerchant);

    cardPaymentProcessor.duplicateInvoice(transactionRequest.getMerchantCode(),
        transactionRequest.getTerminalId(), transactionRequest.getInvoiceNumber());//duplicate invoice check

    try {

      cardPaymentProcessor.duplicateOrderRequest(transactionRequest.getMerchantCode(),
          transactionRequest.getOrderId(), transactionRequest.getTotalTxnAmount(),
          EncryptionUtil.encrypt(transactionRequest.getCardData().getCardNumber()),
          transactionRequest.getTransactionType().toString());
    } catch (ChatakPayException e) {
      logger.error("duplicate request  " + e);
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0099.name(),
          ChatakPayErrorCode.TXN_0099.value());
    } catch (Exception e) {
      logger.error("invalid request  " + e);
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0997.name(),
          ChatakPayErrorCode.TXN_0997.value());
    }

  }

  private void amountValidation(TransactionRequest transactionRequest)
      throws InvalidRequestException {
    if (null == transactionRequest.getMerchantAmount()
        || transactionRequest.getMerchantAmount() <= 0l
        || transactionRequest.getMerchantAmount().toString().length() > Constants.TWELVE) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0003.name(),
          ChatakPayErrorCode.TXN_0003.value());
    }

    if (null == transactionRequest.getTotalTxnAmount()
        || transactionRequest.getTotalTxnAmount() <= 0l
        || transactionRequest.getTotalTxnAmount().toString().length() > Constants.TWELVE) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0003.name(),
          ChatakPayErrorCode.TXN_0003.value());
    }

    if (null != transactionRequest.getFeeAmount() && (transactionRequest.getFeeAmount() < 0l
        || transactionRequest.getFeeAmount().toString().length() > Constants.TWELVE)) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0003.name(),
          ChatakPayErrorCode.TXN_0003.value());
    }
  }

  /**
   * Method to validate VOID transaction
   * 
   * @param transactionRequest
   * @throws InvalidRequestException
   */
  private void validateVOIDRequest(TransactionRequest transactionRequest)
      throws InvalidRequestException {
    if (CommonUtil.isNullAndEmpty(transactionRequest.getTxnRefNumber())
        || CommonUtil.isNullAndEmpty(transactionRequest.getCgRefNumber())) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0023.name(),
          ChatakPayErrorCode.TXN_0023.value());
    }

    try {

      PGTransaction saleOrRefundransaction = voidTransactionDao.getTransactionToVoid(
          transactionRequest.getMerchantCode(), transactionRequest.getTerminalId(),
          transactionRequest.getTxnRefNumber(), transactionRequest.getAuthId());
      if (saleOrRefundransaction == null || PGConstants.PG_SETTLEMENT_REJECTED
          .equalsIgnoreCase(saleOrRefundransaction.getMerchantSettlementStatus())) {
        throw new InvalidRequestException(ChatakPayErrorCode.TXN_0103.name(),
            ChatakPayErrorCode.TXN_0103.value());
      }

    } catch (InvalidRequestException e) {
      throw e;
    } catch (Exception e) {
      logger.error("" + e);
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0103.name(),
          ChatakPayErrorCode.TXN_0103.value());
    }
  }

  /**
   * Method to validate the Card data
   * 
   * @param transactionRequest
   * @throws InvalidRequestException
 * @throws IllegalAccessException 
 * @throws InstantiationException 
   */
  private void validateCardData(TransactionRequest transactionRequest, PGMerchant pgMerchant)
      throws InvalidRequestException, InstantiationException, IllegalAccessException {
    logger.info("Entering :: validateCardData");
    CardData cardData = transactionRequest.getCardData();
    if (null == cardData || (!Constants.FLAG_TRUE
        .equals(Properties.getProperty("chatak-pay.skip.card.type.check", "false"))
        && null == cardData.getCardType())) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0993.name(),
          ChatakPayErrorCode.TXN_0993.value());
    } else {
      transactionRequest.setEntryMode(transactionRequest.getEntryMode() == null
          ? EntryModeEnum.MANUAL : transactionRequest.getEntryMode());
      switch (transactionRequest.getEntryMode()) {
        case MANUAL:
        case PAN_MANUAL_ENTRY_ECOMMERCE:
        case PAN_MANUAL_ENTRY_CONTACTLESS: 
        case PAN_MANUAL_ENTRY_CHIP:
        case MANUAL_KEY_ENTRY:
        case PAN_TAP_NFC:
        case PAN_SCAN_BAR:
        case PAN_SCAN_QR:
        case PAN_SCAN_BLE:
        case PAN_AUTO_ENTRY_CONTACTLESS_M_CHIP:
        case PAN_SWIPE_CONTACTLESS:
        case CARD_TAP:
        case QR_SALE:
        // CradProgram check
        	//Commented because of card-program check
          isValidCard(cardData);
          isValidExpDate(cardData);
          validateCardType(transactionRequest, cardData);
          validateBilling(transactionRequest);
          break;
        case MAGNETIC_STRIP:
          validateMagStripCardData(transactionRequest);
          break;
        case ACCOUNT_PAY:
          break;
        default:
          throw new InvalidRequestException(ChatakPayErrorCode.TXN_0993.name(),
              ChatakPayErrorCode.TXN_0993.value());
      }
    }
    logger.info("Exiting :: validateCardData");
  }

  private void validateBilling(TransactionRequest transactionRequest)
      throws InvalidRequestException {
    if (null != transactionRequest.getBillingData()) {
      validateBillingData(transactionRequest.getBillingData());
    }
  }

  private void validateCardType(TransactionRequest transactionRequest, CardData cardData)
      throws InvalidRequestException {
    switch (transactionRequest.getCardData().getCardType()) {
      case AX:
        if (null != cardData.getCvv() ? cardData.getCvv().length() != Constants.FOUR : false) {
          throw new InvalidRequestException(ChatakPayErrorCode.TXN_0015.name(),
              ChatakPayErrorCode.TXN_0015.value());
        }
        break;

      default:
        if (null != cardData.getCvv() ? cardData.getCvv().length() != Constants.THREE : false) {
          throw new InvalidRequestException(ChatakPayErrorCode.TXN_0015.name(),
              ChatakPayErrorCode.TXN_0015.value());
        }
        break;
    }
  }

  private void isValidExpDate(CardData cardData) throws InvalidRequestException {
    if (CommonUtil.isNullAndEmpty(cardData.getExpDate())) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0009.name(),
          ChatakPayErrorCode.TXN_0009.value());
    } else if (!CommonUtil.isNullAndEmpty(cardData.getExpDate())) {
      isInvalidExpDate(cardData);
    }
  }

  private void isValidCard(CardData cardData) throws InvalidRequestException {
    if (StringUtil.isNullAndEmpty(cardData.getCardNumber())) {
      logger.info(":: validateSALEOrAUTHRequest :: card data is empty");
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0004.name(),
          ChatakPayErrorCode.TXN_0004.value());
    }
  }

  /**
   * Method to validate Magnetic Stripe Card data
   * 
   * @param transactionRequest
   * @throws InvalidRequestException
   */
  public void validateMagStripCardData(TransactionRequest transactionRequest)
      throws InvalidRequestException {
    CardData cardData = transactionRequest.getCardData();
    boolean isValidCard = false;
    CardTrackParser cardTrackParser = null;
    // Decrypting the track data
    logger.info("******************************************************************");
    logger.info("***************************" + cardData.getTrack()
        + "***************************************");
    logger.info("********************************KSN*********************************");
    logger.info("***************************" + cardData.getKeySerial()
        + "***************************************");
    logger.info("********************************KSN*********************************");

    String trackData = cardData.getTrack();
    try {
      if (StringUtil.isNullAndEmpty(cardData.getKeySerial())) {
        ChatakEncryptionHandler chatakEncryptionHandler = new ChatakEncryptionHandler();

        trackData = chatakEncryptionHandler.decrypt(Properties.getProperty("chatak.pay.salt.key"),
            cardData.getTrack());
        validateTrackData(trackData);
      } else {
        trackData =
            StringUtil
                .hexToAscii(
                    HexUtil
                        .toHex(
                            DUKPTUtil.tripleDESDecrypt(HexUtil.hex2Bytes(cardData.getTrack()),
                                HexUtil.hex2Bytes(cardData.getKeySerial()),
                                HexUtil
                                    .hex2Bytes(ProcessorConfig.get(ProcessorConfig.MAG_TEK_KEY))),
            false));
      }
      logger.info(
          "***************************" + trackData + "***************************************");
      logger.info("******************************************************************");
      cardTrackParser = new CardTrackParser(trackData.trim());
    } catch (Exception e1) {
      logger.error("Error :: BaseController :: validateMagStripCardData", e1);
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0024.name(),
          ChatakPayErrorCode.TXN_0024.value());
    }
    cardData.setTrack1(cardTrackParser.getTrack1());
    cardData.setTrack2(cardTrackParser.getTrack2());

    if ((!StringUtil.isNullAndEmpty(cardData.getTrack()))
        && (null != cardData.getTrack1() || null != cardData.getTrack2())) {
      try {

        transactionRequest.getCardData()
            .setCardNumber(cardTrackParser.getCardData().getCardNumber());
        transactionRequest.getCardData().setExpDate(cardTrackParser.getCardData().getExpDate());
        transactionRequest.getCardData().setTrack(trackData);//Setting decrypted track data in request
      } catch (Exception e) {
        logger.error("Error :: BaseController :: validateMagStripCardData", e);
        throw new InvalidRequestException(ChatakPayErrorCode.TXN_0024.name(),
            ChatakPayErrorCode.TXN_0024.value());
      }
      cardData.setCardNumber(transactionRequest.getCardData().getCardNumber());
      cardData.setExpDate(transactionRequest.getCardData().getExpDate());

      isValidCard = CreditCardValidation.isValid(Long.valueOf(cardData.getCardNumber()));
      if (isValidCard) {

        validateExpDate(cardData);
        validateCardTypeWithPAN();
      } else {
        throw new InvalidRequestException(ChatakPayErrorCode.TXN_0004.name(),
            ChatakPayErrorCode.TXN_0004.value());
      }
      validateBillingData(transactionRequest.getBillingData());
    } else {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0024.name(),
          ChatakPayErrorCode.TXN_0024.value());
    }
  }

  private void validateExpDate(CardData cardData) throws InvalidRequestException {
    if (CommonUtil.isNullAndEmpty(cardData.getExpDate())) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0009.name(),
          ChatakPayErrorCode.TXN_0009.value());
    } else {
      isInvalidExpDate(cardData);
    }
  }

  private void validateTrackData(String trackData) throws InvalidRequestException {
    if (StringUtil.isNullAndEmpty(trackData)) {
      logger.info("BaseController::validateMagStripCardData:: plain track data exception");
      throw new InvalidRequestException();
    }
  }

  private void isInvalidExpDate(CardData cardData) throws InvalidRequestException {
    Matcher matcher = expDatePattern.matcher(cardData.getExpDate());
    if (!matcher.matches()) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0009.name(),
          ChatakPayErrorCode.TXN_0009.value());
    }
  }
  
	/**
	 * Method to check given card type is matching with card number
	 * 
	 * @param card
	 * @return
	 * @throws InvalidRequestException
	 */
	public static boolean validateCardTypeWithPAN() throws InvalidRequestException {
		if (Constants.FLAG_TRUE.equals(Properties.getProperty("chatak-pay.skip.card.type.check", "false"))) {
			return true;
		}
		return false;
	}

  /**
   * Method to validate billing details
   * 
   * @param billingData
   * @throws InvalidRequestException
   */
  public static void validateBillingData(BillingData billingData) throws InvalidRequestException {
    if (null != billingData) {

      if (CommonUtil.isNullAndEmpty(billingData.getAddress1())) {
        throw new InvalidRequestException(ChatakPayErrorCode.TXN_0017.name(),
            ChatakPayErrorCode.TXN_0017.value());
      } else if (CommonUtil.isNullAndEmpty(billingData.getCity())) {
        throw new InvalidRequestException(ChatakPayErrorCode.TXN_0018.name(),
            ChatakPayErrorCode.TXN_0018.value());
      } else if (CommonUtil.isNullAndEmpty(billingData.getCountry())) {
        throw new InvalidRequestException(ChatakPayErrorCode.TXN_0019.name(),
            ChatakPayErrorCode.TXN_0019.value());
      } else if (CommonUtil.isNullAndEmpty(billingData.getState())) {
        throw new InvalidRequestException(ChatakPayErrorCode.TXN_0020.name(),
            ChatakPayErrorCode.TXN_0020.value());
      } else if (CommonUtil.isNullAndEmpty(billingData.getZipCode())) {
        throw new InvalidRequestException(ChatakPayErrorCode.TXN_0021.name(),
            ChatakPayErrorCode.TXN_0021.value());
      } else if (CommonUtil.isNullAndEmpty(billingData.getEmail())) {
        throw new InvalidRequestException(ChatakPayErrorCode.TXN_0022.name(),
            ChatakPayErrorCode.TXN_0022.value());
      }
    }
  }

  public static boolean validateSplitStatusRequest(SplitStatusRequest splitReStatusRequest)
      throws InvalidRequestException {
    if ((CommonUtil.isNullAndEmpty(splitReStatusRequest.getMerchantCode())) || (CommonUtil.isNullAndEmpty(splitReStatusRequest.getSplitRefNumber())) || (null == splitReStatusRequest.getSplitTxnAmount()
            || splitReStatusRequest.getSplitTxnAmount() <= 0l)) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0016.name(),
          ChatakPayErrorCode.TXN_0016.value());
    } 
    return true;
  }

  public static boolean validateSplitTransactionData(TransactionRequest transactionRequest)
      throws InvalidRequestException {
    if (null == transactionRequest.getSplitTxnData()) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0104.name(),
          ChatakPayErrorCode.TXN_0104.value());
    }
    if (null == transactionRequest.getSplitTxnData().getSplitAmount()
        || transactionRequest.getSplitTxnData().getSplitAmount() <= 0l
        || transactionRequest.getSplitTxnData().getSplitAmount().toString().length() > Constants.TWELVE) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0105.name(),
          ChatakPayErrorCode.TXN_0105.value());
    }
    if (null == transactionRequest.getSplitTxnData().getRefMobileNumber()
        || !PGUtils.validateNumbersOnly(
            transactionRequest.getSplitTxnData().getRefMobileNumber().toString())) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0106.name(),
          ChatakPayErrorCode.TXN_0106.value());
    }

    return true;
  }

}
