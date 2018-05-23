/**
 * 
 */
package com.chatak.pay.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.bean.BillingData;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.OriginalChannelEnum;
import com.chatak.pg.enums.TransactionType;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.PGUtils;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.crypto.ChatakEncryptionHandler;
import com.chatak.switches.sb.util.ProcessorConfig;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 21-May-2015 7:39:06 PM
 * @version 1.0
 */
public abstract class BaseController {

  private Logger logger = Logger.getLogger(BaseController.class);

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

  private static String dateRegex1 = "^[0-9][0-9](0[1-9]|1[0-2])$";

  private Pattern expDatePattern = Pattern.compile(dateRegex1);

  /**
   * Method to validate the base request
   * 
   * @param transactionRequest
   * @throws Exception
   * @throws ChatakPayException
   */
  protected void validateProcessRequest(TransactionRequest transactionRequest)
      throws ChatakPayException, InvalidRequestException {
    logger.info("Validating the request");
    if (CommonUtil.isNullAndEmpty(transactionRequest.getMerchantId())
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
    if (transactionRequest.getOriginChannel() != null
        && (transactionRequest.getOriginChannel().equals(OriginalChannelEnum.ADMIN_WEB.value())
            || transactionRequest.getOriginChannel()
                .equals(OriginalChannelEnum.MERCHANT_WEB.value()))) {
      logger.info("validateProcessRequest :: origin channel: VT");

      pgMerchant = cardPaymentProcessor.validateMerchantId(transactionRequest.getMerchantId());
    } else {
      logger.info("validateProcessRequest :: origin channel: mPOS");

      // Sale from mPOS
      TSMRequest request = new TSMRequest();
      request.setMerchantCode(transactionRequest.getMerchantId());
      request.setTid(transactionRequest.getTerminalId());

      TSMResponse tsmResponse = (TSMResponse) JsonUtil.sendToTSM(TSMResponse.class, request,
          Properties.getProperty("chatak-tsm.service.fetch.merchant.tid"));

      pgMerchant = getMerchantOnCode(transactionRequest, pgMerchant, tsmResponse);
    }

    if (null != pgMerchant) {

      logger.info("Valid Merchant: " + transactionRequest.getMerchantId());
      logger.info("Valid Terminal: " + transactionRequest.getTerminalId());

      transactionRequest.setMode(pgMerchant.getAppMode());
      transactionRequest.setProcessorMid(pgMerchant.getLitleMID());
      transactionRequest.setMerchantName(pgMerchant.getBusinessName());
      logger.info("Litle Merchant ID: " + pgMerchant.getLitleMID());

      // Set merchant currency code
      transactionRequest.setCurrencyCode(
          cardPaymentProcessor.fetchCurrencyCodeNumeric(pgMerchant.getLocalCurrency()));

      switch (transactionRequest.getTransactionType()) {
        case SALE:
        case AUTH:
        case SPLIT_ACCEPT:
          validateSALEOrAUTHRequest(transactionRequest);
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
      logger.info("Invalid Merchant: " + transactionRequest.getMerchantId());
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0007.name(),
          ChatakPayErrorCode.TXN_0007.value());
    }
  }

  private PGMerchant getMerchantOnCode(TransactionRequest transactionRequest, PGMerchant pgMerchant,
      TSMResponse tsmResponse) throws InvalidRequestException {
    if (tsmResponse.getErrorCode() != null && tsmResponse.getErrorCode().equals("0")
        && tsmResponse.getErrorMessage() != null
        && tsmResponse.getErrorMessage().equalsIgnoreCase("success")) {
      pgMerchant = cardPaymentProcessor.validateMerchantId(transactionRequest.getMerchantId());
      logger.info("validateProcessRequest :: validated mPOS");

    } else {
      logger.info("Invalid Merchant/Terminal ID: " + transactionRequest.getMerchantId());
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0114.name(),
          ChatakPayErrorCode.TXN_0114.value());
    }
    return pgMerchant;
  }

  /**
   * Method to validate SALE transaction
   * 
   * @param transactionRequest
   * @throws Exception
   * @throws ChatakPayException
   */
  private void validateSALEOrAUTHRequest(TransactionRequest transactionRequest)
      throws ChatakPayException, InvalidRequestException {
    logger.info("Entering :: validateSALEOrAUTHRequest");
    
    amountValidation(transactionRequest);

    if (CommonUtil.isNullAndEmpty(transactionRequest.getMerchantName())) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0014.name(),
          ChatakPayErrorCode.TXN_0014.value());
    }

    if (CommonUtil.isNullAndEmpty(transactionRequest.getInvoiceNumber())
        || CommonUtil.isNullAndEmpty(transactionRequest.getRegisterNumber())
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
    validateCardData(transactionRequest);

    cardPaymentProcessor.duplicateInvoice(transactionRequest.getMerchantId(),
        transactionRequest.getTerminalId(), transactionRequest.getInvoiceNumber());//duplicate invoice check

    try {

      cardPaymentProcessor.duplicateOrderRequest(transactionRequest.getMerchantId(),
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
          transactionRequest.getMerchantId(), transactionRequest.getTerminalId(),
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
   */
  private void validateCardData(TransactionRequest transactionRequest)
      throws InvalidRequestException {
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
          // BIN check
          binService.validateBin(cardData.getCardNumber());
          break;
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
          isValidCard(cardData);
          isValidExpDate(cardData);
          validateCardType(transactionRequest, cardData);
          validateBilling(transactionRequest);
          break;
        case MAGNETIC_STRIP:
          validateMagStripCardData(transactionRequest);
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
        validateCardTypeWithPAN(transactionRequest.getCardData());
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
  public static boolean validateCardTypeWithPAN(CardData card) throws InvalidRequestException {
    String cardNumber = card.getCardNumber().substring(0, Constants.TWO) ;
    if (Constants.FLAG_TRUE
        .equals(Properties.getProperty("chatak-pay.skip.card.type.check", "false"))) {
      return true;
    }
    switch (card.getCardType()) {

      case VI:
        return isVisaCard(card);
      case AX:
        return isAmexCard(cardNumber);
      case MC:
        if ("5".equals(String.valueOf(card.getCardNumber().charAt(0))))
          return true;
        else
          throw new InvalidRequestException(ChatakPayErrorCode.TXN_0010.name(),
              ChatakPayErrorCode.TXN_0010.value());
      case DC:
        if ("36".equals(cardNumber) || "38".equals(cardNumber) || "30".equals(cardNumber))
          return true;
        else
          throw new InvalidRequestException(ChatakPayErrorCode.TXN_0010.name(),
              ChatakPayErrorCode.TXN_0010.value());
      case ME:
        if ("6".equals(String.valueOf(card.getCardNumber().charAt(0))))
          return true;
        else
          throw new InvalidRequestException(ChatakPayErrorCode.TXN_0010.name(),
              ChatakPayErrorCode.TXN_0010.value());
      case DI:
        if ("6011".equals(card.getCardNumber().substring(0, Constants.FOUR)))
          return true;
        else
          throw new InvalidRequestException(ChatakPayErrorCode.TXN_0010.name(),
              ChatakPayErrorCode.TXN_0010.value());
      default:
        throw new InvalidRequestException(ChatakPayErrorCode.TXN_0010.name(),
            ChatakPayErrorCode.TXN_0010.value());

    }

  }

  private static boolean isAmexCard(String cardNumber) throws InvalidRequestException {
    if ("37".equals(cardNumber) || "34".equals(cardNumber))
      return true;
    else
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0010.name(),
          ChatakPayErrorCode.TXN_0010.value());
  }

  private static boolean isVisaCard(CardData card) throws InvalidRequestException {
    if ("4".equals(String.valueOf(card.getCardNumber().charAt(0))))
      return true;
    else
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0010.name(),
          ChatakPayErrorCode.TXN_0010.value());
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
    if (CommonUtil.isNullAndEmpty(splitReStatusRequest.getMerchantId())) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0016.name(),
          ChatakPayErrorCode.TXN_0016.value());
    } else if (CommonUtil.isNullAndEmpty(splitReStatusRequest.getSplitRefNumber())) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0016.name(),
          ChatakPayErrorCode.TXN_0016.value());
    } else if (null == splitReStatusRequest.getSplitTxnAmount()
        || splitReStatusRequest.getSplitTxnAmount() <= 0l) {
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
