/**
 * 
 */
package com.chatak.pay.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.chatak.pay.constants.ChatakPayErrorCode;
import com.chatak.pay.controller.model.Response;
import com.chatak.pay.controller.model.SplitStatusRequest;
import com.chatak.pay.controller.model.SplitStatusResponse;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pay.controller.model.TransactionResponse;
import com.chatak.pay.exception.SplitTransactionException;
import com.chatak.pay.service.PGSplitTransactionService;
import com.chatak.pay.service.PGTransactionService;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.SplitTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGSplitTransaction;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.SplitTransactionRepository;
import com.chatak.pg.acq.dao.repository.TransactionRepository;
import com.chatak.pg.constants.ActionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.TransactionType;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.PGUtils;
import com.chatak.pg.util.RandomGenerator;
import com.chatak.pg.util.StringUtils;
import com.chatak.switches.sb.exception.ChatakInvalidTransactionException;
import com.chatak.switches.sb.exception.ServiceException;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jun 5, 2015 9:57:23 PM
 * @version 1.0
 */
@Service
public class PGSplitTransactionServiceImpl implements PGSplitTransactionService {

  @Autowired
  private MessageSource messageSource;

  @Autowired
  SplitTransactionDao splitTransactionDao;

  @Autowired
  SplitTransactionRepository splitTransactionRepository;

  @Autowired
  PGTransactionService pgTransactionService;

  @Autowired
  TransactionDao transactionDao;

  @Autowired
  TransactionRepository transactionRepository;

  @Autowired
  VoidTransactionDao voidTransactionDao;

  @Autowired
  RefundTransactionDao refundTransactionDao;

  private static Logger log = LogManager.getLogger(PGSplitTransactionServiceImpl.class);

  protected String txnRefNum = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_TXN_REF_NUM);

  protected String authId = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_AUTH_ID);

  /**
   * <<Method to log split transaction and send notification>>
   * 
   * @param transactionRequest
   * @param transactionResponse
   * @return
   * @throws SplitTransactionException
   */
  @Override
  public Response processLogSplitTransaction(TransactionRequest transactionRequest,
      TransactionResponse transactionResponse) throws SplitTransactionException {
    try {
      transactionRequest.getShareMode();
      if (null != transactionRequest.getShareMode()) {
        switch (transactionRequest.getShareMode()) {
          // case SINGLE:
          case SPLIT_PAY:
          case PAY_SOMEONE:
            logSplitTransaction(transactionRequest, transactionResponse);
            break;
          default:
            break;
        }
      } else {
        log.error("invalid shareMode:: processSplitTransaction method");
        Response response = new Response();
        response.setErrorCode(ChatakPayErrorCode.TXN_0107.name());
        response.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0107.name(), null,
            LocaleContextHolder.getLocale()));
        response.setTxnDateTime(System.currentTimeMillis());
        return response;
      }
      return null;
    } catch (Exception e) {
      log.error("ERROR:: processSplitTransaction method", e);
      throw new SplitTransactionException(e.getMessage());
    }
  }

  /**
   * <<Method to log split transaction data>>
   * 
   * @param transactionRequest
   * @param transactionResponse
   * @throws Exception
   */
  public void logSplitTransaction(TransactionRequest transactionRequest,
      TransactionResponse transactionResponse) throws Exception {
    PGSplitTransaction pgSplitTransaction = new PGSplitTransaction();
    pgSplitTransaction.setSplitAmount(transactionRequest.getSplitTxnData().getSplitAmount());
    pgSplitTransaction.setSplitTransactionId(txnRefNum);
    pgSplitTransaction.setCreatedDate(new Timestamp(System.currentTimeMillis()));
    pgSplitTransaction.setStatus(Long.valueOf((PGConstants.STATUS_INPROCESS)));
    pgSplitTransaction.setSplitMode(transactionRequest.getShareMode().toString());
    pgSplitTransaction.setPgRefTransactionId(transactionResponse.getTxnRefNumber());
    pgSplitTransaction.setMobileNumber(transactionRequest.getSplitTxnData().getRefMobileNumber());//used for split reference
    pgSplitTransaction.setMerchantId(transactionRequest.getMerchantCode());
    splitTransactionDao.createOrUpdateTransaction(pgSplitTransaction);

  }

  /**
   * <<Method to fetch and validate split transaction data and returns status>>
   * 
   * @param splitStatusRequest
   * @return
   * @throws ServiceException 
   * @throws ChatakInvalidTransactionException 
   */
  @Override
  public SplitStatusResponse getSplitTxnStatus(SplitStatusRequest splitStatusRequest) throws ChatakInvalidTransactionException {
    SplitStatusResponse statusResponse = new SplitStatusResponse();
    PGSplitTransaction pgSplitTransaction =
        splitTransactionDao.getPGSplitTransactionByMerchantIdAndPgRefTransactionIdAndSplitAmount(
            splitStatusRequest.getMerchantCode(), splitStatusRequest.getSplitRefNumber(),
            splitStatusRequest.getSplitTxnAmount());
    if (null != pgSplitTransaction) {
      if (pgSplitTransaction.getStatus().equals(Long.valueOf(PGConstants.STATUS_SUCCESS))) {
        statusResponse.setErrorCode(PGConstants.SUCCESS);
        statusResponse.setErrorMessage("Completed");
        statusResponse.setStatusInfo(true);
        statusResponse.setTxnDateTime(System.currentTimeMillis());
        return statusResponse;
      } else {
        statusValidation(statusResponse, pgSplitTransaction);
      }
      statusResponse.setStatusInfo(false);
      return statusResponse;
    } else {
      statusResponse.setErrorCode(ActionCode.ERROR_CODE_78);
      statusResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_78));
      return statusResponse;
    }
  }

  private void statusValidation(SplitStatusResponse statusResponse, PGSplitTransaction pgSplitTransaction) throws ChatakInvalidTransactionException {
	Timestamp requestTime = pgSplitTransaction.getCreatedDate();
	if (null != requestTime) {
	  long minuites = validateRequestTime(requestTime);

	  if (minuites >= 0 && minuites < Integer.parseInt("5")) {
	    statusResponse.setErrorCode(ActionCode.ERROR_CODE_Z9);
	    statusResponse
	        .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z9));
	    statusResponse.setStatusInfo(false);

	  } else {

	    if (pgSplitTransaction.getStatus().equals(Long.valueOf(PGConstants.STATUS_INPROCESS))) {
	      validateStatus(pgSplitTransaction);
	    }
	    statusResponse.setErrorCode(ActionCode.ERROR_CODE_17);
	    statusResponse
	        .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_17));
	  }
	}
  }

  private void validateStatus(PGSplitTransaction pgSplitTransaction) throws ChatakInvalidTransactionException {
	PGTransaction txnToVoid =
	      voidTransactionDao.findTransactionToReversalByMerchantIdAndPGTxnId(
	          pgSplitTransaction.getMerchantId(),
	          pgSplitTransaction.getPgRefTransactionId());
	  if (null != txnToVoid) {
	    TransactionRequest voidRequest = new TransactionRequest();
	    voidRequest.setTerminalId(txnToVoid.getTerminalId());
	    voidRequest.setMerchantCode(txnToVoid.getMerchantId());
	    voidRequest.setCgRefNumber(txnToVoid.getIssuerTxnRefNum());
	    voidRequest.setTransactionType(TransactionType.VOID);
	    voidRequest.setTxnRefNumber(txnToVoid.getTransactionId());
	    pgTransactionService.processTransaction(voidRequest, null); // Sending empty merchant as null
	  }
	  pgSplitTransaction.setStatus(Long.valueOf((PGConstants.STATUS_FAILED.toString())));
	  splitTransactionDao.createOrUpdateTransaction(pgSplitTransaction);
  }

  private long validateRequestTime(Timestamp requestTime) {
	Calendar calendar = Calendar.getInstance();
	  Date currentDateAndTime = calendar.getTime();
	  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
	  long minuites = 0;
	  try {
	    minuites = Long.parseLong(String.valueOf(currentDateAndTime.getMinutes()))
	        - simpleDateFormat.parse(requestTime.toString()).getMinutes();
      } catch (ParseException e) {
        log.error("Error :: PGSplitTransactionServiceImpl :: validateRequestTime", e);
      }
	return minuites;
  }

  /**
   * <<Method to validate split transaction completion request>>
   * 
   * @param splitStatusRequest
   * @return
   */
  @Override
  public String validateSplitTransaction(TransactionRequest transactionRequest)
      throws SplitTransactionException {
    PGSplitTransaction pgSplitTransaction =
        splitTransactionDao.getPGSplitTransactionByMerchantIdAndPgRefTransactionIdAndSplitAmount(
            transactionRequest.getMerchantCode(), transactionRequest.getSplitRefNumber(),
            transactionRequest.getTotalTxnAmount());

    if (null != pgSplitTransaction
        && PGUtils.validateNumbersOnly(transactionRequest.getMobileNumber())) {
      Long mobileNumber = Long.valueOf(transactionRequest.getMobileNumber());

      if (pgSplitTransaction.getStatus().equals(Long.valueOf(PGConstants.STATUS_INPROCESS))) {
        if (!mobileNumber.equals(pgSplitTransaction.getMobileNumber())) {
          throw new SplitTransactionException(ActionCode.ERROR_CODE_Z8);
        }
        PGTransaction pgTransaction =
            voidTransactionDao.findTransactionToReversalByMerchantIdAndPGTxnId(
                pgSplitTransaction.getMerchantId(), pgSplitTransaction.getPgRefTransactionId());
        if (null == pgTransaction) {

          throw new SplitTransactionException(ActionCode.ERROR_CODE_12);

        }
      } else if (pgSplitTransaction.getStatus().equals(Long.valueOf(PGConstants.STATUS_SUCCESS))) {
        throw new SplitTransactionException(ActionCode.ERROR_CODE_12);
      }
    } else {
      throw new SplitTransactionException(ActionCode.ERROR_CODE_78);
    }
    return null;
  }

  /**
   * << Method to update the split transaction log >>
   * 
   * @param transactionRequest
   * @param transactionResponse
   * @throws Exception
   */
  @Override
  public void updateSplitTransactionLog(TransactionRequest transactionRequest,
      TransactionResponse transactionResponse) {
    PGSplitTransaction pgSplitTransaction =
        splitTransactionDao.getPGSplitTransactionByMerchantIdAndPgRefTransactionIdAndSplitAmount(
            transactionRequest.getMerchantCode(), transactionRequest.getSplitRefNumber(),
            transactionRequest.getTotalTxnAmount());
    if (null != pgSplitTransaction) {
      if (null != transactionRequest.getCardData()) {
        pgSplitTransaction
            .setPan(EncryptionUtil.encrypt(transactionRequest.getCardData().getCardNumber()));
        pgSplitTransaction.setExpDate(transactionRequest.getCardData().getExpDate());
        pgSplitTransaction.setPanMasked(
            StringUtils.getMaskedString(transactionRequest.getCardData().getCardNumber(), Integer.parseInt("5"), Integer.parseInt("4")));
      }
      pgSplitTransaction.setPgTransactionId(transactionResponse.getTxnRefNumber());
      pgSplitTransaction.setUpdateddDate(new Timestamp(System.currentTimeMillis()));
      if (transactionResponse.getErrorCode().equals(PGConstants.SUCCESS)) {
        pgSplitTransaction.setStatus(Long.valueOf(PGConstants.STATUS_SUCCESS));
      }
    }
    splitTransactionDao.createOrUpdateTransaction(pgSplitTransaction);

  }
}
