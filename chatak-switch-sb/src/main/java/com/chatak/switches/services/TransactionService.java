/**
 * 
 */
package com.chatak.switches.services;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.AccountFeeLogDao;
import com.chatak.pg.acq.dao.AccountHistoryDao;
import com.chatak.pg.acq.dao.BINDao;
import com.chatak.pg.acq.dao.CardProgramDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.EMVTransactionDao;
import com.chatak.pg.acq.dao.FeeDetailDao;
import com.chatak.pg.acq.dao.IsoServiceDao;
import com.chatak.pg.acq.dao.MerchantCardProgramMapDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.PGParamsDao;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.SplitTransactionDao;
import com.chatak.pg.acq.dao.SwitchDao;
import com.chatak.pg.acq.dao.SwitchTransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountFeeLog;
import com.chatak.pg.acq.dao.model.PGAccountHistory;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGEMVTransaction;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantCardProgramMap;
import com.chatak.pg.acq.dao.model.PGSplitTransaction;
import com.chatak.pg.acq.dao.model.PGSwitchTransaction;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.BINRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.acq.dao.repository.SplitTransactionRepository;
import com.chatak.pg.bean.Request;
import com.chatak.pg.bean.Response;
import com.chatak.pg.bean.ReversalRequest;
import com.chatak.pg.bean.SplitTxnRequest;
import com.chatak.pg.constants.ActionCode;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.FeePostingStatus;
import com.chatak.pg.constants.ISOConstants;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.emv.util.EMVData;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.NationalPOSEntryModeEnum;
import com.chatak.pg.enums.ProcessorType;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.model.ProcessingFee;
import com.chatak.pg.model.VirtualAccFeeReversalRequest;
import com.chatak.pg.model.virtualAccFeePostResponse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.DateUtils;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.MagneticStripeCardUtil;
import com.chatak.pg.util.PGUtils;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.RandomGenerator;
import com.chatak.pg.util.StringUtils;
import com.chatak.switches.enums.TransactionType;
import com.chatak.switches.sb.exception.ServiceException;
import com.chatak.switches.sb.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 06-May-2015 1:42:23 PM
 * @version 1.0
 */
public abstract class TransactionService extends AccountTransactionService {

  private static Logger logger = Logger.getLogger(TransactionService.class);

  @Autowired
  protected FeeDetailDao feeDetailDao;

  @Autowired
  protected EMVTransactionDao emvTransactionDao;

  @Autowired
  protected SwitchTransactionDao switchTransactionDao;

  @Autowired
  protected SwitchDao switchDao;

  @Autowired
  protected SplitTransactionDao splitTransactionDao;

  @Autowired
  protected SplitTransactionRepository splitTransactionRepository;

  @Autowired
  protected AccountRepository accountRepository;

  @Autowired
  protected BINRepository binRepository;

  @Autowired
  protected BINDao binDao;

  @Autowired
  protected AccountHistoryDao accountHistoryDao;

  @Autowired
  protected PGParamsDao paramsDao;

  @Autowired
  protected AccountFeeLogDao accountFeeLogDao;

  @Autowired
  protected CurrencyConfigDao currencyConfigDao;
  
  @Autowired
  MerchantUpdateDao merchantUpdateDao;
  
  @Autowired
  CardProgramDao cardProgramDao;
  
  @Autowired
  MerchantCardProgramMapDao merchantCardProgramMapDao;
  
  @Autowired
  ProgramManagerDao programManagerDao;
  
  @Autowired
  IsoServiceDao isoServiceDao;
  
  @Autowired
  public AsyncService asyncService;
  
  @Autowired
  private MerchantRepository merchantRepository;

  protected String txnRefNum = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_TXN_REF_NUM);

  protected String authId = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_AUTH_ID);

  private ObjectMapper mapper = new ObjectMapper();

  /**
   * Method to validate the card transaction request
   * 
   * @param request
   * @return boolean
   * @throws ServiceException
   */
  protected boolean validateRequest(Request request) throws ServiceException {
    logger.info("TransactionService | validateRequest | Entering");
    boolean status = false;
    String expDate;
    MagneticStripeCardUtil magUtil = new MagneticStripeCardUtil();
    try {
      if (!CommonUtil.isNullAndEmpty(request.getTrack2())) {
        magUtil._parseTrack2(request.getTrack2());
        expDate = magUtil.getExpDate();
      } else {

        expDate = request.getExpDate();
      }
      if (!PGUtils.isValidCardExpiryDate(expDate)) {
        throw new ServiceException(ActionCode.ERROR_CODE_54);
      }
    } catch (NumberFormatException e) {
      logger.error(
          "TransactionService | validateRequest | NumberFormatException :" + e.getMessage(), e);
      throw new ServiceException(ActionCode.ERROR_CODE_14);
    } catch (DataAccessException e) {
      logger.error("TransactionService | validateRequest | DataAccessException :" + e.getMessage(),
          e);
      throw new ServiceException(ActionCode.ERROR_CODE_Z12);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      logger.error("TransactionService | validateRequest | Exception :" + e.getMessage(), e);
      throw new ServiceException(ActionCode.ERROR_CODE_Z5);
    }
    logger.info("TransactionService | validateRequest | Exiting");
    return status;
  }

  /**
   * Method used to populate PGTransaction object with request object
   * 
   * @param request
   * @return
   * @throws ServiceException
   * @throws Throwable
   */
  protected PGTransaction populatePGTransaction(Request request, String txnType)
      throws Exception, ServiceException {
    logger.info("Entering :: TransactionService :: populatePGTransaction");
    if(!request.getPosEntryMode().equals(Constants.ACCOUNT_PAY_VALUE) || !request.getEntryMode().equals(EntryModeEnum.ACCOUNT_PAY)) {
		if (txnType.equalsIgnoreCase(TransactionType.SALE.toString())
				&& (request.getTotalTxnAmount() < (request.getTxnAmount() + request.getTxnFee()))) {
			throw new ServiceException(ActionCode.ERROR_CODE_12);
		}
    }
    
    PGTransaction pgTransaction = new PGTransaction();
    pgTransaction.setSysTraceNum(request.getSysTraceNum());
    pgTransaction.setTransactionType(txnType);
    pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_CREDIT);
    pgTransaction.setTxnAmount(request.getTxnAmount());
    pgTransaction.setMerchantId(request.getMerchantCode());
    pgTransaction.setTerminalId(request.getTerminalId());
    pgTransaction.setInvoiceNumber(request.getInvoiceNumber());
    logger.info("request's acq_channel" + request.getAcq_channel());

    pgTransaction.setAcqChannel(setAcqChannel(request));

    if (request.getAcq_mode() != null) {
      if (EntryModeEnum.CASH.name().equalsIgnoreCase(request.getAcq_mode())) {
        pgTransaction.setAcqTxnMode(EntryModeEnum.MANUAL.name());
      } else {
        pgTransaction.setAcqTxnMode(request.getAcq_mode());
      }
    } else {
      pgTransaction.setAcqTxnMode(Constants.BALANCE_ENQUIRY);
    }
    
    // PERF >> Replaced by fetching only auto-settlement status and ID based on merchant code
    PGMerchant pgMerchant = merchantUpdateDao.getMerchantAutoSettlementByCode(request.getMerchantCode());
    String autoSettlement = pgMerchant.getMerchantConfig().getAutoSettlement() != null ? pgMerchant.getMerchantConfig().getAutoSettlement().toString() : "1";
    autoSettlement = (autoSettlement!=null && autoSettlement.equals("1")) ? Constants.AUTO_SETTLEMENT_STATUS_NO : Constants.BATCH_STATUS_NA;
    
    if (ProcessorType.LITLE.value().equals(pgTransaction.getProcessor())) {
      pgTransaction.setEftStatus(PGConstants.LITLE_EXECUTED);
    }
    pgTransaction.setBatchId(Constants.BATCH_STATUS_NA);
    pgTransaction.setBatchDate(new Timestamp(System.currentTimeMillis()));
    pgTransaction.setAutoSettlementStatus(autoSettlement);
    pgTransaction.setMti(request.getMti());
    pgTransaction.setProcCode(request.getProcessingCode());
    pgTransaction.setChipTransaction(
        (request.getChipTransaction() != null && request.getChipTransaction()) ? 1 : 0);
    pgTransaction.setChipFallbackTransaction(
        (request.getChipFallback() != null && request.getChipFallback()) ? 1 : 0);
    
    // In case the card number is from a HCE NFC transaction, it might be appended with an 'F'
    // to make it a whole 20 digit card number, 19 card digits + 'F'
    // In such cases, truncate the 'F'
    String cardNumber = null;
    if(request.getPosEntryMode().equals(Constants.ACCOUNT_PAY_VALUE)) {
    	pgTransaction.setPanMasked(request.getAccountNumber());
        pgTransaction.setPan(EncryptionUtil.encrypt(request.getAccountNumber()));
    } else {
    cardNumber = request.getCardNum().replace("F", "");
    pgTransaction.setPanMasked(StringUtils.getMaskedString(cardNumber, Integer.parseInt("5"), Integer.parseInt("4")));
    pgTransaction.setPan(EncryptionUtil.encrypt(cardNumber));
    }
    // PERF >> Will use the primary auto increment key as the transaction id
    //pgTransaction.setTransactionId(transactionDao.generateTransactionRefNumber());
    
    pgTransaction.setAuthId(authId);
    pgTransaction.setCreatedDate(timestamp);
    pgTransaction.setUpdatedDate(timestamp);
    pgTransaction.setStatus(PGConstants.STATUS_INPROCESS);
    pgTransaction.setCardHolderName(request.getCardHolderName());
    pgTransaction.setFeeAmount(request.getTxnFee());
    pgTransaction.setTxnTotalAmount(request.getTotalTxnAmount());
    pgTransaction.setRefTransactionId(request.getTxnRefNumber());
    pgTransaction.setTxnDescription(request.getDescription());
    pgTransaction.setPosEntryMode(request.getPosEntryMode());
    pgTransaction.setTxnMode(request.getMode());
    pgTransaction.setCardHolderEmail(request.getCardHolderEmail());
    pgTransaction.setReason(request.getDescription());
    pgTransaction.setTxnCurrencyCode(request.getCurrencyCode());
    pgTransaction.setTimeZoneOffset(request.getTimeZoneOffset());
    pgTransaction.setTimeZoneRegion(request.getTimeZoneRegion());
    pgTransaction.setDeviceLocalTxnTime(DateUtil.convertTimeZone(request.getTimeZoneOffset(), timestamp.toString()));
    if(!request.getPosEntryMode().equals(Constants.ACCOUNT_PAY_VALUE) || !request.getEntryMode().equals(EntryModeEnum.ACCOUNT_PAY)) {
    	pgTransaction
        .setExpDate(setExpDate(request));
    getCardProgramDetailsByCardNumber(CommonUtil.getIIN(cardNumber), CommonUtil.getPartnerIINExt(cardNumber), 
    		CommonUtil.getIINExt(cardNumber), pgTransaction, pgMerchant.getId(),request); 
    }
    logger.info("Exiting :: TransactionService :: populatePGTransaction");
    return pgTransaction;
  }

	private Long setExpDate(Request request) {
		return request.getExpDate() != null ? Long.valueOf(request.getExpDate()) : null;
	}

	private String setAcqChannel(Request request) {
		return request.getAcq_channel() != null ? request.getAcq_channel() : "web";
	}

  /**
   * Method used to populate PGSwitchTransaction object with request object
   * 
   * @param request
   * @return
   * @throws Exception
   */
  protected PGSwitchTransaction populateSwitchTransactionRequest(Request request) throws Exception {
    logger.info("Entering :: TransactionService :: populateSwitchTransactionRequest");

    PGSwitchTransaction pgSwitchTransaction = new PGSwitchTransaction();
    pgSwitchTransaction.setTxnAmount(request.getTxnAmount());
    pgSwitchTransaction.setPosEntryMode(request.getPosEntryMode());
		if (request.getEntryMode().equals(EntryModeEnum.ACCOUNT_PAY)) {
			pgSwitchTransaction.setPan(EncryptionUtil.encrypt(request.getAccountNumber()));
			pgSwitchTransaction.setPanMasked(StringUtils.getMaskedString(request.getAccountNumber(), 5, 4));
		} else {
			pgSwitchTransaction.setPan(EncryptionUtil.encrypt(request.getCardNum()));
			pgSwitchTransaction.setPanMasked(StringUtils.getMaskedString(request.getCardNum(), 5, 4));
		}
    // PERF >> Commenting since PgTransactionId is auto increment
    //pgSwitchTransaction.setPgTransactionId(transactionDao.generateTransactionRefNumber())
    pgSwitchTransaction.setCreatedDate(timestamp);

    pgSwitchTransaction.setStatus(PGConstants.STATUS_INPROCESS);
    logger.info("Exiting :: TransactionService :: populateSwitchTransactionRequest");

    return pgSwitchTransaction;
  }

  /**
   * Method used to log EMV Transaction data
   * 
   * @param emvData
   * @param txnRefNumber
   */
  protected void logEmvTransaction(EMVData emvData, String txnRefNumber) {

    PGEMVTransaction pgemvTransaction = new PGEMVTransaction();
    pgemvTransaction.setAed(emvData.getAed());
    pgemvTransaction.setAid(emvData.getAid());
    pgemvTransaction.setAip(emvData.getAip());
    pgemvTransaction.setAtc(emvData.getAtc());
    pgemvTransaction.setCvrm(emvData.getCvmr());
    pgemvTransaction.setFci(emvData.getFci());
    pgemvTransaction.setFcip(emvData.getFcip());
    pgemvTransaction.setIad(emvData.getIad());
    pgemvTransaction.setIfd(emvData.getIfd());
    pgemvTransaction.setIsr(emvData.getIsr());
    pgemvTransaction.setIst(emvData.getIst());
    pgemvTransaction.setIst1(emvData.getIst_1());
    pgemvTransaction.setLanRef(emvData.getLan());
    pgemvTransaction.setPgTransactionId(txnRefNumber);
    pgemvTransaction.setPsn(emvData.getPsn());
    pgemvTransaction.setTcc(emvData.getTcc());
    pgemvTransaction.setTsn(emvData.getTsn());
    pgemvTransaction.setTvr(emvData.getTvr());
    pgemvTransaction.setTxnStatusInfo(emvData.getTxnStatusInfo());
    pgemvTransaction.setUnPredNumber(emvData.getUnPredictableNum());

    emvTransactionDao.createTransaction(pgemvTransaction);
  }

  /**
   * @param mti
   * @param txnAmount
   * @param cardNumber
   * @param expDate
   * @param txnRef
   * @return
   * @throws Exception
   */
  protected ISOMsg getISOMsg(String mti, String procCode, Long txnAmount, String cardNumber,
      String expDate, String txnRef) throws Exception {
    ISOMsg isoMsg = new ISOMsg();
    isoMsg.setMTI(mti);

    isoMsg.set(ISOConstants.PAN, cardNumber);
    isoMsg.set(ISOConstants.PROCESSING_CODE, procCode);
    isoMsg.set(ISOConstants.TXN_AMOUNT, txnAmount.toString());
    isoMsg.set(ISOConstants.TRANSMISSION_DATE_TIME, (new SimpleDateFormat("MMddhhmmss").format(new Date())));
    isoMsg.set(ISOConstants.SYSTEM_TRACE_AUDIT_NUMBER, null != txnRef ? txnRef.substring(0, Integer.parseInt("6")) : txnRefNum.substring(0, Integer.parseInt("6")));
    isoMsg.set(ISOConstants.LOCAL_TRANSACTION_TIME, DateUtils.getLocalTransactionTime());// TODO: local time
    // of
    // transaction
    // origination
    // hhmmss
    isoMsg.set(ISOConstants.LOCAL_TRANSACTION_DATE, DateUtils.getLocalTransactionDate());// TODO: local date
    // of
    // transaction
    // origination MMDD
    isoMsg.set(ISOConstants.DATE_EXPIRATION, expDate.substring(Integer.parseInt("2"), Integer.parseInt("4")) + expDate.substring(0, Integer.parseInt("2")));
    isoMsg.set(ISOConstants.DATE_SETTLEMENT, DateUtils.getLocalTransactionDate());
    isoMsg.set(ISOConstants.MERCHANT_TYPE, "1111");
    isoMsg.set(ISOConstants.POINT_OF_SERVICE_ENTRY_MODE, "012");

    isoMsg.set(ISOConstants.FUNCTION_CODE, "000");
    isoMsg.set(ISOConstants.AMOUNT_SETTLEMENT_FEE, "1");// TODO: Acquirer fee
    isoMsg.set(ISOConstants.ACQUIRING_INSTITUTION_IDENTIFICATION_CODE, "840935005");
    isoMsg.set(ISOConstants.RETRIEVAL_REFERENCE_NUMBER, txnRef);// Retrieval Reference Number
    isoMsg.set(ISOConstants.CARD_ACCEPTOR_TERMINAL_IDENTIFICATION, "4712V302");// TODO: need to populate original TID
    isoMsg.set(ISOConstants.CARD_ACCEPTOR_NAME_OR_LOCATION, "Chatak Acquirer");// TODO: Card Acceptor Name & Location
    isoMsg.set(ISOConstants.PRIVATE_ADDITIONAL_DATA, "Chatak merchant");// TODO: Merchant/Bank Name

    isoMsg.set(ISOConstants.TXN_CURRENCY_CODE, "840");
    isoMsg.set(ISOConstants.RESERVED_NATIONAL_57, "220");// TODO: Auth life cycle

    isoMsg.set(ISOConstants.RESERVED_NATIONAL_58, "0000000002");
    isoMsg.set(ISOConstants.RESERVED_NATIONAL_59, "11111");// TODO: National Pointof-Service Geographic
    // Data
    // an..17

    return isoMsg;
  }

  /**
   * @param mti
   * @param txnAmount
   * @param cardNumber
   * @param expDate
   * @param txnRef
   * @return
   * @throws Exception
   */
  protected ISOMsg getISOMsg(Request request, String mti, String procCode, String txnRef)
      throws ISOException {
    logger.info("Entering :: TransactionService :: getISOMsg");
    ISOMsg isoMsg = new ISOMsg();
    try {
    isoMsg.setMTI(mti);
    logger.info("Track 2 data : " + request.getTrack2());
    if (!StringUtils.isValidString(request.getTrack2())) {
    	logger.info("Setting PAN in ISO field");
      isoMsg.set(ISOConstants.PAN, request.getCardNum());
    }
    if(!request.getPosEntryMode().equals(Constants.ACCOUNT_PAY_VALUE) || !request.getEntryMode().equals(EntryModeEnum.ACCOUNT_PAY)) {
    logger.info("PAN Number in Sale Txn Request : " + StringUtils.lastFourDigits(request.getCardNum()));
    logger.info("PAN Number in ISO Packet : " + StringUtils.lastFourDigits((String)isoMsg.getValue(ISOConstants.PAN)));
    isoMsg.set(ISOConstants.DATE_EXPIRATION, request.getExpDate());
    isoMsg.set(ISOConstants.SYSTEM_TRACE_AUDIT_NUMBER, null != txnRef ? txnRef.substring(0, Integer.parseInt("6")) : txnRefNum.substring(0, Integer.parseInt("6")));
    } else {
    	isoMsg.set(ISOConstants.SYSTEM_TRACE_AUDIT_NUMBER, null != txnRef ? txnRef.substring(0, Integer.parseInt("2")) : txnRefNum.substring(0, Integer.parseInt("6")));
    }
    isoMsg.set(ISOConstants.PROCESSING_CODE, procCode);
    isoMsg.set(ISOConstants.TXN_AMOUNT,
        request.getTotalTxnAmount() != null
            ? ISOUtil.padleft(request.getTotalTxnAmount().toString().replace(".", ""), Integer.parseInt("12"), '0')
            : ISOUtil.padleft("0", Integer.parseInt("12"), '0'));

    isoMsg.set(ISOConstants.TRANSMISSION_DATE_TIME, (new SimpleDateFormat("MMddhhmmss").format(new Date())));
    isoMsg.set(ISOConstants.LOCAL_TRANSACTION_TIME, DateUtils.getLocalTransactionTime());// TODO: local time
    // of
    // transaction
    // origination
    // hhmmss
    isoMsg.set(ISOConstants.LOCAL_TRANSACTION_DATE, DateUtils.getLocalTransactionDate());// TODO: local date
    // of
    // transaction
    // origination MMDD
    isoMsg.set(ISOConstants.DATE_SETTLEMENT, DateUtils.getLocalTransactionDate());
    isoMsg.set(ISOConstants.MERCHANT_TYPE, "1111");
    isoMsg.set(ISOConstants.POINT_OF_SERVICE_ENTRY_MODE,
        (null != request.getPosEntryMode()
            ? validatePos(request)
            : "000"));// setting
    // unknown
    // pos
    // entry
    // mode
    // if
    // not
    // available

    isoMsg.set(ISOConstants.FUNCTION_CODE, "000");
    isoMsg.set(ISOConstants.POINT_OF_SERVICE_CONDITION_CODE, "00");
    isoMsg.set(ISOConstants.AMOUNT_SETTLEMENT_FEE, "1");// TODO: Acquirer fee
    isoMsg.set(ISOConstants.ACQUIRING_INSTITUTION_IDENTIFICATION_CODE, "1111");
    if (StringUtils.isValidString(request.getTrack2())) {
      isoMsg.set(ISOConstants.ALTERNATE_TRACK_2_DATA, request.getTrack2());
    }
    isoMsg.set(ISOConstants.RETRIEVAL_REFERENCE_NUMBER, null != txnRef ? txnRef : txnRefNum);// Retrieval
    // Reference
    // Number
    isoMsg.set(ISOConstants.CARD_ACCEPTOR_TERMINAL_IDENTIFICATION, ISOUtil.padleft(request.getTerminalId(), Integer.parseInt("8"), '0'));// TODO:
    // Need
    // to
    // add
    // same
    // TID
    // given
    // by
    // pulse
    isoMsg.set(ISOConstants.CARD_ACCEPTOR_IDENTIFICATION_CODE, ISOUtil.padleft(request.getMerchantCode(), Integer.parseInt("15"), '0'));
    isoMsg.set(ISOConstants.CARD_ACCEPTOR_NAME_OR_LOCATION, ISOUtil.padleft(request.getBusinessName(), Integer.parseInt("23"), ' ')
        + ISOUtil.padleft(request.getCity(), Integer.parseInt("13"), ' '));
    isoMsg.set(ISOConstants.PRIVATE_ADDITIONAL_DATA, request.getAddress());// TODO: Merchant/Bank Name

    isoMsg.set(ISOConstants.TXN_CURRENCY_CODE, request.getCurrencyCode());

    validatePosEntryMode(request, isoMsg);

    isoMsg.set(ISOConstants.RESERVED_NATIONAL_57, "220");// TODO: Auth life cycle

    isoMsg.set(ISOConstants.RESERVED_NATIONAL_58, "11111");

    isoMsg.set(ISOConstants.RESERVED_NATIONAL_58,
        request.getNationalPOSEntryMode().PAN_AUTO_ENTRY_CONTACTLESS_M_CHIP_DE58.value());// national
    // pos
    // entry
    // mode
    isoMsg.set(ISOConstants.RESERVED_NATIONAL_59, "11111");// TODO: National Pointof-Service Geographic
    // Data
    // an..17
    isoMsg.set(ISOConstants.RESERVED_NATIONAL_60, "11111");// dummny data utcd
    isoMsg.set(ISOConstants.ORIGINAL_DATA_ELEMENTS, "0000000");// TODO: Switch expected fields for reversal
    isoMsg.set(ISOConstants.RESERVED_PRIVATE_62, "000008");
    /*
     * isoMsg.set(63, "80L30MMT");// TODO:Need to structure (Added //
     * 29-04-2015)Pulse data
     */
    isoMsg.set(ISOConstants.RESERVED_PRIVATE_63, request.getPulseData());// setting only pseudo terminal
    isoMsg.set(ISOConstants.ORIGINAL_DATA_ELEMENTS, "0000000");// ans ...999 TODO: Reserved National
    isoMsg.set(ISOConstants.RESERVED_PRIVATE_61, request.getQrCode()); //setting QR code value if available.
    isoMsg.set(ISOConstants.SETTLEMENT_CODE, request.getCvv() != null ? request.getCvv() : ""); // Set cvv2 if entered manually
    isoMsg.set(ISOConstants.RESERVED_FOR_PRIVATE_USE, request.getUid() != null ? request.getUid() : ""); //setting UID value if available.
    isoMsg.set(ISOConstants.TIMEZONE_OFFSET, request.getTimeZoneOffset());//setting  TimeZone Offset value.
    isoMsg.set(ISOConstants.TIMEZONE_REGION, request.getTimeZoneRegion());//setting  TimeZone Region value.
    isoMsg.set(ISOConstants.ACCOUNT_NUMBER, request.getAccountNumber());//setting Account Number value
    } catch(NullPointerException | ISOException e) {
      logger.error("Error in IsoMessage", e);
      throw new ISOException("Invalid IsoMessage");
    }
    logger.info("Transaction Currency : " + request.getCurrencyCode());
    logger.info("Exiting :: TransactionService :: getISOMsg");
    return isoMsg;
  }

  private String validatePos(Request request) {
    return request.getPosEntryMode().contains("07") ? "071" : request.getPosEntryMode();
  }

  private void validatePosEntryMode(Request request, ISOMsg isoMsg) throws ISOException {
    if (StringUtils.isValidString(request.getEmv())) {
      isoMsg.set(ISOConstants.RESERVED_ISO, request.getEmv());
      isoMsg.set(ISOConstants.POINT_OF_SERVICE_ENTRY_MODE,
          (null != request.getPosEntryMode()
              ? validatePos(request)
              : "000"));
    } else {
      isoMsg.set(ISOConstants.POINT_OF_SERVICE_ENTRY_MODE,
          (null != request.getPosEntryMode()
              ? validatePosEntryMode(request)
              : "000"));
    }
  }

  private String validatePosEntryMode(Request request) {
    return request.getPosEntryMode().contains("07") ? "910" : request.getPosEntryMode();
  }

  public Integer validateResponseCode(String responseCode) {
    /*
     * 000: Approved 010: Partially Approved 100: Processing Network Unavailable
     * 101: Issuer Unavailable 110: Insufficient Funds
     */
    if ("000".equals(responseCode) || "010".equals(responseCode)) {
      return PGConstants.STATUS_SUCCESS;
    } else if ("100".equals(responseCode) || "101".equals(responseCode)) {
      return PGConstants.STATUS_FAILED;
    } else if ("110".equals(responseCode)) {
      return PGConstants.STATUS_DECLINED;
    } else
      return PGConstants.STATUS_FAILED;

  }

  /**
   * Method to update the merchant account balance
   * 
   * @param merchantId
   * @param paymentMethod
   * @param txnAmount
   * @throws ServiceException
   */

  public PGAccount updateMerchantAccount(String merchantId, String paymentMethod, Long txnAmount,
      Long feeAmount, String transactionId) throws ServiceException {
    PGAccount pgAccount = accountDao.getPgAccount(merchantId);
    if (paymentMethod.equals(PGConstants.PAYMENT_METHOD_CREDIT)) {
      pgAccount.setCurrentBalance(pgAccount.getCurrentBalance() - txnAmount);
    } else if (paymentMethod.equals(PGConstants.PAYMENT_METHOD_DEBIT)) {
      pgAccount.setCurrentBalance(pgAccount.getCurrentBalance() + txnAmount);
    } else if (paymentMethod.equals(PGConstants.AUTH_PAYMENT_METHOD)) {
      // do nothing
    } else if (paymentMethod.equals(PGConstants.CAPTURE_PAYMENT_METHOD)) {
      pgAccount.setCurrentBalance(pgAccount.getCurrentBalance() + txnAmount);
    }
    accountRepository.save(pgAccount);
    logPgAccountHistory(merchantId, paymentMethod, transactionId, pgAccount);
    return pgAccount;
  }

  /**
   * Method to update the Chatak fee balance
   * 
   * @param merchantId
   * @param paymentMethod
   * @param txnAmount
   * @throws ServiceException
   */

  public void updateChatakAccount(Long feeAmount, PGTransaction transaction)
      throws ServiceException {
    PGAccount pgAccount = accountDao.getPgAccount("1");
    pgAccount.setFeeBalance(pgAccount.getFeeBalance() + feeAmount);
    accountRepository.save(pgAccount);
    logPgAccountHistory("1", transaction.getPaymentMethod(), transaction.getTransactionId(), pgAccount);

  }

  public String updateMerchantSettledAccount(PGTransaction originalPgTransaction)
      throws ServiceException {
    String negativeBalanceFlag = "";
    PGAccount pgAccount = accountDao.getPgAccount(originalPgTransaction.getMerchantId());

    List<Object> objectResult =
        getProcessingFee(PGUtils.getCCType(),
            1L, originalPgTransaction.getMerchantId(), originalPgTransaction.getTxnTotalAmount());
    Long chatakFeeAmountTotal = (Long) objectResult.get(1);

    Long totalFeeAmount =
        originalPgTransaction.getTxnTotalAmount() - originalPgTransaction.getTxnAmount();
    Long merchantFeeAmount = 0l;
    if (totalFeeAmount > chatakFeeAmountTotal) {
      merchantFeeAmount = totalFeeAmount - chatakFeeAmountTotal;
    }
    originalPgTransaction.setMerchantFeeAmount(merchantFeeAmount);
    if (0L > pgAccount.getAvailableBalance() || 0L > pgAccount.getCurrentBalance()
        || 0L > pgAccount.getFeeBalance()) {
      negativeBalanceFlag = "[NB]";// Flag for negative balance
    }

    accountRepository.save(pgAccount);
    logPgAccountHistory(pgAccount.getEntityId(), originalPgTransaction.getPaymentMethod(),
        originalPgTransaction.getTransactionId(), pgAccount);

    return negativeBalanceFlag;

  }

  public void updateChatakSettledAccount(Long feeAmount, PGTransaction transaction)
      throws ServiceException {
    PGAccount pgAccount = accountDao.getPgAccount("1");
    if (pgAccount.getFeeBalance() > 0 && pgAccount.getFeeBalance() > feeAmount) {
      pgAccount.setFeeBalance(pgAccount.getFeeBalance() - feeAmount);
    } else {
      pgAccount.setFeeBalance(0L);// updating fee balance based on pay-out
      // state
    }

    accountRepository.save(pgAccount);
    logPgAccountHistory(pgAccount.getEntityId(), transaction.getPaymentMethod(),
        transaction.getTransactionId(), pgAccount);

  }

  /**
   * <<Method to log split transaction data>>
   * 
   * @param transactionRequest
   * @throws Exception
   */
  public void logSplitTransaction(SplitTxnRequest transactionRequest) throws Exception {
    PGSplitTransaction pgSplitTransaction = new PGSplitTransaction();
    pgSplitTransaction.setSplitAmount(transactionRequest.getSplitAmount());
    pgSplitTransaction.setSplitTransactionId(txnRefNum);
    pgSplitTransaction.setCreatedDate(timestamp);
    pgSplitTransaction.setStatus(transactionRequest.getStatus());
    pgSplitTransaction.setSplitMode(transactionRequest.getSplitMode().toString());
    pgSplitTransaction.setPgTransactionId(transactionRequest.getPgTransactionId());
    pgSplitTransaction.setPanMasked(transactionRequest.getPanMasked());
    splitTransactionDao.createOrUpdateTransaction(pgSplitTransaction);
  }

  /**
   * <<Method to add history for account>>
   * 
   * @param merchantId
   * @throws ServiceException
   */
  public void logPgAccountHistory(String merchantId, String paymentMethod, String transactionId, PGAccount updatedAccount)
      throws ServiceException {
	  if(null == updatedAccount) {
		  updatedAccount = accountDao.getPgAccount(merchantId);
	  }
    if (null != updatedAccount) {
      PGAccountHistory pgAccountHistory = new PGAccountHistory();
      pgAccountHistory.setEntityType(updatedAccount.getEntityType());
      pgAccountHistory.setEntityId(updatedAccount.getEntityId());
      pgAccountHistory.setAccountDesc(updatedAccount.getAccountDesc());
      pgAccountHistory.setCurrentBalance(updatedAccount.getCurrentBalance());
      pgAccountHistory.setCategory(updatedAccount.getCategory());
      pgAccountHistory.setAvailableBalance(updatedAccount.getAvailableBalance());
      pgAccountHistory.setAutoPaymentLimit(updatedAccount.getAutoPaymentLimit());
      pgAccountHistory.setCurrency(updatedAccount.getCurrency());
      pgAccountHistory.setAutoPaymentMethod(updatedAccount.getAutoPaymentMethod());
      pgAccountHistory.setStatus(updatedAccount.getStatus());
      pgAccountHistory.setAutoTransferDay(updatedAccount.getAutoTransferDay());
      pgAccountHistory.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
      pgAccountHistory.setFeeBalance(updatedAccount.getFeeBalance());
      pgAccountHistory.setAccountNum(updatedAccount.getAccountNum());
      pgAccountHistory.setPaymentMethod(paymentMethod);
      pgAccountHistory.setTransactionId(transactionId);
      accountHistoryDao.createOrSave(pgAccountHistory);
    }
  }

  /**
   * <<Method to update the account balance based on original sale transaction
   * settlement status while processing refund>>
   * 
   * @param originalPgTransaction
   * @return
   * @throws ServiceException
   * @throws Exception
   */
  public String updateMerchantSettledAccountOnRefund(PGTransaction originalPgTransaction)
      throws ServiceException {
    String negativeBalanceFlag = "";
    PGAccount pgAccount = accountDao.getPgAccount(originalPgTransaction.getMerchantId());
    pgAccount
        .setCurrentBalance(pgAccount.getCurrentBalance() + originalPgTransaction.getTxnAmount());

    pgAccount.setAvailableBalance(
        pgAccount.getAvailableBalance() + originalPgTransaction.getTxnAmount());
    Long chatakFeeAmountTotal;
    List<Object> objectResult =
        getProcessingFee(PGUtils.getCCType(),
            1L, originalPgTransaction.getMerchantId(), originalPgTransaction.getTxnTotalAmount());
    chatakFeeAmountTotal = (Long) objectResult.get(1);
    Long merchantFeeAmount = 0l;

    Long totalFeeAmount =
        originalPgTransaction.getTxnTotalAmount() - originalPgTransaction.getTxnAmount();

    if (totalFeeAmount > chatakFeeAmountTotal) {
      merchantFeeAmount = totalFeeAmount - chatakFeeAmountTotal;
    } else {
      chatakFeeAmountTotal = totalFeeAmount;
    }
    pgAccount.setFeeBalance(pgAccount.getFeeBalance() + merchantFeeAmount);

    updateChatakSettledAccountOnRefund(chatakFeeAmountTotal, originalPgTransaction);// updating
    // account
    // based
    // on
    // void

    if (0L > pgAccount.getAvailableBalance() || 0L > pgAccount.getCurrentBalance()
        || 0L > pgAccount.getFeeBalance()) {
      negativeBalanceFlag = "[NB]";// Flag for negative balance
    }

    accountRepository.save(pgAccount);
    logPgAccountHistory(pgAccount.getEntityId(), originalPgTransaction.getPaymentMethod(),
        originalPgTransaction.getTransactionId(), pgAccount);

    return negativeBalanceFlag;

  }

  public void updateChatakSettledAccountOnRefund(Long feeAmount, PGTransaction transaction)
      throws ServiceException {

    logger.info(
        "pgAccountTransactions:: updateChatakSettledAccountOnRefund method :: fetching currencyConfig for fee credit with numeric code: "
            + transaction.getTxnCurrencyCode());
    PGCurrencyConfig currencyConfig =
        currencyConfigDao.getcurrencyCodeAlpha(transaction.getTxnCurrencyCode());
    logger.info(
        "pgAccountTransactions:: updateChatakSettledAccountOnRefund method :: currency code alpha for the above: "
            + currencyConfig.getCurrencyCodeAlpha());
    PGAccount pgAccount =
        accountRepository.findByEntityTypeAndCurrencyAndStatus(PGConstants.DEFAULT_ENTITY_TYPE,
            currencyConfig.getCurrencyCodeAlpha(), PGConstants.S_STATUS_ACTIVE);

    pgAccount.setFeeBalance(pgAccount.getFeeBalance() + feeAmount);

    accountRepository.save(pgAccount);
    logPgAccountHistory(pgAccount.getEntityId(), transaction.getPaymentMethod(),
        transaction.getTransactionId(), pgAccount);

  }

  /**
   * <<Getting network management code & msg security code and preparing network
   * iso msg>>
   * 
   * @param mti
   * @param networkManagementCode
   * @param msgSecurityCode
   * @param txnRef
   * @return
   * @throws Exception
   */
  public ISOMsg getNetworkIsoMsg(String mti, String networkManagementCode, String msgSecurityCode,
      String txnRef) throws Exception {

    ISOMsg isoMsg = new ISOMsg();
    isoMsg.setMTI(mti);
    isoMsg.set(ISOConstants.TRANSMISSION_DATE_TIME, (new SimpleDateFormat("MMddhhmmss").format(new Date())));
    isoMsg.set(ISOConstants.SYSTEM_TRACE_AUDIT_NUMBER, txnRef.substring(0, Integer.parseInt("6")));
    isoMsg.set(ISOConstants.NETWORK_MANAGEMENT_INFORMATION_CODE, networkManagementCode);
    isoMsg.set(ISOConstants.MSG_SECURITY_CODE, msgSecurityCode);
    return isoMsg;

  }

  /**
   * <<Method to populate reversal request>>
   * 
   * @param request
   * @return
   */
  public ReversalRequest populateReversalRequest(Request request, Response response) {
    ReversalRequest reversalRequest = new ReversalRequest();
    reversalRequest.setAcq_channel(request.getAcq_channel());
    reversalRequest.setAcq_mode(request.getAcq_mode());
    reversalRequest.setInvoiceNumber(request.getInvoiceNumber());
    reversalRequest.setIssuerTxnRefNum(response.getUpStreamTxnRefNum());
    reversalRequest.setSysTraceNum(request.getSysTraceNum());
    reversalRequest.setTotalTxnAmount(request.getTotalTxnAmount());
    reversalRequest.setCardHolderName(request.getCardHolderName());
    reversalRequest.setCardNum(request.getCardNum());
    reversalRequest.setExpDate(request.getExpDate());
    reversalRequest.setCvv(request.getCvv());
    reversalRequest.setTerminalId(request.getTerminalId());
    reversalRequest.setMerchantCode(request.getMerchantCode());
    reversalRequest.setTxnAmount(request.getTxnAmount());
    reversalRequest.setReversalReason(request.getReversalReason());
    reversalRequest.setNationalPOSEntryMode(null != request.getEntryMode()
        ? NationalPOSEntryModeEnum.valueOf(request.getEntryMode() + "_58")
        : NationalPOSEntryModeEnum.UNSPECIFIED_DE58);
    reversalRequest.setPulseData(request.getPulseData());
    return reversalRequest;
  }

  protected PGTransaction populateReversalPGTransaction(Request request, String txnType)
      throws Exception, ServiceException {

    PGTransaction pgTransaction = new PGTransaction();
    pgTransaction.setSysTraceNum(request.getSysTraceNum());
    pgTransaction.setTransactionType(txnType);
    pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_CREDIT);
    pgTransaction.setTxnAmount(request.getTxnAmount());
    pgTransaction.setMerchantId(request.getMerchantCode());
    pgTransaction.setTerminalId(request.getTerminalId());
    pgTransaction.setInvoiceNumber(request.getInvoiceNumber());
    pgTransaction
        .setAcqChannel(setAcqChannel(request));
    pgTransaction.setAcqTxnMode(request.getAcq_mode() != null ? request.getAcq_mode() : "rest");
    pgTransaction.setMti(request.getMti());
    pgTransaction.setProcCode(request.getProcessingCode());
    pgTransaction.setChipTransaction(
        (request.getChipTransaction() != null && request.getChipTransaction()) ? 1 : 0);
    pgTransaction.setChipFallbackTransaction(
        (request.getChipFallback() != null && request.getChipFallback()) ? 1 : 0);
    pgTransaction.setPanMasked(StringUtils.getMaskedString(request.getCardNum(), Integer.parseInt("5"), Integer.parseInt("4")));
    pgTransaction.setPan(EncryptionUtil.encrypt(request.getCardNum()));
    pgTransaction
        .setExpDate(setExpDate(request));
    pgTransaction
        .setTransactionId(RandomGenerator.generateRandNumeric(PGConstants.LENGTH_TXN_REF_NUM));
    pgTransaction.setAuthId(authId);
    pgTransaction.setCreatedDate(timestamp);
    pgTransaction.setUpdatedDate(timestamp);
    pgTransaction.setStatus(PGConstants.STATUS_INPROCESS);
    pgTransaction.setCardHolderName(request.getCardHolderName());

    pgTransaction.setFeeAmount(request.getTxnFee());
    pgTransaction.setTxnTotalAmount(request.getTotalTxnAmount());
    pgTransaction.setRefTransactionId(txnRefNum);
    pgTransaction.setTxnDescription(request.getDescription());
    pgTransaction.setPosEntryMode(request.getPosEntryMode());
    return pgTransaction;
  }

  protected ISOMsg getBalanceEnquiryISOMsg(Request request) throws ISOException {
	  ISOMsg isoMsg = new ISOMsg();
    try {
      isoMsg.setMTI("0200");
      isoMsg.set(ISOConstants.PAN, request.getCardNum());
      isoMsg.set(ISOConstants.PROCESSING_CODE, "310000");
      isoMsg.set(ISOConstants.TXN_AMOUNT,
          request.getTotalTxnAmount() != null
              ? ISOUtil.padleft(request.getTotalTxnAmount().toString().replace(".", ""), Integer.parseInt("12"), '0')
              : ISOUtil.padleft("0", Integer.parseInt("12"), '0'));
      isoMsg.set(ISOConstants.TRANSMISSION_DATE_TIME, (new SimpleDateFormat("MMddhhmmss").format(new Date())));
      isoMsg.set(ISOConstants.SYSTEM_TRACE_AUDIT_NUMBER, txnRefNum.substring(0, Integer.parseInt("6")));
      isoMsg.set(ISOConstants.LOCAL_TRANSACTION_TIME, DateUtils.getLocalTransactionTime());// local time
      isoMsg.set(ISOConstants.LOCAL_TRANSACTION_DATE, DateUtils.getLocalTransactionDate());// local date
      isoMsg.set(ISOConstants.DATE_EXPIRATION, request.getExpDate());
      isoMsg.set(ISOConstants.DATE_SETTLEMENT, DateUtils.getLocalTransactionDate());
      isoMsg.set(ISOConstants.MERCHANT_TYPE, "1111");
      isoMsg.set(ISOConstants.POINT_OF_SERVICE_ENTRY_MODE,
          (null != request.getPosEntryMode()
              ? validatePos(request)
              : "01"));
      isoMsg.set(ISOConstants.ACQUIRING_INSTITUTION_IDENTIFICATION_CODE, "1111");
      isoMsg.set(ISOConstants.RETRIEVAL_REFERENCE_NUMBER, txnRefNum);
      isoMsg.set(ISOConstants.CARD_ACCEPTOR_TERMINAL_IDENTIFICATION, ISOUtil.padleft(request.getTerminalId(), Integer.parseInt("8"), '0'));
      isoMsg.set(ISOConstants.CARD_ACCEPTOR_IDENTIFICATION_CODE, ISOUtil.padleft(request.getMerchantCode(), Integer.parseInt("15"), '0'));
      isoMsg.set(ISOConstants.CARD_ACCEPTOR_NAME_OR_LOCATION, ISOUtil.padleft(request.getBusinessName(), Integer.parseInt("23"), ' ')
          + ISOUtil.padleft(request.getCity(), Integer.parseInt("13"), ' '));
      isoMsg.set(ISOConstants.PRIVATE_ADDITIONAL_DATA, request.getAddress());//  Merchant/Bank Name
      isoMsg.set(ISOConstants.TXN_CURRENCY_CODE, request.getCurrencyCode());
      isoMsg.set(ISOConstants.RESERVED_NATIONAL_57, "220");//  Auth life cycle
      isoMsg.set(ISOConstants.RESERVED_NATIONAL_58, "0000000002");
      isoMsg.set(ISOConstants.RESERVED_PRIVATE_63, "80L30MMT");
      isoMsg.set(ISOConstants.SETTLEMENT_CODE, request.getCvv() != null ? request.getCvv() : "");
      isoMsg.set(ISOConstants.RESERVED_FOR_PRIVATE_USE, request.getUid() != null ? request.getUid() : ""); //setting UID value if available.
      isoMsg.set(ISOConstants.TIMEZONE_OFFSET, request.getTimeZoneOffset());//setting  TimeZone Offset value.
      isoMsg.set(ISOConstants.TIMEZONE_REGION, request.getTimeZoneRegion());//setting  TimeZone Region value.
      isoMsg.set(ISOConstants.ACCOUNT_NUMBER, request.getAccountNumber());//setting Account Number value
    } catch (NullPointerException | ISOException e) {
      logger.error("Error in IsoMessage", e);
      throw new ISOException("Invalid IsoMessage");
    }

    return isoMsg;
  }

  protected ISOMsg getISOMsg(ISOMsg isoMsg) {
      isoMsg.set(ISOConstants.TRANSMISSION_DATE_TIME, (new SimpleDateFormat("MMddhhmmss").format(new Date())));
      isoMsg.set(ISOConstants.LOCAL_TRANSACTION_TIME, DateUtils.getLocalTransactionTime());// TODO: local
      // time
      isoMsg.set(ISOConstants.LOCAL_TRANSACTION_DATE, DateUtils.getLocalTransactionDate());// TODO: local
      // date
      isoMsg.set(ISOConstants.DATE_SETTLEMENT, DateUtils.getLocalTransactionDate());
      isoMsg.set(ISOConstants.MERCHANT_TYPE, "1111");
      isoMsg.set(ISOConstants.ACQUIRING_INSTITUTION_IDENTIFICATION_CODE, "1111");
      isoMsg.set(ISOConstants.RETRIEVAL_REFERENCE_NUMBER, txnRefNum);
      isoMsg.set(ISOConstants.CARD_ACCEPTOR_NAME_OR_LOCATION, "Chatak Acquirer");// TODO: Card Acceptor Name &
      // Location
      isoMsg.set(ISOConstants.PRIVATE_ADDITIONAL_DATA, "Chatak merchant");// TODO: Merchant/Bank Name
      isoMsg.set(ISOConstants.TXN_CURRENCY_CODE, "840");
      isoMsg.set(ISOConstants.RESERVED_NATIONAL_57, "220");// TODO: Auth life cycle
      isoMsg.set(ISOConstants.RESERVED_NATIONAL_58, "0000000002");
      isoMsg.set(ISOConstants.RESERVED_PRIVATE_63, "80L30MMT");

    return isoMsg;
  }

  /**
   * Method to calculate processing fee for acquirer.
   * 
   * @param cardType
   * @param partnerId
   * @param merchantCode
   * @param txnAmount
   * @return
   * @throws Exception
   * @throws DataAccessException
   */
  public List<Object> getProcessingFee(String cardType, Long partnerId, String merchantCode,
      Long txnTotalAmount) {
    logger.info("Entering:: SettlementServiceImpl:: getProcessingFee method ");
    List<Object> results = new ArrayList<Object>(Integer.parseInt("2"));
    List<ProcessingFee> calculatedProcessingFeeList = new ArrayList<ProcessingFee>(0);
    Long chatakFeeAmountTotal = 0l;
    List<PGAcquirerFeeValue> acquirerFeeValueList =
        feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(merchantCode, cardType);
    if (CommonUtil.isListNotNullAndEmpty(acquirerFeeValueList)) {

      acquirerFeeValueListValidation(txnTotalAmount, calculatedProcessingFeeList,
			chatakFeeAmountTotal, acquirerFeeValueList);
    } else {
      String parentMerchantCode = merchantDao.getParentMerchantCode(merchantCode);
      if (null != parentMerchantCode) {
        acquirerFeeValueList =
            feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(parentMerchantCode, cardType);
        if (CommonUtil.isListNotNullAndEmpty(acquirerFeeValueList)) {
          validateAcquirerFeeValueList(txnTotalAmount, calculatedProcessingFeeList,
				chatakFeeAmountTotal, acquirerFeeValueList);
        }
      }
    }
    logger.info("Exiting:: TransactionService:: getProcessingFee method ");
    results.add(calculatedProcessingFeeList);
    results.add(chatakFeeAmountTotal);
    return results;
  }

private void acquirerFeeValueListValidation(Long txnTotalAmount, List<ProcessingFee> calculatedProcessingFeeList,
		Long chatakFeeAmountTotal, List<PGAcquirerFeeValue> acquirerFeeValueList) {
	Double calculatedProcessingFee;
	logger.info(
          " TransactionService:: getProcessingFee method :: Applying this merchant fee code ");
      for (PGAcquirerFeeValue acquirerFeeValue : acquirerFeeValueList) {
        calculatedProcessingFee = 0.00;
        ProcessingFee processingFee =
            getProcessingFeeItem(acquirerFeeValue, txnTotalAmount, calculatedProcessingFee);
        chatakFeeAmountTotal =
            chatakFeeAmountTotal + CommonUtil.getLongAmount(processingFee.getChatakProcessingFee());
        calculatedProcessingFeeList.add(processingFee);
      }
}

private void validateAcquirerFeeValueList(Long txnTotalAmount, List<ProcessingFee> calculatedProcessingFeeList,
		Long chatakFeeAmountTotal, List<PGAcquirerFeeValue> acquirerFeeValueList) {
	Double calculatedProcessingFee;
	logger.info(
	      "Exiting:: TransactionService:: getProcessingFee method :: Applying parentMerchantCode fee ");
	  for (PGAcquirerFeeValue acquirerFeeValue : acquirerFeeValueList) {
	    calculatedProcessingFee = 0.00;
	    ProcessingFee processingFee =
	        getProcessingFeeItem(acquirerFeeValue, txnTotalAmount, calculatedProcessingFee);
	    chatakFeeAmountTotal = chatakFeeAmountTotal
	        + CommonUtil.getLongAmount(processingFee.getChatakProcessingFee());
	    calculatedProcessingFeeList.add(processingFee);
	  }
}

  public void postReversalFeeToIssuance(String merchantId, String transactionId,
      String refundTransactionId) throws Exception {
    String parentMerchantCode = merchantDao.getParentMerchantCode(merchantId);
    List<PGAccountFeeLog> pgAccountFeeLogList =
        accountFeeLogDao.getPGAccountFeeLogOnTransactionId(transactionId);
    List<PGAccountFeeLog> pgAccountFeeLogRefundTxnList =
        accountFeeLogDao.getPGAccountFeeLogOnTransactionId(refundTransactionId);
    String agentId = merchantDao.getAgentId(merchantId);
    if (null != parentMerchantCode) {
      agentId = merchantDao.getAgentId(parentMerchantCode);
    }
    if (CommonUtil.isListNotNullAndEmpty(pgAccountFeeLogRefundTxnList)) {

      int listSize = pgAccountFeeLogRefundTxnList.size();
      PGAccountFeeLog pgAccountFeeLog = null;
      PGAccountFeeLog pgAccountFeeLogRefundTxn = null;

      for (int i = 0; i < listSize; i++) {
        pgAccountFeeLog = pgAccountFeeLogList.get(i);
        pgAccountFeeLogRefundTxn = pgAccountFeeLogRefundTxnList.get(i);
        pgAccountFeeLog.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
          pgAccountFeeLogRefundTxn = postVirtualAccFeeReversal(pgAccountFeeLogRefundTxn, agentId,
              pgAccountFeeLog.getIssuanceFeeTxnId());

          validateAndSetPGAccountFeeLogData(pgAccountFeeLog, pgAccountFeeLogRefundTxn);
        pgAccountFeeLog.setStatus(PGConstants.PG_TXN_REFUNDED);
        accountFeeLogDao.createOrSave(pgAccountFeeLog);
      }
    }
  }

  private void validateAndSetPGAccountFeeLogData(PGAccountFeeLog pgAccountFeeLog,
      PGAccountFeeLog pgAccountFeeLogRefundTxn) {
    if (null != pgAccountFeeLogRefundTxn) {
      pgAccountFeeLogRefundTxn.setFeeTxnDate(timestamp);
      pgAccountFeeLogRefundTxn.setUpdatedDate(timestamp);
      pgAccountFeeLogRefundTxn.setIssuanceFeeTxnId(pgAccountFeeLog.getIssuanceFeeTxnId());
      pgAccountFeeLogRefundTxn.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
      accountFeeLogDao.createOrSave(pgAccountFeeLogRefundTxn);
    }
  }
  
  public PGAccountFeeLog postVirtualAccFeeReversal(PGAccountFeeLog pgAccountFeeLog, String agentId,
	      String ciVirtualAccTxnId) throws Exception {
		PGAccountFeeLog feeLog = pgAccountFeeLog;
		VirtualAccFeeReversalRequest request = new VirtualAccFeeReversalRequest();
		request.setCiVirtualAccTxnId(ciVirtualAccTxnId);
		/* Start posting fee to issuance */
		String mode = merchantDao.getApplicationMode(pgAccountFeeLog.getEntityId());
		try {
			String output = JsonUtil.sendToIssuance(request,
					Properties.getProperty("chatak-issuance.virtual.reverse.fee"), mode, String.class);
			feeLog.setFeeTxnDate(new Timestamp(System.currentTimeMillis()));
			/* End posting fee to issuance */
			virtualAccFeePostResponse feeResponse = mapper.readValue(output, virtualAccFeePostResponse.class);
			if (null != feeResponse) {
				validateVirtualAccFeePostResponse(feeLog, feeResponse);
			}
		} catch (HttpClientException e) {
			logger.error("ERROR:: TransactionService:: postVirtualAccFeeReversal method", e);
			feeLog.setFeePostStatus(FeePostingStatus.FEE_POST_NETWORK_FAIL);
			feeLog.setStatus(String.valueOf(HttpStatus.SC_SERVICE_UNAVAILABLE));
		}
		return feeLog;
	}

private void validateVirtualAccFeePostResponse(PGAccountFeeLog feeLog, virtualAccFeePostResponse feeResponse) {
	if (feeResponse.getErrorCode().equals("CEC_0001")) {
	  feeLog.setFeePostStatus(FeePostingStatus.FEE_POST_SUCCESS);
	} else {
	  feeLog.setFeePostStatus(FeePostingStatus.FEE_POST_DECLINED);
	}
	feeLog.setIssuanceMessage(feeResponse.getErrorMessage());
}

  private ProcessingFee getProcessingFeeItem(PGAcquirerFeeValue acquirerFeeValue,
      Long txnTotalAmount, Double calculatedProcessingFee) {
    logger.info("Entering:: TransactionService:: getProcessingFeeItem method ");
    
    Double flatFee = CommonUtil.getDoubleAmountNotNull(acquirerFeeValue.getFlatFee());
    Double percentageFee = acquirerFeeValue.getFeePercentageOnly();
    percentageFee = txnTotalAmount * (CommonUtil.getDoubleAmountNotNull(percentageFee));
    ProcessingFee processingFee = new ProcessingFee();
    calculatedProcessingFee =
        (CommonUtil.getDoubleAmountNotNull(calculatedProcessingFee + percentageFee)) + flatFee;
    processingFee.setAccountNumber(acquirerFeeValue.getAccountNumber());
    processingFee.setChatakProcessingFee(calculatedProcessingFee);
    logger.info("Exiting:: TransactionService:: getProcessingFeeItem method ");
    return processingFee;
  }
  
  private void getCardProgramDetailsByCardNumber(String iin, String iinPartnerCode, String iinExt, PGTransaction pgTransaction, Long merchantId,Request request)
      throws ServiceException {
	  logger.info("TransactionService | getCardProgramDetailsByCardNumber | Entering");
	  
	  Long cardProgramId = cardProgramDao.findCardProgramByIIN(iin, iinPartnerCode, iinExt);
	  PGMerchant pgMerchant = merchantRepository.findById(merchantId);
	  
	  PGMerchantCardProgramMap pGMerchantCardProgramMap = null;
	  if(pgMerchant.getMerchantType().equalsIgnoreCase(PGConstants.SUB_MERCHANT)) {
		  pGMerchantCardProgramMap = merchantCardProgramMapDao.findByMerchantIdAndCardProgramId(pgMerchant.getParentMerchantId(), cardProgramId);
		  pgTransaction.setCpId(cardProgramId);
	  } else {	  
		  pGMerchantCardProgramMap = merchantCardProgramMapDao.findByMerchantIdAndCardProgramId(merchantId, cardProgramId);
	      pgTransaction.setCpId(cardProgramId);	  
	  }	 
	  
	  logger.info("TransactionService | getCardProgramDetailsByCardNumber | pGMerchantCardProgramMap: " + pGMerchantCardProgramMap);
	  
	  if(!StringUtils.isNullAndEmpty(pGMerchantCardProgramMap) 
			  && !StringUtils.isNullAndEmpty(pGMerchantCardProgramMap.getEntitytype())
			  && pGMerchantCardProgramMap.getEntitytype().equalsIgnoreCase(Constants.ISO_USER_TYPE)) {
	    
	      // check if ISO is active
	      String status = isoServiceDao.findISOStatusById(pGMerchantCardProgramMap.getEntityId());
	      if(!status.equals("Active")) {
	        throw new ServiceException(ActionErrorCode.ERROR_CODE_ISO_01);
	      }
	    
	      Long pmId = isoServiceDao.findByIsoIdAndCardProgramId(pGMerchantCardProgramMap.getEntityId(), cardProgramId);
	    
		  pgTransaction.setIsoId(pGMerchantCardProgramMap.getEntityId());
		  request.setIsoId(pgTransaction.getIsoId());
		  request.setPmId(pmId);
		  logger.info("TransactionService | getCardProgramDetailsByCardNumber | ISO_USER_TYPE | PM ID: " + pmId
		        + ", ISO ID: " + pGMerchantCardProgramMap.getEntityId());
		  
	  } else if(!StringUtils.isNullAndEmpty(pGMerchantCardProgramMap) 
			  && !StringUtils.isNullAndEmpty(pGMerchantCardProgramMap.getEntitytype())
			  && pGMerchantCardProgramMap.getEntitytype().equalsIgnoreCase(Constants.PM_USER_TYPE)) {
		  pgTransaction.setPmId(pGMerchantCardProgramMap.getEntityId());
		  request.setPmId(pgTransaction.getPmId());
		  logger.info("TransactionService | getCardProgramDetailsByCardNumber | PM_USER_TYPE | PM ID: " + pgTransaction.getPmId());
	  }
	  
	  logger.info("TransactionService | getCardProgramDetailsByCardNumber | Exiting");
  }
  
}
