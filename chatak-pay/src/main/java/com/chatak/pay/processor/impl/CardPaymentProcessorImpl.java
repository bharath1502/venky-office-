/**
 * 
 */
package com.chatak.pay.processor.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chatak.pay.constants.ChatakPayErrorCode;
import com.chatak.pay.controller.model.CardDetails;
import com.chatak.pay.controller.model.PaymentDetails;
import com.chatak.pay.controller.model.Response;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.exception.InvalidRequestException;
import com.chatak.pay.processor.CardPaymentProcessor;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.MerchantTerminalDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.OnlineTxnLogDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGOnlineTxnLog;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.TransactionStatus;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.StringUtils;
import com.chatak.switches.sb.util.ProcessorConfig;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 21-Feb-2015 11:26:53 PM
 * @version 1.0
 */

@SuppressWarnings("deprecation")
@Service
public class CardPaymentProcessorImpl implements CardPaymentProcessor {

  private static Logger logger = Logger.getLogger(CardPaymentProcessorImpl.class);

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private OnlineTxnLogDao onlineTxnLogDao;

  @Autowired
  private TransactionDao transactionDao;

  @Autowired
  protected MerchantTerminalDao merchantTerminalDao;

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private CurrencyConfigRepository currencyConfigRepository;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;

  @Autowired
  VoidTransactionDao voidTransactionDao;

  @Override
  public PGMerchant validMerchant(String mId) {
    try {
      PGMerchant pgMerchant = merchantUpdateDao.getMerchant(mId);
      if (null != pgMerchant && pgMerchant.getStatus() != null
          && pgMerchant.getStatus().equals(0)) {
        return pgMerchant;
      }
    } catch (Exception e) {
      logger.error("ERROR:: initializeCardPayment method", e);
    }
    return null;
  }

  @Override
  public PGMerchant validateMerchantIdAndTerminalId(String mId, String tId) {
    try {
      return merchantTerminalDao.validateMerchantIdAndTerminalId(mId, tId);
    } catch (Exception e) {
      logger.error("ERROR:: initializeCardPayment method", e);
    }
    return null;
  }

  @Override
  public PGOnlineTxnLog initializeCardPayment(PaymentDetails paymentDetails,
      CardDetails cardDetails) {
    PGOnlineTxnLog onlineTxnLog = null;
    try {
      onlineTxnLog = logEntry(TransactionStatus.INITATE, paymentDetails, cardDetails);
    } catch (Exception e) {
      logger.error("Error :: CardPaymentProcessorImpl :: initializeCardPayment Method", e);
    }
    return onlineTxnLog;
  }

  @Override
  public Response processCardPayment(ISOMsg isoMsg) {
    return null;
  }

  /**
   * @param txnState
   * @param paymentDetails
   * @param cardDetails
   * @return
   * @throws Exception
   */
  private PGOnlineTxnLog logEntry(TransactionStatus txnState, PaymentDetails paymentDetails,
      CardDetails cardDetails) {

    PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
    pgOnlineTxnLog.setBillerAddress(paymentDetails.getAddress());
    pgOnlineTxnLog.setBillerAddress2(paymentDetails.getAddress2());
    pgOnlineTxnLog.setBillerCity(paymentDetails.getBillerCity());
    if (null != paymentDetails.getBillerCountry()) {
      pgOnlineTxnLog.setBillerCountry(paymentDetails.getBillerCountry().value());
    }
    pgOnlineTxnLog.setBillerEmail(paymentDetails.getBillerEmail());
    pgOnlineTxnLog.setBillerName(paymentDetails.getBillerName());
    pgOnlineTxnLog.setBillerState(paymentDetails.getBillerState());
    pgOnlineTxnLog.setBillerZip(paymentDetails.getBillerZip());
    pgOnlineTxnLog.setMerchantAmount(paymentDetails.getMerchantAmount());
    pgOnlineTxnLog.setMerchantId(paymentDetails.getMerchantId());
    pgOnlineTxnLog.setMerchantReturnUrl(paymentDetails.getReturnURL());
    if (null != cardDetails) {
      pgOnlineTxnLog.setPanData(EncryptionUtil.encrypt(cardDetails.getNumber()));
      pgOnlineTxnLog.setPanMasked(StringUtils.getMaskedString(cardDetails.getNumber(), Constants.FIVE, Constants.FOUR));
    }
    pgOnlineTxnLog.setPosTxnDate(paymentDetails.getOriginTime());
    pgOnlineTxnLog.setTxnDescription(paymentDetails.getDescription());
    pgOnlineTxnLog.setTxnState(txnState.name());
    pgOnlineTxnLog.setTxnTotalAmount(paymentDetails.getTotalAmount());
    pgOnlineTxnLog.setTxnType(paymentDetails.getTransactionType().name());
    pgOnlineTxnLog.setRequestIPPort(paymentDetails.getClientIP()
        + ((paymentDetails.getClientPort() == null || paymentDetails.getClientPort() == 0) ? ""
            : ":" + paymentDetails.getClientPort()));
    pgOnlineTxnLog.setOrderId(paymentDetails.getOrderId());
    pgOnlineTxnLog.setRequestDateTime(new Timestamp(System.currentTimeMillis()));
    pgOnlineTxnLog.setAppMode(
        (paymentDetails.getMode() == null) ? ProcessorConfig.DEMO : paymentDetails.getMode());

    pgOnlineTxnLog = onlineTxnLogDao.logRequest(pgOnlineTxnLog);
    return pgOnlineTxnLog;
  }

  /**
   * Method to check duplicate txn
   * 
   * @param merchantCode
   * @param orderId
   * @return
   * @throws DataAccessException
   */
  public boolean isDuplicateRequest(String merchantCode, String orderId) throws ChatakPayException {
    boolean isDuplicate = onlineTxnLogDao.isDuplicateRequest(merchantCode, orderId);
    if (isDuplicate) {
      throw new ChatakPayException(messageSource.getMessage(ChatakPayErrorCode.TXN_0012.name(),
          null, LocaleContextHolder.getLocale()));
    }
    return false;
  }

  /**
   * @param merchantId
   * @param paymentMethod
   * @param txnAmount
   */
  @Override
  public void updateAccount(String merchantId, String paymentMethod, Long txnAmount) {
    PGAccount pgAccount = accountDao.getPgAccount(merchantId);
    if (paymentMethod.equals(PGConstants.PAYMENT_METHOD_CREDIT)) {
      pgAccount.setCurrentBalance(pgAccount.getCurrentBalance() - txnAmount);
    } else if (paymentMethod.equals(PGConstants.PAYMENT_METHOD_DEBIT)) {
      pgAccount.setCurrentBalance(pgAccount.getCurrentBalance() + txnAmount);
    } else if (paymentMethod.equals(PGConstants.AUTH_PAYMENT_METHOD)) {
      pgAccount.setCurrentBalance(pgAccount.getCurrentBalance() + txnAmount);
    } else if (paymentMethod.equals(PGConstants.CAPTURE_PAYMENT_METHOD)) {
      pgAccount.setAvailableBalance(pgAccount.getAvailableBalance() + txnAmount);
    }
    accountRepository.save(pgAccount);

  }

  /**
   * @param merchantCode
   * @param orderId
   * @param txnAmount
   * @param invoiceNumber
   * @param registerNumber
   * @param txnType
   * @return
   * @throws ChatakPayException
   */
  @Override
  public boolean isDuplicateRequest(String merchantCode, String orderId, Long txnAmount,
      String invoiceNumber, String registerNumber, String txnType) throws ChatakPayException {
    boolean isDuplicate = onlineTxnLogDao.isDuplicateRequest(merchantCode, orderId, txnAmount,
        invoiceNumber, registerNumber, txnType);
    if (isDuplicate) {
      throw new ChatakPayException(messageSource.getMessage(ChatakPayErrorCode.TXN_0012.name(),
          null, LocaleContextHolder.getLocale()));
    }
    return false;
  }

  /**
   * @param merchantCode
   * @param orderId
   * @param txnType
   * @return
   */
  @Override
  public boolean isDuplicateRequest(String merchantCode, String orderId, String txnType)
      throws ChatakPayException {
    boolean isDuplicate = onlineTxnLogDao.isDuplicateRequest(merchantCode, orderId, txnType);
    if (isDuplicate) {
      throw new ChatakPayException(messageSource.getMessage(ChatakPayErrorCode.TXN_0012.name(),
          null, LocaleContextHolder.getLocale()));
    }
    return false;
  }

  /**
   * @param merchantCode
   * @param orderId
   * @param registerNumber
   * @param panData
   * @param txnType
   * @return
   * @throws ChatakPayException
   */
  @Override
  public void duplicateRequest(String merchantCode, String orderId, Long txnAmount,
      String registerNumber, String panData, String txnType) throws ChatakPayException {
    Timestamp previousRequest = onlineTxnLogDao.duplicateRequest(merchantCode, orderId, txnAmount,
        registerNumber, panData, txnType);

    if (null != previousRequest) {
      Calendar calendar = Calendar.getInstance();
      Date currentDateAndTime = calendar.getTime();
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
      long pastHoursData = 0;
      try {
        pastHoursData = Long.parseLong(String.valueOf(currentDateAndTime.getHours()))
            - simpleDateFormat.parse(previousRequest.toString()).getHours();
      } catch (ParseException e) {
        logger.error("Error :: CardPaymentProcessorImpl :: duplicateRequest Method", e);
      }
      if (pastHoursData >= 0 && pastHoursData < Constants.TWENTY_FOUR) {
        throw new ChatakPayException("duplicate transaction");
      }
    }
  }

  /**
   * @param merchantCode
   * @param terminalId
   * @param invoiceNumber
   * @return
   * @throws ChatakPayException
   */
  /**
   * @param merchantCode
   * @param terminalId
   * @param invoiceNumber
   * @throws ChatakPayException
   */
  @Override
  public void duplicateInvoice(String merchantCode, String terminalId, String invoiceNumber)
      throws InvalidRequestException {
    PGTransaction transaction =
        transactionDao.getTransactionOnInvoiceNum(merchantCode, terminalId, invoiceNumber);
    if (null != transaction) {
      throw new InvalidRequestException(ChatakPayErrorCode.TXN_0025.name(),
          ChatakPayErrorCode.TXN_0025.value());
    }
  }

  @Override
  public void duplicateOrderRequest(String merchantCode, String orderId, Long txnAmount,
      String panData, String txnType) throws ChatakPayException {
    Timestamp previousRequest =
        onlineTxnLogDao.duplicateOrderRequest(merchantCode, orderId, txnAmount, panData, txnType);

    if (null != previousRequest) {
      long pastHoursData = 0;
      Calendar calendar = Calendar.getInstance();
      Date currentDateAndTime = calendar.getTime();
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
      try {
        pastHoursData = Long.parseLong(String.valueOf(currentDateAndTime.getHours()))
            - simpleDateFormat.parse(previousRequest.toString()).getHours();
      } catch (ParseException e) {
        logger.error("Error :: CardPaymentProcessorImpl :: duplicateOrderRequest Method", e);
      }
      if (pastHoursData >= 0 && pastHoursData < Constants.TWENTY_FOUR) {
        throw new ChatakPayException("duplicate transaction");
      }
    }
  }

  public String fetchCurrencyCodeNumeric(String localCurrencyAlpha) throws ChatakPayException {
    PGCurrencyConfig pgCurrencyConfig =
        currencyConfigRepository.findByCurrencyCodeAlpha(localCurrencyAlpha);
    if (pgCurrencyConfig != null) {
      logger.info("Merchant currency code: " + pgCurrencyConfig.getCurrencyCodeNumeric());
      return pgCurrencyConfig.getCurrencyCodeNumeric();
    }
    logger.info("Merchant currency code, returning default currency code of \"840\"");
    return "840"; // Default US currency code
  }

  @Override
  public PGMerchant validateMerchantId(String merchantId) {
    try {
      return merchantTerminalDao.validateMerchantId(merchantId);
    } catch (Exception e) {
      logger.error("ERROR:: validateMerchantId method", e);
    }
    return null;
  }
}
