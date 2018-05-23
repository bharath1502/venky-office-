/**
 * 
 */
package com.chatak.pay.service.impl;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chatak.pay.constants.ChatakPayErrorCode;
import com.chatak.pay.controller.model.CardData;
import com.chatak.pay.controller.model.Response;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pay.controller.model.TransactionResponse;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.exception.InvalidRequestException;
import com.chatak.pay.exception.SplitTransactionException;
import com.chatak.pay.model.TransactionDTO;
import com.chatak.pay.model.TransactionDTOResponse;
import com.chatak.pay.service.PGSplitTransactionService;
import com.chatak.pay.service.PGTransactionService;
import com.chatak.pay.util.JsonUtil;
import com.chatak.pay.util.StringUtil;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountFeeLogDao;
import com.chatak.pg.acq.dao.AccountHistoryDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.FeeProgramDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.OnlineTxnLogDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.SplitTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountHistory;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;
import com.chatak.pg.acq.dao.model.PGCurrencyCode;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGOnlineTxnLog;
import com.chatak.pg.acq.dao.model.PGSplitTransaction;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.CurrencyCodeRepository;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.pg.acq.dao.repository.TransactionRepository;
import com.chatak.pg.bean.AuthRequest;
import com.chatak.pg.bean.AuthResponse;
import com.chatak.pg.bean.BalanceEnquiryResponse;
import com.chatak.pg.bean.CaptureRequest;
import com.chatak.pg.bean.CaptureResponse;
import com.chatak.pg.bean.PurchaseRequest;
import com.chatak.pg.bean.PurchaseResponse;
import com.chatak.pg.bean.RefundRequest;
import com.chatak.pg.bean.RefundResponse;
import com.chatak.pg.bean.Request;
import com.chatak.pg.bean.ReversalRequest;
import com.chatak.pg.bean.ReversalResponse;
import com.chatak.pg.bean.VoidRequest;
import com.chatak.pg.bean.VoidResponse;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.ActionCode;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.NationalPOSEntryModeEnum;
import com.chatak.pg.enums.ProcessorType;
import com.chatak.pg.enums.TransactionStatus;
import com.chatak.pg.enums.TransactionType;
import com.chatak.pg.model.ProcessingFee;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;
import com.chatak.pg.util.PGUtils;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.StringUtils;
import com.chatak.switches.sb.SwitchServiceBroker;
import com.chatak.switches.sb.exception.ServiceException;
import com.chatak.switches.sb.util.ProcessorConfig;
import com.chatak.switches.sb.util.SpringDAOBeanFactory;
import com.litle.sdk.generate.MethodOfPaymentTypeEnum;

/**
 * @Author: Girmiti Software
 * @Date: Apr 24, 2015
 * @Time: 12:21:13 PM
 * @Version: 1.0
 * @Comments:
 */
@Service
public class PGTransactionServiceImpl implements PGTransactionService {

	private static Logger log = Logger.getLogger(PGTransactionServiceImpl.class);

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private OnlineTxnLogDao onlineTxnLogDao;

	@Autowired
	private TransactionDao transactionDao;

	@Autowired
	private SplitTransactionDao splitTransactionDao;

	@Autowired
	private PGSplitTransactionService pgSplitTransactionService;
	
	@Autowired
	protected AccountDao accountDao;
	
	@Autowired
	protected AccountHistoryDao accountHistoryDao;
	
	@Autowired
	FeeProgramDao feeProgramDao;
	
	@Autowired
	MerchantDao merchantDao;
	
	@Autowired
	AccountFeeLogDao accountFeeLogDao;
	
	@Autowired
	CurrencyCodeRepository currencyCodeRepository;
	
	@Autowired
	AccountTransactionsDao accountTransactionsDao;
	
	@Autowired
	CurrencyConfigDao currencyConfigDao;

	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	MerchantUpdateDao merchantUpdateDao;
	
	@Autowired
	VoidTransactionDao voidTransactionDao;
	
	@Autowired
	RefundTransactionDao refundTransactionDao;
	
	@Autowired
	CurrencyConfigRepository currencyConfigRepository;
	
	public Response processTransaction(TransactionRequest transactionRequest) {
		log.info("Entering :: processTransaction :: TxnType :  " + transactionRequest.getTransactionType());
		try {
			SpringDAOBeanFactory.appContext = appContext;
			if(null != transactionRequest.getTransactionType()) {
				switch(transactionRequest.getTransactionType()) {
				case AUTH:
					return processAuth(transactionRequest);
				case CAPTURE:
					return processCapture(transactionRequest);
				case SALE:
					return processAuthCapture(transactionRequest);// SALE/Purchase
				case REFUND:
					return processRefund(transactionRequest);
				case VOID:
					return processVoid(transactionRequest);
				case SPLIT_ACCEPT:
					return processSplitSale(transactionRequest);
				case SPLIT_REJECT:
					return processSplitReject(transactionRequest);
				case REVERSAL:
					return processReversal(transactionRequest);
				case REFUND_VOID:
					return processRefundVoid(transactionRequest);// Need to implement
					// for on us processor
				case BALANCE:
				    return processBalanceEnquiry(transactionRequest);
				default:
					break;
				}
			} else {
				log.error("invalid transactiontype:: processCardPayment method");
				return getErrorResponse(ChatakPayErrorCode.TXN_0001.name());
			}
			return null;
		} catch(Exception e) {
			log.error("ERROR:: processCardPayment method", e);
			return getErrorResponse(ChatakPayErrorCode.TXN_0999.name());
		}
	}

	public Response processTransaction(TransactionRequest transactionRequest, PGMerchant merchant) {
		try {
			SpringDAOBeanFactory.appContext = appContext;
			if(null != transactionRequest.getTransactionType()) {
				return processLoadFund(transactionRequest, merchant);
			} else {
				log.error("invalid transactiontype:: processCardPayment method");
				return getErrorResponse(ChatakPayErrorCode.TXN_0001.name());
			}
		} catch(Exception e) {
			log.error("ERROR:: processCardPayment method", e);
			return getErrorResponse(ChatakPayErrorCode.TXN_0999.name());
		}
	}

	/**
	 * Method to process Auth Capture or SALE financial transaction
	 * 
	 * @param transactionRequest
	 * @return
	 */
	public Response processAuthCapture(TransactionRequest transactionRequest) {

		log.info("RestService | PGTransactionServiceImpl | processAuthCapture | Entering");
		TransactionResponse transactionResponse = new TransactionResponse();
		PGOnlineTxnLog pgOnlineTxnLog = null;
		try {
			// Logging into Online txn log
			pgOnlineTxnLog = logEntry(TransactionStatus.INITATE, transactionRequest);

			List<PGAcquirerFeeValue> feeValues = feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(transactionRequest.getMerchantId(),
					transactionRequest.getCardData().getCardType().toString());
			Long feeAmount = 0l;
			if(StringUtil.isListNotNullNEmpty(feeValues)) {
				feeAmount = (long) (transactionRequest.getTotalTxnAmount() * (feeValues.get(0).getFeePercentageOnly()/Integer.parseInt("100")));
				feeAmount = feeAmount + feeValues.get(0).getFlatFee();
			}
			
			PGMerchant pgMerchant = merchantUpdateDao.getMerchantByCode(transactionRequest.getMerchantId());
			
			PurchaseRequest request = new PurchaseRequest();
			request.setCardNum(transactionRequest.getCardData().getCardNumber());
			request.setExpDate(transactionRequest.getCardData().getExpDate());
			request.setCvv(transactionRequest.getCardData().getCvv());
			request.setMerchantId(transactionRequest.getMerchantId());
			request.setTerminalId(transactionRequest.getTerminalId());
			request.setInvoiceNumber(transactionRequest.getInvoiceNumber());
			request.setTrack2(transactionRequest.getCardData().getTrack2());
			request.setTotalTxnAmount(transactionRequest.getTotalTxnAmount());
			request.setTxnFee(feeAmount);
			request.setTxnAmount(transactionRequest.getTotalTxnAmount() - feeAmount);
			request.setCardHolderName(transactionRequest.getCardData().getCardHolderName());
			request.setPosEntryMode(transactionRequest.getPosEntryMode()+"0");
			request.setDescription(transactionRequest.getDescription());
			request.setAcq_mode(EntryModeEnum.getValue(transactionRequest.getPosEntryMode()));
			request.setTrack(transactionRequest.getCardData().getTrack());
			request.setBillingData(transactionRequest.getBillingData());
			request.setNationalPOSEntryMode(NationalPOSEntryModeEnum.valueOf(transactionRequest.getEntryMode() + "_DE58"));
			request.setCardType(transactionRequest.getCardData().getCardType().value());
			request.setPulseData(Properties.getProperty("chatak-pay.pulse.data"));
			request.setAcq_channel(transactionRequest.getOriginChannel());
			request.setProcessorMid(transactionRequest.getProcessorMid());
			request.setMode(transactionRequest.getMode());
			log.info("Processor Merchant ID: "+request.getProcessorMid());
			request.setCardHolderEmail(transactionRequest.getCardData().getCardHolderEmail());
			request.setEmv(transactionRequest.getCardData().getEmv());
			request.setQrCode(transactionRequest.getQrCode());
			log.info("Transaction Currency : " + transactionRequest.getCurrencyCode());
			request.setCurrencyCode(transactionRequest.getCurrencyCode());
			request.setUserName(transactionRequest.getUserName());
			request.setTimeZoneOffset(transactionRequest.getTimeZoneOffset());
			request.setTimeZoneRegion(transactionRequest.getTimeZoneRegion());
            getMerchantDetails(pgMerchant, request);
			
			request.setUid(transactionRequest.getCardData().getUid());
			
			PurchaseResponse purchaseResponse = new SwitchServiceBroker().purchaseTransaction(request);

			transactionResponse.setErrorCode(purchaseResponse.getErrorCode());
			transactionResponse.setErrorMessage(purchaseResponse.getErrorMessage());
			transactionResponse.setTotalAmount(purchaseResponse.getTotalAmount());
			LogHelper.logInfo(log, LoggerMessage.getCallerName(), "Response TotalAmount : " + purchaseResponse.getTotalAmount());
			transactionResponse.setTotalTxnAmount((purchaseResponse.getTotalAmount().doubleValue())/Integer.parseInt("100"));
			transactionResponse.setAuthId(purchaseResponse.getAuthId() != null ? purchaseResponse.getAuthId() : null);
			transactionResponse.setTxnRefNumber(purchaseResponse.getTxnRefNum() != null ? purchaseResponse.getTxnRefNum()
					: null);
			transactionResponse.setCgRefNumber(purchaseResponse.getUpStreamTxnRefNum());
			transactionResponse.setTxnDateTime(System.currentTimeMillis());
			transactionResponse.setMerchantCode(request.getMerchantId());
			transactionResponse.setMerchantName(transactionRequest.getMerchantName());

			logExit(pgOnlineTxnLog,
					TransactionStatus.COMPLETED,
					purchaseResponse.getErrorMessage(),
					(transactionResponse.getTxnRefNumber() == null ? "0" : transactionResponse.getTxnRefNumber()),
					purchaseResponse.getErrorMessage(),
					transactionResponse.getTxnRefNumber());
			
			if (purchaseResponse.getErrorCode().equals("00")) {
				String autoSettlement = pgMerchant.getMerchantConfig().getAutoSettlement().toString();
				if (autoSettlement != null && autoSettlement.equals("1")) {
					updateSettlementStatus(transactionRequest.getMerchantId(), transactionRequest.getTerminalId(),
							purchaseResponse.getTxnRefNum(), "Sale", PGConstants.PG_SETTLEMENT_EXECUTED, "Auto Settlement", feeAmount);
				}
			}

		} catch(ServiceException e) {
		  log.error("RestService | PGTransactionServiceImpl | processAuthCapture | ServiceException :", e);
		  setTxnErrorResponse(transactionResponse, e.getMessage());
		} catch(Exception e) {
			log.error("RestService | PGTransactionServiceImpl | processAuthCapture | Exception :", e);
			setTxnErrorResponse(transactionResponse, ActionErrorCode.ERROR_CODE_PG_SERVICE);
		}
		log.info("RestService | PGTransactionServiceImpl | processAuthCapture | Exiting");
		return transactionResponse;

	}

	public Response processAuth(TransactionRequest transactionRequest) {

		log.info("RestService | PGTransactionServiceImpl | processAuth | Entering");
		TransactionResponse transactionResponse = new TransactionResponse();
		try {
			// Logging into Online txn log
			PGOnlineTxnLog pgOnlineTxnLog = logEntry(TransactionStatus.INITATE, transactionRequest);
			AuthRequest request = new AuthRequest();
			request.setCardNum(transactionRequest.getCardData().getCardNumber());
			request.setExpDate(transactionRequest.getCardData().getExpDate());
			request.setCvv(transactionRequest.getCardData().getCvv());
			request.setMerchantId(transactionRequest.getMerchantId());
			request.setTerminalId(transactionRequest.getTerminalId());
			request.setInvoiceNumber(transactionRequest.getInvoiceNumber());
			request.setTrack2(transactionRequest.getCardData().getTrack2());
			request.setTxnAmount(transactionRequest.getMerchantAmount());
			request.setTxnFee(StringUtils.getValidLongValue(transactionRequest.getFeeAmount()));
			request.setTotalTxnAmount(transactionRequest.getTotalTxnAmount());
			request.setCardHolderName(transactionRequest.getCardData().getCardHolderName());
			request.setDescription(transactionRequest.getDescription());
			request.setPosEntryMode(transactionRequest.getPosEntryMode()+"0");
			request.setAcq_mode(EntryModeEnum.getValue(transactionRequest.getPosEntryMode()));// need to set proper value
			request.setBillingData(transactionRequest.getBillingData());
			request.setTrack(transactionRequest.getCardData().getTrack());
			request.setNationalPOSEntryMode(NationalPOSEntryModeEnum.valueOf(transactionRequest.getEntryMode() + "_DE58"));
			request.setPulseData(Properties.getProperty("chatak-pay.pulse.data"));
			request.setCardType(transactionRequest.getCardData().getCardType().value());
			request.setAcq_channel(transactionRequest.getOriginChannel());
			request.setMode(transactionRequest.getMode());
			request.setProcessorMid(transactionRequest.getProcessorMid());
			request.setCardHolderEmail(transactionRequest.getCardData().getCardHolderEmail());
			request.setEmv(transactionRequest.getCardData().getEmv());
			
			AuthResponse authResponse = new SwitchServiceBroker().authTransaction(request);

			transactionResponse.setErrorCode(authResponse.getErrorCode());
			transactionResponse.setErrorMessage(authResponse.getErrorMessage());
			transactionResponse.setAuthId(authResponse.getAuthId() != null ? authResponse.getAuthId() : null);
			transactionResponse.setTxnRefNumber(authResponse.getTxnRefNum() != null ? authResponse.getTxnRefNum() : null);
			transactionResponse.setTxnDateTime(System.currentTimeMillis());
			transactionResponse.setCgRefNumber(authResponse.getUpStreamTxnRefNum());
			transactionResponse.setMerchantCode(request.getMerchantId());
			logExit(pgOnlineTxnLog,
					TransactionStatus.COMPLETED,
					authResponse.getErrorMessage(),
					(transactionResponse.getTxnRefNumber() == null ? "0" : transactionResponse.getTxnRefNumber()),
					authResponse.getErrorMessage(),
					transactionResponse.getTxnRefNumber());

		} catch(ServiceException e) {
			log.error("RestService | PGTransactionServiceImpl | processAuth | ServiceException :", e);
			setTxnErrorResponse(transactionResponse, e.getMessage());
		} catch(Exception e) {
			log.error("RestService | PGTransactionServiceImpl | processAuth | Exception :", e);
			setTxnErrorResponse(transactionResponse, ActionErrorCode.ERROR_CODE_PG_SERVICE);
		}
		log.info("RestService | PGTransactionServiceImpl | processAuth | Exiting");
		return transactionResponse;

	}

	public Response processCapture(TransactionRequest transactionRequest) {

		log.info("RestService | PGTransactionServiceImpl | authCapture | Entering");
		TransactionResponse transactionResponse = new TransactionResponse();
		try {
			PGTransaction pgTransaction = voidTransactionDao.findTransactionToCaptureByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(transactionRequest.getTxnRefNumber(),
					transactionRequest.getCgRefNumber(),
					transactionRequest.getMerchantId(),
					transactionRequest.getTerminalId());
			if(null != pgTransaction) {

				CardData card = new CardData();
				card.setCardNumber(EncryptionUtil.decrypt(pgTransaction.getPan()));
				card.setExpDate(pgTransaction.getExpDate().toString());
				card.setCardType(MethodOfPaymentTypeEnum.BLANK);
				card.setCardHolderName(pgTransaction.getCardHolderName());
				transactionRequest.setCardData(card);// setting sale txn card details
				// into void request
				transactionRequest.setTxnAmount(pgTransaction.getTxnAmount());
				transactionRequest.setTotalTxnAmount(pgTransaction.getTxnTotalAmount());
				transactionRequest.setInvoiceNumber(pgTransaction.getInvoiceNumber());
				transactionRequest.setFeeAmount(pgTransaction.getFeeAmount());
				// Logging into Online txn log
				PGOnlineTxnLog pgOnlineTxnLog = logEntry(TransactionStatus.INITATE, transactionRequest);
				CaptureRequest request = new CaptureRequest();
				request.setCardNum(transactionRequest.getCardData().getCardNumber());
				request.setExpDate(transactionRequest.getCardData().getExpDate());
				request.setCvv(transactionRequest.getCardData().getCvv());
				request.setMerchantId(transactionRequest.getMerchantId());
				request.setTerminalId(transactionRequest.getTerminalId());
				request.setInvoiceNumber(transactionRequest.getInvoiceNumber());
				request.setTrack2(transactionRequest.getCardData().getTrack2());
				request.setTxnAmount(transactionRequest.getTxnAmount());
				request.setCardHolderEmail(transactionRequest.getCardData().getCardHolderEmail());
				request.setAuthTxnRefNum(transactionRequest.getTxnRefNumber());
				request.setAuthId(transactionRequest.getAuthId());
				request.setIssuerTxnRefNum(transactionRequest.getCgRefNumber());
				request.setTxnFee(StringUtils.getValidLongValue(transactionRequest.getFeeAmount()));
				request.setTotalTxnAmount(transactionRequest.getTotalTxnAmount());
				request.setEntryMode(transactionRequest.getEntryMode());
				request.setCardHolderName(transactionRequest.getCardData().getCardHolderName());
				request.setDescription(transactionRequest.getDescription());
				request.setPosEntryMode(transactionRequest.getPosEntryMode()+"0");
				request.setAcq_mode(EntryModeEnum.getValue(transactionRequest.getPosEntryMode()));// need to set proper value
				request.setPulseData(Properties.getProperty("chatak-pay.pulse.data"));
				request.setCardType(transactionRequest.getCardData().getCardType().value());
				request.setNationalPOSEntryMode(NationalPOSEntryModeEnum.UNSPECIFIED_DE58);
				request.setAcq_channel(transactionRequest.getOriginChannel());
				request.setMode(transactionRequest.getMode());
				request.setProcessorMid(transactionRequest.getProcessorMid());
				request.setEmv(transactionRequest.getCardData().getEmv());
				
				CaptureResponse captureResponse = new SwitchServiceBroker().captureTransaction(request);

				transactionResponse.setErrorCode(captureResponse.getErrorCode());
				transactionResponse.setErrorMessage(captureResponse.getErrorMessage());
				transactionResponse.setAuthId(captureResponse.getAuthId() != null ? captureResponse.getAuthId() : null);
				transactionResponse.setTxnRefNumber(captureResponse.getTxnRefNum() != null ? captureResponse.getTxnRefNum()
						: null);
				transactionResponse.setTxnDateTime(System.currentTimeMillis());
				transactionResponse.setCgRefNumber(captureResponse.getUpStreamTxnRefNum());
				transactionResponse.setMerchantCode(request.getMerchantId());
				logExit(pgOnlineTxnLog,
						TransactionStatus.COMPLETED,
						captureResponse.getErrorMessage(),
						(transactionResponse.getTxnRefNumber() == null ? "0"
								: transactionResponse.getTxnRefNumber()),
								captureResponse.getErrorMessage(),
								transactionResponse.getTxnRefNumber());
			}
			else {
				log.error("RestService | PGTransactionServiceImpl | processCapture |throwing exception :");
				throw new ServiceException(ActionErrorCode.ERROR_CODE_DUPLICATE_CAPTURE_ENTRY);
			}
		} catch(ServiceException e) {
			log.error("RestService | PGTransactionServiceImpl | authCapture | ServiceException :", e);
			setTxnErrorResponse(transactionResponse, e.getMessage());
		} catch(Exception e) {
			log.error("RestService | PGTransactionServiceImpl | authCapture | Exception :", e);
			setTxnErrorResponse(transactionResponse, ActionErrorCode.ERROR_CODE_PG_SERVICE);
		}
		log.info("RestService | PGTransactionServiceImpl | authCapture | Exiting");
		return transactionResponse;

	}

	/**
	 * Method to process VOID transaction
	 * 
	 * @param transactionRequest
	 * @return
	 */
	public Response processVoid(TransactionRequest transactionRequest) {

		log.info("RestService | PGTransactionServiceImpl | processVoid | Entering");
		TransactionResponse transactionResponse = new TransactionResponse();
		PGOnlineTxnLog pgOnlineTxnLog = null;
		try {

			PGTransaction pgTransaction = voidTransactionDao.findTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(transactionRequest.getTxnRefNumber(),
					transactionRequest.getCgRefNumber(),
					transactionRequest.getMerchantId(),
					transactionRequest.getTerminalId());

			if(null == pgTransaction) {
				log.error("RestService | PGTransactionServiceImpl | processVoid |throwing exception");
				throw new ServiceException(ActionErrorCode.ERROR_CODE_TXN_NULL);
			} else if(pgTransaction.getMerchantSettlementStatus().equalsIgnoreCase(PGConstants.TXN_TYPE_EXECUTED)) {
				log.error("RestService | PGTransactionServiceImpl | processVoid |throwing exception");
				throw new ServiceException(ActionErrorCode.ERROR_CODE_TXN_EXECUTED);
			} else if(pgTransaction.getTransactionType().equals(PGConstants.TXN_TYPE_REFUND)) {
				log.error("RestService | PGTransactionServiceImpl | processVoid |throwing exception");
				throw new ServiceException(ActionErrorCode.ERROR_CODE_DUPLICATE_VOID_ENTRY);
			} else {
				reverseSplitSaleIfExists(transactionRequest.getTxnRefNumber(), transactionRequest.getMerchantId());
				setCardDataAndTransactionRequestData(transactionRequest, pgTransaction);

				// Logging into Online txn log
				pgOnlineTxnLog = logEntry(TransactionStatus.INITATE, transactionRequest);
				VoidRequest request = new VoidRequest();
				setVoidRequestData(transactionRequest, request);
				transactionRequest.setPosEntryMode(pgTransaction.getPosEntryMode());
				request.setPosEntryMode(transactionRequest.getPosEntryMode().length() < Integer.parseInt("3") ? transactionRequest.getPosEntryMode()+"0"
						: transactionRequest.getPosEntryMode()); // Why the check for length of 3?
				request.setAcq_mode(EntryModeEnum.getValue(transactionRequest.getPosEntryMode()));// need to set proper value
				request.setNationalPOSEntryMode(NationalPOSEntryModeEnum.UNSPECIFIED_DE58);
				request.setPulseData(Properties.getProperty("chatak-pay.pulse.data"));
				request.setCardType(transactionRequest.getCardData().getCardType().value());
				request.setMode(transactionRequest.getMode());
				request.setAcq_channel(transactionRequest.getOriginChannel());
				request.setProcessorMid(transactionRequest.getProcessorMid());
				request.setEmv(transactionRequest.getCardData().getEmv());
				request.setCardHolderEmail(pgTransaction.getCardHolderEmail());
				request.setUserName(transactionRequest.getUserName());
				request.setCurrencyCode(transactionRequest.getCurrencyCode());
				request.setTimeZoneOffset(transactionRequest.getTimeZoneOffset());
				request.setTimeZoneRegion(transactionRequest.getTimeZoneRegion());
				PGMerchant pgMerchant = merchantUpdateDao.getMerchantByCode(transactionRequest.getMerchantId());
				getMerchantDetails(pgMerchant, request);
				
				VoidResponse voidResponse = new SwitchServiceBroker().voidTransaction(request);

				transactionResponse.setErrorCode(voidResponse.getErrorCode());
				transactionResponse.setErrorMessage(voidResponse.getErrorMessage());
				transactionResponse.setAuthId(voidResponse.getAuthId() != null ? voidResponse.getAuthId() : null);
				transactionResponse.setCgRefNumber(voidResponse.getUpStreamTxnRefNum());
				transactionResponse.setTxnRefNumber(voidResponse.getTxnRefNum() != null ? voidResponse.getTxnRefNum() : null);
				transactionResponse.setTxnDateTime(System.currentTimeMillis());
				transactionResponse.setMerchantCode(request.getMerchantId());
				logExit(pgOnlineTxnLog,
						TransactionStatus.COMPLETED,
						voidResponse.getErrorMessage(),
						(transactionResponse.getTxnRefNumber() == null ? "0"
								: transactionResponse.getTxnRefNumber()),
								voidResponse.getErrorMessage(),
								transactionResponse.getTxnRefNumber());
			}

		} catch(ServiceException e) {
			log.error("RestService | PGTransactionServiceImpl | processVoid | ServiceException :", e);
			setTxnErrorResponse(transactionResponse, e.getMessage());
		} catch(Exception e) {
			log.error("RestService | PGTransactionServiceImpl | processVoid | Exception :", e);
			setTxnErrorResponse(transactionResponse, ActionErrorCode.ERROR_CODE_PG_SERVICE);
		}
		log.info("RestService | PGTransactionServiceImpl | processVoid | Exiting");
		return transactionResponse;

	}

	private void setCardDataAndTransactionRequestData(TransactionRequest transactionRequest,
			PGTransaction pgTransaction) {
		CardData card = new CardData();
		card.setCardNumber(EncryptionUtil.decrypt(pgTransaction.getPan()));
		card.setExpDate(pgTransaction.getExpDate().toString());
		card.setCardType(MethodOfPaymentTypeEnum.valueOf(PGUtils.getCCType(card.getCardNumber())));
		card.setCardHolderName(pgTransaction.getCardHolderName());
		transactionRequest.setCardData(card);// setting sale txn card details
		// into void request
		transactionRequest.setTxnAmount(pgTransaction.getTxnTotalAmount());
		transactionRequest.setTotalTxnAmount(pgTransaction.getTxnTotalAmount());
		transactionRequest.setMerchantAmount(pgTransaction.getTxnAmount());
		transactionRequest.setFeeAmount(pgTransaction.getFeeAmount());
		transactionRequest.setInvoiceNumber(pgTransaction.getInvoiceNumber());
	}

	private void setVoidRequestData(TransactionRequest transactionRequest, VoidRequest request) {
		request.setCardNum(transactionRequest.getCardData().getCardNumber());
		request.setExpDate(transactionRequest.getCardData().getExpDate());
		request.setCvv(transactionRequest.getCardData().getCvv());
		request.setMerchantId(transactionRequest.getMerchantId());
		request.setTerminalId(transactionRequest.getTerminalId());
		request.setInvoiceNumber(transactionRequest.getInvoiceNumber());
		request.setTrack2(transactionRequest.getCardData().getTrack2());
		request.setTxnAmount(transactionRequest.getMerchantAmount());
		request.setTotalTxnAmount(transactionRequest.getTotalTxnAmount());
		request.setTxnFee(StringUtils.getValidLongValue(transactionRequest.getFeeAmount()));
		request.setTxnRefNum(transactionRequest.getTxnRefNumber());
		request.setIssuerTxnRefNum(transactionRequest.getCgRefNumber());
		request.setCardHolderName(transactionRequest.getCardData().getCardHolderName());
		request.setDescription(transactionRequest.getDescription());
	}

	public Response processReversal(TransactionRequest transactionRequest) {

		log.info("RestService | PGTransactionServiceImpl | authCapture | Entering");
		TransactionResponse transactionResponse = new TransactionResponse();
		try {
			PGTransaction pgTransaction = voidTransactionDao.findTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(transactionRequest.getTxnRefNumber(),
					transactionRequest.getCgRefNumber(),
					transactionRequest.getMerchantId(),
					transactionRequest.getTerminalId());
			// Logging into Online txn log
			PGOnlineTxnLog pgOnlineTxnLog = logEntry(TransactionStatus.INITATE, transactionRequest);
			ReversalRequest request = new ReversalRequest();
			request.setCardNum(transactionRequest.getCardData().getCardNumber());
			request.setExpDate(transactionRequest.getCardData().getExpDate());
			request.setCvv(transactionRequest.getCardData().getCvv());
			request.setMerchantId(transactionRequest.getMerchantId());
			request.setTerminalId(transactionRequest.getTerminalId());
			request.setInvoiceNumber(transactionRequest.getInvoiceNumber());
			request.setTrack2(transactionRequest.getCardData().getTrack2());
			request.setTxnAmount(transactionRequest.getTxnAmount());
			request.setCardHolderName(transactionRequest.getCardData().getCardHolderName());
			transactionRequest.setPosEntryMode(pgTransaction.getPosEntryMode());
			request.setPosEntryMode(transactionRequest.getPosEntryMode()+"0");
			request.setAcq_mode(EntryModeEnum.getValue(transactionRequest.getPosEntryMode()));// need to set proper value
			request.setAcq_channel(transactionRequest.getOriginChannel());
			request.setMode(transactionRequest.getMode());
			request.setProcessorMid(transactionRequest.getProcessorMid());
			request.setEmv(transactionRequest.getCardData().getEmv());
			
			ReversalResponse reversalResponse = new SwitchServiceBroker().reversalTransaction(request);

			transactionResponse.setErrorCode(reversalResponse.getErrorCode());
			transactionResponse.setErrorMessage(reversalResponse.getErrorMessage());
			transactionResponse.setAuthId(reversalResponse.getAuthId() != null ? reversalResponse.getAuthId() : null);
			transactionResponse.setTxnRefNumber(reversalResponse.getTxnRefNum() != null ? reversalResponse.getTxnRefNum()
					: null);
			transactionResponse.setTxnDateTime(System.currentTimeMillis());
			logExit(pgOnlineTxnLog,
					TransactionStatus.COMPLETED,
					reversalResponse.getErrorMessage(),
					(transactionResponse.getTxnRefNumber() == null ? "0" : transactionResponse.getTxnRefNumber()),
					reversalResponse.getErrorMessage(),
					transactionResponse.getTxnRefNumber());

		} catch(ServiceException e) {
			log.error("RestService | PGTransactionServiceImpl | authCapture | ServiceException :", e);
			setTxnErrorResponse(transactionResponse, e.getMessage());
		} catch(Exception e) {
			log.error("RestService | PGTransactionServiceImpl | authCapture | Exception :", e);
			setTxnErrorResponse(transactionResponse, ActionErrorCode.ERROR_CODE_PG_SERVICE);
		}
		log.info("RestService | PGTransactionServiceImpl | authCapture | Exiting");
		return transactionResponse;

	}

	public Response processRefund(TransactionRequest transactionRequest) {

		log.info("RestService | PGTransactionServiceImpl | processRefund | Entering");
		TransactionResponse transactionResponse = new TransactionResponse();
		try {
			PGTransaction pgTransaction = refundTransactionDao.findTransactionToRefundByPGTxnIdAndIssuerTxnIdAndMerchantId(transactionRequest.getTxnRefNumber(),
					transactionRequest.getCgRefNumber(),
					transactionRequest.getMerchantId());

			if(null != pgTransaction) {
				
				if((null != pgTransaction.getRefundStatus() && 1 == pgTransaction.getRefundStatus().intValue()) || (PGConstants.PG_SETTLEMENT_REJECTED.equalsIgnoreCase(pgTransaction.getMerchantSettlementStatus()))) {
					transactionResponse.setErrorCode(ChatakPayErrorCode.TXN_0103.name());
					transactionResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0103.name(), null, LocaleContextHolder.getLocale()));
					transactionResponse.setTxnDateTime(System.currentTimeMillis());
					return transactionResponse;
				}
				
				if(pgTransaction.getMerchantSettlementStatus().equals(PGConstants.PG_SETTLEMENT_PROCESSING)) {
					transactionResponse.setErrorCode(ChatakPayErrorCode.TXN_0113.name());
					transactionResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0113.name(), null, LocaleContextHolder.getLocale()));
					transactionResponse.setTxnDateTime(System.currentTimeMillis());
					return transactionResponse;
				}
				
				Long refundedAmount=refundTransactionDao.getRefundedAmountOnTxnId(pgTransaction.getTransactionId());
				if(null!=refundedAmount&&null!=transactionRequest.getTotalTxnAmount()&&((pgTransaction.getTxnTotalAmount()-refundedAmount)<transactionRequest.getTotalTxnAmount())){
					throw new ServiceException(ActionErrorCode.REFUND_AMOUNT_EXCEEDS);
				}
				
				// Check for merchant sufficient balance for a refund
				log.info("RestService | PGTransactionServiceImpl | processRefund | Check for merchant sufficient balance");
				PGAccount pgAccount = accountDao.getPgAccount(transactionRequest.getMerchantId());
				log.info("RestService | PGTransactionServiceImpl | processRefund | incoming: " + transactionRequest.getTotalTxnAmount());
				log.info("RestService | PGTransactionServiceImpl | processRefund | current bal: " + pgAccount.getCurrentBalance());
				validateTxnAmount(transactionRequest, pgAccount);
				
				CardData card = new CardData();
				card.setCardNumber(EncryptionUtil.decrypt(pgTransaction.getPan()));
				card.setExpDate(pgTransaction.getExpDate().toString());
				card.setCardType(MethodOfPaymentTypeEnum.valueOf(PGUtils.getCCType(card.getCardNumber())));
				card.setCardHolderName(pgTransaction.getCardHolderName());
				transactionRequest.setCardData(card);
				validateTotalTxnAndMerchantAmount(transactionRequest, pgTransaction);
				transactionRequest.setInvoiceNumber(pgTransaction.getInvoiceNumber());
				// Logging into Online txn log
				PGOnlineTxnLog pgOnlineTxnLog = logEntry(TransactionStatus.INITATE, transactionRequest);
				RefundRequest request = new RefundRequest();
				validateRefundAmount(transactionRequest, pgTransaction, refundedAmount, request);
				request.setCardNum(transactionRequest.getCardData().getCardNumber());
				request.setMerchantId(transactionRequest.getMerchantId());
				request.setExpDate(transactionRequest.getCardData().getExpDate());
				request.setCvv(transactionRequest.getCardData().getCvv());
				request.setTerminalId(transactionRequest.getTerminalId());
				request.setTxnAmount(transactionRequest.getMerchantAmount());
				request.setInvoiceNumber(transactionRequest.getInvoiceNumber());
				request.setTrack2(transactionRequest.getCardData().getTrack2());
				request.setTotalTxnAmount(transactionRequest.getTotalTxnAmount());
				request.setIssuerTxnRefNum(transactionRequest.getCgRefNumber());
				request.setTxnFee(StringUtils.getValidLongValue(transactionRequest.getFeeAmount()));
				request.setTxnRefNum(transactionRequest.getTxnRefNumber());
				request.setCardHolderName(transactionRequest.getCardData().getCardHolderName());
				request.setDescription(transactionRequest.getDescription());
				transactionRequest.setPosEntryMode(pgTransaction.getPosEntryMode());
				request.setPosEntryMode(transactionRequest.getPosEntryMode());
				request.setAcq_mode(EntryModeEnum.getValue(transactionRequest.getPosEntryMode()));// need to set proper value
				request.setNationalPOSEntryMode(NationalPOSEntryModeEnum.UNSPECIFIED_DE58);
				request.setPulseData(Properties.getProperty("chatak-pay.pulse.data"));
				request.setSaleDependentRefund(true);//Flag to check sale defendant refund or not 
				request.setCardType(transactionRequest.getCardData().getCardType().value());
				request.setAcq_channel(transactionRequest.getOriginChannel());
				request.setMode(transactionRequest.getMode());
				request.setProcessorMid(transactionRequest.getProcessorMid());
				request.setCardHolderEmail(pgTransaction.getCardHolderEmail());
				request.setEmv(transactionRequest.getCardData().getEmv());
				request.setCurrencyCode(transactionRequest.getCurrencyCode());
				request.setUserName(transactionRequest.getUserName());
				request.setTimeZoneOffset(transactionRequest.getTimeZoneOffset());
				request.setTimeZoneRegion(transactionRequest.getTimeZoneRegion());
				PGMerchant pgMerchant = merchantUpdateDao.getMerchantByCode(transactionRequest.getMerchantId());
				getMerchantDetails(pgMerchant, request);
				
				RefundResponse refundResponse = new SwitchServiceBroker().refundTransaction(request);

				setTransactionResponseDetails(transactionResponse, pgOnlineTxnLog, request, refundResponse);
			} else {
				log.error("RestService | PGTransactionServiceImpl | processCapture |throwing exception :");
				throw new ServiceException(ActionErrorCode.ERROR_CODE_DUPLICATE_REFUND_ENTRY);
			}

		} catch(InvalidRequestException e) {
		  log.error("ERROR:: PGTransactionServiceImpl:: fork authCapture", e);
		  setTxnErrorResponse(transactionResponse, e.getMessage());
		} catch(ServiceException e) {
			log.error("RestService | PGTransactionServiceImpl | authCapture | ServiceException :", e);
			setTxnErrorResponse(transactionResponse, e.getMessage());
		} catch(Exception e) {
			log.error("RestService | PGTransactionServiceImpl | authCapture | Exception :", e);
			setTxnErrorResponse(transactionResponse, ActionErrorCode.ERROR_CODE_PG_SERVICE);
		}
		log.info("RestService | PGTransactionServiceImpl | authCapture | Exiting");
		return transactionResponse;

	}

	private void validateTxnAmount(TransactionRequest transactionRequest, PGAccount pgAccount)
			throws InvalidRequestException {
		if(transactionRequest.getTxnAmount() != null && transactionRequest.getTxnAmount() > pgAccount.getCurrentBalance()) {
			throw new InvalidRequestException(ActionErrorCode.ERROR_CODE_51);
		}
	}

	private void setTransactionResponseDetails(TransactionResponse transactionResponse, PGOnlineTxnLog pgOnlineTxnLog,
			RefundRequest request, RefundResponse refundResponse) {
		transactionResponse.setErrorCode(refundResponse.getErrorCode());
		transactionResponse.setErrorMessage(refundResponse.getErrorMessage());
		transactionResponse.setAuthId(refundResponse.getAuthId() != null ? refundResponse.getAuthId() : null);
		transactionResponse.setTxnRefNumber(refundResponse.getTxnRefNum() != null ? refundResponse.getTxnRefNum()
				: null);
		transactionResponse.setTxnDateTime(System.currentTimeMillis());
		transactionResponse.setCgRefNumber(refundResponse.getUpStreamTxnRefNum());
		transactionResponse.setTotalTxnAmount((refundResponse.getTotalTxnAmount().doubleValue())/Integer.parseInt("100"));
		transactionResponse.setMerchantCode(request.getMerchantId());
		transactionResponse.setMerchantCode(request.getMerchantName());
		logExit(pgOnlineTxnLog,
				TransactionStatus.COMPLETED,
				refundResponse.getErrorMessage(),
				(transactionResponse.getTxnRefNumber() == null ? "0"
						: transactionResponse.getTxnRefNumber()),
						refundResponse.getErrorMessage(),
						transactionResponse.getTxnRefNumber());
	}

	private void validateRefundAmount(TransactionRequest transactionRequest, PGTransaction pgTransaction,
			Long refundedAmount, RefundRequest request) throws ServiceException {
		if(null==refundedAmount){
		  
		  if((pgTransaction.getTxnTotalAmount()-transactionRequest.getTotalTxnAmount()) > 0) {
			  request.setRefundStatus(0);
		  } else {
			  request.setRefundStatus(1);
		  }
		  if(PGConstants.PG_SETTLEMENT_PROCESSING.equalsIgnoreCase(pgTransaction.getMerchantSettlementStatus())) {
			  logPgAccountHistory(PGConstants.PAYMENT_METHOD_CREDIT, pgTransaction, true);
		  }
		  
		} else {
		  request.setRefundStatus(((pgTransaction.getTxnTotalAmount()-refundedAmount)-transactionRequest.getTotalTxnAmount())>0?0:1);
		}
	}

	private void validateTotalTxnAndMerchantAmount(TransactionRequest transactionRequest, PGTransaction pgTransaction)
			throws ServiceException {
		if(null!=transactionRequest.getTotalTxnAmount()&&null!=transactionRequest.getMerchantAmount()){
			if(transactionRequest.getTotalTxnAmount()>pgTransaction.getTxnTotalAmount()){
				log.error("RestService | PGTransactionServiceImpl | processRefund |Invalid Partial Refund Amount");
				throw new ServiceException(ActionErrorCode.REFUND_AMOUNT_EXCEEDS);
			}
			transactionRequest.setTxnAmount(transactionRequest.getTxnAmount());
		    transactionRequest.setMerchantAmount(transactionRequest.getMerchantAmount());
		    transactionRequest.setTotalTxnAmount(transactionRequest.getTotalTxnAmount());
		    transactionRequest.setFeeAmount(transactionRequest.getFeeAmount());
		} else{
			transactionRequest.setTxnAmount(pgTransaction.getTxnAmount());
			transactionRequest.setMerchantAmount(pgTransaction.getTxnAmount());
			Long amount = (transactionRequest.getTotalTxnAmount() == null) ? pgTransaction.getTxnTotalAmount() : transactionRequest.getTotalTxnAmount();
			transactionRequest.setTotalTxnAmount(amount);
			transactionRequest.setFeeAmount(pgTransaction.getFeeAmount());
		}
	}
	/**
	 * @param txnState
	 * @param paymentDetails
	 * @param cardDetails
	 * @return
	 * @throws Exception
	 */
	private PGOnlineTxnLog logEntry(TransactionStatus txnState, TransactionRequest transactionRequest) {

		PGOnlineTxnLog pgOnlineTxnLog = new PGOnlineTxnLog();
		if(null != transactionRequest.getBillingData()) {
			pgOnlineTxnLog.setBillerAddress(transactionRequest.getBillingData().getAddress1());
			pgOnlineTxnLog.setBillerAddress2(transactionRequest.getBillingData().getAddress2());
			pgOnlineTxnLog.setBillerCity(transactionRequest.getBillingData().getCity());
			if(null != transactionRequest.getBillingData().getCountry()) {
				pgOnlineTxnLog.setBillerCountry(transactionRequest.getBillingData().getCountry());
			}
			pgOnlineTxnLog.setBillerEmail(transactionRequest.getBillingData().getEmail());
			pgOnlineTxnLog.setBillerState(transactionRequest.getBillingData().getState());
			pgOnlineTxnLog.setBillerZip(transactionRequest.getBillingData().getState());
		}
		pgOnlineTxnLog.setMerchantAmount(transactionRequest.getMerchantAmount());
		pgOnlineTxnLog.setMerchantId(transactionRequest.getMerchantId());

		pgOnlineTxnLog.setPanData(EncryptionUtil.encrypt(transactionRequest.getCardData().getCardNumber()));
		pgOnlineTxnLog.setPanMasked(StringUtils.getMaskedString(transactionRequest.getCardData().getCardNumber(), Integer.parseInt("5"), Integer.parseInt("4")));
		pgOnlineTxnLog.setCardHolderName(transactionRequest.getCardData().getCardHolderName());
		pgOnlineTxnLog.setPosTxnDate(null);// TODO-need to add
		pgOnlineTxnLog.setTxnState(txnState.name());
		pgOnlineTxnLog.setTxnTotalAmount(transactionRequest.getTotalTxnAmount());
		pgOnlineTxnLog.setFeeAmount(transactionRequest.getFeeAmount());
		pgOnlineTxnLog.setTxnType(transactionRequest.getTransactionType().name());
		pgOnlineTxnLog.setRequestIPPort(null);// TODO-need to add
		pgOnlineTxnLog.setOrderId(transactionRequest.getOrderId() == null ? "000000" : transactionRequest.getOrderId());
		pgOnlineTxnLog.setRequestDateTime(new Timestamp(System.currentTimeMillis()));
		pgOnlineTxnLog.setRegisterNumber(transactionRequest.getRegisterNumber());
		pgOnlineTxnLog.setInvoceNumber(transactionRequest.getInvoiceNumber() == null ? null
				: transactionRequest.getInvoiceNumber().toString());
		pgOnlineTxnLog.setRequestIPPort(transactionRequest.getIp_port());
		pgOnlineTxnLog.setTxnDescription(transactionRequest.getDescription());// Adding
		// description
		pgOnlineTxnLog.setCardAssciation(transactionRequest.getCardData().getCardType().value());// Adding
		// card
		// type
		pgOnlineTxnLog.setMerchantName(transactionRequest.getMerchantName());

		pgOnlineTxnLog.setPosEntryMode((transactionRequest.getEntryMode() == null) ? EntryModeEnum.UNSPECIFIED.value()
				: transactionRequest.getEntryMode().value());

		pgOnlineTxnLog.setAppMode((transactionRequest.getMode() == null) ? ProcessorConfig.DEMO
				: transactionRequest.getMode());
		if(null!=transactionRequest.getCardTokenData()){
			pgOnlineTxnLog.setPaymentProcessType(Integer.parseInt("2"));//PaymentProcessTypeEnum.T
		}
		pgOnlineTxnLog = onlineTxnLogDao.logRequest(pgOnlineTxnLog);
		return pgOnlineTxnLog;

	}

	/**
	 * @param pgOnlineTxnLog
	 * @param txnState
	 * @param reason
	 * @param pgTxnId
	 * @param processorResponse
	 * @param processTxnId
	 */
	private void logExit(PGOnlineTxnLog pgOnlineTxnLog,
			TransactionStatus txnState,
			String reason,
			String pgTxnId,
			String processorResponse,
			String processTxnId) {
		pgOnlineTxnLog.setPgTxnId(pgTxnId);
		pgOnlineTxnLog.setProcessorResponse(processorResponse);
		pgOnlineTxnLog.setProcessorTxnId(processTxnId);
		pgOnlineTxnLog.setResponseDateTime(new Timestamp(System.currentTimeMillis()));
		pgOnlineTxnLog.setTxnState(txnState.name());
		pgOnlineTxnLog.setTxnReason(reason);
		onlineTxnLogDao.logRequest(pgOnlineTxnLog);

	}

	/**
	 * <<Method to process split sale transaction >>
	 * 
	 * @param transactionRequest
	 * @return
	 */
	public Response processSplitSale(TransactionRequest transactionRequest) {

		log.info("RestService | PGTransactionServiceImpl | processAuthCapture | Entering");

		TransactionResponse transactionResponse = new TransactionResponse();
		PGOnlineTxnLog pgOnlineTxnLog = null;
		try {
			if(null == pgSplitTransactionService.validateSplitTransaction(transactionRequest)) {
				// Logging into Online txn log
				pgOnlineTxnLog = logEntry(TransactionStatus.INITATE, transactionRequest);
				PurchaseRequest request = new PurchaseRequest();
				request.setCardNum(transactionRequest.getCardData().getCardNumber());
				request.setExpDate(transactionRequest.getCardData().getExpDate());
				request.setCvv(transactionRequest.getCardData().getCvv());
				request.setMerchantId(transactionRequest.getMerchantId());
				request.setTerminalId(transactionRequest.getTerminalId());
				request.setInvoiceNumber(transactionRequest.getInvoiceNumber());
				request.setTrack2(transactionRequest.getCardData().getTrack2());
				request.setTotalTxnAmount(transactionRequest.getTotalTxnAmount());
				request.setTxnFee(StringUtils.getValidLongValue(transactionRequest.getFeeAmount()));
				request.setTxnAmount(transactionRequest.getMerchantAmount());
				request.setDescription(transactionRequest.getDescription());
				request.setPosEntryMode(transactionRequest.getPosEntryMode()+"0");
				request.setAcq_mode(EntryModeEnum.getValue(transactionRequest.getPosEntryMode()));// need to set proper value
				request.setBillingData(transactionRequest.getBillingData());
				request.setNationalPOSEntryMode(NationalPOSEntryModeEnum.valueOf(transactionRequest.getPosEntryMode() + "_DE58"));
				request.setCardType(transactionRequest.getCardData().getCardType().value());
				request.setAcq_channel(transactionRequest.getOriginChannel());
				request.setMode(transactionRequest.getMode());
				request.setProcessorMid(transactionRequest.getProcessorMid());
				request.setEmv(transactionRequest.getCardData().getEmv());
				
				PurchaseResponse purchaseResponse = new SwitchServiceBroker().purchaseTransaction(request);

				transactionResponse.setErrorCode(purchaseResponse.getErrorCode());
				transactionResponse.setErrorMessage(purchaseResponse.getErrorMessage());
				transactionResponse.setAuthId(purchaseResponse.getAuthId() != null ? purchaseResponse.getAuthId() : null);
				transactionResponse.setTxnRefNumber(purchaseResponse.getTxnRefNum() != null ? purchaseResponse.getTxnRefNum()
						: null);
				transactionResponse.setCgRefNumber(purchaseResponse.getUpStreamTxnRefNum());
				transactionResponse.setTxnDateTime(System.currentTimeMillis());

				pgSplitTransactionService.updateSplitTransactionLog(transactionRequest, transactionResponse);

				logExit(pgOnlineTxnLog,
						TransactionStatus.COMPLETED,
						purchaseResponse.getErrorMessage(),
						(transactionResponse.getTxnRefNumber() == null ? "0"
								: transactionResponse.getTxnRefNumber()),
								purchaseResponse.getErrorMessage(),
								transactionResponse.getTxnRefNumber());
			} else {
				throw new SplitTransactionException(ActionCode.ERROR_CODE_12);
			}
		} catch(ServiceException e) {
			log.error("RestService | PGTransactionServiceImpl | processSplitSale | ServiceException :", e);
			setTxnErrorResponse(transactionResponse, e.getMessage());
		} catch(SplitTransactionException e) {
			log.error("RestService | PGTransactionServiceImpl | processSplitSale | ServiceException :", e);
			setTxnErrorResponse(transactionResponse, e.getMessage());
		} catch(Exception e) {
			log.error("RestService | PGTransactionServiceImpl | processAuthCapture | Exception :", e);
			setTxnErrorResponse(transactionResponse, ActionErrorCode.ERROR_CODE_PG_SERVICE);
		}
		log.info("RestService | PGTransactionServiceImpl | processAuthCapture | Exiting");
		return transactionResponse;

	}

	/**
	 * <<Method to process split transaction rejection>>
	 * 
	 * @param transactionRequest
	 * @return
	 * @throws SplitTransactionException
	 */
	public Response processSplitReject(TransactionRequest transactionRequest) throws SplitTransactionException {
		Response splitRejectResponse = new Response();
		PGSplitTransaction pgSplitTransaction = splitTransactionDao.getPGSplitTransactionByMerchantIdAndPgRefTransactionIdAndSplitAmount(transactionRequest.getMerchantId(),
				transactionRequest.getSplitRefNumber(),
				transactionRequest.getTotalTxnAmount());

		if(pgSplitTransaction.getStatus().equals(Long.valueOf(PGConstants.STATUS_INPROCESS))) {
			PGTransaction txnToVoid = voidTransactionDao.findTransactionToReversalByMerchantIdAndPGTxnId(pgSplitTransaction.getMerchantId(),
					pgSplitTransaction.getPgRefTransactionId());
			pgSplitTransaction.setStatus(Long.valueOf((PGConstants.STATUS_FAILED.toString())));
			if(null != txnToVoid) {
				TransactionRequest voidRequest = new TransactionRequest();
				voidRequest.setTerminalId(txnToVoid.getTerminalId());
				voidRequest.setMerchantId(txnToVoid.getMerchantId());
				voidRequest.setCgRefNumber(txnToVoid.getIssuerTxnRefNum());
				voidRequest.setTransactionType(TransactionType.VOID);
				voidRequest.setTxnRefNumber(txnToVoid.getTransactionId());
				splitRejectResponse = processTransaction(voidRequest);
				if(splitRejectResponse.getErrorCode().equals(PGConstants.SUCCESS)) {
					TransactionResponse transactionResponse = new TransactionResponse();
					transactionResponse.setTxnRefNumber(txnToVoid.getTransactionId());
				} else {
					pgSplitTransaction.setStatus(Long.valueOf((PGConstants.STATUS_INPROCESS.toString())));
				}
			}

			splitTransactionDao.createOrUpdateTransaction(pgSplitTransaction);
		} else {
			splitRejectResponse.setErrorCode(ActionCode.ERROR_CODE_12);
			splitRejectResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_12));
		}
		return splitRejectResponse;
	}

	public void reverseSplitSaleIfExists(String transactionId, String merchantId) {
		PGSplitTransaction pgSplitTransaction = splitTransactionDao.getPGSplitTransactionByMerchantIdAndPgRefTransactionId(merchantId,
				transactionId);
		if(null != pgSplitTransaction && pgSplitTransaction.getStatus().equals(PGConstants.STATUS_SUCCESS)) {
			String splitTransactionId = pgSplitTransaction.getPgRefTransactionId();
			String splitTxnMerchantId = pgSplitTransaction.getMerchantId();
			PGTransaction pgTransaction = voidTransactionDao.findTransactionToReversalByMerchantIdAndPGTxnId(splitTxnMerchantId,
					splitTransactionId);
			if(null != pgTransaction) {
				TransactionRequest requestSplitReverse = new TransactionRequest();
				requestSplitReverse.setMerchantId(splitTxnMerchantId);
				requestSplitReverse.setTerminalId(pgTransaction.getTerminalId());
				requestSplitReverse.setTxnRefNumber(pgTransaction.getTransactionId());
				requestSplitReverse.setCgRefNumber(pgTransaction.getIssuerTxnRefNum());
				requestSplitReverse.setTransactionType(TransactionType.VOID);
				processVoid(requestSplitReverse);
				pgSplitTransaction.setStatus(Long.valueOf(PGConstants.STATUS_DELETED.toString()));
			}

		}

	}

	public Response processRefundVoid(TransactionRequest transactionRequest) {

		log.info("RestService | PGTransactionServiceImpl | processVoid | Entering");
		TransactionResponse transactionResponse = new TransactionResponse();
		PGOnlineTxnLog pgOnlineTxnLog = null;
		try {

			PGTransaction pgTransaction = refundTransactionDao.findRefundTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(transactionRequest.getTxnRefNumber(),
					transactionRequest.getCgRefNumber(),
					transactionRequest.getMerchantId(),
					transactionRequest.getTerminalId());
			if(null == pgTransaction) {
				log.error("RestService | PGTransactionServiceImpl | processVoid |throwing exception");
				throw new ServiceException(ActionErrorCode.ERROR_CODE_DUPLICATE_VOID_ENTRY);
			} else {
				setCardDataAndTransactionRequestData(transactionRequest, pgTransaction);

				// Logging into Online txn log
				pgOnlineTxnLog = logEntry(TransactionStatus.INITATE, transactionRequest);
				VoidRequest request = new VoidRequest();
				setVoidRequestData(transactionRequest, request);
				request.setPosEntryMode(transactionRequest.getPosEntryMode()+"0");
				request.setAcq_mode(EntryModeEnum.getValue(transactionRequest.getPosEntryMode()));// need to set proper value
				request.setCardType(transactionRequest.getCardData().getCardType().value());
				request.setAcq_channel(transactionRequest.getOriginChannel());
				request.setMode(transactionRequest.getMode());
				request.setProcessorMid(transactionRequest.getProcessorMid());
				request.setEmv(transactionRequest.getCardData().getEmv());
				
				VoidResponse voidResponse = new SwitchServiceBroker().voidRefundTransaction(request);

				transactionResponse.setErrorCode(voidResponse.getErrorCode());
				transactionResponse.setErrorMessage(voidResponse.getErrorMessage());
				transactionResponse.setAuthId(voidResponse.getAuthId() != null ? voidResponse.getAuthId() : null);
				transactionResponse.setTxnRefNumber(voidResponse.getTxnRefNum() != null ? voidResponse.getTxnRefNum() : null);
				transactionResponse.setTxnDateTime(System.currentTimeMillis());
				logExit(pgOnlineTxnLog,
						TransactionStatus.COMPLETED,
						voidResponse.getErrorMessage(),
						(transactionResponse.getTxnRefNumber() == null ? "0"
								: transactionResponse.getTxnRefNumber()),
								voidResponse.getErrorMessage(),
								transactionResponse.getTxnRefNumber());
			}

		} catch(ServiceException e) {
			log.error("RestService | PGTransactionServiceImpl | processVoid | ServiceException :", e);
			setTxnErrorResponse(transactionResponse, e.getMessage());
		} catch(Exception e) {
			log.error("RestService | PGTransactionServiceImpl | processVoid | Exception :", e);
			setTxnErrorResponse(transactionResponse, ActionErrorCode.ERROR_CODE_PG_SERVICE);
		}
		log.info("RestService | PGTransactionServiceImpl | processVoid | Exiting");
		return transactionResponse;

	}
	
	public Response processLoadFund(TransactionRequest transactionRequest, PGMerchant pgMerchant) {

		log.info("RestService | PGTransactionServiceImpl | processLoadFund | Entering");
		TransactionDTOResponse transactionResponse = new TransactionDTOResponse();
		try {
			if(CommonUtil.isNullAndEmpty(transactionRequest.getMerchantId())
				       || CommonUtil.isNullAndEmpty(transactionRequest.getTerminalId())) {
				throw new InvalidRequestException(ChatakPayErrorCode.TXN_0007.name(), ChatakPayErrorCode.TXN_0007.value());
		    } else if(null == transactionRequest.getTransactionType()) {
		    	throw new InvalidRequestException(ChatakPayErrorCode.TXN_0001.name(), ChatakPayErrorCode.TXN_0001.value());
		    }
			
			String property = Properties.getProperty("chatak-issuance.adjustment.load.card");
			
		    if(null != pgMerchant) {
		    	TransactionDTO transactionDTO = new TransactionDTO();
				transactionDTO.setAgentAccountNumber(pgMerchant.getAgentAccountNumber());
				transactionDTO.setAgentANI(pgMerchant.getAgentANI());
				transactionDTO.setTxnAmount(transactionRequest.getTotalTxnAmount()/Double.parseDouble("100"));
				transactionDTO.setTxnDescription("loading account");
				
				if(null != transactionRequest.getCardData() && !StringUtil.isNullAndEmpty(transactionRequest.getCardData().getCardNumber())) {
					transactionDTO.setCardNumber(transactionRequest.getCardData().getCardNumber());;
				} else if(!StringUtil.isNullAndEmpty(transactionRequest.getMobileNumber())) {
					transactionDTO.setCustomerPhoneNumber(transactionRequest.getMobileNumber());
				} else if(!StringUtil.isNullAndEmpty(transactionRequest.getAccountNumber())) {

					property = Properties.getProperty("chatak-issuance.adjustment.load.account");

					transactionDTO.setCustomerAccountNumber(transactionRequest.getAccountNumber());
				}				
				
				transactionResponse = (TransactionDTOResponse) JsonUtil.sendToIssuance(TransactionDTOResponse.class,
								transactionDTO, property);
				if(transactionResponse.getTransactionDTO() != null && transactionResponse.getTransactionDTO().size() > 0) {
					validateTransactionDTO(transactionResponse);
				}
				
		    } else {
		    	log.info("Invalid Merchant: "+transactionRequest.getMerchantId());
		        throw new InvalidRequestException(ChatakPayErrorCode.TXN_0007.name(), ChatakPayErrorCode.TXN_0007.value());
		    }
			
		} catch(InvalidRequestException e) {
		  log.error("RestService | PGTransactionServiceImpl | processLoadFund | Exception :",e);
			Response response = new Response();
			response.setErrorCode(e.getErrorCode());
			response.setErrorMessage(e.getMessage());
			response.setTxnDateTime(System.currentTimeMillis());
			return response;
		} catch (Exception e) {
			transactionResponse.setErrorCode(ActionErrorCode.ERROR_CODE_PG_SERVICE);
			transactionResponse.setErrorMessage(ActionErrorCode.getInstance()
					.getMessage(ActionErrorCode.ERROR_CODE_PG_SERVICE));
			transactionResponse.setTxnDateTime(System.currentTimeMillis());
			log.error("RestService | PGTransactionServiceImpl | processLoadFund | Exception :",e);
		}
		log.info("RestService | PGTransactionServiceImpl | processLoadFund | Exiting");
		return transactionResponse;

	}

	private void validateTransactionDTO(TransactionDTOResponse transactionResponse) {
		// Re map
		log.info("Remapping response ");
		TransactionDTO transResponse = transactionResponse.getTransactionDTO().get(0);
		if(transResponse != null) {
			log.info("Remapping response :: trans ref number: " + transResponse.getTxnRefNumber());
			transactionResponse.setTxnRefNumber(transResponse.getTxnRefNumber());
		}
	}
	
	/**
	   * <<Method to add history for account>>
	   * 
	   * @param merchantId
	   * @throws ServiceException
	   */
	  public void logPgAccountHistory(String paymentMethod, PGTransaction pgTransaction, boolean updateBalance) throws ServiceException {
	    try {
			PGAccount updatedAccount = accountDao.getPgAccount(pgTransaction.getMerchantId());
			if(null != updatedAccount) {
			  validateForUpdatedPGAccount(paymentMethod, pgTransaction, updateBalance, updatedAccount);
			}
		} catch (DataAccessException e) {
			log.error("RestService :: PGTransactionServiceImpl :: logPgAccountHistory :: Exception : ",e);
		}
	  }

      private void validateForUpdatedPGAccount(String paymentMethod, PGTransaction pgTransaction,
          boolean updateBalance, PGAccount updatedAccount) {
        PGAccountHistory pgAccountHistory = new PGAccountHistory();
        pgAccountHistory.setEntityId(updatedAccount.getEntityId());
        pgAccountHistory.setEntityType(updatedAccount.getEntityType());
        pgAccountHistory.setAccountDesc(updatedAccount.getAccountDesc());
        pgAccountHistory.setCategory(updatedAccount.getCategory());
        Long curBal = updatedAccount.getCurrentBalance();
        Long availBal = updatedAccount.getAvailableBalance();
        if(updateBalance) {
          if(PGConstants.PAYMENT_METHOD_CREDIT.equals(paymentMethod)) {
        	  curBal = (updatedAccount.getCurrentBalance() + (pgTransaction.getTxnAmount() + pgTransaction.getFeeAmount()));
        	  availBal = (updatedAccount.getAvailableBalance() + (pgTransaction.getTxnAmount() + pgTransaction.getFeeAmount()));
          } else {
        	  curBal = (updatedAccount.getCurrentBalance() - (pgTransaction.getTxnAmount() + pgTransaction.getFeeAmount()));
        	  availBal = (updatedAccount.getAvailableBalance() - (pgTransaction.getTxnAmount() + pgTransaction.getFeeAmount()));
          }
        }
        pgAccountHistory.setCurrentBalance(curBal);
        pgAccountHistory.setAvailableBalance(availBal);
     
        pgAccountHistory.setCurrency(updatedAccount.getCurrency());
        pgAccountHistory.setAutoPaymentLimit(updatedAccount.getAutoPaymentLimit());
        pgAccountHistory.setAutoPaymentMethod(updatedAccount.getAutoPaymentMethod());
        pgAccountHistory.setAutoTransferDay(updatedAccount.getAutoTransferDay());
        pgAccountHistory.setStatus(updatedAccount.getStatus());
        pgAccountHistory.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        pgAccountHistory.setAccountNum(updatedAccount.getAccountNum());
        pgAccountHistory.setFeeBalance(updatedAccount.getFeeBalance());
        pgAccountHistory.setTransactionId(pgTransaction.getTransactionId());
        pgAccountHistory.setPaymentMethod(paymentMethod);
        accountHistoryDao.createOrSave(pgAccountHistory);
      }

	  public boolean updateSettlementStatus(String merchantId,
              String terminalId, String txnId, String txnType,
              String status, String comments, long feeAmount) throws Exception {
		  try {
			  log.info("Entering :: PGTransactionServiceImpl :: updateSettlementStatus method");
			  PGTransaction pgTransaction = transactionDao.getTransactionOnTxnIdAndTxnType(merchantId, terminalId, txnId, txnType);
			  if(null != pgTransaction) {
				  PGOnlineTxnLog pgOnlineTxnLog = onlineTxnLogDao.getTransactionOnPgTxnIdAndMerchantId(pgTransaction.getTransactionId(), pgTransaction.getMerchantId());
				  if(status.equals(PGConstants.PG_SETTLEMENT_EXECUTED)) {
					  Long chatakFeeAmountTotal;
					  List<Object> objectResult = getProcessingFee(PGUtils.getCCType(EncryptionUtil.decrypt(pgTransaction.getPan())),
							  pgTransaction.getMerchantId(), pgTransaction.getTxnTotalAmount());
					  chatakFeeAmountTotal = (Long) objectResult.get(1);

					  validatePGCurrencyCode(status, comments, feeAmount, pgTransaction, pgOnlineTxnLog, chatakFeeAmountTotal);
					  
					  Date date = new Date();
                      String batchId = new SimpleDateFormat(Constants.BATCH_ID_DATE_FORMAT).format(date);
                      pgTransaction.setBatchId(batchId + pgTransaction.getMerchantId());
                      pgTransaction.setBatchDate(new Timestamp(System.currentTimeMillis()));
                      
					  logAccountHistory(pgTransaction.getMerchantId(), PGConstants.PAYMENT_METHOD_CREDIT, pgTransaction.getTransactionId());
				  }
				  pgTransaction.setMerchantSettlementStatus(status);
				  pgTransaction.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
				  voidTransactionDao.createTransaction(pgTransaction);
				  log.info("Exiting :: PGTransactionServiceImpl :: updateSettlementStatus method");
				  return true;
			  } else {
				  return false;
			  }
		  } catch(Exception e) {
			  log.error("ERROR :: PGTransactionServiceImpl :: updateSettlementStatus method", e);
			  throw new ChatakPayException(e.getMessage());
		  }
	  }

	private void validatePGCurrencyCode(String status, String comments, long feeAmount, PGTransaction pgTransaction,
			PGOnlineTxnLog pgOnlineTxnLog, Long chatakFeeAmountTotal) {
		Long totalFeeAmount = pgTransaction.getTxnTotalAmount() - pgTransaction.getTxnAmount();
		Long merchantFeeAmount = 0l;

		  if(totalFeeAmount > chatakFeeAmountTotal) {
			  merchantFeeAmount = totalFeeAmount - chatakFeeAmountTotal;
		  }
		  updateAccountCCTransactions(pgTransaction.getTransactionId(), pgTransaction.getTransactionType(), status);
		  String descriptionTemplate = messageSource.getMessage("chatak-pay.sale.description.template", null, LocaleContextHolder.getLocale());
		  if(null != pgOnlineTxnLog) {
			  PGCurrencyCode pGCurrencyCode = currencyCodeRepository.findByCurrencyCodeNumeric(pgTransaction.getTxnCurrencyCode());
			  descriptionTemplate = MessageFormat.format(descriptionTemplate,
					  pGCurrencyCode.getCurrencyCodeAlpha() + " " + StringUtils.amountToString(pgOnlineTxnLog.getMerchantAmount()),
					  pGCurrencyCode.getCurrencyCodeAlpha() + " " + StringUtils.amountToString(feeAmount));
		  }

		  pgTransaction.setMerchantFeeAmount(merchantFeeAmount);
		  pgTransaction.setTxnDescription(descriptionTemplate);
		  pgTransaction.setReason(comments);
		  if(ProcessorType.LITLE.value().equals(pgTransaction.getProcessor())) {
			  pgTransaction.setEftStatus(PGConstants.LITLE_EXECUTED);
		  }
	}
	  
	  public List<Object> getProcessingFee(String cardType, String merchantCode, Long txnTotalAmount) throws DataAccessException, Exception {
		  log.info("Entering :: PGTransactionServiceImpl :: getProcessingFee method ");
		  List<Object> results = new ArrayList<Object>(Integer.parseInt("2"));
		  List<ProcessingFee> calculatedProcessingFeeList = new ArrayList<ProcessingFee>(0);
		  Double calculatedProcessingFee = null;
		  Long chatakFeeAmountTotal = 0l;
		  List<PGAcquirerFeeValue> acquirerFeeValueList = feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(merchantCode, cardType);
		  if(CommonUtil.isListNotNullAndEmpty(acquirerFeeValueList)) {

			  log.info("PGTransactionServiceImpl :: getProcessingFee method :: Applying this merchant fee code ");
			  ProcessingFee processingFee = null;
			  for(PGAcquirerFeeValue acquirerFeeValue : acquirerFeeValueList) {
				  calculatedProcessingFee = 0.00;
				  processingFee = getProcessingFeeItem(acquirerFeeValue, txnTotalAmount, calculatedProcessingFee);
				  chatakFeeAmountTotal = chatakFeeAmountTotal + CommonUtil.getLongAmount(processingFee.getChatakProcessingFee());
				  calculatedProcessingFeeList.add(processingFee);
			  }
		  }
		  log.info("Exiting :: PGTransactionServiceImpl :: getProcessingFee method ");
		  results.add(calculatedProcessingFeeList);
		  results.add(chatakFeeAmountTotal);
		  return results;
	  }
	  
	  private ProcessingFee getProcessingFeeItem(PGAcquirerFeeValue acquirerFeeValue, Long txnTotalAmount, Double calculatedProcessingFee) {
		  log.info("Entering :: PGTransactionServiceImpl :: getProcessingFeeItem method ");
		  ProcessingFee processingFee = new ProcessingFee();
		  Double flatFee = CommonUtil.getDoubleAmountNotNull(acquirerFeeValue.getFlatFee());
		  Double percentageFee = acquirerFeeValue.getFeePercentageOnly();
		  percentageFee = txnTotalAmount * (CommonUtil.getDoubleAmountNotNull(percentageFee));
		  calculatedProcessingFee = (CommonUtil.getDoubleAmountNotNull(calculatedProcessingFee + percentageFee)) + flatFee;
		  processingFee.setAccountNumber(acquirerFeeValue.getAccountNumber());
		  processingFee.setChatakProcessingFee(calculatedProcessingFee);
		  log.info("Exiting :: PGTransactionServiceImpl :: getProcessingFeeItem method ");
		  return processingFee;
	  }
	  
	  public void updateAccountCCTransactions(String pgTransactionId, String txnType, String newStatus) {
		  PGAccount account = null;
		  Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		  log.info("Entering :: PGTransactionServiceImpl :: updateAccountCCTransactions method ");
		  List<PGAccountTransactions> accountTxns = accountTransactionsDao.getAccountTransactionsOnTransactionIdAndTransactionType(pgTransactionId, txnType);
		  for(PGAccountTransactions accTxn : accountTxns) {

			  if(PGConstants.PG_SETTLEMENT_REJECTED.equals(newStatus)) {
				  accTxn.setStatus(PGConstants.PG_SETTLEMENT_REJECTED);
			  } else {

				  switch(accTxn.getTransactionCode()) {
				  case AccountTransactionCode.CC_AMOUNT_CREDIT:
					  // updating pg account debting refund amount
					  account=accountDao.getPgAccount(accTxn.getMerchantCode());
                      setPGAccountDetails(currentTime, accTxn, account);
					  break;
				  case AccountTransactionCode.CC_FEE_DEBIT:
					  account=accountDao.getPgAccount(accTxn.getMerchantCode());
					  account.setAvailableBalance(account.getAvailableBalance() - accTxn.getDebit());
					  account.setCurrentBalance(account.getCurrentBalance() - accTxn.getDebit());
                      setPGAccDetails(account, currentTime, accTxn);
					  break;
				  case AccountTransactionCode.CC_MERCHANT_FEE_CREDIT:
                      validateForMerchantFeeCredit(currentTime, accTxn);
					  break;
				  case AccountTransactionCode.CC_ACQUIRER_FEE_CREDIT:  //ReBrand
					  log.info("PGTransactionServiceImpl :: updateAccountCCTransactions method fetching transactions by PG TRANS ID: " + accTxn.getPgTransactionId());
					  List<PGTransaction> transactions = transactionRepository.findByTransactionId(accTxn.getPgTransactionId());
					  PGTransaction transaction = transactions.get(0);

                      validateForEntityTypeAndCurrencyAndStatus(currentTime, accTxn, transaction);
					  break;
				  default:
				  }
			  }
			  accTxn.setProcessedTime(new Timestamp(System.currentTimeMillis()));
			  accountTransactionsDao.createOrUpdate(accTxn);
		  }

		  log.info("Exiting :: PGTransactionServiceImpl :: updateAccountCCTransactions method ");
	  }

      private void validateForMerchantFeeCredit(Timestamp currentTime, PGAccountTransactions accTxn) {
        PGAccount account;
        account=accountDao.getPgAccount(merchantDao.getParentMerchantCode(accTxn.getMerchantCode()));
        if(null==account){
          account=accountDao.getPgAccount(accTxn.getMerchantCode()); }
                  setPGAccountDetails(currentTime, accTxn, account);
      }

      private void setPGAccDetails(PGAccount account, Timestamp currentTime,
          PGAccountTransactions accTxn) {
        accountDao.savePGAccount(account);
        accTxn.setCurrentBalance(account.getCurrentBalance());
        accTxn.setProcessedTime(currentTime);
        accTxn.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
      }

      private void validateForEntityTypeAndCurrencyAndStatus(Timestamp currentTime,
          PGAccountTransactions accTxn, PGTransaction transaction) {
        PGAccount account;
        log.info("PGTransactionServiceImpl :: updateAccountCCTransactions method :: fetching currencyConfig for fee credit with numeric code: " + transaction.getTxnCurrencyCode());
        PGCurrencyConfig currencyConfig = currencyConfigDao.getcurrencyCodeAlpha(transaction.getTxnCurrencyCode());
        log.info("PGTransactionServiceImpl :: updateAccountCCTransactions method :: currency code alpha for the above: " + currencyConfig.getCurrencyCodeAlpha());
        account = accountRepository.findByEntityTypeAndCurrencyAndStatus(PGConstants.DEFAULT_ENTITY_TYPE, currencyConfig.getCurrencyCodeAlpha(), PGConstants.S_STATUS_ACTIVE);
  
        setPGAccountDetails(currentTime, accTxn, account);
      }

      private void setPGAccountDetails(Timestamp currentTime, PGAccountTransactions accTxn,
          PGAccount account) {
        account.setAvailableBalance(account.getAvailableBalance() + accTxn.getCredit());
        account.setCurrentBalance(account.getCurrentBalance() + accTxn.getCredit());
        setPGAccDetails(account, currentTime, accTxn);
      }
	  
	  public void logAccountHistory(String merchantId, String paymentMethod, String transactionId) throws Exception {
		  log.info("Entering :: PGTransactionServiceImpl :: logAccountHistory method");
		  
		  PGAccount updatedAccount = accountDao.getPgAccount(merchantId);
		  if(null != updatedAccount) {
			  PGAccountHistory pgAccountHistory = new PGAccountHistory();
			  pgAccountHistory.setEntityId(updatedAccount.getEntityId());
			  pgAccountHistory.setAccountDesc(updatedAccount.getAccountDesc());
			  pgAccountHistory.setEntityType(updatedAccount.getEntityType());
			  pgAccountHistory.setTransactionId(transactionId);
			  pgAccountHistory.setCategory(updatedAccount.getCategory());
			  pgAccountHistory.setAvailableBalance(updatedAccount.getAvailableBalance());
			  pgAccountHistory.setCurrentBalance(updatedAccount.getCurrentBalance());
			  pgAccountHistory.setTransactionId(transactionId);
			  pgAccountHistory.setCurrency(updatedAccount.getCurrency());
			  pgAccountHistory.setAutoPaymentMethod(updatedAccount.getAutoPaymentMethod());
			  pgAccountHistory.setAutoPaymentLimit(updatedAccount.getAutoPaymentLimit());
			  pgAccountHistory.setTransactionId(transactionId);
			  pgAccountHistory.setAutoTransferDay(updatedAccount.getAutoTransferDay());
			  pgAccountHistory.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
			  pgAccountHistory.setStatus(updatedAccount.getStatus());
			  pgAccountHistory.setTransactionId(transactionId);
			  pgAccountHistory.setAccountNum(updatedAccount.getAccountNum());
			  pgAccountHistory.setPaymentMethod(paymentMethod);
			  pgAccountHistory.setFeeBalance(updatedAccount.getFeeBalance());
			  accountHistoryDao.createOrSave(pgAccountHistory);
		  }
		  log.info("Exiting :: PGTransactionServiceImpl :: logAccountHistory method");
	  }

  public Response processBalanceEnquiry(TransactionRequest transactionRequest) {
    log.info("Entering :: PGTransactionServiceImpl :: processBalanceEnquiry");
    TransactionResponse transactionResponse = new TransactionResponse();

    try {
      Request balanceRequest = new Request();
      balanceRequest.setCardNum(transactionRequest.getCardData().getCardNumber());
      balanceRequest.setCvv(transactionRequest.getCardData().getCvv());
      balanceRequest.setExpDate(transactionRequest.getCardData().getExpDate());
      balanceRequest.setMerchantId(transactionRequest.getMerchantId());
      balanceRequest.setTerminalId(transactionRequest.getTerminalId());
      PGMerchant merchantData = merchantUpdateDao.getMerchant(transactionRequest.getMerchantId());
      PGCurrencyConfig currencyDetails = currencyConfigRepository.findByCurrencyCodeAlpha(merchantData.getLocalCurrency());
      balanceRequest.setCurrencyCode(currencyDetails.getCurrencyCodeNumeric());
      getMerchantDetails(merchantData, balanceRequest);
      
      balanceRequest.setUid(transactionRequest.getCardData().getUid());
      balanceRequest.setPosEntryMode(transactionRequest.getPosEntryMode() + "0");
      balanceRequest.setCardHolderEmail(transactionRequest.getCardData().getCardHolderName());
      balanceRequest.setInvoiceNumber(transactionRequest.getInvoiceNumber());
      balanceRequest.setUserName(transactionRequest.getUserName());
      balanceRequest.setTimeZoneOffset(transactionRequest.getTimeZoneOffset());
      balanceRequest.setTimeZoneRegion(transactionRequest.getTimeZoneRegion());

      BalanceEnquiryResponse balanceResponse = new SwitchServiceBroker().balanceEnquiry(balanceRequest);
      transactionResponse.setErrorCode(balanceResponse.getErrorCode());
      transactionResponse.setErrorMessage(balanceResponse.getErrorMessage());
      transactionResponse.setCurrency(merchantData.getLocalCurrency());
      
      transactionResponse.setMerchantId(balanceResponse.getMerchantId());
      transactionResponse.setTerminalId(balanceResponse.getTerminalId());
      transactionResponse.setTxnId(balanceResponse.getTxnId());
      transactionResponse.setProcTxnId(balanceResponse.getProcTxnId());
      transactionResponse.setTxnDateTime(System.currentTimeMillis());
      if(balanceResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_00)) {
        transactionResponse.setCustomerBalance(StringUtils.getAmount(balanceResponse.getBalance()));
      }
    } catch (ServiceException e) {
      log.error("Error :: PGTransactionServiceImpl :: processBalanceEnquiry ServiceException", e);
      setTxnErrorResponse(transactionResponse, e.getMessage());
    } catch (Exception e) {
      log.error("Error :: PGTransactionServiceImpl :: processBalanceEnquiry Exception", e);
      setTxnErrorResponse(transactionResponse, e.getMessage());
    }
    log.info("Exiting :: PGTransactionServiceImpl :: processBalanceEnquiry");
    return transactionResponse;
  }

  private void setTxnErrorResponse(TransactionResponse transactionResponse, String errorCode) {
    transactionResponse.setErrorCode(errorCode);
    transactionResponse.setErrorMessage(ActionErrorCode.getInstance().getMessage(errorCode));
    transactionResponse.setTxnDateTime(System.currentTimeMillis());
  }

  private void getMerchantDetails(PGMerchant pgMerchant, Request request) {
    request.setBusinessName(pgMerchant.getBusinessName().substring(0,
        (pgMerchant.getBusinessName().length() > Constants.TWENTY_THREE ? Constants.TWENTY_THREE
            : pgMerchant.getBusinessName().length())));
    request.setCity(
        pgMerchant.getCity().substring(0, (pgMerchant.getCity().length() > Constants.THIRTEEN
            ? Constants.THIRTEEN : pgMerchant.getCity().length())));
  }

  private Response getErrorResponse(String errorCode) {
    Response response = new Response();
    response.setErrorCode(errorCode);
    response.setErrorMessage(messageSource.getMessage(errorCode, null, LocaleContextHolder.getLocale()));
    response.setTxnDateTime(System.currentTimeMillis());
    return response;
  }
}
