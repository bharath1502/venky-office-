/**
 * 
 */
package com.chatak.pay.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.pay.constants.ChatakPayErrorCode;
import com.chatak.pay.controller.model.CardData;
import com.chatak.pay.controller.model.LoyaltyResponse;
import com.chatak.pay.controller.model.Response;
import com.chatak.pay.controller.model.SessionKeyRequest;
import com.chatak.pay.controller.model.SessionKeyResponse;
import com.chatak.pay.controller.model.TmkDataRequest;
import com.chatak.pay.controller.model.TmkDataResponse;
import com.chatak.pay.controller.model.TransactionHistoryResponse;
import com.chatak.pay.controller.model.TransactionRequest;
import com.chatak.pay.controller.model.TransactionResponse;
import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.exception.InvalidRequestException;
import com.chatak.pay.exception.SplitTransactionException;
import com.chatak.pay.model.TransactionDTO;
import com.chatak.pay.model.TransactionDTOResponse;
import com.chatak.pay.service.AsyncService;
import com.chatak.pay.service.LoyaltyService;
import com.chatak.pay.service.PGSplitTransactionService;
import com.chatak.pay.service.PGTransactionService;
import com.chatak.pay.util.JsonUtil;
import com.chatak.pay.util.StringUtil;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AccountFeeLogDao;
import com.chatak.pg.acq.dao.AccountHistoryDao;
import com.chatak.pg.acq.dao.AccountTransactionsDao;
import com.chatak.pg.acq.dao.BatchDao;
import com.chatak.pg.acq.dao.CardProgramDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.FeeProgramDao;
import com.chatak.pg.acq.dao.IsoServiceDao;
import com.chatak.pg.acq.dao.MerchantCardProgramMapDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.OnlineTxnLogDao;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.SplitTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.CardProgram;
import com.chatak.pg.acq.dao.model.MPosSessionKey;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountHistory;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;
import com.chatak.pg.acq.dao.model.PGBatch;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGOnlineTxnLog;
import com.chatak.pg.acq.dao.model.PGSplitTransaction;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.CurrencyCodeRepository;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.pg.acq.dao.repository.MPosSessionKeyRepository;
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
import com.chatak.pg.constants.ActionCode;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.NationalPOSEntryModeEnum;
import com.chatak.pg.enums.TransactionStatus;
import com.chatak.pg.enums.TransactionType;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.model.ProcessingFee;
import com.chatak.pg.model.TransactionHistoryRequest;
import com.chatak.pg.user.bean.PanRangeRequest;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.user.bean.TransactionHistory;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.MDCLoggerUtil;
import com.chatak.pg.util.PGUtils;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.StringUtils;
import com.chatak.switches.sb.SwitchServiceBroker;
import com.chatak.switches.sb.exception.ChatakInvalidTransactionException;
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

	private static Logger log = LogManager.getLogger(PGTransactionServiceImpl.class);
	
	private static ObjectMapper mapper=new ObjectMapper();

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private OnlineTxnLogDao onlineTxnLogDao;

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
	
	@Autowired
	private CardProgramDao cardProgramDao;
	
	@Autowired
	private ProgramManagerDao programManagerDao;
	
	@Autowired
	private BatchDao batchDao;
	
	@Autowired
	MerchantCardProgramMapDao merchantCardProgramMapDao;
	
	@Autowired
	IsoServiceDao isoServiceDao;
	
	@Autowired
	private AsyncService asyncService;
	
	@Autowired
	private TransactionDao transactionDao;
	
	@Autowired
	private MPosSessionKeyRepository mPosSessionKeyRepository;
	
	@Autowired
	private LoyaltyService loyaltyService;

	@Transactional
	public Response processTransaction(TransactionRequest transactionRequest, PGMerchant merchant) throws ChatakInvalidTransactionException {
		MDCLoggerUtil.setMDCLoggerParamsAdmin(transactionRequest.getInvoiceNumber());
		log.info("Entering :: processTransaction :: TxnType :  " + transactionRequest.getTransactionType());
		try {
		  
			SpringDAOBeanFactory.setAppContext(appContext);
			if(null != transactionRequest.getTransactionType()) {
				switch(transactionRequest.getTransactionType()) {
				case AUTH:
					return processAuth(transactionRequest);
				case CAPTURE:
					return processCapture(transactionRequest);
				case SALE:
					return processAuthCapture(transactionRequest, merchant);// SALE/Purchase
				case REFUND:
					return processRefund(transactionRequest);
				case VOID:
					return processVoid(transactionRequest);
				case SPLIT_ACCEPT:
					return processSplitSale(transactionRequest, merchant);
				case SPLIT_REJECT:
					return processSplitReject(transactionRequest, merchant);
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
				log.error("invalid transactiontype:: processCardPaymevirtual-terminal-salent method");
				return getErrorResponse(ChatakPayErrorCode.TXN_0001.name());
			}
			MDCLoggerUtil.clearMDCLoggerParams();
			return null;
		} catch(Exception e) {
			log.error("ERROR:: processCardPayment method", e);
			return getErrorResponse(ChatakPayErrorCode.TXN_0999.name());
		}
	}

	public Response processLoadFundTransaction(TransactionRequest transactionRequest, PGMerchant merchant) {
		try {
			SpringDAOBeanFactory.setAppContext(appContext);
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
	public Response processAuthCapture(TransactionRequest transactionRequest, PGMerchant pgMerchant) {

		log.info("RestService | PGTransactionServiceImpl | processAuthCapture | Entering");
		TransactionResponse transactionResponse = new TransactionResponse();
		PGOnlineTxnLog pgOnlineTxnLog = null;
		CardProgram cardprogram=null;
		try {
			PurchaseRequest request = new PurchaseRequest();
			Long feeAmount = 0l;
			
			// Logging into Online txn log
			pgOnlineTxnLog = logEntry(TransactionStatus.INITATE, transactionRequest);
			// PERF >> Replaced with card program id
			if (!transactionRequest.getEntryMode().equals(EntryModeEnum.ACCOUNT_PAY)) {
				
				List<PanRangeRequest> panRangesList = transactionDao.getPgPanRanges(transactionRequest.getMerchantCode());
				Long panId = cardRangeValidation( panRangesList, transactionRequest, request);
				if(panId == 0l) {
					transactionResponse.setErrorCode(ChatakPayErrorCode.TXN_0115.name());
					transactionResponse.setErrorMessage(ChatakPayErrorCode.TXN_0115.value());
					return transactionResponse;
				}
				List<PGAcquirerFeeValue> feeValues = feeProgramDao
						.getAcquirerFeeValueByCardProgramId(panId);
				if (StringUtil.isListNullNEmpty(feeValues)) {
					transactionResponse.setErrorCode(ChatakPayErrorCode.TXN_0116.name());
					transactionResponse.setErrorMessage(ChatakPayErrorCode.TXN_0116.value());
					return transactionResponse;
				}

				Double totalTxnAmount = StringUtil.getLong(transactionRequest.getTotalTxnAmount()) / 100d;
				Double percentage = StringUtil.getDouble(feeValues.get(0).getFeePercentageOnly());
				feeAmount = PGUtils.calculateAmountByPercentage(totalTxnAmount, percentage);
				feeAmount = feeAmount + feeValues.get(0).getFlatFee();

				if (feeAmount.compareTo(transactionRequest.getTotalTxnAmount()) > 0) {
					transactionResponse.setErrorCode(ChatakPayErrorCode.TXN_0117.name());
					transactionResponse.setErrorMessage(ChatakPayErrorCode.TXN_0117.value());
					return transactionResponse;
				}
				request.setTxnFee(feeAmount);
				request.setTxnAmount(transactionRequest.getTotalTxnAmount() - feeAmount);
			} else {
				request.setTxnAmount(transactionRequest.getTotalTxnAmount());
				request.setAccountNumber(transactionRequest.getAccountNumber());
			}
			request.setEntryMode(transactionRequest.getEntryMode());
			request.setCardNum(transactionRequest.getCardData().getCardNumber());
			request.setExpDate(transactionRequest.getCardData().getExpDate());
			request.setCvv(transactionRequest.getCardData().getCvv());
			request.setMerchantCode(transactionRequest.getMerchantCode());
			request.setMerchantId(pgMerchant.getId());
			request.setTerminalId(transactionRequest.getTerminalId());
			request.setInvoiceNumber(transactionRequest.getInvoiceNumber());
			request.setTrack2(transactionRequest.getCardData().getTrack2());
			request.setTotalTxnAmount(transactionRequest.getTotalTxnAmount());
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
			request.setCardHolderEmail(transactionRequest.getCardData().getCardHolderEmail());
			request.setEmv(transactionRequest.getCardData().getEmv());
			request.setQrCode(transactionRequest.getQrCode());
			request.setCurrencyCode(transactionRequest.getCurrencyCode());
			request.setUserName(transactionRequest.getUserName());
			request.setTimeZoneOffset(transactionRequest.getTimeZoneOffset());
			request.setTimeZoneRegion(transactionRequest.getTimeZoneRegion());
            getMerchantDetails(pgMerchant, request);
			request.setUid(transactionRequest.getCardData().getUid());
			if (!transactionRequest.getEntryMode().equals(EntryModeEnum.ACCOUNT_PAY)) {
				getMerchantBatchId(request, cardprogram, pgMerchant);
			}
			// Hitting redeemLoyaltyTxn API
			if (transactionRequest.getCheckBoxRedeemPoint().equals(PGConstants.TRUE)) {
				LoyaltyResponse loyaltyResponse = loyaltyService.invokeRedeemLoyaltyTxn(transactionRequest, request);
				log.trace(" Redeem Loyalty LoyaltyResponse : " + loyaltyResponse);
				request.setRedeemTxnAmount(loyaltyResponse.getDeductionAmt());
			}
			PurchaseResponse purchaseResponse = new SwitchServiceBroker().purchaseTransaction(request, pgMerchant);

			transactionResponse.setErrorCode(purchaseResponse.getErrorCode());
			transactionResponse.setErrorMessage(purchaseResponse.getErrorMessage());
			transactionResponse.setTotalAmount(purchaseResponse.getTotalAmount());
			
			if(purchaseResponse.getTotalAmount() != null) {
				transactionResponse.setTotalTxnAmount((purchaseResponse.getTotalAmount().doubleValue())/Constants.HUNDRED);
			}
			
			transactionResponse.setAuthId(purchaseResponse.getAuthId() != null ? purchaseResponse.getAuthId() : null);
			transactionResponse.setTxnRefNumber(purchaseResponse.getTxnRefNum() != null ? purchaseResponse.getTxnRefNum()
					: null);
			transactionResponse.setCgRefNumber(purchaseResponse.getUpStreamTxnRefNum());
			transactionResponse.setTxnDateTime(System.currentTimeMillis());
			transactionResponse.setMerchantCode(request.getMerchantCode());
			transactionResponse.setMerchantName(transactionRequest.getMerchantName());
			transactionResponse.setTransactionType(purchaseResponse.getTxnType());

			logExit(pgOnlineTxnLog,
					TransactionStatus.COMPLETED,
					purchaseResponse.getErrorMessage(),
					(transactionResponse.getTxnRefNumber() == null ? "0" : transactionResponse.getTxnRefNumber()),
					purchaseResponse.getErrorMessage(),
					transactionResponse.getTxnRefNumber());
	
			if (purchaseResponse.getErrorCode().equals("00")) {
				
				//Hitting to the Loyalty Service
				LoyaltyResponse response = loyaltyService.invokeLoyalty(transactionRequest, request);
				log.trace(" PGTransactionServiceImpl :: processAuthCapture :: LoyaltyResponse :: " + response.getErrorMessage());
			
				String autoSettlement = pgMerchant.getMerchantConfig().getAutoSettlement() !=null ? pgMerchant.getMerchantConfig().getAutoSettlement().toString() : "0";
				if (autoSettlement != null && autoSettlement.equals("1")) {
					updateSettlementStatus(transactionRequest.getMerchantCode(), transactionRequest.getTerminalId(),
							purchaseResponse.getTxnRefNum(), "Sale", PGConstants.PG_SETTLEMENT_EXECUTED, "Auto Settlement",
							feeAmount,request.getBatchId(), pgOnlineTxnLog);
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
			request.setMerchantCode(transactionRequest.getMerchantCode());
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
			transactionResponse.setMerchantCode(request.getMerchantCode());
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
					transactionRequest.getMerchantCode(),
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
				request.setMerchantCode(transactionRequest.getMerchantCode());
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
				transactionResponse.setMerchantCode(request.getMerchantCode());
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
					transactionRequest.getMerchantCode(),
					transactionRequest.getTerminalId());

			if(null == pgTransaction) {
				log.error("RestService | PGTransactionServiceImpl | processVoid |throwing exception");
				throw new ServiceException(ActionErrorCode.ERROR_CODE_TXN_NULL);
			} else if(pgTransaction.getMerchantSettlementStatus().equalsIgnoreCase(Constants.SETTLEMENT_STATUS)) {
				log.error("RestService | PGTransactionServiceImpl | processVoid |throwing exception");
				throw new ServiceException(ActionErrorCode.ERROR_CODE_TXN_EXECUTED);
			} else if(pgTransaction.getTransactionType().equals(PGConstants.TXN_TYPE_REFUND)) {
				log.error("RestService | PGTransactionServiceImpl | processVoid |throwing exception");
				throw new ServiceException(ActionErrorCode.ERROR_CODE_DUPLICATE_VOID_ENTRY);
			} else {
				reverseSplitSaleIfExists(transactionRequest.getTxnRefNumber(), transactionRequest.getMerchantCode());
				setCardDataAndTransactionRequestData(transactionRequest, pgTransaction);
				CardProgram cardprogram = null;
				if (!transactionRequest.getEntryMode().equals(EntryModeEnum.ACCOUNT_PAY)) {
					cardprogram = cardProgramDao.findCardProgramIdByIinAndPartnerIINCodeAndIinExt(
							CommonUtil.getIIN(transactionRequest.getCardData().getCardNumber()),
							CommonUtil.getPartnerIINExt(transactionRequest.getCardData().getCardNumber()),
							CommonUtil.getIINExt(transactionRequest.getCardData().getCardNumber()));
				}
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
				if (transactionRequest.getEntryMode().equals(EntryModeEnum.ACCOUNT_PAY)) {
					request.setAccountNumber(pgTransaction.getPanMasked());
					request.setEntryMode(transactionRequest.getEntryMode());
				} else {
					request.setEntryMode(transactionRequest.getEntryMode());
					request.setCardType(transactionRequest.getCardData().getCardType().value());
					request.setEmv(transactionRequest.getCardData().getEmv());
				}
				request.setMode(transactionRequest.getMode());
				request.setAcq_channel(transactionRequest.getOriginChannel());
				request.setProcessorMid(transactionRequest.getProcessorMid());
				request.setCardHolderEmail(pgTransaction.getCardHolderEmail());
				request.setUserName(transactionRequest.getUserName());
				request.setCurrencyCode(transactionRequest.getCurrencyCode());
				request.setTimeZoneOffset(transactionRequest.getTimeZoneOffset());
				request.setTimeZoneRegion(transactionRequest.getTimeZoneRegion());
				request.setIsoId(pgTransaction.getIsoId());
				PGMerchant pgMerchant = merchantUpdateDao.getMerchantByCode(transactionRequest.getMerchantCode());
				request.setMerchantId(pgMerchant.getId());
				getMerchantDetails(pgMerchant, request);
				if (!transactionRequest.getEntryMode().equals(EntryModeEnum.ACCOUNT_PAY)) {
					getMerchantBatchId(request, cardprogram, pgMerchant);
				}
				
				VoidResponse voidResponse = new SwitchServiceBroker().voidTransaction(request, pgTransaction);

				transactionResponse.setErrorCode(voidResponse.getErrorCode());
				transactionResponse.setErrorMessage(voidResponse.getErrorMessage());
				transactionResponse.setAuthId(voidResponse.getAuthId() != null ? voidResponse.getAuthId() : null);
				transactionResponse.setCgRefNumber(voidResponse.getUpStreamTxnRefNum());
				transactionResponse.setTxnRefNumber(voidResponse.getTxnRefNum() != null ? voidResponse.getTxnRefNum() : null);
				transactionResponse.setTxnDateTime(System.currentTimeMillis());
				transactionResponse.setTotalTxnAmount((voidResponse.getTxnAmount().doubleValue())/Integer.parseInt("100"));
				transactionResponse.setMerchantCode(request.getMerchantCode());
				transactionResponse.setTransactionType(voidResponse.getTxnType());
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
		if (!transactionRequest.getEntryMode().equals(EntryModeEnum.ACCOUNT_PAY)) {
			card.setCardNumber(EncryptionUtil.decrypt(pgTransaction.getPan()));
			card.setExpDate(pgTransaction.getExpDate().toString());
			card.setCardType(MethodOfPaymentTypeEnum.valueOf(PGUtils.getCCType()));
			card.setCardHolderName(pgTransaction.getCardHolderName());
		}
		transactionRequest.setCardData(card);// setting sale txn card details
		// into void request
		transactionRequest.setTxnAmount(pgTransaction.getTxnTotalAmount());
		transactionRequest.setTotalTxnAmount(pgTransaction.getTxnTotalAmount());
		transactionRequest.setMerchantAmount(pgTransaction.getTxnAmount());
		transactionRequest.setFeeAmount(pgTransaction.getFeeAmount());
		transactionRequest.setInvoiceNumber(pgTransaction.getInvoiceNumber());
	}

	private void setVoidRequestData(TransactionRequest transactionRequest, VoidRequest request) {
		if (!transactionRequest.getEntryMode().equals(EntryModeEnum.ACCOUNT_PAY)) {
			request.setCardNum(transactionRequest.getCardData().getCardNumber());
			request.setExpDate(transactionRequest.getCardData().getExpDate());
			request.setCvv(transactionRequest.getCardData().getCvv());
			request.setTrack2(transactionRequest.getCardData().getTrack2());
			request.setCardHolderName(transactionRequest.getCardData().getCardHolderName());
		}
		request.setMerchantCode(transactionRequest.getMerchantCode());
		request.setTerminalId(transactionRequest.getTerminalId());
		request.setInvoiceNumber(transactionRequest.getInvoiceNumber());
		request.setTxnAmount(transactionRequest.getMerchantAmount());
		request.setTotalTxnAmount(transactionRequest.getTotalTxnAmount());
		request.setTxnFee(StringUtils.getValidLongValue(transactionRequest.getFeeAmount()));
		request.setTxnRefNum(transactionRequest.getTxnRefNumber());
		request.setIssuerTxnRefNum(transactionRequest.getCgRefNumber());
		request.setDescription(transactionRequest.getDescription());
	}

	public Response processReversal(TransactionRequest transactionRequest) {

		log.info("RestService | PGTransactionServiceImpl | authCapture | Entering");
		TransactionResponse transactionResponse = new TransactionResponse();
		try {
			PGTransaction pgTransaction = voidTransactionDao.findTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(transactionRequest.getTxnRefNumber(),
					transactionRequest.getCgRefNumber(),
					transactionRequest.getMerchantCode(),
					transactionRequest.getTerminalId());
			// Logging into Online txn log
			PGOnlineTxnLog pgOnlineTxnLog = logEntry(TransactionStatus.INITATE, transactionRequest);
			ReversalRequest request = new ReversalRequest();
			request.setCardNum(transactionRequest.getCardData().getCardNumber());
			request.setExpDate(transactionRequest.getCardData().getExpDate());
			request.setCvv(transactionRequest.getCardData().getCvv());
			request.setMerchantCode(transactionRequest.getMerchantCode());
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
					transactionRequest.getMerchantCode());

			if(null != pgTransaction) {
				
				if(isTxnAlreadyRefunded(pgTransaction)) {
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
				
				if(pgTransaction.getMerchantSettlementStatus().equals(PGConstants.PG_SETTLEMENT_EXECUTED)) {
					return processVoid(transactionRequest);
				}
				
				Long refundedAmount=refundTransactionDao.getRefundedAmountOnTxnId(pgTransaction.getId().toString());
				if(null!=refundedAmount&&null!=transactionRequest.getTotalTxnAmount()&&((pgTransaction.getTxnTotalAmount()-refundedAmount)<transactionRequest.getTotalTxnAmount())){
					throw new ServiceException(ActionErrorCode.REFUND_AMOUNT_EXCEEDS);
				}
				
				// Check for merchant sufficient balance for a refund
				log.info("RestService | PGTransactionServiceImpl | processRefund | Check for merchant sufficient balance");
				PGAccount pgAccount = accountDao.getPgAccount(transactionRequest.getMerchantCode());
				log.info("RestService | PGTransactionServiceImpl | processRefund | incoming: " + transactionRequest.getTotalTxnAmount());
				log.info("RestService | PGTransactionServiceImpl | processRefund | current bal: " + pgAccount.getCurrentBalance());
				validateTxnAmount(transactionRequest, pgAccount);
				
				CardData card = new CardData();
				card.setCardNumber(EncryptionUtil.decrypt(pgTransaction.getPan()));
				card.setExpDate(pgTransaction.getExpDate().toString());
				card.setCardType(MethodOfPaymentTypeEnum.valueOf(PGUtils.getCCType()));
				card.setCardHolderName(pgTransaction.getCardHolderName());
				transactionRequest.setCardData(card);
				CardProgram cardprogram = cardProgramDao.findCardProgramIdByIinAndPartnerIINCodeAndIinExt(
						CommonUtil.getIIN(transactionRequest.getCardData().getCardNumber()), 
						CommonUtil.getPartnerIINExt(transactionRequest.getCardData().getCardNumber()), 
						CommonUtil.getIINExt(transactionRequest.getCardData().getCardNumber()));
				validateTotalTxnAndMerchantAmount(transactionRequest, pgTransaction);
				transactionRequest.setInvoiceNumber(pgTransaction.getInvoiceNumber());
				// Logging into Online txn log
				PGOnlineTxnLog pgOnlineTxnLog = logEntry(TransactionStatus.INITATE, transactionRequest);
				RefundRequest request = new RefundRequest();
				validateRefundAmount(transactionRequest, pgTransaction, refundedAmount, request);
				request.setCardNum(transactionRequest.getCardData().getCardNumber());
				request.setMerchantCode(transactionRequest.getMerchantCode());
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
				PGMerchant pgMerchant = merchantUpdateDao.getMerchantByCode(transactionRequest.getMerchantCode());
				request.setMerchantId(pgMerchant.getId());
				getMerchantDetails(pgMerchant, request);
				getMerchantBatchId(request, cardprogram, pgMerchant);
				
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

  private boolean isTxnAlreadyRefunded(PGTransaction pgTransaction) {
    return (null != pgTransaction.getRefundStatus() && 1 == pgTransaction.getRefundStatus().intValue()) || (PGConstants.PG_SETTLEMENT_REJECTED.equalsIgnoreCase(pgTransaction.getMerchantSettlementStatus()));
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
		transactionResponse.setMerchantCode(request.getMerchantCode());
		transactionResponse.setTransactionType(refundResponse.getTxnType());
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
			transactionRequest.setMerchantCode(pgTransaction.getMerchantId());
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
			pgOnlineTxnLog.setBillerZip(transactionRequest.getBillingData().getZipCode());
		}
		pgOnlineTxnLog.setMerchantAmount(transactionRequest.getMerchantAmount());
		pgOnlineTxnLog.setMerchantId(transactionRequest.getMerchantCode());
		
		if (transactionRequest.getEntryMode().equals(EntryModeEnum.ACCOUNT_PAY)) {
			pgOnlineTxnLog.setPanMasked(transactionRequest.getAccountNumber());
		} else {
			pgOnlineTxnLog.setPanData(EncryptionUtil.encrypt(transactionRequest.getCardData().getCardNumber()));
			pgOnlineTxnLog.setPanMasked(StringUtils.getMaskedString(transactionRequest.getCardData().getCardNumber(),
					Integer.parseInt("5"), Integer.parseInt("4")));
			pgOnlineTxnLog.setCardHolderName(transactionRequest.getCardData().getCardHolderName());
			// description
			pgOnlineTxnLog.setCardAssciation(transactionRequest.getCardData().getCardType().value());// Adding
		}
		
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
		
		// PERF >> Moving to async service
		asyncService.logExit(pgOnlineTxnLog, txnState, reason, pgTxnId, processorResponse, processTxnId);
		
pgOnlineTxnLog.setPgTxnId(pgTxnId);
	}

	/**
	 * <<Method to process split sale transaction >>
	 * 
	 * @param transactionRequest
	 * @return
	 */
	public Response processSplitSale(TransactionRequest transactionRequest, PGMerchant merchant) {

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
				request.setMerchantCode(transactionRequest.getMerchantCode());
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
				
				PurchaseResponse purchaseResponse = new SwitchServiceBroker().purchaseTransaction(request, merchant);

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
	public Response processSplitReject(TransactionRequest transactionRequest, PGMerchant merchant) throws SplitTransactionException {
		Response splitRejectResponse = new Response();
		PGSplitTransaction pgSplitTransaction = splitTransactionDao.getPGSplitTransactionByMerchantIdAndPgRefTransactionIdAndSplitAmount(transactionRequest.getMerchantCode(),
				transactionRequest.getSplitRefNumber(),
				transactionRequest.getTotalTxnAmount());

		if(pgSplitTransaction.getStatus().equals(Long.valueOf(PGConstants.STATUS_INPROCESS))) {
			PGTransaction txnToVoid = voidTransactionDao.findTransactionToReversalByMerchantIdAndPGTxnId(pgSplitTransaction.getMerchantId(),
					pgSplitTransaction.getPgRefTransactionId());
			pgSplitTransaction.setStatus(Long.valueOf((PGConstants.STATUS_FAILED.toString())));
			if(null != txnToVoid) {
				TransactionRequest voidRequest = new TransactionRequest();
				voidRequest.setTerminalId(txnToVoid.getTerminalId());
				voidRequest.setMerchantCode(txnToVoid.getMerchantId());
				voidRequest.setCgRefNumber(txnToVoid.getIssuerTxnRefNum());
				voidRequest.setTransactionType(TransactionType.VOID);
				voidRequest.setTxnRefNumber(txnToVoid.getTransactionId());
				splitRejectResponse = processVoid(voidRequest);
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
		if(null != pgSplitTransaction && pgSplitTransaction.getStatus().equals(Long.valueOf(PGConstants.STATUS_SUCCESS))) {
			String splitTransactionId = pgSplitTransaction.getPgRefTransactionId();
			String splitTxnMerchantId = pgSplitTransaction.getMerchantId();
			PGTransaction pgTransaction = voidTransactionDao.findTransactionToReversalByMerchantIdAndPGTxnId(splitTxnMerchantId,
					splitTransactionId);
			if(null != pgTransaction) {
				TransactionRequest requestSplitReverse = new TransactionRequest();
				requestSplitReverse.setMerchantCode(splitTxnMerchantId);
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
					transactionRequest.getMerchantCode(),
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
			if(CommonUtil.isNullAndEmpty(transactionRequest.getMerchantCode())
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
				
				String output = (String) JsonUtil.sendToIssuance(String.class,
						transactionDTO, property);
				transactionResponse=mapper.readValue(output, TransactionDTOResponse.class);
				if(transactionResponse.getTransactionDTO() != null && transactionResponse.getTransactionDTO().size() > 0) {
					validateTransactionDTO(transactionResponse);
				}
				
		    } else {
		    	log.info("Invalid Merchant: "+transactionRequest.getMerchantCode());
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

	  public void updateSettlementStatus(String merchantId,
              String terminalId, String txnId, String txnType,
              String status, String comments, long feeAmount,String batchId, PGOnlineTxnLog pgOnlineTxnLog) throws Exception {
		  // PERF >> Moved to async
		  asyncService.updateSettlementStatus(merchantId, terminalId, txnId, txnType, status, comments, feeAmount, batchId, pgOnlineTxnLog);
	  }

	  public List<Object> getProcessingFee(String cardType, String merchantCode, Long txnTotalAmount) throws DataAccessException, Exception {
		  log.info("Entering :: PGTransactionServiceImpl :: getProcessingFee method ");
		  List<Object> results = new ArrayList<Object>(Integer.parseInt("2"));
		  List<ProcessingFee> calculatedProcessingFeeList = new ArrayList<ProcessingFee>(0);
		  Double calculatedProcessingFee = null;
		  Long chatakFeeAmountTotal = 0l;
		  List<PGAcquirerFeeValue> acquirerFeeValueList = feeProgramDao.getAcquirerFeeValueByMerchantIdAndCardType(merchantCode,cardType);
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
	  
	  public void updateAccountCCTransactions(String pgTransactionId, String txnType, String newStatus, Long totalFeeAmount) {
		  // PERF >> Moved to async
		  asyncService.updateAccountCCTransactions(pgTransactionId, txnType, newStatus, totalFeeAmount);
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
		if (transactionRequest.getEntryMode().equals(EntryModeEnum.ACCOUNT_PAY)) {
			balanceRequest.setAccountNumber(transactionRequest.getAccountNumber());
			balanceRequest.setEntryMode(transactionRequest.getEntryMode());
		} else {
			balanceRequest.setCardNum(transactionRequest.getCardData().getCardNumber());
			balanceRequest.setCvv(transactionRequest.getCardData().getCvv());
			balanceRequest.setExpDate(transactionRequest.getCardData().getExpDate());
			balanceRequest.setUid(transactionRequest.getCardData().getUid());
			balanceRequest.setCardHolderEmail(transactionRequest.getCardData().getCardHolderName());
		}
      balanceRequest.setMerchantCode(transactionRequest.getMerchantCode());
      balanceRequest.setTerminalId(transactionRequest.getTerminalId());
      PGMerchant merchantData = merchantUpdateDao.getMerchant(transactionRequest.getMerchantCode());
      PGCurrencyConfig currencyDetails = currencyConfigRepository.findByCurrencyCodeAlpha(merchantData.getLocalCurrency());
      balanceRequest.setCurrencyCode(currencyDetails.getCurrencyCodeNumeric());
      getMerchantDetails(merchantData, balanceRequest);
      
      balanceRequest.setPosEntryMode(transactionRequest.getPosEntryMode() + "0");
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
    request.setAddress(
        pgMerchant.getBusinessName() + ", " + pgMerchant.getAddress1() + ", " + pgMerchant.getCity());
  }

  private Response getErrorResponse(String errorCode) {
    Response response = new Response();
    response.setErrorCode(errorCode);
    response.setErrorMessage(messageSource.getMessage(errorCode, null, LocaleContextHolder.getLocale()));
    response.setTxnDateTime(System.currentTimeMillis());
    return response;
  }

  private void getMerchantBatchId(Request request, CardProgram cardProgram, PGMerchant pgMerchant)throws ServiceException {
	    //log.info("Entering :: PGTransactionServiceImpl :: getMerchantBatchId ::  Card program ID " + cardProgram.getCardProgramId());
		Long pmId;
			
			
			  List<ProgramManagerRequest> programManagerRequestsList = isoServiceDao.findPmByIsoId(request.getIsoId());
			  pmId = programManagerRequestsList.get(0).getProgramManagerId();
			
			if (null != pmId) {
			    log.info(" Program Manager ID "+pmId);
			    request.setPmId(pmId);
				ProgramManagerRequest programManagerRequest = programManagerDao.findStatusAndBatchPrefixByProgramManagerId(pmId);
				log.info(" PM ID "+programManagerRequest.getId());
				
				// Check PM status
				if(!programManagerRequest.getStatus().equals("Active")) {
				  throw new ServiceException(ActionErrorCode.ERROR_CODE_PM_02);
				}
				
				PGBatch batchResponse = batchDao.getBatchIdByProgramManagerId(programManagerRequest.getId());
				
				if (batchResponse.getStatus() !=null && batchResponse.getStatus().equals(PGConstants.BATCH_STATUS_ASSIGNED)) {
					request.setBatchId(batchResponse.getBatchId());
					
				} else if (isStatusProcessingOrCompleted(batchResponse)) {
					processCompletedBatchId(request, batchResponse);
					
				} else {
					generateNewBatchId(request, programManagerRequest);
				}
			} else {
			  throw new ServiceException(ActionErrorCode.ERROR_CODE_PM_01);
			}
		log.info("Exiting :: PGTransactionServiceImpl :: getMerchantBatchId");
	}

  private boolean isStatusProcessingOrCompleted(PGBatch batchResponse) {
    return (batchResponse.getStatus() !=null && batchResponse.getStatus().equals(PGConstants.BATCH_STATUS_PROCESSING))
    		|| (batchResponse.getStatus() !=null && batchResponse.getStatus().equals(PGConstants.BATCH_STATUS_COMPLETED));
  }

	private synchronized void processCompletedBatchId(Request request, PGBatch batch) {
		long number = Long.parseLong(batch.getBatchId().subSequence(Constants.FIVE, batch.getBatchId().length()).toString()) + 1l;
		batch.setBatchId(batch.getBatchId().subSequence(0, Constants.FIVE) + String.format("%05d", number));
		batch.setProgramManagerId(batch.getProgramManagerId());
		batch.setStatus(PGConstants.BATCH_STATUS_ASSIGNED);
		batch.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		batchDao.save(batch);
		request.setBatchId(batch.getBatchId());
	}
     // Creating New BatchId
	private synchronized void generateNewBatchId(Request request, ProgramManagerRequest programManagerRequest) {
		PGBatch batch = new PGBatch();
		batch.setProgramManagerId(programManagerRequest.getId());
		batch.setBatchId(programManagerRequest.getBatchPrefix() + String.format("%05d", 1));
		batch.setStatus(PGConstants.BATCH_STATUS_ASSIGNED);
		batch.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		batchDao.save(batch);
		request.setBatchId(batch.getBatchId());
	}
	
	@Override
	public TransactionHistoryResponse getMerchantTransactionList(TransactionHistoryRequest transactionHistoryRequest) {
		log.info("Entering :: PGTransactionServiceImpl :: getMerchantTransactionList");
		List<TransactionHistory> transactions = refundTransactionDao
				.getMerchantTransactionList(transactionHistoryRequest);
		TransactionHistoryResponse transactionHistoryResponse = new TransactionHistoryResponse();
		try {
			if (StringUtil.isListNotNullNEmpty(transactions)) {
				transactionHistoryResponse.setTransactionList(transactions);
				transactionHistoryResponse.setErrorCode(Constants.SUCCESS_CODE);
				transactionHistoryResponse.setErrorMessage(Constants.SUCCESS);
			} else {
				transactionHistoryResponse.setTransactionList(transactions);
				transactionHistoryResponse.setErrorCode(Constants.ERROR_CODE);
				transactionHistoryResponse.setErrorMessage(Constants.ERROR);
			}
		} catch (Exception exp) {
			log.error("Error :: PGTransactionServiceImpl :: getMerchantTransactionList method", exp);
			transactionHistoryResponse.setErrorCode(ChatakPayErrorCode.TXN_0999.name());
			transactionHistoryResponse.setErrorMessage(messageSource.getMessage(ChatakPayErrorCode.TXN_0999.name(),
					null, LocaleContextHolder.getLocale()));
		}
		log.info("Exiting :: PGTransactionServiceImpl :: getMerchantTransactionList");
		return transactionHistoryResponse;
	}
	
	private Long cardRangeValidation(List<PanRangeRequest> panRangesList, TransactionRequest transactionRequest,
			PurchaseRequest request) {
		Long panId = 0l;
		for (PanRangeRequest panRangeRequest : panRangesList) {
			int panLength = (panRangeRequest.getPanLow().toString()).length();
			String cardNumber = transactionRequest.getCardData().getCardNumber().substring(0, panLength);
			if (panRangeRequest.getPanLow() <= Long.valueOf(cardNumber)
					&& Long.valueOf(cardNumber) <= panRangeRequest.getPanHigh()) {
				request.setIsoId(panRangeRequest.getIsoId());
				request.setPanId(panRangeRequest.getId());
				return panRangeRequest.getId();
			}
		}
		return panId;
	}
	
	@Override
	public SessionKeyResponse getSessionKeyForTmk(SessionKeyRequest sessionKeyRequest) {
		SessionKeyResponse sessionKeyResponse = new SessionKeyResponse();
		try {
			sessionKeyResponse = JsonUtil.postRequestForRKI(SessionKeyResponse.class, sessionKeyRequest, JsonUtil.HSM_SERVICE_URL+Properties.getProperty("hsm.service.session.key.endpoint"),JsonUtil.TOKEN_TYPE_BASIC +Properties.getProperty("hsm.service.oauth.token"));
			if(sessionKeyResponse != null &&  StringUtils.isNotNullAndEmpty(sessionKeyResponse.getSessionKeyUnderLMK())) {
				MPosSessionKey mPosSessionKey = null;
				mPosSessionKey = mPosSessionKeyRepository.findByDeviceSerial(sessionKeyRequest.getDeviceSerialNumber());
				if(mPosSessionKey != null) {
					mPosSessionKeyRepository.updateMPosSessionKeyDeviceSkByDeviceSerail(
							sessionKeyResponse.getSessionKeyUnderLMK(), sessionKeyRequest.getDeviceSerialNumber());
				} else {
				mPosSessionKey = new MPosSessionKey();
				mPosSessionKey.setDeviceSerail(sessionKeyRequest.getDeviceSerialNumber());
				mPosSessionKey.setDeviceSk(sessionKeyResponse.getSessionKeyUnderLMK());
				mPosSessionKeyRepository.save(mPosSessionKey);
				}
			}
		} catch (ChatakPayException | HttpClientException e) {
			log.error("Error :: PGTransactionServiceImpl :: getSessionKeyForTmk method", e);
		}
		
		return sessionKeyResponse; 
	}
	
	@Override
	public TmkDataResponse getTMKByDeviceSerialNumber(TmkDataRequest tmkDataRequest) {
		TmkDataResponse tmkDataResponse = new TmkDataResponse();
		try {
			tmkDataResponse = JsonUtil.postRequestForRKI(TmkDataResponse.class, tmkDataRequest, JsonUtil.TMS_SERVICE_URL+Properties.getProperty("tms.service.tmk.endpoint"),JsonUtil.TOKEN_TYPE_BEARER);
			} catch (ChatakPayException | HttpClientException e) {
				log.error("Error :: PGTransactionServiceImpl :: getTMKByDeviceSerialNumber method", e);
			}
			return tmkDataResponse;
	}
}
